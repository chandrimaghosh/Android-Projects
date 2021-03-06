package edu.neu.madcourse.chandrimaghosh.TwoPlayerCommunication;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.view.View;

import edu.neu.madcourse.chandrimaghosh.R;
import edu.neu.madcourse.chandrimaghosh.Scraggle.ScroggleOnePlayerGameFragment;
import edu.neu.madcourse.chandrimaghosh.Scraggle.ScroggleTile;
import edu.neu.madcourse.chandrimaghosh.ut3.Tile;

/**
 * Created by chandrimaghosh on 3/22/17.
 */

public class ScraggleTwoPlayerTile {
    public enum Owner {
        X, O /* letter O */, NEITHER, BOTH
    }

    // These levels are defined in the drawable definitions
    private static final int LEVEL_X = 0;
    private static final int LEVEL_O = 1; // letter O
    private static final int LEVEL_BLANK = 2;
    private static final int LEVEL_AVAILABLE = 3;
    private static final int LEVEL_TIE = 3;
    private final ScraggleTwoPlayerGameFragment mTwoGame;
    private Tile.Owner mOwner = Tile.Owner.NEITHER;
    private View mView;
    private ScraggleTwoPlayerTile mSubTiles[];
    private  boolean isSelected;
    private int largePosition;
    private int smallPosition;
    private boolean isPartOfWord;

    public boolean isPartOfWord() {
        return isPartOfWord;
    }

    public void setPartOfWord(boolean partOfWord) {
        isPartOfWord = partOfWord;
    }

    public int getLargePosition() {
        return largePosition;
    }

    public void setLargePosition(int largePosition) {
        this.largePosition = largePosition;
    }

    public int getSmallPosition() {
        return smallPosition;
    }

    public void setSmallPosition(int smallPosition) {
        this.smallPosition = smallPosition;
    }

    private  String innerText;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public ScraggleTwoPlayerTile(ScraggleTwoPlayerGameFragment game) {
        this.mTwoGame = game;
    }

    public View getView() {
        return mView;
    }

    public void setView(View view) {
        this.mView = view;
    }

    public Tile.Owner getOwner() {
        return mOwner;
    }

    public void setOwner(Tile.Owner owner) {
        this.mOwner = owner;
    }

    public ScraggleTwoPlayerTile[] getSubTiles() {
        return mSubTiles;
    }

    public void setSubTiles(ScraggleTwoPlayerTile[] subTiles) {
        this.mSubTiles = subTiles;
    }


    public String getInnerText() {
        return innerText;
    }

    public void setInnerText(String innerText) {
        this.innerText = innerText;
    }

    public void animate() {
        Animator anim = AnimatorInflater.loadAnimator(mTwoGame.getActivity(),
                R.animator.tictactoe);
        if (getView() != null) {
            anim.setTarget(getView());
            anim.start();
        }
    }
}
