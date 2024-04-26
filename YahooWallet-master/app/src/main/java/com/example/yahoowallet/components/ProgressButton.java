package com.example.yahoowallet.components;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.yahoowallet.R;

public class ProgressButton {

    private CardView cardView;
    private ConstraintLayout constraintLayout;
    private ProgressBar progressBar;
    private TextView textView;

    Animation fade_in;

    public ProgressButton(Context ct, View view) {
        fade_in = AnimationUtils.loadAnimation(ct, R.anim.fade_in);

        cardView = view.findViewById(R.id.cardView);
        constraintLayout = view.findViewById(R.id.constraintLayout);
        progressBar = view.findViewById(R.id.progressBar);
        textView = view.findViewById(R.id.labelTextView);
    }

    public void buttonActivated() {
        progressBar.setVisibility(View.VISIBLE);
        textView.setAnimation(fade_in);
        //textView.setText("Please wait...");
    }

    public void buttonFinished() {
        progressBar.setVisibility((View.GONE));
    }
}
