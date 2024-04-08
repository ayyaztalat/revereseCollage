package com.picspool.lib.filter.gpu.normal;

import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageBoxBlurFilter extends GPUImageFilter {
    public static final String BLUR_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n uniform lowp float blurSize;\n void main(void) {    vec4 sum = vec4(0.0);\n    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x - 4.0*blurSize, textureCoordinate.y)) * 0.05;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x - 3.0*blurSize, textureCoordinate.y)) * 0.09;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x - 2.0*blurSize, textureCoordinate.y)) * 0.12;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x - blurSize, textureCoordinate.y)) * 0.15;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x, textureCoordinate.y)) * 0.16;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x + blurSize, textureCoordinate.y)) * 0.15;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x + 2.0*blurSize, textureCoordinate.y)) * 0.12;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x + 3.0*blurSize, textureCoordinate.y)) * 0.09;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x + 4.0*blurSize, textureCoordinate.y)) * 0.05;    gl_FragColor = sum; }";
    public static final String BLUR_VERTEX_SHADER = "varying vec2 textureCoordinate;void main(void){    gl_Position = ftransform();    vec2 Pos = sign(gl_Vertex.xy);    gl_Position = vec4(Pos, 0.0, 1.0);    textureCoordinate = Pos * 0.5 + 0.5;}";
    private float mBlurSize;
    private int mBlurSizeLocation;

    public GPUImageBoxBlurFilter() {
        this(0.0f);
    }

    public GPUImageBoxBlurFilter(float f) {
        super(BLUR_VERTEX_SHADER, BLUR_FRAGMENT_SHADER);
        this.mBlurSize = f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mBlurSizeLocation = GLES20.glGetUniformLocation(getProgram(), "blurSize");
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        setBlurSize(this.mBlurSize);
    }

    public void setBlurSize(float f) {
        this.mBlurSize = f;
        setFloat(this.mBlurSizeLocation, f);
    }
}
