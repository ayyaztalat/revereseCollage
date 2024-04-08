package com.picspool.libsquare.widget.blend;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.text.TextUtils;
import android.util.Log;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.FileInputStream;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.http.DMAsyncTextHttp;
import com.picspool.lib.onlineImage.DMAsyncImageLoader;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMPreferencesUtil;

/* loaded from: classes.dex */
public class LayerRes extends DMWBImageRes {
    private String groupName;
    private PorterDuff.Mode mode;
    private String uniqueGroupName;

    @Override // com.picspool.lib.resource.DMWBRes
    public String getType() {
        return "CSLayerRes";
    }

    public PorterDuff.Mode getMaskMode() {
        return this.mode;
    }

    public void setMaskMode(PorterDuff.Mode mode) {
        this.mode = mode;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String str) {
        this.groupName = str;
    }

    public String getUniqueName() {
        return this.uniqueGroupName;
    }

    public void setUniqueGroupName(String str) {
        this.uniqueGroupName = str;
    }

    @Override // com.picspool.lib.resource.DMWBImageRes
    public void getImageBitmap(Context context, DMWBImageRes.OnResImageLoadListener onResImageLoadListener) {
        if (this.imageType == null && onResImageLoadListener != null) {
            onResImageLoadListener.onImageLoadFaile();
        }
        if (this.imageType == DMWBRes.LocationType.RES) {
            if (onResImageLoadListener != null) {
                onResImageLoadListener.onImageLoadFinish(DMBitmapUtil.getImageFromAssetsFile(getResources(), this.imageFileName));
            }
        } else if (this.imageType == DMWBRes.LocationType.ASSERT) {
            if (onResImageLoadListener != null) {
                onResImageLoadListener.onImageLoadFinish(DMBitmapUtil.getImageFromAssetsFile(getResources(), this.imageFileName));
            }
        } else if (this.imageType == DMWBRes.LocationType.CACHE) {
            Bitmap cacheBitmap = getCacheBitmap(context, getImageFileName(), 1);
            if (onResImageLoadListener != null) {
                onResImageLoadListener.onImageLoadFinish(cacheBitmap);
            }
        } else if (this.imageType == DMWBRes.LocationType.ONLINE) {
            getOnlineBitmap(onResImageLoadListener);
        }
    }

    @Override // com.picspool.lib.resource.DMWBRes
    public Bitmap getIconBitmap() {
        if (getIconFileName() == null) {
            return null;
        }
        if (getIconType() == DMWBRes.LocationType.CACHE) {
            return getCacheBitmap(this.context, getIconFileName(), 1);
        }
        return super.getIconBitmap();
    }

    private void getOnlineBitmap(final DMWBImageRes.OnResImageLoadListener onResImageLoadListener) {
        String str = DMPreferencesUtil.get(this.context, "DMAsyncImageLoader", this.imageFileName);
        if (TextUtils.isEmpty(str)) {
            DMAsyncTextHttp.asyncHttpRequest(this.imageFileName, new DMAsyncTextHttp.AsyncTextHttpTaskListener() { // from class: com.picspool.libsquare.widget.blend.LayerRes.1
                @Override // com.picspool.lib.http.DMAsyncTextHttp.AsyncTextHttpTaskListener
                public void onRequestDidFailedStatus(Exception exc) {
                    DMWBImageRes.OnResImageLoadListener onResImageLoadListener2 = onResImageLoadListener;
                    if (onResImageLoadListener2 != null) {
                        onResImageLoadListener2.onImageLoadFaile();
                    }
                }

                @Override // com.picspool.lib.http.DMAsyncTextHttp.AsyncTextHttpTaskListener
                public void onRequestDidFinishLoad(String str2) {
                    try {
                        JSONObject jSONObject = new JSONObject(str2);
                        if (jSONObject.getInt("status") == 1) {
                            String string = jSONObject.getJSONObject("blend").getString("url");
                            DMPreferencesUtil.save(LayerRes.this.context, "DMAsyncImageLoader", LayerRes.this.imageFileName, string);
                            Bitmap loadOnlineBitmapFromUrl = LayerRes.this.loadOnlineBitmapFromUrl(string, onResImageLoadListener);
                            if (onResImageLoadListener == null || loadOnlineBitmapFromUrl == null) {
                                return;
                            }
                            onResImageLoadListener.onImageLoadFinish(loadOnlineBitmapFromUrl);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        DMWBImageRes.OnResImageLoadListener onResImageLoadListener2 = onResImageLoadListener;
                        if (onResImageLoadListener2 != null) {
                            onResImageLoadListener2.onImageLoadFaile();
                        }
                    }
                }
            });
            return;
        }
        Bitmap loadOnlineBitmapFromUrl = loadOnlineBitmapFromUrl(str, onResImageLoadListener);
        if (onResImageLoadListener == null || loadOnlineBitmapFromUrl == null) {
            return;
        }
        onResImageLoadListener.onImageLoadFinish(loadOnlineBitmapFromUrl);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap loadOnlineBitmapFromUrl(String str, final DMWBImageRes.OnResImageLoadListener onResImageLoadListener) {
        Log.e("url", str);
        Bitmap loadImageBitamp = new DMAsyncImageLoader().loadImageBitamp(this.context, str, new DMAsyncImageLoader.ImageCallback() { // from class: com.picspool.libsquare.widget.blend.LayerRes.2
            @Override // com.picspool.lib.onlineImage.DMAsyncImageLoader.ImageCallback
            public void imageLoaded(Bitmap bitmap) {
                Log.e(FirebaseAnalytics.Param.SUCCESS, FirebaseAnalytics.Param.SUCCESS + bitmap.getHeight());
                DMWBImageRes.OnResImageLoadListener onResImageLoadListener2 = onResImageLoadListener;
                if (onResImageLoadListener2 != null) {
                    onResImageLoadListener2.onImageLoadFinish(bitmap);
                }
            }

            @Override // com.picspool.lib.onlineImage.DMAsyncImageLoader.ImageCallback
            public void imageLoadedError(Exception exc) {
                onResImageLoadListener.onImageLoadFaile();
            }
        });
        if (loadImageBitamp != null) {
            return loadImageBitamp;
        }
        return null;
    }

    private Bitmap getCacheBitmap(Context context, String str, int i) {
        Bitmap bitmap = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = i;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeStream(fileInputStream, null, options);
            fileInputStream.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return bitmap;
        }
    }
}
