package io.amirhparhizgar.accordion

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

public fun Modifier.accordion(
    foldDegree: Float,
    n: Int = 2
): Modifier =
//        .layout { measurable, constraints ->
//            val r = PI.toFloat() * foldDegree / 180f
//            val intrinsicHeight = measurable.minIntrinsicHeight(constraints.maxWidth)
//            val newHeight =intrinsicHeight * cos(r)
//            val placeable = measurable.measure(constraints.constrain(Constraints.fixedHeight(newHeight.roundToInt())))
//            layout(placeable.width, placeable.height) {
//                placeable.place(0,0)
//            }
//        }
    this.drawWithContent {
        val r = PI.toFloat() * foldDegree / 180f
        val m = android.graphics.Matrix()
        val oneNHeight = size.height / (n * 2)
        val gap = (size.height - size.height * cos(r)) / (n * 2)
        repeat(n) { i ->
            withTransform(
                {
                    translate(top = -2 * i * gap)
                    rotation(top = 2 * i * oneNHeight, m = m, r = -r, p = 0f, oneNHeight)
                    clipRect(0f, oneNHeight * (2 * i), size.width, oneNHeight * (2 * i + 1))
                }
            ) {
                this@drawWithContent.drawContent()
                drawRect(Color.Black.copy(alpha = 0.2f * sin(r)))
            }
            withTransform(
                {
                    translate(top = -(2 * i + 2) * gap)
                    rotation(top = (2 * i + 1) * oneNHeight, m = m, r = r, p = 1f, oneNHeight)
                    clipRect(0f, oneNHeight * (2 * i + 1), size.width, oneNHeight * (2 * i + 2))
                }
            ) {
                this@drawWithContent.drawContent()
            }
        }
    }
