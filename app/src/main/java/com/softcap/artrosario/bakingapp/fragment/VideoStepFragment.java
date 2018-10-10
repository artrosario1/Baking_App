package com.softcap.artrosario.bakingapp.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.softcap.artrosario.bakingapp.R;
import com.softcap.artrosario.bakingapp.model.Step;

import java.io.InputStream;
import java.util.ArrayList;

public class VideoStepFragment extends Fragment {
    private Step thisStep;
    private ArrayList<Step> steps = new ArrayList<>();
    private SimpleExoPlayer simpleExoPlayer;
    private PlayerView mPlayerView;
    private TextView mStepDescription;
    private ImageView mThumbnail;
    private int currentWindow;
    private long playbackPosition;
    private boolean playWhenReady = true;
    private Button previousButton;
    private Button nextButton;

    public static VideoStepFragment newInstance(int position){
        VideoStepFragment videoStepFragment = new VideoStepFragment();
        Bundle args = new Bundle();
        args.putInt("Position", position);
        videoStepFragment.setArguments(args);
        return videoStepFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);
        Bundle bundle = this.getArguments();
        mPlayerView = rootView.findViewById(R.id.exoPlayerView);
        mStepDescription = rootView.findViewById(R.id.tv_step_long_desc);
        previousButton = getActivity().findViewById(R.id.previousButton);
        mThumbnail = getActivity().findViewById(R.id.iv_thumbnail);
        nextButton = getActivity().findViewById(R.id.nextButton);
        steps = getActivity().getIntent().getParcelableArrayListExtra("stepsArray");

        if(bundle !=null){
            int position = bundle.getInt("Position", 0);
            thisStep = steps.get(position);
            if(position == 0){
                previousButton.setVisibility(View.INVISIBLE);
            }
            else if(position == steps.size()-1){
                nextButton.setVisibility(View.INVISIBLE);
            }
            else{
                previousButton.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.VISIBLE);
            }


        }
        else {
            thisStep = getActivity().getIntent().getParcelableExtra("ThisStep");
            if(Integer.parseInt(thisStep.stepId) == 0){
                previousButton.setVisibility(View.INVISIBLE);
            }
            else if(Integer.parseInt(thisStep.stepId) == steps.size()-1){
                nextButton.setVisibility(View.INVISIBLE);
            }
            else{
                previousButton.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.VISIBLE);
            }

        }
        mStepDescription.setText(thisStep.stepDescription);
        if(savedInstanceState != null) {
          currentWindow = savedInstanceState.getInt("window");
          playbackPosition = savedInstanceState.getLong("playback");
          playWhenReady = savedInstanceState.getBoolean("playWhenReady");
          initializePlayer(thisStep.videoURL);
        }
        else{
          initializePlayer(thisStep.videoURL);
        }
        return rootView;
    }
    private void initializePlayer(String videoURL){
        if(simpleExoPlayer == null) {
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(), new DefaultLoadControl()
            );
            mPlayerView.setPlayer(simpleExoPlayer);

            simpleExoPlayer.setPlayWhenReady(playWhenReady);
            simpleExoPlayer.seekTo(currentWindow, playbackPosition);

            Uri uri = Uri.parse(videoURL);
            if(videoURL.isEmpty()){
                mPlayerView.setVisibility(View.GONE);
                //mThumbnail.setVisibility(View.VISIBLE);
                //mThumbnail.setImageResource(R.drawable.cheesecake);


            }else {
                MediaSource mediaSource = buildMediaSource(uri);
                simpleExoPlayer.prepare(mediaSource, false, false);

            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer(thisStep.videoURL);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if ((Util.SDK_INT <= 23 || simpleExoPlayer == null)) {
            initializePlayer(thisStep.videoURL);
        }
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

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            playbackPosition = simpleExoPlayer.getCurrentPosition();
            currentWindow = simpleExoPlayer.getCurrentWindowIndex();
            playWhenReady = simpleExoPlayer.getPlayWhenReady();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImage(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }
}
