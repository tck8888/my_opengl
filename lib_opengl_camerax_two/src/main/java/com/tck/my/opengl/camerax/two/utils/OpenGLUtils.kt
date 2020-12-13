package com.tck.my.opengl.camerax.two.utils

import android.content.Context
import android.opengl.GLES20
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

/**
 *
 * description:

 * @date 2020/12/12 19:32

 * @author tck88
 *
 * @version v1.0.0
 *
 */
object OpenGLUtils {

    val VERTEX = floatArrayOf(
        -1.0f, -1.0f,
        1.0f, -1.0f,
        -1.0f, 1.0f,
        1.0f, 1.0f
    )

    val TEXURE = floatArrayOf(
        0.0f, 0.0f,
        1.0f, 0.0f,
        0.0f, 1.0f,
        1.0f, 1.0f,
    )

    fun readRawTextFile(context: Context, rawId: Int): String {
        val sb = StringBuilder()
        context.resources.openRawResource(rawId).use {
            InputStreamReader(it).use { inputStreamReader ->
                BufferedReader(inputStreamReader).useLines { lines ->
                    lines.forEach { line ->
                        sb.append(line).append("\n")
                    }
                }
            }
        }
        return sb.toString()
    }

    @Throws(IllegalStateException::class)
    fun loadProgram(context: Context, vertexShaderRawId: Int, fragShaderRawId: Int): Int {
        val vertexShader = readRawTextFile(context, vertexShaderRawId)
        val fragShader = readRawTextFile(context, fragShaderRawId)
        return loadProgram(vertexShader, fragShader)
    }

    @Throws(IllegalStateException::class)
    fun loadProgram(vertexShader: String, fragShader: String): Int {
        /**
         * 顶点着色器
         */
        val vShader = loadShader(vertexShader, GLES20.GL_VERTEX_SHADER)
        val fShader = loadShader(fragShader, GLES20.GL_FRAGMENT_SHADER)

        /**
         * 创建着色器程序
         */
        val program = GLES20.glCreateProgram()

        //绑定顶点和片元
        GLES20.glAttachShader(program, vShader)
        GLES20.glAttachShader(program, fShader)

        //链接着色器程序
        GLES20.glLinkProgram(program)
        //查看配置 是否成功
        val status = IntArray(1)
        GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, status, 0)
        if (status[0] != GLES20.GL_TRUE) {
            throw IllegalStateException("link program:${GLES20.glGetProgramInfoLog(program)}")
        }
        GLES20.glDeleteShader(vShader)
        GLES20.glDeleteShader(fShader)
        return program
    }

    @Throws(IllegalStateException::class)
    private fun loadShader(source: String, type: Int): Int {
        val shader = GLES20.glCreateShader(type)
        //加载着色器代码
        GLES20.glShaderSource(shader, source)
        //编译（配置）
        GLES20.glCompileShader(shader)
        //查看配置 是否成功
        val status = IntArray(1)
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, status, 0)
        if (status[0] != GLES20.GL_TRUE) {
            val errorMsg = if (type == GLES20.GL_VERTEX_SHADER) {
                "load vertex shader:${GLES20.glGetShaderInfoLog(shader)}"
            } else {
                "load fragment shader:${GLES20.glGetShaderInfoLog(shader)}"
            }
            throw IllegalStateException(errorMsg)
        }
        return shader
    }

    fun glGenTextures(textures: IntArray) {
        GLES20.glGenTextures(textures.size, textures, 0)
        textures.forEach {
            //绑定纹理，后续配置纹理
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, it)
            /**
             *  必须：设置纹理过滤参数设置
             */
            /*设置纹理缩放过滤*/
            // GL_NEAREST: 使用纹理中坐标最接近的一个像素的颜色作为需要绘制的像素颜色
            // GL_LINEAR:  使用纹理中坐标最接近的若干个颜色，通过加权平均算法得到需要绘制的像素颜色
            // 后者速度较慢，但视觉效果好
            //放大过滤
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_LINEAR)
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_LINEAR);//缩小过滤
            /**
             * 可选：设置纹理环绕方向
             */
            //纹理坐标的范围是0-1。超出这一范围的坐标将被OpenGL根据GL_TEXTURE_WRAP参数的值进行处理
            //GL_TEXTURE_WRAP_S, GL_TEXTURE_WRAP_T 分别为x，y方向。
            //GL_REPEAT:平铺
            //GL_MIRRORED_REPEAT: 纹理坐标是奇数时使用镜像平铺
            //GL_CLAMP_TO_EDGE: 坐标超出部分被截取成0、1，边缘拉伸
//            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
//                    GLES20.GL_CLAMP_TO_EDGE);
//            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
//                    GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0)
        }
    }


}