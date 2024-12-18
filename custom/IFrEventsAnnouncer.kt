package org.freeplane.features.custom

import org.freeplane.core.resources.IFreeplanePropertyListener
import org.freeplane.features.map.IMapChangeListener
import org.freeplane.features.map.IMapLifeCycleListener
import org.freeplane.features.map.IMapSelectionListener
import org.freeplane.features.map.INodeChangeListener
import org.freeplane.features.map.INodeSelectionListener

interface IFrEventsAnnouncer {

    fun addNodeChangeListener(iNodeChangeListener: INodeChangeListener?): Boolean
    fun removeNodeChangeListener(iNodeChangeListener: INodeChangeListener?): Boolean

    fun addNodeSelectionListener(iNodeSelectionListener: INodeSelectionListener?): Boolean
    fun removeNodeSelectionListener(iNodeSelectionListener: INodeSelectionListener?): Boolean

    fun addMapChangeListener(iMapChangeListener: IMapChangeListener?): Boolean
    fun removeMapChangeListener(iMapChangeListener: IMapChangeListener?): Boolean

    fun addMapSelectionListener(iMapSelectionListener: IMapSelectionListener?): Boolean
    fun removeMapSelectionListener(iMapSelectionListener: IMapSelectionListener?): Boolean

    fun addMapLifecycleListener(mapLifecycleListener: IMapLifeCycleListener?): Boolean
    fun removeMapLifecycleListener(mapLifecycleListener: IMapLifeCycleListener?): Boolean

    fun addPropertyChangeListener(propertyListener: IFreeplanePropertyListener?): Boolean
    fun removePropertyChangeListener(propertyListener: IFreeplanePropertyListener?): Boolean
}