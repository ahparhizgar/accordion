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
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Greeting("Accordion")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    BasicText(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting("Accordion")
}

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

