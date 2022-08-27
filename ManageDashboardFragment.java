package com.example.android.bfit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ManageDashboardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ManageDashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageDashboardFragment extends Fragment  {

    SharedPreferences sharedPreferences;
    public static final String mypreference = "mypref";
    Intent intent;
    ImageButton back_dashboard;
    Button bicycle_edit;
    Button chat_edit;
    Button games_edit;
    Button entertainment_edit;
    Button run_Edit;
    Button walk_edit;
    Button sleep_edit;
    Button heart_edit;
    Button steps_edit;
    Button track_edit;
    Button weight_edit;
    Button calories_edit;
    Button library_edit;
    Button camera_edit;
    Button movie_edit;
    Button music_edit;
    Button car_edit;
    Button broswer_edit;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ManageDashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ManageDashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ManageDashboardFragment newInstance(String param1, String param2) {
        ManageDashboardFragment fragment = new ManageDashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // for back button in dashboard
        back_dashboard=getView().findViewById(R.id.Back_dashboard);
        back_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(),MainPage.class);
                startActivity(intent);
            }
        });

        bicycle_edit=getView().findViewById(R.id.bicyle_edit);
        bicycle_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        run_Edit=getView().findViewById(R.id.run_edit);
        run_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        walk_edit=getView().findViewById(R.id.walk_edit);
        walk_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        calories_edit=getView().findViewById(R.id.calories_edit);
        calories_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        games_edit=getView().findViewById(R.id.games_edit);
        games_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        music_edit=getView().findViewById(R.id.music_edit);
        music_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        entertainment_edit=getView().findViewById(R.id.entertainment_edit);
        entertainment_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sleep_edit=getView().findViewById(R.id.sleep_edit);
        sleep_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        steps_edit=getView().findViewById(R.id.Steps_edit);
        steps_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        heart_edit=getView().findViewById(R.id.heart_edit);
        heart_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        track_edit=getView().findViewById(R.id.Track_edit);
        track_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        weight_edit=getView().findViewById(R.id.weight_edit);
        weight_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        library_edit=getView().findViewById(R.id.library_edit);
        library_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        camera_edit=getView().findViewById(R.id.camera_edit);
        camera_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        movie_edit=getView().findViewById(R.id.movie_edit);
        movie_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        car_edit=getView().findViewById(R.id.car_edit);
        car_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        broswer_edit=getView().findViewById(R.id.browsing_edit);
        broswer_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        chat_edit=getView().findViewById(R.id.chat_edit);
        chat_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        ((MainPage) getActivity()).setActionBarTitle("Manage Dashboard");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage_dashboard, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
