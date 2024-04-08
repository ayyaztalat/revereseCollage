package com.baiwang.libuiinstalens.crop;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;

/* loaded from: classes.dex */
public class CSCropAsyncBitmapCropExecute {
    public static void asyncBitmapCrop(Context context, Uri uri, int i, final CSCropOnBitmapCropListener cSCropOnBitmapCropListener) {
        CSCropAsyncBitmapCropPool.initCropLoader(context);
        CSCropAsyncBitmapCropPool cSCropAsyncBitmapCropPool = CSCropAsyncBitmapCropPool.getInstance();
        cSCropAsyncBitmapCropPool.setData(context, uri, i);
        cSCropAsyncBitmapCropPool.setOnBitmapCropListener(bitmap -> {
            CSCropAsyncBitmapCropPool.shutdownCropLoder();
            cSCropOnBitmapCropListener.onBitmapCropFinish(bitmap);
        });
        cSCropAsyncBitmapCropPool.execute();
    }

    public static void asyncDrawbaleBitmapCrop(Context context, int i, int i2, CSCropOnBitmapCropListener cSCropOnBitmapCropListener) {
        CSCropAsyncBitmapCrop23 cSCropAsyncBitmapCrop23 = new CSCropAsyncBitmapCrop23();
        cSCropAsyncBitmapCrop23.setData(context, i, i2);
        cSCropAsyncBitmapCrop23.setOnBitmapCropListener(cSCropOnBitmapCropListener);
        cSCropAsyncBitmapCrop23.execute();
    }

    public static int getImageQuality(Context context) {
        int memoryClass = 0;
        if (((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass() <= 48) {
            memoryClass = 800;
        }
        double sqrt = Math.sqrt(((memoryClass * 0.125f) / 6.0f) * 1024.0f * 1024.0f);
        if (sqrt > 1920.0d) {
            sqrt = 1920.0d;
        }
        return (int) sqrt;
    }

    public static int getCollageCropSize(Context context, int list) {
            return list;
        /*
            java.lang.String r0 = "activity"
            java.lang.Object r11 = r11.getSystemService(r0)
            android.app.ActivityManager r11 = (android.app.ActivityManager) r11
            int r11 = r11.getMemoryClass()
            r0 = 500(0x1f4, float:7.0E-43)
            r1 = 1080(0x438, float:1.513E-42)
            r2 = 1280(0x500, float:1.794E-42)
            r3 = 1600(0x640, float:2.242E-42)
            r4 = 1920(0x780, float:2.69E-42)
            r5 = 384(0x180, float:5.38E-43)
            r6 = 800(0x320, float:1.121E-42)
            r7 = 32
            r8 = 64
            r9 = 128(0x80, float:1.8E-43)
            r10 = 256(0x100, float:3.59E-43)
            switch(r12) {
                case 1: goto L9d;
                case 2: goto L9d;
                case 3: goto L89;
                case 4: goto L6f;
                case 5: goto L5c;
                case 6: goto L5c;
                case 7: goto L5c;
                case 8: goto L3c;
                case 9: goto L3c;
                default: goto L25;
            }
        L25:
            int r11 = r11 * 1024
            int r11 = r11 * 1024
            int r11 = r11 / 8
            int r11 = r11 / 4
            int r12 = r12 + 2
            int r11 = r11 / r12
            double r11 = (double) r11
            double r11 = java.lang.Math.sqrt(r11)
            double r11 = java.lang.Math.ceil(r11)
            int r1 = (int) r11
            goto Lb2
        L3c:
            if (r11 <= r5) goto L42
        L3e:
            r1 = 1600(0x640, float:2.242E-42)
            goto Lb2
        L42:
            if (r11 <= r10) goto L48
        L44:
            r1 = 1280(0x500, float:1.794E-42)
            goto Lb2
        L48:
            if (r11 <= r9) goto L4c
            goto Lb2
        L4c:
            if (r11 <= r8) goto L52
        L4e:
            r1 = 800(0x320, float:1.121E-42)
            goto Lb2
        L52:
            if (r11 <= r7) goto L58
            r1 = 480(0x1e0, float:6.73E-43)
            goto Lb2
        L58:
            r1 = 360(0x168, float:5.04E-43)
            goto Lb2
        L5c:
            if (r11 < r5) goto L5f
            goto L71
        L5f:
            if (r11 < r10) goto L64
            r0 = 1440(0x5a0, float:2.018E-42)
            goto L87
        L64:
            if (r11 < r9) goto L69
            r0 = 1200(0x4b0, float:1.682E-42)
            goto L87
        L69:
            if (r11 < r8) goto L6c
            goto L80
        L6c:
            if (r11 < r7) goto L87
            goto L85
        L6f:
            if (r11 < r5) goto L74
        L71:
            r0 = 1920(0x780, float:2.69E-42)
            goto L87
        L74:
            if (r11 < r10) goto L79
            r0 = 1600(0x640, float:2.242E-42)
            goto L87
        L79:
            if (r11 < r9) goto L7e
            r0 = 1280(0x500, float:1.794E-42)
            goto L87
        L7e:
            if (r11 < r8) goto L83
        L80:
            r0 = 1080(0x438, float:1.513E-42)
            goto L87
        L83:
            if (r11 < r7) goto L87
        L85:
            r0 = 800(0x320, float:1.121E-42)
        L87:
            r1 = r0
            goto Lb2
        L89:
            if (r11 < r5) goto L8e
            r1 = 1920(0x780, float:2.69E-42)
            goto Lb2
        L8e:
            if (r11 < r10) goto L91
            goto L3e
        L91:
            if (r11 < r9) goto L94
            goto L44
        L94:
            if (r11 < r8) goto L97
            goto Lb2
        L97:
            if (r11 < r7) goto L9a
            goto L4e
        L9a:
            r1 = 600(0x258, float:8.41E-43)
            goto Lb2
        L9d:
            if (r11 < r10) goto La2
            r2 = 1920(0x780, float:2.69E-42)
            goto Lb1
        La2:
            if (r11 < r9) goto La7
            r2 = 1600(0x640, float:2.242E-42)
            goto Lb1
        La7:
            if (r11 < r8) goto Laa
            goto Lb1
        Laa:
            if (r11 < r7) goto Laf
            r2 = 960(0x3c0, float:1.345E-42)
            goto Lb1
        Laf:
            r2 = 800(0x320, float:1.121E-42)
        Lb1:
            r1 = r2
        Lb2:
            return r1
        */
       // throw new UnsupportedOperationException("Method not decompiled: com.baiwang.libuiinstalens.crop.CSCropAsyncBitmapCropExecute.getCollageCropSize(android.content.Context, int):int");
    }
}
