package com.photo.editor.square.splash.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ZipUtils;
import com.photo.editor.square.splash.app.CSMyApp;
import com.photo.editor.square.splash.utils.CSBitmapUtils;
import java.io.File;
import java.io.IOException;

/* loaded from: classes2.dex */
public class CSEffectOnlineRes extends CSEffectRes {
    private static final String TAG = "EffectOnlineResT";
    public boolean isDowanloading = false;
    public boolean isOnlineRes = true;
    public String zipUri;

    @Override // com.photo.editor.square.splash.view.CSEffectRes
    public boolean isOnlineRes() {
        return this.isOnlineRes;
    }

    @Override // com.photo.editor.square.splash.view.CSEffectRes
    public void setOnlineRes(boolean z) {
        this.isOnlineRes = z;
    }

    public boolean isDowanloading() {
        return this.isDowanloading;
    }

    public void setDowanloading(boolean z) {
        this.isDowanloading = z;
    }

    public String getZipUri() {
        return this.zipUri;
    }

    public void setZipUri(String str) {
        this.zipUri = str;
    }

    @Override // com.photo.editor.square.splash.view.CSEffectRes
    public String getBgPath() {
        String resPath = getResPath(CSMyApp.getContext());
        File[] listFiles = new File(resPath).listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (file.getName().contains("bg.png") || file.getName().contains("back.png")) {
                    return resPath + "/" + file.getName();
                }
            }
            return resPath;
        }
        return resPath;
    }

    @Override // com.photo.editor.square.splash.view.CSEffectRes
    public String getFgPath() {
        String resPath = getResPath(CSMyApp.getContext());
        File[] listFiles = new File(resPath).listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (file.getName().contains("fg.png") || file.getName().contains("front.png")) {
                    return resPath + "/" + file.getName();
                }
            }
            return resPath;
        }
        return resPath;
    }

    @Override // com.photo.editor.square.splash.view.CSEffectRes
    public String getIconPath() {
        return this.iconPath;
    }

    @Override // com.photo.editor.square.splash.view.CSEffectRes
    public String getDisIconPath() {
        return this.disIconPath;
    }

    public boolean isDownloaded() {
//        return FileUtils.isFileExists(new File(getResPath(CSMyApp.getContext())));
        return false;
    }

    @Override // com.photo.editor.square.splash.view.CSEffectRes
    public Bitmap getBgBitmap(Context context) {
        return CSBitmapUtils.getImageFromFile(context, getBgPath());
    }

    @Override // com.photo.editor.square.splash.view.CSEffectRes
    public Bitmap getFgBitmap(Context context) {
        return CSBitmapUtils.getImageFromFile(context, getFgPath());
    }

    private String getResPath(Context context) {
        return context.getFilesDir().getAbsolutePath() + "/effect/" + this.type + "_" + this.groupname + "_" + this.name;
    }

    /*public void downloadRes(Context context, final DownloadListener downloadListener) {
        final File file = new File(getResPath(context));
        boolean isFileExists = FileUtils.isFileExists(file);
        boolean isFileExists2 = FileUtils.isFileExists(new File(getBgPath()));
        Log.d(TAG, "downloadRes: fileExists" + isFileExists2);
        if (isFileExists) {
            return;
        }
        DownloadTask register = OkDownload.request(this.zipUri, OkGo.get(this.zipUri)).save().register(new DownloadListener(this.zipUri) { // from class: com.photo.editor.square.splash.view.CSEffectOnlineRes.1
            @Override // com.lzy.okserver.ProgressListener
            public void onStart(Progress progress) {
                DownloadListener downloadListener2 = downloadListener;
                if (downloadListener2 != null) {
                    downloadListener2.onStart(progress);
                }
            }

            @Override // com.lzy.okserver.ProgressListener
            public void onProgress(Progress progress) {
                DownloadListener downloadListener2 = downloadListener;
                if (downloadListener2 != null) {
                    downloadListener2.onProgress(progress);
                }
                Log.d(CSEffectOnlineRes.TAG, "onProgress: " + progress);
            }

            @Override // com.lzy.okserver.ProgressListener
            public void onError(Progress progress) {
                DownloadListener downloadListener2 = downloadListener;
                if (downloadListener2 != null) {
                    downloadListener2.onError(progress);
                }
                CSEffectOnlineRes.this.isDowanloading = false;
                Log.d(CSEffectOnlineRes.TAG, "onError: ");
            }

            @Override // com.lzy.okserver.ProgressListener
            public void onFinish(File file2, Progress progress) {
                Log.d(CSEffectOnlineRes.TAG, "onFinish: " + file2);
                try {
                    FileUtils.createOrExistsDir(file);
                    ZipUtils.unzipFile(file2, file);
                    boolean isFileExists3 = FileUtils.isFileExists(CSEffectOnlineRes.this.getBgPath());
                    boolean isFileExists4 = FileUtils.isFileExists(CSEffectOnlineRes.this.getFgPath());
                    Log.d(CSEffectOnlineRes.TAG, "onFinish: " + isFileExists3 + isFileExists4);
                } catch (IOException e) {
                    Log.d(CSEffectOnlineRes.TAG, "onFinish: ");
                    e.printStackTrace();
                }
                CSEffectOnlineRes.this.isDowanloading = false;
                DownloadListener downloadListener2 = downloadListener;
                if (downloadListener2 != null) {
                    downloadListener2.onFinish(file2, progress);
                }
            }

            @Override // com.lzy.okserver.ProgressListener
            public void onRemove(Progress progress) {
                DownloadListener downloadListener2 = downloadListener;
                if (downloadListener2 != null) {
                    downloadListener2.onRemove(progress);
                }
                CSEffectOnlineRes.this.isDowanloading = false;
            }
        });
        this.isDowanloading = true;
        register.start();
    }*/
}
