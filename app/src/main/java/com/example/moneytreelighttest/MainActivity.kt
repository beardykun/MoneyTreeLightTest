package com.example.moneytreelighttest

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.moneytreelighttest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun showProgressView() {
        binding.progressLay.visibility = View.VISIBLE
    }

    fun hideProgressView() {
        binding.progressLay.visibility = View.GONE
    }
}