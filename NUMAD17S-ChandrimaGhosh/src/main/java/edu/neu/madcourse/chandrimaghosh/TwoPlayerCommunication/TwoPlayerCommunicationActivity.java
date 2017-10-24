package edu.neu.madcourse.chandrimaghosh.TwoPlayerCommunication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.chandrimaghosh.R;
import edu.neu.madcourse.chandrimaghosh.Scraggle.ScraggleControlFragment;
import edu.neu.madcourse.chandrimaghosh.Scraggle.ScraggleOnePlayerActivity;
import edu.neu.madcourse.chandrimaghosh.Scraggle.ScroggleOnePlayerGameFragment;

public class TwoPlayerCommunicationActivity extends AppCompatActivity {
    public static final String KEY_RESTORE = "key_restore";
    public static final String PREF_RESTORE = "pref_restore";
    public static final String PHASE_VALUE = "phase_value";
    private MediaPlayer mMediaPlayer;
    private Handler mHandler = new Handler();
    private ScraggleTwoPlayerGameFragment mGameFragment;
    private ScraggleControlFragment mControlFragment;
    List<String> nineWords = new ArrayList<>();
    TextView mTextField;
    TwoPlayerCommunicationActivity.MyCount counter;
    private final int interval = 90000; //90 seconds ; 1 minute 30 seconds
    long savedRemainingInterval = 0;
    String resultStr = "";
    String finalResult = "";
    String result = "";
    ArrayList<String> vocabList = new ArrayList<String>();
    String insertedText = "";
    private AlertDialog mDialog;
    Boolean resFlag = false;
    public TextView mScoreTextField;
    public TextView mWordTextField;
    int score = 0;
    int score2=0;
    boolean restore;
   public boolean isResumeFlag = false;
    private boolean isPhaseTwo=false;
    private String gameData = "";
    private Context appContext = this;
    private boolean isGameEnd = false;
    String player1id;
    String Player1Email;
    static final String STATE_SCORE = "playerScore";
    static final String STATE_LEVEL = "playerLevel";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_scraggle_two_player);
        Bundle b = this.getIntent().getExtras();

        if (b != null) {
            isPhaseTwo = b.getBoolean("isTwoFlag");
            gameData = b.getString("gameData");
        }

        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(TwoPlayerCommunicationActivity.this);
        player1id=pref.getString("Player1id",null);
        Player1Email = pref.getString("Player1Email", null);

        Intent intent = getIntent();
        final String player2Id = intent.getStringExtra("Player2_Id");
        final String player2Key = intent.getStringExtra("Player2_Key");


        mGameFragment = (ScraggleTwoPlayerGameFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_game_two_scraggle);

        mControlFragment = (edu.neu.madcourse.chandrimaghosh.Scraggle.ScraggleControlFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_game_control_scraggle);

        mTextField = (TextView) findViewById(R.id.timer);

        mScoreTextField = (TextView) findViewById(R.id.scoretwoPlayer);
        mWordTextField = (TextView) findViewById(R.id.word_textView);

        restore = getIntent().getBooleanExtra(KEY_RESTORE, false);


        if (restore) {
            String gameData = getPreferences(MODE_PRIVATE)
                    .getString(PREF_RESTORE, null);
            Log.e("ScraggleActivity", "gameData = " + gameData);
            if (gameData != null) {
                mGameFragment.putState(gameData);
            }
        }



        if (savedRemainingInterval > 0) {

            counter = new TwoPlayerCommunicationActivity.MyCount(savedRemainingInterval, 1000);
        } else {

            counter = new TwoPlayerCommunicationActivity.MyCount(interval, 1000);

        }


    }




    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume", "inside resume");

        if (!isResumeFlag) {
            isResumeFlag = true;
            if (savedRemainingInterval > 0 && isResumeFlag) {
                counter = new TwoPlayerCommunicationActivity.MyCount(savedRemainingInterval, 1000);
                counter.start();
            } else {
                counter = new TwoPlayerCommunicationActivity.MyCount(interval, 1000);
                counter.start();
            }
        }

        mMediaPlayer = MediaPlayer.create(this, R.raw.tabla);
        mMediaPlayer.start();
        mMediaPlayer.setLooping(true);

        Log.e("onResume:Restore", Boolean.toString(restore));
        Log.e("onResume:isPhaseTwo", Boolean.toString(isPhaseTwo));


        if (restore) {
            String gameData = getPreferences(MODE_PRIVATE)
                    .getString(PREF_RESTORE, null);
            Log.e("ScraggleActivity", "gameData = " + gameData);
            if (gameData != null) {
                mGameFragment.putState(gameData);
            }
        }

        if (!isPhaseTwo) {
            mScoreTextField.setText("Score = " + String.valueOf(score));
        } else {
            mScoreTextField.setText("Score = " + String.valueOf(score2));
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(null);
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        mMediaPlayer.release();
        counter.cancel();
        if (isResumeFlag) {
            isResumeFlag = false;
        }

        String gameData = mGameFragment.getState();

        getPreferences(MODE_PRIVATE).edit()
                .putString(PREF_RESTORE, gameData)
                .commit();
        Log.d("Scraggle", "state = " + gameData);

    }
    public void onPauseGame() {
        Log.e("onPauseGame", "inside pause");
        counter.cancel();
        mMediaPlayer.pause();
        mGameFragment.disableLetterGrid();
        isResumeFlag = false;
    }

    public void onResumeGame() {
        Log.e("onResumeGame", "inside pause");
        counter = new TwoPlayerCommunicationActivity.MyCount(savedRemainingInterval, 1000);
        counter.start();
        mMediaPlayer.start();
        mGameFragment.enableLetterGrid();
        isResumeFlag = true;
    }

    public void onQuitGame() {
        Log.e("onQuit", "inside pause");

        counter.cancel();
        mMediaPlayer.pause();
        isResumeFlag = false;
    }

    public void toogleMute() {

        if (mMediaPlayer.isPlaying()) {

            mMediaPlayer.pause();
        } else {

            mMediaPlayer.start();
        }
    }

    //timer
    public class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            if (!isPhaseTwo) {//phaseq1

                if(true) {
                    Log.e("onFinish", "Done");
                    mTextField.setText("            DONE");
                    mGameFragment.disableLetterGrid();

                    AlertDialog.Builder builder = new AlertDialog.Builder(appContext);

                    builder.setTitle("Game Over");
                    builder.setMessage("Phase Two Begins:" +"\n"+
                            "All The words you got right appears in phase2 again" +"\n"+
                            "The goal is to select all those words and select additional words ");
                    builder.setCancelable(false);
                    builder.setPositiveButton(R.string.ok_label,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // take to Phase 2
                                 //   isPhaseTwo = true;
                                    savedRemainingInterval = 0;
                                    Toast.makeText(getApplicationContext(),String.valueOf(getScore()),Toast.LENGTH_LONG).show();
                                    Toast.makeText(getApplicationContext(),String.valueOf(getScore2()),Toast.LENGTH_LONG).show();



//



                                    finish();
                                }
                            });
                    mDialog = builder.show();
                } else {

                    mTextField.setText("GAME OVER");
                    AlertDialog.Builder builder = new AlertDialog.Builder(appContext);
                    builder.setTitle("Game up");
                    builder.setCancelable(false);
                    builder.setPositiveButton(R.string.ok_label,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            });
                    mDialog = builder.show();
                }
            } else {
                mTextField.setText("            GAME OVER");
                mControlFragment.getView().setVisibility(View.INVISIBLE);
                mGameFragment.setGameData("");
                gameData="";
                AlertDialog.Builder builder = new AlertDialog.Builder(appContext);
                builder.setTitle("Game Ended");
                builder.setMessage("Score  in Phase 1 is"+getScore2()+"\n"+"Score  in Phase 1 is"+getScore2()+"\n");
                if(getScore2()<getScore())
                {
                    builder.setMessage("Final Score:0"+"\n"+" Sorry You Had to score more than phase 1");
                }else
                {
                    int finalscore=getScore()+getScore2();
                    builder.setMessage("Final Score:"+finalscore+"\n"+" Well Played");

                }
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.ok_label,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });
                mDialog = builder.show();
                isGameEnd = true;

            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int secs = (int) (millisUntilFinished / 1000);
            int seconds = secs % 60;
            int minutes = secs / 60;
            String stringTime = String.format("%02d:%02d", minutes, seconds);
            if (stringTime.equals("00:05") || stringTime.equals("00:04") || stringTime.equals("00:03") ||
                    stringTime.equals("00:02") || stringTime.equals("00:01")) {
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.timer);
                mTextField.startAnimation(animation1);
                mTextField.setText("            Time: " + stringTime);
            } else {

                mTextField.setText("            Time: " + stringTime);
            }

            savedRemainingInterval = millisUntilFinished;
        }
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public boolean isPhaseTwo() {
        return isPhaseTwo;
    }

    public void setPhaseTwo(boolean phaseTwo) {
        isPhaseTwo = phaseTwo;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public long getSavedRemainingInterval() {
        return savedRemainingInterval;
    }

    public void setSavedRemainingInterval(long savedRemainingInterval) {
        this.savedRemainingInterval = savedRemainingInterval;
    }

}

