package com.tck.my.opengl.one

import android.opengl.GLES20
import android.os.Bundle
import android.view.SurfaceHolder
import androidx.appcompat.app.AppCompatActivity
import com.tck.my.opengl.one.databinding.ActivityOpenglTrainOneBinding

class OpenGLTrainOneActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOpenglTrainOneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpenglTrainOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {

            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                Thread {
                    val eglHelper = EglHelper()
                    eglHelper.initEgl(holder.surface, null)
                    while (true) {
                        GLES20.glViewport(0, 0, width, height)
                        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
                        GLES20.glClearColor(1.0f, 1.0f, 0.0f, 1.0f)
                        eglHelper.swapBuffers()
                        try {
                            Thread.sleep(16)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }

                }.start()
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {

            }
        })
    }
}