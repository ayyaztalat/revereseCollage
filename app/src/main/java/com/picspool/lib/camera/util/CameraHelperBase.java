package com.picspool.lib.camera.util;

import android.content.Context;
import android.hardware.Camera;
import com.picspool.lib.camera.util.CameraHelper;

/* loaded from: classes3.dex */
public class CameraHelperBase implements CameraHelper.CameraHelperImpl {
    private final Context mContext;

    public CameraHelperBase(Context context) {
        this.mContext = context;
    }

    @Override // com.picspool.lib.camera.util.CameraHelper.CameraHelperImpl
    public int getNumberOfCameras() {
        return hasCameraSupport() ? 1 : 0;
    }

    @Override // com.picspool.lib.camera.util.CameraHelper.CameraHelperImpl
    public Camera openCamera(int i) {
        return Camera.open();
    }

    @Override // com.picspool.lib.camera.util.CameraHelper.CameraHelperImpl
    public Camera openDefaultCamera() {
        return Camera.open();
    }

    @Override // com.picspool.lib.camera.util.CameraHelper.CameraHelperImpl
    public boolean hasCamera(int i) {
        if (i == 0) {
            return hasCameraSupport();
        }
        return false;
    }

    @Override // com.picspool.lib.camera.util.CameraHelper.CameraHelperImpl
    public boolean hasLight() {
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.camera.flash");
    }

    @Override // com.picspool.lib.camera.util.CameraHelper.CameraHelperImpl
    public Camera openCameraFacing(int i) {
        if (i == 0) {
            return Camera.open();
        }
        return null;
    }

    @Override // com.picspool.lib.camera.util.CameraHelper.CameraHelperImpl
    public void getCameraInfo(int i, CameraHelper.CameraInfo2 cameraInfo2) {
        cameraInfo2.facing = 0;
        cameraInfo2.orientation = 90;
    }

    private boolean hasCameraSupport() {
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.camera");
    }
}
