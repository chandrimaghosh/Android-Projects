package edu.neu.madcourse.chandrimaghosh.Scraggle;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import edu.neu.madcourse.chandrimaghosh.R;
import edu.neu.madcourse.chandrimaghosh.dictionary.ReadFile;
import edu.neu.madcourse.chandrimaghosh.ut3.GameActivity;
import edu.neu.madcourse.chandrimaghosh.ut3.Tile;
import static android.content.Context.MODE_PRIVATE;
import static edu.neu.madcourse.chandrimaghosh.R.raw.log;
import static edu.neu.madcourse.chandrimaghosh.R.raw.sum;
import static edu.neu.madcourse.chandrimaghosh.Scraggle.ScraggleOnePlayerActivity.PREF_RESTORE;


public class ScroggleOnePlayerGameFragment extends android.app.Fragment {
    ArrayList<String> file=new ArrayList<>();
    private Handler mHandler = new Handler();
    private String nineWords []={"","","","","","","","",""};
    private ScroggleTile mEntireBoard = new ScroggleTile(this);
    private ScroggleTile mLargeTiles[] = new ScroggleTile[9];
    private ScroggleTile mSmallTiles[][] = new ScroggleTile[9][9];
    private  int lastMoveInEachLargeBlock  []={-1,-1,-1,-1,-1,-1,-1,-1,-1};
    static private int mLargeIds[] = {R.id.wglarge1, R.id.wglarge2, R.id.wglarge3,
            R.id.wglarge4, R.id.wglarge5, R.id.wglarge6, R.id.wglarge7, R.id.wglarge8,
            R.id.wglarge9};
    static private int mSmallIds[] = {R.id.wgsmall1, R.id.wgsmall2, R.id.wgsmall3,
            R.id.wgsmall4, R.id.wgsmall5, R.id.wgsmall6, R.id.wgsmall7, R.id.wgsmall8,
            R.id.wgsmall9};
    private int mLastLarge;
    private int mLastSmall;
    private float mVolume = 1f;
    private long savedInterval;
    private int sum = 0;
    private int sum2 = 0;
    private boolean isPhaseTwo = false;
    private boolean isTwoPlayer = false;
    private String gameData = "";
    private  boolean isRestore;
    private String resotreData;
    private int mSoundX, mSoundO, mSoundMiss, mSoundRewind;
    private SoundPool mSoundPool;
    Vibrator v;
    public ScroggleOnePlayerGameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this fragment across configuration changes.
        setRetainInstance(true);
        initGame();
        Bundle b = this.getActivity().getIntent().getExtras();
        if (b != null) {
            isPhaseTwo = b.getBoolean("isTwoFlag");
            gameData = b.getString("gameData");
            isRestore=b.getBoolean(ScraggleOnePlayerActivity.KEY_RESTORE);
        }

        if (isRestore)
        {
             gameData = this.getActivity().getPreferences(MODE_PRIVATE)
                .getString(PREF_RESTORE, null);
            Log.e("ScraggleActivity", "gameData = " + gameData);
            if (gameData!= null && (!(gameData.equals("")))) {
                String[] fields = gameData.split(",");
                String phase = fields[2];
                isPhaseTwo = Boolean.valueOf(phase);
            }

        }


        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        mSoundX = mSoundPool.load(getActivity(), R.raw.shnur_drum_freesound_org, 1);
        mSoundO = mSoundPool.load(getActivity(), R.raw.shnur_drum_freesound_org, 1);
        mSoundMiss = mSoundPool.load(getActivity(), R.raw.bertrof_game_sound_wrong_freesound_org, 1);
        mSoundRewind = mSoundPool.load(getActivity(), R.raw.ohyeah, 1);
        v = (Vibrator) this.getActivity().getSystemService(Context.VIBRATOR_SERVICE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View rootView = inflater.inflate(R.layout.fragment_scroggle_one_player_game, container, false);

            if (isRestore||isPhaseTwo) {

            String gameData = this.getActivity().getPreferences(MODE_PRIVATE)
                    .getString(PREF_RESTORE, null);
                if(gameData==null)
                {
                    initViews(rootView);
                }
               else  if (gameData != null && gameData!="") {
                    Log.e("ScraggleActivity", "gameData = " + gameData);
                putState(gameData);
            initViews(rootView);
        }
        }

        else {//initial
            initViews(rootView);
        }
        return rootView;

    }

    public void initGame() {

        mEntireBoard = new ScroggleTile(this);
        // Create all the tiles
        for (int large = 0; large < 9; large++) {
            mLargeTiles[large] = new ScroggleTile(this);
            for (int small = 0; small < 9; small++) {
                mSmallTiles[large][small] = new ScroggleTile(this);
            }
            mLargeTiles[large].setSubTiles(mSmallTiles[large]);
        }
        mEntireBoard.setSubTiles(mLargeTiles);
        mLastSmall = -1;
        mLastLarge = -1;

    }


    private void initViews(View rootView) {

        String[] words = generateWords();
        mEntireBoard.setView(rootView);
        for (int large = 0; large < 9; large++) {
            String eachWord = words[large];
            char[] wordtoCharArray;
            wordtoCharArray = eachWord.toCharArray();


            View outer = rootView.findViewById(mLargeIds[large]);
            mLargeTiles[large].setView(outer);

            for (int small = 0; small < 9; small++) {
                Button inner = (Button) outer.findViewById
                        (mSmallIds[small]);
                final int fLarge = large;
                final int fSmall = small;
                final ScroggleTile smallTile = mSmallTiles[large][small];
                smallTile.setView(inner);
                smallTile.setLargePosition(large);
                smallTile.setSmallPosition(small);
                String wordToSet;
                try {
                     wordToSet = String.valueOf(wordtoCharArray[small]);
                }catch (Exception e)
                {   wordToSet ="Computer";
                    Log.e("ScraggleActivity",String.valueOf(wordtoCharArray[small]));
                }

                inner.setBackgroundColor(Color.parseColor("#FF5733"));


                if(isRestore) {
                    inner.setText(smallTile.getInnerText());
                    if (smallTile.isSelected()) {
                        inner.setBackgroundColor(Color.parseColor("#FFC300"));
                    }
                    if (smallTile.isPartOfWord()) {
                        inner.setBackgroundColor(Color.parseColor("#12ffc3"));
                    }

                }else if (isPhaseTwo)
                {
                  //seed PhaseTwo

                    if(smallTile.isPartOfWord())
                    {
                        smallTile.setPartOfWord(false);
                        smallTile.setSelected(false);
                        inner.setText(smallTile.getInnerText());
                        }

                    else
                    {
                        smallTile.setPartOfWord(false);
                        smallTile.setSelected(false);
                        Random random = new Random();
                        int r = random.nextInt(26);
                        String randomWord = Character.toString((char) (r + 97));
                        inner.setText(randomWord);
                        smallTile.setInnerText(randomWord);
                    }



                }else if(isTwoPlayer)
                {}
                else {
                    inner.setText(wordToSet);
                    smallTile.setInnerText(wordToSet);
                    smallTile.setSelected(false);
                }

                inner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        smallTile.animate();
                        if (smallTile.isSelected()) {

                            //already selected
                            Toast.makeText(getActivity().getApplicationContext(), "Long Press to Submit Word !", Toast.LENGTH_SHORT).show();
                        } else {

                            if (isValidMove(smallTile, mLastSmall, mLastLarge)) {
                                v.vibrate(50);
                                mSoundPool.play(mSoundX, mVolume, mVolume, 1, 0, 1f);

                                view.setBackgroundColor(Color.parseColor("#FFC300"));
                                smallTile.setSelected(true);
                                mLastLarge = fLarge;
                                mLastSmall = fSmall;
                                lastMoveInEachLargeBlock[mLastLarge] = mLastSmall;
                                addtonineWords(smallTile.getInnerText(), smallTile.getLargePosition());

                            }
                            else{
                                Toast.makeText(getActivity().getApplicationContext(), "Left ,Right or Diagonal Only !", Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    }
                });
                // ...
                inner.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        //if(!isPhaseTwo) {//same logic for 2 phases

                            if (smallTile.isSelected()&&(!(smallTile.isPartOfWord()))){

                                if (CheckWord(smallTile.getInnerText(), smallTile.getLargePosition())) {
                                    if( !(detectRepeatWords( smallTile.getLargePosition())))
                                    {
                                        smallTile.setPartOfWord(true);
                                        v.setBackgroundColor(Color.parseColor("#12ffc3"));
                                        updateAllTiles(smallTile.getLargePosition(), smallTile.getSmallPosition());

                                        if (!isPhaseTwo)
                                        {sum=calculateScoreForPhaseOne(smallTile.getLargePosition());
                                    ((ScraggleOnePlayerActivity) getActivity()).setScore(sum);
                                            ((ScraggleOnePlayerActivity) getActivity()).mScoreTextField.setText("Score =" + Integer.toString(sum));
                                        }else
                                        {sum2=calculateScoreForPhaseTwo(smallTile.getLargePosition());

                                            ((ScraggleOnePlayerActivity) getActivity()).setScore2(sum2);

                                            ((ScraggleOnePlayerActivity) getActivity()).mScoreTextField.setText("Score =" + Integer.toString(sum2));
                                        }


                                        mSoundPool.play(mSoundRewind, mVolume, mVolume, 1, 0, 1f);
                                        String word=nineWords[smallTile.getLargePosition()];
                                        ((ScraggleOnePlayerActivity) getActivity()).mWordTextField.setText(word);

                                        Toast.makeText(getActivity().getApplicationContext(), "Good Job", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(getActivity().getApplicationContext(), "Repeat not allowed", Toast.LENGTH_SHORT).show();

                                    }


                                }
                                if (!(CheckWord(smallTile.getInnerText(), smallTile.getLargePosition())))

                                {mSoundPool.play(mSoundMiss, mVolume, mVolume, 1, 0, 1f);

                                    if (isPhaseTwo) {
                                        sum2 = sum2 - 2;
                                        ((ScraggleOnePlayerActivity) getActivity()).setScore2(sum2);
                                        ((ScraggleOnePlayerActivity) getActivity()).mScoreTextField.setText("Score ="+Integer.toString(sum2));
                                    }else{sum = sum - 2;

                                        ((ScraggleOnePlayerActivity) getActivity()).setScore(sum);
                                        ((ScraggleOnePlayerActivity) getActivity()).mScoreTextField.setText("Score ="+Integer.toString(sum));}



                                    Toast.makeText(getActivity().getApplicationContext(), "Wrong Word", Toast.LENGTH_SHORT).show();

                                }


                            }
                       // }
                        return true;
                    }
                });
            }
        }
    }

    public Boolean detectRepeatWords(int large)

    {

        for (int i=0;i<9;i++)
        {
            if (nineWords[i].equalsIgnoreCase(nineWords[large])&&i!=large)
            {return true;}
        }

        return false ;

    }


    private int calculateScoreForPhaseOne(int large)

    { char[] previousWord=nineWords[large].toCharArray();


        for (int i=0;i<previousWord.length;i++)
        {
            sum=sum+getScore(previousWord[i]);
        }
        return sum;

    }
    private int calculateScoreForPhaseTwo(int large)

    { char[] previousWord=nineWords[large].toCharArray();


        for (int i=0;i<previousWord.length;i++)
        {
            sum2=sum2+getScore(previousWord[i]);
        }
        return sum2;

    }

    //assign score
    private int getScore(char c) {
        int score = 0;
        if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'n' ||
                c == 'l' || c == 'r' || c == 's' || c == 't') {
            score = 1;
        } else if (c == 'd' || c == 'g') {
            score = 2;
        } else if (c == 'b' || c == 'c' || c == 'm' || c == 'p') {
            score = 3;
        } else if (c == 'f' || c == 'h' || c == 'v' || c == 'w' || c == 'y') {
            score = 4;
        } else if (c == 'k') {
            score = 5;
        } else if (c == 'j' || c == 'x') {
            score = 8;
        } else if (c == 'q' || c == 'z') {
            score = 10;
        }
        return score;
    }

    private void updateAllTiles(int large,int small)
    {
        for (int i=0;i<9;i++)

        {
            if( mSmallTiles[large][i].isSelected())
            {
            mSmallTiles[large][i].getView().setBackgroundColor(Color.parseColor("#12ffc3"));
            mSmallTiles[large][i].setPartOfWord(true);}
        }

    }

    private void addtonineWords(String charPressed,int large)
    {

      String previousWord=nineWords[large];
        previousWord=previousWord+charPressed;
        nineWords[large]=previousWord;
        Log.e("ninewords",nineWords[large]);
    }


 private  Boolean CheckWord(String charPressed,int large)
 {
     String filename=nineWords[large];
     if(filename.length()>=3) {
         if (filename.substring(0, 3).contains("new") || filename.substring(0, 3).contains("try")
                 || filename.substring(0, 3).contains("for") || filename.substring(0, 3).contains("con")
                 || filename.substring(0, 3).contains("int")) {
             filename = eliminateKeywords(filename.substring(0, 3));
         }

         readFromile(filename.substring(0, 3));
         Iterator<String> wordIterator = file.iterator();
         while (wordIterator.hasNext()) {
             String word = wordIterator.next();
             if (filename.equalsIgnoreCase(word)) {
                 return true;
             }


         }
     }
     return false;

 }



private boolean switchedBlock(ScroggleTile tile,int lastsmall,int lastlarge)
{
    if (tile.getLargePosition()!=lastlarge)
    {
        return true;
    }
    else
    {return false;
    }

}

    public String eliminateKeywords(String key)
    {
        String eliminatedKeyWord=key+"1";
        return  eliminatedKeyWord;
    }

    public void readFromile(String filename)
    {

        Context context=this.getActivity().getApplicationContext();
        Resources res = context.getResources();
        int wordListId = res.getIdentifier(filename, "raw", context.getPackageName());
        InputStream is;
        try {
            is = getResources().openRawResource(wordListId);
            ReadFile readFile=new ReadFile();
            file=readFile.doInBackground (is);

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private  boolean isValidMove(ScroggleTile tile,int lastsmall,int lastlarge) {
        boolean possible = true;
        List<Integer> intList = new ArrayList<Integer>();
        List<Integer> intList2 = new ArrayList<Integer>();

        if (lastsmall == -1 || lastlarge == -1) //first move
        {
            return possible;
        }
        if ((tile.getLargePosition() != lastlarge))//switch board
        {
            intList2 = getNeighbors(lastMoveInEachLargeBlock[tile.getLargePosition()]);

            if (intList2.contains(tile.getSmallPosition())) {
                return possible;
            } else {
                return !possible;
            }

        } else //same board

        {

            intList = getNeighbors(lastsmall);
            if (intList.contains(tile.getSmallPosition())) {
                return possible;
            }else
            {
                return false;
            }

        }
    }


    //method to generate random words
    public String[] generateWords() {

        Random random = new Random();
        int r = random.nextInt(26);
        String[] nineWords = new String[9];
        String fileName = Character.toString((char) (r + 97));

        Log.d("Scraggle", "FileName=" + fileName);
        Resources res = getActivity().getResources();
        int fileId = res.getIdentifier(fileName, "raw", getActivity().getPackageName());
        Log.d("Scraggle", "FileID=" + fileId);
        if (fileId != 0) {
            InputStream is = getResources().openRawResource(fileId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String str = "";
            int counter = 0;
            if (is != null) {

                try {
                    int i = 0;
                    while ((str = reader.readLine()) != null && i < 9) {
                        nineWords[i] = str;
                        i++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return nineWords;
    }

    //function that creates the list of possible position for a given position.

    private List<Integer> getNeighbors(Integer i) {
        List<Integer> intList = new ArrayList<Integer>();
        switch (i) {
            case -1:
                intList.addAll(Arrays.asList(0, 1, 2, 3, 5, 6, 7, 8));

            case 0:
                intList.addAll(Arrays.asList(1, 3, 4));
                break;
            case 1:
                intList.addAll(Arrays.asList(0, 2, 3, 4, 5));
                break;
            case 2:
                intList.addAll(Arrays.asList(1, 4, 5));
                break;
            case 3:
                intList.addAll(Arrays.asList(0, 1, 4, 6, 7));
                break;
            case 4:
                intList.addAll(Arrays.asList(0, 1, 2, 3, 5, 6, 7, 8));
                break;
            case 5:
                intList.addAll(Arrays.asList(1, 2, 4, 7, 8));
                break;
            case 6:
                intList.addAll(Arrays.asList(3, 4, 7));
                break;
            case 7:
                intList.addAll(Arrays.asList(3, 4, 5, 6, 8));
                break;
            case 8:
                intList.addAll(Arrays.asList(4, 5, 7));
                break;
        }
        return intList;
    }


    //Saving the state of the game

    public String getState() {
        Log.e("SAVING GAME STATE", "in");
        StringBuilder builder = new StringBuilder();
        builder.append(((ScraggleOnePlayerActivity) this.getActivity()).getSavedRemainingInterval());
        builder.append(',');
        builder.append(((ScraggleOnePlayerActivity) this.getActivity()).getScore());
        builder.append(',');
        builder.append(((ScraggleOnePlayerActivity) this.getActivity()).getScore2());
        builder.append(',');
        builder.append(((ScraggleOnePlayerActivity) this.getActivity()).isPhaseTwo());
        builder.append(',');
        builder.append(mLastLarge);
        builder.append(',');
        builder.append(mLastSmall);
        builder.append(',');
        for (int large = 0; large < 9; large++) {
            for (int small = 0; small < 9; small++) {
                builder.append(mSmallTiles[large][small].getInnerText());
                builder.append(',');
                builder.append(mSmallTiles[large][small].isSelected());
                builder.append(',');
                builder.append(mSmallTiles[large][small].isPartOfWord());
                builder.append(',');

            }

        }
        Log.e("SAVING GAME STATE", "out");
        return builder.toString();
    }


    protected void disableLetterGrid() {
        mEntireBoard.getView().setVisibility(View.INVISIBLE);
    }

    protected void enableLetterGrid() {
        mEntireBoard.getView().setVisibility(View.VISIBLE);
    }

    public boolean isPhaseTwo() {
        return isPhaseTwo;
    }

    public void setPhaseTwo(boolean phaseTwo) {
        isPhaseTwo = phaseTwo;
    }


    /*restore the state of the app*/

    public void putState(String gameData) {
        Log.e("RESTORING GAME STATE", "in");
        String[] fields = gameData.split(",");
        int index = 0;
        savedInterval = Long.parseLong(fields[index++]);
        ((ScraggleOnePlayerActivity) this.getActivity()).setSavedRemainingInterval(savedInterval);
        sum = Integer.parseInt(fields[index++]);
        ((ScraggleOnePlayerActivity) this.getActivity()).setScore(sum);
        sum2 = Integer.parseInt(fields[index++]);
        ((ScraggleOnePlayerActivity) this.getActivity()).setScore2(sum2);

        isPhaseTwo = Boolean.parseBoolean(fields[index++]);
        ((ScraggleOnePlayerActivity) this.getActivity()).setPhaseTwo(isPhaseTwo);

        if (isPhaseTwo)
        {
            mLastLarge = -1;
            mLastSmall = -1;
            index++;
            index++;
        }else
        {
            mLastLarge = Integer.parseInt(fields[index++]);
            mLastSmall = Integer.parseInt(fields[index++]);
        }
        for (int large = 0; large < 9; large++) {
            for (int small = 0; small < 9; small++) {
                String innerText = (fields[index++]);
                mSmallTiles[large][small].setInnerText(innerText);
                Boolean isSelected = Boolean.valueOf(fields[index++]);
                mSmallTiles[large][small].setSelected(isSelected);
                Boolean isPartOfWord = Boolean.valueOf(fields[index++]);
                mSmallTiles[large][small].setPartOfWord(isPartOfWord);


            }
            Log.e("RESTORING GAME STATE", "out");
        }

    }

    public int getSum2() {
        return sum2;
    }

    public void setSum2(int sum2) {
        this.sum2 = sum2;
    }

    public String getGameData() {
        return gameData;
    }

    public void setGameData(String gameData) {
        this.gameData = gameData;
    }
}
