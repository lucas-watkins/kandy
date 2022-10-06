/*
* Copyright 2020-2022 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/

package org.jetbrains.kotlinx.ggdsl.letsplot.layers

import org.jetbrains.kotlinx.ggdsl.dsl.LayerCollectorContext
import org.jetbrains.kotlinx.ggdsl.dsl.PlotContext
import org.jetbrains.kotlinx.ggdsl.dsl.PlotDslMarker
import org.jetbrains.kotlinx.ggdsl.letsplot.*
import org.jetbrains.kotlinx.ggdsl.letsplot.util.linetype.LineType
import org.jetbrains.kotlinx.ggdsl.util.color.Color

@PublishedApi
internal val AREA: LetsPlotGeom = LetsPlotGeom("area")

@PlotDslMarker
public class AreaContext @PublishedApi internal constructor(parent: LayerCollectorContext) :
    WithBorderLineContext(parent) {
    public val x: XAes = XAes(this)
    public val y: YAes = YAes(this)


    public val color: FillAes = FillAes(this)
    public val alpha: AlphaAes = AlphaAes(this)
}

/**
 * Adds a new area layer.
 *
 * Creates a context in which you can create bindings using aesthetic attribute properties invocation:
 * ```
 * area {
 *    x(source<Double>("time")) // mapping from data source to 'x' coordinate
 *    borderLine.color(Color.BLUE) // setting of constant color value
 * }
 * ```
 *
 *  ### Aesthetic attributes:
 *
 *  Positional:
 *
 *  - [ x][AreaContext.x]
 *  - [y][AreaContext.y]
 *
 *  Initial mappings to positional attributes are inherited from the parent [PlotContext] (if they exist).
 *
 *   Non-positional:
 *  - [color][AreaContext.color] - color of the area filling, of the type [Color], mappable
 *  - [alpha][AreaContext.alpha] - layer alpha, of the type [Double], mappable
 *  - [borderLine.color][BorderLineSubContext.color] - color of the borderline, of the type [Color], mappable
 *  - [borderLine.width][BorderLineSubContext.width] - width of the borderline, of the type [Double], mappable
 *  - [borderLine.type][BorderLineSubContext.type] - type of the borderline, of the type [LineType], mappable
 *
 *  // TODO write about borderLine invocation?
 *  ```
 *  area {
 *     borderLine {
 *        color(Color.RED)
 *        type(LineType.DOTTED)
 *     }
 *  }
 *  ```
 *
 * // TODO move data overriding to args
 *  By default, the dataset inherited from the parent [PlotContext] is used,
 *  but can be overridden with an assignment to the [data][AreaContext.data].
 *
 *  // TODO refer to bindings?
 */

public inline fun LayerCollectorContext.area(block: AreaContext.() -> Unit) {
    addLayer(AreaContext(this).apply(block), AREA)
}
