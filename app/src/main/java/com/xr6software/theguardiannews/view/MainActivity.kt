package com.xr6software.theguardiannews.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.xr6software.theguardiannews.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var bottomToolbar: BottomNavigationView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        bottomToolbar = findViewById(R.id.ma_bottom_menu)
        configNav()

    }

    private fun configNav() {
        val navController = Navigation.findNavController(this, R.id.ma_fragment_container)
        NavigationUI.setupWithNavController(bottomToolbar, navController)
    }

}