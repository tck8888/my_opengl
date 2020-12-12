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
class CameraRender(private var cameraXView: CameraXView) : GLSurfaceView.Renderer,
    Preview.OnPreviewOutputUpdateListener,
    SurfaceTexture.OnFrameAvailableListener {
    private var cameraHelper: CameraHelper
    private val textures = IntArray(1)
    private var surfaceTexture: SurfaceTexture? = null
    private var screenFilter: ScreenFilter? = null

    private var mtx = FloatArray(16)

    init {
        cameraXView.context as LifecycleOwner
        cameraHelper = CameraHelper(cameraXView.context as LifecycleOwner, this)
    }

    fun onSurfaceDestroyed() {
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        surfaceTexture?.attachToGLContext(textures[0])
        // 当摄像头数据有更新回调 onFrameAvailable
        surfaceTexture?.setOnFrameAvailableListener(this)

        screenFilter = ScreenFilter(cameraXView.context)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        screenFilter?.setSize(width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        surfaceTexture?.updateTexImage()
        surfaceTexture?.getTransformMatrix(mtx)
        screenFilter?.setTransformMatrix(mtx)
        screenFilter?.onDraw(textures[0])
    }

    override fun onUpdated(output: Preview.PreviewOutput?) {
        surfaceTexture = output?.surfaceTexture
    }

    override fun onFrameAvailable(surfaceTexture: SurfaceTexture?) {
      cameraXView.requestRender()
    }
}