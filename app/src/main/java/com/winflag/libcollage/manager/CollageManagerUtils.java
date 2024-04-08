package com.winflag.libcollage.manager;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.util.Log;

import com.picspool.lib.collagelib.resource.collage.LibDMCollageInfo;
import com.picspool.lib.collagelib.resource.collage.LibDMCollagePoint;
import com.picspool.lib.resource.DMWBImageRes;
import com.winflag.libcollage.resource.TemplateRes;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class CollageManagerUtils {
    public static LibDMCollagePoint initCollagePoint(int i, int i2) {
        LibDMCollagePoint libDMCollagePoint = new LibDMCollagePoint();
        libDMCollagePoint.setOriPoint(new Point((int) ((i * 1.0f) + 0.5f), (int) ((i2 * 1.0f) + 0.5f)));
        return libDMCollagePoint;
    }

    public static LibDMCollagePoint initArcCollagePoint(int i, int i2) {
        LibDMCollagePoint libDMCollagePoint = new LibDMCollagePoint();
        libDMCollagePoint.setIsArcPoint(true);
        libDMCollagePoint.setOriPoint(new Point((int) ((i * 1.0f) + 0.5f), (int) ((i2 * 1.0f) + 0.5f)));
        return libDMCollagePoint;
    }

    public static LibDMCollagePoint initCollagePoint(Point point, int i, int i2) {
        LibDMCollagePoint libDMCollagePoint = new LibDMCollagePoint();
        libDMCollagePoint.setOriPoint(point);
        libDMCollagePoint.sethLineMode(i);
        libDMCollagePoint.setvLineMode(i2);
        return libDMCollagePoint;
    }

    public static LibDMCollagePoint initCollagePoint(int i, int i2, int i3, int i4) {
        LibDMCollagePoint libDMCollagePoint = new LibDMCollagePoint();
        libDMCollagePoint.setOriPoint(new Point(i, i2));
        libDMCollagePoint.sethLineMode(i3);
        libDMCollagePoint.setvLineMode(i4);
        return libDMCollagePoint;
    }

    public static LibDMCollagePoint initCollagePoint1024(int i, int i2) {
        LibDMCollagePoint libDMCollagePoint = new LibDMCollagePoint();
        libDMCollagePoint.setOriPoint(new Point((int) ((i * 2.9882812f) + 0.5f), (int) ((i2 * 2.9882812f) + 0.5f)));
        return libDMCollagePoint;
    }

    public static LibDMCollagePoint initCollagePoint1024(int i, int i2, boolean z) {
        LibDMCollagePoint libDMCollagePoint = new LibDMCollagePoint();
        Point point = new Point((int) ((i * 2.9882812f) + 0.5f), (int) ((i2 * 2.9882812f) + 0.5f));
        if (z) {
            libDMCollagePoint.setIsOutRectLinePoint(1);
        }
        libDMCollagePoint.setOriPoint(point);
        return libDMCollagePoint;
    }

    public static List<LibDMCollagePoint> initpoints(int i, int i2, int i3, int i4) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(initCollagePoint(i, i3));
        arrayList.add(initCollagePoint(i2, i3));
        arrayList.add(initCollagePoint(i2, i4));
        arrayList.add(initCollagePoint(i, i4));
        return arrayList;
    }

    public static LibDMCollagePoint initCollagePoint(int i, int i2, boolean z) {
        LibDMCollagePoint libDMCollagePoint = new LibDMCollagePoint();
        Point point = new Point((int) ((i * 1.0f) + 0.5f), (int) ((i2 * 1.0f) + 0.5f));
        if (z) {
            libDMCollagePoint.setIsOutRectLinePoint(1);
        }
        libDMCollagePoint.setOriPoint(point);
        return libDMCollagePoint;
    }

    public static TemplateRes initItem(String str, String str2, int i, int i2, int i3) {
        TemplateRes templateRes = new TemplateRes();
        templateRes.setName(str);
        templateRes.setRootPath(str2);
        templateRes.setPhotoAmount(i);
        templateRes.setPuzzlePath(str2 + "TemplateInfo.xml");
        templateRes.setOutFrameWidth(i2);
        templateRes.setInnerFrameWidth(i2);
        templateRes.setRoundRadius(i3);
        templateRes.setIconFileName(str2 + str);
        return templateRes;
    }

    public static TemplateRes initItem(String str, String str2, int i, int i2, int i3, String str3) {
        TemplateRes templateRes = new TemplateRes();
        templateRes.setName(str);
        templateRes.setRootPath(str2);
        templateRes.setPhotoAmount(i);
        templateRes.setPuzzlePath(str2 + "TemplateInfo.xml");
        templateRes.setOutFrameWidth(i2);
        templateRes.setInnerFrameWidth(i2);
        templateRes.setRoundRadius(i3);
        templateRes.setBackgroundPath(str3);
        return templateRes;
    }

    public static TemplateRes initAssetItem(String str, List<LibDMCollageInfo> list, int i, int i2) {
        return initAssetItem(str, DMWBImageRes.FitType.SCALE, list, i, i2);
    }

    public static TemplateRes initAssetItem(String str, DMWBImageRes.FitType fitType, List<LibDMCollageInfo> list, int i, int i2) {
        return initAssetItem(str, fitType, list, i, i2, i2, null);
    }

    public static TemplateRes initAssetItem(String str, DMWBImageRes.FitType fitType, List<LibDMCollageInfo> list, int i, int i2, int i3, String str2) {
        TemplateRes templateRes = new TemplateRes();
        templateRes.setFitType(fitType);
        templateRes.setName(str);
        templateRes.setOutFrameWidth(i3);
        templateRes.setInnerFrameWidth(i2);
        templateRes.setCollageInfo(list);
        templateRes.setRoundRadius(i);
        templateRes.setBackgroundPath(str2);
        return templateRes;
    }

    public static void createNewImageTpl(Context context, int i, int i2, int i3, String str, String str2, List<TemplateRes> list) {
        String[] strArr;
        String[] strArr2;
        LibDMCollageInfo libDMCollageInfo;
        ArrayList arrayList = new ArrayList();
        try {
            String[] list2 = context.getAssets().list(str2);
            AssetManager assets = context.getAssets();
            InputStream open = assets.open(str2 + "/config.json");
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            String str3 = new String(bArr);
            if (str3.endsWith("\n")) {
                str3 = str3.substring(0, str3.length() - 1);
            }
            String[] split = str3.split("\n");
            int length = split.length;
            int i4 = 0;
            while (i4 < length) {
                String str4 = split[i4];
                ArrayList arrayList2 = new ArrayList();
                int parseInt = Integer.parseInt(str4.substring(5, 6));
                int i5 = i4;
                String[] split2 = str4.substring(str4.indexOf(":") + 1).split(";");
                int length2 = split2.length;
                int i6 = length;
                int i7 = 0;
                while (i7 < length2) {
                    String[] strArr3 = split2;
                    String[] split3 = split2[i7].split(",");
                    arrayList2.add(initCollagePoint(Integer.parseInt(split3[0].trim()), Integer.parseInt(split3[1].trim())));
                    i7++;
                    length2 = length2;
                    split2 = strArr3;
                }
                Log.e("JIE", "createNewImageTpl: " + str4);
                if (list2.length > parseInt) {
                    strArr = split;
                    strArr2 = list2;
                    LibDMCollageInfo libDMCollageInfo2 = new LibDMCollageInfo(arrayList2, i3, i2, i, str2 + "/mask_" + parseInt + ".png", context);
                    Log.e("JIE", "createNewImageTpl: " + str2 + "/mask_" + parseInt + ".png");
                    libDMCollageInfo = libDMCollageInfo2;
                } else {
                    strArr = split;
                    strArr2 = list2;
                    libDMCollageInfo = new LibDMCollageInfo(arrayList2, i3, i2, i);
                }
                arrayList.add(libDMCollageInfo);
                i4 = i5 + 1;
                length = i6;
                list2 = strArr2;
                split = strArr;
            }
        } catch (IOException e) {
            Log.e("JIE", "createNewImageTpl: ", e);
        }
        list.add(initAssetItem(str, arrayList, i3, i2));
    }
}
