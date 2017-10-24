package edu.neu.madcourse.chandrimaghosh.Scraggle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import edu.neu.madcourse.chandrimaghosh.R;

import static edu.neu.madcourse.chandrimaghosh.R.id.textView;

public class ScraggleAcknowledgementActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scraggle_acknowledgement);

        textView = (TextView) findViewById(R.id.acknowledgements_textView);
        textView.setText("Sounds are downloaded from opensource freesound.org "+
        "Data Structres is heavily Inspired from Burnette, Ed, Hello, Android: Introducing Google's Mobile Development Platform");


    }



}
