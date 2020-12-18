package com.tck.my.opengl.one.texture

import android.content.Context
import android.util.AttributeSet
import com.tck.my.opengl.one.TGLSurfaceView

/**
 *
 * description:

 * @date 2020/12/18 22:01

 * @author tck88
 *
 * @version v1.0.0
 *
 */
class TGLTextureView: TGLSurfaceView{

    constructor(context: Context) : super(context) {
        initRender(context,null,0)
    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        initRender(context,attributeSet,0)
    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(
        context,
        attributeSet,
        defStyle
    ) {
        initRender(context,attributeSet,defStyle)
    }
    private fun initRender(context: Context, attributeSet: AttributeSet?=null, defStyle: Int=0) {
        setRender(TTextureRender(context))
    }
}