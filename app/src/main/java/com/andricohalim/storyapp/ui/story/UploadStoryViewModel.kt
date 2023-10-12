package com.andricohalim.storyapp.ui.story

import androidx.lifecycle.ViewModel
import com.andricohalim.storyapp.repository.UserRepository
import java.io.File

class UploadStoryViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun uploadImage(file: File, description: String) = userRepository.uploadImage(file, description)
}