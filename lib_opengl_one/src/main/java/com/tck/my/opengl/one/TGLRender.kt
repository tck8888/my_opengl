package com.tck.my.opengl.one

/**
 *
 * description:

 * @date 2020/12/16 23:04

 * @author tck88
 *
 * @version v1.0.0
 *
 */
interface TGLRender {
    fun onSurfaceCreated()

    fun onSurfaceChanged(width:Int,height:Int)

    fun onDrawFrame()
}