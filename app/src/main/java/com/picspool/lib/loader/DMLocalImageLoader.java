package com.picspool.lib.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import com.picspool.lib.service.DMImageMediaItem;

/* loaded from: classes3.dex */
public class DMLocalImageLoader {
    private static DMLocalImageLoader mInstance = new DMLocalImageLoader();
    private boolean isShutDown = false;
    private ThreadPoolExecutor mThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
    private ArrayList<DMImageRequest> mImagesList = new ArrayList<>();
    private ArrayList<DMImageRequest> mOnLoadingList = new ArrayList<>();
    private boolean onDispath = false;
    private int mThumbBitmapWidth = 0;
    private int mMaxCacheNum = 0;

    private void log(String str, String str2) {
    }

    public void shutdownThumbLoder() {
        log("SysPhotoSelector", "shutdownThumbLoder");
        if (this.isShutDown) {
            return;
        }
        this.isShutDown = true;
        ThreadPoolExecutor threadPoolExecutor = this.mThreadPool;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.shutdownNow();
            this.mThreadPool = null;
        }
        ArrayList<DMImageRequest> arrayList = this.mImagesList;
        if (arrayList != null) {
            arrayList.clear();
            this.mImagesList = null;
        }
        ArrayList<DMImageRequest> arrayList2 = this.mOnLoadingList;
        if (arrayList2 != null) {
            arrayList2.clear();
            this.mOnLoadingList = null;
        }
    }

    private void recycleBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        bitmap.recycle();
    }

    public boolean isShutDown() {
        return this.isShutDown;
    }

    public static DMLocalImageLoader getInstance() {
        if (mInstance == null) {
            mInstance = new DMLocalImageLoader();
        }
        return mInstance;
    }

    public Bitmap loadImage(Context context, DMImageMediaItem dMImageMediaItem, int i, int i2, DMImageCallBack dMImageCallBack) {
        this.mThumbBitmapWidth = i;
        this.mMaxCacheNum = i2;
        addImageRequest(new DMImageRequest(context, dMImageMediaItem, dMImageCallBack));
        return null;
    }

    private void addImageRequest(DMImageRequest dMImageRequest) {
        String data = dMImageRequest.getmImageMediaItem().getData();
        if ((data == null && (data = dMImageRequest.getPath()) == null) || dMImageRequest == null || TextUtils.isEmpty(data)) {
            return;
        }
        synchronized (this.mImagesList) {
            if (this.mMaxCacheNum <= 0) {
                this.mMaxCacheNum = 30;
            }
            if (this.mImagesList.size() > this.mMaxCacheNum) {
                this.mImagesList.remove(0);
            }
            this.mImagesList.remove(dMImageRequest);
            this.mImagesList.add(dMImageRequest);
        }
        log("SysPhotoSelector", "dispatch1 active:" + this.mThreadPool.getActiveCount() + "  corePoolSize:" + this.mThreadPool.getCorePoolSize());
        if (!this.onDispath) {
            log("SysPhotoSelector", "addImageRequest 1 ");
            dispatch();
            return;
        }
        if (this.mThreadPool.getActiveCount() < this.mThreadPool.getCorePoolSize()) {
            log("SysPhotoSelector", "dispatch1 active:" + this.mThreadPool.getActiveCount() + "  corePoolSize:" + this.mThreadPool.getCorePoolSize());
            dispatch();
        }
        log("SysPhotoSelector", "addImageRequest 2 ");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeImageRequest(DMImageRequest dMImageRequest) {
        synchronized (this.mImagesList) {
            if (dMImageRequest != null) {
                this.mImagesList.remove(dMImageRequest);
            }
            if (this.mImagesList.size() > 0) {
                dispatch();
            } else {
                this.onDispath = false;
            }
        }
    }

    private void dispatch() {
        this.onDispath = true;
        if (this.mThreadPool.getActiveCount() >= this.mThreadPool.getCorePoolSize()) {
            return;
        }
        int corePoolSize = this.mThreadPool.getCorePoolSize() - this.mThreadPool.getActiveCount();
        synchronized (this.mImagesList) {
            if (this.mThreadPool.getActiveCount() == 0) {
                this.mOnLoadingList.clear();
            }
            if (this.mImagesList.size() < corePoolSize) {
                Iterator<DMImageRequest> it2 = this.mImagesList.iterator();
                while (it2.hasNext()) {
                    execute(it2.next());
                }
            } else {
                for (int size = this.mImagesList.size() - 1; size >= this.mImagesList.size() - corePoolSize; size--) {
                    execute(this.mImagesList.get(size));
                }
            }
        }
    }

    public Uri getImgUri(String str) {
        return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, str);
    }

    private void execute(final DMImageRequest dMImageRequest) {
        if (this.mOnLoadingList.contains(dMImageRequest)) {
            return;
        }
        try {
            final ImageHandler imageHandler = new ImageHandler(this, dMImageRequest);
            this.mThreadPool.execute(new Runnable() { // from class: com.picspool.lib.loader.DMLocalImageLoader.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        DMLocalImageLoader.this.mOnLoadingList.add(dMImageRequest);
                        Bitmap thumbnail = dMImageRequest.getmImageMediaItem().getThumbnail(dMImageRequest.getmContext(), 120, Bitmap.Config.ARGB_4444);
                        DMLocalImageLoader.this.mOnLoadingList.remove(dMImageRequest);
                        Message obtainMessage = imageHandler.obtainMessage();
                        obtainMessage.obj = thumbnail;
                        imageHandler.sendMessage(obtainMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                        DMLocalImageLoader.this.mOnLoadingList.remove(dMImageRequest);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap decodeThumbBitmapForFile(String str, int i, int i2, boolean z) {
        File file = new File(str);
        if (file.exists()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(str, options);
            options.inSampleSize = computeScale(options, i, i2);
            if (!z) {
                options.inSampleSize += (options.inSampleSize / 2) + 2;
            }
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inJustDecodeBounds = false;
            options.inPurgeable = true;
            options.inInputShareable = true;
            try {
                return BitmapFactory.decodeStream(new FileInputStream(file), null, options);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    private int computeScale(BitmapFactory.Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        if (i3 > i2 || i4 > i) {
            int round = Math.round(i3 / i2);
            int round2 = Math.round(i4 / i);
            if (round >= round2) {
                round = round2;
            }
            while ((i4 * i3) / (round * round) > i * i2 * 2) {
                round++;
            }
            return round;
        }
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class ImageHandler extends Handler {
        private final WeakReference<DMLocalImageLoader> mActivity;
        private final WeakReference<DMImageRequest> mRequest;

        public ImageHandler(DMLocalImageLoader dMLocalImageLoader, DMImageRequest dMImageRequest) {
            this.mActivity = new WeakReference<>(dMLocalImageLoader);
            this.mRequest = new WeakReference<>(dMImageRequest);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            DMImageRequest dMImageRequest = this.mRequest.get();
            if (dMImageRequest != null) {
                DMLocalImageLoader dMLocalImageLoader = this.mActivity.get();
                if (dMLocalImageLoader != null) {
                    dMLocalImageLoader.removeImageRequest(dMImageRequest);
                }
                try {
                    dMImageRequest.getCallBack().onImageLoader((Bitmap) message.obj, dMImageRequest.getmImgId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
