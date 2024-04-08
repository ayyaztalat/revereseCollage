package com.photo.editor.square.splash.view.glitch;

import android.opengl.GLES20;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes2.dex */
public class IL19 extends GPUImageFilter implements IL123456 {

    /* renamed from: f1 */
    public static final String f1632f1 = "        precision highp float;\nuniform sampler2D inputImageTexture;\nvarying vec2 textureCoordinate;\nuniform vec2 uTouch;\nuniform float uTime;\nuniform vec2 uResolution;\nuniform float density;\nfloat width = 5.0;\nconst float hatch_1 = 0.8;\nconst float hatch_2 = 0.6;\nconst float hatch_3 = 0.3;\nconst float hatch_4 = 0.15;\n#define GREY_HATCHES\n#define COLOUR_HATCHES\n#ifdef GREY_HATCHES\n    float hatch_1_brightness = 0.8;\n    float hatch_2_brightness = 0.6;\n    float hatch_3_brightness = 0.3;\n    float hatch_4_brightness = 0.0;\n#else\n    float hatch_1_brightness = 0.0;\n    float hatch_2_brightness = 0.0;\n    float hatch_3_brightness = 0.0;\n    float hatch_4_brightness = 0.0;\n#endif\nconst float d = 1.0; // kernel offset\n    float lookup(vec2 p, float dx, float dy)\n    {\n        vec2 uv = (p.xy + vec2(dx * d, dy * d)) / uResolution.xy;\n        vec4 c = texture2D(inputImageTexture, (uv));\n        return 0.2126*c.r + 0.7152*c.g + 0.0722*c.b;\n    }\n    void main() {\n        float ratio = uResolution.y / uResolution.x;\n        float coordX = textureCoordinate.x;\n        float coordY = textureCoordinate.y;\n        vec2 dstCoord = vec2(coordX, coordY);\n        vec2 srcCoord = vec2(coordX, coordY);\n        vec2 uv = srcCoord.xy;\n        vec3 res = vec3(1.0, 1.0, 1.0);\n        vec4 tex = texture2D(inputImageTexture, (uv));\n        float brightness = (0.2126*tex.x) + (0.7152*tex.y) + (0.0722*tex.z);\n#ifdef COLOUR_HATCHES\n        float dimmestChannel = min( min( tex.r, tex.g ), tex.b );\n        float brightestChannel = max( max( tex.r, tex.g ), tex.b );\n        float delta = brightestChannel - dimmestChannel;\n        if ( delta > 0.1 )\n            tex = tex * ( 1.0 / brightestChannel );\n        else\n            tex.rgb = vec3(1.0,1.0,1.0);\n#endif // COLOUR_HATCHES\n        if (brightness < hatch_1){\n            if (mod(textureCoordinate.x * uResolution.x + textureCoordinate.y * uResolution.y, density) <= width)\n{\n#ifdef COLOUR_HATCHES\n                res = vec3(tex.rgb * hatch_1_brightness);\n#else\n                res = vec3(hatch_1_brightness);\n#endif\n            }\n        }\n        if (brightness < hatch_2)\n        {\n            if (mod(textureCoordinate.x * uResolution.x - textureCoordinate.y * uResolution.y, density) <= width)\n            {\n#ifdef COLOUR_HATCHES\n                res = vec3(tex.rgb * hatch_2_brightness);\n#else\n                res = vec3(hatch_2_brightness);\n#endif\n            }\n        }\n        if (brightness < hatch_3)\n        {\n            if (mod(textureCoordinate.x * uResolution.x + textureCoordinate.y * uResolution.y - (density*0.5), density) <= width)\n            {\n#ifdef COLOUR_HATCHES\n                res = vec3(tex.rgb * hatch_3_brightness);\n#else\n                res = vec3(hatch_3_brightness);\n#endif\n            }\n        }\n        if (brightness < hatch_4)\n        {\n            if (mod(textureCoordinate.x * uResolution.x - textureCoordinate.y * uResolution.y - (density*0.5), density) <= width)\n            {\n#ifdef COLOUR_HATCHES\n                res = vec3(tex.rgb * hatch_4_brightness);\n#else\n                res = vec3(hatch_4_brightness);\n#endif\n            }\n        }\n        vec2 p = textureCoordinate * uResolution;\n        float gx = 0.0;\n        gx += -1.0 * lookup(p, -1.0, -1.0);\n        gx += -2.0 * lookup(p, -1.0,  0.0);\n        gx += -1.0 * lookup(p, -1.0,  1.0);\n        gx +=  1.0 * lookup(p,  1.0, -1.0);\n        gx +=  2.0 * lookup(p,  1.0,  0.0);\n        gx +=  1.0 * lookup(p,  1.0,  1.0);\n        float gy = 0.0;\n        gy += -1.0 * lookup(p, -1.0, -1.0);\n        gy += -2.0 * lookup(p,  0.0, -1.0);\n        gy += -1.0 * lookup(p,  1.0, -1.0);\n        gy +=  1.0 * lookup(p, -1.0,  1.0);\n        gy +=  2.0 * lookup(p,  0.0,  1.0);\n        gy +=  1.0 * lookup(p,  1.0,  1.0);\n        float g = gx*gx + gy*gy;\n        res *= (1.0-g);\n        gl_FragColor = vec4(res, 1.0);\n    }";
    private float density;
    private int densityPosition;
    private float[] resolution;
    private float timeS;
    private int timeSLocation;

    /* renamed from: touch  reason: collision with root package name */
    private float[] f2032touch;
    private int uResolutionPosition;
    private int uTouchPosition;

    public IL19() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, f1632f1);
        this.resolution = new float[]{720.0f, 720.0f};
        this.f2032touch = new float[]{720.0f, 720.0f};
        this.density = 5.0f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.timeSLocation = GLES20.glGetUniformLocation(getProgram(), "uTime");
        this.uResolutionPosition = GLES20.glGetUniformLocation(getProgram(), "uResolution");
        this.uTouchPosition = GLES20.glGetUniformLocation(getProgram(), "uTouch");
        this.densityPosition = GLES20.glGetUniformLocation(getProgram(), "density");
    }

    public void setTime(float f) {
        this.timeS = f;
    }

    @Override // com.photo.editor.square.splash.view.glitch.IL123456
    public void il1(float[] fArr) {
        this.resolution = fArr;
    }

    @Override // com.photo.editor.square.splash.view.glitch.IL123456
    public void il2(float[] fArr) {
        this.f2032touch = fArr;
    }

    @Override // com.photo.editor.square.splash.view.glitch.IL123456
    public void il3(float f) {
        this.density = (f * 9.0f) + 1.0f;
    }

    @Override // com.photo.editor.square.splash.view.glitch.IL123456
    public GPUImageFilter il4() {
        IL19 il19 = new IL19();
        il19.density = this.density;
        return il19;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void draw(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        GLES20.glUseProgram(this.mGLProgId);
        runPendingOnDrawTasks();
        GLES20.glGetError();
        if (this.mIsInitialized) {
            if (i != -1) {
                GLES20.glActiveTexture(33984);
                GLES20.glBindTexture(3553, i);
                GLES20.glUniform1i(this.mGLUniformTexture, 0);
            }
            onDrawArraysPre();
            GLES20.glGetError();
            floatBuffer.position(0);
            GLES20.glVertexAttribPointer(this.mGLAttribPosition, 2, 5126, false, 0, (Buffer) floatBuffer);
            GLES20.glEnableVertexAttribArray(this.mGLAttribPosition);
            floatBuffer2.position(0);
            GLES20.glVertexAttribPointer(this.mGLAttribTextureCoordinate, 2, 5126, false, 0, (Buffer) floatBuffer2);
            GLES20.glEnableVertexAttribArray(this.mGLAttribTextureCoordinate);
            GLES20.glUniform1f(this.timeSLocation, this.timeS);
            GLES20.glUniform1f(this.densityPosition, this.density);
            GLES20.glUniform2fv(this.uResolutionPosition, 1, FloatBuffer.wrap(this.resolution));
            GLES20.glUniform2fv(this.uTouchPosition, 1, FloatBuffer.wrap(this.f2032touch));
            GLES20.glGetError();
            GLES20.glDrawArrays(5, 0, 4);
            GLES20.glDisableVertexAttribArray(this.mGLAttribPosition);
            GLES20.glDisableVertexAttribArray(this.mGLAttribTextureCoordinate);
            GLES20.glBindTexture(3553, 0);
        }
    }
}
