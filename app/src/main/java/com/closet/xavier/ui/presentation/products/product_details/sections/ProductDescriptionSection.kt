package com.closet.xavier.ui.presentation.products.product_details.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.closet.xavier.ui.components.text.ExpandableText

@Composable
fun ProductDescriptionSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(bottom = 8.dp)) {
        val description =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec suscipit elit diam, vel efficitur arcu luctus eu. Donec aliquet vitae lectus eget auctor. Suspendisse justo arcu, consequat ut pretium sit amet, ullamcorper sed metus. Duis blandit tincidunt nibh, non malesuada eros placerat nec. Sed vel pellentesque dui. Vestibulum condimentum nunc a tortor varius volutpat. In at enim sed nisl tempus sagittis. Ut a enim bibendum, condimentum erat lobortis, maximus tortor. Suspendisse vitae ante sem. Curabitur varius lectus sit amet elit fermentum mattis. Maecenas bibendum ut quam vel aliquam. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Fusce maximus metus ac massa pulvinar sodales. Fusce suscipit eget nunc ut volutpat. Praesent pellentesque dapibus ante, eu dignissim augue pulvinar vel. Aliquam efficitur imperdiet laoreet."
        ExpandableText(
            text = description,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        Divider()
    }
}