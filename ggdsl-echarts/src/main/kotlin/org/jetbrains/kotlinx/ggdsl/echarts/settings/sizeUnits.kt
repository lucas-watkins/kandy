package org.jetbrains.kotlinx.ggdsl.echarts.settings

import kotlinx.serialization.Serializable
import org.jetbrains.kotlinx.ggdsl.echarts.translator.serializers.PercentageSerializer
import org.jetbrains.kotlinx.ggdsl.echarts.translator.serializers.SizeUnitSerializer


public val Int.px: Pixel get() = Pixel(this)

public val Int.pct: Percentage get() = Percentage(this)

@Serializable(with = SizeUnitSerializer::class)
public sealed interface SizeUnit

@Serializable
@JvmInline
public value class Pixel(public val pixels: Int) : SizeUnit

@Serializable(with = PercentageSerializer::class)
@JvmInline
public value class Percentage(public val percentage: Int) : SizeUnit

@Serializable
@JvmInline
internal value class IntUnit(val value: Int) : SizeUnit

@Serializable
@JvmInline
internal value class DoubleUnit(val value: Double) : SizeUnit
