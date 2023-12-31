package com.droid.magicapp.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.droid.magicapp.R
import com.droid.magicapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater).also { setContentView(it.root) }
    }
}