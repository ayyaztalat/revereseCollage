package com.picspool.instatextview.edit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.picspool.instatextview.resource.manager.DMDefaultEditTextHeightManager;
import com.picspool.instatextview.resource.manager.DMTextBgManager;
import com.picspool.instatextview.resource.manager.DMTextBgRes;
import com.picspool.instatextview.resource.manager.DMTextEmojiManager;
import com.picspool.instatextview.textview.DMInstaTextView3;
import com.picspool.instatextview.textview.DM_BasicColorView3;
import com.picspool.instatextview.textview.DM_BasicShadowView3;
import com.picspool.instatextview.textview.DM_BasicStokeView3;
import com.picspool.instatextview.utils.DMSelectorImageView;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.picspool.lib.text.DMTextDrawer;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMEditTextView3 extends FrameLayout {
    private LinearLayout basicButton;
    private DMSelectorImageView basicImage;
    private LinearLayout bubbleButton;
    private DMSelectorImageView bubbleImage;
    private RelativeLayout bubbleLayout;
    private DMSelectorImageView colorImage;
    private DM_BasicColorView3 colorView;
    private FrameLayout editLayout;
    private DM_TextFixedView3 editText;
    private DM_TextEmojiAdapter3 emojiAdapter;
    private LinearLayout emojiButton;
    private GridView emojiGridView;
    private DMSelectorImageView emojiImage;
    private FrameLayout emoji_list_layout;
    private LinearLayout finishButton;
    private DMSelectorImageView finishImage;
    private DM_FontAdapter3 fontAdapter;
    private ListView fontList;
    private Handler handler;
    private DM_TextImageBgAdapter3 imageBgAdapter;
    private int imageBgHeight;
    private int imageBgTopMargin;
    private int imageBgWidth;
    private LinearLayout imagebgButton;
    private GridView imagebgGridView;
    private DMSelectorImageView imagebgImage;
    private FrameLayout imagebg_list_layout;
    private InputMethodManager imm;
    private DMInstaTextView3 instaTextView;
    private boolean isHasImageBg;
    private LinearLayout keyButton;
    private DMSelectorImageView keyImage;
    private LinearLayout linear_layout;
    private int linear_layout_height;
    private FrameLayout listLayout;
    private DMTextBgRes mBMTextBgRes;
    private Context mContext;
    private boolean onCreateFlag;
    View rootLayout;
    private SeekBar seekBar;
    private DMSelectorImageView shadowImage;
    private DM_BasicShadowView3 shadowView;
    private boolean showInputFlag;
    private DMSelectorImageView stokeImage;
    private DM_BasicStokeView3 stokeView;
    private DMTextEmojiManager textEmojiManager;
    private FrameLayout textbasicLayout;
    private int topViewHeight;
    private LinearLayout typefaceButton;
    private DMSelectorImageView typefaceImage;

    public DMEditTextView3(Context context) {
        super(context);
        this.handler = new Handler();
        this.showInputFlag = true;
        this.topViewHeight = 0;
        this.linear_layout_height = 0;
        this.isHasImageBg = false;
        this.imageBgWidth = 0;
        this.imageBgHeight = 0;
        this.imageBgTopMargin = 0;
        this.mBMTextBgRes = null;
        this.onCreateFlag = false;
        this.mContext = context;
        iniView();
    }

    public DMEditTextView3(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.handler = new Handler();
        this.showInputFlag = true;
        this.topViewHeight = 0;
        this.linear_layout_height = 0;
        this.isHasImageBg = false;
        this.imageBgWidth = 0;
        this.imageBgHeight = 0;
        this.imageBgTopMargin = 0;
        this.mBMTextBgRes = null;
        this.onCreateFlag = false;
        this.mContext = context;
        iniView();
    }

    public DMEditTextView3(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.handler = new Handler();
        this.showInputFlag = true;
        this.topViewHeight = 0;
        this.linear_layout_height = 0;
        this.isHasImageBg = false;
        this.imageBgWidth = 0;
        this.imageBgHeight = 0;
        this.imageBgTopMargin = 0;
        this.mBMTextBgRes = null;
        this.onCreateFlag = false;
        this.mContext = context;
        iniView();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    private void iniView() {
        View inflate = ((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_text_edit_text_view3, (ViewGroup) null);
        this.rootLayout = inflate;
        this.editLayout = (FrameLayout) inflate.findViewById(R.id.edit_layout);
        this.listLayout = (FrameLayout) this.rootLayout.findViewById(R.id.list_layout);
        this.emoji_list_layout = (FrameLayout) this.rootLayout.findViewById(R.id.emoji_list_layout);
        this.imagebg_list_layout = (FrameLayout) this.rootLayout.findViewById(R.id.imagebg_list_layout);
        this.linear_layout = (LinearLayout) this.rootLayout.findViewById(R.id.linear_layout);
        this.keyButton = (LinearLayout) this.rootLayout.findViewById(R.id.bottom_key);
        this.emojiButton = (LinearLayout) this.rootLayout.findViewById(R.id.bottom_emoji);
        this.imagebgButton = (LinearLayout) this.rootLayout.findViewById(R.id.bottom_bg_image);
        this.typefaceButton = (LinearLayout) this.rootLayout.findViewById(R.id.bottom_typeface);
        this.bubbleButton = (LinearLayout) this.rootLayout.findViewById(R.id.bottom_bubble);
        this.basicButton = (LinearLayout) this.rootLayout.findViewById(R.id.bottom_basic);
        this.finishButton = (LinearLayout) this.rootLayout.findViewById(R.id.bottom_finish);
        this.fontList = (ListView) this.rootLayout.findViewById(R.id.font_list);
        this.editText = (DM_TextFixedView3) this.rootLayout.findViewById(R.id.editText1);
        this.emojiGridView = (GridView) this.rootLayout.findViewById(R.id.emojiGridView);
        this.imagebgGridView = (GridView) this.rootLayout.findViewById(R.id.imagebgGridView);
        DMSelectorImageView dMSelectorImageView = (DMSelectorImageView) this.rootLayout.findViewById(R.id.image_key);
        this.keyImage = dMSelectorImageView;
        dMSelectorImageView.setImgPath("text/text_ui/insta_text_key.png");
        this.keyImage.setImgPressedPath("text/text_ui/insta_text_key1.png");
        this.keyImage.loadImage();
        DMSelectorImageView dMSelectorImageView2 = (DMSelectorImageView) this.rootLayout.findViewById(R.id.image_emoji);
        this.emojiImage = dMSelectorImageView2;
        dMSelectorImageView2.setImgPath("text/text_ui/insta_text_emoji.png");
        this.emojiImage.setImgPressedPath("text/text_ui/insta_text_emoji1.png");
        this.emojiImage.loadImage();
        DMSelectorImageView dMSelectorImageView3 = (DMSelectorImageView) this.rootLayout.findViewById(R.id.image_typeface);
        this.typefaceImage = dMSelectorImageView3;
        dMSelectorImageView3.setImgPath("text/text_ui/insta_text_typeface.png");
        this.typefaceImage.setImgPressedPath("text/text_ui/insta_text_typeface1.png");
        this.typefaceImage.loadImage();
        DMSelectorImageView dMSelectorImageView4 = (DMSelectorImageView) this.rootLayout.findViewById(R.id.image_bubble);
        this.bubbleImage = dMSelectorImageView4;
        dMSelectorImageView4.setImgPath("text/text_ui/insta_text_bubble.png");
        this.bubbleImage.setImgPressedPath("text/text_ui/insta_text_bubble1.png");
        this.bubbleImage.loadImage();
        DMSelectorImageView dMSelectorImageView5 = (DMSelectorImageView) this.rootLayout.findViewById(R.id.image_basic);
        this.basicImage = dMSelectorImageView5;
        dMSelectorImageView5.setImgPath("text/text_ui/insta_text_basic.png");
        this.basicImage.setImgPressedPath("text/text_ui/insta_text_basic1.png");
        this.basicImage.loadImage();
        DMSelectorImageView dMSelectorImageView6 = (DMSelectorImageView) this.rootLayout.findViewById(R.id.image_bg_image);
        this.imagebgImage = dMSelectorImageView6;
        dMSelectorImageView6.setImgPath("text/text_ui/insta_text_bubble.png");
        this.imagebgImage.setImgPressedPath("text/text_ui/insta_text_bubble1.png");
        this.imagebgImage.loadImage();
        DMSelectorImageView dMSelectorImageView7 = (DMSelectorImageView) this.rootLayout.findViewById(R.id.image_finish);
        this.finishImage = dMSelectorImageView7;
        dMSelectorImageView7.setImgPath("text/text_ui/insta_text_done.png");
        this.finishImage.setImgPressedPath("text/text_ui/insta_text_done1.png");
        this.finishImage.loadImage();
        this.finishImage.setTouchFlag(false);
        this.imm = (InputMethodManager) this.editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        this.keyButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.edit.DMEditTextView3.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DMEditTextView3.this.keyImage.isSelected()) {
                    return;
                }
                DMEditTextView3.this.invalidViews();
                DMEditTextView3.this.imm.showSoftInput(DMEditTextView3.this.editText, 0);
                DMEditTextView3.this.showInputFlag = true;
                DMEditTextView3.this.keyImage.setSelected(true);
                if (DMEditTextView3.this.editText.isShowCaretFlag()) {
                    return;
                }
                DMEditTextView3.this.editText.setShowCaretFlag(true);
            }
        });
        this.emojiButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.edit.DMEditTextView3.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DMEditTextView3.this.emojiImage.isSelected()) {
                    return;
                }
                DMEditTextView3.this.invalidViews();
                DMEditTextView3.this.emoji_list_layout.setVisibility(0);
                DMEditTextView3.this.emojiImage.setSelected(true);
                if (DMEditTextView3.this.editText.isShowCaretFlag()) {
                    DMEditTextView3.this.editText.setShowCaretFlag(false);
                }
            }
        });
        this.imagebgButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.edit.DMEditTextView3.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DMEditTextView3.this.imagebgImage.isSelected()) {
                    return;
                }
                DMEditTextView3.this.invalidViews();
                DMEditTextView3.this.imagebg_list_layout.setVisibility(0);
                DMEditTextView3.this.imagebgImage.setSelected(true);
                if (DMEditTextView3.this.editText.isShowCaretFlag()) {
                    DMEditTextView3.this.editText.setShowCaretFlag(false);
                }
            }
        });
        this.typefaceButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.edit.DMEditTextView3.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DMEditTextView3.this.typefaceImage.isSelected()) {
                    return;
                }
                DMEditTextView3.this.invalidViews();
                DMEditTextView3.this.fontList.setVisibility(0);
                DMEditTextView3.this.typefaceImage.setSelected(true);
                if (DMEditTextView3.this.editText.isShowCaretFlag()) {
                    DMEditTextView3.this.editText.setShowCaretFlag(false);
                }
            }
        });
        this.finishButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.edit.DMEditTextView3.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMEditTextView3.this.invalidViews();
                DMEditTextView3 dMEditTextView3 = DMEditTextView3.this;
                dMEditTextView3.finishEditText(dMEditTextView3.editText.getTextDrawer());
            }
        });
        this.basicButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.edit.DMEditTextView3.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DMEditTextView3.this.basicImage.isSelected()) {
                    return;
                }
                DMEditTextView3.this.invalidViews();
                DMEditTextView3 dMEditTextView3 = DMEditTextView3.this;
                dMEditTextView3.showView(dMEditTextView3.textbasicLayout);
                DMEditTextView3.this.basicImage.setSelected(true);
                if (DMEditTextView3.this.editText.isShowCaretFlag()) {
                    DMEditTextView3.this.editText.setShowCaretFlag(false);
                }
            }
        });
        this.bubbleButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.edit.DMEditTextView3.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DMEditTextView3.this.bubbleImage.isSelected()) {
                    return;
                }
                DMEditTextView3.this.invalidViews();
                DMEditTextView3.this.bubbleLayout.setVisibility(0);
                DMEditTextView3.this.bubbleImage.setSelected(true);
                if (DMEditTextView3.this.editText.isShowCaretFlag()) {
                    DMEditTextView3.this.editText.setShowCaretFlag(false);
                }
            }
        });
        this.editLayout.setLayoutParams(new LinearLayout.LayoutParams(DMScreenInfoUtil.screenWidth(this.mContext), DMScreenInfoUtil.screenHeight(this.mContext)));
        this.isHasImageBg = false;
        iniFont();
        iniBgImage();
        iniBase();
        iniackground();
        iniEmoji();
        addView(this.rootLayout);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showView(final View view) {
        this.handler.post(new Runnable() { // from class: com.picspool.instatextview.edit.DMEditTextView3.8
            @Override // java.lang.Runnable
            public void run() {
                view.setVisibility(0);
            }
        });
    }

    private void iniEmoji() {
        this.textEmojiManager = DMTextEmojiManager.getInstance(this.mContext);
        DM_TextEmojiAdapter3 dM_TextEmojiAdapter3 = new DM_TextEmojiAdapter3(this.mContext, this.textEmojiManager);
        this.emojiAdapter = dM_TextEmojiAdapter3;
        dM_TextEmojiAdapter3.setEditText(this.editText);
        this.emojiGridView.setAdapter((ListAdapter) this.emojiAdapter);
        this.emojiGridView.setOnItemClickListener(this.emojiAdapter);
    }

    private void iniBgImage() {
        DM_TextImageBgAdapter3 dM_TextImageBgAdapter3 = new DM_TextImageBgAdapter3(this.mContext, this.editText, this);
        this.imageBgAdapter = dM_TextImageBgAdapter3;
        this.imagebgGridView.setAdapter((ListAdapter) dM_TextImageBgAdapter3);
        this.imagebgGridView.setOnItemClickListener(this.imageBgAdapter);
    }

    private void iniFont() {
        DM_FontAdapter3 dM_FontAdapter3 = new DM_FontAdapter3(this.mContext);
        this.fontAdapter = dM_FontAdapter3;
        dM_FontAdapter3.setEditText(this.editText);
        this.fontList.setAdapter((ListAdapter) this.fontAdapter);
    }

    private void iniBase() {
        this.textbasicLayout = (FrameLayout) this.rootLayout.findViewById(R.id.text_basic_layout);
        final LinearLayout linearLayout = (LinearLayout) this.rootLayout.findViewById(R.id.basic_shadow);
        final LinearLayout linearLayout2 = (LinearLayout) this.rootLayout.findViewById(R.id.basic_color);
        final LinearLayout linearLayout3 = (LinearLayout) this.rootLayout.findViewById(R.id.basic_stoke);
        this.shadowView = (DM_BasicShadowView3) this.rootLayout.findViewById(R.id.basic_shadow_layout);
        this.colorView = (DM_BasicColorView3) this.rootLayout.findViewById(R.id.basic_color_layout);
        this.stokeView = (DM_BasicStokeView3) this.rootLayout.findViewById(R.id.basic_stoke_layout);
        this.shadowView.setTextFixedView(this.editText);
        this.shadowImage = (DMSelectorImageView) this.rootLayout.findViewById(R.id.img_text_basic_shadow);
        this.colorImage = (DMSelectorImageView) this.rootLayout.findViewById(R.id.img_text_basic_color);
        this.stokeImage = (DMSelectorImageView) this.rootLayout.findViewById(R.id.img_text_basic_stoke);
        linearLayout2.setSelected(true);
        this.colorView.setVisibility(0);
        linearLayout.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.edit.DMEditTextView3.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                linearLayout2.setSelected(false);
                linearLayout3.setSelected(false);
                linearLayout.setSelected(true);
                DMEditTextView3.this.shadowView.setVisibility(0);
                DMEditTextView3.this.colorView.setVisibility(4);
                DMEditTextView3.this.stokeView.setVisibility(4);
            }
        });
        linearLayout2.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.edit.DMEditTextView3.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                linearLayout2.setSelected(true);
                linearLayout3.setSelected(false);
                linearLayout.setSelected(false);
                DMEditTextView3.this.shadowView.setVisibility(4);
                DMEditTextView3.this.colorView.setVisibility(0);
                DMEditTextView3.this.stokeView.setVisibility(4);
            }
        });
        linearLayout3.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.edit.DMEditTextView3.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                linearLayout2.setSelected(false);
                linearLayout3.setSelected(true);
                linearLayout.setSelected(false);
                DMEditTextView3.this.shadowView.setVisibility(4);
                DMEditTextView3.this.colorView.setVisibility(4);
                DMEditTextView3.this.stokeView.setVisibility(0);
            }
        });
        this.shadowView.setFixedView(this.editText);
        this.colorView.setColorListener(this.editText);
        this.stokeView.setFixedView(this.editText);
    }

    private void iniackground() {
        this.bubbleLayout = (RelativeLayout) this.rootLayout.findViewById(R.id.bg_layout);
        GridView gridView = (GridView) this.rootLayout.findViewById(R.id.bg_list);
        SeekBar seekBar = (SeekBar) this.rootLayout.findViewById(R.id.seekbar_bg_transparency);
        this.seekBar = seekBar;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.picspool.instatextview.edit.DMEditTextView3.12
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
                DMEditTextView3.this.editText.setBgAlpha(255 - i);
                DMEditTextView3.this.editText.invalidate();
            }
        });
        DM_BackgroundAdapter3 dM_BackgroundAdapter3 = new DM_BackgroundAdapter3(this.mContext, this.editText);
        gridView.setAdapter((ListAdapter) dM_BackgroundAdapter3);
        gridView.setOnItemClickListener(dM_BackgroundAdapter3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidViews() {
        this.fontList.setVisibility(8);
        this.imagebg_list_layout.setVisibility(8);
        this.emoji_list_layout.setVisibility(8);
        this.textbasicLayout.setVisibility(8);
        this.bubbleLayout.setVisibility(8);
        this.keyImage.setSelected(false);
        this.emojiImage.setSelected(false);
        this.typefaceImage.setSelected(false);
        this.bubbleImage.setSelected(false);
        this.imagebgImage.setSelected(false);
        this.basicImage.setSelected(false);
        this.finishImage.setSelected(false);
        this.imm.hideSoftInputFromWindow(this.editText.getWindowToken(), 0);
        this.showInputFlag = false;
    }

    private void iniData() {
        this.fontAdapter.setSelection(this.editText.getTextDrawer().getTypefaceIndex());
        this.seekBar.setProgress(255 - this.editText.getBgAlpha());
        this.shadowView.iniData();
        this.colorView.iniData();
        this.stokeView.iniData();
    }

    @Override // android.view.View
    protected void onSizeChanged(final int i, final int i2, int i3, int i4) {
        if (this.topViewHeight == 0) {
            this.topViewHeight = i2;
        }
        this.handler.post(new Runnable() { // from class: com.picspool.instatextview.edit.DMEditTextView3.13
            @Override // java.lang.Runnable
            public void run() {
                if (DMEditTextView3.this.imm != null && DMEditTextView3.this.showInputFlag && DMEditTextView3.this.imm.isActive()) {
                    DMEditTextView3.this.editLayout.setLayoutParams(new LinearLayout.LayoutParams(i, i2));
                    DMEditTextView3 dMEditTextView3 = DMEditTextView3.this;
                    dMEditTextView3.linear_layout_height = dMEditTextView3.linear_layout.getLayoutParams().height;
                    DMDefaultEditTextHeightManager.defaultHeight = i2 - DMEditTextView3.this.linear_layout_height;
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) DMEditTextView3.this.editText.getLayoutParams();
                    if (DMEditTextView3.this.isHasImageBg) {
                        layoutParams.width = DMEditTextView3.this.imageBgWidth;
                        layoutParams.height = DMEditTextView3.this.imageBgHeight;
                        layoutParams.topMargin = DMEditTextView3.this.imageBgTopMargin;
                    } else {
                        layoutParams.width = DMScreenInfoUtil.screenWidth(DMEditTextView3.this.mContext);
                        layoutParams.height = DMDefaultEditTextHeightManager.defaultHeight;
                        layoutParams.topMargin = 0;
                    }
                    int i5 = DMEditTextView3.this.topViewHeight - i2;
                    if (DMEditTextView3.this.onCreateFlag && DMEditTextView3.this.getVisibility() == 0 && i5 == 0) {
                        DMEditTextView3.this.cancelEdit();
                    }
                    if (!DMEditTextView3.this.onCreateFlag) {
                        DMEditTextView3.this.onCreateFlag = true;
                    }
                    DMEditTextView3.this.listLayout.setLayoutParams(new LinearLayout.LayoutParams(i, i5));
                    if (DMEditTextView3.this.imageBgAdapter == null || DMEditTextView3.this.mBMTextBgRes == null) {
                        return;
                    }
                    DMEditTextView3.this.imageBgAdapter.bgClicked(DMEditTextView3.this.mBMTextBgRes);
                }
            }
        });
    }

    public void resetTextBgResNullable() {
        this.mBMTextBgRes = null;
    }

    public void editText(DMTextDrawer dMTextDrawer) {
        if (dMTextDrawer != null) {
            if (dMTextDrawer.isBackgroundImageDrawable() && dMTextDrawer.mBackgroundDrawable != null) {
                resetEditTextLayout(true, dMTextDrawer.mBgImageWidth, dMTextDrawer.mBgImageHeight);
            } else {
                resetEditTextLayout(false, 0.0f, 0.0f);
            }
            this.editText.setTextDrawer(dMTextDrawer);
            this.editText.setFocusable(true);
            this.editText.setFocusableInTouchMode(true);
            this.editText.requestFocus();
            invalidViews();
            this.imm.showSoftInput(this.editText, 0);
            this.showInputFlag = true;
            this.keyImage.setSelected(true);
            iniData();
            if (!this.editText.isShowCaretFlag()) {
                this.editText.setShowCaretFlag(true);
            }
            invalidate();
        }
    }

    public void resetEditTextLayout(boolean z, float f, float f2) {
        this.isHasImageBg = z;
        if (z) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.editText.getLayoutParams();
            int i = (int) f;
            layoutParams.width = i;
            int i2 = (int) f2;
            layoutParams.height = i2;
            this.imageBgWidth = i;
            this.imageBgHeight = i2;
            if (DMDefaultEditTextHeightManager.defaultHeight > f2) {
                int i3 = (int) ((DMDefaultEditTextHeightManager.defaultHeight - f2) / 2.0f);
                layoutParams.topMargin = i3;
                this.imageBgTopMargin = i3;
            }
            this.editText.setLayoutParams(layoutParams);
            return;
        }
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.editText.getLayoutParams();
        layoutParams2.topMargin = 0;
        layoutParams2.width = DMScreenInfoUtil.screenWidth(this.mContext);
        layoutParams2.height = DMDefaultEditTextHeightManager.defaultHeight;
        this.editText.setLayoutParams(layoutParams2);
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        if (i == 0) {
            this.editText.loadImage();
            loadImage();
        } else if (i == 4) {
            DM_TextFixedView3 dM_TextFixedView3 = this.editText;
            if (dM_TextFixedView3 != null) {
                dM_TextFixedView3.dispose();
            }
            releaseImage(this.shadowImage);
            releaseImage(this.colorImage);
            releaseImage(this.stokeImage);
            DMSelectorImageView dMSelectorImageView = this.keyImage;
            if (dMSelectorImageView != null) {
                dMSelectorImageView.releaseImage();
            }
            DMSelectorImageView dMSelectorImageView2 = this.typefaceImage;
            if (dMSelectorImageView2 != null) {
                dMSelectorImageView2.releaseImage();
            }
            DMSelectorImageView dMSelectorImageView3 = this.bubbleImage;
            if (dMSelectorImageView3 != null) {
                dMSelectorImageView3.releaseImage();
            }
            DMSelectorImageView dMSelectorImageView4 = this.basicImage;
            if (dMSelectorImageView4 != null) {
                dMSelectorImageView4.releaseImage();
            }
            DMSelectorImageView dMSelectorImageView5 = this.finishImage;
            if (dMSelectorImageView5 != null) {
                dMSelectorImageView5.releaseImage();
            }
        }
    }

    public void loadImage() {
        DMSelectorImageView dMSelectorImageView = this.shadowImage;
        if (dMSelectorImageView == null || this.colorImage == null || this.stokeImage == null) {
            return;
        }
        dMSelectorImageView.setImgPath("text/text_ui/text_basic_shadow.png");
        this.shadowImage.setImgPressedPath("text/text_ui/text_basic_shadow.png");
        this.shadowImage.loadImage();
        this.colorImage.setImgPath("text/text_ui/text_basic_color.png");
        this.colorImage.setImgPressedPath("text/text_ui/text_basic_color.png");
        this.colorImage.loadImage();
        this.stokeImage.setImgPath("text/text_ui/text_basic_stoke.png");
        this.stokeImage.setImgPressedPath("text/text_ui/text_basic_stoke.png");
        this.stokeImage.loadImage();
    }

    public void releaseImage(ImageView imageView) {
        if (imageView != null) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
            if (bitmapDrawable != null) {
                imageView.setBackgroundResource(0);
                bitmapDrawable.setCallback(null);
                Bitmap bitmap = bitmapDrawable.getBitmap();
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }
                imageView.setImageBitmap(null);
            }
            System.gc();
        }
    }

    public void finishEditText(DMTextDrawer dMTextDrawer) {
        this.editText.setTextDrawer(null);
        DMInstaTextView3 dMInstaTextView3 = this.instaTextView;
        if (dMInstaTextView3 != null) {
            dMInstaTextView3.finishEditText(dMTextDrawer);
            this.instaTextView.callFinishEditText();
        }
    }

    public DMInstaTextView3 getInstaTextView() {
        return this.instaTextView;
    }

    public void setInstaTextView(DMInstaTextView3 dMInstaTextView3) {
        this.instaTextView = dMInstaTextView3;
    }

    public void cancelEdit() {
        DMInstaTextView3 dMInstaTextView3 = this.instaTextView;
        if (dMInstaTextView3 != null) {
            dMInstaTextView3.callFinishEditText();
            this.instaTextView.cancelEdit();
        }
    }

    public void setBgRes(DMWBImageRes dMWBImageRes) {
        DMTextBgRes dMTextBgRes;
        String name = dMWBImageRes.getName();
        DMTextBgManager singletonInstance = DMTextBgManager.getSingletonInstance(this.mContext);
        if (singletonInstance == null || singletonInstance.getCount() <= 0 || name == null || this.imageBgAdapter == null || (dMTextBgRes = (DMTextBgRes) singletonInstance.getRes(name)) == null) {
            return;
        }
        this.mBMTextBgRes = dMTextBgRes;
    }

    public void showDefultText(boolean z) {
        DM_TextFixedView3 dM_TextFixedView3 = this.editText;
        if (dM_TextFixedView3 != null) {
            dM_TextFixedView3.setIsUserKeyboardContent(z);
        }
    }
}
