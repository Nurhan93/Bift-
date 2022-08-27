package com.example.android.bfit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.UiThreadUtil.*;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.result.DataSourcesResult;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.google.android.gms.common.api.*;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.Field;
import com.scichart.charting.model.dataSeries.IXyDataSeries;
import com.scichart.charting.modifiers.ModifierGroup;
import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.charting.visuals.annotations.IAnnotation;
import com.scichart.charting.visuals.axes.IAxis;
import com.scichart.charting.visuals.renderableSeries.FastMountainRenderableSeries;
import com.scichart.charting.visuals.renderableSeries.IRenderableSeries;
import com.scichart.core.framework.UpdateSuspender;
import com.scichart.drawing.utility.ColorUtil;
import com.scichart.extensions.builders.SciChartBuilder;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.Date;



//import SciChart_Android_v2.Examples.app.src.main.java.com.scichart.examples.data.PriceSeries;
import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link heartdayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link heartdayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class heartdayFragment extends Fragment {

    ExpandableTextView highstress;
    ExpandableTextView lowstress;
    ExpandableTextView meduimstress;
    ExpandableTextView recovery;
    BarChart heartchart;
    HorizontalBarChart Chart_stressandrecovery;
    Context Context;
    @BindView(R.id.chart_heart)
    SciChartSurface surface;
    ImageButton information_heartday;
    ImageButton information_stressday;
    ImageButton shareheartday;




    private ReactContext mReactContext;
    private static boolean mAuthInProgress = false;
    private Activity mActivity;
    private static final int REQUEST_OAUTH = 1;
    private static final String AUTH_PENDING = "auth_state_pending";
    private static final String TAG = "Heart beat";
    private boolean authInProgress = false;
    private GoogleApiClient mClient;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public heartdayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment heartdayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static heartdayFragment newInstance(String param1, String param2) {
        heartdayFragment fragment = new heartdayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        shareheartday=getView().findViewById(R.id.shareheartday);
        shareheartday.setOnClickListener(new View.OnClickListener() {
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

        information_stressday=getView().findViewById(R.id.information_stressday);
        information_stressday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent= new Intent(getActivity(),information_stressandrecovery_fragment.class);
                startActivity(myintent);
            }
        });

        information_heartday=getView().findViewById(R.id.information_heartday);
        information_heartday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(),information_pulse_fragment.class);
                startActivity(intent);
            }
        });

        // mountain chart for heart

        // Create a SciChartSurface
        final SciChartSurface surface = new SciChartSurface(Context);

        // Initialize the SciChartBuilder
        SciChartBuilder.init(Context);

        // Obtain the SciChartBuilder instance
        final SciChartBuilder sciChartBuilder = SciChartBuilder.instance();

        // Create a numeric X axis
        final IAxis xAxis = sciChartBuilder.newNumericAxis()
                .withAxisTitle("Heart Beat")
                .withVisibleRange(-5, 15)
                .build();

        // Create interactivity modifiers
        ModifierGroup chartModifiers = sciChartBuilder.newModifierGroup()
                .withPinchZoomModifier().withReceiveHandledEvents(true).build()
                .withZoomPanModifier().withReceiveHandledEvents(true).build()
                .build();

        final IRenderableSeries lineSeries = sciChartBuilder.newLineSeries()
                .withStrokeStyle(ColorUtil.LightBlue, 2f, true)
                .build();

// Add a RenderableSeries onto the SciChartSurface
        surface.getRenderableSeries().add(lineSeries);

        // Add the Y axis to the YAxes collection of the surface
        IAxis yAxis = null;
        Collections.addAll(surface.getYAxes(), yAxis);

        // Add the X axis to the XAxes collection of the surface
        Collections.addAll(surface.getXAxes(), xAxis);

        // Add the annotation to the Annotations collection of the surface
        IAnnotation textAnnotation = null;
        Collections.addAll(surface.getAnnotations(), textAnnotation);

        // Add the interactions to the ChartModifiers collection of the surface
        Collections.addAll(surface.getChartModifiers(), chartModifiers);


        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            authInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
        }
        mClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Fitness.SENSORS_API)
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ))
                .addScope(new Scope(Scopes.FITNESS_BODY_READ))
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                .build();
    }

    public void onStart() {
        super.onStart();
        mClient.connect();
    }


    public void onConnected(Bundle bundle) {
        final DataSourcesRequest dataSourceRequest = new DataSourcesRequest.Builder()
                .setDataTypes(DataType.TYPE_HEART_RATE_BPM)
                .setDataSourceTypes(DataSource.TYPE_RAW)
                .build();

        Fitness.SensorsApi.findDataSources(mClient, dataSourceRequest)
                .setResultCallback(new ResultCallback<DataSourcesResult>() {
                    @Override
                    public void onResult(DataSourcesResult dataSourcesResult) {
                        for(DataSource dataSource : dataSourcesResult.getDataSources())
                        {
                            Log.i(TAG, "Data source found: " + dataSource.toString());
                            Log.i(TAG, "Data Source type: " + dataSource.getDataType().getName());
                            registerFitnessDataListener(dataSource, DataType.TYPE_HEART_RATE_BPM);
                        }
                    }
                });
//        dataSourceRequest = new DataSourcesRequest.Builder()
//                .setDataTypes(DataType.TYPE_STEP_COUNT_CUMULATIVE)
//                .setDataSourceTypes(DataSource.TYPE_RAW)
//                .build();
//        Fitness.SensorsApi.findDataSources(mApiClient, dataSourceRequest)
//                .setResultCallback(dataSourcesResultCallback);
    }


    public void onConnectionSuspended(int i) {

    }


    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (!authInProgress) {
            try {
                authInProgress = true;
                connectionResult.startResolutionForResult(getActivity(), REQUEST_OAUTH);
            } catch (IntentSender.SendIntentException e) {
            }
        } else {
            Log.e("GoogleFit", "authInProgress");
        }
    }


    public void onDataPoint(DataPoint dataPoint) {
        for (final Field field : dataPoint.getDataType().getFields()) {
            final Value value = dataPoint.getValue(field);
            Log.i("DATASOURCE", field.getName());
            Log.i("DATASOURCE", value.toString());
            getActivity().runOnUiThread(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    TextView tv = (TextView) getView().findViewById(R.id.run);
                    tv.setText("Field: " + field.getName() + " Value: " + value);
                    Toast.makeText(getActivity().getApplicationContext(), "Field: " + field.getName() + " Value: " + value, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_OAUTH) {
            authInProgress = false;
            if (resultCode == REQUEST_OAUTH) {
                if (!mClient.isConnecting() && !mClient.isConnected()) {
                    mClient.connect();
                } else {
                    Log.e("GoogleFit", "connected");
                }
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                Log.e("GoogleFit", "Result_Canceled");
            }
        } else {
            Log.e("GoogleFit", "Request not OAUTH");
        }
    }

    private void registerFitnessDataListener(final DataSource dataSource, DataType dataType) {

        SensorRequest request = new SensorRequest.Builder()
                .setDataSource(dataSource)
                .setDataType(dataType)
                .setSamplingRate(2, TimeUnit.SECONDS)
                .build();

        Fitness.SensorsApi.add(mClient, request, (OnDataPointListener) this)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (!status.isSuccess()) {
                            Log.e("DATASOURCES", "register " + dataSource.getName() + " failed");
                        } else {
                            Log.i("DATASOURCES", "register " + dataSource.getName() + " succeed");
                        }
                    }
                });


        highstress = (ExpandableTextView) getView().findViewById(R.id.highstress).findViewById(R.id.expand_text_view);
        lowstress = (ExpandableTextView) getView().findViewById(R.id.lowstress).findViewById(R.id.expand_text_lowstress);
        meduimstress = (ExpandableTextView) getView().findViewById(R.id.Meduimstress).findViewById(R.id.expand_text_meduimstress);
        recovery = (ExpandableTextView) getView().findViewById(R.id.recovery).findViewById(R.id.expand_text_recovery);


        // horizontal chart for stress and recovery

        Chart_stressandrecovery=getView().findViewById(R.id.Chart_stressandrecovery);
        // bar chart for heart beat

        LineChart chart = (LineChart) getView().findViewById(R.id.chart_heartbeat);

        BarData data = new BarData();

        heartchart.setData(data);

        ArrayList<BarEntry> entriesheart = new ArrayList<>();
        entriesheart.add(new BarEntry(00, 0));
        entriesheart.add(new BarEntry(02, 6));
        entriesheart.add(new BarEntry(04, 11));
        entriesheart.add(new BarEntry(06, 16));
        entriesheart.add(new BarEntry(8, 21));
        entriesheart.add(new BarEntry(10, 26));
        entriesheart.add(new BarEntry(12, 31));
        entriesheart.add(new BarEntry(14, 31));
        entriesheart.add(new BarEntry(16, 31));
        entriesheart.add(new BarEntry(18, 31));
        entriesheart.add(new BarEntry(20, 31));
        entriesheart.add(new BarEntry(22, 31));
        entriesheart.add(new BarEntry(24, 31));

        YAxis leftAxis = heartchart.getAxisLeft();


    






        // color your statue bar Fragment
        if (android.os.Build.VERSION.SDK_INT>= 21){
            Window window=getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Blurgreen));
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_heartday, container, false);
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




    protected void initExample() {


        final SciChartBuilder sciChartBuilder = null;
        final IAxis xBottomAxis = sciChartBuilder.newDateAxis().withGrowBy(0.1d, 0.1d).build();
        final IAxis yRightAxis = sciChartBuilder.newNumericAxis().withGrowBy(0.1d, 0.1d).build();

        //final PriceSeries priceData = DataManager.getInstance().getPriceDataIndu(getActivity());
        //final IXyDataSeries<Date, Double> dataSeries = sciChartBuilder.newXyDataSeries(Date.class, Double.class).build();
        //dataSeries.append(priceData.getDateData(), priceData.getCloseData());

        final FastMountainRenderableSeries rSeries = sciChartBuilder.newMountainSeries()
                //.withDataSeries(dataSeries)
                .withStrokeStyle(0xAAFFC9A8, 1f, true)
                .withAreaFillLinearGradientColors(0xAAFF8D42, 0x88090E11)
                .build();

        UpdateSuspender.using(surface, new Runnable() {
            @Override
            public void run() {
                Collections.addAll(surface.getXAxes(), xBottomAxis);
                Collections.addAll(surface.getYAxes(), yRightAxis);
                Collections.addAll(surface.getRenderableSeries(), rSeries);
                assert sciChartBuilder != null;
                Collections.addAll(surface.getChartModifiers(), sciChartBuilder.newModifierGroupWithDefaultModifiers().build());

                Objects.requireNonNull(sciChartBuilder).newAnimator(rSeries).withWaveTransformation().withInterpolator(new DecelerateInterpolator()).withDuration(3000).withStartDelay(350).start();
            }
        });
    }
}

