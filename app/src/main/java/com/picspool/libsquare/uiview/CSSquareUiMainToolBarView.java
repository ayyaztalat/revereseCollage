package com.picspool.libsquare.uiview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSSquareUiMainToolBarView extends FrameLayout {
    private View ly_Sticker;
    private View ly_background;
    private View ly_blur;
    private View ly_editor;
    private View ly_filter;
    private View ly_frame;
    private View ly_text;
    private Context mContext;
    private OnSquareUiMainToolBarViewListner mListener;

    /* loaded from: classes.dex */
    public interface OnSquareUiMainToolBarViewListner {
        void OnSquareBottomBarItemClick(SquareBottomItem squareBottomItem);
    }

    /* loaded from: classes.dex */
    public enum SquareBottomItem {
        Editor,
        Blur,
        Background,
        Filter,
        Text,
        Sticker,
        Frame
    }

    public CSSquareUiMainToolBarView(Context context) {
        super(context);
        this.mContext = context;
        init(context);
    }

    public CSSquareUiMainToolBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        init(context);
    }

    public void resetSelectorStat() {
        this.ly_editor.setSelected(false);
        this.ly_background.setSelected(false);
        this.ly_blur.setSelected(false);
        this.ly_filter.setSelected(false);
        this.ly_text.setSelected(false);
        this.ly_Sticker.setSelected(false);
        this.ly_frame.setSelected(false);
    }

    public void setInSelectorStat(SquareBottomItem squareBottomItem, boolean z) {
        if (squareBottomItem == SquareBottomItem.Editor) {
            if (z) {
                this.ly_editor.setSelected(true);
            } else {
                this.ly_editor.setSelected(false);
            }
        } else if (squareBottomItem == SquareBottomItem.Background) {
            if (z) {
                this.ly_background.setSelected(true);
            } else {
                this.ly_background.setSelected(false);
            }
        } else if (squareBottomItem == SquareBottomItem.Blur) {
            if (z) {
                this.ly_blur.setSelected(true);
            } else {
                this.ly_blur.setSelected(false);
            }
        } else if (squareBottomItem == SquareBottomItem.Filter) {
            if (z) {
                this.ly_filter.setSelected(true);
            } else {
                this.ly_filter.setSelected(false);
            }
        } else if (squareBottomItem == SquareBottomItem.Text) {
            if (z) {
                this.ly_text.setSelected(true);
            } else {
                this.ly_text.setSelected(false);
            }
        } else if (squareBottomItem == SquareBottomItem.Sticker) {
            if (z) {
                this.ly_Sticker.setSelected(true);
            } else {
                this.ly_Sticker.setSelected(false);
            }
        } else if (squareBottomItem == SquareBottomItem.Frame) {
            if (z) {
                this.ly_frame.setSelected(true);
            } else {
                this.ly_frame.setSelected(false);
            }
        }
    }

    public void setOnSquareUiMainToolBarViewListner(OnSquareUiMainToolBarViewListner onSquareUiMainToolBarViewListner) {
        this.mListener = onSquareUiMainToolBarViewListner;
    }

    private void init(Context context) {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.square_ui_main_toolbar_view, (ViewGroup) this, true);
        View findViewById = findViewById(R.id.item_frame);
        this.ly_frame = findViewById;
        findViewById.setOnClickListener(new OnClickListener() { // from class: com.picspool.libsquare.uiview.CSSquareUiMainToolBarView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSquareUiMainToolBarView.this.mListener != null) {
                    CSSquareUiMainToolBarView.this.mListener.OnSquareBottomBarItemClick(SquareBottomItem.Frame);
                }
            }
        });
        View findViewById2 = findViewById(R.id.item_blur);
        this.ly_blur = findViewById2;
        findViewById2.setOnClickListener(new OnClickListener() { // from class: com.picspool.libsquare.uiview.CSSquareUiMainToolBarView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSquareUiMainToolBarView.this.mListener != null) {
                    CSSquareUiMainToolBarView.this.mListener.OnSquareBottomBarItemClick(SquareBottomItem.Blur);
                }
            }
        });
        View findViewById3 = findViewById(R.id.item_background);
        this.ly_background = findViewById3;
        findViewById3.setOnClickListener(new OnClickListener() { // from class: com.picspool.libsquare.uiview.CSSquareUiMainToolBarView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSquareUiMainToolBarView.this.mListener != null) {
                    CSSquareUiMainToolBarView.this.mListener.OnSquareBottomBarItemClick(SquareBottomItem.Background);
                }
            }
        });
        View findViewById4 = findViewById(R.id.item_sticker);
        this.ly_Sticker = findViewById4;
        findViewById4.setOnClickListener(new OnClickListener() { // from class: com.picspool.libsquare.uiview.CSSquareUiMainToolBarView.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSquareUiMainToolBarView.this.mListener != null) {
                    CSSquareUiMainToolBarView.this.mListener.OnSquareBottomBarItemClick(SquareBottomItem.Sticker);
                }
            }
        });
        View findViewById5 = findViewById(R.id.item_filter);
        this.ly_filter = findViewById5;
        findViewById5.setOnClickListener(new OnClickListener() { // from class: com.picspool.libsquare.uiview.CSSquareUiMainToolBarView.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSquareUiMainToolBarView.this.mListener != null) {
                    CSSquareUiMainToolBarView.this.mListener.OnSquareBottomBarItemClick(SquareBottomItem.Filter);
                }
            }
        });
        View findViewById6 = findViewById(R.id.item_text);
        this.ly_text = findViewById6;
        findViewById6.setOnClickListener(new OnClickListener() { // from class: com.picspool.libsquare.uiview.CSSquareUiMainToolBarView.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSquareUiMainToolBarView.this.mListener != null) {
                    CSSquareUiMainToolBarView.this.mListener.OnSquareBottomBarItemClick(SquareBottomItem.Text);
                }
            }
        });
        View findViewById7 = findViewById(R.id.item_editor);
        this.ly_editor = findViewById7;
        findViewById7.setOnClickListener(new OnClickListener() { // from class: com.picspool.libsquare.uiview.CSSquareUiMainToolBarView.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSquareUiMainToolBarView.this.mListener != null) {
                    CSSquareUiMainToolBarView.this.mListener.OnSquareBottomBarItemClick(SquareBottomItem.Editor);
                }
            }
        });
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_pager);
        if (DMScreenInfoUtil.screenWidthDp(getContext()) > 448) {
            linearLayout.setMinimumWidth(DMScreenInfoUtil.screenWidth(getContext()));
        } else {
            linearLayout.setMinimumWidth(DMScreenInfoUtil.dip2px(getContext(), 448));
        }
    }
}
