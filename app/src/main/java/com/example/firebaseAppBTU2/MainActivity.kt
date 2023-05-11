package com.example.firebaseAppBTU2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.firebaseAppBTU2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            val link = "https://w0.peakpx.com/wallpaper/9/983/HD-wallpaper-dark-vertical-black-thumbnail.jpg"
            Glide.with(this@MainActivity).load("link").into(imageView2)
        }
    }
}