package saman.online.shop.services;

import retrofit2.Call;
import retrofit2.Callback;

import saman.online.shop.clients.SliderClient;
import saman.online.shop.handlers.ClientHandler;
import saman.online.shop.models.SliderItem;
import saman.online.shop.models.base.ServiceResponse;

public class SliderService {

    public static void getAll(Callback<ServiceResponse<SliderItem>> callback) {

        ClientHandler clientHandler = new ClientHandler();
        SliderClient sliderClient = clientHandler.getRetrofit().create(SliderClient.class);
        Call<ServiceResponse<SliderItem>> responseCall = sliderClient.get();
        responseCall.enqueue(callback);
    }
}
