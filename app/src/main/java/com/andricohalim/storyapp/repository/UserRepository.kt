package com.andricohalim.storyapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.andricohalim.storyapp.UserModel
import com.andricohalim.storyapp.response.ErrorResponse
import com.andricohalim.storyapp.response.ListStoryItem
import com.andricohalim.storyapp.response.LoginResponse
import com.andricohalim.storyapp.response.RegisterResponse
import com.andricohalim.storyapp.response.Result
import com.andricohalim.storyapp.response.StoryResponse
import com.andricohalim.storyapp.retrofit.ApiService
import com.andricohalim.storyapp.ui.UserPreference
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class UserRepository private constructor (
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    fun register(name: String, email: String, password: String): LiveData<Result<RegisterResponse>> =
        liveData {
            emit(Result.Loading)
            try {
                val registerResponse = apiService.register(name, email, password)
                emit(Result.Success(registerResponse))
            } catch (e: HttpException) {
                val error = e.response()?.errorBody()?.string()
                val errorRes = Gson().fromJson(error, ErrorResponse::class.java)
                emit(Result.Error(errorRes.message.toString()))
            }
        }

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> =
        liveData {
            emit(Result.Loading)
            try {
                val loginResponse = apiService.login(email, password)
                emit(Result.Success(loginResponse))
            } catch (e: HttpException) {
                val error = e.response()?.errorBody()?.string()
                val errorRes = Gson().fromJson(error, ErrorResponse::class.java)
                emit(Result.Error(errorRes.message.toString()))
            }
        }

    fun getStory(): LiveData<Result<StoryResponse>> =
        liveData {
            emit(Result.Loading)
            try {
                val storyResponse = apiService.getStories()
                emit(Result.Success(storyResponse))
            } catch (e: HttpException) {
                val error = e.response()?.errorBody()?.string()
                val errorRes = Gson().fromJson(error, ErrorResponse::class.java)
                emit(Result.Error(errorRes.message.toString()))
            }
        }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        private const val TAG = "com.andricohalim.storyapp.repository.UserRepository"

        fun getInstance(apiService: ApiService, userPreference: UserPreference): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreference).apply { instance = this }
            }.also { instance = it }
    }
}