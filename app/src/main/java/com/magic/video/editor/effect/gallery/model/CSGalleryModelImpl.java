package com.magic.video.editor.effect.gallery.model;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.magic.video.editor.effect.gallery.present.CSGalleryPresent;

import java.io.File;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class CSGalleryModelImpl implements CSGalleryModel {
    private final CSGalleryPresent galleryPresent;
    private final int mediaType;

    public CSGalleryModelImpl(CSGalleryPresent cSGalleryPresent, int i) {
        this.galleryPresent = cSGalleryPresent;
        this.mediaType = i;
    }

    @Override // com.magic.video.editor.effect.gallery.model.CSGalleryModel

    public java.util.List<String> queryGalleryGroup(Context r14) {

        ArrayList<String> directories = new ArrayList<>();

        ContentResolver contentResolver = r14.getContentResolver();
        Uri queryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;


        String[] projection = new String[]{
                MediaStore.Images.Media.DATA
        };
        String includeImages = MediaStore.Images.Media.MIME_TYPE + " LIKE 'image/%' ";
        String excludeGif =  " AND " + MediaStore.Images.Media.MIME_TYPE + " != 'image/gif' " + " AND " + MediaStore.Images.Media.MIME_TYPE + " != 'image/giff' ";
        String selection =  includeImages + excludeGif;

        Cursor cursor = contentResolver.query(queryUri, projection, selection, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {

                @SuppressLint("Range") String photoUri = cursor.getString(cursor.getColumnIndex(projection[0]));
                if(!directories.contains(new File(photoUri).getParent())){
                    directories.add(new File(photoUri).getParent());
                }

            } while (cursor.moveToNext());
        }

        return directories;

       }

    @SuppressLint("Range")
    public String getRealPathFromURI(Context context, Uri uri) {
        Cursor query = context.getContentResolver().query(uri, null, null, null, null);
        if (query == null) {
            return uri.getPath();
        }
        query.moveToFirst();
        return query.getString(query.getColumnIndex("_data"));
    }
}
