package com.example.android.bfit;

import android.util.Log;

import android.support.annotation.Nullable;


import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResult;



import org.json.JSONObject;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


class CalorieHistory {


    private ReactContext mReactContext;
    private GoogleFitManager googleFitManager;

    private static final String TAG = "CalorieHistory";

    
    public CalorieHistory(ReactContext mReactContext, GoogleFitManager googleFitManager) {
        ReactContext reactContext = null;
        this.mReactContext = reactContext;
        this.googleFitManager = googleFitManager;
    }

    public ReadableArray aggregateDataByDate(long startTime, long endTime) {

        DateFormat dateFormat = DateFormat.getDateInstance();
        Log.i(TAG, "Range Start: " + dateFormat.format(startTime));
        Log.i(TAG, "Range End: " + dateFormat.format(endTime));

        //Check how much calories were expended in specific days.
        DataReadRequest readRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_CALORIES_EXPENDED, DataType.AGGREGATE_CALORIES_EXPENDED)
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();

        DataReadResult dataReadResult = Fitness.HistoryApi.readData(googleFitManager.getGoogleApiClient(), readRequest).await(1, TimeUnit.MINUTES);


        WritableArray map = Arguments.createArray();

        //Used for aggregated data
        if (dataReadResult.getBuckets().size() > 0) {
            Log.i(TAG, "Number of buckets: " + dataReadResult.getBuckets().size());
            for (Bucket bucket : dataReadResult.getBuckets()) {
                List<DataSet> dataSets = bucket.getDataSets();
                for (DataSet dataSet : dataSets) {
                    processDataSet(dataSet, map);
                }
            }
        }
        //Used for non-aggregated data
        else if (dataReadResult.getDataSets().size() > 0) {
            Log.i(TAG, "Number of returned DataSets: " + dataReadResult.getDataSets().size());
            for (DataSet dataSet : dataReadResult.getDataSets()) {
                processDataSet(dataSet, map);
            }
        }

        return map;
    }


    // utility function that gets the basal metabolic rate averaged over a week
    private float getBasalAVG(long _et) throws Exception {
        float basalAVG = 0;
        Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(new Date(_et));
        //set start time to a week before end time
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        long nst = cal.getTimeInMillis();

        DataReadRequest.Builder builder = new DataReadRequest.Builder();
        builder.aggregate(DataType.TYPE_BASAL_METABOLIC_RATE, DataType.AGGREGATE_BASAL_METABOLIC_RATE_SUMMARY);
        builder.bucketByTime(1, TimeUnit.DAYS);
        builder.setTimeRange(nst, _et, TimeUnit.MILLISECONDS);
        DataReadRequest readRequest = builder.build();

        DataReadResult dataReadResult = Fitness.HistoryApi.readData(googleFitManager.getGoogleApiClient(), readRequest).await();

        if (dataReadResult.getStatus().isSuccess()) {
            JSONObject obj = new JSONObject();
            int avgsN = 0;
            for (Bucket bucket : dataReadResult.getBuckets()) {
                // in the com.google.bmr.summary data type, each data point represents
                // the average, maximum and minimum basal metabolic rate, in kcal per day, over the time interval of the data point.
                DataSet ds = bucket.getDataSet(DataType.AGGREGATE_BASAL_METABOLIC_RATE_SUMMARY);
                for (DataPoint dp : ds.getDataPoints()) {
                    float avg = dp.getValue(Field.FIELD_AVERAGE).asFloat();
                    basalAVG += avg;
                    avgsN++;
                }
            }
            // do the average of the averages
            if (avgsN != 0) basalAVG /= avgsN; // this a daily average
            return basalAVG;
        } else throw new Exception(dataReadResult.getStatus().getStatusMessage());
    }


    private void processDataSet(DataSet dataSet, WritableArray map) {
        Log.i(TAG, "Data returned for Data type: " + dataSet.getDataType().getName());
        DateFormat dateFormat = DateFormat.getDateInstance();
        DateFormat timeFormat = DateFormat.getTimeInstance();
        Format formatter = new SimpleDateFormat("EEE");
        WritableMap stepMap = Arguments.createMap();


        for (DataPoint dp : dataSet.getDataPoints()) {
            Log.i(TAG, "Data point:");
            Log.i(TAG, "\tType: " + dp.getDataType().getName());
            Log.i(TAG, "\tStart: " + dateFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)) + " " + timeFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)));
            Log.i(TAG, "\tEnd: " + dateFormat.format(dp.getEndTime(TimeUnit.MILLISECONDS)) + " " + timeFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)));

            String day = formatter.format(new Date(dp.getStartTime(TimeUnit.MILLISECONDS)));
            Log.i(TAG, "Day: " + day);

            for(Field field : dp.getDataType().getFields()) {
                Log.i("History", "\tField: " + field.getName() +
                        " Value: " + dp.getValue(field));

                stepMap.putString("day", day);
                stepMap.putDouble("startDate", dp.getStartTime(TimeUnit.MILLISECONDS));
                stepMap.putDouble("endDate", dp.getEndTime(TimeUnit.MILLISECONDS));
                float basal = 0;
                try {
                    basal = getBasalAVG(dp.getEndTime(TimeUnit.MILLISECONDS));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                stepMap.putDouble("calorie", dp.getValue(field).asFloat() - basal);
                map.pushMap(stepMap);
            }
        }
    }
}
