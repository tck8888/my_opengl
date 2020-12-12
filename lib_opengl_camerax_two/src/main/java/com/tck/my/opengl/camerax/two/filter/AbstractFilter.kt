package com.tck.my.opengl.camerax.two.filter

import android.content.Context
import android.opengl.GLES20
import com.tck.my.opengl.camerax.two.utils.OpenGLUtils
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 *
 * description:

 * @date 2020/12/12 23:05

 * @author tck88
 *
 * @version v1.0.0
 *
 */
abstract class AbstractFilter(context: Context, vertexShaderRawId: Int, fragmentShaderRawId: Int) {

    //顶点坐标缓存区
    val vertexBuffer: FloatBuffer

    //纹理坐标
    val textureBuffer: FloatBuffer

    var width = 0
    var height = 0

    var program = 0
    var vPosition = 0
    var vCoord = 0
    var vTexture = 0

    init {
        vertexBuffer = ByteBuffer.allocateDirect(4 * 4 * 2).order(ByteOrder.nativeOrder())
            .asFloatBuffer()

        textureBuffer = ByteBuffer.allocateDirect(4 * 4 * 2).order(ByteOrder.nativeOrder())
            .asFloatBuffer()

        initCoord()

        initGL(context, vertexShaderRawId, fragmentShaderRawId)
    }

    private fun initCoord() {
        vertexBuffer.clear()
        vertexBuffer.put(OpenGLUtils.VERTEX)

        textureBuffer.clear()
        textureBuffer.put(OpenGLUtils.TEXURE)
    }

    private fun initGL(context: Context, vertexShaderRawId: Int, fragmentShaderRawId: Int) {
        program = OpenGLUtils.loadProgram(context, vertexShaderRawId, fragmentShaderRawId)
        vPosition = GLES20.glGetAttribLocation(program, "vPosition")
        vCoord = GLES20.glGetAttribLocation(program, "vCoord")
        vTexture = GLES20.glGetUniformLocation(program, "vTexture")
    }

    fun setSize(width: Int, height: Int) {
        this.width = width
        this.height = height
    }

    fun onDraw(texture: Int): Int {
        GLES20.glViewport(0, 0, width, height)

        GLES20.glUseProgram(program)

        vertexBuffer.position(0)
        GLES20.glVertexAttribPointer(vPosition, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer)
        GLES20.glEnableVertexAttribArray(vPosition)

        textureBuffer.position(0)
        GLES20.glVertexAttribPointer(vCoord, 2, GLES20.GL_FLOAT, false, 0, textureBuffer)
        GLES20.glEnableVertexAttribArray(vCoord)

        //相当于激活一个用来显示图片的画框
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture)
        // 0: 图层ID  GL_TEXTURE0
        // GL_TEXTURE1 ， 1
        GLES20.glUniform1i(vTexture, 0)

        beforeDraw()

        //通知画画，
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)

        return texture
    }

    abstract fun beforeDraw()

    fun release(){
        GLES20.glDeleteProgram(program)
    }

}