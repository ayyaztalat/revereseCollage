package com.picspool.lib.filter.gpu.util;

/* loaded from: classes3.dex */
public class PS2GpuImageValueConversion {
    public static float getBrightnessValue(float f) {
        return f / 300.0f;
    }

    public static float getContrastValue(float f) {
        return f <= 0.0f ? (float) (((f + 50.0f) / 1000.0f) + 0.9d) : (float) (((f * 0.49d) / 100.0d) + 1.0d);
    }

    public static float getHighlightsValue(float f) {
        return 1.0f - (f / 7.0f);
    }

    public static float getSaturaValue(float f) {
        return f <= 0.0f ? (f + 100.0f) / 100.0f : (f / 53.0f) + 1.0f;
    }
}
