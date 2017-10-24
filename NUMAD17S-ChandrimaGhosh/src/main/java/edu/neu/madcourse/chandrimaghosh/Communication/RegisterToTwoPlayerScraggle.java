package edu.neu.madcourse.chandrimaghosh.Communication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.neu.madcourse.chandrimaghosh.Communication.model.User;
import edu.neu.madcourse.chandrimaghosh.MainActivity;
import edu.neu.madcourse.chandrimaghosh.R;
import edu.neu.madcourse.chandrimaghosh.Scraggle.FCM.UserListActivity;

import static edu.neu.madcourse.chandrimaghosh.R.raw.two;

public class RegisterToTwoPlayerScraggle extends AppCompatActivity {
    SharedPreferences prefs;
    private DatabaseReference mDatabase;
    private ArrayList<User> mplayersList;
    private ArrayList<String> mplayerskeys;
    private LocationManager locationManager;
    private LocationListener listener;
    SharedPreferences pref;
    int index=0;
    private AlertDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_to_two_player_scraggle);
        final EditText email = (EditText) findViewById(R.id.email_register_edittext);
        final EditText refreshToken = (EditText) findViewById(R.id.token_edittext);

         pref = PreferenceManager.getDefaultSharedPreferences(RegisterToTwoPlayerScraggle.this);

        String token = pref.getString("token", null);



        refreshToken.setText(token);
        mplayersList = new ArrayList<>();
        mplayerskeys = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("User");




        Button registerButton = (Button) findViewById(R.id.registerToCloud_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                int repeatCount=0;

              for (int i=0;i<mplayersList.size();i++) {


                    User temp2 = (User) mplayersList.get(i);
                    String Udid = temp2.getUdId();

                    if (Udid.equalsIgnoreCase(refreshToken.getText().toString()))
                    {
                    repeatCount++;
                        index=i;
                    }

                }//for close


                if (repeatCount>0)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterToTwoPlayerScraggle.this,
                            AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

                    builder.setTitle("Alert");
                    builder.setMessage("User Already registered");

                    builder.setCancelable(false);
                    builder.setPositiveButton("Proceed to Game",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String player1id = (String) mplayerskeys.get(index);
                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(RegisterToTwoPlayerScraggle.this);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("Player1id", player1id);
                                    editor.apply();

                                    Intent intent = new Intent(RegisterToTwoPlayerScraggle.this,UserListActivity.class);
                                    startActivity(intent);

                                }
                            });


                    mDialog = builder.show();
                }else{
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(RegisterToTwoPlayerScraggle.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Player1Email",email.getText().toString());
                    editor.apply();
                    String userCountry = preferences.getString("userCountry", null);
                    registerUserToFireBase(email.getText().toString(), refreshToken.getText().toString(),userCountry);
                }
            }

        });



    }






    public  void registerUserToFireBase(String email,String refreshToken,String userCountry)

    {

        if (isNetworkAvailable()) {

            User user = new User(email, refreshToken,"", "",0,userCountry);

            DatabaseReference postref = mDatabase.push();

            postref.setValue(user);

            String player1id = postref.getKey();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Player1id", player1id);
            editor.apply();

            Intent intent = new Intent(RegisterToTwoPlayerScraggle.this,UserListActivity.class);
            startActivity(intent);
        }
        else
        {Toast toast = Toast.makeText(RegisterToTwoPlayerScraggle.this, "No Internet Connection", Toast.LENGTH_LONG);
            toast.show(); }

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    @Override
    protected void onResume() {
        super.onResume();


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    //Getting the data from snapshot

                    User person = postSnapshot.getValue(User.class);

                    String key =postSnapshot.getKey();
                    mplayersList.add(person);
                    mplayerskeys.add(key);

                }

            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
}

