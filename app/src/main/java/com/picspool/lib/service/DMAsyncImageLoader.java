package com.picspool.lib.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.picspool.lib.sysutillib.DMPreferencesUtil;

/* loaded from: classes3.dex */
public class DMAsyncImageLoader {
    static String PrefsName = "DMAsyncImageLoader";
    static String image_cache_key_perfix = "cache_";
    private static DMAsyncImageLoader loader;
    private ExecutorService executorService = Executors.newFixedThreadPool(8);
    private final Handler handler = new Handler();

    /* loaded from: classes3.dex */
    public interface ImageCallback {
        void imageLoaded(Bitmap bitmap);

        void imageLoadedError(Exception exc);
    }

    public static String cachDir() {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/.tmp/");
        if (!file.exists()) {
            file.mkdir();
        }
        return file.getAbsolutePath();
    }

    public static void initThumbLoader() {
        loader = new DMAsyncImageLoader();
    }

    public static void shutdownThumbLoder() {
        loader.shutdown();
    }

    public static void thumbLoad(Context context, DMImageMediaItem dMImageMediaItem, ImageCallback imageCallback) {
        loader.loadThumbImageBitamp(context, dMImageMediaItem, imageCallback);
    }

    public void loadThumbImageBitamp(final Context context, final DMImageMediaItem dMImageMediaItem, final ImageCallback imageCallback) {
        this.executorService.submit(new Runnable() { // from class: com.picspool.lib.service.DMAsyncImageLoader.1
            @Override // java.lang.Runnable
            public void run() {
                Bitmap thumbnail = dMImageMediaItem.getThumbnail(context, 120, Bitmap.Config.ARGB_4444);
                if (thumbnail != null) {
                    imageCallback.imageLoaded(thumbnail);
                } else {
                    imageCallback.imageLoadedError(null);
                }
            }
        });
    }

    public Bitmap loadImageBitamp(final Context context, final String str, final ImageCallback imageCallback) {
        final String str2 = image_cache_key_perfix + str;
        String str3 = DMPreferencesUtil.get(context, PrefsName, str2);
        if (str3 == null) {
            this.executorService.submit(new Runnable() { // from class: com.picspool.lib.service.DMAsyncImageLoader.2
                @Override // java.lang.Runnable
                public void run() {
                    final String str4 = DMAsyncImageLoader.cachDir() + UUID.randomUUID().toString();
                    try {
                        DMAsyncImageLoader.this.saveToDiskFromUrl(str, str4);
                        DMPreferencesUtil.save(context, DMAsyncImageLoader.PrefsName, str2, str4);
                        DMAsyncImageLoader.this.handler.post(new Runnable() { // from class: com.picspool.lib.service.DMAsyncImageLoader.2.1
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

    public void shutdown() {
        ExecutorService executorService = this.executorService;
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    public void saveToDiskFromUrl(String str, String str2) throws Exception {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setDoInput(true);
        if (httpURLConnection.getResponseCode() == 200) {
            InputStream inputStream = httpURLConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(str2);
            byte[] bArr = new byte[1024];
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
    }
}
