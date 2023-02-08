package org.jetbrains.kotlinx.ggdsl.letsplot.stat

import org.jetbrains.kotlinx.ggdsl.ir.data.ColumnPointer

public interface Statistic<T> {
    public val id: String
}

@PublishedApi
internal inline fun <reified T> Statistic<T>.toColumnPointer(): ColumnPointer<T> {
    return ColumnPointer(this.id)
}
