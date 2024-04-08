package com.picspool.libfuncview.bodyshaper;

import android.graphics.PointF;
import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes.dex */
public class CSGPUImagePinchDistortionFilter extends GPUImageFilter {
    public static final String PINCH_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n \n uniform highp float aspectRatio;\n uniform highp vec2 center;\n uniform highp float radius;\n uniform highp float scale;\n \n void main()\n {\n highp vec2 newCoord2 = vec2(textureCoordinate.x,textureCoordinate.y); highp float dis = distance(vec2(newCoord2.x,newCoord2.y*aspectRatio), vec2(center.x,center.y*aspectRatio)); lowp float weight = 1.0;if(dis <= radius)\n{\nweight = pow(dis / radius, scale);\n       newCoord2.x = (center.x +(textureCoordinate.x - center.x)* weight);\n       newCoord2.y = (center.y +(textureCoordinate.y - center.y)* weight);\n   gl_FragColor = texture2D(inputImageTexture, newCoord2);\n}else\n     {\n         gl_FragColor = texture2D(inputImageTexture, textureCoordinate );\n     }\n }";
    private float aspectRatio;
    private PointF center;
    private int mAspectRatioLocation;
    private int mCenterLocation;
    private int mRadiusLocation;
    private int mScaleLocation;
    private float radius;
    private float scale;

    public CSGPUImagePinchDistortionFilter() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, PINCH_FRAGMENT_SHADER);
    }

    public CSGPUImagePinchDistortionFilter(float f, float f2, PointF pointF) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, PINCH_FRAGMENT_SHADER);
        this.radius = f;
        this.scale = f2;
        this.center = pointF;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mScaleLocation = GLES20.glGetUniformLocation(getProgram(), "scale");
        this.mRadiusLocation = GLES20.glGetUniformLocation(getProgram(), "radius");
        this.mCenterLocation = GLES20.glGetUniformLocation(getProgram(), "center");
        this.mAspectRatioLocation = GLES20.glGetUniformLocation(getProgram(), "aspectRatio");
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        setRadius(this.radius);
        setScale(this.scale);
        setCenter(this.center);
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onOutputSizeChanged(int i, int i2) {
        float f = i2 / i;
        this.aspectRatio = f;
        setAspectRatio(f);
        super.onOutputSizeChanged(i, i2);
    }

    public void setRadius(float f) {
        this.radius = f;
        setFloat(this.mRadiusLocation, f);
    }

    public void setScale(float f) {
        this.scale = f;
        setFloat(this.mScaleLocation, f);
    }

    public void setAspectRatio(float f) {
        this.aspectRatio = f;
        setFloat(this.mAspectRatioLocation, f);
    }

    public void setCenter(PointF pointF) {
        this.center = pointF;
        setPoint(this.mCenterLocation, pointF);
    }
}
