package com.baiwang.libuiinstalens.filterbar2;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.filter.gpu.GPUFilterType;
import com.picspool.lib.resource.DMWBRes;

import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class CSFilterBarStyle2Manager {
    private static final String FILTERGROUPBW = "filter_group_bw";
    private static final String FILTERGROUPCLASSIC = "filter_group_classic";
    private static final String FILTERGROUPFADE = "filter_group_fade";
    private static final String FILTERGROUPFILM = "filter_group_film";
    private static final String FILTERGROUPLOMO = "filter_group_lomo";
    private static final String FILTERGROUPSEASON = "filter_group_season";
    private static final String FILTERGROUPSWEET = "filter_group_sweet";
    private final List<DMWBRes> filterGroupList = new ArrayList();
    private final Context mContext;
    private final Bitmap srcbmp;

    public CSFilterBarStyle2Manager(Context context, Bitmap bitmap) {
        this.mContext = context;
        this.srcbmp = DMBitmapUtil.sampeZoomFromBitmap(bitmap, 120);
        initList();
    }

    private void initList() {
        this.filterGroupList.add(initFilterGoup(FILTERGROUPBW, "", "filter/image/mode7.png"));
    }

    private CSFilterGroup initFilterGoup(String str, String str2, String str3) {
        CSFilterGroup cSFilterGroup = getCsFilterGroup(str, str2, str3);
        switch (str) {
            case FILTERGROUPBW -> {
                cSFilterGroup.addItem(initFilterRes("normal", "filter/image/angel.png", GPUFilterType.NOFILTER));
                cSFilterGroup.addItem(initFilterRes1_10("romance", "filter/camera_filter/6romance.acv", GPUFilterType.NOFILTER));
                cSFilterGroup.addItem(initFilterRes1_10("memory", "filter/camera_filter/1memory.acv", GPUFilterType.NOFILTER));
                cSFilterGroup.addItem(initFilterRes1_10("pink", "filter/camera_filter/3pink.acv", GPUFilterType.NOFILTER));
                cSFilterGroup.addItem(initFilterRes1_10("chocolate", "filter/camera_filter/4chocolate.acv", GPUFilterType.NOFILTER));
                cSFilterGroup.addItem(initFilterRes1_10("princess", "filter/camera_filter/8princess.acv", GPUFilterType.NOFILTER));
                cSFilterGroup.addItem(initFilterRes1_10("ice", "filter/camera_filter/15ice.acv", GPUFilterType.NOFILTER));
                cSFilterGroup.addItem(initFilterRes1_10("sunshine", "filter/camera_filter/17sunshine.acv", GPUFilterType.NOFILTER));
                cSFilterGroup.addItem(initFilterRes1_10("baby", "filter/camera_filter/18baby.acv", GPUFilterType.NOFILTER));
                cSFilterGroup.addItem(initFilterRes1_10("love", "filter/camera_filter/21love.acv", GPUFilterType.NOFILTER));
                cSFilterGroup.addItem(initFilterRes1_10("angel", "filter/camera_filter/11angel.acv", GPUFilterType.NOFILTER));
                cSFilterGroup.addItem(initFilterRes("vivien", "filter/icon/angel.png", GPUFilterType.VIVIEN));
                cSFilterGroup.addItem(initFilterRes("audrey", "filter/icon/angel.png", GPUFilterType.AUDREY));
                cSFilterGroup.addItem(initFilterRes1_10("grace", "filter/camera_filter/Suri.acv", GPUFilterType.NOFILTER));
                cSFilterGroup.addItem(initFilterRes1_10("judy", "filter/camera_filter/Alsa.acv", GPUFilterType.NOFILTER));
                cSFilterGroup.addItem(initFilterRes1_10("taylor", "filter/camera_filter/Taylor.acv", GPUFilterType.NOFILTER));
                cSFilterGroup.addItem(initFilterRes1_10("sophia", "filter/camera_filter/Sophia.acv", GPUFilterType.NOFILTER));
                cSFilterGroup.addItem(initFilterRes1_10("monroe", "filter/camera_filter/Monroe.acv", GPUFilterType.NOFILTER));
                cSFilterGroup.addItem(initFilterRes1_10("shirley", "filter/camera_filter/Shirley.acv", GPUFilterType.NOFILTER));
                cSFilterGroup.addItem(initFilterRes1_10("garbo", "filter/camera_filter/Garbo.acv", GPUFilterType.NOFILTER));
                cSFilterGroup.addItem(initFilterRes1_10("hepburn", "filter/camera_filter/Hepburn.acv", GPUFilterType.NOFILTER));
                cSFilterGroup.addItem(initFilterRes1_10("joan", "filter/camera_filter/Miho.acv", GPUFilterType.NOFILTER));
                cSFilterGroup.addItem(initFilterRes1_10("marlene", "filter/camera_filter/Liliane.acv", GPUFilterType.NOFILTER));
            }
            case FILTERGROUPSEASON -> {
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
            }
            case FILTERGROUPCLASSIC -> {
                cSFilterGroup.addItem(initFilterRes("ORI", "filter/image/mode5.png", GPUFilterType.NOFILTER));
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
            }
            case FILTERGROUPSWEET -> {
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
            }
            case FILTERGROUPLOMO -> {
                cSFilterGroup.addItem(initFilterRes("L1", GPUFilterType.LOMO1, 40));
                cSFilterGroup.addItem(initFilterRes("L2", GPUFilterType.LOMO2, 40));
                cSFilterGroup.addItem(initFilterRes("L3", GPUFilterType.LOMO6, 80));
                cSFilterGroup.addItem(initFilterRes("L4", GPUFilterType.LOMO8, 45));
                cSFilterGroup.addItem(initFilterRes("L5", GPUFilterType.LOMO3, 60));
                cSFilterGroup.addItem(initFilterRes("L6", GPUFilterType.LOMO12, 50));
                cSFilterGroup.addItem(initFilterRes("L7", GPUFilterType.LOMO16, 15));
                cSFilterGroup.addItem(initFilterRes("L8", GPUFilterType.LOMO9, 30));
                cSFilterGroup.addItem(initFilterRes("L9", GPUFilterType.LOMO11, 80));
                cSFilterGroup.addItem(initFilterRes("L10", GPUFilterType.LOMO5, 80));
                cSFilterGroup.addItem(initFilterRes("L11", GPUFilterType.LOMO27, 90));
                cSFilterGroup.addItem(initFilterRes("L12", GPUFilterType.LOMO15, 30));
                cSFilterGroup.addItem(initFilterRes("L13", GPUFilterType.LOMO13, 100));
                cSFilterGroup.addItem(initFilterRes("L14", GPUFilterType.LOMO28, 40));
                cSFilterGroup.addItem(initFilterRes("L15", GPUFilterType.LOMO18, 60));
                cSFilterGroup.addItem(initFilterRes("L16", GPUFilterType.LOMO20, 70));
            }
            case FILTERGROUPFILM -> {
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
            }
            case FILTERGROUPFADE -> {
                cSFilterGroup.addItem(initFilterRes("F1", "filter/image/mode6.png", GPUFilterType.FADE_DARK_DESATURATE));
                cSFilterGroup.addItem(initFilterRes("F2", "filter/image/mode6.png", GPUFilterType.FADE_DIFFUSED_MATTE));
                cSFilterGroup.addItem(initFilterRes("F3", "filter/image/mode6.png", GPUFilterType.FADE_EVERYDAY));
                cSFilterGroup.addItem(initFilterRes("F4", "filter/image/mode6.png", GPUFilterType.FADE_LIME));
                cSFilterGroup.addItem(initFilterRes("F5", "filter/image/mode6.png", GPUFilterType.FADE_LUCID));
                cSFilterGroup.addItem(initFilterRes("F6", "filter/image/mode6.png", GPUFilterType.FADE_RETRO));
                cSFilterGroup.addItem(initFilterRes("F7", "filter/image/mode6.png", GPUFilterType.FADE_WHITE_WASH));
                cSFilterGroup.addItem(initFilterRes("F8", "filter/image/mode6.png", GPUFilterType.FADE_BEAUTIFULLY));
                cSFilterGroup.addItem(initFilterRes("F9", "filter/image/mode6.png", GPUFilterType.FADE_COOL_HAZE));
            }
        }
        return cSFilterGroup;
    }

    @NonNull
    private CSFilterGroup getCsFilterGroup(String str, String str2, String str3) {
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

    private CSGPUFilterImageRes initFilterRes1_10(String str, String str3, GPUFilterType gPUFilterType) {
        CSGPUFilterImageRes cSGPUFilterImageRes = new CSGPUFilterImageRes();
        cSGPUFilterImageRes.setContext(this.mContext);
        cSGPUFilterImageRes.setName(str);
        cSGPUFilterImageRes.setShowText(str);
        cSGPUFilterImageRes.setFilterType(gPUFilterType);
        cSGPUFilterImageRes.setIconFileName(str3);
        cSGPUFilterImageRes.setIconType(DMWBRes.LocationType.FILTERED);
        cSGPUFilterImageRes.setImageType(DMWBRes.LocationType.ASSERT);
        cSGPUFilterImageRes.setSRC(this.srcbmp);
        return cSGPUFilterImageRes;
    }

    protected CSAdjustableFilterRes initFilterRes(String str, GPUFilterType gPUFilterType, int i) {
        CSAdjustableFilterRes cSAdjustableFilterRes = new CSAdjustableFilterRes();
        cSAdjustableFilterRes.setContext(this.mContext);
        cSAdjustableFilterRes.setName(str);
        cSAdjustableFilterRes.setShowText(str);
        cSAdjustableFilterRes.setFilterType(gPUFilterType);
        cSAdjustableFilterRes.setIconFileName("filter/image/mode4.png");
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
        ArrayList<DMWBRes> arrayList = new ArrayList<>();
        for (DMWBRes dMWBRes : this.filterGroupList) {
            if (dMWBRes instanceof CSFilterGroup) {
                arrayList.addAll(((CSFilterGroup) dMWBRes).getFilterResList());
            }
        }
        return arrayList;
    }
}
