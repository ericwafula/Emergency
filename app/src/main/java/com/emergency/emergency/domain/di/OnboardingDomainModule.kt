package com.emergency.emergency.domain.di

import com.emergency.emergency.domain.repository.AuthRepository
import com.emergency.emergency.domain.use_case.GetWordFromSentence
import com.emergency.emergency.domain.use_case.Login
import com.emergency.emergency.domain.use_case.Logout
import com.emergency.emergency.domain.use_case.OnboardingUseCases
import com.emergency.emergency.domain.use_case.Signup
import com.emergency.emergency.domain.use_case.ValidateEmail
import com.emergency.emergency.domain.use_case.ValidatePassword
import com.emergency.emergency.domain.use_case.ValidatePhone
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object OnboardingDomainModule {

    @Provides
    @ViewModelScoped
    fun provideValidatePassword(): ValidatePassword {
        return ValidatePassword()
    }

    @Provides
    @ViewModelScoped
    fun provideValidateEmail(): ValidateEmail {
        return ValidateEmail()
    }

    @Provides
    fun provideValidatePhone(): ValidatePhone {
        return ValidatePhone()
    }

    @Provides
    fun provideGetWordFromSentence(): GetWordFromSentence {
        return GetWordFromSentence()
    }

    @Provides
    @ViewModelScoped
    fun provideLogin(repository: AuthRepository): Login = Login(repository)

    @Provides
    @ViewModelScoped
    fun provideSignup(repository: AuthRepository): Signup = Signup(repository)

    @Provides
    @ViewModelScoped
    fun provideLogout(repository: AuthRepository): Logout = Logout(repository)

    @Provides
    @ViewModelScoped
    fun provideOnboardingUseCases(
        validateEmail: ValidateEmail,
        validatePassword: ValidatePassword,
        validatePhone: ValidatePhone,
        getWordFromSentence: GetWordFromSentence,
        login: Login,
        signup: Signup,
        logout: Logout
    ): OnboardingUseCases {
        return OnboardingUseCases(
            validateEmail = validateEmail,
            validatePassword = validatePassword,
            validatePhone = validatePhone,
            getWordFromSentence = getWordFromSentence,
            login = login,
            signup = signup,
            logout = logout
        )
    }



}