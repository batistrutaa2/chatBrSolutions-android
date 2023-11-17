package com.brsolution.chatbrsolutions.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.brsolution.chatbrsolutions.R
import com.brsolution.chatbrsolutions.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.textCadastro.setOnClickListener {
            initEventsClicks()
        }
    }

    private fun initEventsClicks() {
        binding.textCadastro.setOnClickListener {
            startActivity(
                Intent(this, CadastroActivity::class.java)
            )
        }
    }
}