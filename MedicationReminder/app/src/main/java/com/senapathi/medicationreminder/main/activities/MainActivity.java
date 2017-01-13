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
    // Creating dialog object to access its dialog methods
    Dialog dialog = new Dialog();
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Instantiating the FirebaseAuth Object
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);

        //
        mEmailField = (EditText) findViewById(R.id.emailedt);
        mPwfField = (EditText) findViewById(R.id.pwdedt);
        loginButton = (Button) findViewById(R.id.loginbtn);
        forgotPwd = (TextView) findViewById(R.id.fpwdtext);
        mNewAccount = (TextView) findViewById(R.id.newacctext);
        //

        // AuthListener to get the current state of the user
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    dialog.closeProgressDialog(MainActivity.this);
                    startActivity(new Intent(MainActivity.this, AccountActivity.class));
                    finish();
                }
            }
        };
        //

        // on click listeners
        loginButton.setOnClickListener(this);
        forgotPwd.setOnClickListener(this);
        mNewAccount.setOnClickListener(this);
        //
    }


    @Override
    protected void onStart() {
        super.onStart();
        //Setting the Auth Listener to listen to the AuthStates
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        //UnAuthorizing the user
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            // Click Listener for Login Button
            case R.id.loginbtn:
                String email = mEmailField.getText().toString().trim();
                String password = mPwfField.getText().toString().trim();

                // Checking for EmptyFields
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "Empty fields", Toast.LENGTH_SHORT).show();
                }
                // If not found then SignIn with email,password and change the AuthState(CurrentUser!= null)
                else {
                    dialog.showProgressDialog(MainActivity.this);
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                                dialog.closeProgressDialog(MainActivity.this);
                            }
                        }
                    });
                }
                break;

            //Click Listener for ForgotPassword Button
            case R.id.fpwdtext:
                startActivity(new Intent(MainActivity.this,ForgotPasswordActivity.class));
                finish();
                break;

            //Click Listener for Creating a NewAccount
            case R.id.newacctext:
                startActivity(new Intent(MainActivity.this,SignUpActivity.class));
                finish();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
