package com.example.zeroproject.Presentation.Repository.Retrofit;

import com.example.zeroproject.Domain.Model.PostPojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ExchangeAPI {
    @GET("{token}/pair/{firstValue}/{lastValue}")
    Call<PostPojo> getValueFromApi(@Path("firstValue") String value, @Path("lastValue") String lastValue, @Path("token") String token);
}
