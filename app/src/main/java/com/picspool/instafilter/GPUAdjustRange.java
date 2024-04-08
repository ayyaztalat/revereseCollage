package com.picspool.instafilter;

/* loaded from: classes3.dex */
public class GPUAdjustRange {
    public static float range(int i, float f, float f2) {
        return (((f2 - f) * i) / 100.0f) + f;
    }

    public static float getBrightnessRange(int i) {
        return range(i, -0.2f, 0.2f);
    }

    public static float getContrastRange(int i) {
        if (i < 50) {
            return range(i, 0.8f, 1.2f);
        }
        return range(i, 0.6f, 1.4f);
    }

    public static float getSaturationRange(int i) {
        return range(i, 0.0f, 2.0f);
    }

    public static float getExposureRange(int i) {
        return range(i, -0.2f, 0.2f);
    }

    public static float getTemperatureRange(int i) {
        if (i < 50) {
            return range(i, 4000.0f, 6000.0f);
        }
        return range(i, 2000.0f, 8000.0f);
    }

    public static float getTintRange(int i) {
        return range(i, -100.0f, 100.0f);
    }

    public static float getSharpenRange(int i) {
        return range(i, -0.5f, 0.5f);
    }

    public static float getGammaRange(int i) {
        return range(i, 1.2f, 0.8f);
    }

    public static float getHueRange(int i) {
        return range(i, 0.0f, 360.0f);
    }

    public static float getShadowRange(int i) {
        return range(i, 0.0f, 1.0f);
    }

    public static float getHighlightRange(int i) {
        return range(i, 1.0f, 0.0f);
    }

    public static float getRRange(int i) {
        return range(i, 0.0f, 2.0f);
    }

    public static float getGRange(int i) {
        return range(i, 0.0f, 2.0f);
    }

    public static float getBRange(int i) {
        return range(i, 0.0f, 2.0f);
    }

    public static float getVigneeteRange(int i) {
        return range(i, 0.75f, 0.3f);
    }

    public static float getValue(int i) {
        return range(i, 0.0f, 10.0f);
    }
}
