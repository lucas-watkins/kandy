package org.jetbrains.kotlinx.ggdsl.echarts.translator.option.series.settings

import kotlinx.serialization.Serializable
import org.jetbrains.kotlinx.ggdsl.echarts.aes.*
import org.jetbrains.kotlinx.ggdsl.echarts.settings.AreaPosition
import org.jetbrains.kotlinx.ggdsl.echarts.translator.getNPSValue
import org.jetbrains.kotlinx.ggdsl.echarts.translator.option.EchartsColor
import org.jetbrains.kotlinx.ggdsl.echarts.translator.option.toEchartsColor
import org.jetbrains.kotlinx.ggdsl.ir.aes.AesName
import org.jetbrains.kotlinx.ggdsl.ir.bindings.Setting
import org.jetbrains.kotlinx.ggdsl.util.color.Color

internal fun Map<AesName, Setting>.getAreaStyle(): AreaStyle {
    val color = this.getNPSValue<Color>(AREA_COLOR)?.toEchartsColor()
    val origin = this.getNPSValue<AreaPosition>(AREA_POSITION)?.let { it.position ?: it.number.toString() }
    val shadowBlur = this.getNPSValue<Int>(AREA_SHADOW_BLUR)
    val shadowColor = this.getNPSValue<Color>(AREA_SHADOW_COLOR)?.toEchartsColor()
    val opacity = this.getNPSValue<Double>(AREA_ALPHA)

    return AreaStyle(color, origin, shadowBlur, shadowColor, opacity = opacity)
}

@Serializable
public data class AreaStyle(
    val color: EchartsColor? = null,
    val origin: String? = null,
    val shadowBlur: Int? = null,
    val shadowColor: EchartsColor? = null,
    val shadowOffsetX: Int? = null,
    val shadowOffsetY: Int? = null,
    val opacity: Double? = null
)