package com.picspool.snappic.utils;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes.dex */
public class CSAppExecutor {
    private static ExecutorService mExecutorService;

    public static Executor getExecutor() {
        if (mExecutorService == null) {
            synchronized (CSAppExecutor.class) {
                if (mExecutorService == null) {
                    mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() > 0 ? Runtime.getRuntime().availableProcessors() : 2);
                }
            }
        }
        return mExecutorService;
    }

    public static void runOnThread(Runnable runnable) {
        getExecutor().execute(runnable);
    }

    /* loaded from: classes.dex */
    public static class MainThreadExecutor implements Executor {
        private static MainThreadExecutor mainThreadExecutor;
        private final Handler handler = new Handler(Looper.getMainLooper());

        private MainThreadExecutor() {
        }

        public static MainThreadExecutor instance() {
            if (mainThreadExecutor == null) {
                synchronized (MainThreadExecutor.class) {
                    if (mainThreadExecutor == null) {
                        mainThreadExecutor = new MainThreadExecutor();
                    }
                }
            }
            return mainThreadExecutor;
        }

        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            this.handler.post(runnable);
        }
    }
}
