package com.brsolution.chatbrsolutions.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.brsolution.chatbrsolutions.R
import com.brsolution.chatbrsolutions.databinding.ActivityCadastroBinding
import com.brsolution.chatbrsolutions.databinding.ActivityLoginBinding

class CadastroActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCadastroBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initializeToolBar()
    }

    @SuppressLint("UseSupportActionBar")
    private fun initializeToolBar() {
        val toolbar = binding.includetoolBar.tbPrincipal
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.faca_seu_cadastro)
            setDisplayHomeAsUpEnabled(true)
        }
    }
}