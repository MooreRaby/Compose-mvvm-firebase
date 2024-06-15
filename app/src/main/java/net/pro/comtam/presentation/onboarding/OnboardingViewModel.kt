package net.pro.comtam.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pro.comtam.domain.repository.OnBoardingRepository
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val repository: OnBoardingRepository
) : ViewModel() {

    fun onEvent(event: OnBoardingEvent) {
        when (event) {
            is OnBoardingEvent.CompleteOnboarding -> {
                viewModelScope.launch {
                    repository.toggleOnBoardingState()
                    event.completeOnBoarding()
                }
            }
        }
    }
}
