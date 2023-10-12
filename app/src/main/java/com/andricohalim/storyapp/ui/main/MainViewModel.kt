package com.andricohalim.storyapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.andricohalim.storyapp.UserModel
import com.andricohalim.storyapp.repository.UserRepository
import com.andricohalim.storyapp.response.StoryResponse
import kotlinx.coroutines.launch
import com.andricohalim.storyapp.response.Result

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _listStory = MutableLiveData<Result<StoryResponse>>()
    val listStory: LiveData<Result<StoryResponse>> = _listStory

    init {
        getListStory()
    }

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

    private fun getListStory() {
        viewModelScope.launch {
            val response = userRepository.getStory()
            response.asFlow().collect {
                _listStory.value = it
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}