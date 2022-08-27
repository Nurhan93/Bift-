package com.example.android.bfit;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog.OnDateSetListener;


import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Calendar;


public class ProfileViewActivity extends AppCompatActivity implements View.OnClickListener {
    @SuppressLint("StaticFieldLeak")
    private static ProfileViewActivity instance;
    Spinner spinner_height;
    Spinner spinner_weight;
    Spinner spinner_lenght;
    Context context;
    CardView gender;
    TextView txt_gender;
    private GoogleSignInClient mGoogleSignInClient;
    Button sign_out;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    ImageButton back14;
    Context Context;
    TextView tvEmail,tvGender,tvLength,tvHeight,tvWeight;
    AlertDialog alert;
    AlertDialog alertDialog_weight;
    AlertDialog alertDialog_lenght;
    AlertDialog alertDialog_gender;
    TextView explore;
    TextView delete_account;
    CharSequence[] values = {" Female "," Male "};
    //UI References
    private EditText fromDateEtxt;
    private EditText toDateEtxt;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;



    public static ProfileViewActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // for date format
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();

        setDateTimeField();
        // for explore the data into folder
        explore=findViewById(R.id.explore);
        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(v == fromDateEtxt) {
                    fromDatePickerDialog.show();
                } else if(v == toDateEtxt) {
                    toDatePickerDialog.show();
                }
                AlertDialog alertDialog = new AlertDialog.Builder(Context).create();
                alertDialog.setTitle("Export Data");

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Export", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Your file exported", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.show();

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

        // for create a file into create text

        try
        {
            // Creates a trace file in the primary external storage space of the
            // current application.
            // If the file does not exists, it is created.
            File traceFile = new File(((Context)this).getExternalFilesDir(null), "TraceFile.txt");
            if (!traceFile.exists())
                traceFile.createNewFile();
            // Adds a line to the trace file
            BufferedWriter writer = new BufferedWriter(new FileWriter(traceFile, true /*append*/));
            writer.write("This is a test trace file.");
            writer.close();
            // Refresh the data so it can seen when the device is plugged in a
            // computer. You may have to unplug and replug the device to see the
            // latest changes. This is not necessary if the user should not modify
            // the files.
            MediaScannerConnection.scanFile((Context)(this),
                    new String[] { traceFile.toString() },
                    null,
                    null);

        }
        catch (IOException e)
        {
            Log.e("com.cindypotvi.FileTest", "Unable to write to the TraceFile.txt file.");
        }

// read your file that have been saved un the file directory
        String textFromFile = "";
       // Gets the file from the primary external storage space of the
        // current application.
        File testFile = new File(this.getExternalFilesDir(null), "TestFile.txt");
        if (testFile != null) {
            StringBuilder stringBuilder = new StringBuilder();
            // Reads the data from the file
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(testFile));
                String line;

                while ((line = reader.readLine()) != null) {
                    textFromFile += line.toString();
                    textFromFile += "\n";
                }
                reader.close();
            } catch (Exception e) {
                Log.e("ReadWriteFile", "Unable to read the TestFile.txt file.");
            }
        }


        // for delete or clear all account data
        delete_account=findViewById(R.id.delete_data);
        delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // for delete dialog alret
                AlertDialog.Builder alert = new AlertDialog.Builder(Context);
                alert.setTitle("Delete Account");
                alert.setMessage("Are you sure you want to delete?");
                alert.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                alert.create();

                sharedpreferences.edit().clear().apply();

                ProfileViewActivity.getInstance().clearApplicationData();
            }
        });
        // spinner for height dialog
        spinner_height=findViewById(R.id.height_dialog);
        addItemsOnspinner_height();
        // items for lenght
        spinner_lenght=findViewById(R.id.Length_dialog);
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.length,
                        android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_lenght.setAdapter(staticAdapter);
        spinner_lenght.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
                });
// items for weight
        spinner_weight=findViewById(R.id.Weight_dialog);
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapterq = ArrayAdapter
                .createFromResource(this, R.array.calories,
                        android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_weight.setAdapter(staticAdapter);
        spinner_weight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        txt_gender=(TextView) findViewById(R.id.Text_gender);
        txt_gender.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         CreateAlertDialogWithRadioButtonGroup();
                                     }

                                     private void CreateAlertDialogWithRadioButtonGroup() {


                                         AlertDialog.Builder builder = new AlertDialog.Builder(context);

                                         builder.setTitle("Select Your Gender");

                                         builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

                                             public void onClick(DialogInterface dialog, int item) {

                                                 switch (item) {
                                                     case 0:

                                                         Toast.makeText(ProfileViewActivity.this, "Male", Toast.LENGTH_SHORT).show();

                                                         break;
                                                     case 1:

                                                         Toast.makeText(ProfileViewActivity.this, "Female", Toast.LENGTH_SHORT).show();
                                                 }
                                                 alertDialog_gender.dismiss();
                                             }
                                         });
                                         alertDialog_gender = builder.create();
                                         alertDialog_gender.show();
                                     }
                                 });


        // color for your statue bar
        if (android.os.Build.VERSION.SDK_INT>= 21){
            Window window=this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.skyColor));
        }

        setContentView(R.layout.activity_profile_view);
        // for sign out button
        sign_out=findViewById(R.id.sign_out_button);
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.sign_out_button:
                        signOut();
                        break;
                    // ...
                }
            }
        });

       tvGender=findViewById(R.id.tvGender);
       tvEmail=findViewById(R.id.tvEmail);
       tvLength=findViewById(R.id.tvLength);
       tvHeight=findViewById(R.id.tvHeight);
       tvWeight=findViewById(R.id.tvWeight);



        // to get the data from sharedpreference by key and add it to textviews
        SharedPreferences mypref=getSharedPreferences(mypreference,MODE_PRIVATE);
        tvWeight.setText( mypref.getString("weight", null) );  // getting String for weight
         tvHeight.setText(String.valueOf(mypref.getFloat("height", Float.parseFloat(null))));   // getting Float for height
        tvLength.setText(String.valueOf(mypref.getFloat("length", Float.parseFloat(null)))); // getting Float for length
        tvGender.setText(String.valueOf( mypref.getBoolean("male,female", true)));// getting boolean for gender

    }
// set the calendar
    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();

        fromDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }





// declare the 2 edit text from and to date
    private void findViewsById() {
        fromDateEtxt = (EditText) findViewById(R.id.etxt_fromdate);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();

        toDateEtxt = (EditText) findViewById(R.id.etxt_todate);
        toDateEtxt.setInputType(InputType.TYPE_NULL);
    }

    // method for delete account data
    private void clearApplicationData() {

        File cacheDirectory = getCacheDir();
        File applicationDirectory = new File(cacheDirectory.getParent());
        if (applicationDirectory.exists()) {
            String[] fileNames = applicationDirectory.list();
            for (String fileName : fileNames) {
                if (!fileName.equals("lib")) {
                    deleteFile(new File(applicationDirectory, fileName));
                }
            }
        }
    }
// for files have been deleted
    public static boolean deleteFile(File file) {

        boolean deletedAll = true;
        if (file != null) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;
                }
            } else {
                deletedAll = file.delete();
            }
        }

        return deletedAll;
    }



    // for height item selected
    private void addItemsOnspinner_height() {

        List<String> list = new ArrayList<String>();
        list.add("Height");
        list.add("list 2");
        list.add("list 3");

        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.height,
                        android.R.layout.simple_spinner_item);

        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_height.setAdapter(staticAdapter);
        spinner_height.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //nothing
            }


        });

    }


// signout for google
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }
    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    @Override
    public void onClick(View v) {

    }
}
