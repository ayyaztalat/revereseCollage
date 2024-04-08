package com.picspool.lib.filter.gpu.newfilter;

import android.opengl.GLES20;
import android.util.Log;

import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageSmoothFilter extends GPUImageFilter {
    public static final String BEAUTY_FILTER_VERTEX_SHADER = "attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\nuniform mat4 transformMatrix;\nvarying vec2 textureCoordinate;\nvarying vec2 blurCoordinates[6];\nuniform vec2 singleStepOffset;      void main()\n{\n    gl_Position = transformMatrix * vec4(position.xyz, 1.0);\n   blurCoordinates[0] = inputTextureCoordinate.xy + singleStepOffset * vec2(0.0, -10.0);         blurCoordinates[1] = inputTextureCoordinate.xy + singleStepOffset * vec2(0.0, 10.0);         blurCoordinates[2] = inputTextureCoordinate.xy + singleStepOffset * vec2(-10.0, 0.0);         blurCoordinates[3] = inputTextureCoordinate.xy + singleStepOffset * vec2(10.0, 0.0);         blurCoordinates[4] = inputTextureCoordinate.xy + singleStepOffset * vec2(5.0, -8.0);         blurCoordinates[5] = inputTextureCoordinate.xy + singleStepOffset * vec2(5.0, 8.0);          textureCoordinate = inputTextureCoordinate.xy;\n}";
    public static final String BEAUTY_FILTER_VERTEX_SHADER2 = "attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\nuniform mat4 transformMatrix;\nvarying vec2 textureCoordinate;\nvarying vec2 blurCoordinates[6];\nuniform vec2 singleStepOffset;      void main()\n{\n    gl_Position = transformMatrix * vec4(position.xyz, 1.0);\n    blurCoordinates[0] = inputTextureCoordinate.xy + singleStepOffset * vec2(0, -8.0);\n    blurCoordinates[1] = inputTextureCoordinate.xy + singleStepOffset * vec2(-6.93, 4.0);\n    blurCoordinates[2] = inputTextureCoordinate.xy + singleStepOffset * vec2(6.93, 4.0);\n    blurCoordinates[3] = inputTextureCoordinate.xy + singleStepOffset * vec2(-3.46, -2.0);\n    blurCoordinates[4] = inputTextureCoordinate.xy + singleStepOffset * vec2(3.46, 2.0);\n    blurCoordinates[5] = inputTextureCoordinate.xy + singleStepOffset * vec2(0.0, 4.0);\n    textureCoordinate = inputTextureCoordinate.xy;\n}";
    public static final String BEAUTY_FRAGMENT_SHADER = " precision lowp float;       varying lowp vec2 textureCoordinate;       uniform sampler2D inputImageTexture;       uniform highp vec2 singleStepOffset;       uniform mediump float params;       uniform lowp float mixturePercent;\n uniform lowp float mixturePercent2;\n const highp vec3 W = vec3(0.299,0.587,0.114);      varying vec2 blurCoordinates[6];\n void main(){         vec3 centralColor = texture2D(inputImageTexture, textureCoordinate).rgb;         float sampleColor = centralColor.g * 20.0;         sampleColor += texture2D(inputImageTexture, blurCoordinates[0]).g;         sampleColor += texture2D(inputImageTexture, blurCoordinates[1]).g;         sampleColor += texture2D(inputImageTexture, blurCoordinates[2]).g;         sampleColor += texture2D(inputImageTexture, blurCoordinates[3]).g;         sampleColor += texture2D(inputImageTexture, blurCoordinates[4]).g;         sampleColor += texture2D(inputImageTexture, blurCoordinates[5]).g;         sampleColor += texture2D(inputImageTexture, textureCoordinate.xy + singleStepOffset * vec2(-5.0, 8.0)).g;         sampleColor += texture2D(inputImageTexture, textureCoordinate.xy + singleStepOffset * vec2(-5.0, -8.0)).g;         sampleColor += texture2D(inputImageTexture, textureCoordinate.xy + singleStepOffset * vec2(8.0, -5.0)).g;         sampleColor += texture2D(inputImageTexture, textureCoordinate.xy + singleStepOffset * vec2(8.0, 5.0)).g;         sampleColor += texture2D(inputImageTexture, textureCoordinate.xy + singleStepOffset * vec2(-8.0, 5.0)).g;         sampleColor += texture2D(inputImageTexture, textureCoordinate.xy + singleStepOffset * vec2(-8.0, -5.0)).g;         sampleColor += texture2D(inputImageTexture, textureCoordinate.xy + singleStepOffset * vec2(0.0, -6.0)).g * 2.0;         sampleColor += texture2D(inputImageTexture, textureCoordinate.xy + singleStepOffset * vec2(0.0, 6.0)).g * 2.0;         sampleColor += texture2D(inputImageTexture, textureCoordinate.xy + singleStepOffset * vec2(6.0, 0.0)).g * 2.0;         sampleColor += texture2D(inputImageTexture, textureCoordinate.xy + singleStepOffset * vec2(-6.0, 0.0)).g * 2.0;         sampleColor += texture2D(inputImageTexture, textureCoordinate.xy + singleStepOffset * vec2(-4.0, -4.0)).g * 2.0;         sampleColor += texture2D(inputImageTexture, textureCoordinate.xy + singleStepOffset * vec2(-4.0, 4.0)).g * 2.0;         sampleColor += texture2D(inputImageTexture, textureCoordinate.xy + singleStepOffset * vec2(4.0, -4.0)).g * 2.0;         sampleColor += texture2D(inputImageTexture, textureCoordinate.xy + singleStepOffset * vec2(4.0, 4.0)).g * 2.0;         sampleColor = sampleColor * 0.0208333;         float highPass = centralColor.g - sampleColor + 0.5;         float temp = 2.0*highPass*highPass;   sampleColor = step(highPass,0.500001);   highPass = (1.0 - sampleColor)*(4.0*highPass-temp-1.0)+sampleColor*temp;   temp = 2.0*highPass*highPass;   sampleColor = step(highPass,0.500001);   highPass = (1.0 - sampleColor)*(4.0*highPass-temp-1.0)+sampleColor*temp;   temp = 2.0*highPass*highPass;   sampleColor = step(highPass,0.500001);   highPass = (1.0 - sampleColor)*(4.0*highPass-temp-1.0)+sampleColor*temp;   temp = 2.0*highPass*highPass;   sampleColor = step(highPass,0.500001);   highPass = (1.0 - sampleColor)*(4.0*highPass-temp-1.0)+sampleColor*temp;   float luminance = dot(centralColor, W);         luminance = pow(luminance, params);         vec3 smoothColor = centralColor + (centralColor-vec3(highPass))*luminance*0.1;         vec3 resColor = mix(smoothColor.rgb, max(smoothColor, centralColor), luminance);         gl_FragColor = vec4(mix(centralColor.rgb, resColor.rgb, mixturePercent*mixturePercent2), 1.0);       }      ";
    public static final String BEAUTY_FRAGMENT_SHADER2 = " precision lowp float;       varying lowp vec2 textureCoordinate;       uniform sampler2D inputImageTexture;       uniform mediump float params;       uniform lowp float mixturePercent;\n uniform lowp float mixturePercent2;\n const highp vec3 W = vec3(0.299,0.587,0.114);       varying vec2 blurCoordinates[6];       void main(){         vec3 centralColor = texture2D(inputImageTexture, textureCoordinate).rgb;      float sampleColor = centralColor.g * 6.0;\nsampleColor += texture2D(inputImageTexture, blurCoordinates[0]).g;\nsampleColor += texture2D(inputImageTexture, blurCoordinates[1]).g;\nsampleColor += texture2D(inputImageTexture, blurCoordinates[2]).g;\nsampleColor += texture2D(inputImageTexture, blurCoordinates[3]).g * 2.0;\nsampleColor += texture2D(inputImageTexture, blurCoordinates[4]).g * 2.0;\nsampleColor += texture2D(inputImageTexture, blurCoordinates[5]).g * 2.0;\nsampleColor = sampleColor * 0.0666667;\n   float highPass = centralColor.g - sampleColor + 0.5;         float temp = 2.0*highPass*highPass;   sampleColor = step(highPass,0.500001);   highPass = (1.0 - sampleColor)*(4.0*highPass-temp-1.0)+sampleColor*temp;   temp = 2.0*highPass*highPass;   sampleColor = step(highPass,0.500001);   highPass = (1.0 - sampleColor)*(4.0*highPass-temp-1.0)+sampleColor*temp;   temp = 2.0*highPass*highPass;   sampleColor = step(highPass,0.500001);   highPass = (1.0 - sampleColor)*(4.0*highPass-temp-1.0)+sampleColor*temp;   temp = 2.0*highPass*highPass;   sampleColor = step(highPass,0.500001);   highPass = (1.0 - sampleColor)*(4.0*highPass-temp-1.0)+sampleColor*temp;   float luminance = dot(centralColor, W);         float alpha = pow(luminance, params);         vec3 smoothColor = centralColor + (centralColor-vec3(highPass))*alpha*0.1;         vec3 resColor = mix(smoothColor.rgb, max(smoothColor, centralColor), alpha);         gl_FragColor = vec4(mix(centralColor.rgb, resColor.rgb, mixturePercent*mixturePercent2), 1.0);       }      ";
    public float mMix2;
    protected int mMixLocation2;
    private int mParamsLocation;
    private int mSingleStepOffsetLocation;
    private float params;

    public GPUImageSmoothFilter(float f) {
        super(BEAUTY_FILTER_VERTEX_SHADER2, BEAUTY_FRAGMENT_SHADER2);
        this.params = 0.33f;
        this.mMix2 = 1.0f;
        Log.i("Test", "Use low smooth!!!");
    }

    public GPUImageSmoothFilter(int i) {
        super(BEAUTY_FILTER_VERTEX_SHADER, BEAUTY_FRAGMENT_SHADER);
        this.params = 0.33f;
        this.mMix2 = 1.0f;
        Log.i("Test", "Use Hight smooth!!!");
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mSingleStepOffsetLocation = GLES20.glGetUniformLocation(getProgram(), "singleStepOffset");
        this.mParamsLocation = GLES20.glGetUniformLocation(getProgram(), "params");
        setBeautyPercent(this.params);
        this.mMixLocation2 = GLES20.glGetUniformLocation(getProgram(), "mixturePercent2");
        setMix2(this.mMix2);
    }

    private void setTexelSize(float f, float f2) {
        setFloatVec2(this.mSingleStepOffsetLocation, new float[]{2.0f / (f * 1.0f), 2.0f / (f2 * 1.0f)});
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onOutputSizeChanged(int i, int i2) {
        super.onOutputSizeChanged(i, i2);
        setTexelSize(i, i2);
    }

    public void setBeautyLevel(int i) {
        if (i == 1) {
            setBeautyPercent(1.0f);
        } else if (i == 2) {
            setBeautyPercent(0.8f);
        } else if (i == 3) {
            setBeautyPercent(0.6f);
        } else if (i == 4) {
            setBeautyPercent(0.4f);
        } else if (i != 5) {
        } else {
            setBeautyPercent(0.33f);
        }
    }

    public void setBeautyPercent(float f) {
        this.params = f;
        setFloat(this.mParamsLocation, f);
    }

    public void setMix2(float f) {
       // Log.i(Progress.TAG, "set smooth mix:" + f);
        this.mMix2 = f;
        setFloat(this.mMixLocation2, f);
    }
}
