package net.pro.comtam.domain.repository

import kotlinx.coroutines.flow.Flow

interface OnBoardingRepository {
    //    fun readLoginState(): Boolean
    suspend fun toggleOnBoardingState()
    val onBoardingState: Flow<Boolean>
}