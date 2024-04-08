package com.photo.editor.square.splash.view.filterbar;

import android.graphics.Color;
import androidx.core.view.ViewCompat;

/* loaded from: classes2.dex */
public class CSFilterSetSysColors {
    public static String[] colorStrings;
    public static int length;

    static {
        String[] strArr = {"d54266", "6b7c26", "3681c7", "3a957e", "b72137", "db6147", "f2b658", "545454", "3f7bc6", "b132c1", "6b7c26"};
        colorStrings = strArr;
        length = strArr.length;
    }

    public static int getColor(int i) {
        if (i < colorStrings.length) {
            return Color.parseColor("#88" + colorStrings[i]);
        }
        return ViewCompat.MEASURED_STATE_MASK;
    }

    public static int getPos(int i) {
        for (int i2 = 0; i2 < colorStrings.length; i2++) {
            if (Color.parseColor("#88" + colorStrings[i2]) == i) {
                return i2;
            }
        }
        return -1;
    }
}
