package com.picspool.lib.resource.view;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/* loaded from: classes3.dex */
public class DMWBAsyncTextHttp extends AsyncTask<String, Void, String> {
    private DMAsyncTextHttpTaskListener listener;
    int mConnectionTimeout = 5000;
    int mSocketTimeout = 5000;
    private String url;

    public DMWBAsyncTextHttp(String str) {
        this.url = str;
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

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public String doInBackground(String... strArr) {
        try {
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(this.url);
            HttpParams params = defaultHttpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, this.mConnectionTimeout);
            HttpConnectionParams.setSoTimeout(params, this.mSocketTimeout);
            String str = (String) defaultHttpClient.execute(httpGet, new BasicResponseHandler());
            Log.d("response", str);
            return str;
        } catch (Exception unused) {
            return null;
        }
    }

    @Override // android.os.AsyncTask
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(String str) {
        super.onPostExecute( str);
        DMAsyncTextHttpTaskListener dMAsyncTextHttpTaskListener = this.listener;
        if (dMAsyncTextHttpTaskListener != null) {
            if (str != null) {
                dMAsyncTextHttpTaskListener.onRequestDidFinishLoad(str);
            } else {
                dMAsyncTextHttpTaskListener.onRequestDidFailedStatus(null);
            }
        }
    }
}
