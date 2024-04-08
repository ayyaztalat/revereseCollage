package com.picspool.lib.filter.gpu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import java.io.IOException;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.gpu.father.GPUImageThreeInputFilter;
import com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter;
import com.picspool.lib.filter.gpu.normal.GPUImageToneCurveFilter;
import com.picspool.lib.filter.gpu.tonewithblend.GPUImageToneCurveWithNormalBlendOpacityFilter;
import com.picspool.lib.filter.gpu.vignette.GPUImageVignetteMapSelfBlendFilter;
import com.picspool.lib.filter.gpu.vignette.GPUImageVignetteToneCurveMapFilter;

/* loaded from: classes3.dex */
public class GPUImageFilterCreator {
    public static GPUImageFilter createFilterForTwoInputFilter(Context context, String str, Class<? extends GPUImageTwoInputFilter> cls) {
        GPUImageFilter gPUImageFilter = new GPUImageFilter();
        try {
            GPUImageTwoInputFilter newInstance = cls.newInstance();
            Bitmap decodeStream = BitmapFactory.decodeStream(context.getResources().getAssets().open(str));
            if (decodeStream == null) {
                return gPUImageFilter;
            }
            newInstance.setBitmap(decodeStream);
            return newInstance;
        } catch (Exception e) {
            e.printStackTrace();
            return gPUImageFilter;
        }
    }

    public static GPUImageFilter createFilterForThreeInputFilter(Context context, String str, String str2, Class<? extends GPUImageThreeInputFilter> cls) {
        GPUImageFilter gPUImageFilter = new GPUImageFilter();
        try {
            GPUImageThreeInputFilter newInstance = cls.newInstance();
            Bitmap decodeStream = BitmapFactory.decodeStream(context.getResources().getAssets().open(str));
            Bitmap decodeStream2 = BitmapFactory.decodeStream(context.getResources().getAssets().open(str2));
            newInstance.setBitmap2(decodeStream);
            newInstance.setBitmap3(decodeStream2);
            return newInstance;
        } catch (Exception e) {
            e.printStackTrace();
            return gPUImageFilter;
        }
    }

    public static GPUImageFilter createFilterForVignetteTwoInputFilter(Context context, String str, PointF pointF, float f, float f2, Class<? extends GPUImageTwoInputFilter> cls) {
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(context.getResources().getAssets().open(str));
        } catch (IOException e) {
            e.printStackTrace();
            bitmap = null;
        }
        try {
            if (cls == GPUImageVignetteToneCurveMapFilter.class) {
                GPUImageVignetteToneCurveMapFilter gPUImageVignetteToneCurveMapFilter = new GPUImageVignetteToneCurveMapFilter(pointF, f, f2);
                gPUImageVignetteToneCurveMapFilter.setBitmap(bitmap);
                return gPUImageVignetteToneCurveMapFilter;
            } else if (cls == GPUImageVignetteMapSelfBlendFilter.class) {
                GPUImageVignetteMapSelfBlendFilter gPUImageVignetteMapSelfBlendFilter = new GPUImageVignetteMapSelfBlendFilter(pointF, f, f2);
                gPUImageVignetteMapSelfBlendFilter.setBitmap(bitmap);
                return gPUImageVignetteMapSelfBlendFilter;
            } else {
                return new GPUImageFilter();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static GPUImageFilter createBlendFilter(Context context, Class<? extends GPUImageTwoInputFilter> cls) {
        GPUImageFilter gPUImageFilter = new GPUImageFilter();
        try {
            return cls.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return gPUImageFilter;
        }
    }

    public static GPUImageFilter createBlendFilter(Context context, Class<? extends GPUImageTwoInputFilter> cls, String str) {
        GPUImageFilter gPUImageFilter = new GPUImageFilter();
        try {
            GPUImageTwoInputFilter newInstance = cls.newInstance();
            Bitmap decodeStream = BitmapFactory.decodeStream(context.getResources().getAssets().open(str));
            if (decodeStream == null) {
                return gPUImageFilter;
            }
            newInstance.setBitmap(decodeStream);
            return newInstance;
        } catch (Exception e) {
            e.printStackTrace();
            return gPUImageFilter;
        }
    }

    public static GPUImageFilter createACVCurveFilter(Context context, String str) {
        GPUImageFilter gPUImageFilter = new GPUImageFilter();
        try {
            GPUImageToneCurveFilter gPUImageToneCurveFilter = new GPUImageToneCurveFilter();
            gPUImageToneCurveFilter.setFromAcvCurveFileInputStream(context.getResources().getAssets().open(str));
            return gPUImageToneCurveFilter;
        } catch (IOException e) {
            e.printStackTrace();
            return gPUImageFilter;
        }
    }

    public static GPUImageFilter createDATCurveFilter(Context context, String str) {
        GPUImageFilter gPUImageFilter = new GPUImageFilter();
        try {
            GPUImageToneCurveFilter gPUImageToneCurveFilter = new GPUImageToneCurveFilter();
            gPUImageToneCurveFilter.setFromDatCurveFileInputStream(context.getResources().getAssets().open(str));
            gPUImageToneCurveFilter.setFileType("dat");
            return gPUImageToneCurveFilter;
        } catch (IOException e) {
            e.printStackTrace();
            return gPUImageFilter;
        }
    }

    public static GPUImageFilter createBlendFilter(Context context, int i, Class<? extends GPUImageTwoInputFilter> cls) {
        GPUImageFilter gPUImageFilter = new GPUImageFilter();
        try {
            GPUImageTwoInputFilter newInstance = cls.newInstance();
            newInstance.setBitmap(Bitmap.createBitmap(new int[]{i}, 1, 1, Bitmap.Config.ARGB_4444));
            return newInstance;
        } catch (Exception e) {
            e.printStackTrace();
            return gPUImageFilter;
        }
    }

    public static GPUImageFilter createBlendFilter(Context context, int i, float f, Class<? extends GPUImageTwoInputFilter> cls) {
        GPUImageFilter gPUImageFilter = new GPUImageFilter();
        try {
            GPUImageTwoInputFilter newInstance = cls.newInstance();
            newInstance.setBitmap(Bitmap.createBitmap(new int[]{i}, 1, 1, Bitmap.Config.ARGB_4444));
            newInstance.setMix(f);
            return newInstance;
        } catch (Exception e) {
            e.printStackTrace();
            return gPUImageFilter;
        }
    }

    public static GPUImageFilter createBlendFilter(Context context, Bitmap bitmap, Class<? extends GPUImageTwoInputFilter> cls) {
        GPUImageFilter gPUImageFilter = new GPUImageFilter();
        try {
            GPUImageTwoInputFilter newInstance = cls.newInstance();
            newInstance.setBitmap(bitmap);
            return newInstance;
        } catch (Exception e) {
            e.printStackTrace();
            return gPUImageFilter;
        }
    }

    public static GPUImageFilter createBlendFilter(Context context, int i, int i2, Class<? extends GPUImageThreeInputFilter> cls) {
        GPUImageFilter gPUImageFilter = new GPUImageFilter();
        try {
            GPUImageThreeInputFilter newInstance = cls.newInstance();
            newInstance.setBitmap2(Bitmap.createBitmap(new int[]{i}, 1, 1, Bitmap.Config.ARGB_4444));
            newInstance.setBitmap3(Bitmap.createBitmap(new int[]{i2}, 1, 1, Bitmap.Config.ARGB_4444));
            return newInstance;
        } catch (Exception e) {
            e.printStackTrace();
            return gPUImageFilter;
        }
    }

    public static GPUImageFilter createACVCurveFilter(Context context, String str, Class<? extends GPUImageToneCurveFilter> cls) {
        GPUImageFilter gPUImageFilter = new GPUImageFilter();
        try {
            GPUImageToneCurveFilter newInstance = cls.newInstance();
            newInstance.setFromAcvCurveFileInputStream(context.getResources().getAssets().open(str));
            return newInstance;
        } catch (Exception e) {
            e.printStackTrace();
            return gPUImageFilter;
        }
    }

    public static GPUImageFilter createACVCurveFilter(Context context, String str, float f) {
        GPUImageFilter gPUImageFilter = new GPUImageFilter();
        try {
            GPUImageToneCurveWithNormalBlendOpacityFilter gPUImageToneCurveWithNormalBlendOpacityFilter = new GPUImageToneCurveWithNormalBlendOpacityFilter();
            gPUImageToneCurveWithNormalBlendOpacityFilter.setFromAcvCurveFileInputStream(context.getResources().getAssets().open(str));
            gPUImageToneCurveWithNormalBlendOpacityFilter.setOpacity(f);
            return gPUImageToneCurveWithNormalBlendOpacityFilter;
        } catch (IOException e) {
            e.printStackTrace();
            return gPUImageFilter;
        }
    }

    public static GPUImageFilter createACVCurveFilter(Context context, String str, float f, Class<? extends GPUImageToneCurveWithNormalBlendOpacityFilter> cls) {
        GPUImageFilter gPUImageFilter = new GPUImageFilter();
        try {
            GPUImageToneCurveWithNormalBlendOpacityFilter newInstance = cls.newInstance();
            newInstance.setFromAcvCurveFileInputStream(context.getResources().getAssets().open(str));
            newInstance.setOpacity(f);
            return newInstance;
        } catch (Exception e) {
            e.printStackTrace();
            return gPUImageFilter;
        }
    }
}
