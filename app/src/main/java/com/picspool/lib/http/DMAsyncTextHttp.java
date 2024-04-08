package com.picspool.lib.http;

import android.os.Handler;
import android.util.Log;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/* loaded from: classes3.dex */
public class DMAsyncTextHttp {
    private AsyncTextHttpTaskListener listener;
    private String url;
    int mConnectionTimeout = 8000;
    int mSocketTimeout = 8000;
    private final Handler handler = new Handler();

    /* loaded from: classes3.dex */
    public interface AsyncTextHttpTaskListener {
        void onRequestDidFailedStatus(Exception exc);

        void onRequestDidFinishLoad(String str);
    }

    public DMAsyncTextHttp(String str) {
        this.url = str;
    }

    public static void asyncHttpRequest(String str, AsyncTextHttpTaskListener asyncTextHttpTaskListener) {
        DMAsyncTextHttp dMAsyncTextHttp = new DMAsyncTextHttp(str);
        dMAsyncTextHttp.setListener(asyncTextHttpTaskListener);
        dMAsyncTextHttp.execute();
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public AsyncTextHttpTaskListener getListener() {
        return this.listener;
    }

    public void setListener(AsyncTextHttpTaskListener asyncTextHttpTaskListener) {
        this.listener = asyncTextHttpTaskListener;
    }

    public void execute() {
        new Thread(new Runnable() { // from class: com.picspool.lib.http.DMAsyncTextHttp.1
            @Override // java.lang.Runnable
            public void run() {
                String finalStr;
                String str1 = null;
                String str2 = null;
                try {
                    DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(DMAsyncTextHttp.this.url);
                    HttpParams params = defaultHttpClient.getParams();
                    HttpConnectionParams.setConnectionTimeout(params, DMAsyncTextHttp.this.mConnectionTimeout);
                    HttpConnectionParams.setSoTimeout(params, DMAsyncTextHttp.this.mSocketTimeout);
                    str1 = (String) defaultHttpClient.execute(httpGet, new BasicResponseHandler());
                    try {
                        Log.d("response", str1);
                    } catch (Exception unused) {
                        str2 = str1;
                        str1 = str2;
                        finalStr = str1;
                        String finalStr1 = finalStr;
                        DMAsyncTextHttp.this.handler.post(new Runnable() { // from class: com.picspool.lib.http.DMAsyncTextHttp.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (DMAsyncTextHttp.this.listener != null) {
                                    if (finalStr1 != null) {
                                        DMAsyncTextHttp.this.listener.onRequestDidFinishLoad(finalStr1);
                                    } else {
                                        DMAsyncTextHttp.this.listener.onRequestDidFailedStatus(null);
                                    }
                                }
                            }
                        });
                    }
                } catch (Exception ignored) {
                }
                finalStr = str1;
                String finalStr2 = finalStr;
                DMAsyncTextHttp.this.handler.post(new Runnable() { // from class: com.picspool.lib.http.DMAsyncTextHttp.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (DMAsyncTextHttp.this.listener != null) {
                            if (finalStr2 != null) {
                                DMAsyncTextHttp.this.listener.onRequestDidFinishLoad(finalStr2);
                            } else {
                                DMAsyncTextHttp.this.listener.onRequestDidFailedStatus(null);
                            }
                        }
                    }
                });
            }
        }).start();
    }
}
