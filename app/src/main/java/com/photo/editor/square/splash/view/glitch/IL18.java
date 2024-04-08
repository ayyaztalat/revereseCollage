package com.photo.editor.square.splash.view.glitch;

import android.graphics.Bitmap;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.gpu.father.GPUImageFilterGroup;
import com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter;
import com.picspool.lib.filter.gpu.util.Rotation;

/* loaded from: classes2.dex */
public class IL18 {
    /* JADX WARN: Removed duplicated region for block: B:34:0x0080 A[Catch: all -> 0x007c, TryCatch #8 {all -> 0x007c, blocks: (B:31:0x0078, B:34:0x0080, B:36:0x0085), top: B:55:0x0078 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0085 A[Catch: all -> 0x007c, TRY_LEAVE, TryCatch #8 {all -> 0x007c, blocks: (B:31:0x0078, B:34:0x0080, B:36:0x0085), top: B:55:0x0078 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0095 A[Catch: all -> 0x00a0, TryCatch #0 {all -> 0x00a0, blocks: (B:42:0x0090, B:44:0x0095, B:46:0x009a), top: B:53:0x0090 }] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x009a A[Catch: all -> 0x00a0, TRY_LEAVE, TryCatch #0 {all -> 0x00a0, blocks: (B:42:0x0090, B:44:0x0095, B:46:0x009a), top: B:53:0x0090 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x009f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00a0 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0090 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0078 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:68:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static Bitmap filter(Bitmap r5, com.picspool.lib.filter.gpu.father.GPUImageFilter r6, boolean r7) {
        /*
            if (r6 == 0) goto La0
            if (r5 == 0) goto La0
            boolean r0 = r5.isRecycled()
            if (r0 != 0) goto La0
            r0 = 0
            filterSetRotation(r6)     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L8c
            boolean r1 = r6 instanceof com.picspool.lib.filter.gpu.father.GPUImageFilterGroup     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L8c
            if (r1 == 0) goto L2c
            r1 = r6
            com.picspool.lib.filter.gpu.father.GPUImageFilterGroup r1 = (com.picspool.lib.filter.gpu.father.GPUImageFilterGroup) r1     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L8c
            java.util.List r1 = r1.getFilters()     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L8c
            r2 = 0
        L1a:
            int r3 = r1.size()     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L8c
            if (r2 >= r3) goto L2c
            java.lang.Object r3 = r1.get(r2)     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L8c
            com.picspool.lib.filter.gpu.father.GPUImageFilter r3 = (com.picspool.lib.filter.gpu.father.GPUImageFilter) r3     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L8c
            filterSetRotation(r3)     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L8c
            int r2 = r2 + 1
            goto L1a
        L2c:
            com.photo.editor.square.splash.view.glitch.IL7 r1 = new com.photo.editor.square.splash.view.glitch.IL7     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L8c
            r2 = 1
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L8c
            int r2 = r5.getWidth()     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L70
            int r3 = r5.getHeight()     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L70
            r1.setTextureOriWH(r2, r3)     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L70
            r1.setUserBitmap(r5)     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L70
            r1.replaceGlitch(r6)     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L70
            com.picspool.lib.filter.gpu.util.PixelBuffer r2 = new com.picspool.lib.filter.gpu.util.PixelBuffer     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L70
            int r3 = r5.getWidth()     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L70
            int r4 = r5.getHeight()     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L70
            r2.<init>(r3, r4)     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L70
            r2.setRenderer(r1)     // Catch: java.lang.Throwable -> L68 java.lang.Exception -> L6a
            android.graphics.Bitmap r3 = r2.getBitmap()     // Catch: java.lang.Throwable -> L68 java.lang.Exception -> L6a
            r1.m79f2()     // Catch: java.lang.Throwable -> L6e java.lang.Exception -> L72
            r2.destroy()     // Catch: java.lang.Throwable -> L6e java.lang.Exception -> L72
            if (r7 == 0) goto L67
            r6.destroy()     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L65
            goto L67
        L63:
            r2 = r0
            goto L76
        L65:
            r2 = r0
            goto L8e
        L67:
            return r3
        L68:
            r3 = r0
            goto L6e
        L6a:
            r3 = r0
            goto L72
        L6c:
            r2 = r0
            r3 = r2
        L6e:
            r0 = r1
            goto L76
        L70:
            r2 = r0
            r3 = r2
        L72:
            r0 = r1
            goto L8e
        L74:
            r2 = r0
            r3 = r2
        L76:
            if (r7 == 0) goto L7e
            r6.destroy()     // Catch: java.lang.Throwable -> L7c
            goto L7e
        L7c:
            goto L88
        L7e:
            if (r0 == 0) goto L83
            r0.m79f2()     // Catch: java.lang.Throwable -> L7c
        L83:
            if (r2 == 0) goto L88
            r2.destroy()     // Catch: java.lang.Throwable -> L7c
        L88:
            if (r3 == 0) goto L8b
            r5 = r3
        L8b:
            return r5
        L8c:
            r2 = r0
            r3 = r2
        L8e:
            if (r7 == 0) goto L93
            r6.destroy()     // Catch: java.lang.Throwable -> La0
        L93:
            if (r0 == 0) goto L98
            r0.m79f2()     // Catch: java.lang.Throwable -> La0
        L98:
            if (r2 == 0) goto L9d
            r2.destroy()     // Catch: java.lang.Throwable -> La0
        L9d:
            if (r3 == 0) goto La0
            return r3
        La0:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.photo.editor.square.splash.view.glitch.IL18.filter(android.graphics.Bitmap, com.picspool.lib.filter.gpu.father.GPUImageFilter, boolean):android.graphics.Bitmap");
    }

    public static Bitmap filter(Bitmap bitmap, GPUImageFilter gPUImageFilter) {
        return (gPUImageFilter == null || bitmap == null || bitmap.isRecycled()) ? bitmap : filter(bitmap, gPUImageFilter, true);
    }

    public static void recycleTexture(GPUImageFilter gPUImageFilter) {
        recycleTexture(gPUImageFilter, true);
    }

    public static void recycleTexture(GPUImageFilter gPUImageFilter, boolean z) {
        if (!(gPUImageFilter instanceof GPUImageFilterGroup)) {
            if (gPUImageFilter instanceof GPUImageTwoInputFilter) {
                Bitmap textureBitmap = ((GPUImageTwoInputFilter) gPUImageFilter).getTextureBitmap();
                if (!z || textureBitmap == null || textureBitmap.isRecycled()) {
                    return;
                }
                textureBitmap.recycle();
                return;
            }
            return;
        }
        for (GPUImageFilter gPUImageFilter2 : ((GPUImageFilterGroup) gPUImageFilter).getFilters()) {
            if (gPUImageFilter2 instanceof GPUImageTwoInputFilter) {
                Bitmap textureBitmap2 = ((GPUImageTwoInputFilter) gPUImageFilter2).getTextureBitmap();
                if (textureBitmap2 == null || textureBitmap2.isRecycled()) {
                    return;
                }
                textureBitmap2.recycle();
            }
        }
    }

    public static void filterSetRotation(GPUImageFilter gPUImageFilter) {
        if (gPUImageFilter instanceof GPUImageTwoInputFilter) {
            ((GPUImageTwoInputFilter) gPUImageFilter).setRotation(Rotation.NORMAL, false, true);
        }
    }
}
