package net.pro.comtam.presentation.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import net.pro.comtam.domain.repository.SignupRepository
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: SignupRepository
) : ViewModel() {

    private val _email = mutableStateOf(
        FoodikeTextFieldState(
            hint = "Enter email or phone number"
        )
    )
    val email: State<FoodikeTextFieldState> = _email

    private val _password = mutableStateOf(
        FoodikeTextFieldState(
            hint = "Password"
        )
    )
    val password: State<FoodikeTextFieldState> = _password

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun onEvent(event: SignupEvent) {
        when (event) {
            is SignupEvent.EnteredEmail -> {
                _email.value = email.value.copy(
                    text = event.value
                )
            }

            is SignupEvent.EnteredPassword -> {
                _password.value = password.value.copy(
                    text = event.value
                )
            }

            is SignupEvent.PerformSignUp -> {
                _isLoading.value = true
                viewModelScope.launch {
                    val result = repository.signUpWithEmail(email.value.text, password.value.text)
                    result.onSuccess {
                        event.onClick()
                    }.onFailure {
                        _eventFlow.emit(UiEvent.ShowSnackbar(it.message ?: "Sign up failed"))
                    }
                    _isLoading.value = false
                }
            }
        }
    }

}
