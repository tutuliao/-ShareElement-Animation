/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.activityscenetransitionbasic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.transition.Transition;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.squareup.picasso.Picasso;

/**
 * Our secondary Activity which is launched from {@link MainActivity}. Has a simple detail UI
 * which has a large banner image, title and body text.
 */
public class DetailActivity extends AppCompatActivity {

    // Extra name for the ID parameter
    public static final String EXTRA_PARAM_ID = "detail:_id";

    // View name of the header image. Used for activity scene transitions
    public static final String VIEW_NAME_HEADER_IMAGE = "detail:header:image";

    // View name of the header title. Used for activity scene transitions
    public static final String VIEW_NAME_HEADER_TITLE = "detail:header:title";

    public static final String VIEW_NAME_TRANSPORT_IMAGE = "detail:header:transport";

    private ImageView mHeaderImageView;
    private TextView mHeaderTitle;

    private Item mItem;

    //修改
    public String imageResourceName;
    public int imageResourceId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        mItem = Item.getItem(getIntent().getIntExtra(EXTRA_PARAM_ID, 0));

        mHeaderImageView = findViewById(R.id.imageview_header);
        ViewCompat.setTransitionName(mHeaderImageView, VIEW_NAME_HEADER_IMAGE);

        //ViewCompat.setTransitionName(mHeaderTitle, VIEW_NAME_HEADER_TITLE);
        // END_INCLUDE(detail_set_view_name)

        //修改
        imageResourceName = getIntent().getStringExtra("tg");
        imageResourceId = getResources().getIdentifier(imageResourceName, "drawable", getPackageName());
        //
        loadItem();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ObjectAnimator animator = ObjectAnimator.ofFloat(mHeaderImageView, "alpha", 1f, 0f);
        animator.setDuration(500); // 1秒钟的时间
        animator.start();

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (isFinishing()) return;
                DetailActivity.super.onBackPressed();
            }
        });
    }

    public static void fadeOutAnimation(final View view) {
        // 创建淡出动画
        AlphaAnimation fadeOutAnimation = new AlphaAnimation(1.0f, 0.0f);
        // 设置淡出时间
        fadeOutAnimation.setDuration(1000);
        fadeOutAnimation.setFillAfter(true);
        // 设置动画监听器
        fadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // 动画开始时的操作
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // 动画重复时的操作
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 动画结束时的操作
                view.setVisibility(View.GONE);
            }
        });
        // 开始动画
        view.startAnimation(fadeOutAnimation);
    }

    private void loadItem() {
        if (addTransitionListener()) {
            // If we're running on Lollipop and we have added a listener to the shared element
            // transition, load the thumbnail. The listener will load the full-size image when
            // the transition is complete.
            loadThumbnail();
        } else {
            // If all other cases we should just load the full-size image now
            loadFullSizeImage();
        }
    }

    /**
     * Load the item's thumbnail image into our {@link ImageView}.
     */
    private void loadThumbnail() {
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mHeaderImageView, "alpha", 0f, 1f);
        alphaAnimator.setDuration(500);
        alphaAnimator.start();

        Glide.with(mHeaderImageView.getContext())
                .load(imageResourceId)
                .into(mHeaderImageView);
    }
    
    /**
     * Load the item's full-size image into our {@link ImageView}.
     */
    private void loadFullSizeImage() {
        Glide.with(mHeaderImageView.getContext())
                .load(imageResourceId)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(mHeaderImageView);

    }

    /**
     * Try and add a {@link Transition.TransitionListener} to the entering shared element
     * {@link Transition}. We do this so that we can load the full-size image after the transition
     * has completed.
     *
     * @return true if we were successful in adding a listener to the enter transition
     */
    @RequiresApi(21)
    private boolean addTransitionListener() {
        final Transition transition = getWindow().getSharedElementEnterTransition();

        if (transition != null) {
            // There is an entering shared element transition so add a listener to it
            transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionEnd(Transition transition) {
                    // As the transition has ended, we can now load the full-size image


                    // Make sure we remove ourselves as a listener

                }

                @Override
                public void onTransitionStart(Transition transition) {
                    loadThumbnail();
                    transition.removeListener(this);
                    // No-op
                }

                @Override
                public void onTransitionCancel(Transition transition) {
                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionPause(Transition transition) {
                    // No-op
                }

                @Override
                public void onTransitionResume(Transition transition) {
                    // No-op
                }
            });
            return true;
        }

        // If we reach here then we have not added a listener
        return false;
    }
}

