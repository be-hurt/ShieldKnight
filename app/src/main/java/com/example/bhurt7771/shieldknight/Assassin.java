package com.example.bhurt7771.shieldknight;

import android.graphics.RectF;

/**
 * Created by bhurt7771 on 6/1/2017.
 */

public class Assassin extends Enemy {

    //create a new RectF to hold the 4 coordinates for our Assassin
    private RectF Rect;

    //The size of the Assassin
    static final float SCALE = 0.05f;

    //X is the far left of the rectangle that makes our killer
    private float x;

    //Y is the top coordinate
    private float y;

    //This will hold how many pixels per second the Assassin can move
    private float speed;

    private float screenX;
    private float screenY;

    public Assassin(int screenX, int screenY, float x, float y) {
        //Make the Assassin size relative to the screen resolution
//        width = screenX  * SCALE;

        this.screenX = screenX;
        this.screenY = screenY;
        this.x = x;
        this.y = y;

        speed = screenX * 0.005f;

        //Initialize the Rect that represents the assassin
        Rect = new RectF(x, y, x + screenX * SCALE, y + screenX * SCALE);
    }

    //give access to the Rect
    public RectF getRect() {
        return Rect;
    }

    public void pushAssassin(float dx, float dy) {
        Rect.offset(dx, dy);
    }

    //Make a getter method so we can access the assassin's speed for collision handling
    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float value) {
        this.speed = value;
    }

    //Change the Assassin's position each frame
    public void update(long fps) {
        // TODO: Pass in Princess to move to her
        float dx = (screenX / 2) - x; //delta difference x
        float dy = (screenY / 2) - y; //delta difference y
        //difference between princess and Assassin
        float magnitude = (float) Math.sqrt((dx * dx) + (dy * dy));

        //readjust difX and difY by the factor
        dx *= (speed/magnitude);
        dy *= (speed/magnitude);

        Rect.offset(dx, dy);


    }
}
