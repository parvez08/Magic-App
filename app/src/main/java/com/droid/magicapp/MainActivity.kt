package com.droid.magicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.droid.magicapp.call_logs.CallLogsFragment
import com.droid.magicapp.databinding.ActivityMainBinding
import com.droid.magicapp.message_logs.MessageLogsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        loadFragment(CallLogsFragment())
        handleBnvClicks()
    }

    private fun handleBnvClicks() {
        binding.bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bnv_call_logs -> {
                    loadFragment(CallLogsFragment())
                    true
                }

                R.id.bnv_message_logs -> {
                    loadFragment(MessageLogsFragment())
                    true
                }

                else -> {
                    loadFragment(CallLogsFragment())
                    true
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.flTopFrag.id, fragment)
        transaction.commit()
    }
}