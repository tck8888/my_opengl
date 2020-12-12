package com.tck.my.opengl.camerax

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.view.SurfaceHolder

/**
 *
 * description:

 * @date 2020/12/12 16:33

 * @author tck88
 *
 * @version v1.0.0
 *
 */
class CameraXView : GLSurfaceView {
    private lateinit var cameraRender: CameraRender

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        setEGLContextClientVersion(2)
        cameraRender = CameraRender(this)
        setRenderer(cameraRender)
        renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        super.surfaceCreated(holder)
        cameraRender.onSurfaceDestroyed()
    }
}