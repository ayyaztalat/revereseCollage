package com.picspool.lib.media;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.InputStream;
import com.picspool.lib.bitmap.DMBitmapUtil;

/* loaded from: classes3.dex */
public class DMMediaItemRes implements Parcelable {
    public static final Creator<DMMediaItemRes> CREATOR = new Creator<DMMediaItemRes>() { // from class: com.picspool.lib.media.DMMediaItemRes.1
        @Override // android.os.Parcelable.Creator
        public DMMediaItemRes createFromParcel(Parcel parcel) {
            return new DMMediaItemRes(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public DMMediaItemRes[] newArray(int i) {
            return new DMMediaItemRes[i];
        }
    };
    protected String buketDisplayName;
    protected String buketId;
    protected String data;
    protected long dateAdded;
    protected String imgId;
    protected int imgSize;
    protected boolean isCamera;
    protected boolean isSelected;
    protected String mThumbPath;
    protected int orientation;
    protected String thumbId;

    private String scaleThumbPath(ContentResolver contentResolver, String str) {
        return null;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Bitmap getThumbnail(Context context, int i, Bitmap.Config config) {
        return null;
    }

    public String thumbPath(ContentResolver contentResolver, String str) {
        return null;
    }

    public DMMediaItemRes() {
        this.mThumbPath = null;
        this.isSelected = false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public DMMediaItemRes(Parcel parcel) {
        this.mThumbPath = null;
        this.isSelected = false;
        this.imgId = parcel.readString();
        this.thumbId = parcel.readString();
        this.isCamera = parcel.readByte() != 0;
        this.buketId = parcel.readString();
        this.buketDisplayName = parcel.readString();
        this.data = parcel.readString();
        this.dateAdded = parcel.readLong();
        this.mThumbPath = parcel.readString();
        this.imgSize = parcel.readInt();
        this.orientation = parcel.readInt();
        this.isSelected = parcel.readByte() != 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.imgId);
        parcel.writeString(this.thumbId);
        parcel.writeByte(this.isCamera ? (byte) 1 : (byte) 0);
        parcel.writeString(this.buketId);
        parcel.writeString(this.buketDisplayName);
        parcel.writeString(this.data);
        parcel.writeLong(this.dateAdded);
        parcel.writeString(this.mThumbPath);
        parcel.writeInt(this.imgSize);
        parcel.writeInt(this.orientation);
        parcel.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    public String getThumbPath() {
        return this.mThumbPath;
    }

    public void setThumbPath(String str) {
        this.mThumbPath = str;
    }

    public String getImgId() {
        return this.imgId;
    }

    public void setImgId(String str) {
        this.imgId = str;
    }

    public Uri getImgUri() {
        return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, this.imgId);
    }

    public String getThumbId() {
        return this.thumbId;
    }

    public void setThumbId(String str) {
        this.thumbId = str;
    }

    public boolean isCamera() {
        return this.isCamera;
    }

    public void setCamera(boolean z) {
        this.isCamera = z;
    }

    public String getBuketDisplayName() {
        return this.buketDisplayName;
    }

    public void setBuketDisplayName(String str) {
        this.buketDisplayName = str;
    }

    public String getBuketId() {
        return this.buketId;
    }

    public void setBuketId(String str) {
        this.buketId = str;
    }

    public int getImgSize() {
        return this.imgSize;
    }

    public void setImgSize(int i) {
        this.imgSize = i;
    }

    public Bitmap getGalleryThumbnail(Context context) {
        return getThumbnail(context, 120, Bitmap.Config.ARGB_4444);
    }

    public Bitmap getThumbnail(Context context, int i) {
        return getThumbnail(context, i, Bitmap.Config.ARGB_4444);
    }

    public String getData() {
        return this.data;
    }

    public void setData(String str) {
        this.data = str;
    }

    public long getDateAdded() {
        return this.dateAdded;
    }

    public void setDateAdded(long j) {
        this.dateAdded = j;
    }

    public int getOrientation() {
        return this.orientation;
    }

    public void setOrientation(int i) {
        this.orientation = i;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean z) {
        this.isSelected = z;
    }

    public static Bitmap CropSquareImage(Context context, Uri uri, int i) {
        String scheme = uri.getScheme();
        if (scheme.equalsIgnoreCase("file")) {
            try {
                DMBitmapUtil.exifOrientationToDegrees(new ExifInterface(uri.getPath()).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1));
            } catch (Exception unused) {
            }
        } else if (scheme.equalsIgnoreCase(FirebaseAnalytics.Param.CONTENT)) {
            DMBitmapUtil.getOrientation(context, uri);
        }
        try {
            InputStream openInputStream = context.getContentResolver().openInputStream(uri);
            BitmapFactory.Options optionOfBitmapFromStream = DMBitmapUtil.optionOfBitmapFromStream(context.getContentResolver().openInputStream(uri));
            openInputStream.close();
            int i2 = optionOfBitmapFromStream.outHeight;
            int i3 = optionOfBitmapFromStream.outWidth;
            int i4 = (int) ((i2 > i3 ? i2 / i3 : i3 / i2) * i);
            InputStream openInputStream2 = context.getContentResolver().openInputStream(uri);
            Bitmap sampledBitmapFromStream = DMBitmapUtil.sampledBitmapFromStream(openInputStream2, optionOfBitmapFromStream, i4, i4);
            openInputStream2.close();
            return ThumbnailUtils.extractThumbnail(sampledBitmapFromStream, i, i, 2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap getImageThumbnail(String str, int i, int i2) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        int i3 = options.outHeight;
        int i4 = options.outWidth / i;
        int i5 = i3 / i2;
        if (i4 >= i5) {
            i4 = i5;
        }
        options.inSampleSize = i4 > 0 ? i4 : 1;
        options.inJustDecodeBounds = false;
        return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(str, options), i, i2, 2);
    }

    public DMMediaItemRes copy() {
        DMMediaItemRes dMMediaItemRes = new DMMediaItemRes();
        dMMediaItemRes.imgId = this.imgId;
        dMMediaItemRes.thumbId = this.thumbId;
        dMMediaItemRes.isCamera = this.isCamera;
        dMMediaItemRes.buketId = this.buketId;
        dMMediaItemRes.buketDisplayName = this.buketDisplayName;
        dMMediaItemRes.data = this.data;
        dMMediaItemRes.dateAdded = this.dateAdded;
        dMMediaItemRes.mThumbPath = this.mThumbPath;
        dMMediaItemRes.orientation = this.orientation;
        return dMMediaItemRes;
    }
}
