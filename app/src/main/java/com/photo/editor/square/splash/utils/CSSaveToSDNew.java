package com.photo.editor.square.splash.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.media.session.PlaybackStateCompat;
import android.widget.Toast;
import java.io.File;
import java.util.Calendar;
import com.picspool.lib.bitmap.output.save.DMAsyncSaveToSdImpl;
import com.picspool.lib.bitmap.output.save.DMSaveDoneListener;
import com.picspool.lib.p017io.DMDiskSpace;

/* loaded from: classes2.dex */
public class CSSaveToSDNew {
    public static void saveImage(Context context, Bitmap bitmap, String str, Bitmap.CompressFormat compressFormat, DMSaveDoneListener dMSaveDoneListener) {
        String str2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/Pics Cut";
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdirs();
        }
        saveImage(context, str, bitmap, str2, compressFormat, dMSaveDoneListener);
    }

    public static void saveImage(Context context, String str, Bitmap bitmap, String str2, Bitmap.CompressFormat compressFormat, DMSaveDoneListener dMSaveDoneListener) {
        int i;
        if (context == null) {
            if (dMSaveDoneListener != null) {
                dMSaveDoneListener.onSavingException(new Exception("context is null"));
                return;
            }
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int i2 = calendar.get(1);
        int i3 = calendar.get(5);
        int i4 = calendar.get(11);
        int i5 = calendar.get(12);
        int i6 = calendar.get(13);
        int i7 = calendar.get(14);
        String str3 = Integer.toString(i2) + Integer.toString(calendar.get(2) + 1) + Integer.toString(i3) + Integer.toString(i4) + Integer.toString(i5) + Integer.toString(i6) + Integer.toString(i7);
        String str4 = ".jpg";
        if (compressFormat != null && (i = C21562.$SwitchMap$android$graphics$Bitmap$CompressFormat[compressFormat.ordinal()]) != 1) {
            if (i == 2) {
                str4 = ".png";
            } else if (i == 3) {
                str4 = ".webp";
            }
        }
        saveImage(context, bitmap, str2, str + "_" + (str3 + str4), compressFormat, dMSaveDoneListener);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.photo.editor.square.splash.utils.CSSaveToSDNew$2 */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class C21562 {
        static final /* synthetic */ int[] $SwitchMap$android$graphics$Bitmap$CompressFormat;

        static {
            int[] iArr = new int[Bitmap.CompressFormat.values().length];
            $SwitchMap$android$graphics$Bitmap$CompressFormat = iArr;
            try {
                iArr[Bitmap.CompressFormat.JPEG.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$CompressFormat[Bitmap.CompressFormat.PNG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$CompressFormat[Bitmap.CompressFormat.WEBP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static void saveImage(Context context, Bitmap bitmap, String str, String str2, Bitmap.CompressFormat compressFormat, DMSaveDoneListener dMSaveDoneListener) {
        saveImage(context, str + "/" + str2, bitmap, compressFormat, dMSaveDoneListener, true);
    }

    public static void saveImage(final Context context, final String str, Bitmap bitmap, Bitmap.CompressFormat compressFormat, final DMSaveDoneListener dMSaveDoneListener, final boolean z) {
        if (context == null) {
            if (dMSaveDoneListener != null) {
                dMSaveDoneListener.onSavingException(new Exception("context is null"));
            }
        } else if (bitmap == null || bitmap.isRecycled()) {
            if (dMSaveDoneListener != null) {
                dMSaveDoneListener.onSavingException(new Exception("bitmap is null"));
            }
        } else if (!Environment.getExternalStorageState().equals("mounted")) {
            if (context != null) {
                Toast.makeText(context, "No Sdcard", Toast.LENGTH_SHORT).show();
            }
            if (dMSaveDoneListener != null) {
                dMSaveDoneListener.onSavingException(new Exception("sd is null"));
            }
        } else if (DMDiskSpace.sizeofSDCard() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID < 10) {
            if (context != null) {
                Toast.makeText(context, "No Memery", Toast.LENGTH_SHORT).show();
            }
            if (dMSaveDoneListener != null) {
                dMSaveDoneListener.onSavingException(new Exception("sd is full"));
            }
        } else {
            DMAsyncSaveToSdImpl.initLoader(context);
            DMAsyncSaveToSdImpl dMAsyncSaveToSdImpl = DMAsyncSaveToSdImpl.getInstance();
            dMAsyncSaveToSdImpl.setData(context, bitmap, str, compressFormat);
            dMAsyncSaveToSdImpl.setOnSaveDoneListener(new DMSaveDoneListener() { // from class: com.photo.editor.square.splash.utils.CSSaveToSDNew.1
                @Override // com.picspool.lib.bitmap.output.save.DMSaveDoneListener
                public void onSaveDone(String str2, Uri uri) {
                    DMAsyncSaveToSdImpl.shutdownLoder();
                    if (z) {
                        MediaScannerConnection.scanFile(context, new String[]{str}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.photo.editor.square.splash.utils.CSSaveToSDNew.1.1
                            @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                            public void onScanCompleted(String str3, Uri uri2) {
                                if (str3 == null || uri2 == null || dMSaveDoneListener == null) {
                                    return;
                                }
                                dMSaveDoneListener.onSaveDone(str3, uri2);
                            }
                        });
                        return;
                    }
                    DMSaveDoneListener dMSaveDoneListener2 = dMSaveDoneListener;
                    if (dMSaveDoneListener2 != null) {
                        dMSaveDoneListener2.onSaveDone(str2, uri);
                    }
                }

                @Override // com.picspool.lib.bitmap.output.save.DMSaveDoneListener
                public void onSavingException(Exception exc) {
                    DMAsyncSaveToSdImpl.shutdownLoder();
                    DMSaveDoneListener dMSaveDoneListener2 = dMSaveDoneListener;
                    if (dMSaveDoneListener2 != null) {
                        dMSaveDoneListener2.onSavingException(exc);
                    }
                }
            });
            dMAsyncSaveToSdImpl.execute();
        }
    }
}
