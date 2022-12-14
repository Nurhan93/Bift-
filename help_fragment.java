package com.example.android.bfit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Request;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link help_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link help_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class help_fragment extends Fragment {

    WebView help;

    OkHttpClient client = new OkHttpClient();
    TextView txtString;
    public String url= "https://ids.indevice.sonymobile.com/in-device/getSoftwareSupport.htm?sourceAppName=com.sonymobile.lifelog&sourceAppVersion=8388628&sourceAppView=Main&androidVersion=7.0&manufacturer=samsung&model=SM-N920C&locale=en_US&output=html";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public help_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment help_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static help_fragment newInstance(String param1, String param2) {
        help_fragment fragment = new help_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        @SuppressLint("StaticFieldLeak") OkHttpHandler okHttpHandler= new OkHttpHandler() {
            @Override
            protected Object doInBackground(Object[] objects) {
                return null;
            }
        };
        okHttpHandler.execute(url);

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("StaticFieldLeak")
    abstract class OkHttpHandler extends AsyncTask {

        OkHttpClient client = new OkHttpClient();

    }


    protected String doInBackground(String...params) {

        Request.Builder builder = new Request.Builder();
        builder.url(params[0]);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help_fragment, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
