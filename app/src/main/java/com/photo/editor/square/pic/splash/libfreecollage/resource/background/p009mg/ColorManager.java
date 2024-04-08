package com.photo.editor.square.pic.splash.libfreecollage.resource.background.p009mg;

import android.graphics.Color;
import com.picspool.lib.resource.DMWBRes;

import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.resource.DMWBColorRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;
import com.sky.testproject.Opcodes;
import com.sky.testproject.R;

/* renamed from: com.photo.editor.square.pic.splash.libfreecollage.resource.background.mg.ColorManager */
/* loaded from: classes2.dex */
public class ColorManager implements DMWBManager {
    public Integer[] preColorIds = {Integer.valueOf(R.color.white), Integer.valueOf(R.color.black), Integer.valueOf(R.color.bg_fresh1), Integer.valueOf(R.color.bg_fresh2), Integer.valueOf(R.color.bg_fresh3), Integer.valueOf(R.color.bg_fresh4), Integer.valueOf(R.color.bg_fresh5), Integer.valueOf(R.color.bg_fresh6), Integer.valueOf(R.color.bg_brown1), Integer.valueOf(R.color.bg_brown2), Integer.valueOf(R.color.bg_brown3), Integer.valueOf(R.color.bg_brown4), Integer.valueOf(R.color.bg_brown5), Integer.valueOf(R.color.bg_brown6), Integer.valueOf(R.color.bg_purple1), Integer.valueOf(R.color.bg_purple2), Integer.valueOf(R.color.bg_purple3), Integer.valueOf(R.color.bg_purple4), Integer.valueOf(R.color.bg_purple5), Integer.valueOf(R.color.bg_purple6)};
    List<DMWBColorRes> resList;

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    public ColorManager() {
        ArrayList arrayList = new ArrayList();
        this.resList = arrayList;
        arrayList.add(initItem("white", this.preColorIds[0].intValue()));
        this.resList.add(initItem("black", this.preColorIds[1].intValue()));
        this.resList.add(initItemValue("pop1", Color.rgb(243, 112, 92)));
        this.resList.add(initItemValue("pop2", Color.rgb(244, 238, 100)));
        this.resList.add(initItemValue("pop3", Color.rgb(229, 89, 88)));
        this.resList.add(initItemValue("pop4", Color.rgb(43, 92, (int) Opcodes.RET)));
        this.resList.add(initItemValue("pop5", Color.rgb(240, 115, 171)));
        this.resList.add(initItemValue("pop6", Color.rgb(253, (int) Opcodes.INVOKEINTERFACE, 52)));
        this.resList.add(initItemValue("pop7", Color.rgb(117, 164, 84)));
        this.resList.add(initItemValue("pop8", Color.rgb(111, 88, 156)));
        this.resList.add(initItemValue("pop9", Color.rgb(144, 215, 235)));
        this.resList.add(initItemValue("pop10", Color.rgb(238, 92, 113)));
        this.resList.add(initItemValue("pop11", Color.rgb(243, 112, 92)));
        this.resList.add(initItemValue("pop12", Color.rgb(244, 130, 33)));
        this.resList.add(initItemValue("pop13", Color.rgb(156, (int) Opcodes.FCMPL, 201)));
        this.resList.add(initItemValue("pop14", Color.rgb(255, 194, 15)));
        this.resList.add(initItemValue("pop15", Color.rgb(191, 215, 67)));
        this.resList.add(initItemValue("pop16", Color.rgb(101, 194, (int) Opcodes.FCMPL)));
        this.resList.add(initItemValue("pop17", Color.rgb(248, 170, (int) Opcodes.IF_ACMPNE)));
        this.resList.add(initItem("fresh1", this.preColorIds[2].intValue()));
        this.resList.add(initItem("fresh2", this.preColorIds[3].intValue()));
        this.resList.add(initItem("fresh3", this.preColorIds[4].intValue()));
        this.resList.add(initItem("fresh4", this.preColorIds[5].intValue()));
        this.resList.add(initItem("fresh5", this.preColorIds[6].intValue()));
        this.resList.add(initItem("fresh6", this.preColorIds[7].intValue()));
        this.resList.add(initItem("purple1", this.preColorIds[14].intValue()));
        this.resList.add(initItem("purple2", this.preColorIds[15].intValue()));
        this.resList.add(initItem("purple3", this.preColorIds[16].intValue()));
        this.resList.add(initItem("purple4", this.preColorIds[17].intValue()));
        this.resList.add(initItem("purple5", this.preColorIds[18].intValue()));
        this.resList.add(initItem("purple6", this.preColorIds[19].intValue()));
        this.resList.add(initItem("brown1", this.preColorIds[8].intValue()));
        this.resList.add(initItem("brown2", this.preColorIds[9].intValue()));
        this.resList.add(initItem("brown3", this.preColorIds[10].intValue()));
        this.resList.add(initItem("brown4", this.preColorIds[11].intValue()));
        this.resList.add(initItem("brown5", this.preColorIds[12].intValue()));
        this.resList.add(initItem("brown6", this.preColorIds[13].intValue()));
    }

    protected DMWBColorRes initItem(String str, int i) {
        DMWBColorRes dMWBColorRes = new DMWBColorRes();
        dMWBColorRes.setName(str);
        dMWBColorRes.setColorID(i);
        return dMWBColorRes;
    }

    protected DMWBColorRes initItemValue(String str, int i) {
        DMWBColorRes dMWBColorRes = new DMWBColorRes();
        dMWBColorRes.setName(str);
        dMWBColorRes.setColorValue(i);
        return dMWBColorRes;
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public int getCount() {
        return this.resList.size();
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMWBRes getRes(int i) {
        int count = getCount();
        if (i < 0 || i >= count) {
            return null;
        }
        return this.resList.get(i);
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMWBRes getRes(String str) {
        for (int i = 0; i < this.resList.size(); i++) {
            DMWBColorRes dMWBColorRes = this.resList.get(i);
            if (dMWBColorRes.getName().compareToIgnoreCase(str) == 0) {
                return dMWBColorRes;
            }
        }
        return null;
    }
}
