package com.magic.video.editor.effect.cut.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import com.bumptech.glide.load.Key;
import com.magic.video.editor.effect.cut.utils.base.CSUtilsAppHolder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes2.dex */
public class CSAsyncImageLoader {
    static String PrefsName = "BMAsyncImageLoader";
    static String image_cache_key_perfix = "cache_";
    private static CSAsyncImageLoader instance;
    private ExecutorService executorService = Executors.newFixedThreadPool(5);
    private final Handler handler = new Handler();

    /* loaded from: classes2.dex */
    public interface ImageCallback {
        void imageLoaded(Bitmap bitmap);

        void imageLoadedError(Exception exc);
    }

    /* loaded from: classes2.dex */
    public interface OnLineImageToFileCallback {
        void imageDownload(String str);

        void imageDownloadFaile(Exception exc);
    }

    public static synchronized CSAsyncImageLoader getInstance() {
        CSAsyncImageLoader cSAsyncImageLoader;
        synchronized (CSAsyncImageLoader.class) {
            if (instance == null) {
                instance = new CSAsyncImageLoader();
            }
            cSAsyncImageLoader = instance;
        }
        return cSAsyncImageLoader;
    }

    public static String cachDir() {
        File file = new File(CSUtilsAppHolder.appContext.getCacheDir().getPath() + "/.tmp/");
        if (!file.exists()) {
            file.mkdir();
        }
        return file.getAbsolutePath();
    }

    public void loadImageToFile(Context context, final String str, final String str2, final OnLineImageToFileCallback onLineImageToFileCallback) {
        this.executorService.submit(new Runnable() { // from class: com.magic.video.editor.effect.cut.utils.CSAsyncImageLoader.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    CSAsyncImageLoader.this.saveToDiskFromUrl(str, str2);
                    CSAsyncImageLoader.this.handler.post(new Runnable() { // from class: com.magic.video.editor.effect.cut.utils.CSAsyncImageLoader.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (onLineImageToFileCallback != null) {
                                onLineImageToFileCallback.imageDownload(str2);
                            }
                        }
                    });
                } catch (Exception e) {
                    OnLineImageToFileCallback onLineImageToFileCallback2 = onLineImageToFileCallback;
                    if (onLineImageToFileCallback2 != null) {
                        onLineImageToFileCallback2.imageDownloadFaile(e);
                    }
                }
            }
        });
    }

    public Bitmap loadImageBitamp(final Context context, final String str, final ImageCallback imageCallback) {
        final String str2 = image_cache_key_perfix + str;
        String str3 = CSPreferencesUtil.get(context, PrefsName, str2);
        if (str3 == null) {
            this.executorService.submit(new Runnable() { // from class: com.magic.video.editor.effect.cut.utils.CSAsyncImageLoader.2
                @Override // java.lang.Runnable
                public void run() {
                    final String str4 = CSAsyncImageLoader.cachDir() + UUID.randomUUID().toString();
                    try {
                        CSAsyncImageLoader.this.saveToDiskFromUrl(str, str4);
                        CSPreferencesUtil.save(context, CSAsyncImageLoader.PrefsName, str2, str4);
                        CSAsyncImageLoader.this.handler.post(new Runnable() { // from class: com.magic.video.editor.effect.cut.utils.CSAsyncImageLoader.2.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (imageCallback != null) {
                                    imageCallback.imageLoaded(BitmapFactory.decodeFile(str4));
                                }
                            }
                        });
                    } catch (Exception e) {
                        ImageCallback imageCallback2 = imageCallback;
                        if (imageCallback2 != null) {
                            imageCallback2.imageLoadedError(e);
                        }
                    }
                }
            });
            return null;
        }
        return BitmapFactory.decodeFile(str3);
    }

    public Bitmap loadImageBitamp(final Context context, final String str, final ImageCallback imageCallback, final int i) {
        final String str2 = image_cache_key_perfix + str;
        String str3 = CSPreferencesUtil.get(context, PrefsName, str2);
        if (str3 == null) {
            this.executorService.submit(new Runnable() { // from class: com.magic.video.editor.effect.cut.utils.CSAsyncImageLoader.3
                @Override // java.lang.Runnable
                public void run() {
                    final String str4 = CSAsyncImageLoader.cachDir() + UUID.randomUUID().toString();
                    try {
                        CSAsyncImageLoader.this.saveToDiskFromUrl(str, str4);
                        CSPreferencesUtil.save(context, CSAsyncImageLoader.PrefsName, str2, str4);
                        CSAsyncImageLoader.this.handler.post(new Runnable() { // from class: com.magic.video.editor.effect.cut.utils.CSAsyncImageLoader.3.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (imageCallback != null) {
                                    BitmapFactory.Options options = new BitmapFactory.Options();
                                    options.inSampleSize = i;
                                    options.inPurgeable = true;
                                    options.inInputShareable = true;
                                    imageCallback.imageLoaded(BitmapFactory.decodeFile(str4, options));
                                }
                            }
                        });
                    } catch (Exception e) {
                        ImageCallback imageCallback2 = imageCallback;
                        if (imageCallback2 != null) {
                            imageCallback2.imageLoadedError(e);
                        }
                    }
                }
            });
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = i;
        options.inPurgeable = true;
        options.inInputShareable = true;
        return BitmapFactory.decodeFile(str3, options);
    }

    protected Bitmap loadImageFromUrl(String str) {
        try {
            return BitmapFactory.decodeStream(new URL(str).openStream());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveToDiskFromUrl(String str, String str2) throws Exception {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setDoInput(true);
        try {
            if (httpURLConnection.getResponseCode() == 200) {
                httpURLConnection.getContentLength();
                InputStream inputStream = httpURLConnection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(str2);
                byte[] bArr = new byte[4096];
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read != -1) {
                        fileOutputStream.write(bArr, 0, read);
                    } else {
                        inputStream.close();
                        fileOutputStream.close();
                        return;
                    }
                }
            } else {
                throw new RuntimeException(String.valueOf(httpURLConnection.getResponseCode()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
