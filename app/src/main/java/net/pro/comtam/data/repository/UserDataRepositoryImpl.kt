package net.pro.comtam.data.repository


import net.pro.comtam.data.data_source.cartList
import net.pro.comtam.data.data_source.favouriteList
import net.pro.comtam.domain.model.CartItem
import net.pro.comtam.domain.model.Restaurant
import net.pro.comtam.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow



class UserDataRepositoryImpl : UserDataRepository {

    private val menuList = mutableSetOf<CartItem>()
    private val cartListItems = cartList.toMutableList()
    private var currentRestaurant: Restaurant? = null

    override suspend fun setRestaurant(restaurant: Restaurant) {
        currentRestaurant = restaurant
        restaurant.menu.forEach {
            menuList.add(CartItem(it, 0))
        }
    }

    override suspend fun getSavedRestaurant(): Flow<Restaurant> = flow {
        if (currentRestaurant != null) {
            emit(currentRestaurant!!)
        }
    }

    override suspend fun getMenuItems(): Flow<List<CartItem>> = flow {
        emit(menuList.toMutableList())
    }

    var list = favouriteList.toMutableList()


    override suspend fun getLikedRestaurants(): Flow<List<Restaurant>> = flow {
        emit(list)
    }

    override suspend fun isRestaurantLiked(restaurant: Restaurant): Flow<Boolean> = flow {
        emit(list.contains(restaurant))
    }

    override suspend fun getCartItems(): Flow<List<CartItem>> = flow {
        emit(cartListItems)
    }
}