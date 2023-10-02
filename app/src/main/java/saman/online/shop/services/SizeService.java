package saman.online.shop.services;

import retrofit2.Call;
import retrofit2.Callback;
import saman.online.shop.clients.SizeClient;
import saman.online.shop.handlers.ClientHandler;
import saman.online.shop.models.Size;
import saman.online.shop.models.base.ServiceResponse;

public class SizeService {

    public static void getAll(Callback<ServiceResponse<Size>> callback) {

        ClientHandler clientHandler = new ClientHandler();
        SizeClient sizeClient = clientHandler.getRetrofit().create(SizeClient.class);
        Call<ServiceResponse<Size>> responseCall = sizeClient.getAll();
        responseCall.enqueue(callback);
    }
}
