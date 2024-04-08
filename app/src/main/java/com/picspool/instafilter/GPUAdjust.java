package com.picspool.instafilter;

import android.graphics.Bitmap;
import java.util.LinkedList;
import com.picspool.lib.filter.gpu.adjust.GPUImageBrightnessFilter;
import com.picspool.lib.filter.gpu.adjust.GPUImageContrastFilter;
import com.picspool.lib.filter.gpu.adjust.GPUImageExposureFilter;
import com.picspool.lib.filter.gpu.adjust.GPUImageSaturationFilter;
import com.picspool.lib.filter.gpu.adjust.GPUImageSharpenFilter;
import com.picspool.lib.filter.gpu.adjust.GPUImageWhiteBalanceFilter;
import com.picspool.lib.filter.gpu.core.GPUImage;
import com.picspool.lib.filter.listener.OnPostFilteredListener;

/* loaded from: classes3.dex */
public class GPUAdjust {
    public static void filter(Bitmap bitmap, float[] fArr, final OnPostFilteredListener onPostFilteredListener) {
        if (fArr.length < 6) {
            return;
        }
        LinkedList linkedList = new LinkedList();
        linkedList.add(new GPUImageBrightnessFilter(fArr[0]));
        linkedList.add(new GPUImageContrastFilter(fArr[1]));
        linkedList.add(new GPUImageSaturationFilter(fArr[2]));
        linkedList.add(new GPUImageExposureFilter(fArr[3]));
        linkedList.add(new GPUImageWhiteBalanceFilter(fArr[4], 0.0f));
        linkedList.add(new GPUImageSharpenFilter(fArr[5]));
        GPUImage.getBitmapForMultipleFilters(bitmap, linkedList, new GPUImage.ResponseListener<Bitmap>() { // from class: com.picspool.instafilter.GPUAdjust.1
            @Override // com.picspool.lib.filter.gpu.core.GPUImage.ResponseListener
            public void response(Bitmap bitmap2) {
                onPostFilteredListener.postFiltered(bitmap2);
            }
        });
    }

    public static void filterBrightness(Bitmap bitmap, float f, final OnPostFilteredListener onPostFilteredListener) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(new GPUImageBrightnessFilter(f));
        GPUImage.getBitmapForMultipleFilters(bitmap, linkedList, new GPUImage.ResponseListener<Bitmap>() { // from class: com.picspool.instafilter.GPUAdjust.2
            @Override // com.picspool.lib.filter.gpu.core.GPUImage.ResponseListener
            public void response(Bitmap bitmap2) {
                onPostFilteredListener.postFiltered(bitmap2);
            }
        });
    }

    public static void filterContrast(Bitmap bitmap, float f, final OnPostFilteredListener onPostFilteredListener) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(new GPUImageContrastFilter(f));
        GPUImage.getBitmapForMultipleFilters(bitmap, linkedList, new GPUImage.ResponseListener<Bitmap>() { // from class: com.picspool.instafilter.GPUAdjust.3
            @Override // com.picspool.lib.filter.gpu.core.GPUImage.ResponseListener
            public void response(Bitmap bitmap2) {
                onPostFilteredListener.postFiltered(bitmap2);
            }
        });
    }

    public static void filterSaturation(Bitmap bitmap, float f, final OnPostFilteredListener onPostFilteredListener) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(new GPUImageSaturationFilter(f));
        GPUImage.getBitmapForMultipleFilters(bitmap, linkedList, new GPUImage.ResponseListener<Bitmap>() { // from class: com.picspool.instafilter.GPUAdjust.4
            @Override // com.picspool.lib.filter.gpu.core.GPUImage.ResponseListener
            public void response(Bitmap bitmap2) {
                onPostFilteredListener.postFiltered(bitmap2);
            }
        });
    }

    public static void filterExposure(Bitmap bitmap, float f, final OnPostFilteredListener onPostFilteredListener) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(new GPUImageExposureFilter(f));
        GPUImage.getBitmapForMultipleFilters(bitmap, linkedList, new GPUImage.ResponseListener<Bitmap>() { // from class: com.picspool.instafilter.GPUAdjust.5
            @Override // com.picspool.lib.filter.gpu.core.GPUImage.ResponseListener
            public void response(Bitmap bitmap2) {
                onPostFilteredListener.postFiltered(bitmap2);
            }
        });
    }

    public static void filterWhiteBalance(Bitmap bitmap, float f, final OnPostFilteredListener onPostFilteredListener) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(new GPUImageWhiteBalanceFilter(f, 0.0f));
        GPUImage.getBitmapForMultipleFilters(bitmap, linkedList, new GPUImage.ResponseListener<Bitmap>() { // from class: com.picspool.instafilter.GPUAdjust.6
            @Override // com.picspool.lib.filter.gpu.core.GPUImage.ResponseListener
            public void response(Bitmap bitmap2) {
                onPostFilteredListener.postFiltered(bitmap2);
            }
        });
    }

    public static void filterSharpen(Bitmap bitmap, float f, final OnPostFilteredListener onPostFilteredListener) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(new GPUImageSharpenFilter(f));
        GPUImage.getBitmapForMultipleFilters(bitmap, linkedList, new GPUImage.ResponseListener<Bitmap>() { // from class: com.picspool.instafilter.GPUAdjust.7
            @Override // com.picspool.lib.filter.gpu.core.GPUImage.ResponseListener
            public void response(Bitmap bitmap2) {
                onPostFilteredListener.postFiltered(bitmap2);
            }
        });
    }
}
