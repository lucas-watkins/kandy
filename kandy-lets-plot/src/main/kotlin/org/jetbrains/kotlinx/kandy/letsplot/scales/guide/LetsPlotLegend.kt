/*
* Copyright 2020-2023 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/

package org.jetbrains.kotlinx.kandy.letsplot.scales.guide

// import org.jetbrains.kotlinx.kandy.dsl.internal.PlotDslMarker
import org.jetbrains.kotlinx.kandy.util.color.Color
import org.jetbrains.kotlinx.kandy.util.context.SelfInvocationContext

public sealed interface LegendType

public class None internal constructor() : LegendType

public data class DiscreteLegend internal constructor(
    val nRow: Int? = null,
    val nCol: Int? = null,
    val byRow: Boolean? = null
) : LegendType

public data class ColorBar internal constructor(
    val barWidth: Double? = null,
    val barHeight: Double? = null,
    val nBin: Int? = null
) : LegendType

// todo separate model and context
/**
 * Legend, i.e. guide for non-positional scale.
 *
 * @property name legend name.
 * @property type legend type.
 */
public data class Legend<DomainType, out RangeType> @PublishedApi internal constructor(
    var name: String? = null,
    // todo expand & trans
    var type: LegendType? = null,
    internal var breaks: List<DomainType>? = null,
    internal var labels: List<String>? = null,
    internal var format: String? = null,
) : SelfInvocationContext, ScaleParameters {


    /**
     * Sets legend breaks with formatting.
     *
     * @param breaks list of breaks.
     * @param format format string.
     */
    public fun breaks(breaks: List<DomainType>? = null, format: String? = null) {
        this.breaks = breaks
        this.format = format
    }

    /**
     * Sets legend breaks with labels.
     *
     * @param breaksToLabels list of breaks with corresponding labels.
     */
    public fun breaksLabeled(vararg breaksToLabels: Pair<DomainType, String>) {
        breaks = breaksToLabels.map { it.first }
        labels = breaksToLabels.map { it.second }
    }

    /**
     * Sets legend breaks with labels.
     *
     * @param breaks list of breaks.
     * @param labels list of corresponding labels.
     */
    public fun breaksLabeled(breaks: List<DomainType>, labels: List<String>) {
        // todo check equal sizes
        this.breaks = breaks
        this.labels = labels
    }
}

/**
 * Do not display the legend.
 */
public fun Legend<*, *>.none(): None = None()

/**
 * Legend guide.
 * Legend type guide shows key (i.e., geoms) mapped onto values.
 *
 * @param nRow number of rows in legend's guide.
 * @param nCol number of columns in legend's guide.
 * @param byRow type of output: by row (default), or by column.
 */
public fun Legend<*, *>.discreteLegend(
    nRow: Int? = null,
    nCol: Int? = null,
    byRow: Boolean? = null
): DiscreteLegend = DiscreteLegend(nRow, nCol, byRow)

/**
 * Continuous color bar guide.
 * Color bar guide shows continuous color scales mapped onto values.
 * @param barWidth color bar width.
 * @param barHeight color bar height.
 * @param nBin number of bins in color bar.
 */
public fun Legend<*, Color>.colorBar(
    barWidth: Double? = null,
    barHeight: Double? = null,
    nBin: Int? = null
): ColorBar = ColorBar(barWidth, barHeight, nBin)
