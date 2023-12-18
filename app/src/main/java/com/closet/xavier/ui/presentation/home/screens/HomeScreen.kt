package com.closet.xavier.ui.presentation.home.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.closet.xavier.R.*
import com.closet.xavier.data.firebase.model.authentication.UserProfile
import com.closet.xavier.data.firebase.model.brand.Brand
import com.closet.xavier.data.firebase.model.product.Product
import com.closet.xavier.ui.components.BottomNavigationBar
import com.closet.xavier.ui.components.images.ProductImageBox
import com.closet.xavier.ui.components.scaffold.BaseScaffold
import com.closet.xavier.ui.components.text.BaseText
import com.closet.xavier.ui.presentation.home.sections.HomeSalutationSection
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    userProfileState: State<UserProfile>,
    brandsState: State<List<Brand>>,
    popularProductState: State<List<Product>>,
    onFavClicked: (Product) -> Unit,
    onProductClicked: (Product) -> Unit,
    navController: NavController
) {
    BaseScaffold(
        content = {
            HomeScreenContent(
                modifier = Modifier.padding(it),
                userProfile = userProfileState.value,
                brands = brandsState.value,
                popularProducts = popularProductState.value,
                onFavClicked = onFavClicked,
                onProductClicked = onProductClicked
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    )
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    userProfile: UserProfile,
    brands: List<Brand>?,
    popularProducts: List<Product>,
    onFavClicked: (Product) -> Unit,
    onProductClicked: (Product) -> Unit
) {
    val verticalSpace = 18.dp
    val horizontalSpace = 16.dp

    val username = remember {
        mutableStateOf("")
    }

    username.value = userProfile.firstName.lowercase()
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(vertical = verticalSpace, horizontal = horizontalSpace)
            .verticalScroll(rememberScrollState())
    ) {
        HomeSalutationSection(username.value, itemCount = 10)
        SearchBar()
        CategorySection(brands = brands)
        BannerSection()
        PopularShoesSection(
            popularProducts = popularProducts,
            onFavClicked = onFavClicked,
            onProductClicked = onProductClicked,
            userId = userProfile.uid
        )
        RecentProductSection()
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {


}


@Composable
fun CategorySection(brands: List<Brand>?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BaseText(
                text = "Brands",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Black
            )
            TextButton(onClick = { /*TODO*/ }) {
                BaseText(
                    text = "View All",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Black
                )
            }
        }
        LazyRow(
            content = {
                if (brands != null) {
                    items(brands) { item ->
                        CategoryElement(brand = item)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        )


    }
}


@Composable
fun CategoryElement(
    brand: Brand,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val painter = rememberAsyncImagePainter(brand.image)
    Column(
        modifier = modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Box(modifier = Modifier.padding(all = 8.dp)) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.Center)
                )

            }

        }
        BaseText(
            text = brand.name.lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
            modifier = Modifier.paddingFromBaseline(top = 24.dp, bottom = 8.dp),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


@Composable
fun BannerSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(vertical = 8.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = drawable.promo),
                    contentDescription = "promo banner",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

        }
    }
}


@Composable
fun PopularShoesSection(
    modifier: Modifier = Modifier,
    popularProducts: List<Product>,
    onFavClicked: (Product) -> Unit,
    onProductClicked: (Product) -> Unit,
    userId: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        BaseText(
            text = "Best Seller",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Black
        )
        Spacer(modifier = Modifier.height(5.dp))
        LazyRow(
            content = {
                items(popularProducts) { item ->
                    FavoriteCollectionCard(
                        product = item,
                        onFavClicked = onFavClicked,
                        onProductClicked = onProductClicked,
                        userId = userId
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(all = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        )
    }
}


@Composable
fun FavoriteCollectionCard(
    modifier: Modifier = Modifier,
    product: Product,
    onFavClicked: (Product) -> Unit,
    onProductClicked: (Product) -> Unit,
    userId: String
) {
    val isFavourite = remember {
        mutableStateOf(false)
    }

    val list = product.favorites as? List<String>

    if (list != null) {
        isFavourite.value = list.any { it.contains(userId, ignoreCase = true) }
    }
    val favIcon = if (isFavourite.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder
    val iconTint =
        if (isFavourite.value) MaterialTheme.colorScheme.primary else Color.Black.copy(0.5f)

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Box(
            modifier = Modifier.wrapContentSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                val showShimmer = remember { mutableStateOf(true) }

                ProductImageBox(
                    imageUrl = product.image,
                    size = 202.dp,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .clickable { onProductClicked(product) },
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp)
                        .clickable { onProductClicked(product) }
                ) {
                    BaseText(
                        text = product.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    BaseText(
                        text = "Ksh ${product.price}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            IconButton(
                onClick = { onFavClicked(product) },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(imageVector = favIcon, contentDescription = "Favourite Icon", tint = iconTint)
            }
        }
    }
}

@Composable
fun ProductCard(
    modifier: Modifier = Modifier
) {

    Card(modifier = modifier) {
        Box(
            modifier = Modifier.wrapContentSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(drawable.retro),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(200.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.Start
                ) {
                    BaseText(
                        text = "Nike Retro",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    BaseText(
                        text = "Ksh 15,000",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun RecentProductSection(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        BaseText(
            text = "Recent Product",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier.height(168.dp)
        ) {
            items(10) {
                RecentProducts()
            }
        }
    }
}

@Composable
fun RecentProducts() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(255.dp)
        ) {
            Image(
                painter = painterResource(drawable.retro),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(80.dp)
            )
            Column(modifier = Modifier.padding(all = 16.dp)) {
                BaseText(
                    text = "Nike Retro",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


