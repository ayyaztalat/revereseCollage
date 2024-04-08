package com.picspool.instafilter.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import java.util.LinkedList;
import java.util.List;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.filter.gpu.GPUImageFilterTools;
import com.picspool.lib.filter.gpu.GPUImageView;
import com.picspool.lib.filter.gpu.adjust.GPUImageSaturationFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageMultiplyBlendFilter;
import com.picspool.lib.filter.gpu.blend.GPUImageScreenBlendFilter;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.gpu.father.GPUImageFilterGroup;
import com.picspool.lib.filter.gpu.normal.GPUImageBoxBlurFilter;
import com.picspool.lib.filter.gpu.normal.GPUImageToneCurveFilter;
import com.picspool.lib.filter.gpu.normal.GPUImageToneCurveMapFilter;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes3.dex */
public class GPUFilterView extends GPUImageView {
    static Bitmap dst;
    GPUImageFilterGroup filterGroup;
    List<GPUImageFilter> filters;
    public Bitmap src;

    public static Bitmap setFilterMultiple(Bitmap bitmap, DMWBRes dMWBRes, String str, int i) {
        return null;
    }

    public void setLens(DMWBRes dMWBRes) {
    }

    public GPUFilterView(Context context) {
        super(context);
        this.src = null;
        this.filters = new LinkedList();
        this.filterGroup = new GPUImageFilterGroup(this.filters);
    }

    public GPUFilterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.src = null;
        this.filters = new LinkedList();
        this.filterGroup = new GPUImageFilterGroup(this.filters);
    }

    public void setFilter(DMWBRes dMWBRes, String str) {
        if (dMWBRes.getName() == "ori") {
            GPUImageFilter gPUImageFilter = new GPUImageFilter();
            this.filters.clear();
            this.filters.add(gPUImageFilter);
            GPUImageFilterGroup gPUImageFilterGroup = new GPUImageFilterGroup(this.filters);
            this.filterGroup = gPUImageFilterGroup;
            setFilter(gPUImageFilterGroup);
        } else if (str != "Art") {
            if (dMWBRes.getName().startsWith("_")) {
                setFilter(this.filterGroup);
            }
        } else if (str == "Art") {
            if (dMWBRes.getName() == "art1") {
                this.filters.clear();
                this.filters.add(new GPUImageSaturationFilter(0.0f));
                GPUImageMultiplyBlendFilter gPUImageMultiplyBlendFilter = new GPUImageMultiplyBlendFilter();
                gPUImageMultiplyBlendFilter.setBitmap(DMBitmapUtil.getImageFromAssetsFile(getResources(), "art/paper.jpg"));
                this.filters.add(gPUImageMultiplyBlendFilter);
                GPUImageScreenBlendFilter gPUImageScreenBlendFilter = new GPUImageScreenBlendFilter();
                gPUImageScreenBlendFilter.setBitmap(DMBitmapUtil.getImageFromAssetsFile(getResources(), "art/pencil.jpg"));
                this.filters.add(gPUImageScreenBlendFilter);
                GPUImageFilterGroup gPUImageFilterGroup2 = new GPUImageFilterGroup(this.filters);
                this.filterGroup = gPUImageFilterGroup2;
                setFilter(gPUImageFilterGroup2);
            }
            if (dMWBRes.getName() == "art2") {
                this.filters.clear();
                this.filters.add(new GPUImageSaturationFilter(0.0f));
                GPUImageScreenBlendFilter gPUImageScreenBlendFilter2 = new GPUImageScreenBlendFilter();
                gPUImageScreenBlendFilter2.setBitmap(DMBitmapUtil.getImageFromAssetsFile(getResources(), "art/pencil.jpg"));
                this.filters.add(gPUImageScreenBlendFilter2);
                GPUImageMultiplyBlendFilter gPUImageMultiplyBlendFilter2 = new GPUImageMultiplyBlendFilter();
                gPUImageMultiplyBlendFilter2.setBitmap(DMBitmapUtil.getImageFromAssetsFile(getResources(), "art/oldpaper.jpg"));
                this.filters.add(gPUImageMultiplyBlendFilter2);
                GPUImageFilterGroup gPUImageFilterGroup3 = new GPUImageFilterGroup(this.filters);
                this.filterGroup = gPUImageFilterGroup3;
                setFilter(gPUImageFilterGroup3);
            }
            if (dMWBRes.getName() == "art3") {
                this.filters.clear();
                this.filters.add(new GPUImageSaturationFilter(0.0f));
                GPUImageScreenBlendFilter gPUImageScreenBlendFilter3 = new GPUImageScreenBlendFilter();
                gPUImageScreenBlendFilter3.setBitmap(DMBitmapUtil.getImageFromAssetsFile(getResources(), "art/pencil.jpg"));
                this.filters.add(gPUImageScreenBlendFilter3);
                GPUImageScreenBlendFilter gPUImageScreenBlendFilter4 = new GPUImageScreenBlendFilter();
                gPUImageScreenBlendFilter4.setBitmap(DMBitmapUtil.getImageFromAssetsFile(getResources(), "art/colorpencil.jpg"));
                this.filters.add(gPUImageScreenBlendFilter4);
                GPUImageFilterGroup gPUImageFilterGroup4 = new GPUImageFilterGroup(this.filters);
                this.filterGroup = gPUImageFilterGroup4;
                setFilter(gPUImageFilterGroup4);
            }
            if (dMWBRes.getName() == "art4") {
                this.filters.clear();
                GPUImageScreenBlendFilter gPUImageScreenBlendFilter5 = new GPUImageScreenBlendFilter();
                gPUImageScreenBlendFilter5.setBitmap(DMBitmapUtil.getImageFromAssetsFile(getResources(), "art/blot.jpg"));
                this.filters.add(gPUImageScreenBlendFilter5);
                GPUImageMultiplyBlendFilter gPUImageMultiplyBlendFilter3 = new GPUImageMultiplyBlendFilter();
                gPUImageMultiplyBlendFilter3.setBitmap(DMBitmapUtil.getImageFromAssetsFile(getResources(), "art/kraft.jpg"));
                this.filters.add(gPUImageMultiplyBlendFilter3);
                GPUImageFilterGroup gPUImageFilterGroup5 = new GPUImageFilterGroup(this.filters);
                this.filterGroup = gPUImageFilterGroup5;
                setFilter(gPUImageFilterGroup5);
            }
            if (dMWBRes.getName() == "art5") {
                this.filters.clear();
                GPUImageScreenBlendFilter gPUImageScreenBlendFilter6 = new GPUImageScreenBlendFilter();
                gPUImageScreenBlendFilter6.setBitmap(DMBitmapUtil.getImageFromAssetsFile(getResources(), "art/spot.jpg"));
                this.filters.add(gPUImageScreenBlendFilter6);
                GPUImageMultiplyBlendFilter gPUImageMultiplyBlendFilter4 = new GPUImageMultiplyBlendFilter();
                gPUImageMultiplyBlendFilter4.setBitmap(DMBitmapUtil.getImageFromAssetsFile(getResources(), "art/oldpaper2.jpg"));
                this.filters.add(gPUImageMultiplyBlendFilter4);
                GPUImageFilterGroup gPUImageFilterGroup6 = new GPUImageFilterGroup(this.filters);
                this.filterGroup = gPUImageFilterGroup6;
                setFilter(gPUImageFilterGroup6);
            }
            if (dMWBRes.getName() == "art6") {
                this.filters.clear();
                this.filters.add(new GPUImageSaturationFilter(0.0f));
                GPUImageScreenBlendFilter gPUImageScreenBlendFilter7 = new GPUImageScreenBlendFilter();
                gPUImageScreenBlendFilter7.setBitmap(DMBitmapUtil.getImageFromAssetsFile(getResources(), "art/moviespot.jpg"));
                this.filters.add(gPUImageScreenBlendFilter7);
                GPUImageMultiplyBlendFilter gPUImageMultiplyBlendFilter5 = new GPUImageMultiplyBlendFilter();
                gPUImageMultiplyBlendFilter5.setBitmap(DMBitmapUtil.getImageFromAssetsFile(getResources(), "art/oldmovie.jpg"));
                this.filters.add(gPUImageMultiplyBlendFilter5);
                GPUImageFilterGroup gPUImageFilterGroup7 = new GPUImageFilterGroup(this.filters);
                this.filterGroup = gPUImageFilterGroup7;
                setFilter(gPUImageFilterGroup7);
            }
            if (dMWBRes.getName() == "art7") {
                this.filters.clear();
                this.filters.add(new GPUImageBoxBlurFilter());
                GPUImageFilterGroup gPUImageFilterGroup8 = new GPUImageFilterGroup(this.filters);
                this.filterGroup = gPUImageFilterGroup8;
                setFilter(gPUImageFilterGroup8);
            }
        }
    }

    public void setMapFilter(Bitmap bitmap, int i) {
        GPUImageToneCurveFilter gPUImageToneCurveFilter;
        GPUImageToneCurveMapFilter gPUImageToneCurveMapFilter;
        if (bitmap.getWidth() < 256) {
            return;
        }
        this.filters.clear();
        if (i == 1) {
            if (bitmap.getHeight() == 1) {
                gPUImageToneCurveMapFilter = new GPUImageToneCurveMapFilter();
                gPUImageToneCurveMapFilter.setBitmap(bitmap);
            } else {
                Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, 256, 1);
                gPUImageToneCurveMapFilter = new GPUImageToneCurveMapFilter();
                gPUImageToneCurveMapFilter.setBitmap(createBitmap);
            }
            this.filters.add(gPUImageToneCurveMapFilter);
        } else if (i == 3) {
            if (bitmap.getHeight() < 3) {
                return;
            }
            if (bitmap.getHeight() == 3) {
                gPUImageToneCurveFilter = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter.setFileType("map3");
                gPUImageToneCurveFilter.setFromMapCurveFileBitmap(bitmap);
            } else {
                Bitmap createBitmap2 = Bitmap.createBitmap(bitmap, 0, 1, 256, 3);
                gPUImageToneCurveFilter = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter.setFileType("map3");
                gPUImageToneCurveFilter.setFromMapCurveFileBitmap(createBitmap2);
            }
            this.filters.add(gPUImageToneCurveFilter);
        } else if (i == 4) {
            if (bitmap.getHeight() < 4) {
                return;
            }
            Bitmap createBitmap3 = Bitmap.createBitmap(bitmap, 0, 0, 256, 1);
            Bitmap createBitmap4 = Bitmap.createBitmap(bitmap, 0, 1, 256, 3);
            GPUImageToneCurveFilter gPUImageToneCurveFilter2 = new GPUImageToneCurveFilter();
            gPUImageToneCurveFilter2.setFileType("map3");
            gPUImageToneCurveFilter2.setFromMapCurveFileBitmap(createBitmap4);
            this.filters.add(gPUImageToneCurveFilter2);
            GPUImageToneCurveMapFilter gPUImageToneCurveMapFilter2 = new GPUImageToneCurveMapFilter();
            gPUImageToneCurveMapFilter2.setBitmap(createBitmap3);
            this.filters.add(gPUImageToneCurveMapFilter2);
        }
        GPUImageFilterGroup gPUImageFilterGroup = new GPUImageFilterGroup(this.filters);
        this.filterGroup = gPUImageFilterGroup;
        setFilter(gPUImageFilterGroup);
    }

    public void setAdjust(int i) {
        for (int i2 = 0; i2 < this.filters.size(); i2++) {
            new GPUImageFilterTools.FilterAdjuster(this.filters.get(i2)).adjust(i);
        }
        requestRender();
    }
}
