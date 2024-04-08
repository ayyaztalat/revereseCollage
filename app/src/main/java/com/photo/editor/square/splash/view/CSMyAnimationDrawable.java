package com.photo.editor.square.splash.view;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class CSMyAnimationDrawable {
    boolean isloop = false;
    private boolean stoploop = false;

    /* loaded from: classes2.dex */
    public static class MyFrame {
        byte[] bytes;
        Drawable drawable;
        int duration;
        boolean isReady = false;
    }

    /* loaded from: classes2.dex */
    public interface OnDrawableLoadedListener {
        void onDrawableLoaded(List<MyFrame> list);
    }

    public void animateRawManuallyFromXML(int i, final ImageView imageView, final boolean z, final Runnable runnable, final Runnable runnable2) {
        this.isloop = z;
        loadRaw(i, imageView.getContext(), new OnDrawableLoadedListener() { // from class: com.photo.editor.square.splash.view.CSMyAnimationDrawable.1
            @Override // com.photo.editor.square.splash.view.CSMyAnimationDrawable.OnDrawableLoadedListener
            public void onDrawableLoaded(List<MyFrame> list) {
                Runnable runnable3 = runnable;
                if (runnable3 != null) {
                    runnable3.run();
                }
                if (!z) {
                    CSMyAnimationDrawable.this.animateRawManually(list, imageView, runnable2);
                } else {
                    CSMyAnimationDrawable.this.animateRawManuallyloop(list, imageView, runnable2);
                }
            }
        });
    }

    public void animateRawManuallyFromXML(int i, int i2, final ImageView imageView, final boolean z, final Runnable runnable, final Runnable runnable2) {
        this.isloop = z;
        loadRaw(i, imageView.getContext(), i2, new OnDrawableLoadedListener() { // from class: com.photo.editor.square.splash.view.CSMyAnimationDrawable.2
            @Override // com.photo.editor.square.splash.view.CSMyAnimationDrawable.OnDrawableLoadedListener
            public void onDrawableLoaded(List<MyFrame> list) {
                Runnable runnable3 = runnable;
                if (runnable3 != null) {
                    runnable3.run();
                }
                if (!z) {
                    CSMyAnimationDrawable.this.animateRawManually(list, imageView, runnable2);
                } else {
                    CSMyAnimationDrawable.this.animateRawManuallyloop(list, imageView, runnable2);
                }
            }
        });
    }

    private static void loadRaw(int i, Context context, OnDrawableLoadedListener onDrawableLoadedListener) {
        loadFromXml(i, context, onDrawableLoadedListener);
    }

    private static void loadRaw(int i, Context context, int i2, OnDrawableLoadedListener onDrawableLoadedListener) {
        loadFromXml(i, context, i2, onDrawableLoadedListener);
    }

    private static void loadFromXml(final int i, final Context context, final int i2, final OnDrawableLoadedListener onDrawableLoadedListener) {
        new Thread(new Runnable() { // from class: com.photo.editor.square.splash.view.CSMyAnimationDrawable.3
            @Override // java.lang.Runnable
            public void run() {
                final ArrayList arrayList = new ArrayList();
                try {
                    int i3 = i;
                    for (int i4 = 0; i4 < i2; i4++) {
                        byte[] byteArray = CSMyAnimationDrawable.toByteArray(context.getResources().openRawResource(i3 + i4));
                        MyFrame myFrame = new MyFrame();
                        myFrame.bytes = byteArray;
                        myFrame.duration = 55;
                        arrayList.add(myFrame);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new Handler(context.getMainLooper()).post(new Runnable() { // from class: com.photo.editor.square.splash.view.CSMyAnimationDrawable.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (onDrawableLoadedListener != null) {
                            onDrawableLoadedListener.onDrawableLoaded(arrayList);
                        }
                    }
                });
            }
        }).run();
    }

    private static void loadFromXml(final int i, final Context context, final OnDrawableLoadedListener onDrawableLoadedListener) {
        new Thread(new Runnable() { // from class: com.photo.editor.square.splash.view.CSMyAnimationDrawable.4
            @Override // java.lang.Runnable
            public void run() {
                final ArrayList arrayList = new ArrayList();
                XmlResourceParser xml = context.getResources().getXml(i);
                try {
                    for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                        if (eventType != 0 && eventType == 2 && xml.getName().equals("item")) {
                            byte[] bArr = null;
                            int i2 = 1000;
                            for (int i3 = 0; i3 < xml.getAttributeCount(); i3++) {
                                if (xml.getAttributeName(i3).equals("drawable")) {
                                    bArr = CSMyAnimationDrawable.toByteArray(context.getResources().openRawResource(Integer.parseInt(xml.getAttributeValue(i3).substring(1))));
                                } else if (xml.getAttributeName(i3).equals("duration")) {
                                    i2 = xml.getAttributeIntValue(i3, 1000);
                                }
                            }
                            MyFrame myFrame = new MyFrame();
                            myFrame.bytes = bArr;
                            myFrame.duration = i2;
                            arrayList.add(myFrame);
                            continue;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e2) {
                    e2.printStackTrace();
                }
                new Handler(context.getMainLooper()).post(new Runnable() { // from class: com.photo.editor.square.splash.view.CSMyAnimationDrawable.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (onDrawableLoadedListener != null) {
                            onDrawableLoadedListener.onDrawableLoaded(arrayList);
                        }
                    }
                });
            }
        }).run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animateRawManually(List<MyFrame> list, ImageView imageView, Runnable runnable) {
        animateRawManually(list, imageView, runnable, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animateRawManually(final List<MyFrame> list, final ImageView imageView, final Runnable runnable, final int i) {
        final MyFrame myFrame = list.get(i);
        if (i == 0) {
            myFrame.drawable = new BitmapDrawable(imageView.getContext().getResources(), BitmapFactory.decodeByteArray(myFrame.bytes, 0, myFrame.bytes.length));
        } else {
            MyFrame myFrame2 = list.get(i - 1);
            ((BitmapDrawable) myFrame2.drawable).getBitmap().recycle();
            myFrame2.drawable = null;
            myFrame2.isReady = false;
        }
        imageView.setImageDrawable(myFrame.drawable);
        new Handler().postDelayed(new Runnable() { // from class: com.photo.editor.square.splash.view.CSMyAnimationDrawable.5
            @Override // java.lang.Runnable
            public void run() {
                if (imageView.getDrawable() == myFrame.drawable) {
                    if (i + 1 < list.size()) {
                        MyFrame myFrame3 = (MyFrame) list.get(i + 1);
                        if (myFrame3.isReady) {
                            CSMyAnimationDrawable.this.animateRawManually(list, imageView, runnable, i + 1);
                            return;
                        } else {
                            myFrame3.isReady = true;
                            return;
                        }
                    }
                    Runnable runnable2 = runnable;
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }
            }
        }, myFrame.duration);
        if (i + 1 < list.size()) {
            new Thread(new Runnable() { // from class: com.photo.editor.square.splash.view.CSMyAnimationDrawable.6
                @Override // java.lang.Runnable
                public void run() {
                    MyFrame myFrame3 = (MyFrame) list.get(i + 1);
                    myFrame3.drawable = new BitmapDrawable(imageView.getContext().getResources(), BitmapFactory.decodeByteArray(myFrame3.bytes, 0, myFrame3.bytes.length));
                    if (myFrame3.isReady) {
                        CSMyAnimationDrawable.this.animateRawManually(list, imageView, runnable, i + 1);
                    } else {
                        myFrame3.isReady = true;
                    }
                }
            }).run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animateRawManuallyloop(List<MyFrame> list, ImageView imageView, Runnable runnable) {
        animateRawManuallyLoop(list, imageView, runnable, 0);
    }

    public void dispose() {
        this.stoploop = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animateRawManuallyLoop(final List<MyFrame> list, final ImageView imageView, final Runnable runnable, final int i) {
        if (this.stoploop) {
            return;
        }
        final MyFrame myFrame = list.get(i);
        if (i == 0) {
            myFrame.drawable = new BitmapDrawable(imageView.getContext().getResources(), BitmapFactory.decodeByteArray(myFrame.bytes, 0, myFrame.bytes.length));
        } else {
            MyFrame myFrame2 = list.get(i - 1);
            ((BitmapDrawable) myFrame2.drawable).getBitmap().recycle();
            myFrame2.drawable = null;
            myFrame2.isReady = false;
        }
        imageView.setImageDrawable(myFrame.drawable);
        new Handler().postDelayed(new Runnable() { // from class: com.photo.editor.square.splash.view.CSMyAnimationDrawable.7
            @Override // java.lang.Runnable
            public void run() {
                if (imageView.getDrawable() == myFrame.drawable) {
                    if (i + 1 >= list.size()) {
                        CSMyAnimationDrawable.this.animateRawManuallyLoop(list, imageView, runnable, 0);
                        return;
                    }
                    MyFrame myFrame3 = (MyFrame) list.get(i + 1);
                    if (myFrame3.isReady) {
                        CSMyAnimationDrawable.this.animateRawManuallyLoop(list, imageView, runnable, i + 1);
                    } else {
                        myFrame3.isReady = true;
                    }
                }
            }
        }, myFrame.duration);
        new Thread(new Runnable() { // from class: com.photo.editor.square.splash.view.CSMyAnimationDrawable.8
            @Override // java.lang.Runnable
            public void run() {
                int i2 = i + 1 < list.size() ? i + 1 : 0;
                MyFrame myFrame3 = (MyFrame) list.get(i2);
                myFrame3.drawable = new BitmapDrawable(imageView.getContext().getResources(), BitmapFactory.decodeByteArray(myFrame3.bytes, 0, myFrame3.bytes.length));
                if (myFrame3.isReady) {
                    CSMyAnimationDrawable.this.animateRawManuallyLoop(list, imageView, runnable, i2);
                } else {
                    myFrame3.isReady = true;
                }
            }
        }).run();
    }

    public static void animateManuallyFromRawResource(int i, ImageView imageView, Runnable runnable, Runnable runnable2, int i2) throws IOException, XmlPullParserException {
        AnimationDrawable animationDrawable = new AnimationDrawable();
        XmlResourceParser xml = imageView.getContext().getResources().getXml(i);
        for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
            if (eventType != 0 && eventType == 2 && xml.getName().equals("item")) {
                BitmapDrawable bitmapDrawable = null;
                for (int i3 = 0; i3 < xml.getAttributeCount(); i3++) {
                    if (xml.getAttributeName(i3).equals("drawable")) {
                        byte[] byteArray = toByteArray(imageView.getContext().getResources().openRawResource(Integer.parseInt(xml.getAttributeValue(i3).substring(1))));
                        bitmapDrawable = new BitmapDrawable(imageView.getContext().getResources(), BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));
                    } else if (xml.getAttributeName(i3).equals("duration")) {
                        i2 = xml.getAttributeIntValue(i3, 66);
                    }
                }
                animationDrawable.addFrame(bitmapDrawable, i2);
            }
        }
        if (runnable != null) {
            runnable.run();
        }
        animateDrawableManually(animationDrawable, imageView, runnable2, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void animateDrawableManually(final AnimationDrawable animationDrawable, final ImageView imageView, final Runnable runnable, final int i) {
        final Drawable frame = animationDrawable.getFrame(i);
        imageView.setImageDrawable(frame);
        new Handler().postDelayed(new Runnable() { // from class: com.photo.editor.square.splash.view.CSMyAnimationDrawable.9
            @Override // java.lang.Runnable
            public void run() {
                if (imageView.getDrawable() == frame) {
                    if (i + 1 < animationDrawable.getNumberOfFrames()) {
                        CSMyAnimationDrawable.animateDrawableManually(animationDrawable, imageView, runnable, i + 1);
                        return;
                    }
                    Runnable runnable2 = runnable;
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }
            }
        }, animationDrawable.getDuration(i));
    }

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            copy(inputStream, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return byteArray;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                }
                throw th2;
            }
        }
    }

    public static int copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        long copyLarge = copyLarge(inputStream, outputStream);
        if (copyLarge > 2147483647L) {
            return -1;
        }
        return (int) copyLarge;
    }

    public static long copyLarge(InputStream inputStream, OutputStream outputStream) throws IOException {
        return copy(inputStream, outputStream, 4096);
    }

    public static long copy(InputStream inputStream, OutputStream outputStream, int i) throws IOException {
        return copyLarge(inputStream, outputStream, new byte[i]);
    }

    public static long copyLarge(InputStream inputStream, OutputStream outputStream, byte[] bArr) throws IOException {
        long j = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (-1 == read) {
                return j;
            }
            outputStream.write(bArr, 0, read);
            j += read;
        }
    }
}
