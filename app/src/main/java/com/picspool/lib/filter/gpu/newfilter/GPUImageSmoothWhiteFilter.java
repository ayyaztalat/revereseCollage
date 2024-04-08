package com.picspool.lib.filter.gpu.newfilter;

import android.opengl.GLES20;
import android.util.Log;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageSmoothWhiteFilter extends GPUImageFilter {
    public static final String BEAUTY_FRAGMENT_SHADER = " precision lowp float;       varying lowp vec2 textureCoordinate;       uniform sampler2D inputImageTexture;       uniform vec2 singleStepOffset;       uniform mediump float params;       uniform lowp float mixturePercent;     uniform lowp float whitePercent;       uniform lowp float smoothPercent;      uniform highp float whitelevel;       const highp vec3 W = vec3(0.299,0.587,0.114);       vec2 blurCoordinates[20];       float hardLight(float color)       {         if(color <= 0.5){             color = color * color * 2.0;         }else{             color = 1.0 - ((1.0 - color)*(1.0 - color) * 2.0);         }         return color;       }       void main(){         vec4 centralColor = texture2D(inputImageTexture, textureCoordinate);         vec3 smoothColor = vec3(centralColor.rgb);         if(smoothPercent > 0.01){       blurCoordinates[0] = textureCoordinate.xy + singleStepOffset * vec2(0.0, -10.0);             blurCoordinates[1] = textureCoordinate.xy + singleStepOffset * vec2(0.0, 10.0);             blurCoordinates[2] = textureCoordinate.xy + singleStepOffset * vec2(-10.0, 0.0);             blurCoordinates[3] = textureCoordinate.xy + singleStepOffset * vec2(10.0, 0.0);             blurCoordinates[4] = textureCoordinate.xy + singleStepOffset * vec2(5.0, -8.0);             blurCoordinates[5] = textureCoordinate.xy + singleStepOffset * vec2(5.0, 8.0);             blurCoordinates[6] = textureCoordinate.xy + singleStepOffset * vec2(-5.0, 8.0);             blurCoordinates[7] = textureCoordinate.xy + singleStepOffset * vec2(-5.0, -8.0);             blurCoordinates[8] = textureCoordinate.xy + singleStepOffset * vec2(8.0, -5.0);             blurCoordinates[9] = textureCoordinate.xy + singleStepOffset * vec2(8.0, 5.0);             blurCoordinates[10] = textureCoordinate.xy + singleStepOffset * vec2(-8.0, 5.0);             blurCoordinates[11] = textureCoordinate.xy + singleStepOffset * vec2(-8.0, -5.0);             blurCoordinates[12] = textureCoordinate.xy + singleStepOffset * vec2(0.0, -6.0);             blurCoordinates[13] = textureCoordinate.xy + singleStepOffset * vec2(0.0, 6.0);             blurCoordinates[14] = textureCoordinate.xy + singleStepOffset * vec2(6.0, 0.0);             blurCoordinates[15] = textureCoordinate.xy + singleStepOffset * vec2(-6.0, 0.0);             blurCoordinates[16] = textureCoordinate.xy + singleStepOffset * vec2(-4.0, -4.0);             blurCoordinates[17] = textureCoordinate.xy + singleStepOffset * vec2(-4.0, 4.0);             blurCoordinates[18] = textureCoordinate.xy + singleStepOffset * vec2(4.0, -4.0);             blurCoordinates[19] = textureCoordinate.xy + singleStepOffset * vec2(4.0, 4.0);             float sampleColor = centralColor.g * 20.0;             sampleColor += texture2D(inputImageTexture, blurCoordinates[0]).g;             sampleColor += texture2D(inputImageTexture, blurCoordinates[1]).g;             sampleColor += texture2D(inputImageTexture, blurCoordinates[2]).g;             sampleColor += texture2D(inputImageTexture, blurCoordinates[3]).g;             sampleColor += texture2D(inputImageTexture, blurCoordinates[4]).g;             sampleColor += texture2D(inputImageTexture, blurCoordinates[5]).g;             sampleColor += texture2D(inputImageTexture, blurCoordinates[6]).g;             sampleColor += texture2D(inputImageTexture, blurCoordinates[7]).g;             sampleColor += texture2D(inputImageTexture, blurCoordinates[8]).g;             sampleColor += texture2D(inputImageTexture, blurCoordinates[9]).g;             sampleColor += texture2D(inputImageTexture, blurCoordinates[10]).g;             sampleColor += texture2D(inputImageTexture, blurCoordinates[11]).g;             sampleColor += texture2D(inputImageTexture, blurCoordinates[12]).g * 2.0;             sampleColor += texture2D(inputImageTexture, blurCoordinates[13]).g * 2.0;             sampleColor += texture2D(inputImageTexture, blurCoordinates[14]).g * 2.0;             sampleColor += texture2D(inputImageTexture, blurCoordinates[15]).g * 2.0;             sampleColor += texture2D(inputImageTexture, blurCoordinates[16]).g * 2.0;             sampleColor += texture2D(inputImageTexture, blurCoordinates[17]).g * 2.0;             sampleColor += texture2D(inputImageTexture, blurCoordinates[18]).g * 2.0;             sampleColor += texture2D(inputImageTexture, blurCoordinates[19]).g * 2.0;             sampleColor = sampleColor / 48.0;             float highPass = centralColor.g - sampleColor + 0.5;             for(int i = 0; i < 5;i++)             {                 highPass = hardLight(highPass);             }             float luminance = dot(centralColor.rgb, W);             float alpha = pow(luminance, params);             smoothColor = centralColor.rgb + (centralColor.rgb-vec3(highPass))*alpha*0.1;             vec3 resColor = mix(smoothColor.rgb, max(smoothColor, centralColor.rgb), alpha);             smoothColor = mix(centralColor.rgb, resColor.rgb, smoothPercent);         }         if(whitePercent > 0.01){       lowp float tp = log(whitelevel);           float r = log(smoothColor.r * (whitelevel - 1.0) + 1.0)/tp;            float g = log(smoothColor.g * (whitelevel - 1.0) + 1.0)/tp;           float b = log(smoothColor.b * (whitelevel - 1.0) + 1.0)/tp;           vec3 whiteColor = mix(smoothColor.rgb, vec3(r,g,b), whitePercent);           gl_FragColor = vec4(mix(centralColor.rgb, whiteColor.rgb, mixturePercent), centralColor.a);         }else{       gl_FragColor = vec4(mix(centralColor.rgb, smoothColor.rgb, mixturePercent), centralColor.a);         }       }      ";
    private int mParamsLocation;
    private int mSingleStepOffsetLocation;
    public float mSmoothMix;
    protected int mSmoothMixLocation;
    public float mWhiteMix;
    protected int mWhiteMixLocation;
    private float params;
    private float whitelevel;
    private int whitelevelLocation;

    public GPUImageSmoothWhiteFilter() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, BEAUTY_FRAGMENT_SHADER);
        this.params = 0.33f;
        this.mSmoothMix = 1.0f;
        this.mWhiteMix = 1.0f;
        this.whitelevel = 2.0f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mSingleStepOffsetLocation = GLES20.glGetUniformLocation(getProgram(), "singleStepOffset");
        this.mParamsLocation = GLES20.glGetUniformLocation(getProgram(), "params");
        setBeautyPercent(this.params);
        this.whitelevelLocation = GLES20.glGetUniformLocation(getProgram(), "whitelevel");
        setWhiteLevel(this.whitelevel);
        this.mSmoothMixLocation = GLES20.glGetUniformLocation(getProgram(), "smoothPercent");
        setSmoothMix(this.mSmoothMix);
        this.mWhiteMixLocation = GLES20.glGetUniformLocation(getProgram(), "whitePercent");
        setWhiteMix(this.mWhiteMix);
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

    public void setWhiteLevel(float f) {
        this.whitelevel = f;
        setFloat(this.whitelevelLocation, f);
    }

    public void setSmoothMix(float f) {
//        Log.i(Progress.TAG, "set smooth mix:" + f);
        this.mSmoothMix = f;
        setFloat(this.mSmoothMixLocation, f);
    }

    public void setWhiteMix(float f) {
//        Log.i(Progress.TAG, "set white mix:" + f);
        this.mWhiteMix = f;
        setFloat(this.mWhiteMixLocation, f);
    }
}
