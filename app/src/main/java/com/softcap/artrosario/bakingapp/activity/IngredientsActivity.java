package com.softcap.artrosario.bakingapp.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
import com.softcap.artrosario.bakingapp.adapter.IngredientsAdapter;
import com.softcap.artrosario.bakingapp.fragment.IngredientsFragment;
import com.softcap.artrosario.bakingapp.fragment.StepsFragment;
import com.softcap.artrosario.bakingapp.widget.listViewsService;
import com.softcap.artrosario.bakingapp.model.Ingredient;
import com.softcap.artrosario.bakingapp.model.Recipe;
import com.softcap.artrosario.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

public class IngredientsActivity extends AppCompatActivity{

    List<Ingredient> ingredients = new ArrayList<>();
    private ArrayList<Step> steps = new ArrayList<>();
    private Ingredient[] ingredientsArray;
    Context mContext;
    private Recipe thisRecipe;
    private boolean mTwoPane;
    public IngredientsAdapter ingredientsAdapter;
    private TextView mStepDescription;
    private SimpleExoPlayer simpleExoPlayer;
    private PlayerView mPlayerView;
    private int currentWindow;
    private long playbackPosition;
    private boolean playWhenReady = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        thisRecipe = getIntent().getParcelableExtra("ThisRecipe");
        steps = thisRecipe.getStepList();
        if(findViewById(R.id.linearLayoutPlayerView2) != null){
            mTwoPane = true;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            mStepDescription = findViewById(R.id.tv_step_long_desc2);
            mPlayerView = findViewById(R.id.exoPlayerView2);

        }else{
            mTwoPane = false;

            if(savedInstanceState == null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                IngredientsFragment ingredientsFragment = new IngredientsFragment();
                StepsFragment stepsFragment = new StepsFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.ingredientsContainer,ingredientsFragment)
                        .add(R.id.stepsContainer, stepsFragment)
                        .commit();
            }
        }
        listViewsService.updateWidget(this, thisRecipe);
    }
    public boolean isTwoPane(){
        mTwoPane = findViewById(R.id.LinearLayoutIngredientsAndSteps) != null;
        return mTwoPane;
    }

    public void initializePlayer(String videoURL){
        if(simpleExoPlayer == null) {
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(this),
                    new DefaultTrackSelector(), new DefaultLoadControl()
            );

            mPlayerView.setPlayer(simpleExoPlayer);

            simpleExoPlayer.setPlayWhenReady(playWhenReady);
            simpleExoPlayer.seekTo(currentWindow, playbackPosition);

            Uri uri = Uri.parse(videoURL);
            if(videoURL.isEmpty()){
                mPlayerView.setVisibility(View.INVISIBLE);

            }else {
                mPlayerView.setVisibility(View.VISIBLE);
                MediaSource mediaSource = buildMediaSource(uri);
                simpleExoPlayer.prepare(mediaSource, false, false);
            }
        }
    }

    public void setStepTextView(String stepDescription){
        mStepDescription.setText(stepDescription);
    }


    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        releasePlayer();
        outState.putLong("playback", playbackPosition);

        outState.putInt("window",currentWindow);
        outState.putBoolean("playWhenReady", playWhenReady);
        super.onSaveInstanceState(outState);
    }

    public void releasePlayer() {
        if (simpleExoPlayer != null) {
            playbackPosition = simpleExoPlayer.getCurrentPosition();
            currentWindow = simpleExoPlayer.getCurrentWindowIndex();
            playWhenReady = simpleExoPlayer.getPlayWhenReady();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }
}
