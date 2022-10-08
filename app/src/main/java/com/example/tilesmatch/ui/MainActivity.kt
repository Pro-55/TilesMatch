package com.example.tilesmatch.ui

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import com.example.tilesmatch.R
import com.example.tilesmatch.databinding.ActivityMainBinding
import com.example.tilesmatch.utils.extensions.getColorFromAttr
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // Global
    private val TAG = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val colorBackground = getColorFromAttr(R.attr.colorSurface)
            window.statusBarColor = colorBackground
            window.navigationBarColor = colorBackground
            val isNightMode =
                when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                    Configuration.UI_MODE_NIGHT_YES -> true
                    else -> false
                }
            if (!isNightMode) {
                val decorView = window.decorView
                val controller = WindowInsetsControllerCompat(window, decorView)
                controller.isAppearanceLightStatusBars = true
                controller.isAppearanceLightNavigationBars = true
            }
        }
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
}