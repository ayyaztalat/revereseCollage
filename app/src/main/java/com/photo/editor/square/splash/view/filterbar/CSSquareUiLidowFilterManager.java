package com.photo.editor.square.splash.view.filterbar;

import android.content.Context;
import android.graphics.Bitmap;

import com.picspool.lib.resource.DMWBRes;

import java.util.ArrayList;
import java.util.List;
import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.filter.gpu.GPUFilterType;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;
import com.sky.testproject.R;

/* loaded from: classes2.dex */
public class CSSquareUiLidowFilterManager implements DMWBManager {
    private static final int ALL = 0;
    public static final int BWFILTER = 8;
    public static final int CLASSICFILTER = 3;
    public static final int FADEFILTER = 7;
    public static final int FILMFILTER = 6;
    public static final int FOODFILTER = 17;
    public static final int FRESH = 1;
    public static final int HALOFILTER = 18;
    public static final int LIKE = 0;
    public static final int LOMOFILTER = 5;
    public static final int RETROFILTER = 10;
    public static final int SEASONFILTER = 2;
    public static final int SWEETFILTER = 4;
    public static final int VINTAGEFILTER = 9;
    private String isFilterLike;
    private Context mContext;
    private int mMode;
    private List<GPUFilterRes> resList;
    private String strValue;

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    public CSSquareUiLidowFilterManager(Context context, int i, String str) {
        String str2;
        String str3;
        ArrayList arrayList = new ArrayList();
        this.resList = arrayList;
        this.isFilterLike = str;
        this.mContext = context;
        this.mMode = i;
        if (i == 8) {
            arrayList.clear();
            this.resList.add(initAssetItem("B1", "filter/image/mode7.png", GPUFilterType.GINGHAM));
            this.resList.add(initAssetItem("B2", "filter/image/mode7.png", GPUFilterType.CHARMES));
            this.resList.add(initAssetItem("B3", "filter/image/mode7.png", GPUFilterType.BWRetro));
            this.resList.add(initAssetItem("B4", "filter/image/mode7.png", GPUFilterType.CLARENDON));
            this.resList.add(initAssetItem("B5", "filter/image/mode7.png", GPUFilterType.INKWELL));
            this.resList.add(initAssetItem("B6", "filter/image/mode7.png", GPUFilterType.DOGPATCH));
        }
        if (i == 3) {
            this.resList.clear();
            this.resList.add(initAssetItem("C1", "filter/image/mode5.png", GPUFilterType.AMARO));
            this.resList.add(initAssetItem("C2", "filter/image/mode5.png", GPUFilterType.MAYFAIR));
            this.resList.add(initAssetItem("C3", "filter/image/mode5.png", GPUFilterType.RISE));
            this.resList.add(initAssetItem("C4", "filter/image/mode5.png", GPUFilterType.HUDSON));
            this.resList.add(initAssetItem("C5", "filter/image/mode5.png", GPUFilterType.SIERRA));
            this.resList.add(initAssetItem("C6", "filter/image/mode5.png", GPUFilterType.HEFE));
        }
        if (i == 7) {
            this.resList.clear();
            this.resList.add(initAssetItem("F12", "filter/image/mode6.png", GPUFilterType.FADE_DARK_DESATURATE));
            this.resList.add(initAssetItem("F2", "filter/image/mode6.png", GPUFilterType.FADE_DIFFUSED_MATTE));
            this.resList.add(initAssetItem("F3", "filter/image/mode6.png", GPUFilterType.FADE_EVERYDAY));
            this.resList.add(initAssetItem("F4", "filter/image/mode6.png", GPUFilterType.FADE_LUCID));
            this.resList.add(initAssetItem("F5", "filter/image/mode6.png", GPUFilterType.FADE_RETRO));
            this.resList.add(initAssetItem("F6", "filter/image/mode6.png", GPUFilterType.FADE_COOL_HAZE));
        }
        if (i == 6) {
            this.resList.clear();
            this.resList.add(initAssetItem("M1", "filter/image/mode2.png", GPUFilterType.FILM_16));
            this.resList.add(initAssetItem("M2", "filter/image/mode2.png", GPUFilterType.FILM_3));
            this.resList.add(initAssetItem("M3", "filter/image/mode2.png", GPUFilterType.FILM_CARINA));
            this.resList.add(initAssetItem("M4", "filter/image/mode2.png", GPUFilterType.FILM_COOL_BREEZE));
            this.resList.add(initAssetItem("M5", "filter/image/mode2.png", GPUFilterType.FILM_COOLER));
            this.resList.add(initAssetItem("M6", "filter/image/mode2.png", GPUFilterType.FILM_CP_12));
            this.resList.add(initAssetItem("M7", "filter/image/mode2.png", GPUFilterType.FILM_PREMIUM_6));
            this.resList.add(initAssetItem("M8", "filter/image/mode2.png", GPUFilterType.FILM_GREY_LIGHT));
            this.resList.add(initAssetItem("M9", "filter/image/mode2.png", GPUFilterType.FILM_LUST));
        }
        if (i == 17) {
            this.resList.clear();
            this.resList.add(initAssetItem("F12", "filter/food_1.jpg", GPUFilterType.FOOD_ADJUST_TONE_COOL_SHADOWS));
            this.resList.add(initAssetItem("F2", "filter/food_2.jpg", GPUFilterType.FOOD_BRIGHTEN_MIDTONES));
            this.resList.add(initAssetItem("F3", "filter/food_3.jpg", GPUFilterType.FOOD_CALI));
            this.resList.add(initAssetItem("F4", "filter/food_4.jpg", GPUFilterType.FOOD_CONTRAST_HIGH_KEY));
            this.resList.add(initAssetItem("F5", "filter/food_5.jpg", GPUFilterType.FOOD_DETAILS_PAINT_IN_SATURATION));
            this.resList.add(initAssetItem("F6", "filter/food_6.jpg", GPUFilterType.FOOD_FIRST_CLASS));
            this.resList.add(initAssetItem("F7", "filter/food_7.jpg", GPUFilterType.FOOD_GEMMA));
            this.resList.add(initAssetItem("F8", "filter/food_8.jpg", GPUFilterType.FOOD_LUCIANA));
            this.resList.add(initAssetItem("F9", "filter/food_9.jpg", GPUFilterType.FOOD_ORTON));
            this.resList.add(initAssetItem("F10", "filter/food_10.jpg", GPUFilterType.FOOD_PRETTY_PEEPERS));
            this.resList.add(initAssetItem("F11", "filter/food_11.jpg", GPUFilterType.FOOD_RESTORE_COLOR));
        }
        if (i == 18) {
            this.resList.clear();
            this.resList.add(initAssetItem("H1", "filter/image/mode9.png", GPUFilterType.HALO4));
            this.resList.add(initAssetItem("H2", "filter/image/mode9.png", GPUFilterType.HALO3));
            this.resList.add(initAssetItem("H3", "filter/image/mode9.png", GPUFilterType.HALO2));
            this.resList.add(initAssetItem("H4", "filter/image/mode9.png", GPUFilterType.HALO7));
            this.resList.add(initAssetItem("H5", "filter/image/mode9.png", GPUFilterType.HALO6));
            this.resList.add(initAssetItem("H6", "filter/image/mode9.png", GPUFilterType.HALO5));
            this.resList.add(initAssetItem("H7", "filter/image/mode9.png", GPUFilterType.HALO1));
        }
        if (i == 5) {
            this.resList.clear();
            str2 = "F11";
            str3 = "F9";
            this.resList.add(initAssetItem("L1", "filter/image/mode4.png", GPUFilterType.LOMO1, 40));
            this.resList.add(initAssetItem("L2", "filter/image/mode4.png", GPUFilterType.LOMO2, 40));
            this.resList.add(initAssetItem("L3", "filter/image/mode4.png", GPUFilterType.LOMO8, 45));
            this.resList.add(initAssetItem("L4", "filter/image/mode4.png", GPUFilterType.LOMO3, 60));
            this.resList.add(initAssetItem("L5", "filter/image/mode4.png", GPUFilterType.LOMO11, 80));
            this.resList.add(initAssetItem("L6", "filter/image/mode4.png", GPUFilterType.LOMO27, 90));
        } else {
            str2 = "F11";
            str3 = "F9";
        }
        if (i == 10) {
            this.resList.clear();
            this.resList.add(initAssetItem("R1", "filter/retro/r1.jpg", GPUFilterType.RETRO_PS));
            this.resList.add(initAssetItem("R2", "filter/retro/r2.jpg", GPUFilterType.RETRO_A_VOL_1));
            this.resList.add(initAssetItem("R3", "filter/retro/r3.jpg", GPUFilterType.RETRO_A_VOL_2));
            this.resList.add(initAssetItem("R4", "filter/retro/r5.jpg", GPUFilterType.RETRO_A_VOL_4));
            this.resList.add(initAssetItem("R5", "filter/retro/r6.jpg", GPUFilterType.RETRO_A_VOL_12));
            this.resList.add(initAssetItem("R6", "filter/retro/r7.jpg", GPUFilterType.RETRO_A_VOL_20));
            this.resList.add(initAssetItem("R7", "filter/retro/r8.jpg", GPUFilterType.RETRO_A_VOL_22));
            this.resList.add(initAssetItem("R8", "filter/retro/r9.jpg", GPUFilterType.RETRO_AMBITIOUS));
            this.resList.add(initAssetItem("R9", "filter/retro/r10.jpg", GPUFilterType.RETRO_BRISK));
            this.resList.add(initAssetItem("R10", "filter/retro/r11.jpg", GPUFilterType.RETRO_C_VOL_2));
            this.resList.add(initAssetItem("R11", "filter/retro/r12.jpg", GPUFilterType.RETRO_C_VOL_8));
            this.resList.add(initAssetItem("R12", "filter/retro/r13.jpg", GPUFilterType.RETRO_C_VOL_13));
            this.resList.add(initAssetItem("R13", "filter/retro/r14.jpg", GPUFilterType.RETRO_CHESTNUT_BROWN));
            this.resList.add(initAssetItem("R14", "filter/retro/r15.jpg", GPUFilterType.RETRO_CP_24));
            this.resList.add(initAssetItem("R15", "filter/retro/r16.jpg", GPUFilterType.RETRO_DELICATE_BROWN));
            this.resList.add(initAssetItem("R16", "filter/retro/r18.jpg", GPUFilterType.RETRO_FLASH_BACK));
            this.resList.add(initAssetItem("R17", "filter/retro/r22.jpg", GPUFilterType.RETRO_PREMIUM));
            this.resList.add(initAssetItem("R18", "filter/retro/r23.jpg", GPUFilterType.RETRO_3));
            this.resList.add(initAssetItem("R19", "filter/retro/r24.jpg", GPUFilterType.RETRO_17));
            this.resList.add(initAssetItem("R20", "filter/retro/r26.jpg", GPUFilterType.RETRO_NIGHT_FATE));
            this.resList.add(initAssetItem("R21", "filter/retro/r27.jpg", GPUFilterType.RETRO_SPIRITED));
            this.resList.add(initAssetItem("R22", "filter/retro/r27.jpg", GPUFilterType.RETRO_VINTAGE));
        }
        if (i == 2) {
            this.resList.clear();
            this.resList.add(initAssetItem("S1", "filter/image/mode3.png", GPUFilterType.SEASON_SPRING_BLOSSOM));
            this.resList.add(initAssetItem("S2", "filter/image/mode3.png", GPUFilterType.SEASON_WINTER_ICED));
            this.resList.add(initAssetItem("S3", "filter/image/mode3.png", GPUFilterType.SEASON_AUTUMN_GENTLE));
            this.resList.add(initAssetItem("S4", "filter/image/mode3.png", GPUFilterType.SEASON_SPRING_GLORIOUS_BABY));
            this.resList.add(initAssetItem("S5", "filter/image/mode3.png", GPUFilterType.SEASON_SUMMER_DAY));
            this.resList.add(initAssetItem("S6", "filter/image/mode3.png", GPUFilterType.SEASON_AUTUMN_PREMIUM));
            this.resList.add(initAssetItem("S7", "filter/image/mode3.png", GPUFilterType.SEASON_SPRING_LIGHT));
            this.resList.add(initAssetItem("S8", "filter/image/mode3.png", GPUFilterType.SEASON_WINTER_SNAPPY_BABY));
        }
        if (i == 4) {
            this.resList.clear();
            this.resList.add(initAssetItem("S1", "filter/image/mode1.png", GPUFilterType.SWEET_PREMIUM));
            this.resList.add(initAssetItem("S2", "filter/image/mode1.png", GPUFilterType.SWEET_CERULEAN_BLUE));
            this.resList.add(initAssetItem("S3", "filter/image/mode1.png", GPUFilterType.SWEET_DEEP_PURPLE));
            this.resList.add(initAssetItem("S4", "filter/image/mode1.png", GPUFilterType.SWEET_LAVENDER_HAZE));
            this.resList.add(initAssetItem("S5", "filter/image/mode1.png", GPUFilterType.SWEET_MAGENTA));
            this.resList.add(initAssetItem("S6", "filter/image/mode1.png", GPUFilterType.SWEET_MORNING_GLOW));
            this.resList.add(initAssetItem("S7", "filter/image/mode1.png", GPUFilterType.SWEET_RUSTY_TINT));
            this.resList.add(initAssetItem("S8", "filter/image/mode1.png", GPUFilterType.SWEET_SO_COOL));
        }
        if (i == 9) {
            this.resList.clear();
            this.resList.add(initAssetItem("V1", "filter/image/mode8.png", GPUFilterType.Y1970));
            this.resList.add(initAssetItem("V2", "filter/image/mode8.png", GPUFilterType.Y1975));
            this.resList.add(initAssetItem("V3", "filter/image/mode8.png", GPUFilterType.Y1977));
            this.resList.add(initAssetItem("V4", "filter/image/mode8.png", GPUFilterType.LOFI));
            this.resList.add(initAssetItem("V5", "filter/image/mode8.png", GPUFilterType.XPRO2));
            this.resList.add(initAssetItem("V6", "filter/image/mode8.png", GPUFilterType.EARLYBIRD));
            this.resList.add(initAssetItem("V7", "filter/image/mode8.png", GPUFilterType.SUTRO));
        }
        if (i == 1) {
            this.resList.clear();
            this.resList.add(initAssetItem("F12", "filter/image/mode9.png", GPUFilterType.F50));
            this.resList.add(initAssetItem("F2", "filter/image/mode9.png", GPUFilterType.F62));
            this.resList.add(initAssetItem("F3", "filter/image/mode9.png", GPUFilterType.F56));
            this.resList.add(initAssetItem("F4", "filter/image/mode9.png", GPUFilterType.F58));
            this.resList.add(initAssetItem("F5", "filter/image/mode9.png", GPUFilterType.F11));
            this.resList.add(initAssetItem("F6", "filter/image/mode9.png", GPUFilterType.F4));
            this.resList.add(initAssetItem("F7", "filter/image/mode9.png", GPUFilterType.F13));
            this.resList.add(initAssetItem("F8", "filter/image/mode9.png", GPUFilterType.F63));
            this.resList.add(initAssetItem(str3, "filter/image/mode9.png", GPUFilterType.F15));
            this.resList.add(initAssetItem(str2, "filter/image/mode9.png", GPUFilterType.F35));
            this.resList.add(initAssetItem("F12", "filter/image/mode9.png", GPUFilterType.F37));
            this.resList.add(initAssetItem("F13", "filter/image/mode9.png", GPUFilterType.F47));
            this.resList.add(initAssetItem("F14", "filter/image/mode9.png", GPUFilterType.F52));
            this.resList.add(initAssetItem("F15", "filter/image/mode9.png", GPUFilterType.F69));
            this.resList.add(initAssetItem("F16", "filter/image/mode9.png", GPUFilterType.F74));
            this.resList.add(initAssetItem("F17", "filter/image/mode9.png", GPUFilterType.F81));
            this.resList.add(initAssetItem("F18", "filter/image/mode9.png", GPUFilterType.F85));
            this.resList.add(initAssetItem("F19", "filter/image/mode9.png", GPUFilterType.F1));
            this.resList.add(initAssetItem("F20", "filter/image/mode9.png", GPUFilterType.F8));
            this.resList.add(initAssetItem("F21", "filter/image/mode9.png", GPUFilterType.F9));
            this.resList.add(initAssetItem("F22", "filter/image/mode9.png", GPUFilterType.F7));
            this.resList.add(initAssetItem("F23", "filter/image/mode9.png", GPUFilterType.F39));
        }
        if (i == 0) {
            this.resList.clear();
            this.resList.add(initAssetItem("ORI", "filter/image/mode0.png", GPUFilterType.NOFILTER));
            if (str == null || "".equals(str)) {
                return;
            }
            String[] split = str.split(",");
            for (int i2 = 0; i2 < split.length / 3; i2++) {
                int i3 = i2 * 3;
                this.resList.add(initAssetItem(split[i3], split[i3 + 2], GPUFilterType.valueOf(split[i3 + 1])));
            }
        }
    }

    protected GPUFilterRes initAssetItem(String str, String str2, GPUFilterType gPUFilterType) {
        Bitmap imageFromAssetsFile = DMBitmapUtil.getImageFromAssetsFile(this.mContext.getResources(), str2);
        GPUFilterRes gPUFilterRes = new GPUFilterRes();
        gPUFilterRes.setContext(this.mContext);
        gPUFilterRes.setName(str);
        gPUFilterRes.setFilterType(gPUFilterType);
        gPUFilterRes.setIconFileName(str2);
        gPUFilterRes.setIconType(DMWBRes.LocationType.FILTERED);
        gPUFilterRes.setImageType(DMWBRes.LocationType.ASSERT);
        gPUFilterRes.setShowText(str);
        gPUFilterRes.setIsShowText(true);
        gPUFilterRes.setSRC(imageFromAssetsFile);
        String str3 = this.isFilterLike;
        if (str3 != null && !"".equals(str3)) {
            String str4 = this.isFilterLike;
            if (str4.contains(str + "," + gPUFilterType + "," + str2)) {
                gPUFilterRes.setIsShowLikeIcon(true);
            } else {
                gPUFilterRes.setIsShowLikeIcon(false);
            }
        }
        return gPUFilterRes;
    }

    protected CSAdjustableFilterRes initAssetItem(String str, String str2, GPUFilterType gPUFilterType, int i) {
        Bitmap imageFromAssetsFile = DMBitmapUtil.getImageFromAssetsFile(this.mContext.getResources(), str2);
        CSAdjustableFilterRes cSAdjustableFilterRes = new CSAdjustableFilterRes();
        cSAdjustableFilterRes.setContext(this.mContext);
        cSAdjustableFilterRes.setName(str);
        cSAdjustableFilterRes.setFilterType(gPUFilterType);
        cSAdjustableFilterRes.setIconFileName(str2);
        cSAdjustableFilterRes.setIconType(DMWBRes.LocationType.FILTERED);
        cSAdjustableFilterRes.setImageType(DMWBRes.LocationType.ASSERT);
        cSAdjustableFilterRes.setShowText(str);
        cSAdjustableFilterRes.setIsShowText(true);
        cSAdjustableFilterRes.setTextColor(this.mContext.getResources().getColor(R.color.black));
        cSAdjustableFilterRes.setMix(i);
        cSAdjustableFilterRes.setSRC(imageFromAssetsFile);
        String str3 = this.isFilterLike;
        if (str3 != null && !"".equals(str3)) {
            String str4 = this.isFilterLike;
            if (str4.contains(str + "," + gPUFilterType + "," + str2)) {
                cSAdjustableFilterRes.setIsShowLikeIcon(true);
            } else {
                cSAdjustableFilterRes.setIsShowLikeIcon(false);
            }
        }
        return cSAdjustableFilterRes;
    }

    public String getDesStringValue(String str) {
        this.strValue = str;
        return str;
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public int getCount() {
        return this.resList.size();
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMWBRes getRes(int i) {
        return this.resList.get(i);
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMWBRes getRes(String str) {
        for (int i = 0; i < this.resList.size(); i++) {
            GPUFilterRes gPUFilterRes = this.resList.get(i);
            if (gPUFilterRes.getName().compareTo(str) == 0) {
                return gPUFilterRes;
            }
        }
        return null;
    }
}
