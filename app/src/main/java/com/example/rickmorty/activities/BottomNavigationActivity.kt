package com.example.rickmorty.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.rickmorty.fragments.EpisodesFragment
import com.example.rickmorty.fragments.LocationsFragment
import com.example.rickmorty.R
import com.example.rickmorty.fragments.SettingsFragment
import com.example.rickmorty.databinding.ActivityBottomNavigationBinding
import com.example.rickmorty.fragments.CharactersFragment
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
                binding.titleHeader.text = getString(R.string.characters)
                supportFragmentManager.beginTransaction()
                    .replace(binding.viewPager.id, CharactersFragment())
                    .commit()
                return true
            }
            R.id.menu_locations -> {
                binding.titleHeader.text = getString(R.string.locations)
                supportFragmentManager.beginTransaction()
                    .replace(binding.viewPager.id, LocationsFragment())
                    .commit()
                return true
            }
            R.id.menu_episodes -> {
                binding.titleHeader.text = getString(R.string.episodes)
                supportFragmentManager.beginTransaction()
                    .replace(binding.viewPager.id, EpisodesFragment())
                    .commit()
                return true
            }
            R.id.menu_settings -> {
                binding.titleHeader.text = getString(R.string.settings)
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