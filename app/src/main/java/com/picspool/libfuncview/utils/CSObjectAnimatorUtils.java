package com.picspool.libfuncview.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import androidx.constraintlayout.motion.widget.Key;

/* loaded from: classes.dex */
public class CSObjectAnimatorUtils {
    public static int FucBarDuration = 300;

    public static void performYAnimate(View view, float f, float f2, int i, Animator.AnimatorListener animatorListener) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, f, f2);
        ofFloat.setDuration(i);
        if (animatorListener != null) {
            ofFloat.addListener(animatorListener);
        }
        ofFloat.start();
    }
}
