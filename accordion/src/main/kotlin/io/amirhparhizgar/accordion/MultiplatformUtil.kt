package io.amirhparhizgar.accordion

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Matrix
import kotlin.math.cos
import kotlin.math.sin

// not working as expected
fun rotateHeightRad(
    rad: Float,
    top: Float,
    size: Size
): Matrix {
    return rotateTopRad(rad, size).let { rotation ->
        val translate = Matrix().apply { translate(y = -top / 2) }
        val iTranslate = Matrix().apply { translate(y = top / 2) }
        rotation.timesAssign(iTranslate)
        translate.timesAssign(rotation)
        translate
    }
}

// works well
fun rotateTopRad(
    rad: Float,
    size: Size
): Matrix {
    val cameraHeight = 700f
    val topHeight = size.height * sin(rad)
    val topFactor = (cameraHeight + topHeight) / cameraHeight
    val topOffset = (size.width - size.width * topFactor) / 2
    val skew = rotateTop(topOffset / size.width, size)
    val scale = Matrix().apply { scale(y = cos(-rad)) }
    skew.timesAssign(scale)
    return skew
}

fun rotateTop(
    f: Float,
    size: Size
): Matrix {
    val c = -2f * f / (2f * size.height * f - size.height)
    val a = f * size.width * (c * size.height + 1f) / size.height
    val b = size.height * (c * size.height + 1f) / size.height
    return Matrix(
        floatArrayOf(
            1.0f,
            0.0f,
            0.0f,
            0.0f,
            a,
            b,
            0.0f,
            c,
            0.0f,
            0.0f,
            1.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            1.0f
        )
    )
}
