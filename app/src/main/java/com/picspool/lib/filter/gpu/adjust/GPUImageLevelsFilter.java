package com.picspool.lib.filter.gpu.adjust;

import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageLevelsFilter extends GPUImageFilter {
    public static final String LEVELS_FRAGMET_SHADER = " varying highp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n uniform mediump vec3 levelMinimum;\n uniform mediump vec3 levelMiddle;\n uniform mediump vec3 levelMaximum;\n uniform mediump vec3 minOutput;\n uniform mediump vec3 maxOutput;\n \n void main()\n {\n     mediump vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     \n     gl_FragColor = vec4( mix(minOutput, maxOutput, pow(min(max(textureColor.rgb -levelMinimum, vec3(0.0)) / (levelMaximum - levelMinimum  ), vec3(1.0)), 1.0 /levelMiddle)) , textureColor.a);\n }\n";
    private static final String LOGTAG = GPUImageLevelsFilter.class.getSimpleName();
    private float[] mMax;
    private int mMaxLocation;
    private float[] mMaxOutput;
    private int mMaxOutputLocation;
    private float[] mMid;
    private int mMidLocation;
    private float[] mMin;
    private int mMinLocation;
    private float[] mMinOutput;
    private int mMinOutputLocation;

    public GPUImageLevelsFilter() {
        this(new float[]{0.0f, 0.0f, 0.0f}, new float[]{1.0f, 1.0f, 1.0f}, new float[]{1.0f, 1.0f, 1.0f}, new float[]{0.0f, 0.0f, 0.0f}, new float[]{1.0f, 1.0f, 1.0f});
    }

    private GPUImageLevelsFilter(float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4, float[] fArr5) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, LEVELS_FRAGMET_SHADER);
        this.mMin = fArr;
        this.mMid = fArr2;
        this.mMax = fArr3;
        this.mMinOutput = fArr4;
        this.mMaxOutput = fArr5;
        setMin(0.0f, 1.0f, 1.0f, 0.0f, 1.0f);
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mMinLocation = GLES20.glGetUniformLocation(getProgram(), "levelMinimum");
        this.mMidLocation = GLES20.glGetUniformLocation(getProgram(), "levelMiddle");
        this.mMaxLocation = GLES20.glGetUniformLocation(getProgram(), "levelMaximum");
        this.mMinOutputLocation = GLES20.glGetUniformLocation(getProgram(), "minOutput");
        this.mMaxOutputLocation = GLES20.glGetUniformLocation(getProgram(), "maxOutput");
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        updateUniforms();
    }

    public void updateUniforms() {
        setFloatVec3(this.mMinLocation, this.mMin);
        setFloatVec3(this.mMidLocation, this.mMid);
        setFloatVec3(this.mMaxLocation, this.mMax);
        setFloatVec3(this.mMinOutputLocation, this.mMinOutput);
        setFloatVec3(this.mMaxOutputLocation, this.mMaxOutput);
    }

    public void setMin(float f, float f2, float f3, float f4, float f5) {
        setRedMin(f, f2, f3, f4, f5);
        setGreenMin(f, f2, f3, f4, f5);
        setBlueMin(f, f2, f3, f4, f5);
    }

    public void setMin(float f, float f2, float f3) {
        setMin(f, f2, f3, 0.0f, 1.0f);
    }

    public void setRedMin(float f, float f2, float f3, float f4, float f5) {
        this.mMin[0] = f;
        this.mMid[0] = f2;
        this.mMax[0] = f3;
        this.mMinOutput[0] = f4;
        this.mMaxOutput[0] = f5;
        updateUniforms();
    }

    public void setRedMin(float f, float f2, float f3) {
        setRedMin(f, f2, f3, 0.0f, 1.0f);
    }

    public void setGreenMin(float f, float f2, float f3, float f4, float f5) {
        this.mMin[1] = f;
        this.mMid[1] = f2;
        this.mMax[1] = f3;
        this.mMinOutput[1] = f4;
        this.mMaxOutput[1] = f5;
        updateUniforms();
    }

    public void setGreenMin(float f, float f2, float f3) {
        setGreenMin(f, f2, f3, 0.0f, 1.0f);
    }

    public void setBlueMin(float f, float f2, float f3, float f4, float f5) {
        this.mMin[2] = f;
        this.mMid[2] = f2;
        this.mMax[2] = f3;
        this.mMinOutput[2] = f4;
        this.mMaxOutput[2] = f5;
        updateUniforms();
    }

    public void setBlueMin(float f, float f2, float f3) {
        setBlueMin(f, f2, f3, 0.0f, 1.0f);
    }
}
