package com.tck.my.opengl.camerax

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.tck.my.opengl.camerax.databinding.ActivityCameraEnterBinding

class CameraEnterActivity : AppCompatActivity() {

    companion object {
        val permission = arrayOf(
            android.Manifest.permission.CAMERA
        )
    }

    private lateinit var binding: ActivityCameraEnterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraEnterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEnterCamera.setOnClickListener {
            val all = permission.all {
                ContextCompat.checkSelfPermission(
                    this,
                    it
                ) == PackageManager.PERMISSION_GRANTED
            }
            if (all) {
                jump()
            } else {
                ActivityCompat.requestPermissions(this, permission, 100)
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            val all = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
            if (all) {
                jump()
            } else {
                Toast.makeText(this, "请打开相机权限", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun jump() {
        startActivity(Intent(this, CameraxActivity::class.java))
    }
}