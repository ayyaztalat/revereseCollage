package com.magic.video.editor.effect.gallery.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/* loaded from: classes2.dex */
public class CSMediaItem implements Parcelable {
    public static final Creator<CSMediaItem> CREATOR = new Creator<CSMediaItem>() { // from class: com.magic.video.editor.effect.gallery.model.CSMediaItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CSMediaItem[] newArray(int i) {
            return new CSMediaItem[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CSMediaItem createFromParcel(Parcel parcel) {
            return new CSMediaItem(parcel);
        }
    };
    private int type;
    private Uri uriCropped;
    private Uri uriOrigin;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CSMediaItem(int i, Uri uri) {
        this.type = i;
        this.uriOrigin = uri;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public Uri getUriOrigin() {
        return this.uriOrigin;
    }

    public boolean isVideo() {
        return this.type == 2;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.type);
        Uri uri = this.uriCropped;
        if (uri == null) {
            parcel.writeString(null);
        } else {
            parcel.writeString(uri.toString());
        }
        Uri uri2 = this.uriOrigin;
        if (uri2 == null) {
            parcel.writeString(null);
        } else {
            parcel.writeString(uri2.toString());
        }
    }

    private CSMediaItem(Parcel parcel) {
        this.type = parcel.readInt();
        String readString = parcel.readString();
        if (!TextUtils.isEmpty(readString)) {
            this.uriCropped = Uri.parse(readString);
        }
        String readString2 = parcel.readString();
        if (TextUtils.isEmpty(readString2)) {
            return;
        }
        this.uriOrigin = Uri.parse(readString2);
    }

    public int hashCode() {
        Uri uri = this.uriCropped;
        int hashCode = ((uri == null ? 0 : uri.hashCode()) + 31) * 31;
        Uri uri2 = this.uriOrigin;
        return hashCode + (uri2 != null ? uri2.hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CSMediaItem cSMediaItem = (CSMediaItem) obj;
        Uri uri = this.uriCropped;
        if (uri == null) {
            if (cSMediaItem.uriCropped != null) {
                return false;
            }
        } else if (!uri.equals(cSMediaItem.uriCropped)) {
            return false;
        }
        Uri uri2 = this.uriOrigin;
        if (uri2 == null) {
            return cSMediaItem.uriOrigin == null;
        }
        return uri2.equals(cSMediaItem.uriOrigin);
    }

    public String toString() {
        return "CSPMediaItem [type=" + this.type + ", uriCropped=" + this.uriCropped + ", uriOrigin=" + this.uriOrigin + "]";
    }
}
