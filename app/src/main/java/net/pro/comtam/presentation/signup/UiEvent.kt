package net.pro.comtam.presentation.signup

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
}