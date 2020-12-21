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
class TTextureRenderWithVBO(val context: Context) : TGLRender {
    private val vertexData = floatArrayOf(
            -1f, -1f,
            1f, -1f,
            -1f, 1f,
            1f, 1f
    )
    private var vertexBuffer: FloatBuffer

    private val fragmentData = floatArrayOf(
            0f, 1f,
            1f, 1f,
            0f, 0f,
            1f, 0f
//        0f, 0.5f,
//        0.5f, 0.5f,
//        0f, 0f,
//        0.5f, 0f
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

    private var vboId = 0


    override fun onSurfaceCreated() {
        program = MyOpenGLUtils.createProgram(context, R.raw.vertex_shader, R.raw.fragment_shader)
        vPosition = GLES20.glGetAttribLocation(program, "v_Position")
        fPosition = GLES20.glGetAttribLocation(program, "f_Position")
        sampler = GLES20.glGetUniformLocation(program, "sTexture")

        //1. 创建vbo
        val vbos = IntArray(1)
        GLES20.glGenBuffers(1, vbos, 0)
        vboId = vbos[0]
        //2.绑定VBO
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboId)
        //3.分配VBO需要的缓存大小
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexData.size * 4 + fragmentData.size * 4, null, GLES20.GL_STATIC_DRAW)
        //4.为VBO设置顶点数据的值
        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, 0, vertexData.size * 4, vertexBuffer)
        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, vertexData.size * 4, fragmentData.size * 4, fragmentBuffer)
        //5、解绑VBO
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)


        val textureIds = IntArray(1)
        GLES20.glGenTextures(1, textureIds, 0)
        textureid = textureIds[0]

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureid)

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glUniform1i(sampler, 0)

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT)

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)

        var bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.androids)
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)

        bitmap.recycle()
        bitmap = null

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
    }

    override fun onSurfaceChanged(width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame() {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        GLES20.glClearColor(1f, 0f, 0f, 1f)

        GLES20.glUseProgram(program)

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureid)

        //绑定VBO
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboId)

        GLES20.glEnableVertexAttribArray(vPosition)
        GLES20.glVertexAttribPointer(vPosition, 2, GLES20.GL_FLOAT, false, 8,
                0)

        GLES20.glEnableVertexAttribArray(fPosition)
        GLES20.glVertexAttribPointer(fPosition, 2, GLES20.GL_FLOAT, false, 8,
                vertexData.size * 4)

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)

        //解绑VBO
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)
    }
}