package com.magic.video.editor.effect.gallery.view;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.magic.video.editor.effect.cut.utils.CSScreenInfoUtil;
import com.magic.video.editor.effect.gallery.model.CSMediaItem;
import com.magic.video.editor.effect.gallery.model.CSMediaOptions;
import com.magic.video.editor.effect.gallery.model.CSMediaUtils;
import com.magic.video.editor.effect.gallery.present.CSGalleryPagePresent;
import com.magic.video.editor.effect.gallery.present.CSGalleryPagePresentImpl;
import com.sky.testproject.R;

/* loaded from: classes2.dex */
public class CSGalleryPager extends FrameLayout implements AdapterView.OnItemClickListener, CSGalleryPagerView {
    private CSGalleryPagePresent galleryPagePresent;
    private boolean isCreate;
    private GridView mGridView;
    private int mMaxNum;
    private CSGalleryMediaAdapter mMediaAdapter;
    private CSMediaOptions mMediaOptions;
    private int mMediaType;
    private int mPhotoSize;
    private int mPhotoSpacing;
    private String selectFolder;

    public static CSGalleryPager newInstance(CSGalleryActivity cSGalleryActivity, CSMediaOptions cSMediaOptions, String str, int i) {
        CSGalleryPager cSGalleryPager = new CSGalleryPager(cSGalleryActivity);
        cSGalleryPager.mMaxNum = i;
        cSGalleryPager.mMediaOptions = cSMediaOptions;
        if (!str.equals("all")) {
            cSGalleryPager.selectFolder = str;
        }
        if (cSMediaOptions.canSelectPhotoAndVideo() || cSMediaOptions.canSelectPhoto()) {
            cSGalleryPager.mMediaType = 1;
        } else {
            cSGalleryPager.mMediaType = 2;
        }
        cSGalleryPager.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        return cSGalleryPager;
    }

    public CSGalleryPager(Context context) {
        super(context);
        this.isCreate = true;
        init();
    }

    public CSGalleryPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isCreate = true;
        init();
    }

    public CSGalleryPager(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isCreate = true;
        init();
    }

    public void init() {
        this.galleryPagePresent = new CSGalleryPagePresentImpl(this);
        this.mPhotoSize = CSScreenInfoUtil.dip2px(getContext(), 100.0f);
        this.mPhotoSpacing = 0;
        initView(getActivity().getLayoutInflater().inflate(R.layout.fragment_gallery_cs, (ViewGroup) this, true));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mMediaType == 1) {
            this.galleryPagePresent.requestPhotos(true ^ this.isCreate);
        } else {
            this.galleryPagePresent.requestVideos(true ^ this.isCreate);
        }
        this.isCreate = false;
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [android.widget.Adapter] */
    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        Uri videoUri;
        View findViewById = view.findViewById(R.id.layout_selected);
        TextView textView = (TextView) view.findViewById(R.id.num_selected);
        Object item = adapterView.getAdapter().getItem(i);
        if (item instanceof Cursor) {
            if (this.mMediaType == 1) {
                videoUri = CSMediaUtils.getPhotoUri((Cursor) item);
            } else {
                videoUri = CSMediaUtils.getVideoUri((Cursor) item);
            }
            this.mMediaAdapter.setMediaSelected(new CSMediaItem(this.mMediaType, videoUri));
            if (getActivity().isCanSelected()) {
                textView.setText(String.valueOf(getActivity().getUriNum(videoUri) + 1));
                findViewById.setVisibility(View.VISIBLE);
            }
            if (this.mMaxNum == 1) {
                findViewById.setVisibility(View.GONE);
            }
            if (CSGalleryMediaAdapter.errlist.contains(videoUri)) {
                Toast.makeText(getActivity(), getActivity().getText(R.string.loadPicFailure), 0).show();
                findViewById.setVisibility(View.GONE);
                return;
            }
            getActivity().addimg(videoUri);
        }
    }

    @Override // com.magic.video.editor.effect.gallery.view.CSGalleryPagerView
    public void switchToData(Cursor cursor) {
        this.mGridView.setVisibility(View.VISIBLE);
        CSGalleryMediaAdapter cSGalleryMediaAdapter = this.mMediaAdapter;
        if (cSGalleryMediaAdapter == null) {
            CSGalleryMediaAdapter cSGalleryMediaAdapter2 = new CSGalleryMediaAdapter(getActivity(), cursor, 0, this.mMediaType, this.mMediaOptions);
            this.mMediaAdapter = cSGalleryMediaAdapter2;
            cSGalleryMediaAdapter2.setMaxNum(this.mMaxNum);
        } else {
            cSGalleryMediaAdapter.setMediaType(this.mMediaType);
            this.mMediaAdapter.swapCursor(cursor);
        }
        if (this.mGridView.getAdapter() == null) {
            this.mGridView.setAdapter((ListAdapter) this.mMediaAdapter);
        }
    }

    @Override // com.magic.video.editor.effect.gallery.view.CSGalleryPagerView
    public void switchToError() {
        this.mGridView.setVisibility(View.GONE);
    }

    @Override // com.magic.video.editor.effect.gallery.view.CSGalleryPagerView
    public void swapCursor(Cursor cursor) {
        CSGalleryMediaAdapter cSGalleryMediaAdapter = this.mMediaAdapter;
        if (cSGalleryMediaAdapter != null) {
            cSGalleryMediaAdapter.swapCursor(cursor);
        }
    }

    @Override // com.magic.video.editor.effect.gallery.view.CSGalleryPagerView
    public CSGalleryActivity getActivity() {
        if (getContext() instanceof CSGalleryActivity) {
            return (CSGalleryActivity) getContext();
        }
        return null;
    }

    @Override // com.magic.video.editor.effect.gallery.view.CSGalleryPagerView
    public String getSelectFolder() {
        return this.selectFolder;
    }

    private void initView(View view) {
        GridView gridView = (GridView) view.findViewById(R.id.grid);
        this.mGridView = gridView;
        gridView.setOnItemClickListener(this);
        this.mGridView.setOnTouchListener(new OnTouchListener() { // from class: com.magic.video.editor.effect.gallery.view.-$$Lambda$CSGalleryPager$cCOj1yFJqpaMKpAfRS-E_Y1rV9w
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return CSGalleryPager.this.lambda$initView$0$CSGalleryPager(view2, motionEvent);
            }
        });
        this.mGridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.magic.video.editor.effect.gallery.view.-$$Lambda$CSGalleryPager$tJn7Mi96U7_8chouMB28ytxHp5U
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                CSGalleryPager.this.lambda$initView$1$CSGalleryPager();
            }
        });
    }

    public /* synthetic */ boolean lambda$initView$0$CSGalleryPager(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            getActivity().cover(false, null);
        }
        return false;
    }

    public /* synthetic */ void lambda$initView$1$CSGalleryPager() {
        int floor;
        CSGalleryMediaAdapter cSGalleryMediaAdapter = this.mMediaAdapter;
        if (cSGalleryMediaAdapter == null || cSGalleryMediaAdapter.getMaxNum() != 0 || (floor = (int) Math.floor((this.mGridView.getWidth() * 1.0f) / (this.mPhotoSize + this.mPhotoSpacing))) <= 0) {
            return;
        }
        int width = (this.mGridView.getWidth() / floor) - this.mPhotoSpacing;
        this.mMediaAdapter.setMaxNum(floor);
        this.mMediaAdapter.setItemHeight(width);
    }

    public void onDestroy() {
        this.galleryPagePresent.destroy();
        CSGalleryMediaAdapter cSGalleryMediaAdapter = this.mMediaAdapter;
        if (cSGalleryMediaAdapter != null) {
            cSGalleryMediaAdapter.release();
        }
    }

    public void notifyMediaAdapterData() {
        CSGalleryMediaAdapter cSGalleryMediaAdapter = this.mMediaAdapter;
        if (cSGalleryMediaAdapter != null) {
            cSGalleryMediaAdapter.notifyDataSetChanged();
        }
    }
}
