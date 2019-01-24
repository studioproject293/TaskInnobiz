package com.example.vikramjha.taskinobiz.services;


import com.example.vikramjha.taskinobiz.LocationReciveModel;
import com.example.vikramjha.taskinobiz.LoctionSendModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiServices {

    int REQUEST_LOCATION = 1;

    @POST("LocationUpdates")
    Call<LocationReciveModel> getLocation(@Body LoctionSendModel loctionSendModel);



}

