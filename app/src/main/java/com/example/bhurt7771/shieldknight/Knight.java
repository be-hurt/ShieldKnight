package com.example.bhurt7771.shieldknight;

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

    //Which ways the Knight can move
    public final int STOPPED = 0;
    public final int MOVING = 1;
    //TODO: Find a way to determine if the knight is moving or not (if the player is touching the screen, yes, if not, no. Act accordingly)

    //Keep track of if the knight is moving
    private int knightMoving = STOPPED;

    //The screen length and width in pixels
    private int screenX;
    private int screenY;

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
        yCoord = screenY / 2 + (width * 2);

        rect = new RectF(xCoord, yCoord, xCoord + width, yCoord + height);

        //How fast is the Knight (pixels per second)?
        speed = screenX / 2;
        // Cover half the screen in a second. Might need to slow this down.
    }

    //Make a getter method so we can draw the knight in the GameView
    public RectF getRect() {
        return rect;
    }

    //This method will be used for the switch statement in gameView to set how the knight is moving
    public void setMovementState(int state) {
        knightMoving = state;
    }

    //This updates the knights movement and changes the coordinates in mRect if needed
    public void update(float touchX, float touchY) {

        //Make sure the knight doesn't leave the screen
        if(rect.left < 0) {
            xCoord = 0;
        }

        if(rect.right > screenX) {
            xCoord = screenX - (rect.right - rect.left);
        }

        if(rect.top < 0) {
            yCoord = 0;
        }

        if(rect.bottom > screenY) {
            yCoord = screenY - (rect.bottom - rect.top);
        }

        //TODO: Figure out how to get the motionEvent to handle the knights' movement here
        int touchPointX = (int)touchX;
        int touchPointY = (int)touchY;

        float dx = touchPointX - xCoord; //delta difference x
        float dy = touchPointY - yCoord; //delta difference y

        //difference between tap point and Knight
        float magnitude = (float) Math.sqrt((dx * dx) + (dy * dy));

        //readjust difX and difY by the factor
        dx *= (speed/magnitude);
        dy *= (speed/magnitude);

        rect.offset(dx, dy);

        //Update the knight graphics
        rect.left = xCoord;
        rect.right = xCoord + width;
    }


}
