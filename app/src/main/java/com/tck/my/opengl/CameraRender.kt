package com.tck.my.opengl

import android.opengl.GLSurfaceView
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.core.view.get
import androidx.lifecycle.LifecycleOwner
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 *
 * description:

 * @date 2020/12/8 21:44

 * @author tck88
 *
 * @version v1.0.0
 *
 */
class CameraRender : GLSurfaceView.Renderer {
    constructor(cameraView: CameraView) {
        val previewView = PreviewView(cameraView.context)
        previewView.surfaceProvider
        Preview().setSurfaceProvider()
        val lifecycleOwner = cameraView.context as LifecycleOwner
        CameraHelper(lifecycleOwner,this)

    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        TODO("Not yet implemented")
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        TODO("Not yet implemented")
    }

    override fun onDrawFrame(gl: GL10?) {
        TODO("Not yet implemented")
    }

    fun onSurfaceDestroyed() {
        TODO("Not yet implemented")
    }
}