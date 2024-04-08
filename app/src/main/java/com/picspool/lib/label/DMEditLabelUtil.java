package com.picspool.lib.label;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.picspool.lib.label.edit.BMEditLabelView;
import com.picspool.lib.label.edit.BMLabelBMSticker;
import com.picspool.lib.label.edit.BMListLabelView;
import com.picspool.lib.p017io.DMBitmapIoCache;
import com.picspool.lib.sticker.core.DMSticker;
import com.picspool.lib.text.DMEditTextUtil;
import com.picspool.lib.text.DMTextStickerView;
import com.picspool.lib.text.draw.DMTextDrawer;
import com.picspool.lib.text.sticker.DMSmallTextBMSticker;

/* loaded from: classes3.dex */
public class DMEditLabelUtil extends DMEditTextUtil {
    private boolean addLabelFlag;
    protected BMEditLabelView editLabelView;
    private DMEditTextUtil.OnEditingListener finishEditLabelCall;
    private boolean labelListShowed;
    protected BMListLabelView listLabelView;

    public DMEditLabelUtil(ViewGroup viewGroup, DMTextStickerView dMTextStickerView) {
        super(viewGroup, dMTextStickerView);
        this.labelListShowed = false;
        this.addLabelFlag = false;
        this.showTextView.setStickerViewClickListener(new DMTextStickerView.StikcerViewOnClickListener() { // from class: com.picspool.lib.label.DMEditLabelUtil.1
            @Override // com.picspool.lib.text.DMTextStickerView.StikcerViewOnClickListener
            public void clickSticker(DMSticker dMSticker) {
                if (dMSticker == null) {
                    return;
                }
                if (dMSticker instanceof DMSmallTextBMSticker) {
                    DMEditLabelUtil.this.editText(((DMSmallTextBMSticker) dMSticker).getTextDrawer());
                    DMEditLabelUtil.this.showTextView.setSurfaceVisibility(4);
                } else if (dMSticker instanceof BMLabelBMSticker) {
                    DMEditLabelUtil.this.editLabel(((BMLabelBMSticker) dMSticker).getTextDrawer());
                    DMEditLabelUtil.this.showTextView.setSurfaceVisibility(4);
                }
            }

            @Override // com.picspool.lib.text.DMTextStickerView.StikcerViewOnClickListener
            public void deleteSticker(DMSticker dMSticker) {
                if (dMSticker == null) {
                    return;
                }
                if (dMSticker instanceof DMSmallTextBMSticker) {
                    ((DMSmallTextBMSticker) dMSticker).releaseImage();
                } else if (dMSticker instanceof BMLabelBMSticker) {
                    ((BMLabelBMSticker) dMSticker).releaseImage();
                }
            }
        });
    }

    public BMListLabelView createListLabelView() {
        return new BMListLabelView(this.rootView.getContext());
    }

    public void addLabelList() {
        DMEditTextUtil.OnEditingListener onEditingListener = this.finishEditLabelCall;
        if (onEditingListener != null) {
            onEditingListener.startEditing();
        }
        if (this.editLabelView != null) {
            releaseLabelEdit();
        }
        if (this.listLabelView == null) {
            createLabelList();
        }
        this.showTextView.setSurfaceVisibility(4);
        this.labelListShowed = true;
        this.addLabelFlag = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addLabel(DMTextDrawer dMTextDrawer) {
        DMEditTextUtil.OnEditingListener onEditingListener = this.finishEditLabelCall;
        if (onEditingListener != null) {
            onEditingListener.startEditing();
        }
        if (this.listLabelView != null) {
            releaseLabelList();
        }
        if (this.editLabelView == null) {
            createLabelEdit();
        }
        this.editLabelView.editText(dMTextDrawer);
        this.showTextView.setSurfaceVisibility(4);
        this.addLabelFlag = true;
    }

    public void editLabel(DMTextDrawer dMTextDrawer) {
        DMEditTextUtil.OnEditingListener onEditingListener = this.finishEditLabelCall;
        if (onEditingListener != null) {
            onEditingListener.startEditing();
        }
        if (this.listLabelView != null) {
            releaseLabelList();
        }
        if (this.editLabelView == null) {
            createLabelEdit();
        }
        this.editLabelView.editText(dMTextDrawer);
        this.showTextView.setSurfaceVisibility(4);
        this.labelListShowed = false;
        this.addLabelFlag = false;
    }

    public void finishEditLabel(DMTextDrawer dMTextDrawer) {
        if (this.addLabelFlag) {
            if (dMTextDrawer != null && dMTextDrawer.getTextString() != null && dMTextDrawer.getTextString().length() != 0) {
                dMTextDrawer.setShowHelpFlag(false);
                BMLabelBMSticker bMLabelBMSticker = new BMLabelBMSticker(this.rootView.getContext(), dMTextDrawer);
                bMLabelBMSticker.updateBitmap();
                this.showTextView.addTextSticker(bMLabelBMSticker);
            }
        } else {
            DMSticker selectedSticker = this.showTextView.getSelectedSticker();
            if (selectedSticker != null && (selectedSticker instanceof BMLabelBMSticker)) {
                BMLabelBMSticker bMLabelBMSticker2 = (BMLabelBMSticker) selectedSticker;
                bMLabelBMSticker2.updateBitmap();
                this.showTextView.updateTextSticker(bMLabelBMSticker2.getWidth(), bMLabelBMSticker2.getHeight());
            }
        }
        releaseLabelAll();
        DMEditTextUtil.OnEditingListener onEditingListener = this.finishEditLabelCall;
        if (onEditingListener != null) {
            onEditingListener.findshEditing();
        }
    }

    public void cancelEditLabel(boolean z) {
        DMSticker selectedSticker = this.showTextView.getSelectedSticker();
        if (selectedSticker != null && (selectedSticker instanceof BMLabelBMSticker) && z) {
            BMLabelBMSticker bMLabelBMSticker = (BMLabelBMSticker) selectedSticker;
            bMLabelBMSticker.updateBitmap();
            this.showTextView.updateTextSticker(bMLabelBMSticker.getWidth(), bMLabelBMSticker.getHeight());
        }
        this.showTextView.setSurfaceVisibility(0);
        releaseLabelAll();
        DMEditTextUtil.OnEditingListener onEditingListener = this.finishEditLabelCall;
        if (onEditingListener != null) {
            onEditingListener.findshEditing();
        }
    }

    public void createLabelEdit() {
        this.editLabelView = new BMEditLabelView(this.rootView.getContext());
        this.rootView.addView(this.editLabelView, new FrameLayout.LayoutParams(-1, -1));
        this.editLabelView.setEditingChangedListener(new BMEditLabelView.EditingChangedListener() { // from class: com.picspool.lib.label.DMEditLabelUtil.2
            @Override // com.picspool.lib.label.edit.BMEditLabelView.EditingChangedListener
            public void editingBack() {
                if (DMEditLabelUtil.this.labelListShowed) {
                    DMEditLabelUtil.this.releaseLabelEdit();
                    DMEditLabelUtil.this.createLabelList();
                    return;
                }
                DMEditLabelUtil.this.releaseLabelEdit();
                DMEditLabelUtil.this.showTextView.setSurfaceVisibility(0);
            }

            @Override // com.picspool.lib.label.edit.BMEditLabelView.EditingChangedListener
            public void finishEditing(DMTextDrawer dMTextDrawer) {
                DMEditLabelUtil.this.finishEditLabel(dMTextDrawer);
            }

            @Override // com.picspool.lib.label.edit.BMEditLabelView.EditingChangedListener
            public void cancelEditing() {
                DMEditLabelUtil.this.cancelEditLabel(true);
            }
        });
    }

    public void createLabelList() {
        this.listLabelView = createListLabelView();
        this.listLabelView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        this.rootView.addView(this.listLabelView);
        this.listLabelView.setVisibility(0);
        this.listLabelView.setListChangedListener(new BMListLabelView.ListChangedListener() { // from class: com.picspool.lib.label.DMEditLabelUtil.3
            @Override // com.picspool.lib.label.edit.BMListLabelView.ListChangedListener
            public void listBack() {
                DMEditLabelUtil.this.cancelEditLabel(false);
            }

            @Override // com.picspool.lib.label.edit.BMListLabelView.ListChangedListener
            public void textClick() {
                DMEditLabelUtil.this.cancelEditLabel(false);
                DMEditLabelUtil.this.addText();
            }

            @Override // com.picspool.lib.label.edit.BMListLabelView.ListChangedListener
            public void labelEdit(DMTextDrawer dMTextDrawer) {
                DMEditLabelUtil.this.addLabel(dMTextDrawer);
            }
        });
    }

    public void releaseLabelEdit() {
        BMEditLabelView bMEditLabelView = this.editLabelView;
        if (bMEditLabelView != null) {
            bMEditLabelView.removeAllViews();
            this.rootView.removeView(this.editLabelView);
            this.editLabelView = null;
        }
    }

    public void releaseLabelList() {
        BMListLabelView bMListLabelView = this.listLabelView;
        if (bMListLabelView != null) {
            bMListLabelView.removeAllViews();
            this.rootView.removeView(this.listLabelView);
            this.listLabelView = null;
        }
    }

    public void releaseLabelAll() {
        releaseLabelEdit();
        releaseLabelList();
    }

    public void addLabelWithBlurBackground(Activity activity) {
        if (DMBitmapIoCache.getBitmap("text_blur_bitmap.jpg") == null) {
            loadBlurCache(activity);
        }
        addLabelList();
    }

    @Override // com.picspool.lib.text.DMEditTextUtil
    public void resume() {
        if (this.editLabelView != null) {
            if (this.rootView.indexOfChild(this.editLabelView) != -1) {
                this.rootView.removeView(this.editLabelView);
            }
            this.editLabelView = null;
        }
        super.resume();
    }

    @Override // com.picspool.lib.text.DMEditTextUtil
    public void dispose() {
        this.rootView.removeAllViews();
        if (this.showTextView != null) {
            this.showTextView.dipose();
        }
    }
}
