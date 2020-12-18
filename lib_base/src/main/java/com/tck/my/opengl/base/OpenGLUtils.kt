package com.tck.my.opengl.base

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
    fun loadProgram(vertexSource: String, fragmentSource: String): Int {
        /**
         * 顶点着色器
         */
        val vertexShader = loadShader(vertexSource, GLES20.GL_VERTEX_SHADER)
        val fragmentShader = loadShader(fragmentSource, GLES20.GL_FRAGMENT_SHADER)
        if (vertexShader == 0 || fragmentShader == 0) {
            return 0
        }

        /**
         * 创建着色器程序
         */
        val program = GLES20.glCreateProgram()

        //绑定顶点和片元
        GLES20.glAttachShader(program, vertexShader)
        GLES20.glAttachShader(program, fragmentShader)

        //链接着色器程序
        GLES20.glLinkProgram(program)
        //查看配置 是否成功
        val status = IntArray(1)
        GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, status, 0)
        if (status[0] != GLES20.GL_TRUE) {
            MyLog.d("link program error:${GLES20.glGetProgramInfoLog(program)}")
            return 0
        }
        return program
    }

    private fun loadShader(source: String, shaderType: Int): Int {
        val shader = GLES20.glCreateShader(shaderType)
        if (shader != 0) {
            //加载shader代码
            GLES20.glShaderSource(shader, source)
            //编译shader
            GLES20.glCompileShader(shader)
            //检查shader 是否编译成功
            val status = IntArray(1)
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, status, 0)
            if (status[0] != GLES20.GL_TRUE) {
                val errorMsg = if (shaderType == GLES20.GL_VERTEX_SHADER) {
                    "load vertex shader:${GLES20.glGetShaderInfoLog(shader)}"
                } else {
                    "load fragment shader:${GLES20.glGetShaderInfoLog(shader)}"
                }
                MyLog.d(errorMsg)
                GLES20.glDeleteShader(shader)
                return 0
            }
            return shader
        }
        return 0

    }

}