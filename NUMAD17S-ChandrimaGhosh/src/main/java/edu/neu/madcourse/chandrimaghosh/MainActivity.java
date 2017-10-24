package edu.neu.madcourse.chandrimaghosh;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import edu.neu.madcourse.chandrimaghosh.Communication.TwoPlayerScraggleMainActivity;
import edu.neu.madcourse.chandrimaghosh.FinalProject.FinalProjectActivity;
import edu.neu.madcourse.chandrimaghosh.Scraggle.FCM.GameExchangeActivity;
import edu.neu.madcourse.chandrimaghosh.ut3.MainActivityTicTacToe;
import edu.neu.madcourse.chandrimaghosh.About.AboutMe;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Chandrima Ghosh");
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button twoPlayerWordGame=(Button)findViewById(R.id.twoplayerwordgame_button);

        Button finalButton=(Button)findViewById(R.id.finalBotton);


        twoPlayerWordGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,TwoPlayerScraggleMainActivity.class);
                startActivity(intent);
            }
        });

        finalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,FinalProjectActivity.class);
                startActivity(intent);
            }
        });

    }

    public void openAboutMe(View view)
    {
        Intent intent = new Intent(this,AboutMe.class);
        startActivity(intent);


    }
    public void openMemoryGame(View view)
    {
        Intent intent = new Intent(this,AboutMe.class);
        startActivity(intent);

    }

    public void  throwErrorFromHome(View view)
    {
        PackageManager pm = this.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage("wontfindthisinruntime");
        startActivity(intent);
    }

    public  void onCommunication(View view)
      {Intent intent =new Intent(this,TwoPlayerScraggleMainActivity.class);
          startActivity(intent);

      }


    public void launchTicTacToe(View view)
    {
        Intent intent =new Intent(this,MainActivityTicTacToe.class);
        startActivity(intent);
    }

    public void openDictionary(View view)
    {
        Intent intent =new Intent(this,edu.neu.madcourse.chandrimaghosh.dictionary.dictionary.class);
        startActivity(intent);
    }


    public void openScraggleMain(View view)
    {
        Intent intent =new Intent(this,edu.neu.madcourse.chandrimaghosh.Scraggle.ScraggleMainActivity.class);
        startActivity(intent);

    }
    public void quitApplication(View view)
    {
        this.finish();
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
