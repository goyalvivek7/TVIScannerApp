package com.tvi.tvitracker.Utils;

public final class AppConstants {

    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";
//    public static final String BASEURL = "http://ivoclarvivadent.co.in/apis/npdc_new.php/";
//    public static final String BASEURL = "http://vivekgoyal.com/ivoclar/apis/npdc_new.php/";
    public static final String BASEURL = "http://192.168.1.15/scannerapp/apis/npdc_new.php/";
    public static final String LOGIN = BASEURL + "userLogin";
    public static final String SIGNUP = BASEURL + "registerUsers";
    public static final String PROFILE = BASEURL + "viewProfile";
    public static final String UPLOAD_USER_PROFILE = BASEURL + "updateProfileImage";
    public static final String CONTACT_LIST = BASEURL + "contact_numbers_list";
    public static final String SCAN_QR_CODE = BASEURL + "scanQRCode";
    public static final String LIST_SCANS = BASEURL + "listOfScanedQR";
    public static final String GET_SLIDER_IMAGES = BASEURL + "sliderImage";
    public static final String UPDATE_USER_PROFILE = BASEURL + "updateProfile";
    public static final String ADD_FEEDBACK = BASEURL + "addFeedBack";
    public static final String NO_FEEDBACK_GIVEN = BASEURL + "noFeedbackGiven";
    public static final String NOTIFICATION_LIST = BASEURL + "viewAllNotification";
    public static final String OTPVERIFY = BASEURL + "registerOTPResponse";
    public static final String FORGOTPASSWORD = BASEURL + "forgotPassword";
    public static final String SENDNEWPASSWORD = BASEURL + "forgotPasswordResponse";

    private AppConstants() {

    }

}