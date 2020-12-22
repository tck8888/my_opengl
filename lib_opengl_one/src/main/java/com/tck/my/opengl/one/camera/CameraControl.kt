package com.tck.my.opengl.one.camera

import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.Camera

/**
 *
 * description:

 * @date 2020/12/22 22:38

 * @author tck88
 *
 * @version v1.0.0
 *
 */
class CameraControl {

    private var surfaceTexture: SurfaceTexture? = null
    private var camera: Camera? = null

    fun initCamera(surfaceTexture: SurfaceTexture, cameraId: Int) {
        this.surfaceTexture = surfaceTexture
        setCameraParam(cameraId)
    }

    private fun setCameraParam(cameraId: Int) {
        try {
            camera = Camera.open(cameraId)
            camera?.setPreviewTexture(surfaceTexture)
            val parameters = camera?.parameters
            parameters?.apply {
                flashMode = "off"
                previewFormat = ImageFormat.NV21
                setPictureSize(supportedPictureSizes[0].width, supportedPictureSizes[0].height)
                setPreviewSize(supportedPreviewSizes[0].width, supportedPreviewSizes[0].height)
            }
            camera?.parameters = parameters
            camera?.startPreview()
        } catch (e: Exception) {
        }


    }

    fun stopPreview() {
        camera?.stopPreview()
        camera?.release()
        camera = null
    }

    fun changeCamera(cameraId: Int) {
        stopPreview()
        setCameraParam(cameraId)
    }
}