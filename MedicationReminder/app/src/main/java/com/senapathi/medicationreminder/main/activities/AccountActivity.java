package com.senapathi.medicationreminder.main.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.senapathi.medicationreminder.R;

/**
 * Created by Senapathi on 31-12-2016.
 */
public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.accountlayout);

        // Action Listener to listen to the auth state changes
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(AccountActivity.this, MainActivity.class));
                    finish();
                }
            }
        };
        //

        //
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.openDrawer, R.string.closeDrawer);

        // Adding DrawerListener by passing DrawerToggle object
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        //

        // Setting DisplayHome as enabled to enable the backbutton on the ActioBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Onclick listener to Signout
        Button mSIgnOut = (Button) findViewById(R.id.signout);
        mSIgnOut.setOnClickListener(this);
        //
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStop() {
        super.onStop();
        // Removing the Listener
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Adding Listener
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onClick(View v) {
        // Deauthorises the current user
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public void onBackPressed() {
        // Here super() method is removed to make sure that BackButton is disabled.
    }
}
