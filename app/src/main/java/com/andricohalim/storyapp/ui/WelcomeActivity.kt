package com.andricohalim.storyapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.andricohalim.storyapp.R
import com.andricohalim.storyapp.ViewModelFactory
import com.andricohalim.storyapp.databinding.ActivityLoginBinding
import com.andricohalim.storyapp.databinding.ActivityWelcomeBinding
import com.andricohalim.storyapp.ui.login.LoginActivity
import com.andricohalim.storyapp.ui.main.MainActivity
import com.andricohalim.storyapp.ui.main.MainViewModel

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        mainViewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

}