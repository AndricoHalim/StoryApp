package com.andricohalim.storyapp.injection

import com.andricohalim.storyapp.repository.UserRepository
import android.content.Context
import android.util.Log
import com.andricohalim.storyapp.R
import com.andricohalim.storyapp.database.StoryDatabase
import com.andricohalim.storyapp.retrofit.ApiConfig
import com.andricohalim.storyapp.utils.UserPreference
import com.andricohalim.storyapp.utils.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getUser().first() }
        Log.d(context.getString(R.string.token_disimpan), user.token)
        val apiService = ApiConfig.getApiService(user.token)
        val storyDatabase = StoryDatabase.getDatabase(context)
        return UserRepository(apiService, pref, storyDatabase)
    }

}