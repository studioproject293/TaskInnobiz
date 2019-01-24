package com.example.vikramjha.taskinobiz;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vikramjha.taskinobiz.service.LocationMonitoringService;
import com.example.vikramjha.taskinobiz.services.EventListner;
import com.example.vikramjha.taskinobiz.services.TransportManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;


public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, EventListner {
    private GoogleMap mMap;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private static final String TAG = Main2Activity.class.getSimpleName();
    private boolean mAlreadyStartedService = false;
    LinearLayout layoutBottomSheet;
    BottomSheetBehavior sheetBehavior;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        MultiDex.install(this);
        Toolbar toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        layoutBottomSheet = findViewById(R.id.bottom_sheet);
        RecyclerView recyclerView = layoutBottomSheet.findViewById(R.id.recclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        com.example.vikramjha.taskinobiz.adapter.ListAdapter listAdapter = new com.example.vikramjha.taskinobiz.adapter.ListAdapter(this);
        recyclerView.setAdapter(listAdapter);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        final LinearLayout instantLayout = findViewById(R.id.intant);
        final LinearLayout advance = findViewById(R.id.advance);
        final LinearLayout getmequote = findViewById(R.id.getmequote);
        final TextView textInstant = findViewById(R.id.instant);
        final TextView text = findViewById(R.id.text);

        final TextView textAdvance = findViewById(R.id.advanceText);
        final TextView textgetmyquote = findViewById(R.id.getMetextText);
        startStep1();
        getmequote.setBackground(ContextCompat.getDrawable(Main2Activity.this, R.drawable.round_button_lunch));
        advance.setBackground(ContextCompat.getDrawable(Main2Activity.this, R.drawable.round_button_lunch_white));
        instantLayout.setBackground(ContextCompat.getDrawable(Main2Activity.this, R.drawable.round_button_lunch_white));
        textgetmyquote.setTextColor(getResources().getColor(R.color.white));
        textAdvance.setTextColor(getResources().getColor(R.color.red));
        textInstant.setTextColor(getResources().getColor(R.color.red));
        text.setText("You Clicked on GetMy QUOTE");
        instantLayout.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    instantLayout.setBackgroundDrawable(ContextCompat.getDrawable(Main2Activity.this, R.drawable.round_button_lunch));
                    advance.setBackgroundDrawable(ContextCompat.getDrawable(Main2Activity.this, R.drawable.round_button_lunch_white));
                    getmequote.setBackgroundDrawable(ContextCompat.getDrawable(Main2Activity.this, R.drawable.round_button_lunch_white));
                    textInstant.setTextColor(getResources().getColor(R.color.white));
                    textAdvance.setTextColor(getResources().getColor(R.color.red));
                    textgetmyquote.setTextColor(getResources().getColor(R.color.red));
                } else {
                    instantLayout.setBackground(ContextCompat.getDrawable(Main2Activity.this, R.drawable.round_button_lunch));
                    advance.setBackground(ContextCompat.getDrawable(Main2Activity.this, R.drawable.round_button_lunch_white));
                    getmequote.setBackground(ContextCompat.getDrawable(Main2Activity.this, R.drawable.round_button_lunch_white));
                    textInstant.setTextColor(getResources().getColor(R.color.white));
                    textAdvance.setTextColor(getResources().getColor(R.color.red));
                    textgetmyquote.setTextColor(getResources().getColor(R.color.red));
                    text.setText("You Clicked on instant");
                }
            }
        });
        advance.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    advance.setBackgroundDrawable(ContextCompat.getDrawable(Main2Activity.this, R.drawable.round_button_lunch));
                    advance.setBackgroundDrawable(ContextCompat.getDrawable(Main2Activity.this, R.drawable.round_button_lunch_white));
                    instantLayout.setBackgroundDrawable(ContextCompat.getDrawable(Main2Activity.this, R.drawable.round_button_lunch_white));
                    textInstant.setTextColor(getResources().getColor(R.color.red));
                    textAdvance.setTextColor(getResources().getColor(R.color.white));
                    textgetmyquote.setTextColor(getResources().getColor(R.color.red));
                } else {
                    advance.setBackground(ContextCompat.getDrawable(Main2Activity.this, R.drawable.round_button_lunch));
                    instantLayout.setBackground(ContextCompat.getDrawable(Main2Activity.this, R.drawable.round_button_lunch_white));
                    getmequote.setBackground(ContextCompat.getDrawable(Main2Activity.this, R.drawable.round_button_lunch_white));
                    textAdvance.setTextColor(getResources().getColor(R.color.white));
                    textInstant.setTextColor(getResources().getColor(R.color.red));
                    textgetmyquote.setTextColor(getResources().getColor(R.color.red));

                }
            }
        });
        getmequote.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    getmequote.setBackgroundDrawable(ContextCompat.getDrawable(Main2Activity.this, R.drawable.round_button_lunch));
                    advance.setBackgroundDrawable(ContextCompat.getDrawable(Main2Activity.this, R.drawable.round_button_lunch_white));
                    instantLayout.setBackgroundDrawable(ContextCompat.getDrawable(Main2Activity.this, R.drawable.round_button_lunch_white));
                    textgetmyquote.setTextColor(getResources().getColor(R.color.white));
                    textAdvance.setTextColor(getResources().getColor(R.color.red));
                    textInstant.setTextColor(getResources().getColor(R.color.red));
                } else {
                    getmequote.setBackground(ContextCompat.getDrawable(Main2Activity.this, R.drawable.round_button_lunch));
                    advance.setBackground(ContextCompat.getDrawable(Main2Activity.this, R.drawable.round_button_lunch_white));
                    instantLayout.setBackground(ContextCompat.getDrawable(Main2Activity.this, R.drawable.round_button_lunch_white));
                    textgetmyquote.setTextColor(getResources().getColor(R.color.white));
                    textAdvance.setTextColor(getResources().getColor(R.color.red));
                    textInstant.setTextColor(getResources().getColor(R.color.red));
                    text.setText("You Clicked on GetMy QUOTE");
                }
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // check if GPS enabled


        // \n is for new line

        LocalBroadcastManager.getInstance(Main2Activity.this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                double latitude = Double.parseDouble(intent.getStringExtra(LocationMonitoringService.EXTRA_LATITUDE));
                double longitude = Double.parseDouble(intent.getStringExtra(LocationMonitoringService.EXTRA_LONGITUDE));

                if (latitude != 0 && longitude != 0) {
                    Log.d("dfh", "Details===>" + "\n Latitude : " + latitude + "\n Longitude: " + longitude);
                    setMyLocation(latitude, longitude);
                    LoctionSendModel loctionSendModel = new LoctionSendModel();

                    loctionSendModel.setLat(String.valueOf(latitude));
                    loctionSendModel.setLon(String.valueOf(longitude));
                    loctionSendModel.setAddress("vchd");
                    Log.d("Data", "body of map" + new Gson().toJson(loctionSendModel));
                    TransportManager.getInstance(Main2Activity.this).sentLocation(context, loctionSendModel);
                }
            }
        }, new IntentFilter(LocationMonitoringService.ACTION_LOCATION_BROADCAST));
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {

                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {

                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        mMap.setOnMyLocationChangeListener(myLocationChangeListener);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
       /* LatLng TutorialsPoint = new LatLng(21, 57);
        mMap.addMarker(new
                MarkerOptions().position(TutorialsPoint).title("Tutorialspoint.com"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));*/
    }

    private void setMyLocation(double latitude, double longitude) {

        LatLng myLocation = new LatLng(latitude, longitude);
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(myLocation).title("You are at Here!!"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 14f));

    }

    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            setMyLocation(loc.latitude, loc.longitude);
        }
    };

    @Override
    public void onSuccessResponse(int reqType, LocationReciveModel data) {
        Toast.makeText(this, data.getStatus(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailureResponse(int reqType, LocationReciveModel data) {

    }

    private void startStep1() {

        //Check whether this user has installed Google play service which is being used by Location updates.
        if (isGooglePlayServicesAvailable()) {

            //Passing null to indicate that it is executing for the first time.
            startStep2(null);

        } else {
            Toast.makeText(this, "No play service is started", Toast.LENGTH_LONG).show();

        }
    }

    public boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(this, status, 2404).show();
            }
            return false;
        }
        return true;
    }

    private Boolean startStep2(DialogInterface dialog) {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            promptInternetConnect();
            return false;
        }


        if (dialog != null) {
            dialog.dismiss();
        }

        //Yes there is active internet connection. Next check Location is granted by user or not.

        if (checkPermissions()) { //Yes permissions are granted by the user. Go to the next step.
            startStep3();
        } else {  //No user has not granted the permissions yet. Request now.
            requestPermissions();
        }
        return true;
    }

    private boolean checkPermissions() {
        int permissionState1 = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);

        int permissionState2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        return permissionState1 == PackageManager.PERMISSION_GRANTED && permissionState2 == PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermissions() {

        boolean shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION);

        boolean shouldProvideRationale2 = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION);


        // Provide an additional rationale to the img_user. This would happen if the img_user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale || shouldProvideRationale2) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            showSnackbar(R.string.permission_rationale, android.R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Request permission
                    ActivityCompat.requestPermissions(Main2Activity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSIONS_REQUEST_CODE);
                }
            });
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the img_user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    /**
     * Show A Dialog with button to refresh the internet state.
     */
    private void promptInternetConnect() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_alert_no_intenet);
        builder.setMessage(R.string.msg_alert_no_internet);

        String positiveText = getString(R.string.btn_label_refresh);
        builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                //Block the Application Execution until user grants the permissions
                if (startStep2(dialog)) {

                    //Now make sure about location permission.
                    if (checkPermissions()) {

                        //Step 2: Start the Location Monitor Service
                        //Everything is there to start the service.
                        startStep3();
                    } else if (!checkPermissions()) {
                        requestPermissions();
                    }

                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Shows a {@link Snackbar}.
     *
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * @param actionStringId   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    private void showSnackbar(final int mainTextStringId, final int actionStringId, View.OnClickListener listener) {
        Snackbar.make(this.findViewById(android.R.id.content), getString(mainTextStringId), Snackbar.LENGTH_INDEFINITE).setAction(getString(actionStringId), listener).show();
    }

    /**
     * Step 3: Start the Location Monitor Service
     */
    private void startStep3() {

        //And it will be keep running until you close the entire application from task manager.
        //This method will executed only once.

        if (!mAlreadyStartedService) {

            Log.d(TAG, "msg" + R.string.msg_location_service_started);

            //Start location sharing service to app server.........
            Intent intent = new Intent(this, LocationMonitoringService.class);
            startService(intent);

            mAlreadyStartedService = true;
            //Ends................................................
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If img_user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Log.i(TAG, "Permission granted, updates requested, starting location updates");
                startStep3();

            } else {
                // Permission denied.

                // Notify the img_user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the img_user for permission (device policy or "Never ask
                // again" prompts). Therefore, a img_user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                showSnackbar(R.string.permission_denied_explanation, R.string.settings, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Build intent that displays the App settings screen.
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null);
                        intent.setData(uri);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
            }
        }
    }
}
