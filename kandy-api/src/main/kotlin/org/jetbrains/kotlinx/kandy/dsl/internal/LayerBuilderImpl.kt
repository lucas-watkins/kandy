/*
* Copyright 2020-2023 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/

package org.jetbrains.kotlinx.kandy.dsl.internal

import org.jetbrains.kotlinx.dataframe.DataColumn
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.kandy.ir.Layer
import org.jetbrains.kotlinx.kandy.ir.aes.Aes
import org.jetbrains.kotlinx.kandy.ir.bindings.*
import org.jetbrains.kotlinx.kandy.ir.feature.FeatureName
import org.jetbrains.kotlinx.kandy.ir.feature.LayerFeature
import org.jetbrains.kotlinx.kandy.ir.geom.Geom


public abstract class LayerBuilderImpl internal constructor(
    parent: LayerCreatorScope,
) : LayerBuilder {

    internal abstract val geom: Geom
    internal abstract val requiredAes: Set<Aes>

    @PublishedApi
    internal val layerFeatures: MutableMap<FeatureName, LayerFeature> = mutableMapOf()


    internal val plotBuilder: MultiLayerPlotBuilder = parent.plotBuilder
    internal var datasetIndex: Int = parent.datasetIndex
    private var firstMapping: Boolean = true
    internal val datasetHandler: DatasetHandler
        get() = plotBuilder.datasetHandlers[datasetIndex]
    internal open var inheritMappings: Boolean = parent.layersInheritMappings
    private val handlerRowsCount: Int
        get() {
            val buffer = datasetHandler.buffer
            return if (buffer == DataFrame.Empty) {
                datasetHandler.initialNamedData.dataFrame.rowsCount()
            } else {
                buffer.rowsCount()
            }
        }

    private fun overrideDataset() {
        plotBuilder.addDataset(DataFrame.Empty)
        datasetIndex = plotBuilder.datasetHandlers.lastIndex
    }

    internal fun checkSourceSizeAndOverrideDataset(size: Int) {
        if (firstMapping && handlerRowsCount != size) {
            overrideDataset()
            inheritMappings = false
        }
        firstMapping = false
    }

    internal val bindingContext: BindingHandler = object : BindingHandler() {
        override val datasetHandler: DatasetHandler
            get() = this@LayerBuilderImpl.datasetHandler

        override fun <DomainType, RangeType> addNonPositionalMapping(
            aes: Aes,
            columnID: String,
            parameters: NonPositionalMappingParameters<DomainType, RangeType>?
        ): NonPositionalMapping<DomainType, RangeType> {
            firstMapping = false
            return super.addNonPositionalMapping(aes, columnID, parameters)
        }

        override fun <DomainType, RangeType> addNonPositionalMapping(
            aes: Aes,
            values: DataColumn<DomainType>,
            parameters: NonPositionalMappingParameters<DomainType, RangeType>?
        ): NonPositionalMapping<DomainType, RangeType> {
            checkSourceSizeAndOverrideDataset(values.size())
            return super.addNonPositionalMapping(aes, values, parameters)
        }

        override fun <DomainType, RangeType> addNonPositionalMapping(
            aes: Aes,
            values: List<DomainType>,
            name: String?,
            parameters: NonPositionalMappingParameters<DomainType, RangeType>?
        ): NonPositionalMapping<DomainType, RangeType> {
            checkSourceSizeAndOverrideDataset(values.size)
            return super.addNonPositionalMapping(aes, values, name, parameters)
        }

        override fun <DomainType> addPositionalMapping(
            aes: Aes,
            columnID: String,
            parameters: PositionalMappingParameters<DomainType>?
        ): PositionalMapping<DomainType> {
            firstMapping = false
            return super.addPositionalMapping(aes, columnID, parameters)
        }

        override fun <DomainType> addPositionalMapping(
            aes: Aes,
            values: DataColumn<DomainType>,
            parameters: PositionalMappingParameters<DomainType>?
        ): PositionalMapping<DomainType> {
            checkSourceSizeAndOverrideDataset(values.size())
            return super.addPositionalMapping(aes, values, parameters)
        }

        override fun <DomainType> addPositionalMapping(
            aes: Aes,
            values: List<DomainType>,
            name: String?,
            parameters: PositionalMappingParameters<DomainType>?
        ): PositionalMapping<DomainType> {
            checkSourceSizeAndOverrideDataset(values.size)
            return super.addPositionalMapping(aes, values, name, parameters)
        }
    }

    /**
     * Ensures that all required aesthetics are assigned either in the layer or plot context.
     * If any of the required aesthetics are not found, an exception is thrown.
     *
     * @param requiredAes A set of aesthetics that need to be assigned.
     * @param layerContext The context of the layer where the aesthetics could be assigned.
     * @param plotContext The context of the plot where the aesthetics could be assigned (optional).
     *
     * @throws IllegalArgumentException If any of the required aesthetics is not assigned in either the layer or the plot context.
     */
    internal fun checkRequiredAes() {
        val assignedAes: Set<Aes> = with(bindingCollector) {
            mappings.keys + settings.keys
        }.let { layerAssignedAes ->
            if (inheritMappings) {
                plotBuilder.bindingCollector.run {
                    mappings.keys + settings.keys
                } + layerAssignedAes
            } else {
                layerAssignedAes
            }
        }

        requiredAes.forEach {
            require(it in assignedAes) { "`${it.name}` is not assigned." }
        }
    }

    override fun toLayer(): Layer {
        checkRequiredAes()
        return Layer(
            datasetIndex,
            geom,
            bindingCollector.mappings,
            bindingCollector.settings,
            layerFeatures,
            bindingCollector.freeScales,
            inheritMappings
        )
    }

    internal val bindingCollector: BindingCollector = bindingContext.bindingCollector

}
