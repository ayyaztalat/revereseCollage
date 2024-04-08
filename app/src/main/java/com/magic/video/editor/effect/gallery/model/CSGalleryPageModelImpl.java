package com.magic.video.editor.effect.gallery.model;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import com.magic.video.editor.effect.gallery.present.CSGalleryPagePresent;
import com.picspool.lib.database.DMResRecordBean;

/* loaded from: classes2.dex */
public class CSGalleryPageModelImpl implements CSGalleryPageModel, LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LOADER_EXTRA_PROJECT = "loader_extra_project";
    private static final String LOADER_EXTRA_URI = "loader_extra_uri";
    private static final String LOADER_EXTRA_WHERE = "loader_extra_loader_where";
    private static int NewNumber;
    private final CSGalleryPagePresent galleryPagePresent;
    private final int loaderID;

    public CSGalleryPageModelImpl(CSGalleryPagePresent cSGalleryPagePresent) {
        this.galleryPagePresent = cSGalleryPagePresent;
        int i = NewNumber + 1;
        NewNumber = i;
        this.loaderID = i;
    }

    @Override // com.magic.video.editor.effect.gallery.model.CSGalleryPageModel
    public void requestPhotos(boolean z) {
        if (this.galleryPagePresent.getSelectFolder() != null) {
            String[] strArr = {DMResRecordBean.f1945ID, "_data"};
            requestMedia(MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString(), strArr, "_data LIKE '" + this.galleryPagePresent.getSelectFolder() + "/%' AND _data NOT LIKE '" + this.galleryPagePresent.getSelectFolder() + "/%/%'", z);
            return;
        }
        requestMedia(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{DMResRecordBean.f1945ID}, z);
    }

    @Override // com.magic.video.editor.effect.gallery.model.CSGalleryPageModel
    public void requestVideos(boolean z) {
        String[] strArr = {DMResRecordBean.f1945ID, "duration", "_data"};
        if (this.galleryPagePresent.getSelectFolder() != null) {
            requestMedia(MediaStore.Video.Media.EXTERNAL_CONTENT_URI.toString(), strArr, "_data LIKE '" + this.galleryPagePresent.getSelectFolder() + "%' AND _data NOT LIKE '" + this.galleryPagePresent.getSelectFolder() + "/%/%'", z);
            return;
        }
        requestMedia(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, strArr, z);
    }

    @Override // com.magic.video.editor.effect.gallery.model.CSGalleryPageModel
    public void destroy() {
        LoaderManager.getInstance(this.galleryPagePresent.getActivity()).destroyLoader(this.loaderID);
    }

    private void requestMedia(Uri uri, String[] strArr, boolean z) {
        Bundle bundle = new Bundle();
        bundle.putStringArray(LOADER_EXTRA_PROJECT, strArr);
        bundle.putString(LOADER_EXTRA_URI, uri.toString());
        if (z) {
            LoaderManager.getInstance(this.galleryPagePresent.getActivity()).restartLoader(0, bundle, this);
        } else {
            LoaderManager.getInstance(this.galleryPagePresent.getActivity()).initLoader(0, bundle, this);
        }
    }

    private void requestMedia(String str, String[] strArr, String str2, boolean z) {
        Bundle bundle = new Bundle();
        bundle.putStringArray(LOADER_EXTRA_PROJECT, strArr);
        bundle.putString(LOADER_EXTRA_URI, str);
        bundle.putString(LOADER_EXTRA_WHERE, str2);
        if (z) {
            LoaderManager.getInstance(this.galleryPagePresent.getActivity()).restartLoader(this.loaderID, bundle, this);
        } else {
            LoaderManager.getInstance(this.galleryPagePresent.getActivity()).initLoader(this.loaderID, bundle, this);
        }
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String str;
        Uri parse = Uri.parse(bundle.getString(LOADER_EXTRA_URI));
        String[] stringArray = bundle.getStringArray(LOADER_EXTRA_PROJECT);
        String string = bundle.getString(LOADER_EXTRA_WHERE);
        if (TextUtils.isEmpty(string)) {
            str = " _data NOT LIKE '%.gif' ";
        } else {
            str = string + " AND _data NOT LIKE '%.gif' ";
        }
        return new CSMediaCursorLoader(this.galleryPagePresent.getActivity(), parse, stringArray, str, null, "date_added DESC");
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.isClosed() || cursor.getCount() == 0) {
            return;
        }
        bindData(cursor);
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public void onLoaderReset(Loader<Cursor> loader) {
        CSGalleryPagePresent cSGalleryPagePresent = this.galleryPagePresent;
        if (cSGalleryPagePresent != null) {
            cSGalleryPagePresent.swapCursor(null);
        }
    }

    private void bindData(Cursor cursor) {
        if (this.galleryPagePresent != null && (cursor == null || cursor.isClosed() || cursor.getCount() <= 0)) {
            this.galleryPagePresent.switchToError();
            return;
        }
        CSGalleryPagePresent cSGalleryPagePresent = this.galleryPagePresent;
        if (cSGalleryPagePresent != null) {
            cSGalleryPagePresent.switchToData(cursor);
        }
    }
}
