package com.baiwang.libuiinstalens.filter;

import android.content.Context;
import android.graphics.Bitmap;

import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.filter.gpu.GPUFilterType;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;
import com.sky.testproject.R;

import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class CSSquareUiLidowFilterManager implements DMWBManager {
    private static final int ALL = 0;
    public static final int BWFILTER = 7;
    public static final int CLASSICFILTER = 2;
    public static final int FADEFILTER = 6;
    public static final int FILMFILTER = 5;
    public static final int FOODFILTER = 17;
    public static final int HALOFILTER = 9;
    public static final int LIKE = 0;
    public static final int LOMOFILTER = 4;
    public static final int RETROFILTER = 10;
    public static final int SEASONFILTER = 1;
    public static final int SWEETFILTER = 3;
    public static final int VINTAGEFILTER = 8;
    private String isFilterLike;
    private final Context mContext;
    private List<GPUFilterRes> resList;
    private String strValue;

    @Override // org.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    public CSSquareUiLidowFilterManager(Context context) {
        mContext = context;
        resList.add(initAssetItem("ORI", "", GPUFilterType.NOFILTER));
        resList.add(initAssetItem("C1", "filter/Film/Agx100.jpg", GPUFilterType.LOFI));
        resList.add(initAssetItem("C2", "filter/Film/Kuc100.jpg", GPUFilterType.XPRO2));
        resList.add(initAssetItem("C3", "filter/Era/1977.jpg", GPUFilterType.Y1977));
        resList.add(initAssetItem("C4", "filter/Vintage/Weson.jpg", GPUFilterType.EARLYBIRD));
        resList.add(initAssetItem("C5", "filter/Vintage/Lethe.jpg", GPUFilterType.SUTRO));
        resList.add(initAssetItem("C6", "filter/Era/1970.jpg", GPUFilterType.Y1970));
        resList.add(initAssetItem("C7", "filter/Era/1974.jpg", GPUFilterType.Y1975));
        resList.add(initAssetItem("C8", "filter/Classic/Alone.jpg", GPUFilterType.KELVIN));
        resList.add(initAssetItem("C9", "filter/Classic/Passion.jpg", GPUFilterType.HEFE));
        resList.add(initAssetItem("C10", "filter/Classic/Vigour.jpg", GPUFilterType.TOASTER));
        resList.add(initAssetItem("C11", "filter/Classic/Drama.jpg", GPUFilterType.BRANNAN));
        resList.add(initAssetItem("C12", "filter/Classic/Pizazz.jpg", GPUFilterType.NASHVILLE));
        resList.add(initAssetItem("B1", "filter/BW/Kopan.jpg", GPUFilterType.GINGHAM));
        resList.add(initAssetItem("B2", "filter/BW/BWRetro.jpg", GPUFilterType.CHARMES));
        resList.add(initAssetItem("B3", "filter/BW/Selium.jpg", GPUFilterType.WILLOW));
        resList.add(initAssetItem("B4", "filter/BW/Selium.jpg", GPUFilterType.ASHBY));
        resList.add(initAssetItem("B5", "filter/BW/BWRetro.jpg", GPUFilterType.BWRetro));
        resList.add(initAssetItem("B6", "filter/BW/Kopan.jpg", GPUFilterType.CLARENDON));
        resList.add(initAssetItem("B7", "filter/BW/Kopan.jpg", GPUFilterType.INKWELL));
        resList.add(initAssetItem("B8", "filter/BW/Kopan.jpg", GPUFilterType.DOGPATCH));
        resList.add(initAssetItem("V1", "filter/Classic/Fade.jpg", GPUFilterType.VALENCIA));
        resList.add(initAssetItem("V2", "filter/Classic/Gloss.jpg", GPUFilterType.AMARO));
        resList.add(initAssetItem("V3", "filter/Classic/Pale.jpg", GPUFilterType.WALDEN));
        resList.add(initAssetItem("V4", "filter/Classic/Icy.jpg", GPUFilterType.HUDSON));
        resList.add(initAssetItem("V5", "filter/Classic/Versa.jpg", GPUFilterType.MAYFAIR));
        resList.add(initAssetItem("V6", "filter/Classic/Listless.jpg", GPUFilterType.RISE));
        resList.add(initAssetItem("V7", "filter/Classic/Mild.jpg", GPUFilterType.SIERRA));
    }

    public CSSquareUiLidowFilterManager(Context context, int i, String str) {
        isFilterLike = str;
        mContext = context;
        if (i == BWFILTER) {
            resList.clear();
            resList.add(initAssetItem("ORI", "filter/image/mode7.png", GPUFilterType.NOFILTER));
            resList.add(initAssetItem("B1", "filter/image/mode7.png", GPUFilterType.GINGHAM));
            resList.add(initAssetItem("B2", "filter/image/mode7.png", GPUFilterType.CHARMES));
            resList.add(initAssetItem("B3", "filter/image/mode7.png", GPUFilterType.WILLOW));
            resList.add(initAssetItem("B4", "filter/image/mode7.png", GPUFilterType.ASHBY));
            resList.add(initAssetItem("B5", "filter/image/mode7.png", GPUFilterType.BWRetro));
            resList.add(initAssetItem("B6", "filter/image/mode7.png", GPUFilterType.CLARENDON));
            resList.add(initAssetItem("B7", "filter/image/mode7.png", GPUFilterType.INKWELL));
            resList.add(initAssetItem("B8", "filter/image/mode7.png", GPUFilterType.DOGPATCH));
        }
        if (i == CLASSICFILTER) {
            resList.clear();
            resList.add(initAssetItem("ORI", "filter/image/mode5.png", GPUFilterType.NOFILTER));
            resList.add(initAssetItem("C1", "filter/image/mode5.png", GPUFilterType.AMARO));
            resList.add(initAssetItem("C2", "filter/image/mode5.png", GPUFilterType.MAYFAIR));
            resList.add(initAssetItem("C3", "filter/image/mode5.png", GPUFilterType.RISE));
            resList.add(initAssetItem("C4", "filter/image/mode5.png", GPUFilterType.HUDSON));
            resList.add(initAssetItem("C5", "filter/image/mode5.png", GPUFilterType.VALENCIA));
            resList.add(initAssetItem("C6", "filter/image/mode5.png", GPUFilterType.SIERRA));
            resList.add(initAssetItem("C7", "filter/image/mode5.png", GPUFilterType.TOASTER));
            resList.add(initAssetItem("C8", "filter/image/mode5.png", GPUFilterType.BRANNAN));
            resList.add(initAssetItem("C9", "filter/image/mode5.png", GPUFilterType.WALDEN));
            resList.add(initAssetItem("C10", "filter/image/mode5.png", GPUFilterType.HEFE));
            resList.add(initAssetItem("C11", "filter/image/mode5.png", GPUFilterType.NASHVILLE));
            resList.add(initAssetItem("C12", "filter/image/mode5.png", GPUFilterType.KELVIN));
        }
        if (i == FADEFILTER) {
            resList.clear();
            resList.add(initAssetItem("ORI", "filter/image/mode6.png", GPUFilterType.NOFILTER));
            resList.add(initAssetItem("F1", "filter/image/mode6.png", GPUFilterType.FADE_DARK_DESATURATE));
            resList.add(initAssetItem("F2", "filter/image/mode6.png", GPUFilterType.FADE_DIFFUSED_MATTE));
            resList.add(initAssetItem("F3", "filter/image/mode6.png", GPUFilterType.FADE_EVERYDAY));
            resList.add(initAssetItem("F4", "filter/image/mode6.png", GPUFilterType.FADE_LIME));
            resList.add(initAssetItem("F5", "filter/image/mode6.png", GPUFilterType.FADE_LUCID));
            resList.add(initAssetItem("F6", "filter/image/mode6.png", GPUFilterType.FADE_RETRO));
            resList.add(initAssetItem("F7", "filter/image/mode6.png", GPUFilterType.FADE_WHITE_WASH));
            resList.add(initAssetItem("F8", "filter/image/mode6.png", GPUFilterType.FADE_BEAUTIFULLY));
            resList.add(initAssetItem("F9", "filter/image/mode6.png", GPUFilterType.FADE_COOL_HAZE));
        }
        if (i == FILMFILTER) {
            resList.clear();
            resList.add(initAssetItem("ORI", "filter/image/mode2.png", GPUFilterType.NOFILTER));
            resList.add(initAssetItem("M1", "filter/image/mode2.png", GPUFilterType.FILM_16));
            resList.add(initAssetItem("M2", "filter/image/mode2.png", GPUFilterType.FILM_3));
            resList.add(initAssetItem("M3", "filter/image/mode2.png", GPUFilterType.FILM_B_VOL));
            resList.add(initAssetItem("M4", "filter/image/mode2.png", GPUFilterType.FILM_CARINA));
            resList.add(initAssetItem("M5", "filter/image/mode2.png", GPUFilterType.FILM_CLASSIC_BLUE));
            resList.add(initAssetItem("M6", "filter/image/mode2.png", GPUFilterType.FILM_COOL_BREEZE));
            resList.add(initAssetItem("M7", "filter/image/mode2.png", GPUFilterType.FILM_COOLER));
            resList.add(initAssetItem("M8", "filter/image/mode2.png", GPUFilterType.FILM_CP_12));
            resList.add(initAssetItem("M9", "filter/image/mode2.png", GPUFilterType.FILM_PREMIUM_6));
            resList.add(initAssetItem("M10", "filter/image/mode2.png", GPUFilterType.FILM_GREY_LIGHT));
            resList.add(initAssetItem("M11", "filter/image/mode2.png", GPUFilterType.FILM_LUST));
            resList.add(initAssetItem("M12", "filter/image/mode2.png", GPUFilterType.FILM_PAPRIKA));
        }
        if (i == FOODFILTER) {
            resList.clear();
            resList.add(initAssetItem("F1", "filter/food_1.jpg", GPUFilterType.FOOD_ADJUST_TONE_COOL_SHADOWS));
            resList.add(initAssetItem("F2", "filter/food_2.jpg", GPUFilterType.FOOD_BRIGHTEN_MIDTONES));
            resList.add(initAssetItem("F3", "filter/food_3.jpg", GPUFilterType.FOOD_CALI));
            resList.add(initAssetItem("F4", "filter/food_4.jpg", GPUFilterType.FOOD_CONTRAST_HIGH_KEY));
            resList.add(initAssetItem("F5", "filter/food_5.jpg", GPUFilterType.FOOD_DETAILS_PAINT_IN_SATURATION));
            resList.add(initAssetItem("F6", "filter/food_6.jpg", GPUFilterType.FOOD_FIRST_CLASS));
            resList.add(initAssetItem("F7", "filter/food_7.jpg", GPUFilterType.FOOD_GEMMA));
            resList.add(initAssetItem("F8", "filter/food_8.jpg", GPUFilterType.FOOD_LUCIANA));
            resList.add(initAssetItem("F9", "filter/food_9.jpg", GPUFilterType.FOOD_ORTON));
            resList.add(initAssetItem("F10", "filter/food_10.jpg", GPUFilterType.FOOD_PRETTY_PEEPERS));
            resList.add(initAssetItem("F11", "filter/food_11.jpg", GPUFilterType.FOOD_RESTORE_COLOR));
        }
        if (i == HALOFILTER) {
            resList.clear();
            resList.add(initAssetItem("ORI", "filter/image/mode9.png", GPUFilterType.NOFILTER));
            resList.add(initAssetItem("H1", "filter/image/mode9.png", GPUFilterType.HALO4));
            resList.add(initAssetItem("H2", "filter/image/mode9.png", GPUFilterType.HALO3));
            resList.add(initAssetItem("H3", "filter/image/mode9.png", GPUFilterType.HALO2));
            resList.add(initAssetItem("H4", "filter/image/mode9.png", GPUFilterType.HALO7));
            resList.add(initAssetItem("H5", "filter/image/mode9.png", GPUFilterType.HALO6));
            resList.add(initAssetItem("H6", "filter/image/mode9.png", GPUFilterType.HALO5));
            resList.add(initAssetItem("H7", "filter/image/mode9.png", GPUFilterType.HALO1));
        }
        if (i == LOMOFILTER) {
            resList.clear();
            resList.add(initAssetItem("ORI", "filter/image/mode4.png", GPUFilterType.NOFILTER));
            resList.add(initAssetItem("L1", "filter/image/mode4.png", GPUFilterType.LOMO1, 40));
            resList.add(initAssetItem("L2", "filter/image/mode4.png", GPUFilterType.LOMO2, 40));
            resList.add(initAssetItem("L3", "filter/image/mode4.png", GPUFilterType.LOMO6, 80));
            resList.add(initAssetItem("L4", "filter/image/mode4.png", GPUFilterType.LOMO8, 45));
            resList.add(initAssetItem("L5", "filter/image/mode4.png", GPUFilterType.LOMO3, 60));
            resList.add(initAssetItem("L6", "filter/image/mode4.png", GPUFilterType.LOMO12, 50));
            resList.add(initAssetItem("L7", "filter/image/mode4.png", GPUFilterType.LOMO16, 15));
            resList.add(initAssetItem("L8", "filter/image/mode4.png", GPUFilterType.LOMO9, 30));
            resList.add(initAssetItem("L9", "filter/image/mode4.png", GPUFilterType.LOMO11, 80));
            resList.add(initAssetItem("L10", "filter/image/mode4.png", GPUFilterType.LOMO5, 80));
            resList.add(initAssetItem("L11", "filter/image/mode4.png", GPUFilterType.LOMO27, 90));
            resList.add(initAssetItem("L12", "filter/image/mode4.png", GPUFilterType.LOMO15, 30));
        }
        if (i == RETROFILTER) {
            resList.clear();
            resList.add(initAssetItem("R1", "filter/retro/r1.jpg", GPUFilterType.RETRO_PS));
            resList.add(initAssetItem("R2", "filter/retro/r2.jpg", GPUFilterType.RETRO_A_VOL_1));
            resList.add(initAssetItem("R3", "filter/retro/r3.jpg", GPUFilterType.RETRO_A_VOL_2));
            resList.add(initAssetItem("R4", "filter/retro/r5.jpg", GPUFilterType.RETRO_A_VOL_4));
            resList.add(initAssetItem("R5", "filter/retro/r6.jpg", GPUFilterType.RETRO_A_VOL_12));
            resList.add(initAssetItem("R6", "filter/retro/r7.jpg", GPUFilterType.RETRO_A_VOL_20));
            resList.add(initAssetItem("R7", "filter/retro/r8.jpg", GPUFilterType.RETRO_A_VOL_22));
            resList.add(initAssetItem("R8", "filter/retro/r9.jpg", GPUFilterType.RETRO_AMBITIOUS));
            resList.add(initAssetItem("R9", "filter/retro/r10.jpg", GPUFilterType.RETRO_BRISK));
            resList.add(initAssetItem("R10", "filter/retro/r11.jpg", GPUFilterType.RETRO_C_VOL_2));
            resList.add(initAssetItem("R11", "filter/retro/r12.jpg", GPUFilterType.RETRO_C_VOL_8));
            resList.add(initAssetItem("R12", "filter/retro/r13.jpg", GPUFilterType.RETRO_C_VOL_13));
            resList.add(initAssetItem("R13", "filter/retro/r14.jpg", GPUFilterType.RETRO_CHESTNUT_BROWN));
            resList.add(initAssetItem("R14", "filter/retro/r15.jpg", GPUFilterType.RETRO_CP_24));
            resList.add(initAssetItem("R15", "filter/retro/r16.jpg", GPUFilterType.RETRO_DELICATE_BROWN));
            resList.add(initAssetItem("R16", "filter/retro/r18.jpg", GPUFilterType.RETRO_FLASH_BACK));
            resList.add(initAssetItem("R17", "filter/retro/r22.jpg", GPUFilterType.RETRO_PREMIUM));
            resList.add(initAssetItem("R18", "filter/retro/r23.jpg", GPUFilterType.RETRO_3));
            resList.add(initAssetItem("R19", "filter/retro/r24.jpg", GPUFilterType.RETRO_17));
            resList.add(initAssetItem("R20", "filter/retro/r26.jpg", GPUFilterType.RETRO_NIGHT_FATE));
            resList.add(initAssetItem("R21", "filter/retro/r27.jpg", GPUFilterType.RETRO_SPIRITED));
            resList.add(initAssetItem("R22", "filter/retro/r27.jpg", GPUFilterType.RETRO_VINTAGE));
        }
        if (i == SEASONFILTER) {
            resList.clear();
            resList.add(initAssetItem("ORI", "filter/image/mode3.png", GPUFilterType.NOFILTER));
            resList.add(initAssetItem("S1", "filter/image/mode3.png", GPUFilterType.SEASON_SPRING_BLOSSOM));
            resList.add(initAssetItem("S2", "filter/image/mode3.png", GPUFilterType.SEASON_AUTUMN_DAWOOD_HAMADA));
            resList.add(initAssetItem("S3", "filter/image/mode3.png", GPUFilterType.SEASON_WINTER_ICED));
            resList.add(initAssetItem("S4", "filter/image/mode3.png", GPUFilterType.SEASON_AUTUMN_GENTLE));
            resList.add(initAssetItem("S5", "filter/image/mode3.png", GPUFilterType.SEASON_SPRING_GLORIOUS_BABY));
            resList.add(initAssetItem("S6", "filter/image/mode3.png", GPUFilterType.SEASON_SUMMER_DAY));
            resList.add(initAssetItem("S7", "filter/image/mode3.png", GPUFilterType.SEASON_SUMMER_CLASSIC));
            resList.add(initAssetItem("S8", "filter/image/mode3.png", GPUFilterType.SEASON_SUMMER_INDIAN));
            resList.add(initAssetItem("S9", "filter/image/mode3.png", GPUFilterType.SEASON_AUTUMN_PREMIUM));
            resList.add(initAssetItem("S10", "filter/image/mode3.png", GPUFilterType.SEASON_SPRING_LIGHT));
            resList.add(initAssetItem("S11", "filter/image/mode3.png", GPUFilterType.SEASON_WINTER_SNAPPY_BABY));
            resList.add(initAssetItem("S12", "filter/image/mode3.png", GPUFilterType.SEASON_WINTER_SOFT_BROWN));
        }
        if (i == SWEETFILTER) {
            resList.clear();
            resList.add(initAssetItem("ORI", "filter/image/mode1.png", GPUFilterType.NOFILTER));
            resList.add(initAssetItem("S1", "filter/image/mode1.png", GPUFilterType.SWEET_PREMIUM));
            resList.add(initAssetItem("S2", "filter/image/mode1.png", GPUFilterType.SWEET_CERULEAN_BLUE));
            resList.add(initAssetItem("S3", "filter/image/mode1.png", GPUFilterType.SWEET_DEEP_PURPLE));
            resList.add(initAssetItem("S4", "filter/image/mode1.png", GPUFilterType.SWEET_HAZY_TEAL));
            resList.add(initAssetItem("S5", "filter/image/mode1.png", GPUFilterType.SWEET_LAVENDER_HAZE));
            resList.add(initAssetItem("S6", "filter/image/mode1.png", GPUFilterType.SWEET_MAGENTA));
            resList.add(initAssetItem("S7", "filter/image/mode1.png", GPUFilterType.SWEET_MORNING_GLOW));
            resList.add(initAssetItem("S8", "filter/image/mode1.png", GPUFilterType.SWEET_PRIMUEM));
            resList.add(initAssetItem("S9", "filter/image/mode1.png", GPUFilterType.SWEET_ROMANCE));
            resList.add(initAssetItem("S10", "filter/image/mode1.png", GPUFilterType.SWEET_RUSTY_TINT));
            resList.add(initAssetItem("S11", "filter/image/mode1.png", GPUFilterType.SWEET_SO_COOL));
            resList.add(initAssetItem("S12", "filter/image/mode1.png", GPUFilterType.SWEET_SWEET));
        }
        if (i == VINTAGEFILTER) {
            resList.clear();
            resList.add(initAssetItem("ORI", "filter/image/mode8.png", GPUFilterType.NOFILTER));
            resList.add(initAssetItem("V1", "filter/image/mode8.png", GPUFilterType.Y1970));
            resList.add(initAssetItem("V2", "filter/image/mode8.png", GPUFilterType.Y1975));
            resList.add(initAssetItem("V3", "filter/image/mode8.png", GPUFilterType.Y1977));
            resList.add(initAssetItem("V4", "filter/image/mode8.png", GPUFilterType.LOFI));
            resList.add(initAssetItem("V5", "filter/image/mode8.png", GPUFilterType.XPRO2));
            resList.add(initAssetItem("V6", "filter/image/mode8.png", GPUFilterType.EARLYBIRD));
            resList.add(initAssetItem("V7", "filter/image/mode8.png", GPUFilterType.SUTRO));
        }
        if (i == ALL) {
            resList.clear();
            resList.add(initAssetItem("ORI", "filter/image/mode0.png", GPUFilterType.NOFILTER));
            if (str == null || str.isEmpty()) {
                return;
            }
            String[] split = str.split(",");
            for (int i2 = 0; i2 < split.length / 3; i2++) {
                int i3 = i2 * 3;
                resList.add(initAssetItem(split[i3], split[i3 + 2], GPUFilterType.valueOf(split[i3 + 1])));
            }
        }
    }

    protected GPUFilterRes initAssetItem(String str, String str2, GPUFilterType gPUFilterType) {
        Bitmap imageFromAssetsFile = DMBitmapUtil.getImageFromAssetsFile(mContext.getResources(), str2);
        GPUFilterRes gPUFilterRes = new GPUFilterRes();
        gPUFilterRes.setContext(mContext);
        gPUFilterRes.setName(str);
        gPUFilterRes.setFilterType(gPUFilterType);
        gPUFilterRes.setIconFileName(str2);
        gPUFilterRes.setIconType(DMWBRes.LocationType.FILTERED);
        gPUFilterRes.setImageType(DMWBRes.LocationType.ASSERT);
        gPUFilterRes.setShowText(str);
        gPUFilterRes.setIsShowText(true);
        gPUFilterRes.setSRC(imageFromAssetsFile);
        String str3 = isFilterLike;
        if (str3 != null && !str3.isEmpty()) {
            String str4 = isFilterLike;
            gPUFilterRes.setIsShowLikeIcon(str4.contains(str + "," + gPUFilterType + "," + str2));
        }
        return gPUFilterRes;
    }

    protected CSAdjustableFilterRes initAssetItem(String str, String str2, GPUFilterType gPUFilterType, int i) {
        Bitmap imageFromAssetsFile = DMBitmapUtil.getImageFromAssetsFile(mContext.getResources(), str2);
        CSAdjustableFilterRes cSAdjustableFilterRes = new CSAdjustableFilterRes();
        cSAdjustableFilterRes.setContext(mContext);
        cSAdjustableFilterRes.setName(str);
        cSAdjustableFilterRes.setFilterType(gPUFilterType);
        cSAdjustableFilterRes.setIconFileName(str2);
        cSAdjustableFilterRes.setIconType(DMWBRes.LocationType.FILTERED);
        cSAdjustableFilterRes.setImageType(DMWBRes.LocationType.ASSERT);
        cSAdjustableFilterRes.setShowText(str);
        cSAdjustableFilterRes.setIsShowText(true);
        cSAdjustableFilterRes.setTextColor(mContext.getResources().getColor(R.color.black));
        cSAdjustableFilterRes.setMix(i);
        cSAdjustableFilterRes.setSRC(imageFromAssetsFile);
        String str3 = isFilterLike;
        if (str3 != null && !str3.isEmpty()) {
            String str4 = isFilterLike;
            cSAdjustableFilterRes.setIsShowLikeIcon(str4.contains(str + "," + gPUFilterType + "," + str2));
        }
        return cSAdjustableFilterRes;
    }

    public String getDesStringValue(String str) {
        strValue = str;
        return str;
    }

    @Override
    public int getCount() {
        return resList.size();
    }

    @Override
    public DMWBRes getRes(int i) {
        return resList.get(i);
    }

    @Override
    public DMWBRes getRes(String str) {
        for (int i = 0; i < resList.size(); i++) {
            GPUFilterRes gPUFilterRes = resList.get(i);
            if (gPUFilterRes.getName().compareTo(str) == 0) {
                return gPUFilterRes;
            }
        }
        return null;
    }
}
