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
import com.picspool.instatextview.textview.DMInstaTextView;
import com.picspool.instatextview.textview.DM_BasicColorView;
import com.picspool.instatextview.textview.DM_BasicShadowView;
import com.picspool.instatextview.textview.DM_BasicStokeView;
import com.picspool.instatextview.utils.DMSelectorImageView;
import com.picspool.lib.color.DMSysColors;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.picspool.lib.text.DMTextDrawer;
import com.picspool.lib.widget.colorgallery.DMColorGalleryView;
import com.picspool.lib.widget.listener.OnDMColorChangedListener;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMEditTextView2 extends FrameLayout {
    private RelativeLayout basicButton;
    private DMSelectorImageView basicImage;
    private RelativeLayout bubbleButton;
    private DMSelectorImageView bubbleImage;
    private RelativeLayout bubbleLayout;
    private DMColorGalleryView colorGalleryView;
    private DMSelectorImageView colorImage;
    private DM_BasicColorView colorView;
    private FrameLayout editLayout;
    private DM_TextFixedView editText;
    private RelativeLayout finishButton;
    private DMSelectorImageView finishImage;
    private DM_FontAdapter fontAdapter;
    private ListView fontList;
    private Handler handler;
    private InputMethodManager imm;
    private DMInstaTextView instaTextView;
    private boolean isFontStyleHasSelecteFinished;
    private RelativeLayout keyButton;
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
    private RelativeLayout typefaceButton;
    private DMSelectorImageView typefaceImage;

    public DMEditTextView2(Context context) {
        super(context);
        this.handler = new Handler();
        this.showInputFlag = true;
        this.topViewHeight = 0;
        this.isFontStyleHasSelecteFinished = false;
        this.onCreateFlag = false;
        iniView();
    }

    public DMEditTextView2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.handler = new Handler();
        this.showInputFlag = true;
        this.topViewHeight = 0;
        this.isFontStyleHasSelecteFinished = false;
        this.onCreateFlag = false;
        iniView();
    }

    public DMEditTextView2(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.handler = new Handler();
        this.showInputFlag = true;
        this.topViewHeight = 0;
        this.isFontStyleHasSelecteFinished = false;
        this.onCreateFlag = false;
        iniView();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    private void iniView() {
        View inflate = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_text_edit_text_view2, (ViewGroup) null);
        this.rootLayout = inflate;
        this.editLayout = (FrameLayout) inflate.findViewById(R.id.edit_layout);
        this.listLayout = (FrameLayout) this.rootLayout.findViewById(R.id.list_layout);
        this.typefaceButton = (RelativeLayout) this.rootLayout.findViewById(R.id.bottom_typeface);
        this.finishButton = (RelativeLayout) this.rootLayout.findViewById(R.id.bottom_finish);
        this.fontList = (ListView) this.rootLayout.findViewById(R.id.font_list);
        this.editText = (DM_TextFixedView) this.rootLayout.findViewById(R.id.editText1);
        this.typefaceImage = (DMSelectorImageView) this.rootLayout.findViewById(R.id.image_typeface);
        DMSelectorImageView dMSelectorImageView = (DMSelectorImageView) this.rootLayout.findViewById(R.id.image_finish);
        this.finishImage = dMSelectorImageView;
        dMSelectorImageView.setImgPath("text/text_ui/insta_text_done.png");
        this.finishImage.loadImage();
        this.finishImage.setTouchFlag(false);
        DMColorGalleryView dMColorGalleryView = (DMColorGalleryView) this.rootLayout.findViewById(R.id.color_gallery_view);
        this.colorGalleryView = dMColorGalleryView;
        dMColorGalleryView.setFocusable(true);
        this.colorGalleryView.setGalleryItemSize(10, 30, 0, true);
        this.colorGalleryView.setPointTo(33);
        this.colorGalleryView.setListener(new OnDMColorChangedListener() { // from class: com.picspool.instatextview.edit.DMEditTextView2.1
            private boolean iniFlag = false;

            @Override // com.picspool.lib.widget.listener.OnDMColorChangedListener
            public void onColorChanged(int i) {
                int i2 = 0;
                while (true) {
                    if (!this.iniFlag || i2 >= DMSysColors.length) {
                        break;
                    } else if (i == DMSysColors.getColor(i2)) {
                        DMEditTextView2.this.editText.setTextColor(i);
                        DMEditTextView2.this.editText.getTextDrawer().setPaintColorIndex(i2);
                        break;
                    } else {
                        i2++;
                    }
                }
                if (this.iniFlag) {
                    return;
                }
                this.iniFlag = true;
            }
        });
        this.imm = (InputMethodManager) this.editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        this.typefaceButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.edit.DMEditTextView2.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMEditTextView2.this.invalidViews();
                DMEditTextView2.this.textbasicLayout.setVisibility(View.INVISIBLE);
                DMEditTextView2.this.bubbleLayout.setVisibility(View.INVISIBLE);
                DMEditTextView2.this.fontList.setVisibility(View.VISIBLE);
                DMEditTextView2.this.typefaceImage.setSelected(true);
                if (DMEditTextView2.this.editText.isShowCaretFlag()) {
                    DMEditTextView2.this.editText.setShowCaretFlag(false);
                }
            }
        });
        this.finishButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.edit.DMEditTextView2.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!DMEditTextView2.this.isFontStyleHasSelecteFinished) {
                    DMEditTextView2.this.invalidViews();
                    DMEditTextView2.this.isFontStyleHasSelecteFinished = true;
                    DMEditTextView2.this.typefaceButton.performClick();
                    return;
                }
                DMEditTextView2.this.invalidViews();
                DMEditTextView2 dMEditTextView2 = DMEditTextView2.this;
                dMEditTextView2.finishEditText(dMEditTextView2.editText.getTextDrawer());
            }
        });
        this.editLayout.setLayoutParams(new LinearLayout.LayoutParams(DMScreenInfoUtil.screenWidth(getContext()), DMScreenInfoUtil.screenHeight(getContext())));
        iniFont();
        iniBase();
        iniackground();
        addView(this.rootLayout);
    }

    private void showView(final View view) {
        this.handler.post(new Runnable() { // from class: com.picspool.instatextview.edit.DMEditTextView2.4
            @Override // java.lang.Runnable
            public void run() {
                view.setVisibility(View.VISIBLE);
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
        this.colorView.setVisibility(View.VISIBLE);
        linearLayout.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.edit.DMEditTextView2.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                linearLayout2.setSelected(false);
                linearLayout3.setSelected(false);
                linearLayout.setSelected(true);
                DMEditTextView2.this.shadowView.setVisibility(View.VISIBLE);
                DMEditTextView2.this.colorView.setVisibility(View.INVISIBLE);
                DMEditTextView2.this.stokeView.setVisibility(View.INVISIBLE);
            }
        });
        linearLayout2.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.edit.DMEditTextView2.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                linearLayout2.setSelected(true);
                linearLayout3.setSelected(false);
                linearLayout.setSelected(false);
                DMEditTextView2.this.shadowView.setVisibility(View.INVISIBLE);
                DMEditTextView2.this.colorView.setVisibility(View.VISIBLE);
                DMEditTextView2.this.stokeView.setVisibility(View.INVISIBLE);
            }
        });
        linearLayout3.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.edit.DMEditTextView2.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                linearLayout2.setSelected(false);
                linearLayout3.setSelected(true);
                linearLayout.setSelected(false);
                DMEditTextView2.this.shadowView.setVisibility(View.INVISIBLE);
                DMEditTextView2.this.colorView.setVisibility(View.INVISIBLE);
                DMEditTextView2.this.stokeView.setVisibility(View.VISIBLE);
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
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.picspool.instatextview.edit.DMEditTextView2.8
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
                DMEditTextView2.this.editText.setBgAlpha(255 - i);
                DMEditTextView2.this.editText.invalidate();
            }
        });
        DM_BackgroundAdapter dM_BackgroundAdapter = new DM_BackgroundAdapter(getContext(), this.editText);
        gridView.setAdapter((ListAdapter) dM_BackgroundAdapter);
        gridView.setOnItemClickListener(dM_BackgroundAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidViews() {
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
        this.handler.post(new Runnable() { // from class: com.picspool.instatextview.edit.DMEditTextView2.9
            @Override // java.lang.Runnable
            public void run() {
                if (DMEditTextView2.this.imm != null && DMEditTextView2.this.showInputFlag && DMEditTextView2.this.imm.isActive()) {
                    DMEditTextView2.this.editLayout.setLayoutParams(new LinearLayout.LayoutParams(i, i2));
                    int i5 = DMEditTextView2.this.topViewHeight - i2;
                    if (DMEditTextView2.this.onCreateFlag && DMEditTextView2.this.getVisibility() == View.VISIBLE && i5 == 0) {
                        DMEditTextView2.this.cancelEdit();
                    }
                    if (!DMEditTextView2.this.onCreateFlag) {
                        DMEditTextView2.this.onCreateFlag = true;
                    }
                    DMEditTextView2.this.listLayout.setLayoutParams(new LinearLayout.LayoutParams(i, i5));
                }
            }
        });
    }

    public void editText(DMTextDrawer dMTextDrawer) {
        int paintColorIndex;
        if (dMTextDrawer != null) {
            this.editText.setTextDrawer(dMTextDrawer);
            this.editText.setFocusable(true);
            this.editText.setFocusableInTouchMode(true);
            this.editText.requestFocus();
            invalidViews();
            this.imm.showSoftInput(this.editText, 0);
            this.showInputFlag = true;
            iniData();
            if (!this.editText.isShowCaretFlag()) {
                this.editText.setShowCaretFlag(true);
            }
            DM_TextFixedView dM_TextFixedView = this.editText;
            if (dM_TextFixedView != null && dM_TextFixedView.getTextDrawer() != null && (paintColorIndex = this.editText.getTextDrawer().getPaintColorIndex()) >= 0) {
                this.colorGalleryView.setPointTo(paintColorIndex);
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
