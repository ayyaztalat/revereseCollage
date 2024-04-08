package com.picspool.lib.camera.util;

import android.app.Activity;
import android.hardware.Camera;
import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.camera.util.CameraHelper;
import com.picspool.lib.filter.gpu.core.GPUImage;

/* loaded from: classes3.dex */
public class CameraLoader {
    private Activity mActivity;
    private CameraHelper mCameraHelper;
    public Camera mCameraInstance;
    private int mCurrentCameraId = 0;
    private GPUImage mGPUImage;
    private List<GPUImage> mGPUImageList;
    private Camera.Size pictureSize;
    private Camera.Size previewSize;

    public CameraLoader(Activity activity, CameraHelper cameraHelper, GPUImage gPUImage) {
        this.mActivity = activity;
        this.mCameraHelper = cameraHelper;
        this.mGPUImage = gPUImage;
    }

    public CameraLoader(Activity activity, CameraHelper cameraHelper, List<GPUImage> list) {
        this.mActivity = activity;
        this.mCameraHelper = cameraHelper;
        this.mGPUImageList = list;
    }

    public void addGPUImage(GPUImage gPUImage) {
        if (this.mGPUImage == null) {
            this.mGPUImageList.add(gPUImage);
            return;
        }
        ArrayList arrayList = new ArrayList();
        this.mGPUImageList = arrayList;
        arrayList.add(this.mGPUImage);
        this.mGPUImageList.add(gPUImage);
        this.mGPUImage = null;
    }

    public void onResume() {
        setUpCamera(this.mCurrentCameraId);
    }

    public void onPause() {
        releaseCamera();
    }

    public void setCurrentCameraId(int i) {
        this.mCurrentCameraId = i;
    }

    public void switchCamera() {
        if (this.mCameraInstance != null) {
            releaseCamera();
        }
        int numberOfCameras = (this.mCurrentCameraId + 1) % this.mCameraHelper.getNumberOfCameras();
        this.mCurrentCameraId = numberOfCameras;
        setUpCamera(numberOfCameras);
    }

    public boolean isFront() {
        CameraHelper.CameraInfo2 cameraInfo2 = new CameraHelper.CameraInfo2();
        this.mCameraHelper.getCameraInfo(this.mCurrentCameraId, cameraInfo2);
        return cameraInfo2.facing == 1;
    }

    public Camera.Size getPreviewSize() {
        return this.previewSize;
    }

    private void setUpCamera(int i) {
        Camera cameraInstance = getCameraInstance(i);
        this.mCameraInstance = cameraInstance;
        if (cameraInstance == null) {
            return;
        }
        Camera.Parameters parameters = cameraInstance.getParameters();
        this.previewSize = CameraParam.getInstance().getPreviewSize(parameters.getSupportedPreviewSizes(), 600);
        Camera.Size pictureSize = CameraParam.getInstance().getPictureSize(parameters.getSupportedPictureSizes(), 1280);
        this.pictureSize = pictureSize;
        if (pictureSize.width / this.pictureSize.height != this.previewSize.width / this.previewSize.height) {
            this.previewSize = CameraParam.getInstance().getPreviewSize(this.mCameraInstance.getParameters().getSupportedPreviewSizes(), this.pictureSize.width / this.pictureSize.height);
        }
        parameters.setPreviewSize(this.previewSize.width, this.previewSize.height);
        parameters.setPictureSize(this.pictureSize.width, this.pictureSize.height);
        List<String> supportedFocusModes = parameters.getSupportedFocusModes();
        if (supportedFocusModes != null && supportedFocusModes.contains("auto")) {
            parameters.setFocusMode("auto");
        }
        this.mCameraInstance.setParameters(parameters);
        int cameraDisplayOrientation = this.mCameraHelper.getCameraDisplayOrientation(this.mActivity, this.mCurrentCameraId);
        CameraHelper.CameraInfo2 cameraInfo2 = new CameraHelper.CameraInfo2();
        this.mCameraHelper.getCameraInfo(this.mCurrentCameraId, cameraInfo2);
        boolean z = cameraInfo2.facing == 1;
        GPUImage gPUImage = this.mGPUImage;
        if (gPUImage != null) {
            gPUImage.setUpCamera(this.mCameraInstance, cameraDisplayOrientation, z, false);
            return;
        }
        for (GPUImage gPUImage2 : this.mGPUImageList) {
            gPUImage2.setUpCamera(this.mCameraInstance, cameraDisplayOrientation, z, false);
        }
    }

    private Camera getCameraInstance(int i) {
        try {
            return this.mCameraHelper.openCamera(i);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void releaseCamera() {
        Camera camera = this.mCameraInstance;
        if (camera != null) {
            camera.setPreviewCallback(null);
            this.mCameraInstance.stopPreview();
            this.mCameraInstance.release();
        }
        this.mCameraInstance = null;
    }
}
