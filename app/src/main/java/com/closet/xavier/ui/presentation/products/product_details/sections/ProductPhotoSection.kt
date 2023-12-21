package com.closet.xavier.ui.presentation.products.product_details.sections

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.closet.xavier.ui.components.images.ProductImageBox
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductPhotoSection(
    modifier: Modifier = Modifier,
    navigateBack: () -> Boolean,
    images: List<String>
) {
    val pagerState = rememberPagerState(pageCount = {
        images.size
    })
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(
            state = pagerState, modifier = Modifier
                .align(Alignment.TopCenter)
        ) { index ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                ProductImageBox(
                    imageUrl = images[index],
                    modifier = Modifier.align(Alignment.TopCenter),
                    size = 300.dp,
                    contentScale = ContentScale.Fit
                )
            }
        }
        IconButton(
            onClick = { navigateBack() }, modifier = Modifier.align(
                Alignment.TopStart
            )
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back Icon",
                tint = MaterialTheme.colorScheme.onSurface
            )

        }
        LazyRow(
            content = {
                items(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface

                    Card(
                        modifier = Modifier
                            .padding(all = 5.dp)
                            .clickable {
                                scope.launch {
                                    pagerState.scrollToPage(page = iteration)
                                }
                            },
                        shape = RoundedCornerShape(12),
                        colors = CardDefaults.cardColors(
                            containerColor = color
                        )
                    ) {
                        ProductImageBox(
                            imageUrl = images[iteration],
                            size = 60.dp,
                            contentScale = ContentScale.Crop,
                        )
                    }

                }

            },
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        )
        IconButton(
            onClick = {  },
            modifier = Modifier.align(
                Alignment.TopEnd
            ),
        ) {
            Card(
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),

            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Back Icon",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
    }
}
