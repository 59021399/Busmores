package com.example.bs00;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.directions.route.RouteException;
import com.directions.route.RoutingListener;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DriverMapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener,DirectionCallback,
        GoogleMap.OnMarkerClickListener,GoogleApiClient.ConnectionCallbacks, com.google.android.gms.location.LocationListener, GoogleApiClient.OnConnectionFailedListener, RoutingListener {
    GoogleMap mMap;
    private FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

    private LatLng exMyLocation = new LatLng(19.025581, 99.89502);

    Location mLocation;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    DatabaseReference reference;
    private static final String TAG = "MainActivity";
    Query Locationref;
    String serverKey = "AIzaSyCYFUUjEbyijva8900lR8qMQ42R2AJ_4nA";
    //AIzaSyA9rTnxqBiap6yioOqcGREcyrcZno2ShGU
    private final static int MY_PERMISSIONS_REQUEST = 32;
    Polyline polyline;
    LatLng mOriginFrontUP = new LatLng(19.0306021, 99.9225515);
    LatLng mDestinationIct = new LatLng(19.0284821, 99.8996514);
    LatLng mDestinationEN = new LatLng(19.030781, 99.900907);
    LatLng mDestinationSci = new LatLng(19.029935,99.8947646);
    LatLng getmDestinationFrontHall =new LatLng(19.0298251,99.8971221);
    LatLng getmDestinationTowerchancellor =new LatLng(19.0298251,99.8971221);
    LatLng DestinationCEFronup =new LatLng(19.029559, 99.895643);

    LatLng mDestinationPKY = new LatLng(19.025581, 99.89502);
    LatLng DestinationCEUpDorm=new LatLng(19.031867,99.99893540);
    LatLng DestinationUpDorm=new LatLng(19.033178,99.890861);
    LatLng getmDestinationConnect2 = new LatLng(19.030590, 99.898248);

    LatLng mDestinationDemonstration = new LatLng(19.034287, 99.8858557);

    LatLng mDestinationConnect = new LatLng(19.030526, 99.902994);


    int score = 0;
    //selectroute
    RadioButton[] arr_rdo = new RadioButton[3];
    int current_cursor = 0;
    int[] rdo_id = new int[]{R.id.radioButtonRouteUpdorm
            , R.id.radioButtonOdd, R.id.radioButtonEven
    };
    String erorMs= "Not found";
    //Text
    int mMarkerCount = 2; //Global Variable
    Marker mMarker; //Global Variable
    //static Bus
    int Busseat =40;
    int persent = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_map);
// Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        reference = FirebaseDatabase.getInstance().getReference();


        for (int i = 0; i < rdo_id.length; i++)
            arr_rdo[i] = (RadioButton) findViewById(rdo_id[i]);
        RadioGroup rdoGroup = (RadioGroup) findViewById(R.id.radiogroupRoute);
        rdoGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                for (int i = 0; i < rdo_id.length; i++) {
                    if (rdo_id[i] == checkedId)
                        current_cursor = i;

                }
                if (current_cursor == 0) {
                    if (mMap!=null){
                        GetMarkerBusstopUpDorm();

                        RouteUpDrom();
                        getMarkerUpDorm();

                    }
                    mMap.clear();







                } else if (current_cursor == 1) {
                    mMap.clear();
                    RouteOdd();
                    getMarker();
                    GetMarkerBusstopOdd();





                } else if (current_cursor == 2) {
                    mMap.clear();
                    RouteEven();
                    getMarkerEven();
                    GetMarkerBusstopEven();
                }



            }

        });

    }



    //ถนนหน้ามอ
    public void RouteUpDrom() {
        GoogleDirection.withServerKey(serverKey)
                .from(mDestinationDemonstration)
                .and(mDestinationPKY)
                .and(DestinationCEFronup)
                .and(mDestinationEN)
                .and(mDestinationConnect)
                .and(mDestinationIct)
                .to(mDestinationPKY)
                .transportMode(TransportMode.WALKING)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if (direction.isOK()) {
                            Route route = direction.getRouteList().get(0);
                            int legCount = route.getLegList().size();
                            for (int index = 0; index < legCount; index++) {
                                Leg leg = route.getLegList().get(index);


                                List<Step> stepList = leg.getStepList();
                                ArrayList<PolylineOptions> polylineOptionList = DirectionConverter.createTransitPolyline(DriverMapsActivity.this, stepList, 5, Color.BLUE, 3, Color.BLUE);
                                for (PolylineOptions polylineOption : polylineOptionList) {
                                    mMap.addPolyline(polylineOption);

                                }




                            }

                            setCameraWithCoordinationBounds(route);

                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {

                    }
                });

    }

    //คี่
    public void RouteOdd() {
        GoogleDirection.withServerKey(serverKey)
                .from(mOriginFrontUP)
                .and(mDestinationConnect)
                .and(mDestinationIct)
                .and(mDestinationPKY)
                .and(DestinationCEFronup)
                .and(getmDestinationConnect2)
                .to(mDestinationIct)
                //.and(mDestinationIct)
                .transportMode(TransportMode.WALKING)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if (direction.isOK()) {
                            Route route = direction.getRouteList().get(0);
                            int legCount = route.getLegList().size();
                            for (int index = 0; index < legCount; index++) {
                                Leg leg = route.getLegList().get(index);
                                List<Step> stepList = leg.getStepList();
                                ArrayList<PolylineOptions> polylineOptionList = DirectionConverter.createTransitPolyline(DriverMapsActivity.this, stepList, 5, Color.GREEN, 3, Color.BLUE);
                                for (PolylineOptions polylineOption : polylineOptionList) {
                                    mMap.addPolyline(polylineOption);
                                }




                            }

                            setCameraWithCoordinationBounds(route);

                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {

                    }
                });

    }

    //คู่
    public void RouteEven() {
        GoogleDirection.withServerKey(serverKey)
                .from(mOriginFrontUP)
                .and(mDestinationEN)
                .and(mDestinationPKY)
                .and(DestinationCEFronup)
                .and(getmDestinationConnect2)
                .to(mOriginFrontUP)
                //.and(mDestinationIct)
                .transportMode(TransportMode.WALKING)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if (direction.isOK()) {
                            Route route = direction.getRouteList().get(0);
                            int legCount = route.getLegList().size();
                            for (int index = 0; index < legCount; index++) {
                                Leg leg = route.getLegList().get(index);
                                List<Step> stepList = leg.getStepList();
                                ArrayList<PolylineOptions> polylineOptionList = DirectionConverter.createTransitPolyline(DriverMapsActivity.this, stepList, 5, Color.RED, 3, Color.BLUE);
                                for (PolylineOptions polylineOption : polylineOptionList) {
                                    mMap.addPolyline(polylineOption);
                                }
                            }
                            setCameraWithCoordinationBounds(route);

                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {

                    }
                });
            }

    public void getMarkerUpDorm() {
        Locationref = reference.child("Arduino").child("ArduinoUpDorm").orderByKey();
        Locationref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue(String.class);


                int counter = Integer.parseInt(dataSnapshot.child("counter").getValue().toString());

                double percentscount0 = counter*100/40;
                Log.e("Counter xxxx", String.valueOf(counter));
                Log.v("Counter xxxx", String.valueOf(percentscount0));

                double latitude = Double.parseDouble(dataSnapshot.child("latitude").getValue().toString());
                double longitude = Double.parseDouble(dataSnapshot.child("longtitude").getValue().toString());


                LatLng lokasi = new LatLng(latitude, longitude);
                if(mMarkerCount > 0){
                    if(mMarker != null){
                        mMarker.remove();
                    }
                }


                mMarker = mMap.addMarker(new MarkerOptions().position(lokasi).title("Busnumber: " + name).snippet("จำนวนคน: " + counter + "  ประมาณ : " +percentscount0+"% ของจำนวนผู้โดยสารทั้งหมดบนรถ").icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("ic_bus", 70, 70))));
                mMarkerCount++;


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                LatLng lokasi = exMyLocation;
                String name = erorMs;
                String counter = erorMs;
                mMap.addMarker(new
                        MarkerOptions().position(lokasi).title("Busnumber: " + name).snippet("จำนวนคน: " + counter+ "  ประมาณ : "+erorMs+"% ของจำนวนผู้โดยสารทั้งหมดบนรถ").icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("ic_fix",70,70))));

            }
        });



    }
public void  getMarkerEven(){


    Locationref = reference.child("Arduino").child("ArduinoEven");
    Locationref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            String name = dataSnapshot.child("name").getValue(String.class);
            int counter = Integer.parseInt(dataSnapshot.child("counter").getValue().toString());
            double percentscount0 = counter*100/40;
            Log.e("Counter xxxx", String.valueOf(counter));
            Log.v("Counter xxxx", String.valueOf(percentscount0));

            double latitude = Double.parseDouble(dataSnapshot.child("latitude").getValue().toString());
            double longitude = Double.parseDouble(dataSnapshot.child("longtitude").getValue().toString());


            LatLng lokasi = new LatLng(latitude, longitude);
            if(mMarkerCount > 0){
                if(mMarker != null){
                    mMarker.remove();

                }
            }


            mMarker = mMap.addMarker(new
                    MarkerOptions().position(lokasi).title("Busnumber: " + name).snippet("จำนวนคน: " + counter + "  ประมาณ : " +percentscount0+"% ของจำนวนผู้โดยสารทั้งหมดบนรถ").icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("ic_bus", 70, 70))));
            mMarkerCount++;


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            LatLng lokasi = exMyLocation;
            String name = erorMs;
            String counter = erorMs;
            mMap.addMarker(new
                    MarkerOptions().position(lokasi).title("Busnumber: " + name).snippet("จำนวนคน: " + counter+ "  ประมาณ : "+erorMs+"% ของจำนวนผู้โดยสารทั้งหมดบนรถ").icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("ic_fix",70,70))));

        }
    });


}

public void getMarker(){
    Locationref = reference.child("Arduino").child("Arduino");
    Locationref.addValueEventListener(new ValueEventListener() {


        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue(String.class);


                int counter = Integer.parseInt(dataSnapshot.child("counter").getValue().toString());

           double percentscount0 = counter*100/40;
            Log.e("Counter xxxx", String.valueOf(counter));
            Log.v("Counter xxxx", String.valueOf(percentscount0));

                double latitude = Double.parseDouble(dataSnapshot.child("latitude").getValue().toString());
                double longitude = Double.parseDouble(dataSnapshot.child("longtitude").getValue().toString());


                LatLng lokasi = new LatLng(latitude, longitude);
            if(mMarkerCount > 0){
                if(mMarker != null){
                    mMarker.remove();
                }
            }


            mMarker = mMap.addMarker(new
                        MarkerOptions().position(lokasi).title("Busnumber: " + name).snippet("จำนวนคน: " + counter + "  ประมาณ : " +percentscount0+"% ของจำนวนผู้โดยสารทั้งหมดบนรถ").icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("ic_bus", 70, 70))));
            mMarkerCount++;


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }

    );


}
public void  GetMarkerBusstopUpDorm(){
    rootRef.collection("BusStop_UpDorm")
            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {

                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.color(Color.RED);
                MarkerOptions markerOptions = new MarkerOptions();


                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    String name = documentSnapshot.getString("name");
                    GeoPoint geo = documentSnapshot.getGeoPoint("latlng");
                    double lat = geo.getLatitude();
                    double lng = geo.getLongitude();
                    LatLng latLng = new LatLng(lat, lng);

                    mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("ic_busstop",70,70))));
                    // mMap.addMarker(new
                    //                    MarkerOptions().position(lokasi).title("Busnumber: " + name).snippet("จำนวนคน: " + counter+ "  ประมาณ : "+(counter/40)*100+"% ของจำนวนผู้โดยสารทั้งหมดบนรถ").icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("ic_bus",70,70))));
                    //

                }


            }
        }});


}
public  void GetMarkerBusstopEven(){

    rootRef.collection("Bustop_Even")
            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {

                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.color(Color.RED);
                MarkerOptions markerOptions = new MarkerOptions();


                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    String name = documentSnapshot.getString("name");
                    GeoPoint geo = documentSnapshot.getGeoPoint("latlng");
                    double lat = geo.getLatitude();
                    double lng = geo.getLongitude();
                    LatLng latLng = new LatLng(lat, lng);

                    mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("ic_busstop",70,70))));
                    // mMap.addMarker(new
                    //                    MarkerOptions().position(lokasi).title("Busnumber: " + name).snippet("จำนวนคน: " + counter+ "  ประมาณ : "+(counter/40)*100+"% ของจำนวนผู้โดยสารทั้งหมดบนรถ").icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("ic_bus",70,70))));
                    //

                    polylineOptions.add(new LatLng(lat, lng));
                }


            }
        }});




}
public void GetMarkerBusstopOdd(){
    rootRef.collection("BusStop_Odd")
            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {

                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.color(Color.RED);
                MarkerOptions markerOptions = new MarkerOptions();


                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    String name = documentSnapshot.getString("name");
                    GeoPoint geo = documentSnapshot.getGeoPoint("latlng");
                    double lat = geo.getLatitude();
                    double lng = geo.getLongitude();
                    LatLng latLng = new LatLng(lat, lng);

                    mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("ic_busstop",70,70))));
                    // mMap.addMarker(new
                    //                    MarkerOptions().position(lokasi).title("Busnumber: " + name).snippet("จำนวนคน: " + counter+ "  ประมาณ : "+(counter/40)*100+"% ของจำนวนผู้โดยสารทั้งหมดบนรถ").icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("ic_bus",70,70))));
                    //

                    polylineOptions.add(new LatLng(lat, lng));
                }
            }
        }});



}
    private void getScore( int current_score) {
        score += current_score;

    }
    public Bitmap resizeBitmap(String drawableName, int width, int height){

        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(drawableName, "mipmap", getPackageName()));
        return Bitmap.createScaledBitmap(imageBitmap, width, height, false);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (mMap!=null){
            RouteOdd();
            RouteUpDrom();
            RouteEven();
            GetMarkerBusstopUpDorm();
            GetMarkerBusstopEven();
            GetMarkerBusstopOdd();

            LatLngBounds ADELAIDE = new LatLngBounds(
                    new LatLng(19.028203, 99.885863),
                    new LatLng(19.034565, 99.923183)
                    );
// Constrain the camera target to the Adelaide bounds.
            mMap.setLatLngBoundsForCameraTarget(ADELAIDE);
            mMap.setMaxZoomPreference(17.0f);
            mMap.setMinZoomPreference(14.0f);


        }






        //root555555555555555555555555555555555555555555

        rootRef.collection("Route_dorm").whereEqualTo("latlng", true)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                }



                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {


                }

            }
        });
        //Route



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
        buildGoogleApiClient();
        mMap.setMyLocationEnabled(true);


    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }
    @Override
    public void onLocationChanged(Location location) {

        mLocation = location;
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
        mMap.animateCamera(cameraUpdate);
        mMap.setMaxZoomPreference(17);
        String userId = "";

        Circle circle = null;
        if(mMarkerCount > 0){
            if(circle != null){
                circle.remove();
            }
        }
        circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(location.getLatitude(),location.getLongitude()))
                .radius(100).strokeColor(Color.RED)
                .fillColor(Color.RED)

        );



        if(userId==null){
            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("BusAvailable");
        GeoFire geoFire = new GeoFire(reference);
        geoFire.setLocation(userId,new GeoLocation(location.getLatitude(),location.getLongitude()));



    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {


    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(10000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


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
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStop() {
        String userId="";


        if(userId==null){
            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("BusAvailable");
        GeoFire geoFire = new GeoFire(reference);
        geoFire.removeLocation(userId);

        super.onStop();
    }

    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<com.directions.route.Route> arrayList, int i) {

    }

    @Override
    public void onRoutingCancelled() {

    }


    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {




    }

    private void setCameraWithCoordinationBounds(Route route) {

            LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
            LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
            LatLngBounds bounds = new LatLngBounds(southwest, northeast);
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

    @Override
    public void onDirectionFailure(Throwable t) {
    }


}
