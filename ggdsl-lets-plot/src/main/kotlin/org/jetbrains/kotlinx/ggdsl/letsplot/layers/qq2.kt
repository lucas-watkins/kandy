/*
* Copyright 2020-2022 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/

package org.jetbrains.kotlinx.ggdsl.letsplot.layers


import org.jetbrains.kotlinx.ggdsl.letsplot.LetsPlotGeom

/* TODO
@PublishedApi

 */
public val QQ2: LetsPlotGeom = LetsPlotGeom("qq2")

/*
@PlotDslMarker
// todo move x/y?
<<<<<<< HEAD
class QQ2Context(
    parent: LayerCollectorContext,
) : LayerContext(parent) {
=======
public class QQ2Context(
    override var data: MutableNamedData,
) : LayerContext() {
>>>>>>> main
    @PublishedApi
    internal val _x: XAes = XAes(this)

    @PublishedApi
    internal val _y: YAes = YAes(this)

    public val x: XDummyAes = XDummyAes(this)
    public val y: YDummyAes = YDummyAes(this)

    public val alpha: AlphaAes = AlphaAes(this)
    public val fillColor: FillAes = FillAes(this)
    public val color: ColorAes = ColorAes(this)
    public val size: SizeAes = SizeAes(this)
    public val symbol: ShapeAes = ShapeAes(this)
    // todo stroke

    public object Statistics {
        public val X: QQ2Stat.X = QQ2Stat.X
        public val Y: QQ2Stat.Y = QQ2Stat.Y
    }

    public val Stat: Statistics = Statistics


    public inline operator fun <reified DomainType : Any> ScalablePositionalAes.invoke(
        stat: QQ2Stat<DomainType>
    ): ScaledUnspecifiedDefaultPositionalMapping<DomainType> {
        val mapping = ScaledUnspecifiedDefaultPositionalMapping(
            this.name,
            stat.toDataSource().scaled(),
            typeOf<DomainType>()
        )
        context.bindingCollector.mappings[this.name] = mapping
        return mapping
    }

    public inline operator fun <reified DomainType : Any, RangeType : Any> MappableNonPositionalAes<RangeType>.invoke(
        stat: QQ2Stat<DomainType>
    ): ScaledUnspecifiedDefaultNonPositionalMapping<DomainType, RangeType> {
        val mapping = ScaledUnspecifiedDefaultNonPositionalMapping<DomainType, RangeType>(
            this.name,
            stat.toDataSource().scaled(),
            typeOf<DomainType>()
        )
        context.bindingCollector.mappings[this.name] = mapping
        return mapping
    }

}

<<<<<<< HEAD
inline fun <reified T : Any, reified R: Any> PlotContext.qq2(
    sourceX: ColumnPointer<T>,
    sourceY: ColumnPointer<R>,
=======
public inline fun <reified T : Any, reified R : Any> PlotContext.qq2(
    sourceX: DataSource<T>,
    sourceY: DataSource<R>,
>>>>>>> main
    block: QQ2Context.() -> Unit
) {
    layers.add(
        QQ2Context(data)
            .apply {
                copyFrom(this@qq2)
                _x(sourceX)
                _y(sourceY)
            }
            .apply(block)
            .toLayer(QQ2)
    )
}

public inline fun <reified T : Any, reified R : Any> PlotContext.qq2(
    sourceX: Iterable<T>,
    sourceY: Iterable<R>,
    block: QQ2Context.() -> Unit
) {
    layers.add(
        QQ2Context(data)
            .apply {
                copyFrom(this@qq2)
                _x(sourceX)
                _y(sourceY)
            }
            .apply(block)
            .toLayer(QQ2)
    )
}



 */
