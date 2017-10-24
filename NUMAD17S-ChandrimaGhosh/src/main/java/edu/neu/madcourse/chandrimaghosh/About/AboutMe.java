package edu.neu.madcourse.chandrimaghosh.About;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

import edu.neu.madcourse.chandrimaghosh.R;

public class AboutMe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Chandrima Ghosh ");
        setContentView(edu.neu.madcourse.chandrimaghosh.R.layout.activity_about_me);


       // TelephonyManager telecomManager=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        //String imei_code = telecomManager.getDeviceId();
       // Log.d("AboutMe", "IMEI Code obtained is ::: " + imei_code);

        TextView textView;
        textView=(TextView)findViewById(R.id.imei_text_view);
        textView.setText("999977665544333");

    }


}



