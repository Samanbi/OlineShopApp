package saman.online.shop.services;

import retrofit2.Call;
import retrofit2.Callback;
import saman.online.shop.clients.ContentClient;
import saman.online.shop.handlers.ClientHandler;
import saman.online.shop.models.Content;
import saman.online.shop.models.base.ServiceResponse;

public class ContentService {

    public static void getAll(Callback<ServiceResponse<Content>> callback) {

        ClientHandler clientHandler = new ClientHandler();
        ContentClient contentClient = clientHandler.getRetrofit().create(ContentClient.class);
        Call<ServiceResponse<Content>> responseCall = contentClient.getAll();
        responseCall.enqueue(callback);
    }
}
