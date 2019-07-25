package com.tvi.tvitracker.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;
import com.tvi.tvitracker.BASE.BaseActivity;
import com.tvi.tvitracker.BuildConfig;
import com.tvi.tvitracker.R;
import com.tvi.tvitracker.Utils.AppConstants;
import com.tvi.tvitracker.Utils.Logger1;
import com.tvi.tvitracker.Utils.MarshMallowPermission;
import com.tvi.tvitracker.Utils.StaticDataHelper;
import com.tvi.tvitracker.databinding.ActivityPuntchInBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PuntchInActivity extends BaseActivity {

    private static final int REQUEST_CAPTURE_IMAGE = 1000;
    ActivityPuntchInBinding binding;
    String type = "checkin";
    MarshMallowPermission mallowPermission;
    String imageFilePath;
    File photoFile = null;
    File photoFile1 = null;

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    private static final int REQUEST_CHECK_SETTINGS = 100;
    public static String TAG = "Attandance Activity";
    private String mLastUpdateTime;
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;
    private Boolean mRequestingLocationUpdates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_puntch_in);
        setUp();
    }

    @Override
    protected void setUp() {
        init();
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Attendance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setTitleTextColor(0xFFFFFFFF);
        mallowPermission = new MarshMallowPermission(this);
        if (getIntent().getStringExtra("type") != null)
            type = getIntent().getStringExtra("type");
        binding.time.setText(StaticDataHelper.getcurrentimeforattendance());
        binding.date.setText(StaticDataHelper.getcurrendateforattendance());

        if (mallowPermission.checkPermissionForACCESS_FINE_LOCATION()) {
            startLocationUpdates();
        } else {
            mallowPermission.requestPermissionForACCESS_FINE_LOCATION();
        }

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mallowPermission.checkPermissionForACCESS_FINE_LOCATION()) {
                    if (type.equalsIgnoreCase("checkin")) {
                        try {
                            if (isNetworkConnected())
                                if (mCurrentLocation != null && mCurrentLocation.getLatitude() != 0.0)
                                    if (photoFile1 == null) {
                                        Toast.makeText(PuntchInActivity.this, "Please take a selfie", Toast.LENGTH_SHORT).show();
                                    } else {

                                        punchIn();
                                        Toast.makeText(PuntchInActivity.this, "Attendance Marked Successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                else {
                                    Toast.makeText(PuntchInActivity.this, "Location not found", Toast.LENGTH_SHORT).show();
                                    init();
                                }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            if (isNetworkConnected())
                                if (mCurrentLocation != null && mCurrentLocation.getLatitude() != 0.0)
                                    if (photoFile1 == null) {
                                        Toast.makeText(PuntchInActivity.this, "Please take a selfie", Toast.LENGTH_SHORT).show();
                                    } else {
                                        punchOut();
                                        Toast.makeText(PuntchInActivity.this, "Attendance Marked Successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                else {
                                    Toast.makeText(PuntchInActivity.this, "Location not found", Toast.LENGTH_SHORT).show();
                                    init();
                                }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {

                }
            }
        });

        binding.selfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mallowPermission.checkPermissionForCamera()) {
                    if (mallowPermission.checkPermissionForWRITE_EXTERNAL_STORAGE()) {
                        Logger1.e("permission found", "permisision found");
                        openCameraIntent();
                    } else
                        mallowPermission.requestPermissionForWRITE_EXTERNAL_STORAGE();
                } else {
                    mallowPermission.requestPermissionForCamera();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageFilePath = image.getAbsolutePath();
        return image;
    }

    private void openCameraIntent() {

        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                photoFile = createImageFile();
                photoFile1 = photoFile;
            } catch (IOException ex) {
                Logger1.e("Error", ex.getMessage());
            }
            if (photoFile1 != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.tvi.tvitracker.provider", photoFile1);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MarshMallowPermission.CAMERA_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mallowPermission.checkPermissionForWRITE_EXTERNAL_STORAGE())
                        openCameraIntent();
                    else
                        mallowPermission.requestPermissionForWRITE_EXTERNAL_STORAGE();
                } else {

                }
                break;
            case MarshMallowPermission.WRITE_EXTERNEL_STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mallowPermission.checkPermissionForCamera())
                        openCameraIntent();
                    else
                        mallowPermission.requestPermissionForCamera();
                } else {

                }
            }
            break;
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (requestCode == REQUEST_CAPTURE_IMAGE) {
//            if (resultCode == Activity.RESULT_OK) {
//                Logger1.e("success", "success");
//                Picasso.get().load(photoFile).placeholder(R.drawable.user).into(binding.selfie);
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                Logger1.e("failed", "failed");
//            }
//        }
//    }

    public void punchIn() throws JSONException {

        showLoading();
        String base64 = "";
        try {
            base64 = encodeFileToBase64Binary(photoFile1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String address = mCurrentLocation.getLatitude() + "," + mCurrentLocation.getLongitude();
        String encrypt = StaticDataHelper.decrypt(StaticDataHelper.getUserid(this)) + "@#" + binding.location.getText().toString() + "@#" + address;
        Logger1.e("punchin url", AppConstants.BASEURL);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("randtok", StaticDataHelper.encrypt(encrypt));
        jsonObject.put("image", base64);
        Log.wtf("punchin param", jsonObject.toString());

//        AndroidNetworking.post(AppConstants.BASEURL)
//                .addJSONObjectBody(jsonObject)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        hideLoading();
//                        Logger1.e("punchin", "success : " + response.toString());
//
//                        if (response.optString("status").equalsIgnoreCase("success")) {
//                            finish();
//                            Toast.makeText(PuntchInActivity.this, "Thank You! Your attendance is marked for today", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(PuntchInActivity.this, response.optString("msg"), Toast.LENGTH_SHORT).show();
//                        }
//                        Logger1.e("punchin Response", response.toString());
//                    }
//
//                    @Override
//                    public void onError(ANError error) {
//                        hideLoading();
//                        Logger1.e("punchin Error", error.toString());
//                    }
//                });
    }

    public void punchOut() throws JSONException {

        showLoading();
        String base64 = "";
        try {
            base64 = encodeFileToBase64Binary(photoFile1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String address = mCurrentLocation.getLatitude() + "," + mCurrentLocation.getLongitude();
        String encrypt = StaticDataHelper.decrypt(StaticDataHelper.getUserid(this)) + "@#" + binding.location.getText().toString() + "@#" + address;
        Log.wtf("punchout url", AppConstants.BASEURL);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("randtok", StaticDataHelper.encrypt(encrypt));
        jsonObject.put("image", base64);
        Log.wtf("punchout param", jsonObject.toString());

//        AndroidNetworking.post(AppConstants.BASEURL)
//                .addJSONObjectBody(jsonObject)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        hideLoading();
//                        Logger1.e("punchout", "success : " + response.toString());
//
//                        if (response.optString("status").equalsIgnoreCase("success")) {
//                            finish();
//                            Toast.makeText(PuntchInActivity.this, "Thank You! Your checkout attendance is marked for today", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(PuntchInActivity.this, response.optString("msg"), Toast.LENGTH_SHORT).show();
//                        }
//                        Logger1.e("punchout Response", response.toString());
//                    }
//
//                    @Override
//                    public void onError(ANError error) {
//                        hideLoading();
//                        Logger1.e("punchout Error", error.toString());
//                    }
//                });
    }

    private void init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Logger1.e("location updates", "location received");
                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                updateLocationUI();
            }
        };

        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    private void updateLocationUI() {
        if (mCurrentLocation != null) {
            if (mCurrentLocation.getLatitude() != 0.0) {
                binding.location.setText(getCompleteAddressString(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()));
                stopLocationUpdates();
            } else {
                startLocationUpdates();
            }
        }
    }

    @SuppressLint("LongLogTag")
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.e("My Current loction address", strReturnedAddress.toString());
            } else {
                Log.e("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Logger1.e(TAG, "All location settings are satisfied.");
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());
                        updateLocationUI();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Logger1.e(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(PuntchInActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Logger1.e(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Logger1.e(TAG, errorMessage);

                                Toast.makeText(PuntchInActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }
                        updateLocationUI();
                    }
                });
    }

    public void stopLocationUpdates() {
        mFusedLocationClient
                .removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Logger1.e(TAG, "User agreed to make required location settings changes.");
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        Logger1.e(TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        break;
                }
                break;
            case REQUEST_CAPTURE_IMAGE:
                if (resultCode == Activity.RESULT_OK) {
                    Logger1.e("success", "success");
                    if (photoFile1 != null)
                        Picasso.get().load(photoFile1).placeholder(R.drawable.user).into(binding.selfie);
                    else
                        Logger1.e("error", "error");
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Logger1.e("failed", "failed");
                }
                break;
        }
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            startLocationUpdates();
        }
        updateLocationUI();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void restoreValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("is_requesting_updates")) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean("is_requesting_updates");
            }

            if (savedInstanceState.containsKey("last_known_location")) {
                mCurrentLocation = savedInstanceState.getParcelable("last_known_location");
            }

            if (savedInstanceState.containsKey("last_updated_on")) {
                mLastUpdateTime = savedInstanceState.getString("last_updated_on");
            }
        }
        showLastKnownLocation();
        updateLocationUI();
    }

    public void showLastKnownLocation() {
        if (mCurrentLocation != null) {
//            Toast.makeText(getApplicationContext(), "Lat: " + mCurrentLocation.getLatitude()
//                    + ", Lng: " + mCurrentLocation.getLongitude(), Toast.LENGTH_LONG).show();

        } else {
//            Toast.makeText(getApplicationContext(), "Last known location is not available!", Toast.LENGTH_SHORT).show();
        }
    }

    private String encodeFileToBase64Binary(File fileName) throws IOException {

        File file = fileName;
        byte[] bytes = loadFile(file);
        String encodedString = android.util.Base64.encodeToString(bytes, android.util.Base64.DEFAULT);

        return encodedString;
    }

    private static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        is.close();
        return bytes;
    }
}
