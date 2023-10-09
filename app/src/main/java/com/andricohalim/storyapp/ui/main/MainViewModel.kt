package com.andricohalim.storyapp.ui.main

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.andricohalim.storyapp.UserModel
import com.andricohalim.storyapp.repository.UserRepository
import com.andricohalim.storyapp.response.RegisterResponse
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository) : ViewModel (){
    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }
    fun getStory ()=
        userRepository.getStory()

    fun logout(){
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}