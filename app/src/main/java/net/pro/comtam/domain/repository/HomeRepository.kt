package net.pro.comtam.domain.repository

import net.pro.comtam.data.repository.Results
import net.pro.comtam.domain.model.Advertisement
import net.pro.comtam.domain.model.FoodItem
import net.pro.comtam.domain.model.Restaurant

interface HomeRepository {

    suspend fun getRestaurants() : Results<List<Restaurant>>
    suspend fun getAds(): Results<List<Advertisement>>
    suspend fun getFoodItems():Results<List<FoodItem>>
    fun getRestaurantFromName(name: String): Restaurant?

}