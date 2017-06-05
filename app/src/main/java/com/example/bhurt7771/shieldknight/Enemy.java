package com.example.bhurt7771.shieldknight;

import android.graphics.RectF;

/**
 * Created by Bee on 2017-06-01.
 */

public class Enemy {

    private RectF mRect;
    private float mXVelocity;
    private float mYVelocity;
    private float mAssassinWidth;
    private float mAssassinHeight;

    //give access to the Rect
    public RectF getRect() {
        return mRect;
    }

//    //Change the Assassin's position each frame
//    public void update(long fps) {
//        mRect.left = mRect.left + (mXVelocity / fps);
//        mRect.top = mRect.top + (mYVelocity / fps);
//        mRect.right = mRect.left + mAssassinWidth;
//        mRect.bottom = mRect.top - mAssassinHeight;
//    }

    //Continue on page 610
    //Reverse the vertical heading
}
