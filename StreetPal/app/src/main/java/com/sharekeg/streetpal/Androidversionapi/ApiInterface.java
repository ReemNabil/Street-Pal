package com.sharekeg.streetpal.Androidversionapi;

import com.sharekeg.streetpal.Login.LoginCredentials;



import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by MMenem on 2/22/2017.
 */

public interface ApiInterface


{
    @POST("authenticate")
    Call<LoginCredentials> loginWithCredentials(@Body LoginCredentials data);

}
