package com.andricohalim.storyapp

import com.andricohalim.storyapp.repository.UserRepository
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andricohalim.storyapp.injection.Injection
import com.andricohalim.storyapp.ui.UserPreference
import com.andricohalim.storyapp.ui.detail.DetailViewModel
import com.andricohalim.storyapp.ui.login.LoginViewModel
import com.andricohalim.storyapp.ui.main.MainViewModel
import com.andricohalim.storyapp.ui.register.RegisterViewModel

class ViewModelFactory private constructor(
    private val userRepository: UserRepository,
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(userRepository) as T
        }
        else if(modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(userRepository) as T
        } else if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(userRepository) as T
        }else if(modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(userRepository) as T
        }
        throw IllegalArgumentException("No ModelClass: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory {
            return ViewModelFactory(Injection.provideRepository(context))
        }
    }
}