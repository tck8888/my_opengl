package com.tck.my.opengl.camerax.two

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tck.my.opengl.camerax.two.databinding.ActivityCameraXTwoBinding

class CameraXTwoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraXTwoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCameraXTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}