package ba.terawatt.xogame;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/*
 Created by Emir on 1.7.2015

 */
public class XOGameActivity extends Activity{

    private XOGame mGame;
    private Button mBoardbuttons[];

    private TextView mPlayerOneCount;
    private TextView mPlayerTwoCount;
    private TextView mPlayerOneText;
    private TextView mPlayerTwoText;

    private int mPlayerOneCounter = 0;
    private int mPlayerTwoCounter = 0;

    private boolean mPlayerOneFirst = true;
    private boolean mIsSinglePlayer = false;
    private boolean mIsPlayerOneTurn = true;
    private boolean mGameOver = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);



               mBoardbuttons = new Button[XOGame.BOARD_SIZE];
        mBoardbuttons[0] = (Button) findViewById(R.id.one);
        mBoardbuttons[1] = (Button) findViewById(R.id.two);
        mBoardbuttons[2] = (Button) findViewById(R.id.three);
        mBoardbuttons[3] = (Button) findViewById(R.id.four);
        mBoardbuttons[4] = (Button) findViewById(R.id.five);
        mBoardbuttons[5] = (Button) findViewById(R.id.six);
        mBoardbuttons[6] = (Button) findViewById(R.id.seven);
        mBoardbuttons[7] = (Button) findViewById(R.id.eight);
        mBoardbuttons[8] = (Button) findViewById(R.id.nine);

        mPlayerOneCount = (TextView) findViewById(R.id.humanCount);
        mPlayerTwoCount = (TextView) findViewById(R.id.androidCount);
        mPlayerOneText = (TextView) findViewById(R.id.human);
        mPlayerTwoText = (TextView) findViewById(R.id.android);

        mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
        mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));

        mGame = new XOGame();
        startNewGame(true);

        if(Build.VERSION.SDK_INT >= 21){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.statusBarColor));
        }

    }
    private void startNewGame(boolean isSingle){

        this.mIsSinglePlayer = isSingle;

        mGame.clearBoard();
        for(int i = 0; i<mBoardbuttons.length ; i++){
            mBoardbuttons[i].setText("");
            mBoardbuttons[i].setEnabled(true);
            mBoardbuttons[i].setOnClickListener(new ButtonClickListener(i));
        }
        if (mIsSinglePlayer){
            mPlayerOneText.setText(R.string.human);
            mPlayerTwoText.setText(R.string.android);

            if(mPlayerOneFirst){
                mPlayerOneFirst=false;
            } else {
                int move = mGame.getComputerMove();
                setMove(mGame.PLAYER_TWO, move);
                mPlayerOneFirst=true;
            }
        } else {
            mPlayerOneText.setText(R.string.player1);
            mPlayerTwoText.setText(R.string.player2);
        }

        mGameOver = false;
    }
    private class ButtonClickListener implements View.OnClickListener{
        int location;
        public ButtonClickListener(int location){
            this.location = location;
        }
        @Override
        public void onClick(View v) {
            if (!mGameOver){
                if (mBoardbuttons[location].isEnabled()){
                    if(mIsSinglePlayer){
                        setMove(mGame.PLAYER_ONE, location);
                        int winner = mGame.chechForWinner();
                        if(winner == 0){
                            int move = mGame.getComputerMove();
                            setMove(mGame.PLAYER_TWO, move);
                            winner = mGame.chechForWinner();
                        }
                         if (winner == 1){
                             Snackbar.make(findViewById(R.id.coordinator), "DRAW", Snackbar.LENGTH_LONG).show();
                             mGameOver=true;
                             new Handler().postDelayed(new Runnable() {
                                 @Override
                                 public void run() {
                                     startNewGame(true);
                                 }
                             }, 800);
                        }
                        else if (winner == 2){
                            mPlayerOneCounter++;
                            mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
                             Snackbar.make(findViewById(R.id.coordinator), "YOU WIN", Snackbar.LENGTH_LONG).show();
                            mGameOver=true;
                             new Handler().postDelayed(new Runnable() {
                                 @Override
                                 public void run() {
                                     startNewGame(true);
                                 }
                             }, 800);

                        }
                        else if(winner == 3){
                            mPlayerTwoCounter++;
                             mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));
                             Snackbar.make(findViewById(R.id.coordinator), "ANDROID WIN", Snackbar.LENGTH_LONG).show();
                            mGameOver=true;
                             new Handler().postDelayed(new Runnable() {
                                 @Override
                                 public void run() {
                                     startNewGame(true);
                                 }
                             }, 800);
                        }
                    } else {
                        if(mIsPlayerOneTurn)
                            setMove(mGame.PLAYER_ONE, location);
                        else
                            setMove(mGame.PLAYER_TWO, location);

                        int winner = mGame.chechForWinner();
                        if(winner == 0){
                            if(mIsPlayerOneTurn){
                                mIsPlayerOneTurn = false;
                            } else{
                                mIsPlayerOneTurn = true;
                            }
                        } else if (winner == 1){
                            mGameOver=true;
                            Snackbar.make(findViewById(R.id.coordinator), "DRAW", Snackbar.LENGTH_LONG).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startNewGame(true);
                                }
                            }, 800);
                        }
                        else if (winner == 2){
                            mPlayerOneCounter++;
                            mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
                            Snackbar.make(findViewById(R.id.coordinator), "YOU WIN", Snackbar.LENGTH_LONG).show();
                            mGameOver=true;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startNewGame(true);
                                }
                            }, 800);
                        }
                        else {
                            mPlayerTwoCounter++;
                            mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));
                            Snackbar.make(findViewById(R.id.coordinator), "ANDROID WIN", Snackbar.LENGTH_LONG).show();
                            mGameOver=true;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startNewGame(true);
                                }
                            }, 800);
                        }

                    }

                }
            }
        }
    }
    public void setMove(char player, int location){
        mGame.setMove(player, location);
        mBoardbuttons[location].setEnabled(false);
        mBoardbuttons[location].setText(String.valueOf(player));

        if(player == mGame.PLAYER_ONE)
            mBoardbuttons[location].setTextColor(ContextCompat.getColor(this, R.color.XColor));
        else
            mBoardbuttons[location].setTextColor(ContextCompat.getColor(this, R.color.OColor));
    }


}

