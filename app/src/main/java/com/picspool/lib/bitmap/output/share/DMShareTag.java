package com.picspool.lib.bitmap.output.share;

import android.app.Activity;
import android.content.Context;
import com.picspool.lib.packages.DMAppPackages;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMShareTag {
    public static String bestSquareTag;
    public static String blendpicTag;
    public static String caesarApp;
    public static String faceproTag;
    public static String fontoverTag;
    public static String instabokenTag;
    public static String instaboxTag;
    public static String instacollageTag;
    public static String instafaceTag;
    public static String instafaceoffTag;
    public static String instaframeTag;
    public static String instagridTag;
    public static String instamirrorTag;
    public static String instapopartTag;
    public static String instashapeTag;
    public static String instasplitTag;
    public static String instasquareTag;
    public static String instatouchTag;
    public static String lidowTag;
    public static String mirroreditorTag;
    public static String mirrorsquareTag;
    public static String photomirrorTag;
    public static String piceditorTag;
    public static String squareeditorTag;
    public static String squaremakerTag;
    public static String squarepicTag;
    public static String xcollageTag;

    public static void initTag(Context context) {
        caesarApp = context.getResources().getString(R.string.tag_app_from) + "@caesarapp ";
        String string = context.getResources().getString(R.string.tag_made_with);
        instasquareTag = "(" + string + "#instasquare )";
        instashapeTag = "(" + string + "#instashape )";
        instaboxTag = "(" + string + "#instabox )";
        instapopartTag = "(" + string + "#instapopart )";
        instabokenTag = "(" + string + "#instaboken )";
        instagridTag = "(" + string + "#instagrid )";
        instamirrorTag = "(" + string + "#mirrorpic )";
        instacollageTag = "(" + string + "#collagepro )";
        instatouchTag = "(" + string + "#instatouch )";
        photomirrorTag = "(" + string + "#photomirror )";
        instaframeTag = "(" + string + "#instaframe )";
        instafaceTag = "(" + string + "#instaface )";
        instasplitTag = "(" + string + "#instasplit )";
        blendpicTag = "(" + string + "#blendpic )";
        instafaceoffTag = "(" + string + "#instafaceoff )";
        lidowTag = "(" + string + "#lidow )";
        piceditorTag = "(" + string + "#piceditor )";
        faceproTag = "(" + string + "#facepro )";
        squarepicTag = "(" + string + "#squarepic )";
        xcollageTag = "(" + string + "#photocreator )";
        fontoverTag = "(" + string + "#fontover )";
        squaremakerTag = "(" + string + "#squaremaker )";
        squareeditorTag = "(" + string + "#instasquare )";
        mirroreditorTag = "(" + string + "#mirroreditor )";
        bestSquareTag = "(" + string + "#BestSquare )";
        mirrorsquareTag = "(" + string + "#mirrorsquare )";
    }

    public static String getDefaultTag(Activity activity) {
        String packageName = activity.getPackageName();
        if (caesarApp == null) {
            initTag(activity);
        }
        return DMAppPackages.getAppName(packageName);
    }
}
