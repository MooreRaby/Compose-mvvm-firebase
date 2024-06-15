package net.pro.comtam.presentation.home


import android.app.Activity
import android.graphics.Shader

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import net.pro.comtam.R
import net.pro.comtam.domain.model.Advertisement
import net.pro.comtam.domain.model.FoodItem
import net.pro.comtam.presentation.components.RestaurantCard
import net.pro.comtam.ui.theme.backgroundDark
import net.pro.comtam.ui.theme.backgroundLight
import net.pro.comtam.ui.theme.isSelectItemNav
import net.pro.comtam.utils.Screen

@Composable
fun Home(
    scrollState: LazyListState,
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current as Activity
    context.window.statusBarColor = backgroundLight.toArgb()
    context.window.navigationBarColor = backgroundLight.toArgb()

    val homeScreenState by viewModel.homeScreenState

    val largeRadialGradient = object : ShaderBrush() {
        override fun createShader(size: Size): Shader {
            val biggerDimension = maxOf(size.height, size.width)
            return RadialGradientShader(
                colors = listOf( isSelectItemNav, backgroundLight),
                center = size.center,
                radius = biggerDimension / 2f,
                colorStops = listOf(0f, 1f)
            )
        }
    }

    LazyColumn(
        modifier =
        Modifier
            .fillMaxWidth(),
        state = scrollState,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        item {
            Column(modifier = Modifier
                .background(largeRadialGradient)
                .fillMaxWidth()
                .height(280.dp)) {
                TopSection()
                AdCard(
                    ad = Advertisement(
                        title = "Special Discount",
                        subTitle = "Get 50% off on your first order!",
                        color = Color(0xFFE0F7FA), // Example color
                        image = R.drawable.pizza // Example drawable resource
                    )
                )
            }
        }

        item {
            Column(modifier = Modifier.background(color = backgroundDark)) {
                Spacer(modifier = Modifier.height(8.dp))
                RecommendedSection(homeScreenState.foodList)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        items(
            homeScreenState.restaurantList.size,
        ) {
            Column(modifier = Modifier.background(color = backgroundDark)) {
                RestaurantCard(
                    restaurant = homeScreenState.restaurantList[it],
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            viewModel.onEvent(HomeScreenEvent.SelectRestaurant(homeScreenState.restaurantList[it]) {
                                navController.navigate(Screen.RestaurantDetails.route)
                            })
                        }
                )
            }

        }

    }

}

@Composable
fun AdCard(
    ad: Advertisement
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        backgroundColor = ad.color,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Set a fixed height for the card
                .clip(RoundedCornerShape(12.dp))
        ) {
            Image(
                painter = painterResource(id = ad.image),
                contentDescription = null, // No need for content description as it is background
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
            )
            // Overlay a semi-transparent black color
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.5f)) // Adjust alpha to control darkness
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = ad.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = ad.subTitle,
                    fontWeight = FontWeight.Light,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


@Composable
fun RecommendedSection(list: List<FoodItem>) {
    LazyRow(
        Modifier
            .background(color = backgroundDark)
            .fillMaxWidth(),
        contentPadding = PaddingValues(
            start = 8.dp,
            end = 8.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(list.size) {
            RecommendedCard(foodItem = list[it])
        }
    }
}

@Composable
fun RecommendedCard(
    foodItem: FoodItem
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = foodItem.name,
            color = Color.White
        )
        Image(
            painter = painterResource(id = foodItem.image),
            contentDescription = stringResource(R.string.restaurant),
            modifier = Modifier
                .size(80.dp)
                .padding(3.dp, 5.dp, 0.dp, 0.dp)
                .shadow(elevation = 0.dp, shape = RoundedCornerShape(20.dp), clip = true),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun TopSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Absolute.Left,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.logo_comtum),
            contentDescription = stringResource(id = R.string.display_picture)
        )


        Row(modifier = Modifier.padding(10.dp)) {
            Row {
                Text(
                    text = "Cum tứm đim",
                    color = Color.White,
                    fontStyle = FontStyle.Normal
                )
            }
        }

    }

}

