package com.picspool.snappic.manager;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.lib.filter.gpu.GPUFilterType;
import com.picspool.lib.resource.DMWBRes;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSSceneManager {
    private List<GPUFilterRes> filterResList;
    private Context mContext;

    public CSSceneManager(Context context) {
        this.mContext = context;
        ArrayList arrayList = new ArrayList();
        this.filterResList = arrayList;
        arrayList.add(initfilterres("indoor", "fscene/editor_scene_indoor.png", R.string.scene_indoor, GPUFilterType.SCENE_F_DARKEN));
        this.filterResList.add(initfilterres("cloudy", "fscene/editor_scene_cloudy.png", R.string.scene_cloudy, GPUFilterType.SCENE_F_CLOUDY));
        this.filterResList.add(initfilterres("backlight", "fscene/editor_scene_backlight.png", R.string.scene_backlight, GPUFilterType.SCENE_F_BACKLIT));
        this.filterResList.add(initfilterres("flash", "fscene/editor_scene_flash.png", R.string.scene_flash, GPUFilterType.SCENE_F_FLASH));
        this.filterResList.add(initfilterres("landscape", "fscene/editor_scene_landscape.png", R.string.scene_landscape, GPUFilterType.SCENE_F_LANDSCAPE));
        this.filterResList.add(initfilterres("night", "fscene/editor_scene_night.png", R.string.scene_night, GPUFilterType.SCENE_F_NIGHT));
        this.filterResList.add(initfilterres("sundown", "fscene/editor_scene_sundown.png", R.string.scene_sundown, GPUFilterType.SCENE_F_SUNSET));
        this.filterResList.add(initfilterres("food", "fscene/editor_scene_food.png", R.string.scene_food, GPUFilterType.SCENE_F_FOOD));
        this.filterResList.add(initfilterres("beach", "fscene/editor_scene_beach.png", R.string.scene_beach, GPUFilterType.SCENE_F_SANDSNOW));
        this.filterResList.add(initfilterres("daylightlamp", "fscene/editor_scene_daylightlamp.png", R.string.scene_daylightlamp, GPUFilterType.SCENE_F_FLUORESCENT));
    }

    private GPUFilterRes initfilterres(String str, String str2, int i, GPUFilterType gPUFilterType) {
        GPUFilterRes gPUFilterRes = new GPUFilterRes();
        gPUFilterRes.setName(str);
        gPUFilterRes.setIconFileName(str2);
        gPUFilterRes.setIconType(DMWBRes.LocationType.ASSERT);
        gPUFilterRes.setFilterType(gPUFilterType);
        gPUFilterRes.setShowText(this.mContext.getString(i));
        return gPUFilterRes;
    }

    public List<GPUFilterRes> getFilterResList() {
        return this.filterResList;
    }

    public List<DMWBRes> getBMWBResList() {
        ArrayList arrayList = new ArrayList();
        for (GPUFilterRes gPUFilterRes : this.filterResList) {
            arrayList.add(gPUFilterRes);
        }
        return arrayList;
    }
}
