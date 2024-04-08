package com.magic.video.editor.effect.cut.utils.save;

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

/* loaded from: classes2.dex */
public class CSAsyncSaveToSdImpl {
    private static CSAsyncSaveToSdImpl loader;
    private Context context;
    private ExecutorService executorService;
    Bitmap.CompressFormat format;
    private final Handler handler = new Handler();
    Bitmap mBitmap;
    String mFullName;
    CSSaveDoneListener onSaveDoneListener;

    public static CSAsyncSaveToSdImpl getInstance() {
        return loader;
    }

    public static void initLoader(Context context) {
        if (loader == null) {
            loader = new CSAsyncSaveToSdImpl();
        }
        loader.initExecutor();
    }

    public static void shutdownLoder() {
        CSAsyncSaveToSdImpl cSAsyncSaveToSdImpl = loader;
        if (cSAsyncSaveToSdImpl != null) {
            cSAsyncSaveToSdImpl.shutDownExecutor();
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

    public void setOnSaveDoneListener(CSSaveDoneListener cSSaveDoneListener) {
        this.onSaveDoneListener = cSSaveDoneListener;
    }

    public void execute() {
        this.executorService.submit(new Runnable() { // from class: com.magic.video.editor.effect.cut.utils.save.CSAsyncSaveToSdImpl.1
            @Override // java.lang.Runnable
            public void run() {
                FileOutputStream fileOutputStream = null;
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    CSAsyncSaveToSdImpl.this.mBitmap.compress(CSAsyncSaveToSdImpl.this.format, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    int length = byteArray.length;
                    FileOutputStream fileOutputStream2 = new FileOutputStream(CSAsyncSaveToSdImpl.this.mFullName);
                    try {
                        fileOutputStream2.write(byteArray, 0, length);
                        fileOutputStream2.close();
                        byteArrayOutputStream.close();
                        final Uri fromFile = Uri.fromFile(new File(CSAsyncSaveToSdImpl.this.mFullName));
                        if (CSAsyncSaveToSdImpl.this.onSaveDoneListener != null) {
                            CSAsyncSaveToSdImpl.this.handler.post(new Runnable() { // from class: com.magic.video.editor.effect.cut.utils.save.CSAsyncSaveToSdImpl.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    CSAsyncSaveToSdImpl.this.onSaveDoneListener.onSaveDone(CSAsyncSaveToSdImpl.this.mFullName, fromFile);
                                }
                            });
                        }
                    } catch (Exception e) {
                       e.printStackTrace();
                        fileOutputStream = fileOutputStream2;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException unused) {
                            }
                        }
                        if (CSAsyncSaveToSdImpl.this.onSaveDoneListener != null) {
                            CSAsyncSaveToSdImpl.this.handler.post(new Runnable() { // from class: com.magic.video.editor.effect.cut.utils.save.CSAsyncSaveToSdImpl.1.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    CSAsyncSaveToSdImpl.this.onSaveDoneListener.onSavingException(e);
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
