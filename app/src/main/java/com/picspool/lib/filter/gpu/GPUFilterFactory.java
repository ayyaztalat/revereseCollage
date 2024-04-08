package com.picspool.lib.filter.gpu;

import android.content.Context;
import android.graphics.PointF;
import androidx.core.app.FrameMetricsAggregator;
import androidx.core.view.InputDeviceCompat;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSXlbStickerBarView;
import com.google.firebase.messaging.ServiceStarter;
import java.io.IOException;
import java.util.LinkedList;
import okhttp3.internal.http.StatusLine;
import com.picspool.instafilter.GPUAdjustRange;
import com.picspool.lib.filter.gpu.adjust.GPUImageBrightnessFilter;
import com.picspool.lib.filter.gpu.adjust.GPUImageContrastFilter;
import com.picspool.lib.filter.gpu.adjust.GPUImageExposureFilter;
import com.picspool.lib.filter.gpu.adjust.GPUImageGammaFilter;
import com.picspool.lib.filter.gpu.adjust.GPUImageGaussianBlurSimpleFilter;
import com.picspool.lib.filter.gpu.adjust.GPUImageHighlightShadowFilter;
import com.picspool.lib.filter.gpu.adjust.GPUImageHueFilter;
import com.picspool.lib.filter.gpu.adjust.GPUImageLevelsFilter;
import com.picspool.lib.filter.gpu.adjust.GPUImageNewVibranceFilter;
import com.picspool.lib.filter.gpu.adjust.GPUImageRGBFilter;
import com.picspool.lib.filter.gpu.adjust.GPUImageSaturationFilter;
import com.picspool.lib.filter.gpu.adjust.GPUImageSharpenFilter;
import com.picspool.lib.filter.gpu.adjust.GPUImageSoftLightFilter;
import com.picspool.lib.filter.gpu.adjust.GPUImageWhiteBalanceFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageAddBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageChromaKeyBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageColorBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageColorBurnBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageColorDodgeBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageDarkenBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageDifferenceBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageDissolveBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageDivideBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageExclusionBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageHardLightBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageHueBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageLightenBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageLinearBurnBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageLuminosityBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageMapBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageMapSelfBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageMultiplyBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageNormalBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageOverlayBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageSaturationBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageScreenBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageSoftLightBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageSourceOverBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageSubtractBlendFilter;
import com.picspool.lib.filter.gpu.fade.GPUFadeBeautifullyFilter;
import com.picspool.lib.filter.gpu.fade.GPUFadeCoolHazeFilter;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.gpu.father.GPUImageFilterGroup;
import com.picspool.lib.filter.gpu.film.Film18Filter;
import com.picspool.lib.filter.gpu.film.FilmBVolTemplateFilter;
import com.picspool.lib.filter.gpu.film.FilmCP12Filter;
import com.picspool.lib.filter.gpu.film.FilmCarinaFilter;
import com.picspool.lib.filter.gpu.film.FilmClassicBlueFilter;
import com.picspool.lib.filter.gpu.film.FilmCoolBreezeFilter;
import com.picspool.lib.filter.gpu.film.FilmCoolerFilter;
import com.picspool.lib.filter.gpu.film.FilmFreeSpiritFilter;
import com.picspool.lib.filter.gpu.film.FilmNightFate3Filter;
import com.picspool.lib.filter.gpu.film.FilmPaprikaFilter;
import com.picspool.lib.filter.gpu.film.FilmPremium31Filter;
import com.picspool.lib.filter.gpu.film.FilmRendezvousFilter;
import com.picspool.lib.filter.gpu.food.FoodAdjustToneCoolShadowsFilter;
import com.picspool.lib.filter.gpu.food.FoodCaliFilter;
import com.picspool.lib.filter.gpu.food.FoodIceFilter;
import com.picspool.lib.filter.gpu.newfilter.GPUImageColorBalanceFilter;
import com.picspool.lib.filter.gpu.newfilter.GPUImageGaussianBlurFilter;
import com.picspool.lib.filter.gpu.normal.GPUImage3x3ConvolutionFilter;
import com.picspool.lib.filter.gpu.normal.GPUImageABaoFilter;
import com.picspool.lib.filter.gpu.normal.GPUImageColorFilter;
import com.picspool.lib.filter.gpu.normal.GPUImageColorInvertFilter;
import com.picspool.lib.filter.gpu.normal.GPUImageEmbossFilter;
import com.picspool.lib.filter.gpu.normal.GPUImageGrayscaleFilter;
import com.picspool.lib.filter.gpu.normal.GPUImageLookupFilter;
import com.picspool.lib.filter.gpu.normal.GPUImageMonochromeFilter;
import com.picspool.lib.filter.gpu.normal.GPUImageNoFilter;
import com.picspool.lib.filter.gpu.normal.GPUImageOpacityFilter;
import com.picspool.lib.filter.gpu.normal.GPUImagePixelationFilter;
import com.picspool.lib.filter.gpu.normal.GPUImagePosterizeFilter;
import com.picspool.lib.filter.gpu.normal.GPUImageSepiaFilter;
import com.picspool.lib.filter.gpu.normal.GPUImageSobelEdgeDetection;
import com.picspool.lib.filter.gpu.normal.GPUImageToneCurveFilter;
import com.picspool.lib.filter.gpu.normal.GPUImageToneCurveMapFilter;
import com.picspool.lib.filter.gpu.retro.Retro17Filter;
import com.picspool.lib.filter.gpu.retro.RetroCVol8Filter;
import com.picspool.lib.filter.gpu.retro.RetroChestnutBrownFilter;
import com.picspool.lib.filter.gpu.retro.RetroDelicateBrownFilter;
import com.picspool.lib.filter.gpu.retro.RetroPremiumFilter;
import com.picspool.lib.filter.gpu.retro.RetroVintageFilter;
import com.picspool.lib.filter.gpu.scene.GPUImageSceneLevelControlFilter;
import com.picspool.lib.filter.gpu.scene.GPUImageSceneLowSaturationFilter;
import com.picspool.lib.filter.gpu.season.GPUSeasonAutumnGentleFilter;
import com.picspool.lib.filter.gpu.season.GPUSeasonAutumnPremiumFilter;
import com.picspool.lib.filter.gpu.season.GPUSeasonGloriousSpringBabyFilter;
import com.picspool.lib.filter.gpu.season.GPUSeasonSpringBlossomFilter;
import com.picspool.lib.filter.gpu.season.GPUSeasonSpringLightFilter;
import com.picspool.lib.filter.gpu.season.GPUSeasonSummerDayFilter;
import com.picspool.lib.filter.gpu.season.GPUSeasonSummerIndianFilter;
import com.picspool.lib.filter.gpu.season.GPUSeasonWinterIcedFilter;
import com.picspool.lib.filter.gpu.season.GPUSeasonWinterSnappyBabyFilter;
import com.picspool.lib.filter.gpu.season.GPUSeasonWinterSoftBrownFilter;
import com.picspool.lib.filter.gpu.sweet.GPUSweetPremiumFilter;
import com.picspool.lib.filter.gpu.sweet.GPUSweetRomanceFilter;
import com.picspool.lib.filter.gpu.sweet.GPUSweetRustyTintFilter;
import com.picspool.lib.filter.gpu.sweet.GPUSweetSoCoolFilter;
import com.picspool.lib.filter.gpu.tonewithblend.GPUImageToneCurveLuminosityBlendFilter;
import com.picspool.lib.filter.gpu.util.PS2GpuImageValueConversion;
import com.picspool.lib.filter.gpu.vignette.GPUImageVignetteBrightnessFilter;
import com.picspool.lib.filter.gpu.vignette.GPUImageVignetteColorFilter;
import com.picspool.lib.filter.gpu.vignette.GPUImageVignetteColorInvertFilter;
import com.picspool.lib.filter.gpu.vignette.GPUImageVignetteContrastFilter;
import com.picspool.lib.filter.gpu.vignette.GPUImageVignetteExposureFilter;
import com.picspool.lib.filter.gpu.vignette.GPUImageVignetteFilter;
import com.picspool.lib.filter.gpu.vignette.GPUImageVignetteGammaFilter;
import com.picspool.lib.filter.gpu.vignette.GPUImageVignetteGaussianBlurFilter;
import com.picspool.lib.filter.gpu.vignette.GPUImageVignetteGrayscaleFilter;
import com.picspool.lib.filter.gpu.vignette.GPUImageVignetteHueFilter;
import com.picspool.lib.filter.gpu.vignette.GPUImageVignetteMapSelfBlendFilter;
import com.picspool.lib.filter.gpu.vignette.GPUImageVignetteSharpenFilter;
import com.picspool.lib.filter.gpu.vignette.GPUImageVignetteToneCurveFilter;
import com.picspool.lib.filter.gpu.vignette.GPUImageVignetteToneCurveMapFilter;
import com.picspool.lib.sysphotoselector.DMPagerSlidingTabStrip;
import com.sky.testproject.Opcodes;

/* loaded from: classes3.dex */
public class GPUFilterFactory {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static GPUImageFilter createFilterForType(Context context, GPUFilterType gPUFilterType) {
        PointF pointF = new PointF();
        pointF.x = 0.5f;
        pointF.y = 0.5f;
        float[] fArr = {0.0f, 0.0f, 0.0f};
        float[] fArr2 = {0.0f, 0.0f, 0.0f};
        float[] fArr3 = {0.0f, 0.0f, 0.0f};
        LinkedList linkedList = new LinkedList();
        switch (C30981.$SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[gPUFilterType.ordinal()]) {
            case 1:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/test/Fotor_yj.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 2:
                return new GPUImageNoFilter();
            case 3:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a1.acv");
            case 4:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a2.acv");
            case 5:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a3.acv");
            case 6:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a4.acv");
            case 7:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a5.acv");
            case 8:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a6.acv");
            case 9:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a7.acv");
            case 10:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a8.acv");
            case 11:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a9.acv");
            case 12:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a10.acv");
            case 13:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a11.acv");
            case 14:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a12.acv");
            case 15:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a13.acv");
            case 16:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a14.acv");
            case 17:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a15.acv");
            case 18:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a16.acv");
            case 19:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a17.acv");
            case 20:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a18.acv");
            case 21:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a19.acv");
            case 22:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a20.acv");
            case 23:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a21.acv");
            case 24:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a22.acv");
            case 25:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a23.acv");
            case 26:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a24.acv");
            case 27:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a25.acv");
            case 28:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a26.acv");
            case 29:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a27.acv");
            case 30:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a28.acv");
            case 31:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a29.acv");
            case 32:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a30.acv");
            case 33:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a31.acv");
            case 34:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a32.acv");
            case 35:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a33.acv");
            case 36:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a34.acv");
            case 37:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a35.acv");
            case 38:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a36.acv");
            case 39:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a37.acv");
            case 40:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a38.acv");
            case 41:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a39.acv");
            case 42:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a40.acv");
            case 43:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a41.acv");
            case 44:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a42.acv");
            case 45:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a43.acv");
            case 46:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a44.acv");
            case 47:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a45.acv");
            case 48:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a46.acv");
            case 49:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a47.acv");
            case 50:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a48.acv");
            case 51:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/a49.acv");
            case 52:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/a50.acv"));
                break;
            case 53:
                break;
            case 54:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d2.dat");
            case 55:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d3.dat");
            case 56:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d4.dat");
            case 57:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d5.dat");
            case 58:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d6.dat");
            case 59:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d7.dat");
            case 60:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d8.dat");
            case 61:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d9.dat");
            case 62:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d10.dat");
            case 63:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d11.dat");
            case 64:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d12.dat");
            case 65:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d13.dat");
            case 66:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d14.dat");
            case 67:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d15.dat");
            case 68:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d16.dat");
            case 69:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d17.dat");
            case 70:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d18.dat");
            case 71:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d19.dat");
            case 72:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d20.dat");
            case 73:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d21.dat");
            case 74:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d22.dat");
            case 75:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d23.dat");
            case 76:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d24.dat");
            case 77:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d25.dat");
            case 78:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d26.dat");
            case 79:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d27.dat");
            case 80:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d28.dat");
            case 81:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d29.dat");
            case 82:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d30.dat");
            case 83:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d31.dat");
            case 84:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d32.dat");
            case 85:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d33.dat");
            case 86:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d34.dat");
            case 87:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d35.dat");
            case 88:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d36.dat");
            case 89:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d37.dat");
            case 90:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d38.dat");
            case 91:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d39.dat");
            case 92:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d40.dat");
            case 93:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d41.dat");
            case 94:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d42.dat");
            case 95:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d43.dat");
            case 96:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d44.dat");
            case 97:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/curves/acv/7AM.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 98:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/curves/acv/1974.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 99:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/curves/acv/Absinth.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 100:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/curves/acv/Buenos Aires.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 101:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/curves/acv/Denim.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 102:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/curves/acv/Royal Blue.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 103:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/curves/acv/Smoky.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 104:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/curves/acv/Toaster.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 105:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageScreenBlendFilter.class, "filter/bokeh/bokeh1.jpg"));
                return new GPUImageFilterGroup(linkedList);
            case 106:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageScreenBlendFilter.class, "filter/bokeh/bokeh2.jpg"));
                return new GPUImageFilterGroup(linkedList);
            case 107:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageScreenBlendFilter.class, "filter/bokeh/bokeh3.jpg"));
                return new GPUImageFilterGroup(linkedList);
            case 108:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageScreenBlendFilter.class, "filter/bokeh/bokeh4.jpg"));
                return new GPUImageFilterGroup(linkedList);
            case 109:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageScreenBlendFilter.class, "filter/bokeh/bokeh5.jpg"));
                return new GPUImageFilterGroup(linkedList);
            case 110:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageScreenBlendFilter.class, "filter/bokeh/bokeh6.jpg"));
                return new GPUImageFilterGroup(linkedList);
            case 111:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageScreenBlendFilter.class, "filter/bokeh/bokeh7.jpg"));
                return new GPUImageFilterGroup(linkedList);
            case 112:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageMultiplyBlendFilter.class, "filter/bokeh/bokeh8.jpg"));
                return new GPUImageFilterGroup(linkedList);
            case 113:
                linkedList.add(new GPUImageSaturationFilter(0.0f));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageMultiplyBlendFilter.class, "filter/art/paper.jpg"));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageScreenBlendFilter.class, "filter/art/pencil.jpg"));
                return new GPUImageFilterGroup(linkedList);
            case 114:
                linkedList.add(new GPUImageSaturationFilter(0.0f));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageMultiplyBlendFilter.class, "filter/art/pencil.jpg"));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageScreenBlendFilter.class, "filter/art/oldpaper.jpg"));
                return new GPUImageFilterGroup(linkedList);
            case 115:
                linkedList.add(new GPUImageSaturationFilter(0.0f));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageScreenBlendFilter.class, "filter/art/pencil.jpg"));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageScreenBlendFilter.class, "filter/art/colorpencil.jpg"));
                return new GPUImageFilterGroup(linkedList);
            case 116:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageScreenBlendFilter.class, "filter/art/blot.jpg"));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageMultiplyBlendFilter.class, "filter/art/kraft.jpg"));
                return new GPUImageFilterGroup(linkedList);
            case 117:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageScreenBlendFilter.class, "filter/art/spot.jpg"));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageMultiplyBlendFilter.class, "filter/art/oldpaper2.jpg"));
                return new GPUImageFilterGroup(linkedList);
            case 118:
                linkedList.add(new GPUImageSaturationFilter(0.0f));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageScreenBlendFilter.class, "filter/art/moviespot.jpg"));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageMultiplyBlendFilter.class, "filter/art/oldmovie.jpg"));
                return new GPUImageFilterGroup(linkedList);
            case 119:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageMultiplyBlendFilter.class, "filter/lens/1974.png"));
                return new GPUImageFilterGroup(linkedList);
            case 120:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageMultiplyBlendFilter.class, "filter/lens/absinth02.png"));
                return new GPUImageFilterGroup(linkedList);
            case 121:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageMultiplyBlendFilter.class, "filter/lens/buenos_aires.png"));
                return new GPUImageFilterGroup(linkedList);
            case 122:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageMultiplyBlendFilter.class, "filter/lens/denim.png"));
                return new GPUImageFilterGroup(linkedList);
            case 123:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageMultiplyBlendFilter.class, "filter/lens/denim02.png"));
                return new GPUImageFilterGroup(linkedList);
            case 124:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageMultiplyBlendFilter.class, "filter/lens/royalblue.png"));
                return new GPUImageFilterGroup(linkedList);
            case 125:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageMultiplyBlendFilter.class, "filter/lens/smoky.png"));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.IAND /* 126 */:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageMultiplyBlendFilter.class, "filter/lens/toaster.png"));
                return new GPUImageFilterGroup(linkedList);
            case 127:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/Dat/banbo.dat");
            case 128:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/Dat/better_skin.dat");
            case 129:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/Dat/deep_white.dat");
            case 130:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/Dat/HDR.dat");
            case 131:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/Dat/jingdianHDR.dat");
            case 132:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/Dat/natural_white.dat");
            case 133:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/Dat/skin_smooth.dat");
            case 134:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/Dat/sunny.dat");
            case 135:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/Dat/sweety.dat");
            case 136:
                linkedList.add(GPUImageFilterCreator.createDATCurveFilter(context, "filter/Dat/zi_ran.dat"));
                linkedList.add(new GPUImageSoftLightFilter(0.002f, -0.2f, 0.8f));
                return new GPUImageFilterGroup(linkedList);
            case 137:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/Dat/lake_path.dat");
            case 138:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/Dat/lan_diao.dat");
            case 139:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/Dat/xiao_zhen.dat");
            case 140:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/Dat/lomo_path.dat");
            case 141:
                GPUImageVignetteToneCurveFilter gPUImageVignetteToneCurveFilter = new GPUImageVignetteToneCurveFilter(pointF, 0.2f, 0.75f);
                try {
                    gPUImageVignetteToneCurveFilter.setFromDatCurveFileInputStream(context.getAssets().open("filter/Dat/lomo_path.dat"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                gPUImageVignetteToneCurveFilter.setFileType("dat");
                gPUImageVignetteToneCurveFilter.setInvert(true);
                linkedList.add(gPUImageVignetteToneCurveFilter);
                linkedList.add(new GPUImageVignetteBrightnessFilter(pointF, 0.0f, -0.2f, 0.3f, 0.75f));
                GPUImageVignetteToneCurveFilter gPUImageVignetteToneCurveFilter2 = new GPUImageVignetteToneCurveFilter(pointF, 0.3f, 0.75f);
                try {
                    gPUImageVignetteToneCurveFilter2.setFromDatCurveFileInputStream(context.getAssets().open("filter/Dat/lomo.dat"));
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                gPUImageVignetteToneCurveFilter2.setFileType("dat");
                linkedList.add(gPUImageVignetteToneCurveFilter2);
                return new GPUImageFilterGroup(linkedList);
            case 142:
                linkedList.add(GPUImageFilterCreator.createDATCurveFilter(context, "filter/Dat/hei_bai.dat"));
                linkedList.add(new GPUImageSaturationFilter(0.0f));
                return new GPUImageFilterGroup(linkedList);
            case 143:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/Dat/wei_mei.dat");
            case 144:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/Dat/shen_lan.dat");
            case 145:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/Dat/qing_xin.dat");
            case 146:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/Dat/fen_nen.dat");
            case 147:
                return GPUImageFilterCreator.createDATCurveFilter(context, "filter/Dat/qiu_se.dat");
            case Opcodes.LCMP /* 148 */:
                linkedList.add(new GPUImageContrastFilter(0.85f));
                linkedList.add(new GPUImageSaturationFilter(0.75f));
                linkedList.add(new GPUImageVignetteBrightnessFilter(pointF, -0.1f, -0.3f, 0.3f, 0.75f));
                linkedList.add(GPUImageFilterCreator.createDATCurveFilter(context, "filter/Dat/hui_yi.dat"));
                linkedList.add(GPUImageFilterCreator.createDATCurveFilter(context, "filter/Dat/hui_yi.dat"));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.FCMPL /* 149 */:
                return new GPUImageABaoFilter();
            case DMPagerSlidingTabStrip.DEF_VALUE_TAB_TEXT_ALPHA /* 150 */:
                return GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Dat/abao.png", GPUImageLookupFilter.class);
            case Opcodes.DCMPL /* 151 */:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Dat/rixi.png", GPUImageLookupFilter.class));
                linkedList.add(new GPUImageBrightnessFilter(-0.1f));
                return new GPUImageFilterGroup(linkedList);
            case 152:
                pointF.x = 0.55f;
                pointF.y = 0.45f;
                linkedList.add(new GPUImageContrastFilter(1.1f));
                linkedList.add(GPUImageFilterCreator.createFilterForVignetteTwoInputFilter(context, "filter/Classic/Gloss/map.png", pointF, 0.2f, 0.75f, GPUImageVignetteToneCurveMapFilter.class));
                linkedList.add(new GPUImageVignetteBrightnessFilter(pointF, 0.0f, -0.2f, 0.3f, 0.75f));
                linkedList.add(GPUImageFilterCreator.createFilterForVignetteTwoInputFilter(context, "filter/Classic/Gloss/overlay_map.png", pointF, 0.3f, 0.75f, GPUImageVignetteMapSelfBlendFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.IFEQ /* 153 */:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Classic/Versa/colorGradient.png", GPUImageToneCurveMapFilter.class));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Classic/Versa/colorOverlay.png", GPUImageMapSelfBlendFilter.class));
                pointF.x = 0.3f;
                pointF.y = 0.48f;
                linkedList.add(new GPUImageVignetteBrightnessFilter(pointF, 0.08f, 0.0f, 0.1f, 0.25f));
                PointF pointF2 = new PointF();
                pointF2.x = 0.6f;
                pointF2.y = 0.55f;
                linkedList.add(new GPUImageVignetteBrightnessFilter(pointF2, 0.05f, 0.0f, 0.1f, 0.2f));
                PointF pointF3 = new PointF();
                pointF3.x = 0.53f;
                pointF3.y = 0.99f;
                linkedList.add(new GPUImageVignetteBrightnessFilter(pointF3, 0.1f, 0.0f, 0.1f, 0.25f));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.IFNE /* 154 */:
                pointF.x = 0.55f;
                pointF.y = 0.45f;
                linkedList.add(new GPUImageContrastFilter(1.1f));
                linkedList.add(GPUImageFilterCreator.createFilterForVignetteTwoInputFilter(context, "filter/Classic/Listless/map.png", pointF, 0.2f, 0.75f, GPUImageVignetteToneCurveMapFilter.class));
                linkedList.add(new GPUImageVignetteBrightnessFilter(pointF, 0.05f, -0.27f, 0.3f, 0.75f));
                return new GPUImageFilterGroup(linkedList);
            case 155:
                pointF.x = 0.6f;
                pointF.y = 0.65f;
                linkedList.add(new GPUImageContrastFilter(1.1f));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Classic/Icy/map.png", GPUImageToneCurveMapFilter.class));
                linkedList.add(new GPUImageVignetteBrightnessFilter(pointF, 0.09f, -0.2f, 0.3f, 0.8f));
                return new GPUImageFilterGroup(linkedList);
            case 156:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Classic/Fade/map.png", GPUImageToneCurveMapFilter.class));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Classic/Fade/gradient_map.png", GPUImageMapSelfBlendFilter.class));
                linkedList.add(new GPUImageBrightnessFilter(0.02f));
                return new GPUImageFilterGroup(linkedList);
            case 157:
                linkedList.add(new GPUImageVignetteBrightnessFilter(pointF, 0.1f, -0.25f, 0.3f, 0.75f));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Film/Kuc100/map.png", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.IFLE /* 158 */:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Classic/Mild/soft_light.png", GPUImageMapSelfBlendFilter.class));
                linkedList.add(new GPUImageVignetteBrightnessFilter(pointF, 0.1f, -0.25f, 0.3f, 0.75f));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Classic/Mild/map.png", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.IF_ICMPEQ /* 159 */:
                pointF.x = 0.55f;
                pointF.y = 0.45f;
                linkedList.add(new GPUImageContrastFilter(1.1f));
                linkedList.add(new GPUImageVignetteBrightnessFilter(pointF, 0.18f, -0.2f, 0.2f, 0.75f));
                linkedList.add(new GPUImageSaturationFilter(0.0f));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/BW/Selium/willowMap.png", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.IF_ICMPNE /* 160 */:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Film/Agx100/map.png", GPUImageToneCurveMapFilter.class));
                linkedList.add(new GPUImageVignetteBrightnessFilter(pointF, 0.06f, -0.2f, 0.2f, 0.75f));
                linkedList.add(new GPUImageVignetteSharpenFilter(pointF, 0.3f, 0.0f, 0.3f, 0.75f));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Film/Agx100/vignette_map.png", GPUImageMapSelfBlendFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.IF_ICMPLT /* 161 */:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Vintage/Weson/curves_map.png", GPUImageToneCurveMapFilter.class));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Vintage/Weson/overlay_map.png", GPUImageToneCurveMapFilter.class));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Vintage/Weson/blowout_map.png", GPUImageToneCurveMapFilter.class));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Vintage/Weson/map_2d.png", GPUImageMapSelfBlendFilter.class));
                linkedList.add(new GPUImageVignetteBrightnessFilter(pointF, 0.12f, -0.3f, 0.2f, 0.75f));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.IF_ICMPGE /* 162 */:
                linkedList.add(new GPUImageVignetteBrightnessFilter(pointF, 0.12f, -0.2f, 0.2f, 0.75f));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Vintage/Lethe/map.png", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.IF_ICMPGT /* 163 */:
                linkedList.add(GPUImageFilterCreator.createFilterForVignetteTwoInputFilter(context, "filter/Classic/Vigour/map.png", pointF, 0.3f, 0.75f, GPUImageVignetteToneCurveMapFilter.class));
                linkedList.add(new GPUImageVignetteBrightnessFilter(pointF, 0.16f, -0.2f, 0.2f, 0.75f));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Classic/Vigour/color_shift_map.png", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 164:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Classic/Drama/luma_map.png", GPUImageToneCurveMapFilter.class));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Classic/Drama/blowout_map.png", GPUImageToneCurveMapFilter.class));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Classic/Drama/map.png", GPUImageToneCurveMapFilter.class));
                linkedList.add(new GPUImageSaturationFilter(0.6f));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.IF_ACMPEQ /* 165 */:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/BW/Kopan/map.png", GPUImageToneCurveMapFilter.class));
                linkedList.add(new GPUImageSaturationFilter(0.0f));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.IF_ACMPNE /* 166 */:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Classic/Pale/map.png", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.GOTO /* 167 */:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Classic/Passion/map.png", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 168:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Classic/Pizazz/map.png", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.RET /* 169 */:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Era/1977/map.png", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 170:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Classic/Alone/map.png", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 171:
                linkedList.add(new GPUImageSaturationFilter(0.7f));
                linkedList.add(new GPUImageContrastFilter(1.1f));
                linkedList.add(new GPUImageVignetteBrightnessFilter(pointF, 0.18f, -0.2f, 0.2f, 0.75f));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Era/1970/map.png", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 172:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/Era/1974/1974.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 173:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Era/1970/map.png", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 174:
                linkedList.add(new GPUImageSaturationFilter(0.0f));
                linkedList.add(new GPUImageExposureFilter(-0.5f));
                pointF.x = 0.35f;
                pointF.y = 0.35f;
                linkedList.add(new GPUImageVignetteExposureFilter(pointF, 0.2f, -0.5f, 0.4f, 0.85f));
                linkedList.add(new GPUImageVignetteContrastFilter(pointF, 0.7f, 1.2f, 0.2f, 0.75f));
                linkedList.add(new GPUImageVignetteSharpenFilter(pointF, 0.18f, -0.45f, 0.2f, 0.75f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/BW/BWRetro/BWRetro.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 175:
                pointF.x = 0.35f;
                pointF.y = 0.35f;
                linkedList.add(new GPUImageContrastFilter(1.1f));
                linkedList.add(new GPUImageVignetteBrightnessFilter(pointF, 0.18f, -0.2f, 0.2f, 0.75f));
                linkedList.add(new GPUImageSaturationFilter(0.0f));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/BW/ashby/tonemap.png", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.ARETURN /* 176 */:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/BW/brooklyn/curves.png", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.RETURN /* 177 */:
                linkedList.add(new GPUImageSaturationFilter(0.0f));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/BW/charmes/map.png", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.GETSTATIC /* 178 */:
                pointF.x = 0.3f;
                pointF.y = 0.18f;
                linkedList.add(new GPUImageVignetteBrightnessFilter(pointF, 0.08f, 0.0f, 0.1f, 0.25f));
                PointF pointF4 = new PointF();
                pointF4.x = 0.45f;
                pointF4.y = 0.55f;
                linkedList.add(new GPUImageVignetteBrightnessFilter(pointF4, 0.05f, 0.0f, 0.1f, 0.2f));
                PointF pointF5 = new PointF();
                pointF5.x = 0.83f;
                pointF5.y = 0.99f;
                linkedList.add(new GPUImageVignetteBrightnessFilter(pointF5, 0.1f, 0.0f, 0.1f, 0.25f));
                linkedList.add(new GPUImageSaturationFilter(0.0f));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/BW/clarendon/Glacial1.png", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 179:
                linkedList.add(new GPUImageSaturationFilter(0.0f));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/BW/dogpatch/curves1.png", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.GETFIELD /* 180 */:
                linkedList.add(new GPUImageSaturationFilter(0.0f));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/BW/gingham/curves1.png", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.PUTFIELD /* 181 */:
                linkedList.add(new GPUImageSaturationFilter(0.0f));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/BW/ginza/curves1.png", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.INVOKEVIRTUAL /* 182 */:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/BW/helena/epic_1.png", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.INVOKESPECIAL /* 183 */:
                linkedList.add(new GPUImageSaturationFilter(0.0f));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/BW/maven/Lansdowne1.png", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.INVOKESTATIC /* 184 */:
                linkedList.add(new GPUImageSaturationFilter(0.0f));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/BW/moon/curves1.png", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.INVOKEINTERFACE /* 185 */:
                linkedList.add(new GPUImageSaturationFilter(0.0f));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/BW/skyline/curves.png", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 186:
                linkedList.add(new GPUImageSaturationFilter(0.0f));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/BW/stinson/curves.png", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.NEW /* 187 */:
                linkedList.add(new GPUImageSaturationFilter(0.0f));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/BW/vesper/map.png", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.NEWARRAY /* 188 */:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Vintage/AS/A4.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 189:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Vintage/AS/A5.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 190:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Vintage/AS/A6.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 191:
                linkedList.add(new GPUImageSaturationFilter(0.0f));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Vintage/AS/B1.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.CHECKCAST /* 192 */:
                linkedList.add(new GPUImageSaturationFilter(0.0f));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Vintage/AS/B5.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.INSTANCEOF /* 193 */:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Vintage/AS/C1.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 194:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Vintage/AS/F2.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 195:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Vintage/AS/G3.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 196:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Vintage/AS/HB1.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 197:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Vintage/AS/HB2.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.IFNULL /* 198 */:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Vintage/AS/M3.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case Opcodes.IFNONNULL /* 199 */:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Vintage/AS/M5.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 200:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Vintage/AS/P5.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 201:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Vintage/AS/SE1.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 202:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Vintage/AS/SE2.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 203:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Vintage/AS/SE3.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 204:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Vintage/AS/T1.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 205:
                linkedList.add(new GPUImageSaturationFilter(0.0f));
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Vintage/AS/X1.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 206:
                return new GPUImageSoftLightFilter();
            case 207:
                return new GPUImageGaussianBlurSimpleFilter();
            case 208:
                return new GPUImageContrastFilter();
            case 209:
                return new GPUImageGammaFilter();
            case 210:
                return new GPUImageBrightnessFilter();
            case 211:
                return new GPUImageSharpenFilter();
            case 212:
                return new GPUImageSaturationFilter();
            case 213:
                return new GPUImageExposureFilter();
            case 214:
                return new GPUImageWhiteBalanceFilter();
            case 215:
                return new GPUImageVignetteColorFilter(new float[]{0.0f, 0.0f, 0.0f}, pointF, 0.3f, 0.75f);
            case 216:
                return GPUImageFilterCreator.createBlendFilter(context, GPUImageToneCurveMapFilter.class);
            case 217:
                return new GPUImageMapSelfBlendFilter();
            case 218:
                return new GPUImageMapBlendFilter();
            case 219:
                return new GPUImageColorInvertFilter();
            case 220:
                return new GPUImagePixelationFilter();
            case 221:
                return new GPUImageHueFilter();
            case 222:
                return new GPUImageSepiaFilter();
            case 223:
                return new GPUImageSobelEdgeDetection();
            case 224:
                GPUImage3x3ConvolutionFilter gPUImage3x3ConvolutionFilter = new GPUImage3x3ConvolutionFilter();
                gPUImage3x3ConvolutionFilter.setConvolutionKernel(new float[]{-1.0f, 0.0f, 1.0f, -2.0f, 0.0f, 2.0f, -1.0f, 0.0f, 1.0f});
                return gPUImage3x3ConvolutionFilter;
            case 225:
                return new GPUImageEmbossFilter();
            case 226:
                return new GPUImagePosterizeFilter();
            case 227:
                LinkedList linkedList2 = new LinkedList();
                linkedList2.add(new GPUImageGaussianBlurSimpleFilter(0.001953125f));
                linkedList2.add(new GPUImageGaussianBlurSimpleFilter(0.001953125f));
                linkedList2.add(new GPUImageGaussianBlurSimpleFilter(0.001953125f));
                linkedList2.add(new GPUImageGaussianBlurSimpleFilter(0.001953125f));
                linkedList2.add(new GPUImageGaussianBlurSimpleFilter(0.001953125f));
                return new GPUImageFilterGroup(linkedList2);
            case 228:
                return new GPUImageHighlightShadowFilter(0.0f, 1.0f);
            case 229:
                return new GPUImageMonochromeFilter(1.0f, new float[]{0.6f, 0.45f, 0.3f, 1.0f});
            case 230:
                return new GPUImageOpacityFilter(1.0f);
            case 231:
                return new GPUImageRGBFilter(1.0f, 1.0f, 1.0f);
            case 232:
                return new GPUImageToneCurveFilter();
            case 233:
                return new GPUImageVignetteBrightnessFilter(pointF, 0.12f, -0.3f, 0.2f, 0.75f);
            case 234:
                return new GPUImageVignetteColorInvertFilter(pointF, 0.0f, 1.0f, 0.2f, 0.75f);
            case 235:
                return new GPUImageVignetteContrastFilter(pointF, 0.9f, 1.5f, 0.2f, 0.75f);
            case 236:
                return new GPUImageVignetteExposureFilter(pointF, 0.2f, -0.5f, 0.2f, 0.75f);
            case 237:
                return new GPUImageVignetteGammaFilter(pointF, 0.9f, 1.5f, 0.2f, 0.75f);
            case 238:
                return new GPUImageVignetteGaussianBlurFilter(pointF, 0.0f, 0.0026041667f, 0.2f, 0.75f);
            case 239:
                return new GPUImageVignetteGrayscaleFilter(pointF, 1.2f, 0.0f, 0.2f, 0.75f);
            case 240:
                return new GPUImageVignetteHueFilter(pointF, 0.0f, 60.0f, 0.3f, 0.75f);
            case 241:
                return GPUImageFilterCreator.createFilterForVignetteTwoInputFilter(context, "", pointF, 0.2f, 0.75f, GPUImageVignetteMapSelfBlendFilter.class);
            case 242:
                return new GPUImageVignetteSharpenFilter(pointF, 0.3f, -0.7f, 0.2f, 0.75f);
            case 243:
                return GPUImageFilterCreator.createFilterForVignetteTwoInputFilter(context, "", pointF, 0.2f, 0.75f, GPUImageVignetteToneCurveMapFilter.class);
            case 244:
                return GPUImageFilterCreator.createBlendFilter(context, GPUImageDifferenceBlendFilter.class);
            case 245:
                return GPUImageFilterCreator.createBlendFilter(context, GPUImageSourceOverBlendFilter.class);
            case 246:
                return GPUImageFilterCreator.createBlendFilter(context, GPUImageColorBurnBlendFilter.class);
            case 247:
                return GPUImageFilterCreator.createBlendFilter(context, GPUImageColorDodgeBlendFilter.class);
            case 248:
                return GPUImageFilterCreator.createBlendFilter(context, GPUImageDarkenBlendFilter.class);
            case 249:
                return GPUImageFilterCreator.createBlendFilter(context, GPUImageDissolveBlendFilter.class);
            case ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION /* 250 */:
                return GPUImageFilterCreator.createBlendFilter(context, GPUImageExclusionBlendFilter.class);
            case 251:
                return GPUImageFilterCreator.createBlendFilter(context, GPUImageHardLightBlendFilter.class);
            case 252:
                return GPUImageFilterCreator.createBlendFilter(context, GPUImageLightenBlendFilter.class);
            case 253:
                return GPUImageFilterCreator.createBlendFilter(context, GPUImageAddBlendFilter.class);
            case 254:
                return GPUImageFilterCreator.createBlendFilter(context, GPUImageDivideBlendFilter.class);
            case 255:
                return GPUImageFilterCreator.createBlendFilter(context, GPUImageMultiplyBlendFilter.class);
            case 256:
                return GPUImageFilterCreator.createBlendFilter(context, GPUImageOverlayBlendFilter.class);
            case 257:
                return GPUImageFilterCreator.createBlendFilter(context, GPUImageScreenBlendFilter.class);
            case 258:
                return GPUImageFilterCreator.createBlendFilter(context, GPUImageColorBlendFilter.class);
            case 259:
                return GPUImageFilterCreator.createBlendFilter(context, GPUImageHueBlendFilter.class);
            case 260:
                return GPUImageFilterCreator.createBlendFilter(context, GPUImageSaturationBlendFilter.class);
            case 261:
                return GPUImageFilterCreator.createBlendFilter(context, GPUImageLuminosityBlendFilter.class);
            case 262:
                return GPUImageFilterCreator.createBlendFilter(context, GPUImageLinearBurnBlendFilter.class);
            case 263:
                return GPUImageFilterCreator.createBlendFilter(context, GPUImageSoftLightBlendFilter.class);
            case 264:
                return GPUImageFilterCreator.createBlendFilter(context, GPUImageSubtractBlendFilter.class);
            case 265:
                return GPUImageFilterCreator.createBlendFilter(context, GPUImageChromaKeyBlendFilter.class);
            case 266:
                return GPUImageFilterCreator.createBlendFilter(context, GPUImageNormalBlendFilter.class);
            case 267:
                linkedList.add(new GPUImageBrightnessFilter(0.05f));
                linkedList.add(new GPUImageSaturationFilter(1.1582757f));
                linkedList.add(new GPUImageContrastFilter(1.4586205f));
                linkedList.add(new GPUImageSharpenFilter(0.4229886f));
                linkedList.add(new GPUImageWhiteBalanceFilter(5270.1147f));
                linkedList.add(new GPUImageHighlightShadowFilter(0.37659633f, 0.6542146f));
                linkedList.add(new GPUImageVignetteFilter(1.0161124f));
                return new GPUImageFilterGroup(linkedList);
            case 268:
                linkedList.add(new GPUImageBrightnessFilter(0.059866f));
                linkedList.add(new GPUImageSaturationFilter(1.1310344f));
                linkedList.add(new GPUImageContrastFilter(1.1677182f));
                linkedList.add(new GPUImageSharpenFilter(0.5f));
                linkedList.add(new GPUImageWhiteBalanceFilter(6504.31f));
                linkedList.add(new GPUImageHighlightShadowFilter(0.2095685f, 0.8061302f));
                return new GPUImageFilterGroup(linkedList);
            case 269:
                linkedList.add(new GPUImageBrightnessFilter(0.1591954f));
                linkedList.add(new GPUImageSaturationFilter(1.379885f));
                linkedList.add(new GPUImageContrastFilter(1.265517f));
                linkedList.add(new GPUImageSharpenFilter(0.5287356f));
                linkedList.add(new GPUImageWhiteBalanceFilter(5133.6206f));
                linkedList.add(new GPUImageHighlightShadowFilter(0.26747108f, 0.643678f));
                return new GPUImageFilterGroup(linkedList);
            case 270:
                linkedList.add(new GPUImageBrightnessFilter(0.0655174f));
                linkedList.add(new GPUImageSaturationFilter(1.7224138f));
                linkedList.add(new GPUImageContrastFilter(1.2310346f));
                linkedList.add(new GPUImageSharpenFilter(0.2804598f));
                linkedList.add(new GPUImageWhiteBalanceFilter(6778.736f));
                return new GPUImageFilterGroup(linkedList);
            case 271:
                linkedList.add(new GPUImageBrightnessFilter(0.020115f));
                linkedList.add(new GPUImageSaturationFilter(1.2752872f));
                linkedList.add(new GPUImageContrastFilter(1.1448278f));
                linkedList.add(new GPUImageSharpenFilter(0.5632186f));
                linkedList.add(new GPUImageWhiteBalanceFilter(5321.839f));
                return new GPUImageFilterGroup(linkedList);
            case 272:
                linkedList.add(new GPUImageBrightnessFilter(0.066f));
                linkedList.add(new GPUImageSaturationFilter(1.411f));
                linkedList.add(new GPUImageContrastFilter(1.231f));
                linkedList.add(new GPUImageSharpenFilter(0.28f));
                linkedList.add(new GPUImageWhiteBalanceFilter(4500.0f));
                return new GPUImageFilterGroup(linkedList);
            case CSXlbStickerBarView.STICKER_REQUESTCODE /* 273 */:
                linkedList.add(new GPUImageBrightnessFilter(0.06752875f));
                linkedList.add(new GPUImageSaturationFilter(1.547414f));
                linkedList.add(new GPUImageContrastFilter(1.2873555f));
                linkedList.add(new GPUImageSharpenFilter(0.48850575f));
                linkedList.add(new GPUImageWhiteBalanceFilter(5639.368f));
                linkedList.add(new GPUImageVignetteFilter(0.9181035f));
                linkedList.add(new GPUImageRGBFilter(1.0948275f, 1.1954023f, 0.98563224f));
                return new GPUImageFilterGroup(linkedList);
            case 274:
                linkedList.add(new GPUImageBrightnessFilter(0.0890805f));
                linkedList.add(new GPUImageSaturationFilter(1.1163793f));
                linkedList.add(new GPUImageContrastFilter(1.2270114f));
                linkedList.add(new GPUImageSharpenFilter(0.534483f));
                linkedList.add(new GPUImageWhiteBalanceFilter(7467.6724f));
                linkedList.add(new GPUImageVignetteFilter(1.3275863f));
                linkedList.add(new GPUImageRGBFilter(1.1666665f, 1.0790232f, 0.7557467f));
                return new GPUImageFilterGroup(linkedList);
            case 275:
                linkedList.add(new GPUImageBrightnessFilter(0.10201175f));
                linkedList.add(new GPUImageSaturationFilter(1.2844827f));
                linkedList.add(new GPUImageContrastFilter(1.2356323f));
                linkedList.add(new GPUImageSharpenFilter(0.5114945f));
                linkedList.add(new GPUImageWhiteBalanceFilter(6734.9136f));
                linkedList.add(new GPUImageVignetteFilter(1.3265085f));
                linkedList.add(new GPUImageRGBFilter(1.2715517f, 1.0488505f, 1.0272988f));
                return new GPUImageFilterGroup(linkedList);
            case 276:
                linkedList.add(new GPUImageBrightnessFilter(0.0416665f));
                linkedList.add(new GPUImageSaturationFilter(1.086207f));
                linkedList.add(new GPUImageContrastFilter(0.988506f));
                linkedList.add(new GPUImageSharpenFilter(0.37931025f));
                linkedList.add(new GPUImageWhiteBalanceFilter(4400.1436f));
                linkedList.add(new GPUImageVignetteFilter(1.3394397f));
                linkedList.add(new GPUImageRGBFilter(1.0114943f, 1.0100574f, 1.0847702f));
                return new GPUImageFilterGroup(linkedList);
            case 277:
                linkedList.add(new GPUImageBrightnessFilter(0.068966f));
                linkedList.add(new GPUImageSaturationFilter(0.810345f));
                linkedList.add(new GPUImageContrastFilter(1.16092f));
                linkedList.add(new GPUImageSharpenFilter(0.344828f));
                linkedList.add(new GPUImageWhiteBalanceFilter(6522.9883f));
                linkedList.add(new GPUImageVignetteFilter(1.112069f));
                linkedList.add(new GPUImageRGBFilter(1.316092f, 1.086207f, 0.867816f));
                return new GPUImageFilterGroup(linkedList);
            case 278:
                linkedList.add(new GPUImageBrightnessFilter(0.074713f));
                linkedList.add(new GPUImageSaturationFilter(0.856322f));
                linkedList.add(new GPUImageContrastFilter(1.218391f));
                linkedList.add(new GPUImageSharpenFilter(0.45977f));
                linkedList.add(new GPUImageWhiteBalanceFilter(5316.092f));
                linkedList.add(new GPUImageVignetteFilter(0.866379f));
                linkedList.add(new GPUImageRGBFilter(1.517241f, 0.971264f, 0.977012f));
                return new GPUImageFilterGroup(linkedList);
            case 279:
                linkedList.add(new GPUImageBrightnessFilter(0.074713f));
                linkedList.add(new GPUImageSaturationFilter(0.856322f));
                linkedList.add(new GPUImageContrastFilter(1.218391f));
                linkedList.add(new GPUImageSharpenFilter(0.45977f));
                linkedList.add(new GPUImageWhiteBalanceFilter(7025.8623f));
                linkedList.add(new GPUImageVignetteFilter(0.866379f));
                linkedList.add(new GPUImageRGBFilter(1.431034f, 1.143678f, 0.735632f));
                return new GPUImageFilterGroup(linkedList);
            case 280:
                linkedList.add(new GPUImageBrightnessFilter(0.074713f));
                linkedList.add(new GPUImageSaturationFilter(0.856322f));
                linkedList.add(new GPUImageContrastFilter(1.218391f));
                linkedList.add(new GPUImageSharpenFilter(0.45977f));
                linkedList.add(new GPUImageWhiteBalanceFilter(4511.494f));
                linkedList.add(new GPUImageVignetteFilter(0.866379f));
                linkedList.add(new GPUImageRGBFilter(1.183908f, 1.114943f, 1.649425f));
                return new GPUImageFilterGroup(linkedList);
            case 281:
                linkedList.add(new GPUImageBrightnessFilter(-0.034483f));
                linkedList.add(new GPUImageSaturationFilter(1.04023f));
                linkedList.add(new GPUImageContrastFilter(0.988506f));
                linkedList.add(new GPUImageSharpenFilter(0.275862f));
                linkedList.add(new GPUImageWhiteBalanceFilter(4267.241f));
                linkedList.add(new GPUImageVignetteFilter(1.12069f));
                linkedList.add(new GPUImageRGBFilter(1.149425f, 1.04023f, 0.718391f));
                return new GPUImageFilterGroup(linkedList);
            case 282:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/curves/acv/filter_A4.acv"));
                linkedList.add(new GPUImageContrastFilter(PS2GpuImageValueConversion.getContrastValue(30.0f)));
                linkedList.add(new GPUImageSaturationFilter(PS2GpuImageValueConversion.getSaturaValue(-23.0f)));
                linkedList.add(new GPUImageHueFilter(3.0f));
                return new GPUImageFilterGroup(linkedList);
            case 283:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/curves/acv/filter_A5.acv"));
                linkedList.add(new GPUImageSaturationFilter(PS2GpuImageValueConversion.getSaturaValue(-30.0f)));
                linkedList.add(new GPUImageHueFilter(-7.0f));
                linkedList.add(new GPUImageContrastFilter(PS2GpuImageValueConversion.getContrastValue(100.0f)));
                return new GPUImageFilterGroup(linkedList);
            case 284:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/curves/acv/filter_A6.acv"));
                linkedList.add(new GPUImageContrastFilter(PS2GpuImageValueConversion.getContrastValue(58.0f)));
                linkedList.add(new GPUImageSaturationFilter(PS2GpuImageValueConversion.getSaturaValue(-12.0f)));
                return new GPUImageFilterGroup(linkedList);
            case 285:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/curves/acv/filter_ADEN.acv"));
                linkedList.add(new GPUImageSaturationFilter(PS2GpuImageValueConversion.getSaturaValue(-52.0f)));
                linkedList.add(new GPUImageContrastFilter(PS2GpuImageValueConversion.getContrastValue(-25.0f)));
                linkedList.add(new GPUImageHueFilter(-5.0f));
                return new GPUImageFilterGroup(linkedList);
            case 286:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/curves/acv/filter_B1.acv"));
                linkedList.add(new GPUImageSaturationFilter(PS2GpuImageValueConversion.getSaturaValue(-100.0f)));
                return new GPUImageFilterGroup(linkedList);
            case 287:
                linkedList.add(new GPUImageSaturationFilter(PS2GpuImageValueConversion.getSaturaValue(-100.0f)));
                linkedList.add(new GPUImageContrastFilter(PS2GpuImageValueConversion.getContrastValue(100.0f)));
                return new GPUImageFilterGroup(linkedList);
            case 288:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/curves/acv/filter_CREMA.acv"));
                linkedList.add(new GPUImageSaturationFilter(PS2GpuImageValueConversion.getSaturaValue(-33.0f)));
                linkedList.add(new GPUImageContrastFilter(PS2GpuImageValueConversion.getContrastValue(-50.0f)));
                linkedList.add(new GPUImageBrightnessFilter(PS2GpuImageValueConversion.getHighlightsValue(5.0f)));
                return new GPUImageFilterGroup(linkedList);
            case 289:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/curves/acv/filter_HB1.acv"));
                linkedList.add(new GPUImageContrastFilter(PS2GpuImageValueConversion.getContrastValue(100.0f)));
                linkedList.add(new GPUImageSaturationFilter(PS2GpuImageValueConversion.getSaturaValue(-40.0f)));
                linkedList.add(new GPUImageHueFilter(-7.0f));
                return new GPUImageFilterGroup(linkedList);
            case 290:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/curves/acv/LUDWING.acv"));
                linkedList.add(new GPUImageContrastFilter(PS2GpuImageValueConversion.getContrastValue(64.0f)));
                linkedList.add(new GPUImageSaturationFilter(PS2GpuImageValueConversion.getSaturaValue(-20.0f)));
                linkedList.add(new GPUImageHueFilter(8.0f));
                return new GPUImageFilterGroup(linkedList);
            case 291:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/curves/acv/filter_M5.acv"));
                linkedList.add(new GPUImageSaturationFilter(PS2GpuImageValueConversion.getSaturaValue(-20.0f)));
                linkedList.add(new GPUImageContrastFilter(PS2GpuImageValueConversion.getContrastValue(50.0f)));
                linkedList.add(new GPUImageHighlightShadowFilter(PS2GpuImageValueConversion.getHighlightsValue(5.0f)));
                return new GPUImageFilterGroup(linkedList);
            case 292:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/curves/acv/filter_SLUMBER.acv"));
                linkedList.add(new GPUImageSaturationFilter(PS2GpuImageValueConversion.getSaturaValue(-40.0f)));
                linkedList.add(new GPUImageContrastFilter(PS2GpuImageValueConversion.getContrastValue(-7.0f)));
                linkedList.add(new GPUImageHueFilter(5.0f));
                linkedList.add(new GPUImageHighlightShadowFilter(PS2GpuImageValueConversion.getHighlightsValue(7.0f)));
                return new GPUImageFilterGroup(linkedList);
            case 293:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/curves/acv/filter_T1.acv"));
                linkedList.add(new GPUImageSaturationFilter(PS2GpuImageValueConversion.getSaturaValue(-4.0f)));
                linkedList.add(new GPUImageContrastFilter(PS2GpuImageValueConversion.getContrastValue(-50.0f)));
                linkedList.add(new GPUImageHueFilter(-1.0f));
                GPUImageColorFilter gPUImageColorFilter = new GPUImageColorFilter(-7829368);
                gPUImageColorFilter.setMix(0.45f);
                linkedList.add(gPUImageColorFilter);
                return new GPUImageFilterGroup(linkedList);
            case 294:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/curves/acv/filter_X1.acv"));
                linkedList.add(new GPUImageSaturationFilter(PS2GpuImageValueConversion.getSaturaValue(-100.0f)));
                linkedList.add(new GPUImageContrastFilter(PS2GpuImageValueConversion.getContrastValue(26.0f)));
                return new GPUImageFilterGroup(linkedList);
            case 295:
                GPUImageSceneLevelControlFilter gPUImageSceneLevelControlFilter = new GPUImageSceneLevelControlFilter();
                gPUImageSceneLevelControlFilter.setSceneParameter(0.0f, 0.81f, 0.7058824f, 0.0f, 1.0f);
                linkedList.add(gPUImageSceneLevelControlFilter);
                return new GPUImageFilterGroup(linkedList);
            case 296:
                GPUImageSceneLevelControlFilter gPUImageSceneLevelControlFilter2 = new GPUImageSceneLevelControlFilter();
                gPUImageSceneLevelControlFilter2.setSceneParameter(0.05882353f, 1.8f, 0.75686276f, 0.0f, 1.0f);
                linkedList.add(gPUImageSceneLevelControlFilter2);
                return new GPUImageFilterGroup(linkedList);
            case 297:
                GPUImageSceneLevelControlFilter gPUImageSceneLevelControlFilter3 = new GPUImageSceneLevelControlFilter();
                gPUImageSceneLevelControlFilter3.setSceneParameter(0.0f, 0.75f, 1.0f, 0.0f, 1.0f);
                linkedList.add(gPUImageSceneLevelControlFilter3);
                return new GPUImageFilterGroup(linkedList);
            case 298:
                GPUImageSceneLevelControlFilter gPUImageSceneLevelControlFilter4 = new GPUImageSceneLevelControlFilter();
                gPUImageSceneLevelControlFilter4.setSceneParameter(0.11764706f, 1.2f, 0.9098039f, 0.0f, 1.0f);
                linkedList.add(gPUImageSceneLevelControlFilter4);
                return new GPUImageFilterGroup(linkedList);
            case 299:
                GPUImageSceneLevelControlFilter gPUImageSceneLevelControlFilter5 = new GPUImageSceneLevelControlFilter();
                gPUImageSceneLevelControlFilter5.setSceneParameter(0.0f, 1.5f, 0.7921569f, 0.0f, 1.0f);
                linkedList.add(gPUImageSceneLevelControlFilter5);
                return new GPUImageFilterGroup(linkedList);
            case 300:
                GPUImageSceneLevelControlFilter gPUImageSceneLevelControlFilter6 = new GPUImageSceneLevelControlFilter();
                gPUImageSceneLevelControlFilter6.setSceneParameter(0.05490196f, 0.64f, 1.0f, 0.0f, 1.0f);
                linkedList.add(gPUImageSceneLevelControlFilter6);
                return new GPUImageFilterGroup(linkedList);
            case 301:
                linkedList.add(new GPUImageSceneLowSaturationFilter());
                return new GPUImageFilterGroup(linkedList);
            case 302:
                GPUImageSceneLevelControlFilter gPUImageSceneLevelControlFilter7 = new GPUImageSceneLevelControlFilter();
                gPUImageSceneLevelControlFilter7.setSceneParameter(0.06666667f, 1.0f, 0.7529412f, 0.0f, 1.0f);
                linkedList.add(gPUImageSceneLevelControlFilter7);
                return new GPUImageFilterGroup(linkedList);
            case 303:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Scene/filter_scene_backlit.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 304:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Scene/filter_scene_cloudy.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 305:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Scene/filter_scene_darken.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 306:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Scene/filter_scene_flash.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case StatusLine.HTTP_TEMP_REDIRECT /* 307 */:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Scene/filter_scene_fluorescent.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case StatusLine.HTTP_PERM_REDIRECT /* 308 */:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Scene/filter_scene_food.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 309:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Scene/filter_scene_landscape.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 310:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Scene/filter_scene_night.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 311:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Scene/filter_portrait.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 312:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Scene/filter_sandsnow.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 313:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Scene/filter_shade.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 314:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Scene/filter_sunset.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 315:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/Scene/filter_theatre.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 316:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/SceneW/filter_w_fengjing.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 317:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/SceneW/filter_w_shiwu.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 318:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/SceneW/filter_w_yanhuo.jpg", GPUImageToneCurveMapFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 319:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/name_acv/Suri.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 320:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/name_acv/Taylor.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 321:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/name_acv/Monroe.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 322:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/name_acv/Shirley.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 323:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/name_acv/Hepburn.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 324:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/name_acv/Lily.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 325:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/name_acv/Alsa.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 326:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/name_acv/Garbo.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 327:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/name_acv/Ingrid.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 328:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/name_acv/Miho.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 329:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/name_acv/Betty.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 330:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/name_acv/Sophia.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 331:
                linkedList.add(new GPUImageSaturationFilter(0.0f));
                linkedList.add(new GPUImageExposureFilter(-0.5f));
                pointF.x = 0.35f;
                pointF.y = 0.35f;
                linkedList.add(new GPUImageVignetteExposureFilter(pointF, 0.2f, -0.5f, 0.4f, 0.85f));
                linkedList.add(new GPUImageVignetteContrastFilter(pointF, 0.7f, 1.2f, 0.2f, 0.75f));
                linkedList.add(new GPUImageVignetteSharpenFilter(pointF, 0.18f, -0.45f, 0.2f, 0.75f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/name_acv/Vivien.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 332:
                linkedList.add(GPUImageFilterCreator.createFilterForTwoInputFilter(context, "filter/BW/Kopan/map.png", GPUImageToneCurveMapFilter.class));
                linkedList.add(new GPUImageSaturationFilter(0.0f));
                return new GPUImageFilterGroup(linkedList);
            case 333:
                GPUImageFilter createACVCurveFilter = GPUImageFilterCreator.createACVCurveFilter(context, "filter/lomo/l1.acv");
                createACVCurveFilter.setMix(0.6f);
                linkedList.add(createACVCurveFilter);
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 334:
                linkedList.add(new GPUImageGrayscaleFilter());
                GPUImageFilter createACVCurveFilter2 = GPUImageFilterCreator.createACVCurveFilter(context, "filter/lomo/l2.acv");
                createACVCurveFilter2.setMix(0.6f);
                linkedList.add(createACVCurveFilter2);
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 335:
                GPUImageFilter createACVCurveFilter3 = GPUImageFilterCreator.createACVCurveFilter(context, "filter/lomo/l3.acv");
                createACVCurveFilter3.setMix(0.875f);
                linkedList.add(createACVCurveFilter3);
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 336:
                linkedList.add(new GPUImageVignetteFilter(0.95f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_lomo/shx_lomo_1.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 337:
                linkedList.add(new GPUImageVignetteFilter(0.95f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_lomo/shx_lomo_2.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 338:
                linkedList.add(new GPUImageVignetteFilter(0.95f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_lomo/shx_lomo_3.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 339:
                linkedList.add(new GPUImageVignetteFilter(0.95f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_lomo/shx_lomo_4.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 340:
                linkedList.add(new GPUImageVignetteFilter(0.95f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_lomo/shx_lomo_5.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 341:
                linkedList.add(new GPUImageVignetteFilter(0.95f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_lomo/shx_lomo_6.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 342:
                linkedList.add(new GPUImageVignetteFilter(0.95f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_lomo/shx_lomo_7.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 343:
                linkedList.add(new GPUImageVignetteFilter(0.95f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_lomo/shx_lomo_8.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 344:
                linkedList.add(new GPUImageVignetteFilter(0.95f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_lomo/shx_lomo_9.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 345:
                linkedList.add(new GPUImageVignetteFilter(0.95f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_lomo/shx_lomo_10.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 346:
                linkedList.add(new GPUImageVignetteFilter(0.95f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_lomo/shx_lomo_11.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 347:
                linkedList.add(new GPUImageVignetteFilter(0.95f, 0.5f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_lomo/shx_lomo_12.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 348:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_pure/shx_pure_1.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 349:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_pure/shx_pure_2.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 350:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_pure/shx_pure_3.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 351:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_pure/shx_pure_4.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 352:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_pure/shx_pure_5.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 353:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_pure/shx_pure_6.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 354:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_pure/shx_pure_7.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 355:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_pure/shx_pure_8.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 356:
                linkedList.add(new GPUImageSaturationFilter(0.7f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_pure_s/shx_pure_s_1.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 357:
                linkedList.add(new GPUImageSaturationFilter(0.7f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_pure_s/shx_pure_s_2.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 358:
                linkedList.add(new GPUImageSaturationFilter(0.7f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_pure_s/shx_pure_s_3.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 359:
                linkedList.add(new GPUImageSaturationFilter(0.7f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_pure_s/shx_pure_s_4.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 360:
                linkedList.add(new GPUImageSaturationFilter(0.7f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_pure_s/shx_pure_s_5.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 361:
                linkedList.add(new GPUImageSaturationFilter(0.7f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_pure_s/shx_pure_s_6.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 362:
                linkedList.add(new GPUImageSaturationFilter(0.7f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_pure_s/shx_pure_s_7.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 363:
                linkedList.add(new GPUImageSaturationFilter(0.7f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_pure_s/shx_pure_s_8.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 364:
                linkedList.add(new GPUImageSaturationFilter(0.7f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_pure_s/shx_pure_s_9.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 365:
                linkedList.add(new GPUImageSaturationFilter(0.7f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_pure_s/shx_pure_s_10.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 366:
                linkedList.add(new GPUImageSaturationFilter(0.7f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_pure_s/shx_pure_s_11.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 367:
                linkedList.add(new GPUImageSaturationFilter(0.7f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_pure_s/shx_pure_s_12.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 368:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_film/shx_film_1.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 369:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_film/shx_film_2.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 370:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_film/shx_film_3.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 371:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_film/shx_film_4.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 372:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_film/shx_film_5.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 373:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_film/shx_film_6.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 374:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_film/shx_film_7.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 375:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_film/shx_film_8.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 376:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_film/shx_film_9.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 377:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_film/shx_film_10.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 378:
                linkedList.add(new GPUImageSaturationFilter(0.6f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_film_s/shx_film_s_1.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 379:
                linkedList.add(new GPUImageSaturationFilter(0.6f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_film_s/shx_film_s_2.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 380:
                linkedList.add(new GPUImageSaturationFilter(0.6f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_film_s/shx_film_s_3.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 381:
                linkedList.add(new GPUImageSaturationFilter(0.6f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_film_s/shx_film_s_4.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 382:
                linkedList.add(new GPUImageSaturationFilter(0.6f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_film_s/shx_film_s_5.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 383:
                linkedList.add(new GPUImageSaturationFilter(0.6f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_film_s/shx_film_s_6.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 384:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_retro/shx_retro_1.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 385:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_retro/shx_retro_2.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 386:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_retro/shx_retro_3.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 387:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_retro/shx_retro_4.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 388:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_retro/shx_retro_5.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 389:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_retro/shx_retro_6.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 390:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_retro/shx_retro_7.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 391:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_retro/shx_retro_8.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 392:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_retro/shx_retro_9.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 393:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_retro/shx_retro_10.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 394:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_retro/shx_retro_11.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 395:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_retro/shx_retro_12.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 396:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_retro/shx_retro_13.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 397:
                linkedList.add(new GPUImageSaturationFilter(0.57f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_retro_s/shx_retro_s_1.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 398:
                linkedList.add(new GPUImageSaturationFilter(0.57f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_retro_s/shx_retro_s_2.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 399:
                linkedList.add(new GPUImageSaturationFilter(0.57f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_retro_s/shx_retro_s_3.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 400:
                linkedList.add(new GPUImageSaturationFilter(0.57f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_retro_s/shx_retro_s_4.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 401:
                linkedList.add(new GPUImageSaturationFilter(0.57f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/shx_retro_s/shx_retro_s_5.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 402:
                linkedList.add(new GPUImageSaturationFilter(0.63f));
                GPUImageLevelsFilter gPUImageLevelsFilter = new GPUImageLevelsFilter();
                gPUImageLevelsFilter.setMin(0.0f, 0.71f, 0.8901961f);
                linkedList.add(gPUImageLevelsFilter);
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/lomo/l4.acv"));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageOverlayBlendFilter.class));
                linkedList.add(new GPUImageOpacityFilter(0.14f));
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 403:
                GPUImageFilter createACVCurveFilter4 = GPUImageFilterCreator.createACVCurveFilter(context, "filter/lomo/l5.acv");
                createACVCurveFilter4.setMix(0.9f);
                linkedList.add(createACVCurveFilter4);
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 404:
                linkedList.add(new GPUImageSaturationFilter(0.75f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/lomo/l6.acv"));
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 405:
                GPUImageLevelsFilter gPUImageLevelsFilter2 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter2.setMin(0.0f, 0.42f, 0.92156863f);
                gPUImageLevelsFilter2.setRedMin(0.0f, 1.56f, 0.7019608f, 0.39215687f, 0.48235294f);
                linkedList.add(gPUImageLevelsFilter2);
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 406:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/lomo/l8.acv"));
                GPUImageColorBalanceFilter gPUImageColorBalanceFilter = new GPUImageColorBalanceFilter();
                gPUImageColorBalanceFilter.setShowdows(new float[]{-0.1f, -0.135f, 0.0f});
                gPUImageColorBalanceFilter.setPreserveLuminosity(false);
                linkedList.add(gPUImageColorBalanceFilter);
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 407:
                GPUImageFilter createACVCurveFilter5 = GPUImageFilterCreator.createACVCurveFilter(context, "filter/lomo/l9.acv");
                createACVCurveFilter5.setMix(0.6f);
                linkedList.add(createACVCurveFilter5);
                new GPUImageLevelsFilter().setMin(0.0f, 0.8f, 1.0f, 0.13333334f, 0.8627451f);
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 408:
                linkedList.add(new GPUImageSaturationFilter(0.85f));
                GPUImageFilter createACVCurveFilter6 = GPUImageFilterCreator.createACVCurveFilter(context, "filter/lomo/l10.acv");
                createACVCurveFilter6.setMix(0.55f);
                linkedList.add(createACVCurveFilter6);
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 409:
                linkedList.add(new GPUImageColorBalanceFilter(new float[]{0.0f, -0.05f, 0.0f}, new float[]{-0.12f, 0.09f, -0.03f}, new float[]{0.0f, -0.065f, 0.13f}));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, 863504640, GPUImageExclusionBlendFilter.class));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/lomo/l11.acv"));
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 410:
                linkedList.add(new GPUImageOpacityFilter(0.6f));
                GPUImageColorBalanceFilter gPUImageColorBalanceFilter2 = new GPUImageColorBalanceFilter();
                gPUImageColorBalanceFilter2.setShowdows(new float[]{0.0f, 0.0f, 0.105f});
                gPUImageColorBalanceFilter2.setMidtones(new float[]{-0.23f, 0.01f, -0.16f});
                gPUImageColorBalanceFilter2.setPreserveLuminosity(false);
                linkedList.add(gPUImageColorBalanceFilter2);
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, -5456156, GPUImageSoftLightBlendFilter.class));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, -16767389, GPUImageLightenBlendFilter.class));
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 411:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, -9652759, GPUImageSoftLightBlendFilter.class));
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 412:
                linkedList.add(new GPUImageSaturationFilter(1.21f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/lomo/l14.acv"));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, -16185051, GPUImageExclusionBlendFilter.class));
                linkedList.add(new GPUImageSharpenFilter());
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 413:
                GPUImageLevelsFilter gPUImageLevelsFilter3 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter3.setMin(0.0f, 1.0f, 1.0f, 0.11372549f, 0.8745098f);
                linkedList.add(gPUImageLevelsFilter3);
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/lomo/l15.acv"));
                gPUImageLevelsFilter3.setMin(0.1764706f, 1.16f, 0.8784314f, 0.08627451f, 0.85882354f);
                linkedList.add(gPUImageLevelsFilter3);
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 414:
                GPUImageLevelsFilter gPUImageLevelsFilter4 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter4.setMin(0.11764706f, 0.7f, 1.0f, 0.19215687f, 1.0f);
                linkedList.add(gPUImageLevelsFilter4);
                GPUImageFilter createACVCurveFilter7 = GPUImageFilterCreator.createACVCurveFilter(context, "filter/lomo/l16.acv");
                createACVCurveFilter7.setMix(0.6f);
                linkedList.add(createACVCurveFilter7);
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/lomo/l16.acv"));
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 415:
                linkedList.add(new GPUImageColorBalanceFilter(new float[]{-0.21f, -0.04f, 0.365f}, new float[]{-0.115f, 0.065f, 0.15f}, new float[]{0.21f, -0.01f, -0.335f}));
                GPUImageLevelsFilter gPUImageLevelsFilter5 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter5.setMin(0.03529412f, 1.74f, 0.80784315f, 0.09803922f, 0.8980392f);
                linkedList.add(gPUImageLevelsFilter5);
                linkedList.add(new GPUImageOpacityFilter(0.6f));
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 416:
                GPUImageLevelsFilter gPUImageLevelsFilter6 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter6.setMin(0.0f, 0.6f, 1.0f);
                linkedList.add(gPUImageLevelsFilter6);
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/lomo/l18.acv"));
                linkedList.add(new GPUImageHueFilter());
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 417:
                linkedList.add(new GPUImageBrightnessFilter(-0.25f));
                linkedList.add(new GPUImageContrastFilter(2.0f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/lomo/l19.acv"));
                linkedList.add(new GPUImageColorBalanceFilter(new float[]{0.0f, -0.025f, 0.12f}, new float[]{0.0f, 0.0f, 0.31f}, new float[]{0.0f, -0.015f, -0.065f}, false));
                linkedList.add(new GPUImageBrightnessFilter(0.27f));
                linkedList.add(new GPUImageContrastFilter(0.76f));
                linkedList.add(new GPUImageBrightnessFilter(0.03f));
                linkedList.add(new GPUImageContrastFilter(1.11f));
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 418:
                linkedList.add(new GPUImageContrastFilter(1.25f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/lomo/l20.acv"));
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 419:
                GPUImageColorBalanceFilter gPUImageColorBalanceFilter3 = new GPUImageColorBalanceFilter();
                gPUImageColorBalanceFilter3.setMidtones(new float[]{-0.125f, 0.0f, 0.135f});
                gPUImageColorBalanceFilter3.setPreserveLuminosity(false);
                linkedList.add(gPUImageColorBalanceFilter3);
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 420:
                GPUImageColorBalanceFilter gPUImageColorBalanceFilter4 = new GPUImageColorBalanceFilter();
                gPUImageColorBalanceFilter4.setMidtones(new float[]{0.195f, 0.0f, -0.37f});
                linkedList.add(gPUImageColorBalanceFilter4);
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, -2613, GPUImageMultiplyBlendFilter.class));
                GPUImageColorBalanceFilter gPUImageColorBalanceFilter5 = new GPUImageColorBalanceFilter();
                gPUImageColorBalanceFilter5.setMidtones(new float[]{0.315f, 0.0f, 0.0f});
                linkedList.add(gPUImageColorBalanceFilter5);
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 421:
                GPUImageColorBalanceFilter gPUImageColorBalanceFilter6 = new GPUImageColorBalanceFilter();
                gPUImageColorBalanceFilter6.setMidtones(new float[]{0.415f, 0.0f, 0.0f});
                linkedList.add(gPUImageColorBalanceFilter6);
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, -2613, GPUImageMultiplyBlendFilter.class));
                GPUImageColorBalanceFilter gPUImageColorBalanceFilter7 = new GPUImageColorBalanceFilter();
                gPUImageColorBalanceFilter7.setMidtones(new float[]{-0.24f, 0.0f, 0.11f});
                linkedList.add(gPUImageColorBalanceFilter7);
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 422:
                fArr2[0] = -0.315f;
                fArr2[1] = -0.085f;
                fArr2[2] = 0.23f;
                fArr3[2] = -0.5f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3));
                GPUImageLevelsFilter gPUImageLevelsFilter7 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter7.setMin(0.0f, 1.23f, 0.7647059f);
                linkedList.add(gPUImageLevelsFilter7);
                fArr2[0] = 0.125f;
                fArr2[1] = 0.0f;
                fArr2[2] = 0.185f;
                fArr3[2] = 0.0f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3, false));
                fArr2[0] = -0.19f;
                fArr2[2] = 0.22f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3, false));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, -2104, GPUImageMultiplyBlendFilter.class));
                fArr2[0] = -0.305f;
                fArr2[1] = -0.095f;
                fArr2[2] = -0.105f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3, false));
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 423:
                fArr2[2] = -0.195f;
                fArr3[1] = 0.145f;
                fArr[2] = 0.33f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3, false));
                fArr2[0] = -0.45f;
                fArr2[1] = -0.32f;
                fArr2[2] = -0.45f;
                fArr3[0] = -0.085f;
                fArr3[1] = 0.0f;
                fArr[2] = 0.0f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3, false));
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 424:
                fArr2[1] = -0.29f;
                fArr2[2] = -0.145f;
                fArr3[2] = 0.175f;
                fArr[0] = -0.49f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, -2063609232, GPUImageMultiplyBlendFilter.class));
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 425:
                fArr2[2] = -0.125f;
                fArr3[2] = 0.13f;
                fArr[0] = -0.49f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3));
                fArr2[2] = 0.0f;
                fArr3[2] = 0.0f;
                fArr[0] = 0.0f;
                fArr[2] = 0.34f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, -9559, GPUImageMultiplyBlendFilter.class));
                linkedList.add(new GPUImageOpacityFilter(0.55f));
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 426:
                linkedList.add(new GPUImageSaturationFilter(0.0f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/lomo/l28.acv"));
                fArr2[0] = -0.055f;
                fArr2[2] = 0.08f;
                fArr[0] = 0.145f;
                fArr[2] = 0.085f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, -19456, GPUImageMultiplyBlendFilter.class));
                linkedList.add(new GPUImageOpacityFilter(0.09f));
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 427:
                fArr2[0] = 0.315f;
                fArr2[1] = 0.3f;
                fArr2[2] = 0.345f;
                fArr[0] = 0.055f;
                fArr[1] = -0.245f;
                fArr[2] = -0.27f;
                fArr3[1] = 0.12f;
                fArr3[2] = 0.26f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3));
                linkedList.add(new GPUImageColorFilter(-3417159));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, -3417159, GPUImageMultiplyBlendFilter.class));
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 428:
                fArr2[0] = 0.23f;
                fArr2[1] = -0.145f;
                fArr2[2] = 0.03f;
                fArr3[0] = -0.25f;
                fArr3[1] = 0.05f;
                fArr3[2] = -0.15f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, -16763238, GPUImageExclusionBlendFilter.class));
                linkedList.add(new GPUImageOpacityFilter(0.51f));
                fArr2[0] = 0.0f;
                fArr2[1] = -0.17f;
                fArr2[2] = -0.01f;
                fArr3[0] = 0.0f;
                fArr3[1] = 0.0f;
                fArr3[2] = 0.0f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3));
                linkedList.add(new GPUImageVignetteFilter());
                return new GPUImageFilterGroup(linkedList);
            case 429:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/softlight.acv"));
                linkedList.add(new GPUImageWhiteBalanceFilter(4850.0f, 40.0f));
                linkedList.add(new GPUImageGaussianBlurFilter(0.8f));
                return new GPUImageFilterGroup(linkedList);
            case 430:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/softlight.acv"));
                linkedList.add(new GPUImageWhiteBalanceFilter(4490.0f, 0.0f));
                linkedList.add(new GPUImageSaturationFilter(GPUAdjustRange.getSaturationRange(42)));
                linkedList.add(new GPUImageGaussianBlurFilter(0.4f));
                return new GPUImageFilterGroup(linkedList);
            case 431:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/softlight.acv"));
                linkedList.add(new GPUImageWhiteBalanceFilter(4850.0f, 40.0f));
                linkedList.add(new GPUImageGaussianBlurFilter(0.4f));
                return new GPUImageFilterGroup(linkedList);
            case 432:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/softlight.acv"));
                linkedList.add(new GPUImageWhiteBalanceFilter(7900.0f, 0.0f));
                linkedList.add(new GPUImageSaturationFilter(GPUAdjustRange.getSaturationRange(34)));
                linkedList.add(new GPUImageGaussianBlurFilter(0.4f));
                return new GPUImageFilterGroup(linkedList);
            case 433:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/softlight.acv"));
                linkedList.add(new GPUImageSaturationFilter(GPUAdjustRange.getSaturationRange(36)));
                linkedList.add(new GPUImageGaussianBlurFilter(0.2f));
                return new GPUImageFilterGroup(linkedList);
            case 434:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/softlight.acv"));
                linkedList.add(new GPUImageSaturationFilter(GPUAdjustRange.getSaturationRange(25)));
                linkedList.add(new GPUImageWhiteBalanceFilter(6300.0f, 0.0f));
                linkedList.add(new GPUImageGaussianBlurFilter(0.4f));
                return new GPUImageFilterGroup(linkedList);
            case 435:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/softlight.acv"));
                linkedList.add(new GPUImageSaturationFilter(GPUAdjustRange.getSaturationRange(40)));
                linkedList.add(new GPUImageWhiteBalanceFilter(4300.0f, 90.0f));
                linkedList.add(new GPUImageGaussianBlurFilter(0.64f));
                return new GPUImageFilterGroup(linkedList);
            case 436:
                return new GPUSeasonGloriousSpringBabyFilter();
            case 437:
                return new GPUSeasonSpringBlossomFilter();
            case 438:
                return new GPUSeasonSpringLightFilter();
            case 439:
                linkedList.add(new GPUImageBrightnessFilter(0.04f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/season/summer/classic_1.acv"));
                linkedList.add(new GPUImageBrightnessFilter(0.13f));
                GPUImageLevelsFilter gPUImageLevelsFilter8 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter8.setRedMin(0.0f, 0.94f, 1.0f);
                gPUImageLevelsFilter8.setGreenMin(0.0f, 0.96f, 1.0f);
                gPUImageLevelsFilter8.setBlueMin(0.05490196f, 0.89f, 0.96862745f);
                linkedList.add(gPUImageLevelsFilter8);
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/season/summer/classic_2.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 440:
                return new GPUSeasonSummerIndianFilter();
            case 441:
                return new GPUSeasonSummerDayFilter();
            case 442:
                GPUImageLevelsFilter gPUImageLevelsFilter9 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter9.setMin(0.078431375f, 1.0f, 1.0f);
                linkedList.add(gPUImageLevelsFilter9);
                linkedList.add(new GPUImageExposureFilter(0.355f));
                linkedList.add(new GPUImageSaturationFilter(1.1f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/season/autumn/dawood_hamada.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 443:
                return new GPUSeasonAutumnGentleFilter();
            case 444:
                return new GPUSeasonAutumnPremiumFilter();
            case 445:
                linkedList.add(new GPUImageSaturationFilter(1.3f));
                GPUImageLevelsFilter gPUImageLevelsFilter10 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter10.setMin(0.09019608f, 1.16f, 0.88235295f, 0.07058824f, 0.9607843f);
                linkedList.add(gPUImageLevelsFilter10);
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/season/winter/iced.acv", GPUSeasonWinterIcedFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 446:
                return new GPUSeasonWinterSnappyBabyFilter();
            case 447:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/season/winter/soft_brown.acv", GPUSeasonWinterSoftBrownFilter.class);
            case 448:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/sweet/premium_1.acv"));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/sweet/premium_2.acv"));
                linkedList.add(new GPUSweetPremiumFilter());
                return new GPUImageFilterGroup(linkedList);
            case 449:
                return new GPUImageNewVibranceFilter(-1.0f);
            case 450:
                GPUImageLevelsFilter gPUImageLevelsFilter11 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter11.setMin(0.0f, 1.23f, 1.0f);
                linkedList.add(gPUImageLevelsFilter11);
                GPUImageLevelsFilter gPUImageLevelsFilter12 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter12.setRedMin(0.0f, 1.1f, 1.0f, 0.0f, 0.827451f);
                gPUImageLevelsFilter12.setGreenMin(0.0f, 1.0f, 0.98039216f);
                gPUImageLevelsFilter12.setBlueMin(0.0f, 1.0f, 0.85490197f);
                linkedList.add(gPUImageLevelsFilter12);
                return new GPUImageFilterGroup(linkedList);
            case 451:
                GPUImageLevelsFilter gPUImageLevelsFilter13 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter13.setMin(0.0f, 0.81f, 1.0f);
                linkedList.add(gPUImageLevelsFilter13);
                GPUImageLevelsFilter gPUImageLevelsFilter14 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter14.setRedMin(0.0f, 1.0f, 1.0f, 0.11764706f, 1.0f);
                gPUImageLevelsFilter14.setGreenMin(0.0f, 1.0f, 1.0f, 0.0f, 0.95686275f);
                gPUImageLevelsFilter14.setBlueMin(0.0f, 1.11f, 1.0f, 0.078431375f, 1.0f);
                linkedList.add(gPUImageLevelsFilter14);
                return new GPUImageFilterGroup(linkedList);
            case 452:
                GPUImageLevelsFilter gPUImageLevelsFilter15 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter15.setMin(0.09803922f, 1.29f, 1.0f, 0.25490198f, 1.0f);
                linkedList.add(gPUImageLevelsFilter15);
                GPUImageLevelsFilter gPUImageLevelsFilter16 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter16.setRedMin(0.17254902f, 1.1f, 0.95686275f);
                gPUImageLevelsFilter16.setGreenMin(0.0f, 1.14f, 1.0f, 0.023529412f, 1.0f);
                gPUImageLevelsFilter16.setBlueMin(0.0f, 1.0f, 1.0f, 0.06666667f, 0.9372549f);
                linkedList.add(gPUImageLevelsFilter16);
                linkedList.add(new GPUImageOpacityFilter(0.65f));
                return new GPUImageFilterGroup(linkedList);
            case 453:
                GPUImageLevelsFilter gPUImageLevelsFilter17 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter17.setMin(0.07450981f, 1.24f, 1.0f, 0.1882353f, 1.0f);
                linkedList.add(gPUImageLevelsFilter17);
                GPUImageLevelsFilter gPUImageLevelsFilter18 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter18.setRedMin(0.11372549f, 1.08f, 0.99215686f, 0.2901961f, 0.98039216f);
                gPUImageLevelsFilter18.setBlueMin(0.10980392f, 1.07f, 1.0f, 0.2901961f, 0.93333334f);
                linkedList.add(gPUImageLevelsFilter18);
                return new GPUImageFilterGroup(linkedList);
            case 454:
                GPUImageLevelsFilter gPUImageLevelsFilter19 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter19.setRedMin(0.0f, 1.0f, 1.0f, 0.10980392f, 1.0f);
                gPUImageLevelsFilter19.setGreenMin(0.0f, 1.0f, 1.0f, 0.0f, 0.95686275f);
                gPUImageLevelsFilter19.setBlueMin(0.0f, 1.0f, 1.0f, 0.078431375f, 1.0f);
                return gPUImageLevelsFilter19;
            case 455:
                GPUImageLevelsFilter gPUImageLevelsFilter20 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter20.setBlueMin(0.0f, 1.3f, 1.0f);
                return gPUImageLevelsFilter20;
            case 456:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/sweet/primuem_1.acv"));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, 142677526, GPUImageSoftLightBlendFilter.class));
                fArr2[0] = -0.06f;
                fArr2[1] = -0.03f;
                fArr2[2] = 0.115f;
                fArr[1] = -0.06f;
                fArr[2] = -0.06f;
                fArr3[0] = -0.06f;
                fArr3[2] = 0.025f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, 993300109, GPUImageScreenBlendFilter.class));
                new GPUImageLevelsFilter().setMin(0.08235294f, 0.77f, 1.0f, 0.12941177f, 1.0f);
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/sweet/primuem_2.acv"));
                linkedList.add(new GPUImageNewVibranceFilter(0.05f));
                return new GPUImageFilterGroup(linkedList);
            case 457:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/sweet/romance.acv", GPUSweetRomanceFilter.class);
            case 458:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/sweet/rusty_tint.acv", GPUSweetRustyTintFilter.class);
            case 459:
                return new GPUSweetSoCoolFilter();
            case 460:
                GPUImageLevelsFilter gPUImageLevelsFilter21 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter21.setMin(0.0f, 1.5f, 1.0f, 0.078431375f, 1.0f);
                linkedList.add(gPUImageLevelsFilter21);
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/sweet/sweet.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 461:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/sweet/sweet_fall_embrace.acv");
            case 462:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/sweet/wake_up_1.acv"));
                GPUImageLevelsFilter gPUImageLevelsFilter22 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter22.setMin(0.050980393f, 1.13f, 1.0f, 0.13725491f, 0.9529412f);
                linkedList.add(gPUImageLevelsFilter22);
                gPUImageLevelsFilter22.setMin(0.0f, 1.0f, 1.0f, 0.0f, 1.0f);
                gPUImageLevelsFilter22.setBlueMin(0.0f, 1.0f, 1.0f, 0.20784314f, 0.89411765f);
                linkedList.add(gPUImageLevelsFilter22);
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/fresh/wake_up_2.acv"));
                gPUImageLevelsFilter22.setBlueMin(0.0f, 1.0f, 1.0f, 0.0f, 1.0f);
                gPUImageLevelsFilter22.setGreenMin(0.0f, 1.11f, 1.0f);
                linkedList.add(gPUImageLevelsFilter22);
                return new GPUImageFilterGroup(linkedList);
            case 463:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/fade/beautifully.acv", GPUFadeBeautifullyFilter.class);
            case 464:
                return new GPUFadeCoolHazeFilter();
            case 465:
                return GPUImageFilterCreator.createBlendFilter(context, -65794, GPUImageSoftLightBlendFilter.class);
            case 466:
                GPUImageLevelsFilter gPUImageLevelsFilter23 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter23.setMin(0.039215688f, 1.0f, 1.0f);
                linkedList.add(gPUImageLevelsFilter23);
                linkedList.add(new GPUImageContrastFilter(0.8f));
                linkedList.add(new GPUImageNewVibranceFilter(0.25f));
                linkedList.add(new GPUImageSaturationFilter(0.45f));
                linkedList.add(new GPUImageExposureFilter(0.06f));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, 450746289, GPUImageSoftLightBlendFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 467:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, -620159955, GPUImageExclusionBlendFilter.class));
                GPUImageLevelsFilter gPUImageLevelsFilter24 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter24.setRedMin(0.0f, 1.03f, 0.99607843f);
                gPUImageLevelsFilter24.setBlueMin(0.0f, 0.97f, 1.0f);
                linkedList.add(gPUImageLevelsFilter24);
                return new GPUImageFilterGroup(linkedList);
            case 468:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/fade/everyday.acv"));
                GPUImageLevelsFilter gPUImageLevelsFilter25 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter25.setMin(0.039215688f, 1.1f, 0.9607843f);
                linkedList.add(gPUImageLevelsFilter25);
                linkedList.add(new GPUImageNewVibranceFilter(0.12f));
                linkedList.add(new GPUImageSaturationFilter(0.88f));
                linkedList.add(new GPUImageContrastFilter(0.96f));
                linkedList.add(new GPUImageExposureFilter(0.03f));
                return new GPUImageFilterGroup(linkedList);
            case 469:
                linkedList.add(new GPUImageBrightnessFilter(0.07f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/fade/lime.acv"));
                linkedList.add(new GPUImageBrightnessFilter(0.07f));
                return new GPUImageFilterGroup(linkedList);
            case 470:
                linkedList.add(new GPUImageContrastFilter(1.34f));
                linkedList.add(new GPUImageSaturationFilter(0.81f));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, 704643071, GPUImageNormalBlendFilter.class));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, 451103820, GPUImageScreenBlendFilter.class));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, 447183225, GPUImageLightenBlendFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 471:
                linkedList.add(new GPUImageSaturationFilter(0.55f));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, 862786269, GPUImageAddBlendFilter.class));
                linkedList.add(new GPUImageSaturationFilter(0.7f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/fade/retro.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 472:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/fade/white_wash.acv"));
                linkedList.add(new GPUImageNewVibranceFilter(-0.3f));
                return new GPUImageFilterGroup(linkedList);
            case 473:
                return GPUImageFilterCreator.createBlendFilter(context, -65794, GPUImageSoftLightBlendFilter.class);
            case 474:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/ps.acv"));
                linkedList.add(new GPUImageHueFilter(12.0f));
                linkedList.add(new GPUImageSaturationFilter(0.83f));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, 301989888, GPUImageNormalBlendFilter.class));
                GPUImageLevelsFilter gPUImageLevelsFilter26 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter26.setMin(0.12941177f, 1.0f, 1.0f);
                linkedList.add(gPUImageLevelsFilter26);
                linkedList.add(new GPUImageContrastFilter(1.08f));
                return new GPUImageFilterGroup(linkedList);
            case 475:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/a_vol_1_1.acv"));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/a_vol_1_2.acv"));
                linkedList.add(new GPUImageHueFilter(1.0f));
                linkedList.add(new GPUImageSaturationFilter(0.52f));
                return new GPUImageFilterGroup(linkedList);
            case 476:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/a_vol_2_1.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 477:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/a_vol_3_1.acv");
            case 478:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/a_vol_4_1.acv");
            case 479:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/a_vol_12_1.acv");
            case 480:
                linkedList.add(new GPUImageSaturationFilter(0.75f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/a_vol_20_1.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 481:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/a_vol_22_1.acv");
            case 482:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/ambitious_1.acv"));
                linkedList.add(new GPUImageNewVibranceFilter(0.39f));
                linkedList.add(new GPUImageSaturationFilter(0.9f));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, 452954624, GPUImageExclusionBlendFilter.class));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/ambitious_2.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 483:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/brisk_1.acv", 0.5f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/brisk_2.acv"));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/brisk_3.acv", 0.5f));
                return new GPUImageFilterGroup(linkedList);
            case 484:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/c_vol_2_1.acv");
            case 485:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/c_vol_8_1.acv", RetroCVol8Filter.class);
            case 486:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/c_vol_13_1.acv"));
                fArr2[0] = 0.1f;
                fArr2[1] = -0.215f;
                fArr2[2] = -0.26f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3));
                linkedList.add(new GPUImageSaturationFilter(0.85f));
                return new GPUImageFilterGroup(linkedList);
            case 487:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/chestnut_brown_1.acv", RetroChestnutBrownFilter.class);
            case 488:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, 1506150620, GPUImageLuminosityBlendFilter.class));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/cp_24.acv"));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, 452393048, GPUImageExclusionBlendFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 489:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/delicate_brown.acv", RetroDelicateBrownFilter.class);
            case 490:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/flash_back_1.acv", 0.5f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/flash_back_2.acv"));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/flash_back_3.acv", 0.5f, GPUImageToneCurveLuminosityBlendFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 491:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/premium_1.acv", RetroPremiumFilter.class);
            case 492:
                linkedList.add(new GPUImageSaturationFilter(0.8f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/3.acv"));
                GPUImageLevelsFilter gPUImageLevelsFilter27 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter27.setMin(0.0f, 1.0f, 1.0f, 0.05882353f, 0.93333334f);
                linkedList.add(gPUImageLevelsFilter27);
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, 1303359540, GPUImageHardLightBlendFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 493:
                return new Retro17Filter();
            case 494:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/royal_1.acv"));
                linkedList.add(new GPUImageNewVibranceFilter(0.3f));
                return new GPUImageFilterGroup(linkedList);
            case 495:
                linkedList.add(new GPUImageGrayscaleFilter());
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, -15526634, GPUImageExclusionBlendFilter.class));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, -1558043296, GPUImageExclusionBlendFilter.class));
                fArr[0] = 0.055f;
                fArr[2] = 0.055f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/night_fate.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 496:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/spirited_1.acv", 0.5f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/spirited_2.acv"));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/retro/spirited_3.acv", 0.5f, GPUImageToneCurveLuminosityBlendFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 497:
                return new RetroVintageFilter();
            case 498:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/16.acv");
            case 499:
                GPUImageLevelsFilter gPUImageLevelsFilter28 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter28.setRedMin(0.003921569f, 1.0f, 1.0f);
                gPUImageLevelsFilter28.setGreenMin(0.003921569f, 1.0f, 1.0f);
                gPUImageLevelsFilter28.setBlueMin(0.003921569f, 1.0f, 0.99607843f);
                linkedList.add(gPUImageLevelsFilter28);
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/3.acv"));
                return new GPUImageFilterGroup(linkedList);
            case ServiceStarter.ERROR_UNKNOWN /* 500 */:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/b_vol_1.acv"));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, -8938919, GPUImageSoftLightBlendFilter.class));
                fArr2[0] = -0.105f;
                fArr2[1] = -0.145f;
                fArr2[2] = 0.205f;
                fArr3[2] = -0.065f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, -2020108894, GPUImageMultiplyBlendFilter.class));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/b_vol_2.acv"));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, 1208420126, GPUImageExclusionBlendFilter.class));
                linkedList.add(new GPUImageNewVibranceFilter(0.08f));
                linkedList.add(new FilmBVolTemplateFilter());
                return new GPUImageFilterGroup(linkedList);
            case 501:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/carina.acv", FilmCarinaFilter.class);
            case 502:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/classic_blue.acv", FilmClassicBlueFilter.class);
            case 503:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/cool_breeze.acv", FilmCoolBreezeFilter.class);
            case 504:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/cooler.acv", FilmCoolerFilter.class);
            case 505:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/cp_12.acv", FilmCP12Filter.class);
            case 506:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/free_spirit_1.acv", FilmFreeSpiritFilter.class));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/free_spirit_2.acv"));
                fArr2[0] = -0.035f;
                fArr2[2] = 0.02f;
                fArr3[0] = 0.015f;
                fArr3[2] = -0.085f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/free_spirit_3.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 507:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, 436247807, GPUImageExclusionBlendFilter.class));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, 872403601, GPUImageDarkenBlendFilter.class));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/grey_light.acv"));
                fArr2[0] = 0.035f;
                fArr2[1] = -0.05f;
                fArr2[2] = -0.08f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, -1027423550, GPUImageOverlayBlendFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 508:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/lust_1.acv", 0.5f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/lust_2.acv"));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/lust_3.acv", 0.5f, GPUImageToneCurveLuminosityBlendFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 509:
                return new FilmPaprikaFilter();
            case 510:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/premium_6.acv");
            case FrameMetricsAggregator.EVERY_DURATION /* 511 */:
                GPUImageLevelsFilter gPUImageLevelsFilter29 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter29.setRedMin(0.0f, 1.0f, 1.0f, 0.22745098f, 0.91764706f);
                gPUImageLevelsFilter29.setGreenMin(0.0f, 1.0f, 1.0f, 0.03137255f, 0.9019608f);
                gPUImageLevelsFilter29.setBlueMin(0.0f, 1.0f, 1.0f, 0.31764707f, 0.7490196f);
                linkedList.add(gPUImageLevelsFilter29);
                fArr2[1] = 0.025f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3));
                return new GPUImageFilterGroup(linkedList);
            case 512:
                return new FilmPremium31Filter();
            case InputDeviceCompat.SOURCE_DPAD /* 513 */:
                linkedList.add(new FilmRendezvousFilter());
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/rendezvous_1.acv"));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/rendezvous_2.acv"));
                fArr2[0] = -0.045f;
                fArr2[1] = 0.005f;
                fArr3[0] = -0.06f;
                fArr3[1] = 0.035f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/rendezvous_3.acv", 0.5f));
                return new GPUImageFilterGroup(linkedList);
            case 514:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/18.acv", Film18Filter.class);
            case 515:
                linkedList.add(new GPUImageSaturationFilter(0.45f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/night_fate_2.acv"));
                fArr2[0] = 0.15f;
                fArr2[2] = -0.25f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3));
                return new GPUImageFilterGroup(linkedList);
            case 516:
                fArr2[0] = -0.235f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3, false));
                linkedList.add(new GPUImageSaturationFilter(0.55f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/night_fate_6_1.acv"));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, -15855326, GPUImageDifferenceBlendFilter.class));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, -2059065023, GPUImageExclusionBlendFilter.class));
                fArr2[0] = 0.2f;
                fArr2[2] = 0.03f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/night_fate_6_2.acv"));
                fArr2[0] = -0.105f;
                fArr2[2] = 0.11f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3));
                return new GPUImageFilterGroup(linkedList);
            case 517:
                linkedList.add(new GPUImageSaturationFilter(1.3f));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, 1505957186, GPUImageLightenBlendFilter.class));
                fArr2[0] = -0.185f;
                fArr2[2] = 0.22f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3));
                linkedList.add(new FilmNightFate3Filter());
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/night_fate_3_1.acv"));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, 536857607, GPUImageNormalBlendFilter.class));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/night_fate_3_2.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 518:
                fArr2[0] = 0.045f;
                fArr2[1] = -0.085f;
                fArr2[2] = -0.115f;
                fArr[0] = -0.035f;
                fArr[1] = -0.03f;
                fArr[2] = -0.005f;
                fArr3[0] = -0.035f;
                fArr3[1] = -0.055f;
                fArr3[2] = -0.155f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/toning_evolution.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 519:
                fArr2[0] = -0.015f;
                fArr2[1] = 0.13f;
                fArr2[2] = -0.045f;
                fArr[0] = 0.02f;
                fArr[1] = -0.005f;
                fArr[2] = 0.05f;
                fArr3[0] = -0.115f;
                fArr3[1] = 0.07f;
                fArr3[2] = 0.045f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/toning_hazard.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 520:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, -37888, GPUImageHueBlendFilter.class));
                GPUImageLevelsFilter gPUImageLevelsFilter30 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter30.setMin(0.043137256f, 1.0f, 1.0f, 0.039215688f, 1.0f);
                linkedList.add(gPUImageLevelsFilter30);
                gPUImageLevelsFilter30.setRedMin(0.039215688f, 1.0f, 1.0f, 0.08627451f, 0.9607843f);
                gPUImageLevelsFilter30.setGreenMin(0.0f, 1.0f, 1.0f, 0.0f, 1.0f);
                gPUImageLevelsFilter30.setBlueMin(0.0f, 1.0f, 1.0f, 0.015686275f, 0.96862745f);
                linkedList.add(gPUImageLevelsFilter30);
                fArr2[0] = -0.07f;
                fArr2[1] = -0.015f;
                fArr[0] = -0.04f;
                fArr[1] = -0.015f;
                fArr3[0] = -0.04f;
                fArr3[1] = 0.005f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3));
                return new GPUImageFilterGroup(linkedList);
            case 521:
                fArr2[0] = -0.185f;
                fArr2[1] = 0.04f;
                fArr2[2] = 0.015f;
                fArr[0] = -0.045f;
                fArr[1] = -0.07f;
                fArr[2] = 0.01f;
                fArr3[1] = 0.025f;
                fArr3[2] = -0.095f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/toning_urban_criminal.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 522:
                GPUImageLevelsFilter gPUImageLevelsFilter31 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter31.setMin(0.05882353f, 1.27f, 1.0f);
                linkedList.add(gPUImageLevelsFilter31);
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/vintage_made_simple_1.acv"));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/vintage_made_simple_2.acv"));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, 449676912, GPUImageNormalBlendFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 523:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/Film/warm_tones.acv"));
                GPUImageLevelsFilter gPUImageLevelsFilter32 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter32.setRedMin(0.0f, 1.14f, 1.0f);
                gPUImageLevelsFilter32.setGreenMin(0.0f, 1.1f, 1.0f);
                gPUImageLevelsFilter32.setBlueMin(0.0f, 0.84f, 1.0f);
                linkedList.add(gPUImageLevelsFilter32);
                linkedList.add(new GPUImageBrightnessFilter(0.04f));
                fArr2[0] = 0.05f;
                fArr2[1] = 0.01f;
                fArr2[2] = -0.01f;
                fArr[0] = -0.015f;
                fArr[1] = 0.01f;
                fArr[2] = -0.03f;
                fArr3[0] = 0.04f;
                fArr3[1] = -0.01f;
                fArr3[2] = -0.05f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3));
                return new GPUImageFilterGroup(linkedList);
            case 524:
                return new FoodAdjustToneCoolShadowsFilter();
            case 525:
                GPUImageLevelsFilter gPUImageLevelsFilter33 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter33.setMin(0.019607844f, 1.18f, 0.9882353f);
                return gPUImageLevelsFilter33;
            case 526:
                return new FoodCaliFilter();
            case 527:
                GPUImageLevelsFilter gPUImageLevelsFilter34 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter34.setMin(0.11764706f, 1.28f, 0.9254902f);
                return gPUImageLevelsFilter34;
            case 528:
                return new GPUImageSaturationFilter(1.3f);
            case 529:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/food/first_class_1.acv"));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, 450154641, GPUImageScreenBlendFilter.class));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, 452971520, GPUImageMultiplyBlendFilter.class));
                fArr2[2] = 0.095f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/food/first_class_2.acv"));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, 1110847565, GPUImageExclusionBlendFilter.class));
                linkedList.add(new GPUImageBrightnessFilter(0.05f));
                linkedList.add(new GPUImageContrastFilter(1.08f));
                return new GPUImageFilterGroup(linkedList);
            case 530:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/food/gemma_1.acv"));
                linkedList.add(new GPUImageSaturationFilter(1.22f));
                fArr2[0] = -0.085f;
                fArr2[1] = -0.01f;
                fArr2[2] = 0.08f;
                fArr[0] = -0.12f;
                fArr[1] = 0.045f;
                fArr[2] = 0.105f;
                fArr3[2] = -0.06f;
                linkedList.add(new GPUImageColorBalanceFilter(fArr, fArr2, fArr3));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, -3170897, GPUImageSoftLightBlendFilter.class));
                linkedList.add(new GPUImageExposureFilter(-0.06f));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/food/gemma_2.acv"));
                return new GPUImageFilterGroup(linkedList);
            case 531:
                return new FoodIceFilter();
            case 532:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/food/luciana_1.acv"));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, 444612473, GPUImageDifferenceBlendFilter.class));
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/food/luciana_2.acv"));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, 729739007, GPUImageSoftLightBlendFilter.class));
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, 906761779, GPUImageScreenBlendFilter.class));
                return new GPUImageFilterGroup(linkedList);
            case 533:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/food/orton.acv"));
                linkedList.add(new GPUImageBrightnessFilter(0.12f));
                GPUImageLevelsFilter gPUImageLevelsFilter35 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter35.setRedMin(0.078431375f, 0.91f, 0.94509804f);
                gPUImageLevelsFilter35.setBlueMin(0.0f, 0.94f, 1.0f);
                linkedList.add(gPUImageLevelsFilter35);
                return new GPUImageFilterGroup(linkedList);
            case 534:
                linkedList.add(new GPUImageBrightnessFilter(0.04f));
                linkedList.add(new GPUImageContrastFilter(1.41f));
                linkedList.add(new GPUImageSaturationFilter(1.3f));
                return new GPUImageFilterGroup(linkedList);
            case 535:
                linkedList.add(GPUImageFilterCreator.createACVCurveFilter(context, "filter/food/restore_color.acv"));
                GPUImageLevelsFilter gPUImageLevelsFilter36 = new GPUImageLevelsFilter();
                gPUImageLevelsFilter36.setMin(0.0f, 1.1f, 1.0f);
                linkedList.add(gPUImageLevelsFilter36);
                linkedList.add(new GPUImageSaturationFilter(1.3f));
                return new GPUImageFilterGroup(linkedList);
            case 536:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/ls/6romance.acv");
            case 537:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/ls/1memory.acv");
            case 538:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/ls/3pink.acv");
            case 539:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/ls/4chocolate.acv");
            case 540:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/ls/8princess.acv");
            case 541:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/ls/15ice.acv");
            case 542:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/ls/17sunshine.acv");
            case 543:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/ls/18baby.acv");
            case 544:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/ls/21love.acv");
            case 545:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/ls/11angel.acv");
            case 546:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageScreenBlendFilter.class, "filter/sip/Light.png"));
                return new GPUImageFilterGroup(linkedList);
            case 547:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageScreenBlendFilter.class, "filter/sip/Warm.png"));
                return new GPUImageFilterGroup(linkedList);
            case 548:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageScreenBlendFilter.class, "filter/sip/Dazzle.png"));
                return new GPUImageFilterGroup(linkedList);
            case 549:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageScreenBlendFilter.class, "filter/sip/Flare.png"));
                return new GPUImageFilterGroup(linkedList);
            case 550:
                return GPUImageFilterCreator.createACVCurveFilter(context, "filter/ls/chocolates.acv");
            case 551:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageScreenBlendFilter.class, "filter/ls/halo_9.png"));
                return new GPUImageFilterGroup(linkedList);
            case 552:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageScreenBlendFilter.class, "filter/ls/halo_12.png"));
                return new GPUImageFilterGroup(linkedList);
            case 553:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageScreenBlendFilter.class, "filter/ls/leak_12.png"));
                return new GPUImageFilterGroup(linkedList);
            case 554:
                linkedList.add(GPUImageFilterCreator.createBlendFilter(context, GPUImageScreenBlendFilter.class, "filter/ls/leak_14.png"));
                return new GPUImageFilterGroup(linkedList);
            default:
                throw new IllegalStateException("No filter of that type!");
        }
        return GPUImageFilterCreator.createDATCurveFilter(context, "filter/d1.dat");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.picspool.lib.filter.gpu.GPUFilterFactory$1 */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class C30981 {
        static final /* synthetic */ int[] $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType;

        static {
            int[] iArr = new int[GPUFilterType.values().length];
            $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType = iArr;
            try {
                iArr[GPUFilterType.TEST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.NOFILTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F1.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F2.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F3.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F4.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F5.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F6.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F7.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F8.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F9.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F10.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F11.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F12.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F13.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F14.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F15.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F16.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F17.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F18.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F19.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F20.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F21.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F22.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F23.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F24.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F25.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F26.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F27.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F28.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F29.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F30.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F31.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F32.ordinal()] = 34;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F33.ordinal()] = 35;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F34.ordinal()] = 36;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F35.ordinal()] = 37;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F36.ordinal()] = 38;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F37.ordinal()] = 39;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F38.ordinal()] = 40;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F39.ordinal()] = 41;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F40.ordinal()] = 42;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F41.ordinal()] = 43;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F42.ordinal()] = 44;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F43.ordinal()] = 45;
            } catch (NoSuchFieldError unused45) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F44.ordinal()] = 46;
            } catch (NoSuchFieldError unused46) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F45.ordinal()] = 47;
            } catch (NoSuchFieldError unused47) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F46.ordinal()] = 48;
            } catch (NoSuchFieldError unused48) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F47.ordinal()] = 49;
            } catch (NoSuchFieldError unused49) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F48.ordinal()] = 50;
            } catch (NoSuchFieldError unused50) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F49.ordinal()] = 51;
            } catch (NoSuchFieldError unused51) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F50.ordinal()] = 52;
            } catch (NoSuchFieldError unused52) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F51.ordinal()] = 53;
            } catch (NoSuchFieldError unused53) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F52.ordinal()] = 54;
            } catch (NoSuchFieldError unused54) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F53.ordinal()] = 55;
            } catch (NoSuchFieldError unused55) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F54.ordinal()] = 56;
            } catch (NoSuchFieldError unused56) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F55.ordinal()] = 57;
            } catch (NoSuchFieldError unused57) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F56.ordinal()] = 58;
            } catch (NoSuchFieldError unused58) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F57.ordinal()] = 59;
            } catch (NoSuchFieldError unused59) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F58.ordinal()] = 60;
            } catch (NoSuchFieldError unused60) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F59.ordinal()] = 61;
            } catch (NoSuchFieldError unused61) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F60.ordinal()] = 62;
            } catch (NoSuchFieldError unused62) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F61.ordinal()] = 63;
            } catch (NoSuchFieldError unused63) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F62.ordinal()] = 64;
            } catch (NoSuchFieldError unused64) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F63.ordinal()] = 65;
            } catch (NoSuchFieldError unused65) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F64.ordinal()] = 66;
            } catch (NoSuchFieldError unused66) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F65.ordinal()] = 67;
            } catch (NoSuchFieldError unused67) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F66.ordinal()] = 68;
            } catch (NoSuchFieldError unused68) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F67.ordinal()] = 69;
            } catch (NoSuchFieldError unused69) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F68.ordinal()] = 70;
            } catch (NoSuchFieldError unused70) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F69.ordinal()] = 71;
            } catch (NoSuchFieldError unused71) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F70.ordinal()] = 72;
            } catch (NoSuchFieldError unused72) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F71.ordinal()] = 73;
            } catch (NoSuchFieldError unused73) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F72.ordinal()] = 74;
            } catch (NoSuchFieldError unused74) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F73.ordinal()] = 75;
            } catch (NoSuchFieldError unused75) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F74.ordinal()] = 76;
            } catch (NoSuchFieldError unused76) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F75.ordinal()] = 77;
            } catch (NoSuchFieldError unused77) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F76.ordinal()] = 78;
            } catch (NoSuchFieldError unused78) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F77.ordinal()] = 79;
            } catch (NoSuchFieldError unused79) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F78.ordinal()] = 80;
            } catch (NoSuchFieldError unused80) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F79.ordinal()] = 81;
            } catch (NoSuchFieldError unused81) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F80.ordinal()] = 82;
            } catch (NoSuchFieldError unused82) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F81.ordinal()] = 83;
            } catch (NoSuchFieldError unused83) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F82.ordinal()] = 84;
            } catch (NoSuchFieldError unused84) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F83.ordinal()] = 85;
            } catch (NoSuchFieldError unused85) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F84.ordinal()] = 86;
            } catch (NoSuchFieldError unused86) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F85.ordinal()] = 87;
            } catch (NoSuchFieldError unused87) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F86.ordinal()] = 88;
            } catch (NoSuchFieldError unused88) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F87.ordinal()] = 89;
            } catch (NoSuchFieldError unused89) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F88.ordinal()] = 90;
            } catch (NoSuchFieldError unused90) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F89.ordinal()] = 91;
            } catch (NoSuchFieldError unused91) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F90.ordinal()] = 92;
            } catch (NoSuchFieldError unused92) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F91.ordinal()] = 93;
            } catch (NoSuchFieldError unused93) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F92.ordinal()] = 94;
            } catch (NoSuchFieldError unused94) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F93.ordinal()] = 95;
            } catch (NoSuchFieldError unused95) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.F94.ordinal()] = 96;
            } catch (NoSuchFieldError unused96) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_T7AM.ordinal()] = 97;
            } catch (NoSuchFieldError unused97) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_Y1974.ordinal()] = 98;
            } catch (NoSuchFieldError unused98) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_ABSINTH.ordinal()] = 99;
            } catch (NoSuchFieldError unused99) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_BUENOS_AIRES.ordinal()] = 100;
            } catch (NoSuchFieldError unused100) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_DENIM.ordinal()] = 101;
            } catch (NoSuchFieldError unused101) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_ROYAL_BLUE.ordinal()] = 102;
            } catch (NoSuchFieldError unused102) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_SMOKY.ordinal()] = 103;
            } catch (NoSuchFieldError unused103) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_TOASTER.ordinal()] = 104;
            } catch (NoSuchFieldError unused104) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_BK1.ordinal()] = 105;
            } catch (NoSuchFieldError unused105) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_BK2.ordinal()] = 106;
            } catch (NoSuchFieldError unused106) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_BK3.ordinal()] = 107;
            } catch (NoSuchFieldError unused107) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_BK4.ordinal()] = 108;
            } catch (NoSuchFieldError unused108) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_BK5.ordinal()] = 109;
            } catch (NoSuchFieldError unused109) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_BK6.ordinal()] = 110;
            } catch (NoSuchFieldError unused110) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_BK7.ordinal()] = 111;
            } catch (NoSuchFieldError unused111) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_BK8.ordinal()] = 112;
            } catch (NoSuchFieldError unused112) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_SKETCH.ordinal()] = 113;
            } catch (NoSuchFieldError unused113) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_CLASSICSKETCH.ordinal()] = 114;
            } catch (NoSuchFieldError unused114) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_COLORSKETCH.ordinal()] = 115;
            } catch (NoSuchFieldError unused115) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_KRAFT.ordinal()] = 116;
            } catch (NoSuchFieldError unused116) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_OLDPHOTO.ordinal()] = 117;
            } catch (NoSuchFieldError unused117) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_OLDMOVIE.ordinal()] = 118;
            } catch (NoSuchFieldError unused118) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_Y1974_SHOT.ordinal()] = 119;
            } catch (NoSuchFieldError unused119) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_ABSINTH_SHOT.ordinal()] = 120;
            } catch (NoSuchFieldError unused120) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_BUENOS_AIRES_SHOT.ordinal()] = 121;
            } catch (NoSuchFieldError unused121) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_DENIM_SHOT.ordinal()] = 122;
            } catch (NoSuchFieldError unused122) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_DENIM2_SHOT.ordinal()] = 123;
            } catch (NoSuchFieldError unused123) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_ROYAL_BLUE_SHOT.ordinal()] = 124;
            } catch (NoSuchFieldError unused124) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_SMOKY_SHOT.ordinal()] = 125;
            } catch (NoSuchFieldError unused125) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OLD_TOASTER_SHOT.ordinal()] = 126;
            } catch (NoSuchFieldError unused126) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.DAT_BANBO.ordinal()] = 127;
            } catch (NoSuchFieldError unused127) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.DAT_BETTERSKIN.ordinal()] = 128;
            } catch (NoSuchFieldError unused128) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.DAT_DEEPWHITE.ordinal()] = 129;
            } catch (NoSuchFieldError unused129) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.DAT_HDR.ordinal()] = 130;
            } catch (NoSuchFieldError unused130) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.DAT_JDHDR.ordinal()] = 131;
            } catch (NoSuchFieldError unused131) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.DAT_NATURALWHITE.ordinal()] = 132;
            } catch (NoSuchFieldError unused132) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.DAT_SKINSMOOTH.ordinal()] = 133;
            } catch (NoSuchFieldError unused133) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.DAT_SUNNY.ordinal()] = 134;
            } catch (NoSuchFieldError unused134) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.DAT_SWEETY.ordinal()] = 135;
            } catch (NoSuchFieldError unused135) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.DAT_ZIRAN.ordinal()] = 136;
            } catch (NoSuchFieldError unused136) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.DAT_LANDIAOPATH.ordinal()] = 137;
            } catch (NoSuchFieldError unused137) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.DAT_LANDIAO.ordinal()] = 138;
            } catch (NoSuchFieldError unused138) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.DAT_XIAOZHEN.ordinal()] = 139;
            } catch (NoSuchFieldError unused139) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.DAT_LOMOPATH.ordinal()] = 140;
            } catch (NoSuchFieldError unused140) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.DAT_LOMO.ordinal()] = 141;
            } catch (NoSuchFieldError unused141) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.DAT_HEIBAI.ordinal()] = 142;
            } catch (NoSuchFieldError unused142) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.DAT_WEIMEI.ordinal()] = 143;
            } catch (NoSuchFieldError unused143) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.DAT_SHENLAN.ordinal()] = 144;
            } catch (NoSuchFieldError unused144) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.DAT_QINGXIN.ordinal()] = 145;
            } catch (NoSuchFieldError unused145) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.DAT_FENNEN.ordinal()] = 146;
            } catch (NoSuchFieldError unused146) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.DAT_QIUSE.ordinal()] = 147;
            } catch (NoSuchFieldError unused147) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.DAT_HUIYI.ordinal()] = 148;
            } catch (NoSuchFieldError unused148) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.ABAO2.ordinal()] = 149;
            } catch (NoSuchFieldError unused149) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.ABAO.ordinal()] = 150;
            } catch (NoSuchFieldError unused150) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.RIXI.ordinal()] = 151;
            } catch (NoSuchFieldError unused151) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.AMARO.ordinal()] = 152;
            } catch (NoSuchFieldError unused152) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.MAYFAIR.ordinal()] = 153;
            } catch (NoSuchFieldError unused153) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.RISE.ordinal()] = 154;
            } catch (NoSuchFieldError unused154) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.HUDSON.ordinal()] = 155;
            } catch (NoSuchFieldError unused155) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VALENCIA.ordinal()] = 156;
            } catch (NoSuchFieldError unused156) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.XPRO2.ordinal()] = 157;
            } catch (NoSuchFieldError unused157) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SIERRA.ordinal()] = 158;
            } catch (NoSuchFieldError unused158) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.WILLOW.ordinal()] = 159;
            } catch (NoSuchFieldError unused159) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOFI.ordinal()] = 160;
            } catch (NoSuchFieldError unused160) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.EARLYBIRD.ordinal()] = 161;
            } catch (NoSuchFieldError unused161) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SUTRO.ordinal()] = 162;
            } catch (NoSuchFieldError unused162) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.TOASTER.ordinal()] = 163;
            } catch (NoSuchFieldError unused163) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BRANNAN.ordinal()] = 164;
            } catch (NoSuchFieldError unused164) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.INKWELL.ordinal()] = 165;
            } catch (NoSuchFieldError unused165) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.WALDEN.ordinal()] = 166;
            } catch (NoSuchFieldError unused166) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.HEFE.ordinal()] = 167;
            } catch (NoSuchFieldError unused167) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.NASHVILLE.ordinal()] = 168;
            } catch (NoSuchFieldError unused168) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.Y1977.ordinal()] = 169;
            } catch (NoSuchFieldError unused169) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.KELVIN.ordinal()] = 170;
            } catch (NoSuchFieldError unused170) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.Y1970.ordinal()] = 171;
            } catch (NoSuchFieldError unused171) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.Y1975.ordinal()] = 172;
            } catch (NoSuchFieldError unused172) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.Y1980.ordinal()] = 173;
            } catch (NoSuchFieldError unused173) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BWRetro.ordinal()] = 174;
            } catch (NoSuchFieldError unused174) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.ASHBY.ordinal()] = 175;
            } catch (NoSuchFieldError unused175) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BROOKLYN.ordinal()] = 176;
            } catch (NoSuchFieldError unused176) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.CHARMES.ordinal()] = 177;
            } catch (NoSuchFieldError unused177) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.CLARENDON.ordinal()] = 178;
            } catch (NoSuchFieldError unused178) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.DOGPATCH.ordinal()] = 179;
            } catch (NoSuchFieldError unused179) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.GINGHAM.ordinal()] = 180;
            } catch (NoSuchFieldError unused180) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.GINZA.ordinal()] = 181;
            } catch (NoSuchFieldError unused181) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.HELENA.ordinal()] = 182;
            } catch (NoSuchFieldError unused182) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.MAVEN.ordinal()] = 183;
            } catch (NoSuchFieldError unused183) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.MOON.ordinal()] = 184;
            } catch (NoSuchFieldError unused184) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SKYLINE.ordinal()] = 185;
            } catch (NoSuchFieldError unused185) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.STINSON.ordinal()] = 186;
            } catch (NoSuchFieldError unused186) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VESPER.ordinal()] = 187;
            } catch (NoSuchFieldError unused187) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VSCO1.ordinal()] = 188;
            } catch (NoSuchFieldError unused188) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VSCO2.ordinal()] = 189;
            } catch (NoSuchFieldError unused189) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VSCO3.ordinal()] = 190;
            } catch (NoSuchFieldError unused190) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VSCO4.ordinal()] = 191;
            } catch (NoSuchFieldError unused191) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VSCO5.ordinal()] = 192;
            } catch (NoSuchFieldError unused192) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VSCO6.ordinal()] = 193;
            } catch (NoSuchFieldError unused193) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VSCO7.ordinal()] = 194;
            } catch (NoSuchFieldError unused194) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VSCO8.ordinal()] = 195;
            } catch (NoSuchFieldError unused195) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VSCO9.ordinal()] = 196;
            } catch (NoSuchFieldError unused196) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VSCO10.ordinal()] = 197;
            } catch (NoSuchFieldError unused197) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VSCO11.ordinal()] = 198;
            } catch (NoSuchFieldError unused198) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VSCO12.ordinal()] = 199;
            } catch (NoSuchFieldError unused199) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VSCO13.ordinal()] = 200;
            } catch (NoSuchFieldError unused200) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VSCO14.ordinal()] = 201;
            } catch (NoSuchFieldError unused201) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VSCO15.ordinal()] = 202;
            } catch (NoSuchFieldError unused202) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VSCO16.ordinal()] = 203;
            } catch (NoSuchFieldError unused203) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VSCO17.ordinal()] = 204;
            } catch (NoSuchFieldError unused204) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VSCO18.ordinal()] = 205;
            } catch (NoSuchFieldError unused205) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SOFTLIGHT.ordinal()] = 206;
            } catch (NoSuchFieldError unused206) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.GSBLUR.ordinal()] = 207;
            } catch (NoSuchFieldError unused207) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.CONTRAST.ordinal()] = 208;
            } catch (NoSuchFieldError unused208) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.GAMMA.ordinal()] = 209;
            } catch (NoSuchFieldError unused209) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BRIGHTNESS.ordinal()] = 210;
            } catch (NoSuchFieldError unused210) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHARPEN.ordinal()] = 211;
            } catch (NoSuchFieldError unused211) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SATURATION.ordinal()] = 212;
            } catch (NoSuchFieldError unused212) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.EXPOSURE.ordinal()] = 213;
            } catch (NoSuchFieldError unused213) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.WHITE_BALANCE.ordinal()] = 214;
            } catch (NoSuchFieldError unused214) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VIGNETTE.ordinal()] = 215;
            } catch (NoSuchFieldError unused215) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.TONE_CURVE_MAP.ordinal()] = 216;
            } catch (NoSuchFieldError unused216) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.MAPSELFBLEND.ordinal()] = 217;
            } catch (NoSuchFieldError unused217) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.MAPBLEND.ordinal()] = 218;
            } catch (NoSuchFieldError unused218) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.INVERT.ordinal()] = 219;
            } catch (NoSuchFieldError unused219) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.PIXELATION.ordinal()] = 220;
            } catch (NoSuchFieldError unused220) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.HUE.ordinal()] = 221;
            } catch (NoSuchFieldError unused221) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SEPIA.ordinal()] = 222;
            } catch (NoSuchFieldError unused222) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SOBEL_EDGE_DETECTION.ordinal()] = 223;
            } catch (NoSuchFieldError unused223) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.THREE_X_THREE_CONVOLUTION.ordinal()] = 224;
            } catch (NoSuchFieldError unused224) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.EMBOSS.ordinal()] = 225;
            } catch (NoSuchFieldError unused225) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.POSTERIZE.ordinal()] = 226;
            } catch (NoSuchFieldError unused226) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.COM_FILTER_GROUP.ordinal()] = 227;
            } catch (NoSuchFieldError unused227) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.HIGHLIGHT_SHADOW.ordinal()] = 228;
            } catch (NoSuchFieldError unused228) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.MONOCHROME.ordinal()] = 229;
            } catch (NoSuchFieldError unused229) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.OPACITY.ordinal()] = 230;
            } catch (NoSuchFieldError unused230) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.RGB.ordinal()] = 231;
            } catch (NoSuchFieldError unused231) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.TONE_CURVE.ordinal()] = 232;
            } catch (NoSuchFieldError unused232) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VIGNETTE_BRIGHTNESS.ordinal()] = 233;
            } catch (NoSuchFieldError unused233) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VIGNETTE_COLORINVERT.ordinal()] = 234;
            } catch (NoSuchFieldError unused234) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VIGNETTE_CONTRAST.ordinal()] = 235;
            } catch (NoSuchFieldError unused235) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VIGNETTE_EXPOSURE.ordinal()] = 236;
            } catch (NoSuchFieldError unused236) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VIGNETTE_GAMMA.ordinal()] = 237;
            } catch (NoSuchFieldError unused237) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VIGNETTE_GSBLUR.ordinal()] = 238;
            } catch (NoSuchFieldError unused238) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VIGNETTE_GRAYSCALE.ordinal()] = 239;
            } catch (NoSuchFieldError unused239) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VIGNETTE_HUE.ordinal()] = 240;
            } catch (NoSuchFieldError unused240) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VIGNETTE_MAPSELFBLEND.ordinal()] = 241;
            } catch (NoSuchFieldError unused241) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VIGNETTE_SHARPNESS.ordinal()] = 242;
            } catch (NoSuchFieldError unused242) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VIGNETTE_TONECURVEMAP.ordinal()] = 243;
            } catch (NoSuchFieldError unused243) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BLEND_DIFFERENCE.ordinal()] = 244;
            } catch (NoSuchFieldError unused244) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BLEND_SOURCE_OVER.ordinal()] = 245;
            } catch (NoSuchFieldError unused245) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BLEND_COLOR_BURN.ordinal()] = 246;
            } catch (NoSuchFieldError unused246) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BLEND_COLOR_DODGE.ordinal()] = 247;
            } catch (NoSuchFieldError unused247) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BLEND_DARKEN.ordinal()] = 248;
            } catch (NoSuchFieldError unused248) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BLEND_DISSOLVE.ordinal()] = 249;
            } catch (NoSuchFieldError unused249) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BLEND_EXCLUSION.ordinal()] = 250;
            } catch (NoSuchFieldError unused250) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BLEND_HARD_LIGHT.ordinal()] = 251;
            } catch (NoSuchFieldError unused251) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BLEND_LIGHTEN.ordinal()] = 252;
            } catch (NoSuchFieldError unused252) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BLEND_ADD.ordinal()] = 253;
            } catch (NoSuchFieldError unused253) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BLEND_DIVIDE.ordinal()] = 254;
            } catch (NoSuchFieldError unused254) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BLEND_MULTIPLY.ordinal()] = 255;
            } catch (NoSuchFieldError unused255) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BLEND_OVERLAY.ordinal()] = 256;
            } catch (NoSuchFieldError unused256) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BLEND_SCREEN.ordinal()] = 257;
            } catch (NoSuchFieldError unused257) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BLEND_COLOR.ordinal()] = 258;
            } catch (NoSuchFieldError unused258) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BLEND_HUE.ordinal()] = 259;
            } catch (NoSuchFieldError unused259) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BLEND_SATURATION.ordinal()] = 260;
            } catch (NoSuchFieldError unused260) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BLEND_LUMINOSITY.ordinal()] = 261;
            } catch (NoSuchFieldError unused261) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BLEND_LINEAR_BURN.ordinal()] = 262;
            } catch (NoSuchFieldError unused262) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BLEND_SOFT_LIGHT.ordinal()] = 263;
            } catch (NoSuchFieldError unused263) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BLEND_SUBTRACT.ordinal()] = 264;
            } catch (NoSuchFieldError unused264) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BLEND_CHROMA_KEY.ordinal()] = 265;
            } catch (NoSuchFieldError unused265) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BLEND_NORMAL.ordinal()] = 266;
            } catch (NoSuchFieldError unused266) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_MS.ordinal()] = 267;
            } catch (NoSuchFieldError unused267) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_SN.ordinal()] = 268;
            } catch (NoSuchFieldError unused268) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_NG.ordinal()] = 269;
            } catch (NoSuchFieldError unused269) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_RL.ordinal()] = 270;
            } catch (NoSuchFieldError unused270) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_SGD.ordinal()] = 271;
            } catch (NoSuchFieldError unused271) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_FJ.ordinal()] = 272;
            } catch (NoSuchFieldError unused272) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LEMO_JD.ordinal()] = 273;
            } catch (NoSuchFieldError unused273) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LEMO_PY.ordinal()] = 274;
            } catch (NoSuchFieldError unused274) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LEMO_PR.ordinal()] = 275;
            } catch (NoSuchFieldError unused275) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LEMO_PB.ordinal()] = 276;
            } catch (NoSuchFieldError unused276) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LEMO_PO.ordinal()] = 277;
            } catch (NoSuchFieldError unused277) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LEMO_JDR.ordinal()] = 278;
            } catch (NoSuchFieldError unused278) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LEMO_JDY.ordinal()] = 279;
            } catch (NoSuchFieldError unused279) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LEMO_JDB.ordinal()] = 280;
            } catch (NoSuchFieldError unused280) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LEMO_PB2.ordinal()] = 281;
            } catch (NoSuchFieldError unused281) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_A4.ordinal()] = 282;
            } catch (NoSuchFieldError unused282) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_A5.ordinal()] = 283;
            } catch (NoSuchFieldError unused283) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_A6.ordinal()] = 284;
            } catch (NoSuchFieldError unused284) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_ADEN.ordinal()] = 285;
            } catch (NoSuchFieldError unused285) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_B1.ordinal()] = 286;
            } catch (NoSuchFieldError unused286) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_B5.ordinal()] = 287;
            } catch (NoSuchFieldError unused287) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_CREMA.ordinal()] = 288;
            } catch (NoSuchFieldError unused288) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_HB1.ordinal()] = 289;
            } catch (NoSuchFieldError unused289) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_LUDWIG.ordinal()] = 290;
            } catch (NoSuchFieldError unused290) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_M5.ordinal()] = 291;
            } catch (NoSuchFieldError unused291) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_SLUMBER.ordinal()] = 292;
            } catch (NoSuchFieldError unused292) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_T1.ordinal()] = 293;
            } catch (NoSuchFieldError unused293) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_X1.ordinal()] = 294;
            } catch (NoSuchFieldError unused294) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_W_FIREWORK.ordinal()] = 295;
            } catch (NoSuchFieldError unused295) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_W_NIGHT.ordinal()] = 296;
            } catch (NoSuchFieldError unused296) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_W_SUNSET.ordinal()] = 297;
            } catch (NoSuchFieldError unused297) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_W_BACKLIT.ordinal()] = 298;
            } catch (NoSuchFieldError unused298) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_W_BRIGHTEN.ordinal()] = 299;
            } catch (NoSuchFieldError unused299) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_W_DARKEN.ordinal()] = 300;
            } catch (NoSuchFieldError unused300) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_W_LOWSAT.ordinal()] = 301;
            } catch (NoSuchFieldError unused301) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_W_HIGHSAT.ordinal()] = 302;
            } catch (NoSuchFieldError unused302) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_F_BACKLIT.ordinal()] = 303;
            } catch (NoSuchFieldError unused303) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_F_CLOUDY.ordinal()] = 304;
            } catch (NoSuchFieldError unused304) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_F_DARKEN.ordinal()] = 305;
            } catch (NoSuchFieldError unused305) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_F_FLASH.ordinal()] = 306;
            } catch (NoSuchFieldError unused306) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_F_FLUORESCENT.ordinal()] = 307;
            } catch (NoSuchFieldError unused307) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_F_FOOD.ordinal()] = 308;
            } catch (NoSuchFieldError unused308) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_F_LANDSCAPE.ordinal()] = 309;
            } catch (NoSuchFieldError unused309) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_F_NIGHT.ordinal()] = 310;
            } catch (NoSuchFieldError unused310) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_F_PORTRAIT.ordinal()] = 311;
            } catch (NoSuchFieldError unused311) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_F_SANDSNOW.ordinal()] = 312;
            } catch (NoSuchFieldError unused312) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_F_SHADE.ordinal()] = 313;
            } catch (NoSuchFieldError unused313) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_F_SUNSET.ordinal()] = 314;
            } catch (NoSuchFieldError unused314) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_F_THEATRE.ordinal()] = 315;
            } catch (NoSuchFieldError unused315) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_WN_FENGJING.ordinal()] = 316;
            } catch (NoSuchFieldError unused316) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_WN_SHIWU.ordinal()] = 317;
            } catch (NoSuchFieldError unused317) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SCENE_WN_YANHUO.ordinal()] = 318;
            } catch (NoSuchFieldError unused318) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SURI.ordinal()] = 319;
            } catch (NoSuchFieldError unused319) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.TAYLOR.ordinal()] = 320;
            } catch (NoSuchFieldError unused320) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.MONROE.ordinal()] = 321;
            } catch (NoSuchFieldError unused321) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHIRLEY.ordinal()] = 322;
            } catch (NoSuchFieldError unused322) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.HEPBURN.ordinal()] = 323;
            } catch (NoSuchFieldError unused323) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LILIANE.ordinal()] = 324;
            } catch (NoSuchFieldError unused324) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.ALSA.ordinal()] = 325;
            } catch (NoSuchFieldError unused325) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.GARBO.ordinal()] = 326;
            } catch (NoSuchFieldError unused326) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.INGRID.ordinal()] = 327;
            } catch (NoSuchFieldError unused327) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.MIHO.ordinal()] = 328;
            } catch (NoSuchFieldError unused328) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.BETTY.ordinal()] = 329;
            } catch (NoSuchFieldError unused329) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SOPHIA.ordinal()] = 330;
            } catch (NoSuchFieldError unused330) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.VIVIEN.ordinal()] = 331;
            } catch (NoSuchFieldError unused331) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.AUDREY.ordinal()] = 332;
            } catch (NoSuchFieldError unused332) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO1.ordinal()] = 333;
            } catch (NoSuchFieldError unused333) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO2.ordinal()] = 334;
            } catch (NoSuchFieldError unused334) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO3.ordinal()] = 335;
            } catch (NoSuchFieldError unused335) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_LOMO_1.ordinal()] = 336;
            } catch (NoSuchFieldError unused336) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_LOMO_2.ordinal()] = 337;
            } catch (NoSuchFieldError unused337) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_LOMO_3.ordinal()] = 338;
            } catch (NoSuchFieldError unused338) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_LOMO_4.ordinal()] = 339;
            } catch (NoSuchFieldError unused339) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_LOMO_5.ordinal()] = 340;
            } catch (NoSuchFieldError unused340) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_LOMO_6.ordinal()] = 341;
            } catch (NoSuchFieldError unused341) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_LOMO_7.ordinal()] = 342;
            } catch (NoSuchFieldError unused342) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_LOMO_8.ordinal()] = 343;
            } catch (NoSuchFieldError unused343) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_LOMO_9.ordinal()] = 344;
            } catch (NoSuchFieldError unused344) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_LOMO_10.ordinal()] = 345;
            } catch (NoSuchFieldError unused345) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_LOMO_11.ordinal()] = 346;
            } catch (NoSuchFieldError unused346) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_LOMO_12.ordinal()] = 347;
            } catch (NoSuchFieldError unused347) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_PURE_1.ordinal()] = 348;
            } catch (NoSuchFieldError unused348) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_PURE_2.ordinal()] = 349;
            } catch (NoSuchFieldError unused349) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_PURE_3.ordinal()] = 350;
            } catch (NoSuchFieldError unused350) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_PURE_4.ordinal()] = 351;
            } catch (NoSuchFieldError unused351) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_PURE_5.ordinal()] = 352;
            } catch (NoSuchFieldError unused352) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_PURE_6.ordinal()] = 353;
            } catch (NoSuchFieldError unused353) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_PURE_7.ordinal()] = 354;
            } catch (NoSuchFieldError unused354) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_PURE_8.ordinal()] = 355;
            } catch (NoSuchFieldError unused355) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_PURE_S_1.ordinal()] = 356;
            } catch (NoSuchFieldError unused356) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_PURE_S_2.ordinal()] = 357;
            } catch (NoSuchFieldError unused357) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_PURE_S_3.ordinal()] = 358;
            } catch (NoSuchFieldError unused358) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_PURE_S_4.ordinal()] = 359;
            } catch (NoSuchFieldError unused359) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_PURE_S_5.ordinal()] = 360;
            } catch (NoSuchFieldError unused360) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_PURE_S_6.ordinal()] = 361;
            } catch (NoSuchFieldError unused361) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_PURE_S_7.ordinal()] = 362;
            } catch (NoSuchFieldError unused362) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_PURE_S_8.ordinal()] = 363;
            } catch (NoSuchFieldError unused363) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_PURE_S_9.ordinal()] = 364;
            } catch (NoSuchFieldError unused364) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_PURE_S_10.ordinal()] = 365;
            } catch (NoSuchFieldError unused365) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_PURE_S_11.ordinal()] = 366;
            } catch (NoSuchFieldError unused366) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_PURE_S_12.ordinal()] = 367;
            } catch (NoSuchFieldError unused367) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_FILM_1.ordinal()] = 368;
            } catch (NoSuchFieldError unused368) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_FILM_2.ordinal()] = 369;
            } catch (NoSuchFieldError unused369) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_FILM_3.ordinal()] = 370;
            } catch (NoSuchFieldError unused370) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_FILM_4.ordinal()] = 371;
            } catch (NoSuchFieldError unused371) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_FILM_5.ordinal()] = 372;
            } catch (NoSuchFieldError unused372) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_FILM_6.ordinal()] = 373;
            } catch (NoSuchFieldError unused373) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_FILM_7.ordinal()] = 374;
            } catch (NoSuchFieldError unused374) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_FILM_8.ordinal()] = 375;
            } catch (NoSuchFieldError unused375) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_FILM_9.ordinal()] = 376;
            } catch (NoSuchFieldError unused376) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_FILM_10.ordinal()] = 377;
            } catch (NoSuchFieldError unused377) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_FILM_S_1.ordinal()] = 378;
            } catch (NoSuchFieldError unused378) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_FILM_S_2.ordinal()] = 379;
            } catch (NoSuchFieldError unused379) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_FILM_S_3.ordinal()] = 380;
            } catch (NoSuchFieldError unused380) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_FILM_S_4.ordinal()] = 381;
            } catch (NoSuchFieldError unused381) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_FILM_S_5.ordinal()] = 382;
            } catch (NoSuchFieldError unused382) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_FILM_S_6.ordinal()] = 383;
            } catch (NoSuchFieldError unused383) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_RETRO_1.ordinal()] = 384;
            } catch (NoSuchFieldError unused384) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_RETRO_2.ordinal()] = 385;
            } catch (NoSuchFieldError unused385) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_RETRO_3.ordinal()] = 386;
            } catch (NoSuchFieldError unused386) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_RETRO_4.ordinal()] = 387;
            } catch (NoSuchFieldError unused387) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_RETRO_5.ordinal()] = 388;
            } catch (NoSuchFieldError unused388) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_RETRO_6.ordinal()] = 389;
            } catch (NoSuchFieldError unused389) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_RETRO_7.ordinal()] = 390;
            } catch (NoSuchFieldError unused390) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_RETRO_8.ordinal()] = 391;
            } catch (NoSuchFieldError unused391) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_RETRO_9.ordinal()] = 392;
            } catch (NoSuchFieldError unused392) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_RETRO_10.ordinal()] = 393;
            } catch (NoSuchFieldError unused393) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_RETRO_11.ordinal()] = 394;
            } catch (NoSuchFieldError unused394) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_RETRO_12.ordinal()] = 395;
            } catch (NoSuchFieldError unused395) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_RETRO_13.ordinal()] = 396;
            } catch (NoSuchFieldError unused396) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_RETRO_S_1.ordinal()] = 397;
            } catch (NoSuchFieldError unused397) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_RETRO_S_2.ordinal()] = 398;
            } catch (NoSuchFieldError unused398) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_RETRO_S_3.ordinal()] = 399;
            } catch (NoSuchFieldError unused399) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_RETRO_S_4.ordinal()] = 400;
            } catch (NoSuchFieldError unused400) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SHX_RETRO_S_5.ordinal()] = 401;
            } catch (NoSuchFieldError unused401) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO4.ordinal()] = 402;
            } catch (NoSuchFieldError unused402) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO5.ordinal()] = 403;
            } catch (NoSuchFieldError unused403) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO6.ordinal()] = 404;
            } catch (NoSuchFieldError unused404) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO7.ordinal()] = 405;
            } catch (NoSuchFieldError unused405) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO8.ordinal()] = 406;
            } catch (NoSuchFieldError unused406) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO9.ordinal()] = 407;
            } catch (NoSuchFieldError unused407) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO10.ordinal()] = 408;
            } catch (NoSuchFieldError unused408) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO11.ordinal()] = 409;
            } catch (NoSuchFieldError unused409) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO12.ordinal()] = 410;
            } catch (NoSuchFieldError unused410) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO13.ordinal()] = 411;
            } catch (NoSuchFieldError unused411) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO14.ordinal()] = 412;
            } catch (NoSuchFieldError unused412) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO15.ordinal()] = 413;
            } catch (NoSuchFieldError unused413) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO16.ordinal()] = 414;
            } catch (NoSuchFieldError unused414) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO17.ordinal()] = 415;
            } catch (NoSuchFieldError unused415) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO18.ordinal()] = 416;
            } catch (NoSuchFieldError unused416) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO19.ordinal()] = 417;
            } catch (NoSuchFieldError unused417) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO20.ordinal()] = 418;
            } catch (NoSuchFieldError unused418) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO21.ordinal()] = 419;
            } catch (NoSuchFieldError unused419) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO22.ordinal()] = 420;
            } catch (NoSuchFieldError unused420) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO23.ordinal()] = 421;
            } catch (NoSuchFieldError unused421) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO24.ordinal()] = 422;
            } catch (NoSuchFieldError unused422) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO25.ordinal()] = 423;
            } catch (NoSuchFieldError unused423) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO26.ordinal()] = 424;
            } catch (NoSuchFieldError unused424) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO27.ordinal()] = 425;
            } catch (NoSuchFieldError unused425) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO28.ordinal()] = 426;
            } catch (NoSuchFieldError unused426) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO29.ordinal()] = 427;
            } catch (NoSuchFieldError unused427) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LOMO30.ordinal()] = 428;
            } catch (NoSuchFieldError unused428) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.HALO1.ordinal()] = 429;
            } catch (NoSuchFieldError unused429) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.HALO2.ordinal()] = 430;
            } catch (NoSuchFieldError unused430) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.HALO3.ordinal()] = 431;
            } catch (NoSuchFieldError unused431) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.HALO4.ordinal()] = 432;
            } catch (NoSuchFieldError unused432) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.HALO5.ordinal()] = 433;
            } catch (NoSuchFieldError unused433) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.HALO6.ordinal()] = 434;
            } catch (NoSuchFieldError unused434) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.HALO7.ordinal()] = 435;
            } catch (NoSuchFieldError unused435) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SEASON_SPRING_GLORIOUS_BABY.ordinal()] = 436;
            } catch (NoSuchFieldError unused436) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SEASON_SPRING_BLOSSOM.ordinal()] = 437;
            } catch (NoSuchFieldError unused437) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SEASON_SPRING_LIGHT.ordinal()] = 438;
            } catch (NoSuchFieldError unused438) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SEASON_SUMMER_CLASSIC.ordinal()] = 439;
            } catch (NoSuchFieldError unused439) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SEASON_SUMMER_INDIAN.ordinal()] = 440;
            } catch (NoSuchFieldError unused440) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SEASON_SUMMER_DAY.ordinal()] = 441;
            } catch (NoSuchFieldError unused441) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SEASON_AUTUMN_DAWOOD_HAMADA.ordinal()] = 442;
            } catch (NoSuchFieldError unused442) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SEASON_AUTUMN_GENTLE.ordinal()] = 443;
            } catch (NoSuchFieldError unused443) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SEASON_AUTUMN_PREMIUM.ordinal()] = 444;
            } catch (NoSuchFieldError unused444) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SEASON_WINTER_ICED.ordinal()] = 445;
            } catch (NoSuchFieldError unused445) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SEASON_WINTER_SNAPPY_BABY.ordinal()] = 446;
            } catch (NoSuchFieldError unused446) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SEASON_WINTER_SOFT_BROWN.ordinal()] = 447;
            } catch (NoSuchFieldError unused447) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SWEET_PREMIUM.ordinal()] = 448;
            } catch (NoSuchFieldError unused448) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SWEET_ACTION.ordinal()] = 449;
            } catch (NoSuchFieldError unused449) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SWEET_CERULEAN_BLUE.ordinal()] = 450;
            } catch (NoSuchFieldError unused450) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SWEET_DEEP_PURPLE.ordinal()] = 451;
            } catch (NoSuchFieldError unused451) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SWEET_HAZY_TEAL.ordinal()] = 452;
            } catch (NoSuchFieldError unused452) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SWEET_LAVENDER_HAZE.ordinal()] = 453;
            } catch (NoSuchFieldError unused453) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SWEET_MAGENTA.ordinal()] = 454;
            } catch (NoSuchFieldError unused454) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SWEET_MORNING_GLOW.ordinal()] = 455;
            } catch (NoSuchFieldError unused455) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SWEET_PRIMUEM.ordinal()] = 456;
            } catch (NoSuchFieldError unused456) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SWEET_ROMANCE.ordinal()] = 457;
            } catch (NoSuchFieldError unused457) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SWEET_RUSTY_TINT.ordinal()] = 458;
            } catch (NoSuchFieldError unused458) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SWEET_SO_COOL.ordinal()] = 459;
            } catch (NoSuchFieldError unused459) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SWEET_SWEET.ordinal()] = 460;
            } catch (NoSuchFieldError unused460) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SWEET_SWEETFALLEMBRACE.ordinal()] = 461;
            } catch (NoSuchFieldError unused461) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SWEET_WAKE_UP.ordinal()] = 462;
            } catch (NoSuchFieldError unused462) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FADE_BEAUTIFULLY.ordinal()] = 463;
            } catch (NoSuchFieldError unused463) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FADE_COOL_HAZE.ordinal()] = 464;
            } catch (NoSuchFieldError unused464) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FADE_CUSTOM_CLEAN_LIGHT.ordinal()] = 465;
            } catch (NoSuchFieldError unused465) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FADE_DARK_DESATURATE.ordinal()] = 466;
            } catch (NoSuchFieldError unused466) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FADE_DIFFUSED_MATTE.ordinal()] = 467;
            } catch (NoSuchFieldError unused467) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FADE_EVERYDAY.ordinal()] = 468;
            } catch (NoSuchFieldError unused468) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FADE_LIME.ordinal()] = 469;
            } catch (NoSuchFieldError unused469) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FADE_LUCID.ordinal()] = 470;
            } catch (NoSuchFieldError unused470) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FADE_RETRO.ordinal()] = 471;
            } catch (NoSuchFieldError unused471) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FADE_WHITE_WASH.ordinal()] = 472;
            } catch (NoSuchFieldError unused472) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SWEET_CUSTOM_CLEAN_LIGHT.ordinal()] = 473;
            } catch (NoSuchFieldError unused473) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.RETRO_PS.ordinal()] = 474;
            } catch (NoSuchFieldError unused474) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.RETRO_A_VOL_1.ordinal()] = 475;
            } catch (NoSuchFieldError unused475) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.RETRO_A_VOL_2.ordinal()] = 476;
            } catch (NoSuchFieldError unused476) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.RETRO_A_VOL_3.ordinal()] = 477;
            } catch (NoSuchFieldError unused477) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.RETRO_A_VOL_4.ordinal()] = 478;
            } catch (NoSuchFieldError unused478) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.RETRO_A_VOL_12.ordinal()] = 479;
            } catch (NoSuchFieldError unused479) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.RETRO_A_VOL_20.ordinal()] = 480;
            } catch (NoSuchFieldError unused480) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.RETRO_A_VOL_22.ordinal()] = 481;
            } catch (NoSuchFieldError unused481) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.RETRO_AMBITIOUS.ordinal()] = 482;
            } catch (NoSuchFieldError unused482) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.RETRO_BRISK.ordinal()] = 483;
            } catch (NoSuchFieldError unused483) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.RETRO_C_VOL_2.ordinal()] = 484;
            } catch (NoSuchFieldError unused484) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.RETRO_C_VOL_8.ordinal()] = 485;
            } catch (NoSuchFieldError unused485) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.RETRO_C_VOL_13.ordinal()] = 486;
            } catch (NoSuchFieldError unused486) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.RETRO_CHESTNUT_BROWN.ordinal()] = 487;
            } catch (NoSuchFieldError unused487) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.RETRO_CP_24.ordinal()] = 488;
            } catch (NoSuchFieldError unused488) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.RETRO_DELICATE_BROWN.ordinal()] = 489;
            } catch (NoSuchFieldError unused489) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.RETRO_FLASH_BACK.ordinal()] = 490;
            } catch (NoSuchFieldError unused490) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.RETRO_PREMIUM.ordinal()] = 491;
            } catch (NoSuchFieldError unused491) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.RETRO_3.ordinal()] = 492;
            } catch (NoSuchFieldError unused492) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.RETRO_17.ordinal()] = 493;
            } catch (NoSuchFieldError unused493) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.RETRO_ROYAL.ordinal()] = 494;
            } catch (NoSuchFieldError unused494) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.RETRO_NIGHT_FATE.ordinal()] = 495;
            } catch (NoSuchFieldError unused495) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.RETRO_SPIRITED.ordinal()] = 496;
            } catch (NoSuchFieldError unused496) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.RETRO_VINTAGE.ordinal()] = 497;
            } catch (NoSuchFieldError unused497) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_16.ordinal()] = 498;
            } catch (NoSuchFieldError unused498) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_3.ordinal()] = 499;
            } catch (NoSuchFieldError unused499) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_B_VOL.ordinal()] = 500;
            } catch (NoSuchFieldError unused500) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_CARINA.ordinal()] = 501;
            } catch (NoSuchFieldError unused501) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_CLASSIC_BLUE.ordinal()] = 502;
            } catch (NoSuchFieldError unused502) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_COOL_BREEZE.ordinal()] = 503;
            } catch (NoSuchFieldError unused503) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_COOLER.ordinal()] = 504;
            } catch (NoSuchFieldError unused504) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_CP_12.ordinal()] = 505;
            } catch (NoSuchFieldError unused505) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_FREE_SPIRIT.ordinal()] = 506;
            } catch (NoSuchFieldError unused506) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_GREY_LIGHT.ordinal()] = 507;
            } catch (NoSuchFieldError unused507) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_LUST.ordinal()] = 508;
            } catch (NoSuchFieldError unused508) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_PAPRIKA.ordinal()] = 509;
            } catch (NoSuchFieldError unused509) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_PREMIUM_6.ordinal()] = 510;
            } catch (NoSuchFieldError unused510) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_PREMIUM_19.ordinal()] = 511;
            } catch (NoSuchFieldError unused511) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_PREMIUM_31.ordinal()] = 512;
            } catch (NoSuchFieldError unused512) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_RENDEZVOUS.ordinal()] = 513;
            } catch (NoSuchFieldError unused513) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_18.ordinal()] = 514;
            } catch (NoSuchFieldError unused514) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_NIGHT_FATE_2.ordinal()] = 515;
            } catch (NoSuchFieldError unused515) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_NIGHT_FATE_6.ordinal()] = 516;
            } catch (NoSuchFieldError unused516) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_NIGHT_FATE_3.ordinal()] = 517;
            } catch (NoSuchFieldError unused517) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_TONING_EVOLUTION.ordinal()] = 518;
            } catch (NoSuchFieldError unused518) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_TONING_HAZARD.ordinal()] = 519;
            } catch (NoSuchFieldError unused519) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_TONING_RUST.ordinal()] = 520;
            } catch (NoSuchFieldError unused520) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_TONING_URBAN_CRIMINAL.ordinal()] = 521;
            } catch (NoSuchFieldError unused521) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_VINTAGE_MADE_SIMPLE.ordinal()] = 522;
            } catch (NoSuchFieldError unused522) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FILM_WARM_TONES.ordinal()] = 523;
            } catch (NoSuchFieldError unused523) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FOOD_ADJUST_TONE_COOL_SHADOWS.ordinal()] = 524;
            } catch (NoSuchFieldError unused524) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FOOD_BRIGHTEN_MIDTONES.ordinal()] = 525;
            } catch (NoSuchFieldError unused525) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FOOD_CALI.ordinal()] = 526;
            } catch (NoSuchFieldError unused526) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FOOD_CONTRAST_HIGH_KEY.ordinal()] = 527;
            } catch (NoSuchFieldError unused527) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FOOD_DETAILS_PAINT_IN_SATURATION.ordinal()] = 528;
            } catch (NoSuchFieldError unused528) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FOOD_FIRST_CLASS.ordinal()] = 529;
            } catch (NoSuchFieldError unused529) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FOOD_GEMMA.ordinal()] = 530;
            } catch (NoSuchFieldError unused530) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FOOD_ICE.ordinal()] = 531;
            } catch (NoSuchFieldError unused531) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FOOD_LUCIANA.ordinal()] = 532;
            } catch (NoSuchFieldError unused532) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FOOD_ORTON.ordinal()] = 533;
            } catch (NoSuchFieldError unused533) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FOOD_PRETTY_PEEPERS.ordinal()] = 534;
            } catch (NoSuchFieldError unused534) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.FOOD_RESTORE_COLOR.ordinal()] = 535;
            } catch (NoSuchFieldError unused535) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LS_ROMANCE.ordinal()] = 536;
            } catch (NoSuchFieldError unused536) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LS_MEMORY.ordinal()] = 537;
            } catch (NoSuchFieldError unused537) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LS_PINK.ordinal()] = 538;
            } catch (NoSuchFieldError unused538) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LS_CHOCOLATE.ordinal()] = 539;
            } catch (NoSuchFieldError unused539) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LS_PRINCESS.ordinal()] = 540;
            } catch (NoSuchFieldError unused540) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LS_ICE.ordinal()] = 541;
            } catch (NoSuchFieldError unused541) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LS_SUNSHINE.ordinal()] = 542;
            } catch (NoSuchFieldError unused542) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LS_BABY.ordinal()] = 543;
            } catch (NoSuchFieldError unused543) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LS_LOVE.ordinal()] = 544;
            } catch (NoSuchFieldError unused544) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LS_ANGEL.ordinal()] = 545;
            } catch (NoSuchFieldError unused545) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SIP_LIGHT.ordinal()] = 546;
            } catch (NoSuchFieldError unused546) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SIP_WARM.ordinal()] = 547;
            } catch (NoSuchFieldError unused547) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SIP_DAZZLE.ordinal()] = 548;
            } catch (NoSuchFieldError unused548) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.SIP_FLARE.ordinal()] = 549;
            } catch (NoSuchFieldError unused549) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.LS_CHOCOLATES.ordinal()] = 550;
            } catch (NoSuchFieldError unused550) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.IS_H9.ordinal()] = 551;
            } catch (NoSuchFieldError unused551) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.IS_H12.ordinal()] = 552;
            } catch (NoSuchFieldError unused552) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.IS_L10.ordinal()] = 553;
            } catch (NoSuchFieldError unused553) {
            }
            try {
                $SwitchMap$org$picspool$lib$filter$gpu$GPUFilterType[GPUFilterType.IS_L12.ordinal()] = 554;
            } catch (NoSuchFieldError unused554) {
            }
        }
    }
}
