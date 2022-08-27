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


import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link bicyclemonthFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link bicyclemonthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class bicyclemonthFragment extends Fragment {

    TextView dailyaverage;
    TextView calories;
    ImageButton sharebicyclemonth;

    private int totalCalories;
    private float totalDays;
    public float averageCalories;

    @SuppressLint("ValidFragment")
    public bicyclemonthFragment(List<LogEntry> logEntries) {
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

    public bicyclemonthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment bicyclemonthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static bicyclemonthFragment newInstance(String param1, String param2) {
        bicyclemonthFragment fragment = new bicyclemonthFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        sharebicyclemonth=getView().findViewById(R.id.sharebicyclemonth);
        sharebicyclemonth.setOnClickListener(new View.OnClickListener() {
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

        dailyaverage=getView().findViewById(R.id.dailyaverage);
        dailyaverage.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onClick(View v) {
                dailyaverage.setText("Total overall consumption time: "
                        + String.format("%.1f", totalDays) + " days");
            }
        });

        calories=getView().findViewById(R.id.caloriesbicycle);
        calories.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                calories.setText("Total calorie consumption: "
                        + Integer.toString(totalCalories) + " cal");
            }
        });
        super.onCreate(savedInstanceState);

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

        CombinedChart chart = (CombinedChart) getView().findViewById(R.id.chart_bicyclemonth);

        BarData data= new BarData();



        ArrayList<BarEntry> g1 = new ArrayList<>();
        g1.add(new BarEntry(1,0));
        g1.add(new BarEntry(6,6));
        g1.add(new BarEntry(11,11));
        g1.add(new BarEntry(16,16));
        g1.add(new BarEntry(21,21));
        g1.add(new BarEntry(26,26));
        g1.add(new BarEntry(31,31));

        BarDataSet set= new BarDataSet(g1,"min 0");

        ArrayList<BarEntry> bargroup2 = new ArrayList<>();
        bargroup2.add(new BarEntry(2, 2));
        bargroup2.add(new BarEntry(3, 3));
        bargroup2.add(new BarEntry(4, 4));
        bargroup2.add(new BarEntry(5, 5));
        bargroup2.add(new BarEntry(7, 7));
        bargroup2.add(new BarEntry(8, 8));
        bargroup2.add(new BarEntry(9, 9));
        bargroup2.add(new BarEntry(10, 10));
        bargroup2.add(new BarEntry(12, 12));
        bargroup2.add(new BarEntry(13, 13));
        bargroup2.add(new BarEntry(14, 14));
        bargroup2.add(new BarEntry(15, 15));
        bargroup2.add(new BarEntry(17, 17));
        bargroup2.add(new BarEntry(18, 18));
        bargroup2.add(new BarEntry(19, 19));
        bargroup2.add(new BarEntry(20, 20));
        bargroup2.add(new BarEntry(22, 22));
        bargroup2.add(new BarEntry(23, 23));
        bargroup2.add(new BarEntry(24, 24));
        bargroup2.add(new BarEntry(25, 25));
        bargroup2.add(new BarEntry(27, 27));
        bargroup2.add(new BarEntry(28, 28));
        bargroup2.add(new BarEntry(29, 29));
        bargroup2.add(new BarEntry(30, 30));

        // creating dataset for Bar Group1
        BarDataSet barDataSet1 = new BarDataSet(g1, "Hours");
        ArrayList<BarEntry> barDataSet2;
        barDataSet2 = new ArrayList<BarEntry>(bargroup2);

        ArrayList<ArrayList<BarEntry>> dataSets = new ArrayList<>();  // combined all dataset into an arraylist
        dataSets.add(g1);
        dataSets.add(barDataSet2);





        ((MainPage)getActivity()).setActionBarTitle("Month");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bicyclemonth, container, false);
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
