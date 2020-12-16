package com.tck.my.opengl.one

import java.lang.ref.WeakReference
import javax.microedition.khronos.egl.EGLContext

/**
 *
 * description:

 * @date 2020/12/16 23:04

 * @author tck88
 *
 * @version v1.0.0
 *
 */
class TEGLThread(val weakReference: WeakReference<TGLSurfaceView>) : Thread() {

    var isExit = false
    var isCreate = false
    var isChange = false
    var isStart = false
    var width = 0
    var height = 0
    private var eglHelper: EglHelper? = null
    private val `object` = Any()
    override fun run() {
        super.run()
        isExit = false
        isStart = false

        eglHelper = EglHelper()
    }

    fun getEglContext(): EGLContext {
        TODO("Not yet implemented")
    }

    fun requestRender() {
        TODO("Not yet implemented")
    }

    fun onDestroy() {
        TODO("Not yet implemented")
    }
}