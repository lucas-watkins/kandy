package org.jetbrains.kotlinx.ggdsl.letsplot.translator

import org.jetbrains.kotlinx.ggdsl.ir.geom.Geom
import org.jetbrains.kotlinx.ggdsl.letsplot.layers.*
import org.jetbrains.letsPlot.Stat
import org.jetbrains.letsPlot.intern.GeomKind
import org.jetbrains.letsPlot.intern.layer.GeomOptions

internal fun Geom.toStat(): org.jetbrains.letsPlot.intern.layer.StatOptions {
    return when (this) {
        BIN_2D -> Stat.bin2D()
        BOXPLOT_STAT -> Stat.boxplot()
        CONTOUR -> Stat.contour()
        CONTOUR_FILLED -> Stat.contourFilled()
        DENSITY -> Stat.density()
        DENSITY_2D -> Stat.density2D()
        DENSITY_2D_FILLED -> Stat.density2DFilled()
        FREQPOLY -> Stat.bin()
        HISTOGRAM -> Stat.bin()
        QQ -> Stat.qq()
        QQ2 -> Stat.qq2()
        QQ2_LINE -> Stat.qq2Line()
        QQ_LINE -> Stat.qqLine()
        SMOOTH -> Stat.smooth()
        VIOLIN -> Stat.yDensity()
        else -> Stat.identity
    }
}

internal fun Geom.wrap(): GeomOptions {
    return when (this) {
        AB_LINE -> org.jetbrains.letsPlot.Geom.abline()
        AREA -> org.jetbrains.letsPlot.Geom.area()
        BAR -> org.jetbrains.letsPlot.Geom.bar()
        BIN_2D -> org.jetbrains.letsPlot.Geom.tile()
        BOXPLOT -> org.jetbrains.letsPlot.Geom.boxplot()
        BOXPLOT_STAT -> org.jetbrains.letsPlot.Geom.boxplot()
        CONTOUR -> GeomOptions(GeomKind.CONTOUR)
        CONTOUR_FILLED -> GeomOptions(GeomKind.CONTOURF)
        CROSS_BAR -> org.jetbrains.letsPlot.Geom.crossbar()
        DENSITY -> GeomOptions(GeomKind.DENSITY)
        DENSITY_2D -> GeomOptions(GeomKind.DENSITY2D)
        DENSITY_2D_FILLED -> GeomOptions(GeomKind.DENSITY2DF)
        ERROR_BAR -> org.jetbrains.letsPlot.Geom.errorbar()
        FREQPOLY -> GeomOptions(GeomKind.FREQPOLY)
        HISTOGRAM -> org.jetbrains.letsPlot.Geom.histogram()
        H_LINE -> org.jetbrains.letsPlot.Geom.hline()
        LINE -> org.jetbrains.letsPlot.Geom.line()
        PATH -> org.jetbrains.letsPlot.Geom.path()
        LINE_RANGE -> org.jetbrains.letsPlot.Geom.linerange()
        POINT -> org.jetbrains.letsPlot.Geom.point()
        POINT_RANGE -> org.jetbrains.letsPlot.Geom.pointrange()
        QQ -> GeomOptions(GeomKind.Q_Q)
        QQ2 -> GeomOptions(GeomKind.Q_Q_2)
        QQ_LINE -> GeomOptions(GeomKind.Q_Q_LINE)
        QQ2_LINE -> GeomOptions(GeomKind.Q_Q_2_LINE)
        RASTER -> org.jetbrains.letsPlot.Geom.raster()
        RECT -> org.jetbrains.letsPlot.Geom.rect()
        RIBBON -> org.jetbrains.letsPlot.Geom.ribbon()
        SEGMENT -> org.jetbrains.letsPlot.Geom.segment()
        SMOOTH -> GeomOptions(GeomKind.SMOOTH)
        STEP -> org.jetbrains.letsPlot.Geom.step()
        TILE -> org.jetbrains.letsPlot.Geom.tile()
        TEXT -> org.jetbrains.letsPlot.Geom.text()
        VIOLIN -> GeomOptions(GeomKind.VIOLIN)
        V_LINE -> org.jetbrains.letsPlot.Geom.vline()
        else -> TODO()
    }
}