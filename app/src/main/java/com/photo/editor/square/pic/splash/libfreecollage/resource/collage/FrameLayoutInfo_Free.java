package com.photo.editor.square.pic.splash.libfreecollage.resource.collage;

import java.util.ArrayList;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes2.dex */
public class FrameLayoutInfo_Free extends DMWBRes {
    public ArrayList<ComposeLayoutInfo_Free> layoutInfoes = new ArrayList<>();

    public void addLayoutInfo(ComposeLayoutInfo_Free composeLayoutInfo_Free) {
        this.layoutInfoes.add(composeLayoutInfo_Free);
    }
}
