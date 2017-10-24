package edu.neu.madcourse.chandrimaghosh.Scraggle;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.neu.madcourse.chandrimaghosh.R;
import edu.neu.madcourse.chandrimaghosh.TwoPlayerCommunication.TwoPlayerCommunicationActivity;

public class ScraggleControlFragment extends Fragment{

    boolean isPaused=false;
    boolean isMuted=false;

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public boolean isMuted() {
        return isMuted;
    }

    public void setMuted(boolean muted) {
        isMuted = muted;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =
                inflater.inflate(R.layout.fragment_scraggle_control, container, false);

      final  Button pause =(Button) rootView.findViewById(R.id.play_pause_button);


        View quit = rootView.findViewById(R.id.quit_button);
         View muteToggle = rootView.findViewById(R.id.mute_unmute_button);



        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TwoPlayerCommunicationActivity) getActivity()).onQuitGame();
                getActivity().finish();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(((TwoPlayerCommunicationActivity) getActivity()).isResumeFlag){
                    ((TwoPlayerCommunicationActivity) getActivity()).onPauseGame();
                    pause.setText("PLAY");

                }else
                {
                    ((TwoPlayerCommunicationActivity) getActivity()).onResumeGame();
                    pause.setText("PAUSE");
                }




            }
        });

        muteToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TwoPlayerCommunicationActivity) getActivity()).toogleMute();

            }
        });


        return rootView;
    }



}
