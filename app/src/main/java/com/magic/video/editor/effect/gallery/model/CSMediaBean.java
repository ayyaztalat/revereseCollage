package com.magic.video.editor.effect.gallery.model;

import android.net.Uri;

/* loaded from: classes2.dex */
public class CSMediaBean {
    private int duration;

    /* renamed from: id */
    private int f1531id;
    private boolean isPhoto;
    private boolean isVideo;
    private final Uri originalUri;
    private String realPath;
    private Uri srcUri;
    private Uri thumbnailUri;

    public CSMediaBean(Uri uri) {
        this.originalUri = uri;
    }

    public Uri getOriginalUri() {
        return this.originalUri;
    }

    public int getId() {
        return this.f1531id;
    }

    public void setId(int i) {
        this.f1531id = i;
    }

    public Uri getSrcUri() {
        return this.srcUri;
    }

    public void setSrcUri(Uri uri) {
        this.srcUri = uri;
    }

    public Uri getThumbnailUri() {
        return this.thumbnailUri;
    }

    public void setThumbnailUri(Uri uri) {
        this.thumbnailUri = uri;
    }

    public boolean isVideo() {
        return this.isVideo;
    }

    public void setVideo(boolean z) {
        this.isVideo = z;
    }

    public boolean isPhoto() {
        return this.isPhoto;
    }

    public void setPhoto(boolean z) {
        this.isPhoto = z;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int i) {
        this.duration = i;
    }

    public String getRealPath() {
        return this.realPath;
    }

    public void setRealPath(String str) {
        this.realPath = str;
    }
}
