package edu.neu.madcourse.chandrimaghosh.dictionary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import edu.neu.madcourse.chandrimaghosh.R;

public class AcknowledgementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(edu.neu.madcourse.chandrimaghosh.R.layout.activity_acknowledgement);

        TextView textView;
        textView = (TextView) findViewById(R.id.ack1_textView);
        textView.setText("The main strategy used in the game involves logically splitting the file into many smaller chunks."+
                "The file name corresponds to every unique combination of first three letters and contains all the words beignning with those three letters");
        TextView textView1;
        textView = (TextView) findViewById(R.id.ack2_textView);
        textView.setText("ArrayAdapter code was refered from https://developer.android.com/reference/android/widget/ArrayAdapter.html  ");
        TextView textView2;
        textView = (TextView) findViewById(R.id.ack3_textView);
        textView.setText("beep sound downloaded from opensource freesound.org ");


    }




}
