package com.example.android.bfit;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.uimanager.IllegalViewOperationException;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;


class GoogleFitModule  extends ReactContextBaseJavaModule implements
        LifecycleEventListener {

    private static final String REACT_MODULE = "RNGoogleFit";
    private ReactContext mReactContext;
    private GoogleFitManager mGoogleFitManager = null;

    GoogleFitModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return REACT_MODULE;
    }

    @Override
    public void initialize() {
        super.initialize();

        getReactApplicationContext().addLifecycleEventListener(this);
    }

    @Override
    public void onHostResume() {
        if (mGoogleFitManager != null) {
            mGoogleFitManager.resetAuthInProgress();
        }
    }

    @Override
    public void onHostPause() {
    }

    @Override
    public void onHostDestroy() {
    }

    @ReactMethod
    public void authorize() {
        final Activity activity = getCurrentActivity();

        if (mGoogleFitManager == null) {
            mGoogleFitManager = new GoogleFitManager(mReactContext, activity);
        }

        if (mGoogleFitManager.isAuthorized()) {
            return;
        }
        mGoogleFitManager.authorize();
    }

    @ReactMethod
    public void startFitnessRecording() {
        final PendingResult<Status> subscribe;
        //subscribe = mGoogleFitManager.getRecordingApi().subscribe();

    }

    @ReactMethod
    public void observeSteps() {
        mGoogleFitManager.getStepCounter().findFitnessDataSources();
    }

    @ReactMethod
    public void getDailySteps(double startDay, double endDay) {
        mGoogleFitManager.getStepHistory().displayLastWeeksData((long)startDay, (long)endDay);
    }

    @ReactMethod
    public void getWeeklySteps(double startDate, double endDate) {
        mGoogleFitManager.getStepHistory().displayLastWeeksData((long)startDate, (long)endDate);
    }

    @ReactMethod
    public void getDailyStepCountSamples(double startDate,
                                         double endDate,
                                         Callback errorCallback,
                                         Callback successCallback) {

        try {
            successCallback.invoke(mGoogleFitManager.getStepHistory().aggregateDataByDate((long)startDate, (long)endDate));
        } catch (IllegalViewOperationException e) {
            errorCallback.invoke(e.getMessage());
        }
    }

    @ReactMethod
    public void getDailyDistanceSamples(double startDate,
                                        double endDate,
                                        Callback errorCallback,
                                        Callback successCallback) {

        try {
            successCallback.invoke(mGoogleFitManager.getDistanceHistory().aggregateDataByDate((long)startDate, (long)endDate));
        } catch (IllegalViewOperationException e) {
            errorCallback.invoke(e.getMessage());
        }
    }

    @ReactMethod
    public void getWeightSamples(double startDate,
                                 double endDate,
                                 Callback errorCallback,
                                 Callback successCallback) {

        try {
            successCallback.invoke(mGoogleFitManager.getWeightsHistory().displayLastWeeksData((long)startDate, (long)endDate));
        } catch (IllegalViewOperationException e) {
            errorCallback.invoke(e.getMessage());
        }
    }


    @ReactMethod
    public void getDailyCalorieSamples(double startDate,
                                       double endDate,
                                       Callback errorCallback,
                                       Callback successCallback) {

        try {
            successCallback.invoke(mGoogleFitManager.getCalorieHistory().aggregateDataByDate((long)startDate, (long)endDate));
        } catch (IllegalViewOperationException e) {
            errorCallback.invoke(e.getMessage());
        }
    }

    @ReactMethod
    public void saveWeight(ReadableMap weightSample,
                           Callback errorCallback,
                           Callback successCallback) {

        try {
            successCallback.invoke(mGoogleFitManager.getWeightsHistory().saveWeight(weightSample));
        } catch (IllegalViewOperationException e) {
            errorCallback.invoke(e.getMessage());
        }
    }

    @ReactMethod
    public void deleteWeight(ReadableMap weightSample, Callback errorCallback, Callback successCallback) {
        try {
            successCallback.invoke(mGoogleFitManager.getWeightsHistory().deleteWeight(weightSample));
        } catch (IllegalViewOperationException e) {
            errorCallback.invoke(e.getMessage());
        }
    }

    @ReactMethod
    public void isAvailable(Callback errorCallback, Callback successCallback) { // true if GoogleFit installed
        try {
            successCallback.invoke(isAvailableCheck());
        } catch (IllegalViewOperationException e) {
            errorCallback.invoke(e.getMessage());
        }
    }

    @ReactMethod
    public void isEnabled(Callback errorCallback, Callback successCallback) { // true if permission granted
        try {
            successCallback.invoke(isEnabledCheck());
        } catch (IllegalViewOperationException e) {
            errorCallback.invoke(e.getMessage());
        }
    }

    private boolean isAvailableCheck() {
        PackageManager pm = mReactContext.getPackageManager();
        try {
            String GOOGLE_FIT_APP_URI = "com.google.android.apps.fitness";
            pm.getPackageInfo(GOOGLE_FIT_APP_URI, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private boolean isEnabledCheck() {
        if (mGoogleFitManager == null) {
            mGoogleFitManager = new GoogleFitManager(mReactContext, getCurrentActivity());
        }
        return mGoogleFitManager.isAuthorized();
    }

}

