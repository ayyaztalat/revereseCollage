package com.picspool.lib.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.picspool.lib.service.DMImageMediaItem;
import com.picspool.lib.service.DMMediaBucket;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMBucketListAdapter extends BaseAdapter {
    static final String TAG = "DMBucketListAdapter";
    DMMediaBucket buckets;
    List<List<DMImageMediaItem>> bucketsList;
    private Context context;
    private LayoutInflater mInflater;
    private ListView mListView;
    HashMap<String, Bitmap> bitmapHash = new HashMap<>();
    private List<ViewHolder> holderArray = new ArrayList();

    public DMBucketListAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public void setListView(ListView listView) {
        this.mListView = listView;
    }

    public void setBuckets(DMMediaBucket dMMediaBucket, List<List<DMImageMediaItem>> list) {
        this.buckets = dMMediaBucket;
        this.bucketsList = list;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.bucketsList.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.bucketsList.get(i);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        if (this.bucketsList.get(i).get(0).getBuketId() == null) {
            return 0L;
        }
        return Long.parseLong(this.bucketsList.get(i).get(0).getBuketId());
    }

    public String getBuckDisName(int i) {
        String buketDisplayName = this.bucketsList.get(i).get(0).getBuketDisplayName();
        return (buketDisplayName != null || this.bucketsList.get(i).size() <= 1) ? buketDisplayName : this.bucketsList.get(i).get(1).getBuketDisplayName();
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2;
        final ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view2 = this.mInflater.inflate(R.layout.dm_view_list_bucket, (ViewGroup) null);
            viewHolder.img = (ImageView) view2.findViewById(R.id.img);
            viewHolder.title = (TextView) view2.findViewById(R.id.title);
            viewHolder.info = (TextView) view2.findViewById(R.id.info);
            view2.setTag(viewHolder);
            this.holderArray.add(viewHolder);
        } else {
            view2 = view;
            viewHolder = (ViewHolder) view.getTag();
        }
        String buketId = this.bucketsList.get(i).get(0).getBuketId();
        String buketDisplayName = this.bucketsList.get(i).get(0).getBuketDisplayName();
        ImageView imageView = viewHolder.img;
        imageView.setTag("PhotoSelector" + buketId);
        viewHolder.title.setText(buketDisplayName);
        int size = this.bucketsList.get(i).size();
        TextView textView = viewHolder.info;
        textView.setText("(" + String.valueOf(size) + ")");
        if (this.bitmapHash.containsKey(buketId)) {
            viewHolder.img.setImageBitmap(null);
            viewHolder.img.setImageBitmap(this.bitmapHash.get(buketId));
            return view2;
        }
        viewHolder.img.setImageBitmap(null);
        this.buckets.bucketCover(this.context, buketId, new DMMediaBucket.OnBucketCoverLoadListener() { // from class: com.picspool.lib.view.DMBucketListAdapter.1
            @Override // com.picspool.lib.service.DMMediaBucket.OnBucketCoverLoadListener
            public void onBucketCoverLoadSuccess(Bitmap bitmap, DMImageMediaItem dMImageMediaItem) {
                if (DMBucketListAdapter.this.mListView != null) {
                    ListView listView = DMBucketListAdapter.this.mListView;
                    ImageView imageView2 = (ImageView) listView.findViewWithTag("PhotoSelector" + dMImageMediaItem.getBuketId());
                    if (bitmap != null && !bitmap.isRecycled() && imageView2 != null) {
                        imageView2.setImageBitmap(bitmap);
                        DMBucketListAdapter.this.bitmapHash.put(dMImageMediaItem.getBuketId(), bitmap);
                        return;
                    } else if (bitmap != null) {
                        bitmap.isRecycled();
                        return;
                    } else {
                        return;
                    }
                }
                viewHolder.img.setImageBitmap(null);
                if (viewHolder.iconBitmap != null && !viewHolder.iconBitmap.isRecycled()) {
                    viewHolder.iconBitmap.recycle();
                }
                viewHolder.iconBitmap = bitmap;
                viewHolder.img.setImageBitmap(viewHolder.iconBitmap);
                viewHolder.img.setImageBitmap(bitmap);
            }
        });
        return view2;
    }

    public void dispose() {
        for (int i = 0; i < this.holderArray.size(); i++) {
            ViewHolder viewHolder = this.holderArray.get(i);
            viewHolder.img.setImageBitmap(null);
            if (viewHolder.iconBitmap != null && !viewHolder.iconBitmap.isRecycled()) {
                viewHolder.iconBitmap.recycle();
            }
            viewHolder.iconBitmap = null;
        }
        ArrayList arrayList = new ArrayList();
        for (String str : this.bitmapHash.keySet()) {
            if (str != null && str.length() != 0) {
                arrayList.add(str);
            }
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            Bitmap remove = this.bitmapHash.remove((String) arrayList.get(i2));
            if (remove != null && !remove.isRecycled()) {
                remove.recycle();
            }
        }
        arrayList.clear();
    }

    /* loaded from: classes3.dex */
    final class ViewHolder {
        public Bitmap iconBitmap;
        public ImageView img;
        public TextView info;
        public TextView title;

        ViewHolder() {
        }
    }
}
