package net.pro.comtam.presentation.login

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
}