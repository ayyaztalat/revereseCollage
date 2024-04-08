package com.picspool.lib.camera.util;

import android.hardware.Camera;
import android.util.Log;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class CameraParam {
    private static CameraParam mCamPara = null;
    private static final String tag = "CameraParam";
    private CameraSizeComparator sizeComparator = new CameraSizeComparator();

    private CameraParam() {
    }

    public static CameraParam getInstance() {
        CameraParam cameraParam = mCamPara;
        if (cameraParam == null) {
            CameraParam cameraParam2 = new CameraParam();
            mCamPara = cameraParam2;
            return cameraParam2;
        }
        return cameraParam;
    }

    public Camera.Size getPreviewSize(List<Camera.Size> list, int i) {
        Collections.sort(list, this.sizeComparator);
        Iterator<Camera.Size> it2 = list.iterator();
        int i2 = 0;
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            Camera.Size next = it2.next();
            if (next.width >= i && equalRate(next, 1.33f)) {
                Log.i(tag, "最终设置预览尺寸:w = " + next.width + "h = " + next.height);
                break;
            }
            i2++;
        }
        if (i2 == list.size()) {
            i2--;
        }
        return list.get(i2);
    }

    public Camera.Size getPreviewSize(List<Camera.Size> list, float f) {
        Collections.sort(list, this.sizeComparator);
        Iterator<Camera.Size> it2 = list.iterator();
        int i = 0;
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            Camera.Size next = it2.next();
            if (next.width >= 600 && equalRate(next, f)) {
                Log.i(tag, "最终设置预览尺寸:w = " + next.width + "h = " + next.height);
                break;
            }
            i++;
        }
        if (i == list.size()) {
            i--;
        }
        return list.get(i);
    }

    public Camera.Size getPictureSize(List<Camera.Size> list, int i) {
        Camera.Size next = null;
        Collections.sort(list, this.sizeComparator);
        Iterator<Camera.Size> it2 = list.iterator();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            Camera.Size next2 = it2.next();
            if (next2.width >= i && equalRate(next2, 1.33f)) {
                Log.i(tag, "最终设置图片尺寸:w = " + next2.width + "h = " + next2.height);
                break;
            }
            i3++;
        }
        if (i3 == list.size()) {
            Iterator<Camera.Size> it3 = list.iterator();
            while (true) {
                if (!it3.hasNext()) {
                    break;
                }
                if (equalRate(it3.next(), 1.33f)) {
                    Log.i(tag, "最终设置图片尺寸:w = " + next.width + "h = " + next.height);
                    break;
                }
                i2++;
            }
            i3 = i2;
        }
        if (i3 == list.size()) {
            i3--;
        }
        return list.get(i3);
    }

    public boolean equalRate(Camera.Size size, float f) {
        return ((double) Math.abs((((float) size.width) / ((float) size.height)) - f)) <= 0.2d;
    }

    /* loaded from: classes3.dex */
    public class CameraSizeComparator implements Comparator<Camera.Size> {
        public CameraSizeComparator() {
        }

        @Override // java.util.Comparator
        public int compare(Camera.Size size, Camera.Size size2) {
            if (size.width == size2.width) {
                return 0;
            }
            return size.width > size2.width ? 1 : -1;
        }
    }
}
