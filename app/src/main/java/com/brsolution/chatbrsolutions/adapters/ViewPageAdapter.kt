package com.brsolution.chatbrsolutions.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.brsolution.chatbrsolutions.ui.fragments.ContatosFragment
import com.brsolution.chatbrsolutions.ui.fragments.ConversasFragment

class ViewPageAdapter(
    private val tabs: List<String>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return tabs.size
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return ConversasFragment()
            1 -> return ContatosFragment()
        }
        return ConversasFragment()
    }
}