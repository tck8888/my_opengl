package com.tck.my.opengl.one.texture

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.opengl.GLUtils
import com.tck.my.opengl.base.MyOpenGLUtils
import com.tck.my.opengl.one.R
import com.tck.my.opengl.one.TGLRender
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 *
 * description:

 * @date 2020/12/18 22:15

 * @author tck88
 *
 * @version v1.0.0
 *
 */
class TTextureRender(val context: Context) : TGLRender {
    private val vertexData = floatArrayOf(
        -1f, -1f,
        1f, -1f,
        -1f, 1f,
        1f, 1f
    )
    private var vertexBuffer: FloatBuffer

    private val fragmentData = floatArrayOf(
        //            0f, 1f,
        //            1f, 1f,
        //            0f, 0f,
        //            1f, 0f
        0f, 0.5f,
        0.5f, 0.5f,
        0f, 0f,
        0.5f, 0f
    )
    private var fragmentBuffer: FloatBuffer


    init {
        vertexBuffer = ByteBuffer
            .allocateDirect(vertexData.size * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(vertexData)
        vertexBuffer.position(0)

        fragmentBuffer = ByteBuffer
            .allocateDirect(fragmentData.size * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(fragmentData)
        fragmentBuffer.position(0)
    }

    private var program: Int = 0
    private var vPosition: Int = 0
    private var fPosition: Int = 0
    private var sampler: Int = 0
    private var textureid: Int = 0

    override fun onSurfaceCreated() {
        program = MyOpenGLUtils.createProgram(context, R.raw.vertex_shader, R.raw.fragment_shader)
        vPosition =  GLES20.glGetAttribLocation(program,"v_Position")
        fPosition =  GLES20.glGetAttribLocation(program,"f_Position")
        sampler =  GLES20.glGetUniformLocation(program,"sTexture")

        val textureIds = IntArray(1)
        GLES20.glGenTextures(1,textureIds,0)
        textureid=textureIds[0]

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureid)

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glUniform1i(sampler, 0)

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT)

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)

        var bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_baseline_5g_24)
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)

        bitmap.recycle()
        bitmap=null

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
    }

    override fun onSurfaceChanged(width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame() {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        GLES20.glClearColor(1f,0f, 0f, 1f)

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureid)

        GLES20.glEnableVertexAttribArray(vPosition)
        GLES20.glVertexAttribPointer(vPosition, 2, GLES20.GL_FLOAT, false, 8,
            vertexBuffer)

        GLES20.glEnableVertexAttribArray(fPosition);
        GLES20.glVertexAttribPointer(fPosition, 2, GLES20.GL_FLOAT, false, 8,
            fragmentBuffer)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
    }
}