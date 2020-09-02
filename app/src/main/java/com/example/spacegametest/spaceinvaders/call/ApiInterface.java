package com.example.spacegametest.spaceinvaders.call;

import com.example.spacegametest.spaceinvaders.models.Example;
import com.example.spaceinvaders.models.Example;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("app")
    Call<Example> getResponse();

}
