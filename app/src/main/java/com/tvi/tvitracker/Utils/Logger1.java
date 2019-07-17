package com.tvi.tvitracker.Utils;

public class Logger1 {

    //TODO:
    /**
     *  during development mode --> LOG = true
     *  Releasing application or giving build for testing --> LOG = false
     **/
    static final boolean LOG = true ;

    public static void i(String tag, String msg)
    {
        if(LOG)
        {
            android.util.Log.i(tag, msg);
        }
    }

    public static void e(String tag, String msg)
    {
        if (msg==null)
            msg="NULL Value";
        if(LOG)
        {
            android.util.Log.e(tag,""+ msg);
        }
    }

    public static void d(String tag, String msg)
    {
        if(LOG)
        {
            android.util.Log.d(tag, msg);
        }
    }

    public static void v(String tag, String msg)
    {
        if(LOG)
        {
            android.util.Log.v(tag, msg);
        }
    }

    public static void w(String tag, String msg)
    {
        if(LOG)
        {
            android.util.Log.w(tag, msg);
        }
    }

}

