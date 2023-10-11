package com.andricohalim.storyapp.ui.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.andricohalim.storyapp.R
import com.andricohalim.storyapp.ViewModelFactory
import com.andricohalim.storyapp.ui.main.MainActivity
import com.andricohalim.storyapp.UserModel
import com.andricohalim.storyapp.databinding.ActivityLoginBinding
import com.andricohalim.storyapp.response.LoginResult
import com.andricohalim.storyapp.response.Result

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupView()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            loginUser(email, password)
        }
    }

    private fun loginUser(email: String, password: String) {
        loginViewModel.loginUser(email, password).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    setupAction()
                    AlertDialog.Builder(this).apply {
                        setTitle(getString(R.string.sukses_login))
                        setMessage(getString(R.string.login_berhasil))
                        setPositiveButton(getString(R.string.lanjut)) { _, _ ->
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        show()
                    }
                    saveSession(result.data.loginResult)
                }

                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    AlertDialog.Builder(this).apply {
                        setTitle(getString(R.string.error))
                        setMessage(result.error)
                        setPositiveButton(getString(R.string.ok)) { it, _ ->
                            it.dismiss()
                        }
                    }.create().show()
                }
            }
        }
    }

    private fun saveSession(token: LoginResult) {
        loginViewModel.saveSession(UserModel(token.token))
        Log.d("Token disimpan", token.token)
    }

}