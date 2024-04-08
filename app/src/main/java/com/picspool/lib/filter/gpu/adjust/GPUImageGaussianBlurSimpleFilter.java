package com.picspool.lib.filter.gpu.adjust;

import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageGaussianBlurSimpleFilter extends GPUImageFilter {
    public static final String BLUR_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n uniform lowp float blurSize;\n uniform lowp float mixturePercent;\n const highp vec4 kRGBToYPrime = vec4 (0.299, 0.587, 0.114, 0.0); void main() {     lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     mediump vec4 sum = vec4(0.0);\n    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x, textureCoordinate.y)) * 0.147761;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x + blurSize, textureCoordinate.y)) * 0.118318;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x - blurSize, textureCoordinate.y)) * 0.118318;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x, textureCoordinate.y + blurSize)) * 0.118318;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x, textureCoordinate.y - blurSize)) * 0.118318;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x + blurSize, textureCoordinate.y + blurSize)) * 0.0947416;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x + blurSize, textureCoordinate.y - blurSize)) * 0.0947416;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x - blurSize, textureCoordinate.y + blurSize)) * 0.0947416;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x - blurSize, textureCoordinate.y - blurSize)) * 0.0947416;      lowp float YPrime = dot(textureColor, kRGBToYPrime);  if(YPrime > 0.4){ sum = textureColor;}   gl_FragColor = vec4(mix(textureColor.rgb, sum.rgb, textureColor.a*mixturePercent), textureColor.a);\n }";
    private float mBlurSize;
    private int mBlurSizeLocation;

    public GPUImageGaussianBlurSimpleFilter() {
        this(0.0017361111f);
    }

    public GPUImageGaussianBlurSimpleFilter(float f) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, BLUR_FRAGMENT_SHADER);
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
