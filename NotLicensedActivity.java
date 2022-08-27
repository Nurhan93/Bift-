package com.example.android.bfit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

public class NotLicensedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_licensed);

        String message = Objects.requireNonNull(getIntent().getExtras()).getString("message");
        ((TextView) findViewById(R.id.message_text)).setText(message);
        ((TextView) findViewById(R.id.message_text2)).setText(message);
    }
    public void quitApp(View view) {
        finish();

    }
}
