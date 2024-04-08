package com.picspool.snappic.manager;

import android.content.Context;
import com.picspool.libfuncview.onlinestore.CSMaterialFactory;
import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.resource.DMWBRes;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSBottomBarManager {
    private List<BottomBarEntity> imageResList;
    private Context mContext;

    public List<BottomBarEntity> getImageResList() {
        return this.imageResList;
    }

    public List<DMWBRes> getResList() {
        ArrayList arrayList = new ArrayList();
        for (BottomBarEntity bottomBarEntity : this.imageResList) {
            arrayList.add(bottomBarEntity);
        }
        return arrayList;
    }

    public CSBottomBarManager(Context context) {
        this.mContext = context;
        ArrayList arrayList = new ArrayList();
        this.imageResList = arrayList;
        arrayList.add(initImageRes("square", "bottombar/square_bottom_icon_square.png", R.string.libui_barview_title_square));
        this.imageResList.add(initImageRes("blur", "bottombar/square_editor_blur.png", R.string.libui_barview_title_blur));
        this.imageResList.add(initImageRes("crop", "bottombar/square_bottom_icon_crop.png", R.string.bottom_editor));
        this.imageResList.add(initImageRes(CSMaterialFactory.EffectFunName, "bottombar/square_bottom_icon_effect.png", R.string.bottom_effect));
        this.imageResList.add(initImageRes("glitch", "bottombar/square_bottom_icon_glitch.png", R.string.bottom_glitch));
        this.imageResList.add(initImageRes("filter", "bottombar/square_bottom_icon_filter.png", R.string.bottom_filter));
        this.imageResList.add(initImageRes("slimbody", "bottombar/square_bottom_icon_slimbody.png", R.string.bottom_slimbody));
        this.imageResList.add(initImageRes("stretch", "bottombar/square_bottom_icon_streth.png", R.string.bottom_stretch));
        this.imageResList.add(initImageRes("curve", "bottombar/square_bottom_icon_curve.png", R.string.bottom_curve));
        this.imageResList.add(initImageRes("scene", "bottombar/square_bottom_icon_scene.png", R.string.bottom_scene));
        this.imageResList.add(initImageRes("mosaic", "bottombar/square_bottom_icon_mosaic.png", R.string.bottom_mosaic));
        this.imageResList.add(initImageRes("bodyshaper", "bottombar/square_bottom_icon_bodyshaper.png", R.string.libui_barview_title_bodyshaper));
        this.imageResList.add(initImageRes("sticker", "bottombar/square_bottom_icon_stickers.png", R.string.bottom_sticker));
        this.imageResList.add(initImageRes("text", "bottombar/square_bottom_icon_text.png", R.string.bottom_text));
        this.imageResList.add(initImageRes("snap", "bottombar/square_bottom_icon_snap.png", R.string.bottom_snap));
        this.imageResList.add(initImageRes("splash", "bottombar/square_bottom_icon_splash.png", R.string.bottom_splash));
    }

    private BottomBarEntity initImageRes(String str, String str2, int i) {
        BottomBarEntity bottomBarEntity = new BottomBarEntity();
        bottomBarEntity.setName(str);
        bottomBarEntity.setIconFileName(str2);
        bottomBarEntity.setIconType(DMWBRes.LocationType.ASSERT);
        bottomBarEntity.setShowText(this.mContext.getString(i));
        return bottomBarEntity;
    }

    /* loaded from: classes.dex */
    public static class BottomBarEntity extends DMWBRes {

        /* renamed from: ad */
        private Object f1691ad;

        public Object getAd() {
            return this.f1691ad;
        }

        public void setAd(Object obj) {
            this.f1691ad = obj;
        }
    }
}
