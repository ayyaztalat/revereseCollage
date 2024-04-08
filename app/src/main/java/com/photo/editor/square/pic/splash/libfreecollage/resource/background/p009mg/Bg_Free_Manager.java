package com.photo.editor.square.pic.splash.libfreecollage.resource.background.p009mg;

import android.content.Context;
import android.graphics.RectF;
import com.photo.editor.square.pic.splash.libfreecollage.res.ComposeInfo_Free;
import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.resource.DMWBRes;

/* renamed from: com.photo.editor.square.pic.splash.libfreecollage.resource.background.mg.Bg_Free_Manager */
/* loaded from: classes2.dex */
public class Bg_Free_Manager {
    List<ComposeInfo_Free> _styleInfoArray = new ArrayList();
    private int bg_num;
    private Context mContext;

    /* renamed from: com.photo.editor.square.pic.splash.libfreecollage.resource.background.mg.Bg_Free_Manager$BgType */
    /* loaded from: classes2.dex */
    public enum BgType {
        Foreground_Background,
        Background
    }

    public Bg_Free_Manager(Context context, int i) {
        this.mContext = context;
        this.bg_num = i;
    }

    public List<ComposeInfo_Free> allItem() {
        if (this._styleInfoArray.size() == 0) {
            preInstall();
        }
        return this._styleInfoArray;
    }

    public void addRepeatObject(String str, String str2, String str3) {
        ComposeInfo_Free composeInfo_Free = new ComposeInfo_Free(this.mContext);
        composeInfo_Free.setName(str);
        composeInfo_Free.setIconType(DMWBRes.LocationType.ASSERT);
        composeInfo_Free.setIconFileName(str2);
        composeInfo_Free.setBackgroundName_1_1(str3);
        composeInfo_Free.setBackgroundName_5_4(str3);
        composeInfo_Free.setPhotogroundColor(-1);
        composeInfo_Free.setMarginRectF(new RectF(3.0f, 3.0f, 3.0f, 3.0f));
        composeInfo_Free.setIsTile(true);
        composeInfo_Free.setIsWallpaper(false);
        composeInfo_Free.setBgType(BgType.Background);
        this._styleInfoArray.add(composeInfo_Free);
    }

    public void addWallpaperObject(String str, String str2, String str3) {
        ComposeInfo_Free composeInfo_Free = new ComposeInfo_Free(this.mContext);
        composeInfo_Free.setName(str);
        composeInfo_Free.setIconType(DMWBRes.LocationType.ASSERT);
        composeInfo_Free.setIconFileName(str2);
        composeInfo_Free.setBackgroundName_1_1(str3);
        composeInfo_Free.setBackgroundName_5_4(str3);
        composeInfo_Free.setPhotogroundColor(-1);
        composeInfo_Free.setMarginRectF(new RectF(3.0f, 3.0f, 3.0f, 3.0f));
        composeInfo_Free.setIsTile(false);
        composeInfo_Free.setIsWallpaper(true);
        composeInfo_Free.setBgType(BgType.Background);
        this._styleInfoArray.add(composeInfo_Free);
    }

    public void addForeBackgroundObject() {
        for (int i = 29; i <= 35; i++) {
            String valueOf = String.valueOf(i);
            String str = "bj_" + valueOf;
            String str2 = "freestyle/" + valueOf + "/icon.png";
            String str3 = "freestyle/" + valueOf + "/b11.jpg";
            String str4 = "freestyle/" + valueOf + "/f11.png";
            String str5 = "freestyle/" + valueOf + "/b34.jpg";
            ComposeInfo_Free composeInfo_Free = new ComposeInfo_Free(this.mContext);
            composeInfo_Free.setName(str);
            composeInfo_Free.setIconType(DMWBRes.LocationType.ASSERT);
            composeInfo_Free.setIconFileName(str2);
            composeInfo_Free.setBackgroundName_1_1(str3);
            composeInfo_Free.setForegroundName_1_1(str4);
            composeInfo_Free.setBackgroundName_5_4(str5);
            composeInfo_Free.setForegroundName_5_4("freestyle/" + valueOf + "/f34.png");
            composeInfo_Free.setPhotogroundColor(-1);
            composeInfo_Free.setMarginRectF(new RectF(3.0f, 3.0f, 3.0f, 3.0f));
            composeInfo_Free.setIsTile(false);
            composeInfo_Free.setIsWallpaper(false);
            composeInfo_Free.setBgType(BgType.Foreground_Background);
            this._styleInfoArray.add(composeInfo_Free);
        }
    }

    public void addBackgroundObject() {
        for (int i = 1; i <= 13; i++) {
            String valueOf = String.valueOf(i);
            String str = "bj_" + valueOf;
            String str2 = "freestyle/" + valueOf + "/icon.png";
            String str3 = "freestyle/" + valueOf + "/b11.jpg";
            ComposeInfo_Free composeInfo_Free = new ComposeInfo_Free(this.mContext);
            composeInfo_Free.setName(str);
            composeInfo_Free.setIconType(DMWBRes.LocationType.ASSERT);
            composeInfo_Free.setIconFileName(str2);
            composeInfo_Free.setBackgroundName_1_1(str3);
            composeInfo_Free.setBackgroundName_5_4("freestyle/" + valueOf + "/b34.jpg");
            composeInfo_Free.setPhotogroundColor(-1);
            composeInfo_Free.setMarginRectF(new RectF(3.0f, 3.0f, 3.0f, 3.0f));
            composeInfo_Free.setIsTile(false);
            composeInfo_Free.setIsWallpaper(false);
            composeInfo_Free.setBgType(BgType.Background);
            this._styleInfoArray.add(composeInfo_Free);
        }
    }

    public void addBackgroundDefault() {
        for (int i = 1; i <= this.bg_num; i++) {
            String valueOf = String.valueOf(i);
            String str = "bj_" + valueOf;
            String str2 = "freestyle/" + valueOf + "/icon.png";
            String str3 = "freestyle/" + valueOf + "/b11.jpg";
            String str4 = "freestyle/" + valueOf + "/b54.jpg";
            String str5 = "freestyle/" + valueOf + "/f11.png";
            ComposeInfo_Free composeInfo_Free = new ComposeInfo_Free(this.mContext);
            composeInfo_Free.setName(str);
            composeInfo_Free.setIconType(DMWBRes.LocationType.ASSERT);
            composeInfo_Free.setIconFileName(str2);
            composeInfo_Free.setBackgroundName_1_1(str3);
            composeInfo_Free.setBackgroundName_5_4(str4);
            composeInfo_Free.setForegroundName_1_1(str5);
            composeInfo_Free.setForegroundName_5_4("freestyle/" + valueOf + "/f54.png");
            composeInfo_Free.setPhotogroundColor(-1);
            composeInfo_Free.setMarginRectF(new RectF(3.0f, 3.0f, 3.0f, 3.0f));
            composeInfo_Free.setIsTile(false);
            composeInfo_Free.setIsWallpaper(false);
            composeInfo_Free.setBgType(BgType.Background);
            this._styleInfoArray.add(composeInfo_Free);
        }
    }

    public void preInstall() {
        addBackgroundDefault();
    }
}
