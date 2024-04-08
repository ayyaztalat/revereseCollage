package com.picspool.lib.bitmap.output.save;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes3.dex */
public class DMAsyncSaveToSdImpl {
    private static DMAsyncSaveToSdImpl loader;
    private Context context;
    private ExecutorService executorService;
    Bitmap.CompressFormat format;
    private final Handler handler = new Handler();
    Bitmap mBitmap;
    String mFullName;
    DMSaveDoneListener onSaveDoneListener;

    public static DMAsyncSaveToSdImpl getInstance() {
        return loader;
    }

    public static void initLoader(Context context) {
        if (loader == null) {
            loader = new DMAsyncSaveToSdImpl();
        }
        loader.initExecutor();
    }

    public static void shutdownLoder() {
        DMAsyncSaveToSdImpl dMAsyncSaveToSdImpl = loader;
        if (dMAsyncSaveToSdImpl != null) {
            dMAsyncSaveToSdImpl.shutDownExecutor();
        }
        loader = null;
    }

    public void initExecutor() {
        if (this.executorService != null) {
            shutDownExecutor();
        }
        this.executorService = Executors.newFixedThreadPool(1);
    }

    public void shutDownExecutor() {
        ExecutorService executorService = this.executorService;
        if (executorService != null) {
            executorService.shutdown();
        }
        this.context = null;
        this.mBitmap = null;
    }

    public void setData(Context context, Bitmap bitmap, String str, Bitmap.CompressFormat compressFormat) {
        this.mBitmap = bitmap;
        this.context = context;
        this.mFullName = str;
        this.format = compressFormat;
    }

    public void setOnSaveDoneListener(DMSaveDoneListener dMSaveDoneListener) {
        this.onSaveDoneListener = dMSaveDoneListener;
    }

    public void execute() {
        this.executorService.submit(new Runnable() { // from class: com.picspool.lib.bitmap.output.save.DMAsyncSaveToSdImpl.1
            @Override // java.lang.Runnable
            public void run() {
                FileOutputStream fileOutputStream = null;
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    DMAsyncSaveToSdImpl.this.mBitmap.compress(DMAsyncSaveToSdImpl.this.format, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    int length = byteArray.length;
                    FileOutputStream fileOutputStream2 = new FileOutputStream(DMAsyncSaveToSdImpl.this.mFullName);
                    try {
                        fileOutputStream2.write(byteArray, 0, length);
                        fileOutputStream2.close();
                        byteArrayOutputStream.close();
                        final Uri fromFile = Uri.fromFile(new File(DMAsyncSaveToSdImpl.this.mFullName));
                        if (DMAsyncSaveToSdImpl.this.onSaveDoneListener != null) {
                            DMAsyncSaveToSdImpl.this.handler.post(new Runnable() { // from class: com.picspool.lib.bitmap.output.save.DMAsyncSaveToSdImpl.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    DMAsyncSaveToSdImpl.this.onSaveDoneListener.onSaveDone(DMAsyncSaveToSdImpl.this.mFullName, fromFile);
                                }
                            });
                        }
                    } catch (Exception e) {
                        e = e;
                        fileOutputStream = fileOutputStream2;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException unused) {
                            }
                        }
                        if (DMAsyncSaveToSdImpl.this.onSaveDoneListener != null) {
                            Exception finalE = e;
                            DMAsyncSaveToSdImpl.this.handler.post(new Runnable() { // from class: com.picspool.lib.bitmap.output.save.DMAsyncSaveToSdImpl.1.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    DMAsyncSaveToSdImpl.this.onSaveDoneListener.onSavingException(finalE);
                                }
                            });
                        }
                    }
                } catch (Exception e2) {
                   e2.printStackTrace();
                }
            }
        });
    }
}
