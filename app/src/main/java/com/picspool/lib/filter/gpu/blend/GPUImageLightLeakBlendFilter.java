package com.picspool.lib.filter.gpu.blend;

import android.opengl.GLES20;
import android.opengl.Matrix;
import com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter;

/* loaded from: classes3.dex */
public class GPUImageLightLeakBlendFilter extends GPUImageTwoInputFilter {
    public static final String LightLeak_BLEND_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n varying highp vec2 textureCoordinate2;\n\n uniform sampler2D inputImageTexture;\n uniform sampler2D inputImageTexture2;\n uniform lowp float mixturePercent;\nprecision highp float;\n \nuniform mediump float hueAdjust;\nconst highp vec4 kRGBToYPrime = vec4 (0.299, 0.587, 0.114, 0.0);\nconst highp vec4 kRGBToI = vec4 (0.595716, -0.274453, -0.321263, 0.0);\nconst highp vec4 kRGBToQ = vec4 (0.211456, -0.522591, 0.31135, 0.0);\n\nconst highp vec4 kYIQToR = vec4 (1.0, 0.9563, 0.6210, 0.0);\nconst highp vec4 kYIQToG = vec4 (1.0, -0.2721, -0.6474, 0.0);\nconst highp vec4 kYIQToB = vec4 (1.0, -1.1070, 1.7046, 0.0);\n\n void main()\n {\n     mediump vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     mediump vec4 textureColor2 = texture2D(inputImageTexture2, textureCoordinate2);\n    highp float YPrime = dot (textureColor2, kRGBToYPrime);\n    highp float I = dot (textureColor2, kRGBToI);\n    highp float Q = dot (textureColor2, kRGBToQ);\n\n    // Calculate the hue and chroma\n    highp float hue = atan (Q, I);\n    highp float chroma = sqrt (I * I + Q * Q);\n\n    // Make the user's adjustments\n    hue += (-hueAdjust); //why negative rotation?\n\n    // Convert back to YIQ\n    Q = chroma * sin (hue);\n    I = chroma * cos (hue);\n\n    // Convert back to RGB\nif(textureColor2.r==0.0&&textureColor2.g==0.0&&textureColor2.b==0.0){\n\t  textureColor2.r = 1.0;\n    textureColor2.g = 1.0;\n    textureColor2.b = 1.0;\n}\nelse {\n    highp vec4 yIQ = vec4 (YPrime, I, Q, 0.0);\n    textureColor2.r = dot (yIQ, kYIQToR);\n    textureColor2.g = dot (yIQ, kYIQToG);\n    textureColor2.b = dot (yIQ, kYIQToB);\n}\n     mediump vec4 whiteColor = vec4(1.0);\n     lowp vec4 textureColor3 = whiteColor - ((whiteColor - textureColor2) * (whiteColor - textureColor));\nif(textureColor2.r==1.0&&textureColor2.g==1.0&&textureColor2.b==1.0){\n     gl_FragColor=textureColor;\n}else{\n     gl_FragColor =vec4(mix(textureColor.rgb, textureColor3.rgb, textureColor3.a*mixturePercent), textureColor.a);}\n }";
    private static final String LightLeak_BLEND_VERTEX_SHADER = "attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\nattribute vec4 inputTextureCoordinate2;\n \nuniform mat4 transformMatrix;\nuniform mat4 transformMatrix2;\n\nvarying vec2 textureCoordinate;\nvarying vec2 textureCoordinate2;\n \nvoid main()\n{\n    gl_Position = transformMatrix * vec4(position.xyz, 1.0);\n    textureCoordinate = inputTextureCoordinate.xy;vec4 pos2 = transformMatrix2*vec4(inputTextureCoordinate2.xy,1.0,1.0);    textureCoordinate2 = pos2.xy;\n\n}";
    private float mHue;
    private int mHueLocation;
    protected float mScale;
    protected int mTransformLocation2;

    public GPUImageLightLeakBlendFilter() {
        super(LightLeak_BLEND_VERTEX_SHADER, LightLeak_BLEND_FRAGMENT_SHADER);
        this.mScale = 0.0f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter, com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mHueLocation = GLES20.glGetUniformLocation(getProgram(), "hueAdjust");
        this.mTransformLocation2 = GLES20.glGetUniformLocation(getProgram(), "transformMatrix2");
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter, com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        setHue(this.mHue);
        setLightLeakScale(this.mScale);
    }

    public void setHue(float f) {
        this.mHue = f;
        setFloat(this.mHueLocation, ((f % 360.0f) * 3.1415927f) / 180.0f);
    }

    public void setLightLeakScale(float f) {
        this.mScale = f;
        float[] fArr = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
        Matrix.translateM(fArr, 0, f / 2.0f, f / 2.0f, 0.0f);
        float f2 = this.mScale;
        Matrix.scaleM(fArr, 0, 1.0f - f2, 1.0f - f2, 0.0f);
        setUniformMatrix4f(this.mTransformLocation2, fArr);
    }
}
