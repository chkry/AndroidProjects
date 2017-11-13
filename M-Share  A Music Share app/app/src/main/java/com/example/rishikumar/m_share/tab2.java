package com.example.rishikumar.m_share;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link tab2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link tab2# newInstance} factory method to
 * create an instance of this fragment.
 */
public class tab2 extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PARAM1 = "param1";
    public static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    public String mParam1;
    public String mParam2 , texttv12="Title", texttv22="Artist" , texttv32="Album";
    public ImageButton ib1;
    public View thisview;
    public static View sthisview;
    public ToggleButton tb1, tb3, tb4;
    public TextView tv12, tv22, tv32;
    public  Boolean isPressed =false;
    public OnFragmentInteractionListener mListener;
    public LayoutInflater thisinflater;
    public ViewGroup thiscontainer;

    public MainActivity activity;


    public tab2() {
        // Required empty public constructor
    }


    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get data from intent and update
        }
    };

    public static tab2 newInstance (String param1, String param2) {
        tab2 fragment = new tab2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if ( getActivity() instanceof MainActivity){
            activity = (MainActivity) getActivity();
        }


        thiscontainer= container;
        thisinflater= inflater;
        View thview = thisinflater.inflate(R.layout.tab2, thiscontainer, false);


        return thview;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        Log.d("Taggg", "your tab2 fragment view is created with parameters :  :" + view.getTag() + "  " + view.getId()+ " "+ view.getContext() +" "+ view.getOverlay() + "  " + view.getTag());

        tb1 = (ToggleButton) view.findViewById(R.id.tb1);
        tb1.setOnClickListener(this );
        ib1 = (ImageButton) view.findViewById(R.id.ib1);
        ib1.setOnClickListener(this);
       tv12 = (TextView) view.findViewById(R.id.tv1);
        tv22 = (TextView) view.findViewById(R.id.tv2);
        tv32 = (TextView) view.findViewById(R.id.tv3);


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }


        Log.d("Taggg", "your tab2 fragment is attached");
    }

    //@Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Log.d("Taggg", "your tab2 fragment is de-attached");
    }

    



    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tb1:
                Log.d("taggg", "You pressed tb1");

                if (isPressed == false) {
                    tv12.setTextColor(Color.BLACK);
                    tv22.setTextColor(Color.BLACK);
                    tv32.setTextColor(Color.BLACK);

                   /* tv12.setText(texttv12);
                    Log.d("taggg", "On pressing toggle button , I set TextTv2 to :" + texttv22);
                    tv22.setText(texttv22);
                    tv32.setText(texttv32);*/
                    isPressed = true;
                } else {
                   /* tv12.setText(texttv12);
                    Log.d("taggg", "On pressing toggle button , I set TextTv2 to :" + texttv22);
                    tv22.setText(texttv22);
                    tv32.setText(texttv32);*/
                    tv12.setTextColor(Color.WHITE);
                    tv22.setTextColor(Color.WHITE);
                    tv32.setTextColor(Color.WHITE);

                    isPressed = false;
                }
                break;

        }
    }

    public void setTexttv2(String texttv2) {
        texttv22 = texttv2;


        Log.d("taggg", "On pressing toggle button , I set TextTv2 to :" + texttv22);
       tv22.setText(texttv22);

    }

    public void setTexttv3(String texttv3) {
        texttv32 = texttv3;



        Log.d("taggg", "On pressing toggle button , I set TextTv2 to :" + texttv22);
       // tv32.setText(texttv32);

    }

    public void setTexttv1(String texttv1) {
        texttv12 = texttv1;



        Log.d("taggg", "On pressing toggle button , I set TextTv2 to :" + texttv22);
        //tv12.setText(texttv12);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
