package com.example.bhurt7771.shieldknight;

import android.graphics.RectF;

/**
 * Created by Bee on 2017-06-01.
 */

public class Princess {

    //create a new RectF to hold the 4 coordinates for our knight
    private RectF mRect;

    //How long and tall our knight will be
    private float mWidth;
    private float mHeight;

    //X is the far left of the rectangle that makes our knight
    private float mXCoord;

    //Y is the top coordinate
    private float mYCoord;

    //The screen length and width in pixels
    private int mScreenX;
    private int mScreenY;

    //Now for the constructor
    public Princess(int x, int y) {
        mScreenX = x;
        mScreenY = y;

        //Make her 1 / 25 of the screen width wide
        mWidth = mScreenX / 20;

        //This princess is also total square
        mHeight = mWidth;

        //Draw the Princess in the center of the screen
        mXCoord = mScreenX / 2;
        mYCoord = mScreenY / 2;

        mRect = new RectF(mXCoord, mYCoord, mXCoord + mWidth, mYCoord + mHeight);
    }

    //Make a getter method so we can draw the princess in the GameView
    public RectF getRect() {

        return mRect;
    }

    //Get the princess' width
    public float getmWidth() {
        return mWidth;
    }
}
