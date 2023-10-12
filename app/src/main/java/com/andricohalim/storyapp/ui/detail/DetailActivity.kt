package com.andricohalim.storyapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.andricohalim.storyapp.databinding.ActivityDetailBinding
import com.andricohalim.storyapp.response.ListStoryItem
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Locale

class DetailActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetailBinding
    private val binding get() = detailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setData()
    }

    private fun setData() {
        @Suppress("DEPRECATION")
        val story = intent.getParcelableExtra<ListStoryItem>(DETAIL_STORY) as ListStoryItem
        Glide.with(applicationContext)
            .load(story.photoUrl)
            .into(binding.ivDetail)
        binding.tvDetailName.text = story.name
        binding.tvGetDeskripsi.text = story.description
        val formattedDate = formatDate(story.createdAt)
        binding.tvDateCreated.text = formattedDate
    }

    private fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        return outputFormat.format(date!!)
    }

    companion object {
        const val DETAIL_STORY = "detail_story"
    }
}