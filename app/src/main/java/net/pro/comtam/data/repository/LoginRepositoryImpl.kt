package net.pro.comtam.data.repository


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import net.pro.comtam.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import net.pro.comtam.domain.model.User
import net.pro.comtam.utils.PrefsDataStore
import java.io.IOException
import javax.inject.Inject

private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class LoginRepositoryImpl @Inject constructor(
    private val context: Context,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : PrefsDataStore(context = context, PREF_LOGIN_STATE), LoginRepository {

    companion object {
        const val PREF_LOGIN_STATE = "user_login_state_pref"
        private val LOGIN_STATE_KEY = booleanPreferencesKey("user_login_state")
        private val USER_KEY = stringPreferencesKey("user_key")
    }

    private val gson = Gson()

    override val loginState: Flow<Boolean>
        get() = mDataStore.data.map { preferences ->
            preferences[LOGIN_STATE_KEY] ?: false
        }

    override suspend fun loginWithEmail(email: String, password: String): Result<User> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid ?: return Result.failure(Exception("User ID is null"))
            val document = firestore.collection("users").document(userId).get().await()
            val user = document.toObject(User::class.java) ?: return Result.failure(Exception("User not found"))
            saveUserData(user)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun toggleLoginState() {
        mDataStore.edit { preferences ->
            val loginState = preferences[LOGIN_STATE_KEY] ?: false
            preferences[LOGIN_STATE_KEY] = !loginState
        }
    }

    override suspend fun saveUserData(user: User) {
        context.userDataStore.edit { preferences ->
            preferences[USER_KEY] = gson.toJson(user)
        }
    }

    override suspend fun clearUserData() {
        context.userDataStore.edit { preferences ->
            preferences.clear()
        }
    }

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
}