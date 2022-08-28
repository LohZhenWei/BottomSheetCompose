package com.loh.bottomsheetcompose

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.loh.bottomsheetcompose.ui.theme.BottomSheetComposeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sheetType: MutableState<SheetType> = mutableStateOf(SheetType.Close)

        setContent {
            val scope = rememberCoroutineScope()

           /* val sheetType: MutableState<SheetType> = remember {
                mutableStateOf(SheetType.Close)
            }*/

            BottomSheetComposeTheme {
                BottomSheetLayoutScreen(sheetType.value) { sheetState ->
                    Scaffold(topBar = { TopBar() }) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                        ) {
                            Button(onClick = {
                                sheetType.value = SheetType.SheetA
                                scope.openBottomSheet(sheetState)
                            }) {
                                Text(text = "Sheet A ")
                            }
                            Button(onClick = {
                                sheetType.value = SheetType.SheetB
                                scope.openBottomSheet(sheetState)

                            }) {
                                Text(text = "Sheet B ")
                            }
                            Button(onClick = {
                                sheetType.value = SheetType.SheetC
                                scope.openBottomSheet(sheetState)
                            }) {
                                Text(text = "Sheet C ")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun CoroutineScope.openBottomSheet(sheetState: ModalBottomSheetState){
        this.launch {
            if (sheetState.isVisible) {
                sheetState.hide()
            } else
                sheetState.show()
        }
    }
}

@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(text = "App bar", fontSize = 18.sp) },
        contentColor = Color.White
    )
}

@ExperimentalMaterialApi
@Composable
fun BottomSheetLayoutScreen(
    sheetType: SheetType,
    content: @Composable (sheetState: ModalBottomSheetState) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    ModalBottomSheetLayout(
        sheetContent = {
            BottomSheetContent(sheetType)
        },
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = Color.Green,
    ) {
        content(sheetState)
    }
}

@ExperimentalMaterialApi
@Composable
fun BottomSheetScaffoldTest(
    sheetType: SheetType,
    modifier: Modifier = Modifier,
    content: @Composable (sheetState: BottomSheetState) -> Unit

) {
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            BottomSheetContent(sheetType)
        },
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = Color.Green,
        sheetPeekHeight = 0.dp
    ) {
        content(sheetState)
    }
}

@Composable
fun BottomSheetContent(sheetType: SheetType) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = sheetType.type,
            fontSize = 30.sp
        )
        Text(
            text = sheetType.type,
            fontSize = 30.sp
        )
    }
}

sealed class SheetType(val type: String) {
    object SheetA : SheetType("Sheet A")
    object SheetB : SheetType("Sheet B")
    object SheetC : SheetType("Sheet C")
    object Close : SheetType("Sheet C")
}