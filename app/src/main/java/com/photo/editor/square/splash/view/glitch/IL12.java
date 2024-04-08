package com.photo.editor.square.splash.view.glitch;

import android.opengl.GLES20;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes2.dex */
public class IL12 extends GPUImageFilter implements IL123456 {
    public static final String fragment = "precision highp float;\nuniform sampler2D inputImageTexture;\nvarying vec2 textureCoordinate;\nuniform vec2 uTouch;\nuniform float uTime;\nuniform vec2 uResolution;\nfloat random(vec2 c){\n    return fract(sin(dot(c.xy ,vec2(12.9898,78.233))) * 43758.5453);\n}\nvec3 mod289(vec3 x) {\n    return x - floor(x * (1.0 / 289.0)) * 289.0;\n}\nvec4 mod289(vec4 x) {\n    return x - floor(x * (1.0 / 289.0)) * 289.0;\n}\nvec4 permute(vec4 x) {\n    return mod289(((x*34.0)+1.0)*x);\n}\nvec4 taylorInvSqrt(vec4 r) {\n    return 1.79284291400159 - 0.85373472095314 * r;\n}\nfloat snoise3(vec3 v) {\nconst vec2  C = vec2(1.0/6.0, 1.0/3.0);\nconst vec4  D = vec4(0.0, 0.5, 1.0, 2.0);\n// First corner\nvec3 i  = floor(v + dot(v, C.yyy));\nvec3 x0 =   v - i + dot(i, C.xxx);\n// Other corners\nvec3 g = step(x0.yzx, x0.xyz);\nvec3 l = 1.0 - g;\nvec3 i1 = min( g.xyz, l.zxy );\nvec3 i2 = max( g.xyz, l.zxy );\nvec3 x1 = x0 - i1 + C.xxx;\nvec3 x2 = x0 - i2 + C.yyy; // 2.0*C.x = 1/3 = C.y\nvec3 x3 = x0 - D.yyy;      // -1.0+3.0*C.x = -0.5 = -D.y\n// Permutations\ni = mod289(i);\nvec4 p = permute( permute( permute(\ni.z + vec4(0.0, i1.z, i2.z, 1.0 ))\n+ i.y + vec4(0.0, i1.y, i2.y, 1.0 ))\n+ i.x + vec4(0.0, i1.x, i2.x, 1.0 ));\nfloat n_ = 0.142857142857; // 1.0/7.0\nvec3  ns = n_ * D.wyz - D.xzx;\nvec4 j = p - 49.0 * floor(p * ns.z * ns.z);  //  mod(p,7*7)\nvec4 x_ = floor(j * ns.z);\nvec4 y_ = floor(j - 7.0 * x_ );    // mod(j,N)\nvec4 x = x_ *ns.x + ns.yyyy;\nvec4 y = y_ *ns.x + ns.yyyy;\nvec4 h = 1.0 - abs(x) - abs(y);\nvec4 b0 = vec4( x.xy, y.xy );\nvec4 b1 = vec4( x.zw, y.zw );\nvec4 s0 = floor(b0)*2.0 + 1.0;\nvec4 s1 = floor(b1)*2.0 + 1.0;\nvec4 sh = -step(h, vec4(0.0));\nvec4 a0 = b0.xzyw + s0.xzyw*sh.xxyy ;\nvec4 a1 = b1.xzyw + s1.xzyw*sh.zzww ;\nvec3 p0 = vec3(a0.xy,h.x);\nvec3 p1 = vec3(a0.zw,h.y);\nvec3 p2 = vec3(a1.xy,h.z);\nvec3 p3 = vec3(a1.zw,h.w);\nvec4 norm = taylorInvSqrt(vec4(dot(p0,p0), dot(p1,p1), dot(p2, p2), dot(p3,p3)));\np0 *= norm.x;\np1 *= norm.y;\np2 *= norm.z;\np3 *= norm.w;\nvec4 m = max(0.6 - vec4(dot(x0,x0), dot(x1,x1), dot(x2,x2), dot(x3,x3)), 0.0);\nm = m * m;\nreturn 42.0 * dot( m*m, vec4( dot(p0,x0), dot(p1,x1),\n        dot(p2,x2), dot(p3,x3) ) );\n}\nconst float interval = 3.0;\nvoid main(void){\nfloat strength = smoothstep(interval * 0.5, interval, interval - mod(uTime, interval));\nvec2 shake = vec2(strength * 8.0 + 0.5) * vec2(\n        random(vec2(uTime)) * 2.0 - 1.0,\n        random(vec2(uTime * 2.0)) * 2.0 - 1.0\n) / uResolution;\nfloat y = textureCoordinate.y * uResolution.y;\nfloat rgbWave = (\n        snoise3(vec3(0.0, y * 0.01, uTime * 400.0)) * (2.0 + strength * 32.0)\n                * snoise3(vec3(0.0, y * 0.02, uTime * 200.0)) * (1.0 + strength * 4.0)\n                + step(0.9995, sin(y * 0.005 + uTime * 1.6)) * 12.0\n                + step(0.9999, sin(y * 0.005 + uTime * 2.0)) * -18.0\n) / uResolution.x;\n float rgbDiff = (6.0 + sin(uTime * 500.0 + textureCoordinate.y * 40.0) * (20.0 * strength + 1.0)) / uResolution.x;\nfloat rgbUvX = textureCoordinate.x + rgbWave;\nfloat r = texture2D(inputImageTexture, vec2(rgbUvX + rgbDiff, textureCoordinate.y) + shake).r;\nfloat g = texture2D(inputImageTexture, vec2(rgbUvX, textureCoordinate.y) + shake).g;\nfloat b = texture2D(inputImageTexture, vec2(rgbUvX - rgbDiff, textureCoordinate.y) + shake).b;\nfloat whiteNoise = (random(textureCoordinate + mod(uTime, 10.0)) * 2.0 - 1.0) * (0.15 + strength * 0.15);\nfloat bnTime = floor(uTime * 20.0) * 200.0;\nfloat noiseX = step((snoise3(vec3(0.0, textureCoordinate.x * 3.0, bnTime)) + 1.0) / 2.0, 0.12 + strength * 0.3);\nfloat noiseY = step((snoise3(vec3(0.0, textureCoordinate.y * 3.0, bnTime)) + 1.0) / 2.0, 0.12 + strength * 0.3);\nfloat bnMask = noiseX * noiseY;\nfloat bnUvX = textureCoordinate.x + sin(bnTime) * 0.2 + rgbWave;\nfloat bnR = texture2D(inputImageTexture, vec2(bnUvX + rgbDiff, textureCoordinate.y)).r * bnMask;\nfloat bnG = texture2D(inputImageTexture, vec2(bnUvX, textureCoordinate.y)).g * bnMask;\nfloat bnB = texture2D(inputImageTexture, vec2(bnUvX - rgbDiff, textureCoordinate.y)).b * bnMask;\nvec4 blockNoise = vec4(bnR, bnG, bnB, 1.0);\nfloat bnTime2 = floor(uTime * 25.0) * 300.0;\nfloat noiseX2 = step((snoise3(vec3(0.0, textureCoordinate.x * 2.0, bnTime2)) + 1.0) / 2.0, 0.12 + strength * 0.5);\nfloat noiseY2 = step((snoise3(vec3(0.0, textureCoordinate.y * 8.0, bnTime2)) + 1.0) / 2.0, 0.12 + strength * 0.3);\nfloat bnMask2 = noiseX2 * noiseY2;\nfloat bnR2 = texture2D(inputImageTexture, vec2(bnUvX + rgbDiff, textureCoordinate.y)).r * bnMask2;\nfloat bnG2 = texture2D(inputImageTexture, vec2(bnUvX, textureCoordinate.y)).g * bnMask2;\nfloat bnB2 = texture2D(inputImageTexture, vec2(bnUvX - rgbDiff, textureCoordinate.y)).b * bnMask2;\nvec4 blockNoise2 = vec4(bnR2, bnG2, bnB2, 1.0);\nfloat waveNoise = (sin(textureCoordinate.y * 1200.0) + 1.0) / 2.0 * (0.15 + strength * 0.2);\ngl_FragColor = vec4(r, g, b, 1.0) * (1.0 - bnMask - bnMask2) + (whiteNoise + blockNoise + blockNoise2 - waveNoise);\n}";
    private float[] resolution;
    private float timeS;
    private int timeSLocation;

    /* renamed from: touch  reason: collision with root package name */
    private float[] f2028touch;
    private int uResolutionPosition;
    private int uTouchPosition;

    public IL12() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, fragment);
        this.resolution = new float[]{720.0f, 720.0f};
        this.f2028touch = new float[]{720.0f, 720.0f};
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.timeSLocation = GLES20.glGetUniformLocation(getProgram(), "uTime");
        this.uResolutionPosition = GLES20.glGetUniformLocation(getProgram(), "uResolution");
        this.uTouchPosition = GLES20.glGetUniformLocation(getProgram(), "uTouch");
    }

    /* renamed from: f1 */
    public void m81f1(float f) {
        this.timeS = f;
    }

    @Override // com.photo.editor.square.splash.view.glitch.IL123456
    public void il1(float[] fArr) {
        this.resolution = fArr;
    }

    @Override // com.photo.editor.square.splash.view.glitch.IL123456
    public void il2(float[] fArr) {
        this.f2028touch = fArr;
    }

    @Override // com.photo.editor.square.splash.view.glitch.IL123456
    public void il3(float f) {
        m81f1(f * 3.0f);
    }

    @Override // com.photo.editor.square.splash.view.glitch.IL123456
    public GPUImageFilter il4() {
        IL12 il12 = new IL12();
        il12.timeS = this.timeS;
        return il12;
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
            GLES20.glUniform2fv(this.uResolutionPosition, 1, FloatBuffer.wrap(this.resolution));
            GLES20.glUniform2fv(this.uTouchPosition, 1, FloatBuffer.wrap(this.f2028touch));
            GLES20.glGetError();
            GLES20.glDrawArrays(5, 0, 4);
            GLES20.glDisableVertexAttribArray(this.mGLAttribPosition);
            GLES20.glDisableVertexAttribArray(this.mGLAttribTextureCoordinate);
            GLES20.glBindTexture(3553, 0);
        }
    }
}
