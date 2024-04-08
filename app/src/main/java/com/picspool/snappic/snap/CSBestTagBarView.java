package com.picspool.snappic.snap;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import com.photoart.libsticker.sticker.DMStickerManager;
import com.photoart.libsticker.sticker2.DMStickerModeManager;
import com.photoart.libsticker.sticker2.DMStickerModeManager2;

import com.picspool.instatextview.utils.DMSelectorImageView;
import com.picspool.lib.resource.DMWBRes;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSBestTagBarView extends FrameLayout {
    View btn_keyboard;
    private FrameLayout editLayout;
    private CSBestEmojiAdapter emojiAdapter;
    private GridView emojiGridView;
    private CSBestFrameAdapter frameAdapter;
    private GridView frameGridView;
    CSBestFrameManager frameManager;
    private Handler handler;
    private DMSelectorImageView img_frame;
    private DMSelectorImageView img_keyboard;
    private DMSelectorImageView img_sticker;
    private FrameLayout listLayout;
    Context mContext;
    private EditText mEditText;
    private InputMethodManager mImm;
    public OnTagNewListener mListener;
    DMStickerManager manager;

    /* loaded from: classes.dex */
    public interface OnTagNewListener {
        void onTagFrameClicked(DMWBRes dMWBRes);

        void onTagNewBarDoneClicked();

        void onTagNewBarKeyboardClicked();
    }

    public CSBestTagBarView(Context context, EditText editText, InputMethodManager inputMethodManager) {
        super(context);
        this.handler = new Handler();
        this.mContext = context;
        this.mEditText = editText;
        this.mImm = inputMethodManager;
        init(context);
    }

    public CSBestTagBarView(Context context, AttributeSet attributeSet, EditText editText, InputMethodManager inputMethodManager) {
        super(context, attributeSet);
        this.handler = new Handler();
        this.mContext = context;
        this.mEditText = editText;
        this.mImm = inputMethodManager;
        init(context);
    }

    private void init(Context context) {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_tool_tag, (ViewGroup) this, true);
        findViewById(R.id.btn_sticker).setOnClickListener(new OnClickListener() { // from class: com.picspool.snappic.snap.CSBestTagBarView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSBestTagBarView.this.img_sticker.isSelected()) {
                    return;
                }
                CSBestTagBarView.this.invalidViews();
                CSBestTagBarView.this.img_sticker.setSelected(true);
                CSBestTagBarView.this.emojiGridView.setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.btn_frame).setOnClickListener(new OnClickListener() { // from class: com.picspool.snappic.snap.CSBestTagBarView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSBestTagBarView.this.img_frame.isSelected()) {
                    return;
                }
                CSBestTagBarView.this.invalidViews();
                CSBestTagBarView.this.img_frame.setSelected(true);
                CSBestTagBarView.this.frameGridView.setVisibility(View.VISIBLE);
            }
        });
        View findViewById = findViewById(R.id.btn_keyboard);
        this.btn_keyboard = findViewById;
        findViewById.setOnClickListener(new OnClickListener() { // from class: com.picspool.snappic.snap.CSBestTagBarView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSBestTagBarView.this.img_keyboard.isSelected()) {
                    return;
                }
                CSBestTagBarView.this.invalidViews();
                CSBestTagBarView.this.img_keyboard.setSelected(true);
                if (CSBestTagBarView.this.mListener != null) {
                    CSBestTagBarView.this.mListener.onTagNewBarKeyboardClicked();
                }
            }
        });
        findViewById(R.id.btn_done).setOnClickListener(new OnClickListener() { // from class: com.picspool.snappic.snap.CSBestTagBarView.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CSBestTagBarView.this.invalidViews();
                if (CSBestTagBarView.this.mListener != null) {
                    CSBestTagBarView.this.mListener.onTagNewBarDoneClicked();
                }
            }
        });
        DMSelectorImageView dMSelectorImageView = (DMSelectorImageView) findViewById(R.id.img_keyboard);
        this.img_keyboard = dMSelectorImageView;
        dMSelectorImageView.setImgPath("textui/text_key.png");
        this.img_keyboard.setImgPressedPath("textui/text_key_press.png");
        this.img_keyboard.loadImage();
        DMSelectorImageView dMSelectorImageView2 = (DMSelectorImageView) findViewById(R.id.img_sticker);
        this.img_sticker = dMSelectorImageView2;
        dMSelectorImageView2.setImgPath("textui/text_sticker.png");
        this.img_sticker.setImgPressedPath("textui/text_sticker_press.png");
        this.img_sticker.loadImage();
        DMSelectorImageView dMSelectorImageView3 = (DMSelectorImageView) findViewById(R.id.img_frame);
        this.img_frame = dMSelectorImageView3;
        dMSelectorImageView3.setImgPath("textui/text_frame.png");
        this.img_frame.setImgPressedPath("textui/text_frame_press.png");
        this.img_frame.loadImage();
        DMSelectorImageView dMSelectorImageView4 = (DMSelectorImageView) findViewById(R.id.img_done);
        dMSelectorImageView4.setImgPath("textui/text_done.png");
        dMSelectorImageView4.setImgPressedPath("textui/text_done.png");
        dMSelectorImageView4.loadImage();
        this.emojiGridView = (GridView) findViewById(R.id.emojiGridView);
        this.frameGridView = (GridView) findViewById(R.id.frameGridView);
        this.editLayout = (FrameLayout) findViewById(R.id.edit_layout);
        this.listLayout = (FrameLayout) findViewById(R.id.list_layout);
        this.manager = DMStickerModeManager2.getStickerImageManager(this.mContext, DMStickerModeManager.StickerMode.STICKERALL);
        CSBestEmojiAdapter cSBestEmojiAdapter = new CSBestEmojiAdapter(this.mContext, this.mEditText, this.manager);
        this.emojiAdapter = cSBestEmojiAdapter;
        this.emojiGridView.setAdapter((ListAdapter) cSBestEmojiAdapter);
        this.emojiGridView.setOnItemClickListener(this.emojiAdapter);
        this.frameManager = CSBestFrameManager.getSingletonInstance(this.mContext);
        CSBestFrameAdapter cSBestFrameAdapter = new CSBestFrameAdapter(this.mContext, this.frameManager);
        this.frameAdapter = cSBestFrameAdapter;
        this.frameGridView.setAdapter((ListAdapter) cSBestFrameAdapter);
        this.frameGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.picspool.snappic.snap.CSBestTagBarView.5
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (CSBestTagBarView.this.mListener != null) {
                    CSBestTagBarView.this.mListener.onTagFrameClicked(CSBestTagBarView.this.frameManager.getRes(i));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidViews() {
        this.emojiGridView.setVisibility(View.GONE);
        this.frameGridView.setVisibility(View.GONE);
        this.img_keyboard.setSelected(false);
        this.img_sticker.setSelected(false);
        this.img_frame.setSelected(false);
        InputMethodManager inputMethodManager = this.mImm;
        if (inputMethodManager == null || !inputMethodManager.isActive()) {
            return;
        }
        this.mImm.hideSoftInputFromWindow(this.mEditText.getWindowToken(), 0);
    }

    public void setOnTagNewListenerListener(OnTagNewListener onTagNewListener) {
        this.mListener = onTagNewListener;
    }

    public void initBegin() {
        this.btn_keyboard.performClick();
    }

    public void resetLayoutParam(final int i, final int i2, final int i3) {
        this.handler.post(new Runnable() { // from class: com.picspool.snappic.snap.CSBestTagBarView.6
            @Override // java.lang.Runnable
            public void run() {
                CSBestTagBarView.this.editLayout.setLayoutParams(new LinearLayout.LayoutParams(i, i2));
                CSBestTagBarView.this.listLayout.setLayoutParams(new LinearLayout.LayoutParams(i, i3));
            }
        });
    }

    public void dispose() {
        CSBestEmojiAdapter cSBestEmojiAdapter = this.emojiAdapter;
        if (cSBestEmojiAdapter != null) {
            cSBestEmojiAdapter.dispose();
        }
        CSBestFrameAdapter cSBestFrameAdapter = this.frameAdapter;
        if (cSBestFrameAdapter != null) {
            cSBestFrameAdapter.dispose();
        }
        this.img_keyboard.releaseImage();
        this.img_sticker.releaseImage();
        this.img_frame.releaseImage();
        this.emojiAdapter = null;
        this.frameAdapter = null;
    }
}
