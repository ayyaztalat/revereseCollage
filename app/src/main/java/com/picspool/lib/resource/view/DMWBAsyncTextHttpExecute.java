package com.picspool.lib.resource.view;

import android.os.Build;

/* loaded from: classes3.dex */
public class DMWBAsyncTextHttpExecute {
    public static void asyncHttpRequest(String str, DMAsyncTextHttpTaskListener dMAsyncTextHttpTaskListener) {
        if (Build.VERSION.SDK_INT <= 10) {
            DMWBAsyncTextHttp23 dMWBAsyncTextHttp23 = new DMWBAsyncTextHttp23(str);
            dMWBAsyncTextHttp23.setListener(dMAsyncTextHttpTaskListener);
            dMWBAsyncTextHttp23.execute();
            return;
        }
        DMWBAsyncTextHttp dMWBAsyncTextHttp = new DMWBAsyncTextHttp(str);
        dMWBAsyncTextHttp.setListener(dMAsyncTextHttpTaskListener);
        dMWBAsyncTextHttp.execute(new String[0]);
    }
}
