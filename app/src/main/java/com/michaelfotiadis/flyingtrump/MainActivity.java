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
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.michaelfotiadis.flyingtrump.view.animation.AnimatorCallback;
import com.michaelfotiadis.flyingtrump.view.animation.TrumpAnimator;

import java.util.concurrent.atomic.AtomicBoolean;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    private static final String NAVIGATION_EXTRA = "SELECTED_VIEW_EXTRA";

    private ViewGroup mContentView;
    private ImageView mImageView;
    private Button mButton;
    private BottomNavigationView mNavigationView;
    private TrumpAnimator mTrumpAnimator;
    private final AtomicBoolean isAnimating = new AtomicBoolean(false);

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setTitle("The White House!");
                    mImageView.setImageResource(R.drawable.bg_white_house);
                    return true;
                case R.id.navigation_dashboard:
                    setTitle("The Moon!");
                    return true;
                case R.id.navigation_notifications:
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

        mContentView = (ViewGroup) findViewById(R.id.trump_view);
        mImageView = (ImageView) findViewById(R.id.image);
        mButton = (Button) findViewById(R.id.button);

        mButton.setText(getRandomText());

        mTrumpAnimator = new TrumpAnimator(getResources(), mContentView, new AnimatorCallback() {
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
                }
            }
        });

        mNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        final int selectedId = savedInstanceState == null ? R.id.navigation_home : savedInstanceState.getInt(NAVIGATION_EXTRA, R.id.navigation_home);
        mNavigationView.setSelectedItemId(selectedId);


    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mNavigationView != null) {
            outState.putInt(NAVIGATION_EXTRA, mNavigationView.getSelectedItemId());
        }
    }


    private String getRandomText() {

        final String[] array = getResources().getStringArray(R.array.quotes);
        final int r = (int) (Math.random() * array.length);
        return array[r];
    }

}
