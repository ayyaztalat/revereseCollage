package com.picspool.lib.border;

import android.content.Context;
import android.os.Handler;

/* loaded from: classes.dex */
public class CSTAsyncBorderProcess {
    CSTBorderRes borderRes;
    Context context;
    private final Handler handler = new Handler();
    int height;
    OnBorderProcessListener listener;
    int width;

    /* loaded from: classes.dex */
    public interface OnBorderProcessListener {
        void onBorderProcessFinish(CSBorderReturns cSBorderReturns);
    }

    public static void asyncBorderProcess(Context context, CSTBorderRes cSTBorderRes, int i, int i2, OnBorderProcessListener onBorderProcessListener) {
        CSTAsyncBorderProcess cSTAsyncBorderProcess = new CSTAsyncBorderProcess();
        cSTAsyncBorderProcess.setData(context, i, i2, cSTBorderRes);
        cSTAsyncBorderProcess.setOnBorderProcessListener(onBorderProcessListener);
        cSTAsyncBorderProcess.execute();
    }

    public void setData(Context context, int i, int i2, CSTBorderRes cSTBorderRes) {
        this.context = context;
        this.width = i;
        this.height = i2;
        this.borderRes = cSTBorderRes;
    }

    public void setOnBorderProcessListener(OnBorderProcessListener onBorderProcessListener) {
        this.listener = onBorderProcessListener;
    }

    public void execute() {
        new Thread(new Runnable() { // from class: com.picspool.lib.border.CSTAsyncBorderProcess.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    final CSBorderReturns processNinePathBorderOuter = CSTBorderProcess.processNinePathBorderOuter(CSTAsyncBorderProcess.this.context, CSTAsyncBorderProcess.this.width, CSTAsyncBorderProcess.this.height, CSTAsyncBorderProcess.this.borderRes);
                    CSTAsyncBorderProcess.this.handler.post(new Runnable() { // from class: com.picspool.lib.border.CSTAsyncBorderProcess.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (CSTAsyncBorderProcess.this.listener != null) {
                                CSTAsyncBorderProcess.this.listener.onBorderProcessFinish(processNinePathBorderOuter);
                            }
                        }
                    });
                } catch (Exception unused) {
                    if (CSTAsyncBorderProcess.this.listener != null) {
                        CSTAsyncBorderProcess.this.listener.onBorderProcessFinish(null);
                    }
                }
            }
        }).start();
    }
}
