package com.picspool.libsquare.uiview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSSquareUiEditorToolBarView extends FrameLayout {
    private OnSquareUiEditorToolBarViewListner mListener;

    /* loaded from: classes.dex */
    public interface OnSquareUiEditorToolBarViewListner {
        void onFillClick();

        void onHorzontalClick();

        void onLeftClick();

        void onRightClick();

        void onSquareClick();

        void onVertialClick();
    }

    public CSSquareUiEditorToolBarView(Context context) {
        super(context);
        init(context);
    }

    public CSSquareUiEditorToolBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public void setOnSquareUiEditorToolBarViewListner(OnSquareUiEditorToolBarViewListner onSquareUiEditorToolBarViewListner) {
        this.mListener = onSquareUiEditorToolBarViewListner;
    }

    private void init(Context context) {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.square_ui_editor_toolbar_view, (ViewGroup) this, true);
        findViewById(R.id.item_fill).setOnClickListener(new OnClickListener() { // from class: com.picspool.libsquare.uiview.CSSquareUiEditorToolBarView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSquareUiEditorToolBarView.this.mListener != null) {
                    CSSquareUiEditorToolBarView.this.mListener.onFillClick();
                }
            }
        });
        findViewById(R.id.item_square).setOnClickListener(new OnClickListener() { // from class: com.picspool.libsquare.uiview.CSSquareUiEditorToolBarView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSquareUiEditorToolBarView.this.mListener != null) {
                    CSSquareUiEditorToolBarView.this.mListener.onSquareClick();
                }
            }
        });
        findViewById(R.id.item_horizontal).setOnClickListener(new OnClickListener() { // from class: com.picspool.libsquare.uiview.CSSquareUiEditorToolBarView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSquareUiEditorToolBarView.this.mListener != null) {
                    CSSquareUiEditorToolBarView.this.mListener.onHorzontalClick();
                }
            }
        });
        findViewById(R.id.item_vertial).setOnClickListener(new OnClickListener() { // from class: com.picspool.libsquare.uiview.CSSquareUiEditorToolBarView.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSquareUiEditorToolBarView.this.mListener != null) {
                    CSSquareUiEditorToolBarView.this.mListener.onVertialClick();
                }
            }
        });
        findViewById(R.id.item_left).setOnClickListener(new OnClickListener() { // from class: com.picspool.libsquare.uiview.CSSquareUiEditorToolBarView.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSquareUiEditorToolBarView.this.mListener != null) {
                    CSSquareUiEditorToolBarView.this.mListener.onLeftClick();
                }
            }
        });
        findViewById(R.id.item_right).setOnClickListener(new OnClickListener() { // from class: com.picspool.libsquare.uiview.CSSquareUiEditorToolBarView.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSquareUiEditorToolBarView.this.mListener != null) {
                    CSSquareUiEditorToolBarView.this.mListener.onRightClick();
                }
            }
        });
    }
}
