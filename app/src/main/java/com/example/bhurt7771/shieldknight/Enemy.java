package com.example.bhurt7771.shieldknight;

import android.graphics.RectF;

/**
 * Created by Bee on 2017-06-01.
 */

public class Enemy {

    private RectF rect;

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

    //give access to the Rect
    public RectF getRect() {
        return rect;
    }

    //allow the altering of the enemies' speed for pushing
    public void setSpeed(float value) {

        speed = value;
    }

    //have a method that allows us to pass in the knight's velocity values to make the enemy
    //behave as though it is being pushed when the two collide
    public void beingPushed(float dx, float dy) {

        rect.offset(dx, dy);
    }

    //Change the Enemy's position each frame
    public void update() {
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
