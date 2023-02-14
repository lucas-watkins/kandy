/*
* Copyright 2020-2022 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/

package org.jetbrains.kotlinx.ggdsl.dsl.unit

import org.jetbrains.kotlinx.ggdsl.dsl.categorical
import org.jetbrains.kotlinx.ggdsl.dsl.categoricalPos
import org.jetbrains.kotlinx.ggdsl.dsl.continuous
import org.jetbrains.kotlinx.ggdsl.dsl.continuousPos
import org.jetbrains.kotlinx.ggdsl.dsl.internal.typedList
import org.jetbrains.kotlinx.ggdsl.dsl.internal.typedPair
import org.jetbrains.kotlinx.ggdsl.ir.scale.*
import org.jetbrains.kotlinx.ggdsl.util.color.Color
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ScaleTest {

    @Test
    fun testContinuousPosDefault() {
        assertEquals(PositionalContinuousUnspecifiedScale(), continuousPos())
    }

    @Test
    fun testContinuousPos() {
        val limits = -5 to 19
        val scale = continuousPos(limits)
        assertEquals(PositionalContinuousScale(limits.typedPair(), null, null), scale)
    }

    @Test
    fun testCategoricalPosDefault() {
        assertEquals(PositionalCategoricalUnspecifiedScale, categoricalPos())
    }

    @Test
    fun testCategoricalPos() {
        val categories = listOf("a", "b", "CCC", "123")
        val scale = categoricalPos(categories = categories)
        assertEquals(PositionalCategoricalScale(categories, /*null*/), scale)
    }

    @Test
    fun testContinuousDefault() {
        assertEquals(NonPositionalContinuousUnspecifiedScale(), continuous())
    }

    @Test
    fun testContinuous() {
        val domainLimits = 23.0 to 129.13
        val rangeLimits = 0.0F to 1.0F
        val scale = continuous(domainLimits, rangeLimits)
        assertEquals(NonPositionalContinuousScale(domainLimits.typedPair(), rangeLimits.typedPair(), null, null), scale)
    }

    @Test
    fun testCategoricalDefault() {
        assertEquals(NonPositionalCategoricalUnspecifiedScale, categorical())
    }

    @Test
    fun testCategorical() {
        val domainCategories = listOf(1, 3, 100, 999)
        val rangeCategories = listOf(Color.RED, Color.BLACK, Color.BLUE, Color.WHITE)
        val scale = categorical(domainCategories, rangeCategories)
        assertEquals(NonPositionalCategoricalScale(domainCategories, rangeCategories, /*null*/), scale)
    }
}
