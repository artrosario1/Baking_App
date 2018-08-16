package com.softcap.artrosario.bakingapp.activity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.softcap.artrosario.bakingapp.R;
import com.softcap.artrosario.bakingapp.fragment.VideoStepFragment;
import com.softcap.artrosario.bakingapp.model.Step;

import java.util.ArrayList;

public class VideoStepsActivity extends FragmentActivity {

    private ArrayList<Step> steps = new ArrayList<>();
    private Step thisStep;
    private int position;
    private Button previousButton;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_steps);

        Intent intent = getIntent();
        steps = intent.getParcelableArrayListExtra("stepsArray");
        thisStep = intent.getParcelableExtra("ThisStep");

        nextButton = findViewById(R.id.nextButton);
        previousButton = findViewById(R.id.previousButton);
        position = Integer.parseInt(thisStep.stepId);


        if(savedInstanceState == null) {

            VideoStepFragment videoStepFragment = new VideoStepFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.videoStepsContainer, videoStepFragment)
                    .commit();
        }

        View.OnClickListener listener = new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                int position;
                final VideoStepFragment fragment = new VideoStepFragment();

                if (v == findViewById(R.id.nextButton)) {
                    position = Integer.parseInt(thisStep.stepId);
                    if (position != steps.size() - 1) {
                        thisStep = steps.get(position + 1);
                        Bundle bundle = new Bundle();
                        bundle.putInt("Position", position +1);
                        fragment.setArguments(bundle);
                    }

                } else  {
                    position  = Integer.parseInt(thisStep.stepId);
                    if (position != 0) {
                        thisStep = steps.get(position - 1);
                        Bundle bundle = new Bundle();
                        bundle.putInt("Position", position -1);
                        fragment.setArguments(bundle);
                    }
                }
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.videoStepsContainer, fragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.commit();

            }
        };

        previousButton.setOnClickListener(listener);
        nextButton.setOnClickListener(listener);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getFragmentManager().popBackStack();
    }
}
