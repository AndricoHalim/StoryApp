package com.andricohalim.storyapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andricohalim.storyapp.databinding.StoryRowBinding
import com.andricohalim.storyapp.response.ListStoryItem
import com.andricohalim.storyapp.ui.detail.DetailActivity
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Locale


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
        val (photoUrl, name, dateCreated, id ) = listStory[position]
        val formattedDate = formatDate(dateCreated)
        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load(photoUrl)
                .into(imageView)
            holder.binding.tvName.text = name
            holder.binding.tvDateCreated.text = formattedDate
        }
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.KEY_USER, id)
            intent.putExtra(DetailActivity.KEY_URL, photoUrl)
            holder.itemView.context.startActivity(intent)
        }
    }

    private fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            return outputFormat.format(date!!)
        }

    override fun getItemCount(): Int {
        return listStory.size
    }

    class ViewHolder(val binding: StoryRowBinding) : RecyclerView.ViewHolder(binding.root)
}