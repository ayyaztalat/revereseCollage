package com.magic.video.editor.effect.gallery.model;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.CancellationSignal;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

/* loaded from: classes2.dex */
public class CSMediaCursorLoader extends AsyncTaskLoader<Cursor> {
    private CancellationSignal mCancellationSignal;
    private Cursor mCursor;
    private final Loader<Cursor>.ForceLoadContentObserver mObserver;
    private final String[] mProjection;
    private final String mSelection;
    private final String[] mSelectionArgs;
    private final String mSortOrder;
    private Uri mUri;

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.loader.content.AsyncTaskLoader
    public Cursor loadInBackground() {
        Cursor cursor;
        synchronized (this) {
            if (isLoadInBackgroundCanceled()) {
                return null;
            }
            this.mCancellationSignal = new CancellationSignal();
            try {
                cursor = getContext().getContentResolver().query(this.mUri, this.mProjection, this.mSelection, this.mSelectionArgs, this.mSortOrder, this.mCancellationSignal);
            } catch (Exception e) {
                e.printStackTrace();
                cursor = null;
            }
            if (cursor != null) {
                try {
                    cursor.registerContentObserver(this.mObserver);
                } catch (RuntimeException e2) {
                    e2.printStackTrace();
                    if (!cursor.isClosed()) {
                        try {
                            cursor.close();
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    }
                    cursor = null;
                }
            }
            this.mCancellationSignal = null;
            return cursor;
        }
    }

    @Override // androidx.loader.content.AsyncTaskLoader
    public void cancelLoadInBackground() {
        super.cancelLoadInBackground();
        synchronized (this) {
            if (this.mCancellationSignal != null) {
                this.mCancellationSignal.cancel();
            }
        }
    }

    @Override // androidx.loader.content.Loader
    public void deliverResult(Cursor cursor) {
        if (isReset()) {
            if (cursor == null || cursor.isClosed()) {
                return;
            }
            cursor.close();
            return;
        }
        Cursor cursor2 = this.mCursor;
        this.mCursor = cursor;
        if (isStarted()) {
            super.deliverResult(cursor);
        }
        if (cursor2 == null || cursor2 == cursor || cursor2.isClosed()) {
            return;
        }
        cursor2.close();
    }

    public CSMediaCursorLoader(Context context, Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        super(context);
        this.mObserver = new ForceLoadContentObserver();
        this.mUri = uri;
        this.mProjection = strArr;
        this.mSelection = str;
        this.mSelectionArgs = strArr2;
        this.mSortOrder = str2;
    }

    @Override // androidx.loader.content.Loader
    protected void onStartLoading() {
        Cursor cursor = this.mCursor;
        if (cursor != null) {
            deliverResult(cursor);
        }
        if (takeContentChanged() || this.mCursor == null) {
            forceLoad();
        }
    }

    @Override // androidx.loader.content.Loader
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override // androidx.loader.content.AsyncTaskLoader
    public void onCanceled(Cursor cursor) {
        if (cursor == null || cursor.isClosed()) {
            return;
        }
        cursor.close();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.loader.content.Loader
    public void onReset() {
        super.onReset();
        onStopLoading();
        Cursor cursor = this.mCursor;
        if (cursor != null && !cursor.isClosed()) {
            this.mCursor.close();
        }
        this.mCursor = null;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public void setUri(Uri uri) {
        this.mUri = uri;
    }
}
