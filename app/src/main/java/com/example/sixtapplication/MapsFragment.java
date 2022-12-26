package com.example.sixtapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sixtapplication.model.Car;
import com.example.sixtapplication.model.api.API;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsFragment extends Fragment {

    private ArrayList<Car> carList;
    private API myapi;
    private final String BaseUrl = "https://cdn.sixt.io/codingtask/";

    private GoogleMap mMap;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            displayRetrofitData();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }


    private void displayRetrofitData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        myapi = retrofit.create( API.class);
        Call<ArrayList<Car>> arrayListCall = myapi.callCar();
        arrayListCall.enqueue(new Callback<ArrayList<Car>>() {
            @Override
            public void onResponse(Call<ArrayList<Car>> call, Response<ArrayList<Car>> response) {
                carList = response.body();
                double latitude,longitude;
                String title;
                for(int i=0; i<carList.size(); i++){
                    // Take location information from the response and put marker into map

                    latitude = Double.parseDouble(carList.get(i).getLatitude());
                    longitude = Double.parseDouble(carList.get(i).getLongitude());
                    title = "Marker of " + getCarList().get(i).getName();
                    LatLng location = new LatLng(latitude, longitude);
                    mMap.addMarker(new MarkerOptions().position(location).title(title));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Car>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(),"Request is Unsuccessful",Toast.LENGTH_LONG).show();
            }
        });

    }


    public API getMyapi() {
        return myapi;
    }

    public void setMyapi(API myapi) {
        this.myapi = myapi;
    }

    public String getBaseUrl() {
        return BaseUrl;
    }

    public ArrayList<Car> getCarList() {
        return carList;
    }

    public void setCarList(ArrayList<Car> carList) {
        this.carList = carList;
    }



}