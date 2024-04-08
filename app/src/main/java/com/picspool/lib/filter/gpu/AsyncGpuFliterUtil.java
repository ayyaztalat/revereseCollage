package com.picspool.lib.filter.gpu;

import android.graphics.Bitmap;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.gpu.father.GPUImageFilterGroup;
import com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter;
import com.picspool.lib.filter.gpu.util.Rotation;

/* loaded from: classes3.dex */
public class AsyncGpuFliterUtil {
    /* JADX WARN: Removed duplicated region for block: B:35:0x007a A[Catch: all -> 0x0076, TryCatch #3 {all -> 0x0076, blocks: (B:32:0x0072, B:35:0x007a, B:37:0x007f), top: B:54:0x0072 }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x007f A[Catch: all -> 0x0076, TRY_LEAVE, TryCatch #3 {all -> 0x0076, blocks: (B:32:0x0072, B:35:0x007a, B:37:0x007f), top: B:54:0x0072 }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0084 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0085 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x008f A[Catch: all -> 0x009a, TryCatch #7 {all -> 0x009a, blocks: (B:43:0x008a, B:45:0x008f, B:47:0x0094), top: B:56:0x008a }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0094 A[Catch: all -> 0x009a, TRY_LEAVE, TryCatch #7 {all -> 0x009a, blocks: (B:43:0x008a, B:45:0x008f, B:47:0x0094), top: B:56:0x008a }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0099 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x009a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0072 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x008a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static Bitmap filter(Bitmap r5, com.picspool.lib.filter.gpu.father.GPUImageFilter r6, boolean r7) {
        /*
            if (r6 == 0) goto L9a
            if (r5 == 0) goto L9a
            boolean r0 = r5.isRecycled()
            if (r0 == 0) goto Lc
            goto L9a
        Lc:
            r0 = 0
            filterSetRotation(r6)     // Catch: java.lang.Throwable -> L6e java.lang.Exception -> L86
            boolean r1 = r6 instanceof com.picspool.lib.filter.gpu.father.GPUImageFilterGroup     // Catch: java.lang.Throwable -> L6e java.lang.Exception -> L86
            r2 = 0
            if (r1 == 0) goto L2f
            r1 = r6
            com.picspool.lib.filter.gpu.father.GPUImageFilterGroup r1 = (com.picspool.lib.filter.gpu.father.GPUImageFilterGroup) r1     // Catch: java.lang.Throwable -> L6e java.lang.Exception -> L86
            java.util.List r1 = r1.getFilters()     // Catch: java.lang.Throwable -> L6e java.lang.Exception -> L86
            r3 = 0
        L1d:
            int r4 = r1.size()     // Catch: java.lang.Throwable -> L6e java.lang.Exception -> L86
            if (r3 >= r4) goto L2f
            java.lang.Object r4 = r1.get(r3)     // Catch: java.lang.Throwable -> L6e java.lang.Exception -> L86
            com.picspool.lib.filter.gpu.father.GPUImageFilter r4 = (com.picspool.lib.filter.gpu.father.GPUImageFilter) r4     // Catch: java.lang.Throwable -> L6e java.lang.Exception -> L86
            filterSetRotation(r4)     // Catch: java.lang.Throwable -> L6e java.lang.Exception -> L86
            int r3 = r3 + 1
            goto L1d
        L2f:
            com.picspool.lib.filter.gpu.core.GPUImageRenderer r1 = new com.picspool.lib.filter.gpu.core.GPUImageRenderer     // Catch: java.lang.Throwable -> L6e java.lang.Exception -> L86
            r1.<init>(r6)     // Catch: java.lang.Throwable -> L6e java.lang.Exception -> L86
            r1.setImageBitmap(r5, r2)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L6a
            com.picspool.lib.filter.gpu.util.Rotation r3 = com.picspool.lib.filter.gpu.util.Rotation.NORMAL     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L6a
            r4 = 1
            r1.setRotation(r3, r2, r4)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L6a
            com.picspool.lib.filter.gpu.util.PixelBuffer r2 = new com.picspool.lib.filter.gpu.util.PixelBuffer     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L6a
            int r3 = r5.getWidth()     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L6a
            int r4 = r5.getHeight()     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L6a
            r2.<init>(r3, r4)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L6a
            r2.setRenderer(r1)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
            android.graphics.Bitmap r3 = r2.getBitmap()     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
            r1.deleteImage()     // Catch: java.lang.Throwable -> L68 java.lang.Exception -> L6c
            r2.destroy()     // Catch: java.lang.Throwable -> L68 java.lang.Exception -> L6c
            if (r7 == 0) goto L61
            r6.destroy()     // Catch: java.lang.Throwable -> L5d java.lang.Exception -> L5f
            goto L61
        L5d:
            r2 = r0
            goto L70
        L5f:
            r2 = r0
            goto L88
        L61:
            return r3
        L62:
            r3 = r0
            goto L68
        L64:
            r3 = r0
            goto L6c
        L66:
            r2 = r0
            r3 = r2
        L68:
            r0 = r1
            goto L70
        L6a:
            r2 = r0
            r3 = r2
        L6c:
            r0 = r1
            goto L88
        L6e:
            r2 = r0
            r3 = r2
        L70:
            if (r7 == 0) goto L78
            r6.destroy()     // Catch: java.lang.Throwable -> L76
            goto L78
        L76:
            goto L82
        L78:
            if (r0 == 0) goto L7d
            r0.deleteImage()     // Catch: java.lang.Throwable -> L76
        L7d:
            if (r2 == 0) goto L82
            r2.destroy()     // Catch: java.lang.Throwable -> L76
        L82:
            if (r3 == 0) goto L85
            return r3
        L85:
            return r5
        L86:
            r2 = r0
            r3 = r2
        L88:
            if (r7 == 0) goto L8d
            r6.destroy()     // Catch: java.lang.Throwable -> L9a
        L8d:
            if (r0 == 0) goto L92
            r0.deleteImage()     // Catch: java.lang.Throwable -> L9a
        L92:
            if (r2 == 0) goto L97
            r2.destroy()     // Catch: java.lang.Throwable -> L9a
        L97:
            if (r3 == 0) goto L9a
            return r3
        L9a:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.picspool.lib.filter.gpu.AsyncGpuFliterUtil.filter(android.graphics.Bitmap, com.picspool.lib.filter.gpu.father.GPUImageFilter, boolean):android.graphics.Bitmap");
    }

    public static Bitmap filter(Bitmap bitmap, GPUImageFilter gPUImageFilter) {
        return (gPUImageFilter == null || bitmap == null || bitmap.isRecycled()) ? bitmap : filter(bitmap, gPUImageFilter, true);
    }

    public static void recycleTexture(GPUImageFilter gPUImageFilter) {
        recycleTexture(gPUImageFilter, true);
    }

    public static void recycleTexture(GPUImageFilter gPUImageFilter, boolean z) {
        if (gPUImageFilter instanceof GPUImageFilterGroup) {
            for (GPUImageFilter gPUImageFilter2 : ((GPUImageFilterGroup) gPUImageFilter).getFilters()) {
                if (gPUImageFilter2 instanceof GPUImageTwoInputFilter) {
                    Bitmap textureBitmap = ((GPUImageTwoInputFilter) gPUImageFilter2).getTextureBitmap();
                    if (textureBitmap == null || textureBitmap.isRecycled()) {
                        return;
                    }
                    textureBitmap.recycle();
                }
            }
        } else if (gPUImageFilter instanceof GPUImageTwoInputFilter) {
            Bitmap textureBitmap2 = ((GPUImageTwoInputFilter) gPUImageFilter).getTextureBitmap();
            if (!z || textureBitmap2 == null || textureBitmap2.isRecycled()) {
                return;
            }
            textureBitmap2.recycle();
        }
    }

    public static void filterSetRotation(GPUImageFilter gPUImageFilter) {
        if (gPUImageFilter instanceof GPUImageTwoInputFilter) {
            ((GPUImageTwoInputFilter) gPUImageFilter).setRotation(Rotation.NORMAL, false, true);
        }
    }
}
