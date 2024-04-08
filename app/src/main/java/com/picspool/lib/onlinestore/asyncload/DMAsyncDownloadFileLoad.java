package com.picspool.lib.onlinestore.asyncload;

import android.os.Handler;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* loaded from: classes3.dex */
public class DMAsyncDownloadFileLoad implements Runnable {
    private AsyncDownloadFileListener aListener;
    private String[] params;
    private Handler handler = new Handler();
    private int len = 0;
    private long total = 0;
    private int lenght = 0;

    /* loaded from: classes3.dex */
    public interface AsyncDownloadFileListener {
        void onImageDownLoadFaile();

        void onPostExecute(Object obj);

        void onProgressUpdate(Integer... numArr);
    }

    protected boolean doInBackground() throws IOException {
        int i;
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(this.params[0]).openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
        } catch (Exception e) {
            e.printStackTrace();
            AsyncDownloadFileListener asyncDownloadFileListener = this.aListener;
            if (asyncDownloadFileListener != null) {
                asyncDownloadFileListener.onImageDownLoadFaile();
            }
        }
        try {
            if (httpURLConnection.getResponseCode() == 200) {
                this.lenght = httpURLConnection.getContentLength();
                InputStream inputStream = httpURLConnection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(this.params[1]);
                byte[] bArr = new byte[4096];
                while (true) {
                    int read = inputStream.read(bArr);
                    this.len = read;
                    if (read == -1) {
                        break;
                    }
                    this.total += read;
                    this.handler.post(new Runnable() { // from class: com.picspool.lib.onlinestore.asyncload.DMAsyncDownloadFileLoad.1
                        @Override // java.lang.Runnable
                        public void run() {
                            DMAsyncDownloadFileLoad dMAsyncDownloadFileLoad = DMAsyncDownloadFileLoad.this;
                            dMAsyncDownloadFileLoad.onProgressUpdate(Integer.valueOf((int) ((dMAsyncDownloadFileLoad.total * 100) / DMAsyncDownloadFileLoad.this.lenght)));
                        }
                    });
                    fileOutputStream.write(bArr, 0, this.len);
                }
                inputStream.close();
                fileOutputStream.close();
                return (this.params[1] == null || (i = this.lenght) == 0 || ((long) i) != this.total) ? false : true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException(String.valueOf(httpURLConnection.getResponseCode()));
    }

    protected void onPostExecute(boolean z) {
        AsyncDownloadFileListener asyncDownloadFileListener = this.aListener;
        if (asyncDownloadFileListener != null) {
            asyncDownloadFileListener.onPostExecute(Boolean.valueOf(z));
        }
    }

    protected void onProgressUpdate(Integer... numArr) {
        AsyncDownloadFileListener asyncDownloadFileListener = this.aListener;
        if (asyncDownloadFileListener != null) {
            asyncDownloadFileListener.onProgressUpdate(numArr);
        }
    }

    public void execute(String... strArr) {
        this.params = strArr;
        new Thread(this).start();
    }

    public void setAsyncDownloadFileListener(AsyncDownloadFileListener asyncDownloadFileListener) {
        this.aListener = asyncDownloadFileListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean doInBackground = false;
        try {
            doInBackground = doInBackground();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean finalDoInBackground = doInBackground;
        this.handler.post(new Runnable() { // from class: com.picspool.lib.onlinestore.asyncload.DMAsyncDownloadFileLoad.2
            @Override // java.lang.Runnable
            public void run() {
                DMAsyncDownloadFileLoad.this.onPostExecute(finalDoInBackground);
            }
        });
    }
}
