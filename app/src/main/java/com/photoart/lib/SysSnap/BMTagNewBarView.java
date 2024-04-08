package com.photoart.lib.SysSnap;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import com.sky.testproject.R;
import com.picspool.instatextview.utils.DMSelectorImageView;

/* loaded from: classes3.dex */
public class BMTagNewBarView extends FrameLayout {
    View btn_keyboard;
    private FrameLayout editLayout;
    private BMEmojiAdapter emojiAdapter;
    private GridView emojiGridView;
    private Handler handler;
    private DMSelectorImageView img_keyboard;
    private DMSelectorImageView img_sticker;
    private FrameLayout listLayout;
    Context mContext;
    private EditText mEditText;
    private InputMethodManager mImm;
    public OnTagNewListener mListener;
    BMStickerImageManager manager;

    /* loaded from: classes3.dex */
    public interface OnTagNewListener {
        void onTagNewBarDoneClicked();

        void onTagNewBarKeyboardClicked();
    }

    public BMTagNewBarView(Context context, EditText editText, InputMethodManager inputMethodManager) {
        super(context);
        this.handler = new Handler();
        this.mContext = context;
        this.mEditText = editText;
        this.mImm = inputMethodManager;
        init(context);
    }

    public BMTagNewBarView(Context context, AttributeSet attributeSet, EditText editText, InputMethodManager inputMethodManager) {
        super(context, attributeSet);
        this.handler = new Handler();
        this.mContext = context;
        this.mEditText = editText;
        this.mImm = inputMethodManager;
        init(context);
    }

    private void init(Context context) {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.bm_view_tool_tag, (ViewGroup) this, true);
        findViewById(R.id.btn_sticker).setOnClickListener(new OnClickListener() { // from class: org.photoart.lib.SysSnap.BMTagNewBarView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (BMTagNewBarView.this.img_sticker.isSelected()) {
                    return;
                }
                BMTagNewBarView.this.invalidViews();
                BMTagNewBarView.this.img_sticker.setSelected(true);
                BMTagNewBarView.this.emojiGridView.setVisibility(View.VISIBLE);
            }
        });
        View findViewById = findViewById(R.id.btn_keyboard);
        this.btn_keyboard = findViewById;
        findViewById.setOnClickListener(new OnClickListener() { // from class: org.photoart.lib.SysSnap.BMTagNewBarView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (BMTagNewBarView.this.img_keyboard.isSelected()) {
                    return;
                }
                BMTagNewBarView.this.invalidViews();
                BMTagNewBarView.this.img_keyboard.setSelected(true);
                if (BMTagNewBarView.this.mListener != null) {
                    BMTagNewBarView.this.mListener.onTagNewBarKeyboardClicked();
                }
            }
        });
        findViewById(R.id.btn_done).setOnClickListener(new OnClickListener() { // from class: org.photoart.lib.SysSnap.BMTagNewBarView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BMTagNewBarView.this.invalidViews();
                if (BMTagNewBarView.this.mListener != null) {
                    BMTagNewBarView.this.mListener.onTagNewBarDoneClicked();
                }
            }
        });
        DMSelectorImageView dMSelectorImageView = (DMSelectorImageView) findViewById(R.id.img_keyboard);
        this.img_keyboard = dMSelectorImageView;
        dMSelectorImageView.setImgPath("text/text_ui/insta_text_key.png");
        this.img_keyboard.setImgPressedPath("text/text_ui/insta_text_key1.png");
        this.img_keyboard.loadImage();
        DMSelectorImageView dMSelectorImageView2 = (DMSelectorImageView) findViewById(R.id.img_sticker);
        this.img_sticker = dMSelectorImageView2;
        dMSelectorImageView2.setImgPath("text/text_ui/text_sticker.png");
        this.img_sticker.setImgPressedPath("text/text_ui/text_sticker_press.png");
        this.img_sticker.loadImage();
        this.emojiGridView = (GridView) findViewById(R.id.emojiGridView);
        this.editLayout = (FrameLayout) findViewById(R.id.edit_layout);
        this.listLayout = (FrameLayout) findViewById(R.id.list_layout);
        this.manager = BMStickerModeManager.getStickerImageManager(this.mContext.getApplicationContext(), BMStickerModeManager.StickerMode.STICKERALL);
        BMEmojiAdapter bMEmojiAdapter = new BMEmojiAdapter(this.mContext, this.mEditText, this.manager);
        this.emojiAdapter = bMEmojiAdapter;
        this.emojiGridView.setAdapter((ListAdapter) bMEmojiAdapter);
        this.emojiGridView.setOnItemClickListener(this.emojiAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidViews() {
        this.emojiGridView.setVisibility(View.GONE);
        this.img_keyboard.setSelected(false);
        this.img_sticker.setSelected(false);
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
        this.handler.post(new Runnable() { // from class: org.photoart.lib.SysSnap.BMTagNewBarView.4
            @Override // java.lang.Runnable
            public void run() {
                BMTagNewBarView.this.editLayout.setLayoutParams(new LinearLayout.LayoutParams(i, i2));
                BMTagNewBarView.this.listLayout.setLayoutParams(new LinearLayout.LayoutParams(i, i3));
            }
        });
    }

    public void dispose() {
        BMEmojiAdapter bMEmojiAdapter = this.emojiAdapter;
        if (bMEmojiAdapter != null) {
            bMEmojiAdapter.dispose();
        }
        this.img_keyboard.releaseImage();
        this.img_sticker.releaseImage();
        this.emojiAdapter = null;
    }
}
