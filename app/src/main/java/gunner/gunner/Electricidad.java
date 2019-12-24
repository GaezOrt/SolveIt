package gunner.gunner;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;






import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static gunner.gunner.MainActivity.locationManager;
import static gunner.gunner.R.id.imageView5;
import static gunner.gunner.R.id.imageView6;
import static gunner.gunner.R.id.spinner2;
import static gunner.gunner.R.id.swiperefresh;
import static gunner.gunner.R.id.textView29;


/**
 * Created by Gaston on 11/18/2019.
 */

public class Electricidad extends AppCompatActivity implements MultiSpinner.MultiSpinnerListener {

    static String location;
    static int comentarios;
    TextView locations;
    static ArrayList<Electricista> electricistas = new ArrayList<Electricista>();
    static boolean showLoad;
    static MyListAdaptor adapter;


    protected void onCreate(Bundle savedInstanceState) {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));
        }


        setTheme(R.style.Theme_Design_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.electr);


        //onLocationChanged(loc);
        final Animation listaAnim = AnimationUtils.loadAnimation(this, R.anim.translate_electricidad);
        ListView lista = (ListView) findViewById(R.id.lista);
        lista.setAnimation(listaAnim);

        final Animation locationAnim = AnimationUtils.loadAnimation(this, R.anim.translate_location_search);
        ImageView imageLoc = (ImageView) findViewById(R.id.imageView22);
        imageLoc.startAnimation(locationAnim);

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.startAnimation(locationAnim);

        ImageView searchImage = (ImageView) findViewById(imageView6);
        searchImage.startAnimation(locationAnim);

        TextView text2 = (TextView) findViewById(textView29);
        text2.startAnimation(locationAnim);


        MultiSpinner ms = (MultiSpinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        list.add("Palermo");
        list.add("Recoleta");
        list.add("Almagro");
        list.add("Caballito");
        list.add("Boedo");
        list.add("Belgrano");
        list.add("Villa Pueyrredon");
        list.add("Villa Urquiza");
        list.add("Flores");
        list.add("Lugano");
        ms.setItems(list, "Zonas de trabajo", this);

        ImageView search = (ImageView) findViewById(imageView6);

        Intent r = new Intent(this, DownloadStuffInBackground.class);
        search.setOnClickListener((v) -> {
            Electricidad.electricistas.clear();
            DownloadStuffInBackground.lookForLocation = true;
            Spinner spinner = (Spinner) findViewById(R.id.spinner2);
            DownloadStuffInBackground.LookingAfterLocation = spinner.getSelectedItem().toString();
            startService(r);
            adapter.notifyDataSetChanged();

        });

        adapter = new MyListAdaptor(this, R.layout.list_view, electricistas);
        final ListView listView = (ListView) findViewById(R.id.lista);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.invalidateViews();
        Intent u = new Intent(this, DownloadStuffInBackground.class);

        SwipeRefreshLayout swipe = (SwipeRefreshLayout) findViewById(swiperefresh);

        swipe.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Electricidad.electricistas.clear();
                        DownloadStuffInBackground.keepLooking = true;
                        startService(u);
                        adapter.notifyDataSetChanged();
                        swipe.setRefreshing(true);
                        if (!DownloadStuffInBackground.isRunning) {
                            swipe.setRefreshing(false);
                        }
                    }

                });

        DownloadStuffInBackground.searchComments = true;
        Intent d = new Intent(this, DownloadStuffInBackground.class);

        //Realizar ampliacion cuando se clickea item de lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startService(d);
                FindInDatabase.namePassedViaParam = electricistas.get(position).email;
                FindInDatabase.ubicacionElectricista = position;
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.cli);
                mp.start();
                finish();
                startActivity(new Intent(Electricidad.this, FindInDatabase.class));
            }
        });

        //Ir para atras
        final ImageView atrasBut = (ImageView) findViewById(imageView5);
        atrasBut.setOnClickListener((v) -> {

            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.cli);
            mp.start();
            finish();
            startActivity(new Intent(Electricidad.this, MainActivity.class));
        });
    }


    @Override
    public void onBackPressed() {
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.cli);
        mp.start();
        startActivity(new Intent(Electricidad.this, MainActivity.class));

    }


    public static Handler UIHandler;

    static {
        UIHandler = new Handler(Looper.getMainLooper());
    }

    public static void runOnUI(Runnable runnable) {
        UIHandler.post(runnable);
    }

    @Override
    public void onItemsSelected(boolean[] selected) {

    }

    /*
    @Override
    public void onConnected(@Nullable Bundle bundle) {
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

        startLocationUpdates();

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if(mLocation == null){
            startLocationUpdates();
        }
        if (mLocation != null) {

            // mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
            //mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onConnectionSuspended(int i) {
        Log.i("AA", "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("aa", "Connection failed. Error: " + connectionResult.getErrorCode());

    }
    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
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
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, (LocationListener) this);
        Log.d("reque", "--->>>>");
    }
    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    private boolean checkLocation() {
        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }
    public void onLocationChanged(Location location) {

        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        mLatitudeTextView.setText(String.valueOf(location.getLatitude()));
        mLongitudeTextView.setText(String.valueOf(location.getLongitude() ));
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
        //LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
    }
    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

     */
}





