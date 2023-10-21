package com.andricohalim.storyapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.andricohalim.data.StoryPagingSource
import com.andricohalim.storyapp.retrofit.UserModel
import com.andricohalim.storyapp.response.ErrorResponse
import com.andricohalim.storyapp.response.ListStoryItem
import com.andricohalim.storyapp.response.LoginResponse
import com.andricohalim.storyapp.response.MapsResponse
import com.andricohalim.storyapp.response.RegisterResponse
import com.andricohalim.storyapp.response.Result
import com.andricohalim.storyapp.response.StoryResponse
import com.andricohalim.storyapp.retrofit.ApiService
import com.andricohalim.storyapp.utils.UserPreference
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class UserRepository (
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    fun register(
        name: String,
        email: String,
        password: String
    ): LiveData<Result<RegisterResponse>> =
        liveData {
            emit(Result.Loading)
            try {
                val registerResponse = apiService.register(name, email, password)
                emit(Result.Success(registerResponse))
            } catch (e: HttpException) {
                val error = e.response()?.errorBody()?.string()
                val errorRes = Gson().fromJson(error, ErrorResponse::class.java)
                Log.d(TAG, "register: ${e.message.toString()}")
                emit(Result.Error(errorRes.message))
            } catch (e: Exception) {
                emit(Result.Error(e.toString()))
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
                Log.d(TAG, "login: ${e.message.toString()}")
                emit(Result.Error(errorRes.message))
            } catch (e: Exception) {
                emit(Result.Error(e.toString()))
            }

        }

    fun getStory(): LiveData<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService)
            }
        ).liveData
    }

    fun uploadImage(imageFile: File, description: String) = liveData {
        emit(Result.Loading)
        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            imageFile.name,
            requestImageFile
        )
        try {
            val successResponse = apiService.uploadImage(multipartBody, requestBody)
            emit(Result.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            emit(Result.Error(errorResponse.message))
        }catch (e: Exception){
            emit(Result.Error(e.toString()))
        }
    }

    fun getStoryWithLocation(): LiveData<Result<StoryResponse>> =
        liveData {
            emit(Result.Loading)
            try{
                val storyLocationResponse = apiService.getStoriesWithLocation()
                emit(Result.Success(storyLocationResponse))
            }catch (e: HttpException) {
                val error = e.response()?.errorBody()?.string()
                val errorRes = Gson().fromJson(error, MapsResponse::class.java)
                Log.d(TAG, "getStoryWithLocation ${e.message.toString()}")
                emit(Result.Error(errorRes.message))
            }catch (e: Exception){
                emit(Result.Error(e.toString()))
            }
        }

    suspend fun logout(){
        userPreference.logout()
    }
        suspend fun saveSession(user: UserModel) {
            userPreference.saveSession(user)
        }

        fun getSession(): Flow<UserModel> {
            return userPreference.getSession()
        }

    companion object {
        private const val TAG = "UserRepository"
    }
}