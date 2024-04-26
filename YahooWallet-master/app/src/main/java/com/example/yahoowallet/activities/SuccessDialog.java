package com.example.yahoowallet.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.yahoowallet.activities.LoginActivity;

// Class for the pop up message of the success activity
public class SuccessDialog extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Creates a new AlertDialog as a pop up message with the success activity being associated
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Uh-oh!");
        builder.setMessage("There was a problem processing your card. " +
                "Please login and try again. If problems persist, please try again later.");

        // Switches to the sign in activity when the "OK" button is pressed
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Intent is used to switch from the previous activity to a new activity
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
                // Finishes the previous activity so the back button won't bring it back
                getActivity().finish();
            }
        });

        return builder.create();
    }
}
