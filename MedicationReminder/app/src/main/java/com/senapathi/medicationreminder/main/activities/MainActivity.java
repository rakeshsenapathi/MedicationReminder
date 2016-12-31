package com.senapathi.medicationreminder.main.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmailField;
    private EditText mPwfField;
    private Button loginButton;
    private TextView forgotPwd;
    private TextView mNewAccount;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);


        mEmailField = (EditText) findViewById(R.id.emailedt);
        mPwfField = (EditText) findViewById(R.id.pwdedt);
        loginButton = (Button) findViewById(R.id.loginbtn);
        forgotPwd = (TextView) findViewById(R.id.fpwdtext);
        mNewAccount = (TextView) findViewById(R.id.newacctext);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(MainActivity.this, AccountActivity.class));
                }
            }
        };

        loginButton.setOnClickListener(this);
        forgotPwd.setOnClickListener(this);
        mNewAccount.setOnClickListener(this);

    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginbtn:
                String email = mEmailField.getText().toString();
                String password = mPwfField.getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "Empty fields", Toast.LENGTH_SHORT).show();
                } else {

                    Dialog dialog = new Dialog();
                    dialog.showProgressDialog(MainActivity.this);
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }
                break;
            case R.id.fpwdtext:
                Toast.makeText(MainActivity.this, "Forgot Password", Toast.LENGTH_SHORT).show();
                break;
            case R.id.newacctext:
                Toast.makeText(MainActivity.this, "New Account", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
