package com.picspool.lib.filter.gpu.newfilter;

import android.graphics.PointF;
import android.graphics.RectF;
import android.opengl.GLES20;
import android.util.Log;
import com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter;

/* loaded from: classes3.dex */
public class GPUImagePIPStepTwoFilter extends GPUImageTwoInputFilter {
    private static final String PIP_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\nvarying highp vec2 textureCoordinate2;\nuniform sampler2D inputImageTexture;  \nuniform sampler2D inputImageTexture2; \nuniform highp int backcamera;\nuniform highp vec4 mask2Rect; \nuniform highp int isFilters;\nconst lowp int GAUSSIAN_SAMPLES = 8;\nvarying highp vec2 blurCoordinates2[GAUSSIAN_SAMPLES];\nuniform highp int isRotate270;\nhighp vec4 getBlurColor(highp vec4 textureColor)\n {\n\tlowp vec3 sum = vec3(0.0);\n   lowp vec4 fragColor=texture2D(inputImageTexture,textureCoordinate);\n    sum += texture2D(inputImageTexture, blurCoordinates2[0]).rgb * 0.125;\n    sum += texture2D(inputImageTexture, blurCoordinates2[1]).rgb * 0.125;\n    sum += texture2D(inputImageTexture, blurCoordinates2[2]).rgb * 0.125;\n    sum += texture2D(inputImageTexture, blurCoordinates2[3]).rgb * 0.125;\n    sum += texture2D(inputImageTexture, blurCoordinates2[4]).rgb * 0.125;\n    sum += texture2D(inputImageTexture, blurCoordinates2[5]).rgb * 0.125;\n    sum += texture2D(inputImageTexture, blurCoordinates2[6]).rgb * 0.125;\n    sum += texture2D(inputImageTexture, blurCoordinates2[7]).rgb * 0.125;\n\tgl_FragColor = vec4(sum,fragColor.a);\n     return gl_FragColor;\n} \nvoid main()\n{\n       highp vec4 srcColor = texture2D(inputImageTexture, textureCoordinate);\n       highp float cy = 0.0;\n       highp float cx = 0.0;\n       if(backcamera>0){\n             if(isRotate270 > 0){               cy = 1.0 - (textureCoordinate2.x - 0.125)/0.75;\n               cx = 1.0 - textureCoordinate2.y;\n             }else{               cy = (textureCoordinate2.x - 0.125)/0.75;\n               cx = textureCoordinate2.y;\n             }       }else{\n             cy = 1.0 - (textureCoordinate2.x - 0.125)/0.75;\n             cx = textureCoordinate2.y;\n       }\n       highp vec4 maskColor;       if(textureCoordinate2.x >= 0.125){           if(isFilters > 0){               if(backcamera>0){                   if(isRotate270 > 0){                       maskColor = texture2D(inputImageTexture2, vec2(1.0 - cx,  cy));                   }else{\n                       maskColor = texture2D(inputImageTexture2, vec2(1.0 - cx, cy));                   }\n               }else{                   maskColor = texture2D(inputImageTexture2, vec2(1.0 - cx, cy));               }\n           }else{               maskColor = texture2D(inputImageTexture2, vec2(cx, cy));\n           }           if(maskColor.a > 0.0){\n               gl_FragColor = vec4(srcColor.rgb,srcColor.a);           }else{               highp vec4 blurColor = srcColor;\n               blurColor = getBlurColor(blurColor);\n               gl_FragColor = vec4(blurColor.rgb,srcColor.a);\n           }       }else{\n           highp vec4 blurColor = srcColor;\n           blurColor = getBlurColor(srcColor);\n           gl_FragColor = vec4(blurColor.rgb,srcColor.a);\n     }\n}\n";
    private static final String VERTEX_SHADER = "attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\nattribute vec4 inputTextureCoordinate2;\nvarying vec2 textureCoordinate;\nvarying vec2 textureCoordinate2;\nuniform sampler2D inputImageTexture;  \nuniform sampler2D inputImageTexture2; \nconst int GAUSSIAN_SAMPLES = 8;\nvarying vec2 blurCoordinates2[GAUSSIAN_SAMPLES];\n uniform highp float texelWidthOffset; \n uniform highp float texelHeightOffset; \n uniform highp float blurSize;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n    textureCoordinate2 = inputTextureCoordinate2.xy;\n\tvec2 blurStep;\n   vec2 singleStepOffset = vec2(texelHeightOffset, texelWidthOffset);\n   blurStep = singleStepOffset * blurSize;\n   blurCoordinates2[0] = vec2(inputTextureCoordinate.x - blurStep.x,inputTextureCoordinate.y - blurStep.y);   blurCoordinates2[1] = vec2(inputTextureCoordinate.x,inputTextureCoordinate.y - blurStep.y);   blurCoordinates2[2] = vec2(inputTextureCoordinate.x + blurStep.x,inputTextureCoordinate.y - blurStep.y);   blurCoordinates2[3] = vec2(inputTextureCoordinate.x - blurStep.x,inputTextureCoordinate.y);   blurCoordinates2[4] = vec2(inputTextureCoordinate.x + blurStep.x,inputTextureCoordinate.y);   blurCoordinates2[5] = vec2(inputTextureCoordinate.x - blurStep.x,inputTextureCoordinate.y + blurStep.y);   blurCoordinates2[6] = vec2(inputTextureCoordinate.x,inputTextureCoordinate.y + blurStep.y);   blurCoordinates2[7] = vec2(inputTextureCoordinate.x + blurStep.x,inputTextureCoordinate.y + blurStep.y);}";
    private int backcameraLocation;
    private boolean isBackCamera;
    private int isFilters;
    private int isFiltersLocation;
    private float mBlur2Size;
    private int mBlur2SizeLocation;
    private int mIsRotate270;
    private int mIsRotate270Location;
    private float mTexelHeightOffset;
    private float mTexelWidthOffset;
    private RectF mask2Rect;
    private int mask2RectLocation;
    private PointF mask2Size;
    private int mask2SizeLocation;
    private int texelHeightOffsetLocation;
    private int texelWidthOffsetLocation;
    private float widthAndHeightRatio;

    public GPUImagePIPStepTwoFilter() {
        super(VERTEX_SHADER, PIP_FRAGMENT_SHADER);
        this.mBlur2Size = 4.0f;
        this.mask2Size = new PointF(1.0f, 1.0f);
        this.mask2Rect = new RectF(0.0f, 0.0f, 1.0f, 1.0f);
        this.isBackCamera = true;
        this.widthAndHeightRatio = 1.0f;
        this.isFilters = 0;
        this.mIsRotate270 = 0;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter, com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mask2SizeLocation = GLES20.glGetUniformLocation(getProgram(), "mask2Size");
        this.mask2RectLocation = GLES20.glGetUniformLocation(getProgram(), "mask2Rect");
        this.backcameraLocation = GLES20.glGetUniformLocation(getProgram(), "backcamera");
        this.texelWidthOffsetLocation = GLES20.glGetUniformLocation(getProgram(), "texelWidthOffset");
        this.texelHeightOffsetLocation = GLES20.glGetUniformLocation(getProgram(), "texelHeightOffset");
        this.mBlur2SizeLocation = GLES20.glGetUniformLocation(getProgram(), "blurSize");
        this.isFiltersLocation = GLES20.glGetUniformLocation(getProgram(), "isFilters");
        this.mIsRotate270Location = GLES20.glGetUniformLocation(getProgram(), "isRotate270");
        setIsRotate270(this.mIsRotate270);
        setBlurSize(this.mBlur2Size);
        setIsBackCamera(this.isBackCamera);
        setWidthAndHeightRatio(this.widthAndHeightRatio);
        setTexelWidthOffset(this.mTexelWidthOffset);
        setTexelHeightOffset(this.mTexelHeightOffset);
        setIsFilters(this.isFilters);
    }

    public void setIsRotate270(int i) {
        this.mIsRotate270 = i;
        setInteger(this.mIsRotate270Location, i);
    }

    public void setIsFilters(int i) {
        this.isFilters = i;
        setInteger(this.isFiltersLocation, i);
    }

    public void setTexelWidthOffset(float f) {
        this.mTexelWidthOffset = f;
        setFloat(this.texelWidthOffsetLocation, f);
    }

    public void setTexelHeightOffset(float f) {
        this.mTexelHeightOffset = f;
        setFloat(this.texelHeightOffsetLocation, f);
    }

    public void setMaskSize(float f, float f2) {
        this.mask2Size = new PointF(f, f2);
        setFloatVec2(this.mask2SizeLocation, new float[]{f, f2});
    }

    public void setIsBackCamera(boolean z) {
        this.isBackCamera = z;
        setInteger(this.backcameraLocation, z ? 1 : 0);
    }

    public void setBlurSize(float f) {
        this.mBlur2Size = f;
        setFloat(this.mBlur2SizeLocation, f);
    }

    public void setWidthAndHeightRatio(float f) {
        this.widthAndHeightRatio = f;
        float f2 = this.mask2Size.x;
        float f3 = this.mask2Size.y;
        if (f <= 1.0d) {
            f2 = this.mask2Size.x * f;
        } else {
            f3 = this.mask2Size.y / f;
        }
        float f4 = (1.0f - f2) * 0.5f;
        float f5 = (1.0f - f3) * 0.5f;
        float f6 = 1.0f - f4;
        float f7 = 1.0f - f5;
        Log.i("luca", "mask2RectLocation  left:" + f4 + " top:" + f5 + " right:" + f6 + " bottom:" + f7);
        setFloatVec4(this.mask2RectLocation, new float[]{f4, f5, f6, f7});
    }
}
