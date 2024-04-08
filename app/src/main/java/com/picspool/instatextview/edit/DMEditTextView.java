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
import com.sky.testproject.R;
import com.picspool.instatextview.textview.DMInstaTextView;
import com.picspool.instatextview.textview.DM_BasicColorView;
import com.picspool.instatextview.textview.DM_BasicShadowView;
import com.picspool.instatextview.textview.DM_BasicStokeView;
import com.picspool.instatextview.utils.DMSelectorImageView;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.picspool.lib.text.DMTextDrawer;

/* loaded from: classes3.dex */
public class DMEditTextView extends FrameLayout {
    private LinearLayout basicButton;
    private DMSelectorImageView basicImage;
    private LinearLayout bubbleButton;
    private DMSelectorImageView bubbleImage;
    private RelativeLayout bubbleLayout;
    private DMSelectorImageView colorImage;
    private DM_BasicColorView colorView;
    private FrameLayout editLayout;
    private DM_TextFixedView editText;
    private LinearLayout finishButton;
    private DMSelectorImageView finishImage;
    private DM_FontAdapter fontAdapter;
    private ListView fontList;
    private Handler handler;
    private InputMethodManager imm;
    private DMInstaTextView instaTextView;
    private LinearLayout keyButton;
    private DMSelectorImageView keyImage;
    private FrameLayout listLayout;
    private boolean onCreateFlag;
    View rootLayout;
    private SeekBar seekBar;
    private DMSelectorImageView shadowImage;
    private DM_BasicShadowView shadowView;
    private boolean showInputFlag;
    private DMSelectorImageView stokeImage;
    private DM_BasicStokeView stokeView;
    private FrameLayout textbasicLayout;
    private int topViewHeight;
    private LinearLayout typefaceButton;
    private DMSelectorImageView typefaceImage;

    public DMEditTextView(Context context) {
        super(context);
        this.handler = new Handler();
        this.showInputFlag = true;
        this.topViewHeight = 0;
        this.onCreateFlag = false;
        iniView();
    }

    public DMEditTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.handler = new Handler();
        this.showInputFlag = true;
        this.topViewHeight = 0;
        this.onCreateFlag = false;
        iniView();
    }

    public DMEditTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.handler = new Handler();
        this.showInputFlag = true;
        this.topViewHeight = 0;
        this.onCreateFlag = false;
        iniView();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    private void iniView() {
        View inflate = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.dm_text_edit_text_view, (ViewGroup) null);
        this.rootLayout = inflate;
        this.editLayout = (FrameLayout) inflate.findViewById(R.id.edit_layout);
        this.listLayout = (FrameLayout) this.rootLayout.findViewById(R.id.list_layout);
        this.keyButton = (LinearLayout) this.rootLayout.findViewById(R.id.bottom_key);
        this.typefaceButton = (LinearLayout) this.rootLayout.findViewById(R.id.bottom_typeface);
        this.bubbleButton = (LinearLayout) this.rootLayout.findViewById(R.id.bottom_bubble);
        this.basicButton = (LinearLayout) this.rootLayout.findViewById(R.id.bottom_basic);
        this.finishButton = (LinearLayout) this.rootLayout.findViewById(R.id.bottom_finish);
        this.fontList = (ListView) this.rootLayout.findViewById(R.id.font_list);
        this.editText = (DM_TextFixedView) this.rootLayout.findViewById(R.id.editText1);
        DMSelectorImageView dMSelectorImageView = (DMSelectorImageView) this.rootLayout.findViewById(R.id.image_key);
        this.keyImage = dMSelectorImageView;
        dMSelectorImageView.setImgPath("text/text_ui/insta_text_key.png");
        this.keyImage.setImgPressedPath("text/text_ui/insta_text_key1.png");
        this.keyImage.loadImage();
        DMSelectorImageView dMSelectorImageView2 = (DMSelectorImageView) this.rootLayout.findViewById(R.id.image_typeface);
        this.typefaceImage = dMSelectorImageView2;
        dMSelectorImageView2.setImgPath("text/text_ui/insta_text_typeface.png");
        this.typefaceImage.setImgPressedPath("text/text_ui/insta_text_typeface1.png");
        this.typefaceImage.loadImage();
        DMSelectorImageView dMSelectorImageView3 = (DMSelectorImageView) this.rootLayout.findViewById(R.id.image_bubble);
        this.bubbleImage = dMSelectorImageView3;
        dMSelectorImageView3.setImgPath("text/text_ui/insta_text_bubble.png");
        this.bubbleImage.setImgPressedPath("text/text_ui/insta_text_bubble1.png");
        this.bubbleImage.loadImage();
        DMSelectorImageView dMSelectorImageView4 = (DMSelectorImageView) this.rootLayout.findViewById(R.id.image_basic);
        this.basicImage = dMSelectorImageView4;
        dMSelectorImageView4.setImgPath("text/text_ui/insta_text_basic.png");
        this.basicImage.setImgPressedPath("text/text_ui/insta_text_basic1.png");
        this.basicImage.loadImage();
        DMSelectorImageView dMSelectorImageView5 = (DMSelectorImageView) this.rootLayout.findViewById(R.id.image_finish);
        this.finishImage = dMSelectorImageView5;
        dMSelectorImageView5.setImgPath("text/text_ui/insta_text_done.png");
        this.finishImage.loadImage();
        this.finishImage.setTouchFlag(false);
        this.imm = (InputMethodManager) this.editText.getContext().getSystemService("input_method");
        this.keyButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.edit.DMEditTextView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DMEditTextView.this.keyImage.isSelected()) {
                    return;
                }
                DMEditTextView.this.invalidViews();
                DMEditTextView.this.imm.showSoftInput(DMEditTextView.this.editText, 0);
                DMEditTextView.this.showInputFlag = true;
                DMEditTextView.this.keyImage.setSelected(true);
                if (DMEditTextView.this.editText.isShowCaretFlag()) {
                    return;
                }
                DMEditTextView.this.editText.setShowCaretFlag(true);
            }
        });
        this.typefaceButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.edit.DMEditTextView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DMEditTextView.this.typefaceImage.isSelected()) {
                    return;
                }
                DMEditTextView.this.invalidViews();
                DMEditTextView.this.fontList.setVisibility(0);
                DMEditTextView.this.typefaceImage.setSelected(true);
                if (DMEditTextView.this.editText.isShowCaretFlag()) {
                    DMEditTextView.this.editText.setShowCaretFlag(false);
                }
            }
        });
        this.finishButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.edit.DMEditTextView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DMEditTextView.this.showInputFlag && DMEditTextView.this.keyImage.isSelected()) {
                    DMEditTextView.this.invalidViews();
                    DMEditTextView.this.basicButton.performClick();
                    return;
                }
                DMEditTextView dMEditTextView = DMEditTextView.this;
                dMEditTextView.finishEditText(dMEditTextView.editText.getTextDrawer());
            }
        });
        this.basicButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.edit.DMEditTextView.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DMEditTextView.this.basicImage.isSelected()) {
                    return;
                }
                DMEditTextView.this.invalidViews();
                DMEditTextView dMEditTextView = DMEditTextView.this;
                dMEditTextView.showView(dMEditTextView.textbasicLayout);
                DMEditTextView.this.basicImage.setSelected(true);
                if (DMEditTextView.this.editText.isShowCaretFlag()) {
                    DMEditTextView.this.editText.setShowCaretFlag(false);
                }
            }
        });
        this.bubbleButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.edit.DMEditTextView.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DMEditTextView.this.bubbleImage.isSelected()) {
                    return;
                }
                DMEditTextView.this.invalidViews();
                DMEditTextView.this.bubbleLayout.setVisibility(0);
                DMEditTextView.this.bubbleImage.setSelected(true);
                if (DMEditTextView.this.editText.isShowCaretFlag()) {
                    DMEditTextView.this.editText.setShowCaretFlag(false);
                }
            }
        });
        this.editLayout.setLayoutParams(new LinearLayout.LayoutParams(DMScreenInfoUtil.screenWidth(getContext()), DMScreenInfoUtil.screenHeight(getContext())));
        iniFont();
        iniBase();
        iniackground();
        addView(this.rootLayout);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showView(final View view) {
        this.handler.post(new Runnable() { // from class: com.picspool.instatextview.edit.DMEditTextView.6
            @Override // java.lang.Runnable
            public void run() {
                view.setVisibility(0);
            }
        });
    }

    private void iniFont() {
        DM_FontAdapter dM_FontAdapter = new DM_FontAdapter(getContext());
        this.fontAdapter = dM_FontAdapter;
        dM_FontAdapter.setEditText(this.editText);
        this.fontList.setAdapter((ListAdapter) this.fontAdapter);
    }

    private void iniBase() {
        this.textbasicLayout = (FrameLayout) this.rootLayout.findViewById(R.id.text_basic_layout);
        final LinearLayout linearLayout = (LinearLayout) this.rootLayout.findViewById(R.id.basic_shadow);
        final LinearLayout linearLayout2 = (LinearLayout) this.rootLayout.findViewById(R.id.basic_color);
        final LinearLayout linearLayout3 = (LinearLayout) this.rootLayout.findViewById(R.id.basic_stoke);
        this.shadowView = (DM_BasicShadowView) this.rootLayout.findViewById(R.id.basic_shadow_layout);
        this.colorView = (DM_BasicColorView) this.rootLayout.findViewById(R.id.basic_color_layout);
        this.stokeView = (DM_BasicStokeView) this.rootLayout.findViewById(R.id.basic_stoke_layout);
        this.shadowView.setTextFixedView(this.editText);
        this.shadowImage = (DMSelectorImageView) this.rootLayout.findViewById(R.id.img_text_basic_shadow);
        this.colorImage = (DMSelectorImageView) this.rootLayout.findViewById(R.id.img_text_basic_color);
        this.stokeImage = (DMSelectorImageView) this.rootLayout.findViewById(R.id.img_text_basic_stoke);
        linearLayout2.setSelected(true);
        this.colorView.setVisibility(0);
        linearLayout.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.edit.DMEditTextView.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                linearLayout2.setSelected(false);
                linearLayout3.setSelected(false);
                linearLayout.setSelected(true);
                DMEditTextView.this.shadowView.setVisibility(0);
                DMEditTextView.this.colorView.setVisibility(4);
                DMEditTextView.this.stokeView.setVisibility(4);
            }
        });
        linearLayout2.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.edit.DMEditTextView.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                linearLayout2.setSelected(true);
                linearLayout3.setSelected(false);
                linearLayout.setSelected(false);
                DMEditTextView.this.shadowView.setVisibility(4);
                DMEditTextView.this.colorView.setVisibility(0);
                DMEditTextView.this.stokeView.setVisibility(4);
            }
        });
        linearLayout3.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.edit.DMEditTextView.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                linearLayout2.setSelected(false);
                linearLayout3.setSelected(true);
                linearLayout.setSelected(false);
                DMEditTextView.this.shadowView.setVisibility(4);
                DMEditTextView.this.colorView.setVisibility(4);
                DMEditTextView.this.stokeView.setVisibility(0);
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
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.picspool.instatextview.edit.DMEditTextView.10
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
                DMEditTextView.this.editText.setBgAlpha(255 - i);
                DMEditTextView.this.editText.invalidate();
            }
        });
        DM_BackgroundAdapter dM_BackgroundAdapter = new DM_BackgroundAdapter(getContext(), this.editText);
        gridView.setAdapter((ListAdapter) dM_BackgroundAdapter);
        gridView.setOnItemClickListener(dM_BackgroundAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidViews() {
        this.fontList.setVisibility(8);
        this.textbasicLayout.setVisibility(8);
        this.bubbleLayout.setVisibility(8);
        this.keyImage.setSelected(false);
        this.typefaceImage.setSelected(false);
        this.bubbleImage.setSelected(false);
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
        this.handler.post(new Runnable() { // from class: com.picspool.instatextview.edit.DMEditTextView.11
            @Override // java.lang.Runnable
            public void run() {
                if (DMEditTextView.this.imm != null && DMEditTextView.this.showInputFlag && DMEditTextView.this.imm.isActive()) {
                    DMEditTextView.this.editLayout.setLayoutParams(new LinearLayout.LayoutParams(i, i2));
                    int i5 = DMEditTextView.this.topViewHeight - i2;
                    if (DMEditTextView.this.onCreateFlag && DMEditTextView.this.getVisibility() == 0 && i5 == 0) {
                        DMEditTextView.this.cancelEdit();
                    }
                    if (!DMEditTextView.this.onCreateFlag) {
                        DMEditTextView.this.onCreateFlag = true;
                    }
                    DMEditTextView.this.listLayout.setLayoutParams(new LinearLayout.LayoutParams(i, i5));
                }
            }
        });
    }

    public void editText(DMTextDrawer dMTextDrawer) {
        if (dMTextDrawer != null) {
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

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        if (i == 0) {
            this.editText.loadImage();
            loadImage();
        } else if (i == 4) {
            this.editText.dispose();
            releaseImage(this.shadowImage);
            releaseImage(this.colorImage);
            releaseImage(this.stokeImage);
            this.keyImage.releaseImage();
            this.typefaceImage.releaseImage();
            this.bubbleImage.releaseImage();
            this.basicImage.releaseImage();
            this.finishImage.releaseImage();
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
        this.instaTextView.finishEditText(dMTextDrawer);
        DMInstaTextView dMInstaTextView = this.instaTextView;
        if (dMInstaTextView != null) {
            dMInstaTextView.callFinishEditText();
        }
    }

    public DMInstaTextView getInstaTextView() {
        return this.instaTextView;
    }

    public void setInstaTextView(DMInstaTextView dMInstaTextView) {
        this.instaTextView = dMInstaTextView;
    }

    public void cancelEdit() {
        DMInstaTextView dMInstaTextView = this.instaTextView;
        if (dMInstaTextView != null) {
            dMInstaTextView.callFinishEditText();
            this.instaTextView.cancelEdit();
        }
    }
}
