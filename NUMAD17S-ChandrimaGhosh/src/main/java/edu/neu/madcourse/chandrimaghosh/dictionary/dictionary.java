package edu.neu.madcourse.chandrimaghosh.dictionary;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.text.Editable;
import android.os.Vibrator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import  edu.neu.madcourse.chandrimaghosh.R;

public class dictionary extends AppCompatActivity {
    ArrayList<String> file=new ArrayList<>();
    ArrayList<String> wordList=new ArrayList<>();
    ArrayAdapter adapter;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(edu.neu.madcourse.chandrimaghosh.R.layout.activity_dictionary);
        Toolbar toolbar = (Toolbar) findViewById(edu.neu.madcourse.chandrimaghosh.R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Test Dictionary");
         adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,wordList);

        ListView listView = (ListView) findViewById(R.id.hint_listview);
        listView.setAdapter(adapter);
        TextView textView;
        textView=(TextView)findViewById(R.id.dict_text);

        textView.addTextChangedListener(new TextWatcher (){

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before,
                                                                      int count) {

                                                String wordEntered = s.toString().trim().toLowerCase();


                                                if (wordEntered.length() == 3) {
                                                    String filename;
                                                     //handle java keywords
                                                    if (wordEntered.contains("new")||wordEntered.contains( "try")
                                                            ||wordEntered.contains("for")||wordEntered.contains("con")
                                                            ||wordEntered.contains("int")) {
                                                        filename = eliminateKeywords(s.toString().trim());
                                                    }
                                                     else {
                                                        filename = wordEntered;
                                                    }

                                                    readFromile(filename);


                                                    Iterator<String> wordIterator = file.iterator();
                                                    while (wordIterator.hasNext()) {
                                                        //System.out.println(wordIterator.next());
                                                        String word = wordIterator.next();
                                                        if (word.length() == s.toString().trim().length()&& word.equalsIgnoreCase(s.toString().trim())) {

                                                            int duplicate=0;
                                                            Iterator<String> wordDuplicateIterator = wordList.iterator();
                                                            while (wordDuplicateIterator.hasNext()) {
                                                                if (wordEntered.equalsIgnoreCase(wordDuplicateIterator.next()))
                                                                {
                                                                    duplicate++;
                                                                }

                                                            }


                                                            if (duplicate==1){
                                                                //do not add duplicate

                                                            }else{

                                                            adapter.add(word);
                                                            adapter.notifyDataSetChanged();
                                                            mediaPlayer = MediaPlayer.create(dictionary.this, R.raw.beep);
                                                            mediaPlayer.start();
                                                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                                            v.vibrate(100);
                                                        }}


                                                    }

                                                }


                                                if (s.toString().trim().length() > 3) {

                                                    Iterator<String> wordIterator = file.iterator();
                                                    while (wordIterator.hasNext()) {

                                                        String word = wordIterator.next();
                                                        String wordInput = s.toString().trim().toLowerCase();



                                                        if (word.length() == s.toString().trim().length() && word.equalsIgnoreCase(wordInput)) {


                                                            //check for duplicate
                                                            int duplicate=0;
                                                            Iterator<String> wordDuplicateIterator = wordList.iterator();
                                                            while (wordDuplicateIterator.hasNext()) {
                                                                if (wordInput.equalsIgnoreCase(wordDuplicateIterator.next()))
                                                                {
                                                                    duplicate++;
                                                                }

                                                            }


                                                            if (duplicate==1){
                                                                //do not add duplicate

                                                            }
                                                            else{adapter.add(word);
                                                                adapter.notifyDataSetChanged();
                                                                mediaPlayer = MediaPlayer.create(dictionary.this, R.raw.beep);
                                                                mediaPlayer.start();
                                                                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                                                v.vibrate(100);


                                                            }

                                                        }


                                                    }



                                                }
                                            }
                                            @Override
                                            public void afterTextChanged(Editable s) {



                                            }
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }


                                        }
        );





    }


    public String eliminateKeywords(String key)
    {
        String eliminatedKeyWord=key+"1";
        return  eliminatedKeyWord;
    }

  public void readFromile(String filename)
  {

      Context context=getApplicationContext();
      Resources res = context.getResources();
      int wordListId = res.getIdentifier(filename, "raw", context.getPackageName());
      InputStream is;
      try {
          is = getResources().openRawResource(wordListId);
          ReadFile readFile=new ReadFile();
          file=readFile.doInBackground (is);
          System.out.println("returned file");

      }catch (Exception e){
          e.printStackTrace();
      }


  }
    public void openAck(View view)
    {
        Intent intent =new Intent(this,edu.neu.madcourse.chandrimaghosh.dictionary.AcknowledgementActivity.class);
        startActivity(intent);
    }

    public void returnToMenu(View view)
    {

        dictionary.this.finish();

    }

    public void clearText(View view)

    {
        TextView textView;
        textView = (TextView) findViewById(R.id.dict_text);
        textView.setText("");
        adapter.clear();
        adapter.notifyDataSetChanged();
    }



}