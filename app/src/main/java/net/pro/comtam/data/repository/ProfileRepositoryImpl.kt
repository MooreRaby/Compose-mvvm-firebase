package net.pro.comtam.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import net.pro.comtam.domain.model.User
import net.pro.comtam.domain.repository.ProfileRepository
import java.io.IOException
import javax.inject.Inject

private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class ProfileRepositoryImpl @Inject constructor(
    private val context: Context,
    private val firestore: FirebaseFirestore
) : ProfileRepository {

    companion object {
        private val USER_KEY = stringPreferencesKey("user_key")
    }

    private val gson = Gson()

    override val userData: Flow<User?>
        get() = context.userDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[USER_KEY]?.let { jsonString ->
                    gson.fromJson(jsonString, User::class.java)
                }
            }

    override suspend fun updateProfile(user: User): Result<Unit> {
        return try {
            firestore.collection("users").document(user.id).set(user).await()
            saveUserData(user)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun saveUserData(user: User) {
        context.userDataStore.edit { preferences ->
            preferences[USER_KEY] = gson.toJson(user)
        }
    }
}
