package com.example.bhurt7771.shieldknight;

//import android.support.v7.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.app.Activity;
import android.view.Display;
import android.widget.TextView;

public class GameActivity extends Activity {

    GameView gameView;
    //TODO: finish adding in data persistence for high score
    //A SharedPreferences class for reading data
    SharedPreferences prefs;

    //A sharedPreferences.Editor for writing data
    SharedPreferences.Editor editor;

    private int oldHighScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get a Display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();

        //Load the resolution into a point object
        Point size = new Point();
        display.getSize(size);

        //Initialize the gameView and set it as the view
        gameView = new GameView(this, size.x, size.y);
        setContentView(gameView);

        prefs = getSharedPreferences("Shield Knight", MODE_PRIVATE);
        editor = prefs.edit();
    }

    //This method executes when the player starts the game
    @Override
    protected void onResume() {
        super.onResume();

        //The the gameView resume method to execute
        gameView.resume();
    }

    //This method handles the player quitting the game
    @Override
    protected void onPause() {
        super.onPause();

        //Tell the gameView pause method to execute
        checkHighScore();
        gameView.pause();
    }

    //This method handles checking for and saving the high score. Might need this in the onPause method
    private void checkHighScore() {
        oldHighScore = prefs.getInt("high score", 0);

        if (gameView.getScore() > oldHighScore) {
            editor.putInt("high score", gameView.getScore());
            editor.commit();
        }
    }
}
