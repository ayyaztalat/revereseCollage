package com.photo.editor.square.splash.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import com.google.firebase.messaging.Constants;
import com.photo.editor.square.splash.utils.CSSaveToSDNew;
import com.picspool.snappic.activity.CSSnapPicMain2Activity;
import com.picspool.snappic.snap.CSBestSnapMainLayout;
import com.picspool.lib.bitmap.output.save.DMSaveDoneListener;

/* loaded from: classes2.dex */
public class CSSingleActivity extends CSSnapPicMain2Activity {
    private Bitmap cropBitmap;
    private ImageView image_gift_anim;
    String from = "";
    boolean isSave = false;
    boolean finish = false;

    @Override // com.picspool.snappic.snap.CSBestDragSnapView.OnSnapListener
    public void doubleTapSnap(CSBestSnapMainLayout cSBestSnapMainLayout) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.picspool.snappic.activity.CSSnapPicMain2Activity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // com.picspool.snappic.activity.CSSnapPicMain2Activity
    public void onShareImpl(Bitmap bitmap) {
        super.onShareImpl(oriBitmap);
        goShareAct(bitmap);
    }

    private void goShareAct(final Bitmap bitmap) {
        showProcessDialog();
        CSSaveToSDNew.saveImage(getApplicationContext(), bitmap, "Color Splash", Bitmap.CompressFormat.PNG, new DMSaveDoneListener() { // from class: com.photo.editor.square.splash.activitys.CSSingleActivity.1
            @Override // com.picspool.lib.bitmap.output.save.DMSaveDoneListener
            public void onSavingException(Exception exc) {
                CSSingleActivity.this.dismissProcessDialog();
            }

            @Override // com.picspool.lib.bitmap.output.save.DMSaveDoneListener
            public void onSaveDone(String str, Uri uri) {
                CSSingleActivity.this.dismissProcessDialog();
                if (CSSingleActivity.this.isSave) {
                    return;
                }
                CSSingleActivity.this.isSave = true;
                Intent intent = new Intent(CSSingleActivity.this, CSShareActivity.class);
                intent.putExtra("keyShareBmp", uri.toString());
                intent.putExtra("shareBmpWidth", bitmap.getWidth());
                intent.putExtra("keyShareBmpPath", str);
                if (!TextUtils.isEmpty(CSSingleActivity.this.from) && CSSingleActivity.this.from.equals("edit")) {
                    intent.putExtra(Constants.MessagePayloadKeys.FROM, "edit");
                } else if (!TextUtils.isEmpty(CSSingleActivity.this.from) && CSSingleActivity.this.from.equals("camera")) {
                    intent.putExtra(Constants.MessagePayloadKeys.FROM, "camera");
                }
                CSSingleActivity.this.startActivity(intent);
                CSSingleActivity.this.overridePendingTransition(0, 0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.picspool.snappic.activity.CSSnapPicMain2Activity, com.mhh.libraryads.libads.adsBaseAdActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.isSave = false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.mhh.libraryads.libads.adsBaseAdActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.picspool.snappic.activity.CSSnapPicMain2Activity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }
}
