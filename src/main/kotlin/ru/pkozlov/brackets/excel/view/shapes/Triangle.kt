package ru.pkozlov.brackets.excel.view.shapes

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon

/** From [developer.android.com](https://developer.android.com/jetpack/compose/graphics/draw/shapes) */
class Triangle : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val roundedPolygon = RoundedPolygon(
            numVertices = 3,
            radius = size.minDimension / 2,
            centerX = size.width / 2,
            centerY = size.height / 2,
            rounding = CornerRounding(
                radius = size.minDimension / 10f,
                smoothing = 0.1f
            )
        )
        val roundedPolygonPath = roundedPolygon.cubics.toPath()
        return Outline.Generic(path = roundedPolygonPath)
    }
}