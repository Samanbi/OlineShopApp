package saman.online.shop.clients;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import saman.online.shop.models.User;
import saman.online.shop.models.base.ServiceResponse;

public interface UserClient {
    @POST("user/login")
    Call<ServiceResponse<User>> login(@Body() User data);

    @GET("user/getUserInfo")
    Call<ServiceResponse<User>> getUserInfo(
            @Header("Authorization") String token
    );
}

