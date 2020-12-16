package com.tck.my.opengl.one

import android.content.Context
import android.util.AttributeSet
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.lang.ref.WeakReference
import javax.microedition.khronos.egl.EGLContext

/**
 *
 * description:

 * @date 2020/12/16 22:59

 * @author tck88
 *
 * @version v1.0.0
 *
 */
class TGLSurfaceView : SurfaceView, SurfaceHolder.Callback {

    companion object {
        val RENDERMODE_WHEN_DIRTY = 0
        val RENDERMODE_CONTINUOUSLY = 1
    }

    private var surface: Surface? = null
    private var eglContext: EGLContext? = null
    private var tglRender: TGLRender? = null
    private var teglThread: TEGLThread? = null


    private var renderMode = RENDERMODE_CONTINUOUSLY

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(
        context,
        attributeSet,
        defStyle
    ) {
        holder.addCallback(this)
    }

    fun setRender(tglRender: TGLRender?) {
        this.tglRender = tglRender
    }

    fun setRenderMode(renderMode: Int) {
        if (tglRender == null) {
            throw   RuntimeException("must set render before")
        }
        this.renderMode = renderMode
    }

    fun setSurfaceAndEglContext(surface: Surface?, eglContext: EGLContext?) {
        this.surface = surface
        this.eglContext = eglContext
    }

    fun getEglContext(): EGLContext? {
        return teglThread?.getEglContext()
    }

    fun requestRender() {
        teglThread?.requestRender()
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        val tempSurface = surface
        if (tempSurface == null) {
            surface = holder.surface
        }
        teglThread = TEGLThread(WeakReference<TGLSurfaceView>(this))
        teglThread?.isCreate = true
        teglThread?.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        teglThread?.width = width
        teglThread?.height = height
        teglThread?.isChange = true
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        teglThread?.onDestroy()
        teglThread = null
        surface = null
        eglContext = null
    }
}