package com.andricohalim.storyapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.andricohalim.storyapp.ViewModelFactory
import com.andricohalim.storyapp.R
import com.andricohalim.storyapp.adapter.StoryAdapter
import com.andricohalim.storyapp.databinding.ActivityMainBinding
import com.andricohalim.storyapp.response.ListStoryItem
import com.andricohalim.storyapp.response.Result
import com.andricohalim.storyapp.ui.WelcomeActivity
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
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    setupAction(result.data.listStory)
                }

                is Result.Error -> {

                }
            }
        }

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, UploadStoryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
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

    private fun logoutConfirmation(){
        AlertDialog.Builder(this).apply {
            setTitle("Confirmation")
            setMessage("Are you sure want to logout?")
            setPositiveButton("Yes") { _, _ ->
                mainViewModel.logout()
            }
            setNegativeButton("No", null)
        }.create().show()
    }

    private fun setupAction(story: List<ListStoryItem>) {
        binding.apply {
            if (story.isNotEmpty()) {
                val adapter = StoryAdapter(story)
                binding.rvStory.adapter = adapter
            } else {
                rvStory.adapter = null
            }
        }
    }
}