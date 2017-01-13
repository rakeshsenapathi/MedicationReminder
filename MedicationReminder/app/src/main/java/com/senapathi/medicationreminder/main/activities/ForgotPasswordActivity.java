package com.senapathi.medicationreminder.main.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.senapathi.medicationreminder.R;
import com.senapathi.medicationreminder.main.utils.Dialog;

/**
 * Created by Senapathi on 02-01-2017.
 */

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmailField;
    private Button mResetBtn;
    private FirebaseAuth mAuth;
    // Creating dialog object to access its dialog methods
    Dialog dialog = new Dialog();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.forgotpassword);

        //
        mEmailField = (EditText) findViewById(R.id.reset_email);
        mResetBtn = (Button) findViewById(R.id.resetbtn);
        //

        mResetBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        String email = mEmailField.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
        } else {
            dialog.showProgressDialog(ForgotPasswordActivity.this);
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPasswordActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ForgotPasswordActivity.this, "Failed to reset password", Toast.LENGTH_SHORT).show();
                            }
                            dialog.closeProgressDialog(ForgotPasswordActivity.this);
                        }
                    });
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ForgotPasswordActivity.this,MainActivity.class));
    }
}
