package com.example.android.bfit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.facebook.react.bridge.ReactContext;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link caloriesdayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link caloriesdayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class caloriesdayFragment extends Fragment {

    private ReactContext mReactContext;
    private GoogleFitManager googleFitManager;
    ImageButton sharecaloriesday;

    private static final String TAG = "CalorieHistory";


    @SuppressLint("ValidFragment")
    public caloriesdayFragment(ReactContext mReactContext, GoogleFitManager googleFitManager) {
        ReactContext reactContext = null;
        this.mReactContext = reactContext;
        this.googleFitManager = googleFitManager;
    }
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public caloriesdayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment caloriesdayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static caloriesdayFragment newInstance(String param1, String param2) {
        caloriesdayFragment fragment = new caloriesdayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        sharecaloriesday=getView().findViewById(R.id.sharecaloriesday);
        sharecaloriesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myintent= new Intent(Intent.ACTION_SEND);
                myintent.setType("Data");
                String Sharebody="Your body here";
                String sharesub="Your object here";
                myintent.putExtra(Intent.EXTRA_TEXT,Sharebody);
                myintent.putExtra(Intent.EXTRA_SUBJECT,sharesub);
                startActivity(Intent.createChooser(myintent,"Share Using"));
            }
        });

        // color your statue bar Fragment
        if (android.os.Build.VERSION.SDK_INT>= 21){
            Window window=getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }
        super.onCreate(savedInstanceState);
        ((MainPage)getActivity()).setActionBarTitle("Day");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        GraphView graph = (GraphView) getView().findViewById(R.id.graphcaloriesday);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(04, 3),
                new DataPoint(05, 3),
                new DataPoint(06, 6),
                new DataPoint(07, 2),
                new DataPoint(9, 5),
                new DataPoint(10, 5),
                new DataPoint(11, 5),
                new DataPoint(13, 5),
                new DataPoint(14, 5),
                new DataPoint(15, 5),
                new DataPoint(17, 5),
                new DataPoint(18, 5),
                new DataPoint(19, 5),
                new DataPoint(21, 5),
                new DataPoint(22, 5),
                new DataPoint(23, 5)

        });
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        graph.addSeries(series);
        series.setColor(Color.parseColor("Lightblue"));

        graph.getGridLabelRenderer();


        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(00, -2),
                new DataPoint(01, 5),
                new DataPoint(02, 3),
                new DataPoint(03, 2),
                new DataPoint(8, 6),
                new DataPoint(12, 6),
                new DataPoint(16, 6),
                new DataPoint(20, 6),
                new DataPoint(00, 5)

        });
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        graph.addSeries(series2);
        series2.setColor(Color.parseColor("Lightblue"));
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_caloriesday, container, false);
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
