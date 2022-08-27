package com.example.android.bfit;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

import java.util.Date;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.google.android.gms.maps.GoogleMap.*;

import java.util.Calendar;


public class LocationFragment extends Fragment implements OnMapReadyCallback {
    ImageButton datetime;
    ImageButton backmap;
    ImageButton datelocation;
    CardView horizontal_color;
    Calendar myCalendar;
    TextView textdate;
    DatePickerDialog.OnDateSetListener dateSetListener;
    DatePicker datePicker;
    int d,m,y;
    Button location_site;
    AlertDialog alertDialog1;
    CharSequence[] values = {" House "," Work "," School ","Other"};




    private GoogleMap mMap;
    private int _day;
    private int _month;
    private Context _context;




    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        horizontal_color.setBackgroundResource(R.drawable.horizontalcolor_location); // for color the card view


        View rootview=inflater.inflate(R.layout.activity_location,null);
        SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        backmap =  (ImageButton) rootview.findViewById(R.id.Backmap);

        location_site=rootview.findViewById(R.id.buttonlocation);





      /*  View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.popup_dialog_date,null);
        datePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker);*/





        datetime =  (ImageButton) rootview.findViewById(R.id.datetime);
        datetime.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Calendar calendar = Calendar.getInstance();
                                            d = calendar.get(Calendar.DAY_OF_MONTH);
                                            m = calendar.get(Calendar.MONTH);
                                            y = calendar.get(Calendar.YEAR);



                                        }

                                        private DatePickerDialog.OnDateSetListener dateSetListener =
                                                new DatePickerDialog.OnDateSetListener() {
                                                    public void onDateSet(DatePicker view, int year, int month, int day) {
                                                        Toast.makeText(getActivity(), "selected date is " + view.getYear() +
                                                                " / " + (view.getMonth() + 1) +
                                                                " / " + view.getDayOfMonth(), Toast.LENGTH_SHORT).show();
                                                    }
                                                };
                                    });

        // create dialog for radio buttons
        datelocation = (ImageButton) getView().findViewById(R.id.editlocation);

        datelocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreateAlertDialogWithRadioButtonGroup() ;

            }

            private void CreateAlertDialogWithRadioButtonGroup() {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Select Your Choice");

                builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {

                        switch(item)
                        {
                            case 0:

                                Toast.makeText(getActivity(),"First Item Clicked",Toast.LENGTH_SHORT).show();

                                break;
                            case 1:

                                Toast.makeText(getActivity(),"Second Item Clicked",Toast.LENGTH_SHORT).show();

                                break;
                            case 2:

                                Toast.makeText(getActivity(),"Third Item Clicked",Toast.LENGTH_SHORT).show();

                                break;

                            case 3:
                                Toast.makeText(getActivity(),"Fourth Item Clicked",Toast.LENGTH_SHORT).show();

                                break;
                        }
                        alertDialog1.dismiss();
                    }
                });
                alertDialog1 = builder.create();
                alertDialog1.show();
            }
        });



            return rootview;
    }






    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Egypt and move the camera
        LatLng Egypt = new LatLng(26.854498, 29.697416);
        mMap.addMarker(new MarkerOptions().position(new LatLng(26.854498, 29.697416)).title("My First Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Egypt));

        // Sets the map type to be "hybrid", normal, satellite and terrain
        mMap.setMapType(MAP_TYPE_HYBRID);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }
}
