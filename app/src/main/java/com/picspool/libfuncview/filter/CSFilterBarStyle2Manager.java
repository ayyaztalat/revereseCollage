package com.picspool.libfuncview.filter;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.filter.gpu.GPUFilterType;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes.dex */
public class CSFilterBarStyle2Manager {
    private static String FILTERGROUPBW = "filter_group_bw";
    private static String FILTERGROUPCLASSIC = "filter_group_classic";
    private static String FILTERGROUPFADE = "filter_group_fade";
    private static String FILTERGROUPFILM = "filter_group_film";
    private static String FILTERGROUPLOMO = "filter_group_lomo";
    private static String FILTERGROUPSEASON = "filter_group_season";
    private static String FILTERGROUPSWEET = "filter_group_sweet";
    private static String FILTERGROUPVINTAGE = "filter_group_vintage";
    private List<DMWBRes> filterGroupList = new ArrayList();
    private Context mContext;
    private Bitmap srcbmp;

    public CSFilterBarStyle2Manager(Context context, Bitmap bitmap) {
        this.mContext = context;
        this.srcbmp = DMBitmapUtil.sampeZoomFromBitmap(bitmap, 120);
        initList();
    }

    private void initList() {
        this.filterGroupList.add(initFilterGoup(FILTERGROUPBW, "B", "filter/group/bw.png"));
        this.filterGroupList.add(initFilterGoup(FILTERGROUPSEASON, ExifInterface.LATITUDE_SOUTH, "filter/group/season.png"));
        this.filterGroupList.add(initFilterGoup(FILTERGROUPCLASSIC, "C", "filter/group/classic.png"));
        this.filterGroupList.add(initFilterGoup(FILTERGROUPSWEET, ExifInterface.LONGITUDE_WEST, "filter/group/sweet.png"));
        this.filterGroupList.add(initFilterGoup(FILTERGROUPLOMO, "L", "filter/group/lomo.png"));
        this.filterGroupList.add(initFilterGoup(FILTERGROUPFILM, "F", "filter/group/film.png"));
        this.filterGroupList.add(initFilterGoup(FILTERGROUPFADE, ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, "filter/group/fade.png"));
        this.filterGroupList.add(initFilterGoup(FILTERGROUPFADE, ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "filter/group/vintage.png"));
    }

    private CSFilterGroup initFilterGoup(String str, String str2, String str3) {
        CSFilterGroup cSFilterGroup = new CSFilterGroup();
        cSFilterGroup.setName(str);
        cSFilterGroup.setShowText(str2);
        cSFilterGroup.setIconFileName(str3);
        cSFilterGroup.setIconType(DMWBRes.LocationType.ASSERT);
        int i = 0;
        for (DMWBRes dMWBRes : this.filterGroupList) {
            if (dMWBRes instanceof CSFilterGroup) {
                i += ((CSFilterGroup) dMWBRes).getFilterResList().size();
            }
        }
        cSFilterGroup.setFirstItemIndex(i);
        if (str.equals(FILTERGROUPBW)) {
            cSFilterGroup.addItem(initFilterRes("B1", "filter/image/mode7.png", GPUFilterType.GINGHAM));
            cSFilterGroup.addItem(initFilterRes("B2", "filter/image/mode7.png", GPUFilterType.CHARMES));
            cSFilterGroup.addItem(initFilterRes("B3", "filter/image/mode7.png", GPUFilterType.WILLOW));
            cSFilterGroup.addItem(initFilterRes("B4", "filter/image/mode7.png", GPUFilterType.ASHBY));
            cSFilterGroup.addItem(initFilterRes("B5", "filter/image/mode7.png", GPUFilterType.BWRetro));
            cSFilterGroup.addItem(initFilterRes("B6", "filter/image/mode7.png", GPUFilterType.CLARENDON));
            cSFilterGroup.addItem(initFilterRes("B7", "filter/image/mode7.png", GPUFilterType.INKWELL));
            cSFilterGroup.addItem(initFilterRes("B8", "filter/image/mode7.png", GPUFilterType.DOGPATCH));
        } else if (str.equals(FILTERGROUPSEASON)) {
            cSFilterGroup.addItem(initFilterRes("S1", "filter/image/mode3.png", GPUFilterType.SEASON_SPRING_BLOSSOM));
            cSFilterGroup.addItem(initFilterRes("S2", "filter/image/mode3.png", GPUFilterType.SEASON_AUTUMN_DAWOOD_HAMADA));
            cSFilterGroup.addItem(initFilterRes("S3", "filter/image/mode3.png", GPUFilterType.SEASON_WINTER_ICED));
            cSFilterGroup.addItem(initFilterRes("S4", "filter/image/mode3.png", GPUFilterType.SEASON_AUTUMN_GENTLE));
            cSFilterGroup.addItem(initFilterRes("S5", "filter/image/mode3.png", GPUFilterType.SEASON_SPRING_GLORIOUS_BABY));
            cSFilterGroup.addItem(initFilterRes("S6", "filter/image/mode3.png", GPUFilterType.SEASON_SUMMER_DAY));
            cSFilterGroup.addItem(initFilterRes("S7", "filter/image/mode3.png", GPUFilterType.SEASON_SUMMER_CLASSIC));
            cSFilterGroup.addItem(initFilterRes("S8", "filter/image/mode3.png", GPUFilterType.SEASON_SUMMER_INDIAN));
            cSFilterGroup.addItem(initFilterRes("S9", "filter/image/mode3.png", GPUFilterType.SEASON_AUTUMN_PREMIUM));
            cSFilterGroup.addItem(initFilterRes("S10", "filter/image/mode3.png", GPUFilterType.SEASON_SPRING_LIGHT));
            cSFilterGroup.addItem(initFilterRes("S11", "filter/image/mode3.png", GPUFilterType.SEASON_WINTER_SNAPPY_BABY));
            cSFilterGroup.addItem(initFilterRes("S12", "filter/image/mode3.png", GPUFilterType.SEASON_WINTER_SOFT_BROWN));
        } else if (str.equals(FILTERGROUPCLASSIC)) {
            cSFilterGroup.addItem(initFilterRes("C1", "filter/image/mode5.png", GPUFilterType.AMARO));
            cSFilterGroup.addItem(initFilterRes("C2", "filter/image/mode5.png", GPUFilterType.MAYFAIR));
            cSFilterGroup.addItem(initFilterRes("C3", "filter/image/mode5.png", GPUFilterType.RISE));
            cSFilterGroup.addItem(initFilterRes("C4", "filter/image/mode5.png", GPUFilterType.HUDSON));
            cSFilterGroup.addItem(initFilterRes("C5", "filter/image/mode5.png", GPUFilterType.VALENCIA));
            cSFilterGroup.addItem(initFilterRes("C6", "filter/image/mode5.png", GPUFilterType.SIERRA));
            cSFilterGroup.addItem(initFilterRes("C7", "filter/image/mode5.png", GPUFilterType.TOASTER));
            cSFilterGroup.addItem(initFilterRes("C8", "filter/image/mode5.png", GPUFilterType.BRANNAN));
            cSFilterGroup.addItem(initFilterRes("C9", "filter/image/mode5.png", GPUFilterType.WALDEN));
            cSFilterGroup.addItem(initFilterRes("C10", "filter/image/mode5.png", GPUFilterType.HEFE));
            cSFilterGroup.addItem(initFilterRes("C11", "filter/image/mode5.png", GPUFilterType.NASHVILLE));
            cSFilterGroup.addItem(initFilterRes("C12", "filter/image/mode5.png", GPUFilterType.KELVIN));
        } else if (str.equals(FILTERGROUPSWEET)) {
            cSFilterGroup.addItem(initFilterRes("S1", "filter/image/mode1.png", GPUFilterType.SWEET_PREMIUM));
            cSFilterGroup.addItem(initFilterRes("S2", "filter/image/mode1.png", GPUFilterType.SWEET_CERULEAN_BLUE));
            cSFilterGroup.addItem(initFilterRes("S3", "filter/image/mode1.png", GPUFilterType.SWEET_DEEP_PURPLE));
            cSFilterGroup.addItem(initFilterRes("S4", "filter/image/mode1.png", GPUFilterType.SWEET_HAZY_TEAL));
            cSFilterGroup.addItem(initFilterRes("S5", "filter/image/mode1.png", GPUFilterType.SWEET_LAVENDER_HAZE));
            cSFilterGroup.addItem(initFilterRes("S6", "filter/image/mode1.png", GPUFilterType.SWEET_MAGENTA));
            cSFilterGroup.addItem(initFilterRes("S7", "filter/image/mode1.png", GPUFilterType.SWEET_MORNING_GLOW));
            cSFilterGroup.addItem(initFilterRes("S8", "filter/image/mode1.png", GPUFilterType.SWEET_PRIMUEM));
            cSFilterGroup.addItem(initFilterRes("S9", "filter/image/mode1.png", GPUFilterType.SWEET_ROMANCE));
            cSFilterGroup.addItem(initFilterRes("S10", "filter/image/mode1.png", GPUFilterType.SWEET_RUSTY_TINT));
            cSFilterGroup.addItem(initFilterRes("S11", "filter/image/mode1.png", GPUFilterType.SWEET_SO_COOL));
            cSFilterGroup.addItem(initFilterRes("S12", "filter/image/mode1.png", GPUFilterType.SWEET_SWEET));
            cSFilterGroup.addItem(initFilterRes("S13", "filter/image/mode1.png", GPUFilterType.SWEET_SWEETFALLEMBRACE));
            cSFilterGroup.addItem(initFilterRes("S14", "filter/image/mode1.png", GPUFilterType.SWEET_WAKE_UP));
            cSFilterGroup.addItem(initFilterRes("S15", "filter/image/mode1.png", GPUFilterType.SWEET_CUSTOM_CLEAN_LIGHT));
        } else if (str.equals(FILTERGROUPLOMO)) {
            cSFilterGroup.addItem(initFilterRes("L1", "filter/image/mode4.png", GPUFilterType.LOMO1, 40));
            cSFilterGroup.addItem(initFilterRes("L2", "filter/image/mode4.png", GPUFilterType.LOMO2, 40));
            cSFilterGroup.addItem(initFilterRes("L3", "filter/image/mode4.png", GPUFilterType.LOMO6, 80));
            cSFilterGroup.addItem(initFilterRes("L4", "filter/image/mode4.png", GPUFilterType.LOMO8, 45));
            cSFilterGroup.addItem(initFilterRes("L5", "filter/image/mode4.png", GPUFilterType.LOMO3, 60));
            cSFilterGroup.addItem(initFilterRes("L6", "filter/image/mode4.png", GPUFilterType.LOMO12, 50));
            cSFilterGroup.addItem(initFilterRes("L7", "filter/image/mode4.png", GPUFilterType.LOMO16, 15));
            cSFilterGroup.addItem(initFilterRes("L8", "filter/image/mode4.png", GPUFilterType.LOMO9, 30));
            cSFilterGroup.addItem(initFilterRes("L9", "filter/image/mode4.png", GPUFilterType.LOMO11, 80));
            cSFilterGroup.addItem(initFilterRes("L10", "filter/image/mode4.png", GPUFilterType.LOMO5, 80));
            cSFilterGroup.addItem(initFilterRes("L11", "filter/image/mode4.png", GPUFilterType.LOMO27, 90));
            cSFilterGroup.addItem(initFilterRes("L12", "filter/image/mode4.png", GPUFilterType.LOMO15, 30));
            cSFilterGroup.addItem(initFilterRes("L13", "filter/image/mode4.png", GPUFilterType.LOMO13, 100));
            cSFilterGroup.addItem(initFilterRes("L14", "filter/image/mode4.png", GPUFilterType.LOMO28, 40));
            cSFilterGroup.addItem(initFilterRes("L15", "filter/image/mode4.png", GPUFilterType.LOMO18, 60));
            cSFilterGroup.addItem(initFilterRes("L16", "filter/image/mode4.png", GPUFilterType.LOMO20, 70));
        } else if (str.equals(FILTERGROUPFILM)) {
            cSFilterGroup.addItem(initFilterRes("M1", "filter/image/mode2.png", GPUFilterType.FILM_16));
            cSFilterGroup.addItem(initFilterRes("M2", "filter/image/mode2.png", GPUFilterType.FILM_3));
            cSFilterGroup.addItem(initFilterRes("M3", "filter/image/mode2.png", GPUFilterType.FILM_B_VOL));
            cSFilterGroup.addItem(initFilterRes("M4", "filter/image/mode2.png", GPUFilterType.FILM_CARINA));
            cSFilterGroup.addItem(initFilterRes("M5", "filter/image/mode2.png", GPUFilterType.FILM_CLASSIC_BLUE));
            cSFilterGroup.addItem(initFilterRes("M6", "filter/image/mode2.png", GPUFilterType.FILM_COOL_BREEZE));
            cSFilterGroup.addItem(initFilterRes("M7", "filter/image/mode2.png", GPUFilterType.FILM_COOLER));
            cSFilterGroup.addItem(initFilterRes("M8", "filter/image/mode2.png", GPUFilterType.FILM_CP_12));
            cSFilterGroup.addItem(initFilterRes("M9", "filter/image/mode2.png", GPUFilterType.FILM_PREMIUM_6));
            cSFilterGroup.addItem(initFilterRes("M10", "filter/image/mode2.png", GPUFilterType.FILM_GREY_LIGHT));
            cSFilterGroup.addItem(initFilterRes("M11", "filter/image/mode2.png", GPUFilterType.FILM_LUST));
            cSFilterGroup.addItem(initFilterRes("M12", "filter/image/mode2.png", GPUFilterType.FILM_PAPRIKA));
            cSFilterGroup.addItem(initFilterRes("M13", "filter/image/mode2.png", GPUFilterType.FILM_PREMIUM_6));
            cSFilterGroup.addItem(initFilterRes("M14", "filter/image/mode2.png", GPUFilterType.FILM_PREMIUM_19));
            cSFilterGroup.addItem(initFilterRes("M15", "filter/image/mode2.png", GPUFilterType.FILM_PREMIUM_31));
            cSFilterGroup.addItem(initFilterRes("M16", "filter/image/mode2.png", GPUFilterType.FILM_RENDEZVOUS));
            cSFilterGroup.addItem(initFilterRes("M17", "filter/image/mode2.png", GPUFilterType.FILM_18));
            cSFilterGroup.addItem(initFilterRes("M18", "filter/image/mode2.png", GPUFilterType.FILM_NIGHT_FATE_2));
            cSFilterGroup.addItem(initFilterRes("M19", "filter/image/mode2.png", GPUFilterType.FILM_NIGHT_FATE_6));
            cSFilterGroup.addItem(initFilterRes("M20", "filter/image/mode2.png", GPUFilterType.FILM_NIGHT_FATE_3));
            cSFilterGroup.addItem(initFilterRes("M21", "filter/image/mode2.png", GPUFilterType.FILM_TONING_EVOLUTION));
            cSFilterGroup.addItem(initFilterRes("M22", "filter/image/mode2.png", GPUFilterType.FILM_TONING_HAZARD));
            cSFilterGroup.addItem(initFilterRes("M23", "filter/image/mode2.png", GPUFilterType.FILM_TONING_RUST));
            cSFilterGroup.addItem(initFilterRes("M24", "filter/image/mode2.png", GPUFilterType.FILM_TONING_URBAN_CRIMINAL));
            cSFilterGroup.addItem(initFilterRes("M25", "filter/image/mode2.png", GPUFilterType.FILM_VINTAGE_MADE_SIMPLE));
            cSFilterGroup.addItem(initFilterRes("M26", "filter/image/mode2.png", GPUFilterType.FILM_WARM_TONES));
        } else if (str.equals(FILTERGROUPFADE)) {
            cSFilterGroup.addItem(initFilterRes("F1", "filter/image/mode6.png", GPUFilterType.FADE_DARK_DESATURATE));
            cSFilterGroup.addItem(initFilterRes("F2", "filter/image/mode6.png", GPUFilterType.FADE_DIFFUSED_MATTE));
            cSFilterGroup.addItem(initFilterRes("F3", "filter/image/mode6.png", GPUFilterType.FADE_EVERYDAY));
            cSFilterGroup.addItem(initFilterRes("F4", "filter/image/mode6.png", GPUFilterType.FADE_LIME));
            cSFilterGroup.addItem(initFilterRes("F5", "filter/image/mode6.png", GPUFilterType.FADE_LUCID));
            cSFilterGroup.addItem(initFilterRes("F6", "filter/image/mode6.png", GPUFilterType.FADE_RETRO));
            cSFilterGroup.addItem(initFilterRes("F7", "filter/image/mode6.png", GPUFilterType.FADE_WHITE_WASH));
            cSFilterGroup.addItem(initFilterRes("F8", "filter/image/mode6.png", GPUFilterType.FADE_BEAUTIFULLY));
            cSFilterGroup.addItem(initFilterRes("F9", "filter/image/mode6.png", GPUFilterType.FADE_COOL_HAZE));
        } else if (str.equals(FILTERGROUPVINTAGE)) {
            cSFilterGroup.addItem(initFilterRes("V1", "filter/image/mode6.png", GPUFilterType.Y1970));
            cSFilterGroup.addItem(initFilterRes("V2", "filter/image/mode6.png", GPUFilterType.Y1975));
            cSFilterGroup.addItem(initFilterRes("V3", "filter/image/mode6.png", GPUFilterType.Y1977));
            cSFilterGroup.addItem(initFilterRes("V4", "filter/image/mode6.png", GPUFilterType.LOFI));
            cSFilterGroup.addItem(initFilterRes("V5", "filter/image/mode6.png", GPUFilterType.XPRO2));
            cSFilterGroup.addItem(initFilterRes("V6", "filter/image/mode6.png", GPUFilterType.EARLYBIRD));
            cSFilterGroup.addItem(initFilterRes("V7", "filter/image/mode6.png", GPUFilterType.SUTRO));
            cSFilterGroup.addItem(initFilterRes("V8", "filter/image/mode6.png", GPUFilterType.FADE_BEAUTIFULLY));
            cSFilterGroup.addItem(initFilterRes("V9", "filter/image/mode6.png", GPUFilterType.FADE_COOL_HAZE));
        }
        return cSFilterGroup;
    }

    private CSGPUFilterImageRes initFilterRes(String str, String str2, GPUFilterType gPUFilterType) {
        CSGPUFilterImageRes cSGPUFilterImageRes = new CSGPUFilterImageRes();
        cSGPUFilterImageRes.setContext(this.mContext);
        cSGPUFilterImageRes.setName(str);
        cSGPUFilterImageRes.setShowText(str);
        cSGPUFilterImageRes.setFilterType(gPUFilterType);
        cSGPUFilterImageRes.setIconFileName(str2);
        cSGPUFilterImageRes.setIconType(DMWBRes.LocationType.FILTERED);
        cSGPUFilterImageRes.setImageType(DMWBRes.LocationType.ASSERT);
        cSGPUFilterImageRes.setSRC(this.srcbmp);
        return cSGPUFilterImageRes;
    }

    protected CSAdjustableFilterRes initFilterRes(String str, String str2, GPUFilterType gPUFilterType, int i) {
        CSAdjustableFilterRes cSAdjustableFilterRes = new CSAdjustableFilterRes();
        cSAdjustableFilterRes.setContext(this.mContext);
        cSAdjustableFilterRes.setName(str);
        cSAdjustableFilterRes.setShowText(str);
        cSAdjustableFilterRes.setFilterType(gPUFilterType);
        cSAdjustableFilterRes.setIconFileName(str2);
        cSAdjustableFilterRes.setMix(i);
        cSAdjustableFilterRes.setContext(this.mContext);
        cSAdjustableFilterRes.setIconType(DMWBRes.LocationType.FILTERED);
        cSAdjustableFilterRes.setSRC(this.srcbmp);
        return cSAdjustableFilterRes;
    }

    public List<DMWBRes> getFilterGroupList() {
        return this.filterGroupList;
    }

    public List<DMWBRes> getFilterList() {
        ArrayList arrayList = new ArrayList();
        for (DMWBRes dMWBRes : this.filterGroupList) {
            if (dMWBRes instanceof CSFilterGroup) {
                for (CSGPUFilterImageRes cSGPUFilterImageRes : ((CSFilterGroup) dMWBRes).getFilterResList()) {
                    arrayList.add(cSGPUFilterImageRes);
                }
            }
        }
        return arrayList;
    }
}
