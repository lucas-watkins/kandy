/*
* Copyright 2020-2023 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/

package org.jetbrains.kotlinx.kandy.dsl.internal.dataframe

import org.jetbrains.kotlinx.dataframe.*
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.Infer
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.api.copy
import org.jetbrains.kotlinx.dataframe.columns.ColumnAccessor
import org.jetbrains.kotlinx.kandy.dsl.internal.DatasetBuilder
import org.jetbrains.kotlinx.kandy.ir.data.TableData


internal abstract class DatasetBuilderImpl(
    initialBuilder:DatasetBuilderImpl? = null
): DatasetBuilder {
    protected abstract val baseDataFrame: DataFrame<*>
    private val referredColumns: MutableMap<String, String> = mutableMapOf()

    @PublishedApi
    internal var buffer: DataFrame<*> = initialBuilder?.buffer?.copy() ?: DataFrame.Empty

    override fun rowsCount(): Int = buffer.rowsCount()

    override fun takeColumn(name: String): String {
        return referredColumns[name] ?: run {
            val columnId = addColumn(
                baseDataFrame.getColumnOrNull(name) ?: error("invalid column id: $name")
            )
            referredColumns[name] = columnId
            name
        }
    }

    fun takeColumn(column: ColumnAccessor<*>): String {
        return takeColumn(column.name())
    }

    fun addColumn(column: DataColumn<*>): String {
        return if (buffer.containsColumn(column.name())) {
            if (buffer[column.name()] == column) {
                column.name()
            } else {
                addColumn(column.rename(column.name() + "*"))
            }
        } else {
            buffer = buffer.add(column)
            column.name()
        }
    }

    override fun addColumn(values: List<*>, name: String): String {
        val columnId = addColumn(DataColumn.createValueColumn(name, values, Infer.Type))
        referredColumns[name] = columnId
        return addColumn(DataColumn.createValueColumn(name, values, Infer.Type))
    }

    internal companion object {
        fun fromData(dataFrame: DataFrame<*>, initialBuilder: DatasetBuilderImpl? = null): NamedDataBuilder {
            return NamedDataBuilder(dataFrame, initialBuilder)
        }
        fun fromData(groupBy: GroupBy<*, *>, initialBuilder: DatasetBuilderImpl? = null): GroupedDataBuilder {
            return GroupedDataBuilder(groupBy, initialBuilder)
        }
        fun fromData(dataset: TableData, initialBuilder: DatasetBuilderImpl? = null): DatasetBuilderImpl {
            return when (dataset) {
                is NamedData -> NamedDataBuilder(dataset, initialBuilder)
                is GroupedData ->GroupedDataBuilder(dataset, initialBuilder)
                else -> error("Unexpected dataset type: ${dataset::class}")
            }
        }
    }
}
