package com.picspool.lib.filter.cpu.util;

/* loaded from: classes3.dex */
public interface Quantizer {
    void addPixels(int[] iArr, int i, int i2);

    int[] buildColorTable();

    int getIndexForColor(int i);

    void setup(int i);
}
