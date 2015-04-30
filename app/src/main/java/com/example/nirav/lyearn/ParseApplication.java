package com.example.nirav.lyearn;

/**
 * Created by nirav on 4/29/15.
 */
import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Instantiate Parse
        // Initialize Crash Reporting.
        ParseCrashReporting.enable(this);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(this, "jQ8Jk6K4Niv2xToL7XQ2y7x3VlccVckjWB135oy5", "QLr3aNcPhQQ1EUpvRqeLTy2zHbyFFCTHwxGiUlsw");

        //ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}

