package com.picspool.lib.resource.view;

import android.os.Handler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/* loaded from: classes3.dex */
public class DMWBAsyncTextHttp23 {
    private DMAsyncTextHttpTaskListener listener;
    private String url;
    int mConnectionTimeout = 5000;
    int mSocketTimeout = 5000;
    private final Handler handler = new Handler();

    public DMWBAsyncTextHttp23(String str) {
        this.url = str;
    }

    public static void asyncHttpRequest(String str, DMAsyncTextHttpTaskListener dMAsyncTextHttpTaskListener) {
        DMWBAsyncTextHttp dMWBAsyncTextHttp = new DMWBAsyncTextHttp(str);
        dMWBAsyncTextHttp.setListener(dMAsyncTextHttpTaskListener);
        dMWBAsyncTextHttp.execute(new String[0]);
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public DMAsyncTextHttpTaskListener getListener() {
        return this.listener;
    }

    public void setListener(DMAsyncTextHttpTaskListener dMAsyncTextHttpTaskListener) {
        this.listener = dMAsyncTextHttpTaskListener;
    }

    public void execute() {
        new Thread(new Runnable() { // from class: com.picspool.lib.resource.view.DMWBAsyncTextHttp23.1
            @Override // java.lang.Runnable
            public void run() {
                final String str;
                String str1;
                try {
                    DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(DMWBAsyncTextHttp23.this.url);
                    HttpParams params = defaultHttpClient.getParams();
                    HttpConnectionParams.setConnectionTimeout(params, DMWBAsyncTextHttp23.this.mConnectionTimeout);
                    HttpConnectionParams.setSoTimeout(params, DMWBAsyncTextHttp23.this.mSocketTimeout);
                    str1 = (String) defaultHttpClient.execute(httpGet, new BasicResponseHandler());
                } catch (Exception unused) {
                    str1 = null;
                }
                str = str1;
                DMWBAsyncTextHttp23.this.handler.post(new Runnable() { // from class: com.picspool.lib.resource.view.DMWBAsyncTextHttp23.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (DMWBAsyncTextHttp23.this.listener != null) {
                            if (str != null) {
                                DMWBAsyncTextHttp23.this.listener.onRequestDidFinishLoad(str);
                            } else {
                                DMWBAsyncTextHttp23.this.listener.onRequestDidFailedStatus(null);
                            }
                        }
                    }
                });
            }
        }).start();
    }
}
