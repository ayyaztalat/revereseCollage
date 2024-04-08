package com.picspool.instatextview.online;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.exifinterface.media.ExifInterface;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes3.dex */
public class DM_FontOkHttpClient {
    private static DM_FontOkHttpClient instance = null;
    static String isTest = "2";
    private Context mContext;
    private OkHttpClient mOkHttpClient = new OkHttpClient();
    private Handler mDelivery = new Handler(Looper.getMainLooper());

    /* loaded from: classes3.dex */
    public interface ResponseListener {
        void onResponseFail(IOException iOException);

        void onResponseSuccess(String str);
    }

    private DM_FontOkHttpClient(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static DM_FontOkHttpClient getInstance(Context context) {
        if (instance == null) {
            instance = new DM_FontOkHttpClient(context);
        }
        return instance;
    }

    public static void setIsTest(String str) {
        isTest = str;
    }

    public void asyncPost(final ResponseListener responseListener, String str, String str2) {
        String requestUrl = getRequestUrl(str2);
        this.mOkHttpClient.newCall(new Request.Builder().url(requestUrl).post(addParams(new FormBody.Builder(), str).build()).build()).enqueue(new Callback() { // from class: com.picspool.instatextview.online.DM_FontOkHttpClient.1
            @Override // okhttp3.Callback
            public void onFailure(Call call, final IOException iOException) {
                DM_FontOkHttpClient.this.mDelivery.post(new Runnable() { // from class: com.picspool.instatextview.online.DM_FontOkHttpClient.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (responseListener != null) {
                            responseListener.onResponseFail(iOException);
                        }
                    }
                });
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                DM_FontOkHttpClient.this.mDelivery.post(new Runnable() { // from class: com.picspool.instatextview.online.DM_FontOkHttpClient.1.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (responseListener != null) {
                            responseListener.onResponseSuccess(string);
                        }
                    }
                });
            }
        });
    }

    private FormBody.Builder addParams(FormBody.Builder builder, String str) {
        builder.add("statue", ExifInterface.GPS_MEASUREMENT_2D);
        builder.add("platform", "android");
        return builder;
    }

    private String getRequestUrl(String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("http://s1.picsjoin.com/single_material_src/public/material/" + str);
        arrayList.add("http://s2.picsjoin.com/single_material_src/public/material/" + str);
        arrayList.add("http://s3.picsjoin.com/single_material_src/public/material/" + str);
        arrayList.add("http://s4.picsjoin.com/single_material_src/public/material/" + str);
        arrayList.add("http://s5.picsjoin.com/single_material_src/public/material/" + str);
        int nextInt = new Random().nextInt(arrayList.size());
        if (nextInt >= arrayList.size()) {
            nextInt = 0;
        }
        return (String) arrayList.get(nextInt);
    }
}
