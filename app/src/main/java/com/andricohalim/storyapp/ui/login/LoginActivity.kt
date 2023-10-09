package com.andricohalim.storyapp.ui.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.andricohalim.storyapp.ViewModelFactory
import com.andricohalim.storyapp.ui.main.MainActivity
import com.andricohalim.storyapp.UserModel
import com.andricohalim.storyapp.databinding.ActivityLoginBinding
import com.andricohalim.storyapp.response.LoginResponse
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

            when {
                email.isEmpty() -> {
                    binding.edEmailLayout.error = "Email Tidak Boleh Kosong"
                }

                password.isEmpty() -> {
                    binding.edLoginPasswordLayout.error = "Password Tidak Boleh Kosong"
                }

                else -> {
                    loginUser(email, password)
                }
            }
        }
    }

    private fun saveSession(token: LoginResponse) {
        val email = binding.edLoginEmail.text.toString()
        loginViewModel.saveSession(UserModel(token.loginResult.token,email))
        val toast =
            Toast.makeText(this, "Token Disimpan: ${token.loginResult.token}", Toast.LENGTH_SHORT)
        toast.show()
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
                        setTitle("Sukses Login")
                        setMessage("Login Berhasil")
                        setPositiveButton("Lanjut") { _, _ ->
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        show()
                    }
                    saveSession(result.data)
                }

                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    AlertDialog.Builder(this).apply {
                        setTitle("Error")
                        setMessage(result.error)
                        setPositiveButton("OK") { p0, _ ->
                            p0.dismiss()
                        }
                    }.create().show()
                }
            }
        }
    }
}