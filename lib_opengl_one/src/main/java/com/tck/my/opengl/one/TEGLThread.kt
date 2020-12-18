package com.tck.my.opengl.one

import com.tck.my.opengl.base.MyLog
import com.tck.my.opengl.one.TGLSurfaceView.Companion.RENDERMODE_CONTINUOUSLY
import com.tck.my.opengl.one.TGLSurfaceView.Companion.RENDERMODE_WHEN_DIRTY
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
class TEGLThread(var weakReference: WeakReference<TGLSurfaceView>) : Thread() {

    var isExit = false
    var isCreate = false
    var isChange = false
    var isStart = false
    var width = 0
    var height = 0
    private var eglHelper: EglHelper? = null
    private val objects = Object()

    override fun run() {
        super.run()
        isExit = false
        isStart = false

        val tGLSurfaceView = weakReference.get() ?: return
        val surface = tGLSurfaceView.surface ?: return

        val tempEglHelper = EglHelper()
        eglHelper = tempEglHelper

        tempEglHelper.initEgl(surface, tGLSurfaceView.teglContext)

        while (true) {
            if (isExit) {
                //释放资源
                release()
                break
            }
            if (isStart) {
                when (tGLSurfaceView.mRenderMode) {
                    RENDERMODE_WHEN_DIRTY -> {
                        synchronized(objects) {
                            try {
                                objects.wait()
                            } catch (e: InterruptedException) {
                                MyLog.d("TEGLThread:${e.message}")
                            }
                        }
                    }
                    RENDERMODE_CONTINUOUSLY -> {
                        try {
                            sleep((1000 / 60).toLong())
                        } catch (e: InterruptedException) {
                            MyLog.d("TEGLThread:${e.message}")
                        }
                    }
                    else -> {
                        throw   RuntimeException("mRenderMode is wrong value")
                    }
                }
            }
            onCreate()
            onChange(width, height);
            onDraw()

            isStart = true
        }
    }

    private fun onCreate() {
        if (isCreate && weakReference.get()?.mTGLRender != null) {
            isCreate = false
            weakReference.get()?.mTGLRender?.onSurfaceCreated()
        }
    }

    private fun onChange(width: Int, height: Int) {
        if (isChange && weakReference.get()?.mTGLRender != null) {
            isChange = false
            weakReference.get()?.mTGLRender?.onSurfaceChanged(width, height)
        }
    }

    private fun onDraw() {
        if (eglHelper != null && weakReference.get()?.mTGLRender != null) {
            weakReference.get()?.mTGLRender?.onDrawFrame()
            if (!isStart) {
                weakReference.get()?.mTGLRender?.onDrawFrame()
            }
            eglHelper?.swapBuffers()
        }
    }

    fun release() {
        eglHelper?.destoryEgl()
        eglHelper = null
    }


    fun getEglContext(): EGLContext? {
        return eglHelper?.eglContext
    }

    fun requestRender() {
        synchronized(objects) {
            objects.notifyAll()
        }
    }

    fun onDestroy() {
        isExit = true
        requestRender()
    }
}