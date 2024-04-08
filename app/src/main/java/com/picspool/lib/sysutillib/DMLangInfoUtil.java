package com.picspool.lib.sysutillib;

import java.util.Locale;

/* loaded from: classes3.dex */
public class DMLangInfoUtil {
    public static boolean isLangChinese() {
        return Locale.getDefault().getLanguage().equalsIgnoreCase("zh");
    }

    public static boolean isLangSimpleChinese() {
        Locale locale = Locale.getDefault();
        return locale.getLanguage().equalsIgnoreCase("zh") && locale.getCountry().equalsIgnoreCase("cn");
    }

    public static boolean isLangTraditionalChinese() {
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();
        String country = locale.getCountry();
        return (language.equalsIgnoreCase("zh") && country.equalsIgnoreCase("TW")) || (language.equalsIgnoreCase("zh") && country.equalsIgnoreCase("HK"));
    }
}
