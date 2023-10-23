package com.andricohalim.storyapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.andricohalim.storyapp.retrofit.UserModel
import com.andricohalim.storyapp.repository.UserRepository
import com.andricohalim.storyapp.response.ListStoryItem
import com.andricohalim.storyapp.response.StoryResponse
import kotlinx.coroutines.launch
import com.andricohalim.storyapp.response.Result

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    val listStory: LiveData<PagingData<ListStoryItem>> =
        userRepository.getStory().cachedIn(viewModelScope)

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}