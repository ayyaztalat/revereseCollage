package com.picspool.lib.widget.colorart;

import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class DMHashBag<K> extends HashMap<K, Integer> {
    private static final long serialVersionUID = 1;

    public int getCount(K k) {
        if (get(k) == null) {
            return 0;
        }
        return get(k).intValue();
    }

    public void add(K k) {
        if (get(k) == null) {
            put(k, 1);
        } else {
            put(k, Integer.valueOf(get(k).intValue() + 1));
        }
    }

    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
