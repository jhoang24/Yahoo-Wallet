package com.example.yahoowallet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;

import com.example.yahoowallet.helpers.CharUtils;
import com.example.yahoowallet.components.ProgressButton;
import com.example.yahoowallet.R;
import com.example.yahoowallet.authenticator.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends BaseActivity {

    private View continueButtonView;
    private TextInputLayout usernameTextInputLayout, passwordTextInputLayout;
    private TextInputEditText usernameTextInputEditText, passwordTextInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        continueButtonView = (View) findViewById(R.id.continueButton);
        TextView textView = (TextView) continueButtonView.findViewById(R.id.labelTextView);
        textView.setText(R.string.continue_button);

        usernameTextInputLayout = (TextInputLayout) findViewById(R.id.usernameTextInputLayout);
        passwordTextInputLayout = (TextInputLayout) findViewById(R.id.passwordTextInputLayout);
        usernameTextInputEditText = (TextInputEditText) findViewById(R.id.usernameTextInputEditText);
        passwordTextInputEditText = (TextInputEditText) findViewById(R.id.passwordTextInputEditText);

        setupHyperlink();

        usernameTextInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                usernameTextInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        passwordTextInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordTextInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        continueButtonView.setOnClickListener(view -> {
            ProgressButton progressButton = new ProgressButton(LoginActivity.this, view);
            progressButton.buttonActivated();

            if (!isValidInfo()) {
                progressButton.buttonFinished();
                return;
            }

            User user = new User();
            user.create(newUser -> {
                newUser.setEmail(usernameTextInputEditText.getText().toString())
                    .setPassword(passwordTextInputEditText.getText().toString());
                newUser.postUser(postedUser -> { });

                new Runnable() {
                    @Override
                    public void run() {
                        progressButton.buttonFinished();
                    }
                };

                Intent intent = new Intent(LoginActivity.this, AddressActivity.class);
                intent.putExtra("USER", newUser);
                startActivity(intent);
            });
        });
    }

    public boolean isValidEmail(CharSequence email) {
        if (TextUtils.isEmpty(email)) {
            usernameTextInputLayout.setError("Required");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            usernameTextInputLayout.setError("Invalid email");
            return false;
        }

        return true;
    }

    public boolean isValidPassword(CharSequence password) {
        if (TextUtils.isEmpty(password)) {
            passwordTextInputLayout.setError("Required");
            return false;
        }

        if (!CharUtils.minLength(password, 4)) {
            passwordTextInputLayout.setError("Must be 4 characters minimum");
            return false;
        }

//        Pattern pattern;
//        Matcher matcher;
//        final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\da-zA-Z]).{4,32}$";
//        pattern = Pattern.compile(PASSWORD_PATTERN);
//        matcher = pattern.matcher(target);
//        if (!matcher.matches()) {
//            passwordTextInputLayout.setError("Invalid password");
//            return false;
//        }

        return true;
    }

    private boolean isValidInfo() {
        return isValidEmail(usernameTextInputEditText.getText()) &&
                isValidPassword(passwordTextInputEditText.getText());
    }

    private void setupHyperlink() {
        TextView linkTextView = findViewById(R.id.signUpTextView);
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}