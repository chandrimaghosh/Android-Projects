package edu.neu.madcourse.chandrimaghosh.Scraggle.FCM;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.google.firebase.FirebaseException;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.neu.madcourse.chandrimaghosh.Communication.RegisterToTwoPlayerScraggle;
import edu.neu.madcourse.chandrimaghosh.Communication.model.User;
import edu.neu.madcourse.chandrimaghosh.R;

public class UserListActivity extends AppCompatActivity {

    private ListView mplayerList;
    private DatabaseReference mDatabase;
    private ArrayList<String> mplayers;
    private ArrayList<String> mplayerskeys;
    private ArrayList<User> mplayersList;
    private  ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);



        mplayerList = (ListView) findViewById(R.id.playerlist_listview);
        mplayers=new ArrayList<>();
        mplayersList=new ArrayList<>();
        mplayerskeys=new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, mplayers);
        mplayerList.setAdapter(arrayAdapter);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("User");



    }
    @Override
    protected void onPause() {
        super.onPause();
        mplayers.clear();
        arrayAdapter.notifyDataSetChanged();


    }
    @Override
    protected void onResume() {
        super.onResume();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                mplayers.clear();
                arrayAdapter.notifyDataSetChanged();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    //Getting the data from snapshot

                    User person = postSnapshot.getValue(User.class);

                    String key =postSnapshot.getKey();

                    SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(UserListActivity.this);

                    String token=pref.getString("token",null);

                    if (person.getUdId().equalsIgnoreCase(token))
                    {

                    }else{

                        //Adding it to a string
                        mplayerskeys.add(key);
                        mplayersList.add(person);
                        String string = "Email: "+person.getEmail()+"\nCountry: "+person.getCountry()
                                +"\nDeviceId: "+person.getUdId()+"\n\n";
                        mplayers.add(string);
                    }

                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


        mplayerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text

                //code specific to first list item
                Intent myIntent = new Intent(view.getContext(),RequestNewGame.class);
                myIntent.putExtra("Player2_Id",mplayersList.get(position).getUdId());
                myIntent.putExtra("Player2_Key",mplayerskeys.get(position));
                startActivityForResult(myIntent, 0);

            }
        });

    }

}
