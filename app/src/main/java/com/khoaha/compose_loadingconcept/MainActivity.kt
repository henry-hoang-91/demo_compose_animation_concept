package com.khoaha.compose_loadingconcept

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.khoaha.compose_loadingconcept.ui.theme.Compose_LoadingConceptTheme

class MainActivity : ComponentActivity() {

    private val loadingLD = MutableLiveData<Boolean>()
    private val successLD = MutableLiveData<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_LoadingConceptTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
//                    color = Color.Red
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        PlayState(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(45.dp),
                            loadingLD,
                            successLD
                        )
                        Row {
                            Button(onClick = {
                                loadingLD.postValue(true)
                                successLD.postValue(false)
                            }) {
                                Text(text = "Start Loading")
                            }
                            Button(onClick = {
                                loadingLD.postValue(false)
                                successLD.postValue(true)
                            }) {
                                Text(text = "Success")
                            }
                            Button(onClick = {
                                loadingLD.postValue(false)
                                successLD.postValue(false)
                            }) {
                                Text(text = "Reset")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ViewLoadingState(modifier: Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        color = Color.Blue
    ) {
        BoxWithConstraints(
            contentAlignment = Alignment.Center,
            modifier = Modifier.height(20.dp)
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Compose_LoadingConceptTheme {
        ViewLoadingState(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        )
    }
}