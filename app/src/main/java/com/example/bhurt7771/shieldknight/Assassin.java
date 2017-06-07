package com.example.bhurt7771.shieldknight;

import android.graphics.RectF;

/**
 * Created by bhurt7771 on 6/1/2017.
 */

public class Assassin extends Enemy {

    //create a new RectF to hold the 4 coordinates for our Assassin
    private RectF rect;

    //The size of the Assassin
    static final float SCALE = 0.05f;

    //X is the far left of the rectangle that makes our killer
    private float xCoord;

    //Y is the top coordinate
    private float yCoord;

    //This will hold how many pixels per second the Assassin can move
    private float speed;

    private float screenX;
    private float screenY;

    float dy; //delta difference y
    float dx; //delta difference x

    public Assassin(int screenX, int screenY, float x, float y) {
        //Make the Assassin size relative to the screen resolution
//        width = screenX  * SCALE;

        this.screenX = screenX;
        this.screenY = screenY;
        this.xCoord = x;
        this.yCoord = y;

        speed = screenX * 0.005f;

        //Initialize the rect that represents the assassin
        rect = new RectF(xCoord, yCoord, xCoord + screenX * SCALE, yCoord + screenX * SCALE);
    }

    //give access to the rect
    public RectF getRect() {

        return rect;
    }

    public void setSpeed(float value) {

        speed = value;
    }

    //have a method that allows us to pass in the knight's velocity values to make the assassin
    //behave as though it is being pushed when the two collide
    public void pushAssassin(float dx, float dy) {

        rect.offset(dx, dy);
    }

    public void resetAssassin() {
        dx = (screenX / 2) - xCoord; //delta difference x
        dy = (screenY / 2) - yCoord; //delta difference y
        //difference between princess and Assassin
        float magnitude = (float) Math.sqrt((dx * dx) + (dy * dy));

        //readjust difX and difY by the factor
        dx *= (speed/magnitude);
        dy *= (speed/magnitude);
        rect.offset(dx, dy);

        xCoord = rect.left;
        yCoord = rect.top;
    }

    //Change the Assassin's position each frame
    public void update(long fps) {
        // TODO: Pass in Princess to move to her
        dx = (screenX / 2) - xCoord; //delta difference x
        dy = (screenY / 2) - yCoord; //delta difference y
        //difference between princess and Assassin
        float magnitude = (float) Math.sqrt((dx * dx) + (dy * dy));

        //readjust difX and difY by the factor
        dx *= (speed/magnitude);
        dy *= (speed/magnitude);

        if ((dx + rect.left) < 0) {
            dx = -rect.left;
        } else if ((dx + rect.right) >= screenX) {
            dx = screenX - rect.right - 1;
        }
        if ((dy + rect.top) < 0) {
            dy = -rect.top;
        } else if ((dy + rect.bottom) >= screenY) {
            dy = screenY - rect.bottom - 1;
        }

        rect.offset(dx, dy);

        xCoord = rect.left;
        yCoord = rect.top;
    }
}
