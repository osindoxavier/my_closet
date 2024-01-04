package com.closet.xavier.ui.components.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.closet.xavier.ui.components.dialog.LoadingDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    floatingActionButton: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    isLoading: Boolean = false
) {
    if (isLoading) {
        LoadingDialog()
    }

    Scaffold(
        modifier = modifier,
        topBar = topBar,
        content = content,
        floatingActionButtonPosition=floatingActionButtonPosition,
        floatingActionButton = floatingActionButton,
        bottomBar = bottomBar
    )
}