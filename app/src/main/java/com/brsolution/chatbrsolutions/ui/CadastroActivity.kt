package com.brsolution.chatbrsolutions.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.brsolution.chatbrsolutions.R
import com.brsolution.chatbrsolutions.databinding.ActivityCadastroBinding
import com.brsolution.chatbrsolutions.databinding.ActivityLoginBinding
import com.brsolution.chatbrsolutions.model.User
import com.brsolution.chatbrsolutions.ultis.showMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class CadastroActivity : AppCompatActivity() {

    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String


    private val fireBaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val fireStore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val binding by lazy {
        ActivityCadastroBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initializeToolBar()
        initEventsClicks()
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

    private fun initEventsClicks() {
        binding.btnCadastrar.setOnClickListener {
            name = binding.editNome.text.toString()
            email = binding.editEmail.text.toString()
            password = binding.editSenha.text.toString()

            if (validateInputs()) {
                createUser(name, email, password)
            }
        }

    }

    private fun createUser(name: String, email: String, password: String) {
        fireBaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val user = it.result.user?.let { it1 -> User(it1.uid, name, email, password) }
                createUserFireStore(user)
            }
        }.addOnFailureListener {
            try {
                showMessage(it.message.toString())
            } catch (errorFireBaseCollisionException: FirebaseAuthWeakPasswordException) {
                showMessage(getString(R.string.senha_fraca_digite_outras_com_letras_numeros_e_caracteres_especiais))
            } catch (errorFireBaseCollisionException: FirebaseAuthUserCollisionException) {
                showMessage(getString(R.string.usuario_ja_existente_na_base_de_dados))
            } catch (errorCredentialsInvalidade: FirebaseAuthInvalidCredentialsException) {
                showMessage(getString(R.string.credenciais_invalidas))
            }
        }
    }

    private fun createUserFireStore(user: User?) {
        user?.let {
            fireStore.collection(getString(R.string.firestore_usuarios))
                .document(user.id)
                .set(it)
                .addOnSuccessListener {
                    showMessage(getString(R.string.usuario_cadastrado_com_sucesso))
                    startActivity(Intent(this, MainActivity::class.java))
                }.addOnFailureListener {
                    showMessage(getString(R.string.erro_cadastro_usuario))
                }
        }
    }

    private fun validateInputs(): Boolean {
        if (name.trim().isEmpty()) {
            binding.textInputLayoutNome.error = getString(R.string.digite_seu_nome)
            return false
        }
        if (email.trim().isEmpty()) {
            binding.textInputLayoutNome.error = null
            binding.textInputLayoutEmail.error = getString(R.string.digite_seu_email)
            return false
        }

        if (password.isEmpty()) {
            binding.textInputLayoutEmail.error = null
            binding.textInputLayoutSenha.error = getString(R.string.digite_sua_senha)
            return false
        }

        binding.textInputLayoutNome.error = null
        binding.textInputLayoutEmail.error = null
        binding.textInputLayoutSenha.error = null
        return true
    }

}