package com.picspool.lib.filter.gpu.newfilter;

import android.opengl.GLES20;
import android.util.Log;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageWhiteFilter extends GPUImageFilter {
    public static final String BEAUTY_FRAGMENT_SHADER = " precision lowp float;       varying lowp vec2 textureCoordinate;       uniform sampler2D inputImageTexture;       uniform highp float whitelevel;       uniform lowp float mixturePercent;\n uniform lowp float mixturePercent2;\n void main(){         lowp vec4 srcColor = texture2D(inputImageTexture, textureCoordinate);         lowp float a = log(whitelevel);       float r = log(srcColor.r * (whitelevel - 1.0) + 1.0)/a;        float g = log(srcColor.g * (whitelevel - 1.0) + 1.0)/a;       float b = log(srcColor.b * (whitelevel - 1.0) + 1.0)/a;       gl_FragColor = vec4(mix(srcColor.rgb, vec3(r,g,b), mixturePercent*mixturePercent2), srcColor.a);       }      ";
    public float mMix2;
    protected int mMixLocation2;
    private float whitelevel;
    private int whitelevelLocation;

    public GPUImageWhiteFilter() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, BEAUTY_FRAGMENT_SHADER);
        this.whitelevel = 2.0f;
        this.mMix2 = 1.0f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.whitelevelLocation = GLES20.glGetUniformLocation(getProgram(), "whitelevel");
        setWhiteLevel(this.whitelevel);
        this.mMixLocation2 = GLES20.glGetUniformLocation(getProgram(), "mixturePercent2");
        setMix2(this.mMix2);
    }

    public void setWhiteLevel(float f) {
        this.whitelevel = f;
        setFloat(this.whitelevelLocation, f);
    }

    public void setMix2(float f) {
//        Log.i(Progress.TAG, "set white mix:" + f);
        this.mMix2 = f;
        setFloat(this.mMixLocation2, f);
    }
}
