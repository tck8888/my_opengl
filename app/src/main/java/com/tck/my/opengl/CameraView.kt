package com.tck.my.opengl

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.view.SurfaceHolder

/**
 *
 * description:

 * @date 2020/12/8 21:28

 * @author tck88
 *
 * @version v1.0.0
 *
 */
class CameraView:GLSurfaceView {

    private val renderer:CameraRender

    constructor(context:Context):this(context,null)
    constructor(context:Context,attrs: AttributeSet?) : super(context,attrs) {
       setEGLContextClientVersion(2)
        renderer= CameraRender(this)
        setRenderer(renderer)

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        super.surfaceDestroyed(holder)
        renderer.onSurfaceDestroyed()
    }
}