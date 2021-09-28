package com.example.tilesmatch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tilesmatch.R
import com.example.tilesmatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Global
    private val TAG = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}