package net.pro.comtam.data.repository


import net.pro.comtam.data.data_source.adList
import net.pro.comtam.data.data_source.recommendedList
import net.pro.comtam.data.data_source.restaurantList
import net.pro.comtam.domain.model.Advertisement
import net.pro.comtam.domain.model.FoodItem
import net.pro.comtam.domain.model.Restaurant
import net.pro.comtam.domain.repository.HomeRepository

class HomeRepositoryImpl() : HomeRepository {

    override suspend fun getRestaurants(): Results<List<Restaurant>> {
        return Results.Success(restaurantList)
    }

    override suspend fun getAds(): Results<List<Advertisement>> {
        return Results.Success(adList)
    }

    override suspend fun getFoodItems(): Results<List<FoodItem>> {
        return Results.Success(recommendedList)
    }

    override fun getRestaurantFromName(name: String): Restaurant? {
        return restaurantList.find {
            it.name == name
        }
    }
}