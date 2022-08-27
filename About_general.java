package com.example.android.bfit;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;


public class About_general extends AppCompatActivity {

    ImageButton Back_about;
    Toolbar about;
    CardView weather;
    CardView recognition;
    TextView medical_Disclaimer;
    TextView open_source;
    TextView privacy;
    TextView terms_of_use;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_general);
        setSupportActionBar((android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_about));

        terms_of_use=findViewById(R.id.terms_of_use);
        terms_of_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        privacy=findViewById(R.id.privacy);
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        open_source=findViewById(R.id.open_source);
        open_source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        medical_Disclaimer=findViewById(R.id.medical_Disclaimer);
        medical_Disclaimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        recognition=(CardView)findViewById(R.id.recognition);
        recognition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        weather=findViewById(R.id.weather);
        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        Back_about=findViewById(R.id.Back_about);
        Back_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(About_general.this,
                        MainPage.class);
                startActivity(myIntent);
            }
        });
    }
}
