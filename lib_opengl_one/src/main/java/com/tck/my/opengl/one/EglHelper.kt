package com.tck.my.opengl.one


import android.view.Surface
import com.tck.my.opengl.base.MyLog
import javax.microedition.khronos.egl.*

/**
 *
 * description:

 * @date 2020/12/12 13:21

 * @author tck88
 *
 * @version v1.0.0
 *
 */
class EglHelper {

    private var egl: EGL10? = null
    var eglContext: EGLContext? = null
    private var eglDisplay: EGLDisplay? = null
    private var eglSurface: EGLSurface? = null

    fun initEgl(surface: Surface, context: EGLContext?) {

        //1.得到Egl实例
        val tempEgl = EGLContext.getEGL() as? EGL10
        if (tempEgl == null) {
            MyLog.d("tempEgl==null")
            return
        }
        //2、得到默认的显示设备（就是窗口）
        val eglGetDisplay = tempEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY)
        if (eglGetDisplay == EGL10.EGL_NO_DISPLAY) {
            throw  RuntimeException("eglGetDisplay failed")
        }
        //3、初始化默认显示设备
        val version = IntArray(2)
        if (!tempEgl.eglInitialize(eglGetDisplay, version)) {
            throw  RuntimeException("eglInitialize failed")
        }
        //4、设置显示设备的属性
        val attrbutes = intArrayOf(
            EGL10.EGL_RED_SIZE, 8,
            EGL10.EGL_GREEN_SIZE, 8,
            EGL10.EGL_BLUE_SIZE, 8,
            EGL10.EGL_ALPHA_SIZE, 8,
            EGL10.EGL_DEPTH_SIZE, 8,
            EGL10.EGL_STENCIL_SIZE, 8,
            EGL10.EGL_RENDERABLE_TYPE, 4,
            EGL10.EGL_NONE
        )

        val num_configs = IntArray(1)
        if (!tempEgl.eglChooseConfig(eglGetDisplay, attrbutes, null, 1, num_configs)) {
            throw  IllegalArgumentException("eglChooseConfig failed")
        }
        val num_config = num_configs[0]
        if (num_config < 0) {
            throw IllegalArgumentException("No configs match configSpec")
        }
        //5、从系统中获取对应属性的配置
        val configs = arrayOfNulls<EGLConfig>(num_config)
        if (!tempEgl.eglChooseConfig(
                eglGetDisplay, attrbutes, configs, num_config,
                num_configs
            )
        ) {
            throw  IllegalArgumentException("eglChooseConfig failed #2")
        }
        //6、创建EglContext
        eglContext = if (context != null) {
            tempEgl.eglCreateContext(eglGetDisplay, configs[0], context, null)
        } else {
            tempEgl.eglCreateContext(eglGetDisplay, configs[0], EGL10.EGL_NO_CONTEXT, null)
        }
        //7、创建渲染的Surface
        eglSurface = tempEgl.eglCreateWindowSurface(eglGetDisplay, configs[0], surface, null)
        //8、绑定EglContext和Surface到显示设备中
        if (!tempEgl.eglMakeCurrent(eglGetDisplay, eglSurface, eglSurface,eglContext)) {
            throw  RuntimeException("eglMakeCurrent fail")
        }
        egl = tempEgl
        eglDisplay = eglGetDisplay
        //9、刷新数据，显示渲染场景
    }


    fun swapBuffers(): Boolean {
        if (egl != null) {
            return egl!!.eglSwapBuffers(eglDisplay, eglSurface)
        } else {
            throw RuntimeException("egl is null")
        }
    }

    fun destoryEgl() {
        val tempEgl = egl ?: return

        tempEgl.eglMakeCurrent(
            eglDisplay, EGL10.EGL_NO_SURFACE,
            EGL10.EGL_NO_SURFACE,
            EGL10.EGL_NO_CONTEXT
        )

        tempEgl.eglDestroySurface(eglDisplay, eglSurface)
        eglSurface = null

        tempEgl.eglDestroyContext(eglDisplay, eglContext)
        eglContext = null

        tempEgl.eglTerminate(eglDisplay)
        eglDisplay = null

        egl = null
    }

}