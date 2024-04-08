package com.sky.testproject;

import android.content.Context;

/* renamed from: jp.co.cyberagent.android.gpuimage.GPUImageNativeLibrary */
/* loaded from: classes3.dex */
public class GPUImageNativeLibrary {
    public static native void YUVtoARBG(byte[] bArr, int i, int i2, int[] iArr);

    public static native void YUVtoRBGA(byte[] bArr, int i, int i2, int[] iArr);

    public static native void blur(int[] iArr, int i, int i2, int i3);

    public static native boolean initGpuNativeLibrary(Context context);

    static {
        try {
            System.loadLibrary("gpuimage");
        } catch (Exception unused) {
        }
    }
}
