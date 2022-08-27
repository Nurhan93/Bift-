package com.example.android.bfit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TimePicker;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link cardayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link cardayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class cardayFragment extends Fragment {

    ImageButton sharecarday;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public cardayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment cardayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static cardayFragment newInstance(String param1, String param2) {
        cardayFragment fragment = new cardayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharecarday=getView().findViewById(R.id.sharecarday);
        sharecarday.setOnClickListener(new View.OnClickListener() {
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
            window.setStatusBarColor(this.getResources().getColor(R.color.Green));
        }
        ((MainPage)getActivity()).setActionBarTitle("Day");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        CombinedChart Chart = (CombinedChart) getView().findViewById(R.id.chart_broswerday);

        BarData generateBarData;

        BarData d = new BarData();
        ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();
        entries1 = getBarEnteries(entries1);

        BarDataSet set1 = new BarDataSet(entries1, "Bar");
        //set1.setColor(Color.rgb(60, 220, 78));
        set1.setColors(ColorTemplate.COLORFUL_COLORS);
        set1.setValueTextColor(Color.rgb(60, 220, 78));
        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);


        d.addDataSet(set1);



        LineData d1 = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

        entries = getLineEntriesData(entries);

        LineDataSet set = new LineDataSet(entries, "Day");
        set.setColors(ColorTemplate.COLORFUL_COLORS);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d1.addDataSet(set);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_carday, container, false);
    }

    public void Click2 (View view){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout= inflater.inflate(R.layout.pupup_dialog_hours,null);
        final TimePicker timePicker = (TimePicker) alertLayout.findViewById(R.id.timepicker);
        timePicker.setIs24HourView(true);


        int hour = 0;
        int minute = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
            timePicker.setHour(hour);
            timePicker.setMinute(minute);
        }



        final String timeString = String.valueOf(hour)+":"+String.valueOf(minute)+" hour";


        final AlertDialog.Builder show = new AlertDialog.Builder(getActivity());
        show.setTitle("Edit daily goal");
        show.setView(alertLayout);
        show.setCancelable(false);
        show.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        show.setPositiveButton("SET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = show.create();
        dialog.show();

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

    private ArrayList<BarEntry> getBarEnteries(ArrayList< BarEntry > entries) {

        entries.add((BarEntry) new Entry(0, 20));
        entries.add((BarEntry) new Entry(4, 10));
        entries.add((BarEntry) new Entry(5, 8));
        entries.add((BarEntry) new Entry(6, 40));
        entries.add((BarEntry) new Entry(7, 37));
        entries.add((BarEntry) new Entry(8, 37));
        entries.add((BarEntry) new Entry(9, 37));
        entries.add((BarEntry) new Entry(10, 37));
        entries.add((BarEntry) new Entry(11, 37));
        entries.add((BarEntry) new Entry(13, 37));
        entries.add((BarEntry) new Entry(14, 37));
        entries.add((BarEntry) new Entry(15, 37));
        entries.add((BarEntry) new Entry(16, 37));
        entries.add((BarEntry) new Entry(17, 37));
        entries.add((BarEntry) new Entry(18, 37));
        entries.add((BarEntry) new Entry(19, 37));
        entries.add((BarEntry) new Entry(20, 37));
        entries.add((BarEntry) new Entry(21, 37));
        entries.add((BarEntry) new Entry(22, 37));
        entries.add((BarEntry) new Entry(23, 37));


        return entries;
    }

    private ArrayList<Entry> getLineEntriesData(ArrayList<Entry> entries1) {

        entries1.add(new Entry(1,5));
        entries1.add(new Entry(2, 3));
        entries1.add(new Entry(3, 2));
        entries1.add(new Entry(8, 40));
        entries1.add(new Entry(12, 37));
        entries1.add(new Entry(16, 37));
        entries1.add(new Entry(20, 37));
        entries1.add(new Entry(00, 37));
        return entries1;

    }
}
