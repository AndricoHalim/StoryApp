package com.andricohalim.storyapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.andricohalim.storyapp.ViewModelFactory
import com.andricohalim.storyapp.databinding.ActivityDetailBinding
import com.andricohalim.storyapp.response.DetailResponse
import com.andricohalim.storyapp.response.Result
import com.andricohalim.storyapp.ui.detail.DetailViewModel.Companion.id
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>{
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getStringExtra(KEY_USER).toString()

        detailViewModel.detailStory.observe(this){
                when (it){
                    is Result.Loading ->{

                    }

                    is Result.Success ->{
                        setData(it.data)
                    }

                    is Result.Error ->{

                    }
                }
        }
    }

    private fun setData(id: DetailResponse){
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(id.photoUrl)
                .into(ivDetail)
            tvDetailName.text = id.name
            tvDeskripsi.text = id.description
        }
    }

    companion object {
        const val KEY_USER = "key_user"
        const val KEY_URL = "key_url"
    }
}