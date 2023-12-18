package com.closet.xavier.ui.presentation.store.product_details.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.closet.xavier.ui.components.text.BaseText

@Composable
fun ProductDetailsSizeSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        BaseText(
            text = "Size",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Black
        )
        Spacer(modifier = Modifier.height(4.dp))
        LazyRow(content = {
            items(5) {
                SizeAssistChipElement(size = it.toString())
            }
        }, modifier = Modifier.fillMaxWidth())

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SizeAssistChipElement(size: String) {
    AssistChip(
        onClick = {

        },
        label = { BaseText(size) },
    )
}