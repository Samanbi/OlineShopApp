package saman.online.shop.handlers;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import saman.online.shop.config.UnsafeSSLConfig;
import saman.online.shop.enums.ApiAddress;

public class ClientHandler {
    private Retrofit.Builder builder;

    private Retrofit retrofit;

    public ClientHandler() {
        builder = new Retrofit.Builder()
                .baseUrl(ApiAddress.baseDomain + "/api/")
                .client(UnsafeSSLConfig.getUnsafeOkHttpClient().build())
                .addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();

    }

    public Retrofit.Builder getBuilder() {
        return builder;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
