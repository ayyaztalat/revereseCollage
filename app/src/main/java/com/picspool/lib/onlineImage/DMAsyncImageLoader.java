package com.picspool.lib.onlineImage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import com.bumptech.glide.load.Key;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.net.HttpHeaders;
import com.picspool.lib.sysutillib.DMPreferencesUtil;

/* loaded from: classes3.dex */
public class DMAsyncImageLoader {
    static String PrefsName = "DMAsyncImageLoader";
    static String image_cache_key_perfix = "cache_";
    private static DMAsyncImageLoader instance;
    private ExecutorService executorService = Executors.newFixedThreadPool(5);
    private final Handler handler = new Handler();

    /* loaded from: classes3.dex */
    public interface ImageCallback {
        void imageLoaded(Bitmap bitmap);

        void imageLoadedError(Exception exc);
    }

    /* loaded from: classes3.dex */
    public interface OnLineImageToFileCallback {
        void imageDownload(String str);

        void imageDownloadFaile(Exception exc);
    }

    public static synchronized DMAsyncImageLoader getInstance() {
        DMAsyncImageLoader dMAsyncImageLoader;
        synchronized (DMAsyncImageLoader.class) {
            if (instance == null) {
                instance = new DMAsyncImageLoader();
            }
            dMAsyncImageLoader = instance;
        }
        return dMAsyncImageLoader;
    }

    public static String cachDir() {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/.tmp/");
        if (!file.exists()) {
            file.mkdir();
        }
        return file.getAbsolutePath();
    }

    public void loadImageToFile(Context context, final String str, final String str2, final OnLineImageToFileCallback onLineImageToFileCallback) {
        this.executorService.submit(new Runnable() { // from class: com.picspool.lib.onlineImage.DMAsyncImageLoader.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    DMAsyncImageLoader.this.saveToDiskFromUrl(str, str2);
                    DMAsyncImageLoader.this.handler.post(new Runnable() { // from class: com.picspool.lib.onlineImage.DMAsyncImageLoader.1.1
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
        String str3 = DMPreferencesUtil.get(context, PrefsName, str2);
        if (str3 == null) {
            this.executorService.submit(new Runnable() { // from class: com.picspool.lib.onlineImage.DMAsyncImageLoader.2
                @Override // java.lang.Runnable
                public void run() {
                    final String str4 = DMAsyncImageLoader.cachDir() + UUID.randomUUID().toString();
                    try {
                        DMAsyncImageLoader.this.saveToDiskFromUrl(str, str4);
                        DMPreferencesUtil.save(context, DMAsyncImageLoader.PrefsName, str2, str4);
                        DMAsyncImageLoader.this.handler.post(new Runnable() { // from class: com.picspool.lib.onlineImage.DMAsyncImageLoader.2.1
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
        String str3 = DMPreferencesUtil.get(context, PrefsName, str2);
        if (str3 == null) {
            this.executorService.submit(new Runnable() { // from class: com.picspool.lib.onlineImage.DMAsyncImageLoader.3
                @Override // java.lang.Runnable
                public void run() {
                    final String str4 = DMAsyncImageLoader.cachDir() + UUID.randomUUID().toString();
                    try {
                        DMAsyncImageLoader.this.saveToDiskFromUrl(str, str4);
                        DMPreferencesUtil.save(context, DMAsyncImageLoader.PrefsName, str2, str4);
                        DMAsyncImageLoader.this.handler.post(new Runnable() { // from class: com.picspool.lib.onlineImage.DMAsyncImageLoader.3.1
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

    public long getFileSize(String str) throws IOException, Exception {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setRequestMethod("GET");
//        httpURLConnection.setRequestProperty(HttpHeaders.HEAD_KEY_ACCEPT_ENCODING, "identity");
        httpURLConnection.setRequestProperty("Referer", str);
        httpURLConnection.setRequestProperty("Charset", Key.STRING_CHARSET_NAME);
//        httpURLConnection.setRequestProperty(HttpHeaders.HEAD_KEY_CONNECTION, "Keep-Alive");
        httpURLConnection.connect();
        if (httpURLConnection.getResponseCode() == 200) {
            return httpURLConnection.getContentLength();
        }
        return 0L;
    }
}
