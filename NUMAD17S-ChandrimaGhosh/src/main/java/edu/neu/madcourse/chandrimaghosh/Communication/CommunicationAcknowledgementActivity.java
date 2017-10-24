package edu.neu.madcourse.chandrimaghosh.Communication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import edu.neu.madcourse.chandrimaghosh.R;

public class CommunicationAcknowledgementActivity extends AppCompatActivity {
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication_acknowledgement);
        textView = (TextView) findViewById(R.id.com_ack_textView);
        textView.setText("FirebaseDemo supplied By Anirudh and Dhara "+"\n"+
                "Data Structres is heavily Inspired from Burnette, Ed, Hello, Android: Introducing Google's Mobile Development Platform");

    }
}
