package com.picspool.lib.crash;

import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.Thread;

/* loaded from: classes3.dex */
public class DMCrashHandler implements Thread.UncaughtExceptionHandler {
    public static final boolean DEBUG = true;
    private static DMCrashHandler INSTANCE;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private void postReport(File file) {
    }

    private DMCrashHandler() {
    }

    public static DMCrashHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DMCrashHandler();
        }
        return INSTANCE;
    }

    public void init(Context context) {
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
        if (!handleException(th) && (uncaughtExceptionHandler = this.mDefaultHandler) != null) {
            uncaughtExceptionHandler.uncaughtException(thread, th);
            return;
        }
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException unused) {
        }
        Process.killProcess(Process.myPid());
        System.exit(10);
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.picspool.lib.crash.DMCrashHandler$1] */
    private boolean handleException(Throwable th) {
        if (th == null) {
            return false;
        }
        final StackTraceElement[] stackTrace = th.getStackTrace();
        final String message = th.getMessage();
        new Thread() { // from class: com.picspool.lib.crash.DMCrashHandler.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Looper.prepare();
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(Environment.getExternalStorageDirectory(), "crash-" + System.currentTimeMillis() + ".log"), true);
                    fileOutputStream.write(message.getBytes());
                    for (int i = 0; i < stackTrace.length; i++) {
                        fileOutputStream.write(stackTrace[i].toString().getBytes());
                    }
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception unused) {
                }
                Looper.loop();
            }
        }.start();
        return false;
    }
}
