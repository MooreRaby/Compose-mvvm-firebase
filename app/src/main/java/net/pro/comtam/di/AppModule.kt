package net.pro.comtam.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.pro.comtam.data.repository.HomeRepositoryImpl
import net.pro.comtam.data.repository.LoginRepositoryImpl
import net.pro.comtam.data.repository.OnboardingRepositoryImpl
import net.pro.comtam.data.repository.ProfileRepositoryImpl
import net.pro.comtam.data.repository.SignupRepositoryImpl
import net.pro.comtam.data.repository.UserDataRepositoryImpl
import net.pro.comtam.domain.repository.HomeRepository
import net.pro.comtam.domain.repository.LoginRepository
import net.pro.comtam.domain.repository.OnBoardingRepository
import net.pro.comtam.domain.repository.ProfileRepository
import net.pro.comtam.domain.repository.SignupRepository
import net.pro.comtam.domain.repository.UserDataRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Provides
    @Singleton
    fun provideLoginRepository(
        @ApplicationContext context: Context,
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): LoginRepository {
        return LoginRepositoryImpl(context, auth, firestore)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(
        @ApplicationContext context: Context,
        firestore: FirebaseFirestore,
    ): ProfileRepository = ProfileRepositoryImpl(context, firestore)

    @Provides
    @Singleton
    fun provideSignupRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): SignupRepository = SignupRepositoryImpl(auth, firestore)

    @Provides
    @Singleton
    fun providesHomeRepository(): HomeRepository = HomeRepositoryImpl()

    @Provides
    @Singleton
    fun providesUserDataRepository(): UserDataRepository = UserDataRepositoryImpl()


    @Provides
    @Singleton
    fun providesOnBoardingRepository( @ApplicationContext context: Context): OnBoardingRepository = OnboardingRepositoryImpl(context = context)
    // Add other dependencies like Room Database, SharedPreferences, etc.
}
