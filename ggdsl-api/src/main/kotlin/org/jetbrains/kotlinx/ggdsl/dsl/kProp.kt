/*
* Copyright 2020-2022 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/

package org.jetbrains.kotlinx.ggdsl.dsl

import org.jetbrains.kotlinx.ggdsl.ir.bindings.*
import org.jetbrains.kotlinx.ggdsl.ir.data.ColumnPointer
import org.jetbrains.kotlinx.ggdsl.ir.scale.NonPositionalScale
import org.jetbrains.kotlinx.ggdsl.ir.scale.NonPositionalUnspecifiedScale
import org.jetbrains.kotlinx.ggdsl.ir.scale.PositionalScale
import org.jetbrains.kotlinx.ggdsl.ir.scale.PositionalUnspecifiedScale
import kotlin.reflect.KProperty
import kotlin.reflect.typeOf

//TODO
@PublishedApi
internal inline fun <reified T : Any> KProperty<T>.toColumnPointer(): ColumnPointer<T> =
    ColumnPointer(this.name)

/**
 * Maps the given [KProperty] (i.e. a column with the same name and type as the given property)
 * to this non-scalable positional ("sub-positional") aesthetic attribute.
 *
 * @param property the mapped [KProperty].
 */
public inline operator fun <reified DomainType : Any> NonScalablePositionalAes.invoke(
    property: KProperty<DomainType>
) {
    context.bindingCollector.mappings[this.name] =
        NonScalablePositionalMapping(this.name, property.toColumnPointer(), typeOf<DomainType>())
}

/**
 * Maps the given [KProperty] (i.e. a column with the same name and type as the given property)
 * to this positional aesthetic attribute with a default (i.e. without specifying the type and parameters; 
 * they will be defined automatically) scale.
 *
 * @param property the mapped [KProperty].
 */
public inline operator fun <reified DomainType : Any> ScalablePositionalAes.invoke(
    property: KProperty<DomainType>
): ScaledUnspecifiedDefaultPositionalMapping<DomainType> {
    val mapping = ScaledUnspecifiedDefaultPositionalMapping<DomainType>(
        this.name,
        property.toColumnPointer().scaled(),
        typeOf<DomainType>()
    )
    context.bindingCollector.mappings[this.name] = mapping
    return mapping
}

/**
 * Maps the given [KProperty] (i.e. a column with the same name and type as the given property)
 * to this non-positional aesthetic attribute with a default (i.e. without specifying the type and parameters; 
 * they will be defined automatically) scale.
 *
 * @param property the mapped [KProperty].
 */
public inline operator fun <reified DomainType : Any, RangeType : Any> ScalableNonPositionalAes<RangeType>.invoke(
    property: KProperty<DomainType>
): ScaledUnspecifiedDefaultNonPositionalMapping<DomainType, RangeType> {
    val mapping = ScaledUnspecifiedDefaultNonPositionalMapping<DomainType, RangeType>(
        this.name,
        property.toColumnPointer().scaled(),
        typeOf<DomainType>()
    )
    context.bindingCollector.mappings[this.name] = mapping
    return mapping
}

/**
 * Applies default (i.e. without specifying the type and parameters;
 * they will be defined automatically; can be both used for positional and non-positional mappings) scale
 * to this [KProperty] (i.e. a column with the same name and type as the given property).
 */
public inline fun <reified DomainType : Any> KProperty<DomainType>.scaled()
: ColumnScaledUnspecifiedDefault<DomainType> =
    ColumnScaledUnspecifiedDefault(this.toColumnPointer())

/**
 * Applies unspecified (i.e. without specifying the type and parameters;
 * they will be defined automatically) positional scale to this [KProperty] (i.e. a column with the same
 * name and type as the given property).
 *
 * @param DomainType type of domain.
 * @param scale positional default scale.
 * @return scaled source.
 */
public inline fun <reified DomainType : Any> KProperty<DomainType>.scaled(scale: PositionalUnspecifiedScale):
        ColumnScaledPositionalUnspecified<DomainType> =
    ColumnScaledPositionalUnspecified(this.toColumnPointer(), scale)

/**
 * Applies unspecified (i.e. without specifying the type and parameters;
 * they will be defined automatically) non-positional scale to this [KProperty] (i.e. a column with the same
 * name and type as the given property).
 *
 * @param DomainType type of domain.
 * @param scale non-positional default scale.
 * @return scaled source.
 */

public inline fun <reified DomainType : Any> KProperty<DomainType>.scaled(scale: NonPositionalUnspecifiedScale):
        ColumnScaledNonPositionalUnspecified<DomainType> =
    ColumnScaledNonPositionalUnspecified(this.toColumnPointer(), scale)

/**
 * Applies positional scale to this [KProperty] (i.e. a column with the same name
 * and type as the given property).
 *
 * @param DomainType type of domain.
 * @param scale positional scale.
 * @return scaled source.
 */

public inline fun <reified DomainType : Any> KProperty<DomainType>.scaled(
    scale: PositionalScale<DomainType>
): ColumnScaledPositional<DomainType> = ColumnScaledPositional(this.toColumnPointer(), scale)

/**
 * Applies non-positional scale to this [KProperty] (i.e. a column with the same name
 * and type as the given property).
 *
 * @param DomainType type of domain.
 * @param scale non-positional scale.
 * @return scaled source.
 */
public inline fun <reified DomainType : Any, RangeType : Any> KProperty<DomainType>.scaled(
    scale: NonPositionalScale<DomainType, RangeType>
): ColumnScaledNonPositional<DomainType, RangeType> = ColumnScaledNonPositional(this.toColumnPointer(), scale)
