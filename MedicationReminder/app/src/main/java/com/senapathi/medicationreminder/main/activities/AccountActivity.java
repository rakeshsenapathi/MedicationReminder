package com.senapathi.medicationreminder.main.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.senapathi.medicationreminder.R;
import com.senapathi.medicationreminder.main.utils.CircleTransform;

/**
 * Created by Senapathi on 31-12-2016.
 */
public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private TextView mUserName;
    NavigationView mNavigationView;
    private TextView navHeaderName;
    private TextView navHeaderMail;

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
        //

        // Adding DrawerListener by passing DrawerToggle object
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        //

        // Setting DisplayHome as enabled to enable the backbutton on the ActioBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // This is to change the title color of the default action bar
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'> Medication Reminder </font>"));

        //Onclick listener to Signout
        Button mSIgnOut = (Button) findViewById(R.id.signout);
        mSIgnOut.setOnClickListener(this);
        //


        //Getting the view for NavigationView
        mNavigationView = (NavigationView) findViewById(R.id.navigationView);

        // Getting the views for email and pwd to display in NavHeader
        View view = mNavigationView.getHeaderView(0);
        mUserName = (TextView) findViewById(R.id.setUserName);
        navHeaderName = (TextView) view.findViewById(R.id.name);
        navHeaderMail = (TextView) view.findViewById(R.id.userEmailText);
        //

        //
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            mUserName.setText(user.getDisplayName());
            navHeaderName.setText(user.getDisplayName()); //setting the name in NavHeader's TV
            navHeaderMail.setText(user.getEmail()); //setting the email in NavHeader's TV
        }
        //

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {

                    case R.id.nav_signout:
                        FirebaseAuth.getInstance().signOut();
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_chat:
                        Toast.makeText(AccountActivity.this, "Clicked on NAV_CHAT item", Toast.LENGTH_SHORT).show();
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_heath:
                        Toast.makeText(AccountActivity.this, "Clicked on NAV_HEALTH item", Toast.LENGTH_SHORT).show();
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        break;

                    case R.id.nav_home:
                        Toast.makeText(AccountActivity.this, "Clicked on NAV_HOME item", Toast.LENGTH_SHORT).show();
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        break;
                }
                return true;
            }
        });
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
        finish();
    }
}
