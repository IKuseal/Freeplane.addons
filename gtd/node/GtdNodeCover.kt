package org.freeplane.features.gtd.node

import org.freeplane.features.custom.treestream.forEach
import org.freeplane.features.custom.treestream.forEachWithSpread
import org.freeplane.features.custom.map.NodeCover
import org.freeplane.features.custom.map.currentNodeCover
import org.freeplane.features.custom.map.extensions.InheritedExtensionsOfType
import org.freeplane.features.custom.map.extensions.WrapperExtension
import org.freeplane.features.custom.treestream.nextAncestor
import org.freeplane.features.custom.pinneddata.IPinnedDataContainer
import org.freeplane.features.custom.pinneddata.PinnedDataContainer
import org.freeplane.features.gtd.intent.GtdIntent
import org.freeplane.features.gtd.data.elements.*
import org.freeplane.features.gtd.data.elements.GtdStateClass.I
import org.freeplane.features.gtd.intent.GtdIntentController
import org.freeplane.features.gtd.intent.IGtdIntent
import org.freeplane.features.gtd.intentsfiltering.intentsFilteringConditionModel
import org.freeplane.features.gtd.layers.GcsType
import org.freeplane.features.gtd.map.cover.gtdMapCover
import org.freeplane.features.gtd.tag.*
import org.freeplane.features.map.NodeModel

class GtdNodeCover(
    val nodeCover: NodeCover
) : GtdNode(),
    IPinnedDataContainer by PinnedDataContainer(),
    IGtdIntentSetListener
{
    inner class GtdIntentCover(val model: GtdIntent) : IGtdIntent {
        override val state: GtdState by model::state
        override val gcsType: GcsType  by model::gcsType
        override val subStatesMap: Map<GtdSubStateClass, GtdSubState>  by model::subStatesMap

//        val coveringIntent : GtdIntent? = null

        override val tags: Set<GtdTag>
            get() {
                val isNoPlanes = model.allPlanes.isEmpty()
                val isNoOtherTags = model.allOtherTags.isEmpty()

                return when {
                    !(isNoPlanes || isNoOtherTags) -> model.tags
                    else -> mutableSetOf<GtdTag>().apply {
                        addAll(model.tags)
                        if(isNoPlanes) add(GTD_PLANE_NO_PLANES)
                        if(isNoOtherTags) add(GTD_TAG_NO_OTHER_TAGS)
                    }
                }
            }

        // * * *
        override fun copy() = GtdIntent(state, tags, gcsType).apply {
            subStates.forEach {
                putSubState(it)
            }
        }

        override fun hashCode() = model.hashCode()

        override fun equals(other: Any?) : Boolean {
            if(this === other) return true
            other ?: return false
            if(other !is IGtdIntent) return false
            return other.equalsInternal(model)
        }

        override fun equalsInternal(other: IGtdIntent) =
            other.equalsInternal(model)
    }

    // GtdIntentDefault ************************************************************************
    private fun isCustomMode(plane : GtdPlane) =
        !(plane.totalCoverageType || gtdMapCover.totalCoverModeParamsNotSet)

    inner class GtdIntentDefault(
        plane: GtdPlane
    ) : GtdIntent() {
        val planeOfDefaultIntent = plane
        val isCustomMode = isCustomMode(planeOfDefaultIntent)

        val original : GtdIntent = GtdIntent(
            gtdMapCover.totalCoverageModeState.takeIf { isCustomMode } ?: I(GtdNum.ND),
            arrayListOf<GtdTag>(plane)
                .also {
                    if (!isCustomMode(plane))
                        it.add(GTD_TAG_REVIEW)
                },
            gtdMapCover.totalCoverageModeGcsType.takeIf { isCustomMode } ?: GcsType.GLOB
        )

        var isSubstituted = false

        override var state: GtdState
            get() = original.state
            set(value) {
                original.state = value
                if(!isSubstituted) {
                    if(!isCustomMode) {
                        original.removeTag(GTD_TAG_REVIEW)
                        original.gcsType = GcsType.LOCAL
                    }
                    substitute()
                }
            }

        override var gcsType: GcsType
            get() = original.gcsType
            set(value) {
                original.gcsType = value
                if(!isSubstituted) substitute()
            }

        override val subStatesMap: Map<GtdSubStateClass, GtdSubState> by original::subStatesMap

        override fun putSubState(subState: GtdSubState): GtdSubState? {
            val oldState = original.putSubState(subState)
            if(!isSubstituted) substitute()
            return oldState
        }

        override fun putAllSubStates(subStates: Collection<GtdSubState>) {
            original.putAllSubStates(subStates)
            if(!isSubstituted) substitute()
        }

        override fun removeSubState(subStateClass: GtdSubStateClass): GtdSubState? {
            if(!isSubstituted) return null

            return original.removeSubState(subStateClass)
        }

        override val tags: Set<GtdTag> by original::tags

        override fun addTag(tag: GtdTag) {
            if(!isSubstituted) substitute()
            original.addTag(tag)
        }

        override fun addTags(tags: Collection<GtdTag>) {
            if(!isSubstituted) substitute()
            original.addTags(tags)
        }

        override fun removeTag(tag: GtdTag) {
            if(!isSubstituted) substitute()
            original.removeTag(tag)
        }

        override fun hashCode() = original.hashCode()

        override fun equals(other: Any?): Boolean {
            if(this === other) return true
            if(other == null) return false
            if(other !is IGtdIntent) return false
            return other.equalsInternal(original)
        }

        override fun equalsInternal(other: IGtdIntent) =
            other.equalsInternal(original)

        private fun substitute() {
            isSubstituted = true
            GtdIntentController.addIntent(gtdNodeModel, original)
        }
    }

    fun isToUseCustomParams(plane: GtdPlane) =
        gtdMapCover

    object FakeGtdIntent : GtdIntent(plane = GTD_PLANE_FAKE) {
        override var plane: GtdPlane
            get() = super.plane
            set(value) {}

        override var state: GtdState
            get() = GtdStateClass.F.default
            set(value) {}

        override var gcsType: GcsType
            get() = GcsType.LOCAL
            set(value) {}

        override val subStates: Collection<GtdSubState> get() = listOf()
        override fun getSubState(subStateClass: GtdSubStateClass) = null
        override fun putSubState(subState: GtdSubState) = null
        override fun removeSubState(subStateClass: GtdSubStateClass) = null
        override fun containsSubState(subStateClass: GtdSubStateClass) = false

//        override val isEmpty = true

        override fun copy(): GtdIntent {
            return this
        }
    }

    override val node = nodeCover.node
    val gtdNodeModel = node.gtdNode
    init { gtdNodeModel.addIntentSetListener(this) }

    val gtdMapCover = nodeCover.mapCover.gtdMapCover

    // tree ************************************************************************************************
    val parent : GtdNodeCover? get() = nodeCover.parent?.gtdNodeCover

    val children : Collection<GtdNodeCover> get() = nodeCover.children.map { it.gtdNodeCover }

    // intents **********************************************************************************************
    lateinit var allIntents : Collection<GtdIntentCover>

    override lateinit var intents: Collection<GtdIntentCover>

    lateinit var shadowedIntents : Collection<GtdIntentCover>

    val modelIntents : Collection<GtdIntentCover>
        get() = gtdNodeModel.intents.map { it.cover }

    private fun calculateExtraIntents() : Collection<GtdIntentCover> {
        fun defaultIntentForTopmostPlane(usedPlanes : Collection<GtdPlane>) : GtdIntent? {
            if(!gtdMapCover.isTotalCoverageMode) return null
            val topmostPlane = intentsOrderStrategy.topmostPlane
                .takeIf { !it.totalCoverageType  && !usedPlanes.contains(it) } ?: return null

            return GtdIntentDefault(topmostPlane)
        }

        fun defaultIntentsForTotalCoveragePlanes(usedPlanes : Collection<GtdPlane>) =
            builtinTotalCoveragePlanes
                .filterNot(usedPlanes::contains)
                .map(::GtdIntentDefault)

        val extraIntents = arrayListOf<GtdIntent>()

        val usedPlanes = modelIntents.map(GtdIntentCover::allPlanes).flatten()

        defaultIntentForTopmostPlane(usedPlanes)?.let(extraIntents::add)
        defaultIntentsForTotalCoveragePlanes(usedPlanes).let(extraIntents::addAll)

        return extraIntents.map { it.cover }
    }

    lateinit var orderedIntents : List<GtdIntentCover>

    private fun sortIntents() {
        orderedIntents = intentsOrderStrategy.orderedIntents(intents)
    }

    var intentsOrderStrategy : IntentsOrderStrategy = StandardIntentsOrderStrategy(this)

    // intentToTop ********************
    var intentToTop : GtdIntent? = null

    private fun revalidateIntentToTopOnIntentRemove(intent: GtdIntent) {
        if(intentToTop == intent) intentToTop = null
    }

    // current intent ****************************************************************************************
    val intent : GtdIntentCover?
        get() = intentToTop?.cover ?: orderedIntents.firstOrNull()

    val intentModel : GtdIntent?
        get() = intent?.model

    val intentOrFake : GtdIntentCover
        get() = intent ?: FakeGtdIntent.cover

    val intentState : GtdState get() = intentOrFake.state

    val intentTags : Set<GtdTag> get() = intentOrFake.tags

    val isFakeIntent get() = intent == null

    // * * *
    override fun onIntentRemove(intent: GtdIntent) {
        revalidateIntentToTopOnIntentRemove(intent)
    }

    // invalidate ************************************************************************************************
    fun invalidate() {
        allIntents = arrayListOf<GtdIntentCover>().apply {
            addAll(modelIntents)
            addAll(calculateExtraIntents())
        }

        val intentsByFilteringResult = allIntents.groupBy(gtdMapCover.intentsFilteringConditionModel::check)
        intents = intentsByFilteringResult[true] ?: listOf()
        shadowedIntents = intentsByFilteringResult[false] ?: listOf()

        sortIntents()
    }

    // copy *****************************************************************************************************
    override fun copy(): GtdNodeModel {
        val copyIntents : (GtdNodeModel) -> Unit = { node ->
            intents.forEach {
                node.addIntent(it.model.copy())
            }
        }
        return gtdNodeModel.copy(copyIntents)
    }

    // extensions ************************************************************************************************
    // transparency
    var isTransparent = false

    // heap ******************************************************************************************************
    val GtdIntent.cover: GtdIntentCover
        get() = GtdIntentCover(this)

    init {
        invalidate()
    }
}

// access **********************************************************************************************
val NodeModel.currentGtdNodeCover get() = currentNodeCover.gtdNodeCover

val NodeCover.gtdNodeCover get() =
    getExtension(GtdNodeCover::class) ?: GtdNodeCover(this).also { putExtension(it) }

val GtdNodeModel.currentCover get() = node.currentGtdNodeCover

// inherited extensions *******************************************************************************
val InheritedExtensionsOfType<out WrapperExtension<out GtdStatus>>.status : GtdStatus?
    get() = list.asSequence().map{it.value}.firstOrNull()

val InheritedExtensionsOfType<WrapperExtension<GtdState>>.state : GtdState?
    get() = status as GtdState?

val InheritedExtensionsOfType<WrapperExtension<GtdSubState>>.subState : GtdSubState?
    get() = status as GtdSubState?

// tree *************************************************************************************************
fun GtdNodeCover.forEach(depth : Int = Int.MAX_VALUE, action : GtdNodeCover.() -> Unit) {
    this.forEach(GtdNodeCover::children, depth, action)
}

fun <T> GtdNodeCover.forEachWithSpread(initial : T, depth : Int = Int.MAX_VALUE, action : GtdNodeCover.(T) -> T) {
    this.forEachWithSpread(GtdNodeCover::children, initial, depth, action)
}

fun GtdNodeCover.nextAncestor(condition : (GtdNodeCover) -> Boolean) : GtdNodeCover? {
    return this.nextAncestor(GtdNodeCover::parent, condition)
}

// relations *************************************************************************************************
val GtdNodeCover.gtdMapCRoot get() = gtdMapCover.root

val GtdNodeCover.branchRoot get() = nextAncestor { it.parent == null } ?: this


// GtdIntentCover **********************************************************************************
//val GtdNodeCover.GtdIntentCover.planes get() =
typealias GtdIntentCover = GtdNodeCover.GtdIntentCover

val GtdIntentCover.isDefault get() = model is GtdNodeCover.GtdIntentDefault