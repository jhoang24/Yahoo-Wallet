package com.example.yahoowallet.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yahoowallet.R;
import com.example.yahoowallet.components.ProgressButton;

public class SuccessActivity extends BaseActivity {

    private View addAnotherView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        // Initializes the button
        addAnotherView = (View) findViewById(R.id.continueButton);
        TextView textView = (TextView) addAnotherView.findViewById(R.id.labelTextView);
        textView.setText("Add Another Card");

        // When the user clicks the button
        addAnotherView.setOnClickListener(view -> {
            ProgressButton progressButton = new ProgressButton(SuccessActivity.this, view);
            progressButton.buttonActivated();

            openDialog();

            progressButton.buttonFinished();
        });

        // Sets the time the pop up message appears
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openDialog();
            }
        }, 5000);
    }

    // Displays a pop up message
    public void openDialog() {
        SuccessDialog successDialog = new SuccessDialog();
        // Stops the user from clicking the back button or clicking outside
        successDialog.setCancelable(false);
        successDialog.show(getSupportFragmentManager(), "success dialog");
    }
}