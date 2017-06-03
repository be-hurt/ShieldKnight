package com.example.bhurt7771.shieldknight;

import android.graphics.RectF;

/**
 * Created by bhurt7771 on 6/1/2017.
 */

public class Assassin extends Enemy {

    private RectF mRect;
    private float mXVelocity;
    private float mYVelocity;
    private float mAssassinWidth;
    private float mAssassinHeight;

    public Assassin(int screenX, int screenY) {
        //Make the Assassin size relative to the screen resolution
        mAssassinWidth = screenX / 70;
        mAssassinHeight = mAssassinWidth;

        /*This initializes the assassin's movement. Start him at the edge of the screen
        * (top or bottom) and have him move towards the princess*/


        //Initialize the Rect that represents the mAssassin pg.609
        mRect = new RectF();
    }

    //give access to the Rect
    public RectF getRect() {
        return mRect;
    }

    //Change the Assassin's position each frame
    public void update(long fps) {
        mRect.left = mRect.left + (mXVelocity / fps);
        mRect.top = mRect.top + (mYVelocity / fps);
        mRect.right = mRect.left + mAssassinWidth;
        mRect.bottom = mRect.top - mAssassinHeight;
    }

    //Continue on page 610
    //Reverse the vertical heading
}
