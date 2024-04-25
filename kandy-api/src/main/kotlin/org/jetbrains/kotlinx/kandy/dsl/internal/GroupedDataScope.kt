package org.jetbrains.kotlinx.kandy.dsl.internal

import org.jetbrains.kotlinx.dataframe.ColumnsContainer
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.GroupBy
import org.jetbrains.kotlinx.dataframe.api.concat
import org.jetbrains.kotlinx.dataframe.api.getColumns
import org.jetbrains.kotlinx.dataframe.api.toColumnGroup
import org.jetbrains.kotlinx.dataframe.columns.ColumnGroup

/**
 * Represents a context specifically tailored for managing and visualizing grouped datasets.
 *
 * @property key `ColumnGroup` of key columns
 */
public class GroupedDataScope<T>(
    internal val groupBy: GroupBy<T, *>,
    initialBuffer: DataFrame<*>,
    override val plotBuilder: MultiLayerPlotBuilder
): LayerCreatorScope() {

    @Suppress("UNCHECKED_CAST")
    public val key: ColumnGroup<T> =
        groupBy.concatFixed().getColumns(*groupBy.keys.columnNames().toTypedArray()).toColumnGroup(
            "key"
        ) as ColumnGroup<T>

    override val datasetIndex: Int = plotBuilder.addDataset(groupBy, initialBuffer)
    override val layersInheritMappings: Boolean = true
}