package com.andricohalim.storyapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.andricohalim.storyapp.repository.UserRepository
import com.andricohalim.storyapp.response.DetailResponse
import kotlinx.coroutines.launch
import com.andricohalim.storyapp.response.Result

class DetailViewModel(private val userRepository: UserRepository) : ViewModel(){

    private val _detailStory = MutableLiveData<Result<DetailResponse>>()
    val detailStory : LiveData<Result<DetailResponse>> = _detailStory

    init{
        getDetailStory()
    }

    private fun getDetailStory(){
        viewModelScope.launch {
            val repo = userRepository.getDetailStory(id)
            repo.asFlow().collect(){
                _detailStory.value = it
            }

        }
    }

    companion object{
        var id = "ID"
    }
}