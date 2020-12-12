package com.tck.my.opengl.camerax

import android.os.HandlerThread
import android.util.Size
import androidx.camera.core.CameraX
import androidx.camera.core.Preview
import androidx.camera.core.PreviewConfig
import androidx.lifecycle.LifecycleOwner

/**
 *
 * description:

 * @date 2020/12/12 16:49

 * @author tck88
 *
 * @version v1.0.0
 *
 */
class CameraHelper(
    lifecycleOwner: LifecycleOwner,
    private val listener: Preview.OnPreviewOutputUpdateListener
) {
    private val handlerThread: HandlerThread = HandlerThread("Analyze-thread")

    init {
        handlerThread.start()
        CameraX.bindToLifecycle(lifecycleOwner, getPreView())
    }

    private fun getPreView(): Preview {
        val previewConfig = PreviewConfig.Builder().setTargetResolution(Size(640, 480))
            .setLensFacing(CameraX.LensFacing.BACK).build()

        return Preview(previewConfig).apply {
            onPreviewOutputUpdateListener = listener
        }
    }
}