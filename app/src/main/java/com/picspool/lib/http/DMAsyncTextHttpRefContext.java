package com.picspool.lib.http;

import android.content.Context;
import android.os.Handler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/* loaded from: classes3.dex */
public class DMAsyncTextHttpRefContext {
    private static Context mContext;
    private AsyncTextHttpTaskListener listener;
    private String url;
    int mConnectionTimeout = 5000;
    int mSocketTimeout = 5000;
    private final Handler handler = new Handler();

    /* loaded from: classes3.dex */
    public interface AsyncTextHttpTaskListener {
        void onRequestDidFailedStatus(Exception exc);

        void onRequestDidFinishLoad(String str);
    }

    public DMAsyncTextHttpRefContext(String str) {
        this.url = str;
    }

    public static void asyncHttpRequest(Context context, String str, AsyncTextHttpTaskListener asyncTextHttpTaskListener) {
        mContext = context;
        DMAsyncTextHttpRefContext dMAsyncTextHttpRefContext = new DMAsyncTextHttpRefContext(str);
        dMAsyncTextHttpRefContext.setListener(asyncTextHttpTaskListener);
        dMAsyncTextHttpRefContext.execute();
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
        new Thread(new Runnable() { // from class: com.picspool.lib.http.DMAsyncTextHttpRefContext.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(DMAsyncTextHttpRefContext.this.url);
                    HttpParams params = defaultHttpClient.getParams();
                    HttpConnectionParams.setConnectionTimeout(params, DMAsyncTextHttpRefContext.this.mConnectionTimeout);
                    HttpConnectionParams.setSoTimeout(params, DMAsyncTextHttpRefContext.this.mSocketTimeout);
                    String str = (String) defaultHttpClient.execute(httpGet, new BasicResponseHandler());
                    if (DMAsyncTextHttpRefContext.this.listener != null) {
                        DMAsyncTextHttpRefContext.this.listener.onRequestDidFinishLoad(str);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (DMAsyncTextHttpRefContext.this.listener != null) {
                        DMAsyncTextHttpRefContext.this.listener.onRequestDidFailedStatus(e);
                    }
                }
            }
        }).start();
    }
}
