package net.pro.comtam.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import net.pro.comtam.domain.model.User
import net.pro.comtam.domain.repository.SignupRepository
import javax.inject.Inject

class SignupRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : SignupRepository {

    override suspend fun signUpWithEmail(email: String, password: String): Result<String> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid ?: return Result.failure(Exception("User ID is null"))
            val user = User(id = userId, email = email)
            firestore.collection("users").document(userId).set(user).await()
            Result.success("User registered successfully")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
