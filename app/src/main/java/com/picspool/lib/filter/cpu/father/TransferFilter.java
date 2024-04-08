package com.picspool.lib.filter.cpu.father;

import com.picspool.lib.filter.cpu.util.PixelUtils;

/* loaded from: classes3.dex */
public abstract class TransferFilter extends PointFilter {
    protected int[] bTable;
    protected int[] gTable;
    protected boolean initialized = false;
    protected int[] rTable;

    protected float transferFunction(float f) {
        return 0.0f;
    }

    public TransferFilter() {
        this.canFilterIndexColorModel = true;
    }

    @Override // com.picspool.lib.filter.cpu.father.PointFilter
    public int filterRGB(int i, int i2, int i3) {
        int i4 = this.rTable[(i3 >> 16) & 255];
        int i5 = this.gTable[(i3 >> 8) & 255];
        return ((-16777216) & i3) | (i4 << 16) | (i5 << 8) | this.bTable[i3 & 255];
    }

    @Override // com.picspool.lib.filter.cpu.father.PointFilter
    public int[] filter(int[] iArr, int i, int i2) {
        if (!this.initialized) {
            initialize();
        }
        return super.filter(iArr, i, i2);
    }

    protected void initialize() {
        this.initialized = true;
        int[] makeTable = makeTable();
        this.bTable = makeTable;
        this.gTable = makeTable;
        this.rTable = makeTable;
    }

    protected int[] makeTable() {
        int[] iArr = new int[256];
        for (int i = 0; i < 256; i++) {
            iArr[i] = PixelUtils.clamp((int) (transferFunction(i / 255.0f) * 255.0f));
        }
        return iArr;
    }

    public int[] getLUT() {
        if (!this.initialized) {
            initialize();
        }
        int[] iArr = new int[256];
        for (int i = 0; i < 256; i++) {
            iArr[i] = filterRGB(0, 0, (i << 24) | (i << 16) | (i << 8) | i);
        }
        return iArr;
    }
}
