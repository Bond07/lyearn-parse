//package com.example.nirav.lyearn;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.app.PendingIntent;
//import android.content.DialogInterface;
//import android.content.IntentSender;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesUtil;
//import com.google.android.gms.common.api.GoogleApiClient;
//
///**
// * Created by nirav on 4/28/15.
// */
//public class LoginActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ServerAuthCodeCallbacks {
//
//
//    private static final int RC_SIGN_IN = 0;
//
//    private static final int STATE_DEFAULT = 0;
//    private static final int STATE_SIGN_IN = 1;
//    private static final int STATE_IN_PROGRESS = 2;
//
//
//    private static final String TAG = "LOGIN ACTIVITY";
//
//    // We use mSignInProgress to track whether user has clicked sign in.
//    // mSignInProgress can be one of three values:
//    //
//    //       STATE_DEFAULT: The default state of the application before the user
//    //                      has clicked 'sign in', or after they have clicked
//    //                      'sign out'.  In this state we will not attempt to
//    //                      resolve sign in errors and so will display our
//    //                      Activity in a signed out state.
//    //       STATE_SIGN_IN: This state indicates that the user has clicked 'sign
//    //                      in', so resolve successive errors preventing sign in
//    //                      until the user has successfully authorized an account
//    //                      for our app.
//    //   STATE_IN_PROGRESS: This state indicates that we have started an intent to
//    //                      resolve an error, and so we should not start further
//    //                      intents until the current intent completes.
//    private int mSignInProgress;
//
//    // Used to store the PendingIntent most recently returned by Google Play
//    // services until the user clicks 'sign in'.
//    private PendingIntent mSignInIntent;
//
//    // Used to store the error code most recently returned by Google Play services
//    // until the user clicks 'sign in'.
//    private int mSignInError;
//
//    private GoogleApiClient mGoogleApiClient;
//
//// ...
//
//    @Override
//    public void onConnectionFailed(ConnectionResult result) {
//
//        Log.i(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
//                + result.getErrorCode());
//
//        if (result.getErrorCode() == ConnectionResult.API_UNAVAILABLE) {
//
//            Log.w(TAG, "API Unavailable.");
//        } else if (mSignInProgress != STATE_IN_PROGRESS) {
//
//            mSignInIntent = result.getResolution();
//            mSignInError = result.getErrorCode();
//
//            if (mSignInProgress == STATE_SIGN_IN) {
//                // STATE_SIGN_IN indicates the user already clicked the sign in button
//                // so we should continue processing errors until the user is signed in
//                // or they click cancel.
//                resolveSignInError();
//            }
//        }
//
//        // In this sample we consider the user signed out whenever they do not have
//        // a connection to Google Play services.
//        //onSignedOut();
//    }
//
//    private void resolveSignInError() {
//        if (mSignInIntent != null) {
//            // We have an intent which will allow our user to sign in or
//            // resolve an error.  For example if the user needs to
//            // select an account to sign in with, or if they need to consent
//            // to the permissions your app is requesting.
//
//            try {
//                // Send the pending intent that we stored on the most recent
//                // OnConnectionFailed callback.  This will allow the user to
//                // resolve the error currently preventing our connection to
//                // Google Play services.
//                mSignInProgress = STATE_IN_PROGRESS;
//                startIntentSenderForResult(mSignInIntent.getIntentSender(),
//                        RC_SIGN_IN, null, 0, 0, 0);
//            }
//            catch (IntentSender.SendIntentException e) {
//                Log.i(TAG, "Sign in intent could not be sent: "
//                        + e.getLocalizedMessage());
//                // The intent was canceled before it was sent.  Attempt to connect to
//                // get an updated ConnectionResult.
//                mSignInProgress = STATE_SIGN_IN;
//                mGoogleApiClient.connect();
//            }
//        } else {
//            // Google Play services wasn't able to provide an intent for some
//            // error types, so we show the default Google Play services error
//            // dialog which may still start an intent on our behalf if the
//            // user can resolve the issue.
//            createErrorDialog().show();
//        }
//    }
//
//    private Dialog createErrorDialog() {
//        if (GooglePlayServicesUtil.isUserRecoverableError(mSignInError)) {
//            return GooglePlayServicesUtil.getErrorDialog(
//                    mSignInError,
//                    this,
//                    RC_SIGN_IN,
//                    new DialogInterface.OnCancelListener() {
//                        @Override
//                        public void onCancel(DialogInterface dialog) {
//                            Log.e(TAG, "Google Play services resolution cancelled");
//                            mSignInProgress = STATE_DEFAULT;
//                            Toast.makeText(LoginActivity.this, "Signed out", Toast.LENGTH_LONG);
//                        }
//                    });
//        } else {
//            return new AlertDialog.Builder(this)
//                    .setMessage(R.string.play_services_error)
//                    .setPositiveButton(R.string.close,
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    Log.e(TAG, "Google Play services error could not be "
//                                            + "resolved: " + mSignInError);
//                                    mSignInProgress = STATE_DEFAULT;
//                                    Toast.makeText(LoginActivity.this, "Signed out", Toast.LENGTH_LONG);
//                                }
//                            }).create();
//        }
//    }
//
//}
