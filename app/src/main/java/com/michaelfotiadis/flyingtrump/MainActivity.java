package com.michaelfotiadis.flyingtrump;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.crashlytics.android.Crashlytics;
import com.michaelfotiadis.flyingtrump.audio.AudioPlayer;
import com.michaelfotiadis.flyingtrump.view.animation.AnimatorCallback;
import com.michaelfotiadis.flyingtrump.view.animation.TrumpAnimator;

import java.util.concurrent.atomic.AtomicBoolean;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    private static final String NAVIGATION_EXTRA = "SELECTED_VIEW_EXTRA";

    private ViewFlipper mViewFlipper;
    private Button mButton;
    private BottomNavigationView mNavigationView;
    private TrumpAnimator mTrumpAnimator;
    private AudioPlayer mAudioPlayer;
    private final AtomicBoolean isAnimating = new AtomicBoolean(false);

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_house:
                    setTitle("To the White House!");
                    mViewFlipper.setDisplayedChild(0);
                    return true;
                case R.id.navigation_moon:
                    setTitle("To the moon!");
                    mViewFlipper.setDisplayedChild(1);
                    return true;
                case R.id.navigation_desert:
                    setTitle("To the desert!");
                    mViewFlipper.setDisplayedChild(2);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAudioPlayer = new AudioPlayer(this);

        mViewFlipper = (ViewFlipper) findViewById(R.id.flipper);
        mViewFlipper.setInAnimation(this, R.anim.slide_in_right_chrome);
        mViewFlipper.setOutAnimation(this, R.anim.slide_out_left_chrome);
        mButton = (Button) findViewById(R.id.button);

        mButton.setText(getRandomText());

        final ViewGroup contentView = (ViewGroup) findViewById(R.id.trump_view);
        mTrumpAnimator = new TrumpAnimator(getResources(), contentView, new AnimatorCallback() {
            @Override
            public void onStarted() {
                isAnimating.set(true);
            }

            @Override
            public void onFinished() {
                isAnimating.set(false);
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mButton.setText(getRandomText());
                if (!isAnimating.get()) {
                    mTrumpAnimator.animate();
                    playRandomSound();
                }
            }
        });

        mNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        final int selectedId = savedInstanceState == null ? R.id.navigation_house : savedInstanceState.getInt(NAVIGATION_EXTRA, R.id.navigation_house);
        mNavigationView.setSelectedItemId(selectedId);


    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mNavigationView != null) {
            outState.putInt(NAVIGATION_EXTRA, mNavigationView.getSelectedItemId());
        }
    }

    private void playRandomSound() {

        final int r = (int) (Math.random() * 10 + 1);
        final int resId;
        switch (r) {
            case 1:
                resId = R.raw.trump_1;
                break;
            case 2:
                resId = R.raw.trump_2;
                break;
            case 3:
                resId = R.raw.trump_3;
                break;
            case 4:
                resId = R.raw.trump_4;
                break;
            case 5:
                resId = R.raw.trump_5;
                break;
            case 6:
                resId = R.raw.trump_6;
                break;
            case 7:
                resId = R.raw.trump_7;
                break;
            case 8:
                resId = R.raw.trump_8;
                break;
            case 9:
                resId = R.raw.trump_9;
                break;
            case 10:
                resId = R.raw.trump_10;
                break;
            default:
                resId = R.raw.trump_1;
        }
        mAudioPlayer.play(resId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAudioPlayer != null) {
            mAudioPlayer.stop();
        }
    }

    private String getRandomText() {

        final String[] array = getResources().getStringArray(R.array.quotes);
        final int r = (int) (Math.random() * array.length);
        return array[r];
    }

}
