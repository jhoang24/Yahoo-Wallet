package com.example.yahoowallet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.yahoowallet.R;
import com.example.yahoowallet.authenticator.User;
import com.example.yahoowallet.components.ProgressButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Array;
import java.sql.Date;

public class AddCardActivity extends BaseActivity {

    View continueButtonView;
    TextInputLayout cardNameTextInputLayout, cardNumberTextInputLayout, cardCVVTextInputLayout;
    TextInputEditText cardNameTextInputEditText, cardNumberTextInputEditText, cardCVVTextInputEditText;
    Spinner cardExpirationMonthSpinner, cardExpirationYearSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        continueButtonView = (View) findViewById(R.id.continueButton);
        TextView textView = (TextView) continueButtonView.findViewById(R.id.labelTextView);
        textView.setText(R.string.continue_button);

        cardNameTextInputLayout = (TextInputLayout)  findViewById(R.id.cardNameTextInputLayout);
        cardNumberTextInputLayout = (TextInputLayout)  findViewById(R.id.cardNumberTextInputLayout);
        cardCVVTextInputLayout = (TextInputLayout)  findViewById(R.id.cardCVVTextInputLayout);
        cardNameTextInputEditText = (TextInputEditText) findViewById(R.id.cardNameTextInputEditText);
        cardNumberTextInputEditText = (TextInputEditText) findViewById(R.id.cardNumberTextInputEditText);
        cardCVVTextInputEditText = (TextInputEditText) findViewById(R.id.cardCVVTextInputEditText);
        cardExpirationMonthSpinner = (Spinner) findViewById(R.id.cardExpirationMonthSpinner);
        cardExpirationYearSpinner = (Spinner) findViewById(R.id.cardExpirationYearSpinner);

        cardNameTextInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cardNameTextInputEditText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        cardNumberTextInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cardNumberTextInputEditText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        cardCVVTextInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cardCVVTextInputEditText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        continueButtonView.setOnClickListener(view -> {
            ProgressButton progressButton = new ProgressButton(AddCardActivity.this, view);
            progressButton.buttonActivated();

            if (!isValidInfo()) {
                progressButton.buttonFinished();
                return;
            }

            User user = (User) getIntent().getSerializableExtra("USER");
            user.setName(cardNameTextInputEditText.getText().toString())
                .setCreditCard(cardNumberTextInputEditText.getText().toString())
                .setCvv(cardCVVTextInputEditText.getText().toString())
                .setExpiration(new Date(Integer.parseInt(cardExpirationYearSpinner.getSelectedItem().toString()), (int) cardExpirationMonthSpinner.getSelectedItemId(), 0));
            user.postUser(postedUser -> { });

            user.postUser(postedUser -> {
                new Runnable() {
                    @Override
                    public void run() {
                        progressButton.buttonFinished();
                    }
                };

                Intent intent = new Intent(AddCardActivity.this, SuccessActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
            });
        });
    }

    private boolean isValidInfo() {
        return isValidName(cardNameTextInputEditText.getText().toString()) &&
                isValidCreditCard(cardNumberTextInputEditText.getText().toString(), cardCVVTextInputEditText.getText().toString());
    }

    private boolean isValidName(CharSequence name) {
        if (TextUtils.isEmpty(name)) {
            cardNameTextInputEditText.setError("Required");
            return false;
        }

        return true;
    }

    private boolean isValidCreditCard(CharSequence cardNumber, CharSequence cardCVV) {
        if (TextUtils.isEmpty(cardNumber)) {
            cardNumberTextInputEditText.setError("Required");
            return false;
        }

        if (cardNumber.length() < 15) {
            cardNumberTextInputEditText.setError("Invalid");
            return false;
        }

        if (TextUtils.isEmpty(cardCVV)) {
            cardCVVTextInputEditText.setError("Required");
            return false;
        }

        if (cardCVVTextInputEditText.length() < 3) {
            cardCVVTextInputEditText.setError("Invalid");
            return false;
        }

        return true;
    }
}