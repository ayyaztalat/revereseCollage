package com.picspool.lib.color;

import android.graphics.Color;
import androidx.core.view.ViewCompat;

/* loaded from: classes3.dex */
public class DMSysColors {
    public static String[] colorStrings;
    public static int length;

    static {
        String[] strArr = {"6c3a00", "925617", "bd8a56", "dcb292", "38302b", "594a40", "a6937d", "ddd1c2", "fededc", "fbd1f0", "ffadce", "ff94ff", "eb73d4", "ff4aa4", "fe01f3", "cb0090", "a0017c", "6c015c", "5e0212", "6c0223", "85020f", "ad012a", "fe0200", "fc3a43", "fd6404", "fe9d00", "fdcc49", "fef201", "fbfa98", "000001", "333233", "626262", "aaaba8", "fdfdfe", "e9ffb0", "beea86", "97ce28", "8dde00", "4a9102", "226200", "1f4300", "003200", "002a29", "003d2e", "016e61", "01b16c", "02c67f", "0ac0ad", "00e6c8", "0afdfe", "99fcfc", "d3edfe", "00c7fd", "019dfd", "0081fe", "0035b0", "050191", "0d0050", "13013a", "2a066b", "5327ad", "8971cc", "d8c5f5"};
        colorStrings = strArr;
        length = strArr.length;
    }

    public static int getColor(int i) {
        if (i < colorStrings.length) {
            return Color.parseColor("#ff" + colorStrings[i]);
        }
        return ViewCompat.MEASURED_STATE_MASK;
    }

    public static int getPos(int i) {
        for (int i2 = 0; i2 < colorStrings.length; i2++) {
            if (Color.parseColor("#" + colorStrings[i2]) == i) {
                return i2;
            }
        }
        return -1;
    }
}
