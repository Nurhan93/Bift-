package com.example.android.bfit;

import android.annotation.SuppressLint;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link musicyearFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link musicyearFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class musicyearFragment extends Fragment {

    // for categoriez the app storage
    public final static String GOOGLE_URL= "https://play.google.com/store/apps/category/MUSIC_AND_AUDIO"; // url for the categories i used in music
    public static final String ERROR = "error";
    private ActivityUtil mActivityUtil;
    private static final String TAG = "UsageStatsActivity";
    private static final boolean localLOGV = false;
    private UsageStatsManager mUsageStatsManager;
    private LayoutInflater mInflater;
    private movieweekFragment.UsageStatsAdapter mAdapter;
    private PackageManager mPm;
    private float totalDays;
    TextView dailyaverager;

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

    public musicyearFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment musicyearFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static musicyearFragment newInstance(String param1, String param2) {
        musicyearFragment fragment = new musicyearFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        dailyaverager=getView().findViewById(R.id.dailyaveragemusicyear);
        dailyaverager.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                dailyaverager.setText("Total overall consumption time: "
                        + String.format("%.1f", totalDays) + " Years");
            }
        });


        // music bar chart
        BarChart chart = (BarChart) getView().findViewById(R.id.chart_musicyear);
// for hours
        ArrayList<BarEntry> BarEntry = new ArrayList<>();
        BarEntry.add(new BarEntry(01f, 0));
        BarEntry.add(new BarEntry(02f, 1));
        BarEntry.add(new BarEntry(03f, 2));
        BarEntry.add(new BarEntry(04f, 3));
        BarEntry.add(new BarEntry(05f, 4));
        BarEntry.add(new BarEntry(06f, 5));
        BarEntry.add(new BarEntry(07f, 6));
        BarEntry.add(new BarEntry(08f, 7));
        BarEntry.add(new BarEntry(09f, 8));
        BarEntry.add(new BarEntry(10f, 9));
        BarEntry.add(new BarEntry(11f, 10));
        BarEntry.add(new BarEntry(12f, 11));

        BarDataSet dataSet = new BarDataSet(BarEntry, "00:00 Min");

        ArrayList<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("July");
        labels.add("August");
        labels.add("September");
        labels.add("October");
        labels.add("November");
        labels.add("December");

        BarData data = new BarData(labels, dataSet);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        chart.setData(data);
        chart.setDescription("Broswer");

        if (android.os.Build.VERSION.SDK_INT>= 21){
            Window window=getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Lightred));
        }
        super.onCreate(savedInstanceState);
        ((MainPage)getActivity()).setActionBarTitle("Year");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_musicyear, container, false);
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
