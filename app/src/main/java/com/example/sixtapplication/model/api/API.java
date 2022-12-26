package com.example.sixtapplication.model.api;

import com.example.sixtapplication.model.Car;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {

    @GET("cars")
    Call<ArrayList<Car>> callCar();

}
