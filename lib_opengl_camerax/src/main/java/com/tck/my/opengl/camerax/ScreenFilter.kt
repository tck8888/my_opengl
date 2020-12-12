package com.tck.my.opengl.camerax

import android.content.Context
import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 *
 * description:

 * @date 2020/12/12 19:25

 * @author tck88
 *
 * @version v1.0.0
 *
 */
class ScreenFilter(context: Context) {

    private val vertexBuffer: FloatBuffer
    private val textureBuffer: FloatBuffer

    private var program = 0

    private var width = 0
    private var height = 0

    private var vPosition = 0
    private var vCoord = 0
    private var vTexture = 0
    private var vMatrix = 0

    private var mtx: FloatArray? = null


    init {
        vertexBuffer =
            ByteBuffer.allocateDirect(4 * 4 * 2).order(ByteOrder.nativeOrder()).asFloatBuffer()

        val VERTEX = floatArrayOf(
            -1.0f, -1.0f,
            1.0f, -1.0f,
            -1.0f, 1.0f,
            1.0f, 1.0f
        )

        vertexBuffer.clear()
        vertexBuffer.put(VERTEX)

        textureBuffer = ByteBuffer.allocateDirect(4 * 4 * 2).order(ByteOrder.nativeOrder())
            .asFloatBuffer()

        val TEXTURE = floatArrayOf(
            0.0f, 0.0f,
            1.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f
        )

        textureBuffer.clear()
        textureBuffer.put(TEXTURE)

        val vertexShader: String = OpenGLUtils.readRawTextFile(context, R.raw.camerax_vert)
        val fragShader: String = OpenGLUtils.readRawTextFile(context, R.raw.camerax_frag)

        program = OpenGLUtils.loadProgram(vertexShader, fragShader)

        vPosition =  GLES20.glGetAttribLocation(program, "vPosition")
        vCoord = GLES20.glGetAttribLocation(program, "vCoord")
        vTexture = GLES20.glGetUniformLocation(program, "vTexture")
        vMatrix = GLES20.glGetUniformLocation(program, "vMatrix")
    }

    fun setSize(width: Int, height: Int) {
        this.width = width
        this.height = height
    }

    fun setTransformMatrix(mtx: FloatArray) {
        this.mtx = mtx
    }

    fun onDraw(texture: Int) {
        GLES20.glViewport(0, 0, width, height)

        GLES20.glUseProgram(program)

        vertexBuffer.position(0)

        GLES20.glVertexAttribPointer(vPosition, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer)

        GLES20.glEnableVertexAttribArray(vPosition)

        textureBuffer.position(0)
        GLES20.glVertexAttribPointer(vCoord, 2, GLES20.GL_FLOAT, false, 0, textureBuffer)
        GLES20.glEnableVertexAttribArray(vCoord)

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,texture)

        // 0: 图层ID  GL_TEXTURE0
        // GL_TEXTURE1 ， 1
        GLES20.glUniform1i(vTexture,0)

        GLES20.glUniformMatrix4fv(vMatrix, 1, false, mtx, 0)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,4)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0)
    }
}