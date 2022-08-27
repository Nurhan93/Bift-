package com.example.android.bfit;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.preference.PreferenceManager;
import android.app.AppOpsManager.*;



public class SettingActivity extends AppCompatActivity {


    UsageStatsManager mUsageStatsManager;

    public static final String
            KEY_PREF_EXAMPLE_SWITCH = "example_switch";
    static public final int REQUEST_LOCATION = 1;
    ImageButton back_setting;
    TextView location_permission;
    TextView app_usage;
    TextView usage_sleeptime;
    TextView distance_weight;
    AlertDialog distance_weight_dialog;
    TextView temperature;
    AlertDialog dialog_temperature;
    TextView notification;
    Switch switch_setting;
    Context context;
    Menu setting;




    CharSequence[] values = {" km/kg "," miles/pounds "};
    CharSequence[] values_temperature = {" Celsius "," Fahrenheit "};
    private int preferences;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        // for location permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
         // Check permission
          ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
          // <-- Start Beemray here
        }


        // for intent Setting to usage permission
        if (!isAccessGranted()) {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }


       // for setting in preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);
        Boolean switchPref = sharedPref.getBoolean
                (SettingActivity.KEY_PREF_EXAMPLE_SWITCH, false);
        Toast.makeText(this, switchPref.toString(), Toast.LENGTH_SHORT).show();

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);



        back_setting=(ImageButton)findViewById(R.id.Back_setting);
        location_permission=(TextView)findViewById(R.id.location_permission);
        location_permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        app_usage=(TextView)findViewById(R.id.app_usage);
        app_usage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


        usage_sleeptime=(TextView)findViewById(R.id.usage_sleep_time);
        usage_sleeptime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        distance_weight=(TextView)findViewById(R.id.distance_weight);
        distance_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAlertDialogWithRadioButtonGroup();
            }

            private void CreateAlertDialogWithRadioButtonGroup() {


                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Distance");

                builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {

                        switch (item) {
                            case 0:

                                Toast.makeText(SettingActivity.this, "First Item Clicked", Toast.LENGTH_SHORT).show();

                                break;
                            case 1:

                                Toast.makeText(SettingActivity.this, "Second Item Clicked", Toast.LENGTH_SHORT).show();
                        }
                        distance_weight_dialog.dismiss();
                    }
                });
                distance_weight_dialog = builder.create();
                distance_weight_dialog.show();
            }
        });

        temperature=(TextView)findViewById(R.id.temperature);
        temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAlertDialogWithRadioButtonGroup();
            }

            private void CreateAlertDialogWithRadioButtonGroup() {


                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Select Your Gender");

                builder.setSingleChoiceItems(values_temperature, -1, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {

                        switch (item) {
                            case 0:

                                Toast.makeText(SettingActivity.this, "First Item Clicked", Toast.LENGTH_SHORT).show();

                                break;
                            case 1:

                                Toast.makeText(SettingActivity.this, "Second Item Clicked", Toast.LENGTH_SHORT).show();
                        }
                        dialog_temperature.dismiss();
                    }
                });
                dialog_temperature = builder.create();
                dialog_temperature.show();
            }
        });


        notification=(TextView)findViewById(R.id.Notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = R.xml.preferences;

                //Intent intent= new Intent(this, preferences.class);
                //startActivity(intent);
            }
        });
        switch_setting=(Switch)findViewById(R.id.switch_setting);
        switch_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean switchState = switch_setting.isChecked();

                if(switch_setting.getVisibility()==View.GONE)
                {
                    usage_sleeptime.setVisibility(View.VISIBLE);
                }
                else
                {
                    usage_sleeptime.setVisibility(View.INVISIBLE);
                }

            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
      if (requestCode == REQUEST_LOCATION) {
      if(grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show(); // <-- Start Beemray here
    } else {
          Toast.makeText(getApplicationContext(), "Permission cancelled", Toast.LENGTH_SHORT).show();// Permission was denied or request was cancelled
    }
      }
    }

    private void addPreferencesFromResource(int preferences) {
    }

    // for access the usage app
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean isAccessGranted() {
            try {
                PackageManager packageManager = getPackageManager();
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
                AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
                int mode = 0;
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                    assert appOpsManager != null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                                applicationInfo.uid, applicationInfo.packageName);
                    }
                }
                return (mode == AppOpsManager.MODE_ALLOWED);

            } catch (PackageManager.NameNotFoundException e) {
                return false;
            }
        }


    public void onClick(View v) {
        Intent intent = new Intent(this,MainPage.class);
        startActivity(intent);
    }

    // for about page
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.about, menu);

        return true;
    }

    // items in menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return id == R.id.About;

    }

    public void About(MenuItem item) {
        Intent myintent= new Intent(this,About_general.class);
        startActivity(myintent);
    }
}
