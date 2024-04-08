package com.picspool.instatextview.online;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.picspool.instatextview.online.DM_OnlineFontDownloadInterface;
import com.picspool.lib.zip.DMTZipUtil;

/* loaded from: classes3.dex */
public class DM_OnlineAsyncFontLoader {
    static String PrefsName = "AsyncFontLoader";
    static String font_cache_key_perfix = "cache_";
    private ExecutorService executorService = Executors.newFixedThreadPool(5);
    private final Handler handler = new Handler();

    /* loaded from: classes3.dex */
    public interface DownLoadFontCallback {
        void fontDownLoaded(Bitmap bitmap);

        void fontDownLoadedError(Exception exc);
    }

    public static String cachDir() {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/.tmp/");
        if (!file.exists()) {
            file.mkdir();
        }
        return file.getAbsolutePath();
    }

    public void loadFontToFile(Context context, final String str, final String str2, final String str3, final DM_OnlineFontDownloadInterface.OnFontResDownLoadListener onFontResDownLoadListener) {
        this.executorService.submit(new Runnable() { // from class: com.picspool.instatextview.online.DM_OnlineAsyncFontLoader.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    DM_OnlineAsyncFontLoader.this.saveToDiskFromUrl(str, str2);
                    DMTZipUtil.unZip(str2, str3);
                    DM_OnlineAsyncFontLoader.this.handler.post(new Runnable() { // from class: com.picspool.instatextview.online.DM_OnlineAsyncFontLoader.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (onFontResDownLoadListener != null) {
                                onFontResDownLoadListener.onDownLoadFinish(str);
                            }
                        }
                    });
                } catch (Exception e) {
                    DM_OnlineFontDownloadInterface.OnFontResDownLoadListener onFontResDownLoadListener2 = onFontResDownLoadListener;
                    if (onFontResDownLoadListener2 != null) {
                        onFontResDownLoadListener2.onDownLoadFaile(e.toString());
                    }
                }
            }
        });
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
