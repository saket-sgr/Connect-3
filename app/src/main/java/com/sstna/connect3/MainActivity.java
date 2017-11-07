package com.sstna.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int yellowScore = 0;
    int redScore = 0;

    //0 = yellow, 1 = red

    int startPlayer=0;
    int activePlayer = startPlayer;

    boolean gameIsActive = true;

    //2 = unplayed
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    public void dropIn(View view){
        TextView yScore = (TextView)findViewById(R.id.ylwScore);
        TextView rScore = (TextView)findViewById(R.id.rdScore);
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if(gameState[tappedCounter]==2 && gameIsActive) {
            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1000f);
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1000f).setDuration(300);
            for(int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2) {
                    gameIsActive = false;
                    String winner = "";
                    if (gameState[winningPosition[0]] == 0) {
                        winner = "Yellow";
                        yellowScore++;
                    } else {
                        winner = "Red";
                        redScore++;
                    }
                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                    winnerMessage.setText(winner + " has won!!");
                    LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                    yScore.setText(String.valueOf(yellowScore));
                    rScore.setText(String.valueOf(redScore));
                    break;
                }
            }
            if(gameIsActive) {
                boolean draw = true;
                for (int counterState : gameState) {
                    if (counterState == 2) {
                        draw = false;
                        break;
                    }
                }
                if (draw) {
                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                    winnerMessage.setText("It's a draw!!");
                    LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void playAgain(View view) {
        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);
        if(startPlayer==0)
            startPlayer=1;
        else
            startPlayer=0;
        activePlayer = startPlayer;
        gameIsActive = true;
        for(int i = 0; i<gameState.length; i++) {
            gameState[i] = 2;
        }
        GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);
        for(int i =0; i<gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
        String str="Red";
        if(startPlayer==0)
            str="Yellow";
        Toast toast = Toast.makeText(this,str + " Starts!!", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public void restart(View view) {
        yellowScore = 0;
        redScore = 0;
        startPlayer=1;
        TextView yScore = (TextView)findViewById(R.id.ylwScore);
        TextView rScore = (TextView)findViewById(R.id.rdScore);
        yScore.setText("0");
        rScore.setText("0");
        playAgain(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast toast = Toast.makeText(this,"Yellow Starts!!", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}
