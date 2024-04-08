package com.picspool.lib.filter.gpu.newfilter;

import android.graphics.Color;
import android.opengl.GLES20;
import android.util.Log;
import androidx.core.view.ViewCompat;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageSmoothFilter2 extends GPUImageFilter {
    public static final String FALSECOLOR_FRAGMENT_SHADER = "precision lowp float;\n\nvarying highp vec2 textureCoordinate;\n\nuniform sampler2D inputImageTexture;\n uniform lowp float mixturePercent;\nuniform vec3 nColor;\nvoid main()\n{\nlowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\nif(nColor.r>=0.9){\ngl_FragColor = vec4(1.0,0.0,0.0,1.0);\n}else if(nColor.g>=0.9){\ngl_FragColor = vec4(0.0,1.0,0.0,0.6);\n}else if(nColor.b>=0.9){\ngl_FragColor = vec4(0.0,0.0,1.0,1.0);\n}else{\ngl_FragColor = vec4( nColor.rgb , textureColor.a*mixturePercent);\n}}\n";
    private int mNewColorLocation;
    private int newColor;

    public GPUImageSmoothFilter2() {
        this(ViewCompat.MEASURED_SIZE_MASK);
    }

    public GPUImageSmoothFilter2(int i) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, FALSECOLOR_FRAGMENT_SHADER);
        this.newColor = i;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mNewColorLocation = GLES20.glGetUniformLocation(getProgram(), "nColor");
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        setNewColor(this.newColor);
    }

    public void setNewColor(int i) {
        this.newColor = i;
        float[] fArr = {Color.red(i) / 255.0f, Color.green(i) / 255.0f, Color.blue(i) / 255.0f};
        Log.i("TEST", "COLOR:" + fArr[0] + "," + fArr[1] + "," + fArr[2]);
        setFloatVec3(this.mNewColorLocation, fArr);
    }
}
