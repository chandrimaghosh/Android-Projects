package edu.neu.madcourse.chandrimaghosh.Communication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import edu.neu.madcourse.chandrimaghosh.R;
import edu.neu.madcourse.chandrimaghosh.Scraggle.FCM.LocationManagerActivity;
import edu.neu.madcourse.chandrimaghosh.Scraggle.FCM.UserListActivity;
import edu.neu.madcourse.chandrimaghosh.Scraggle.ScraggleAcknowledgementActivity;
import edu.neu.madcourse.chandrimaghosh.Scraggle.ScraggleMainActivity;
import edu.neu.madcourse.chandrimaghosh.TwoPlayerCommunication.HighScoreActivity;

public class TwoPlayerScraggleMainActivity extends AppCompatActivity {
    private LocationManager locationManager;
    private LocationListener listener;
    TextView loc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player_scraggle_main);
        loc=(TextView)findViewById(R.id.locationupdate_textView);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String add;
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    Address obj = addresses.get(0);
                    add = obj.getCountryName();
                    loc.setText(add);


                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(TwoPlayerScraggleMainActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("userCountry",add);
                    editor.apply();
                    Log.v("IGA", "Address" + add);

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        configure_button();




        Button registerButton = (Button) findViewById(R.id.button_register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TwoPlayerScraggleMainActivity.this, RegisterToTwoPlayerScraggle.class);
                startActivity(intent);
            }
        });
        Button highScoreButton = (Button) findViewById(R.id.highScorersButton);

        highScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TwoPlayerScraggleMainActivity.this, HighScoreActivity.class);
                startActivity(intent);
            }
        });

        Button twoplayerButton = (Button) findViewById(R.id.button_2player);
        twoplayerButton.setVisibility(View.GONE);

        twoplayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Intent intent = new Intent(TwoPlayerScraggleMainActivity.this,UserListActivity.class);
                //  startActivity(intent);
            }
        });
        Button ackbutton = (Button) findViewById(R.id.button_acknowledgements);
        ackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 Intent intent = new Intent(TwoPlayerScraggleMainActivity.this,CommunicationAcknowledgementActivity.class);
                  startActivity(intent);
            }
        });
        Button locationButton = (Button) findViewById(R.id.location_button);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TwoPlayerScraggleMainActivity.this,LocationManagerActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        locationManager.requestLocationUpdates("gps", 5000, 0, listener);

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
