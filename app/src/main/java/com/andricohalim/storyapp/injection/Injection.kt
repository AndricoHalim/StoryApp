package com.andricohalim.storyapp.injection

import com.andricohalim.storyapp.repository.UserRepository
import android.content.Context
import android.util.Log
import com.andricohalim.storyapp.retrofit.ApiConfig
import com.andricohalim.storyapp.ui.UserPreference
import com.andricohalim.storyapp.ui.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getUser().first() }
        Log.d("Tes", user.token)
        val apiService = ApiConfig.getApiService(user.token)
        return UserRepository(apiService, pref)
    }

}