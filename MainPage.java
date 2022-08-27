package com.example.android.bfit;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.app.Dialog;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.DatePicker;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Fragment;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator.AnimatorUpdateListener;


import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.ServerManagedPolicy;
import com.google.android.vending.licensing.AESObfuscator;


import java.util.Calendar;
import java.util.Random;

import static com.example.android.bfit.InsightFragment.instantiate;


public class MainPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnTouchListener {
    AnimationDrawable animation, animationR, animationW; // for run girl image
    AnimationDrawable animation1, animationR1, animationW1; // for bicycle girl image
    AnimationDrawable animation2, animationR2, animationW2; // for idel wait girl image
    AnimationDrawable animation3, animationR3, animationW3; // for idel wave girl image
    AnimationDrawable animation4, animationR4, animationW4; // for sleep girl image
    AnimationDrawable animation5, animationR5, animationW5; // for car drive girl image
    AnimationDrawable animation6, animationR6, animationW6; // for walk girl image

    AnimationDrawable animations, animationm, animation1W; // for run male image
    AnimationDrawable animations1, animationm1, animation2W; // for bicycle male image
    AnimationDrawable animations2, animationm2, animation3W; // for idel wait male image
    AnimationDrawable animations3, animationm3, animation4W; // for idel wave male image
    AnimationDrawable animations4, animationm4, animation5W; // for sleep male image
    AnimationDrawable animations5, animationm5, animation6W; // for car drive male image
    AnimationDrawable animations6, animationm6, animation7W; // for walk male image
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    private LicenseCheckerCallback mLicenseCheckerCallback;
    private LicenseChecker mChecker;
    private boolean keepGoing = true;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    android.support.v4.app.FragmentTransaction fragmentTransaction;

    // licence for the app
    private static final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnoxWkzaXGL66qj6/ChNaz1EuIIeoN3FgRlkd/zvZWxl/kM3ygxI4NALYo4ruLI2SSnLpbPK+SKIkgo5ulUcGp6KEdS48Qy0wr82mcUyxcG8kLTWq57KNPqDCQVYRlV8cPcJE3ztRyslloLGGYua3Femh8aaCha/ZDOcLw6sLbyVXYWkIsEJoLBfWLJuTlOVBrQe5KWsIqggKomVIu5HJddiDHIKGG9GW02n69ZltfDPlP/3FkmhQfd9Y4K6ZzQn1rM2sf1pCICiELQu1c03oJ+aeDWggq0iyOSMikorSi8U2mt0nz0D29MDcbnPMkHSc4qxPyTA1CvblPcfqBRTdQQIDAQAB";
    private static final byte[] SALT = new byte[]{ //Twenty numbers (ranging from
            // -128 to +127) go here
    };


    ImageButton location;
    ImageButton time;
    Button calories;
    Button weight;
    Button track;
    Button steps;
    Button heart;
    Button walk;
    Button run;
    Button sleep;
    Button bicycle;
    Button car;
    Button music;
    Button games;
    Button movie;
    Button entertainment;
    Button library;
    Button chat;
    Button camera;
    Button broswer;
    TextView clock;
    CardView summary_insight;
    Context Context;
    AlertDialog create_bookmark;
    EditText bookmark_edit1;


    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    TextView timer; // for time in the images
    private float xCoOrdinate, yCoOrdinate;
    private RelativeLayout mRelLay;
    private int mCurrentSelectedPosition;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        setNavigationViewListener();
        // liscense the app
        @SuppressLint("HardwareIds") String deviceId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        mLicenseCheckerCallback = new MyLicenseCheckerCallback();
        mChecker = new LicenseChecker(this, new ServerManagedPolicy(this,
                new AESObfuscator(SALT, getPackageName(), deviceId)),
                BASE64_PUBLIC_KEY);
        doCheck();
        // for broswer in help
        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();

        // for insight summary card and to intent your fragment in your activity
        summary_insight = findViewById(R.id.summary_card_insight);
        summary_insight.setCardBackgroundColor(000000);
        summary_insight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment InsightFragment = new Fragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main_content, InsightFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        mRelLay = (RelativeLayout) findViewById(R.id.relativeLayout);
        clock = findViewById(R.id.timer); // for the timer in animation
        // change the color of statue bar
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.skyColor));
        }

        // to get the male and female from the SharedPreference
        SharedPreferences mypref = getSharedPreferences(mypreference, MODE_PRIVATE);
        mypref.getBoolean("male", true); // getting boolean for male
        mypref.getBoolean("female", true); // getting boolean for female

        for (int i = 0; i < mRelLay.getChildCount(); i++)
            mRelLay.getChildAt(i).setOnTouchListener(this);


        // for the imageview(run girl)
        final ImageView girlrun = findViewById(R.id.girlrun);
        animationR = (AnimationDrawable) getResources().getDrawable(R.drawable.femalerun);
        animation = (AnimationDrawable) getResources().getDrawable(R.drawable.background_for_the_animation);
        // set the background color
        int colorFrom = getResources().getColor(R.color.White);
        int colorTo = getResources().getColor(R.color.skyColor);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(250); // milliseconds
        colorAnimation.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                girlrun.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();

        ContextCompat.getColor(this, R.color.Black);
        girlrun.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xCoOrdinate = view.getX() - event.getRawX();
                        yCoOrdinate = view.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        view.animate().x(event.getRawX() + xCoOrdinate).y(event.getRawY() + yCoOrdinate).setDuration(30).start();
                        break;
                    default:
                        return false;

                }
                return true;
            }
        });


        // for the bicyle girl image view
        final ImageView bicyclegirl = findViewById(R.id.bicylegirl);
        animationR1 = (AnimationDrawable) getResources().getDrawable(R.drawable.bicyclegirl);
        animation1 = (AnimationDrawable) getResources().getDrawable(R.drawable.background_for_the_animation);

        // set the background color
        int colorFrom1 = getResources().getColor(R.color.Black);
        ValueAnimator colorAnimation1 = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom1);
        colorAnimation.setDuration(250); // milliseconds
        colorAnimation.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                bicyclegirl.setBackgroundColor((int) animator.getAnimatedValue());

                AnimationDrawable animationDrawable = (AnimationDrawable) bicyclegirl.getBackground();

                animationDrawable.setEnterFadeDuration(2500);
                animationDrawable.setExitFadeDuration(5000);

                animationDrawable.start();
            }

        });
        colorAnimation.start();

        bicyclegirl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xCoOrdinate = view.getX() - event.getRawX();
                        yCoOrdinate = view.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        view.animate().x(event.getRawX() + xCoOrdinate).y(event.getRawY() + yCoOrdinate).setDuration(30).start();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        // for wait idel girl
        final ImageView femaleidel = findViewById(R.id.idelgirl);
        animationR2 = (AnimationDrawable) getResources().getDrawable(R.drawable.female_idel_wait);
        animation2 = (AnimationDrawable) getResources().getDrawable(R.drawable.background_for_the_animation);

        // set the background color
        int colorFrom2 = getResources().getColor(R.color.Black);
        ValueAnimator colorAnimation2 = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom2);
        colorAnimation.setDuration(250); // milliseconds
        colorAnimation.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                bicyclegirl.setBackgroundColor((int) animator.getAnimatedValue());

                AnimationDrawable animationDrawable = (AnimationDrawable) femaleidel.getBackground();

                animationDrawable.setEnterFadeDuration(2500);
                animationDrawable.setExitFadeDuration(5000);

                animationDrawable.start();
            }

        });
        colorAnimation.start();
        // for wait idel girl to move by touch
        femaleidel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xCoOrdinate = view.getX() - event.getRawX();
                        yCoOrdinate = view.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        view.animate().x(event.getRawX() + xCoOrdinate).y(event.getRawY() + yCoOrdinate).setDuration(30).start();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        // for wave girl
        final ImageView female_wave = findViewById(R.id.wave);
        animationR3 = (AnimationDrawable) getResources().getDrawable(R.drawable.female_wave);
        animation3 = (AnimationDrawable) getResources().getDrawable(R.drawable.background_for_the_animation);

        // set the background color
        int colorFrom3 = getResources().getColor(R.color.Black);
        ValueAnimator colorAnimation3 = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom3);
        colorAnimation.setDuration(250); // milliseconds
        colorAnimation.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                bicyclegirl.setBackgroundColor((int) animator.getAnimatedValue());

                AnimationDrawable animationDrawable = (AnimationDrawable) female_wave.getBackground();

                animationDrawable.setEnterFadeDuration(2500);
                animationDrawable.setExitFadeDuration(5000);

                animationDrawable.start();
            }

        });
        colorAnimation.start();

        // for click on move in wave
        female_wave.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xCoOrdinate = view.getX() - event.getRawX();
                        yCoOrdinate = view.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        view.animate().x(event.getRawX() + xCoOrdinate).y(event.getRawY() + yCoOrdinate).setDuration(30).start();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
        // for sleep girl
        final ImageView female_sleep = findViewById(R.id.sleeping);
        animationR4 = (AnimationDrawable) getResources().getDrawable(R.drawable.female_sleep);
        animation4 = (AnimationDrawable) getResources().getDrawable(R.drawable.background_for_the_animation);

        // set the background color
        int colorFrom4 = getResources().getColor(R.color.Black);
        ValueAnimator colorAnimation4 = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom4);
        colorAnimation.setDuration(250); // milliseconds
        colorAnimation.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                bicyclegirl.setBackgroundColor((int) animator.getAnimatedValue());

                AnimationDrawable animationDrawable = (AnimationDrawable) female_sleep.getBackground();

                animationDrawable.setEnterFadeDuration(2500);
                animationDrawable.setExitFadeDuration(5000);

                animationDrawable.start();
            }

        });
        colorAnimation.start();

        // move on click on female sleep
        female_sleep.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xCoOrdinate = view.getX() - event.getRawX();
                        yCoOrdinate = view.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        view.animate().x(event.getRawX() + xCoOrdinate).y(event.getRawY() + yCoOrdinate).setDuration(30).start();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        // for car drive girl
        final ImageView female_transport = findViewById(R.id.transport);
        animationR5 = (AnimationDrawable) getResources().getDrawable(R.drawable.female_transport);
        animation5 = (AnimationDrawable) getResources().getDrawable(R.drawable.background_for_the_animation);
        // set the background color
        int colorFrom5 = getResources().getColor(R.color.Black);
        ValueAnimator colorAnimation5 = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom5);
        colorAnimation.setDuration(250); // milliseconds
        colorAnimation.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                bicyclegirl.setBackgroundColor((int) animator.getAnimatedValue());

                AnimationDrawable animationDrawable = (AnimationDrawable) female_transport.getBackground();

                animationDrawable.setEnterFadeDuration(2500);
                animationDrawable.setExitFadeDuration(5000);

                animationDrawable.start();
            }

        });
        colorAnimation.start();

       // click on move in drive girl
        female_transport.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xCoOrdinate = view.getX() - event.getRawX();
                        yCoOrdinate = view.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        view.animate().x(event.getRawX() + xCoOrdinate).y(event.getRawY() + yCoOrdinate).setDuration(30).start();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        // for walk girl
        final ImageView female_walk = findViewById(R.id.girlwalk);
        animationR6 = (AnimationDrawable) getResources().getDrawable(R.drawable.female_walk);
        animation6 = (AnimationDrawable) getResources().getDrawable(R.drawable.background_for_the_animation);
        // set the background color
        int colorFrom6 = getResources().getColor(R.color.Black);
        ValueAnimator colorAnimation6 = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom6);
        colorAnimation.setDuration(250); // milliseconds
        colorAnimation.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                bicyclegirl.setBackgroundColor((int) animator.getAnimatedValue());

                AnimationDrawable animationDrawable = (AnimationDrawable) female_walk.getBackground();

                animationDrawable.setEnterFadeDuration(2500);
                animationDrawable.setExitFadeDuration(5000);

                animationDrawable.start();
            }

        });
        colorAnimation.start();


        // move on touch in walk girl
        female_walk.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xCoOrdinate = view.getX() - event.getRawX();
                        yCoOrdinate = view.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        view.animate().x(event.getRawX() + xCoOrdinate).y(event.getRawY() + yCoOrdinate).setDuration(30).start();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
        // set the images for boy (run)
        final ImageView boyrun = findViewById(R.id.boyrun);
        animationm = (AnimationDrawable) getResources().getDrawable(R.drawable.male_run);

        // set the image for bicycle boy
        final ImageView bicycleboy = findViewById(R.id.bicyleboy);
        animationm1 = (AnimationDrawable) getResources().getDrawable(R.drawable.male_bicycle);

        // set the image for idel wait boy
        final ImageView idelwaitboy = findViewById(R.id.idelboy);
        animationm2 = (AnimationDrawable) getResources().getDrawable(R.drawable.male_idle_wait);

        // set the image for wave boy
        final ImageView waveboy = findViewById(R.id.waveboy);
        animationm3 = (AnimationDrawable) getResources().getDrawable(R.drawable.male_idle_wave);

        // set the image for sleep boy
        final ImageView sleepboy = findViewById(R.id.sleepingboy);
        animationm4 = (AnimationDrawable) getResources().getDrawable(R.drawable.male_sleep);

        // set the image for drive boy
        final ImageView driveboy = findViewById(R.id.transportboy);
        animationm5 = (AnimationDrawable) getResources().getDrawable(R.drawable.male_transport);

        // set the image for drive boy
        final ImageView walkboy = findViewById(R.id.boywalk);
        animationm6 = (AnimationDrawable) getResources().getDrawable(R.drawable.male_walk);

        // image buttons
        ImageButton location = (ImageButton) findViewById(R.id.Location);
        location.setImageResource(R.drawable.ic_location_on_black_24dp);

        ImageButton time = (ImageButton) findViewById(R.id.time);
        time.setImageResource(R.drawable.ic_today_black_24dp);

        // Calender button
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);
        calories = (Button) findViewById(R.id.calories);
        weight = (Button) findViewById(R.id.weight);
        track = (Button) findViewById(R.id.Track);
        steps = (Button) findViewById(R.id.Steps);
        heart = (Button) findViewById(R.id.heart);
        walk = (Button) findViewById(R.id.walk);
        run = (Button) findViewById(R.id.run);
        sleep = (Button) findViewById(R.id.sleep);
        bicycle = (Button) findViewById(R.id.bicyle);
        car = (Button) findViewById(R.id.car);
        music = (Button) findViewById(R.id.music);
        games = (Button) findViewById(R.id.games);
        movie = findViewById(R.id.movie);
        entertainment = findViewById(R.id.entertainment);
        chat = findViewById(R.id.chat);
        camera = findViewById(R.id.camera);
        library = findViewById(R.id.library);
        broswer = findViewById(R.id.browsing);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.setTitle("Animated Toolbar");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // default fragment for Insight
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.rootLayout, new InsightFragment());
        ft.commit();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!keepGoing) {
            finish();
        }
    }
    private void doCheck() {
        mChecker.checkAccess(mLicenseCheckerCallback);
    }
    private class MyLicenseCheckerCallback implements LicenseCheckerCallback {
        public void allow(int policyReason) {
            if (isFinishing()) {
                // Don't update UI if Activity is finishing.
                return;
            }
        }
        public void dontAllow(int policyReason) {
            if (isFinishing()) {
                return;
            }
            keepGoing = false;
            Intent intent =
                    new Intent(MainPage.this, NotLicensedActivity.class);
            intent.putExtra("message", getResources().getString(R.string.app_not_licensed) +
                    " (0x" + Integer.toHexString(policyReason) +")");
            startActivity(intent);
        }
        public void applicationError(int errorCode) {
            if (isFinishing()) {
                return;
            }
            keepGoing = false;
            Intent intent = new Intent(MainPage.this,
                    NotLicensedActivity.class);
            intent.putExtra("message",getResources().getString(R.string.application_error) +
                    " (0x" +Integer.toHexString(errorCode) +")");
            startActivity(intent);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mChecker.onDestroy();  //Don't forget this line. Without it, your app might crash.
    }


    private void setNavigationViewListener() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_view);
    }


    // declare the  girl and boy images
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // girl images
        animationR.start();
        animationR1.start();
        animationR2.start();
        animationR3.start();
        animationR4.start();
        animationR5.start();
        animationR6.start();
        // boy images
    }

    @SuppressWarnings("deprecation") //set up the time in button
    public void time(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "time", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected Dialog onCreateDialog(int id) { // show the date

        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() { // define the date
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year = 2009;
            month = 2;
            dayOfMonth = 21;
            showDate(2009, 2, 21);
        }
    };

    private void showDate(int year, int month, int day) {
        //time.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));

    }

    public void location(View v) { // for the location button
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.rootLayout, new LocationFragment());
        ft.commit();
        Toast.makeText(getApplicationContext(), "ic_location_on_black_24dp", Toast.LENGTH_LONG).show();

    }

    public void Mychat(View v) {// chat button to go to next page

        Intent chat = new Intent(this, chat.class);
        startActivity(chat);
    }

    public void Mymovie(View v) {// movie button to go to next page

        Intent movie = new Intent(this, movie.class);
        startActivity(movie);
    }

    public void Mysite(View v) {// site button to go to next page

        Intent broswer = new Intent(this, browsing.class);
        startActivity(broswer);
    }

    public void Mycamera(View v) {// camera button to go to next page

        Intent camera = new Intent(this, camera.class);
        startActivity(camera);
    }

    public void Myentertainment(View v) {// entertainment button to go to next page

        Intent entertainment = new Intent(this, entertainment.class);
        startActivity(entertainment);
    }

    public void Mylibrary(View v) {// library button to go to next page

        Intent library = new Intent(this, library.class);
        startActivity(library);
    }


    public void Mycalories(View v) { // the calories button to go the next page
        // to get data from preference in calories bottom
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        calories.getText().toString();
        // to save data in SharedPreference
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("calories", calories.getText().toString());
        editor.commit();

        Intent calories = new Intent(this, calories.class);
        startActivity(calories);
    }

    public void Myweight(View v) { // the weight button to go the next page

        // to get data from preference in weight bottom
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        weight.getText().toString();
        // to save data in SharedPreference
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("weight", weight.getText().toString());
        editor.commit();

        Intent weight = new Intent(this, weight.class);
        startActivity(weight);
    }

    public void Mytrack(View v) { // the track button to go the next page

        // to get data from preference in track bottom
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        track.getText().toString();
        // to save data in SharedPreference
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("track", track.getText().toString());
        editor.commit();

        Intent track = new Intent(this, track.class);
        startActivity(track);
    }

    public void Mysteps(View v) { // the Steps button to go the next page

        // to get data from preference in steps bottom
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        steps.getText().toString();
        // to save data in SharedPreference
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("steps", steps.getText().toString());
        editor.apply();

        Intent steps = new Intent(this, Steps.class);
        startActivity(steps);
    }

    public void Mywalk(View v) { // the walk button to go the next page

        // to get data from preference in Walk bottom
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        walk.getText().toString();
        // to save data in SharedPreference
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("calories", walk.getText().toString());
        editor.commit();

        Intent walk = new Intent(this, Walk.class);
        startActivity(walk);
    }

    public void Myheart(View v) { // the heart button to go the next page

        // to get data from preference in Heart bottom
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        heart.getText().toString();
        // to save data in SharedPreference
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("Heart", heart.getText().toString());
        editor.commit();

        Intent heart = new Intent(this, Heart.class);
        startActivity(heart);
    }

    public void Myrun(View v) { // the run button to go the next page

        // to get data from preference in run bottom
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        run.getText().toString();
        // to save data in SharedPreference
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("Run", run.getText().toString());
        editor.commit();

        Intent run = new Intent(this, Run.class);
        startActivity(run);
    }

    public void Mysleep(View v) { // the sleep button to go the next page

        // to get data from preference in Sleep bottom
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        sleep.getText().toString();
        // to save data in SharedPreference
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("Sleep", sleep.getText().toString());
        editor.commit();

        Intent sleep = new Intent(this, Sleep.class);
        startActivity(sleep);
    }

    public void Mybicyle(View v) { // the bicycle button to go the next page

        // to get data from preference in bicycle bottom
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        bicycle.getText().toString();
        // to save data in SharedPreference
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("bicycle", bicycle.getText().toString());
        editor.commit();
        Intent bicycle = new Intent(this, Bicycle.class);
        startActivity(bicycle);
    }

    public void Mycar(View v) { // the Car button to go the next page

        // to get data from preference in car bottom
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        car.getText().toString();
        // to save data in SharedPreference
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("car", car.getText().toString());
        editor.commit();

        Intent car = new Intent(this, Car.class);
        startActivity(car);
    }

    public void Mymusic(View v) { // the Music button to go the next page

        // to get data from preference in Music bottom
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        music.getText().toString();
        // to save data in SharedPreference
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("Music", music.getText().toString());
        editor.commit();

        Intent music = new Intent(this, Music.class);
        startActivity(music);
    }

    public void Mygames(View v) { // the calories button to go the next page

        // to get data from preference in Games bottom
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        games.getText().toString();
        // to save data in SharedPreference
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("Games", games.getText().toString());
        editor.commit();
        editor.apply();

        Intent games = new Intent(this, Games.class);
        startActivity(games);
    }

    public void setActionBarTitle(String Title) {
        getSupportActionBar().setTitle(Title);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    // for about and bookmark page
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.refresh_mainpage, menu);

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
        if (id == R.id.refresh) {
            return true;
        }
        return id == R.id.bookmark || super.onOptionsItemSelected(item);

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem single) {
        // Handle navigation view item clicks here.
        int id = single.getItemId();

        if (id == R.id.Insight) {
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rootLayout, new InsightFragment());
            ft.commit();
        } else if (id == R.id.Life) {
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rootLayout, new LifeBookmarkFragment());
            ft.commit();

        } else if (id == R.id.Location) {
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rootLayout, new LocationFragment());
            ft.commit();

        } else if (id == R.id.Profile) {
            Intent profile = new Intent(this, ProfileViewActivity.class);
            startActivity(profile);

        } else if (id == R.id.Dashboard) {

            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rootLayout, new ManageDashboardFragment());
            ft.commit();

        } else if (id == R.id.Setting) {
            Intent setting = new Intent(this, SettingActivity.class);
            startActivity(setting);

        } else if (id == R.id.help) {

            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rootLayout, new help_fragment());
            ft.commit();
            // intent for the browser in help fragment
            Intent browserIntent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://ids.indevice.sonymobile.com/in-device/getSoftwareSupport.htm?sourceAppName=com.sonymobile.lifelog&sourceAppVersion=8388628&sourceAppView=Main&androidVersion=7.0&manufacturer=samsung&model=SM-N920C&locale=en_US&output=html"));
            startActivity(browserIntent);


//            mCurrentSelectedPosition = 99;
//            MaterialDialog dialog = new MaterialDialog.Builder(Context)
//                    .title(R.string.Web)
//                    .customView(R.layout.dialog_changelog, false)
//                    .positiveText(getResources().getString(R.string.close))
//                    .theme(getDialogTheme())
//                    .build();
//            WebView webView = (WebView) dialog.getCustomView().findViewById(R.id.help);
//            webView.getSettings();
//            int theme = ApplyTheme.getConfigTheme(Context);
//            if (theme == 0){
//                webView.loadUrl("file:///android_asset/changelog_light.html");
//            } else {
//                webView.loadUrl("file:///android_asset/changelog_dark.html");
//            }
//            webView.setWebViewClient(new WebViewClient() {
//                public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                    if (url != null) {
//                        view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//                        return true;
//                    } else {
//                        return false;
//                    }
//                }
        }





// for close the navigation bar
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    // for edit text bookmark
    public void bookmark(MenuItem item) {
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") View alertLayout= inflater.inflate(R.layout.popup_create_bookmark,null);
        final EditText bookmark_edit1= alertLayout.findViewById(R.id.bookmark_edit);



        final AlertDialog.Builder show = new AlertDialog.Builder(Context);
        show.setTitle("Enter your Bookmark:");
        show.setView(alertLayout);
        show.setCancelable(false);

        final EditText edittext = new EditText(Context);
        show.setMessage("Enter Your Note");
        show.setTitle("Enter Your Title");

        show.setView(edittext);
        show.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Editable bookmarkEdit1Text = bookmark_edit1.getText();


            }
        });
        show.setPositiveButton("SET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = show.create();
        dialog.show();

    }


    public void refresh(MenuItem item) {

        // for swipe
        final SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setColorSchemeResources(R.color.skyColor,R.color.Black,R.color.White);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);

                        int min=50;
                        int max=65;
                        Random random= new Random();
                        int i=random.nextInt(max-min+1)*min;
                    }
                },1000);
            }
        });

    }
}
