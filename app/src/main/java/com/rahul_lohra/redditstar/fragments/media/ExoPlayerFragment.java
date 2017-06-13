package com.rahul_lohra.redditstar.fragments.media;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSource;
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;
import com.rahul_lohra.redditstar.Application.Initializer;
import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.Utility.Constants;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

public class ExoPlayerFragment extends Fragment {
    private static final String ARG_PARAM1 = "videoUrl";
    @Bind(R.id.player_view)
    SimpleExoPlayerView playerView;
    @Bind(R.id.retry_button)
    Button retryButton;

    private Uri mp4VideoUri;
    SimpleExoPlayer player;

    @Inject
    OkHttpClient okHttpClient;

    public ExoPlayerFragment() {
        // Required empty public constructor
    }

    public static ExoPlayerFragment newInstance(String param1) {
        ExoPlayerFragment fragment = new ExoPlayerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mp4VideoUri = Uri.parse(getArguments().getString(ARG_PARAM1));
        }
        ((Initializer) getContext().getApplicationContext()).getNetComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_exoplayer, container, false);
        ButterKnife.bind(this, v);
        createPlayer();
        attachPlayerToView();
        preparePlayer();
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    void createPlayer() {
        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Create a default LoadControl
        LoadControl loadControl = new DefaultLoadControl();

        // 3. Create the player
        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
    }

    void attachPlayerToView(){
        playerView.setPlayer(player);
    }

    void preparePlayer(){
        // Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory f1 = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), getContext().getApplicationInfo().className), bandwidthMeter);
//        DataSource.Factory dataSource = new OkHttpDataSourceFactory()
//                (DataSource.Factory)new OkHttpDataSource(okHttpClient, Constants.getUserAgent(getContext()), null, bandwidthMeter);
//        (getContext(), bandwidthMeter,
//                new OkHttpDataSource(okHttpClient, Constants.getUserAgent(getContext()), null, bandwidthMeter));
        // Produces Extractor instances for parsing the media data.
//        new Defa

        DataSource.Factory f2 = new OkHttpDataSourceFactory(okHttpClient,Constants.getUserAgent(getContext()),null);

        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource(mp4VideoUri,
                f2, extractorsFactory, null, null);
        // Prepare the player with the source.
        player.prepare(videoSource);
    }

    void releasePlayer(){
        if(player!=null){
            player.release();
            player = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }
}
