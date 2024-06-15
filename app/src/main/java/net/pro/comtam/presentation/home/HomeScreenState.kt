package net.pro.comtam.presentation.home

import net.pro.comtam.domain.model.Advertisement
import net.pro.comtam.domain.model.FoodItem
import net.pro.comtam.domain.model.Restaurant

data class HomeScreenState(
    val adsList: List<Advertisement> = emptyList(),
    val foodList: List<FoodItem> = emptyList(),
    val likedRestaurantList : List<Restaurant> = emptyList(),
    val restaurantList : List<Restaurant> = emptyList(),
)