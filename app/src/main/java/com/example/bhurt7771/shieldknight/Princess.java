package com.example.bhurt7771.shieldknight;

import android.graphics.RectF;

/**
 * Created by Bee on 2017-06-01.
 */

public class Princess {

    private RectF mRect;
    private float mPrincessWidth;
    private float mPrincessHeight;

    public Princess(int screenX, int screenY) {
        //Make the Princess size relative to the screen resolution
        mPrincessWidth = screenX / 90;
        mPrincessHeight = mPrincessWidth;

        /*This initializes the princess' position*/


        //Initialize the Rect that represents the mPrincess pg.609
        mRect = new RectF();
    }
}
