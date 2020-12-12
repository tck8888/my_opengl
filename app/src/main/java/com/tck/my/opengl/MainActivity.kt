package com.tck.my.opengl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tck.my.opengl.camerax.CameraxActivity
import com.tck.my.opengl.databinding.ActivityMainBinding
import com.tck.my.opengl.one.OpenGLTrainOneActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOpenglTrainOne.setOnClickListener {
            startActivity(Intent(this, OpenGLTrainOneActivity::class.java))
        }

        binding.btnOpenglCamerax.setOnClickListener {
            startActivity(Intent(this, CameraxActivity::class.java))
        }
    }
}