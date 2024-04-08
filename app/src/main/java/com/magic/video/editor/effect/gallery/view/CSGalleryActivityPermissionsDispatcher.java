package com.magic.video.editor.effect.gallery.view;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;



/* loaded from: classes2.dex */
final class CSGalleryActivityPermissionsDispatcher {
    private static final String[] PERMISSION_TAKEPICTURE = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_TAKEPICTURE = 0;

    private CSGalleryActivityPermissionsDispatcher() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void takePictureWithPermissionCheck(CSGalleryActivity cSGalleryActivity) {
//        if (Manifest.permission.hasSelfPermissions(cSGalleryActivity, PERMISSION_TAKEPICTURE)) {
//            cSGalleryActivity.takePicture();
//        } else {
//            ActivityCompat.requestPermissions(cSGalleryActivity, PERMISSION_TAKEPICTURE, 0);
//        }
        if (cSGalleryActivity.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(cSGalleryActivity, PERMISSION_TAKEPICTURE, 0);
        }else if (cSGalleryActivity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(cSGalleryActivity, PERMISSION_TAKEPICTURE, 0);
        }else{
            cSGalleryActivity.takePicture();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void onRequestPermissionsResult(CSGalleryActivity cSGalleryActivity, int i, int[] iArr) {
//        if (i == 0 && PermissionUtils.verifyPermissions(iArr)) {
//            cSGalleryActivity.takePicture();
//        }
    }
}
