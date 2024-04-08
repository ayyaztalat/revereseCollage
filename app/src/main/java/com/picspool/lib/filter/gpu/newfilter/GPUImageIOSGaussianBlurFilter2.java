package com.picspool.lib.filter.gpu.newfilter;

import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.gpu.father.GPUImageTwoPassTextureSamplingFilter;

/* loaded from: classes3.dex */
public class GPUImageIOSGaussianBlurFilter2 extends GPUImageTwoPassTextureSamplingFilter {
    protected int mBlurSize;
    private float texelSpacingMultiplier;

    public static GPUImageIOSGaussianBlurFilter2 getInstance(int i) {
        int i2;
        if (i >= 1) {
            double d = i;
            int floor = (int) Math.floor(Math.sqrt(Math.pow(d, 2.0d) * (-2.0d) * Math.log(0.00390625f * Math.sqrt(Math.pow(d, 2.0d) * 6.283185307179586d))));
            i2 = floor + (floor % 2);
        } else {
            i2 = 0;
        }
        return new GPUImageIOSGaussianBlurFilter2(GPUImageFilter.NO_FILTER_VERTEX_SHADER, fragmentShaderForOptimizedBlurOfRadius(i2, i), i);
    }

    public GPUImageIOSGaussianBlurFilter2(String str, String str2, int i) {
        super(str, str2, str, str2);
        this.mBlurSize = 1;
        this.texelSpacingMultiplier = 1.0f;
        this.mBlurSize = i;
    }

    private static String fragmentShaderForOptimizedBlurOfRadius(int i, int i2) {
        float f = 0;
        if (i < 1) {
            return GPUImageFilter.NO_FILTER_FRAGMENT_SHADER;
        }
        int i3 = i + 1;
        float[] fArr = new float[i3];
        float f2 = 0.0f;
        for (int i4 = 0; i4 < i3; i4++) {
            double d = i2;
            fArr[i4] = (float) ((1.0d / Math.sqrt(Math.pow(d, 2.0d) * 6.283185307179586d)) * Math.exp((-Math.pow(i4, 2.0d)) / (Math.pow(d, 2.0d) * 2.0d)));
            if (i4 == 0) {
                f2 += fArr[i4];
            } else {
                f2 = (float) (f2 + (fArr[i4] * 2.0d));
            }
        }
        for (int i5 = 0; i5 < i3; i5++) {
            fArr[i5] = fArr[i5] / f2;
        }
        int i6 = (i / 2) + (i % 2);
        int min = Math.min(i6, 7);
        String str = "#ifdef GL_FRAGMENT_PRECISION_HIGH \n   precision highp float;\n#else \n   precision mediump float;\n#endif \nuniform sampler2D inputImageTexture;\nvarying vec2 textureCoordinate;\nuniform float texelWidthOffset;\nuniform float texelHeightOffset;\nvoid main()\n{\n   lowp vec4 sum = vec4(0.0);\n   vec2 singleStepOffset = vec2(texelWidthOffset, texelHeightOffset);\n" + String.format("   sum += texture2D(inputImageTexture, textureCoordinate.xy) * %.5f;\n", Float.valueOf(fArr[0]));
        float[] fArr2 = new float[min];
        for (int i7 = 0; i7 < min; i7++) {
            int i8 = i7 * 2;
            int i9 = i8 + 1;
            float f3 = fArr[i9];
            int i10 = i8 + 2;
            float f4 = fArr[i10];
            fArr2[i7] = ((f3 * i9) + (f4 * i10)) / (f3 + f4);
        }
        for (int i11 = 0; i11 < min; i11++) {
            int i12 = i11 * 2;
            float f5 = fArr[i12 + 1] + fArr[i12 + 2];
            str = (str + String.format("   sum += texture2D(inputImageTexture, textureCoordinate.xy + singleStepOffset * %.5f) * %.5f;\n", Float.valueOf(fArr2[i11]), Float.valueOf(f5))) + String.format("   sum += texture2D(inputImageTexture, textureCoordinate.xy - singleStepOffset * %.5f) * %.5f;\n", Float.valueOf(fArr2[i11]), Float.valueOf(f5));
        }
        if (i6 > min) {
            while (min < i6) {
                int i13 = min * 2;
                int i14 = i13 + 1;
                float f6 = fArr[i14];
                int i15 = i13 + 2;
                float f7 = fArr[i15];
                float f8 = ((f6 * i14) + (f7 * i15)) / (f6 + f7);
                str = (str + String.format("   sum += texture2D(inputImageTexture, textureCoordinate.xy + singleStepOffset * %.5f) * %.5f;\n", Float.valueOf(f8), Float.valueOf(f))) + String.format("   sum += texture2D(inputImageTexture, textureCoordinate.xy - singleStepOffset * %.5f) * %.5f;\n", Float.valueOf(f8), Float.valueOf(f));
                min++;
            }
        }
        return str + "   gl_FragColor = sum;\n}\n";
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageTwoPassTextureSamplingFilter, com.picspool.lib.filter.gpu.father.GPUImageFilterGroup, com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        initTexelOffsets();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.picspool.lib.filter.gpu.father.GPUImageTwoPassTextureSamplingFilter
    public void initTexelOffsets() {
        float horizontalTexelOffsetRatio = getHorizontalTexelOffsetRatio();
        GPUImageFilter gPUImageFilter = this.mFilters.get(0);
        int glGetUniformLocation = GLES20.glGetUniformLocation(gPUImageFilter.getProgram(), "texelWidthOffset");
        int glGetUniformLocation2 = GLES20.glGetUniformLocation(gPUImageFilter.getProgram(), "texelHeightOffset");
        gPUImageFilter.setFloat(glGetUniformLocation, 0.0f);
        gPUImageFilter.setFloat(glGetUniformLocation2, horizontalTexelOffsetRatio / this.mOutputHeight);
        float verticalTexelOffsetRatio = getVerticalTexelOffsetRatio();
        GPUImageFilter gPUImageFilter2 = this.mFilters.get(1);
        int glGetUniformLocation3 = GLES20.glGetUniformLocation(gPUImageFilter2.getProgram(), "texelWidthOffset");
        int glGetUniformLocation4 = GLES20.glGetUniformLocation(gPUImageFilter2.getProgram(), "texelHeightOffset");
        gPUImageFilter2.setFloat(glGetUniformLocation3, verticalTexelOffsetRatio / this.mOutputWidth);
        gPUImageFilter2.setFloat(glGetUniformLocation4, 0.0f);
    }

    public void setTexelSpacingMultiplier(float f) {
        this.texelSpacingMultiplier = f;
        runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.newfilter.GPUImageIOSGaussianBlurFilter2.1
            @Override // java.lang.Runnable
            public void run() {
                GPUImageIOSGaussianBlurFilter2.this.initTexelOffsets();
            }
        });
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageTwoPassTextureSamplingFilter, com.picspool.lib.filter.gpu.father.GPUImageFilterGroup, com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onOutputSizeChanged(int i, int i2) {
        super.onOutputSizeChanged(i, i2);
        initTexelOffsets();
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageTwoPassTextureSamplingFilter
    public float getVerticalTexelOffsetRatio() {
        return this.texelSpacingMultiplier;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageTwoPassTextureSamplingFilter
    public float getHorizontalTexelOffsetRatio() {
        return this.texelSpacingMultiplier;
    }
}
