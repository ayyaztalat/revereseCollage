package com.picspool.instatextview.resource.manager;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class DMTextEmojiSelectedManager {
    private static DMTextEmojiSelectedManager instance;
    private Map<Integer, Integer> textEmojiMap = new HashMap();

    private DMTextEmojiSelectedManager() {
    }

    public static DMTextEmojiSelectedManager getInstance() {
        if (instance == null) {
            instance = new DMTextEmojiSelectedManager();
        }
        return instance;
    }

    public Map<Integer, Integer> getTextEmojiSelectedList() {
        return this.textEmojiMap;
    }

    public void addSelectedTextEmoji(int i, int i2) {
        this.textEmojiMap.put(Integer.valueOf(i), Integer.valueOf(i2));
    }

    public void removeAllSelected() {
        this.textEmojiMap.clear();
    }
}
