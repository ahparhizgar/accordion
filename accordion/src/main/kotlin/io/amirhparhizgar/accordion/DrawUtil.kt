package io.amirhparhizgar.accordion

import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.drawscope.DrawTransform
import androidx.compose.ui.graphics.setFrom
import kotlin.math.cos
import kotlin.math.sin

internal fun DrawTransform.rotation(
    top: Float,
    m: android.graphics.Matrix,
    r: Float,
    topRatio: Float,
    height: Float
): Matrix {
    m.setRotation(top, size.width, height, topRatio, r)
    return Matrix().apply { setFrom(m) }
}

internal fun android.graphics.Matrix.setRotation(
    top: Float,
    width: Float,
    height: Float,
    topRatio: Float,
    rad: Float,
    cameraHeight: Float = 700f
): Pair<Float, Float> {
    val sin = sin(rad)
    val cos = cos(rad)
    val src = floatArrayOf(
        0f,
        top,
        width,
        top,
        width,
        top + height,
        0f,
        top + height
    )
    val iTopRatio = 1f - topRatio
    val topHeight = height * sin * topRatio
    val topFactor = (cameraHeight + topHeight) / cameraHeight
    val topOffset = (width - width * topFactor) / 2
    val bottomHeight = height * iTopRatio * sin
    val bottomFactor = (cameraHeight - bottomHeight) / cameraHeight
    val bottomOffset = (width - width * bottomFactor) / 2
    val dst = floatArrayOf(
        -topOffset,
        top + height * (1 - cos) * topRatio,
        width + topOffset,
        top + height * (1 - cos) * topRatio,
        width + bottomOffset,
        top + height - height * (1 - cos) * iTopRatio,
        -bottomOffset,
        top + height - height * (1 - cos) * iTopRatio,
    )
    val pointCount = 4
    setPolyToPoly(src, 0, dst, 0, pointCount)
    return Pair(dst[1], dst[5])
}

