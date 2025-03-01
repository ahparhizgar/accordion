package io.amirhparhizgar.accordion

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

fun Modifier.accordion(
    foldDegree: Float,
    n: Int = 4
): Modifier =
    this.then(
        Modifier
//        .layout { measurable, constraints ->
//            val r = PI.toFloat() * foldDegree / 180f
//            val intrinsicHeight = measurable.minIntrinsicHeight(constraints.maxWidth)
//            val newHeight =intrinsicHeight * cos(r)
//            val placeable = measurable.measure(constraints.constrain(Constraints.fixedHeight(newHeight.roundToInt())))
//            layout(placeable.width, placeable.height) {
//                placeable.place(0,0)
//            }
//        }
            .drawWithContent {
                val r = PI.toFloat() * foldDegree / 180f
                val m = android.graphics.Matrix()
                val oneNHeight = size.height / n
                val gap = (size.height - size.height * cos(r)) / n
                withTransform(
                    {
                        rotation(top = 0f, m = m, r = -r, p = 0f, oneNHeight)
                        clipRect(0f, oneNHeight * 0, size.width, oneNHeight * 1)
                    }
                ) {
                    this@drawWithContent.drawContent()
                    drawRect(Color.Black.copy(alpha = 0.2f * sin(r)))
                }
                withTransform(
                    {
                        translate(top = -2 * gap)
                        rotation(1 * oneNHeight, m = m, r = r, p = 1f, oneNHeight)
                        clipRect(0f, oneNHeight * 1, size.width, oneNHeight * 2)
                    }
                ) {
                    this@drawWithContent.drawContent()
                }
                withTransform(
                    {
                        translate(top = -2 * gap)
                        rotation(top = 2 * oneNHeight, m = m, r = -r, p = 0f, oneNHeight)
                        clipRect(0f, oneNHeight * 2, size.width, oneNHeight * 3)
                    }
                ) {
                    this@drawWithContent.drawContent()
                    drawRect(Color.Black.copy(alpha = 0.2f * sin(r)))
                }
                withTransform(
                    {
                        translate(top = -4 * gap)
                        rotation(top = 3 * oneNHeight, m = m, r = r, p = 1f, oneNHeight)
                        clipRect(0f, oneNHeight * 3, size.width, oneNHeight * 4)
                    }
                ) {
                    this@drawWithContent.drawContent()
                }
            }
    )
