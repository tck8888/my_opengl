package com.tck.my.opengl.one.camera

import android.content.Context
import android.graphics.SurfaceTexture
import android.hardware.Camera
import android.util.AttributeSet
import com.tck.my.opengl.one.TGLSurfaceView

/**
 *
 * description:

 * @date 2020/12/22 22:37

 * @author tck88
 *
 * @version v1.0.0
 *
 */
class CameraView : TGLSurfaceView {

    private lateinit var cameraRender: CameraRender
    private lateinit var cameraControl: CameraControl

    private val cameraId: Int = Camera.CameraInfo.CAMERA_FACING_BACK

    constructor(context: Context) : super(context) {
        initRender(context, null, 0)
    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        initRender(context, attributeSet, 0)
    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(
        context,
        attributeSet,
        defStyle
    ) {
        initRender(context, attributeSet, defStyle)
    }

    private fun initRender(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyle: Int = 0
    ) {
        cameraRender = CameraRender(context)
        cameraControl = CameraControl()
        setRender(cameraRender)

        cameraRender.setOnSurfaceCreateListener(object : OnSurfaceCreateListener {
            override fun onSurfaceCreate(surfaceTexture: SurfaceTexture) {
                cameraControl.initCamera(surfaceTexture, cameraId)
            }
        })
    }

    fun onDestroy() {
        cameraControl.stopPreview()
    }
}