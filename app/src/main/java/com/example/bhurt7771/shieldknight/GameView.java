package com.example.bhurt7771.shieldknight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static android.R.attr.x;

/**
 * Created by Bee on 2017-06-03.
 */

//Implement runnable so we have a thread and can override the Run method
public class GameView extends SurfaceView implements Runnable {

    //This is our thread
    Thread GameThread = null;

    //Add a SurfaceHolder: Needed when we use paint and canvas in a thread
    SurfaceHolder holder;

    //A boolean to determine if the game is currently running
    volatile boolean playing;

    //pause game to start
    boolean paused = true;

    //Make Canvas and Paint objects
    Canvas canvas;
    Paint paint;

    //Keep track of frameRate
    long frames;

    //Screen Size (pixels)
    int screenX;
    int screenY;

    //Touch coordinates
    float touchX;
    float touchY;

    //The Knight
    Knight knight;

    //The Princess
    Princess princess;

    //The Assassins
    List<Assassin> assassins;

    /*Sound FX will go here if there's time*/

    //The score
    int score = 0;

    //Lives
    int lives = 3;

    long nextSpawn;

    //When we call new() on gameView this constructor runs
    public GameView(Context context, int x, int y) {
        //Set up our object using the SurfaceView
        super(context);

        //Set the screen width and height (matches the device height/width)
        screenX = x;
        screenY = y;

        //Initialize holder and paint objects
        holder = getHolder();
        paint = new Paint();

        //A new Knight
        knight = new Knight(screenX, screenY);

        //A new Princess
        princess = new Princess(screenX, screenY);

        assassins = new ArrayList<>();

        /*Soundpool and accompanying try catch will go here*/

        setupGame();
    }

    public void setupGame() {
        //if game over reset lives and score
        if (lives == 0) {
            score = 0;
            lives = 4;

            nextSpawn = 10;
        }
    }

    @Override
    public void run() {
        while (playing) {
            //Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();

            //Update the frame
            if(!paused) {
                update();
            }

            //Draw the frame
            draw();

            /*Calculate the frames this frame. The result is then used to time animations in the update method*/
            long timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame > 0) {
                frames = 1000 / timeThisFrame;
            }
        }
    }

    public void update() {
        float assassinX;
        float assassinY;

        nextSpawn -= frames;

        //don't forget to limit the number of assassins on screen at once
        if (nextSpawn <= 0 && assassins.size() < 4) {
            Random random = new Random();
            boolean pickedSide = random.nextBoolean();

            assassinY  = random.nextInt(screenY); //change this to getRandomNumberInRange(x, y) when bridge edges are known. This is fine for examples though.

            if(pickedSide) {
                //Spawn on the left
                assassinX = 0;
            } else {
                //spawn on the right
                assassinX = screenX * (1 - Assassin.SCALE); //max width of the screen - Assassin width
            }
            assassins.add(new Assassin(screenX, screenY, assassinX, assassinY));

            nextSpawn = 5000; // Determines number of milliseconds before next enemy spawn: make this happen faster the longer the player lasts (up to a reasonable point anyway)
        }

        //Move the knight if required
        knight.update();

        for (Assassin a: assassins) {
            a.update(frames);
        }

        //Check for the Assassins colliding with the Knight
        for (Iterator<Assassin> it = assassins.iterator(); it.hasNext(); ) {
            Assassin a = it.next();
            if(RectF.intersects(knight.getRect(), a.getRect())) {
                /*if the Assassin and Knight are colliding, have the Assassin reverse direction at
                half its normal speed to emulate being pushed. Also have the knight move forward at
                half its normal speed*/
                knight.setSpeed(screenX * 0.005f);
                a.setSpeed(-(screenX * 0.005f));
                //should instead be pushed in the opposite direction of the knights movement

            } else{
                //reset the speed of the knight and assassin
                knight.setSpeed(screenX * 0.01f);
                a.setSpeed(screenX * 0.005f);
            }

            /*Check if the Assassin has been pushed past the boundary (over the edge). If
                so, remove it from play and add 50 points to the player's score*/

            /*If the Assassin collides with the princess, subtract 1 life and remove the assassin from play*/
            if (RectF.intersects(a.getRect(), princess.getRect())) {
                lives -= 1;
                //maybe have the assassin flash to draw attention to it before killing it?
                it.remove();
            }
        }

        //prevent the knight from passing through the princess
        //TODO: this is glitchy
        if (RectF.intersects(knight.getRect(), princess.getRect())) {
            knight.setSpeed(-(screenX * 0.01f));
        } else{
            knight.setSpeed(screenX * 0.01f);
        }

        /*If the knight goes past the boundary, subtract 1 life*/

        setupGame();
    }

    //Draw everything here
    public void draw() {
        //Make sure the drawing surface is valid to avoid crashing
        if (holder.getSurface().isValid()) {
            //Lock the canvas so it's good to doodle on
            canvas = holder.lockCanvas();

            //Draw the background color
            canvas.drawColor(Color.argb(255, 26, 128, 182));

            //Choose the color of the Knight
            paint.setColor(Color.argb(255, 255, 255, 255));

            //Draw the knight
            canvas.drawRect(knight.getRect(), paint);

            //Choose the color for the Assassin
            paint.setColor(Color.argb(255, 0, 0, 0));

            //Draw the assassins
            for (Assassin a : assassins) {
                canvas.drawRect(a.getRect(), paint);
            }

            //Choose the color for the Princess
            paint.setColor(Color.argb(255, 255, 82, 163));

            //Draw the princess
            canvas.drawRect(princess.getRect(), paint);

            //Choose the color for the score/lives text
            paint.setColor(Color.argb(360, 100, 0, 1));

            //Draw the score and lives
            paint.setTextSize(35);
            canvas.drawText("Score: " + score + "   Lives: " + lives, 10, 50, paint);

            //Draw everything to the screen
            holder.unlockCanvasAndPost(canvas);
        }
    }

    //Stop the thread if our Activity is paused or closed
    public void pause() {
        playing = false;
        try {
            GameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }
    }

    //If the Activity starts/restarts, begin the thread
    public void resume() {
        playing = true;
        GameThread = new Thread(this);
        GameThread.start();
    }

    //The SurfaceView class implements an OnTouchListener
    //So we can override the method to check for screen touches
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & motionEvent.ACTION_MASK) {
            //Player has touched the screen
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                knight.setMovementState(true);
                touchX = motionEvent.getX();
                touchY = motionEvent.getY();
                knight.setDestination(touchX, touchY);
                paused = false;
                break;

            //Player has removed finger from screen
            case MotionEvent.ACTION_UP:
                knight.setMovementState(false);
                break;
        }

        return true;
    }

}
