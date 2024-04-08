package com.picspool.lib.filter.cpu.util;

import android.content.res.Resources;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes3.dex */
public class ACVFileReader {
    public static Curve[] getFromInputStream(Resources resources, String str) {
        int i;
        try {
            InputStream open = resources.getAssets().open(str);
            readShort(open);
            int readShort = readShort(open);
            Curve[] curveArr = new Curve[readShort];
            Boolean[] boolArr = new Boolean[readShort];
            int i2 = 0;
            while (true) {
                i = 1;
                if (i2 >= readShort) {
                    break;
                }
                short readShort2 = readShort(open);
                curveArr[i2] = new Curve();
                boolArr[i2] = false;
                for (int i3 = 0; i3 < readShort2; i3++) {
                    short readShort3 = readShort(open);
                    short readShort4 = readShort(open);
                    if (readShort4 != readShort3 || (readShort4 != 0 && readShort3 != 255)) {
                        boolArr[i2] = true;
                        curveArr[i2].addKnot(readShort4 * 0.003921569f, readShort3 * 0.003921569f);
                    }
                }
                i2++;
            }
            open.close();
            int i4 = readShort;
            for (int i5 = 0; i5 < readShort; i5++) {
                if (!boolArr[i5].booleanValue()) {
                    i4--;
                }
            }
            if (i4 == 3) {
                i = i4;
            }
            Curve[] curveArr2 = new Curve[i];
            int i6 = 0;
            for (int i7 = 0; i7 < readShort; i7++) {
                Curve curve = curveArr[i7];
                if (boolArr[i7].booleanValue() && i6 < i) {
                    curveArr2[i6] = curve;
                    i6++;
                }
            }
            return curveArr2;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static short readShort(InputStream inputStream) throws IOException {
        return (short) (inputStream.read() | (inputStream.read() << 8));
    }
}
