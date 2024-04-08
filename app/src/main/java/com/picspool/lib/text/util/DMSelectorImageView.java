package com.picspool.lib.text.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import java.io.InputStream;

/* loaded from: classes3.dex */
public class DMSelectorImageView extends ImageView {
    private int imgID;
    private String imgPath;
    private int imgPressedID;
    private String imgPressedPath;
    private boolean touchFlag;

    public DMSelectorImageView(Context context) {
        super(context);
        this.touchFlag = true;
        iniView();
    }

    public DMSelectorImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.touchFlag = true;
        iniView();
    }

    private void iniView() {
        setOnTouchListener(new OnTouchListener() { // from class: com.picspool.lib.text.util.DMSelectorImageView.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (DMSelectorImageView.this.touchFlag) {
                    int action = motionEvent.getAction() & 255;
                    if (action == 0) {
                        DMSelectorImageView.this.laodSelectorImage();
                        return false;
                    } else if (action != 1) {
                        return false;
                    } else {
                        DMSelectorImageView.this.laodSelectorImagePressed();
                        return false;
                    }
                }
                return false;
            }
        });
    }

    public void loadImage() {
        laodSelectorImage();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void laodSelectorImage() {
        int i = this.imgID;
        if (i != 0) {
            loadSelectorImagefromID(i);
            return;
        }
        String str = this.imgPath;
        if (str != null) {
            loadSelectorImagefromPath(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void laodSelectorImagePressed() {
        int i = this.imgPressedID;
        if (i != 0) {
            loadSelectorImagefromID(i);
        } else if (this.imgPath != null) {
            loadSelectorImagefromPath(this.imgPressedPath);
        }
    }

    private void loadSelectorImagefromID(int i) {
        releaseImage();
        if (i != 0) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inPurgeable = true;
            options.inInputShareable = true;
            InputStream openRawResource = getResources().openRawResource(i);
            Bitmap decodeStream = openRawResource != null ? BitmapFactory.decodeStream(openRawResource, null, options) : null;
            if (decodeStream != null) {
                setImageBitmap(decodeStream);
            }
        }
    }

    private void loadSelectorImagefromPath(String str) {
        releaseImage();
        if (str != null) {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                options.inPurgeable = true;
                options.inInputShareable = true;
                InputStream open = getResources().getAssets().open(str);
                Bitmap decodeStream = open != null ? BitmapFactory.decodeStream(open, null, options) : null;
                if (decodeStream != null) {
                    setImageBitmap(decodeStream);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void releaseImage() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getDrawable();
        if (bitmapDrawable != null) {
            setBackgroundResource(0);
            bitmapDrawable.setCallback(null);
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            setImageBitmap(null);
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public void setSelected(boolean z) {
        super.setSelected(z);
        if (z) {
            laodSelectorImagePressed();
        } else {
            laodSelectorImage();
        }
    }

    public int getImgPressedID() {
        return this.imgPressedID;
    }

    public void setImgPressedID(int i) {
        this.imgPressedID = i;
    }

    public int getImgID() {
        return this.imgID;
    }

    public void setImgID(int i) {
        this.imgID = i;
    }

    public boolean isTouchFlag() {
        return this.touchFlag;
    }

    public void setTouchFlag(boolean z) {
        this.touchFlag = z;
    }

    public String getImgPressedPath() {
        return this.imgPressedPath;
    }

    public void setImgPressedPath(String str) {
        this.imgPressedPath = str;
    }

    public String getImgPath() {
        return this.imgPath;
    }

    public void setImgPath(String str) {
        this.imgPath = str;
    }
}
