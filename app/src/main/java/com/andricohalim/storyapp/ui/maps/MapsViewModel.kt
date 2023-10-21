package com.andricohalim.storyapp.ui.maps

import androidx.lifecycle.ViewModel
import com.andricohalim.storyapp.repository.UserRepository

class MapsViewModel (private val userRepository: UserRepository) : ViewModel() {
    fun getListStoryLocation() = userRepository.getStoryWithLocation()
}