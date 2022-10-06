/*
* Copyright 2020-2022 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/

package org.jetbrains.kotlinx.ggdsl.dsl

import org.jetbrains.kotlinx.ggdsl.ir.aes.*
import org.jetbrains.kotlinx.ggdsl.ir.bindings.*
import org.jetbrains.kotlinx.ggdsl.ir.data.ColumnPointer
import kotlin.reflect.KProperty
import kotlin.reflect.typeOf

/**
 * Sets the given constant value to this non-positional aesthetic attribute
 *
 * @param value the assigned value.
 */
public operator fun <T : Any> NonPositionalAes<T>.invoke(value: T) {
    context.bindingCollector.settings[this.name] = NonPositionalSetting(this.name, value)
}

// TODO
public operator fun <T : Number> PositionalAes.invoke(value: T) {
    context.bindingCollector.settings[this.name] = PositionalSetting(this.name, value)
}

/**
 * Maps the given [ColumnPointer] to this non-scalable ("sub-positional") aesthetic attribute.
 *
 * @param source the mapped raw data source.
 */

public inline operator fun <reified DomainType : Any> NonScalablePositionalAes.invoke(
    source: ColumnPointer<DomainType>
) {
    context.bindingCollector.mappings[this.name] =
        NonScalablePositionalMapping(this.name, source, typeOf<DomainType>())
}
/*
/**
 * Maps the given [Iterable] to this non-scalable ("sub-positional") aesthetic attribute.
 *
 * @param data the mapped raw data.
 */
public inline operator fun <reified DomainType : Any> NonScalablePositionalAes.invoke(
    data: Iterable<DomainType>
) {
    context.bindingCollector.mappings[this.name] =
        NonScalablePositionalMapping(this.name, with(context) { data.toDataSource() }, typeOf<DomainType>())
}

 */

/**
 * Maps the given property to this non-scalable ("sub-positional") aesthetic attribute.
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
 * Maps the given [ColumnPointer] to this positional aesthetic attribute with default scale.
 *
 * @param source the mapped raw data source.
 */
public inline operator fun <reified DomainType : Any> ScalablePositionalAes.invoke(
    source: ColumnPointer<DomainType>
): ScaledUnspecifiedDefaultPositionalMapping<DomainType> {
    val mapping = ScaledUnspecifiedDefaultPositionalMapping<DomainType>(
        this.name,
        source.scaled(),
        typeOf<DomainType>()
    )
    context.bindingCollector.mappings[this.name] = mapping
    return mapping
}

/**
 * Maps the given [Iterable] to this positional aesthetic attribute with default scale.
 *
 * @param data the mapped raw data.
 */
/*
inline operator fun <reified DomainType : Any> ScalablePositionalAes.invoke(
=======
public inline operator fun <reified DomainType : Any> ScalablePositionalAes.invoke(
>>>>>>> main
    data: Iterable<DomainType>
): ScaledUnspecifiedDefaultPositionalMapping<DomainType> {
    val mapping = ScaledUnspecifiedDefaultPositionalMapping(
        this.name,
        with(context) { data.toDataSource() }.scaled(),
        typeOf<DomainType>()
    )
    context.bindingCollector.mappings[this.name] = mapping
    return mapping
}

 */

/**
 * Maps the given property to this positional aesthetic attribute with a default scale.
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
<<<<<<< HEAD
 * Maps the given [ColumnPointer] to this non-positional aesthetic attribute with default scale.
 *
 * @param source the mapped raw data source.
 */
public inline operator fun <reified DomainType : Any, RangeType : Any> MappableNonPositionalAes<RangeType>.invoke(
    source: ColumnPointer<DomainType>
): ScaledUnspecifiedDefaultNonPositionalMapping<DomainType, RangeType> {
    val mapping = ScaledUnspecifiedDefaultNonPositionalMapping<DomainType, RangeType>(
        this.name,
        source.scaled(),
        typeOf<DomainType>()
    )
    context.bindingCollector.mappings[this.name] = mapping
    return mapping
}

/**
 * Maps the given [Iterable] to this non-positional aesthetic attribute with a default scale.
 *
 * @param data the mapped raw data.
 */

/*
inline operator fun <reified DomainType : Any, RangeType : Any> MappableNonPositionalAes<RangeType>.invoke(
=======
public inline operator fun <reified DomainType : Any, RangeType : Any> MappableNonPositionalAes<RangeType>.invoke(
>>>>>>> main
    data: Iterable<DomainType>
): ScaledUnspecifiedDefaultNonPositionalMapping<DomainType, RangeType> {
    val mapping = ScaledUnspecifiedDefaultNonPositionalMapping<DomainType, RangeType>(
        this.name,
        with(context) { data.toDataSource() }.scaled(),
        typeOf<DomainType>()
    )
    context.bindingCollector.mappings[this.name] = mapping
    return mapping
}

 */

/**
 * Maps the given property to this non-positional aesthetic attribute with a default scale.
 *
 * @param property the mapped [KProperty].
 */
public inline operator fun <reified DomainType : Any, RangeType : Any> MappableNonPositionalAes<RangeType>.invoke(
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
 * Maps the given scaled source to this positional aesthetic attribute with an unspecified scale.
 *
 * @param sourceScaledDefault the mapped source scaled default.
 */
public inline operator fun <reified DomainType : Any> ScalablePositionalAes.invoke(
    sourceScaledDefault: SourceScaledUnspecifiedDefault<DomainType>
): ScaledUnspecifiedDefaultPositionalMapping<DomainType> {
    val mapping = ScaledUnspecifiedDefaultPositionalMapping(
        this.name,
        sourceScaledDefault,
        typeOf<DomainType>()
    )
    context.bindingCollector.mappings[this.name] = mapping
    return mapping
}

/**
 * Maps the given scaled source to this non-positional aesthetic attribute with an unspecified scale.
 *
 * @param sourceScaledDefault the mapped source scaled default.
 */
public inline operator fun <reified DomainType : Any, RangeType : Any> MappableNonPositionalAes<RangeType>.invoke(
    sourceScaledDefault: SourceScaledUnspecifiedDefault<DomainType>
): ScaledUnspecifiedDefaultNonPositionalMapping<DomainType, RangeType> {
    val mapping = ScaledUnspecifiedDefaultNonPositionalMapping<DomainType, RangeType>(
        this.name,
        sourceScaledDefault,
        typeOf<DomainType>()
    )
    context.bindingCollector.mappings[this.name] = mapping
    return mapping
}

/**
 * Maps the given scaled source to this positional aesthetic attribute with a default scale.
 *
 * @param sourceScaledDefault the mapped source scaled unspecified positional.
 */
public inline operator fun <reified DomainType : Any> ScalablePositionalAes.invoke(
    sourceScaledDefault: SourceScaledPositionalUnspecified<DomainType>
): ScaledPositionalUnspecifiedMapping<DomainType> {
    val mapping = ScaledPositionalUnspecifiedMapping(
        this.name,
        sourceScaledDefault,
        typeOf<DomainType>()
    )
    context.bindingCollector.mappings[this.name] = mapping
    return mapping
}


/**
 * Maps the given scaled source to this non-positional aesthetic attribute with an unspecified scale.
 *
 * @param sourceScaledDefault the mapped source scaled unspecified non-positional.
 */
public inline operator fun <reified DomainType : Any, RangeType : Any> MappableNonPositionalAes<RangeType>.invoke(
    sourceScaledDefault: SourceScaledNonPositionalUnspecified<DomainType>
): ScaledNonPositionalDefaultMapping<DomainType, RangeType> {
    val mapping = ScaledNonPositionalDefaultMapping<DomainType, RangeType>(
        this.name,
        sourceScaledDefault,
        typeOf<DomainType>()
    )
    context.bindingCollector.mappings[this.name] = mapping
    return mapping
}

/**
 * Maps the given scaled source to this positional aesthetic attribute.
 *
 * @param sourceScaledPositional the mapped source scaled positional.
 */
public inline operator fun <reified DomainType : Any> ScalablePositionalAes.invoke(
    sourceScaledPositional: SourceScaledPositional<DomainType>
): ScaledPositionalMapping<DomainType> {
    val mapping = ScaledPositionalMapping(
        this.name,
        sourceScaledPositional,
        typeOf<DomainType>()
    )
    context.bindingCollector.mappings[this.name] = mapping
    return mapping
}

/**
 * Maps the given scaled source to this non-positional aesthetic attribute.
 *
 * @param sourceScaledNonPositional the mapped source scaled non-positional.
 */
public inline operator fun <reified DomainType : Any, reified RangeType : Any>
    MappableNonPositionalAes<RangeType>.invoke(
    sourceScaledNonPositional: SourceScaledNonPositional<DomainType, RangeType>
): ScaledNonPositionalMapping<DomainType, RangeType> {
    val mapping = ScaledNonPositionalMapping(
        this.name,
        sourceScaledNonPositional,
        typeOf<DomainType>()
    )
    context.bindingCollector.mappings[this.name] = mapping
    return mapping
}
