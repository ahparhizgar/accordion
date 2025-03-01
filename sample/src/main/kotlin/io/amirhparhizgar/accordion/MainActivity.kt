package io.amirhparhizgar.accordion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import kotlin.math.max

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column(Modifier.padding(WindowInsets.systemBars.asPaddingValues())) {
                FoldPreview()
            }
        }
    }
}

@Preview
@Composable
private fun FoldPreview() {
    var foldDegree by remember { mutableStateOf(80f) }
    val anim = rememberInfiniteTransition()
    val degreeAnim by
    anim.animateFloat(0f, 90f, InfiniteRepeatableSpec(tween(2000), RepeatMode.Reverse))
    Column(Modifier.size(200.dp)) {
        BasicText(
            modifier = Modifier
                .accordion(foldDegree = degreeAnim % 91, n = 1)
                .background(Color.White)
                .clickable {
                    foldDegree += 10
                },
            text = "AAAAAAAAAAAA\n" +
                    "BBBBBBBBBBBB\n" +
                    "CCCCCCCCCCCC\n" +
                    "DDDDDDDDDDDD"
        )
        BasicText(text = "Next layout")
    }
}

@Preview
@Composable
private fun Collapse() {
    var scale by remember { mutableFloatStateOf(1f) }
    BasicText(
        modifier = Modifier
//            .height(120.dp)
            .layout { measurable, constraints ->
                if (constraints.hasBoundedHeight) {
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
            .drawWithContent {
                scale(scaleX = 1f, scaleY = scale, pivot = Offset.Zero) {
                    this@drawWithContent.drawContent()
                }
            }
            .background(Color.White),
        text = "Collapsing section\n" +
                "Line 2\n" +
                "Line 3"
    )

}