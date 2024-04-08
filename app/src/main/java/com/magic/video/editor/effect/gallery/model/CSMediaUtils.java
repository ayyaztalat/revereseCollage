package com.magic.video.editor.effect.gallery.model;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.picspool.lib.database.DMResRecordBean;

/* loaded from: classes2.dex */
public class CSMediaUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    public static Uri getPhotoUri(Cursor cursor) {
        return getMediaUri(cursor, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    }

    public static Uri getVideoUri(Cursor cursor) {
        return getMediaUri(cursor, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
    }

    public static int getVideoDurion(Cursor cursor) {
        return cursor.getInt(cursor.getColumnIndex("duration"));
    }

    private static Uri getMediaUri(Cursor cursor, Uri uri) {
        return Uri.withAppendedPath(uri, cursor.getString(cursor.getColumnIndex(DMResRecordBean.f1945ID)));
    }

    public static CSMediaBean parseVideoUri(Context context, Uri uri) {
        Cursor query = context.getContentResolver().query(uri, new String[]{"_data", DMResRecordBean.f1945ID, "duration", "datetaken"}, null, null, "datetaken DESC");
        CSMediaBean cSMediaBean = new CSMediaBean(uri);
        if (query != null) {
            query.getCount();
            if (query.moveToFirst()) {
                do {
                    cSMediaBean.setSrcUri(string2Uri(query.getString(query.getColumnIndex("_data"))));
                    cSMediaBean.setId(query.getInt(query.getColumnIndex(DMResRecordBean.f1945ID)));
                    Uri.Builder buildUpon = MediaStore.Video.Media.EXTERNAL_CONTENT_URI.buildUpon();
                    cSMediaBean.setRealPath(buildUpon.appendPath(cSMediaBean.getId() + "").build().toString());
                    cSMediaBean.setDuration(query.getInt(query.getColumnIndex("duration")));
                    cSMediaBean.setVideo(true);
                    cSMediaBean.setPhoto(false);
                } while (query.moveToNext());
                query.close();
            } else {
                query.close();
            }
        }
        return cSMediaBean;
    }

    private static Uri string2Uri(String str) {
        return Uri.parse("file://" + str);
    }
}
