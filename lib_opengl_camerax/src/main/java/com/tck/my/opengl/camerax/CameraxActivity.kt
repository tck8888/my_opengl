package com.tck.my.opengl.camerax

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tck.my.opengl.camerax.databinding.ActivityCameraxBinding

class CameraxActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraxBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraxBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}