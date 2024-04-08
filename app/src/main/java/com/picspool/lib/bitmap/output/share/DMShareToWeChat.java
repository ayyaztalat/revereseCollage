package com.picspool.lib.bitmap.output.share;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;
import com.picspool.lib.otherapp.DMOtherApp;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMShareToWeChat {
    public static void shareImageToWeChat(Activity activity, Uri uri, String str) {
        if (!DMOtherApp.isInstalled(activity, "com.tencent.mm").booleanValue()) {
            Toast.makeText(activity, activity.getResources().getString(R.string.warning_weichat_no_installed), 1).show();
            return;
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI"));
        intent.setAction("android.intent.action.SEND");
        intent.setType("image/*");
        intent.putExtra("android.intent.extra.STREAM", uri);
        activity.startActivity(intent);
    }

    public static void shareImageToMoment(Activity activity, Uri uri, String str) {
        if (!DMOtherApp.isInstalled(activity, "com.tencent.mm").booleanValue()) {
            Toast.makeText(activity, activity.getResources().getString(R.string.warning_weichat_no_installed), 1).show();
            return;
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI"));
        intent.setAction("android.intent.action.SEND");
        intent.setType("image/*");
        intent.putExtra("android.intent.extra.STREAM", uri);
        activity.startActivity(intent);
    }
}
