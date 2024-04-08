package com.picspool.lib.filter.gpu.newfilter;

import android.graphics.PointF;
import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageSlimFaceFilter extends GPUImageFilter {
    public static final String BEAUTY_FRAGMENT_SHADER = " precision highp float;      varying highp vec2 textureCoordinate;      uniform sampler2D inputImageTexture;      uniform highp float radius;      uniform highp vec2 point1;      uniform highp vec2 point2;      uniform highp vec2 point3;      uniform highp vec2 point4;      void main()      {        highp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);        gl_FragColor = vec4(textureColor.rgb, textureColor.w);        if(radius>0.0001){           if(point1.x==point2.x && point1.y==point2.y){              return;          }          if(point1.x==point2.x || point3.x==point4.x){              return;          }          highp vec2 cp = textureCoordinate.xy;          highp float a1 = (point2.y - point1.y)/(point2.x - point1.x);          highp float b1 = point1.y - a1 * point1.x;          highp float ds = distance(point1,point2);          float b3 = cp.y + 1.0/a1*cp.x;          float tx = (b3 - b1)/(a1 + 1.0/a1);          highp float ml = 0.0;          highp float dist = 0.0;          highp float sp = 0.0;          highp float dl = 0.0;          if(tx < point1.x || tx > point2.x){             dist = 1.0;          }else{             dist = abs(((a1)*cp.x - cp.y + b1)/sqrt(a1*a1 + 1.0));             sp = distance(point1,cp);             dl = sqrt(sp*sp - dist*dist);             if(dl > ds/2.0){                 dl = ds - dl;             }             ml = dl / (ds/2.0) * radius;          }          if(dist < ml){           highp float dm = dl/(ds/2.0);\n        if(dm > 0.5) dm = 0.5;        dm = sin(dm*3.1415926*0.5);\n        dm = dm*(1.0 - dist/ml);        dm = dm * radius/5.0;             float a = -1.0/a1;             float b = b3;             float as2 = 1.0 + a * a;             float bs2 = 2.0 * a * b - 2.0* cp.x - 2.0 * a*cp.y;             float cs2 = cp.x*cp.x + b * b - 2.0*b*cp.y + cp.y*cp.y - dm * dm;             float dist2 = bs2 * bs2 - 4.0 * as2 * cs2;             if(dist2<0.000001){                return;             }             float x1 = (-bs2 + sqrt(dist2))/(2.0*as2);             highp vec2 np = vec2(0.0);             if(x1 > cp.x){                float x2 = (-bs2 - sqrt(dist2))/(2.0*as2);                float y2 = a * x2 + b;                np = vec2(x2, y2);             }else{                float y1 = a * x1 + b;                np = vec2(x1, y1);             }             gl_FragColor = texture2D(inputImageTexture, np);           }else{              highp float a2 = (point4.y - point3.y)/(point4.x - point3.x);              highp float b2 = point3.y - a2 * point3.x;              b3 = cp.y + cp.x/a2;              tx = (b3 - b2)/(a2 + 1.0/a2);              if(tx < point3.x || tx > point4.x){                  return;              }              ds = distance(point3,point4);              dist = abs(((a2)*cp.x - cp.y + b2)/sqrt(a2*a2 + 1.0));              sp = distance(point3,cp);              dl = sqrt(sp*sp - dist*dist);              if(dl > ds/2.0){                 dl = ds - dl;              }              ml = dl / (ds/2.0) * radius;              if(dist < ml){                highp float dm = dl/(ds/2.0);\n           if(dm > 0.5) dm = 0.5;           dm = sin(dm*3.1415926*0.5);\n           dm = dm*(1.0 - dist/ml);           dm = dm * radius/5.0;                  float a = -1.0/a2;                 float b = b3;                 float as2 = 1.0 + a * a;                 float bs2 = 2.0 * a * b - 2.0* cp.x - 2.0 * a*cp.y;                 float cs2 = cp.x*cp.x + b * b - 2.0*b*cp.y + cp.y*cp.y - dm * dm;                 float dist2 = bs2 * bs2 - 4.0 * as2 * cs2;                 if(dist2<0.000001){                     return;                 }                 float x1 = (-bs2 + sqrt(dist2))/(2.0*as2);                 highp vec2 np = vec2(0);                 if(x1 < cp.x){                     float x2 = (-bs2 - sqrt(dist2))/(2.0*as2);                      float y2 = a * x2 + b;                      np = vec2(x2, y2);                 }else{                      float y1 = a * x1 + b;                      np = vec2(x1, y1);                 }                 gl_FragColor = texture2D(inputImageTexture, np);              }else{                  return;              }           }        }      }     ";
    private PointF point1;
    private PointF point2;
    private PointF point3;
    private PointF point4;
    private int pointLocation1;
    private int pointLocation2;
    private int pointLocation3;
    private int pointLocation4;
    private float radius;
    private int radiusLocation;

    public GPUImageSlimFaceFilter() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, BEAUTY_FRAGMENT_SHADER);
        this.point1 = new PointF(0.0f, 0.0f);
        this.point2 = new PointF(0.0f, 0.0f);
        this.point3 = new PointF(0.0f, 0.0f);
        this.point4 = new PointF(0.0f, 0.0f);
        this.radius = 0.08f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.radiusLocation = GLES20.glGetUniformLocation(getProgram(), "radius");
        setRadius(this.radius);
        this.pointLocation1 = GLES20.glGetUniformLocation(getProgram(), "point1");
        this.pointLocation2 = GLES20.glGetUniformLocation(getProgram(), "point2");
        this.pointLocation3 = GLES20.glGetUniformLocation(getProgram(), "point3");
        this.pointLocation4 = GLES20.glGetUniformLocation(getProgram(), "point4");
        setPoints(this.point1, this.point2, this.point3, this.point4);
    }

    public void setRadius(float f) {
        this.radius = f;
        setFloat(this.radiusLocation, f);
    }

    public void setPoints(PointF pointF, PointF pointF2, PointF pointF3, PointF pointF4) {
        this.point1 = pointF;
        this.point2 = pointF2;
        this.point3 = pointF3;
        this.point4 = pointF4;
        setPoint(this.pointLocation1, pointF);
        setPoint(this.pointLocation2, this.point2);
        setPoint(this.pointLocation3, this.point3);
        setPoint(this.pointLocation4, this.point4);
    }
}
