/**
 *
 *	MIT License
 *
 *	Copyright (c) 2023 Gautam Hazarika
 *
 *	Permission is hereby granted, free of charge, to any person obtaining a copy
 *	of this software and associated documentation files (the "Software"), to deal
 *	in the Software without restriction, including without limitation the rights
 *	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *	copies of the Software, and to permit persons to whom the Software is
 *	furnished to do so, subject to the following conditions:
 *
 *	The above copyright notice and this permission notice shall be included in all
 *	copies or substantial portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *	SOFTWARE.
 *
 **/

package net.pro.comtam.presentation.history

import android.app.Activity
import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import net.pro.comtam.domain.model.Restaurant
import net.pro.comtam.presentation.components.RestaurantCard
import net.pro.comtam.presentation.components.SearchBar
import net.pro.comtam.utils.Screen
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import net.pro.comtam.R
import net.pro.comtam.ui.theme.backgroundLight

@ExperimentalPagerApi
@Composable
fun History(
    viewModel: HistoryViewModel = hiltViewModel(),
    navHostController: NavHostController
) {

    val state by viewModel.likedRestaurants

    val pagerState = rememberPagerState()

    val context = LocalContext.current as Activity
    context.window.statusBarColor = backgroundLight.toArgb()
    context.window.navigationBarColor = backgroundLight.toArgb()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundLight)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            SearchBar()
            Spacer(modifier = Modifier.height(8.dp))

            Tabs(pagerState = pagerState)
        }
        TabsContent(
            pagerState = pagerState, state.likedRestaurantList,
            onClick = {
                viewModel.onEvent(HistoryEvent.SelectRestaurant(it) {
                    navHostController.navigate(Screen.RestaurantDetails.route)
                })
            }
        )

    }
}


@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState) {
    val list = listOf("History", "Favourites")
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 0.dp, 75.dp, 0.dp),
        backgroundColor = Color.Transparent,
        contentColor = Color.White,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 0.dp,
                color = Color.White
            )
        },

        ) {
        list.forEachIndexed { index, _ ->
            Tab(
                text = {
                    Text(
                        text = list[index],
                        fontSize = 16.sp,
                        fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.Light,
                        color = if (pagerState.currentPage == index) Color.White else Color.LightGray,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Left
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }


            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TabsContent(
    pagerState: PagerState,
    list: List<Restaurant>,
    onClick: (restaurant: Restaurant) -> Unit
) {

    HorizontalPager(count = 2, state = pagerState) { page ->
        when (page) {
            0 -> HistorySection()
            1 -> FavouritesSection(list = list, onClick)
        }
    }

}

@Composable
fun FavouritesSection(
    list: List<Restaurant>,
    onClick: (restaurant: Restaurant) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)

    ) {
        if (list.isNotEmpty()) {
            LazyColumn {

                items(list.size) {
                    RestaurantCard(
                        restaurant = list[it],
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                onClick(list[it])
                            }
                    )
                }

            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ) {
                Text(text = stringResource(R.string.empty))
            }
        }
    }
}

@Composable
fun HistorySection() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            elevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column() {
                        Text(text = "Fish n Rolls", fontWeight = FontWeight.Bold)
                        Text(
                            text = "Tezpur",
                            modifier = Modifier.alpha(0.5f),
                        )

                        Text(
                            text = "13 Aug 2022, 11:12 PM",
                            modifier = Modifier.alpha(0.5f),
                        )

                    }
                    Column(
                        horizontalAlignment = Alignment.End,
                    ) {
                        Text(
                            text = "$7.90",
                        )
                    }
                }
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 8.dp),
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(id = R.drawable.ic_non_veg),
                            contentDescription = "Non-Vegetarian"
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Text(text = "Chinese Shawarma\nCombo (1)", maxLines = 2)
                    }
                    Row {
                        Button(onClick = { /*TODO*/ }) {
                            Text(text = "Reorder")
                        }
                    }

                }

                Spacer(modifier = Modifier.height(8.dp))


                Row(
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Your rating for delivery",
                                modifier = Modifier.alpha(0.5f),
                            )
                            Text(
                                text = "Your rating for food",
                                modifier = Modifier.alpha(0.5f),
                            )

                        }

                        Column {
                            Row {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = "Rating",
                                    tint = Color(0xFFFF7A00)
                                )
                                Text(text = "5")
                            }
                            Row {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = "Rating",
                                    tint = Color(0xFFFF7A00)
                                )
                                Text(text = "5")
                            }
                        }

                    }




                    Spacer(modifier = Modifier.width(16.dp))


                    Row() {
                        Text(
                            text = "Delivered",
                            modifier = Modifier.alpha(0.5f),
                        )
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Rating",
                            tint = MaterialTheme.colors.primary
                        )

                    }
                }
            }

        }
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            elevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column() {
                        Text(text = "Fish n Rolls", fontWeight = FontWeight.Bold)
                        Text(
                            text = "Tezpur",
                            modifier = Modifier.alpha(0.5f),
                        )

                        Text(
                            text = "13 Aug 2022, 11:12 PM",
                            modifier = Modifier.alpha(0.5f),
                        )

                    }
                    Column(
                        horizontalAlignment = Alignment.End,
                    ) {
                        Text(
                            text = "$7.90",
                        )
                    }
                }
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 8.dp),
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(id = R.drawable.ic_non_veg),
                            contentDescription = "Non-Vegetarian"
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Text(text = "Chinese Shawarma\nCombo (1)", maxLines = 2)
                    }
                    Row {
                        Button(onClick = { /*TODO*/ }) {
                            Text(text = "Reorder")
                        }
                    }

                }

                Spacer(modifier = Modifier.height(8.dp))


                Row(
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Row {
                        Text(
                            text = "Rate Order",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.primary,
                            fontSize = 16.sp,
                            modifier = Modifier.clickable { }
                        )
                    }




                    Spacer(modifier = Modifier.width(16.dp))


                    Row() {
                        Text(
                            text = "Delivered",
                            modifier = Modifier.alpha(0.5f),
                        )
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Rating",
                            tint = MaterialTheme.colors.primary
                        )

                    }
                }
            }

        }

    }
}
