package com.parse.starter;

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

    // Initialize Crash Reporting.
    ParseCrashReporting.enable(this);

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    // Add your initialization code here
    Parse.initialize(this, "NCBBZU6AvY7n0Gt0ZCbtlD9NOyY7a8f62J2y26jc", "j9xyB5AuFi6tVOXlmBFlo26xQhzJFeAfGDgxXd97");

    ParseUser.enableAutomaticUser();
    ParseACL defaultACL = new ParseACL();
    // Optionally enable public read access.
    // defaultACL.setPublicReadAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);


    ParseObject testObject = new ParseObject("TestObject");
    testObject.put("foo", "bar");
    testObject.saveInBackground();


  }
}
