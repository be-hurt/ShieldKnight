package com.example.bhurt7771.shieldknight;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener{

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Make a button from the button in our layout
        Button button = (Button) findViewById(R.id.button);
        //Get the high score textView so we can put the highScore into it
        TextView txtHighScore = (TextView) findViewById(R.id.txtHighScore);

        //Make it listen for clicks
        button.setOnClickListener(this);

        prefs = getSharedPreferences("Shield Knight", MODE_PRIVATE);
        int highScore = prefs.getInt("high score", 0);
        txtHighScore.setText("" + highScore);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        i = new Intent(this, GameActivity.class);
        startActivity(i);

    }
}
