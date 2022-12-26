package com.example.sixtapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sixtapplication.model.Car;
import com.example.sixtapplication.model.api.API;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListFragment extends Fragment {

    private ArrayList<Car> carList;
    private ListView list_view;
    private API myapi;
    private final String BaseUrl = "https://cdn.sixt.io/codingtask/";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         displayRetrofitData();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment1_layout,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list_view = view.findViewById(R.id.list_view);

    }

    private void displayRetrofitData() {
        //Fetch the data via Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        myapi = retrofit.create( API.class);
        Call<ArrayList<Car>> arrayListCall = myapi.callCar();

        // Enqueue works on background, it does not affect main thread
        arrayListCall.enqueue(new Callback<ArrayList<Car>>() {
            @Override
            public void onResponse(Call<ArrayList<Car>> call, Response<ArrayList<Car>> response) {
                //Loading Car List
                carList = response.body();

                for(int i=0; i<carList.size(); i++){
                    // For Each car create new Custom Object with the InstanceView
                    Custom custom = new Custom(carList , getActivity(),R.layout.instanceview);
                    list_view.setAdapter(custom);
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
