package com.photo.editor.square.splash.utils;

import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.photo.editor.square.splash.app.CSMyApp;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class CSGlideUtil {
    public static void loadLocalImage(String str, ImageView imageView) {
        WeakReference weakReference = new WeakReference(imageView);
        Glide.with(CSMyApp.getContext()).load(str).apply((BaseRequestOptions<?>) new RequestOptions().centerCrop().priority(Priority.HIGH).disallowHardwareConfig().diskCacheStrategy(DiskCacheStrategy.ALL)).into((ImageView) weakReference.get());
    }

    public static void loadLocalImage(String str, ImageView imageView, int i) {
        WeakReference weakReference = new WeakReference(imageView);
        Glide.with(CSMyApp.getContext()).load(str).apply((BaseRequestOptions<?>) new RequestOptions().centerCrop().placeholder(i).error(i).priority(Priority.HIGH).disallowHardwareConfig().diskCacheStrategy(DiskCacheStrategy.ALL)).into((ImageView) weakReference.get());
    }
}
