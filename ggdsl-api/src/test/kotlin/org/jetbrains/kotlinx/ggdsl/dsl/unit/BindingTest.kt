/*
* Copyright 2020-2022 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/

package org.jetbrains.kotlinx.ggdsl.dsl.unit

import org.jetbrains.kotlinx.ggdsl.dsl.*
import org.jetbrains.kotlinx.ggdsl.dsl.column.columnPointer
import org.jetbrains.kotlinx.ggdsl.dsl.internal.BindingCollector
import org.jetbrains.kotlinx.ggdsl.dsl.internal.BindingContext
import org.jetbrains.kotlinx.ggdsl.dsl.internal.typed
import org.jetbrains.kotlinx.ggdsl.ir.aes.AesName
import org.jetbrains.kotlinx.ggdsl.ir.bindings.*
import org.jetbrains.kotlinx.ggdsl.util.color.Color
import kotlin.reflect.typeOf
import kotlin.test.Test
import kotlin.test.assertEquals

internal class BindingTest {
    companion object {
        val MOCK_AES_DOUBLE_NON_POS = AesName("mock_aes_double_np")
        val MOCK_AES_STRING_MAP_NON_POS = AesName("mock_aes_string_mnp")
        val MOCK_AES_COLOR_MAP_NON_POS = AesName("mock_aes_string_mnp")
        val MOCK_AES_NON_SCLB_POS = AesName("mock_aes_non_sclb_pos")
        val MOCK_AES_SCLB_POS = AesName("mock_aes_sclb_pos")
    }

    class MockAesDoubleNonPos(override val context: BindingContext) : NonPositionalAes<Double> {
        override val name: AesName = MOCK_AES_DOUBLE_NON_POS
    }

    class MockAesStringMapNonPos(override val context: BindingContext) : ScalableNonPositionalAes<String> {
        override val name: AesName = MOCK_AES_STRING_MAP_NON_POS
    }

    class MockAesNonSclbPos(override val context: BindingContext) : NonScalablePositionalAes {
        override val name: AesName = MOCK_AES_NON_SCLB_POS
    }

    class MockAesSclbPos(override val context: BindingContext) : ScalablePositionalAes {
        override val name: AesName = MOCK_AES_SCLB_POS
    }

    class MockAesColorMapNonPos(override val context: BindingContext) : ScalableNonPositionalAes<Color> {
        override val name: AesName = MOCK_AES_COLOR_MAP_NON_POS
    }

    class TestContext : BindingContext {
        override val bindingCollector: BindingCollector = BindingCollector()
        val x = XAes(this)
        val y = YAes(this)

        val mockAesDoubleNonPos = MockAesDoubleNonPos(this)
        val mockAesStringMapNonPos = MockAesStringMapNonPos(this)
        val mockAesColorMapNonPos = MockAesColorMapNonPos(this)
        val mockAesNonSclbPos = MockAesNonSclbPos(this)
        val mockAesSclbPos = MockAesSclbPos(this)
    }

    @Test
    fun testSetting() {
        //  val mockAesDouble = NonPositionalAes<Double>("mock_aes")
        val valueDouble = 34.831
        val context = TestContext().apply {
            mockAesDoubleNonPos(valueDouble)
        }
        assertEquals(
            mapOf(MOCK_AES_DOUBLE_NON_POS to NonPositionalSetting<Double>(MOCK_AES_DOUBLE_NON_POS, valueDouble.typed())),
            context.bindingCollector.settings.toMap()
        )

        // val mockAesString = NonPositionalAes<String>("mock_aes_string")
        val valueString = "MOCK_VALUE"
        context.apply {
            mockAesStringMapNonPos(valueString)
        }
        assertEquals(
            mapOf(
                MOCK_AES_DOUBLE_NON_POS to NonPositionalSetting<Double>(MOCK_AES_DOUBLE_NON_POS, valueDouble.typed()),
                MOCK_AES_STRING_MAP_NON_POS to NonPositionalSetting<Double>(MOCK_AES_STRING_MAP_NON_POS, valueString.typed())
            ),
            context.bindingCollector.settings.toMap()
        )
    }

    @Test
    fun testMappingNonScalable() {
        //  val mockAes = NonScalablePositionalAes("mock_aes")
        val mockSource = columnPointer<Double>("mock_source")
        val context = TestContext().apply {
            mockAesNonSclbPos(mockSource)
        }
        assertEquals<Map<AesName, Mapping>>(
            mapOf(
                MOCK_AES_NON_SCLB_POS to NonScalablePositionalMapping(
                    MOCK_AES_NON_SCLB_POS,
                    mockSource,
                    typeOf<Double>()
                )
            ),
            context.bindingCollector.mappings
        )
    }

    @Test
    fun testMappingUnscaled() {
        //   val mockAes = MappableNonPositionalAes<Int>("mock_aes")
        val mockSource = columnPointer<Int>("mock_source")
        val context = TestContext().apply {
            mockAesStringMapNonPos(mockSource)
        }
        assertEquals<Map<AesName, Mapping>>(
            mapOf(
                MOCK_AES_STRING_MAP_NON_POS to ScaledUnspecifiedDefaultNonPositionalMapping<Int, String>(
                    MOCK_AES_STRING_MAP_NON_POS,
                    mockSource.scaled(),
                    typeOf<Int>()
                )
            ),
            context.bindingCollector.mappings
        )
    }

    @Test
    fun testMappingScaledUnspecified() {
      //  val mockAes = ScalablePositionalAes("mock_aes")
        val mockSource = columnPointer<String>("mock_source")
        val context = TestContext().apply {
            mockAesSclbPos(mockSource.scaled())
        }
        assertEquals<Map<AesName, Mapping>>(
            mapOf(
                MOCK_AES_SCLB_POS to ScaledUnspecifiedDefaultPositionalMapping(
                    MOCK_AES_SCLB_POS, mockSource.scaled(), typeOf<String>()
                )
            ),
            context.bindingCollector.mappings
        )
    }

    @Test
    fun testMappingScaledPositionalDefault() {
       // val mockAes = ScalablePositionalAes("mock_aes")
        val mockSource = columnPointer<Float>("mock_source")
        val context = TestContext().apply {
            mockAesSclbPos(mockSource.scaled(continuousPos()))
        }
        assertEquals<Map<AesName, Mapping>>(
            mapOf(
                MOCK_AES_SCLB_POS to ScaledPositionalUnspecifiedMapping(
                    MOCK_AES_SCLB_POS, mockSource.scaled(
                    continuousPos()
                ), typeOf<Float>()
                )
            ),
            context.bindingCollector.mappings
        )
    }

    @Test
    fun testMappingScaledNonPositionalDefault() {
      //  val mockAes = MappableNonPositionalAes<LineType>("mock_aes")
        val mockSource = columnPointer<String>("mock_source")
        val context = TestContext().apply {
            mockAesStringMapNonPos(mockSource.scaled(categorical()))
        }
        assertEquals<Map<AesName, Mapping>>(
            mapOf(
                MOCK_AES_STRING_MAP_NON_POS to ScaledNonPositionalUnspecifiedMapping<String, String>(
                    MOCK_AES_STRING_MAP_NON_POS, mockSource.scaled(
                    categorical()
                ), typeOf<String>()
                )
            ),
            context.bindingCollector.mappings
        )
    }

    @Test
    fun testMappingScaledPositional() {
       // val mockAes = ScalablePositionalAes("mock_aes")
        val mockSource = columnPointer<String>("mock_source")

        val scale = categoricalPos(
            categories = listOf("cat1", "cat2", "cat3")
        )
        val context = TestContext().apply {
            mockAesSclbPos(mockSource.scaled(scale))
        }
        assertEquals<Map<AesName, Mapping>>(
            mapOf(
                MOCK_AES_SCLB_POS to ScaledPositionalMapping(
                    MOCK_AES_SCLB_POS,
                    mockSource.scaled(scale),
                    typeOf<String>()
                )
            ),
            context.bindingCollector.mappings
        )
    }

    @Test
    fun testMappingScaledNonPositional() {
      //  val mockAes = MappableNonPositionalAes<Color>("mock_aes")
        val mockSource = columnPointer<Int>("mock_source")
        val scale = continuous<Int, Color>(
            rangeLimits = Color.rgb(1, 1, 1) to Color.rgb(1, 100, 100)
        )
        val context = TestContext().apply {
            mockAesColorMapNonPos(mockSource.scaled(scale))
        }
        assertEquals<Map<AesName, Mapping>>(
            mapOf(
                MOCK_AES_STRING_MAP_NON_POS to ScaledNonPositionalMapping(
                    MOCK_AES_STRING_MAP_NON_POS,
                    mockSource.scaled(scale),
                    typeOf<Int>(),
                    //typeOf<Color>()
                )
            ),
            context.bindingCollector.mappings
        )
    }
}



