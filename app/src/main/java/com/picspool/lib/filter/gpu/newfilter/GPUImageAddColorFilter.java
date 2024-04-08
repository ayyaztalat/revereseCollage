package com.picspool.lib.filter.gpu.newfilter;

import android.graphics.Color;
import android.opengl.GLES20;
import androidx.core.view.ViewCompat;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageAddColorFilter extends GPUImageFilter {
    public static final String FALSECOLOR_FRAGMENT_SHADER = "precision lowp float;\nvarying highp vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\nuniform lowp float mixturePercent;\nuniform lowp float colorMixPercent;\nuniform vec3 nColor;\nlowp vec3 rgb2hsv(vec3 rgbColor){  \n     lowp float mx = rgbColor.r;   \n     int mindex = 0;               \n     if(rgbColor.g > mx){          \n         mx = rgbColor.g;          \n         mindex = 1;               \n     }                             \n     if(rgbColor.b > mx){          \n         mx = rgbColor.b;          \n         mindex = 2;               \n     }                             \n     lowp float mi = min(rgbColor.r, min(rgbColor.g, rgbColor.b)); \n     lowp float df = mx - mi;      \n     highp float h = 0.0;            \n     lowp float s = 0.0;             \n     lowp float v = 0.0;             \n     if(mindex == 0){              \n         h = (60.0 * ((rgbColor.g - rgbColor.b)/df) + 360.0);  \n         while( h > 360.0){        \n             h = h - 360.0;        \n         }                         \n     }else if(mindex == 1){        \n         h = (60.0 * ((rgbColor.b - rgbColor.r)/df) + 120.0);  \n         while(h > 360.0){        \n             h = h - 360.0;        \n         }                         \n     }else{                        \n         h = (60.0 * ((rgbColor.r - rgbColor.g)/df) + 240.0);  \n         while(h > 360.0){        \n             h = h - 360.0;        \n         }                         \n     }                             \n     if( mx <= 0.0 ){                  \n         s = 0.0;                    \n     }else{                        \n         s = df / mx;              \n     }                             \n     v = mx;                       \n     return vec3(h, s, v);         \n }lowp vec3 RGB2YCrCb(lowp vec3 rgbVal){\n     lowp float Y = 0.299 * rgbVal.r + 0.587 * rgbVal.g + 0.114 * rgbVal.b;\n     lowp float Cb = 0.564 * (rgbVal.g - Y);\n     lowp float Cr = 0.713 * (rgbVal.r - Y);\n     lowp vec3 yCrCb = vec3(Y, Cr, Cb);\n     return yCrCb;\n}void main()\n{\n   lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n       lowp vec4 newColor = vec4(textureColor.rgb + 2.0 * nColor.rgb - 1.0, textureColor.a);       gl_FragColor = vec4(mix(textureColor.rgb,newColor.rgb,mixturePercent*colorMixPercent) , textureColor.a);}\n";
    private float colorMix;
    private int mColorMixLocation;
    private int mNewColorLocation;
    private int newColor;

    public GPUImageAddColorFilter() {
        this(ViewCompat.MEASURED_SIZE_MASK);
    }

    public GPUImageAddColorFilter(int i) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, FALSECOLOR_FRAGMENT_SHADER);
        this.colorMix = 0.0f;
        this.newColor = i;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mNewColorLocation = GLES20.glGetUniformLocation(getProgram(), "nColor");
        this.mColorMixLocation = GLES20.glGetUniformLocation(getProgram(), "colorMixPercent");
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        setNewColor(this.newColor);
        setColorMix(this.colorMix);
    }

    public void setNewColor(int i) {
        this.newColor = i;
        setFloatVec3(this.mNewColorLocation, new float[]{Color.red(i) / 255.0f, Color.green(i) / 255.0f, Color.blue(i) / 255.0f});
    }

    public void setColorMix(float f) {
        this.colorMix = f;
        setFloat(this.mColorMixLocation, f);
    }
}
