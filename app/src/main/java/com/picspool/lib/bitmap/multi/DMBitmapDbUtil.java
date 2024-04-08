package com.picspool.lib.bitmap.multi;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes3.dex */
public class DMBitmapDbUtil {
    public static int imgageOrientation(Context context, Uri uri) {
        Cursor query = context.getContentResolver().query(uri, new String[]{"orientation"}, null, null, null);
        if (query == null) {
            return -1;
        }
        try {
            if (query.moveToFirst()) {
                return query.getInt(0);
            }
            return -1;
        } finally {
            query.close();
        }
    }

    public static String imagelPathFromURI(Context context, Uri uri) {
        Cursor query = context.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
        String string = (query == null || !query.moveToFirst()) ? "" : query.getString(query.getColumnIndexOrThrow("_data"));
        if (query != null) {
            query.close();
        }
        return string;
    }

    public static Bitmap getImageFromAssetsFile(Context context, String str) {
        Bitmap bitmap = null;
        try {
            InputStream open = context.getResources().getAssets().open(str);
            bitmap = BitmapFactory.decodeStream(open);
            open.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    public static Bitmap getImageFromAssetsFile(Resources resources, String str) {
        Bitmap bitmap = null;
        try {
            InputStream open = resources.getAssets().open(str);
            bitmap = BitmapFactory.decodeStream(open);
            open.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    public static Bitmap getImageFromResourceFile(Resources resources, int i) {
        Bitmap bitmap = null;
        try {
            InputStream openRawResource = resources.openRawResource(i);
            bitmap = BitmapFactory.decodeStream(openRawResource);
            openRawResource.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    public static Bitmap getImageFromResourceFile(Context context, int i) {
        Bitmap bitmap = null;
        try {
            InputStream openRawResource = context.getResources().openRawResource(i);
            bitmap = BitmapFactory.decodeStream(openRawResource);
            openRawResource.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return bitmap;
        }
    }
}
