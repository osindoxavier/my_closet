package com.closet.xavier.ui.components.cart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalMall
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.closet.xavier.ui.components.text.BaseText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartIconBox(onCartClicked: () -> Unit, itemCount: Int = 0,modifier: Modifier=Modifier) {
    IconButton(onClick = onCartClicked) {
        Box(
            modifier = modifier
                .size(56.dp)
                .padding(all = 1.dp)
        ) {
            Icon(
                imageVector = Icons.Default.LocalMall,
                contentDescription = "cart icon",
                modifier = Modifier
                    .align(Alignment.Center)
            )

            if (itemCount >= 1) {

                Badge(
                    containerColor = MaterialTheme.colorScheme.error,
                    content = {
                        BaseText(
                            text = "$itemCount",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onError
                        )
                    },
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }

        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun CartIconBoxPreview() {
    CartIconBox(onCartClicked = {}, itemCount = 5)
}