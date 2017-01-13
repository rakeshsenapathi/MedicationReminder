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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.senapathi.medicationreminder.R;
import com.senapathi.medicationreminder.main.utils.Dialog;

/**
 * Created by Senapathi on 02-01-2017.
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmailField;
    private EditText mPwfField;
    private Button signupButton;
    private TextView mLogin;
    private FirebaseAuth mAuth;
    // Creating dialog object to access its dialog methods
    Dialog dialog = new Dialog();
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Instantiating the FirebaseAuth Object
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.signup);

        // Typecasting finding the views
        mEmailField = (EditText) findViewById(R.id.emailSignup);
        mPwfField = (EditText) findViewById(R.id.pwdSignup);
        signupButton = (Button) findViewById(R.id.signupbtn);
        mLogin = (TextView) findViewById(R.id.logintext);
        //

        // AuthListener to get the current state of the user
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    dialog.closeProgressDialog(SignUpActivity.this);
                    Toast.makeText(SignUpActivity.this, "SignUp sucessful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                }
            }
        };
        //

        // OnClick listeners
        signupButton.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        //

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //Click Listener for Login Button
            case R.id.signupbtn:
                String email = mEmailField.getText().toString().trim();
                String password = mPwfField.getText().toString().trim();
                // Checking for EmptyFields
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(SignUpActivity.this, "Empty fields", Toast.LENGTH_SHORT).show();
                }
                // If not found then SignUp with email,password to create a new user
                else {
                    dialog.showProgressDialog(SignUpActivity.this);
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;

            // Click Listener for Login
            case R.id.logintext:
                Toast.makeText(SignUpActivity.this, "Login", Toast.LENGTH_SHORT).show();
                finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
        finish();
    }
}
