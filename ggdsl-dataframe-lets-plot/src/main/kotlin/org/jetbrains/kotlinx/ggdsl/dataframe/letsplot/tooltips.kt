package org.jetbrains.kotlinx.ggdsl.dataframe.letsplot

import org.jetbrains.kotlinx.dataframe.columns.ColumnReference
import org.jetbrains.kotlinx.ggdsl.dsl.LayerContext
import org.jetbrains.kotlinx.ggdsl.ir.aes.Aes
import org.jetbrains.kotlinx.ggdsl.ir.data.DataSource
import org.jetbrains.kotlinx.ggdsl.letsplot.layers.stat.Stat
import org.jetbrains.kotlinx.ggdsl.letsplot.tooltips.Anchor
import org.jetbrains.kotlinx.ggdsl.letsplot.tooltips.LayerTooltips
import org.jetbrains.kotlinx.ggdsl.letsplot.tooltips.LayerTooltipsContext

/**
 * Inserts value of given column into format string.
 *
 * @param column column whose value will be inserted into the tooltip
 * @return format string
 */
public fun value(column: ColumnReference<*>): String {
    return "@${column.name()}"
}

/**
 * Adds standard line for given column
 * (name of this column on the left side and corresponding value on the right side).
 *
 * @param column
 */
public fun LayerTooltipsContext.line(column: ColumnReference<*>) {
    line("@|@${column.name()}")
}



public inline fun LayerContext.tooltips(
    columns: List<ColumnReference<*>> = listOf(),
    variablesDS: List<DataSource<*>> = listOf(),
    title: String? = null,
    anchor: Anchor? = null,
    minWidth: Double? = null,
    hide: Boolean = false,
    dsFormats: Map<DataSource<*>, String> = mapOf(),
    columnsFormats: Map<ColumnReference<*>, String> = mapOf(),
    aesFormats: Map<Aes, String> = mapOf(),
    statFormats: Map<Stat<*>, String> = mapOf(),
    tooltipsContextAction: LayerTooltipsContext.() -> Unit
) {
    features[LayerTooltips.FEATURE_NAME] = LayerTooltips.fromContext(
        columns.map { it.name() } + variablesDS.map { it.id },
        title,
        anchor,
        minWidth,
        hide,
        dsFormats.map { it.key.id to it.value }
                + columnsFormats.map { it.key.name() to it.value }
                + aesFormats.map { "^" + it.key.name.name to it.value }
                + statFormats.map { it.key.name to it.value },
        LayerTooltipsContext().apply(tooltipsContextAction)
    )
}
