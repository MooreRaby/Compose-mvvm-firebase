package net.pro.comtam.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pro.comtam.data.repository.Results
import net.pro.comtam.domain.repository.HomeRepository
import net.pro.comtam.domain.repository.UserDataRepository
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val userDataRepository: UserDataRepository,

    ) : ViewModel() {

    private val _homeScreenState = mutableStateOf(
        HomeScreenState()
    )
    val homeScreenState: State<HomeScreenState> = _homeScreenState

    init {
        viewModelScope.launch {
            when (val result = repository.getAds()) {
                is Results.Success -> _homeScreenState.value = homeScreenState.value.copy(
                    adsList = result.data
                )
                is Results.Error -> {
                }
            }

            when (val result = repository.getFoodItems()) {
                is Results.Success -> _homeScreenState.value = homeScreenState.value.copy(
                    foodList = result.data
                )
                is Results.Error -> {

                }
            }

            when (val result = repository.getRestaurants()) {
                is Results.Success -> _homeScreenState.value = homeScreenState.value.copy(
                    restaurantList = result.data,
                )
                is Results.Error -> {

                }
            }

            userDataRepository.getLikedRestaurants().collect {
                _homeScreenState.value = homeScreenState.value.copy(
                    likedRestaurantList = it
                )

            }

        }


    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.SelectRestaurant -> {
                viewModelScope.launch {
                    userDataRepository.setRestaurant(event.restaurant)
                    event.onClick()
                }
            }
        }
    }


}