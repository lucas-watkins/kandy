/*
* Copyright 2020-2023 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/

package org.jetbrains.kotlinx.kandy.dsl.impl

/**
 * Symbol described by one string.
 *
 * @param description the string describing this symbol.
 */
data class Symbol(val description: String) {
    companion object {
        val CIRCLE = Symbol("circle")
        val TRIANGLE = Symbol("triangle")
        val RECT = Symbol("rect")
    }
}
