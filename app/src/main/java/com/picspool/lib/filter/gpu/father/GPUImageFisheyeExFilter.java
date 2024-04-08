package com.picspool.lib.filter.gpu.father;

import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.newfilter.GPUImageFisheyeFilter;

/* loaded from: classes3.dex */
public class GPUImageFisheyeExFilter extends GPUImageFisheyeFilter {
    public static final String FOCUS_FRAGMENT_SHADER = "precision mediump float; varying highp vec2 textureCoordinate; varying highp vec2 textureCoordinate2; uniform sampler2D inputImageTexture; uniform sampler2D inputImageTexture2; uniform  float aspectRatio; uniform highp vec2 center; uniform  float radius; uniform highp float refractiveIndex; uniform int isMirror; uniform vec3 filterColor; uniform int isColor; uniform float ratio; uniform int isBlur; const highp vec3 ambientLightPosition = vec3(0.0, 0.0, 1.0); void main() {    lowp vec4 blurredImageColor;    if (isBlur == 0){    blurredImageColor = texture2D(inputImageTexture2, textureCoordinate2);    }    else{    blurredImageColor = texture2D(inputImageTexture, textureCoordinate);    }    if (isColor == 1){         blurredImageColor = vec4(mix(blurredImageColor.rgb,filterColor,0.35),1.0);}    if (textureCoordinate.x >= (0.5 - 0.5 / ratio) && textureCoordinate.x <= 1.0 - (0.5 - 0.5 / ratio))    {    highp vec2 textureCoordinateToUse = vec2(textureCoordinate.x, (textureCoordinate.y * aspectRatio + 0.5 - 0.5 * aspectRatio));    highp vec2 pos = vec2(textureCoordinateToUse.x * ratio - center.x * ratio + center.x, textureCoordinateToUse.y);    highp float distanceFromCenter = distance(center, pos);    lowp float checkForPresenceWithinSphere = step(distanceFromCenter, radius);    vec4 finalcolor;    if (distanceFromCenter < radius){            float normalizedDepth = sqrt(radius * radius - distanceFromCenter * distanceFromCenter);            highp vec3 sphereNormal = normalize(vec3(textureCoordinateToUse - center, normalizedDepth));            highp vec3 refractedVector = refract(vec3(0.0, 0.0, -1.0), sphereNormal, refractiveIndex);            if (isMirror == 0){refractedVector.xy = -refractedVector.xy;}            highp vec3 finalSphereColor = texture2D(inputImageTexture, (refractedVector.xy + 1.0) * 0.5).rgb;            float lightingIntensity = 0.45 * (1.0 - pow(clamp(dot(ambientLightPosition, sphereNormal), 0.0, 1.0), 0.25));            finalSphereColor += lightingIntensity;            finalcolor = vec4(finalSphereColor, 1.0) * 1.0;            float d = radius - distanceFromCenter;            float t = smoothstep(0.0, 0.02, d);            finalcolor = mix(finalcolor, blurredImageColor,1.0- t);    }else{            finalcolor = blurredImageColor;    }    gl_FragColor = finalcolor;    }}";
    public static final String GETOUTPUT_FRAGMENT_SHADER = "precision mediump float; varying highp vec2 textureCoordinate; varying highp vec2 textureCoordinate2; uniform sampler2D inputImageTexture; uniform sampler2D inputImageTexture2; uniform  float aspectRatio; uniform highp vec2 center; uniform  float radius; uniform highp float refractiveIndex; uniform int isMirror; uniform vec3 filterColor; uniform int isColor; uniform float ratio; uniform int isBlur; const highp vec3 ambientLightPosition = vec3(0.0, 0.0, 1.0); void main() {    lowp vec4 blurredImageColor;    if (isBlur == 0){    blurredImageColor = texture2D(inputImageTexture2, textureCoordinate2);    }    else{    blurredImageColor = texture2D(inputImageTexture, textureCoordinate);    }    if (isColor == 1){         blurredImageColor = vec4(mix(blurredImageColor.rgb,filterColor,0.35),1.0);}    if (textureCoordinate.y >= (0.5 - 0.5 / ratio) && textureCoordinate.y <= 1.0 - (0.5 - 0.5 / ratio))    {    highp vec2 textureCoordinateToUse = vec2(textureCoordinate.x, (textureCoordinate.y * aspectRatio + 0.5 - 0.5 * aspectRatio));    highp vec2 pos = vec2(textureCoordinateToUse.x, textureCoordinateToUse.y / ratio - center.y / ratio + center.y);    highp float distanceFromCenter = distance(center, pos);    lowp float checkForPresenceWithinSphere = step(distanceFromCenter, radius);    vec4 finalcolor;    if (distanceFromCenter < radius){            float normalizedDepth = sqrt(radius * radius - distanceFromCenter * distanceFromCenter);            highp vec3 sphereNormal = normalize(vec3(textureCoordinateToUse - center, normalizedDepth));            highp vec3 refractedVector = refract(vec3(0.0, 0.0, -1.0), sphereNormal, refractiveIndex);            if (isMirror == 0){refractedVector.xy = -refractedVector.xy;}            highp vec3 finalSphereColor = texture2D(inputImageTexture, (refractedVector.xy + 1.0) * 0.5).rgb;            float lightingIntensity = 0.45 * (1.0 - pow(clamp(dot(ambientLightPosition, sphereNormal), 0.0, 1.0), 0.25));            finalSphereColor += lightingIntensity;            finalcolor = vec4(finalSphereColor, 1.0) * 1.0;            float d = radius - distanceFromCenter;            float t = smoothstep(0.0, 0.02, d);            finalcolor = mix(finalcolor, blurredImageColor,1.0- t);    }else{            finalcolor = blurredImageColor;    }    gl_FragColor = finalcolor;    }}";
    private boolean mIsBlur;
    private int mIsBlurLocation;
    private float mRatio;
    private int mRatioLocation;

    public GPUImageFisheyeExFilter(String str, float f, boolean z) {
        super(str);
        this.mRatio = f;
        this.mIsBlur = z;
    }

    public GPUImageFisheyeExFilter(String str, float f, boolean z, float[] fArr, boolean z2, boolean z3, float f2, float f3) {
        super(str, fArr, z2, z3, f2, f3);
        this.mRatio = f;
        this.mIsBlur = z;
    }

    @Override // com.picspool.lib.filter.gpu.newfilter.GPUImageFisheyeFilter, com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter, com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mRatioLocation = GLES20.glGetUniformLocation(getProgram(), "ratio");
        this.mIsBlurLocation = GLES20.glGetUniformLocation(getProgram(), "isBlur");
    }

    @Override // com.picspool.lib.filter.gpu.newfilter.GPUImageFisheyeFilter, com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter, com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        setRatio(this.mRatio);
        setIsBlur(this.mIsBlur);
    }

    public void setRatio(float f) {
        this.mRatio = f;
        setFloat(this.mRatioLocation, f);
    }

    public void setIsBlur(boolean z) {
        this.mIsBlur = z;
        if (z) {
            setInteger(this.mIsBlurLocation, 1);
        } else {
            setInteger(this.mIsBlurLocation, 0);
        }
    }
}
