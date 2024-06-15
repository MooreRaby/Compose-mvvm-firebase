package net.pro.comtam.presentation.login



import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import net.pro.comtam.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
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


    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredEmail -> {
                _email.value = email.value.copy(
                    text = event.value
                )
            }
            is LoginEvent.EnteredPassword -> {
                _password.value = password.value.copy(
                    text = event.value
                )
            }
            is LoginEvent.PerformLogin -> {
                _isLoading.value = true
                viewModelScope.launch {
                    val result = repository.loginWithEmail(email.value.text, password.value.text)
                    result.onSuccess { user ->
                        repository.toggleLoginState()
                        event.onClick()
                    }.onFailure {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = it.message ?: "Please enter correct email and password"
                            )
                        )
                    }
                    _isLoading.value = false
                }
            }

        }
    }


}

