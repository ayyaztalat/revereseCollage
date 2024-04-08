package com.blankj.utilcode.util;

import android.app.Activity;
import android.content.Intent;
import com.blankj.utilcode.util.C0879Utils;
import com.blankj.utilcode.util.UtilsTransActivity;

/* loaded from: classes.dex */
public class UtilsTransActivity4MainProcess extends UtilsTransActivity {
    public static void start(TransActivityDelegate transActivityDelegate) {
        start(null, null, transActivityDelegate, UtilsTransActivity4MainProcess.class);
    }

    public static void start(C0879Utils.Consumer<Intent> consumer, TransActivityDelegate transActivityDelegate) {
        start(null, consumer, transActivityDelegate, UtilsTransActivity4MainProcess.class);
    }

    public static void start(Activity activity, TransActivityDelegate transActivityDelegate) {
        start(activity, null, transActivityDelegate, UtilsTransActivity4MainProcess.class);
    }

    public static void start(Activity activity, C0879Utils.Consumer<Intent> consumer, TransActivityDelegate transActivityDelegate) {
        start(activity, consumer, transActivityDelegate, UtilsTransActivity4MainProcess.class);
    }
}
