package com.picspool.snappic.manager;

import android.content.Context;
import androidx.core.view.ViewCompat;

import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.resource.DMWBColorRes;
import com.picspool.lib.resource.DMWBRes;

import io.reactivex.rxjava3.annotations.SchedulerSupport;

/* loaded from: classes.dex */
public class CSSquareBgEffectColorManager {
    private Context mContext;
    private List<DMWBRes> resList;

    public CSSquareBgEffectColorManager(Context context) {
        ArrayList arrayList = new ArrayList();
        this.resList = arrayList;
        this.mContext = context;
        arrayList.add(initItem(SchedulerSupport.NONE, 4546286));
        this.resList.add(initItem("", -7701));
        this.resList.add(initItem("", -17743));
        this.resList.add(initItem("", -13908));
        this.resList.add(initItem("", -1920859));
        this.resList.add(initItem("", -1784143));
        this.resList.add(initItem("", -792616));
        this.resList.add(initItem("", -68929));
        this.resList.add(initItem("", -3150156));
        this.resList.add(initItem("", -4722737));
        this.resList.add(initItem("", -3674645));
        this.resList.add(initItem("", -2039059));
        this.resList.add(initItem("", ViewCompat.MEASURED_STATE_MASK));
        this.resList.add(initItem("", -15652164));
        this.resList.add(initItem("", -16729662));
        this.resList.add(initItem("", -11156677));
        this.resList.add(initItem("", -3948996));
        this.resList.add(initItem("", -3647205));
        this.resList.add(initItem("", -3604466));
        this.resList.add(initItem("", -6157635));
        this.resList.add(initItem("", -13229243));
        this.resList.add(initItem("", -11984064));
        this.resList.add(initItem("", -10479585));
        this.resList.add(initItem("", -12573142));
        this.resList.add(initItem("", -11525857));
        this.resList.add(initItem("", -10016216));
        this.resList.add(initItem("", -7973819));
        this.resList.add(initItem("", -8231867));
        this.resList.add(initItem("", -8229279));
        this.resList.add(initItem("", -9744334));
        this.resList.add(initItem("", -12107224));
        this.resList.add(initItem("", -12562380));
        this.resList.add(initItem("", -16763343));
        this.resList.add(initItem("", -2500135));
        this.resList.add(initItem("", -5723992));
        this.resList.add(initItem("", -11250604));
        this.resList.add(initItem("", -14277082));
        this.resList.add(initItem("", -5029252));
        this.resList.add(initItem("", -5807969));
        this.resList.add(initItem("", -10335043));
        this.resList.add(initItem("", -13350725));
        this.resList.add(initItem("", -14713174));
        this.resList.add(initItem("", -16418177));
        this.resList.add(initItem("", -16735596));
        this.resList.add(initItem("", -14501230));
        this.resList.add(initItem("", -11229837));
        this.resList.add(initItem("", -10371198));
        this.resList.add(initItem("", -8205157));
        this.resList.add(initItem("", -5125515));
        this.resList.add(initItem("", -3614387));
        this.resList.add(initItem("", -2299283));
        this.resList.add(initItem("", -1114541));
        this.resList.add(initItem("", -67233));
        this.resList.add(initItem("", -71574));
        this.resList.add(initItem("", -209810));
        this.resList.add(initItem("", -25804));
        this.resList.add(initItem("", -26505));
        this.resList.add(initItem("", -1416399));
        this.resList.add(initItem("", -1882035));
        this.resList.add(initItem("", -708809));
    }

    private DMWBRes initItem(String str, int i) {
        DMWBColorRes dMWBColorRes = new DMWBColorRes();
        dMWBColorRes.setName(str);
        dMWBColorRes.setIconID(i);
        dMWBColorRes.setColorValue(i);
        if (str.equals(SchedulerSupport.NONE)) {
            dMWBColorRes.setIconFileName("square/square_adjust_color_none.png");
            dMWBColorRes.setIconType(DMWBRes.LocationType.ASSERT);
        }
        return dMWBColorRes;
    }

    public List<DMWBRes> getResList() {
        return this.resList;
    }
}
