package com.picspool.lib.filter.gpu.newfilter;

import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.gpu.father.GPUImageTwoPassTextureSamplingFilter;

/* loaded from: classes3.dex */
public class GPUImageIOSGaussianBlurFilter extends GPUImageTwoPassTextureSamplingFilter {
    protected int mBlurSize;
    private float texelSpacingMultiplier;

    public static GPUImageIOSGaussianBlurFilter getInstance(int i) {
        int i2;
        if (i >= 1) {
            double d = i;
            int floor = (int) Math.floor(Math.sqrt(Math.pow(d, 2.0d) * (-2.0d) * Math.log(0.00390625f * Math.sqrt(Math.pow(d, 2.0d) * 6.283185307179586d))));
            i2 = floor + (floor % 2);
        } else {
            i2 = 0;
        }
        return new GPUImageIOSGaussianBlurFilter(vertexShaderForOptimizedBlurOfRadius(i2, i), fragmentShaderForOptimizedBlurOfRadius(i2, i), i);
    }

    public GPUImageIOSGaussianBlurFilter(String str, String str2, int i) {
        super(str, str2, str, str2);
        this.mBlurSize = 1;
        this.texelSpacingMultiplier = 1.0f;
        this.mBlurSize = i;
    }

    private static String vertexShaderForOptimizedBlurOfRadius(int i, int i2) {
        if (i < 1) {
            return GPUImageFilter.NO_FILTER_VERTEX_SHADER;
        }
        int i3 = i + 1;
        float[] fArr = new float[i3];
        float f = 0.0f;
        for (int i4 = 0; i4 < i3; i4++) {
            double d = i2;
            fArr[i4] = (float) ((1.0d / Math.sqrt(Math.pow(d, 2.0d) * 6.283185307179586d)) * Math.exp((-Math.pow(i4, 2.0d)) / (Math.pow(d, 2.0d) * 2.0d)));
            if (i4 == 0) {
                f += fArr[i4];
            } else {
                f = (float) (f + (fArr[i4] * 2.0d));
            }
        }
        for (int i5 = 0; i5 < i3; i5++) {
            fArr[i5] = fArr[i5] / f;
        }
        int min = Math.min((i / 2) + (i % 2), 7);
        float[] fArr2 = new float[min];
        for (int i6 = 0; i6 < min; i6++) {
            int i7 = i6 * 2;
            int i8 = i7 + 1;
            float f2 = fArr[i8];
            int i9 = i7 + 2;
            float f3 = fArr[i9];
            fArr2[i6] = ((f2 * i8) + (f3 * i9)) / (f2 + f3);
        }
        String str = ("attribute vec4 position;\n attribute vec4 inputTextureCoordinate;\nuniform float texelWidthOffset;\nuniform float texelHeightOffset;\nvarying vec2 blurCoordinates[" + ((min * 2) + 1) + "];\nvoid main()\n{\n    gl_Position = position;\n    vec2 singleStepOffset = vec2(texelWidthOffset, texelHeightOffset);\n") + "blurCoordinates[0] = inputTextureCoordinate.xy;\n";
        for (int i10 = 0; i10 < min; i10++) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            int i11 = i10 * 2;
            sb.append(String.format("blurCoordinates[%d] = inputTextureCoordinate.xy + singleStepOffset * %.5f;\nblurCoordinates[%d] = inputTextureCoordinate.xy - singleStepOffset * %.5f;\n", Integer.valueOf(i11 + 1), Float.valueOf(fArr2[i10]), Integer.valueOf(i11 + 2), Float.valueOf(fArr2[i10])));
            str = sb.toString();
        }
        return str + "}\n";
    }

    private static String fragmentShaderForOptimizedBlurOfRadius(int i, int i2) {
        float f = 0;
        int i3 = 0;
        int i4 = 0;
        if (i < 1) {
            return GPUImageFilter.NO_FILTER_FRAGMENT_SHADER;
        }
        int i5 = i + 1;
        float[] fArr = new float[i5];
        float f2 = 0.0f;
        for (int i6 = 0; i6 < i5; i6++) {
            double d = i2;
            fArr[i6] = (float) ((1.0d / Math.sqrt(Math.pow(d, 2.0d) * 6.283185307179586d)) * Math.exp((-Math.pow(i6, 2.0d)) / (Math.pow(d, 2.0d) * 2.0d)));
            if (i6 == 0) {
                f2 += fArr[i6];
            } else {
                f2 = (float) (f2 + (fArr[i6] * 2.0d));
            }
        }
        for (int i7 = 0; i7 < i5; i7++) {
            fArr[i7] = fArr[i7] / f2;
        }
        int i8 = (i / 2) + (i % 2);
        int min = Math.min(i8, 7);
        String str = ("#ifdef GL_FRAGMENT_PRECISION_HIGH \n   precision highp float;\n#else \n   precision mediump float;\n#endif \nuniform sampler2D inputImageTexture;\nuniform float texelWidthOffset;\nuniform float texelHeightOffset;\nvarying vec2 blurCoordinates[" + ((min * 2) + 1) + "];\nvoid main()\n{\n   lowp vec4 sum = vec4(0.0);\n") + String.format("   sum += texture2D(inputImageTexture, blurCoordinates[0]) * %.5f;\n", Float.valueOf(fArr[0]));
        for (int i9 = 0; i9 < min; i9++) {
            int i10 = i9 * 2;
            float f3 = fArr[i10 + 1] + fArr[i10 + 2];
            str = (str + String.format("   sum += texture2D(inputImageTexture, blurCoordinates[%d]) * %.5f;\n", Integer.valueOf(i3), Float.valueOf(f3))) + String.format("   sum += texture2D(inputImageTexture, blurCoordinates[%d]) * %.5f;\n", Integer.valueOf(i4), Float.valueOf(f3));
        }
        if (i8 > min) {
            str = str + "   vec2 singleStepOffset = vec2(texelWidthOffset, texelHeightOffset);\n";
            while (min < i8) {
                int i11 = min * 2;
                int i12 = i11 + 1;
                float f4 = fArr[i12];
                int i13 = i11 + 2;
                float f5 = fArr[i13];
                float f6 = ((f4 * i12) + (f5 * i13)) / (f4 + f5);
                str = (str + String.format("   sum += texture2D(inputImageTexture, blurCoordinates[0] + singleStepOffset * %.5f) * %.5f;\n", Float.valueOf(f6), Float.valueOf(f))) + String.format("   sum += texture2D(inputImageTexture, blurCoordinates[0] - singleStepOffset * %.5f) * %.5f;\n", Float.valueOf(f6), Float.valueOf(f));
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
        runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.newfilter.GPUImageIOSGaussianBlurFilter.1
            @Override // java.lang.Runnable
            public void run() {
                GPUImageIOSGaussianBlurFilter.this.initTexelOffsets();
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
