package com.picspool.instatextview.online;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import androidx.recyclerview.widget.ItemTouchHelper;
import com.picspool.instatextview.edit.DM_BackgroundAdapter;
import com.picspool.instatextview.edit.DM_TextFixedView;
import com.picspool.instatextview.utils.DMSelectorImageView;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.picspool.lib.text.DMTextDrawer;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DM_OnlineEditTextView extends FrameLayout {
    private LinearLayout basicButton;
    private DMSelectorImageView basicImage;
    private LinearLayout bubbleButton;
    private DMSelectorImageView bubbleImage;
    private RelativeLayout bubbleLayout;
    private DMSelectorImageView colorImage;
    private DM_OnlineBasicColorView colorView;
    private FrameLayout editLayout;
    private DM_TextFixedView editText;
    private LinearLayout finishButton;
    private DMSelectorImageView finishImage;
    private DM_OnlineFontAdapter fontAdapter;
    private ListView fontList;
    private Handler handler;
    private InputMethodManager imm;
    private DM_OnlineInstaTextView instaTextView;
    private LinearLayout keyButton;
    private DMSelectorImageView keyImage;
    private FrameLayout listLayout;
    private boolean onCreateFlag;
    View rootLayout;
    private SeekBar seekBar;
    private DMSelectorImageView shadowImage;
    private DM_OnlineBasicShadowView shadowView;
    private boolean showInputFlag;
    private DMSelectorImageView stokeImage;
    private DM_OnlineBasicStokeView stokeView;
    private FrameLayout textbasicLayout;
    private int topViewHeight;
    private LinearLayout typefaceButton;
    private DMSelectorImageView typefaceImage;

    public DM_OnlineEditTextView(Context context) {
        super(context);
        this.handler = new Handler();
        this.showInputFlag = true;
        this.topViewHeight = 0;
        this.onCreateFlag = false;
        iniView();
    }

    public DM_OnlineEditTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.handler = new Handler();
        this.showInputFlag = true;
        this.topViewHeight = 0;
        this.onCreateFlag = false;
        iniView();
    }

    public DM_OnlineEditTextView(Context context, AttributeSet attributeSet, int i) {
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
        View inflate = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_text_online_edit_text_view, (ViewGroup) null);
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
        this.imm = (InputMethodManager) this.editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        this.keyButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.online.DM_OnlineEditTextView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DM_OnlineEditTextView.this.keyImage.isSelected()) {
                    return;
                }
                DM_OnlineEditTextView.this.invalidViews();
                DM_OnlineEditTextView.this.imm.showSoftInput(DM_OnlineEditTextView.this.editText, 0);
                DM_OnlineEditTextView.this.showInputFlag = true;
                DM_OnlineEditTextView.this.keyImage.setSelected(true);
                if (DM_OnlineEditTextView.this.editText.isShowCaretFlag()) {
                    return;
                }
                DM_OnlineEditTextView.this.editText.setShowCaretFlag(true);
            }
        });
        this.typefaceButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.online.DM_OnlineEditTextView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DM_OnlineEditTextView.this.typefaceImage.isSelected()) {
                    return;
                }
                DM_OnlineEditTextView.this.invalidViews();
                DM_OnlineEditTextView.this.fontList.setVisibility(View.VISIBLE);
                DM_OnlineEditTextView.this.typefaceImage.setSelected(true);
                if (DM_OnlineEditTextView.this.editText.isShowCaretFlag()) {
                    DM_OnlineEditTextView.this.editText.setShowCaretFlag(false);
                }
            }
        });
        this.finishButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.online.DM_OnlineEditTextView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DM_OnlineEditTextView.this.showInputFlag && DM_OnlineEditTextView.this.keyImage.isSelected()) {
                    DM_OnlineEditTextView.this.invalidViews();
                    DM_OnlineEditTextView.this.basicButton.performClick();
                    return;
                }
                DM_OnlineEditTextView dM_OnlineEditTextView = DM_OnlineEditTextView.this;
                dM_OnlineEditTextView.finishEditText(dM_OnlineEditTextView.editText.getTextDrawer());
            }
        });
        this.basicButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.online.DM_OnlineEditTextView.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DM_OnlineEditTextView.this.basicImage.isSelected()) {
                    return;
                }
                DM_OnlineEditTextView.this.invalidViews();
                DM_OnlineEditTextView dM_OnlineEditTextView = DM_OnlineEditTextView.this;
                dM_OnlineEditTextView.showView(dM_OnlineEditTextView.textbasicLayout);
                DM_OnlineEditTextView.this.basicImage.setSelected(true);
                if (DM_OnlineEditTextView.this.editText.isShowCaretFlag()) {
                    DM_OnlineEditTextView.this.editText.setShowCaretFlag(false);
                }
            }
        });
        this.bubbleButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.online.DM_OnlineEditTextView.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DM_OnlineEditTextView.this.bubbleImage.isSelected()) {
                    return;
                }
                DM_OnlineEditTextView.this.invalidViews();
                DM_OnlineEditTextView.this.bubbleLayout.setVisibility(View.VISIBLE);
                DM_OnlineEditTextView.this.bubbleImage.setSelected(true);
                if (DM_OnlineEditTextView.this.editText.isShowCaretFlag()) {
                    DM_OnlineEditTextView.this.editText.setShowCaretFlag(false);
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
        this.handler.post(new Runnable() { // from class: com.picspool.instatextview.online.DM_OnlineEditTextView.6
            @Override // java.lang.Runnable
            public void run() {
                view.setVisibility(View.VISIBLE);
            }
        });
    }

    private void iniFont() {
        DM_OnlineFontAdapter dM_OnlineFontAdapter = new DM_OnlineFontAdapter(getContext());
        this.fontAdapter = dM_OnlineFontAdapter;
        dM_OnlineFontAdapter.setEditText(this.editText);
        this.fontList.setAdapter((ListAdapter) this.fontAdapter);
    }

    private void iniBase() {
        this.textbasicLayout = (FrameLayout) this.rootLayout.findViewById(R.id.text_basic_layout);
        final LinearLayout linearLayout = (LinearLayout) this.rootLayout.findViewById(R.id.basic_shadow);
        final LinearLayout linearLayout2 = (LinearLayout) this.rootLayout.findViewById(R.id.basic_color);
        final LinearLayout linearLayout3 = (LinearLayout) this.rootLayout.findViewById(R.id.basic_stoke);
        this.shadowView = (DM_OnlineBasicShadowView) this.rootLayout.findViewById(R.id.basic_shadow_layout);
        this.colorView = (DM_OnlineBasicColorView) this.rootLayout.findViewById(R.id.basic_color_layout);
        this.stokeView = (DM_OnlineBasicStokeView) this.rootLayout.findViewById(R.id.basic_stoke_layout);
        this.shadowView.setTextFixedView(this.editText);
        this.shadowImage = (DMSelectorImageView) this.rootLayout.findViewById(R.id.img_text_basic_shadow);
        this.colorImage = (DMSelectorImageView) this.rootLayout.findViewById(R.id.img_text_basic_color);
        this.stokeImage = (DMSelectorImageView) this.rootLayout.findViewById(R.id.img_text_basic_stoke);
        linearLayout2.setSelected(true);
        linearLayout2.setBackgroundColor(Color.rgb(233, 233, 233));
        this.colorView.setVisibility(View.VISIBLE);
        linearLayout.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.online.DM_OnlineEditTextView.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                linearLayout2.setSelected(false);
                linearLayout3.setSelected(false);
                linearLayout.setSelected(true);
                DM_OnlineEditTextView.this.shadowView.setVisibility(View.VISIBLE);
                DM_OnlineEditTextView.this.colorView.setVisibility(View.INVISIBLE);
                DM_OnlineEditTextView.this.stokeView.setVisibility(View.INVISIBLE);
                linearLayout.setBackgroundColor(Color.rgb(233, 233, 233));
                linearLayout3.setBackgroundColor(Color.rgb((int) ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, (int) ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, (int) ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION));
                linearLayout2.setBackgroundColor(Color.rgb((int) ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, (int) ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, (int) ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION));
            }
        });
        linearLayout2.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.online.DM_OnlineEditTextView.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                linearLayout2.setSelected(true);
                linearLayout3.setSelected(false);
                linearLayout.setSelected(false);
                DM_OnlineEditTextView.this.shadowView.setVisibility(View.INVISIBLE);
                DM_OnlineEditTextView.this.colorView.setVisibility(View.VISIBLE);
                DM_OnlineEditTextView.this.stokeView.setVisibility(View.INVISIBLE);
                linearLayout2.setBackgroundColor(Color.rgb(233, 233, 233));
                linearLayout3.setBackgroundColor(Color.rgb((int) ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, (int) ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, (int) ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION));
                linearLayout.setBackgroundColor(Color.rgb((int) ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, (int) ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, (int) ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION));
            }
        });
        linearLayout3.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.online.DM_OnlineEditTextView.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                linearLayout2.setSelected(false);
                linearLayout3.setSelected(true);
                linearLayout.setSelected(false);
                DM_OnlineEditTextView.this.shadowView.setVisibility(View.INVISIBLE);
                DM_OnlineEditTextView.this.colorView.setVisibility(View.INVISIBLE);
                DM_OnlineEditTextView.this.stokeView.setVisibility(View.VISIBLE);
                linearLayout3.setBackgroundColor(Color.rgb(233, 233, 233));
                linearLayout2.setBackgroundColor(Color.rgb((int) ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, (int) ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, (int) ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION));
                linearLayout.setBackgroundColor(Color.rgb((int) ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, (int) ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, (int) ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION));
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
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.picspool.instatextview.online.DM_OnlineEditTextView.10
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
                DM_OnlineEditTextView.this.editText.setBgAlpha(255 - i);
                DM_OnlineEditTextView.this.editText.invalidate();
            }
        });
        DM_BackgroundAdapter dM_BackgroundAdapter = new DM_BackgroundAdapter(getContext(), this.editText);
        gridView.setAdapter((ListAdapter) dM_BackgroundAdapter);
        gridView.setOnItemClickListener(dM_BackgroundAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidViews() {
        this.fontList.setVisibility(View.GONE);
        this.textbasicLayout.setVisibility(View.GONE);
        this.bubbleLayout.setVisibility(View.GONE);
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
        this.handler.post(new Runnable() { // from class: com.picspool.instatextview.online.DM_OnlineEditTextView.11
            @Override // java.lang.Runnable
            public void run() {
                if (DM_OnlineEditTextView.this.imm != null && DM_OnlineEditTextView.this.showInputFlag && DM_OnlineEditTextView.this.imm.isActive()) {
                    DM_OnlineEditTextView.this.editLayout.setLayoutParams(new LinearLayout.LayoutParams(i, i2));
                    int i5 = DM_OnlineEditTextView.this.topViewHeight - i2;
                    if (DM_OnlineEditTextView.this.onCreateFlag && DM_OnlineEditTextView.this.getVisibility() == View.VISIBLE && i5 == 0) {
                        DM_OnlineEditTextView.this.cancelEdit();
                    }
                    if (!DM_OnlineEditTextView.this.onCreateFlag) {
                        DM_OnlineEditTextView.this.onCreateFlag = true;
                    }
                    DM_OnlineEditTextView.this.listLayout.setLayoutParams(new LinearLayout.LayoutParams(i, i5));
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
            DM_OnlineInstaTextView dM_OnlineInstaTextView = this.instaTextView;
            if (dM_OnlineInstaTextView != null) {
                dM_OnlineInstaTextView.callFinishEditText();
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
        this.instaTextView.finishEditText(dMTextDrawer);
    }

    public DM_OnlineInstaTextView getInstaTextView() {
        return this.instaTextView;
    }

    public void setInstaTextView(DM_OnlineInstaTextView dM_OnlineInstaTextView) {
        this.instaTextView = dM_OnlineInstaTextView;
    }

    public void cancelEdit() {
        this.instaTextView.cancelEdit();
    }

    public void dispose() {
        DM_OnlineFontAdapter dM_OnlineFontAdapter = this.fontAdapter;
        if (dM_OnlineFontAdapter != null) {
            dM_OnlineFontAdapter.dispose();
            this.fontAdapter = null;
        }
    }
}
