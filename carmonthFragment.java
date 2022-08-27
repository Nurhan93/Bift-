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
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link carmonthFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link carmonthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class carmonthFragment extends Fragment {
    ImageButton sharecarmonth;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private float totalDays;
    TextView dailyaveragerunyear;

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

    private OnFragmentInteractionListener mListener;

    public carmonthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment carmonthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static carmonthFragment newInstance(String param1, String param2) {
        carmonthFragment fragment = new carmonthFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        sharecarmonth=getView().findViewById(R.id.sharecarmonth);
        sharecarmonth.setOnClickListener(new View.OnClickListener() {
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

        dailyaveragerunyear=getView().findViewById(R.id.dailyaveragemonthcameramonth);
        dailyaveragerunyear.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onClick(View v) {
                dailyaveragerunyear.setText("Total overall consumption time: "
                        + String.format("%.1f", totalDays) + " Years");
            }
        });

        // color your statue bar Fragment
        if (android.os.Build.VERSION.SDK_INT>= 21){
            Window window=getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Green));
        }
        super.onCreate(savedInstanceState);
        ((MainPage)getActivity()).setActionBarTitle("Month");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        BarChart chart = (BarChart) getView().findViewById(R.id.chart_carmonth);

        BarData data= new BarData();

        chart.setData(data);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1,0));
        entries.add(new BarEntry(6,6));
        entries.add(new BarEntry(11,11));
        entries.add(new BarEntry(16,16));
        entries.add(new BarEntry(21,21));
        entries.add(new BarEntry(26,26));
        entries.add(new BarEntry(31,31));

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawAxisLine(false); // to remove y axis left vertical line


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
        BarDataSet barDataSet1 = new BarDataSet(entries, "Hours");
        ArrayList<BarEntry> barDataSet2;
        barDataSet2 = new ArrayList<BarEntry>(bargroup2);

        ArrayList<ArrayList<BarEntry>> dataSets = new ArrayList<>();  // combined all dataset into an arraylist
        dataSets.add(entries);
        dataSets.add(barDataSet2);

        BarDataSet set= new BarDataSet(entries,"min 0");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_carmonth, container, false);
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
