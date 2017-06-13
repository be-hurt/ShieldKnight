package com.example.bhurt7771.shieldknight;

import android.graphics.Point;
import android.graphics.RectF;

/**
 * Created by Bee on 2017-06-03.
 */

public class Knight {

    //create a new RectF to hold the 4 coordinates for our knight
    private RectF rect;

    //How long and tall our knight will be
    private float width;
    private float height;

    //X is the far left of the rectangle that makes our knight
    private float xCoord;

    //Y is the top coordinate
    private float yCoord;

    //This will hold how many pixels per second the Knight can move
    private float speed;

    //Keep track of if the knight is moving
    private boolean knightMoving = false;

    //The screen length and width in pixels
    private int screenX;
    private int screenY;

    private float dx; //delta difference x
    private float dy; //delta difference y

    private float touchX, touchY;

    //Now for the constructor
    public Knight(int x, int y) {
        screenX = x;
        screenY = y;

        //Make him 1 / 15 of the screen width wide
        width = screenX / 15;

        //This knight is a total square, so let's make his/her appearance reflect that
        height = width;

        //Start the knight close to the screen center, near the Princess
        xCoord = screenX / 2 + (width * 2);
        yCoord = screenY / 2;

        rect = new RectF(xCoord, yCoord, xCoord + width, yCoord + height);

        //How fast is the Knight (pixels per second)?
        speed = screenX * 0.007f;
        // Cover a third of the screen in a second. Might need to slow this down.
    }

    //Make a getter method so we can draw the knight in the GameView
    public RectF getRect() {
        return rect;
    }

    //Make a getter method so we can access the knight's speed for collision handling
    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float value) {
        this.speed = value;
    }

    //This method will be used for the switch statement in gameView to set how the knight is moving
    public void setMovementState(boolean state) {
        knightMoving = state;
    }

    //This updates the knights movement and changes the coordinates in mRect if needed
    public void update() {
        if (knightMoving) {
            dx = touchX - xCoord; //delta difference x
            dy = touchY - yCoord; //delta difference y

            //difference between tap point and Knight
            float magnitude = (float) Math.sqrt((dx * dx) + (dy * dy));

            //readjust difX and difY by the factor
            dx *= (speed / magnitude);
            dy *= (speed / magnitude);

            if ((dx + rect.left) < 0) {
                dx = 0;
            } else if ((dx + rect.right) >= screenX) {
                dx = screenX - rect.right - 1;
            }
            if ((dy + rect.top) < 0) {
                dy = 0;
            } else if ((dy + rect.bottom) >= screenY) {
                dy = screenY - rect.bottom - 1;
            }

            if (touchX == xCoord && touchY == yCoord) {
                speed = 0;
            } else {
                speed = screenX * 0.007f;
            }

            rect.offset(dx, dy);

            xCoord = rect.left;
            yCoord = rect.top;
        }
    }

    public void setDestination(float x, float y) {
        touchX = x;
        touchY = y;
    }

    public float getDx() {
        return dx;
    }

    public float getxCoord() {
        return xCoord;
    }

    public void setxCoord(float value) {
        xCoord = value;
        rect.offsetTo(xCoord, rect.top);
    }

    public float getDy() {
        return dy;
    }

    public float getyCoord() {
        return yCoord;
    }

    public void setyCoord(float value) {
        yCoord = value;
        rect.offsetTo(rect.left, yCoord);
    }

    public float getWidth() {
        return width;
    }
}
