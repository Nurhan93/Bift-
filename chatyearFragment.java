package com.example.android.bfit;

import android.annotation.TargetApi;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.content.pm.PackageManager.NameNotFoundException;

import org.jsoup.Jsoup;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link chatyearFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link chatyearFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class chatyearFragment extends Fragment  implements OnItemSelectedListener {

    // for categoriez the app storage
    public final static String GOOGLE_URL = "https://play.google.com/store/apps/category/SOCIAL"; // url for the categories i used in chat
    public static final String ERROR = "error";
    private ActivityUtil mActivityUtil;
    private static final String TAG = "UsageStatsActivity";
    private static final boolean localLOGV = false;
    private UsageStatsManager mUsageStatsManager;
    private LayoutInflater mInflater;
    private chatyearFragment.UsageStatsAdapter mAdapter;
    private PackageManager mPm;
    ImageButton sharechatyear;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mAdapter.sortList(position);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public static class AppNameComparator implements Comparator<UsageStats> {
        private Map<String, String> mAppLabelList;

        AppNameComparator(Map<String, String> appList) {
            mAppLabelList = appList;
        }

        @Override
        public final int compare(UsageStats a, UsageStats b) {
            String alabel = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                alabel = mAppLabelList.get(a.getPackageName());
                String blabel = mAppLabelList.get(b.getPackageName());
                return alabel.compareTo(blabel);
            }
            return 0;
        }

    }
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public chatyearFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment chatyearFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static chatyearFragment newInstance(String param1, String param2) {
        chatyearFragment fragment = new chatyearFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static class LastTimeUsedComparator implements Comparator<UsageStats> {
        @Override
        public final int compare(UsageStats a, UsageStats b) {
            // return by descending order
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                return (int) (b.getLastTimeUsed() - a.getLastTimeUsed());
            }
            return 0;
        }
    }

    public static class UsageTimeComparator implements Comparator<UsageStats> {
        @Override
        public final int compare(UsageStats a, UsageStats b) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                return (int) (b.getTotalTimeInForeground() - a.getTotalTimeInForeground());
            }
            return 0;
        }
    }

    // View Holder used when displaying views
    static class AppViewHolder {
        TextView pkgName;
        TextView lastTimeUsed;
        TextView usageTime;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    class UsageStatsAdapter extends BaseAdapter {
        // Constants defining order for display order
        private static final int _DISPLAY_ORDER_USAGE_TIME = 0;
        private static final int _DISPLAY_ORDER_LAST_TIME_USED = 1;
        private static final int _DISPLAY_ORDER_APP_NAME = 2;

        private int mDisplayOrder = _DISPLAY_ORDER_USAGE_TIME;
        private chatyearFragment.LastTimeUsedComparator mLastTimeUsedComparator = new LastTimeUsedComparator();
        private chatyearFragment.UsageTimeComparator mUsageTimeComparator = new UsageTimeComparator();
        private chatyearFragment.AppNameComparator mAppLabelComparator;
        private final ArrayMap<String, String> mAppLabelMap = new ArrayMap<>();
        private final ArrayList<UsageStats> mPackageStats = new ArrayList<>();

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        UsageStatsAdapter() {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, -5);

            List<UsageStats> stats = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST,
                        cal.getTimeInMillis(), System.currentTimeMillis());

                if (stats == null) {
                    return;
                }
            }


            ArrayMap<String, UsageStats> map = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                map = new ArrayMap<>();
            }
            final int statCount = stats.size();
            for (int i = 0; i < statCount; i++) {
                final UsageStats pkgStats = stats.get(i);

                // load application labels for each application
                try {
                    ApplicationInfo appInfo = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        appInfo = mPm.getApplicationInfo(pkgStats.getPackageName(), 0);
                    }
                    String label = appInfo.loadLabel(mPm).toString();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mAppLabelMap.put(pkgStats.getPackageName(), label);
                    }

                    UsageStats existingStats =
                            null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        existingStats = map.get(pkgStats.getPackageName());
                    }
                    if (existingStats == null) {
                        map.put(pkgStats.getPackageName(), pkgStats);
                    } else {
                        existingStats.add(pkgStats);
                    }

                } catch (NameNotFoundException e) {
                    // This package may be gone.
                }
            }
            mPackageStats.addAll(map.values());

            // Sort list
            mAppLabelComparator = new AppNameComparator(mAppLabelMap);
            sortList();
        }

        @Override
        public int getCount() {
            return mPackageStats.size();
        }

        @Override
        public Object getItem(int position) {
            return mPackageStats.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // A ViewHolder keeps references to children views to avoid unneccessary calls
            // to findViewById() on each row.
            AppViewHolder holder;

            // When convertView is not null, we can reuse it directly, there is no need
            // to reinflate it. We only inflate a new View when the convertView supplied
            // by ListView is null.
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.usage_state_item_broswer, null);

                // Creates a ViewHolder and store references to the two children views
                // we want to bind data to.
                holder = new AppViewHolder();
                holder.pkgName = (TextView) convertView.findViewById(R.id.package_name);
                holder.lastTimeUsed = (TextView) convertView.findViewById(R.id.last_time_used);
                holder.usageTime = (TextView) convertView.findViewById(R.id.usage_time);
                convertView.setTag(holder);
            } else {
                // Get the ViewHolder back to get fast access to the TextView
                // and the ImageView.
                holder = (chatyearFragment.AppViewHolder) convertView.getTag();
            }

            // Bind the data efficiently with the holder
            UsageStats pkgStats = mPackageStats.get(position);
            if (pkgStats != null) {
                String label = mAppLabelMap.get(pkgStats.getPackageName());
                holder.pkgName.setText(label);
                holder.lastTimeUsed.setText(DateUtils.formatSameDayTime(pkgStats.getLastTimeUsed(),
                        System.currentTimeMillis(), DateFormat.MEDIUM, DateFormat.MEDIUM));
                holder.usageTime.setText(
                        DateUtils.formatElapsedTime(pkgStats.getTotalTimeInForeground() / 1000));
            } else {
                Log.w(TAG, "No usage stats info for package:" + position);
            }
            return convertView;
        }
        void sortList(int sortOrder) {
            if (mDisplayOrder == sortOrder) {
                // do nothing
                return;
            }
            mDisplayOrder= sortOrder;
            sortList();
        }
        private void sortList() {
            if (mDisplayOrder == _DISPLAY_ORDER_USAGE_TIME) {
                if (localLOGV) Log.i(TAG, "Sorting by usage time");
                Collections.sort(mPackageStats, mUsageTimeComparator);
            } else if (mDisplayOrder == _DISPLAY_ORDER_LAST_TIME_USED) {
                if (localLOGV) Log.i(TAG, "Sorting by last time used");
                Collections.sort(mPackageStats, mLastTimeUsedComparator);
            } else if (mDisplayOrder == _DISPLAY_ORDER_APP_NAME) {
                if (localLOGV) Log.i(TAG, "Sorting by application name");
                Collections.sort(mPackageStats, mAppLabelComparator);
            }
            notifyDataSetChanged();
        }
    }
    // for categories the app by the google play link
    class FetchCategoryTask extends AsyncTask<Void, Void, Void> {

        private final String TAG = chatyearFragment.FetchCategoryTask.class.getSimpleName();


        @Override
        protected Void doInBackground(Void... errors) {
            String category;
            mPm = getActivity().getPackageManager();
            List<ApplicationInfo> packages = mPm.getInstalledApplications(PackageManager.GET_META_DATA);
            Iterator<ApplicationInfo> iterator = packages.iterator();
            while (iterator.hasNext()) {
                ApplicationInfo packageInfo = iterator.next();
                String query_url = GOOGLE_URL + packageInfo.packageName;
                Log.i(TAG, query_url);
                category = getCategory(query_url);
                // store category or do something else
            }
            return null;
        }

        private String getCategory (String query_url){
            boolean network = mActivityUtil.isNetworkAvailable();
            if (!network) {
                //manage connectivity lost
                return ERROR;
            } else {
                try {
                    org.jsoup.nodes.Document doc = Jsoup.connect(query_url).get();
                    org.jsoup.nodes.Element link = doc.select("span[itemprop=genre]").first();
                    return link.text();
                } catch (Exception e) {
                    return ERROR;
                }
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {

        sharechatyear=getView().findViewById(R.id.sharechatyear);
        sharechatyear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myintent= new Intent(Intent.ACTION_SEND);
                myintent.setType("Data");
                String Sharebody="Your body here";
                String sharesub="Your object here";
                myintent.putExtra(Intent.EXTRA_TEXT,Sharebody);
                myintent.putExtra(Intent.EXTRA_SUBJECT,sharesub);
                startActivity(Intent.createChooser(myintent,"Share Using"));
            }
        });
        // for usage data

        mUsageStatsManager = (UsageStatsManager) getActivity().getSystemService(Context.USAGE_STATS_SERVICE);
        mInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPm = getActivity().getPackageManager();

        ListView listView = (ListView) getView().findViewById(R.id.comunication_list3);
        mAdapter = new UsageStatsAdapter();
        listView.setAdapter(mAdapter);

        // color your statue bar Fragment
        if (android.os.Build.VERSION.SDK_INT>= 21){
            Window window=getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.skyColor));
        }
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chatyear, container, false);
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
