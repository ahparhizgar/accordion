package io.amirhparhizgar.accordion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.unit.Constraints

fun Modifier.squeeze(effect: ContentDrawScope.(scale: Float) -> Unit): Modifier =
    this then SqueezeElement(effect)

@OptIn(ExperimentalComposeUiApi::class)
data class SqueezeElement(val onDraw: ContentDrawScope.(Float) -> Unit) :
    ModifierNodeElement<SqueezeNode>(inspectorInfo = {}) {
    override fun create(): SqueezeNode {
        return SqueezeNode(onDraw)
    }

    override fun update(node: SqueezeNode): SqueezeNode {
        node.onDraw = onDraw
        return node
    }
}

@OptIn(ExperimentalComposeUiApi::class)
class SqueezeNode(var onDraw: ContentDrawScope.(Float) -> Unit) :
    LayoutModifierNode, DrawModifierNode, Modifier.Node() {
    private var scale by mutableStateOf(1f)
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        return if (constraints.hasBoundedHeight) {
            val p = measurable.measure(
                constraints.copy(maxHeight = Constraints.Infinity)
            )
            val pHeight = p.height
            val myHeight = if (pHeight > constraints.maxHeight)
                constraints.minHeight
            else
                pHeight
            scale = myHeight.toFloat() / pHeight
            layout(p.width, myHeight) {
                p.place(0, 0)
            }
        } else {
            val p = measurable.measure(constraints)
            scale = 1f
            layout(p.width, p.height) {
                p.place(0, 0)
            }
        }
    }

    override fun ContentDrawScope.draw() {
        onDraw(scale)
    }
}

public fun ContentDrawScope.accordionSqueeze(
    scale: Float,
    countStrategy: AccordionFoldStrategy = AccordionFoldStrategy.Default
) {
    accordion(
        originalHeight = size.height,
        width = size.width,
        newHeight = size.height * scale,
        countStrategy = countStrategy
    )
}

public fun ContentDrawScope.scalingSqueeze(scale: Float) {
    scale(scaleY = scale, scaleX = 1f, pivot = Offset.Zero) {
        this@scalingSqueeze.drawContent()
    }
}

public fun ContentDrawScope.bottomVisibleSqueeze(scale: Float) {
    translate(top = size.height * scale - size.height) {
        this@bottomVisibleSqueeze.drawContent()
    }
}
