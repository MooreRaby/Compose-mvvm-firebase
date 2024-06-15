package net.pro.comtam.domain.repository


interface SignupRepository {
    suspend fun signUpWithEmail(email: String, password: String): Result<String>
}