package org.jetbrains.kotlinx.ggdsl.dsl

import org.jetbrains.kotlinx.ggdsl.ir.data.NamedData

/**
 * Mutual version of [NamedData].
 */
public typealias MutableNamedData = MutableMap<String, List<Any>>
