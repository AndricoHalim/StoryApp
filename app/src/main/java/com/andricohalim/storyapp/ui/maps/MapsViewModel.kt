package com.andricohalim.storyapp.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.andricohalim.storyapp.repository.UserRepository
import com.andricohalim.storyapp.response.Result
import com.andricohalim.storyapp.response.StoryResponse
import kotlinx.coroutines.launch

class MapsViewModel (private val userRepository: UserRepository) : ViewModel() {
    fun getListStoryLocation() = userRepository.getStoryWithLocation()
}