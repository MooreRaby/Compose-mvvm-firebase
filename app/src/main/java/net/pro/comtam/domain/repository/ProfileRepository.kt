package net.pro.comtam.domain.repository

import kotlinx.coroutines.flow.Flow
import net.pro.comtam.domain.model.User


interface ProfileRepository {
    suspend fun updateProfile(user: User): Result<Unit>
    val userData: Flow<User?>
}
