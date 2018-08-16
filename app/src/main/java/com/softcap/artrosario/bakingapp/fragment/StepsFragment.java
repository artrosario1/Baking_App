package com.softcap.artrosario.bakingapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.softcap.artrosario.bakingapp.R;
import com.softcap.artrosario.bakingapp.activity.IngredientsActivity;
import com.softcap.artrosario.bakingapp.activity.VideoStepsActivity;
import com.softcap.artrosario.bakingapp.adapter.IngredientsAdapter;
import com.softcap.artrosario.bakingapp.adapter.StepsAdapter;
import com.softcap.artrosario.bakingapp.model.Ingredient;
import com.softcap.artrosario.bakingapp.model.Recipe;
import com.softcap.artrosario.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

public class StepsFragment extends Fragment implements StepsAdapter.StepClickHandler {
    private Recipe thisRecipe;
    private Step thisStep;
    private ArrayList<Step> steps = new ArrayList<>();
    private List<Integer> mStepIds;
    private int mStepIndex;
    private StepsAdapter.StepClickHandler mStepClickHandler;
    boolean isTwoPane;


    public StepsFragment(){

    }
    public interface OnStepSelectedListener{
        public void onStepSelected(int position);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);
        RecyclerView stepsRV = rootView.findViewById(R.id.rv_ƒragment_steps);
        thisRecipe = getActivity().getIntent().getParcelableExtra("ThisRecipe");
        mStepClickHandler = this;

        if(savedInstanceState != null){
            steps = savedInstanceState.getParcelableArrayList("StepList");

            StepsAdapter stepsAdapter = new StepsAdapter(steps,R.layout.layout_step_card, getContext(), mStepClickHandler);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

            stepsRV.setLayoutManager(linearLayoutManager);
            stepsRV.setAdapter(stepsAdapter);
        }
        else{
            steps = thisRecipe.getStepList();
            StepsAdapter stepsAdapter = new StepsAdapter(steps,R.layout.layout_step_card,getContext(), mStepClickHandler);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

            stepsRV.setLayoutManager(linearLayoutManager);
            stepsRV.setAdapter(stepsAdapter);
        }

        return rootView;
    }

    @Override
    public void onStepClick(View view, Step step) {
        isTwoPane = ((IngredientsActivity) getActivity()).isTwoPane();
        if(isTwoPane){
            ((IngredientsActivity) getActivity()).releasePlayer();
            ((IngredientsActivity) getActivity()).initializePlayer(step.videoURL);
            ((IngredientsActivity)getActivity()).setStepTextView(step.stepDescription);

        }else {
            Context context = getContext();
            Intent intent = new Intent(context, VideoStepsActivity.class);
            intent.putExtra("ThisStep", step);
            intent.putExtra("stepsArray", steps);
            startActivity(intent);
        }
    }

    public void setStepIds(List<Integer> stepIds){
        mStepIds = stepIds;
    }
    public void setListIndex(int stepIndex){
        mStepIndex = stepIndex;
    }


}
