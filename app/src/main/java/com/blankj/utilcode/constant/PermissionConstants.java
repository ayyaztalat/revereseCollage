package com.blankj.utilcode.constant;

import android.Manifest;
import android.os.Build;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public final class PermissionConstants {
    public static final String ACTIVITY_RECOGNITION = "ACTIVITY_RECOGNITION";
    public static final String CALENDAR = "CALENDAR";
    public static final String CAMERA = "CAMERA";
    public static final String CONTACTS = "CONTACTS";
    public static final String LOCATION = "LOCATION";
    public static final String MICROPHONE = "MICROPHONE";
    public static final String PHONE = "PHONE";
    public static final String SENSORS = "SENSORS";
    public static final String SMS = "SMS";
    public static final String STORAGE = "STORAGE";
    private static final String[] GROUP_CALENDAR = {Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR};
    private static final String[] GROUP_CAMERA = {Manifest.permission.CAMERA};
    private static final String[] GROUP_CONTACTS = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.GET_ACCOUNTS};
    private static final String[] GROUP_LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION};
    private static final String[] GROUP_MICROPHONE = {Manifest.permission.RECORD_AUDIO};
    private static final String[] GROUP_PHONE = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_NUMBERS, Manifest.permission.CALL_PHONE, Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG, Manifest.permission.ADD_VOICEMAIL, Manifest.permission.USE_SIP, Manifest.permission.PROCESS_OUTGOING_CALLS, Manifest.permission.ANSWER_PHONE_CALLS};
    private static final String[] GROUP_PHONE_BELOW_O = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_NUMBERS, Manifest.permission.CALL_PHONE, Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG, Manifest.permission.ADD_VOICEMAIL, Manifest.permission.USE_SIP, Manifest.permission.PROCESS_OUTGOING_CALLS};
    private static final String[] GROUP_SENSORS = {Manifest.permission.BODY_SENSORS};
    private static final String[] GROUP_SMS = {Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_WAP_PUSH, Manifest.permission.RECEIVE_MMS};
    private static final String[] GROUP_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final String[] GROUP_ACTIVITY_RECOGNITION = {Manifest.permission.ACTIVITY_RECOGNITION};

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface PermissionGroup {
    }

    public static String[] getPermissions(String str) {
        if (str == null) {
            return new String[0];
        }
        char c = 65535;
        switch (str.hashCode()) {
            case -1611296843:
                if (str.equals("LOCATION")) {
                    c = 3;
                    break;
                }
                break;
            case -1596608551:
                if (str.equals(SENSORS)) {
                    c = 6;
                    break;
                }
                break;
            case -1166291365:
                if (str.equals(STORAGE)) {
                    c = '\b';
                    break;
                }
                break;
            case 82233:
                if (str.equals(SMS)) {
                    c = 7;
                    break;
                }
                break;
            case 76105038:
                if (str.equals(PHONE)) {
                    c = 5;
                    break;
                }
                break;
            case 140654183:
                if (str.equals(ACTIVITY_RECOGNITION)) {
                    c = '\t';
                    break;
                }
                break;
            case 215175251:
                if (str.equals(CONTACTS)) {
                    c = 2;
                    break;
                }
                break;
            case 604302142:
                if (str.equals(CALENDAR)) {
                    c = 0;
                    break;
                }
                break;
            case 1856013610:
                if (str.equals(MICROPHONE)) {
                    c = 4;
                    break;
                }
                break;
            case 1980544805:
                if (str.equals(CAMERA)) {
                    c = 1;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return GROUP_CALENDAR;
            case 1:
                return GROUP_CAMERA;
            case 2:
                return GROUP_CONTACTS;
            case 3:
                return GROUP_LOCATION;
            case 4:
                return GROUP_MICROPHONE;
            case 5:
                if (Build.VERSION.SDK_INT < 26) {
                    return GROUP_PHONE_BELOW_O;
                }
                return GROUP_PHONE;
            case 6:
                return GROUP_SENSORS;
            case 7:
                return GROUP_SMS;
            case '\b':
                return GROUP_STORAGE;
            case '\t':
                return GROUP_ACTIVITY_RECOGNITION;
            default:
                return new String[]{str};
        }
    }
}
