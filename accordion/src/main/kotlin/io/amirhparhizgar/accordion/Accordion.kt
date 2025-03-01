package io.amirhparhizgar.accordion

import android.graphics.Matrix
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.layout.layout
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

public fun Modifier.accordion(
    foldDegree: Float,
    n: Int = 2,
    resize: Boolean = true
): Modifier = this
    .layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        val r = PI.toFloat() * foldDegree / 180f
        val newHeight = placeable.height * cos(r)
        layout(placeable.width, if (resize) newHeight.roundToInt() else placeable.height) {
            placeable.place(x = 0, y = 0)
        }
    }
    .drawWithContent {
        accordionLogic(
            originalHeight = size.height,
            width = size.width,
            foldDegree = foldDegree,
            n = n
        )
    }


internal fun ContentDrawScope.accordionLogic(
    originalHeight: Float,
    width: Float,
    foldDegree: Float,
    n: Int
) {
    val r = PI.toFloat() * foldDegree / 180f
    val m = Matrix()
    val oneNHeight = originalHeight / (n * 2)
    val gap = (originalHeight - originalHeight * cos(r)) / (n * 2)
    repeat(n) { i ->
        withTransform(
            {
                translate(top = -2 * i * gap)
                rotation(top = 2 * i * oneNHeight, m = m, r = -r, topRatio = 0f, oneNHeight)
                clipRect(0f, oneNHeight * (2 * i), width, oneNHeight * (2 * i + 1))
            }
        ) {
            this@accordionLogic.drawContent()
            drawRect(Color.Black.copy(alpha = 0.2f * sin(r)))
        }
        withTransform(
            {
                translate(top = -(2 * i + 2) * gap)
                rotation(top = (2 * i + 1) * oneNHeight, m = m, r = r, topRatio = 1f, oneNHeight)
                clipRect(0f, oneNHeight * (2 * i + 1), width, oneNHeight * (2 * i + 2))
            }
        ) {
            this@accordionLogic.drawContent()
        }
    }
}
