package com.example.android.bfit;



import com.facebook.react.bridge.ReactContext;
import android.util.Log;

import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessStatusCodes;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.fitness.data.DataType;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import com.google.android.gms.fitness.result.ListSubscriptionsResult;
import com.google.android.gms.fitness.data.Subscription;

import com.facebook.react.modules.core.DeviceEventManagerModule;
import android.support.annotation.Nullable;

public class RecordingApi  {

    private ReactContext reactContext;
    private GoogleFitManager googleFitManager;

    private static final String TAG = "RecordingApi";

    public RecordingApi (ReactContext reactContext, GoogleFitManager googleFitManager) {

        this.reactContext = reactContext;
        this.googleFitManager = googleFitManager;

    }

    public void subscribe () {

        Fitness.RecordingApi.subscribe(googleFitManager.getGoogleApiClient(), DataType.TYPE_STEP_COUNT_CUMULATIVE)
                .setResultCallback(new ResultCallback <Status> () {

                    @Override
                    public void onResult(Status status) {

                        WritableMap map = Arguments.createMap();

                        if (status.isSuccess()) {
                            map.putBoolean("recording", true);
                            Log.i(TAG, "RecordingAPI - Connected");
                            sendEvent(reactContext, "STEP_RECORDING", map);

                        } else {
                            map.putBoolean("recording", false);
                            Log.i(TAG, "RecordingAPI - Error connecting");
                            sendEvent(reactContext, "STEP_RECORDING", map);
                        }
                    }
                });
        Fitness.RecordingApi.subscribe(googleFitManager.getGoogleApiClient(), DataType.TYPE_DISTANCE_DELTA)
                .setResultCallback(new ResultCallback <Status> () {


                    public void onResult(Status status) {

                        WritableMap map = Arguments.createMap();

                        if (status.isSuccess()) {
                            map.putBoolean("recording", true);
                            // Log.i(TAG, "RecordingAPI - Connected");
                            sendEvent(reactContext, "DISTANCE_RECORDING", map);

                        } else {
                            map.putBoolean("recording", false);
                            // Log.i(TAG, "RecordingAPI - Error connecting");
                            sendEvent(reactContext, "DISTANCE_RECORDING", map);
                        }
                    }
                });
    }


    private void sendEvent(ReactContext reactContext,
                           String eventName, @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }
}
