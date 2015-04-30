package com.example.nirav.lyearn;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

import java.io.InputStream;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class HomeActivity extends ActionBarActivity implements NavigationDrawerCallbacks, View.OnClickListener, AssignedCardFragment.OnFragmentInteractionListener, SettingsFragment.OnFragmentInteractionListener, HomeFragment.OnFragmentInteractionListener, SubmissionFragment.OnFragmentInteractionListener {
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private GoogleApiClient mGoogleApiClient;

    public static String[] userProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerLayout.setStatusBarBackgroundColor(
                getResources().getColor(R.color.myPrimaryDarkColor));


        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);

        ImageView profileImage = (ImageView) findViewById(R.id.imgAvatar);
        TextView userName = (TextView) findViewById(R.id.txtUsername);
        TextView userEmail = (TextView) findViewById(R.id.txtUserEmail);

        //sign out button
        Button signOutButton = (Button) findViewById(R.id.btn_sign_out);
        signOutButton.setOnClickListener(this);

        //get Intent from Main Activity
        Bundle extras = getIntent().getExtras();
        userProfile = extras.getStringArray(getString(R.string.key_name));
        new LoadProfileImage(profileImage).execute(userProfile[2]);
        userName.setText(userProfile[0]);
        userEmail.setText(userProfile[3]);


        //set font style
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Roboto/Roboto-Medium.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        mGoogleApiClient = buildGoogleApiClient();
    }

    private GoogleApiClient buildGoogleApiClient() {
        // When we build the GoogleApiClient we specify where connected and
        // connection failed callbacks should be returned, which Google APIs our
        // app uses and which OAuth 2.0 scopes our app requests.
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addScope(Plus.SCOPE_PLUS_LOGIN);
        return builder.build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        //Toast.makeText(this, "Menu item selected -> " + position, Toast.LENGTH_SHORT).show();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (position) {
            case 0: {
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.container, homeFragment).commit();
                break;
            }
            case 1: {
                AssignedCardFragment assignedCardFragment = new AssignedCardFragment();
                fragmentTransaction.replace(R.id.container, assignedCardFragment).commit();
                break;
            }
            case 2: {
                SubmissionFragment submissionFragment = new SubmissionFragment();
                fragmentTransaction.replace(R.id.container,submissionFragment).commit();
                break;
            }
            case 3: {
                SettingsFragment settingsFragment = new SettingsFragment();
                fragmentTransaction.replace(R.id.container, settingsFragment).commit();
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        //else
            //super.onBackPressed();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_out:
                if (mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.clearDefaultAccountAndReconnect();
                    Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient);
                    mGoogleApiClient.disconnect();
                    mGoogleApiClient.connect();
                    finish();
                }
        }
    }

    @Override
    public void onFragmentInteraction(String title) {
        getSupportActionBar().setTitle(title);
    }


    /**
     * Background Async task to load user profile picture from url
     * */
    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            ImageRounding image = new ImageRounding();
            bmImage.setImageBitmap(image.getBitmap(result));

        }
    }

}

