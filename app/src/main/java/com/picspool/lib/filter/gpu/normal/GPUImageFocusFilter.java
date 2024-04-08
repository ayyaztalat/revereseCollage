package com.picspool.lib.filter.gpu.normal;

import android.graphics.PointF;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter;
import com.sky.testproject.Opcodes;

/* loaded from: classes3.dex */
public class GPUImageFocusFilter extends GPUImageTwoInputFilter {
    private static final String FOCUS_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;varying highp vec2 textureCoordinate2;uniform sampler2D inputImageTexture;uniform sampler2D inputImageTexture2;uniform lowp float excludeCircleRadius;uniform lowp vec2 excludeCirclePoint;uniform lowp float excludeBlurSize;uniform highp float aspectRatio;uniform lowp float isShowBorder;void main(){ lowp vec4 sharpImageColor = texture2D(inputImageTexture, textureCoordinate);lowp vec4 blurredImageColor = texture2D(inputImageTexture2, textureCoordinate);highp vec2 textureCoordinateToUse = vec2(textureCoordinate2.x, (textureCoordinate2.y * aspectRatio + 0.5 - 0.5 * aspectRatio)); highp vec2 v1 = vec2(0.5,0.5) ;highp float distanceFromCenter = distance(v1, textureCoordinateToUse);if(isShowBorder == 1.0 ){highp float d =  distanceFromCenter - excludeCircleRadius;highp float t = smoothstep(-0.01, 0.01, d); if (t > 0.5){t = 1.0-t;t = t + 0.1;}highp vec4 resColor = mix(sharpImageColor, blurredImageColor, smoothstep(excludeCircleRadius - excludeBlurSize, excludeCircleRadius, distanceFromCenter));highp vec4 lineColor = vec4(vec3(1),t);gl_FragColor = mix(resColor,lineColor,lineColor.a );}else{gl_FragColor = mix(sharpImageColor, blurredImageColor, smoothstep(excludeCircleRadius - excludeBlurSize, excludeCircleRadius, distanceFromCenter));}}";
    private static final String VERTEX_SHADER = "attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\nattribute vec4 inputTextureCoordinate2;\n \nuniform mat4 transformMatrix;\nuniform mat4 transformMatrix2;\n \nvarying vec2 textureCoordinate;\nvarying vec2 textureCoordinate2;\n \nvoid main()\n{\n    gl_Position = transformMatrix * vec4(position.xyz, 1.0);\n    textureCoordinate = inputTextureCoordinate.xy;\n    vec4 pos2 = transformMatrix2*vec4(inputTextureCoordinate2.xy,1.0,1.0);    textureCoordinate2 = pos2.xy;\n}";
    private float aspectRatio;
    private int aspectRatioLocation;
    private float excludeBlurSize;
    private int excludeBlurSizeLocation;
    private PointF excludeCirclePoint;
    private int excludeCirclePointLocation;
    private float excludeCircleRadius;
    private int excludeCircleRadiusLocation;
    private int isShowBorder;
    private int isShowBorderLocation;
    private float left;
    protected int mTransformLocation2;
    private int rotate;
    private float scale;
    private float top;
    private float viewHeight;
    private float viewWidth;

    public GPUImageFocusFilter() {
        super(VERTEX_SHADER, FOCUS_FRAGMENT_SHADER);
        this.excludeCirclePoint = new PointF(0.5f, 0.5f);
        this.excludeCircleRadius = 0.15f;
        this.excludeBlurSize = 0.0f;
        this.aspectRatio = 0.5f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter, com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mTransformLocation2 = GLES20.glGetUniformLocation(getProgram(), "transformMatrix2");
        this.excludeCirclePointLocation = GLES20.glGetUniformLocation(getProgram(), "excludeCirclePoint");
        this.excludeCircleRadiusLocation = GLES20.glGetUniformLocation(getProgram(), "excludeCircleRadius");
        this.excludeBlurSizeLocation = GLES20.glGetUniformLocation(getProgram(), "excludeBlurSize");
        this.aspectRatioLocation = GLES20.glGetUniformLocation(getProgram(), "aspectRatio");
        this.isShowBorderLocation = GLES20.glGetUniformLocation(getProgram(), "isShowBorder");
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter, com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        setTransform2(this.rotate, this.left, this.top, this.scale, this.viewWidth, this.viewHeight);
        setCircleRadius(this.excludeCircleRadius);
        setCirclePoint(this.excludeCirclePoint);
        setBlurSize(this.excludeBlurSize);
        setAspectRatio(this.aspectRatio);
    }

    public void setCircleRadius(float f) {
        this.excludeCircleRadius = f;
        setFloat(this.excludeCircleRadiusLocation, f);
    }

    public void setBlurSize(float f) {
        this.excludeBlurSize = f;
        setFloat(this.excludeBlurSizeLocation, f);
    }

    public void setAspectRatio(float f) {
        this.aspectRatio = f;
        setFloat(this.aspectRatioLocation, f);
    }

    public void setCirclePoint(PointF pointF) {
        this.excludeCirclePoint = pointF;
        setFloatVec2(this.excludeCirclePointLocation, new float[]{pointF.x, this.excludeCirclePoint.y});
    }

    public void setIsShowBorder(boolean z) {
        if (z) {
            this.isShowBorder = 1;
            setFloat(this.isShowBorderLocation, 1);
            return;
        }
        this.isShowBorder = 0;
        setFloat(this.isShowBorderLocation, 0);
    }

    public void setTransform2(int i, float f, float f2, float f3, float f4, float f5) {
        this.rotate = i;
        this.left = f;
        this.top = f2;
        this.scale = f3;
        this.viewWidth = f4;
        this.viewHeight = f5;
        float f6 = f4 / f5;
        float[] fArr = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
        float[] fArr2 = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
        float[] fArr3 = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
        float[] fArr4 = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
        Matrix.setLookAtM(fArr, 0, 0.0f, 0.0f, -3.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        Matrix.frustumM(fArr2, 0, -1.0f, 1.0f, -f6, f6, 1.0f, 7.0f);
        Matrix.multiplyMM(fArr4, 0, fArr2, 0, fArr, 0);
        Matrix.translateM(fArr3, 0, 0.125f, 0.135f, 0.0f);
        Matrix.rotateM(fArr3, 0, i + Opcodes.GETFIELD, 0.0f, 0.0f, 1.0f);
        float f7 = 1.0f - f3;
        Matrix.scaleM(fArr3, 0, f7, f7, 0.0f);
        Matrix.translateM(fArr3, 0, f - 0.5f, f2 - 0.5f, 0.0f);
        Matrix.multiplyMM(fArr4, 0, fArr3, 0, fArr4, 0);
        setUniformMatrix4f(this.mTransformLocation2, fArr4);
    }
}
