package com.picspool.lib.text.font;

import android.graphics.Typeface;
import java.util.List;

/* loaded from: classes3.dex */
public class DMFontList {
    private static List<Typeface> tfList;

    public static void setTfList(List<Typeface> list) {
        tfList = list;
    }

    public static List<Typeface> getTfList() {
        return tfList;
    }
}
