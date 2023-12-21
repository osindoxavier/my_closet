package com.closet.xavier.ui.components.images

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.closet.xavier.utils.shimmerBrush
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

@Composable
fun ProductImageBox(
    imageUrl: String,
    size: Dp = 50.dp,
    contentScale: ContentScale = ContentScale.Fit,
    modifier: Modifier = Modifier
) {
    val showShimmer = remember { mutableStateOf(true) }
    Box(
        modifier = modifier
            .size(size)
            .padding(all = 2.dp)
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            modifier = Modifier
                .background(
                    shimmerBrush(
                        targetValue = 1300f, showShimmer = showShimmer.value
                    )
                )
                .fillMaxSize()
                .align(Alignment.Center),
            onSuccess = {
                showShimmer.value = false
            },
            contentDescription = null,
            contentScale = contentScale,
        )
    }
}


