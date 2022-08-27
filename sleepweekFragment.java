package com.example.android.bfit;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link sleepweekFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link sleepweekFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class sleepweekFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public sleepweekFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment sleepweekFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static sleepweekFragment newInstance(String param1, String param2) {
        sleepweekFragment fragment = new sleepweekFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        if (android.os.Build.VERSION.SDK_INT>= 21){
            Window window=getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.darkpurple));
        }
        super.onCreate(savedInstanceState);
        ((MainPage)getActivity()).setActionBarTitle("Week");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        HorizontalBarChart horizontalBarChart = (HorizontalBarChart) getView().findViewById(R.id.chart_sleepweek);
        // create BarEntry for Bar Group 1
        ArrayList<BarEntry> bargroup1 = new ArrayList<>();
        horizontalBarChart.getBarBounds(new BarEntry(1f, 12));
        horizontalBarChart.getBarBounds(new BarEntry(3f, 18));
        horizontalBarChart.getBarBounds(new BarEntry(5f, 00));
        horizontalBarChart.getBarBounds(new BarEntry(7f, 06));
        horizontalBarChart.getBarBounds(new BarEntry(9f, 12));

// create BarEntry for Bar Group 1
        ArrayList<BarEntry> bargroup2 = new ArrayList<>();
        bargroup2.add(new BarEntry(2f, 13));
        bargroup2.add(new BarEntry(4f, 14));
        bargroup2.add(new BarEntry(6f, 15));
        bargroup2.add(new BarEntry(8f, 16));
        bargroup2.add(new BarEntry(10f, 17));
        bargroup2.add(new BarEntry(11f, 19));


        // creating dataset for Bar Group1
        BarDataSet barDataSet1 = new BarDataSet(bargroup1, "Hours");
        BarDataSet barDataSet2 = new BarDataSet(bargroup2, "00:00");

        ArrayList<BarDataSet> dataSets = new ArrayList<>();  // combined all dataset into an arraylist
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Saturday");
        labels.add("Sunday");
        labels.add("Monday");
        labels.add("Tuesday");
        labels.add("Thursday");
        labels.add("Wednesday");
        labels.add("Friday");

        ArrayList labels1= new ArrayList<Integer>();

// initialize the Bardata with argument labels and dataSet
        BarData data= new BarData(labels, dataSets);
        horizontalBarChart.setData(data);
        horizontalBarChart.getXAxis().setDrawGridLines(false);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sleepweek, container, false);
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
