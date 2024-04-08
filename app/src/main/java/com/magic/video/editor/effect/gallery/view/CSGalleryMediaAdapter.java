package com.magic.video.editor.effect.gallery.view;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.magic.video.editor.effect.cut.utils.CSScreenInfoUtil;
import com.magic.video.editor.effect.gallery.model.CSMediaItem;
import com.magic.video.editor.effect.gallery.model.CSMediaOptions;
import com.magic.video.editor.effect.gallery.model.CSMediaUtils;
import com.sky.testproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes2.dex */
public class CSGalleryMediaAdapter extends CursorAdapter implements AbsListView.RecyclerListener {
    public static final ArrayList<Uri> errlist = new ArrayList<>();
    private final CSGalleryActivity galleryActivity;
    private final Handler handler;
    private final List<GalleryMediaViewHolder> holderList;
    private int imgWidth;
    private final AbsListView.LayoutParams mGridViewLayoutParams;
    private final RelativeLayout.LayoutParams mImageViewLayoutParams;
    private int mItemHeight;
    private int mMaxNum;
    private List<CSMediaItem> mMediaListSelected;
    private final CSMediaOptions mMediaOptions;
    private int mMediaType;
    private final List<CSPickerImageView> mPickerImageViewSelected;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class GalleryMediaViewHolder {
        RelativeLayout container;
        CSPickerImageView imageView;
        TextView numSelected;
        FrameLayout selected;
        TextView videoDurtation;

        private GalleryMediaViewHolder() {
        }
    }

    public CSGalleryMediaAdapter(CSGalleryActivity cSGalleryActivity, Cursor cursor, int i, int i2, CSMediaOptions cSMediaOptions) {
        this(cSGalleryActivity, cursor, i, null, i2, cSMediaOptions);
    }

    private CSGalleryMediaAdapter(CSGalleryActivity cSGalleryActivity, Cursor cursor, int i, List<CSMediaItem> list, int i2, CSMediaOptions cSMediaOptions) {
        super(cSGalleryActivity, cursor, i);
        this.galleryActivity = cSGalleryActivity;
        this.mMediaListSelected = new ArrayList();
        this.mItemHeight = 0;
        this.mMaxNum = 0;
        this.mPickerImageViewSelected = new ArrayList();
        this.handler = new Handler();
        this.imgWidth = -1;
        if (list != null) {
            this.mMediaListSelected = list;
        }
        this.mMediaType = i2;
        this.mMediaOptions = cSMediaOptions;
        int screenWidth = CSScreenInfoUtil.screenWidth(cSGalleryActivity) / 4;
        this.mImageViewLayoutParams = new RelativeLayout.LayoutParams(screenWidth, screenWidth);
        this.imgWidth = screenWidth;
        this.mGridViewLayoutParams = new AbsListView.LayoutParams(screenWidth, screenWidth);
        this.holderList = new ArrayList();
    }

    @Override // android.widget.CursorAdapter
    public void bindView(View view, Context context, Cursor cursor) {
        final Uri uri;
        GalleryMediaViewHolder galleryMediaViewHolder = (GalleryMediaViewHolder) view.getTag();
        if (this.mMediaType == 1) {
            uri = CSMediaUtils.getPhotoUri(cursor);
            galleryMediaViewHolder.videoDurtation.setVisibility(View.GONE);
        } else {
            Uri videoUri = CSMediaUtils.getVideoUri(cursor);
            galleryMediaViewHolder.videoDurtation.setVisibility(View.VISIBLE);
            galleryMediaViewHolder.videoDurtation.setText(durationToString(CSMediaUtils.getVideoDurion(cursor)));
            uri = videoUri;
        }
        if (this.imgWidth != -1) {
            Glide.with(context).load(uri).placeholder(R.drawable.shape_gallery_default).listener(new RequestListener<Drawable>() {
                @Override // com.bumptech.glide.request.RequestListener
                public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                    return false;
                }

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onLoadFailed(GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                    if (Objects.equals(uri, CSGalleryMediaAdapter.this.galleryActivity.getTempUri()) || CSGalleryMediaAdapter.errlist.contains(uri)) {
                        return false;
                    }
                    CSGalleryMediaAdapter.errlist.add(uri);
                    return false;
                }
            }).dontAnimate().override(this.imgWidth).into(galleryMediaViewHolder.imageView);
        }
        if (this.mMaxNum == 1) {
            galleryMediaViewHolder.selected.setVisibility(View.GONE);
            return;
        }
        int uriNum = ((CSGalleryActivity) context).getUriNum(uri);
        if (uriNum == 0) {
            galleryMediaViewHolder.selected.setVisibility(View.GONE);
            galleryMediaViewHolder.numSelected.setText("");
            return;
        }
        galleryMediaViewHolder.selected.setVisibility(View.VISIBLE);
        galleryMediaViewHolder.numSelected.setText(String.valueOf(uriNum));
    }

    @Override // android.widget.CursorAdapter
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        GalleryMediaViewHolder galleryMediaViewHolder = new GalleryMediaViewHolder();
        View inflate = View.inflate(context, R.layout.item_gallery_media_cs, null);
        inflate.setPadding(4, 4, 4, 4);
        galleryMediaViewHolder.imageView = (CSPickerImageView) inflate.findViewById(R.id.thumbnail);
        galleryMediaViewHolder.selected = (FrameLayout) inflate.findViewById(R.id.layout_selected);
        galleryMediaViewHolder.numSelected = (TextView) inflate.findViewById(R.id.num_selected);
        galleryMediaViewHolder.videoDurtation = (TextView) inflate.findViewById(R.id.video_durtation);
        galleryMediaViewHolder.container = (RelativeLayout) inflate.findViewById(R.id.container);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        galleryMediaViewHolder.imageView.setLayoutParams(layoutParams);
        galleryMediaViewHolder.selected.setLayoutParams(layoutParams);
        inflate.setLayoutParams(this.mGridViewLayoutParams);
        inflate.setTag(galleryMediaViewHolder);
        this.holderList.add(galleryMediaViewHolder);
        return inflate;
    }

    public boolean hasSelected() {
        return this.mMediaListSelected.size() > 0;
    }

    public boolean isSelected(Uri uri) {
        if (uri == null) {
            return false;
        }
        for (CSMediaItem cSMediaItem : this.mMediaListSelected) {
            if (cSMediaItem.getUriOrigin().equals(uri)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSelected(CSMediaItem cSMediaItem) {
        return this.mMediaListSelected.contains(cSMediaItem);
    }

    public void setMediaSelected(CSMediaItem cSMediaItem) {
        syncMediaSelectedAsOptions();
        if (this.mMediaListSelected.contains(cSMediaItem)) {
            return;
        }
        this.mMediaListSelected.add(cSMediaItem);
    }

    public void updateMediaSelected(CSMediaItem cSMediaItem, CSPickerImageView cSPickerImageView) {
        if (this.mMediaListSelected.contains(cSMediaItem)) {
            this.mMediaListSelected.remove(cSMediaItem);
            cSPickerImageView.setSelected(false);
            this.mPickerImageViewSelected.remove(cSPickerImageView);
            for (CSPickerImageView cSPickerImageView2 : this.mPickerImageViewSelected) {
                Uri uri = cSPickerImageView2.getUri();
                int i = 1;
                for (CSMediaItem cSMediaItem2 : this.mMediaListSelected) {
                    if (cSMediaItem2.getUriOrigin().equals(uri)) {
                        cSPickerImageView2.setNumber(i);
                        cSPickerImageView2.invalidate();
                    }
                    i++;
                }
            }
        } else if (this.mMediaListSelected.size() < this.mMaxNum) {
            if (syncMediaSelectedAsOptions()) {
                for (CSPickerImageView cSPickerImageView3 : this.mPickerImageViewSelected) {
                    cSPickerImageView3.setSelected(false);
                }
                this.mPickerImageViewSelected.clear();
            }
            this.mMediaListSelected.add(cSMediaItem);
            cSPickerImageView.setSelected(true);
            this.mPickerImageViewSelected.add(cSPickerImageView);
        } else if (this.mMediaListSelected.size() >= this.mMaxNum) {
            this.handler.post(new Runnable() { // from class: com.magic.video.editor.effect.gallery.view.-$$Lambda$CSGalleryMediaAdapter$_6Yo31jAIdV0DrOYhL_jyTwEwEU
                @Override // java.lang.Runnable
                public final void run() {
                    CSGalleryMediaAdapter.this.lambda$updateMediaSelected$0$CSGalleryMediaAdapter();
                }
            });
        }
        cSPickerImageView.setNumber(this.mMediaListSelected.indexOf(cSMediaItem) + 1);
        cSPickerImageView.setUri(cSMediaItem.getUriOrigin());
        cSPickerImageView.invalidate();
    }

    public /* synthetic */ void lambda$updateMediaSelected$0$CSGalleryMediaAdapter() {
        Toast.makeText(this.galleryActivity, "Please select " + this.mMaxNum + " photo only.", Toast.LENGTH_SHORT).show();
    }

    public List<CSMediaItem> getMediaSelectedList() {
        return this.mMediaListSelected;
    }

    public void setMediaSelectedList(List<CSMediaItem> list) {
        this.mMediaListSelected = list;
    }

    private boolean syncMediaSelectedAsOptions() {
        int i = this.mMediaType;
        if (i == 1) {
            if (this.mMediaOptions.canSelectMultiPhoto()) {
                return false;
            }
            this.mMediaListSelected.clear();
            return true;
        } else if (i == 2 && !this.mMediaOptions.canSelectMultiVideo()) {
            this.mMediaListSelected.clear();
            return true;
        } else {
            return false;
        }
    }

    public void setMediaType(int i) {
        this.mMediaType = i;
    }

    public void setMaxNum(int i) {
        this.mMaxNum = i;
    }

    public int getMaxNum() {
        return this.mMaxNum;
    }

    public void setItemHeight(int i) {
        if (i != this.mItemHeight) {
            this.mItemHeight = i;
            this.mImageViewLayoutParams.height = i;
            this.mImageViewLayoutParams.width = i;
            notifyDataSetChanged();
        }
    }

    @Override // android.widget.CursorAdapter, android.widget.Adapter
    public int getCount() {
        if (getCursor() == null || !getCursor().isClosed()) {
            return super.getCount();
        }
        return 0;
    }

    @Override // android.widget.AbsListView.RecyclerListener
    public void onMovedToScrapHeap(View view) {
        this.mPickerImageViewSelected.remove(view.findViewById(R.id.thumbnail));
    }

    public void release() {
        this.mPickerImageViewSelected.clear();
        for (GalleryMediaViewHolder galleryMediaViewHolder : this.holderList) {
            galleryMediaViewHolder.imageView.release();
        }
        this.holderList.clear();
    }

    public static String durationToString(int i) {
        String num;
        String num2;
        int i2 = i / 1000;
        int i3 = i2 / 60;
        int i4 = i2 - (i3 * 60);
        if (i4 == 0) {
            i4 = 1;
        }
        if (i3 / 10 < 1) {
            num = Integer.toString(0) + Integer.toString(i3);
        } else {
            num = Integer.toString(i3);
        }
        if (i4 / 10 < 1) {
            num2 = Integer.toString(0) + Integer.toString(i4);
        } else {
            num2 = Integer.toString(i4);
        }
        return num + ":" + num2;
    }
}
