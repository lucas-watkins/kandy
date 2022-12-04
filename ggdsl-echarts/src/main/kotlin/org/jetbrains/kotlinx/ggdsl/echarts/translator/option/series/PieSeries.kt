package org.jetbrains.kotlinx.ggdsl.echarts.translator.option.series

import kotlinx.serialization.Serializable
import org.jetbrains.kotlinx.ggdsl.echarts.features.animation.AnimationLineFeature
import org.jetbrains.kotlinx.ggdsl.echarts.translator.option.series.settings.*
import org.jetbrains.kotlinx.ggdsl.echarts.translator.option.series.settings.marks.*
import org.jetbrains.kotlinx.ggdsl.ir.Layer

internal fun Layer.toPieSeries(name: String?, encode: Encode?): PieSeries {
    val animation = (features[AnimationLineFeature.FEATURE_NAME] as? AnimationLineFeature)

    return PieSeries(
        name = name,
        encode = encode,
        markPoint = features.getEchartsMarkPoint(),
        markLine = features.getEchartsMarkLine(),
        animation = animation?.enable,
        animationThreshold = animation?.threshold,
        animationDuration = animation?.duration,
        animationEasing = animation?.easing?.name,
        animationDelay = animation?.delay,
    )
}

@Serializable
public class PieSeries(
    public override val type: String = "pie",
    public override val id: String? = null,
    public override val name: String? = null,
    public override val colorBy: String? = null,
    public override val legendHoverLink: Boolean? = null,
    public override val coordinateSystem: CoordinateSystem? = null,
    public val geoIndex: Int? = null,
    public val calendarIndex: Int? = null,
    public override val selectedMode: String? = null,
    public val selectedOffset: Int? = null,
    public val clockwise: Boolean? = null,
    public val startAngle: Int? = null,
    public val minAngle: Int? = null,
    public val minShowLabelAngle: Int? = null,
    public val roseType: String? = null,
    public val avoidLabelOverlap: Boolean? = null,
    public val stillShowZeroSum: Boolean? = null,
    public val percentPrecision: Int? = null,
    public val cursor: String? = null,
    public override val zlevel: Int? = null,
    public override val z: Int? = null,
    public val left: String? = null,
    public val top: String? = null,
    public val right: String? = null,
    public val bottom: String? = null,
    public val width: String? = null,
    public val height: String? = null,
    public val showEmptyCircle: Boolean? = null,
    public val emtyCircleStyle: EmptyCircleStyle? = null,
    public val label: Label? = null,
    public val labelLine: LabelLine? = null,
    public val labelLayout: LabelLayout? = null,
    public override val itemStyle: ItemStyle? = null,
    public override val emphasis: Emphasis? = null,
    public override val blur: Blur? = null,
    public override val select: Select? = null,
    public val center: Pair<String, String>? = null,
    public val radius: Pair<String, String>? = null,
    public val seriesLayoutBy: String? = null,
    public val datasetIndex: Int? = null,
    public override val dimensions: List<Dimension>? = null,
    public override val encode: Encode? = null,
    public override val dataGroupId: String? = null,
    public override val data: List<List<String>>? = null,
    public override val markPoint: EchartsMarkPoint? = null,
    public override val markLine: EchartsMarkLine? = null,
    public override val markArea: MarkArea? = null,
    public override val silent: Boolean? = null,
    public val animationType: String? = null,
    public val animationTypeUpdate: String? = null,
    public val animation: Boolean? = null,
    public val animationThreshold: Int? = null,
    public override val animationDuration: Int? = null,
    public override val animationEasing: String? = null,
    public override val animationDelay: Int? = null,
    public val animationDurationUpdate: Int? = null,
    public val animationEasingUpdate: String? = null,
    public val animationDelayUpdate: Int? = null,
    public override val universalTransition: UniversalTransition? = null,
    public override val tooltip: Tooltip? = null
) : Series()