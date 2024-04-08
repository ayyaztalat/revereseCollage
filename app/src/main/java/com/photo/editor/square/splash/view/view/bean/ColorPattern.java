package com.photo.editor.square.splash.view.view.bean;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.PointF;
import com.picspool.lib.filter.gpu.adjust.GPUImageSharpenFilter;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.gpu.vignette.GPUImageVignetteFilter;
import com.sky.testproject.R;

/* loaded from: classes2.dex */
public class ColorPattern<T> {
    private static final float[] colors = {0.0f, 0.0f, 0.0f};
    private static final PointF pointF = new PointF(0.5f, 0.5f);
    private Action<T> Action;
    private int icon;
    private int oldProgress;
    private int progress;
    private String title;

    public ColorPattern(int i, Action<T> action) {
        this.icon = i;
        this.Action = action;
    }

    public ColorPattern(int i, String str, Action<T> action) {
        this.icon = i;
        this.Action = action;
        this.title = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public int getIcon() {
        return this.icon;
    }

    public void setIcon(int i) {
        this.icon = i;
    }

    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int i) {
        this.progress = i;
    }

    public Action<T> getAction() {
        return this.Action;
    }

    public void setAction(Action<T> action) {
        this.Action = action;
    }

    public int getOldProgress() {
        return this.oldProgress;
    }

    public void setOldProgress(int i) {
        this.oldProgress = i;
    }

    public void resetChange() {
        this.progress = this.oldProgress;
    }

    public void applyChange() {
        this.oldProgress = this.progress;
    }

    /* loaded from: classes2.dex */
    public static class ColorColorPattern extends ColorPattern<ColorMatrix> {
        private int blur;
        private int green;
        private int model;
        private int oldBlur;
        private int oldGreen;
        private int oldRed;
        private int red;

        public ColorColorPattern(int i, Action<ColorMatrix> action) {
            super(i, action);
            this.red = 100;
            this.green = 100;
            this.blur = 100;
            this.oldRed = 100;
            this.oldGreen = 100;
            this.oldBlur = 100;
            setTitle("Color");
        }

        public void setModel(int i) {
            this.model = i;
        }

        public int getModel() {
            return this.model;
        }

        public int getRed() {
            return this.red;
        }

        public int getGreen() {
            return this.green;
        }

        public int getBlur() {
            return this.blur;
        }

        @Override // com.photo.editor.square.splash.view.view.bean.IL22
        public int getProgress() {
            int i = this.model;
            if (i != 0) {
                if (i == 1) {
                    return this.green;
                }
                return this.blur;
            }
            return this.red;
        }

        @Override // com.photo.editor.square.splash.view.view.bean.IL22
        public void setOldProgress(int i) {
            int i2 = this.model;
            if (i2 == 0) {
                this.oldRed = i;
            } else if (i2 == 1) {
                this.oldGreen = i;
            } else if (i2 != 2) {
            } else {
                this.oldBlur = i;
            }
        }

        @Override // com.photo.editor.square.splash.view.view.bean.IL22
        public void setProgress(int i) {
            int i2 = this.model;
            if (i2 == 0) {
                this.red = i;
            } else if (i2 == 1) {
                this.green = i;
            } else if (i2 != 2) {
            } else {
                this.blur = i;
            }
        }

        @Override // com.photo.editor.square.splash.view.view.bean.IL22
        public int getOldProgress() {
            int i = this.model;
            if (i != 0) {
                if (i == 1) {
                    return this.oldGreen;
                }
                return this.oldBlur;
            }
            return this.oldRed;
        }

        @Override // com.photo.editor.square.splash.view.view.bean.IL22
        public void resetChange() {
            super.resetChange();
            this.red = this.oldRed;
            this.blur = this.oldBlur;
            this.green = this.oldGreen;
        }

        @Override // com.photo.editor.square.splash.view.view.bean.IL22
        public void applyChange() {
            super.applyChange();
            this.oldBlur = this.blur;
            this.oldGreen = this.green;
            this.oldRed = this.red;
        }
    }

    public static ColorMatrix getBaoHe(int i) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation((i * 0.006f) + 1.0f);
        return colorMatrix;
    }

    public static ColorMatrix getLiangDu(int i) {
        float f = i * 0.4f;
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(new float[]{1.0f, 0.0f, 0.0f, 0.0f, f, 0.0f, 1.0f, 0.0f, 0.0f, f, 0.0f, 0.0f, 1.0f, 0.0f, f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
        return colorMatrix;
    }

    public static ColorMatrix getDuiBiDu(int i) {
        float f = (i * 0.003f) + 1.0f;
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(new float[]{f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
        return colorMatrix;
    }

    public static ColorMatrix getColorFilter(float f, float f2, float f3) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setScale(f, f2, f3, 1.0f);
        return colorMatrix;
    }

    public static ColorMatrix getGaoLiang(int i) {
        float f = (i * 0.002f) + 1.0f;
        return new ColorMatrix(new float[]{f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
    }

    public static ColorMatrix getSeWen(int i, Context context) {
        int i2 = (((-i) * 6) / 10) + 100;
        int[] intArray = context.getResources().getIntArray(R.array.red);
        int[] intArray2 = context.getResources().getIntArray(R.array.green);
        int[] intArray3 = context.getResources().getIntArray(R.array.blue);
        int i3 = intArray[i2];
        int i4 = intArray2[i2];
        int i5 = intArray3[i2];
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(new float[]{i3 / 255.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, i4 / 255.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, i5 / 255.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
        return colorMatrix;
    }

    public static GPUImageFilter getYunYing(int i) {
        float f = 0.68f - ((i / 1000.0f) * 4.5f);
        GPUImageVignetteFilter gPUImageVignetteFilter = new GPUImageVignetteFilter();
        gPUImageVignetteFilter.setVignetteCenter(pointF);
        gPUImageVignetteFilter.setVignetteColor(colors);
        gPUImageVignetteFilter.setVignetteStart(f);
        gPUImageVignetteFilter.setVignetteEnd(0.8f);
        return gPUImageVignetteFilter;
    }

    public static GPUImageFilter getRuiHua(int i) {
        return new GPUImageSharpenFilter(i / 200.0f);
    }
}
