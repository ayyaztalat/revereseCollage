package com.picspool.libfuncview.adjust;

import android.content.Context;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.resource.DMWBRes;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSCurveResManager {
    private List<DMWBRes> curveModeList;
    private Context mContext;

    public CSCurveResManager(Context context) {
        ArrayList arrayList = new ArrayList();
        this.curveModeList = arrayList;
        this.mContext = context;
        arrayList.add(initCurveModeItem("ori", context.getResources().getString(R.string.libui_curvemode_reset), "", new PointF[]{new PointF(0.0f, 0.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(255.0f, 255.0f)}));
        this.curveModeList.add(initCurveModeItem("fupian", this.mContext.getResources().getString(R.string.libui_curvemode_fupian), "", new PointF[]{new PointF(0.0f, 255.0f), new PointF(255.0f, 0.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(255.0f, 255.0f)}));
        this.curveModeList.add(initCurveModeItem("SoftContrast", this.mContext.getResources().getString(R.string.libui_curvemode_softcontrast), "", new PointF[]{new PointF(0.0f, 0.0f), new PointF(78.0f, 72.0f), new PointF(177.0f, 182.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(255.0f, 255.0f)}));
        this.curveModeList.add(initCurveModeItem("MiddleContrast", this.mContext.getResources().getString(R.string.libui_curvemode_middlecontrast), "", new PointF[]{new PointF(0.0f, 0.0f), new PointF(72.0f, 56.0f), new PointF(163.0f, 164.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(255.0f, 255.0f)}));
        this.curveModeList.add(initCurveModeItem("HardContrast", this.mContext.getResources().getString(R.string.libui_curvemode_hardcontrast), "", new PointF[]{new PointF(0.0f, 0.0f), new PointF(77.0f, 50.0f), new PointF(151.0f, 153.0f), new PointF(176.0f, 188.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(255.0f, 255.0f)}));
        this.curveModeList.add(initCurveModeItem("Brighten", this.mContext.getResources().getString(R.string.libui_curvemode_brighten), "", new PointF[]{new PointF(0.0f, 0.0f), new PointF(104.0f, 125.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(255.0f, 255.0f)}));
        this.curveModeList.add(initCurveModeItem("Darken", this.mContext.getResources().getString(R.string.libui_curvemode_darken), "", new PointF[]{new PointF(0.0f, 0.0f), new PointF(130.0f, 102.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(255.0f, 255.0f)}));
        this.curveModeList.add(initCurveModeItem("E21", "E21", "", new PointF[]{new PointF(21.0f, 2.0f), new PointF(171.0f, 187.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(8.0f, 0.0f), new PointF(137.0f, 129.0f), new PointF(205.0f, 207.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(27.0f, 28.0f), new PointF(158.0f, 158.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(17.0f, 47.0f), new PointF(193.0f, 183.0f), new PointF(255.0f, 231.0f)}));
        this.curveModeList.add(initCurveModeItem("E22", "E22", "", new PointF[]{new PointF(18.0f, 0.0f), new PointF(74.0f, 46.0f), new PointF(136.0f, 145.0f), new PointF(243.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(245.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 28.0f), new PointF(52.0f, 62.0f), new PointF(113.0f, 120.0f), new PointF(190.0f, 192.0f), new PointF(248.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 30.0f), new PointF(58.0f, 67.0f), new PointF(129.0f, 114.0f), new PointF(194.0f, 161.0f), new PointF(255.0f, 222.0f)}));
        this.curveModeList.add(initCurveModeItem("E23", "E23", "", new PointF[]{new PointF(0.0f, 32.0f), new PointF(42.0f, 38.0f), new PointF(89.0f, 71.0f), new PointF(146.0f, 132.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(69.0f, 74.0f), new PointF(140.0f, 152.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(16.0f, 0.0f), new PointF(71.0f, 55.0f), new PointF(146.0f, 135.0f), new PointF(204.0f, 200.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(65.0f, 44.0f), new PointF(152.0f, 129.0f), new PointF(207.0f, 201.0f), new PointF(255.0f, 255.0f)}));
        this.curveModeList.add(initCurveModeItem("E24", "E24", "", new PointF[]{new PointF(0.0f, 0.0f), new PointF(50.0f, 42.0f), new PointF(199.0f, 208.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(34.0f, 23.0f), new PointF(181.0f, 202.0f), new PointF(255.0f, 241.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(218.0f, 214.0f), new PointF(255.0f, 235.0f)}, new PointF[]{new PointF(0.0f, 29.0f), new PointF(253.0f, 227.0f)}));
        this.curveModeList.add(initCurveModeItem("M01", "M01", "", new PointF[]{new PointF(0.0f, 0.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(64.0f, 40.0f), new PointF(128.0f, 125.0f), new PointF(175.0f, 190.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(128.0f, 146.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(255.0f, 255.0f)}));
        this.curveModeList.add(initCurveModeItem("M02", "M02", "", new PointF[]{new PointF(0.0f, 0.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(64.0f, 40.0f), new PointF(128.0f, 125.0f), new PointF(175.0f, 190.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(64.0f, 48.0f), new PointF(97.0f, 128.0f), new PointF(190.0f, 208.0f), new PointF(255.0f, 208.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(59.0f, 24.0f), new PointF(181.0f, 223.0f), new PointF(255.0f, 255.0f)}));
        this.curveModeList.add(initCurveModeItem("M03", "M03", "", new PointF[]{new PointF(0.0f, 0.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(70.0f, 60.0f), new PointF(196.0f, 246.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(132.0f, 156.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 55.0f), new PointF(255.0f, 255.0f)}));
        this.curveModeList.add(initCurveModeItem("K01", "K01", "", new PointF[]{new PointF(0.0f, 0.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(73.0f, 33.0f), new PointF(128.0f, 125.0f), new PointF(172.0f, 204.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(73.0f, 49.0f), new PointF(129.0f, 129.0f), new PointF(190.0f, 213.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 27.0f), new PointF(62.0f, 61.0f), new PointF(155.0f, 189.0f), new PointF(255.0f, 235.0f)}));
        this.curveModeList.add(initCurveModeItem("K02", "K02", "", new PointF[]{new PointF(0.0f, 33.0f), new PointF(97.0f, 77.0f), new PointF(151.0f, 152.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(23.0f, 0.0f), new PointF(67.0f, 48.0f), new PointF(206.0f, 208.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(68.0f, 60.0f), new PointF(185.0f, 196.0f), new PointF(240.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 77.0f), new PointF(58.0f, 92.0f), new PointF(163.0f, 163.0f), new PointF(237.0f, 255.0f)}));
        this.curveModeList.add(initCurveModeItem("K03", "K03", "", new PointF[]{new PointF(0.0f, 0.0f), new PointF(62.0f, 46.0f), new PointF(171.0f, 193.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(30.0f, 0.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(32.0f, 26.0f), new PointF(88.0f, 94.0f), new PointF(255.0f, 255.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(78.0f, 107.0f), new PointF(224.0f, 237.0f)}));
    }

    private CSCurveMode initCurveModeItem(String str, String str2, String str3, PointF[] pointFArr, PointF[] pointFArr2, PointF[] pointFArr3, PointF[] pointFArr4) {
        CSCurveMode cSCurveMode = new CSCurveMode();
        cSCurveMode.setName(str);
        cSCurveMode.setShowText(str2);
        cSCurveMode.setIconFileName(str3);
        cSCurveMode.getCurveLineList().get(0).getPointFList().clear();
        cSCurveMode.getCurveLineList().get(1).getPointFList().clear();
        cSCurveMode.getCurveLineList().get(2).getPointFList().clear();
        cSCurveMode.getCurveLineList().get(3).getPointFList().clear();
        for (PointF pointF : pointFArr) {
            pointF.x /= 255.0f;
            pointF.y /= 255.0f;
            cSCurveMode.getCurveLineList().get(0).getPointFList().add(pointF);
        }
        for (PointF pointF2 : pointFArr2) {
            pointF2.x /= 255.0f;
            pointF2.y /= 255.0f;
            cSCurveMode.getCurveLineList().get(1).getPointFList().add(pointF2);
        }
        for (PointF pointF3 : pointFArr3) {
            pointF3.x /= 255.0f;
            pointF3.y /= 255.0f;
            cSCurveMode.getCurveLineList().get(2).getPointFList().add(pointF3);
        }
        for (PointF pointF4 : pointFArr4) {
            pointF4.x /= 255.0f;
            pointF4.y /= 255.0f;
            cSCurveMode.getCurveLineList().get(3).getPointFList().add(pointF4);
        }
        cSCurveMode.resetFilter();
        return cSCurveMode;
    }

    public List<DMWBRes> getCurveModeList() {
        return this.curveModeList;
    }
}
