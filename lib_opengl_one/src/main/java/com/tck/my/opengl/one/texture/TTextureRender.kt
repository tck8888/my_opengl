package com.tck.my.opengl.one.texture

import android.content.Context
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
private var vertexBuffer:FloatBuffer

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
    private var fragmentBuffer:FloatBuffer
    init {
        vertexBuffer= ByteBuffer
            .allocateDirect(vertexData.size * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(vertexData)
        vertexBuffer.position(0)

        fragmentBuffer= ByteBuffer
            .allocateDirect(fragmentData.size * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(fragmentData)
        fragmentBuffer.position(0)
    }

    override fun onSurfaceCreated() {
        TODO("Not yet implemented")
    }

    override fun onSurfaceChanged(width: Int, height: Int) {
        TODO("Not yet implemented")
    }

    override fun onDrawFrame() {
        TODO("Not yet implemented")
    }
}