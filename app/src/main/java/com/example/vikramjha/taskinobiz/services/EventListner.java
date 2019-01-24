package com.example.vikramjha.taskinobiz.services;

import com.example.vikramjha.taskinobiz.LocationReciveModel;

public interface EventListner {


    public void onSuccessResponse(int reqType, LocationReciveModel data);

    public void onFailureResponse(int reqType, LocationReciveModel data);

}
