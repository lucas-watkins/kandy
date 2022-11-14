/*
* Copyright 2020-2022 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/

package org.jetbrains.kotlinx.ggdsl.dsl

import org.jetbrains.kotlinx.ggdsl.dsl.internal.*
import org.jetbrains.kotlinx.ggdsl.ir.Layer
import org.jetbrains.kotlinx.ggdsl.ir.Plot
import org.jetbrains.kotlinx.ggdsl.ir.aes.AesName
import org.jetbrains.kotlinx.ggdsl.ir.bindings.*
import org.jetbrains.kotlinx.ggdsl.ir.feature.FeatureName
import org.jetbrains.kotlinx.ggdsl.ir.feature.LayerFeature
import org.jetbrains.kotlinx.ggdsl.ir.feature.PlotFeature
import org.jetbrains.kotlinx.ggdsl.ir.scale.NonPositionalCategoricalScale
import org.jetbrains.kotlinx.ggdsl.ir.scale.PositionalContinuousUnspecifiedScale
import kotlin.reflect.typeOf
import kotlin.test.Test
import kotlin.test.assertEquals

internal class CustomizationTest {

    class SpecificAes(override val context: BindingContext) : MappableNonPositionalAes<CustomGeomType> {
        override val name = SPECIFIC_AES
    }

    companion object {
        val CUSTOM_GEOM = CommonGeom("custom")
        val SPECIFIC_AES = AesName("customAes")
    }

    data class CustomGeomType(val name: String)

    class CustomGeomContextImmutable(parent: LayerCollectorContextImmutable) :
        LayerContextImmutable(parent) {
        val x = XAes(this)
        val y = YAes(this)
        val specificAes = SpecificAes(this)
    }

    fun LayerCollectorContextImmutable.customLayer(block: CustomGeomContextImmutable.() -> Unit) {
        addLayer(CustomGeomContextImmutable(this).apply(block), CUSTOM_GEOM)
    }

    data class MockLayerFeature(val value: Int) : LayerFeature {
        override val featureName = FEATURE_NAME

        companion object {
            val FEATURE_NAME = FeatureName("mock_layer_feature")
        }
    }

    private fun LayerContextInterface.mockFeatureFunction(value: Int) {
        features[MockLayerFeature.FEATURE_NAME] = MockLayerFeature(value)
    }

    data class MockPlotFeature(val title: String) : PlotFeature {
        override val featureName = FEATURE_NAME

        companion object {
            val FEATURE_NAME = FeatureName("mock_plot_feature")
        }
    }

    private var LayerPlotContext.mockFeatureProp: String
        get() = ""
        set(value) {
            features[MockPlotFeature.FEATURE_NAME] = MockPlotFeature(value)
        }


    val mockSrcDouble = columnPointer<Double>("mock_double")
    val mockSrcInt = columnPointer<Int>("mock_int")
    val mockSrcString = columnPointer<String>("mock_string")
    val mockSrcFloat = columnPointer<Float>("mock_float")

    val dataset = NamedData(mapOf())

    @Test
    fun testCustomLayer() {
        val plot = plot(dataset) {
            y(mockSrcDouble)
            customLayer {
                x(mockSrcFloat.scaled(continuousPos()))
                specificAes(
                    mockSrcString.scaled(
                        categorical(
                            listOf("A", "B"),
                            listOf(CustomGeomType("x"), CustomGeomType("xxx"))
                        )
                    )
                )
            }
            customLayer {
                x(mockSrcFloat)
                specificAes(CustomGeomType("yy"))
            }
        }
        assertEquals(
            Plot(
                dataset,
                listOf(
                    Layer(
                        dataset,
                        CUSTOM_GEOM,
                        mapOf(
                            X to ScaledPositionalUnspecifiedMapping(
                                X,
                                ColumnScaledPositionalUnspecified(mockSrcFloat, PositionalContinuousUnspecifiedScale()),
                                typeOf<Float>()
                            ),
                            Y to ScaledUnspecifiedDefaultPositionalMapping(
                                Y,
                                ColumnScaledUnspecifiedDefault(mockSrcDouble),
                                typeOf<Double>()
                            ),
                            SPECIFIC_AES to ScaledNonPositionalMapping(
                                SPECIFIC_AES,
                                ColumnScaledNonPositional(
                                    mockSrcString,
                                    NonPositionalCategoricalScale(
                                        listOf("A", "B"),
                                        listOf(CustomGeomType("x"), CustomGeomType("xxx"))
                                    )
                                ),
                                typeOf<String>(),
                                //typeOf<CustomGeomType>()
                            )
                        ),
                        mapOf(),
                        mapOf()
                    ),
                    Layer(
                        dataset,
                        CUSTOM_GEOM,
                        mapOf(
                            X to ScaledUnspecifiedDefaultPositionalMapping(
                                X,
                                ColumnScaledUnspecifiedDefault(mockSrcFloat),
                                typeOf<Float>()
                            ),
                            Y to ScaledUnspecifiedDefaultPositionalMapping(
                                Y,
                                ColumnScaledUnspecifiedDefault(mockSrcDouble),
                                typeOf<Double>()
                            )
                        ),
                        mapOf(
                            SPECIFIC_AES to NonPositionalSetting(
                                SPECIFIC_AES,
                                CustomGeomType("yy")
                            )
                        ),
                        mapOf()
                    )
                ),
                mapOf(Y to ScaledUnspecifiedDefaultPositionalMapping(
                    Y,
                    ColumnScaledUnspecifiedDefault(mockSrcDouble),
                    typeOf<Double>()
                ),),
                emptyMap(),
                emptyMap()
            ),
            plot
        )
    }

    @Test
    fun testFeatures() {
        val plot = plot(dataset) {
            mockFeatureProp = "amentes ineptias"
            bars {
                mockFeatureFunction(14)
            }
        }

        assertEquals(
            Plot(
                dataset,
                listOf(
                    Layer(
                        dataset,
                        BAR,
                        mapOf(),
                        mapOf(),
                        mapOf(MockLayerFeature.FEATURE_NAME to MockLayerFeature(14))
                    )
                ),
                mapOf(),
                mapOf(MockPlotFeature.FEATURE_NAME to MockPlotFeature("amentes ineptias")),
                emptyMap()
            ),
            plot
        )
    }
}


