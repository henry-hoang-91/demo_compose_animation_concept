package com.khoaha.compose_loadingconcept

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.delay

@Composable
fun PlayState(
    modifier: Modifier,
    loadingLD: MutableLiveData<Boolean>,
    successLD: MutableLiveData<Boolean>
) {

    val LOADING_DURATION = 1000
    val SUCCESS_DURATION = 300

    val playLoadingAnim by loadingLD.observeAsState()
    val playSuccess by successLD.observeAsState()

    val loadingValue by animateFloatAsState(
        targetValue = if (playLoadingAnim == true) 1f else 0f,
        animationSpec = keyframes {
            durationMillis = if (playLoadingAnim == true) LOADING_DURATION else 300
        }
    )

    val successValue by animateFloatAsState(
        targetValue = if (playSuccess == true) 1f else 0f,
        animationSpec = keyframes {
            durationMillis = SUCCESS_DURATION
        }
    )

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
    ) {
        LoadingState(modifier, loadingValue)
        SuccessState(modifier, playSuccess ?: false, successValue)
    }

//    LaunchedEffect(key1 = Unit) {
//        delay(1000)
//        playLoadingAnim = true
//    }
}

@Composable
private fun SuccessState(modifier: Modifier, playSuccess: Boolean, alpha: Float) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    Box(
        modifier = modifier
            .graphicsLayer(alpha = alpha)
            .background(Color.Green.copy(alpha = 0.2f))
    ) {
        ConstraintLayout(modifier = modifier) {
            val (icon, message) = createRefs()

            AnimatedVisibility(
                modifier = Modifier
                    .constrainAs(message) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .onSizeChanged {
                        size = it
                    },
                visible = playSuccess,
                enter = fadeIn(animationSpec = tween(durationMillis = 1000, delayMillis = 500)),
                exit = fadeOut(),
            ) {
                Text(
                    text = "You're all set!",
                    style = TextStyle(
                        color = Color.Green,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
            }

            AnimatedVisibility(
                modifier = Modifier.constrainAs(icon) {
                    top.linkTo(message.top)
                    end.linkTo(message.start, margin = 8.dp)
                    bottom.linkTo(message.bottom)
                },
                visible = playSuccess,
                enter = slideInHorizontally(
                    animationSpec = tween(
                        durationMillis = 300,
                        delayMillis = 700
                    )
                ) { fullWidth ->
                    size.width / 2 + fullWidth
                } + fadeIn(),
                exit = slideOutHorizontally(animationSpec = spring(stiffness = Spring.StiffnessHigh)) {
                    // Overwrites the ending position of the slide-out to 200 (pixels) to the right
                    200
                } + fadeOut(),
            ) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Success icon",
                    tint = Color.Green
                )
            }
        }


    }
}

@Composable
private fun LoadingState(modifier: Modifier, alpha: Float) {
    Box(
        modifier = modifier
            .graphicsLayer(alpha = alpha)
            .background(Color.Blue),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color.White)
    }
}