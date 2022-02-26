package com.example.zeroproject.Presentation.Repository.Retrofit;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.zeroproject.BuildConfig;
import com.example.zeroproject.Domain.Model.PostPojo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExChangeLogic {
    private ExchangeAPI api;

    public ExChangeLogic(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://v6.exchangerate-api.com/v6/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(ExchangeAPI.class);
    }


    public LiveData<PostPojo> getValue(String convertFrom, String convertTo){

        MutableLiveData<PostPojo> stockPrice = new MutableLiveData<>();

        Call<PostPojo> call = api.getValueFromApi(convertFrom, convertTo, BuildConfig.API_KEY);
        call.enqueue(new Callback<PostPojo>() {
            @Override
            public void onResponse(Call<PostPojo> call, Response<PostPojo> response) {
                if (response.isSuccessful() && response.body() != null){
                    stockPrice.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<PostPojo> call, Throwable t) {
                Log.e("Retrofit", "server is not responding"+t+call);
            }
        });
        return stockPrice;
    }

}
