package com.photo.editor.square.splash.view.cropview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.HashMap;
import com.picspool.lib.bitmap.multi.DMBitmapDbUtil;
import com.picspool.lib.resource.adapter.DMResImageAdapter;
import com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouchBase;
import com.sky.testproject.R;

/* loaded from: classes2.dex */
public class CSCropBottomAdapter extends DMResImageAdapter {
    public static final int B_0618 = 8;
    public static final int B_16_9 = 6;
    public static final int B_1_1 = 3;
    public static final int B_3_4 = 5;
    public static final int B_4_3 = 4;
    public static final int B_9_16 = 7;
    public static final int FREE = 2;
    public static final int ORIGIN = 1;

    public CSCropBottomAdapter(Context context) {
        super(context);
        HashMap hashMap = new HashMap();
        String string = context.getResources().getString(R.string.crop_origin);
        hashMap.put(ImageViewTouchBase.LOG_TAG, DMBitmapDbUtil.getImageFromAssetsFile(context, "crop/crop_ori_1.png"));
        hashMap.put("text", string);
        hashMap.put("id", 1);
        hashMap.put("imageSelAssetFile", "crop/crop_ori_2.png");
        addObject(hashMap);
        String string2 = context.getResources().getString(R.string.crop_free);
        HashMap hashMap2 = new HashMap();
        hashMap2.put(ImageViewTouchBase.LOG_TAG, DMBitmapDbUtil.getImageFromAssetsFile(context, "crop/crop_free_1.png"));
        hashMap2.put("text", string2);
        hashMap2.put("id", 2);
        hashMap2.put("imageSelAssetFile", "crop/crop_free_2.png");
        addObject(hashMap2);
        String string3 = context.getResources().getString(R.string.crop_goldenratio);
        HashMap hashMap3 = new HashMap();
        hashMap3.put(ImageViewTouchBase.LOG_TAG, DMBitmapDbUtil.getImageFromAssetsFile(context, "crop/crop_gold.png"));
        hashMap3.put("text", string3);
        hashMap3.put("id", 8);
        hashMap3.put("imageSelAssetFile", "crop/crop_gold_2.png");
        addObject(hashMap3);
        HashMap hashMap4 = new HashMap();
        hashMap4.put(ImageViewTouchBase.LOG_TAG, DMBitmapDbUtil.getImageFromAssetsFile(context, "crop/icon_1to1.png"));
        hashMap4.put("text", "1:1");
        hashMap4.put("id", 3);
        hashMap4.put("imageSelAssetFile", "crop/icon_1to1_2.png");
        addObject(hashMap4);
        HashMap hashMap5 = new HashMap();
        hashMap5.put(ImageViewTouchBase.LOG_TAG, DMBitmapDbUtil.getImageFromAssetsFile(context, "crop/icon_4to3.png"));
        hashMap5.put("text", "4:3");
        hashMap5.put("id", 4);
        hashMap5.put("imageSelAssetFile", "crop/icon_4to3_2.png");
        addObject(hashMap5);
        HashMap hashMap6 = new HashMap();
        hashMap6.put(ImageViewTouchBase.LOG_TAG, DMBitmapDbUtil.getImageFromAssetsFile(context, "crop/icon_3to4.png"));
        hashMap6.put("text", "3:4");
        hashMap6.put("id", 5);
        hashMap6.put("imageSelAssetFile", "crop/icon_3to4_2.png");
        addObject(hashMap6);
        HashMap hashMap7 = new HashMap();
        hashMap7.put(ImageViewTouchBase.LOG_TAG, DMBitmapDbUtil.getImageFromAssetsFile(context, "crop/icon_16to9.png"));
        hashMap7.put("text", "16:9");
        hashMap7.put("id", 6);
        hashMap7.put("imageSelAssetFile", "crop/icon_16to9_2.png");
        addObject(hashMap7);
        HashMap hashMap8 = new HashMap();
        hashMap8.put(ImageViewTouchBase.LOG_TAG, DMBitmapDbUtil.getImageFromAssetsFile(context, "crop/icon_9to16.png"));
        hashMap8.put("text", "9:16");
        hashMap8.put("id", 7);
        hashMap8.put("imageSelAssetFile", "crop/icon_9to16_2.png");
        addObject(hashMap8);
    }

    @Override // com.picspool.lib.resource.adapter.DMResImageAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2 = super.getView(i, view, viewGroup);
        ((TextView) view2.findViewById(R.id.item_text)).setTextColor(view2.getResources().getColorStateList(R.color.adjust_text_color_selectable));
        return view2;
    }
}
