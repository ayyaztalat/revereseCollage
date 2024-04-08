package com.magic.video.editor.effect.gallery.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class CSMediaOptions implements Parcelable {
    public static final Creator<CSMediaOptions> CREATOR = new Creator<CSMediaOptions>() { // from class: com.magic.video.editor.effect.gallery.model.CSMediaOptions.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CSMediaOptions[] newArray(int i) {
            return new CSMediaOptions[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CSMediaOptions createFromParcel(Parcel parcel) {
            return new CSMediaOptions(parcel);
        }
    };
    private final int aspectX;
    private final int aspectY;
    private final boolean canSelectMultiPhoto;
    private final boolean canSelectMultiVideo;
    private final boolean canSelectPhoto;
    private final boolean canSelectVideo;
    private final File croppedFile;
    private final boolean fixAspectRatio;
    private final boolean isCropped;
    private final int maxVideoDuration;
    private List<CSMediaItem> mediaListSelected;
    private final int minVideoDuration;
    private final File photoCaptureFile;
    private final boolean showWarningVideoDuration;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* loaded from: classes2.dex */
    public static class Builder {
        private File croppedFile;
        private List<CSMediaItem> mediaListSelected;
        private File photoFile;
        private int aspectX = 1;
        private int aspectY = 1;
        private boolean canSelectMultiPhoto = false;
        private boolean canSelectMultiVideo = false;
        private boolean canSelectPhoto = false;
        private boolean canSelectVideo = false;
        private boolean fixAspectRatio = true;
        private boolean isCropped = false;
        private int maxVideoDuration = Integer.MAX_VALUE;
        private int minVideoDuration = 0;
        private boolean showWarningBeforeRecord = false;

        public Builder setShowWarningBeforeRecordVideo(boolean z) {
            this.showWarningBeforeRecord = z;
            return this;
        }

        public Builder setMediaListSelected(List<CSMediaItem> list) {
            this.mediaListSelected = list;
            return this;
        }

        public Builder setCroppedFile(File file) {
            this.croppedFile = file;
            return this;
        }

        public Builder setFixAspectRatio(boolean z) {
            this.fixAspectRatio = z;
            return this;
        }

        public Builder setAspectX(int i) {
            this.aspectX = i;
            return this;
        }

        public Builder setAspectY(int i) {
            this.aspectY = i;
            return this;
        }

        public Builder setIsCropped(boolean z) {
            this.isCropped = z;
            return this;
        }

        public Builder canSelectMultiPhoto(boolean z) {
            this.canSelectMultiPhoto = z;
            return this;
        }

        public Builder canSelectMultiVideo(boolean z) {
            this.canSelectMultiVideo = z;
            if (z) {
                this.maxVideoDuration = Integer.MAX_VALUE;
                this.minVideoDuration = 0;
            }
            return this;
        }

        public Builder setMaxVideoDuration(int i) {
            if (i > 0) {
                this.maxVideoDuration = i;
                this.canSelectMultiVideo = false;
                return this;
            }
            throw new IllegalArgumentException("Max duration must be > 0");
        }

        public Builder setMinVideoDuration(int i) {
            if (i > 0) {
                this.minVideoDuration = i;
                this.canSelectMultiVideo = false;
                return this;
            }
            throw new IllegalArgumentException("Min duration must be > 0");
        }

        public Builder canSelectBothPhotoVideo() {
            this.canSelectPhoto = true;
            this.canSelectVideo = true;
            return this;
        }

        public Builder selectVideo() {
            this.canSelectVideo = true;
            this.canSelectPhoto = false;
            return this;
        }

        public Builder selectPhoto() {
            this.canSelectPhoto = true;
            this.canSelectVideo = false;
            return this;
        }

        public Builder setPhotoCaptureFile(File file) {
            this.photoFile = file;
            return this;
        }

        public CSMediaOptions build() {
            return new CSMediaOptions(this);
        }
    }

    public boolean isShowWarningVideoDuration() {
        return this.showWarningVideoDuration;
    }

    public List<CSMediaItem> getMediaListSelected() {
        return this.mediaListSelected;
    }

    public File getCroppedFile() {
        return this.croppedFile;
    }

    public int getAspectX() {
        return this.aspectX;
    }

    public int getAspectY() {
        return this.aspectY;
    }

    public boolean isFixAspectRatio() {
        return this.fixAspectRatio;
    }

    public boolean canSelectMultiPhoto() {
        return this.canSelectMultiPhoto;
    }

    public boolean canSelectMultiVideo() {
        return this.canSelectMultiVideo;
    }

    public boolean isCropped() {
        return this.isCropped;
    }

    public int getMaxVideoDuration() {
        return this.maxVideoDuration;
    }

    public int getMinVideoDuration() {
        return this.minVideoDuration;
    }

    public boolean canSelectPhotoAndVideo() {
        return this.canSelectPhoto && this.canSelectVideo;
    }

    public boolean canSelectPhoto() {
        return this.canSelectPhoto;
    }

    public boolean canSelectVideo() {
        return this.canSelectVideo;
    }

    public File getPhotoFile() {
        return this.photoCaptureFile;
    }

    private CSMediaOptions(Builder builder) {
        this.mediaListSelected = new ArrayList();
        this.canSelectMultiPhoto = builder.canSelectMultiPhoto;
        this.canSelectMultiVideo = builder.canSelectMultiVideo;
        this.isCropped = builder.isCropped;
        this.maxVideoDuration = builder.maxVideoDuration;
        this.minVideoDuration = builder.minVideoDuration;
        this.canSelectPhoto = builder.canSelectPhoto;
        this.canSelectVideo = builder.canSelectVideo;
        this.photoCaptureFile = builder.photoFile;
        this.aspectX = builder.aspectX;
        this.aspectY = builder.aspectY;
        this.fixAspectRatio = builder.fixAspectRatio;
        this.croppedFile = builder.croppedFile;
        this.mediaListSelected = builder.mediaListSelected;
        this.showWarningVideoDuration = builder.showWarningBeforeRecord;
    }

    public static CSMediaOptions createDefault() {
        return new Builder().build();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.canSelectMultiPhoto ? 1 : 0);
        parcel.writeInt(this.canSelectMultiVideo ? 1 : 0);
        parcel.writeInt(this.canSelectPhoto ? 1 : 0);
        parcel.writeInt(this.canSelectVideo ? 1 : 0);
        parcel.writeInt(this.isCropped ? 1 : 0);
        parcel.writeInt(this.fixAspectRatio ? 1 : 0);
        parcel.writeInt(this.showWarningVideoDuration ? 1 : 0);
        parcel.writeInt(this.maxVideoDuration);
        parcel.writeInt(this.minVideoDuration);
        parcel.writeInt(this.aspectX);
        parcel.writeInt(this.aspectY);
        parcel.writeSerializable(this.photoCaptureFile);
        parcel.writeSerializable(this.croppedFile);
        parcel.writeTypedList(this.mediaListSelected);
    }

    private CSMediaOptions(Parcel parcel) {
        this.mediaListSelected = new ArrayList();
        this.canSelectMultiPhoto = parcel.readInt() != 0;
        this.canSelectMultiVideo = parcel.readInt() != 0;
        this.canSelectPhoto = parcel.readInt() != 0;
        this.canSelectVideo = parcel.readInt() != 0;
        this.isCropped = parcel.readInt() != 0;
        this.fixAspectRatio = parcel.readInt() != 0;
        this.showWarningVideoDuration = parcel.readInt() != 0;
        this.maxVideoDuration = parcel.readInt();
        this.minVideoDuration = parcel.readInt();
        this.aspectX = parcel.readInt();
        this.aspectY = parcel.readInt();
        this.photoCaptureFile = (File) parcel.readSerializable();
        this.croppedFile = (File) parcel.readSerializable();
        parcel.readTypedList(this.mediaListSelected, CSMediaItem.CREATOR);
    }

    public int hashCode() {
        return (((((((((((this.canSelectMultiPhoto ? 1231 : 1237) + 31) * 31) + (this.canSelectPhoto ? 1231 : 1237)) * 31) + (this.canSelectVideo ? 1231 : 1237)) * 31) + (this.isCropped ? 1231 : 1237)) * 31) + this.maxVideoDuration) * 31) + this.minVideoDuration;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CSMediaOptions cSMediaOptions = (CSMediaOptions) obj;
        return this.canSelectMultiPhoto == cSMediaOptions.canSelectMultiPhoto && this.canSelectPhoto == cSMediaOptions.canSelectPhoto && this.canSelectVideo == cSMediaOptions.canSelectVideo && this.isCropped == cSMediaOptions.isCropped && this.maxVideoDuration == cSMediaOptions.maxVideoDuration && this.minVideoDuration == cSMediaOptions.minVideoDuration;
    }
}
