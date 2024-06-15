package net.pro.comtam.domain.repository

import kotlinx.coroutines.flow.Flow
import net.pro.comtam.domain.model.User

interface LoginRepository {
    suspend fun loginWithEmail(email: String, password: String): Result<User>
    suspend fun toggleLoginState()
    val loginState: Flow<Boolean>
    suspend fun saveUserData(user: User)
    suspend fun clearUserData()
    val userData: Flow<User?>
}
