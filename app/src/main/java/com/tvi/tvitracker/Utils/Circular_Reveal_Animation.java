package com.tvi.tvitracker.Utils;

import android.animation.Animator;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.LinearLayout;

public class Circular_Reveal_Animation {




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View circularRevealActivity(View rootLayout, Context context) {

        int cx = rootLayout.getRight()-getDips(48,context ) ;
        int cy = rootLayout.getBottom()-getDips(48,context) ;
        float finalRadius = Math.max(rootLayout.getWidth(), rootLayout.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, 0, finalRadius);
        circularReveal.setDuration(500);

        // make the view visible and start the animation
        rootLayout.setVisibility(View.VISIBLE);
        circularReveal.start();
        return rootLayout;
    }

    public int getDips(int dps,Context context) {
        Resources resources= (Resources) context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dps,resources.getDisplayMetrics());
    }
}
