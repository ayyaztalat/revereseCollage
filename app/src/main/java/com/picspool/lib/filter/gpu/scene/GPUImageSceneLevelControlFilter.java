package com.picspool.lib.filter.gpu.scene;

import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageSceneLevelControlFilter extends GPUImageFilter {
    public static final String SCENE_FRAGMENT_SHADER = " varying highp vec2 textureCoordinate;\n \n precision highp float;\n \n uniform float width;\n uniform float height;\n uniform sampler2D inputImageTexture;\n \n uniform float gamma;\n uniform float minInput;\n uniform float maxInput;\n uniform float minOutput;\n uniform float maxOutput;\n \n #define GammaCorrection(color, gamma)  pow(color, vec3(1.0 / gamma)) \n \n #define LevelsControlInputRange(color, minInput, maxInput)  min(max(color - vec3(minInput), vec3(0.0)) / (vec3(maxInput) - vec3(minInput)), vec3(1.0)) \n \n #define LevelsControlInput(color, minInput, gamma, maxInput)  GammaCorrection(LevelsControlInputRange(color, minInput, maxInput), gamma) \n \n #define LevelsControlOutputRange(color, minOutput, maxOutput)  mix(vec3(minOutput), vec3(maxOutput), color) \n \n #define LevelsControl(color, minInput, gamma, maxInput, minOutput, maxOutput)  LevelsControlOutputRange(LevelsControlInput(color, minInput, gamma, maxInput), minOutput, maxOutput) \n \n void main()\n {\n     lowp vec3 color = texture2D(inputImageTexture, textureCoordinate).xyz;\n     \n     lowp vec3 levelColor = LevelsControl(color, minInput, gamma, maxInput, minOutput, maxOutput); \n     \n     gl_FragColor = vec4(levelColor, 1.0);\n }";
    private float mGamma;
    private int mGammaLocation;
    private float mMaxInput;
    private int mMaxInputLocation;
    private float mMaxOutput;
    private int mMaxOutputLocation;
    private float mMinInput;
    private int mMinInputLocation;
    private float mMinOutput;
    private int mMinOutputLocation;

    public GPUImageSceneLevelControlFilter() {
        this(0.0f, 0.0f, 1.0f, 0.0f, 1.0f);
    }

    public GPUImageSceneLevelControlFilter(float f, float f2, float f3, float f4, float f5) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, SCENE_FRAGMENT_SHADER);
        this.mMinInput = f;
        this.mGamma = f2;
        this.mMaxInput = f3;
        this.mMinOutput = f4;
        this.mMaxOutput = f5;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mMinInputLocation = GLES20.glGetUniformLocation(getProgram(), "minInput");
        this.mGammaLocation = GLES20.glGetUniformLocation(getProgram(), "gamma");
        this.mMaxInputLocation = GLES20.glGetUniformLocation(getProgram(), "maxInput");
        this.mMinOutputLocation = GLES20.glGetUniformLocation(getProgram(), "minOutput");
        this.mMaxOutputLocation = GLES20.glGetUniformLocation(getProgram(), "maxOutput");
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        setSceneParameter(this.mMinInput, this.mGamma, this.mMaxInput, this.mMinOutput, this.mMaxOutput);
    }

    public void setSceneParameter(float f, float f2, float f3, float f4, float f5) {
        this.mMinInput = f;
        this.mGamma = f2;
        this.mMaxInput = f3;
        this.mMinOutput = f4;
        this.mMaxOutput = f5;
        setFloat(this.mMinInputLocation, f);
        setFloat(this.mGammaLocation, this.mGamma);
        setFloat(this.mMaxInputLocation, this.mMaxInput);
        setFloat(this.mMinOutputLocation, this.mMinOutput);
        setFloat(this.mMaxOutputLocation, this.mMaxOutput);
    }
}
