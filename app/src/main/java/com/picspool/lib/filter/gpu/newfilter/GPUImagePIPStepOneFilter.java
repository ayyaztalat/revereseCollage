package com.picspool.lib.filter.gpu.newfilter;

import android.graphics.PointF;
import android.graphics.RectF;
import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter;

/* loaded from: classes3.dex */
public class GPUImagePIPStepOneFilter extends GPUImageTwoInputFilter {
    private static final String PIP_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\nvarying highp vec2 textureCoordinate2;\nuniform sampler2D inputImageTexture;  \nuniform sampler2D inputImageTexture2; \nuniform highp int backcamera;\nuniform highp vec4 maskRect; \nuniform highp vec4 maskInnerRect; \nuniform highp float maskRatio; \nuniform highp float widthAndHeightRatio;\nuniform highp int isFilters;\nconst highp int GAUSSIAN_SAMPLES = 8;\nvarying highp vec2 blurCoordinates[GAUSSIAN_SAMPLES];\nuniform highp int isRotate270;\nhighp vec4 getBlurColor(highp vec4 textureColor)\n {\n\thighp vec3 sum = vec3(0.0);\n   highp vec4 fragColor=texture2D(inputImageTexture,textureCoordinate);\n    sum += texture2D(inputImageTexture, blurCoordinates[0]).rgb * 0.125;\n    sum += texture2D(inputImageTexture, blurCoordinates[1]).rgb * 0.125;\n    sum += texture2D(inputImageTexture, blurCoordinates[2]).rgb * 0.125;\n    sum += texture2D(inputImageTexture, blurCoordinates[3]).rgb * 0.125;\n    sum += texture2D(inputImageTexture, blurCoordinates[4]).rgb * 0.125;\n    sum += texture2D(inputImageTexture, blurCoordinates[5]).rgb * 0.125;\n    sum += texture2D(inputImageTexture, blurCoordinates[6]).rgb * 0.125;\n    sum += texture2D(inputImageTexture, blurCoordinates[7]).rgb * 0.125;\n\tgl_FragColor = vec4(sum,fragColor.a);\n     return gl_FragColor;\n} \nvoid main()\n{\n   highp vec4 srcColor = texture2D(inputImageTexture, textureCoordinate);\n       highp float cy = 0.0;\n       highp float cx = 0.0;\n           if(backcamera>0){\n                  if(isRotate270 > 0){                       cy = 1.0 - (textureCoordinate2.x - 0.125)/0.75;\n                       cx = textureCoordinate2.y;\n                   }else{                       cy = (textureCoordinate2.x - 0.125)/0.75;\n                       cx = 1.0 - textureCoordinate2.y;\n                   }           }else{\n               cy = 1.0 - (textureCoordinate2.x - 0.125)/0.75;\n               cx = 1.0 - textureCoordinate2.y;\n           }\n           if(textureCoordinate2.x >= 0.125){       highp vec4 maskColor;       if(isFilters > 0){           maskColor = texture2D(inputImageTexture2, vec2(1.0 - cx, cy));\n       }else{           maskColor = texture2D(inputImageTexture2, vec2(cx, cy));\n       }           if(maskColor.a > 0.0){\n               if(backcamera > 0){\n                   if(maskInnerRect.w - maskInnerRect.y >= maskInnerRect.z - maskInnerRect.x){\n                       if(isRotate270 > 0){                           cx = 1.0 - (((1.0 - textureCoordinate.x - 0.125 - maskInnerRect.y*0.75 )/((maskInnerRect.w - maskInnerRect.y)*0.75))*0.75 + 0.125);\n                           cy = (((textureCoordinate.y - maskInnerRect.x)/(maskInnerRect.z - maskInnerRect.x)) * ((maskInnerRect.z - maskInnerRect.x)/(maskInnerRect.w - maskInnerRect.y)) + (1.0 - (maskInnerRect.z - maskInnerRect.x)/(maskInnerRect.w - maskInnerRect.y))/2.0);\n                       }else{                           cx = ((textureCoordinate.x - 0.125 - maskInnerRect.y*0.75 )/((maskInnerRect.w - maskInnerRect.y)*0.75))*0.75 + 0.125;\n                           cy = 1.0 - (((1.0 - textureCoordinate.y - maskInnerRect.x)/(maskInnerRect.z - maskInnerRect.x)) * ((maskInnerRect.z - maskInnerRect.x)/(maskInnerRect.w - maskInnerRect.y)) + (1.0 - (maskInnerRect.z - maskInnerRect.x)/(maskInnerRect.w - maskInnerRect.y))/2.0);\n                       }                   }else{\n                       if(isRotate270 > 0){                           cx = 1.0 - (((1.0 - textureCoordinate.x - 0.125 - maskInnerRect.y*0.75 )/((maskInnerRect.w - maskInnerRect.y)*0.75)) * ((maskInnerRect.w - maskInnerRect.y)/(maskInnerRect.z - maskInnerRect.x))*0.75+(1.0 - ((maskInnerRect.w - maskInnerRect.y)/(maskInnerRect.z - maskInnerRect.x))*0.75)/2.0);\n                           cy = ((textureCoordinate.y - maskInnerRect.x)/(maskInnerRect.z - maskInnerRect.x));\n                       }else{                           cx = ((textureCoordinate.x - 0.125 - maskInnerRect.y*0.75 )/((maskInnerRect.w - maskInnerRect.y)*0.75)) * ((maskInnerRect.w - maskInnerRect.y)/(maskInnerRect.z - maskInnerRect.x))*0.75+(1.0 - ((maskInnerRect.w - maskInnerRect.y)/(maskInnerRect.z - maskInnerRect.x))*0.75)/2.0;\n                           cy = 1.0 - ((1.0 - textureCoordinate.y - maskInnerRect.x)/(maskInnerRect.z - maskInnerRect.x));\n                       }                   }\n               }else{                   if(maskInnerRect.w - maskInnerRect.y >= maskInnerRect.z - maskInnerRect.x){                       cx = ((textureCoordinate.x - (1.0 - 0.125 - maskInnerRect.w*0.75 ))/((maskInnerRect.w - maskInnerRect.y)*0.75))*0.75 + 0.125;\n                       cy = 1.0 - (((1.0 - textureCoordinate.y - maskInnerRect.x)/(maskInnerRect.z - maskInnerRect.x)) * ((maskInnerRect.z - maskInnerRect.x)/(maskInnerRect.w - maskInnerRect.y)) + (1.0 - (maskInnerRect.z - maskInnerRect.x)/(maskInnerRect.w - maskInnerRect.y))/2.0);\n                   }else{\n                       cx = ((textureCoordinate.x - (1.0 - 0.125 - maskInnerRect.w*0.75))/((maskInnerRect.w - maskInnerRect.y)*0.75)) * ((maskInnerRect.w - maskInnerRect.y)/(maskInnerRect.z - maskInnerRect.x))*0.75+(1.0 - ((maskInnerRect.w - maskInnerRect.y)/(maskInnerRect.z - maskInnerRect.x)))/2.0*0.75+ 0.125;\n                       cy = 1.0 - ((1.0 - textureCoordinate.y - maskInnerRect.x)/(maskInnerRect.z - maskInnerRect.x));\n                   }\n               }           highp vec4 smallColor = texture2D(inputImageTexture, vec2(cx, cy));\n           gl_FragColor = vec4(smallColor.rgb,smallColor.a);          }else{           highp vec4 blurColor = srcColor;\n           blurColor = getBlurColor(blurColor);\n           gl_FragColor = vec4(blurColor.rgb,srcColor.a);\n          }       }else{\n           highp vec4 blurColor = srcColor;\n           blurColor = getBlurColor(blurColor);\n           gl_FragColor = vec4(blurColor.rgb,srcColor.a);\n     }\n}\n";
    private static final String VERTEX_SHADER = "uniform sampler2D inputImageTexture;  \nuniform sampler2D inputImageTexture2; \nattribute vec4 position;\nattribute vec4 inputTextureCoordinate;\nattribute vec4 inputTextureCoordinate2;\nvarying vec2 textureCoordinate;\nvarying vec2 textureCoordinate2;\nconst int GAUSSIAN_SAMPLES = 8;\nvarying highp vec2 blurCoordinates[GAUSSIAN_SAMPLES];\n uniform highp float texelWidthOffset; \n uniform highp float texelHeightOffset; \n uniform highp float blurSize;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n    textureCoordinate2 = inputTextureCoordinate2.xy;\n\t// Calculate the positions for the blur\n\tint multiplier = 0;\n\tvec2 blurStep;\n   vec2 singleStepOffset = vec2(texelHeightOffset, texelWidthOffset);\n   blurStep = singleStepOffset * blurSize;\n   blurCoordinates[0] = vec2(inputTextureCoordinate.x - blurStep.x,inputTextureCoordinate.y - blurStep.y);   blurCoordinates[1] = vec2(inputTextureCoordinate.x,inputTextureCoordinate.y - blurStep.y);   blurCoordinates[2] = vec2(inputTextureCoordinate.x + blurStep.x,inputTextureCoordinate.y - blurStep.y);   blurCoordinates[3] = vec2(inputTextureCoordinate.x - blurStep.x,inputTextureCoordinate.y);   blurCoordinates[4] = vec2(inputTextureCoordinate.x + blurStep.x,inputTextureCoordinate.y);   blurCoordinates[5] = vec2(inputTextureCoordinate.x - blurStep.x,inputTextureCoordinate.y + blurStep.y);   blurCoordinates[6] = vec2(inputTextureCoordinate.x,inputTextureCoordinate.y + blurStep.y);   blurCoordinates[7] = vec2(inputTextureCoordinate.x + blurStep.x,inputTextureCoordinate.y + blurStep.y);}";
    private int backcameraLocation;
    private boolean isBackCamera;
    private int isFilters;
    private int isFiltersLocation;
    private float mBlurSize;
    private int mBlurSizeLocation;
    private int mIsRotate270;
    private int mIsRotate270Location;
    private float mTexelHeightOffset;
    private float mTexelWidthOffset;
    private RectF maskInnerRect;
    private int maskInnerRectLocation;
    private float maskRatio;
    private int maskRatioLocation;
    private RectF maskRect;
    private int maskRectLocation;
    private PointF maskSize;
    private int maskSizeLocation;
    private int ratioLocation;
    private int texelHeightOffsetLocation;
    private int texelWidthOffsetLocation;
    private float widthAndHeightRatio;

    public GPUImagePIPStepOneFilter() {
        super(VERTEX_SHADER, PIP_FRAGMENT_SHADER);
        this.mBlurSize = 6.0f;
        this.mIsRotate270 = 0;
        this.maskSize = new PointF(1.0f, 1.0f);
        this.maskRect = new RectF(0.0f, 0.0f, 1.0f, 1.0f);
        this.maskInnerRect = new RectF(0.0f, 0.0f, 1.0f, 1.0f);
        this.maskRatio = 1.0f;
        this.isBackCamera = true;
        this.widthAndHeightRatio = 1.0f;
        this.isFilters = 0;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter, com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.maskSizeLocation = GLES20.glGetUniformLocation(getProgram(), "maskSize");
        this.maskRectLocation = GLES20.glGetUniformLocation(getProgram(), "maskRect");
        this.maskInnerRectLocation = GLES20.glGetUniformLocation(getProgram(), "maskInnerRect");
        this.maskRatioLocation = GLES20.glGetUniformLocation(getProgram(), "maskRatio");
        this.backcameraLocation = GLES20.glGetUniformLocation(getProgram(), "backcamera");
        this.ratioLocation = GLES20.glGetUniformLocation(getProgram(), "widthAndHeightRatio");
        this.texelWidthOffsetLocation = GLES20.glGetUniformLocation(getProgram(), "texelWidthOffset");
        this.texelHeightOffsetLocation = GLES20.glGetUniformLocation(getProgram(), "texelHeightOffset");
        this.mBlurSizeLocation = GLES20.glGetUniformLocation(getProgram(), "blurSize");
        this.isFiltersLocation = GLES20.glGetUniformLocation(getProgram(), "isFilters");
        this.mIsRotate270Location = GLES20.glGetUniformLocation(getProgram(), "isRotate270");
        setIsRotate270(this.mIsRotate270);
        setBlurSize(this.mBlurSize);
        setMaskInnerRect(this.maskInnerRect.left, this.maskInnerRect.top, this.maskInnerRect.right, this.maskInnerRect.bottom);
        setMaskContenteRatio(this.maskRatio);
        setIsBackCamera(this.isBackCamera);
        setWidthAndHeightRatio(this.widthAndHeightRatio);
        setTexelWidthOffset(this.mTexelWidthOffset);
        setTexelHeightOffset(this.mTexelHeightOffset);
        setIsFilters(this.isFilters);
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

    public void setMaskInnerRect(float f, float f2, float f3, float f4) {
        this.maskInnerRect = new RectF(f, f2, f3, f4);
        setFloatVec4(this.maskInnerRectLocation, new float[]{f, f2, f3, f4});
    }

    public void setMaskContenteRatio(float f) {
        this.maskRatio = f;
        setFloat(this.maskRatioLocation, f);
    }

    public void setMaskSize(float f, float f2) {
        this.maskSize = new PointF(f, f2);
        setFloatVec2(this.maskSizeLocation, new float[]{f, f2});
    }

    public void setIsBackCamera(boolean z) {
        this.isBackCamera = z;
        setInteger(this.backcameraLocation, z ? 1 : 0);
        setWidthAndHeightRatio(this.widthAndHeightRatio);
    }

    public void setBlurSize(float f) {
        this.mBlurSize = f;
        setFloat(this.mBlurSizeLocation, f);
    }

    public void setIsRotate270(int i) {
        this.mIsRotate270 = i;
        setInteger(this.mIsRotate270Location, i);
    }

    public void setWidthAndHeightRatio(float f) {
        this.widthAndHeightRatio = f;
        setFloat(this.ratioLocation, f);
        float f2 = this.maskSize.x;
        float f3 = this.maskSize.y;
        if (f <= 1.0d) {
            f2 = this.maskSize.x * f;
        } else {
            f3 = this.maskSize.y / f;
        }
        float f4 = (1.0f - f2) * 0.5f;
        float f5 = (1.0f - f3) * 0.5f;
        setFloatVec4(this.maskRectLocation, new float[]{f4, f5, 1.0f - f4, 1.0f - f5});
        setFloat(this.maskRatioLocation, this.maskRatio);
    }
}
