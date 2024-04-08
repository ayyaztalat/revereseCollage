package com.picspool.snappic.manager;

import android.content.Context;
import com.picspool.libsquare.res.CSShapeRes;
import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes.dex */
public class CSSquareShapeManager {
    private Context mContext;
    private List<CSShapeRes> shapeResList;

    public List<CSShapeRes> getShapeResList() {
        return this.shapeResList;
    }

    public CSSquareShapeManager(Context context) {
        this.mContext = context;
        ArrayList arrayList = new ArrayList();
        this.shapeResList = arrayList;
        arrayList.add(initshapeitem("shape_none", "shape/icon/none_icon.png", "shape/shape/none_shape.png", CSShapeRes.ShapeMode.TRANSPARENT));
        this.shapeResList.add(initshapeitem("shape_001", "shape/icon/shape_001_icon.png", "shape/shape/shape_001_shape.png", CSShapeRes.ShapeMode.TRANSPARENT));
        this.shapeResList.add(initshapeitem("shape_003", "shape/icon/shape_003_icon.png", "shape/shape/shape_003_shape.png", CSShapeRes.ShapeMode.TRANSPARENT));
        this.shapeResList.add(initshapeitem("shape_005", "shape/icon/shape_005_icon.png", "shape/shape/shape_005_shape.png", CSShapeRes.ShapeMode.TRANSPARENT));
        this.shapeResList.add(initshapeitem("shape_002", "shape/icon/shape_002_icon.png", "shape/shape/shape_002_shape.png", CSShapeRes.ShapeMode.TRANSPARENT));
        this.shapeResList.add(initshapeitem("shape_004", "shape/icon/shape_004_icon.png", "shape/shape/shape_004_shape.png", CSShapeRes.ShapeMode.TRANSPARENT));
        this.shapeResList.add(initshapeitem("shape_008", "shape/icon/shape_008_icon.png", "shape/shape/shape_008_shape.png", CSShapeRes.ShapeMode.TRANSPARENT));
        this.shapeResList.add(initshapeitem("shape_010", "shape/icon/shape_010_icon.png", "shape/shape/shape_010_shape.png", CSShapeRes.ShapeMode.TRANSPARENT));
        this.shapeResList.add(initshapeitem("shape_006", "shape/icon/shape_006_icon.png", "shape/shape/shape_006_shape.png", CSShapeRes.ShapeMode.TRANSPARENT));
        this.shapeResList.add(initshapeitem("shape_007", "shape/icon/shape_007_icon.png", "shape/shape/shape_007_shape.png", CSShapeRes.ShapeMode.TRANSPARENT));
        this.shapeResList.add(initshapeitem("shape_009", "shape/icon/shape_009_icon.png", "shape/shape/shape_009_shape.png", CSShapeRes.ShapeMode.TRANSPARENT));
        this.shapeResList.add(initshapeitem("shape_011", "shape/icon/shape_011_icon.png", "shape/shape/shape_011_shape.png", CSShapeRes.ShapeMode.TRANSPARENT));
        this.shapeResList.add(initshapeitem("shape_012", "shape/icon/shape_012_icon.png", "shape/shape/shape_012_shape.png", CSShapeRes.ShapeMode.TRANSPARENT));
        this.shapeResList.add(initshapeitem("shape_013", "shape/icon/shape_013_icon.png", "shape/shape/shape_013_shape.png", CSShapeRes.ShapeMode.TRANSPARENT));
        this.shapeResList.add(initshapeitem("shape_014", "shape/icon/shape_014_icon.png", "shape/shape/shape_014_shape.png", CSShapeRes.ShapeMode.TRANSPARENT));
        this.shapeResList.add(initshapeitem("shape_015", "shape/icon/shape_015_icon.png", "shape/shape/shape_015_shape.png", CSShapeRes.ShapeMode.TRANSPARENT));
    }

    private CSShapeRes initshapeitem(String str, String str2, String str3, CSShapeRes.ShapeMode shapeMode) {
        CSShapeRes cSShapeRes = new CSShapeRes();
        cSShapeRes.setContext(this.mContext);
        cSShapeRes.setName(str);
        cSShapeRes.setIconFileName(str2);
        cSShapeRes.setIconType(DMWBRes.LocationType.ASSERT);
        cSShapeRes.setImageFileName(str3);
        cSShapeRes.setImageType(DMWBRes.LocationType.ASSERT);
        cSShapeRes.setShapeMode(shapeMode);
        return cSShapeRes;
    }
}
