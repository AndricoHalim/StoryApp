package com.andricohalim.storyapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.andricohalim.storyapp.R
import com.andricohalim.storyapp.databinding.ActivityDetailBinding
import com.andricohalim.storyapp.utils.loadImage
import com.andricohalim.storyapp.response.ListStoryItem
import java.text.SimpleDateFormat
import java.util.Locale

class DetailActivity : AppCompatActivity() {

    private var detailBinding: ActivityDetailBinding? = null
    private val binding get() = detailBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = getString(R.string.detail_story)

        setData()
    }

    private fun setData() {
        @Suppress("DEPRECATION")
        val story = intent.getParcelableExtra<ListStoryItem>(DETAIL_STORY) as ListStoryItem
        loadImage(applicationContext, story.photoUrl, binding.ivDetail)
        binding.tvDetailName.text = story.name
        binding.tvGetDeskripsi.text = story.description
        val formattedDate = formatDate(story.createdAt)
        binding.tvDateCreated.text = formattedDate
    }

    private fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat(getString(R.string.date_format), Locale.getDefault())
        val outputFormat = SimpleDateFormat(getString(R.string.output_date), Locale.getDefault())
        val date = inputFormat.parse(dateString)
        return outputFormat.format(date!!)
    }

    companion object {
        const val DETAIL_STORY = "detail_story"
    }
}