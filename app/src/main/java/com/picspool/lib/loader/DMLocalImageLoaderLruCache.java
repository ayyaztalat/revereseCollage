package com.picspool.lib.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import androidx.collection.LruCache;
import java.io.File;
import java.io.FileInputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import com.picspool.lib.service.DMImageMediaItem;

/* loaded from: classes3.dex */
public class DMLocalImageLoaderLruCache {
    public static Bitmap.Config iconFormat = Bitmap.Config.ARGB_4444;
    private static DMLocalImageLoaderLruCache mInstance = new DMLocalImageLoaderLruCache();
    private HashSet<String> lruCacheSet;
    private LruCache<String, Bitmap> mMemoryCache;
    private boolean isShutDown = false;
    private ThreadPoolExecutor mThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
    private ArrayList<DMImageRequest> mImagesList = new ArrayList<>();
    private ArrayList<DMImageRequest> mOnLoadingList = new ArrayList<>();
    private boolean onDispath = false;
    private int mThumbBitmapWidth = 0;
    private int mMaxCacheNum = 0;
    private boolean isLoadFromHead = true;

    public DMLocalImageLoaderLruCache() {
        this.mMemoryCache = null;
        try {
            this.mMemoryCache = new LruCache<String, Bitmap>(((int) (Runtime.getRuntime().maxMemory() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID)) / 4) { // from class: com.picspool.lib.loader.DMLocalImageLoaderLruCache.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // androidx.collection.LruCache
                public int sizeOf(String str, Bitmap bitmap) {
                    return (bitmap.getRowBytes() * bitmap.getHeight()) / 1024;
                }
            };
            this.lruCacheSet = new HashSet<>();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void setLoadFromHead(boolean z) {
        this.isLoadFromHead = z;
    }

    public void clearCache() {
        ArrayList<DMImageRequest> arrayList = this.mImagesList;
        if (arrayList != null) {
            arrayList.clear();
        }
        ArrayList<DMImageRequest> arrayList2 = this.mOnLoadingList;
        if (arrayList2 != null) {
            arrayList2.clear();
        }
        LruCache<String, Bitmap> lruCache = this.mMemoryCache;
        if (lruCache != null) {
            lruCache.evictAll();
        }
        this.onDispath = false;
    }

    public void shutdownThumbLoder() {
        if (this.isShutDown) {
            return;
        }
        this.isShutDown = true;
        ThreadPoolExecutor threadPoolExecutor = this.mThreadPool;
        try {
            if (threadPoolExecutor != null) {
                try {
                    threadPoolExecutor.shutdownNow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            LruCache<String, Bitmap> lruCache = this.mMemoryCache;
            if (lruCache != null) {
                lruCache.evictAll();
                this.mMemoryCache = null;
            }
            ArrayList<DMImageRequest> arrayList = this.mImagesList;
            if (arrayList != null) {
                arrayList.clear();
                this.mImagesList = null;
            }
            ArrayList<DMImageRequest> arrayList2 = this.mOnLoadingList;
            if (arrayList2 != null) {
                arrayList2.clear();
            }
            mInstance = null;
        } finally {
            this.mThreadPool = null;
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

    public static DMLocalImageLoaderLruCache getInstance() {
        if (mInstance == null) {
            mInstance = new DMLocalImageLoaderLruCache();
        }
        return mInstance;
    }

    public Bitmap loadImage(Context context, DMImageMediaItem dMImageMediaItem, int i, int i2, DMImageCallBack dMImageCallBack) {
        DMImageRequest dMImageRequest = new DMImageRequest(context, dMImageMediaItem, dMImageCallBack);
        String data = dMImageMediaItem.getData();
        if (data == null && (data = dMImageRequest.getPath()) == null) {
            return null;
        }
        this.mThumbBitmapWidth = i;
        this.mMaxCacheNum = i2;
        Bitmap bitmapFromMemCache = data != null ? getBitmapFromMemCache(data) : null;
        if (bitmapFromMemCache == null || bitmapFromMemCache.isRecycled()) {
            addImageRequest(dMImageRequest);
        }
        return bitmapFromMemCache;
    }

    private void addImageRequest(DMImageRequest dMImageRequest) {
        String data = dMImageRequest.getmImageMediaItem().getData();
        if ((data == null && (data = dMImageRequest.getPath()) == null) || dMImageRequest == null || TextUtils.isEmpty(data)) {
            return;
        }
        synchronized (this.mImagesList) {
            if (this.mMaxCacheNum <= 0) {
                this.mMaxCacheNum = 50;
            }
            this.mImagesList.size();
            this.mImagesList.remove(dMImageRequest);
            this.mImagesList.add(dMImageRequest);
        }
        if (this.onDispath) {
            return;
        }
        dispatch();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeImageRequest(DMImageRequest dMImageRequest) {
        ArrayList<DMImageRequest> arrayList;
        String data = dMImageRequest.getmImageMediaItem().getData();
        if ((data == null && (data = dMImageRequest.getPath()) == null) || dMImageRequest == null || TextUtils.isEmpty(data) || (arrayList = this.mImagesList) == null) {
            return;
        }
        synchronized (arrayList) {
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
        int size;
        if (this.isShutDown) {
            return;
        }
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
                synchronized (this.mOnLoadingList) {
                    Iterator<DMImageRequest> it2 = this.mImagesList.iterator();
                    while (it2.hasNext()) {
                        DMImageRequest next = it2.next();
                        if (next != null && !this.mOnLoadingList.contains(next)) {
                            execute(next);
                        }
                    }
                }
            } else if (!this.isLoadFromHead) {
                synchronized (this.mOnLoadingList) {
                    for (int size2 = this.mImagesList.size() - 1; size2 >= this.mImagesList.size() - corePoolSize; size2--) {
                        DMImageRequest dMImageRequest = this.mImagesList.get(size2);
                        if (dMImageRequest != null && !this.mOnLoadingList.contains(dMImageRequest)) {
                            execute(this.mImagesList.get(size2));
                        }
                    }
                }
            } else {
                int i = 0;
                if (this.mImagesList.size() > this.mMaxCacheNum && ((size = this.mImagesList.size() - this.mMaxCacheNum) >= corePoolSize || (this.mImagesList.size() - corePoolSize) - 2 >= 0)) {
                    i = size;
                }
                synchronized (this.mOnLoadingList) {
                    while (i < this.mImagesList.size()) {
                        DMImageRequest dMImageRequest2 = this.mImagesList.get(i);
                        if (dMImageRequest2 != null && !this.mOnLoadingList.contains(dMImageRequest2)) {
                            execute(this.mImagesList.get(i));
                        }
                        i++;
                    }
                }
            }
        }
    }

    public Uri getImgUri(String str) {
        return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, str);
    }

    private void execute(final DMImageRequest dMImageRequest) {
        try {
            final ImageHandler imageHandler = new ImageHandler(this, dMImageRequest);
            this.mThreadPool.execute(new Runnable() { // from class: com.picspool.lib.loader.DMLocalImageLoaderLruCache.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (DMLocalImageLoaderLruCache.this.mOnLoadingList != null) {
                            synchronized (DMLocalImageLoaderLruCache.this.mOnLoadingList) {
                                DMLocalImageLoaderLruCache.this.mOnLoadingList.add(dMImageRequest);
                            }
                        }
                        Bitmap thumbnail = dMImageRequest.getmImageMediaItem().getThumbnail(dMImageRequest.getmContext(), DMLocalImageLoaderLruCache.this.mThumbBitmapWidth, DMLocalImageLoaderLruCache.iconFormat);
                        if (thumbnail != null) {
                            thumbnail.isRecycled();
                        }
                        if (DMLocalImageLoaderLruCache.this.mOnLoadingList != null) {
                            synchronized (DMLocalImageLoaderLruCache.this.mOnLoadingList) {
                                DMLocalImageLoaderLruCache.this.mOnLoadingList.remove(dMImageRequest);
                            }
                        }
                        DMLocalImageLoaderLruCache.this.addBitmapToMemoryCache(dMImageRequest.getmImageMediaItem().getData(), thumbnail);
                        Message obtainMessage = imageHandler.obtainMessage();
                        obtainMessage.obj = thumbnail;
                        imageHandler.sendMessage(obtainMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (DMLocalImageLoaderLruCache.this.mOnLoadingList != null) {
                            synchronized (DMLocalImageLoaderLruCache.this.mOnLoadingList) {
                                DMLocalImageLoaderLruCache.this.mOnLoadingList.remove(dMImageRequest);
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addBitmapToMemoryCache(String str, Bitmap bitmap) {
        LruCache<String, Bitmap> lruCache;
        Bitmap bitmapFromMemCache = getBitmapFromMemCache(str);
        if (str != null) {
            if ((bitmapFromMemCache != null && !bitmapFromMemCache.isRecycled()) || bitmap == null || (lruCache = this.mMemoryCache) == null) {
                return;
            }
            synchronized (lruCache) {
                this.mMemoryCache.put(str, bitmap);
                this.lruCacheSet.add(str);
            }
        }
    }

    private Bitmap getBitmapFromMemCache(String str) {
        LruCache<String, Bitmap> lruCache;
        if (str == null || str.equalsIgnoreCase("") || (lruCache = this.mMemoryCache) == null) {
            return null;
        }
        synchronized (lruCache) {
            Bitmap bitmap = this.mMemoryCache.get(str);
            if (bitmap != null) {
                this.mMemoryCache.remove(str);
                this.mMemoryCache.put(str, bitmap);
                return bitmap;
            }
            return null;
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
        private final WeakReference<DMLocalImageLoaderLruCache> mActivity;
        private DMImageRequest mRequest;

        public ImageHandler(DMLocalImageLoaderLruCache dMLocalImageLoaderLruCache, DMImageRequest dMImageRequest) {
            this.mActivity = new WeakReference<>(dMLocalImageLoaderLruCache);
            this.mRequest = dMImageRequest;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (this.mRequest != null) {
                DMLocalImageLoaderLruCache dMLocalImageLoaderLruCache = this.mActivity.get();
                if (dMLocalImageLoaderLruCache != null) {
                    dMLocalImageLoaderLruCache.removeImageRequest(this.mRequest);
                }
                this.mRequest.getCallBack().onImageLoader((Bitmap) message.obj, this.mRequest.getmImgId());
            }
        }
    }
}
