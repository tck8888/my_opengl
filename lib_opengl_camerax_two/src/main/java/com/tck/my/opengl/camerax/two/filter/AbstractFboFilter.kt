package com.tck.my.opengl.camerax.two.filter

import android.content.Context

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
    override fun beforeDraw() {
        TODO("Not yet implemented")
    }

}