package org.freeplane.features.gtd.conditionalstyles

import org.freeplane.features.gtd.data.elements.GtdNum
import org.freeplane.features.gtd.data.elements.GtdNum.*
import org.freeplane.features.gtd.data.elements.GtdStateClass
import org.freeplane.features.gtd.data.elements.GtdStateClass.*
import org.freeplane.features.gtd.data.elements.GtdSubStateClass
import org.freeplane.features.gtd.data.elements.GtdSubStateClass.CD
import org.freeplane.features.gtd.data.elements.GtdSubStateClass.DSC
import org.freeplane.features.gtd.layers.GcsType
import org.freeplane.features.gtd.layers.GcsType.*
import org.freeplane.features.gtd.tag.*
import org.freeplane.features.styles.IStyle
import org.freeplane.features.styles.StyleString

val gtdNumStyles = linkedMapOf<GtdNum, IStyle>().apply {
    this[V0] = StyleString("V0")
    this[V1] = StyleString("V1")
    this[V2] = StyleString("V2")
    this[S0] = StyleString("S0")
    this[S1] = StyleString("S1")
    this[S2] = StyleString("S2")
    this[S3] = StyleString("S3")
    this[S4] = StyleString("S4")
    this[S5] = StyleString("S5")
    this[S6] = StyleString("S6")
    this[S7] = StyleString("S7")
    this[S8] = StyleString("S8")
    this[S9] = StyleString("S9")
    this[T] = StyleString("T")
    this[ND] = StyleString("ND")
}
val GtdNum.style : IStyle? get() = gtdNumStyles[this]

val gtdStateClassStyles = linkedMapOf<GtdStateClass, IStyle>().apply {
    this[F] = StyleString("F")
    this[I] = StyleString("I")
    this[I2] = StyleString("I2")
    this[R] = StyleString("Z")
    this[L] = StyleString("L")

//    this[Z] = StyleString("Z")
    this[Y2] = StyleString("Y2")
    this[Y0] = StyleString("Y0")
    this[W2] = StyleString("W2")
    this[W1] = StyleString("W1")
    this[W0] = StyleString("W0")
    this[E2] = StyleString("E2")
    this[E1] = StyleString("E1")
    this[E0] = StyleString("E0")
    this[S] = StyleString("S")
    this[U2] = StyleString("U2")
    this[U0] = StyleString("U0")
    this[O] = StyleString("O")
    this[Q2] = StyleString("Q2")
    this[Q1] = StyleString("Q1")
    this[Q0] = StyleString("Q0")
}
val STYLE_GTD_STATE_R5 = StyleString("R")
val GtdStateClass.style : IStyle? get() = gtdStateClassStyles[this]

val gtdSubStateClassStyles = linkedMapOf<GtdSubStateClass, IStyle>().apply {
    this[DSC] = StyleString("DSC")
    this[CD] = StyleString("CNDT")
}
val GtdSubStateClass.style : IStyle? get() = gtdSubStateClassStyles[this]

val gtdTagStyles = linkedMapOf<GtdTag, IStyle>().apply {
    this[GTD_TAG_GCS_EXCLUDED] = StyleString("gtd_tag_gcs_excluded")

    this[GTD_PLANE_TRACKED] = StyleString("gtd_plane_tracked")
    this[GTD_PLANE_OBSERVATION] = StyleString("gtd_plane_observation")
    this[GTD_PLANE_CART] = StyleString("gtd_plane_cart")
    this[GTD_PLANE_BUFFER] = StyleString("gtd_plane_buffer")
    this[GTD_PLANE_MEMO] = StyleString("gtd_plane_memo")
    this[GTD_TAG_PERIODICAL] = StyleString("gtd_tag_periodical")
    this[GTD_TAG_ARTIFACT_REVIEW] = StyleString("gtd_plane_artifact_review")
    this[GTD_TAG_REVIEW] = StyleString("gtd_plane_review")
    this[GTD_TAG_LIGHT_REVIEW] = StyleString("gtd_plane_light_review")
    this[GTD_TAG_SYNC_REVIEW] = StyleString("gtd_plane_sync_review")
    this[GTD_TAG_CHECK0] = StyleString("gtd_tag_check0")
    this[GTD_TAG_CHECK1] = StyleString("gtd_tag_check1")
    this[GTD_TAG_ARCHIVE] = StyleString("ZIP")
    this[GTD_TAG_BOOKMARK] = StyleString("gtd_tag_bookmark")
    this[GTD_TAG_EMBED] = StyleString("gtd_tag_embed")
    this[GTD_TAG_PMC] = StyleString("gtd_tag_pmc")
    this[GT_TAG_MINDDOC] = StyleString("gtd_tag_minddoc")
    this[GTD_TAG_DIRECTION] = StyleString("gtd_tag_direction")
    this[GTD_TAG_COLLECTOR] = StyleString("gtd_tag_collector")
    this[GTD_PLANE_DELIVERY] = StyleString("gtd_plane_delivery")

    this[GTD_PLANE_BLACK] = StyleString("gtd_plane_black")
    this[GTD_PLANE_BLUE] = StyleString("gtd_plane_blue")
    this[GTD_PLANE_RED] = StyleString("gtd_plane_red")
    this[GTD_PLANE_GREEN] = StyleString("gtd_plane_green")
    this[GTD_PLANE_YELLOW] = StyleString("gtd_plane_yellow")

    this[GTD_PLANE_NO_PLANES] = StyleString("gtd_plane_no_planes")
}
val GtdTag.style : IStyle? get() = gtdTagStyles[this]

val STYLE_GTD_INTENT_DEFAULT : IStyle = StyleString("gtd_intent_default")

val gcsTypeStyles = linkedMapOf<GcsType, IStyle>().apply {
    this[GLOB] = StyleString("glob_gcs_type")
    this[MODULE] = StyleString("module_gcs_type")
    this[DIRECTION] = StyleString("direction_gcs_type")
    this[SUBDIRECTION] = StyleString("subdirection_gcs_type")
}
val GcsType.style : IStyle? get() = gcsTypeStyles[this]

val STYLE_TRANSPARENT  : IStyle = StyleString("transparent")

val STYLE_GTD_MODULE : IStyle = StyleString("gtd_module")

val STYLE_LAYER : IStyle = StyleString("layer")
val STYLE_LAYER_LOCAL_0 : IStyle = StyleString("layer_local_0")
val STYLE_LAYER_LOCAL_1 : IStyle = StyleString("layer_local_1")
val STYLE_LAYER_SUBDIRECTION_0 : IStyle = StyleString("layer_subdirection_0")
val STYLE_LAYER_SUBDIRECTION_1 : IStyle = StyleString("layer_subdirection_1")
val STYLE_LAYER_DIRECTION_0 : IStyle = StyleString("layer_direction_0")
val STYLE_LAYER_DIRECTION_1 : IStyle = StyleString("layer_direction_1")

// Ai ***********************************************************************************
val STYLE_AI_0 : IStyle = StyleString("Ai0")
val STYLE_AI_1 : IStyle = StyleString("Ai1")
val STYLE_AI_2 : IStyle = StyleString("Ai2")
val STYLE_AI_3 : IStyle = StyleString("Ai3")
val STYLE_AI_4 : IStyle = StyleString("Ai4")