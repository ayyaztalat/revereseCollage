package com.picspool.lib.bitmap.output.save;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;
import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import com.picspool.lib.p017io.DMDiskSpace;
import com.picspool.lib.packages.DMAppPackages;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMSaveToSD {
    public static void saveImage(Context context, Bitmap bitmap, DMSaveDoneListener dMSaveDoneListener) {
        saveImage(context, bitmap, DMSaveDIR.DCIM, Bitmap.CompressFormat.JPEG, dMSaveDoneListener);
    }

    public static void saveImage(Context context, Bitmap bitmap, DMSaveDIR dMSaveDIR, Bitmap.CompressFormat compressFormat, DMSaveDoneListener dMSaveDoneListener) {
        if (context == null) {
            if (dMSaveDoneListener != null) {
                dMSaveDoneListener.onSavingException(new Exception("context is null"));
                return;
            }
            return;
        }
        String str = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/camera";
        if (dMSaveDIR == DMSaveDIR.PICTURES) {
            str = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        }
        if (dMSaveDIR == DMSaveDIR.SDROOT) {
            str = Environment.getExternalStorageDirectory().getPath();
        }
        if (dMSaveDIR == DMSaveDIR.APPDIR) {
            str = Environment.getExternalStorageDirectory().getPath() + "/" + DMAppPackages.getAppName(context.getPackageName());
        }
        if (dMSaveDIR == DMSaveDIR.PICTURESAPPDIR) {
            str = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/" + DMAppPackages.getAppName(context.getPackageName());
        }
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        saveImage(context, bitmap, str, compressFormat, dMSaveDoneListener);
    }

    public static void saveImage(Context context, Bitmap bitmap, DMSaveDIR dMSaveDIR, String str, Bitmap.CompressFormat compressFormat, DMSaveDoneListener dMSaveDoneListener) {
        if (context == null) {
            if (dMSaveDoneListener != null) {
                dMSaveDoneListener.onSavingException(new Exception("context is null"));
                return;
            }
            return;
        }
        String str2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/camera";
        if (dMSaveDIR == DMSaveDIR.PICTURES) {
            str2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        }
        if (dMSaveDIR == DMSaveDIR.SDROOT) {
            str2 = Environment.getExternalStorageDirectory().getPath();
        }
        if (dMSaveDIR == DMSaveDIR.APPDIR) {
            str2 = Environment.getExternalStorageDirectory().getPath() + "/" + str;
        }
        if (dMSaveDIR == DMSaveDIR.PICTURESAPPDIR) {
            str2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/" + str;
        }
        if (dMSaveDIR == DMSaveDIR.DCIM && str != null) {
            str2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/" + str;
        }
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdirs();
        }
        saveImage(context, bitmap, str2, compressFormat, dMSaveDoneListener);
    }

    public static void saveImage(Context context, Bitmap bitmap, String str, Bitmap.CompressFormat compressFormat, DMSaveDoneListener dMSaveDoneListener) {
        int i;
        if (context == null) {
            if (dMSaveDoneListener != null) {
                dMSaveDoneListener.onSavingException(new Exception("context is null"));
                return;
            }
            return;
        }
        String appName = DMAppPackages.getAppName(context.getPackageName());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int i2 = calendar.get(1);
        int i3 = calendar.get(5);
        int i4 = calendar.get(11);
        int i5 = calendar.get(12);
        int i6 = calendar.get(13);
        int i7 = calendar.get(14);
        String str2 = Integer.toString(i2) + Integer.toString(calendar.get(2) + 1) + Integer.toString(i3) + Integer.toString(i4) + Integer.toString(i5) + Integer.toString(i6) + Integer.toString(i7);
        String str3 = ".jpg";
        if (compressFormat != null && (i = C30803.$SwitchMap$android$graphics$Bitmap$CompressFormat[compressFormat.ordinal()]) != 1) {
            if (i == 2) {
                str3 = ".png";
            } else if (i == 3) {
                str3 = ".webp";
            }
        }
        saveImage(context, bitmap, str, appName + "_" + (str2 + str3), compressFormat, dMSaveDoneListener);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.picspool.lib.bitmap.output.save.DMSaveToSD$3 */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class C30803 {
        static final /* synthetic */ int[] $SwitchMap$android$graphics$Bitmap$CompressFormat;

        static {
            int[] iArr = new int[Bitmap.CompressFormat.values().length];
            $SwitchMap$android$graphics$Bitmap$CompressFormat = iArr;
            try {
                iArr[Bitmap.CompressFormat.JPEG.ordinal()] = 1;
            } catch (NoSuchFieldError ignored) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$CompressFormat[Bitmap.CompressFormat.PNG.ordinal()] = 2;
            } catch (NoSuchFieldError ignored) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$CompressFormat[Bitmap.CompressFormat.WEBP.ordinal()] = 3;
            } catch (NoSuchFieldError ignored) {
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
            Toast.makeText(context, context.getResources().getString(R.string.warning_no_image), Toast.LENGTH_LONG).show();
            if (dMSaveDoneListener != null) {
                dMSaveDoneListener.onSavingException(new Exception("bitmap is null"));
            }
        } else if (!Environment.getExternalStorageState().equals("mounted")) {
            Toast.makeText(context, context.getResources().getString(R.string.warning_no_sd), Toast.LENGTH_LONG).show();
            if (dMSaveDoneListener != null) {
                dMSaveDoneListener.onSavingException(new Exception("sd is null"));
            }
        } else {
            DMAsyncSaveToSdImpl.initLoader(context);
            DMAsyncSaveToSdImpl dMAsyncSaveToSdImpl = DMAsyncSaveToSdImpl.getInstance();
            dMAsyncSaveToSdImpl.setData(context, bitmap, str, compressFormat);
            dMAsyncSaveToSdImpl.setOnSaveDoneListener(new DMSaveDoneListener() { // from class: com.picspool.lib.bitmap.output.save.DMSaveToSD.1
                @Override // com.picspool.lib.bitmap.output.save.DMSaveDoneListener
                public void onSaveDone(String str2, Uri uri) {
                    DMAsyncSaveToSdImpl.shutdownLoder();
                    if (z) {
                        MediaScannerConnection.scanFile(context, new String[]{str}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.picspool.lib.bitmap.output.save.DMSaveToSD.1.1
                            @Override
                            // android.media.MediaScannerConnection.OnScanCompletedListener
                            public void onScanCompleted(String str3, Uri uri2) {
                            }
                        });
                        dMSaveDoneListener.onSaveDone(str2, uri);
                    } else {
                        dMSaveDoneListener.onSaveDone(str2, uri);
                    }
                }

                @Override // com.picspool.lib.bitmap.output.save.DMSaveDoneListener
                public void onSavingException(Exception exc) {
                    DMAsyncSaveToSdImpl.shutdownLoder();
                    dMSaveDoneListener.onSavingException(exc);
                }
            });
            dMAsyncSaveToSdImpl.execute();
        }
    }

    public static void saveMoreImage(Context context, String str, Map<String, Bitmap> map, Bitmap.CompressFormat compressFormat, DMAsyncSaveMoreToSdImpl.SaveMoreDoneListener saveMoreDoneListener) {
        saveMoreImage(context, str, map, compressFormat, saveMoreDoneListener, false);
    }

    public static void saveMoreImage(Context context, String str, Map<String, Bitmap> map, Bitmap.CompressFormat compressFormat, final DMAsyncSaveMoreToSdImpl.SaveMoreDoneListener saveMoreDoneListener, boolean z) {
        if (context == null) {
            if (saveMoreDoneListener != null) {
                saveMoreDoneListener.onSavingMoreException(new Exception("context is null"));
            }
        } else if (!Environment.getExternalStorageState().equals("mounted")) {
            if (context != null) {
                Toast.makeText(context, context.getResources().getString(R.string.warning_no_sd), Toast.LENGTH_LONG).show();
            }
            if (saveMoreDoneListener != null) {
                saveMoreDoneListener.onSavingMoreException(new Exception("sd is null"));
            }
        }  else {
            try {
                File file = new File(str);
                if (!file.exists()) {
                    file.mkdirs();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (saveMoreDoneListener != null) {
                    saveMoreDoneListener.onSavingMoreException(e);
                }
            }
            DMAsyncSaveMoreToSdImpl dMAsyncSaveMoreToSdImpl = DMAsyncSaveMoreToSdImpl.getInstance();
            dMAsyncSaveMoreToSdImpl.setData(context, map, compressFormat, z);
            dMAsyncSaveMoreToSdImpl.setOnSaveMoreDoneListener(new DMAsyncSaveMoreToSdImpl.SaveMoreDoneListener() { // from class: com.picspool.lib.bitmap.output.save.DMSaveToSD.2
                @Override // com.picspool.lib.bitmap.output.save.DMAsyncSaveMoreToSdImpl.SaveMoreDoneListener
                public void onSaveMoreDone(List<String> list) {
                    dMAsyncSaveMoreToSdImpl.onSaveMoreDoneListener.onSaveMoreDone(list);
                    DMAsyncSaveMoreToSdImpl.shutdownLoder();

                }

                @Override // com.picspool.lib.bitmap.output.save.DMAsyncSaveMoreToSdImpl.SaveMoreDoneListener
                public void onSavingMoreException(Exception exc) {
                    dMAsyncSaveMoreToSdImpl.onSaveMoreDoneListener.onSavingMoreException(exc);
                    DMAsyncSaveMoreToSdImpl.shutdownLoder();
                }
            });
            dMAsyncSaveMoreToSdImpl.execute();
        }
    }
}
