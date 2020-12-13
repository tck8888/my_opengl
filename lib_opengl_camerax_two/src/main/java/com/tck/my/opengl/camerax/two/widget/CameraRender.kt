package com.tck.my.opengl.camerax.two.widget

import android.graphics.SurfaceTexture
import android.opengl.EGL14
import android.opengl.GLSurfaceView
import androidx.camera.core.Preview
import androidx.lifecycle.LifecycleOwner
import com.tck.my.opengl.camerax.two.filter.CameraFilter
import com.tck.my.opengl.camerax.two.filter.ScreenFilter
import com.tck.my.opengl.camerax.two.record.MediaRecorder
import com.tck.my.opengl.camerax.two.utils.CameraHelper
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

    private var cameraFilter: CameraFilter? = null
    private var screenFilter: ScreenFilter? = null

    private var mtx = FloatArray(16)

    private var mediaRecorder: MediaRecorder? = null

    init {
        cameraXView.context as LifecycleOwner
        cameraHelper = CameraHelper(cameraXView.context as LifecycleOwner, this)
    }

    fun onSurfaceDestroyed() {
        cameraFilter?.release()
        screenFilter?.release()
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        surfaceTexture?.attachToGLContext(textures[0])
        // 当摄像头数据有更新回调 onFrameAvailable
        surfaceTexture?.setOnFrameAvailableListener(this)

        cameraFilter = CameraFilter(cameraXView.context)
        screenFilter = ScreenFilter(cameraXView.context)

        mediaRecorder= MediaRecorder(cameraXView.context,"", EGL14.eglGetCurrentContext(),480f, 640f)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        cameraFilter?.setSize(width, height)
        screenFilter?.setSize(width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        surfaceTexture?.updateTexImage()
        surfaceTexture?.getTransformMatrix(mtx)
        cameraFilter?.setTransformMatrix(mtx)
        val id = cameraFilter?.onDraw(textures[0])
        id?.let {
            screenFilter?.onDraw(it)
        }
        mediaRecorder?.fireFrame(id,surfaceTexture?.timestamp)

    }

    override fun onUpdated(output: Preview.PreviewOutput?) {
        surfaceTexture = output?.surfaceTexture
    }

    override fun onFrameAvailable(surfaceTexture: SurfaceTexture?) {
        cameraXView.requestRender()
    }
}