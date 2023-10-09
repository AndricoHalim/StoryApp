package com.andricohalim.storyapp.ui.register

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.andricohalim.storyapp.ViewModelFactory
import com.andricohalim.storyapp.R
import com.andricohalim.storyapp.databinding.ActivityRegisterBinding
import com.andricohalim.storyapp.response.Result
import com.andricohalim.storyapp.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var progressBar: ProgressBar
    private val registerViewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        progressBar = findViewById(R.id.progressBar)



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

    private fun registerUser(name: String, email: String, password: String) {
        registerViewModel.registerUser(name, email, password).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    setupAction()
                    binding.progressBar.visibility = View.GONE

                    AlertDialog.Builder(this).apply {
                        setTitle("Akun Dibuat")
                        setMessage("Akun dengan $email berhasil dibuat. Login Sekarang.")
                        setPositiveButton("Lanjut") { _, _ ->
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        show()
                    }
                }

                is Result.Error -> {
                    progressBar.visibility = View.GONE
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

    private fun setupAction() {
        binding.tvLoginDisini.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()

            when {
                name.isEmpty() -> {
                    binding.edRegisterNameLayout.error = "Nama tidak boleh kosonng"
                }

                email.isEmpty() -> {
                    binding.edRegisterEmailLayout.error = "Email tidak boleh kosong"
                }

                password.isEmpty() -> {
                    binding.edRegisterPasswordLayout.error = "Password tidak boleh kosong"
                }

                else -> {
                    binding.edRegisterNameLayout.error = null
                    binding.edRegisterEmailLayout.error = null
                    binding.edRegisterPasswordLayout.error = null
                    registerUser(name, email, password)
                }
            }
        }
        binding.edRegisterName.addTextChangedListener {
            binding.edRegisterNameLayout.error = null
        }
        binding.edRegisterEmail.addTextChangedListener {
            binding.edRegisterEmailLayout.error = null
        }
        binding.edRegisterPassword.addTextChangedListener {
            binding.edRegisterPasswordLayout.error = null
        }
    }
}