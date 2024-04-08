package com.picspool.lib.service;

import static java.lang.Short.MAX_VALUE;

import android.content.Context;
import android.graphics.Bitmap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class DMMediaBucket {
    String bucketPath;
    String camerBucketId;
    Context mContext;
    private HashMap<String, List<DMImageMediaItem>> buketItem = new HashMap<>();
    private HashMap<String, String> buketMapName = new HashMap<>();
    private HashMap<String, Date> buketLastDate = new HashMap<>();

    /* loaded from: classes3.dex */
    public interface OnBucketCoverLoadListener {
        void onBucketCoverLoadSuccess(Bitmap bitmap, DMImageMediaItem dMImageMediaItem);
    }

    public DMMediaBucket(Context context) {
        this.mContext = context;
    }

    public void addMediaItem(DMImageMediaItem dMImageMediaItem) {
        String buketId = dMImageMediaItem.getBuketId();
        if (this.buketItem.get(buketId) == null) {
            LinkedList linkedList = new LinkedList();
            this.buketItem.put(buketId, linkedList);
            linkedList.add(dMImageMediaItem);
        } else {
            this.buketItem.get(buketId).add(dMImageMediaItem);
        }
        this.buketMapName.put(buketId, dMImageMediaItem.getBuketDisplayName());
        if (dMImageMediaItem.isCamera()) {
            this.camerBucketId = dMImageMediaItem.getBuketId();
        }
    }

    public Bitmap bucketCover(Context context, String str) {
        List<DMImageMediaItem> list = this.buketItem.get(str);
        if (list == null || list.size() <= 0) {
            return null;
        }
        return list.get(0).getThumbnail(context, 120);
    }

    public void bucketCover(Context context, String str, final OnBucketCoverLoadListener onBucketCoverLoadListener) {
        List<DMImageMediaItem> list = this.buketItem.get(str);
        if (list == null || list.size() <= 0) {
            return;
        }
        DMAsyncThumbnailLoader dMAsyncThumbnailLoader = DMAsyncThumbnailLoader.getInstance();
        if (dMAsyncThumbnailLoader == null) {
            DMAsyncThumbnailLoader.shutdownThumbLoder();
            DMAsyncThumbnailLoader.initThumbLoader();
            dMAsyncThumbnailLoader = DMAsyncThumbnailLoader.getInstance();
        }
        if (dMAsyncThumbnailLoader == null) {
            return;
        }
        dMAsyncThumbnailLoader.thumbLoad(this.mContext, list.get(0), new DMAsyncThumbnailLoader.OnImageLoadListener() { // from class: com.picspool.lib.service.DMMediaBucket.1
            @Override // com.picspool.lib.service.DMAsyncThumbnailLoader.OnImageLoadListener
            public void onLoadFail(DMImageMediaItem dMImageMediaItem) {
            }

            @Override // com.picspool.lib.service.DMAsyncThumbnailLoader.OnImageLoadListener
            public void onLoadFinish(DMImageMediaItem dMImageMediaItem, Bitmap bitmap) {
                onBucketCoverLoadListener.onBucketCoverLoadSuccess(bitmap, dMImageMediaItem);
            }
        }, false);
    }

    public String getBucketPath(String str) {
        String data;
        int lastIndexOf;
        List<DMImageMediaItem> list = this.buketItem.get(str);
        if (list == null || list.size() <= 0 || (data = list.get(0).getData()) == null || (lastIndexOf = data.lastIndexOf("/")) < 0 || lastIndexOf >= data.length()) {
            return null;
        }
        return data.substring(0, lastIndexOf);
    }

    public long getBucketLastAddedTime(String str) {
        List<DMImageMediaItem> list = this.buketItem.get(str);
        long j = 0;
        if (list != null) {
            for (DMImageMediaItem dMImageMediaItem : list) {
                if (dMImageMediaItem.getDateAdded() > j) {
                    j = dMImageMediaItem.getDateAdded();
                }
            }
        }
        return j;
    }

    public List<List<DMImageMediaItem>> getBucketList() {
        ArrayList<Map.Entry> arrayList = new ArrayList<>(this.buketItem.entrySet());
        arrayList.sort((Comparator<? super Map.Entry>) (entry, entry2) -> {
            long bucketWeight = DMMediaBucket.this.getBucketWeight(String.valueOf(entry.getKey()));
            return Long.compare(DMMediaBucket.this.getBucketWeight(String.valueOf(entry2.getKey())), bucketWeight);
        });
        ArrayList<List<DMImageMediaItem>> arrayList2 = new ArrayList();
        for (Map.Entry entry : arrayList) {
            arrayList2.add((List<DMImageMediaItem>) entry.getValue());
        }
        return arrayList2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getBucketWeight(String str) {
        String bucketPath = getBucketPath(str);
        if (bucketPath == null) {
            return getBucketLastAddedTime(str);
        }
        long j = bucketPath.contains("DCIM") ? 9223372036854775707L : 0L;
        if (bucketPath.contains("DCIM") && bucketPath.contains("Camera")) {
            j = MAX_VALUE;
        }
        return j == 0 ? getBucketLastAddedTime(str) : j;
    }
}
