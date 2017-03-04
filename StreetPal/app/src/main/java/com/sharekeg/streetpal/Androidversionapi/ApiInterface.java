package com.sharekeg.streetpal.Androidversionapi;

import com.sharekeg.streetpal.Login.LoginCredentials;
import com.sharekeg.streetpal.userinfo.UserInfo;




import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;

import retrofit2.http.POST;


/**
 * Created by MMenem on 2/22/2017.
 */

public interface ApiInterface


{

    @POST("authenticate")
    Call<LoginCredentials> loginWithCredentials(@Body LoginCredentials data);


    @GET("me")
    Call<UserInfo> getUser(@Header("Authorization") String token);
}
