package saman.online.shop.services;


import retrofit2.Call;
import retrofit2.Callback;
import saman.online.shop.clients.ProductClient;
import saman.online.shop.handlers.ClientHandler;
import saman.online.shop.models.Product;
import saman.online.shop.models.base.ServiceResponse;

public class ProductService {

    public static void getNew(Callback<ServiceResponse<Product>> callback) {
        ClientHandler clientHandler = new ClientHandler();
        ProductClient client = clientHandler.getRetrofit().create(ProductClient.class);
        Call<ServiceResponse<Product>> responseCall = client.getNew();
        responseCall.enqueue(callback);
    }

    public static void getPopular(Callback<ServiceResponse<Product>> callback) {
        ClientHandler clientHandler = new ClientHandler();
        ProductClient client = clientHandler.getRetrofit().create(ProductClient.class);
        Call<ServiceResponse<Product>> responseCall = client.getPopular();
        responseCall.enqueue(callback);
    }

    public static void getByCategory(Callback<ServiceResponse<Product>> callback, long categoryId, int pageNumber, int pageSize) {
        ClientHandler clientHandler = new ClientHandler();
        ProductClient client = clientHandler.getRetrofit().create(ProductClient.class);
        Call<ServiceResponse<Product>> responseCall = client.getByCategory(categoryId, pageNumber, pageSize);
        responseCall.enqueue(callback);
    }
}