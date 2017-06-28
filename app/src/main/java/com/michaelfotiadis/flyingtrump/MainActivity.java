package com.michaelfotiadis.flyingtrump;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.michaelfotiadis.flyingtrump.audio.AudioPlayer;
import com.michaelfotiadis.flyingtrump.dialog.AboutDialog;
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
                    Answers.getInstance().logCustom(new CustomEvent("Viewed White House"));
                    setTitle("To the White House!");
                    mViewFlipper.setDisplayedChild(0);
                    return true;
                case R.id.navigation_moon:
                    Answers.getInstance().logCustom(new CustomEvent("Viewed Moon"));
                    setTitle("To the moon!");
                    mViewFlipper.setDisplayedChild(1);
                    return true;
                case R.id.navigation_desert:
                    Answers.getInstance().logCustom(new CustomEvent("Viewed Desert"));
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
                Answers.getInstance().logCustom(new CustomEvent("Button pressed"));
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

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            new AboutDialog().show(getSupportFragmentManager(), AboutDialog.class.getSimpleName());
            return true;
        } else if (id == R.id.action_rate) {
            //noinspection AnonymousInnerClassMayBeStatic
            new AlertDialog.Builder(this).setTitle(R.string.dialog_title).setMessage(R.string.dialog_message)
                    .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {
                            rate();
                        }
                    })
                    .setNegativeButton(R.string.label_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {
                            dialog.dismiss();
                        }
                    }).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void rate() {
        final Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
        final Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        } else {
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        }

        try {
            startActivity(goToMarket);
        } catch (final ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
        }

    }

}
