/*
* Copyright 2020-2022 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/
package org.jetbrains.kotlinx.ggdsl.letsplot.layers

import org.jetbrains.kotlinx.ggdsl.dsl.LayerCollectorContext
import org.jetbrains.kotlinx.ggdsl.dsl.LayerContext
import org.jetbrains.kotlinx.ggdsl.dsl.PlotDslMarker
import org.jetbrains.kotlinx.ggdsl.letsplot.*

// TODO

@PublishedApi
internal val V_LINE: LetsPlotGeom = LetsPlotGeom("vLine")


@PlotDslMarker
public class VLineContext(parent: LayerCollectorContext) : LayerContext(parent) {
    public val x: XInterceptAes = XInterceptAes(this)

    public val color: ColorAes = ColorAes(this)
    public val alpha: AlphaAes = AlphaAes(this)
    public val type: LineTypeAes = LineTypeAes(this)
    public val width: SizeAes = SizeAes(this)
}


public inline fun LayerCollectorContext.vLine(block: VLineContext.() -> Unit) {
    addLayer(VLineContext(this).apply(block), V_LINE)
}
