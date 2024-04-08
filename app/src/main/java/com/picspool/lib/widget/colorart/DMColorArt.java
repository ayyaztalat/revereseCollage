package com.picspool.lib.widget.colorart;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import androidx.core.view.ViewCompat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class DMColorArt {
    private static final String LOG_TAG = DMColorArt.class.getSimpleName();
    private int mBackgroundColor;
    private final Bitmap mBitmap;
    private DMHashBag<Integer> mImageColors;
    private final double COLOR_THRESHOLD_MINIMUM_PERCENTAGE = 0.01d;
    private final double EDGE_COLOR_DISCARD_THRESHOLD = 0.3d;
    private final float MINIMUM_SATURATION_THRESHOLD = 0.15f;
    private Integer mPrimaryColor = null;
    private Integer mSecondaryColor = null;
    private Integer mDetailColor = null;

    public DMColorArt(Bitmap bitmap) {
        this.mBitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, false);
        analyzeImage();
        Bitmap bitmap2 = this.mBitmap;
        if (bitmap2 == null || bitmap2.isRecycled()) {
            return;
        }
        this.mBitmap.recycle();
    }

    private void analyzeImage() {
        this.mBackgroundColor = findEdgeColor();
        findTextColors(this.mImageColors);
        boolean isDarkColor = isDarkColor(this.mBackgroundColor);
        Integer num = this.mPrimaryColor;
        Integer valueOf = Integer.valueOf((int) ViewCompat.MEASURED_STATE_MASK);
        if (num == null) {
            Log.d(LOG_TAG, "Unable to detect primary color in image");
            if (isDarkColor) {
                this.mPrimaryColor = -1;
            } else {
                this.mPrimaryColor = valueOf;
            }
        }
        if (this.mSecondaryColor == null) {
            Log.d(LOG_TAG, "Unable to detect secondary in image");
            if (isDarkColor) {
                this.mSecondaryColor = -1;
            } else {
                this.mSecondaryColor = valueOf;
            }
        }
        if (this.mDetailColor == null) {
            Log.d(LOG_TAG, "Unable to detect detail color in image");
            if (isDarkColor) {
                this.mDetailColor = -1;
            } else {
                this.mDetailColor = valueOf;
            }
        }
    }

    private int findEdgeColor() {
        int height = this.mBitmap.getHeight();
        int width = this.mBitmap.getWidth();
        this.mImageColors = new DMHashBag<>();
        DMHashBag dMHashBag = new DMHashBag();
        for (int i = 0; i < width; i++) {
            for (int i2 = 0; i2 < height; i2++) {
                if (i == 0) {
                    dMHashBag.add(Integer.valueOf(this.mBitmap.getPixel(i, i2)));
                }
                this.mImageColors.add(Integer.valueOf(this.mBitmap.getPixel(i, i2)));
            }
        }
        ArrayList arrayList = new ArrayList();
        int i3 = (int) (height * 0.01d);
        Iterator it2 = dMHashBag.iterator();
        while (it2.hasNext()) {
            Integer num = (Integer) it2.next();
            int count = dMHashBag.getCount(num);
            if (count >= i3) {
                arrayList.add(new CountedColor(num.intValue(), count));
            }
        }
        Collections.sort(arrayList);
        Iterator it3 = arrayList.iterator();
        if (it3.hasNext()) {
            CountedColor countedColor = (CountedColor) it3.next();
            if (!countedColor.isBlackOrWhite()) {
                return countedColor.getColor();
            }
            while (true) {
                if (!it3.hasNext()) {
                    break;
                }
                CountedColor countedColor2 = (CountedColor) it3.next();
                if (countedColor2.getCount() / countedColor.getCount() > 0.3d) {
                    if (!countedColor2.isBlackOrWhite()) {
                        countedColor = countedColor2;
                        break;
                    }
                } else {
                    break;
                }
            }
            return countedColor.getColor();
        }
        return ViewCompat.MEASURED_STATE_MASK;
    }

    private void findTextColors(DMHashBag<Integer> dMHashBag) {
        Iterator<Integer> it2 = dMHashBag.iterator();
        ArrayList arrayList = new ArrayList();
        boolean z = !isDarkColor(this.mBackgroundColor);
        while (it2.hasNext()) {
            int colorWithMinimumSaturation = colorWithMinimumSaturation(it2.next().intValue(), 0.15f);
            if (isDarkColor(colorWithMinimumSaturation) == z) {
                arrayList.add(new CountedColor(colorWithMinimumSaturation, dMHashBag.getCount(Integer.valueOf(colorWithMinimumSaturation))));
            }
        }
        Collections.sort(arrayList);
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            int color = ((CountedColor) it3.next()).getColor();
            Integer num = this.mPrimaryColor;
            if (num == null) {
                if (isContrastingColor(color, this.mBackgroundColor)) {
                    this.mPrimaryColor = Integer.valueOf(color);
                }
            } else {
                Integer num2 = this.mSecondaryColor;
                if (num2 == null) {
                    if (isDistinctColor(num.intValue(), color) && isContrastingColor(color, this.mBackgroundColor)) {
                        this.mSecondaryColor = Integer.valueOf(color);
                    }
                } else if (this.mDetailColor == null && isDistinctColor(num2.intValue(), color) && isDistinctColor(this.mPrimaryColor.intValue(), color) && isContrastingColor(color, this.mBackgroundColor)) {
                    this.mDetailColor = Integer.valueOf(color);
                    return;
                }
            }
        }
    }

    public int getBackgroundColor() {
        return this.mBackgroundColor;
    }

    public int getPrimaryColor() {
        return this.mPrimaryColor.intValue();
    }

    public int getSecondaryColor() {
        return this.mSecondaryColor.intValue();
    }

    public int getDetailColor() {
        return this.mDetailColor.intValue();
    }

    private int colorWithMinimumSaturation(int i, float f) {
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        return fArr[1] < f ? Color.HSVToColor(new float[]{fArr[0], f, fArr[2]}) : i;
    }

    private boolean isDarkColor(int i) {
        return (((((double) Color.red(i)) / 255.0d) * 0.2126d) + ((((double) Color.green(i)) / 255.0d) * 0.7152d)) + ((((double) Color.blue(i)) / 255.0d) * 0.0722d) < 0.5d;
    }

    private boolean isContrastingColor(int i, int i2) {
        double red = ((Color.red(i) / 255.0d) * 0.2126d) + ((Color.green(i) / 255.0d) * 0.7152d) + ((Color.blue(i) / 255.0d) * 0.0722d);
        double red2 = ((Color.red(i2) / 255.0d) * 0.2126d) + ((Color.green(i2) / 255.0d) * 0.7152d) + ((Color.blue(i2) / 255.0d) * 0.0722d);
        return ((red > red2 ? 1 : (red == red2 ? 0 : -1)) > 0 ? (red + 0.05d) / (red2 + 0.05d) : (red2 + 0.05d) / (red + 0.05d)) > 1.6d;
    }

    private boolean isDistinctColor(int i, int i2) {
        double red = Color.red(i) / 255.0d;
        double green = Color.green(i) / 255.0d;
        double blue = Color.blue(i) / 255.0d;
        double alpha = Color.alpha(i) / 255.0d;
        double red2 = Color.red(i2) / 255.0d;
        double green2 = Color.green(i2) / 255.0d;
        double blue2 = Color.blue(i2) / 255.0d;
        double alpha2 = Color.alpha(i2) / 255.0d;
        if (Math.abs(red - red2) > 0.25d || Math.abs(green - green2) > 0.25d || Math.abs(blue - blue2) > 0.25d || Math.abs(alpha - alpha2) > 0.25d) {
            return Math.abs(red - green) >= 0.03d || Math.abs(red - blue) >= 0.03d || Math.abs(red2 - green2) >= 0.03d || Math.abs(red2 - blue2) >= 0.03d;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class CountedColor implements Comparable<CountedColor> {
        private final int mColor;
        private final int mCount;

        public CountedColor(int i, int i2) {
            this.mColor = i;
            this.mCount = i2;
        }

        @Override // java.lang.Comparable
        public int compareTo(CountedColor countedColor) {
            if (getCount() < countedColor.getCount()) {
                return -1;
            }
            return getCount() == countedColor.getCount() ? 0 : 1;
        }

        public boolean isBlackOrWhite() {
            double red = Color.red(this.mColor) / 255.0d;
            double green = Color.green(this.mColor) / 255.0d;
            double blue = Color.blue(this.mColor) / 255.0d;
            if (red <= 0.91d || green <= 0.91d || blue <= 0.91d) {
                return red < 0.09d && green < 0.09d && blue < 0.09d;
            }
            return true;
        }

        public int getCount() {
            return this.mCount;
        }

        public int getColor() {
            return this.mColor;
        }
    }
}
