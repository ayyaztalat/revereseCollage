package com.magic.video.editor.effect.gallery.present;

import android.net.Uri;
import com.magic.video.editor.effect.gallery.model.CSGalleryModel;
import com.magic.video.editor.effect.gallery.model.CSGalleryModelImpl;
import com.magic.video.editor.effect.gallery.view.CSGalleryView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class CSGalleryPresentImpl implements CSGalleryPresent {
    private CSGalleryModel galleryModel;
    private CSGalleryView galleryView;
    private final List<String> groupList;
    private final List<Uri> uriList = new ArrayList();

    @Override // com.magic.video.editor.effect.gallery.present.CSGalleryPresent
    public void pause() {
    }

    @Override // com.magic.video.editor.effect.gallery.present.CSGalleryPresent
    public void resume() {
    }

    public CSGalleryPresentImpl(CSGalleryView cSGalleryView, int i) {
        this.galleryView = cSGalleryView;
        this.galleryModel = new CSGalleryModelImpl(this, i);
        this.groupList = this.galleryModel.queryGalleryGroup(cSGalleryView.getContext());
    }

    @Override // com.magic.video.editor.effect.gallery.present.CSGalleryPresent
    public void openGallery() {
        if (isSelectedMax()) {
            this.galleryView.onShowMaxSelectedTips();
        } else {
            this.galleryView.onOpenGallery();
        }
    }

    @Override // com.magic.video.editor.effect.gallery.present.CSGalleryPresent
    public void openCamera() {
        if (isSelectedMax()) {
            this.galleryView.onShowMaxSelectedTips();
        } else {
            this.galleryView.onOpenCamera();
        }
    }

    @Override // com.magic.video.editor.effect.gallery.present.CSGalleryPresent
    public void selectedItem(Uri uri) {
        if (isSelectedMax()) {
            this.galleryView.onShowMaxSelectedTips();
            return;
        }
        this.uriList.add(uri);
        this.galleryView.onAddItem(this.uriList.size() - 1);
        this.galleryView.updateUi();
    }

    @Override // com.magic.video.editor.effect.gallery.present.CSGalleryPresent
    public void next() {
        this.galleryView.onNextBtnClick(this.uriList);
    }

    @Override // com.magic.video.editor.effect.gallery.present.CSGalleryPresent
    public void deleteItem(int i) {
        if (this.uriList.size() > i) {
            this.uriList.remove(i);
            this.galleryView.onDeleteItem(i);
            if (this.uriList.isEmpty()) {
                this.galleryView.onDeleteAll();
            }
            this.galleryView.updateUi();
        }
    }

    @Override // com.magic.video.editor.effect.gallery.present.CSGalleryPresent
    public void deleteAll() {
        this.uriList.clear();
        this.galleryView.onDeleteAll();
        this.galleryView.updateUi();
    }

    @Override // com.magic.video.editor.effect.gallery.present.CSGalleryPresent
    public void destroy() {
        this.galleryView = null;
        this.galleryModel = null;
    }

    @Override // com.magic.video.editor.effect.gallery.present.CSGalleryPresent
    public String getGroupItem(int i) {
        return this.groupList.get(i);
    }

    @Override // com.magic.video.editor.effect.gallery.present.CSGalleryPresent
    public int getGroupCount() {
        return this.groupList.size();
    }

    @Override // com.magic.video.editor.effect.gallery.present.CSGalleryPresent
    public int getSelectedImageCount() {
        return this.uriList.size();
    }

    @Override // com.magic.video.editor.effect.gallery.present.CSGalleryPresent
    public List<Uri> getSelectedImages() {
        return this.uriList;
    }

    @Override // com.magic.video.editor.effect.gallery.present.CSGalleryPresent
    public boolean isSelectedMax() {
        return this.uriList.size() >= this.galleryView.getSelectMaxNumber();
    }
}
