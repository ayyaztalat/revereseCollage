package com.picspool.lib.filter.gpu;

import android.content.Context;
import android.graphics.PointF;
import java.util.LinkedList;
import java.util.List;
import com.picspool.lib.filter.gpu.adjust.GPUImageBrightnessFilter;
import com.picspool.lib.filter.gpu.adjust.GPUImageContrastFilter;
import com.picspool.lib.filter.gpu.adjust.GPUImageExposureFilter;
import com.picspool.lib.filter.gpu.adjust.GPUImageGammaFilter;
import com.picspool.lib.filter.gpu.adjust.GPUImageHighlightShadowFilter;
import com.picspool.lib.filter.gpu.adjust.GPUImageHueFilter;
import com.picspool.lib.filter.gpu.adjust.GPUImageRGBFilter;
import com.picspool.lib.filter.gpu.adjust.GPUImageSaturationFilter;
import com.picspool.lib.filter.gpu.adjust.GPUImageSharpenFilter;
import com.picspool.lib.filter.gpu.adjust.GPUImageVibranceFilter;
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
import com.picspool.lib.filter.gpu.blend.GPUImageMultiplyBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageNormalBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageOverlayBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageSaturationBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageScreenBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageSoftLightBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageSourceOverBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageSubtractBlendFilter;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.gpu.father.GPUImageFilterGroup;
import com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter;
import com.picspool.lib.filter.gpu.normal.GPUImage3x3ConvolutionFilter;
import com.picspool.lib.filter.gpu.normal.GPUImage3x3TextureSamplingFilter;
import com.picspool.lib.filter.gpu.normal.GPUImageColorInvertFilter;
import com.picspool.lib.filter.gpu.normal.GPUImageEmbossFilter;
import com.picspool.lib.filter.gpu.normal.GPUImageLookupFilter;
import com.picspool.lib.filter.gpu.normal.GPUImageMonochromeFilter;
import com.picspool.lib.filter.gpu.normal.GPUImageOpacityFilter;
import com.picspool.lib.filter.gpu.normal.GPUImagePixelationFilter;
import com.picspool.lib.filter.gpu.normal.GPUImagePosterizeFilter;
import com.picspool.lib.filter.gpu.normal.GPUImageSepiaFilter;
import com.picspool.lib.filter.gpu.normal.GPUImageSobelEdgeDetection;
import com.picspool.lib.filter.gpu.normal.GPUImageToneCurveFilter;
import com.picspool.lib.filter.gpu.vignette.GPUImageVignetteBlendFilter;
import com.picspool.lib.filter.gpu.vignette.GPUImageVignetteColorFilter;
import com.picspool.lib.filter.gpu.vignette.GPUImageVignetteFilter;

/* loaded from: classes3.dex */
public class GPUImageFilterTools {

    /* loaded from: classes3.dex */
    private enum FilterType {
        CONTRAST,
        GRAYSCALE,
        SHARPEN,
        SEPIA,
        SOBEL_EDGE_DETECTION,
        THREE_X_THREE_CONVOLUTION,
        FILTER_GROUP,
        EMBOSS,
        POSTERIZE,
        GAMMA,
        BRIGHTNESS,
        INVERT,
        HUE,
        PIXELATION,
        SATURATION,
        EXPOSURE,
        HIGHLIGHT_SHADOW,
        MONOCHROME,
        OPACITY,
        RGB,
        WHITE_BALANCE,
        VIGNETTE,
        TONE_CURVE,
        BLEND_COLOR_BURN,
        BLEND_COLOR_DODGE,
        BLEND_DARKEN,
        BLEND_DIFFERENCE,
        BLEND_DISSOLVE,
        BLEND_EXCLUSION,
        BLEND_SOURCE_OVER,
        BLEND_HARD_LIGHT,
        BLEND_LIGHTEN,
        BLEND_ADD,
        BLEND_DIVIDE,
        BLEND_MULTIPLY,
        BLEND_OVERLAY,
        BLEND_SCREEN,
        BLEND_ALPHA,
        BLEND_COLOR,
        BLEND_HUE,
        BLEND_SATURATION,
        BLEND_LUMINOSITY,
        BLEND_LINEAR_BURN,
        BLEND_SOFT_LIGHT,
        BLEND_SUBTRACT,
        BLEND_CHROMA_KEY,
        BLEND_NORMAL,
        LOOKUP_AMATORKA,
        LOOKUP_BLEACH,
        LOOKUP_FRESHEN,
        LOOKUP_HOLLYWOOD,
        LOOKUP_JESTER,
        LOOKUP_LEGACY,
        LOOKUP_SEPIA
    }

    /* loaded from: classes3.dex */
    public interface OnGpuImageFilterChosenListener {
        void onGpuImageFilterChosenListener(GPUImageFilter gPUImageFilter);
    }

    /* renamed from: com.picspool.lib.filter.gpu.GPUImageFilterTools$1 */
    /* loaded from: classes3.dex */
    static /* synthetic */ class C31001 {

        /* renamed from: $SwitchMap$org$picspool$lib$filter$gpu$GPUImageFilterTools$FilterType */
        static final /* synthetic */ int[] f1982x2d73be48;

        static {
            int[] iArr = new int[FilterType.values().length];
            f1982x2d73be48 = iArr;
            try {
                iArr[FilterType.CONTRAST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1982x2d73be48[FilterType.GAMMA.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f1982x2d73be48[FilterType.INVERT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f1982x2d73be48[FilterType.PIXELATION.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f1982x2d73be48[FilterType.HUE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f1982x2d73be48[FilterType.BRIGHTNESS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f1982x2d73be48[FilterType.SEPIA.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f1982x2d73be48[FilterType.SHARPEN.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f1982x2d73be48[FilterType.SOBEL_EDGE_DETECTION.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f1982x2d73be48[FilterType.THREE_X_THREE_CONVOLUTION.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f1982x2d73be48[FilterType.EMBOSS.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f1982x2d73be48[FilterType.POSTERIZE.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f1982x2d73be48[FilterType.FILTER_GROUP.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f1982x2d73be48[FilterType.SATURATION.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f1982x2d73be48[FilterType.EXPOSURE.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                f1982x2d73be48[FilterType.HIGHLIGHT_SHADOW.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                f1982x2d73be48[FilterType.MONOCHROME.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                f1982x2d73be48[FilterType.OPACITY.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                f1982x2d73be48[FilterType.RGB.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                f1982x2d73be48[FilterType.WHITE_BALANCE.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                f1982x2d73be48[FilterType.VIGNETTE.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                f1982x2d73be48[FilterType.TONE_CURVE.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                f1982x2d73be48[FilterType.BLEND_DIFFERENCE.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                f1982x2d73be48[FilterType.BLEND_SOURCE_OVER.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                f1982x2d73be48[FilterType.BLEND_COLOR_BURN.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                f1982x2d73be48[FilterType.BLEND_COLOR_DODGE.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                f1982x2d73be48[FilterType.BLEND_DARKEN.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                f1982x2d73be48[FilterType.BLEND_DISSOLVE.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                f1982x2d73be48[FilterType.BLEND_EXCLUSION.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                f1982x2d73be48[FilterType.BLEND_HARD_LIGHT.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                f1982x2d73be48[FilterType.BLEND_LIGHTEN.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                f1982x2d73be48[FilterType.BLEND_ADD.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                f1982x2d73be48[FilterType.BLEND_DIVIDE.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                f1982x2d73be48[FilterType.BLEND_MULTIPLY.ordinal()] = 34;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                f1982x2d73be48[FilterType.BLEND_OVERLAY.ordinal()] = 35;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                f1982x2d73be48[FilterType.BLEND_SCREEN.ordinal()] = 36;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                f1982x2d73be48[FilterType.BLEND_COLOR.ordinal()] = 37;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                f1982x2d73be48[FilterType.BLEND_HUE.ordinal()] = 38;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                f1982x2d73be48[FilterType.BLEND_SATURATION.ordinal()] = 39;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                f1982x2d73be48[FilterType.BLEND_LUMINOSITY.ordinal()] = 40;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                f1982x2d73be48[FilterType.BLEND_LINEAR_BURN.ordinal()] = 41;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                f1982x2d73be48[FilterType.BLEND_SOFT_LIGHT.ordinal()] = 42;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                f1982x2d73be48[FilterType.BLEND_SUBTRACT.ordinal()] = 43;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                f1982x2d73be48[FilterType.BLEND_CHROMA_KEY.ordinal()] = 44;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                f1982x2d73be48[FilterType.BLEND_NORMAL.ordinal()] = 45;
            } catch (NoSuchFieldError unused45) {
            }
            try {
                f1982x2d73be48[FilterType.LOOKUP_AMATORKA.ordinal()] = 46;
            } catch (NoSuchFieldError unused46) {
            }
        }
    }

    private static GPUImageFilter createFilterForType(Context context, FilterType filterType) {
        switch (C31001.f1982x2d73be48[filterType.ordinal()]) {
            case 1:
                return new GPUImageContrastFilter(2.0f);
            case 2:
                return new GPUImageGammaFilter(2.0f);
            case 3:
                return new GPUImageColorInvertFilter();
            case 4:
                return new GPUImagePixelationFilter();
            case 5:
                return new GPUImageHueFilter(90.0f);
            case 6:
                return new GPUImageBrightnessFilter(1.5f);
            case 7:
                return new GPUImageSepiaFilter();
            case 8:
                GPUImageSharpenFilter gPUImageSharpenFilter = new GPUImageSharpenFilter();
                gPUImageSharpenFilter.setSharpness(2.0f);
                return gPUImageSharpenFilter;
            case 9:
                return new GPUImageSobelEdgeDetection();
            case 10:
                GPUImage3x3ConvolutionFilter gPUImage3x3ConvolutionFilter = new GPUImage3x3ConvolutionFilter();
                gPUImage3x3ConvolutionFilter.setConvolutionKernel(new float[]{-1.0f, 0.0f, 1.0f, -2.0f, 0.0f, 2.0f, -1.0f, 0.0f, 1.0f});
                return gPUImage3x3ConvolutionFilter;
            case 11:
                return new GPUImageEmbossFilter();
            case 12:
                return new GPUImagePosterizeFilter();
            case 13:
                LinkedList linkedList = new LinkedList();
                new GPUImageToneCurveFilter();
                return new GPUImageFilterGroup(linkedList);
            case 14:
                return new GPUImageSaturationFilter(1.0f);
            case 15:
                return new GPUImageExposureFilter(0.0f);
            case 16:
                return new GPUImageHighlightShadowFilter(0.0f, 1.0f);
            case 17:
                return new GPUImageMonochromeFilter(1.0f, new float[]{0.6f, 0.45f, 0.3f, 1.0f});
            case 18:
                return new GPUImageOpacityFilter(1.0f);
            case 19:
                return new GPUImageRGBFilter(1.0f, 1.0f, 1.0f);
            case 20:
                return new GPUImageWhiteBalanceFilter(5000.0f, 0.0f);
            case 21:
                PointF pointF = new PointF();
                pointF.x = 0.5f;
                pointF.y = 0.5f;
                return new GPUImageVignetteColorFilter(new float[]{0.0f, 0.0f, 0.0f}, pointF, 0.3f, 0.75f);
            case 22:
                return new GPUImageToneCurveFilter();
            case 23:
                return createBlendFilter(context, GPUImageDifferenceBlendFilter.class);
            case 24:
                return createBlendFilter(context, GPUImageSourceOverBlendFilter.class);
            case 25:
                return createBlendFilter(context, GPUImageColorBurnBlendFilter.class);
            case 26:
                return createBlendFilter(context, GPUImageColorDodgeBlendFilter.class);
            case 27:
                return createBlendFilter(context, GPUImageDarkenBlendFilter.class);
            case 28:
                return createBlendFilter(context, GPUImageDissolveBlendFilter.class);
            case 29:
                return createBlendFilter(context, GPUImageExclusionBlendFilter.class);
            case 30:
                return createBlendFilter(context, GPUImageHardLightBlendFilter.class);
            case 31:
                return createBlendFilter(context, GPUImageLightenBlendFilter.class);
            case 32:
                return createBlendFilter(context, GPUImageAddBlendFilter.class);
            case 33:
                return createBlendFilter(context, GPUImageDivideBlendFilter.class);
            case 34:
                return createBlendFilter(context, GPUImageMultiplyBlendFilter.class);
            case 35:
                return createBlendFilter(context, GPUImageOverlayBlendFilter.class);
            case 36:
                return createBlendFilter(context, GPUImageScreenBlendFilter.class);
            case 37:
                return createBlendFilter(context, GPUImageColorBlendFilter.class);
            case 38:
                return createBlendFilter(context, GPUImageHueBlendFilter.class);
            case 39:
                return createBlendFilter(context, GPUImageSaturationBlendFilter.class);
            case 40:
                return createBlendFilter(context, GPUImageLuminosityBlendFilter.class);
            case 41:
                return createBlendFilter(context, GPUImageLinearBurnBlendFilter.class);
            case 42:
                return createBlendFilter(context, GPUImageSoftLightBlendFilter.class);
            case 43:
                return createBlendFilter(context, GPUImageSubtractBlendFilter.class);
            case 44:
                return createBlendFilter(context, GPUImageChromaKeyBlendFilter.class);
            case 45:
                return createBlendFilter(context, GPUImageNormalBlendFilter.class);
            case 46:
                GPUImageLookupFilter gPUImageLookupFilter = new GPUImageLookupFilter();
                gPUImageLookupFilter.setBitmap(null);
                return gPUImageLookupFilter;
            default:
                throw new IllegalStateException("No filter of that type!");
        }
    }

    private static GPUImageFilter createBlendFilter(Context context, Class<? extends GPUImageTwoInputFilter> cls) {
        try {
            GPUImageTwoInputFilter newInstance = cls.newInstance();
            newInstance.setBitmap(null);
            return newInstance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* loaded from: classes3.dex */
    private static class FilterList {
        public List<String> names = new LinkedList();
        public List<FilterType> filters = new LinkedList();

        private FilterList() {
        }

        public void addFilter(String str, FilterType filterType) {
            this.names.add(str);
            this.filters.add(filterType);
        }
    }

    /* loaded from: classes3.dex */
    public static class FilterAdjuster {
        private final Adjuster<? extends GPUImageFilter> adjuster;

        public FilterAdjuster(GPUImageFilter gPUImageFilter) {
            if (gPUImageFilter instanceof GPUImageSharpenFilter) {
                this.adjuster = new SharpnessAdjuster(this, null).filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageSepiaFilter) {
                this.adjuster = new SepiaAdjuster(this, null).filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageContrastFilter) {
                this.adjuster = new ContrastAdjuster(this, null).filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageGammaFilter) {
                this.adjuster = new GammaAdjuster(this, null).filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageBrightnessFilter) {
                this.adjuster = new BrightnessAdjuster(this, null).filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageSobelEdgeDetection) {
                this.adjuster = new SobelAdjuster(this, null).filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImage3x3TextureSamplingFilter) {
                this.adjuster = new GPU3x3TextureAdjuster(this, null).filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageEmbossFilter) {
                this.adjuster = new EmbossAdjuster(this, null).filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageHueFilter) {
                this.adjuster = new HueAdjuster(this, null).filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImagePosterizeFilter) {
                this.adjuster = new PosterizeAdjuster(this, null).filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImagePixelationFilter) {
                this.adjuster = new PixelationAdjuster(this, null).filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageSaturationFilter) {
                this.adjuster = new SaturationAdjuster(this, null).filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageExposureFilter) {
                this.adjuster = new ExposureAdjuster(this, null).filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageHighlightShadowFilter) {
                this.adjuster = new HighlightShadowAdjuster(this, null).filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageMonochromeFilter) {
                this.adjuster = new MonochromeAdjuster(this, null).filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageOpacityFilter) {
                this.adjuster = new OpacityAdjuster(this, null).filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageRGBFilter) {
                this.adjuster = new RGBAdjuster(this, null).filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageWhiteBalanceFilter) {
                this.adjuster = new WhiteBalanceAdjuster(this, null).filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageVignetteFilter) {
                this.adjuster = new VignetteAdjuster(this, null).filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageDissolveBlendFilter) {
                this.adjuster = new DissolveBlendAdjuster(this, null).filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageToneCurveFilter) {
                this.adjuster = new CurveAdjuster(this, null).filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageScreenBlendFilter) {
                this.adjuster = new ScreenBlendAdjuster(this, null).filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageMultiplyBlendFilter) {
                this.adjuster = new MultiplyBlendAdjuster(this, null).filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageVignetteBlendFilter) {
                this.adjuster = new VignetteBlendAdjuster(this, null).filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageVibranceFilter) {
                this.adjuster = new VibranceAdjuster(this, null).filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageLookupFilter) {
                this.adjuster = new LookupAdjuster(this, null).filter(gPUImageFilter);
            } else {
                this.adjuster = null;
            }
        }

        public void adjust(int i) {
            Adjuster<? extends GPUImageFilter> adjuster = this.adjuster;
            if (adjuster != null) {
                adjuster.adjust(i);
            }
        }

        /* loaded from: classes3.dex */
        private abstract class Adjuster<T extends GPUImageFilter> {
            private T filter;

            public abstract void adjust(int i);

            protected float range(int i, float f, float f2) {
                return (((f2 - f) * i) / 100.0f) + f;
            }

            private Adjuster() {
            }

             Adjuster(FilterAdjuster filterAdjuster, C31001 c31001) {
                this();
            }

            /* JADX WARN: Multi-variable type inference failed */
            public Adjuster<T> filter(GPUImageFilter gPUImageFilter) {
                this.filter = (T) gPUImageFilter;
                return this;
            }

            public T getFilter() {
                return this.filter;
            }

            protected int range(int i, int i2, int i3) {
                return (((i3 - i2) * i) / 100) + i2;
            }
        }

        /* loaded from: classes3.dex */
        private class SharpnessAdjuster extends Adjuster<GPUImageSharpenFilter> {
            private SharpnessAdjuster() {
                super(FilterAdjuster.this, null);
            }

            /* synthetic */ SharpnessAdjuster(FilterAdjuster filterAdjuster, C31001 c31001) {
                this();
            }

            @Override // com.picspool.lib.filter.gpu.GPUImageFilterTools.FilterAdjuster.Adjuster
            public void adjust(int i) {
                getFilter().setSharpness(range(i, -4.0f, 4.0f));
            }
        }

        /* loaded from: classes3.dex */
        private class PixelationAdjuster extends Adjuster<GPUImagePixelationFilter> {
            private PixelationAdjuster() {
                super(FilterAdjuster.this, null);
            }

            /* synthetic */ PixelationAdjuster(FilterAdjuster filterAdjuster, C31001 c31001) {
                this();
            }

            @Override // com.picspool.lib.filter.gpu.GPUImageFilterTools.FilterAdjuster.Adjuster
            public void adjust(int i) {
                getFilter().setPixel(range(i, 1.0f, 100.0f));
            }
        }

        /* loaded from: classes3.dex */
        private class HueAdjuster extends Adjuster<GPUImageHueFilter> {
            private HueAdjuster() {
                super(FilterAdjuster.this, null);
            }

            /* synthetic */ HueAdjuster(FilterAdjuster filterAdjuster, C31001 c31001) {
                this();
            }

            @Override // com.picspool.lib.filter.gpu.GPUImageFilterTools.FilterAdjuster.Adjuster
            public void adjust(int i) {
                getFilter().setHue(range(i, 0.0f, 360.0f));
            }
        }

        /* loaded from: classes3.dex */
        private class ContrastAdjuster extends Adjuster<GPUImageContrastFilter> {
            private ContrastAdjuster() {
                super(FilterAdjuster.this, null);
            }

            /* synthetic */ ContrastAdjuster(FilterAdjuster filterAdjuster, C31001 c31001) {
                this();
            }

            @Override // com.picspool.lib.filter.gpu.GPUImageFilterTools.FilterAdjuster.Adjuster
            public void adjust(int i) {
                getFilter().setContrast(range(i, 0.0f, 2.0f));
            }
        }

        /* loaded from: classes3.dex */
        private class GammaAdjuster extends Adjuster<GPUImageGammaFilter> {
            private GammaAdjuster() {
                super(FilterAdjuster.this, null);
            }

          GammaAdjuster(FilterAdjuster filterAdjuster, C31001 c31001) {
                this();
            }

            @Override // com.picspool.lib.filter.gpu.GPUImageFilterTools.FilterAdjuster.Adjuster
            public void adjust(int i) {
                getFilter().setGamma(range(i, 0.0f, 3.0f));
            }
        }

        /* loaded from: classes3.dex */
        private class BrightnessAdjuster extends Adjuster<GPUImageBrightnessFilter> {
            private BrightnessAdjuster() {
                super(FilterAdjuster.this, null);
            }

            /* synthetic */ BrightnessAdjuster(FilterAdjuster filterAdjuster, C31001 c31001) {
                this();
            }

            @Override // com.picspool.lib.filter.gpu.GPUImageFilterTools.FilterAdjuster.Adjuster
            public void adjust(int i) {
                getFilter().setBrightness(range(i, -1.0f, 1.0f));
            }
        }

        /* loaded from: classes3.dex */
        private class SepiaAdjuster extends Adjuster<GPUImageSepiaFilter> {
            private SepiaAdjuster() {
                super(FilterAdjuster.this, null);
            }

            /* synthetic */ SepiaAdjuster(FilterAdjuster filterAdjuster, C31001 c31001) {
                this();
            }

            @Override // com.picspool.lib.filter.gpu.GPUImageFilterTools.FilterAdjuster.Adjuster
            public void adjust(int i) {
                getFilter().setIntensity(range(i, 0.0f, 2.0f));
            }
        }

        /* loaded from: classes3.dex */
        private class SobelAdjuster extends Adjuster<GPUImageSobelEdgeDetection> {
            private SobelAdjuster() {
                super(FilterAdjuster.this, null);
            }

            /* synthetic */ SobelAdjuster(FilterAdjuster filterAdjuster, C31001 c31001) {
                this();
            }

            @Override // com.picspool.lib.filter.gpu.GPUImageFilterTools.FilterAdjuster.Adjuster
            public void adjust(int i) {
                getFilter().setLineSize(range(i, 0.0f, 5.0f));
            }
        }

        /* loaded from: classes3.dex */
        private class EmbossAdjuster extends Adjuster<GPUImageEmbossFilter> {
            private EmbossAdjuster() {
                super(FilterAdjuster.this, null);
            }

            /* synthetic */ EmbossAdjuster(FilterAdjuster filterAdjuster, C31001 c31001) {
                this();
            }

            @Override // com.picspool.lib.filter.gpu.GPUImageFilterTools.FilterAdjuster.Adjuster
            public void adjust(int i) {
                getFilter().setIntensity(range(i, 0.0f, 4.0f));
            }
        }

        /* loaded from: classes3.dex */
        private class PosterizeAdjuster extends Adjuster<GPUImagePosterizeFilter> {
            private PosterizeAdjuster() {
                super(FilterAdjuster.this, null);
            }

            /* synthetic */ PosterizeAdjuster(FilterAdjuster filterAdjuster, C31001 c31001) {
                this();
            }

            @Override // com.picspool.lib.filter.gpu.GPUImageFilterTools.FilterAdjuster.Adjuster
            public void adjust(int i) {
                getFilter().setColorLevels(range(i, 1, 50));
            }
        }

        /* loaded from: classes3.dex */
        private class GPU3x3TextureAdjuster extends Adjuster<GPUImage3x3TextureSamplingFilter> {
            private GPU3x3TextureAdjuster() {
                super(FilterAdjuster.this, null);
            }

            /* synthetic */ GPU3x3TextureAdjuster(FilterAdjuster filterAdjuster, C31001 c31001) {
                this();
            }

            @Override // com.picspool.lib.filter.gpu.GPUImageFilterTools.FilterAdjuster.Adjuster
            public void adjust(int i) {
                getFilter().setLineSize(range(i, 0.0f, 5.0f));
            }
        }

        /* loaded from: classes3.dex */
        private class SaturationAdjuster extends Adjuster<GPUImageSaturationFilter> {
            private SaturationAdjuster() {
                super(FilterAdjuster.this, null);
            }

            /* synthetic */ SaturationAdjuster(FilterAdjuster filterAdjuster, C31001 c31001) {
                this();
            }

            @Override // com.picspool.lib.filter.gpu.GPUImageFilterTools.FilterAdjuster.Adjuster
            public void adjust(int i) {
                getFilter().setSaturation(range(i, 0.0f, 2.0f));
            }
        }

        /* loaded from: classes3.dex */
        private class ExposureAdjuster extends Adjuster<GPUImageExposureFilter> {
            private ExposureAdjuster() {
                super(FilterAdjuster.this, null);
            }

            /* synthetic */ ExposureAdjuster(FilterAdjuster filterAdjuster, C31001 c31001) {
                this();
            }

            @Override // com.picspool.lib.filter.gpu.GPUImageFilterTools.FilterAdjuster.Adjuster
            public void adjust(int i) {
                getFilter().setExposure(range(i, -10.0f, 10.0f));
            }
        }

        /* loaded from: classes3.dex */
        private class HighlightShadowAdjuster extends Adjuster<GPUImageHighlightShadowFilter> {
            private HighlightShadowAdjuster() {
                super(FilterAdjuster.this, null);
            }

            /* synthetic */ HighlightShadowAdjuster(FilterAdjuster filterAdjuster, C31001 c31001) {
                this();
            }

            @Override // com.picspool.lib.filter.gpu.GPUImageFilterTools.FilterAdjuster.Adjuster
            public void adjust(int i) {
                getFilter().setHighlights(range(i, 0.0f, 1.0f));
            }
        }

        /* loaded from: classes3.dex */
        private class MonochromeAdjuster extends Adjuster<GPUImageMonochromeFilter> {
            private MonochromeAdjuster() {
                super(FilterAdjuster.this, null);
            }

            /* synthetic */ MonochromeAdjuster(FilterAdjuster filterAdjuster, C31001 c31001) {
                this();
            }

            @Override // com.picspool.lib.filter.gpu.GPUImageFilterTools.FilterAdjuster.Adjuster
            public void adjust(int i) {
                getFilter().setIntensity(range(i, 0.0f, 1.0f));
            }
        }

        /* loaded from: classes3.dex */
        private class OpacityAdjuster extends Adjuster<GPUImageOpacityFilter> {
            private OpacityAdjuster() {
                super(FilterAdjuster.this, null);
            }

            /* synthetic */ OpacityAdjuster(FilterAdjuster filterAdjuster, C31001 c31001) {
                this();
            }

            @Override // com.picspool.lib.filter.gpu.GPUImageFilterTools.FilterAdjuster.Adjuster
            public void adjust(int i) {
                getFilter().setOpacity(range(i, 0.0f, 1.0f));
            }
        }

        /* loaded from: classes3.dex */
        private class RGBAdjuster extends Adjuster<GPUImageRGBFilter> {
            private RGBAdjuster() {
                super(FilterAdjuster.this, null);
            }

            /* synthetic */ RGBAdjuster(FilterAdjuster filterAdjuster, C31001 c31001) {
                this();
            }

            @Override // com.picspool.lib.filter.gpu.GPUImageFilterTools.FilterAdjuster.Adjuster
            public void adjust(int i) {
                getFilter().setRed(range(i, 0.0f, 1.0f));
            }
        }

        /* loaded from: classes3.dex */
        private class WhiteBalanceAdjuster extends Adjuster<GPUImageWhiteBalanceFilter> {
            private WhiteBalanceAdjuster() {
                super(FilterAdjuster.this, null);
            }

            /* synthetic */ WhiteBalanceAdjuster(FilterAdjuster filterAdjuster, C31001 c31001) {
                this();
            }

            @Override // com.picspool.lib.filter.gpu.GPUImageFilterTools.FilterAdjuster.Adjuster
            public void adjust(int i) {
                getFilter().setTemperature(range(i, 2000.0f, 8000.0f));
            }
        }

        /* loaded from: classes3.dex */
        private class VignetteAdjuster extends Adjuster<GPUImageVignetteFilter> {
            private VignetteAdjuster() {
                super(FilterAdjuster.this, null);
            }

            /* synthetic */ VignetteAdjuster(FilterAdjuster filterAdjuster, C31001 c31001) {
                this();
            }

            @Override // com.picspool.lib.filter.gpu.GPUImageFilterTools.FilterAdjuster.Adjuster
            public void adjust(int i) {
                getFilter().setVignetteStart(range(i, 0.0f, 1.0f));
            }
        }

        /* loaded from: classes3.dex */
        private class DissolveBlendAdjuster extends Adjuster<GPUImageDissolveBlendFilter> {
            private DissolveBlendAdjuster() {
                super(FilterAdjuster.this, null);
            }

            /* synthetic */ DissolveBlendAdjuster(FilterAdjuster filterAdjuster, C31001 c31001) {
                this();
            }

            @Override // com.picspool.lib.filter.gpu.GPUImageFilterTools.FilterAdjuster.Adjuster
            public void adjust(int i) {
                getFilter().setMix(range(i, 0.0f, 1.0f));
            }
        }

        /* loaded from: classes3.dex */
        private class CurveAdjuster extends Adjuster<GPUImageToneCurveFilter> {
            private CurveAdjuster() {
                super(FilterAdjuster.this, null);
            }

            /* synthetic */ CurveAdjuster(FilterAdjuster filterAdjuster, C31001 c31001) {
                this();
            }

            @Override // com.picspool.lib.filter.gpu.GPUImageFilterTools.FilterAdjuster.Adjuster
            public void adjust(int i) {
                getFilter().setMix(range(i, 0.0f, 1.0f));
            }
        }

        /* loaded from: classes3.dex */
        private class ScreenBlendAdjuster extends Adjuster<GPUImageScreenBlendFilter> {
            private ScreenBlendAdjuster() {
                super(FilterAdjuster.this, null);
            }

            /* synthetic */ ScreenBlendAdjuster(FilterAdjuster filterAdjuster, C31001 c31001) {
                this();
            }

            @Override // com.picspool.lib.filter.gpu.GPUImageFilterTools.FilterAdjuster.Adjuster
            public void adjust(int i) {
                getFilter().setMix(range(i, 0.0f, 1.0f));
            }
        }

        /* loaded from: classes3.dex */
        private class MultiplyBlendAdjuster extends Adjuster<GPUImageMultiplyBlendFilter> {
            private MultiplyBlendAdjuster() {
                super(FilterAdjuster.this, null);
            }

            /* synthetic */ MultiplyBlendAdjuster(FilterAdjuster filterAdjuster, C31001 c31001) {
                this();
            }

            @Override // com.picspool.lib.filter.gpu.GPUImageFilterTools.FilterAdjuster.Adjuster
            public void adjust(int i) {
                getFilter().setMix(range(i, 0.0f, 1.0f));
            }
        }

        /* loaded from: classes3.dex */
        private class VignetteBlendAdjuster extends Adjuster<GPUImageVignetteBlendFilter> {
            private VignetteBlendAdjuster() {
                super(FilterAdjuster.this, null);
            }

            /* synthetic */ VignetteBlendAdjuster(FilterAdjuster filterAdjuster, C31001 c31001) {
                this();
            }

            @Override // com.picspool.lib.filter.gpu.GPUImageFilterTools.FilterAdjuster.Adjuster
            public void adjust(int i) {
                getFilter().setVignetteStart(1.0f - range(i, 0.0f, 1.0f));
            }
        }

        /* loaded from: classes3.dex */
        private class VibranceAdjuster extends Adjuster<GPUImageVibranceFilter> {
            private VibranceAdjuster() {
                super(FilterAdjuster.this, null);
            }

            /* synthetic */ VibranceAdjuster(FilterAdjuster filterAdjuster, C31001 c31001) {
                this();
            }

            @Override // com.picspool.lib.filter.gpu.GPUImageFilterTools.FilterAdjuster.Adjuster
            public void adjust(int i) {
                getFilter().setSaturation(range(i, 0.3f, 2.0f));
            }
        }

        /* loaded from: classes3.dex */
        private class LookupAdjuster extends Adjuster<GPUImageLookupFilter> {
            private LookupAdjuster() {
                super(FilterAdjuster.this, null);
            }

            /* synthetic */ LookupAdjuster(FilterAdjuster filterAdjuster, C31001 c31001) {
                this();
            }

            @Override // com.picspool.lib.filter.gpu.GPUImageFilterTools.FilterAdjuster.Adjuster
            public void adjust(int i) {
                getFilter().setMix(range(i, 0.0f, 1.0f));
            }
        }
    }
}
