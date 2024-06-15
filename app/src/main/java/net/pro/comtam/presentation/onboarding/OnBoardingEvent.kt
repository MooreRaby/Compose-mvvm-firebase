package net.pro.comtam.presentation.onboarding

sealed class OnBoardingEvent {
    data class CompleteOnboarding (val completeOnBoarding: () -> Unit) : OnBoardingEvent()
}