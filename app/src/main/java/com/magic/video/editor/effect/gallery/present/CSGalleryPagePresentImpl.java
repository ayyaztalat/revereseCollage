package com.magic.video.editor.effect.gallery.present;

import android.database.Cursor;
import androidx.fragment.app.FragmentActivity;
import com.magic.video.editor.effect.gallery.model.CSGalleryPageModel;
import com.magic.video.editor.effect.gallery.model.CSGalleryPageModelImpl;
import com.magic.video.editor.effect.gallery.view.CSGalleryPagerView;

/* loaded from: classes2.dex */
public class CSGalleryPagePresentImpl implements CSGalleryPagePresent {
    private CSGalleryPageModel galleryPageModel = new CSGalleryPageModelImpl(this);
    private CSGalleryPagerView galleryPagerView;

    public CSGalleryPagePresentImpl(CSGalleryPagerView cSGalleryPagerView) {
        this.galleryPagerView = cSGalleryPagerView;
    }

    @Override // com.magic.video.editor.effect.gallery.present.CSGalleryPagePresent
    public void requestPhotos(boolean z) {
        if (this.galleryPagerView != null) {
            this.galleryPageModel.requestPhotos(z);
        }
    }

    @Override // com.magic.video.editor.effect.gallery.present.CSGalleryPagePresent
    public FragmentActivity getActivity() {
        CSGalleryPagerView cSGalleryPagerView = this.galleryPagerView;
        if (cSGalleryPagerView != null) {
            return cSGalleryPagerView.getActivity();
        }
        return null;
    }

    @Override // com.magic.video.editor.effect.gallery.present.CSGalleryPagePresent
    public String getSelectFolder() {
        CSGalleryPagerView cSGalleryPagerView = this.galleryPagerView;
        if (cSGalleryPagerView != null) {
            return cSGalleryPagerView.getSelectFolder();
        }
        return null;
    }

    @Override // com.magic.video.editor.effect.gallery.present.CSGalleryPagePresent
    public void switchToError() {
        CSGalleryPagerView cSGalleryPagerView = this.galleryPagerView;
        if (cSGalleryPagerView != null) {
            cSGalleryPagerView.switchToError();
        }
    }

    @Override // com.magic.video.editor.effect.gallery.present.CSGalleryPagePresent
    public void switchToData(Cursor cursor) {
        CSGalleryPagerView cSGalleryPagerView = this.galleryPagerView;
        if (cSGalleryPagerView != null) {
            cSGalleryPagerView.switchToData(cursor);
        }
    }

    @Override // com.magic.video.editor.effect.gallery.present.CSGalleryPagePresent
    public void swapCursor(Cursor cursor) {
        CSGalleryPagerView cSGalleryPagerView = this.galleryPagerView;
        if (cSGalleryPagerView != null) {
            cSGalleryPagerView.swapCursor(cursor);
        }
    }

    @Override // com.magic.video.editor.effect.gallery.present.CSGalleryPagePresent
    public void requestVideos(boolean z) {
        CSGalleryPageModel cSGalleryPageModel = this.galleryPageModel;
        if (cSGalleryPageModel != null) {
            cSGalleryPageModel.requestVideos(z);
        }
    }

    @Override // com.magic.video.editor.effect.gallery.present.CSGalleryPagePresent
    public void destroy() {
        this.galleryPageModel.destroy();
        this.galleryPageModel = null;
        this.galleryPagerView = null;
    }
}
