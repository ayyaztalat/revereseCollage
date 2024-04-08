package com.picspool.lib.filter.cpu.normal;

import androidx.core.view.ViewCompat;
import com.picspool.lib.filter.cpu.father.CellularFilter;
import com.picspool.lib.filter.cpu.util.ImageMath;

/* loaded from: classes3.dex */
public class PointillizeFilter extends CellularFilter {
    private float edgeThickness = 0.4f;
    private boolean fadeEdges = false;
    private int edgeColor = ViewCompat.MEASURED_STATE_MASK;
    private float fuzziness = 0.1f;

    @Override // com.picspool.lib.filter.cpu.father.CellularFilter
    public String toString() {
        return "Pixellate/Pointillize...";
    }

    public PointillizeFilter() {
        setScale(16.0f);
        setRandomness(0.0f);
    }

    public void setEdgeThickness(float f) {
        this.edgeThickness = f;
    }

    public float getEdgeThickness() {
        return this.edgeThickness;
    }

    public void setFadeEdges(boolean z) {
        this.fadeEdges = z;
    }

    public boolean getFadeEdges() {
        return this.fadeEdges;
    }

    public void setEdgeColor(int i) {
        this.edgeColor = i;
    }

    public int getEdgeColor() {
        return this.edgeColor;
    }

    public void setFuzziness(float f) {
        this.fuzziness = f;
    }

    public float getFuzziness() {
        return this.fuzziness;
    }

    @Override // com.picspool.lib.filter.cpu.father.CellularFilter
    public int getPixel(int i, int i2, int[] iArr, int i3, int i4) {
        float f = i;
        float f2 = i2;
        evaluate((((this.m00 * f) + (this.m01 * f2)) / this.scale) + 1000.0f, (((this.m10 * f) + (this.m11 * f2)) / (this.scale * this.stretch)) + 1000.0f);
        float f3 = this.results[0].distance;
        int i5 = i3 - 1;
        int i6 = i4 - 1;
        int i7 = iArr[(ImageMath.clamp((int) ((this.results[0].f1958y - 1000.0f) * this.scale), 0, i6) * i3) + ImageMath.clamp((int) ((this.results[0].f1957x - 1000.0f) * this.scale), 0, i5)];
        if (this.fadeEdges) {
            return ImageMath.mixColors((f3 * 0.5f) / this.results[1].distance, i7, iArr[(ImageMath.clamp((int) ((this.results[1].f1958y - 1000.0f) * this.scale), 0, i6) * i3) + ImageMath.clamp((int) ((this.results[1].f1957x - 1000.0f) * this.scale), 0, i5)]);
        }
        float f4 = this.edgeThickness;
        return ImageMath.mixColors(1.0f - ImageMath.smoothStep(f4, this.fuzziness + f4, f3), this.edgeColor, i7);
    }
}
