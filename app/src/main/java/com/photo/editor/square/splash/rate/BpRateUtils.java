package com.photo.editor.square.splash.rate;

import android.content.Context;
import android.text.TextUtils;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.util.Random;
import com.picspool.lib.sysutillib.DMPreferencesUtil;

/* loaded from: classes2.dex */
public class BpRateUtils {
    public static final String RATE_ENTER_COUNT = "bcoe_rate_enter_count";
    public static final String RATE_JSON = "rate_js";
    public static final String RATE_LAST_SHOW_TIME = "bcoe_last_show_time";
    public static final String RATE_MAX_TIMES_PER_DAY = "bcoe_rate_max_times_per_day";
    public static final String RATE_MAX_TIMES_PER_USER = "bcoe_rate_max_times_per_user";
    public static final String RATE_OLD_USER_KEY = "bcoe_user_enter_cnt";
    public static final String RATE_PRE = "bcoe_rate_prefs";
    public static final String RATE_SHOW_INTERVAL_COUNT = "bcoe_show_interval";
    public static final String RATE_SHOW_KEY = "bcoe_rate_dont_show_again";
    public static final String RATE_START_SHOW_TIMES = "bcoe_start_show_times";
    public static final String RATE_TODAY_SHOW_COUNT = "bcoe_today_show";
    public static final String RATE_TOTAL_SHOW_COUNT = "bcoe_total_show";
    private static final int Rate_Default = 100;
    private static final int Rate_Max_Times_Per_Day_Default = -1;
    private static final int Rate_Max_Times_Per_User_Default = 2;
    private static final int Rate_Position_Default = 1;
    private static final int Rate_Show_Interval_Default = 2;
    private static final int Rate_User_Default = 1;
    private static final int Rate_start_show_times = 3;

    private void ______judge_new_old_user________() {
    }

    private void ______tools________() {
    }

    public static long getRateRate(BpRateEntity bpRateEntity) {
        if (bpRateEntity != null) {
            return bpRateEntity.getScoreRate();
        }
        return 100L;
    }

    public static boolean getRateUserIsOldUser(BpRateEntity bpRateEntity) {
        return getRateUser(bpRateEntity) == 2;
    }

    public static long getRateUser(BpRateEntity bpRateEntity) {
        if (bpRateEntity != null) {
            return bpRateEntity.getRateUser();
        }
        return 1L;
    }

    public static boolean getRatePositionIsShowInHomePage(Context context, BpRateEntity bpRateEntity) {
        return getRatePosition(context, bpRateEntity) == 2;
    }

    public static boolean getRatePositionIsShowInSharePage(Context context, BpRateEntity bpRateEntity) {
        return getRatePosition(context, bpRateEntity) == 1;
    }

    public static long getRatePosition(Context context, BpRateEntity bpRateEntity) {
        if (bpRateEntity != null) {
            return bpRateEntity.getRatePosition();
        }
        return 1L;
    }

    public static void recordRateJson(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            return;
        }
        DMPreferencesUtil.save(context, RATE_PRE, RATE_JSON, str);
    }

    public static long getRateShowAgainInterval(BpRateEntity bpRateEntity) {
        if (bpRateEntity != null) {
            return bpRateEntity.getRateInternal();
        }
        return 2L;
    }

    public static void recordRateShowMaxTimesPerUser(Context context, String str) {
        DMPreferencesUtil.save(context, RATE_PRE, RATE_MAX_TIMES_PER_USER, str);
    }

    public static long getRateShowMaxTimesPerUser(Context context) {
        String str = DMPreferencesUtil.get(context, RATE_PRE, RATE_MAX_TIMES_PER_USER);
        if (!TextUtils.isEmpty(str)) {
            try {
                return Long.parseLong(str);
            } catch (Exception unused) {
            }
        }
        return 2L;
    }

    public static void recordRateShowMaxTimesPerDay(Context context, String str) {
        DMPreferencesUtil.save(context, RATE_PRE, RATE_MAX_TIMES_PER_DAY, str);
    }

    public static long getRateShowMaxTimesPerDay(Context context) {
        String str = DMPreferencesUtil.get(context, RATE_PRE, RATE_MAX_TIMES_PER_DAY);
        if (!TextUtils.isEmpty(str)) {
            try {
                return Long.parseLong(str);
            } catch (Exception unused) {
            }
        }
        return -1L;
    }

    public static int getEnterCount(Context context) {
        String str = DMPreferencesUtil.get(context, RATE_PRE, RATE_ENTER_COUNT);
        if (!TextUtils.isEmpty(str)) {
            try {
                return Integer.parseInt(str);
            } catch (Exception unused) {
            }
        }
        return 1;
    }

    public static void saveEnterCount(Context context, int i) {
        try {
            DMPreferencesUtil.save(context, RATE_PRE, RATE_ENTER_COUNT, i + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isCanShowDialog(Context context, BpRateEntity bpRateEntity) {
        long j;
        String str = DMPreferencesUtil.get(context, RATE_PRE, RATE_SHOW_KEY);
        boolean z = true;
        if (!TextUtils.isEmpty(str)) {
            try {
                j = Long.parseLong(str);
            } catch (Exception unused) {
                j = 0;
            }
            if (j != 0) {
                z = false;
            }
        }
        if (z) {
            z = getExactrafromRandom(getRateRate(bpRateEntity)).booleanValue();
        }
        return (!z || bpRateEntity == null) ? z : bpRateEntity.isOSSupport();
    }

    public static void setNotShowRateDialogForever(Context context) {
        DMPreferencesUtil.save(context, RATE_PRE, RATE_SHOW_KEY, "1");
    }

    public static String getRateJsnon(Context context) {
        return FirebaseRemoteConfig.getInstance().getString(RATE_JSON);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x001e, code lost:
        if (r4 != false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0023, code lost:
        if (r4 == false) goto L7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x002c, code lost:
        if (isOldUser(r2) == false) goto L7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0031, code lost:
        if (r4 != false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x003a, code lost:
        if (r3 != false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0013, code lost:
        if (r4 == false) goto L7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isShow(Context r2, boolean r3, boolean r4, BpRateEntity r5) {
        /*
            long r0 = getRatePosition(r2, r5)
            int r5 = (int) r0
            r0 = 1
            r1 = 0
            switch(r5) {
                case 1: goto L40;
                case 2: goto L34;
                case 3: goto L2f;
                case 4: goto L26;
                case 5: goto L21;
                case 6: goto L16;
                case 7: goto Lb;
                default: goto La;
            }
        La:
            goto L42
        Lb:
            if (r3 == 0) goto L3e
            boolean r2 = isOldUser(r2)
            if (r2 != 0) goto L3e
            if (r4 == 0) goto L3d
            goto L3e
        L16:
            if (r3 == 0) goto L3d
            boolean r2 = isOldUser(r2)
            if (r2 != 0) goto L3e
            if (r4 == 0) goto L3d
            goto L3e
        L21:
            if (r3 == 0) goto L3e
            if (r4 == 0) goto L3d
            goto L3e
        L26:
            if (r3 == 0) goto L3e
            boolean r2 = isOldUser(r2)
            if (r2 == 0) goto L3d
            goto L3e
        L2f:
            if (r3 == 0) goto L3d
            if (r4 == 0) goto L3d
            goto L3e
        L34:
            boolean r2 = isOldUser(r2)
            if (r2 == 0) goto L3d
            if (r3 == 0) goto L3d
            goto L3e
        L3d:
            r0 = 0
        L3e:
            r1 = r0
            goto L42
        L40:
            r1 = r3 ^ 1
        L42:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.photo.editor.square.splash.rate.BpRateUtils.isShow(android.content.Context, boolean, boolean, com.photo.editor.square.splash.rate.BpRateEntity):boolean");
    }

    public static boolean isOldUser(Context context) {
        long j;
        String str = DMPreferencesUtil.get(context, RATE_PRE, RATE_OLD_USER_KEY);
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            j = Long.parseLong(str);
        } catch (Exception unused) {
            j = 0;
        }
        return j > 0;
    }

    public static void setOldUserFlag(Context context) {
        try {
            String str = DMPreferencesUtil.get(context, RATE_PRE, RATE_OLD_USER_KEY);
            if (TextUtils.isEmpty(str)) {
                DMPreferencesUtil.save(context, RATE_PRE, RATE_OLD_USER_KEY, "0");
            } else {
                long j = 0;
                try {
                    j = Long.parseLong(str);
                } catch (Exception unused) {
                }
                if (j < 1) {
                    DMPreferencesUtil.save(context, RATE_PRE, RATE_OLD_USER_KEY, "1");
                }
            }
        } catch (Exception unused2) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x003c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getTodayShowCount(Context r14) {
        /*
            java.lang.String r0 = "bcoe_rate_prefs"
            java.lang.String r1 = "bcoe_last_show_time"
            java.lang.String r2 = com.picspool.lib.sysutillib.DMPreferencesUtil.get(r14, r0, r1)
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            java.lang.String r4 = ""
            r5 = 0
            if (r3 != 0) goto L17
            long r2 = java.lang.Long.parseLong(r2)     // Catch: java.lang.Exception -> L2d
            goto L2e
        L17:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r4)
            long r7 = java.lang.System.currentTimeMillis()
            r2.append(r7)
            java.lang.String r2 = r2.toString()
            com.picspool.lib.sysutillib.DMPreferencesUtil.save(r14, r0, r1, r2)
        L2d:
            r2 = r5
        L2e:
            java.lang.String r7 = "bcoe_today_show"
            java.lang.String r8 = com.picspool.lib.sysutillib.DMPreferencesUtil.get(r14, r0, r7)
            boolean r9 = android.text.TextUtils.isEmpty(r8)
            java.lang.String r10 = "0"
            if (r9 != 0) goto L41
            long r8 = java.lang.Long.parseLong(r8)     // Catch: java.lang.Exception -> L44
            goto L45
        L41:
            com.picspool.lib.sysutillib.DMPreferencesUtil.save(r14, r0, r7, r10)
        L44:
            r8 = r5
        L45:
            long r11 = java.lang.System.currentTimeMillis()
            long r11 = r11 - r2
            r2 = 86400000(0x5265c00, double:4.2687272E-316)
            int r13 = (r11 > r2 ? 1 : (r11 == r2 ? 0 : -1))
            if (r13 <= 0) goto L6b
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r4)
            long r3 = java.lang.System.currentTimeMillis()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.picspool.lib.sysutillib.DMPreferencesUtil.save(r14, r0, r1, r2)
            com.picspool.lib.sysutillib.DMPreferencesUtil.save(r14, r0, r7, r10)
            goto L6c
        L6b:
            r5 = r8
        L6c:
            int r14 = (int) r5
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.photo.editor.square.splash.rate.BpRateUtils.getTodayShowCount(android.content.Context):int");
    }

    public static void setTodayShowCountAdd(Context context) {
        long parseLong = 0;
        String str = DMPreferencesUtil.get(context, RATE_PRE, RATE_TODAY_SHOW_COUNT);
        if (!TextUtils.isEmpty(str)) {
            try {
                parseLong = Long.parseLong(str);
            } catch (Exception ignored) {
            }
            DMPreferencesUtil.save(context, RATE_PRE, RATE_TODAY_SHOW_COUNT, "" + (parseLong + 1));
        }
        parseLong = 0;
        DMPreferencesUtil.save(context, RATE_PRE, RATE_TODAY_SHOW_COUNT, "" + (parseLong + 1));
    }

    public static long getRateCurrentShowIntervalCount(Context context) {
        String str = DMPreferencesUtil.get(context, RATE_PRE, RATE_SHOW_INTERVAL_COUNT);
        if (!TextUtils.isEmpty(str)) {
            try {
                return Long.parseLong(str);
            } catch (Exception unused) {
            }
        } else {
            DMPreferencesUtil.save(context, RATE_PRE, RATE_SHOW_INTERVAL_COUNT, "0");
        }
        return 0L;
    }

    public static void setRateCurrentShowIntervalCountAdd(Context context, boolean z) {
        if (z) {
            DMPreferencesUtil.save(context, RATE_PRE, RATE_SHOW_INTERVAL_COUNT, "0");
            return;
        }
        DMPreferencesUtil.save(context, RATE_PRE, RATE_SHOW_INTERVAL_COUNT, "" + (getRateCurrentShowIntervalCount(context) + 1));
    }

    public static void setRateCurrentShowIntervalIsMax(Context context, BpRateEntity bpRateEntity) {
        DMPreferencesUtil.save(context, RATE_PRE, RATE_SHOW_INTERVAL_COUNT, "" + (getRateShowAgainInterval(bpRateEntity) + 1));
    }

    public static long getRateUserCurrentTotalShowCount(Context context) {
        String str = DMPreferencesUtil.get(context, RATE_PRE, RATE_TOTAL_SHOW_COUNT);
        if (!TextUtils.isEmpty(str)) {
            try {
                return Long.parseLong(str);
            } catch (Exception unused) {
            }
        } else {
            DMPreferencesUtil.save(context, RATE_PRE, RATE_TOTAL_SHOW_COUNT, "0");
        }
        return 0L;
    }

    public static void setRateUserCurrentTotalShowCountAdd(Context context) {
        DMPreferencesUtil.save(context, RATE_PRE, RATE_TOTAL_SHOW_COUNT, "" + (getRateUserCurrentTotalShowCount(context) + 1));
    }

    public static Boolean getExactrafromRandom(String str) {
        if (str.matches("^\\d+$")) {
            return Boolean.valueOf(new Random().nextInt(100) + 1 <= Integer.parseInt(str));
        }
        return true;
    }

    public static Boolean getExactrafromRandom(long j) {
        if (j > 0) {
            return Boolean.valueOf(((long) (new Random().nextInt(100) + 1)) <= j);
        }
        return false;
    }
}
