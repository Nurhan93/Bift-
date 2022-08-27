package com.example.android.bfit;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.text.TextUtils;
import android.content.SharedPreferences;

public class Profile extends AppCompatActivity {
    private EditText weight;
    private EditText height;
    private EditText lenght;
    private RadioGroup gendergroup;
    private RadioButton gender;
    Button finish;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(1);
        getWindow().setFlags(1024,1024);
        getSupportActionBar().setTitle("Profile");
        super.onCreate(savedInstanceState);

        // change the color of statue bar
        if (android.os.Build.VERSION.SDK_INT>= 21){
            Window window= this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Green));
        }
        setContentView(R.layout.activity_profile);
        finish = findViewById(R.id.Finish);


         weight = (EditText) findViewById(R.id.Wight);
        height = (EditText) findViewById(R.id.Height);
       lenght = (EditText) findViewById(R.id.Length);
        gendergroup = (RadioGroup) findViewById(R.id.gender);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                RadioButton rbGender=findViewById(gendergroup.getCheckedRadioButtonId());

// to save data in SharedPreference
                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString("weight",weight.getText().toString());
                editor.putString("height",height.getText().toString());
                editor.putString("lenght",lenght.getText().toString());
                editor.putString("gender",rbGender.getText().toString());
                editor.apply();

                startActivity(new Intent(Profile.this,ProfileViewActivity.class));
            }
        });

    }


// how this function calls?
   public void weight(View v) { // for weight editText

        String sttr = weight.getText().toString();

        if (TextUtils.isEmpty(sttr)) {
            weight.setError("Please enter your weight");
            weight.requestFocus();
            return;
        }
    }

    public void height(View v) { // for height editText

        String sttr1 = height.getText().toString();

        if (TextUtils.isEmpty(sttr1)) {
            height.setError("Please enter your height");
            height.requestFocus();
            return;
        }
    }

    public void length(View v) { // for length editText

        String sttr3 = lenght.getText().toString();
        if (TextUtils.isEmpty(sttr3)) {
            lenght.setError("Please enter your length");
            lenght.requestFocus();
            return;
        }
    }

    // Convert height from string to float
    float h = Float.valueOf(height.getText().toString());
    float w = Float.valueOf(weight.getText().toString());
    float l = Float.valueOf(lenght.getText().toString());


//    *//***
//     * Time for math!
//     * BMI is calculated
//     * (weigth in kg / (height in meter * height in meter)
//     * But since we want the user to input in CM, we just
//     * multiply it with 10 000 to get the correct value.
//     *//*
    float BMI = w / (h * h) * 10000;
    float Ml = w / h * 1000;

    // interpret what BMI means
    private String interpretIMC(double imcValue) {
        // Check which radio button was clicked
        if (gendergroup.getCheckedRadioButtonId()==R.id.Male) {
            if (imcValue < 16) {
                return "Normal";
            } else if (imcValue < 18.5) {

                return "Lightness";
            } else if (imcValue < 25) {

                return "Healthy";
            } else if (imcValue < 30) {

                return "OverWeight";

            } else if (imcValue < 35) {

                return "Obesity Grade I";
            } else if (imcValue < 40) {
                return "Obesity grade II";
            } else {
                return "Obesity grade III";
            }};


        if (gendergroup.getCheckedRadioButtonId()==R.id.Female) {
            if (imcValue < 16) {
                return "Normal";
            } else if (imcValue < 18.5) {

                return "Lightness";
            } else if (imcValue < 25) {

                return "Healthy";
            } else if (imcValue < 30) {

                return "OverWeight";

            } else if (imcValue < 35) {

                return "Obesity Grade I";
            } else if (imcValue < 40) {
                return "Obesity grade II";
            } else {

                    return "Obesity grade III";
                }}
        return null;
    }




    public void male (View view) { // for male button
        int male=gendergroup.getCheckedRadioButtonId();
        gender=(RadioButton) findViewById(R.id.Male);
        gender=(RadioButton) findViewById(R.id.Female);
        Toast.makeText(Profile.this, gender.getText(), Toast.LENGTH_SHORT).show();

    }
        }


