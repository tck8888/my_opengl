package com.tck.my.opengl.camerax.two.filter

import android.content.Context
import android.opengl.GLES20
import com.tck.my.opengl.camerax.two.R

/**
 *
 * description:

 * @date 2020/12/13 10:32

 * @author tck88
 *
 * @version v1.0.0
 *
 */
class CameraFilter(context: Context) :
    AbstractFilter(context, R.raw.camera_vert, R.raw.camera_frag) {

    private var mtx: FloatArray? = null
    private var vMatrix: Int = 0

    override fun initGL(context: Context, vertexShaderRawId: Int, fragmentShaderRawId: Int) {
        super.initGL(context, vertexShaderRawId, fragmentShaderRawId)
        vMatrix = GLES20.glGetUniformLocation(program, "vMatrix")
    }

    override fun beforeDraw() {
        super.beforeDraw()
        GLES20.glUniformMatrix4fv(vMatrix, 1, false, mtx, 0)
    }

    fun setTransformMatrix(mtx: FloatArray) {
        this.mtx = mtx
    }
}