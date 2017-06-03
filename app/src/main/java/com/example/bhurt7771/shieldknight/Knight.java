package com.example.bhurt7771.shieldknight;

import android.graphics.RectF;

/**
 * Created by Bee on 2017-06-03.
 */

public class Knight {

    //create a new RectF to hold the 4 coordinates for our knight
    private RectF mRect;

    //How long and tall our knight will be
    private float mWidth;
    private float mHeight;

    //X is the far left of the rectangle that makes our knight
    private float mXCoord;

    //Y is the top coordinate
    private float mYCoord;

    //This will hold how many pixels per second the Knight can move
    private float mKnightSpeed;

    //Which ways the Knight can move
    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;

    //Keep track of if the mKnight is moving and in which direction (handled in the switch case in GameView)
    private int mKnightMoving = STOPPED;

    //The screen length and width in pixels
    private int mScreenX;
    private int mScreenY;

    //Now for the constructor
    public Knight(int x, int y) {
        mScreenX = x;
        mScreenY = y;

        //Make him 1 / 15 of the screen width wide
        mWidth = mScreenX / 15;

        //This knight is a total square, so let's make his/her appearance reflect that
        mHeight = mWidth;

        //Start the mKnight close to the screen center, near the Princess
        mXCoord = mScreenX / 2 + (mWidth * 2);
        mYCoord = mScreenY / 2 + (mWidth * 2);

        mRect = new RectF(mXCoord, mYCoord, mXCoord + mWidth, mYCoord + mHeight);

        //How fast is the Knight (pixels per second)?
        mKnightSpeed = mScreenX / 2;
        // Cover half the screen in a second. Might need to slow this down.
    }

    //Make a getter method so we can draw the knight in the GameView
    public RectF getRect() {
        return mRect;
    }

    //This method will be used for the switch statement in gameView to set how the knight is moving
    public void setMovementState(int state) {
        mKnightMoving = state;
    }

    
}
