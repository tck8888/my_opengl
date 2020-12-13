package com.tck.my.opengl.camerax.two.filter

import android.content.Context
import android.opengl.GLES20
import com.tck.my.opengl.camerax.two.utils.OpenGLUtils

/**
 *
 * description:

 * @date 2020/12/12 23:05

 * @author tck88
 *
 * @version v1.0.0
 *
 */
class AbstractFboFilter(
    context: Context,
    vertexShaderRawId: Int,
    fragmentShaderRawId: Int
) : AbstractFilter(context, vertexShaderRawId, fragmentShaderRawId) {

    private val frameBuffer = IntArray(1)
    private val frameTextures = IntArray(1)


    override fun setSize(width: Int, height: Int) {
        super.setSize(width, height)
        releaseFrame()

        GLES20.glGenFramebuffers(1, frameBuffer, 0)
        OpenGLUtils.glGenTextures(frameTextures)
        /**
         * 2、fbo与纹理关联
         */
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, frameTextures[0])

        GLES20.glTexImage2D(
            GLES20.GL_TEXTURE_2D,
            0,
            GLES20.GL_RGBA,
            width,
            height,
            0,
            GLES20.GL_RGBA,
            GLES20.GL_UNSIGNED_BYTE,
            null
        )

        //纹理关联 fbo
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, frameBuffer[0])

        GLES20.glFramebufferTexture2D(
            GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D,
            frameTextures[0],
            0
        )

        /**
         * 3、解除绑定
         */
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0)

    }

    override fun release() {
        super.release()
        releaseFrame()
    }

    private fun releaseFrame() {
        GLES20.glDeleteTextures(1, frameTextures, 0)
        GLES20.glDeleteFramebuffers(1, frameBuffer, 0)
    }

    override fun onDraw(texture: Int): Int {
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, frameBuffer[0])//綁定fbo
        super.onDraw(texture)
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0)
        return frameTextures[0]
    }


}