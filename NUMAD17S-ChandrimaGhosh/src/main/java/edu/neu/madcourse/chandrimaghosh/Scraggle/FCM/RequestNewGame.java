package edu.neu.madcourse.chandrimaghosh.Scraggle.FCM;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import edu.neu.madcourse.chandrimaghosh.Communication.RegisterToTwoPlayerScraggle;
import edu.neu.madcourse.chandrimaghosh.R;
import edu.neu.madcourse.chandrimaghosh.TwoPlayerCommunication.TwoPlayerCommunicationActivity;

public class RequestNewGame extends AppCompatActivity {
    private   String CLIENT_REGISTRATION_TOKEN ;
    private static final String SERVER_KEY = "key=AAAAUlv_sjk:APA91bFNoKkkoujNOSbRYNBUlM_r_qR7u3PcZgMZjTJxGla7Oxub90NSHGbgkWn_Ymmd-5ndTmJC54fsaTWlDdd-m0c0Jz3UKAspXOrwkb7e4MY8dIQs-0Xk5SCzAeBngNL-dESDc0XQ";
    String Player1Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_new_game);

        Intent intent = getIntent();
        final String player2Id = intent.getStringExtra("Player2_Id");
        final String player2Key = intent.getStringExtra("Player2_Key");
        CLIENT_REGISTRATION_TOKEN=player2Id;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("CLIENT_KEY", player2Id);
        editor.apply();



         Player1Email = preferences.getString("Player1Email", null);
        Button sendInvite=(Button)findViewById(R.id.send_invite_button);
        sendInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable())
                {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        pushNotification();
                    }
                }).start();
                Intent myIntent = new Intent(view.getContext(),TwoPlayerCommunicationActivity.class);

                myIntent.putExtra("Player2_Id",player2Id);
                myIntent.putExtra("Player2_Key",player2Key);
                    myIntent.putExtra("two","twoPlayerFirstMove");
                startActivityForResult(myIntent, 0);
                }
            else
            {Toast toast = Toast.makeText(RequestNewGame.this, "No Internet Connection", Toast.LENGTH_LONG);
                toast.show(); }}
        });

    }


    private void pushNotification() {
        JSONObject jPayload = new JSONObject();
        JSONObject jNotification = new JSONObject();
        try {
            jNotification.put("title",Player1Email+"challenged you to Scraggle" );
            jNotification.put("body", "You Have been challenged");
            jNotification.put("sound", "default");
            jNotification.put("badge", "1");
            jNotification.put("click_action", "ACTIVITY_XPTO");

            // If sending to a single client
            jPayload.put("to", CLIENT_REGISTRATION_TOKEN);

            /*
            // If sending to multiple clients (must be more than 1 and less than 1000)
            JSONArray ja = new JSONArray();
            ja.put(CLIENT_REGISTRATION_TOKEN);
            // Add Other client tokens
            ja.put(FirebaseInstanceId.getInstance().getToken());
            jPayload.put("registration_ids", ja);
            */

            jPayload.put("priority", "high");
            jPayload.put("notification", jNotification);

            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", SERVER_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Send FCM message content.
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jPayload.toString().getBytes());
            outputStream.close();

            // Read FCM response.
            InputStream inputStream = conn.getInputStream();
            final String resp = convertStreamToString(inputStream);

            Handler h = new Handler(Looper.getMainLooper());
            h.post(new Runnable() {
                @Override
                public void run() {
                    Log.e("TAG", "run: " + resp);
                    Toast.makeText(RequestNewGame.this,resp,Toast.LENGTH_LONG);
                }
            });
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next().replace(",", ",\n") : "";
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
