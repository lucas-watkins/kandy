/*
* Copyright 2020-2022 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/

package org.jetbrains.kotlinx.ggdsl.letsplot.translator

import org.jetbrains.kotlinx.ggdsl.dsl.NamedData
import org.jetbrains.kotlinx.ggdsl.dsl.column.ColumnReference
import org.jetbrains.kotlinx.ggdsl.dsl.internal.toTyped
import org.jetbrains.kotlinx.ggdsl.dsl.internal.typed
import org.jetbrains.kotlinx.ggdsl.dsl.internal.typedList
import org.jetbrains.kotlinx.ggdsl.dsl.scaled
import org.jetbrains.kotlinx.ggdsl.ir.Layer
import org.jetbrains.kotlinx.ggdsl.ir.bindings.*
import org.jetbrains.kotlinx.ggdsl.ir.scale.NonPositionalCategoricalScale
import org.jetbrains.kotlinx.ggdsl.ir.scale.PositionalContinuousUnspecifiedScale
import org.jetbrains.kotlinx.ggdsl.letsplot.internal.*
import org.jetbrains.kotlinx.ggdsl.letsplot.layers.BAR
import org.jetbrains.kotlinx.ggdsl.letsplot.layers.POINT
import org.jetbrains.kotlinx.ggdsl.letsplot.position.Position
import org.jetbrains.kotlinx.ggdsl.util.color.Color
import org.jetbrains.letsPlot.intern.toSpec
import kotlin.reflect.typeOf
import kotlin.test.Test
import kotlin.test.assertEquals

internal class LayerWrapperTest {
    private val emptyDataset = NamedData(mapOf())
    @Test
    fun testSimple() {
        val layer = Layer(
            emptyDataset,
            POINT,
            mapOf(
                FILL to ScaledUnspecifiedDefaultNonPositionalMapping<Int, Color>(
                    FILL,
                    ColumnReference<Int>("F").scaled(),
                    typeOf<Int>()
                )
            ),
            mapOf(
                COLOR to NonPositionalSetting<Color>(
                    COLOR,
                    Color.RED
                )
            ),
            mapOf()
        )

        val wrappedLayer = LayerWrapper(layer, false)
        assertEquals(
            mapOf(
                "mapping" to mapOf(
                    "fill" to "F"
                ),
                "sampling" to "none",
                "stat" to "identity",
                "data" to mapOf<String, Any>(),
                //"shape" to 21.0,
                "color" to "#ee6666",
                "position" to "identity",
                "geom" to "point"
            ),
            wrappedLayer.toSpec()
        )
    }

    @Test
    fun testComplex() {
        /*
        val dataset: NamedData = mapOf(
            "TIME_T" to listOf(1.0F, 2.0F, 3.0F),
            "VAL_V" to listOf(19921.44, 42423.324, 34242.1987)
        )

         */
        val layer = Layer(
            emptyDataset,
            BAR,
            mapOf(
                X to ScaledUnspecifiedDefaultPositionalMapping(
                    X,
                    ColumnScaledUnspecifiedDefault(
                        ColumnReference<Float>("TIME_T")
                    ),
                    typeOf<Float>()
                ),
                Y to ScaledPositionalUnspecifiedMapping(
                    Y,
                    ColumnScaledPositionalUnspecified(
                        ColumnReference<Double>("VAL_V"),
                        PositionalContinuousUnspecifiedScale()
                    ),
                    typeOf<Double>()
                ),
                FILL to ScaledNonPositionalMapping(
                    FILL,
                    ColumnScaledNonPositional(
                        ColumnReference<String>("BAFGA"),
                        NonPositionalCategoricalScale<String, Color>(
                            null,
                            rangeValues = listOf(Color.BLACK, Color.WHITE, Color.GREY),
                            //null,
                        ),
                    ),
                    typeOf<String>(),
                    //typeOf<Color>()
                )
            ),
            mapOf(
                COLOR to NonPositionalSetting<Color>(
                    COLOR,
                    Color.RED
                ),
                WIDTH to NonPositionalSetting<Color>(
                    WIDTH,
                    5.0
                ),
            ),
            mapOf(
                Position.FEATURE_NAME to Position.Dodge(0.9)
            )
        )

        val wrappedLayer = LayerWrapper(layer, false)

        assertEquals(
            mapOf(
                "mapping" to mapOf(
                    "x" to "TIME_T",
                    "y" to "VAL_V",
                    "fill" to "BAFGA",
                ),
                "sampling" to "none",
                "stat" to "identity",
                "data" to mapOf<String, Any>(),
                "width" to 5.0,
                "color" to "#ee6666",
                "position" to mapOf(
                    "name" to "dodge",
                    "width" to 0.9,
                    "kind" to "pos"
                ),
                "geom" to "bar",
                "data_meta" to mapOf<String, Any>(
                    "mapping_annotations" to
                        listOf(
                            mapOf(
                                "aes" to "fill",
                                "annotation" to "as_discrete",
                                "parameters" to mapOf<String, Any?>(
                                    "label" to "BAFGA",
                                    "order_by" to null,
                                    "order" to null
                                )
                            )
                        )
                )
            ),
            wrappedLayer.toSpec()
        )

        /*
        LayerAssert.assertThat(wrappedLayer)
            .aes("x", "TIME_T")
            .aes("y", "VAL_V")
            .aes("fill", "BAFGA")
            .parameter("color", "red")
            .parameter("width", 5.0)
            .geom()
            .kind(GeomKind.BAR)
            .noMapping()
        LayerAssert.assertThat(wrappedLayer)
            .stat()
            .kind(StatKind.IDENTITY)

        LayerAssert.assertThat(wrappedLayer)
            .position(PosKind.DODGE)
            .parameter("width", 0.9)


         */
    }

    @Test
    fun testBarNoSampling() {
        val data = mapOf(
            "v1" to List(100) {it},
            "v2" to List(100) {it}
        )
        val layer = Layer(
            NamedData(data.toTyped()),
            BAR,
            mapOf(
                X to ScaledUnspecifiedDefaultPositionalMapping(
                    X, ColumnReference<Int>("v1").scaled(), typeOf<Int>()
                ),
                Y to ScaledUnspecifiedDefaultPositionalMapping(
                    Y, ColumnReference<Int>("v2").scaled(), typeOf<Int>()
                )
            ),
            mapOf(),
            mapOf()
        )

        val wrappedLayer = LayerWrapper(layer, false)
        assertEquals(
            mapOf(
                "mapping" to mapOf(
                    "x" to "v1",
                    "y" to "v2",
                ),
                "stat" to "identity",
                "data" to data.map {
                    it.key to it.value.map { it.toDouble() }
                }.toMap(),
                "position" to "identity",
                "geom" to "bar",
                "sampling" to "none",
            ),
            wrappedLayer.toSpec()
        )
    }
}


