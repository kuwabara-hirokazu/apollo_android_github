package com.example.apollo_android_github.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.apollo_android_github.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
