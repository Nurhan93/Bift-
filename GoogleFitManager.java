package com.example.android.bfit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.RecordingApi;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.result.ListSubscriptionsResult;


@SuppressLint("ValidFragment")
class GoogleFitManager extends stepsdayFragment implements ActivityEventListener {

    private ReactContext mReactContext;
    private GoogleApiClient mApiClient;
    private static final int REQUEST_OAUTH = 1001;
    private static final String AUTH_PENDING = "auth_state_pending";
    private static boolean mAuthInProgress = false;
    private Activity mActivity;

    private DistanceHistory distanceHistory;
    private StepHistory stepHistory;
    private WeightsHistory weightsHistory;
    private CalorieHistory calorieHistory;
    private StepCounter mStepCounter;
    private StepSensor stepSensor;
    private RecordingApi recordingApi = new RecordingApi() {
        @Override
        public PendingResult<Status> subscribe(GoogleApiClient googleApiClient, DataType dataType) {
            return null;
        }

        @Override
        public PendingResult<Status> subscribe(GoogleApiClient googleApiClient, DataSource dataSource) {
            return null;
        }

        @Override
        public PendingResult<Status> unsubscribe(GoogleApiClient googleApiClient, DataType dataType) {
            return null;
        }

        @Override
        public PendingResult<Status> unsubscribe(GoogleApiClient googleApiClient, DataSource dataSource) {
            return null;
        }

        @Override
        public PendingResult<Status> unsubscribe(GoogleApiClient googleApiClient, Subscription subscription) {
            return null;
        }

        @Override
        public PendingResult<ListSubscriptionsResult> listSubscriptions(GoogleApiClient googleApiClient) {
            return null;
        }

        @Override
        public PendingResult<ListSubscriptionsResult> listSubscriptions(GoogleApiClient googleApiClient, DataType dataType) {
            return null;
        }
    };

    private static final String TAG = "RNGoogleFit";

    public GoogleFitManager(ReactContext reactContext, Activity activity) {
        super();

        //Log.i(TAG, "Initializing GoogleFitManager" + mAuthInProgress);
        this.mReactContext = reactContext;
        this.mActivity = activity;

        mReactContext.addActivityEventListener(this);


        this.mStepCounter = new StepCounter(mReactContext, this, activity);
        this.stepHistory = new StepHistory(mReactContext, this);
        this.weightsHistory = new WeightsHistory(mReactContext, this);
        this.distanceHistory = new DistanceHistory(mReactContext, this);
        this.calorieHistory = new CalorieHistory(mReactContext, this);
        this.stepSensor = new StepSensor(mReactContext, activity);
    }

    public GoogleApiClient getGoogleApiClient() {
        return mApiClient;
    }

    public RecordingApi getRecordingApi() {
        return recordingApi;
    }

    public StepCounter getStepCounter() {
        return mStepCounter;
    }

    public StepHistory getStepHistory() {
        return stepHistory;
    }

    public WeightsHistory getWeightsHistory() {
        return weightsHistory;
    }

    public DistanceHistory getDistanceHistory() {
        return distanceHistory;
    }

    public void resetAuthInProgress()
    {
        if (!isAuthorized()) {
            mAuthInProgress = false;
        }
    }

    public CalorieHistory getCalorieHistory() { return calorieHistory; }

    public void authorize() {
        final ReactContext mReactContext = this.mReactContext;

        mApiClient = new GoogleApiClient.Builder(mReactContext.getApplicationContext())
                .addApi(Fitness.SENSORS_API)
                .addApi(Fitness.HISTORY_API)
                .addApi(Fitness.RECORDING_API)
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ))
                .addScope(new Scope(Scopes.FITNESS_BODY_READ_WRITE))
                .addScope(new Scope(Scopes.FITNESS_LOCATION_READ))
                .addConnectionCallbacks(
                        new GoogleApiClient.ConnectionCallbacks() {
                            @Override
                            public void onConnected(@Nullable Bundle bundle) {
                                Log.i(TAG, "Authorization - Connected");
                                sendEvent(mReactContext, "GoogleFitAuthorizeSuccess", null);
                            }

                            @Override
                            public void onConnectionSuspended(int i) {
                                Log.i(TAG, "Authorization - Connection Suspended");
                                if ((mApiClient != null) && (mApiClient.isConnected())) {
                                    mApiClient.disconnect();
                                }
                            }
                        }
                )
                .addOnConnectionFailedListener(
                        new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                                WritableMap map = Arguments.createMap();
                                map.putString("message", "" + connectionResult);
                                sendEvent(mReactContext, "GoogleFitAuthorizeFailure", map);

                                Log.i(TAG, "Authorization - Failed Authorization Mgr:" + connectionResult);
                                if (mAuthInProgress) {
                                    Log.i(TAG, "Authorization - Already attempting to resolve an error.");
                                } else if (connectionResult.hasResolution()) {
                                    try {
                                        mAuthInProgress = true;
                                        connectionResult.startResolutionForResult(mActivity, REQUEST_OAUTH);
                                    } catch (IntentSender.SendIntentException e) {
                                        Log.i(TAG, "Authorization - Failed again: " + e);
                                        mApiClient.connect();
                                    }
                                } else {
                                    Log.i(TAG, "Show dialog using GoogleApiAvailability.getErrorDialog()");
                                    showErrorDialog(connectionResult.getErrorCode());
                                    mAuthInProgress = true;
                                }
                            }
                        }
                )
                .build();

        mApiClient.connect();
    }

    public boolean isAuthorized() {
        if (mApiClient != null && mApiClient.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    protected void stop() {
        Fitness.SensorsApi.remove(mApiClient, (OnDataPointListener) mStepCounter)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            mApiClient.disconnect();
                        }
                    }
                });
    }


    public void sendEvent(ReactContext reactContext,
                          String eventName,
                          @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }



    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_OAUTH) {
            mAuthInProgress = false;
            if (resultCode == Activity.RESULT_OK) {
                // Make sure the app is not already connected or attempting to connect
                if (!mApiClient.isConnecting() && !mApiClient.isConnected()) {
                    mApiClient.connect();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.e(TAG, "Authorization - Cancel");
            }
        }
    }


    public void onNewIntent(Intent intent) {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public static class GoogleFitCustomErrorDialig extends ErrorDialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Get the error code and retrieve the appropriate dialog
            int errorCode = this.getArguments().getInt(AUTH_PENDING);
            return GoogleApiAvailability.getInstance().getErrorDialog(
                    this.getActivity(), errorCode, REQUEST_OAUTH);
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            mAuthInProgress = false;
        }
    }

    /* Creates a dialog for an error message */
    private void showErrorDialog(int errorCode) {
        // Create a fragment for the error dialog
        GoogleFitCustomErrorDialig dialogFragment = new GoogleFitCustomErrorDialig();
        // Pass the error that should be displayed
        Bundle args = new Bundle();
        args.putInt(AUTH_PENDING, errorCode);
        dialogFragment.setArguments(args);
        dialogFragment.show(mActivity.getFragmentManager(), "errordialog");
    }
}

