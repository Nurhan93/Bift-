package com.example.android.bfit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;


import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link bicycleyearFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link bicycleyearFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class bicycleyearFragment extends Fragment {

    private int totalCalories;
    private float totalDays;
    public float averageCalories;
    ImageButton sharebicycleyear;
    TextView calories;
    TextView dailyaverage;
    TextView fewdailyaverage;
    TextView mostdialyaverage;

    @SuppressLint("ValidFragment")
    public bicycleyearFragment(List<LogEntry> logEntries) {
        calculateSummary(logEntries);
    }

    private void calculateSummary(List<LogEntry> logEntries) {

        totalCalories = 0;
        totalDays = averageCalories = 0;

        for (LogEntry l : logEntries)
            totalCalories += l.getTotalCalories();

        if (logEntries.size() > 1) {
            Date max = getMaxDate(logEntries);
            Date min = getMinDate(logEntries);
            totalDays = ( max.getTime() - min.getTime() ) / ( 1000 * 60 * 60 * 24 );
        }

        if (logEntries.size() != 0)
            averageCalories = totalCalories / logEntries.size();
    }

    public Date getMinDate(List<LogEntry> logEntries) {

        Date max;
        if (logEntries.size() < 1)
            return null;

        max = logEntries.get(0).getDate();
        for (int i = 1; i < logEntries.size(); i++) {
            if (logEntries.get(i).getDate().compareTo(max) > 0)
                max = logEntries.get(i).getDate();
        }
        return max;
    }

    public Date getMaxDate(List<LogEntry> logEntries) {
        Date max;
        if (logEntries.size() < 1)
            return null;

        max = logEntries.get(0).getDate();
        for (int i = 1; i < logEntries.size(); i++) {
            if (logEntries.get(i).getDate().compareTo(max) > 0)
                max = logEntries.get(i).getDate();
        }
        return max;

    }
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public bicycleyearFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment bicycleyearFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static bicycleyearFragment newInstance(String param1, String param2) {
        bicycleyearFragment fragment = new bicycleyearFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        sharebicycleyear=getView().findViewById(R.id.sharebicycleyear);
        sharebicycleyear.setOnClickListener(new View.OnClickListener() {
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

        calories=getView().findViewById(R.id.caloriesyearbicycle);
        calories.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                calories.setText("Total calorie consumption: "
                        + Integer.toString(totalCalories) + " cal");
            }
        });

        dailyaverage=getView().findViewById(R.id.dailyaverageyear);
        dailyaverage.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onClick(View v) {
                dailyaverage.setText("Total overall consumption time: "
                        + String.format("%.1f", totalDays) + " days");
            }
        });
        super.onCreate(savedInstanceState);

        // color your statue bar Fragment
        if (android.os.Build.VERSION.SDK_INT>= 21){
            Window window=getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.skyColor));
        }
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        BarChart chart = (BarChart) getView().findViewById(R.id.chart_bicycleYear);



        BarData data= new BarData();

        chart.setData(data);

        ArrayList<BarEntry> entriesmonth = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("Jun");
        labels.add("Jul");
        labels.add("Augest");
        labels.add("September");
        labels.add("November");
        labels.add("December");

        ((MainPage)getActivity()).setActionBarTitle("Year");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bicycleyear, container, false);
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
