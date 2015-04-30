package com.example.nirav.lyearn;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AssignedCardFragment extends Fragment {

    private RecyclerView mRecyclerView;

    private AssignedCardAdapter mCardAdapter;

    private OnFragmentInteractionListener mListener;

    public AssignedCardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_assigned_card, container, false);

        mRecyclerView = (RecyclerView) layout.findViewById(R.id.card_assigned);

        mCardAdapter = new AssignedCardAdapter(getData());

        mRecyclerView.setAdapter(mCardAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //
        if (mListener != null) {
            mListener.onFragmentInteraction("Assigned Cards");
        }


        return layout;

    }

    public static ArrayList<AssignedCards> getData() {
        ArrayList<AssignedCards> data  = new ArrayList<>();

        String[] titles = {"Test1", "Test2", "Test3"};
        String[] assigner = {"Name1", "Name2", "Name3"};

        for (int i = 0; i<titles.length && i<assigner.length; i++ ) {

            AssignedCards current = new AssignedCards(titles[i],assigner[i]);
            data.add(current);
        }

        return data;
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

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String title);
    }


}
