package com.tck.my.opengl.camerax

import android.graphics.SurfaceTexture
import android.opengl.GLSurfaceView
import androidx.camera.core.Preview
import androidx.lifecycle.LifecycleOwner
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 *
 * description:

 * @date 2020/12/12 16:44

 * @author tck88
 *
 * @version v1.0.0
 *
 */
class CameraRender : GLSurfaceView.Renderer,
    Preview.OnPreviewOutputUpdateListener,
    SurfaceTexture.OnFrameAvailableListener {
    private val cameraHelper: CameraHelper
    private val cameraXView: CameraXView

    constructor(cameraXView: CameraXView) {
        this.cameraXView = cameraXView
        cameraXView.context as LifecycleOwner
        cameraHelper = CameraHelper(cameraXView.context as LifecycleOwner, this)
    }

    fun onSurfaceDestroyed() {
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

    override fun onUpdated(output: Preview.PreviewOutput?) {
        TODO("Not yet implemented")
    }

    override fun onFrameAvailable(surfaceTexture: SurfaceTexture?) {
        TODO("Not yet implemented")
    }
}