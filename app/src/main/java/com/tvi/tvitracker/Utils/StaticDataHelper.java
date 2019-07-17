package com.tvi.tvitracker.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tvi.tvitracker.MyApplication;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class StaticDataHelper {
    public static Context context;

    static String key = "UqSTpQVQClSGwcG5x6Ioz2d3Hw6L+low"; // 128 bit key
    static String initVector = "kHlclHvRcJc+0RlJ"; // 16 bytes IV

    public static String encrypt(String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            Logger1.e("Encryption", "encrypted string: " + Base64.encodeToString(encrypted, Base64.NO_WRAP));

            return Base64.encodeToString(encrypted, Base64.NO_WRAP);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String decrypt(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decode(encrypted, Base64.NO_WRAP));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static final String APP_PREF_NAME = "IVOCLAR";
    public static final String LOGIN_RESPONSE = "IVOCLAR_LOGINRESPONSE";
    public static final String USER_ID = "IVOCLAR_USERID";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    public static String ISUSERLOGIN = "IVOCLAR_ISUSERLOGIN";
    public static String ISFEEDBACKGIVEN = "IVOCLAR_ISFEEDBACKGIVEN";
    public static String MOBILE_NUMBER = "IVOCLAR_MOBILENO";
    public static String EMAIL = "INVOCLAREMAIL";
    public static String ROLE = "INVOCLARROLE";
    public static String NAME = "INVOCLARNAME";
    public static String PROFILE = "INVOCLARPROFILE";
    public static String ADDRESS = "INVOCLARADDRESS";
    public static String PINCODE = "INVOCLARPINCODE";
    public static String diff;
    private static SharedPreferences pref;

    public static SharedPreferences getPref(Context context) {
        return context.getSharedPreferences(APP_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void setStringInPreferences(Context ctx, String key, String value) {
        pref = ctx.getSharedPreferences(APP_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void saveloginresponse(Context ctx, String value) {
        pref = ctx.getSharedPreferences(APP_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
//        LoginResponse loginModel = MyApplication.getInstance().getGson().fromJson(value, LoginResponse.class);
//        setStringInPreferences(ctx, USER_ID, loginModel.getUserData().getId());
//        setStringInPreferences(ctx, NAME, loginModel.getUserData().getName());
//        setStringInPreferences(ctx, MOBILE_NUMBER, loginModel.getUserData().getMobile());
//        setStringInPreferences(ctx, EMAIL, loginModel.getUserData().getEmail());
//        setStringInPreferences(ctx, ROLE, loginModel.getUserData().getRole());
//        setStringInPreferences(ctx, PROFILE, loginModel.getUserData().getImage());
//        setStringInPreferences(ctx, PINCODE, loginModel.getUserData().getPincode());
//        setStringInPreferences(ctx, ADDRESS, loginModel.getUserData().getAddress());
        setBooleanInPreferences(ctx, ISUSERLOGIN, true);
        editor.putString(LOGIN_RESPONSE, value);
        editor.apply();
    }

    public static String getUserid(Context context) {
        return getStringFromPreferences(context, USER_ID);
    }

    public static String getRole(Context context) {
        return getStringFromPreferences(context, ROLE);
    }

    public static String getEmail(Context context) {
        return getStringFromPreferences(context, EMAIL);
    }

    public static String getAddress(Context context) {
        return getStringFromPreferences(context, ADDRESS);
    }

    public static String getPINCODE(Context context) {
        return getStringFromPreferences(context, PINCODE);
    }

    public static boolean isUserLogin(Context context) {
        return getBooleanFromPreferences(context, ISUSERLOGIN);
    }

    public static void setisUserLogin(Context context,Boolean value) {
        setBooleanInPreferences(context, ISUSERLOGIN, value);
    }

    public static boolean isFeedbackGiven(Context context) {
        return getBooleanFromPreferences(context, ISFEEDBACKGIVEN);
    }

    public static void setisFeedbackGiven(Context context,Boolean value) {
        setBooleanInPreferences(context, ISFEEDBACKGIVEN, value);
    }

    public static String getMobileNumber(Context context) {
        return getStringFromPreferences(context, MOBILE_NUMBER);
    }

    public static String getName(Context context) {
        return getStringFromPreferences(context, NAME);
    }

    public static String getProfile(Context context) {
        return getStringFromPreferences(context, PROFILE);
    }

    public static void setBooleanInPreferences(Context ctx, String key, boolean value) {
        pref = ctx.getSharedPreferences(APP_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean(key, value);

        editor.apply();
    }

    public static String getStringFromPreferences(Context ctx, String key) {
        pref = ctx.getSharedPreferences(APP_PREF_NAME, Context.MODE_PRIVATE);
        return pref.getString(key, "");
    }

    public static boolean isToday(Date date) {

        return isSameDay(date, Calendar.getInstance().getTime());
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    public static boolean getBooleanFromPreferences(Context ctx, String key) {
        if (ctx == null)
            ctx = MyApplication.getContext();
        pref = ctx.getSharedPreferences(APP_PREF_NAME, Context.MODE_PRIVATE);
        return pref.getBoolean(key, false);
    }

    public static boolean getBooleanFromPreferences1(Context ctx, String key) {
        if (ctx == null)
            ctx = MyApplication.getContext();
        pref = ctx.getSharedPreferences(APP_PREF_NAME, Context.MODE_PRIVATE);
        return pref.getBoolean(key, true);
    }

    public static void showtoast(Context ctx, String msg) {
        if (msg == null || msg.equalsIgnoreCase(""))
            return;
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    public static void Call(Context con, String no) {
        Logger1.e("phone no", "" + no);
        if (!no.equalsIgnoreCase("")) {
            if (isValidMobile(no)) {
                try {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + no));
                    con.startActivity(intent);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            } else {
                StaticDataHelper.showtoast(con, "Not a valid No.");
            }
        } else {
            StaticDataHelper.showtoast(con, "Phone no not found.");
        }
    }

    static boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    public static String Format_Date(String date) {
        try {
            // 2017-03-29 14:40:26
            //  String strCurrentDate = "Wed, 18 Apr 2012 07:55:29 +0000";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            //SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z");
            Date newDate = null;

            newDate = format.parse(date);
            format = new SimpleDateFormat("dd.MM");
            return format.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    public static String Format_Date_send(String date) {
        try {
            // 2017-03-29 14:40:26
            //  String strCurrentDate = "Wed, 18 Apr 2012 07:55:29 +0000";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            //SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z");
            Date newDate = null;

            newDate = format.parse(date);
            format = new SimpleDateFormat("yy-MM-dd");
            return format.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    public static String changedateNormalTOServer(String date) {
        try {
            // 2017-03-29 14:40:26
            //  String strCurrentDate = "Wed, 18 Apr 2012 07:55:29 +0000";
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            //SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z");
            Date newDate = null;

            newDate = format.parse(date);
            format = new SimpleDateFormat("yy-MM-dd");
            return format.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    public static String dateFormatChatReply(String date) {
        try {
            // 2017-03-29 14:40:26
            //  String strCurrentDate = "Wed, 18 Apr 2012 07:55:29 +0000";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z");
            Date newDate = null;

            newDate = format.parse(date);
            format = new SimpleDateFormat("MMM dd hh:mm a");
            return format.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    public static String Format_Date_server(String date) {
        try {
            // 2017-03-29 14:40:26
            //  String strCurrentDate = "Wed, 18 Apr 2012 07:55:29 +0000";
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            //SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z");
            Date newDate = null;

            newDate = format.parse(date);
            format = new SimpleDateFormat("yyyy-MM-dd");
            return format.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    public static String changeFormatServerToNormal(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = null;
            newDate = format.parse(date);
            format = new SimpleDateFormat("dd-MM-yyyy");
            return format.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    public static String Format_Date_Only(String date) {
        try {
            // 2017-03-29 14:40:26
            //  String strCurrentDate = "Wed, 18 Apr 2012 07:55:29 +0000";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            //SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z");
            Date newDate = null;

            newDate = format.parse(date);


            //  format = new SimpleDateFormat("dd MMM yyyy hh:mm a");
            format = new SimpleDateFormat("dd MMM yyyy");
            return format.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    public static Date getDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.ENGLISH);
        Date testDate = null;
        try {
            testDate = sdf.parse(date);
            return testDate;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
//        SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy hh:mm:ss a");
//        String newFormat = formatter.format(testDate);
//        System.out.println(".....Date..."+newFormat);
    }

    public static String Format_Time_notification(String date) {
        try {
            // 2017-03-29 14:40:26
            //  String strCurrentDate = "Wed, 18 Apr 2012 07:55:29 +0000";
            //  String strCurrentDate = "2018-01-16T12:53:19.200Z";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
            //SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z");
            Date newDate = null;

            newDate = format.parse(date);
            //  format = new SimpleDateFormat("dd MMM yyyy hh:mm a");
            format = new SimpleDateFormat("hh:mm:ss aa");
            Logger1.e("staticdatahelper log", format.format(newDate));
            return format.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            Logger1.e("date", e.getMessage());
            return date;
        }
    }

    public static void tickettimer(final String time, final TextView timerview, final Activity context) {

        Timer updateTimer = new Timer();
        updateTimer.schedule(new TimerTask() {
            public void run() {
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss a");
                    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("hh:mm a");
                    Date date = simpleDateFormat1.parse(time);
//                    Logger1.e("date",date.toString());
                    String tickettime = simpleDateFormat.format(date);
                    Date date1 = simpleDateFormat.parse(tickettime);
//                    Logger1.e("time of ticket",date1.toString());
                    Calendar cal = Calendar.getInstance();
                    String currenttime = simpleDateFormat.format(cal.getTime());
                    Date date2 = simpleDateFormat.parse(currenttime);
//                    Logger1.e("Current time",date2.toString());
                    long difference = date2.getTime() - date1.getTime();
                    int days = (int) (difference / (1000 * 60 * 60 * 24));
                    int hours = (int) (difference / (1000 * 60 * 60));
                    int min = (int) (difference / (1000 * 60)) % 60;
                    int sec = (int) (difference / 1000) % 60;

                    diff = "<b> Timer : </b>" + "<fonts color=#ff0000>" + String.format("%02d", hours) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec) + "</fonts>"; // updated value every1 second
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timerview.setText(Html.fromHtml(diff));
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }, 0, 1000);
    }

    public static void gettimedifference(final String time1, final String time2, final TextView timerview, Activity context) {

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss aa");
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm");
            Date date = simpleDateFormat1.parse(time1);
            String tickettime = simpleDateFormat.format(date);
            Date date1 = simpleDateFormat.parse(tickettime);
            //String currenttime = simpleDateFormat.format(time2);
            Date date2 = simpleDateFormat.parse(time2);
            long difference = date2.getTime() - date1.getTime();
            int days = (int) (difference / (1000 * 60 * 60 * 24));
            int hours = (int) (difference / (1000 * 60 * 60));
            int min = (int) (difference / (1000 * 60)) % 60;
            int sec = (int) (difference / 1000) % 60;


            diff = "<b> Timer : </b>" + "<fonts color=#ff0000>" + String.format("%02d", hours) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec) + "</fonts>";// updated value every1 second
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    timerview.setText(Html.fromHtml(diff));
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showSnackBar(View v, String msg) {
        LinearLayout.LayoutParams objLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Snackbar snackbar = Snackbar.make(v, "" + msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static String getCurrentDateServer() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public static File getdirectory() {

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "Sterling");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Logger1.e("Sterling", "failed to create directory");
                return null;
            }
        }
        return mediaStorageDir;
    }

    public static void hideSoftInput(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showSoftInput(EditText edit, Context context) {
        edit.setFocusable(true);
        edit.setFocusableInTouchMode(true);
        edit.requestFocus();
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edit, 0);
    }

    public static void toggleSoftInput(Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void setFirstTimeLaunch(Context context, boolean isFirstTime) {
        setBooleanInPreferences(context, IS_FIRST_TIME_LAUNCH, isFirstTime);
    }

    public static boolean isFirstTimeLaunch(Context context) {
        return getBooleanFromPreferences1(context, IS_FIRST_TIME_LAUNCH);
    }

    public static String getCurrentDateTime() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd MMM hh:mm:ss a");
        String formattedDate = df.format(c);

        return formattedDate;
    }

}
