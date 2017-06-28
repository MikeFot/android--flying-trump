package com.michaelfotiadis.flyingtrump.view.animation;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.michaelfotiadis.flyingtrump.R;
import com.michaelfotiadis.flyingtrump.utils.AppLog;
import com.michaelfotiadis.flyingtrump.view.utils.ViewUtils;

public class TrumpAnimator {

    @DrawableRes
    private static final int ASSET_ONE = R.drawable.asset_one;
    @DrawableRes
    private static final int ASSET_TWO = R.drawable.asset_two;

    private final Resources mResources;
    private final ViewGroup mParent;
    private AnimatorCallback mCallback;

    public TrumpAnimator(final Resources resources,
                         final ViewGroup parent,
                         final AnimatorCallback callback) {
        mResources = resources;
        mParent = parent;
        mCallback = callback;
    }

    public void setCallback(final AnimatorCallback callback) {
        mCallback = callback;
    }

    public void animate() {

        final ImageView imageView = new ImageView(mParent.getContext());

        final Drawable drawable = ContextCompat.getDrawable(imageView.getContext(), getRandomDrawable());
        ViewUtils.setDrawable(imageView, drawable.mutate());
        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);

        mParent.addView(imageView, params);
        mParent.bringToFront();
        imageView.bringToFront();
        final int width = mResources.getDisplayMetrics().widthPixels;
        final int height = mResources.getDisplayMetrics().heightPixels;


        final Coordinates coordinates = new Coordinates(getRandomDirection(), width, height);

        final int startX = coordinates.getStartX();
        final int midX = coordinates.getMidX();
        final int endX = coordinates.getEndX();
        final int startY = coordinates.getStartY();
        final int midY = coordinates.getMidY();
        final int endY = coordinates.getEndY();

        final Animation shake = AnimationUtils.loadAnimation(mParent.getContext(), R.anim.shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(final Animation animation) {

            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                final TranslateAnimation transAnimation2 = new TranslateAnimation(midX, endX, midY, endY);
                transAnimation2.setDuration(600);

                transAnimation2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(final Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(final Animation animation) {
                        mParent.removeView(imageView);
                        if (mCallback != null) {
                            mCallback.onFinished();
                        }
                    }

                    @Override
                    public void onAnimationRepeat(final Animation animation) {

                    }
                });

                imageView.startAnimation(transAnimation2);
            }

            @Override
            public void onAnimationRepeat(final Animation animation) {

            }
        });

        final TranslateAnimation transAnimation = new TranslateAnimation(startX, midX, startY, midY);
        transAnimation.setDuration(1000);
        transAnimation.setFillEnabled(true);
        transAnimation.setFillAfter(true);
        transAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(final Animation animation) {

            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                AppLog.d("Animating parent");
                mParent.startAnimation(shake);


            }

            @Override
            public void onAnimationRepeat(final Animation animation) {

            }
        });
        imageView.startAnimation(transAnimation);
        if (mCallback != null) {
            mCallback.onStarted();
        }
    }

    private static Direction getRandomDirection() {

        final int r = (int) (Math.random() * 8);
        for (final Direction direction : Direction.values()) {
            if (r == direction.id) {
                return direction;
            }
        }
        return Direction.NW;

    }


    @DrawableRes
    private static int getRandomDrawable() {
        final int r = (int) (Math.random() * 3);
        switch (r) {
            case 0:
                return ASSET_TWO;
            default:
                return ASSET_ONE;
        }
    }


}
