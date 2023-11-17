package com.brsolution.chatbrsolutions.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.brsolution.chatbrsolutions.R
import com.brsolution.chatbrsolutions.databinding.ActivityLoginBinding
import com.brsolution.chatbrsolutions.ultis.showMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class LoginActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var password: String

    private val fireBaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initEventsClicks()
    }

    override fun onStart() {
        super.onStart()
        checkUserLogged()
    }

    private fun checkUserLogged() {
        val userCurrent = fireBaseAuth.currentUser

        if (userCurrent != null) {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }
    }

    private fun initEventsClicks() {
        binding.textCadastro.setOnClickListener {
            startActivity(
                Intent(this, CadastroActivity::class.java)
            )
        }

        binding.btnLogar.setOnClickListener {
            email = binding.editEmail.text.toString()
            password = binding.editSenha.text.toString()
            if (validateInputs()) {
                loginUser()
            }
        }
    }

    private fun loginUser() {
        fireBaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            showMessage(getString(R.string.logado_com_sucesso))
            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }.addOnFailureListener { error ->
            try {
                showMessage(error.message.toString())
            } catch (errorUseInvalid: FirebaseAuthInvalidUserException) {
                showMessage(getString(R.string.e_mail_n_o_cadastrado))
            } catch (errorUseInvalid: FirebaseAuthInvalidCredentialsException) {
                showMessage(getString(R.string.e_mail_ou_senha_incorretos))
            }
        }
    }

    private fun validateInputs(): Boolean {

        if (email.trim().isEmpty()) {
            binding.textInputLayoutLoginEmail.error = getString(R.string.digite_seu_email)
            return false
        }

        if (password.isEmpty()) {
            binding.textInputLayoutLoginEmail.error = null
            binding.textInputLayoutLoginSenha.error = getString(R.string.digite_sua_senha)
            return false
        }
        binding.textInputLayoutLoginEmail.error = null
        binding.textInputLayoutLoginSenha.error = null
        return true
    }
}