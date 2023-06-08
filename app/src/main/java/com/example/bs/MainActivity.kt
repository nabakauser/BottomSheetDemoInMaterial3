package com.example.bs

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val viewModel: BottomSheetViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BottomSheetDemo(viewModel = viewModel)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDemo(
    viewModel: BottomSheetViewModel,

) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false
        )
    )
    val isLoading by viewModel.isLoading.collectAsState()
    BottomSheetScaffold(
        sheetPeekHeight = 0.dp,
        sheetContent = {
            BottomSheetContent(isLoading = isLoading)
        },
        scaffoldState = scaffoldState
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Button(
                onClick = {
                    scope.launch {
                        viewModel.setLoadingState()
                        if (scaffoldState.bottomSheetState.isVisible) {
                            scaffoldState.bottomSheetState.hide()
                        } else {
                            Log.d("BState", "BottomSheetDemo: expanded")
                            scaffoldState.bottomSheetState.expand()
                        }
                    }
                }
            ) {
                Text(text = "Open Bottom Sheet")
            }
        }
    }
}

@Composable
fun BottomSheetContent(isLoading: Boolean) {

    Column (
        modifier = Modifier.fillMaxSize(.7f)
            ) {
        Text(text = "Bottom Sheet")
        CircularProgressBar(isDisplayed = isLoading)
    }
}


@Composable
fun CircularProgressBar(
    isDisplayed: Boolean,
) {
    if (isDisplayed) {
        Row(
            Modifier
                .size(100.dp)
                .padding(20.dp), horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(color = Color.Black)
        }
    }
}