package edu.neu.madcourse.chandrimaghosh.Scraggle;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.neu.madcourse.chandrimaghosh.About.AboutMe;
import edu.neu.madcourse.chandrimaghosh.R;
import edu.neu.madcourse.chandrimaghosh.dictionary.AcknowledgementActivity;

public class ScraggleMainActivity extends AppCompatActivity {
    public static final String KEY_SAVED_GAME = "saved_game_key";
    public static final String KEY_RESTORE="key_restore";
    public static final String SERVER_KEY="key=AAAAUlv_sjk:APA91bFNoKkkoujNOSbRYNBUlM_r_qR7u3PcZgMZjTJxGla7Oxub90NSHGbgkWn_Ymmd-5ndTmJC54fsaTWlDdd-m0c0Jz3UKAspXOrwkb7e4MY8dIQs-0Xk5SCzAeBngNL-dESDc0XQ";

    MediaPlayer mMediaPlayer;
    // ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scraggle_main);

        Button continueButton=(Button)findViewById(R.id.continue_scraggle_button);
        Button ackButton=(Button)findViewById(R.id.ack_scraggle_button);
        Button insButton=(Button)findViewById(R.id.instruction_button);

        ackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ScraggleMainActivity.this,MemorQuestionActivity.class);
                startActivity(intent);
            }
        });
        insButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScraggleMainActivity.this,InstructionsActivity.class);
                startActivity(intent);

            }
        });
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                continueGame(view);
            }
        });
    }

//new game
    public void  openNewGame(View view)
    {

        Intent intent = new Intent(this,ScraggleOnePlayerActivity.class);
        startActivity(intent);
    }

//continue game


    public void continueGame(View view)

    {
        Intent intent =new Intent(this,ScraggleOnePlayerActivity.class);
        intent.putExtra(ScraggleOnePlayerActivity.KEY_RESTORE,true);
        startActivity(intent);
    }




    @Override
    protected void onResume() {
        super.onResume();
        mMediaPlayer = MediaPlayer.create(this, R.raw.africa_jam);
        mMediaPlayer.setVolume(0.5f, 0.5f);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        mMediaPlayer.release();
    }









}
