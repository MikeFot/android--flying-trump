package com.michaelfotiadis.flyingtrump.audio;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.annotation.RawRes;

public class AudioPlayer {


    private final Activity mActivity;
    private MediaPlayer mPlayer;

    public AudioPlayer(final Activity activity) {
        this.mActivity = activity;
    }

    public void play(@RawRes final int resourceId) {
        mPlayer = MediaPlayer.create(mActivity, resourceId);
        mPlayer.start();
    }

    public void stop() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }
}