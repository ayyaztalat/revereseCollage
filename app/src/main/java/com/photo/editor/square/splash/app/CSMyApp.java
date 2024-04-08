package com.photo.editor.square.splash.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSStickerBarConfig;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.resource.CSClearStickerSDMaterialFile;
import com.photo.editor.square.splash.activitys.CSStickerOnlineStoreAcitvity;
import com.photo.editor.square.splash.rate.BpFirebaseConfig;
import java.util.LinkedList;

import com.picspool.instatextview.resource.DMWBFontRes;
import com.picspool.instatextview.resource.manager.DMFontManager;
import com.picspool.instatextview.textview.DMInstaTextView;
import com.picspool.instatextview.textview.DMInstaTextView3;
import com.picspool.lib.sysutillib.DMPreferencesUtil;
import com.sky.testproject.GPUImageNativeLibrary;

/* loaded from: classes2.dex */
public class CSMyApp extends Application {
    static Context context;
    private static Bitmap swapBitmap;

    @Override // android.content.ContextWrapper, android.content.Context
    public Context getApplicationContext() {
        return this;
    }

    public static Context getContext() {
        return context;
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        BpFirebaseConfig.getInstance(this).init();
//        AdUtils.getInstance().init(this);
        try {
            GPUImageNativeLibrary.initGpuNativeLibrary(this);
        } catch (Throwable unused) {
        }
        LinkedList linkedList = new LinkedList();
        DMFontManager dMFontManager = new DMFontManager(getApplicationContext());
        int count = dMFontManager.getCount();
        for (int i = 0; i < count; i++) {
            try {
                DMWBFontRes res = dMFontManager.getRes(i);
                if (res.getFontTypeface(getApplicationContext()) != null) {
                    linkedList.add(res.getFontTypeface(getApplicationContext()));
                }
            } catch (Throwable unused2) {
            }
        }
        DMInstaTextView.setTfList(linkedList);
        DMInstaTextView3.setTfList(linkedList);
        CSStickerBarConfig.setStickerOnlineStoreAcitvity(CSStickerOnlineStoreAcitvity.class);
        try {
            if (DMPreferencesUtil.get(getApplicationContext(), "stickerbar_clean", "clear_All_StickerFile") == null) {
                new Thread(new Runnable() { // from class: com.photo.editor.square.splash.app.CSMyApp.1
                    @Override // java.lang.Runnable
                    public void run() {
                        CSClearStickerSDMaterialFile.clearFile(CSMyApp.this.getApplicationContext());
                        DMPreferencesUtil.save(CSMyApp.this.getApplicationContext(), "stickerbar_clean", "clear_All_StickerFile", "removed");
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        ForegroundCallbacks.init(this);
//        ForegroundCallbacks.get((Application) this).addListener(new ForegroundCallbacks.Listener() { // from class: com.photo.editor.square.splash.app.CSMyApp.2
//            @Override // com.mhh.libraryads.libads.ForegroundCallbacks.Listener
//            public void onBecameBackground() {
//            }
//
//            @Override // com.mhh.libraryads.libads.ForegroundCallbacks.Listener
//            public void onBecameForeground(Activity activity) {
//                adsBaseAdActivity.isBecameForeground = true;
//            }
//        });
    }

    public static Bitmap getSwapBitmap() {
        return swapBitmap;
    }

    public static void setSwapBitmap(Bitmap bitmap) {
        Bitmap bitmap2;
        if (bitmap == null && (bitmap2 = swapBitmap) != null) {
            if (!bitmap2.isRecycled()) {
                swapBitmap.recycle();
            }
            swapBitmap = null;
        }
        swapBitmap = bitmap;
    }
}
