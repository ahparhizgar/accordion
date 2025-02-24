package io.amirhparhizgar.accordion

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.drawscope.DrawTransform
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.setFrom
import androidx.compose.ui.tooling.preview.Preview
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
                val maxSqueeze = 0.7f
                val m = android.graphics.Matrix()
                val smallWidth = size.width - size.width * sin(r) * (1 - maxSqueeze)
                val rotatedHeight =
                    size.height * cos(r) * ratioOfPerspective(smallWidth / size.width, n)
                val rotatedHeight2 =
                    size.height * cos(r) * ratioOfPerspective(size.width / smallWidth, n)
                val totalRotatedHeight = size.height * cos(r)
//        val rotatedHeight = totalRotatedHeight * (size.width / (size.width + smallWidth))
//        val rotatedHeight = totalRotatedHeight * size.width / (n * (smallWidth + size.width) / 2)
                val oneNHeight = size.height / n
//                val gap = android.graphics
//                    .Matrix()
//                    .setRotation(oneNHeight, 1f, oneNHeight, 1f, r)
//                    .first
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

private fun DrawTransform.rotation(
    top: Float,
    m: android.graphics.Matrix,
    r: Float,
    p: Float,
    height: Float
) {
    m.setRotation(top, size.width, height, p, r)
    transform(Matrix().apply { setFrom(m) })
}


fun ratioOfPerspective(
    r: Float = 1f / 10f,
    n: Int
): Float = ((-2) + kotlin.math.sqrt(4 + 4 * (r - 1) * (1 + r) / n)) / (2 * (r - 1))

@Preview
@Composable
private fun FoldPreview() {
    var foldDegree by remember { mutableStateOf(80f) }
    val anim = rememberInfiniteTransition()
    val degreeAnim by
    anim.animateFloat(0f, 90f, InfiniteRepeatableSpec(tween(2000), RepeatMode.Reverse))
    Column {
        BasicText(
            modifier = Modifier
                .accordion(degreeAnim % 90)
                .background(
                    Brush.linearGradient(
                        Pair(0f, Color.White),
                        Pair(1f, Color.Black)
                    )
                ).background(Color.White)
                .clickable {
                    foldDegree += 10
                },
            text = "AAAAAAAAAAAA\n" +
                    "BBBBBBBBBBBB\n" +
                    "CCCCCCCCCCCC\n" +
                    "DDDDDDDDDDDD"
        )
    }
}

fun android.graphics.Matrix.setRotation(
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
