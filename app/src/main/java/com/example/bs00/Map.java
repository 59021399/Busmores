package com.example.bs00;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Map extends Fragment implements OnMapReadyCallback, LocationListener {
    private GoogleMap mMap;
    DatabaseReference databaseReference;
    List<Venue> venueList;

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
       mMap = googleMap;
       databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    Venue venue = child.getValue(Venue.class);
                    for(int i = 0 ;i<venueList.size();i++){
                        LatLng latLng = new LatLng(venue.venueLat,venue.venueLong);
                        if (mMap!= null){
                            Marker mMarker;
                            mMarker = mMap.addMarker(new MarkerOptions().
                                    position(latLng).title(venue.venueName));
                            
                        }
                    }

                }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });


    }
}
