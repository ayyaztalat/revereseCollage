package com.picspool.lib.filter.cpu.normal;

import java.util.Random;
import com.picspool.lib.filter.cpu.father.PointFilter;
import com.picspool.lib.filter.cpu.util.PixelUtils;

/* loaded from: classes3.dex */
public class NoiseFilter extends PointFilter {
    public static final int GAUSSIAN = 0;
    public static final int UNIFORM = 1;
    private int amount = 25;
    private int distribution = 1;
    private boolean monochrome = false;
    private float density = 1.0f;
    private Random randomNumbers = new Random();

    public String toString() {
        return "Stylize/Add Noise...";
    }

    public void setAmount(int i) {
        this.amount = i;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setDistribution(int i) {
        this.distribution = i;
    }

    public int getDistribution() {
        return this.distribution;
    }

    public void setMonochrome(boolean z) {
        this.monochrome = z;
    }

    public boolean getMonochrome() {
        return this.monochrome;
    }

    public void setDensity(float f) {
        this.density = f;
    }

    public float getDensity() {
        return this.density;
    }

    private int random(int i) {
        int nextGaussian = i + ((int) ((this.distribution == 0 ? this.randomNumbers.nextGaussian() : (this.randomNumbers.nextFloat() * 2.0f) - 1.0f) * this.amount));
        if (nextGaussian < 0) {
            return 0;
        }
        if (nextGaussian > 255) {
            return 255;
        }
        return nextGaussian;
    }

    @Override // com.picspool.lib.filter.cpu.father.PointFilter
    public int filterRGB(int i, int i2, int i3) {
        int random;
        int random2;
        int random3;
        if (this.randomNumbers.nextFloat() <= this.density) {
            int i4 = (-16777216) & i3;
            int i5 = (i3 >> 16) & 255;
            int i6 = (i3 >> 8) & 255;
            int i7 = i3 & 255;
            if (this.monochrome) {
                int nextGaussian = (int) ((this.distribution == 0 ? this.randomNumbers.nextGaussian() : (this.randomNumbers.nextFloat() * 2.0f) - 1.0f) * this.amount);
                random = PixelUtils.clamp(i5 + nextGaussian);
                random2 = PixelUtils.clamp(i6 + nextGaussian);
                random3 = PixelUtils.clamp(i7 + nextGaussian);
            } else {
                random = random(i5);
                random2 = random(i6);
                random3 = random(i7);
            }
            return i4 | (random << 16) | (random2 << 8) | random3;
        }
        return i3;
    }
}
