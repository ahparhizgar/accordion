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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
    val ratio by
    anim.animateFloat(1f, 0f, InfiniteRepeatableSpec(tween(2000), RepeatMode.Reverse))
    Column(Modifier.size(200.dp)) {
        BasicText(
            modifier = Modifier
                .accordion(scale = ratio)
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
