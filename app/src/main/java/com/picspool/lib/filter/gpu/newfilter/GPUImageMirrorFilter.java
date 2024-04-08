package com.picspool.lib.filter.gpu.newfilter;

import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageMirrorFilter extends GPUImageFilter {
    public static final int MIRROR_STYLE_FOUR = 4;
    public static final int MIRROR_STYLE_LEFT_RIGHT = 1;
    public static final int MIRROR_STYLE_THREE = 3;
    public static final int MIRROR_STYLE_TOP_BOTTOM_INVERTED = 5;
    public static final int MIRROR_STYLE_TOP_BTTTOM = 2;
    public static final String SHADER_FRAG = "varying highp vec2 textureCoordinate;uniform sampler2D inputImageTexture;uniform int mirrorsyle;uniform int islrmirror; uniform int isusebackcam;void main(){highp vec2 texture = textureCoordinate;lowp float half_change_X = 0.5* 3.0 /4.0;if (mirrorsyle == 1){if (texture.y > 0.5){texture.y = 1.0 - texture.y;}}else if (mirrorsyle == 2){if (isusebackcam == 0){if (texture.x < 0.5){texture.x = texture.x + half_change_X;texture.y = 1.0 - texture.y;}}else{if (texture.x > 0.5){texture.x = texture.x - half_change_X;texture.y = 1.0 - texture.y;}}}else if (mirrorsyle == 3){if (texture.y < 0.33){texture.y = texture.y + 0.33;}else if (texture.y >0.66){texture.y = texture.y - 0.33;}}else if (mirrorsyle == 4){if (isusebackcam == 0){if (texture.x < 0.5 && texture.y > 0.5){  texture.y = 1.0 - texture.y ;}else if (texture.x > 0.5 && texture.y < 0.5 ){texture.x = texture.x - half_change_X;texture.y =   0.5 - texture.y;}else if (texture.x > 0.5 && texture.y > 0.5){texture.x = texture.x - half_change_X;texture.y = texture.y - 0.5;}}else{if (texture.x < 0.5 && texture.y > 0.5){texture.x = texture.x + half_change_X;texture.y = texture.y -0.5;}else if (texture.x < 0.5 && texture.y < 0.5 ){texture.x = texture.x + half_change_X;texture.y =   0.5 - texture.y;}else if (texture.x > 0.5 && texture.y > 0.5){texture.y = 1.0 - texture.y;}}}else if (mirrorsyle == 5){     if (isusebackcam == 1)     {         if (texture.x < 0.5)         {             texture.x = 1.0 - texture.x;         }     }     else     {         if (texture.x > 0.5)         {             texture.x = 1.0 - texture.x;         }     }}if (islrmirror == 1){texture.y = 1.0 - texture.y;}gl_FragColor = texture2D(inputImageTexture, texture);}";
    public static final String SHADER_FRAG_GETPIC = "varying highp vec2 textureCoordinate;uniform sampler2D inputImageTexture;uniform int mirrorsyle;uniform int islrmirror;void main(){highp vec2 texture = textureCoordinate;lowp float half_change_X = 0.5;if (mirrorsyle == 1){if (texture.x < 0.5){texture.x = 1.0 - texture.x;}}else if (mirrorsyle == 2){if (texture.y < 0.5){texture.y = texture.y + half_change_X;texture.x = 1.0 - texture.x;}}else if (mirrorsyle == 3){if (texture.x < 0.33){texture.x = texture.x + 0.33;}else if (texture.x >0.66){texture.x = texture.x - 0.33;}}else if (mirrorsyle == 4){if (texture.x < 0.5 && texture.y < 0.5){texture.x = 1.0 - texture.x ;}else if (texture.y >= 0.5 && texture.x < 0.5 ){texture.y = texture.y - half_change_X;texture.x = 0.5 + texture.x ;}else if (texture.y >= 0.5 && texture.x >= 0.5){texture.y = texture.y - half_change_X;texture.x = 1.5 - texture.x ;}}else if (mirrorsyle == 5){     if (texture.y > 0.5)     {         texture.y = 1.0 - texture.y;     }}if (islrmirror == 1){texture.x = 1.0 - texture.x;}gl_FragColor = texture2D(inputImageTexture, texture);}";
    public static final String SHADER_VERT = "attribute vec4 position;attribute vec4 inputTextureCoordinate;uniform mat4 transformMatrix;varying vec2 textureCoordinate;void main(){    gl_Position = transformMatrix * vec4(position.xyz, 1.0);textureCoordinate = inputTextureCoordinate.xy;}";
    private int mImageHeightFactorLocation;
    private int mImageWidthFactorLocation;
    private int mIsLRMirror;
    private int mIsLRMirrorLocation;
    private int mIsUseBackCam;
    private int mIsUseBackCamLocation;
    private int mMirrorStyle;
    private int mMirrorStyleLocation;

    public void setFishEyeFactor(float f) {
    }

    public GPUImageMirrorFilter(String str, String str2, int i) {
        super(str, str2);
        this.mMirrorStyle = i;
        this.mIsLRMirror = 0;
        this.mIsUseBackCam = 1;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mMirrorStyleLocation = GLES20.glGetUniformLocation(getProgram(), "mirrorsyle");
        this.mIsLRMirrorLocation = GLES20.glGetUniformLocation(getProgram(), "islrmirror");
        this.mIsUseBackCamLocation = GLES20.glGetUniformLocation(getProgram(), "isusebackcam");
        this.mImageWidthFactorLocation = GLES20.glGetUniformLocation(getProgram(), "imageWidthFactor");
        this.mImageHeightFactorLocation = GLES20.glGetUniformLocation(getProgram(), "imageHeightFactor");
        setMirrorStyle(this.mMirrorStyle);
        setmIsLRMirror(this.mIsLRMirror);
        setmIsUseBackCam(this.mIsUseBackCam);
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onOutputSizeChanged(int i, int i2) {
        super.onOutputSizeChanged(i, i2);
        setFloat(this.mImageWidthFactorLocation, 1.0f / i);
        setFloat(this.mImageHeightFactorLocation, 1.0f / i2);
    }

    public void setMirrorStyle(int i) {
        this.mMirrorStyle = i;
        setInteger(this.mMirrorStyleLocation, i);
    }

    public void setmIsLRMirror(int i) {
        this.mIsLRMirror = i;
        setInteger(this.mIsLRMirrorLocation, i);
    }

    public void setmIsUseBackCam(int i) {
        this.mIsUseBackCam = i;
        setInteger(this.mIsUseBackCamLocation, i);
    }

    public void resetBackCam() {
        int i = this.mIsUseBackCam == 0 ? 1 : 0;
        this.mIsUseBackCam = i;
        setmIsUseBackCam(i);
    }
}
