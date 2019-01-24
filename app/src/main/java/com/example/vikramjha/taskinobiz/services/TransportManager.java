package com.example.vikramjha.taskinobiz.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.vikramjha.taskinobiz.LocationReciveModel;
import com.example.vikramjha.taskinobiz.LoctionSendModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TransportManager {

    private static ApiServices apiServices;
    EventListner listener;
    private static TransportManager manager;
    private static final int CODE_UNAUTHORIZED = 401;

    public static TransportManager getInstance(EventListner conlistener) {
        if (manager == null)
            manager = new TransportManager();
        manager.setListener(conlistener);
        return manager;
    }

    public static TransportManager getInstance() {
        if (manager == null)
            manager = new TransportManager();
        return manager;
    }

    public void setListener(EventListner listener) {
        this.listener = listener;
    }

    public static ApiServices getAPIService() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(logging);
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(90, TimeUnit.SECONDS);
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://mapi.eventsexpo.in/Dev/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        apiServices = retrofit.create(ApiServices.class);
        return apiServices;
    }

    public void sentLocation(Context context, LoctionSendModel paymentModel) {
        if (isConnectionAvailable(context)) {
            getAPIService().getLocation(paymentModel).enqueue(new Callback<LocationReciveModel>() {
                @Override
                public void onResponse(Call<LocationReciveModel> call, Response<LocationReciveModel> res) {
                    if (res.isSuccessful()) {
//                        listener.onSuccessResponse(ApiServices.REQUEST_PRODUCTS, res.body());
                        filterData(ApiServices.REQUEST_LOCATION, res.body());
                    } else {
                        processResponse(res, ApiServices.REQUEST_LOCATION);
                    }
                }

                @Override
                public void onFailure(Call<LocationReciveModel> call, Throwable arg0) {
                    //arg0.printStackTrace();
                    processResponse(ApiServices.REQUEST_LOCATION, arg0.getLocalizedMessage());
                }
            });
        } else {
            processResponse(ApiServices.REQUEST_LOCATION, "No internet");
        }
    }




    public void filterData(int type, LocationReciveModel result) {

        listener.onSuccessResponse(type, result);

    }

    public boolean isConnectionAvailable(Context context) {
        if (context == null) return false;
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    /* public void processResponse(Response res, int reqType) {
         Log.d("TM", "processResponse: reqType " + reqType + " res " + res + " res.code() " + res.code());
         DialogUtil.stopProgressDisplay();
         Log.d(TAG, "processResponse: " + res.body());
     }
 */
    public void processResponse(Response res, int reqType) {
//        Log.d("TM", "processResponse: reqType " + reqType + " res " + res + " res.code() " + res.code());

//        Log.d(TAG, "processResponse: " + res.body());
        if (res != null) {
            LocationReciveModel result = new LocationReciveModel();
            result.setStatus("Faild");
            result.setMsg(res.message());
            listener.onFailureResponse(reqType, result);
        } else {
            LocationReciveModel result = new LocationReciveModel();
            result.setMsg("Unable to process");
            listener.onFailureResponse(reqType, result);
        }
    }

    public void processResponse(int reqType, String res) {

        if (res != null) {
            LocationReciveModel result = new LocationReciveModel();
            if (res.contains("Unable to resolve host"))
                result.setMsg("No Internet");
            else
                result.setStatus("Faild");
            listener.onFailureResponse(reqType, result);
        } else {
            LocationReciveModel result = new LocationReciveModel();
            result.setMsg("Unable to process");
            listener.onFailureResponse(reqType, result);
        }
    }
}
