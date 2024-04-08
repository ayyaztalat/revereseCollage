package com.picspool.lib.type;

import androidx.exifinterface.media.ExifInterface;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes3.dex */
public class DMDataString {

    /* renamed from: c */
    private Calendar f1998c;
    private String mDay;
    private String mMonth;
    private String mWay;
    private String mYear;

    public DMDataString() {
        Calendar calendar = Calendar.getInstance();
        this.f1998c = calendar;
        calendar.setTimeZone(TimeZone.getDefault());
        this.mYear = String.valueOf(this.f1998c.get(1));
        this.mMonth = String.valueOf(this.f1998c.get(2) + 1);
        this.mDay = String.valueOf(this.f1998c.get(5));
        this.mWay = String.valueOf(this.f1998c.get(7));
    }

    public DMDataString(Calendar calendar) {
        this.f1998c = calendar;
        this.mYear = String.valueOf(calendar.get(1));
        this.mMonth = String.valueOf(calendar.get(2) + 1);
        this.mDay = String.valueOf(calendar.get(5));
        this.mWay = String.valueOf(calendar.get(7));
    }

    public DMDataString(Calendar calendar, TimeZone timeZone) {
        calendar.setTimeZone(timeZone);
        this.mYear = String.valueOf(calendar.get(1));
        this.mMonth = String.valueOf(calendar.get(2) + 1);
        this.mDay = String.valueOf(calendar.get(5));
        this.mWay = String.valueOf(calendar.get(7));
    }

    public String getCNStringData() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        this.mYear = String.valueOf(calendar.get(1));
        this.mMonth = String.valueOf(calendar.get(2) + 1);
        this.mDay = String.valueOf(calendar.get(5));
        this.mWay = String.valueOf(calendar.get(7));
        String valueOf = String.valueOf(calendar.get(7));
        if ("1".equals(this.mWay)) {
            valueOf = "天";
        } else if (ExifInterface.GPS_MEASUREMENT_2D.equals(this.mWay)) {
            valueOf = "一";
        } else if (ExifInterface.GPS_MEASUREMENT_3D.equals(this.mWay)) {
            valueOf = "二";
        } else if ("4".equals(this.mWay)) {
            valueOf = "三";
        } else if ("5".equals(this.mWay)) {
            valueOf = "四";
        } else if ("6".equals(this.mWay)) {
            valueOf = "五";
        } else if ("7".equals(this.mWay)) {
            valueOf = "六";
        }
        return this.mYear + "年" + this.mMonth + "月" + this.mDay + "日/星期" + valueOf;
    }

    public String getENWeekString() {
        return "1".equals(this.mWay) ? "Sunday" : ExifInterface.GPS_MEASUREMENT_2D.equals(this.mWay) ? "Monday" : ExifInterface.GPS_MEASUREMENT_3D.equals(this.mWay) ? "Tuesday" : "4".equals(this.mWay) ? "Wednesday" : "5".equals(this.mWay) ? "Thursday" : "6".equals(this.mWay) ? "Friday" : "7".equals(this.mWay) ? "Saturday" : String.valueOf(this.f1998c.get(7));
    }

    public static String getDateString(String str, Locale locale) {
        return new SimpleDateFormat(str, locale).format(new Date(System.currentTimeMillis()));
    }
}
