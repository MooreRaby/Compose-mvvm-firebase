package net.pro.comtam.presentation.signup

sealed class SignupEvent {

    data class EnteredEmail(val value: String) : SignupEvent()
    data class EnteredPassword(val value: String) : SignupEvent()
    data class PerformSignUp(val onClick: () -> Unit) : SignupEvent()

}