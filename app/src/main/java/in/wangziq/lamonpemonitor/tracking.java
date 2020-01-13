package in.wangziq.lamonpemonitor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class tracking extends Fragment implements OnMapReadyCallback {

    Location location;
    LocationManager mLocationManager;
    double longitude;
    double latitude;

    private DatabaseReference DataBaseref1;
    private DatabaseReference DataBaseref2;

    FirebaseDatabase database;
    DatabaseReference myRef;

    SupportMapFragment  mapFragment;
    private GoogleMap mMap;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public tracking() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState
                             ) {
        View v= inflater.inflate(R.layout.fragment_tracking, container, false);
        mapFragment= (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

                if(mapFragment == null)
                {
                    FragmentManager fm=getFragmentManager();
                    FragmentTransaction ft=fm.beginTransaction();
                    mapFragment= SupportMapFragment.newInstance();
                    ft.replace(R.id.map, mapFragment).commit();
                }
                mapFragment.getMapAsync(this);
        return v;

    }

    // TODO: Rename and change types and number of parameters
    public static tracking newInstance(String param1, String param2) {
        tracking fragment = new tracking();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

    public static class MapLocation {

        public String Latitude;
        public String Longitude;

        public MapLocation(String Latitude, String Longitude) {
            this.Latitude = Latitude;
            this.Longitude = Longitude;
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        myRef = FirebaseDatabase.getInstance().getReference().child("MapLocation");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Double lat = (Double) dataSnapshot.child("Latitude").getValue();
                Double lon = (Double) dataSnapshot.child("Longitude").getValue();
                // Add a marker in Sydney and move the camera
                LatLng lansia = new LatLng(lat, lon);
                mMap.addMarker(new MarkerOptions().position(lansia).title("Marker Lansia"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(lansia));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lansia, 18), 5000, null);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });


    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
