package edu.neu.madcourse.chandrimaghosh.Scraggle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import edu.neu.madcourse.chandrimaghosh.R;

import static edu.neu.madcourse.chandrimaghosh.R.id.textView;

public class InstructionsActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        textView = (TextView) findViewById(R.id.instructions_textView);
        textView.setText("Select characters from a 9x9 grid with 9x9 letters in the grid ." +
                "\n"+" Each smaller grid has been populated with 9-letter words using Boggle rules (up/down/diagonal letters are connected)."+
                "\n"+  "Points are alloted  with classic Scrable rules.Select the longest words possible"+
        "\n"+"Long Press the selected letters to make word");


    }
}
