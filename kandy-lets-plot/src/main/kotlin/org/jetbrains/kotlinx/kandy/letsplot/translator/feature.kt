/*
* Copyright 2020-2022 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/

package org.jetbrains.kotlinx.kandy.letsplot.translator

//import org.jetbrains.kotlinx.kandy.dsl.NamedData
//import org.jetbrains.kotlinx.kandy.dsl.column.invoke
//import org.jetbrains.kotlinx.kandy.ir.data.TypedList
import org.jetbrains.kotlinx.kandy.ir.feature.PlotFeature
import org.jetbrains.kotlinx.kandy.letsplot.facet.feature.FacetGridFeature
import org.jetbrains.kotlinx.kandy.letsplot.facet.feature.FacetWrapFeature
import org.jetbrains.kotlinx.kandy.letsplot.feature.CoordFlip
import org.jetbrains.kotlinx.kandy.letsplot.feature.Layout
import org.jetbrains.kotlinx.kandy.letsplot.feature.Reversed
import org.jetbrains.kotlinx.kandy.letsplot.position.Position
import org.jetbrains.kotlinx.kandy.letsplot.theme.Flavor
import org.jetbrains.kotlinx.kandy.letsplot.tooltips.feature.LayerTooltips
import org.jetbrains.letsPlot.coord.coordFlip
import org.jetbrains.letsPlot.facet.facetGrid
import org.jetbrains.letsPlot.facet.facetWrap
import org.jetbrains.letsPlot.ggsize
import org.jetbrains.letsPlot.intern.Feature
import org.jetbrains.letsPlot.intern.OptionsMap
import org.jetbrains.letsPlot.intern.layer.PosOptions
import org.jetbrains.letsPlot.label.labs
import org.jetbrains.letsPlot.pos.*
import org.jetbrains.letsPlot.themes.flavorDarcula
import org.jetbrains.letsPlot.themes.flavorHighContrastDark
import org.jetbrains.letsPlot.themes.flavorHighContrastLight
import org.jetbrains.letsPlot.themes.flavorSolarizedLight
import org.jetbrains.letsPlot.tooltips.TooltipOptions
import org.jetbrains.letsPlot.tooltips.layerTooltips
import org.jetbrains.letsPlot.tooltips.tooltipsNone

internal fun FacetGridFeature.wrap(): OptionsMap {
    return facetGrid(
        x = x,
        y = y,
        scales = scalesSharing?.name,
        xOrder = xOrder.value,
        yOrder = yOrder.value,
        xFormat = xFormat,
        yFormat = yFormat
    )
}

internal fun FacetWrapFeature.wrap(): OptionsMap {
    return facetWrap(
        facets = facets,
        ncol = nCol,
        nrow = nRow,
        scales = scalesSharing.name,
        order = orders.map { it.value },
        format = formats,
        dir = direction.name
    )
}

internal fun Flavor.wrap(): OptionsMap {
    return when(this) {
        Flavor.DARCULA -> flavorDarcula()
        Flavor.SOLARIZED_LIGHT -> flavorSolarizedLight()
        Flavor.SOLARIZED_DARK -> flavorSolarizedLight()
        Flavor.HIGH_CONTRAST_LIGHT -> flavorHighContrastLight()
        Flavor.HIGH_CONTRAST_DARK -> flavorHighContrastDark()
    }
}

internal fun Layout.wrap(featureBuffer: MutableList<Feature>) {
    val labs = labs(
        title, subtitle, caption, xAxisLabel, yAxisLabel
    )
    featureBuffer.addAll(labs.elements)
    size?.let {
        featureBuffer.add(ggsize(it.first, it.second))
    }
    theme?.let {
        featureBuffer.add(it.wrap())
    }
    customTheme?.let {
        featureBuffer.add(it.wrap())
    }
    flavor?.let {
        featureBuffer.add(it.wrap())
    }
}

internal fun PlotFeature.wrap(featureBuffer: MutableList<Feature>) {
    if (this is ExternalLetsPlotFeature) {
        featureBuffer += wrap()
        return
    }
    // todo featureName
    //TODO check is lp feature
    when (featureName) {
        FacetGridFeature.FEATURE_NAME -> {
            featureBuffer.add((this as FacetGridFeature).wrap())
        }

        FacetWrapFeature.FEATURE_NAME -> {
            featureBuffer.add((this as FacetWrapFeature).wrap())
        }

        CoordFlip.FEATURE_NAME -> {
            featureBuffer.add(coordFlip())
        }

        Layout.NAME -> {
            (this as Layout).wrap(featureBuffer)
        }

        /*
        GatheringList.FEATURE_NAME -> {
            (this as GatheringList).gatheringList.forEach {
                it.toLayer().wrap(featureBuffer, null)
            }
        }

         */

        else -> TODO()
    }
    //return featureBuffer
}

//internal val palette = listOf(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE)
/*
internal fun Gathering.toLayer(): Layer {
    val dataFrame = data.dataFrame
    val size = dataFrame.rowsCount()
    val mappingAesNames = series.first().settings.keys
    val xBuffer = mutableListOf<Any?>()
    val yBuffer = mutableListOf<Any?>()
    val labelBuffer = mutableListOf<String>()
    val scaleBuffer = mappingAesNames.associateWith {
        mutableListOf<String>() to mutableListOf<Any?>()
    }

    val xType = series.first().mappings[X]!!.domainType
    val yType = series.first().mappings[Y]!!.domainType

    series.forEach {series ->
        xBuffer.addAll(dataFrame[series.mappings[X]!!.columnName()].values())
        yBuffer.addAll(dataFrame[series.mappings[Y]!!.columnName()].values())
        labelBuffer.addAll(List(size){series.label})
        series.settings.forEach { (aesName, setting) ->
            scaleBuffer[aesName]!!.let {
                it.first.add(series.label)
                it.second.add((setting as NonPositionalSetting<*>).value!!)
            }
        }
    }


    val nonPosScales: Map<AesName, Mapping> = if (scaleBuffer.isEmpty()) {
        mapOf(
            COLOR to ScaledNonPositionalUnspecifiedMapping<String, Color>(
            COLOR,
            "label"<String>().scaled(categorical()),
            typeOf<String>()
        ))
    } else {
        scaleBuffer.map { (aesName, buffer) ->
            aesName to ScaledNonPositionalMapping<String, Any?>(
                aesName,
                "label"<String>().scaled(
                    categorical(
                        buffer.first,
                        buffer.second
                    )
                ),
                typeOf<String>()
            )
        }.toMap()
    }

    val df = dataFrameOf(
        "x" to xBuffer,
        "y" to yBuffer,
        "label" to labelBuffer
    )
    val newData = NamedData(df)
    return Layer(
        newData,
        geom,
        mapOf(
            X to ScaledUnspecifiedDefaultPositionalMapping<Any>(
                X,
                "x"<Any>().scaled(),
                xType
            ),
            Y to ScaledUnspecifiedDefaultPositionalMapping<Any>(
                Y,
                "y"<Any>().scaled(),
                yType
            )
        ) + nonPosScales,
        globalSettings,
        mapOf(Position.FEATURE_NAME to position)
    )
}


 */

internal fun Position.wrap(): PosOptions {
    return when (this) {
        is Position.Identity -> return positionIdentity
        is Position.Stack -> return positionStack()
        is Position.Dodge -> return positionDodge(width)
        is Position.Jitter -> return positionJitter(width, height)
        is Position.Nudge -> return positionNudge(x, y)
        is Position.JitterDodge -> positionJitterDodge(dodgeWidth, jitterWidth, jitterHeight)
    }
}

internal fun LayerTooltips.wrap(): TooltipOptions {
    if (hide) {
        return tooltipsNone
    }
    var buffer = layerTooltips(*(variables.toTypedArray()))
    title?.let {
        buffer = buffer.title(it)
    }
    anchor?.let {
        buffer = buffer.anchor(it.value)
    }
    minWidth?.let {
        buffer = buffer.minWidth(it)
    }
    formats.forEach {
        buffer = buffer.format(it.first, it.second)
    }
    lines?.forEach {
        buffer = buffer.line(it)
    }
    return buffer
}

internal fun Reversed.wrap(): String = if (value) {
    "y"
} else {
    "x"
}
