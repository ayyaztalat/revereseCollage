package com.photo.editor.square.splash.rate;

import android.os.Build;

/* loaded from: classes2.dex */
public class BpRateEntity {
    private int[] noRateOs;
    private int rateUser = 1;
    private int scoreRate = 0;
    private int ratePosition = 1;
    private int rateInternal = 0;
    private int rateStartTimes = 5;
    private int rateShowToast = 100;
    private int maxShowCount = -1;

    public void setNoRateOs(int[] iArr) {
        this.noRateOs = iArr;
    }

    public int getMaxShowCount() {
        return this.maxShowCount;
    }

    public void setMaxShowCount(int i) {
        this.maxShowCount = i;
    }

    public int getRateStartTimes() {
        return this.rateStartTimes;
    }

    public void setRateStartTimes(int i) {
        this.rateStartTimes = i;
    }

    public int getRateUser() {
        return this.rateUser;
    }

    public void setRateUser(int i) {
        this.rateUser = i;
    }

    public int getScoreRate() {
        return this.scoreRate;
    }

    public void setScoreRate(int i) {
        this.scoreRate = i;
    }

    public int getRatePosition() {
        return this.ratePosition;
    }

    public void setRatePosition(int i) {
        this.ratePosition = i;
    }

    public int getRateInternal() {
        return this.rateInternal;
    }

    public void setRateInternal(int i) {
        this.rateInternal = i;
    }

    public int getRateShowToast() {
        return this.rateShowToast;
    }

    public void setRateShowToast(int i) {
        this.rateShowToast = i;
    }

    public String toString() {
        return "rateUser:" + this.rateUser + " scoreRate:" + this.scoreRate + " ratePosition:" + this.ratePosition + " rateInternal:" + this.rateInternal;
    }

    public boolean isOSSupport() {
        try {
            if (this.noRateOs != null) {
                int i = Build.VERSION.SDK_INT;
                for (int i2 : this.noRateOs) {
                    if (i2 == i) {
                        return false;
                    }
                }
                return true;
            }
            return true;
        } catch (Throwable th) {
            th.printStackTrace();
            return true;
        }
    }
}
