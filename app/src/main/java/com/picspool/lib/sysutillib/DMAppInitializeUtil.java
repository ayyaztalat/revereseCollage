package com.picspool.lib.sysutillib;

import android.content.Context;
import android.content.pm.PackageManager;
import java.util.Date;

/* loaded from: classes3.dex */
public class DMAppInitializeUtil {
    public static void setUserInitialize(Context context) {
        if (DMPreferencesUtil.get(context, "app_initialize_config", "version_code_number") != null) {
            return;
        }
        try {
            DMPreferencesUtil.save(context, "app_initialize_config", "version_code_number", String.valueOf(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode * 10000));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean isNewUser(Context context) {
        int i;
        String str = DMPreferencesUtil.get(context, "app_initialize_config", "version_code_number");
        if (str == null) {
            return true;
        }
        try {
            i = Integer.parseInt(str);
        } catch (Exception unused) {
            i = -1;
        }
        if (i == -1) {
            return true;
        }
        try {
            return i >= context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode * 10000;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return true;
        }
    }

    private static void recordUserFirstLogin(Context context) {
        if (DMPreferencesUtil.get(context, "record_user_info", "first_login") == null) {
            DMPreferencesUtil.save(context, "record_user_info", "first_login", String.valueOf(new Date().getTime()));
        }
    }

    public static long getUserFirstLoginTime(Context context) {
        String str = DMPreferencesUtil.get(context, "record_user_info", "first_login");
        if (str != null) {
            return Long.valueOf(str).longValue();
        }
        return -1L;
    }

    private static void recordUserlastLogin(Context context) {
        DMPreferencesUtil.save(context, "record_user_info", "last_login", String.valueOf(new Date().getTime()));
    }

    public static long getUserLastLoginTime(Context context) {
        String str = DMPreferencesUtil.get(context, "record_user_info", "last_login");
        if (str != null) {
            return Long.valueOf(str).longValue();
        }
        return -1L;
    }

    public static void recordUserLoginCount(Context context) {
        try {
            DMPreferencesUtil.save(context, "record_user_info", "login_count", String.valueOf(DMPreferencesUtil.get(context, "record_user_info", "login_count") != null ? 1 + Long.valueOf(DMPreferencesUtil.get(context, "record_user_info", "login_count")).longValue() : 1L));
        } catch (Throwable unused) {
        }
    }

    public static long getUserUserLoginCount(Context context) {
        try {
            String str = DMPreferencesUtil.get(context, "record_user_info", "login_count");
            if (str != null) {
                return Long.valueOf(str).longValue();
            }
            return 0L;
        } catch (Throwable unused) {
            return 0L;
        }
    }

    public static void recordUserLoginInfomation(Context context) {
        recordUserFirstLogin(context);
        recordUserlastLogin(context);
    }

    public static void recordUserHomeTopADClickCount(Context context) {
        try {
            DMPreferencesUtil.save(context, "record_user_info", "hometop_adclick_count", String.valueOf(DMPreferencesUtil.get(context, "record_user_info", "hometop_adclick_count") != null ? 1 + Long.valueOf(DMPreferencesUtil.get(context, "record_user_info", "hometop_adclick_count")).longValue() : 1L));
        } catch (Throwable unused) {
        }
    }

    public static long getUserUserHomeTopADClickCount(Context context) {
        try {
            String str = DMPreferencesUtil.get(context, "record_user_info", "hometop_adclick_count");
            if (str != null) {
                return Long.valueOf(str).longValue();
            }
            return 0L;
        } catch (Throwable unused) {
            return 0L;
        }
    }

    public static void recordUserExitADClickCount(Context context) {
        try {
            DMPreferencesUtil.save(context, "record_user_info", "exit_adclick_count", String.valueOf(DMPreferencesUtil.get(context, "record_user_info", "exit_adclick_count") != null ? 1 + Long.valueOf(DMPreferencesUtil.get(context, "record_user_info", "exit_adclick_count")).longValue() : 1L));
        } catch (Throwable unused) {
        }
    }

    public static long getUserUserExitADClickCount(Context context) {
        try {
            String str = DMPreferencesUtil.get(context, "record_user_info", "exit_adclick_count");
            if (str != null) {
                return Long.valueOf(str).longValue();
            }
            return 0L;
        } catch (Throwable unused) {
            return 0L;
        }
    }

    public static void recordUserShareADClickCount(Context context) {
        try {
            DMPreferencesUtil.save(context, "record_user_info", "share_adclick_count", String.valueOf(DMPreferencesUtil.get(context, "record_user_info", "share_adclick_count") != null ? 1 + Long.valueOf(DMPreferencesUtil.get(context, "record_user_info", "share_adclick_count")).longValue() : 1L));
        } catch (Throwable unused) {
        }
    }

    public static long getUserUserShareADClickCount(Context context) {
        try {
            String str = DMPreferencesUtil.get(context, "record_user_info", "share_adclick_count");
            if (str != null) {
                return Long.valueOf(str).longValue();
            }
            return 0L;
        } catch (Throwable unused) {
            return 0L;
        }
    }

    public static void recordUserCountryLevel(Context context, String str) {
        DMPreferencesUtil.save(context, "record_user_info", "user_country_level", str);
    }

    public static String getUserUserCountryLevel(Context context) {
        String str = DMPreferencesUtil.get(context, "record_user_info", "user_country_level");
        return str != null ? str : "-1";
    }

    public static String getChannel(Context context) {
        String str = DMPreferencesUtil.get(context, "record_user_info", "user_channel");
        return str != null ? str : "GooglePlay";
    }

    public static void setChannel(Context context, String str) {
        DMPreferencesUtil.save(context, "record_user_info", "user_channel", str);
    }

    public static void recordUserChargeADClickCount(Context context) {
        try {
            DMPreferencesUtil.save(context, "record_user_info", "charge_adclick_count", String.valueOf(DMPreferencesUtil.get(context, "record_user_info", "charge_adclick_count") != null ? 1 + Long.valueOf(DMPreferencesUtil.get(context, "record_user_info", "charge_adclick_count")).longValue() : 1L));
        } catch (Throwable unused) {
        }
    }

    public static long getUserUserChargeADClickCount(Context context) {
        try {
            String str = DMPreferencesUtil.get(context, "record_user_info", "charge_adclick_count");
            if (str != null) {
                return Long.valueOf(str).longValue();
            }
            return 0L;
        } catch (Throwable unused) {
            return 0L;
        }
    }

    public static void recordUserOtherADClickCount(Context context) {
        try {
            DMPreferencesUtil.save(context, "record_user_info", "other_adclick_count", String.valueOf(DMPreferencesUtil.get(context, "record_user_info", "other_adclick_count") != null ? 1 + Long.valueOf(DMPreferencesUtil.get(context, "record_user_info", "other_adclick_count")).longValue() : 1L));
        } catch (Throwable unused) {
        }
    }

    public static long getUserUserOtherADClickCount(Context context) {
        try {
            String str = DMPreferencesUtil.get(context, "record_user_info", "other_adclick_count");
            if (str != null) {
                return Long.valueOf(str).longValue();
            }
            return 0L;
        } catch (Throwable unused) {
            return 0L;
        }
    }

    public static void recordUserHomeButtonADClickCount(Context context) {
        try {
            DMPreferencesUtil.save(context, "record_user_info", "homebutton_adclick_count", String.valueOf(DMPreferencesUtil.get(context, "record_user_info", "homebutton_adclick_count") != null ? 1 + Long.valueOf(DMPreferencesUtil.get(context, "record_user_info", "homebutton_adclick_count")).longValue() : 1L));
        } catch (Throwable unused) {
        }
    }

    public static long getUserUserHomeButtonADClickCount(Context context) {
        try {
            String str = DMPreferencesUtil.get(context, "record_user_info", "homebutton_adclick_count");
            if (str != null) {
                return Long.valueOf(str).longValue();
            }
            return 0L;
        } catch (Throwable unused) {
            return 0L;
        }
    }
}
