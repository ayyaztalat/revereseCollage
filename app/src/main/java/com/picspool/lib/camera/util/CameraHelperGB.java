package com.picspool.lib.camera.util;

import android.content.Context;
import android.hardware.Camera;
import com.picspool.lib.camera.util.CameraHelper;

/* loaded from: classes3.dex */
public class CameraHelperGB implements CameraHelper.CameraHelperImpl {
    private final Context mContext;

    public CameraHelperGB(Context context) {
        this.mContext = context;
    }

    @Override // com.picspool.lib.camera.util.CameraHelper.CameraHelperImpl
    public int getNumberOfCameras() {
        return Camera.getNumberOfCameras();
    }

    @Override // com.picspool.lib.camera.util.CameraHelper.CameraHelperImpl
    public Camera openCamera(int i) {
        return Camera.open(i);
    }

    @Override // com.picspool.lib.camera.util.CameraHelper.CameraHelperImpl
    public Camera openDefaultCamera() {
        return Camera.open(0);
    }

    @Override // com.picspool.lib.camera.util.CameraHelper.CameraHelperImpl
    public boolean hasCamera(int i) {
        return getCameraId(i) != -1;
    }

    @Override // com.picspool.lib.camera.util.CameraHelper.CameraHelperImpl
    public boolean hasLight() {
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.camera.flash");
    }

    @Override // com.picspool.lib.camera.util.CameraHelper.CameraHelperImpl
    public Camera openCameraFacing(int i) {
        return Camera.open(getCameraId(i));
    }

    @Override // com.picspool.lib.camera.util.CameraHelper.CameraHelperImpl
    public void getCameraInfo(int i, CameraHelper.CameraInfo2 cameraInfo2) {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(i, cameraInfo);
        cameraInfo2.facing = cameraInfo.facing;
        cameraInfo2.orientation = cameraInfo.orientation;
    }

    private int getCameraId(int i) {
        int numberOfCameras = Camera.getNumberOfCameras();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i2 = 0; i2 < numberOfCameras; i2++) {
            Camera.getCameraInfo(i2, cameraInfo);
            if (cameraInfo.facing == i) {
                return i2;
            }
        }
        return -1;
    }
}
