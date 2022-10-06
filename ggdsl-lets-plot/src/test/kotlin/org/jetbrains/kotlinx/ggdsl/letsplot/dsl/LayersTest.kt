/*
* Copyright 2020-2022 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/

package org.jetbrains.kotlinx.ggdsl.letsplot.dsl

import org.jetbrains.kotlinx.ggdsl.dsl.*
import org.jetbrains.kotlinx.ggdsl.ir.Layer
import org.jetbrains.kotlinx.ggdsl.ir.Plot
import org.jetbrains.kotlinx.ggdsl.ir.bindings.*
import org.jetbrains.kotlinx.ggdsl.ir.scale.NonPositionalCategoricalScale
import org.jetbrains.kotlinx.ggdsl.ir.scale.PositionalContinuousUnspecifiedScale
import org.jetbrains.kotlinx.ggdsl.letsplot.ALPHA
import org.jetbrains.kotlinx.ggdsl.letsplot.FILL
import org.jetbrains.kotlinx.ggdsl.letsplot.SIZE
import org.jetbrains.kotlinx.ggdsl.letsplot.Y
import org.jetbrains.kotlinx.ggdsl.letsplot.layers.AREA
import org.jetbrains.kotlinx.ggdsl.letsplot.layers.area
import org.jetbrains.kotlinx.ggdsl.util.color.Color
import kotlin.reflect.typeOf
import kotlin.test.Test
import kotlin.test.assertEquals

internal class LayersTest {
    val emptyDataset = NamedData(mapOf())
    @Test
    fun testArea() {
        val time = columnPointer<Int>("time")
        val type = columnPointer<String>("type")
        val plot = plot(emptyDataset) {
            area {
                y(time.scaled(continuousPos()))
                color(
                    type.scaled(
                        categorical(
                            rangeValues = listOf(Color.RED, Color.BLUE)
                        )
                    )
                )
                // TODO
                alpha(0.7)
                borderLine.width(2.0)

            }
        }
        // TODO
        assertEquals(
            Plot(
                emptyDataset,
                listOf(
                    Layer(
                        emptyDataset,
                        AREA,
                        mapOf(
                            Y to ScaledPositionalUnspecifiedMapping(
                                Y, ColumnScaledPositionalUnspecified(
                                time,
                                PositionalContinuousUnspecifiedScale()
                            ), typeOf<Int>()
                            ),
                            FILL to ScaledNonPositionalMapping(
                                FILL,
                                ColumnScaledNonPositional(
                                    type,
                                    NonPositionalCategoricalScale(
                                        rangeValues = listOf(Color.RED, Color.BLUE)
                                    )
                                ),
                                typeOf<String>(),
                                //  typeOf<Color>()
                            )
                        ),
                        mapOf(
                            ALPHA to NonPositionalSetting(
                                ALPHA,
                                0.7
                            ),
                            SIZE to NonPositionalSetting(
                                SIZE,
                                2.0
                            )
                        ),
                        mapOf()
                    )
                ),
                mapOf()
            ),
            plot
        )
    }
    // todo others???
}


