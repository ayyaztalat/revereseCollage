package com.picspool.lib.filter.cpu.normal;

import com.picspool.lib.filter.cpu.father.TransferFilter;
import com.picspool.lib.filter.cpu.util.Curve;

/* loaded from: classes3.dex */
public class CurvesFilter extends TransferFilter {
    private Curve[] curves;

    public String toString() {
        return "Colors/Curves...";
    }

    public CurvesFilter() {
        this.curves = new Curve[1];
        Curve[] curveArr = new Curve[3];
        this.curves = curveArr;
        curveArr[0] = new Curve();
        this.curves[1] = new Curve();
        this.curves[2] = new Curve();
    }

    public CurvesFilter(int[] iArr, int[] iArr2, int[] iArr3) {
        this.curves = new Curve[1];
        this.rTable = iArr;
        this.gTable = iArr2;
        this.bTable = iArr3;
        this.initialized = true;
    }

    @Override // com.picspool.lib.filter.cpu.father.TransferFilter
    protected void initialize() {
        if (this.initialized) {
            return;
        }
        this.initialized = true;
        Curve[] curveArr = this.curves;
        if (curveArr.length == 1) {
            int[] makeTable = curveArr[0].makeTable();
            this.bTable = makeTable;
            this.gTable = makeTable;
            this.rTable = makeTable;
            return;
        }
        this.rTable = curveArr[0].makeTable();
        this.gTable = this.curves[1].makeTable();
        this.bTable = this.curves[2].makeTable();
    }

    public void setCurve(Curve curve) {
        this.curves = new Curve[]{curve};
        this.initialized = false;
    }

    public void setCurves(Curve[] curveArr) {
        if (curveArr == null || (curveArr.length != 1 && curveArr.length != 3)) {
            throw new IllegalArgumentException("Curves must be length 1 or 3");
        }
        this.curves = curveArr;
        this.initialized = false;
    }

    public Curve[] getCurves() {
        return this.curves;
    }
}
