package edu.neu.madcourse.chandrimaghosh.Scraggle.FCM;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.neu.madcourse.chandrimaghosh.Communication.RegisterToTwoPlayerScraggle;
import edu.neu.madcourse.chandrimaghosh.Communication.model.User;
import edu.neu.madcourse.chandrimaghosh.R;
import edu.neu.madcourse.chandrimaghosh.TwoPlayerCommunication.TwoPlayerCommunicationActivity;


public class GameExchangeActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    String player1id;
    String Player1Email;
    EditText exchangeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_exchange);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("User");
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(GameExchangeActivity.this);

         player1id=pref.getString("Player1id",null);

        Player1Email = pref.getString("Player1Email", null);

        Intent intent = getIntent();
        final String player2Id = intent.getStringExtra("Player2_Id");
        final String player2Key = intent.getStringExtra("Player2_Key");





        Button exchange=(Button)findViewById(R.id.exchange_button);
         exchangeText=(EditText)findViewById(R.id.exchange_text_edittext);








        exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isNetworkAvailable()) {

                    Intent intent = new Intent(GameExchangeActivity.this, TwoPlayerCommunicationActivity.class);
                    intent.putExtra("two","twoPlayer");
                    startActivityForResult(intent, 0);



                    // mDatabase.child(player1id).child("gameData").setValue("   "+Player1Email+":  "+exchangeText.getText().toString());
                }
                else
                {Toast toast = Toast.makeText(GameExchangeActivity.this, "No Internet Connection", Toast.LENGTH_LONG);
                    toast.show(); }

            }
        });


        mDatabase.addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        User user = dataSnapshot.getValue(User.class);

                        if (dataSnapshot.getKey().equalsIgnoreCase(player1id)) {



                        } else {


                        }
                        Log.e("onChildAdded", "onChildAdded: dataSnapshot = " + dataSnapshot.getValue());
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        User user = dataSnapshot.getValue(User.class);
                        exchangeText.setText(user.getGameData());

                        if (dataSnapshot.getKey().equalsIgnoreCase(player1id)) {
                            exchangeText.setText(user.getGameData());

                        }
                        else {
                            exchangeText.setText(user.getGameData());

                        }
                        Log.v("OnChildChanged", "onChildChanged: "+dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("onCancelled", "onCancelled:" + databaseError);
                    }
                }
        );

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

