package saman.online.shop.services;

import saman.online.shop.clients.ProductCategoryClient;
import saman.online.shop.handlers.ClientHandler;
import saman.online.shop.models.ProductCategory;
import saman.online.shop.models.base.ServiceResponse;
import retrofit2.Call;
import retrofit2.Callback;

public class ProductCategoryService {

    public static void getAll(Callback<ServiceResponse<ProductCategory>> callback) {
        ClientHandler clientHandler = new ClientHandler();
        ProductCategoryClient client = clientHandler.getRetrofit().create(ProductCategoryClient.class);
        Call<ServiceResponse<ProductCategory>> responseCall = client.get();
        responseCall.enqueue(callback);
    }
}
