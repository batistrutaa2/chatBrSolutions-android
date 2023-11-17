package com.brsolution.chatbrsolutions.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuProvider
import com.brsolution.chatbrsolutions.R
import com.brsolution.chatbrsolutions.adapters.ViewPageAdapter
import com.brsolution.chatbrsolutions.databinding.ActivityLoginBinding
import com.brsolution.chatbrsolutions.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private val tabs = listOf("CONVERSAS", "CONTATOS")
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    private val fireBaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializeToolBar()
        initializeNavigationTabs()
    }

    private fun initializeNavigationTabs() {
        val tabLayout = binding.tabLayoutPrincipal
        val viewPager = binding.viewPagerPrincipal

        viewPager.adapter = ViewPageAdapter(tabs, supportFragmentManager, lifecycle)

        tabLayout.isTabIndicatorFullWidth = true

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabs[position]
        }.attach()
    }


    @SuppressLint("UseSupportActionBar")
    private fun initializeToolBar() {
        val toolbar = binding.includeMainToolBar.tbPrincipal
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "ChatBrSolutions"
        }
        addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_principal, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    when (menuItem.itemId) {
                        R.id.item_perfil -> {
                            startActivity(Intent(applicationContext, PerfilActivity::class.java))
                        }

                        R.id.item_sair -> {
                            logoutUser()
                        }
                    }
                    return true
                }
            }
        )
    }

    private fun logoutUser() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.deslogar))
            .setMessage(getString(R.string.deseja_realmente_sair))
            .setNegativeButton(getString(R.string.nao)) { dialog, _ -> dialog.dismiss() }
            .setPositiveButton(getString(R.string.sim)) { _, _ ->
                fireBaseAuth.signOut()
                startActivity(Intent(applicationContext, LoginActivity::class.java))
            }
            .create()
            .show()
    }

}