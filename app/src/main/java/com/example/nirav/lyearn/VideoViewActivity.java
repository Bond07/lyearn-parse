package com.example.nirav.lyearn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.VideoView;


public class VideoViewActivity extends ActionBarActivity {

    static final int REQUEST_VIDEO_CAPTURE = 1;

    //public static final int RESULT_OK  = -1;

    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        mVideoView = (VideoView) findViewById(R.id.videoView);

        dispatchTakeVideoIntent();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_video_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = getIntent().getData();
            mVideoView.setVideoURI(videoUri);
        }
    }
}
