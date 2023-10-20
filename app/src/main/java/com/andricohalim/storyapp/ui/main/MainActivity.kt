package com.andricohalim.storyapp.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.andricohalim.storyapp.ui.maps.MapsActivity
import com.andricohalim.storyapp.utils.ViewModelFactory
import com.andricohalim.storyapp.R
import com.andricohalim.storyapp.adapter.StoryAdapter
import com.andricohalim.storyapp.databinding.ActivityMainBinding
import com.andricohalim.storyapp.response.ListStoryItem
import com.andricohalim.storyapp.response.Result
import com.andricohalim.storyapp.ui.welcome.WelcomeActivity
import com.andricohalim.storyapp.ui.story.UploadStoryActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customActionBar()

        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager

        mainViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
        mainViewModel.listStory.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    setupAction(result.data.listStory)
                    binding.swiperefresh.isRefreshing = false
                }

                is Result.Error -> {
                    showLoading(false)
                    binding.tvError.text = result.error
                    binding.tvError.visibility = View.VISIBLE
                }
            }
        }

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, UploadStoryActivity::class.java)
            startActivity(intent)
        }

        binding.swiperefresh.setOnRefreshListener {
            mainViewModel.getListStory()
            Toast.makeText(this, "Story Refresh", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onResume() {
        super.onResume()
        mainViewModel.getListStory()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_maps -> {
                startActivity(Intent(this, MapsActivity::class.java))
                true
            }

            R.id.menu_logout -> {
                logoutConfirmation()
                true
            }

            R.id.menu_localization -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("InflateParams")
    private fun customActionBar(){
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.title = ""

        val customView = layoutInflater.inflate(R.layout.custom_actionbarlogo, null)
        supportActionBar?.customView = customView

        val logoImageView = customView.findViewById<ImageView>(R.id.logoImageView)
        logoImageView.setImageResource(R.drawable.logo)
    }

    private fun logoutConfirmation(){
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.confirmation))
            setMessage(getString(R.string.are_you_sure_want_to_logout))
            setPositiveButton(context.getString(R.string.yes)) { _, _ ->
                mainViewModel.logout()
            }
            setNegativeButton(getString(R.string.no), null)
        }.create().show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    private fun setupAction(story: List<ListStoryItem>) {
        binding.apply {
            if (story.isNotEmpty()) {
                val adapter = StoryAdapter(story)
                binding.rvStory.adapter = adapter
            } else {
                rvStory.adapter = null
                binding.tvError.visibility = View.VISIBLE
            }
        }
    }
}