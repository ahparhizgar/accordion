package io.amirhparhizgar.accordion

import android.graphics.Matrix
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

public fun Modifier.accordion(
    scale: Float,
    countStrategy: AccordionFoldStrategy = AccordionFoldStrategy.Default,
    resize: Boolean = true
): Modifier = this
    .layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        val r = acos(scale)
        val newHeight = placeable.height * cos(r)
        layout(placeable.width, if (resize) newHeight.roundToInt() else placeable.height) {
            placeable.place(x = 0, y = 0)
        }
    }
    .drawWithContent {
        accordion(
            originalHeight = size.height,
            width = size.width,
            scale = scale,
            countStrategy = countStrategy
        )
    }


public fun ContentDrawScope.accordion(
    originalHeight: Float,
    width: Float,
    scale: Float,
    countStrategy: AccordionFoldStrategy = AccordionFoldStrategy.Default
) {
    val r = acos(scale)
    val m = Matrix()
    val n = countStrategy.calculateFoldCount(originalHeight, width, this)
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
            this@accordion.drawContent()
            drawRect(Color.Black.copy(alpha = 0.2f * sin(r)))
        }
        withTransform(
            {
                translate(top = -(2 * i + 2) * gap)
                rotation(top = (2 * i + 1) * oneNHeight, m = m, r = r, topRatio = 1f, oneNHeight)
                clipRect(0f, oneNHeight * (2 * i + 1), width, oneNHeight * (2 * i + 2))
            }
        ) {
            this@accordion.drawContent()
        }
    }
}

public interface AccordionFoldStrategy {
    fun calculateFoldCount(mainAxisSize: Float, otherAxisSize: Float, density: Density): Int

    companion object {
        val Default = FoldSize(50.dp)
    }

    public data class Fixed(val count: Int) : AccordionFoldStrategy {
        override fun calculateFoldCount(
            mainAxisSize: Float,
            otherAxisSize: Float,
            density: Density
        ): Int {
            return count
        }
    }

    public data class FoldSize(val size: Dp) : AccordionFoldStrategy {
        override fun calculateFoldCount(
            mainAxisSize: Float,
            otherAxisSize: Float,
            density: Density
        ): Int {
            return (mainAxisSize / with(density) { size.toPx() }).roundToInt().coerceAtLeast(1)
        }
    }
}