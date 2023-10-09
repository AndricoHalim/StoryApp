package com.andricohalim.storyapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.andricohalim.storyapp.repository.UserRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andricohalim.storyapp.UserModel
import com.andricohalim.storyapp.response.LoginResult
import kotlinx.coroutines.launch
import com.andricohalim.storyapp.response.Result


class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {


    fun loginUser(email: String, password: String)=
        userRepository.login(email, password)

    fun saveSession(user: UserModel)=
        viewModelScope.launch {
            userRepository.saveSession(user)

        }
}