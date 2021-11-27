package com.samdev.githubsearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_GithubSearch)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}