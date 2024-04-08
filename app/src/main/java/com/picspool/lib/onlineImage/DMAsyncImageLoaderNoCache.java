package com.picspool.lib.onlineImage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

/* loaded from: classes3.dex */
public class DMAsyncImageLoaderNoCache {
    static String PrefsName = "DMAsyncImageLoader";
    static String image_cache_key_perfix = "cache_";
    private ExecutorService executorService = Executors.newFixedThreadPool(2);
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

    public Bitmap loadImageBitamp(Context context, final String str, final ImageCallback imageCallback) {
        Log.w("DMAsyncImageLoader", "loadImageBitmap");
        this.executorService.submit(new Runnable() { // from class: com.picspool.lib.onlineImage.DMAsyncImageLoaderNoCache.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    final Bitmap bitmapFromNetUrl = DMAsyncImageLoaderNoCache.this.bitmapFromNetUrl(str);
                    DMAsyncImageLoaderNoCache.this.handler.post(new Runnable() { // from class: com.picspool.lib.onlineImage.DMAsyncImageLoaderNoCache.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (imageCallback != null) {
                                imageCallback.imageLoaded(bitmapFromNetUrl);
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

    public Bitmap loadImageFromUrl(String str) {
        HttpEntity entity;
        try {
            Log.w("DMAsyncImageLoader", "loadImageFromUrl " + str);
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
            HttpParams params = defaultHttpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 2000);
            HttpConnectionParams.setSoTimeout(params, 3000);
            defaultHttpClient.getParams().setParameter("http.connection.timeout", 2000);
            defaultHttpClient.getParams().setParameter("http.socket.timeout", (int) 3000);
            HttpResponse execute = defaultHttpClient.execute(new HttpGet(str));
            int statusCode = execute.getStatusLine().getStatusCode();
            Log.w("DMAsyncImageLoader", "connected");
            if (statusCode != 200 || (entity = execute.getEntity()) == null) {
                return null;
            }
            byte[] byteArray = EntityUtils.toByteArray(entity);
            Bitmap decodeByteArray = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            Log.w("DMAsyncImageLoader", "get bitmap");
            return decodeByteArray;
        } catch (Exception e) {
            Log.w("DMAsyncImageLoader", e.toString());
            return null;
        }
    }

    public Bitmap bitmapFromNetUrl(String str) throws Exception {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setRequestMethod("GET");
        if (httpURLConnection.getResponseCode() == 200) {
            Log.w("DMAsyncImageLoader", "success " + str);
            InputStream inputStream = httpURLConnection.getInputStream();
            Bitmap decodeStream = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return decodeStream;
        }
        Log.w("DMAsyncImageLoader", "failed");
        throw new RuntimeException(String.valueOf(httpURLConnection.getResponseCode()));
    }

    public void saveToDiskFromUrl(String str, String str2) throws Exception {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setDoInput(true);
        if (httpURLConnection.getResponseCode() == 200) {
            Log.w("DMAsyncImageLoader", "success " + str);
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
            Log.w("DMAsyncImageLoader", "failed");
            throw new RuntimeException(String.valueOf(httpURLConnection.getResponseCode()));
        }
    }
}
