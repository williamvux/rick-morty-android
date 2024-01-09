package com.example.rickmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.example.rickmorty.databinding.ActivityBottomNavigationBinding
import com.google.android.material.navigation.NavigationView

class BottomNavigationActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityBottomNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction()
            .replace(binding.viewPager.id, CharactersFragment())
            .commit()

        binding.bottomNav.setOnItemSelectedListener { item ->
            onNavigationItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_characters -> {
                supportFragmentManager.beginTransaction()
                    .replace(binding.viewPager.id, CharactersFragment())
                    .commit()
                return true
            }
            R.id.menu_episodes -> {
                supportFragmentManager.beginTransaction()
                    .replace(binding.viewPager.id, EpisodesFragment())
                    .commit()
                return true
            }
            R.id.menu_settings -> {
                supportFragmentManager.beginTransaction()
                    .replace(binding.viewPager.id, SettingsFragment())
                    .commit()
                return true
            }
            else -> {
                return false
            }
        }
    }
}