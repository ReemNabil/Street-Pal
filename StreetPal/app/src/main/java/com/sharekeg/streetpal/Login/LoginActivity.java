package com.sharekeg.streetpal.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sharekeg.streetpal.Androidversionapi.ApiInterface;
import com.sharekeg.streetpal.R;
import com.sharekeg.streetpal.Registration.RegisterActivity;
import com.sharekeg.streetpal.Registration.SelectTrustedContacts;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btLogin;
    private TextView tvSignUp;
    private String userName;
    private String password;
    View focusView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = (EditText) findViewById(R.id.etusername);
        etPassword = (EditText) findViewById(R.id.etpassword);
        btLogin = (Button) findViewById(R.id.btnLogin);
        tvSignUp = (TextView) findViewById(R.id.link_to_register);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SelectTrustedContacts.class);
                startActivity(i);
            }
        });
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });


    }

    private void attemptLogin() {

        boolean mCancel = this.loginValidation();
        if (mCancel) {
            focusView.requestFocus();
        } else {
            loginProcessWithRetrofit(userName, password);
        }


    }

    private boolean loginValidation() {
        userName = etUsername.getText().toString();
        password = etPassword.getText().toString();
        boolean cancel = false;
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            etPassword.setError("Invalid password");
            focusView = etPassword;
            cancel = true;

        }
        if (TextUtils.isEmpty(userName)) {
            etUsername.setError("Empty username or email");
            focusView = etUsername;
            cancel = true;
        } else if (!isEmailValid(userName)) {
            etUsername.setError("Invalid username or email");
            focusView = etUsername;
            cancel = true;
        }
        return cancel;
    }

    private boolean isEmailValid(String userName) {
        return userName.contains("M");
    }

    private boolean isPasswordValid(String password) {

        return password.length() > 3;
    }

    private void loginProcessWithRetrofit(String userName, String password) {

        ApiInterface mApiService = this.getInterfaceService();
        Call<LoginCredentials> mService = mApiService.loginWithCredentials(new LoginCredentials(userName, password));
        mService.enqueue(new Callback<LoginCredentials>() {
            @Override
            public void onResponse(Call<LoginCredentials> call, Response<LoginCredentials> response) {

                Toast.makeText(LoginActivity.this,response.body().getToken(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<LoginCredentials> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Please check your network connection and internet permission", Toast.LENGTH_LONG).show();

            }
        });

    }


    private ApiInterface getInterfaceService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.43/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ApiInterface mInterfaceService = retrofit.create(ApiInterface.class);
        return mInterfaceService;
    }

}
