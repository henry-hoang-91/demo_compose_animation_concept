package com.khoaha.compose_loadingconcept

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SimpleAnimation_Demo() {
    var visible by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically {
            // Slide in from 40 dp from the top.
            with(density) { -40.dp.roundToPx() }
        } + expandVertically(
            // Expand from the top.
            expandFrom = Alignment.Top
        ) + fadeIn(
            // Fade in with the initial alpha of 0.3f.
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        Text(
            "Hello World People",
            Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
    }

    LaunchedEffect(key1 = Unit) {
        delay(2000)
        visible = true
    }
}

@Composable
fun ObserverAnimationState() {
    // Create a MutableTransitionState<Boolean> for the AnimatedVisibility.
    val state = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }
    Column {
        AnimatedVisibility(visibleState = state) {
            Text(text = "Hello, world!")
        }

        // Use the MutableTransitionState to know the current animation state
        // of the AnimatedVisibility.
        Text(
            text = when {
                state.isIdle && state.currentState -> "Visible"
                !state.isIdle && state.currentState -> "Disappearing"
                state.isIdle && !state.currentState -> "Invisible"
                else -> "Appearing"
            }
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun EnterAndExitChildren() {
    var visible by remember { mutableStateOf(false) }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        // Fade in/out the background and the foreground.
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
        ) {
            Box(
                Modifier
                    .align(Alignment.Center)
                    .animateEnterExit(
                        // Slide in/out the inner box.
                        enter = slideInVertically(),
                        exit = slideOutVertically()
                    )
                    .sizeIn(minWidth = 256.dp, minHeight = 64.dp)
                    .background(Color.Red)
            ) {
                Button(onClick = { }) {
                    Text("Button")
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        delay(2000)
        visible = true
        delay(1000)
        visible = false
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimateContent() {
    Row {
        var count by remember { mutableStateOf(0) }
        Button(onClick = { count++ }) {
            Text("Add")
        }
        AnimatedContent(targetState = count) { targetCount ->
            // Make sure to use `targetCount`, not `count`.
            Text(text = "Count: $targetCount")
        }
    }
}

@Composable
fun CrossFade() {
    var currentPage by remember { mutableStateOf("A") }
    Crossfade(targetState = currentPage) { screen ->
        when (screen) {
            "A" -> Text("Page A")
            "B" -> Text("Page B")
        }
    }

    LaunchedEffect(key1 = Unit) {
        delay(2000)
        currentPage = "B"
        delay(1000)
        currentPage = "A"
    }
}

@Composable
fun AnimateContentSize() {
    var message by remember { mutableStateOf("Hello") }
    Column {
        Box(
            modifier = Modifier
                .background(Color.Red)
                .animateContentSize()
        ) { Text(text = message) }
    }

    LaunchedEffect(key1 = Unit) {
        delay(2000)
        message = "Hello World"
        delay(1000)
        message = "Hello World Java"
        delay(2000)
        message = "Hello"
    }
}