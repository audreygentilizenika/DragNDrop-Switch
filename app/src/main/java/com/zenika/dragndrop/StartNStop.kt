package com.zenika.dragndrop

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.zenika.dragndrop.ui.theme.DragnDropTheme
import kotlin.math.roundToInt

private const val MIN_OFFSET = 0f

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun StartNStop() {
    var offsetX by remember { mutableStateOf(0f) }
    val text by remember { derivedStateOf { if (offsetX < 275) "Start" else "Stop" } }
    var maxOffset by remember {
        mutableStateOf(-1f)
    }
    var arrowSize by remember {
        mutableStateOf(0f)
    }
    val innerPadding = 8.dp
    val density = LocalDensity.current

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(100.dp)
                .width(300.dp)
                .padding(20.dp)
                .clip(shape = RoundedCornerShape(100.dp))
                .drawBehind {
                    drawRect(
                        Color(
                            255 - offsetX.roundToInt() / 3,
                            offsetX.roundToInt() / 3,
                            0
                        )
                    )
                }
                .onSizeChanged {
                    maxOffset =
                        it.width.toFloat() - arrowSize - with(density) { 2 * innerPadding.toPx() }
                }
        ) {
            Box(
                modifier = Modifier
                    .offset { IntOffset(offsetX.roundToInt(), 0) }
                    .size(60.dp)
                    .padding(innerPadding)
                    .clip(shape = CircleShape)
                    .background(Color.Gray)
                    .draggable(
                        orientation = Orientation.Horizontal,
                        state = rememberDraggableState { delta ->
                            if (offsetX + delta in MIN_OFFSET..maxOffset) offsetX += delta
                        }
                    )
                    .onSizeChanged { arrowSize = it.width.toFloat() }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "arrow",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .graphicsLayer { rotationZ = offsetX / maxOffset * 180 }
                )
            }
            AnimatedContent(
                targetState = text,
                label = "text",
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(
                    text = it,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
fun StartNStopPreview() {
    DragnDropTheme {
        StartNStop()
    }
}