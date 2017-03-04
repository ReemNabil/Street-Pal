package com.sharekeg.streetpal.Home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Toast;

import com.sharekeg.streetpal.Androidversionapi.ApiInterface;
import com.sharekeg.streetpal.R;
import com.sharekeg.streetpal.userinfo.UserInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeActivity extends AppCompatActivity {
    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        token = getIntent().getExtras().getString("Token");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.36/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface mApiService = retrofit.create(ApiInterface.class);
        Call<UserInfo> mSerivce = mApiService.getUser(token);
        mSerivce.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                Toast.makeText(HomeActivity.this, "UserName " + response.body().getEmail(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "failed", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
