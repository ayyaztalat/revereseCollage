package com.picspool.lib.filter.gpu.newfilter;

import android.opengl.GLES20;
import android.util.Log;
import com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter;

/* loaded from: classes3.dex */
public class GPUImageWhiteBlendFilter extends GPUImageTwoInputFilter {
    public static final String BEAUTY_FRAGMENT_SHADER = " precision lowp float;       varying highp vec2 textureCoordinate;       uniform sampler2D inputImageTexture;       varying highp vec2 textureCoordinate2;\n uniform sampler2D inputImageTexture2;\n uniform highp float whitelevel;       uniform lowp float mixturePercent;\n uniform lowp float whitePercent;\n uniform lowp float smoothPercent;\n void main(){         lowp vec4 srcColor = texture2D(inputImageTexture, textureCoordinate);         lowp vec4 blendColor = texture2D(inputImageTexture2, textureCoordinate2);         vec3 smoothColor = vec3(srcColor.rgb);   if(smoothPercent > 0.01){       smoothColor = mix(srcColor.rgb, blendColor.rgb, smoothPercent);   }   if(whitePercent > 0.01){              lowp float a = log(whitelevel);           float r = log(smoothColor.r * (whitelevel - 1.0) + 1.0)/a;            float g = log(smoothColor.g * (whitelevel - 1.0) + 1.0)/a;           float b = log(smoothColor.b * (whitelevel - 1.0) + 1.0)/a;          vec3 whiteColor = mix(smoothColor.rgb, vec3(r,g,b), whitePercent);       gl_FragColor = vec4(mix(srcColor.rgb, whiteColor.rgb, mixturePercent), srcColor.a);         }else{       gl_FragColor = vec4(mix(srcColor.rgb, smoothColor.rgb,mixturePercent), srcColor.a);   } }      ";
    public float mSmoothMix;
    protected int mSmoothMixLocation;
    public float mWhiteMix;
    protected int mWhiteMixLocation;
    private float whitelevel;
    private int whitelevelLocation;

    public GPUImageWhiteBlendFilter() {
        super(BEAUTY_FRAGMENT_SHADER);
        this.whitelevel = 2.0f;
        this.mSmoothMix = 1.0f;
        this.mWhiteMix = 1.0f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter, com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.whitelevelLocation = GLES20.glGetUniformLocation(getProgram(), "whitelevel");
        setWhiteLevel(this.whitelevel);
        this.mSmoothMixLocation = GLES20.glGetUniformLocation(getProgram(), "smoothPercent");
        setSmoothMix(this.mSmoothMix);
        this.mWhiteMixLocation = GLES20.glGetUniformLocation(getProgram(), "whitePercent");
        setWhiteMix(this.mWhiteMix);
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
//        this.mWhiteMix = f;
        setFloat(this.mWhiteMixLocation, f);
    }
}
