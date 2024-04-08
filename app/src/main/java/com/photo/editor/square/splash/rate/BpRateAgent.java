package com.photo.editor.square.splash.rate;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class BpRateAgent {
    public static final String RATE_ED_T = "rate_times";
    public static final String TAG = "BpRateAgent";
    private BpRateEntity entity;
    private boolean isExit;
    private boolean isHomePage;

    public BpRateAgent(boolean z, boolean z2) {
        this.isHomePage = z;
        this.isExit = z2;
    }

    public static void showRate(Context context) {
        try {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + context.getPackageName())));
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(context, "No Play Store installed on device", Toast.LENGTH_SHORT).show();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean showRateDialog(Context r9) {
        return false;
        /*
            r8 = this;
            com.photo.editor.square.splash.rate.BpRateEntity r0 = r8.getRateEntity(r9)
            r8.entity = r0
            boolean r0 = com.photo.editor.square.splash.rate.BpRateUtils.isCanShowDialog(r9, r0)
            r1 = 0
            if (r0 == 0) goto L90
            boolean r0 = r8.isHomePage
            boolean r2 = r8.isExit
            com.photo.editor.square.splash.rate.BpRateEntity r3 = r8.entity
            boolean r0 = com.photo.editor.square.splash.rate.BpRateUtils.isShow(r9, r0, r2, r3)
            if (r0 == 0) goto L8f
            int r0 = com.photo.editor.square.splash.rate.BpRateUtils.getEnterCount(r9)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "rt_start_times:"
            r2.append(r3)
            com.photo.editor.square.splash.rate.BpRateEntity r3 = r8.entity
            int r3 = r3.getRateStartTimes()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "BpRateAgent"
            android.util.Log.e(r3, r2)
            com.photo.editor.square.splash.rate.BpRateEntity r2 = r8.entity
            int r2 = r2.getRateStartTimes()
            r3 = 1
            if (r0 >= r2) goto L47
            int r0 = r0 + r3
            com.photo.editor.square.splash.rate.BpRateUtils.saveEnterCount(r9, r0)
            return r1
        L47:
            long r4 = com.photo.editor.square.splash.rate.BpRateUtils.getRateShowMaxTimesPerDay(r9)
            r6 = -1
            int r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r0 != 0) goto L53
        L51:
            r0 = 1
            goto L63
        L53:
            int r0 = com.photo.editor.square.splash.rate.BpRateUtils.getTodayShowCount(r9)
            long r6 = (long) r0
            int r0 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r0 >= 0) goto L5d
            goto L51
        L5d:
            com.photo.editor.square.splash.rate.BpRateEntity r0 = r8.entity
            com.photo.editor.square.splash.rate.BpRateUtils.setRateCurrentShowIntervalIsMax(r9, r0)
            r0 = 0
        L63:
            if (r0 == 0) goto L88
            long r4 = com.photo.editor.square.splash.rate.BpRateUtils.getRateUserCurrentTotalShowCount(r9)
            r6 = 0
            int r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r2 <= 0) goto L88
            com.photo.editor.square.splash.rate.BpRateEntity r2 = r8.entity
            long r4 = com.photo.editor.square.splash.rate.BpRateUtils.getRateShowAgainInterval(r2)
            int r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r2 <= 0) goto L88
            long r6 = com.photo.editor.square.splash.rate.BpRateUtils.getRateCurrentShowIntervalCount(r9)
            int r2 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r2 >= 0) goto L85
            com.photo.editor.square.splash.rate.BpRateUtils.setRateCurrentShowIntervalCountAdd(r9, r1)
            goto L89
        L85:
            com.photo.editor.square.splash.rate.BpRateUtils.setRateCurrentShowIntervalCountAdd(r9, r3)
        L88:
            r1 = r0
        L89:
            if (r1 == 0) goto L90
            r8.showDialog(r9)
            goto L90
        L8f:
            r1 = r0
        L90:
            return r1
        */
//        throw new UnsupportedOperationException("Method not decompiled: com.photo.editor.square.splash.rate.BpRateAgent.showRateDialog(android.content.Context):boolean");
    }

    private BpRateEntity getRateEntity(Context context) {
        BpRateEntity bpRateEntity = new BpRateEntity();
        try {
            JSONObject jSONObject = new JSONObject(BpRateUtils.getRateJsnon(context));
            bpRateEntity.setRateInternal(jSONObject.has("rt_again_times") ? jSONObject.getInt("rt_again_times") : 0);
            bpRateEntity.setRatePosition(jSONObject.has("rt_positon") ? jSONObject.getInt("rt_positon") : 0);
            bpRateEntity.setScoreRate(jSONObject.has("rt_rate") ? jSONObject.getInt("rt_rate") : 0);
            bpRateEntity.setRateUser(jSONObject.has("rt_user") ? jSONObject.getInt("rt_user") : 0);
            bpRateEntity.setRateStartTimes(jSONObject.has("rt_start_times") ? jSONObject.getInt("rt_start_times") : 0);
            bpRateEntity.setRateShowToast(jSONObject.has("isshow_rt_tst") ? jSONObject.getInt("isshow_rt_tst") : 0);
            bpRateEntity.setMaxShowCount(jSONObject.has("max_times") ? jSONObject.getInt("max_times") : 0);
            if (jSONObject.has("no_rate_os")) {
                JSONArray jSONArray = jSONObject.getJSONArray("no_rate_os");
                if (jSONArray.length() > 0) {
                    int[] iArr = new int[jSONArray.length()];
                    for (int i = 0; i < jSONArray.length(); i++) {
                        iArr[i] = jSONArray.getInt(i);
                    }
                    bpRateEntity.setNoRateOs(iArr);
                }
            }
        } catch (Throwable unused) {
        }
        return bpRateEntity;
    }

    public void showDialog(Context context) {
        BpRateUtils.setTodayShowCountAdd(context);
        BpRateEntity bpRateEntity = this.entity;
        long maxShowCount = bpRateEntity != null ? bpRateEntity.getMaxShowCount() : BpRateUtils.getRateShowMaxTimesPerUser(context);
        Log.e(TAG, "max_times:" + maxShowCount);
        if (maxShowCount > -1) {
            long rateUserCurrentTotalShowCount = BpRateUtils.getRateUserCurrentTotalShowCount(context);
            Log.e(TAG, "curTotalShow:" + rateUserCurrentTotalShowCount);
            if (rateUserCurrentTotalShowCount >= maxShowCount - 1) {
                BpRateUtils.setNotShowRateDialogForever(context);
            }
        }
        BpRateUtils.setRateUserCurrentTotalShowCountAdd(context);
        BpRateDialog bpRateDialog = new BpRateDialog(context, this.entity);
        Activity activity = (Activity) context;
        if (activity.isFinishing() || activity.isDestroyed()) {
            return;
        }
        bpRateDialog.show();
    }
}
