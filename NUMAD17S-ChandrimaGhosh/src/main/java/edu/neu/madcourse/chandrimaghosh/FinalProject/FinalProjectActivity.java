package edu.neu.madcourse.chandrimaghosh.FinalProject;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import edu.neu.madcourse.chandrimaghosh.R;


public class FinalProjectActivity extends AppCompatActivity {
    private AlertDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_project);

       TextView appDescTextView=(TextView)findViewById(R.id.appDescTextView) ;

        appDescTextView.setText("Won’t it be exciting to reflect back on your past week in a form of a memory\n" +
                "\n" +
                "Quiz Game? What’s even more exciting is, as you recollect or retrospect your last week through the quiz,\n" +
                "\n" +
                "Our App builds you a Beautiful story with those answers simultaneously and effortlessly.");
    }


    public void goToApp(View v){
        try{
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("syedamanalam.madcourse.neu.edu.numad17s_syedamanalam");
        startActivity(launchIntent);
    }catch (ActivityNotFoundException e)
        {

        }catch (Exception e){

        }
    }



    /**
     * Opens Acknowledgement Dialog Box
     */
    public void onAckWearableActivityClick(View view) {
        Log.i("Acknowledgements", "Ack Dialog Box");

        StringBuffer str = new StringBuffer();
        str.append("<b><u>Memor-Me</u></b><br><br>");
        str.append("<u>Acknowledgements</u><br>");
        str.append("1.) Professor Intille<br>");
        str.append("2.) TAs : Anirudh and dhara<br>");
        str.append("3.) The custom fonts used in diary are downloaded from https://goo.gl/PdeMgI<br>");
        str.append("4.)The final Project Icon is taken from https://goo.gl/3LXYNX <br>");
        str.append("5.) The location picker in maps is implemented using google maps api's tutorials " +
                " https://developers.google.com/places/android-api/placepicker<br>");
        str.append("6.)The Diary Icon is openSource <br>");
        str.append("7.)The custom fonts used in diary are downloaded from https://goo.gl/PdeMgI<br>");
        str.append(" https://code.tutsplus.com/tutorials/how-to-recognize-user-activity-with-activity-recognition--cms-25851");
        str.append(" publicly available code snippets from github");
        str.append(".) Tested on the following Android Phones:<br>");
        str.append("      a.) Nexus6P API 25<br>");
        str.append("      b.) Nexus 5 API 23<br>");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(Html.fromHtml(str.toString()));
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.ok_label,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // nothing
                    }
                });
        mDialog = builder.show();
    }
}
