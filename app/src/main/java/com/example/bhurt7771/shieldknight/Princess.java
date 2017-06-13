package com.example.bhurt7771.shieldknight;

import android.graphics.RectF;

/**
 * Created by Bee on 2017-06-01.
 */

public class Princess {

    //create a new RectF to hold the 4 coordinates for our knight
    private RectF rect;

    //How long and tall our knight will be
    private float width;
    private float height;

    //X is the far left of the rectangle that makes our knight
    private float xCoord;

    //Y is the top coordinate
    private float yCoord;

    //The screen length and width in pixels
    private int screenX;
    private int screenY;

    //Now for the constructor
    public Princess(int x, int y) {
        screenX = x;
        screenY = y;

        //Make her 1 / 25 of the screen width wide
        width = screenX / 15;

        //This princess is also total square
        height = width;

        //Draw the Princess in the center of the screen
        xCoord = screenX / 2 - (width / 2);
        yCoord = screenY / 2 - (width / 2);

        rect = new RectF(xCoord, yCoord, xCoord + width, yCoord + height);
    }

    //Make a getter method so we can draw the princess in the GameView
    public RectF getRect() {

        return rect;
    }

    //Get the princess' position
    public float getYCoord() {
        return yCoord;
    }

    public float getXCoord() {
        return xCoord;
    }
}
