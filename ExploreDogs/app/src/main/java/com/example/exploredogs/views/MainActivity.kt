package com.example.exploredogs.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.exploredogs.R

class MainActivity : AppCompatActivity() {
    private lateinit var navontroller:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navontroller=Navigation.findNavController(this,R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this,navontroller)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navontroller,null)
    }
}