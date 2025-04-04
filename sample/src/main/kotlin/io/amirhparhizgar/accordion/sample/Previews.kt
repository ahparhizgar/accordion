package io.amirhparhizgar.accordion.sample

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.amirhparhizgar.accordion.AccordionFoldStrategy
import io.amirhparhizgar.accordion.accordion
import io.amirhparhizgar.accordion.accordionSqueeze
import io.amirhparhizgar.accordion.squeeze

@Preview
@Composable
fun FoldPreview() {
    var foldDegree by remember { mutableFloatStateOf(80f) }
    val anim = rememberInfiniteTransition()
    val ratio by
    anim.animateFloat(1f, 0f, InfiniteRepeatableSpec(tween(2000), RepeatMode.Reverse))
    Column(Modifier.size(200.dp)) {
        BasicText(
            modifier = Modifier
                .accordion(height = { ratio * it })
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
private fun SqueezePreview() {
    BasicText(
        modifier = Modifier
            .height(40.dp)
            .squeeze {
                accordionSqueeze(it)
//                scalingSqueeze(it)
//                bottomVisibleSqueeze(it)
            }
            .background(Color.White),
        text = "Collapsing section\n" +
                "Line 2\n" +
                "Line 3"
    )

}

@Preview
@Composable
private fun DraggableSample() {
    var diff by remember { mutableFloatStateOf(0f) }
    Box(
        Modifier
            .size(100.dp)
            .draggable(remember { DraggableState { diff += it } }, Orientation.Vertical)
    ) {
        Image(
            modifier = Modifier
                .accordion(height = { (it + diff).coerceIn(0f, it) },AccordionFoldStrategy.Fixed(4))
                .size(100.dp),
            painter = painterResource(R.mipmap.ic_launcher),
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun SqueezeSample() {
    Column(Modifier.height(200.dp)) {
        repeat(3) {
            Image(
                modifier = Modifier
                    .squeeze { accordionSqueeze(it) },
                painter = painterResource(R.mipmap.ic_launcher),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun ZeroHeightTest() {
    Box(Modifier.size(100.dp)) {
        Image(
            modifier = Modifier.accordion({ 0f }),
            painter = painterResource(R.mipmap.ic_launcher),
            contentDescription = null
        )
    }
}

@Preview(backgroundColor = 0xFFF, showBackground = true)
@Composable
private fun MinimalUsage() {
    BasicText(
        modifier = Modifier.accordion(height = { original -> original * 0.5f }),
        text = "1\n2\n3\n4",
        style = TextStyle.Default.copy(fontSize = 60.sp)
    )
}
