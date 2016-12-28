package com.senapathi.medicationreminder.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.senapathi.medicationreminder.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmailField;
    private EditText mPwfField;
    private Button loginButton;
    private TextView forgotPwd;
    private TextView mNewAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmailField = (EditText) findViewById(R.id.emailedt);
        mPwfField = (EditText) findViewById(R.id.pwdedt);
        loginButton = (Button) findViewById(R.id.loginbtn);
        forgotPwd = (TextView) findViewById(R.id.fpwdtext);
        mNewAccount = (TextView) findViewById(R.id.newacctext);

        loginButton.setOnClickListener(this);
        forgotPwd.setOnClickListener(this);
        mNewAccount.setOnClickListener(this);

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
                    Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
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


}
