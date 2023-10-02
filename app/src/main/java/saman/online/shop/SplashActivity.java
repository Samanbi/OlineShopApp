package saman.online.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.Locale;

import saman.online.shop.handlers.CardDBHandler;
import saman.online.shop.handlers.CurrentUserHandler;
import saman.online.shop.handlers.UserDBHandler;
import saman.online.shop.models.User;
import saman.online.shop.models.base.ServiceResponse;
import saman.online.shop.services.UserService;
import saman.online.shop.utils.InternetConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {
        bindViews();
    }

    private void cardDBCheck() {
        CardDBHandler cardDBHandler = new CardDBHandler(SplashActivity.this);
        cardDBHandler.checkAndCreateTable();
    }


    private void currentUserCheck() {
        UserDBHandler userDBHandler = new UserDBHandler(SplashActivity.this);
        userDBHandler.checkAndCreateTable();
        User user = userDBHandler.getUserData();
        if (user != null) {
            UserService.getUserInfo(new Callback<ServiceResponse<User>>() {
                @Override
                public void onResponse(Call<ServiceResponse<User>> call, Response<ServiceResponse<User>> response) {
                    CurrentUserHandler currentUserHandler = new CurrentUserHandler();
                    if (response.isSuccessful()) {
                        if (!response.body().isHasError()) {
                            User userInfo = response.body().getDataList().get(0);
                            user.setCustomerId(userInfo.getCustomerId());
                            currentUserHandler.setCurrentUser(user);
                        }else if (response.body().isHasError() && response.body().getMessage().toLowerCase().startsWith("jwt expired")){
                            currentUserHandler.setCurrentUser(null);
                            userDBHandler.deleteAllUsers();
                        }
                        openMainActivity();
                    }
                }

                @Override
                public void onFailure(Call<ServiceResponse<User>> call, Throwable t) {

                }
            }, user.getToken());
        } else {
            openMainActivity();
        }
    }




    private void openMainActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    private void openInternetError() {
        startActivity(new Intent(SplashActivity.this, ConnectionErrorActivity.class));
    }

    private void bindViews() {

    }


    @Override
    protected void onResume() {
        super.onResume();
        boolean isConnected = InternetConnection.checkConnection(SplashActivity.this);
        if (!isConnected) {
            openInternetError();
            return;
        }
        cardDBCheck();
        currentUserCheck();
    }

}