package com.andricohalim.storyapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.andricohalim.storyapp.databinding.StoryRowBinding
import com.andricohalim.storyapp.response.ListStoryItem
import com.andricohalim.storyapp.ui.detail.DetailActivity
import com.andricohalim.storyapp.withCustomAndFullDateFormat
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Locale

private fun formatDate(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val date = inputFormat.parse(dateString)
    return outputFormat.format(date!!)
}
class StoryAdapter(private val listStory: List<ListStoryItem>) :
RecyclerView.Adapter<StoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            StoryRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false))
    }

    class ViewHolder(private var binding: StoryRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(stories: ListStoryItem) {
            Glide.with(binding.root.context)
                .load(stories.photoUrl)
                .into(binding.imageView)
            binding.tvName.text = stories.name
            val formattedDate = formatDate(stories.createdAt)
            binding.tvDateCreated.text = formattedDate


            binding.root.setOnClickListener {
                val detailIntent = Intent(binding.root.context, DetailActivity::class.java)
                detailIntent.putExtra(DetailActivity.DETAIL_STORY, stories)
                itemView.context.startActivity(detailIntent, ActivityOptionsCompat.makeSceneTransitionAnimation(itemView.context as Activity).toBundle())
            }
        }
    }

    override fun getItemCount(): Int {
        return listStory.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(listStory[position])
    }
}