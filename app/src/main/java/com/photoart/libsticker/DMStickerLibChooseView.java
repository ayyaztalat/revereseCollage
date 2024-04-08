package com.photoart.libsticker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import com.photoart.libsticker.sticker.DMStickerManager;
import com.photoart.libsticker.sticker.DMStickersGridAdapter;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes2.dex */
public class DMStickerLibChooseView extends FrameLayout {
    private DMStickerManager.EStickerType crrentSelectType;
    private ImageView img_imoji_1;
    private ImageView img_imoji_2;
    private ImageView img_imoji_3;
    private ImageView img_imoji_4;
    private ImageView img_imoji_5;
    private ImageView img_imoji_6;
    private DMStickersGridAdapter mAdapter;
    private GridView mGridView;
    private OnStickerChooseListener mListener;

    /* loaded from: classes2.dex */
    public interface OnStickerChooseListener {
        void onClose();

        void onStickerChoose(DMWBRes dMWBRes);
    }

    public DMStickerLibChooseView(Context context) {
        super(context);
        init(context);
    }

    public DMStickerLibChooseView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public void setOnStickerChooseListener(OnStickerChooseListener onStickerChooseListener) {
        this.mListener = onStickerChooseListener;
    }

    private void init(Context context) {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_sticker_lib_choose_view, (ViewGroup) this, true);
        findViewById(R.id.layout_bg).getBackground().setAlpha(95);
        ImageView imageView = (ImageView) findViewById(R.id.img_imoji_1);
        this.img_imoji_1 = imageView;
        imageView.setImageResource(R.drawable.dm_emoji_one_select);
        this.mGridView = (GridView) findViewById(R.id.sticker_gridView);
        DMStickersGridAdapter dMStickersGridAdapter = new DMStickersGridAdapter();
        this.mAdapter = dMStickersGridAdapter;
        dMStickersGridAdapter.setContext(getContext());
        DMStickerManager dMStickerManager = new DMStickerManager(getContext(), DMStickerManager.EStickerType.STICKER1);
        this.crrentSelectType = DMStickerManager.EStickerType.STICKER1;
        this.mAdapter.initData(dMStickerManager);
        this.mGridView.setAdapter((ListAdapter) this.mAdapter);
        findViewById(R.id.vChooseStickerBack).setOnClickListener(new OnClickListener() { // from class: com.photoart.libsticker.DMStickerLibChooseView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DMStickerLibChooseView.this.mListener != null) {
                    DMStickerLibChooseView.this.mListener.onClose();
                }
            }
        });
        this.mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.photoart.libsticker.DMStickerLibChooseView.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                DMWBImageRes dMWBImageRes = (DMWBImageRes) DMStickerLibChooseView.this.mAdapter.getItem(i);
                if (DMStickerLibChooseView.this.mListener != null) {
                    DMStickerLibChooseView.this.mListener.onStickerChoose(dMWBImageRes);
                }
            }
        });
        this.img_imoji_1.setOnClickListener(new OnClickListener() { // from class: com.photoart.libsticker.DMStickerLibChooseView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DMStickerLibChooseView.this.crrentSelectType == DMStickerManager.EStickerType.STICKER1) {
                    return;
                }
                DMStickerLibChooseView.this.resetIcons();
                DMStickerLibChooseView.this.img_imoji_1.setImageResource(R.drawable.dm_emoji_one_select);
                DMStickerLibChooseView.this.mGridView.setAdapter((ListAdapter) null);
                if (DMStickerLibChooseView.this.mAdapter != null) {
                    DMStickerLibChooseView.this.mAdapter.clearAll();
                }
                DMStickerLibChooseView.this.mAdapter = null;
                DMStickerManager dMStickerManager2 = new DMStickerManager(DMStickerLibChooseView.this.getContext(), DMStickerManager.EStickerType.STICKER1);
                DMStickerLibChooseView.this.crrentSelectType = DMStickerManager.EStickerType.STICKER1;
                DMStickerLibChooseView.this.mAdapter = new DMStickersGridAdapter();
                DMStickerLibChooseView.this.mAdapter.setContext(DMStickerLibChooseView.this.getContext());
                DMStickerLibChooseView.this.mAdapter.initData(dMStickerManager2);
                DMStickerLibChooseView.this.mGridView.setAdapter((ListAdapter) DMStickerLibChooseView.this.mAdapter);
            }
        });
        ImageView imageView2 = (ImageView) findViewById(R.id.img_imoji_2);
        this.img_imoji_2 = imageView2;
        imageView2.setOnClickListener(new OnClickListener() { // from class: com.photoart.libsticker.DMStickerLibChooseView.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DMStickerLibChooseView.this.crrentSelectType == DMStickerManager.EStickerType.STICKER2) {
                    return;
                }
                DMStickerLibChooseView.this.resetIcons();
                DMStickerLibChooseView.this.img_imoji_2.setImageResource(R.drawable.dm_emoji_two_select);
                DMStickerLibChooseView.this.mGridView.setAdapter((ListAdapter) null);
                if (DMStickerLibChooseView.this.mAdapter != null) {
                    DMStickerLibChooseView.this.mAdapter.clearAll();
                }
                DMStickerLibChooseView.this.mAdapter = null;
                DMStickerManager dMStickerManager2 = new DMStickerManager(DMStickerLibChooseView.this.getContext(), DMStickerManager.EStickerType.STICKER2);
                DMStickerLibChooseView.this.crrentSelectType = DMStickerManager.EStickerType.STICKER2;
                DMStickerLibChooseView.this.mAdapter = new DMStickersGridAdapter();
                DMStickerLibChooseView.this.mAdapter.setContext(DMStickerLibChooseView.this.getContext());
                DMStickerLibChooseView.this.mAdapter.initData(dMStickerManager2);
                DMStickerLibChooseView.this.mGridView.setAdapter((ListAdapter) DMStickerLibChooseView.this.mAdapter);
            }
        });
        ImageView imageView3 = (ImageView) findViewById(R.id.img_imoji_3);
        this.img_imoji_3 = imageView3;
        imageView3.setOnClickListener(new OnClickListener() { // from class: com.photoart.libsticker.DMStickerLibChooseView.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DMStickerLibChooseView.this.crrentSelectType == DMStickerManager.EStickerType.STICKER3) {
                    return;
                }
                DMStickerLibChooseView.this.resetIcons();
                DMStickerLibChooseView.this.img_imoji_3.setImageResource(R.drawable.dm_emoji_three_select);
                DMStickerLibChooseView.this.mGridView.setAdapter((ListAdapter) null);
                if (DMStickerLibChooseView.this.mAdapter != null) {
                    DMStickerLibChooseView.this.mAdapter.clearAll();
                }
                DMStickerLibChooseView.this.mAdapter = null;
                DMStickerManager dMStickerManager2 = new DMStickerManager(DMStickerLibChooseView.this.getContext(), DMStickerManager.EStickerType.STICKER3);
                DMStickerLibChooseView.this.crrentSelectType = DMStickerManager.EStickerType.STICKER3;
                DMStickerLibChooseView.this.mAdapter = new DMStickersGridAdapter();
                DMStickerLibChooseView.this.mAdapter.setContext(DMStickerLibChooseView.this.getContext());
                DMStickerLibChooseView.this.mAdapter.initData(dMStickerManager2);
                DMStickerLibChooseView.this.mGridView.setAdapter((ListAdapter) DMStickerLibChooseView.this.mAdapter);
            }
        });
        ImageView imageView4 = (ImageView) findViewById(R.id.img_imoji_4);
        this.img_imoji_4 = imageView4;
        imageView4.setOnClickListener(new OnClickListener() { // from class: com.photoart.libsticker.DMStickerLibChooseView.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DMStickerLibChooseView.this.crrentSelectType == DMStickerManager.EStickerType.STICKER4) {
                    return;
                }
                DMStickerLibChooseView.this.resetIcons();
                DMStickerLibChooseView.this.img_imoji_4.setImageResource(R.drawable.dm_emoji_four_select);
                DMStickerLibChooseView.this.mGridView.setAdapter((ListAdapter) null);
                if (DMStickerLibChooseView.this.mAdapter != null) {
                    DMStickerLibChooseView.this.mAdapter.clearAll();
                }
                DMStickerLibChooseView.this.mAdapter = null;
                DMStickerManager dMStickerManager2 = new DMStickerManager(DMStickerLibChooseView.this.getContext(), DMStickerManager.EStickerType.STICKER4);
                DMStickerLibChooseView.this.crrentSelectType = DMStickerManager.EStickerType.STICKER4;
                DMStickerLibChooseView.this.mAdapter = new DMStickersGridAdapter();
                DMStickerLibChooseView.this.mAdapter.setContext(DMStickerLibChooseView.this.getContext());
                DMStickerLibChooseView.this.mAdapter.initData(dMStickerManager2);
                DMStickerLibChooseView.this.mGridView.setAdapter((ListAdapter) DMStickerLibChooseView.this.mAdapter);
            }
        });
        ImageView imageView5 = (ImageView) findViewById(R.id.img_imoji_5);
        this.img_imoji_5 = imageView5;
        imageView5.setOnClickListener(new OnClickListener() { // from class: com.photoart.libsticker.DMStickerLibChooseView.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DMStickerLibChooseView.this.crrentSelectType == DMStickerManager.EStickerType.STICKER5) {
                    return;
                }
                DMStickerLibChooseView.this.resetIcons();
                DMStickerLibChooseView.this.img_imoji_5.setImageResource(R.drawable.dm_emoji_five_select);
                DMStickerLibChooseView.this.mGridView.setAdapter((ListAdapter) null);
                if (DMStickerLibChooseView.this.mAdapter != null) {
                    DMStickerLibChooseView.this.mAdapter.clearAll();
                }
                DMStickerLibChooseView.this.mAdapter = null;
                DMStickerManager dMStickerManager2 = new DMStickerManager(DMStickerLibChooseView.this.getContext(), DMStickerManager.EStickerType.STICKER5);
                DMStickerLibChooseView.this.crrentSelectType = DMStickerManager.EStickerType.STICKER5;
                DMStickerLibChooseView.this.mAdapter = new DMStickersGridAdapter();
                DMStickerLibChooseView.this.mAdapter.setContext(DMStickerLibChooseView.this.getContext());
                DMStickerLibChooseView.this.mAdapter.initData(dMStickerManager2);
                DMStickerLibChooseView.this.mGridView.setAdapter((ListAdapter) DMStickerLibChooseView.this.mAdapter);
            }
        });
        ImageView imageView6 = (ImageView) findViewById(R.id.img_imoji_6);
        this.img_imoji_6 = imageView6;
        imageView6.setOnClickListener(new OnClickListener() { // from class: com.photoart.libsticker.DMStickerLibChooseView.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DMStickerLibChooseView.this.crrentSelectType == DMStickerManager.EStickerType.STICKER6) {
                    return;
                }
                DMStickerLibChooseView.this.resetIcons();
                DMStickerLibChooseView.this.img_imoji_6.setImageResource(R.drawable.dm_emoji_six_select);
                DMStickerLibChooseView.this.mGridView.setAdapter((ListAdapter) null);
                if (DMStickerLibChooseView.this.mAdapter != null) {
                    DMStickerLibChooseView.this.mAdapter.clearAll();
                }
                DMStickerLibChooseView.this.mAdapter = null;
                DMStickerManager dMStickerManager2 = new DMStickerManager(DMStickerLibChooseView.this.getContext(), DMStickerManager.EStickerType.STICKER6);
                DMStickerLibChooseView.this.crrentSelectType = DMStickerManager.EStickerType.STICKER6;
                DMStickerLibChooseView.this.mAdapter = new DMStickersGridAdapter();
                DMStickerLibChooseView.this.mAdapter.setContext(DMStickerLibChooseView.this.getContext());
                DMStickerLibChooseView.this.mAdapter.initData(dMStickerManager2);
                DMStickerLibChooseView.this.mGridView.setAdapter((ListAdapter) DMStickerLibChooseView.this.mAdapter);
            }
        });
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ly_group_container);
        if (DMScreenInfoUtil.screenWidthDp(getContext()) > 510) {
            linearLayout.setMinimumWidth(DMScreenInfoUtil.screenWidth(getContext()));
        } else {
            linearLayout.setMinimumWidth(DMScreenInfoUtil.dip2px(getContext(), 510));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetIcons() {
        this.img_imoji_1.setImageResource(R.drawable.dm_emoji_one);
        this.img_imoji_2.setImageResource(R.drawable.dm_emoji_two);
        this.img_imoji_3.setImageResource(R.drawable.dm_emoji_three);
        this.img_imoji_4.setImageResource(R.drawable.dm_emoji_four);
        this.img_imoji_5.setImageResource(R.drawable.dm_emoji_five);
        this.img_imoji_6.setImageResource(R.drawable.dm_emoji_six);
    }

    public void dispose() {
        this.mGridView.setAdapter((ListAdapter) null);
        DMStickersGridAdapter dMStickersGridAdapter = this.mAdapter;
        if (dMStickersGridAdapter != null) {
            dMStickersGridAdapter.clearAll();
        }
        this.mAdapter = null;
    }
}
