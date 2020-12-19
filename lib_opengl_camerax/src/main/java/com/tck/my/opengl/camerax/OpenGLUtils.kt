 package com.tck.my.opengl.camerax

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

    fun readRawTextFile(context: Context, rawId: Int): String {
        val sb = StringBuilder()
        context.resources.openRawResource(rawId).use {
            InputStreamReader(it).use { inputStreamReader ->
                BufferedReader(inputStreamReader).use { bufferedReader ->
                    var line: String?
                    do {
                        line = bufferedReader.readLine()
                        if (line == null) {
                            break
                        }
                        sb.append(line).append("\n")
                    } while (true)
                }
            }
        }
        return sb.toString()
    }

    fun readRawTextFile2(context: Context, rawId: Int): String {
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

}