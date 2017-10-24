package edu.neu.madcourse.chandrimaghosh.dictionary;

import android.content.Context;
import android.content.res.Resources;
import android.content.ContextWrapper;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * Created by chandrimaghosh on 1/30/17.
 */

public  class ReadFile extends AsyncTask <InputStream,String,ArrayList<String>>
{



@Override
    public  ArrayList<String> doInBackground(InputStream... params)

    {
        ArrayList<String> wordsList=new ArrayList<>();
        String word;
        InputStream is=params[0];


        try {


            //get resource id from filename



          //  FileInputStream fstream = new FileInputStream("b.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF8"));

            while((word=br.readLine())!=null)
            {

                wordsList.add(word);
            }



        }
        catch(IOException e)
        {

        }


        return wordsList;
    }
}
