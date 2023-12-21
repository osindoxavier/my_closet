package com.closet.xavier.ui.presentation.products.product_details.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.closet.xavier.data.firebase.model.product.Product

@Composable
fun ProductDetailsSection(product: Product) {
    val verticalSpace = 56.dp
    val horizontalSpace = 18.dp
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(topEndPercent = 5, topStartPercent = 5))
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(horizontal = horizontalSpace)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = verticalSpace, top = 16.dp)
                .verticalScroll(rememberScrollState())

        ) {
            ProductNameAndPriceSection(product = product)
            ProductDescriptionSection()
            ProductBrandAndStockSection()
            ProductDetailsSizeSection()
        }
        ProductDetailActionButtonSection(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun ProductBrandAndStockSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        val text = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    color = MaterialTheme.colorScheme.onSecondary,
                )
            ) {
                append("Stock: ")
            }
            withStyle(
                SpanStyle(
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                append("In stock")
            }
        }
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.3.sp,
            textAlign = TextAlign.Start
        )
    }
}






