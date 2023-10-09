package com.andricohalim.storyapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andricohalim.storyapp.databinding.StoryRowBinding
import com.andricohalim.storyapp.response.ListStoryItem
import com.andricohalim.storyapp.ui.story.DetailStoryActivity
import com.bumptech.glide.Glide

class StoryAdapter(private val listStory: List<ListStoryItem>) :
    RecyclerView.Adapter<StoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            StoryRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (photoUrl, name, description ) = listStory[position]
        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load(photoUrl)
                .into(imageView)
            holder.binding.tvName.text = name
            holder.binding.tvDescription.text = description
        }
    }

    override fun getItemCount(): Int {
        return listStory.size
    }

    class ViewHolder(val binding: StoryRowBinding) : RecyclerView.ViewHolder(binding.root)
}