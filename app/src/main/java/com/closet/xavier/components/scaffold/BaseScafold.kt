package com.closet.xavier.components.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScaffold(
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        content = content
    )
}