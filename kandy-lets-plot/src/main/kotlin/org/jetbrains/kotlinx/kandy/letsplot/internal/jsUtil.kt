package org.jetbrains.kotlinx.kandy.letsplot.internal

import org.jetbrains.letsPlot.core.util.PlotHtmlHelper

internal const val LETS_PLOT_JS_VERSION = "4.2.0"

internal val letsPlotJSUrl: String
    get() = PlotHtmlHelper.scriptUrl(LETS_PLOT_JS_VERSION)