package com.picspool.lib.filter.cpu.util;

import android.content.res.Resources;
import java.io.BufferedInputStream;
import java.io.DataInputStream;

/* loaded from: classes3.dex */
public class DATFileReader {

    /* renamed from: b */
    public int[] f1961b;

    /* renamed from: g */
    public int[] f1962g;

    /* renamed from: r */
    public int[] f1963r;

    public void loadFile(Resources resources, String str) {
        this.f1963r = new int[256];
        this.f1962g = new int[256];
        this.f1961b = new int[256];
        try {
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(resources.getAssets().open(str)));
            int i = 0;
            int i2 = 0;
            do {
                this.f1963r[i2] = dataInputStream.readByte();
                if (this.f1963r[i2] <= 0 && i2 > 0 && this.f1963r[i2 - 1] > 0) {
                    int[] iArr = this.f1963r;
                    iArr[i2] = iArr[i2] + 256;
                }
                i2++;
            } while (i2 < 256);
            int i3 = 0;
            do {
                this.f1962g[i3] = dataInputStream.readByte();
                if (this.f1962g[i3] <= 0 && i3 > 0 && this.f1962g[i3 - 1] > 0) {
                    int[] iArr2 = this.f1962g;
                    iArr2[i3] = iArr2[i3] + 256;
                }
                i3++;
            } while (i3 < 256);
            do {
                this.f1961b[i] = dataInputStream.readByte();
                if (this.f1961b[i] <= 0 && i > 0 && this.f1961b[i - 1] > 0) {
                    int[] iArr3 = this.f1961b;
                    iArr3[i] = iArr3[i] + 256;
                }
                i++;
            } while (i < 256);
        } catch (Exception unused) {
        }
    }
}
