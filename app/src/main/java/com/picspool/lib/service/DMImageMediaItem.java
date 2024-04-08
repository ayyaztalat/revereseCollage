package com.picspool.lib.service;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.InputStream;
import com.picspool.lib.bitmap.DMBitmapCrop;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.database.DMResRecordBean;
import com.picspool.lib.media.DMMediaItemRes;

/* loaded from: classes3.dex */
public class DMImageMediaItem extends DMMediaItemRes implements Parcelable {
    public static final Creator<DMImageMediaItem> CREATOR = new Creator<DMImageMediaItem>() { // from class: com.picspool.lib.service.DMImageMediaItem.1
        @Override // android.os.Parcelable.Creator
        public DMImageMediaItem createFromParcel(Parcel parcel) {
            return new DMImageMediaItem(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public DMImageMediaItem[] newArray(int i) {
            return new DMImageMediaItem[i];
        }
    };

    @Override // com.picspool.lib.media.DMMediaItemRes, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DMImageMediaItem() {
    }

    protected DMImageMediaItem(Parcel parcel) {
        super(parcel);
    }

    @Override // com.picspool.lib.media.DMMediaItemRes, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }

    @Override // com.picspool.lib.media.DMMediaItemRes
    public Uri getImgUri() {
        return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, this.imgId);
    }

    @Override // com.picspool.lib.media.DMMediaItemRes
    public Bitmap getGalleryThumbnail(Context context) {
        return getThumbnail(context, 120, Bitmap.Config.ARGB_4444);
    }

    @Override // com.picspool.lib.media.DMMediaItemRes
    public Bitmap getThumbnail(Context context, int i) {
        return getThumbnail(context, i, Bitmap.Config.ARGB_4444);
    }

    @Override // com.picspool.lib.media.DMMediaItemRes
    public Bitmap getThumbnail(Context context, int i, Bitmap.Config config) {
        String thumbPath = getThumbPath();
        if (thumbPath == null) {
            thumbPath = scaleThumbPath(context.getContentResolver(), getImgId());
            setThumbPath(thumbPath);
        }
        Bitmap bitmap = null;
        if (thumbPath != null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inDither = false;
            options.inJustDecodeBounds = true;
            Bitmap decodeFile = BitmapFactory.decodeFile(thumbPath, options);
            int i2 = options.outHeight;
            int i3 = options.outWidth;
            float f = i;
            int i4 = (int) (0.1f * f);
            if (i - i3 < i4 && i - i2 < i4) {
                options.inJustDecodeBounds = false;
                float f2 = i3;
                float f3 = i2;
                float f4 = f2 / f3;
                if (i2 > i3) {
                    f4 = f3 / f2;
                }
                int i5 = (int) (f * f4);
                options.inPreferredConfig = config;
                options.outHeight = i5;
                options.outWidth = i5;
                bitmap = BitmapFactory.decodeFile(thumbPath, options);
            } else if (decodeFile == null) {
                bitmap = decodeFile;
            } else if (!decodeFile.isRecycled()) {
                decodeFile.recycle();
            }
        }
        if (bitmap == null || bitmap.isRecycled()) {
            Bitmap CropSquareImage = CropSquareImage(context, getImgUri(), i);
            Bitmap extractThumbnail = ThumbnailUtils.extractThumbnail(CropSquareImage, i, i);
            if (CropSquareImage != extractThumbnail) {
                CropSquareImage.recycle();
                return extractThumbnail;
            }
            return extractThumbnail;
        }
        int orientation = getOrientation();
        if (orientation != -1 && orientation != 0) {
            Matrix matrix = new Matrix();
            matrix.setRotate(orientation, bitmap.getWidth(), bitmap.getHeight());
            Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            bitmap = createBitmap;
        }
        if (bitmap.getWidth() != bitmap.getHeight()) {
            Bitmap extractThumbnail2 = ThumbnailUtils.extractThumbnail(bitmap, i, i);
            if (bitmap != extractThumbnail2) {
                bitmap.recycle();
            }
            return extractThumbnail2;
        }
        return bitmap;
    }

    public Bitmap getThumbnailByCustom(Context context, int i) {
        Bitmap bitmap;
        Log.i("luca", "getThumbnailByCustom   bmpWidth:" + i);
        String thumbPath = getThumbPath();
        if (thumbPath == null) {
            thumbPath = scaleThumbPath(context.getContentResolver(), getImgId());
            setThumbPath(thumbPath);
        }
        if (thumbPath != null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inDither = false;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.outHeight = i;
            options.outWidth = i;
            bitmap = BitmapFactory.decodeFile(thumbPath, options);
        } else {
            bitmap = null;
        }
        if (bitmap == null || bitmap.isRecycled()) {
            Bitmap CropItemImage = DMBitmapCrop.CropItemImage(context, getImgUri(), i);
            Bitmap extractThumbnail = ThumbnailUtils.extractThumbnail(CropItemImage, i, i);
            if (CropItemImage != extractThumbnail) {
                CropItemImage.recycle();
                return extractThumbnail;
            }
            return extractThumbnail;
        }
        int orientation = getOrientation();
        if (getOrientation() != -1 && orientation != 0) {
            Matrix matrix = new Matrix();
            matrix.setRotate(orientation, bitmap.getWidth(), bitmap.getHeight());
            Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            bitmap = createBitmap;
        }
        if (bitmap.getWidth() != bitmap.getHeight()) {
            Bitmap extractThumbnail2 = ThumbnailUtils.extractThumbnail(bitmap, i, i);
            if (bitmap != extractThumbnail2) {
                bitmap.recycle();
            }
            return extractThumbnail2;
        }
        return bitmap;
    }

    private String scaleThumbPath(ContentResolver contentResolver, String str) {
        String str2;
        String[] strArr = {"_data", "kind", DMResRecordBean.f1945ID};
        Uri uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
        Cursor query = contentResolver.query(uri, strArr, "image_id=" + str, null, null);
        if (query == null || query.getCount() <= 0) {
            str2 = null;
        } else {
            query.getCount();
            query.moveToFirst();
            str2 = query.getString(query.getColumnIndexOrThrow("_data"));
        }
        query.close();
        return str2;
    }

    @Override // com.picspool.lib.media.DMMediaItemRes
    public String thumbPath(ContentResolver contentResolver, String str) {
        String str2;
        String[] strArr = {"_data", "kind", DMResRecordBean.f1945ID};
        Uri uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
        Cursor query = contentResolver.query(uri, strArr, "image_id=" + str, null, null);
        if (query == null || query.getCount() <= 0) {
            str2 = null;
        } else {
            query.getCount();
            query.moveToFirst();
            str2 = query.getString(query.getColumnIndexOrThrow("_data"));
        }
        query.close();
        return str2;
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
}
