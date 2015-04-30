package com.example.nirav.lyearn;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener{

    private Context mContext;
    private Activity mActivity;
    private OnFragmentInteractionListener mListener;

    private ImageView mImageView;
    private Button mAudioRecordButton;
    private Button mVideoRecordButton;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int RESULT_OK  = -1;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private Intent intent;


    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = getActivity();
        mContext = getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_settings,
                container, false);

        if (mListener != null) {
            mListener.onFragmentInteraction("Settings");
        }

        mImageView = (ImageView)  view.findViewById(R.id.imgAvatarSettings);
        mAudioRecordButton =(Button) view.findViewById(R.id.audioRecordButton);
        mVideoRecordButton = (Button) view.findViewById(R.id.videoRecordButton);

        mImageView.setOnClickListener(this);
        mAudioRecordButton.setOnClickListener(this);
        mVideoRecordButton.setOnClickListener(this);

        //Load image using Async Task
        new LoadProfileImage(mImageView).execute(HomeActivity.userProfile[2]);

        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgAvatarSettings: {
                dispatchTakePictureIntent();
                break;
            }

            case R.id.audioRecordButton: {
                Intent intent = new Intent(getActivity(), AudioRecordingActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.videoRecordButton: {
                //dispatchTakeVideoIntent();
                Intent intent = new Intent(getActivity(), VideoViewActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 500, 500, true);
            ImageRounding image = new ImageRounding();
            mImageView.setImageBitmap(image.getBitmap(imageBitmap));
        }
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
               maxImageSize / realImage.getWidth(),
               maxImageSize / realImage.getHeight());
        int width = Math.round(ratio * realImage.getWidth());
        int height = Math.round(ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String title);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
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
