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
    p: Float,
    height: Float
) {
    m.setRotation(top, size.width, height, p, r)
    transform(Matrix().apply { setFrom(m) })
}

internal fun android.graphics.Matrix.setRotation(
    top: Float,
    width: Float,
    height: Float,
    p: Float,
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

    fun moveInZ(
        y: Float,
        z: Float
    ): Float {
        val distanceFromCenter = (height * 4 * cos - (y - top)) / 2
        val d = distanceFromCenter * cameraHeight / (cameraHeight - z)
        return y - (d - distanceFromCenter)
    }

    val topHeight = height * p * sin
    val topFactor = (cameraHeight + topHeight) / cameraHeight
    val topOffset = (width - width * topFactor) / 2
    val bottomHeight = height * (1 - p) * sin
    val bottomFactor = (cameraHeight - bottomHeight) / cameraHeight
    val bottomOffset = (width - width * bottomFactor) / 2
    val dst = floatArrayOf(
        -topOffset,
        moveInZ(top + height * p * (1 - cos), -height * p * sin),
        width + topOffset,
        moveInZ(top + height * p * (1 - cos), -height * p * sin),
        width + bottomOffset,
        moveInZ(top + height - height * (1 - cos) * (1 - p), height * (1 - p) * sin),
        -bottomOffset,
        moveInZ(top + height - height * (1 - cos) * (1 - p), height * (1 - p) * sin),
    )
    val pointCount = 4
    setPolyToPoly(src, 0, dst, 0, pointCount)
    return Pair(dst[1], dst[5])
}

