package com.example.bhurt7771.shieldknight;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Bee on 2017-06-03.
 */

//Implement runnable so we have a thread and can override the Run method
public class GameView extends SurfaceView implements Runnable {

    //This is our thread
    Thread mGameThread = null;

    //Add a SurfaceHolder: Needed when we use paint and canvas in a thread
    SurfaceHolder mOurHolder;

    //A boolean to determine if the game is currently running
    volatile boolean mPlaying;

    //pause game to start
    boolean mPaused = true;

    //Make Canvas and Paint objects
    Canvas mCanvas;
    Paint mPaint;

    //Keep track of frameRate
    long mFPS;

    //Screen Size (pixels)
    int mScreenX;
    int mScreenY;

    //The Knight
    Knight mKnight;

    //The Princess
    Princess mPrincess;

    //The Assassin
    Assassin mAssassin;

    /*Sound FX will go here if there's time*/

    //The score
    int mScore = 0;

    //Lives
    int mLives = 3;

    //When we call new() on gameView this constructor runs
    public GameView(Context context, int x, int y) {
        //Set up our object using the SurfaceView
        super(context);

        //Set the screen width and height (matches the device height/width)
        mScreenX = x;
        mScreenY = y;

        //Initialize mOurHolder and mPaint objects
        mOurHolder = getHolder();
        mPaint = new Paint();

        //A new Knight
        mKnight = new Knight(mScreenX, mScreenY);

        //A new Princess
        mPrincess = new Princess(mScreenX, mScreenY);

        //A new enemy: remember, more will spawn over a period of time
        mAssassin = new Assassin();

        /*Soundpool and accompanying try catch will go here*/

        setupAndRestart();
    }

    public void setupAndRestart() {
        //Put the Knight back to his start position
        mKnight.reset(mScreenX, mScreenY);

        //if game over reset lives and score
        mScore = 0;
        mLives = 3;
    }

    @Override
    public void run() {
        while (mPlaying) {
            //Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();

            //Update the frame
            if(!mPaused) {
                update();
            }

            //Draw the frame
            draw();

            /*Calculate the FPS this frame. The result is then used to time animations in the update method*/
            long timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                mFPS = 1000 / timeThisFrame;
            }
        }
    }

    public void update() {

        //Move the mKnight if required
        mKnight.update(mFPS);

        mAssassin.update(mFPS);

        //Check for the Assassin colliding with the Knight
        if(RectF.intersects(mKnight.getRect, mAssassin.getRect())) {
            /*if the Assassin and Knight are colliding, have the Assassin reverse direction at
            half its normal speed to emulate being pushed. Also have the knight move forward at
            half its normal speed*/

            /*Check if the Assassin has been pushed past the boundary (over the edge). If
            so, remove it from play and add 50 points to the player's score*/

            /*If the Assassin collides with the princess, subtract 1 life*/

            /*If the knight hits the top or bottom edge of the screen, prevent him from going further*/

            /*If the knight goes past the boundary, subtract 1 life*/
        }
    }

    //Draw everything here
    public void draw() {

        //Make sure the drawing surface is valid to avoid crashing
        if (mOurHolder.getSurface().isValid()) {

            //Lock the mCanvas so it's good to doodle on
            mCanvas = mOurHolder.lockCanvas();

            //Draw the background color
            mCanvas.drawColor(Color.argb(255, 26, 128, 182));

            //Choose the color of the Knight
            mPaint.setColor(Color.argb(255, 255, 255, 255));

            //Draw the mKnight
            mCanvas.drawRect(mKnight.getRect(), mPaint);

            //Choose the color for the Assassin
            mPaint.setColor(Color.argb(360, 100, 0, 1));

            //Draw the mAssassin
            mCanvas.drawRect(mAssassin.getRect(), mPaint);

            //Choose the color for the Princess
            mPaint.setColor(Color.argb(330, 100, 67, 1));

            //Draw the mPrincess
            mCanvas.drawRect(mPrincess.getRect(), mPaint);

            //Choose the color for the score/lives text
            mPaint.setColor(Color.argb(360, 100, 0, 1));

            //Draw the mScore and mLives
            mPaint.setTextSize(35);
            mCanvas.drawText("Score: " + mScore + "   Lives: " + mLives, 10, 50, mPaint);

            //Draw everything to the screen
            mOurHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    //Stop the thread if our Activity is paused or closed
    public void pause() {
        mPlaying = false;
        try {
            mGameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }
    }

    //If the Activity starts/restarts, begin the thread
    public void resume() {
        mPlaying = true;
        mGameThread = new Thread(this);
        mGameThread.start();
    }

    //The SurfaceView class implements an OnTouchListener
    //So we can override the method to check for screen touches
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & motionEvent.ACTION_MASK) {
            //Player has touched the screen
            case MotionEvent.ACTION_DOWN:

                mPaused = false;

                //Check which side the touch is on
                if(motionEvent.getX() > mScreenX / 2) {
                    mKnight.setMovementState(mKnight.RIGHT);
                } else {
                    mKnight.setMovementState(mKnight.LEFT);
                }

                break;

            //Player has removed finger from screen
            case MotionEvent.ACTION_UP:
                mKnight.setMovementState(mKnight.STOPPED);
                break;
        }
        return true;
    }
}
