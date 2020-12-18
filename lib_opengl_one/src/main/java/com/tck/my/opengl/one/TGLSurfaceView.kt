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
open class TGLSurfaceView : SurfaceView, SurfaceHolder.Callback {

    companion object {
        val RENDERMODE_WHEN_DIRTY = 0
        val RENDERMODE_CONTINUOUSLY = 1
    }

    var surface: Surface? = null
    var teglContext: EGLContext? = null
    var mTGLRender: TGLRender? = null
    private var teglThread: TEGLThread? = null


    var mRenderMode = RENDERMODE_CONTINUOUSLY

    constructor(context: Context) : super(context) {
        initHolder(context,null,0)
    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        initHolder(context,attributeSet,0)
    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(
        context,
        attributeSet,
        defStyle
    ) {
        initHolder(context,attributeSet,defStyle)
    }

    private fun initHolder(context: Context, attributeSet: AttributeSet?=null, defStyle: Int=0) {
        holder.addCallback(this)
    }

    fun setRender(tglRender: TGLRender?) {
        this.mTGLRender = tglRender
    }

    fun setRenderMode(renderMode: Int) {
        if (mTGLRender == null) {
            throw   RuntimeException("must set render before")
        }
        this.mRenderMode = renderMode
    }

    fun setSurfaceAndEglContext(surface: Surface?, eglContext: EGLContext?) {
        this.surface = surface
        this.teglContext = eglContext
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
        teglContext = null
    }
}