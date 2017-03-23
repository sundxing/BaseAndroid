package com.sundxing.android.baseandroid.util;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by sundxing on 17/3/23.
 */

public class Utils {

    /**
     * Remove duplicate element in list.
     * @param src source list may has duplicate element.
     * @param target target list that put result. If null, we use src for container.
     * @param comparator
     * @param <T>
     */
    public static <T> void removeDuplicates(List<T> src, List<T> target, Comparator<T> comparator) {
        Set<T> set = new TreeSet<>(comparator);
        set.addAll(src);
        if (target == null) {
            src.clear();
            src.addAll(set);
        } else {
            target.addAll(set);
        }
    }
}
