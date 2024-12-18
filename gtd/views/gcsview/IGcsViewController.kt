package org.freeplane.features.gtd.views.gcsview

import org.freeplane.features.gtd.layers.Gcs

interface IGcsViewController {
    fun showGcsView(gcs : Gcs)
    fun hideGcsView()
    val isGcsViewShown : Boolean
}