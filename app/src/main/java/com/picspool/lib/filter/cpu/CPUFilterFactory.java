package com.picspool.lib.filter.cpu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.filter.cpu.art.ClassicSketch;
import com.picspool.lib.filter.cpu.art.ColorSketch;
import com.picspool.lib.filter.cpu.art.Kraft;
import com.picspool.lib.filter.cpu.art.OldMovie;
import com.picspool.lib.filter.cpu.art.OldPhoto;
import com.picspool.lib.filter.cpu.art.Sketch;
import com.picspool.lib.filter.cpu.normal.CommonCurve;
import com.picspool.lib.filter.cpu.normal.FastBlurFilter;
import com.picspool.lib.filter.cpu.normal.MaskFilter;
import com.picspool.lib.filter.cpu.normal.ShadowFilter;

/* loaded from: classes3.dex */
public class CPUFilterFactory {
    public static Bitmap filter(Context context, Bitmap bitmap, CPUFilterType cPUFilterType) {
        if (context == null || bitmap == null || bitmap.isRecycled()) {
            return bitmap;
        }
        Resources resources = context.getResources();
        switch (C30921.$SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[cPUFilterType.ordinal()]) {
            case 1:
                return CommonCurve.filter(resources, bitmap, 1, "curves/acv/7AM.acv", null, false);
            case 2:
                return CommonCurve.filter(resources, bitmap, 1, "curves/acv/1974.acv", null, false);
            case 3:
                return CommonCurve.filter(resources, bitmap, 1, "curves/acv/Absinth.acv", null, false);
            case 4:
                return CommonCurve.filter(resources, bitmap, 1, "curves/acv/Buenos Aires.acv", null, false);
            case 5:
                return CommonCurve.filter(resources, bitmap, 1, "curves/acv/Denim.acv", null, false);
            case 6:
                return CommonCurve.filter(resources, bitmap, 1, "curves/acv/Royal Blue.acv", null, false);
            case 7:
                return CommonCurve.filter(resources, bitmap, 1, "curves/acv/Smoky.acv", null, false);
            case 8:
                return CommonCurve.filter(resources, bitmap, 1, "curves/acv/Toaster.acv", null, false);
            case 9:
                return MaskFilter.filter(bitmap, getRes(resources, "bokeh/bokeh1.jpg"), PorterDuff.Mode.SCREEN, false);
            case 10:
                return MaskFilter.filter(bitmap, getRes(resources, "bokeh/bokeh2.jpg"), PorterDuff.Mode.SCREEN, false);
            case 11:
                return MaskFilter.filter(bitmap, getRes(resources, "bokeh/bokeh3.jpg"), PorterDuff.Mode.SCREEN, false);
            case 12:
                return MaskFilter.filter(bitmap, getRes(resources, "bokeh/bokeh4.jpg"), PorterDuff.Mode.SCREEN, false);
            case 13:
                return MaskFilter.filter(bitmap, getRes(resources, "bokeh/bokeh5.jpg"), PorterDuff.Mode.SCREEN, false);
            case 14:
                return MaskFilter.filter(bitmap, getRes(resources, "bokeh/bokeh6.jpg"), PorterDuff.Mode.SCREEN, false);
            case 15:
                return MaskFilter.filter(bitmap, getRes(resources, "bokeh/bokeh7.jpg"), PorterDuff.Mode.SCREEN, false);
            case 16:
                return MaskFilter.filter(bitmap, getRes(resources, "bokeh/bokeh8.jpg"), PorterDuff.Mode.MULTIPLY, false);
            case 17:
                return Sketch.filter(resources, bitmap);
            case 18:
                return ClassicSketch.filter(resources, bitmap);
            case 19:
                return ColorSketch.filter(resources, bitmap);
            case 20:
                return Kraft.filter(resources, bitmap);
            case 21:
                return OldPhoto.filter(resources, bitmap);
            case 22:
                return OldMovie.filter(resources, bitmap);
            case 23:
                return MaskFilter.filter(bitmap, getRes(resources, "lens/1974.png"), PorterDuff.Mode.MULTIPLY, false);
            case 24:
                return MaskFilter.filter(bitmap, getRes(resources, "lens/absinth02.png"), PorterDuff.Mode.MULTIPLY, false);
            case 25:
                return MaskFilter.filter(bitmap, getRes(resources, "lens/buenos_aires.png"), PorterDuff.Mode.MULTIPLY, false);
            case 26:
                return MaskFilter.filter(bitmap, getRes(resources, "lens/denim.png"), PorterDuff.Mode.MULTIPLY, false);
            case 27:
                return MaskFilter.filter(bitmap, getRes(resources, "lens/denim02.png"), PorterDuff.Mode.MULTIPLY, false);
            case 28:
                return MaskFilter.filter(bitmap, getRes(resources, "lens/royalblue.png"), PorterDuff.Mode.MULTIPLY, false);
            case 29:
                return MaskFilter.filter(bitmap, getRes(resources, "lens/smoky.png"), PorterDuff.Mode.MULTIPLY, false);
            case 30:
                return MaskFilter.filter(bitmap, getRes(resources, "lens/toaster.png"), PorterDuff.Mode.MULTIPLY, false);
            case 31:
                return FastBlurFilter.blur(bitmap, 25, false);
            case 32:
                return ShadowFilter.filter(bitmap, -7829368, 5);
            default:
                return bitmap;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.picspool.lib.filter.cpu.CPUFilterFactory$1 */
    /* loaded from: classes3.dex */
    public static class C30921 {
        static final int[] $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType;

        static {
            int[] iArr = new int[CPUFilterType.values().length];
            $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType = iArr;
            try {
                iArr[CPUFilterType.T7AM.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.Y1974.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.ABSINTH.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.BUENOS_AIRES.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.DENIM.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.ROYAL_BLUE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.SMOKY.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.TOASTER.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.B1.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.B2.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.B3.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.B4.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.B5.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.B6.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.B7.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.B8.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.SKETCH.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.CLASSICSKETCH.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.COLORSKETCH.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.KRAFT.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.OLDPHOTO.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.OLDMOVIE.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.Y1974_SHOT.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.ABSINTH_SHOT.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.BUENOS_AIRES_SHOT.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.DENIM_SHOT.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.DENIM2_SHOT.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.ROYAL_BLUE_SHOT.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.SMOKY_SHOT.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.TOASTER_SHOT.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.BLUR_FAST.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$cpu$CPUFilterType[CPUFilterType.SHADOW.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
        }
    }

    private static Bitmap getRes(Resources resources, String str) {
        return DMBitmapUtil.getImageFromAssetsFile(resources, str);
    }
}
