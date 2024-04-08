package com.photoart.libsticker.sticker3;

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

/* loaded from: classes2.dex */
public class DMSgOkHttpClient {
    private Context mContext;
    private OkHttpClient mOkHttpClient = new OkHttpClient();
    private Handler mDelivery = new Handler(Looper.getMainLooper());

    /* loaded from: classes2.dex */
    public interface ResponseListener {
        void onResponseFail(IOException iOException);

        void onResponseSuccess(String str);
    }

    private DMSgOkHttpClient(Context context) {
        this.mContext = context;
    }

    public static DMSgOkHttpClient getInstance(Context context) {
        return new DMSgOkHttpClient(context);
    }

    public void asyncStickersGet(String str, final ResponseListener responseListener) {
        this.mOkHttpClient.newCall(new Request.Builder().url(str).build()).enqueue(new Callback() { // from class: com.photoart.libsticker.sticker3.DMSgOkHttpClient.1
            @Override // okhttp3.Callback
            public void onFailure(Call call, final IOException iOException) {
                DMSgOkHttpClient.this.mDelivery.post(new Runnable() { // from class: com.photoart.libsticker.sticker3.DMSgOkHttpClient.1.1
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
                DMSgOkHttpClient.this.mDelivery.post(new Runnable() { // from class: com.photoart.libsticker.sticker3.DMSgOkHttpClient.1.2
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

    public void asyncStickersPost(final ResponseListener responseListener, String str) {
        String requestUrl = getRequestUrl();
        this.mOkHttpClient.newCall(new Request.Builder().url(requestUrl).post(addParams(new FormBody.Builder(), str).build()).build()).enqueue(new Callback() { // from class: com.photoart.libsticker.sticker3.DMSgOkHttpClient.2
            @Override // okhttp3.Callback
            public void onFailure(Call call, final IOException iOException) {
                DMSgOkHttpClient.this.mDelivery.post(new Runnable() { // from class: com.photoart.libsticker.sticker3.DMSgOkHttpClient.2.1
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
                DMSgOkHttpClient.this.mDelivery.post(new Runnable() { // from class: com.photoart.libsticker.sticker3.DMSgOkHttpClient.2.2
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
        builder.add("statue", DMLibStickerManager.getTestStatus() ? "1" : ExifInterface.GPS_MEASUREMENT_2D);
        builder.add("platform", "android");
        return builder;
    }

    private String getRequestUrl() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("http://s1.picsjoin.com/PicsJion/public/material/stickers/getStickersList");
        arrayList.add("http://s2.picsjoin.com/PicsJion/public/material/stickers/getStickersList");
        arrayList.add("http://s3.picsjoin.com/PicsJion/public/material/stickers/getStickersList");
        arrayList.add("http://s4.picsjoin.com/PicsJion/public/material/stickers/getStickersList");
        arrayList.add("http://s5.picsjoin.com/PicsJion/public/material/stickers/getStickersList");
        int nextInt = new Random().nextInt(arrayList.size());
        if (nextInt >= arrayList.size()) {
            nextInt = 0;
        }
        return (String) arrayList.get(nextInt);
    }
}
