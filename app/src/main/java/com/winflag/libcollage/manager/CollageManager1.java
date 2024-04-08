package com.winflag.libcollage.manager;

import com.google.firebase.messaging.ServiceStarter;
import com.picspool.lib.collagelib.resource.collage.LibDMCollageInfo;
import com.winflag.libcollage.activity.BaseSdk;
import com.winflag.libcollage.resource.TemplateRes;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
class CollageManager1 {
    CollageManager1() {
    }

    public static void oneImageTpl(int i, int i2, int i3, int i4, int i5, List<TemplateRes> list) {
        int i6;
        int i7;
        int i8;
        int i9;
        if (i4 > 0 && i5 > 0) {
            if (i5 > i4) {
                i9 = (int) (((i4 * 3060.0f) / i5) + 0.5f);
                i7 = (3060 - i9) / 2;
                i8 = 3060;
                i6 = 0;
            } else {
                int i10 = (int) (((i5 * 3060.0f) / i4) + 0.5f);
                i6 = (3060 - i10) / 2;
                i7 = 0;
                i8 = i10;
                i9 = 3060;
            }
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(CollageManagerUtils.initCollagePoint(i7, i6));
            int i11 = i9 + i7;
            arrayList2.add(CollageManagerUtils.initCollagePoint(i11, i6));
            int i12 = i8 + i6;
            arrayList2.add(CollageManagerUtils.initCollagePoint(i11, i12));
            arrayList2.add(CollageManagerUtils.initCollagePoint(i7, i12));
            arrayList.add(new LibDMCollageInfo(arrayList2, 0, 0, 0));
            list.add(CollageManagerUtils.initAssetItem("g1_n_c", arrayList, 10, i2));
        }
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        arrayList4.add(CollageManagerUtils.initCollagePoint(30, 30));
        arrayList4.add(CollageManagerUtils.initCollagePoint(3030, 30));
        arrayList4.add(CollageManagerUtils.initCollagePoint(3030, 3030));
        arrayList4.add(CollageManagerUtils.initCollagePoint(30, 3030));
        arrayList3.add(new LibDMCollageInfo(arrayList4, 10, 30, 30));
        list.add(CollageManagerUtils.initAssetItem("g1_1", arrayList3, 10, i2));
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        arrayList6.add(CollageManagerUtils.initCollagePoint(0, 0));
        arrayList6.add(CollageManagerUtils.initCollagePoint(3060, 0));
        arrayList6.add(CollageManagerUtils.initCollagePoint(3060, 3060));
        arrayList6.add(CollageManagerUtils.initCollagePoint(0, 3060));
        LibDMCollageInfo libDMCollageInfo = new LibDMCollageInfo(arrayList6, 10, 30, 30, "template/mask/mask_heart.png", BaseSdk.getContext());
        libDMCollageInfo.setIsCanShadow(true);
        arrayList5.add(libDMCollageInfo);
        list.add(CollageManagerUtils.initAssetItem("g1_n_6", arrayList5, 10, i2));
        ArrayList arrayList7 = new ArrayList();
        ArrayList arrayList8 = new ArrayList();
        arrayList8.add(CollageManagerUtils.initCollagePoint(0, 0));
        arrayList8.add(CollageManagerUtils.initCollagePoint(3060, 0));
        arrayList8.add(CollageManagerUtils.initCollagePoint(3060, 3060));
        arrayList8.add(CollageManagerUtils.initCollagePoint(0, 3060));
        LibDMCollageInfo libDMCollageInfo2 = new LibDMCollageInfo(arrayList8, 10, 30, 30, "template/mask/mask_circle.png", BaseSdk.getContext());
        libDMCollageInfo2.setIsCanShadow(true);
        libDMCollageInfo2.setIsCanCorner(false);
        arrayList7.add(libDMCollageInfo2);
        list.add(CollageManagerUtils.initAssetItem("g1_n_5", arrayList7, 10, i2));
        ArrayList arrayList9 = new ArrayList();
        ArrayList arrayList10 = new ArrayList();
        arrayList10.add(CollageManagerUtils.initCollagePoint(933, 386));
        arrayList10.add(CollageManagerUtils.initCollagePoint(1532, 686));
        arrayList10.add(CollageManagerUtils.initCollagePoint(2133, 386));
        arrayList10.add(CollageManagerUtils.initCollagePoint(2680, 850));
        arrayList10.add(CollageManagerUtils.initCollagePoint(2680, 1653));
        arrayList10.add(CollageManagerUtils.initCollagePoint(1533, 2676));
        arrayList10.add(CollageManagerUtils.initCollagePoint(386, 1653));
        arrayList10.add(CollageManagerUtils.initCollagePoint(386, 850));
        arrayList9.add(new LibDMCollageInfo(arrayList10, 10, 30, 30));
        list.add(CollageManagerUtils.initAssetItem("g1_n_1", arrayList9, 10, i2));
        ArrayList arrayList11 = new ArrayList();
        ArrayList arrayList12 = new ArrayList();
        arrayList12.add(CollageManagerUtils.initCollagePoint(1530, 310));
        arrayList12.add(CollageManagerUtils.initCollagePoint(2750, 1530));
        arrayList12.add(CollageManagerUtils.initCollagePoint(1530, 2750));
        arrayList12.add(CollageManagerUtils.initCollagePoint(310, 1530));
        arrayList11.add(new LibDMCollageInfo(arrayList12, 10, 30, 30));
        list.add(CollageManagerUtils.initAssetItem("g1_n_2", arrayList11, 10, i2));
        ArrayList arrayList13 = new ArrayList();
        ArrayList arrayList14 = new ArrayList();
        arrayList14.add(CollageManagerUtils.initCollagePoint(1530, 310));
        arrayList14.add(CollageManagerUtils.initCollagePoint(2650, 920));
        arrayList14.add(CollageManagerUtils.initCollagePoint(2650, 2138));
        arrayList14.add(CollageManagerUtils.initCollagePoint(1530, 2750));
        arrayList14.add(CollageManagerUtils.initCollagePoint(409, 2138));
        arrayList14.add(CollageManagerUtils.initCollagePoint(409, 920));
        arrayList13.add(new LibDMCollageInfo(arrayList14, 10, 30, 30));
        list.add(CollageManagerUtils.initAssetItem("g1_n_3", arrayList13, 10, i2));
        ArrayList arrayList15 = new ArrayList();
        ArrayList arrayList16 = new ArrayList();
        arrayList16.add(CollageManagerUtils.initCollagePoint(852, 64));
        arrayList16.add(CollageManagerUtils.initCollagePoint(2994, 64));
        arrayList16.add(CollageManagerUtils.initCollagePoint(2207, 2994));
        arrayList16.add(CollageManagerUtils.initCollagePoint(63, 2994));
        arrayList15.add(new LibDMCollageInfo(arrayList16, 10, 30, 30));
        list.add(CollageManagerUtils.initAssetItem("g1_n_4", arrayList15, 10, i2));
        ArrayList arrayList17 = new ArrayList();
        ArrayList arrayList18 = new ArrayList();
        arrayList18.add(CollageManagerUtils.initCollagePoint(0, 0));
        arrayList18.add(CollageManagerUtils.initCollagePoint(3060, 0));
        arrayList18.add(CollageManagerUtils.initCollagePoint(3060, 3060));
        arrayList18.add(CollageManagerUtils.initCollagePoint(0, 3060));
        arrayList17.add(new LibDMCollageInfo(arrayList18, 0, 0, 0));
        list.add(CollageManagerUtils.initAssetItem("g1_2", arrayList17, 0, i2));
        ArrayList arrayList19 = new ArrayList();
        ArrayList arrayList20 = new ArrayList();
        arrayList20.add(CollageManagerUtils.initCollagePoint(ServiceStarter.ERROR_UNKNOWN, 0));
        arrayList20.add(CollageManagerUtils.initCollagePoint(2560, 0));
        arrayList20.add(CollageManagerUtils.initCollagePoint(2560, 3060));
        arrayList20.add(CollageManagerUtils.initCollagePoint(ServiceStarter.ERROR_UNKNOWN, 3060));
        arrayList19.add(new LibDMCollageInfo(arrayList20, 10, 0, 0));
        list.add(CollageManagerUtils.initAssetItem("g1_3", arrayList19, i3, i2));
        ArrayList arrayList21 = new ArrayList();
        ArrayList arrayList22 = new ArrayList();
        arrayList22.add(CollageManagerUtils.initCollagePoint(0, ServiceStarter.ERROR_UNKNOWN));
        arrayList22.add(CollageManagerUtils.initCollagePoint(3060, ServiceStarter.ERROR_UNKNOWN));
        arrayList22.add(CollageManagerUtils.initCollagePoint(3060, 2560));
        arrayList22.add(CollageManagerUtils.initCollagePoint(0, 2560));
        arrayList21.add(new LibDMCollageInfo(arrayList22, 10, 0, 0));
        list.add(CollageManagerUtils.initAssetItem("g1_4", arrayList21, i3, i2));
        ArrayList arrayList23 = new ArrayList();
        ArrayList arrayList24 = new ArrayList();
        arrayList24.add(CollageManagerUtils.initCollagePoint(750, 0));
        arrayList24.add(CollageManagerUtils.initCollagePoint(2310, 0));
        arrayList24.add(CollageManagerUtils.initCollagePoint(2310, 3060));
        arrayList24.add(CollageManagerUtils.initCollagePoint(750, 3060));
        arrayList23.add(new LibDMCollageInfo(arrayList24, 10, 0, 0));
        list.add(CollageManagerUtils.initAssetItem("g1_5", arrayList23, i3, i2));
        ArrayList arrayList25 = new ArrayList();
        ArrayList arrayList26 = new ArrayList();
        arrayList26.add(CollageManagerUtils.initCollagePoint(300, 300));
        arrayList26.add(CollageManagerUtils.initCollagePoint(2760, 300));
        arrayList26.add(CollageManagerUtils.initCollagePoint(2760, 2760));
        arrayList26.add(CollageManagerUtils.initCollagePoint(300, 2760));
        arrayList25.add(new LibDMCollageInfo(arrayList26, 10, 0, 0));
        list.add(CollageManagerUtils.initAssetItem("g1_10", arrayList25, i3, i2));
    }
}
