package com.example.android.bfit;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class InsightFragment extends Fragment {
ImageButton back13;
ImageButton share_sleep1;
ImageButton share_sleep2;
ImageButton share_sleep3;
ImageButton share_sleep4;
ImageButton share_steps1;
ImageButton share_steps2;
ImageButton share_steps3;
ImageButton share_steps4;
ImageButton share_steps5;
ImageButton share_steps6;
ImageButton share_steps7;
LineChart chart_StepSummary;
BarChart chart_summary_sleep;
LineChart chart_summary_weekstep2;
LineChart chart_summary_weekstep3;
BarChart chart_summary_sleepweek;
LineChart chart_summary_weekstep4;
LineChart chart_summary_weekstep5;
BarChart chart_summary_sleepweek1;
LineChart chart_summary_weekstep6;
LineChart chart_summary_weekstep7;
BarChart chart_summary_sleepweek3;
LineDataSet lineDataSet;
Context Context;





    public InsightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // for steps summary line chart
        chart_StepSummary=getView().findViewById(R.id.chart_StepSummary);
        LineChart chart_StepSummary= new LineChart(Context);

        ArrayList<Entry> lineEntries = null;
        lineEntries.add(new Entry(0, 4));
        lineEntries.add(new Entry(1, 1));
        lineEntries.add(new Entry(2, 2));
        lineEntries.add(new Entry(3, 4));

        LineData lineData = new LineData();
        chart_StepSummary.setData(lineData);

        lineDataSet = new LineDataSet(lineEntries, "Steps Summary");

        final String[] months = new String[]{"Jan", "Feb", "Mar", "Apr","May","Jun","July","Aug","Sep","Oct","Nov","Dec"};

        // for summary sleep
        chart_summary_sleep=getView().findViewById(R.id.chart_summary_sleep);

        BarData data= new BarData();

        chart_summary_sleep.setData(data);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(2,1));
        entries.add(new BarEntry(3,2));
        entries.add(new BarEntry(4,3));
        entries.add(new BarEntry(5,4));
        entries.add(new BarEntry(6,5));
        entries.add(new BarEntry(7,6));
        entries.add(new BarEntry(8,7));
        entries.add(new BarEntry(9,8));
        entries.add(new BarEntry(10,9));


        YAxis leftAxis = chart_summary_sleep.getAxisLeft();
        leftAxis.setDrawAxisLine(false); // to remove y axis left vertical line

        // chart for week 2
        chart_summary_weekstep2=getView().findViewById(R.id.chart_summary_weekstep2);

        LineChart chart_summary_weekstep2= new LineChart(Context);

        ArrayList<Entry> lineEntries1 = null;
        lineEntries1.add(new Entry(0, 4));
        lineEntries1.add(new Entry(1, 1));
        lineEntries1.add(new Entry(2, 2));
        lineEntries1.add(new Entry(3, 4));

        LineData lineData1 = new LineData();
        chart_summary_weekstep2.setData(lineData);

        lineDataSet = new LineDataSet(lineEntries1, "Steps Summary Week 1");

        final String[] months1 = new String[]{"Jan", "Feb", "Mar", "Apr","May","Jun","July","Aug","Sep","Oct","Nov","Dec"};

        // chart steps for week 3
        chart_summary_weekstep3=getView().findViewById(R.id.chart_summary_weekstep3);

        LineChart chart_summary_weekstep3= new LineChart(Context);

        ArrayList<Entry> lineEntries2 = null;
        lineEntries2.add(new Entry(0, 4));
        lineEntries2.add(new Entry(1, 1));
        lineEntries2.add(new Entry(2, 2));
        lineEntries2.add(new Entry(3, 4));

        LineData lineData2 = new LineData();
        chart_summary_weekstep3.setData(lineData);

        lineDataSet = new LineDataSet(lineEntries2, "Steps Summary");

        final String[] months2 = new String[]{"Jan", "Feb", "Mar", "Apr","May","Jun","July","Aug","Sep","Oct","Nov","Dec"};

        // chart databar for sleep week
        chart_summary_sleepweek=getView().findViewById(R.id.chart_summary_sleepweek);
        BarData datasleep1= new BarData();

        chart_summary_sleep.setData(datasleep1);

        ArrayList<BarEntry> entries3 = new ArrayList<>();
        entries3.add(new BarEntry(2,1));
        entries3.add(new BarEntry(3,2));
        entries3.add(new BarEntry(4,3));
        entries3.add(new BarEntry(5,4));
        entries3.add(new BarEntry(6,5));
        entries3.add(new BarEntry(7,6));
        entries3.add(new BarEntry(8,7));
        entries3.add(new BarEntry(9,8));
        entries3.add(new BarEntry(10,9));


        YAxis leftAxis3 = chart_summary_sleep.getAxisLeft();
        leftAxis3.setDrawAxisLine(false); // to remove y axis left vertical line
        // chart for steps week 4
        chart_summary_weekstep4=getView().findViewById(R.id.chart_summary_weekstep4);

        LineChart chart_summary_weekstep4= new LineChart(Context);

        ArrayList<Entry> lineEntries4 = null;
        lineEntries4.add(new Entry(0, 4));
        lineEntries4.add(new Entry(1, 1));
        lineEntries4.add(new Entry(2, 2));
        lineEntries4.add(new Entry(3, 4));

        LineData lineData4 = new LineData();
        chart_summary_weekstep4.setData(lineData4);

        lineDataSet = new LineDataSet(lineEntries4, "Steps Summary Week 4");

        final String[] months4 = new String[]{"Jan", "Feb", "Mar", "Apr","May","Jun","July","Aug","Sep","Oct","Nov","Dec"};

        // chart for steps week 5
        chart_summary_weekstep5=getView().findViewById(R.id.chart_summary_weekstep5);

        LineChart chart_summary_weekstep5= new LineChart(Context);

        ArrayList<Entry> lineEntries5 = null;
        lineEntries5.add(new Entry(0, 4));
        lineEntries5.add(new Entry(1, 1));
        lineEntries5.add(new Entry(2, 2));
        lineEntries5.add(new Entry(3, 4));

        LineData lineData5 = new LineData();
        chart_summary_weekstep5.setData(lineData5);

        lineDataSet = new LineDataSet(lineEntries, "Steps Summary week 5");

        final String[] months5 = new String[]{"Jan", "Feb", "Mar", "Apr","May","Jun","July","Aug","Sep","Oct","Nov","Dec"};

        chart_summary_sleepweek1=getView().findViewById(R.id.chart_summary_sleepweek1);

        BarData databar= new BarData();

        chart_summary_sleepweek1.setData(databar);

        ArrayList<BarEntry> entries1 = new ArrayList<>();
        entries1.add(new BarEntry(2,1));
        entries1.add(new BarEntry(3,2));
        entries1.add(new BarEntry(4,3));
        entries1.add(new BarEntry(5,4));
        entries1.add(new BarEntry(6,5));
        entries1.add(new BarEntry(7,6));
        entries1.add(new BarEntry(8,7));
        entries1.add(new BarEntry(9,8));
        entries1.add(new BarEntry(10,9));


        YAxis leftAxis1 = chart_summary_sleepweek1.getAxisLeft();
        leftAxis1.setDrawAxisLine(false); // to remove y axis left vertical line

        // chart summary for steps week 6
        chart_summary_weekstep6=getView().findViewById(R.id.chart_summary_weekstep6);

        LineChart chart_summary_weekstep6= new LineChart(Context);

        ArrayList<Entry> lineEntries6 = null;
        lineEntries6.add(new Entry(0, 4));
        lineEntries6.add(new Entry(1, 1));
        lineEntries6.add(new Entry(2, 2));
        lineEntries6.add(new Entry(3, 4));

        LineData lineData6 = new LineData();
        chart_summary_weekstep6.setData(lineData6);

        lineDataSet = new LineDataSet(lineEntries6, "Steps Summary");

        final String[] months6 = new String[]{"Jan", "Feb", "Mar", "Apr","May","Jun","July","Aug","Sep","Oct","Nov","Dec"};

        // steps summary week 7
        chart_summary_weekstep7=getView().findViewById(R.id.chart_summary_weekstep7);

        LineChart chart_summary_weekstep7= new LineChart(Context);

        ArrayList<Entry> lineEntries7 = null;
        lineEntries7.add(new Entry(0, 4));
        lineEntries7.add(new Entry(1, 1));
        lineEntries7.add(new Entry(2, 2));
        lineEntries7.add(new Entry(3, 4));

        LineData lineData7 = new LineData();
        chart_summary_weekstep7.setData(lineData7);

        lineDataSet = new LineDataSet(lineEntries7, "Steps Summary");

        final String[] months7 = new String[]{"Jan", "Feb", "Mar", "Apr","May","Jun","July","Aug","Sep","Oct","Nov","Dec"};


        chart_summary_sleepweek3=getView().findViewById(R.id.chart_summary_sleepweek3);
        BarData datasleep= new BarData();

        chart_summary_sleep.setData(datasleep);

        ArrayList<BarEntry> entries2 = new ArrayList<>();
        entries2.add(new BarEntry(2,1));
        entries2.add(new BarEntry(3,2));
        entries2.add(new BarEntry(4,3));
        entries2.add(new BarEntry(5,4));
        entries2.add(new BarEntry(6,5));
        entries2.add(new BarEntry(7,6));
        entries2.add(new BarEntry(8,7));
        entries2.add(new BarEntry(9,8));
        entries2.add(new BarEntry(10,9));


        YAxis leftAxis2 = chart_summary_sleep.getAxisLeft();
        leftAxis2.setDrawAxisLine(false); // to remove y axis left vertical line


        // for share buttons
        share_sleep1=getView().findViewById(R.id.share_summary_sleep);
        share_sleep1.setOnClickListener(new View.OnClickListener() {
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
        share_sleep2=getView().findViewById(R.id.share_sleepweek);
        share_sleep2.setOnClickListener(new View.OnClickListener() {
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
        share_sleep3=getView().findViewById(R.id.sharesleepweek1);
        share_sleep3.setOnClickListener(new View.OnClickListener() {
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
        share_sleep4=getView().findViewById(R.id.share_sleepweek3);
        share_sleep4.setOnClickListener(new View.OnClickListener() {
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
        share_steps1=getView().findViewById(R.id.share_stepssummary);
        share_steps1.setOnClickListener(new View.OnClickListener() {
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
        share_steps2=getView().findViewById(R.id.share_weeksteps2);
        share_steps2.setOnClickListener(new View.OnClickListener() {
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
        share_steps3=getView().findViewById(R.id.share_weeksteps3);
        share_steps3.setOnClickListener(new View.OnClickListener() {
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
        share_steps4=getView().findViewById(R.id.share_weeksteps4);
        share_steps4.setOnClickListener(new View.OnClickListener() {
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
        share_steps5=getView().findViewById(R.id.share_weeksteps5);
        share_steps5.setOnClickListener(new View.OnClickListener() {
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
        share_steps6=getView().findViewById(R.id.share_weeksteps6);
        share_steps6.setOnClickListener(new View.OnClickListener() {
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
        share_steps7=getView().findViewById(R.id.share_weeksteps7);
        share_steps7.setOnClickListener(new View.OnClickListener() {
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
        View rootView= inflater.inflate(R.layout.fragment_insight, container, false);
        ((MainPage)getActivity()).setActionBarTitle("Insight");

        if (android.os.Build.VERSION.SDK_INT>= 21){
            Window window=getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Blurgreen));
        }
        // Inflate the layout for this fragment

        // for images button create
         back13=rootView.findViewById(R.id.Back13);
        back13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity= new Intent(getActivity(),MainPage.class);
                startActivity(intentLoadNewActivity);
            }
        });
        return  rootView;
    }


    public static void instantiate(Context context) {
    }
}
