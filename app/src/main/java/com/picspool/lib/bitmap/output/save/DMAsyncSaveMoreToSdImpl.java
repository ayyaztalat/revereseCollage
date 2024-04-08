package com.picspool.lib.bitmap.output.save;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes3.dex */
public class DMAsyncSaveMoreToSdImpl {
    private static DMAsyncSaveMoreToSdImpl loader;
    private Map<String, Bitmap> bitmapMaps;
    private Context context;
    private ExecutorService executorService;
    Bitmap.CompressFormat format;
    private final Handler handler = new Handler();
    boolean isRecycleBmp = false;
    SaveMoreDoneListener onSaveMoreDoneListener;

    /* loaded from: classes3.dex */
    public interface SaveMoreDoneListener {
        void onSaveMoreDone(List<String> list);

        void onSavingMoreException(Exception exc);
    }

    public static DMAsyncSaveMoreToSdImpl getInstance() {
        return loader;
    }

    public static void initLoader(Context context) {
        if (loader == null) {
            loader = new DMAsyncSaveMoreToSdImpl();
        }
        loader.initExecutor();
    }

    public static void shutdownLoder() {
        DMAsyncSaveMoreToSdImpl dMAsyncSaveMoreToSdImpl = loader;
        if (dMAsyncSaveMoreToSdImpl != null) {
            dMAsyncSaveMoreToSdImpl.shutDownExecutor();
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
        Map<String, Bitmap> map = this.bitmapMaps;
        if (map != null) {
            map.clear();
        }
        this.bitmapMaps = null;
    }

    public void setData(Context context, Map<String, Bitmap> map, Bitmap.CompressFormat compressFormat, boolean z) {
        this.bitmapMaps = map;
        this.context = context;
        this.format = compressFormat;
        this.isRecycleBmp = z;
    }

    public void setOnSaveMoreDoneListener(SaveMoreDoneListener saveMoreDoneListener) {
        this.onSaveMoreDoneListener = saveMoreDoneListener;
    }

    public void execute() {
        this.executorService.submit(new Runnable() { // from class: com.picspool.lib.bitmap.output.save.DMAsyncSaveMoreToSdImpl.1
            @Override // java.lang.Runnable
            public void run() {
                if (DMAsyncSaveMoreToSdImpl.this.bitmapMaps != null) {
                    FileOutputStream fileOutputStream = null;
                    try {
                        final ArrayList arrayList = new ArrayList();
                        for (Object obj : DMAsyncSaveMoreToSdImpl.this.bitmapMaps.keySet()) {
                            String obj2 = obj.toString();
                            Bitmap bitmap = (Bitmap) DMAsyncSaveMoreToSdImpl.this.bitmapMaps.get(obj2);
                            if (bitmap != null && bitmap.isRecycled()) {
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                bitmap.compress(DMAsyncSaveMoreToSdImpl.this.format, 100, byteArrayOutputStream);
                                byte[] byteArray = byteArrayOutputStream.toByteArray();
                                int length = byteArray.length;
                                FileOutputStream fileOutputStream2 = new FileOutputStream(obj2);
                                try {
                                    fileOutputStream2.write(byteArray, 0, length);
                                    fileOutputStream2.close();
                                    byteArrayOutputStream.close();
                                    arrayList.add(obj2);
                                    if (DMAsyncSaveMoreToSdImpl.this.isRecycleBmp && bitmap != null && !bitmap.isRecycled()) {
                                        bitmap.recycle();
                                    }
                                } catch (Exception e) {
                                    e = e;
                                    fileOutputStream = fileOutputStream2;
                                    if (fileOutputStream != null) {
                                        try {
                                            fileOutputStream.close();
                                        } catch (IOException ignored) {
                                        }
                                    }
                                    if (DMAsyncSaveMoreToSdImpl.this.onSaveMoreDoneListener != null) {
                                        Exception finalE = e;
                                        DMAsyncSaveMoreToSdImpl.this.handler.post(new Runnable() { // from class: com.picspool.lib.bitmap.output.save.DMAsyncSaveMoreToSdImpl.1.3
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                DMAsyncSaveMoreToSdImpl.this.onSaveMoreDoneListener.onSavingMoreException(finalE);
                                            }
                                        });
                                        return;
                                    }
                                    return;
                                }
                            }
                        }
                        if (DMAsyncSaveMoreToSdImpl.this.onSaveMoreDoneListener != null) {
                            DMAsyncSaveMoreToSdImpl.this.handler.post(new Runnable() { // from class: com.picspool.lib.bitmap.output.save.DMAsyncSaveMoreToSdImpl.1.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    DMAsyncSaveMoreToSdImpl.this.onSaveMoreDoneListener.onSaveMoreDone(arrayList);
                                }
                            });
                        }
                    } catch (Exception e2) {
                       e2.printStackTrace();
                    }
                } else if (DMAsyncSaveMoreToSdImpl.this.onSaveMoreDoneListener != null) {
                    DMAsyncSaveMoreToSdImpl.this.handler.post(new Runnable() { // from class: com.picspool.lib.bitmap.output.save.DMAsyncSaveMoreToSdImpl.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            DMAsyncSaveMoreToSdImpl.this.onSaveMoreDoneListener.onSaveMoreDone(null);
                        }
                    });
                }
            }
        });
    }
}
