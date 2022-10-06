/*
* Copyright 2020-2022 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/

package org.jetbrains.kotlinx.ggdsl.letsplot.layers

import org.jetbrains.kotlinx.ggdsl.letsplot.*


public val DENSITY_2D_FILLED: LetsPlotGeom = LetsPlotGeom("density_2D_filled")
/*
=======
public val DENSITY_2D_FILLED: LetsPlotGeom = LetsPlotGeom("density_2D_filled")

>>>>>>> main

// todo stats
@PlotDslMarker
// todo move x/y?
<<<<<<< HEAD
class Density2DFilledContext(
    parent: LayerCollectorContext,
=======
public class Density2DFilledContext(
    override var data: MutableNamedData,
>>>>>>> main
    kernel: Kernel?,
    bandWidth: BandWidth?,
    pointsSampled: Int?,
    trim: Boolean?,
    adjust: Double?,
    fullScanMax: Int?,
) : LayerContext(parent) {
    // todo internal
    @PublishedApi
    internal val _x: XAes = XAes(this)

    @PublishedApi
    internal val _y: YAes = YAes(this)

    public val x: XDummyAes = XDummyAes(this)
    public val y: YDummyAes = YDummyAes(this)

    public object Statistics {
        public val X: ContourStat.X = ContourStat.X
        public val Y: ContourStat.Y = ContourStat.Y
        public val LEVEL: ContourStat.Level = ContourStat.Level
        public val GROUP: ContourStat.Group = ContourStat.Group
    }

    public val Stat: Statistics = Statistics


    public val alpha: AlphaAes = AlphaAes(this)
    public val fillColor: FillAes = FillAes(this)
    public val borderLineWidth: SizeAes = SizeAes(this)
    public val borderLineColor: ColorAes = ColorAes(this)
    public val borderLineType: LineTypeAes = LineTypeAes(this)

    // todo weight
// TODO params

    @PublishedApi
    internal val kernel: KernelAes = KernelAes(this)

    @PublishedApi
    internal val bw: BWAes = BWAes(this)

    @PublishedApi
    internal val pointsSampled: NumberAes = NumberAes(this)

    @PublishedApi
    internal val trim: TrimAes = TrimAes(this)

    @PublishedApi
    internal val adjust: AdjustAes = AdjustAes(this)

    @PublishedApi
    internal val fullScanMax: FullScanMaxAes = FullScanMaxAes(this)

    init {
        kernel?.let {
            kernel(it)
        }
        bandWidth?.let {
            bw(it)
        }
        pointsSampled?.let {
            pointsSampled(it)
        }
        trim?.let {
            trim(it)
        }
        adjust?.let {
            adjust(it)
        }
        fullScanMax?.let {
            fullScanMax(it)
        }
    }


    public inline operator fun <reified DomainType : Any> ScalablePositionalAes.invoke(
        stat: ContourStat<DomainType>
    ): ScaledUnspecifiedDefaultPositionalMapping<DomainType> {
        val mapping = ScaledUnspecifiedDefaultPositionalMapping(
            this.name,
            stat.toDataSource().scaled(),
            typeOf<DomainType>()
        )
        context.bindingCollector.mappings[this.name] = mapping
        return mapping
    }


    public inline operator fun <reified DomainType : Any, RangeType : Any>
        MappableNonPositionalAes<RangeType>.invoke(
        stat: ContourStat<DomainType>
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
inline fun <reified T : Any, reified R: Any> PlotContext.density2DFilled(
    sourceX: ColumnPointer<T>,
    sourceY: ColumnPointer<R>,
=======
public inline fun <reified T : Any, reified R : Any> PlotContext.density2DFilled(
    sourceX: DataSource<T>,
    sourceY: DataSource<R>,
>>>>>>> main
    kernel: Kernel? = null,
    bandWidth: BandWidth? = null,
    pointsSampled: Int? = null,
    trim: Boolean? = null,
    adjust: Double? = null,
    fullScanMax: Int? = null,
    block: Density2DFilledContext.() -> Unit
) {
    layers.add(
        Density2DFilledContext(data, kernel, bandWidth, pointsSampled, trim, adjust, fullScanMax)
            .apply {
                copyFrom(this@density2DFilled)
                _x(sourceX)
                _y(sourceY)
            }
            .apply(block)
            .toLayer(DENSITY_2D_FILLED)
    )
}

public inline fun <reified T : Any, reified R : Any> PlotContext.density2DFilled(
    sourceX: Iterable<T>,
    sourceY: Iterable<R>,
    kernel: Kernel? = null,
    bandWidth: BandWidth? = null,
    pointsSampled: Int? = null,
    trim: Boolean? = null,
    adjust: Double? = null,
    fullScanMax: Int? = null,
    block: Density2DFilledContext.() -> Unit
) {
    layers.add(
        Density2DFilledContext(data, kernel, bandWidth, pointsSampled, trim, adjust, fullScanMax)
            .apply {
                copyFrom(this@density2DFilled)
                _x(sourceX)
                _y(sourceY)
            }
            .apply(block)
            .toLayer(DENSITY_2D_FILLED)
    )
}

 */