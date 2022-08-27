package com.example.android.bfit;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.app.ProgressDialog;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.data.Entry;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BicycledayFragment extends Fragment {

    CombinedChart mChart;
    ProgressDialog dailog;
    ProgressBar progressBar;
    TimePicker timePicker;
    TextView dailyaverage;
    ImageButton sharebicycleday;
    public float totalDays;


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


    public BicycledayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sharebicycleday=getView().findViewById(R.id.sharebicycleday);
        sharebicycleday.setOnClickListener(new View.OnClickListener() {
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

        ((MainPage)getActivity()).setActionBarTitle("Day");


        mChart = (CombinedChart) getView().findViewById(R.id.chart_bicycleday);
        // for hour dailog


        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);


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






        // change the color of statue bar
        if (Build.VERSION.SDK_INT>= 21){
            Window window=getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.skyColor));
        }




        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bicycleday, container, false);
    }

    public void Click (View view){
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

    // add the data for bar and line combined bar

    private ArrayList<BarEntry> getBarEnteries(ArrayList<BarEntry> entries) {

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
