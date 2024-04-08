package com.picspool.lib.sticker.util;

import android.os.SystemClock;

/* loaded from: classes3.dex */
public class DM_Mover implements Runnable {
    static final float COEFFICIENT_OF_RESTITUTION = 0.75f;
    static final long JUMBLE_EVERYTHING_DELAY = 15000;
    static final float MAX_VELOCITY = 8000.0f;
    static final float SPEED_OF_GRAVITY = 150.0f;
    private DMRenderable[] mBMRenderables;
    private long mLastJumbleTime;
    private long mLastTime;
    private int mViewHeight;
    private int mViewWidth;

    @Override // java.lang.Runnable
    public void run() {
        if (this.mBMRenderables == null) {
            return;
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        long j = this.mLastTime;
        float f = ((float) j) > 0.0f ? ((float) (uptimeMillis - j)) / 1000.0f : 0.0f;
        this.mLastTime = uptimeMillis;
        int i = 0;
        boolean z = uptimeMillis - this.mLastJumbleTime > JUMBLE_EVERYTHING_DELAY;
        if (z) {
            this.mLastJumbleTime = uptimeMillis;
        }
        while (true) {
            DMRenderable[] dMRenderableArr = this.mBMRenderables;
            if (i >= dMRenderableArr.length) {
                return;
            }
            DMRenderable dMRenderable = dMRenderableArr[i];
            if (z) {
                dMRenderable.velocityX += 4000.0f - ((float) (Math.random() * 8000.0d));
                dMRenderable.velocityY += 4000.0f - ((float) (Math.random() * 8000.0d));
            }
            dMRenderable.f1986x += dMRenderable.velocityX * f;
            dMRenderable.f1987y += dMRenderable.velocityY * f;
            dMRenderable.f1988z += dMRenderable.velocityZ * f;
            dMRenderable.velocityY -= SPEED_OF_GRAVITY * f;
            if ((dMRenderable.f1986x < 0.0f && dMRenderable.velocityX < 0.0f) || (dMRenderable.f1986x > this.mViewWidth - dMRenderable.width && dMRenderable.velocityX > 0.0f)) {
                dMRenderable.velocityX = (-dMRenderable.velocityX) * 0.75f;
                dMRenderable.f1986x = Math.max(0.0f, Math.min(dMRenderable.f1986x, this.mViewWidth - dMRenderable.width));
                if (Math.abs(dMRenderable.velocityX) < 0.1f) {
                    dMRenderable.velocityX = 0.0f;
                }
            }
            if ((dMRenderable.f1987y < 0.0f && dMRenderable.velocityY < 0.0f) || (dMRenderable.f1987y > this.mViewHeight - dMRenderable.height && dMRenderable.velocityY > 0.0f)) {
                dMRenderable.velocityY = (-dMRenderable.velocityY) * 0.75f;
                dMRenderable.f1987y = Math.max(0.0f, Math.min(dMRenderable.f1987y, this.mViewHeight - dMRenderable.height));
                if (Math.abs(dMRenderable.velocityY) < 0.1f) {
                    dMRenderable.velocityY = 0.0f;
                }
            }
            i++;
        }
    }

    public void setRenderables(DMRenderable[] dMRenderableArr) {
        this.mBMRenderables = dMRenderableArr;
    }

    public void setViewSize(int i, int i2) {
        this.mViewHeight = i2;
        this.mViewWidth = i;
    }
}
