package com.picspool.libfuncview.adjust;

import android.content.Context;
import android.graphics.Bitmap;
import com.picspool.libfuncview.adjust.CSAdjustCurveTouchView;
import java.util.ArrayList;
import java.util.List;
import com.picspool.instafilter.GPUFilter;
import com.picspool.lib.filter.gpu.normal.GPUImageToneCurveFilter;
import com.picspool.lib.filter.listener.OnPostFilteredListener;
import com.picspool.lib.resource.DMWBAsyncPostIconListener;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes.dex */
public class CSCurveMode extends DMWBRes {
    private Bitmap filtered;
    private GPUImageToneCurveFilter gpuImageToneCurveFilter;
    private Context mContext;
    private List<CSCurveLine> curveLineList = new ArrayList(4);
    private CSCurveLine curveRGB = new CSCurveLine(CSAdjustCurveTouchView.CurveColor.RGB);
    private CSCurveLine curveRed = new CSCurveLine(CSAdjustCurveTouchView.CurveColor.Red);
    private CSCurveLine curveGreen = new CSCurveLine(CSAdjustCurveTouchView.CurveColor.Green);
    private CSCurveLine curveBlue = new CSCurveLine(CSAdjustCurveTouchView.CurveColor.Blue);

    public CSCurveMode() {
        this.curveLineList.clear();
        this.curveLineList.add(this.curveRGB);
        this.curveLineList.add(this.curveRed);
        this.curveLineList.add(this.curveGreen);
        this.curveLineList.add(this.curveBlue);
    }

    public void resetFilter() {
        GPUImageToneCurveFilter gPUImageToneCurveFilter = new GPUImageToneCurveFilter();
        this.gpuImageToneCurveFilter = gPUImageToneCurveFilter;
        gPUImageToneCurveFilter.setRgbCompositeControlPoints(this.curveRGB.getPontFArray());
        this.gpuImageToneCurveFilter.setRedControlPoints(this.curveRed.getPontFArray());
        this.gpuImageToneCurveFilter.setGreenControlPoints(this.curveGreen.getPontFArray());
        this.gpuImageToneCurveFilter.setBlueControlPoints(this.curveBlue.getPontFArray());
    }

    public List<CSCurveLine> getCurveLineList() {
        return this.curveLineList;
    }

    public void setCurveLineList(List<CSCurveLine> list) {
        this.curveLineList = list;
    }

    public CSCurveLine getCurveRGB() {
        return this.curveRGB;
    }

    public void setCurveRGB(CSCurveLine cSCurveLine) {
        this.curveRGB = cSCurveLine;
    }

    public CSCurveLine getCurveRed() {
        return this.curveRed;
    }

    public void setCurveRed(CSCurveLine cSCurveLine) {
        this.curveRed = cSCurveLine;
    }

    public CSCurveLine getCurveGreen() {
        return this.curveGreen;
    }

    public void setCurveGreen(CSCurveLine cSCurveLine) {
        this.curveGreen = cSCurveLine;
    }

    public CSCurveLine getCurveBlue() {
        return this.curveBlue;
    }

    public void setCurveBlue(CSCurveLine cSCurveLine) {
        this.curveBlue = cSCurveLine;
    }

    public void getAsyncIconBitmap(Bitmap bitmap, final DMWBAsyncPostIconListener dMWBAsyncPostIconListener) {
        Bitmap bitmap2 = this.filtered;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            dMWBAsyncPostIconListener.postIcon(this.filtered);
            return;
        }
        try {
            synchronized (bitmap) {
                if (this.gpuImageToneCurveFilter != null) {
                    GPUFilter.asyncFilterForFilterNotRecycle(bitmap, this.gpuImageToneCurveFilter, new OnPostFilteredListener() { // from class: com.picspool.libfuncview.adjust.CSCurveMode.1
                        @Override // com.picspool.lib.filter.listener.OnPostFilteredListener
                        public void postFiltered(Bitmap bitmap3) {
                            CSCurveMode.this.filtered = bitmap3;
                            dMWBAsyncPostIconListener.postIcon(CSCurveMode.this.filtered);
                        }
                    });
                }
            }
        } catch (Throwable unused) {
        }
    }
}
