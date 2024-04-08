package com.picspool.lib.filter.gpu.newfilter;

import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageAverageBlurFilter extends GPUImageFilter {
    public static final String FRAGMENT_SHADER = "uniform sampler2D inputImageTexture;\n\nconst lowp int GAUSSIAN_SAMPLES = 4;\n\nvarying highp vec2 textureCoordinate;\nvarying highp vec2 blurCoordinates[GAUSSIAN_SAMPLES];\n\nvoid main()\n{\n   lowp vec4 fragColor=texture2D(inputImageTexture,textureCoordinate);\n\t    lowp vec4 sum = vec4(0.0);\n       int cnt = 0; \n       lowp vec4 tmp=vec4(0.0);       for(int i=0;i<GAUSSIAN_SAMPLES;i++){           tmp = texture2D(inputImageTexture,blurCoordinates[i]); \n           if(tmp.a>0.001) { \n              sum += tmp; \n              cnt += 1; \n           }        }\n       if(cnt==0){   \t    gl_FragColor = fragColor;\n       }else{           gl_FragColor = vec4(sum.r/float(cnt),sum.g/float(cnt),sum.b/float(cnt),sum.a/float(cnt)); \n       }}";
    public static final String FRAGMENT_SHADER2 = "uniform sampler2D inputImageTexture;\n\nconst lowp int GAUSSIAN_SAMPLES = 4;\n\nvarying highp vec2 textureCoordinate;\nvarying highp vec2 blurCoordinates[GAUSSIAN_SAMPLES];\n\nvoid main()\n{\n   lowp vec4 fragColor=texture2D(inputImageTexture,textureCoordinate);\n   if(fragColor.a>0.01){ \n\t    lowp vec4 sum = vec4(0.0);\n       int cnt = 0; \n       lowp vec4 tmp=fragColor;       for(int i=0;i<GAUSSIAN_SAMPLES;i++){           tmp = texture2D(inputImageTexture,blurCoordinates[i]); \n           if(tmp.a>0.001) { \n              sum += tmp; \n              cnt += 1; \n           }        }\n      gl_FragColor = vec4(sum.r/float(cnt),sum.g/float(cnt),sum.b/float(cnt),sum.a/float(cnt)); \n   }else{   \tgl_FragColor = fragColor;\n   }\n}";
    public static final String VERTEX_SHADER = "attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n\nconst int GAUSSIAN_SAMPLES = 8;\n\nuniform float texelWidthOffset;\nuniform float texelHeightOffset;\n\nvarying vec2 textureCoordinate;\nvarying vec2 blurCoordinates[GAUSSIAN_SAMPLES];\n\nvoid main()\n{\n\tgl_Position = position;\n\ttextureCoordinate = inputTextureCoordinate.xy;\n   vec2 blurStep = vec2(-1.0*texelWidthOffset,0.0);\n   blurCoordinates[0] = inputTextureCoordinate.xy + blurStep;\n   blurStep = vec2(texelWidthOffset,0.0);\n   blurCoordinates[1] = inputTextureCoordinate.xy + blurStep;\n   blurStep = vec2(0.0,-1.0*texelHeightOffset);\n   blurCoordinates[2] = inputTextureCoordinate.xy + blurStep;\n   blurStep = vec2(0.0,texelHeightOffset);\n   blurCoordinates[3] = inputTextureCoordinate.xy + blurStep;\n   blurStep = vec2(-1.0*texelWidthOffset,-1.0*texelHeightOffset);\n   blurCoordinates[4] = inputTextureCoordinate.xy + blurStep;\n   blurStep = vec2(-1.0*texelWidthOffset,texelHeightOffset);\n   blurCoordinates[5] = inputTextureCoordinate.xy + blurStep;\n   blurStep = vec2(texelWidthOffset,-1.0*texelHeightOffset);\n   blurCoordinates[6] = inputTextureCoordinate.xy + blurStep;\n   blurStep = vec2(texelWidthOffset,texelHeightOffset);\n   blurCoordinates[7] = inputTextureCoordinate.xy + blurStep;\n}\n";
    float texelHeightOffset;
    int texelHeightOffsetLocation;
    float texelWidthOffset;
    int texelWidthOffsetLocation;

    public GPUImageAverageBlurFilter() {
        super(VERTEX_SHADER, FRAGMENT_SHADER);
        this.texelWidthOffset = 1.0f;
        this.texelHeightOffset = 1.0f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.texelWidthOffsetLocation = GLES20.glGetUniformLocation(getProgram(), "texelWidthOffset");
        this.texelHeightOffsetLocation = GLES20.glGetUniformLocation(getProgram(), "texelHeightOffset");
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        float f = this.texelHeightOffset;
        setTexelOffset(f, f);
    }

    public void setTexelOffset(float f, float f2) {
        this.texelWidthOffset = f;
        this.texelHeightOffset = f2;
        setFloat(this.texelWidthOffsetLocation, f);
        setFloat(this.texelHeightOffsetLocation, f2);
    }
}
