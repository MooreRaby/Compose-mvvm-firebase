package net.pro.comtam.presentation.profile


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import net.pro.comtam.domain.model.Address
import net.pro.comtam.domain.model.User
import net.pro.comtam.domain.repository.LoginRepository
import net.pro.comtam.domain.repository.ProfileRepository
import net.pro.comtam.presentation.signup.UiEvent
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _user = mutableStateOf(User())
    val user: State<User> = _user

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    val loginState = loginRepository.loginState

    init {
        viewModelScope.launch {
            profileRepository.userData.collect { user ->
                user?.let { _user.value = it }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            loginRepository.clearUserData()
            loginRepository.toggleLoginState()
        }
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.EnteredPhoneNumber -> {
                _user.value = user.value.copy(phoneNumber = event.value)
            }
            is ProfileEvent.EnteredWard -> {
                _user.value = user.value.copy(address = user.value.address.copy(ward = event.value)
                    ?: Address(ward = event.value))
            }
            is ProfileEvent.EnteredStreet -> {
                _user.value = user.value.copy(address = user.value.address.copy(street = event.value)
                    ?: Address(street = event.value))
            }
            is ProfileEvent.EnteredHouseNumber -> {
                _user.value = user.value.copy(address = user.value.address.copy(houseNumber = event.value)
                    ?: Address(houseNumber = event.value))
            }
            is ProfileEvent.SaveProfile -> {
                _isLoading.value = true
                viewModelScope.launch {
                    val result = profileRepository.updateProfile(user.value)
                    result.onSuccess {
                        event.onSave()
                    }.onFailure {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = it.message ?: "Failed to update profile"
                            )
                        )
                    }
                    _isLoading.value = false
                }
            }

            is ProfileEvent.EnterUsername -> {
                _user.value = user.value.copy(userName = event.value)
            }
        }
    }
}
