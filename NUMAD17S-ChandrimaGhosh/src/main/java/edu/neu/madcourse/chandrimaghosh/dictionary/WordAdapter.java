package edu.neu.madcourse.chandrimaghosh.dictionary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.neu.madcourse.chandrimaghosh.R;

/**
 * Created by chandrimaghosh on 2/2/17.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    ArrayList<Word> words =null;
    Context context;
    int resource;
    public WordAdapter(Context context,int resource,ArrayList<Word> words)
    {
     super(context,resource);
        this.context=context;
        this.resource=resource;
        this.words=words;



    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Word word= words.get(position);
    if (convertView==null)
    {
        convertView= LayoutInflater.from(context).inflate(R.layout.word_view,parent,false);

    }
        TextView word_text_view=(TextView)convertView.findViewById(R.id.word_text);
        word_text_view.setText((CharSequence) word.toString());

return convertView;
    }
}
