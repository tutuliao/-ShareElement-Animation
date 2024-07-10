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
import android.os.Bundle;
import android.transition.Transition;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import com.bumptech.glide.Glide;


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

    private Item mItem;
    private Transition transition;
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
        //ViewCompat.setTransitionName(sp, VIEW_NAME_HEADER_TITLE);
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
        mHeaderImageView.setAlpha(1f);
        mHeaderImageView.animate()
                .alpha(0f)
                .setDuration(500)
                .start();
        //ObjectAnimator animator = ObjectAnimator.ofFloat(mHeaderImageView, "alpha", 1f, 0f);
        //animator.setDuration(500); // 1秒钟的时间
        //animator.start();
        //
        //animator.addListener(new AnimatorListenerAdapter() {
        //    @Override
        //    public void onAnimationEnd(Animator animation) {
        //        if (isFinishing()) return;
        //        DetailActivity.super.onBackPressed();
        //    }
        //});
    }

    private void loadItem() {
        if (addTransitionListener()) {
            loadThumbnail();
        } else {
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
                .into(mHeaderImageView);

    }


    private boolean addTransitionListener() {
        transition = getWindow().getSharedElementEnterTransition();
        if (transition != null) {
            // There is an entering shared element transition so add a listener to it
            transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionEnd(Transition transition) {
                }

                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionCancel(Transition transition) {
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionPause(Transition transition) {
                }

                @Override
                public void onTransitionResume(Transition transition) {
                }
            });
            return true;
        }

        // If we reach here then we have not added a listener
        return false;
    }
}

