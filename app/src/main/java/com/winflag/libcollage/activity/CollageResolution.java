package com.winflag.libcollage.activity;

/* loaded from: classes.dex */
public class CollageResolution {
    public static int getCollageCropSize(int i) {
        switch (i) {
            case 1:
                return 1280;
            case 2:
                return 960;
            case 3:
                return 800;
            case 4:
                return 700;
            case 5:
                return 600;
            case 6:
                return 520;
            case 7:
                return 460;
            case 8:
                return 450;
            case 9:
                return 430;
            default:
                return 612;
        }
    }
}
