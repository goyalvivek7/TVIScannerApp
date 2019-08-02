package com.tvi.tvitracker;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tvi.tvitracker.Utils.MarshMallowPermission;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by samrat on 27/01/17.
 */

public class MyApplication extends Application {
MarshMallowPermission marshMallowPermission;
    public static final String TAG = MyApplication.class.getSimpleName();
//    private RequestQueue mRequestQueue;
    Gson gson;
    private static MyApplication instance;
    CalligraphyConfig mCalligraphyConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        CalligraphyConfig.initDefault(mCalligraphyConfig);
    }

    public static synchronized MyApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance;
    }

    public Gson getGson() {
        if (gson != null) {
            return gson;
        } else {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gson = gsonBuilder.create();
            return gson;
        }
    }

    public String getDate_Time() {
        String Date = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss", Locale.US).format(new Date());
        return Date;
    }

    public Date getDate(String str) {

        final Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        int day = c.get(Calendar.DAY_OF_MONTH);
        String str1 = month + "/" + day + "/" + year + " " + str;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss", Locale.US);
        try {
            Date date = sdf.parse(str1);
            System.out.println(date.toString());
            return date;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

//    public RequestQueue getRequestQueue() {
//        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
//        return mRequestQueue;
//    }
//
//    public <T> void addToRequestQueue(Request<T> req, String tag) {
//        req.setShouldCache(false);
//        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
//        getRequestQueue().add(req);
//    }
//
//    public <T> void addToRequestQueue(Request<T> req) {
//        req.setRetryPolicy(new DefaultRetryPolicy(
//                120000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        req.setShouldCache(false);
//        req.setTag(TAG);
//        getRequestQueue().add(req);
//    }
//
//    public void cancelPendingRequests(Object tag) {
//        if (mRequestQueue != null) {
//            mRequestQueue.cancelAll(tag);
//        }
//    }

}
