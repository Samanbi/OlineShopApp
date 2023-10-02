package saman.online.shop.services;

import retrofit2.Call;
import retrofit2.Callback;
import saman.online.shop.clients.BlogClient;
import saman.online.shop.handlers.ClientHandler;
import saman.online.shop.models.Blog;
import saman.online.shop.models.base.ServiceResponse;

public class BlogService {

    public static void getAllData(Callback<ServiceResponse<Blog>> callback,  int pageNumber, int pageSize) {

        ClientHandler clientHandler = new ClientHandler();
        BlogClient blogClient = clientHandler.getRetrofit().create(BlogClient.class);
        Call<ServiceResponse<Blog>> responseCall = blogClient.getAllData(pageNumber, pageSize);
        responseCall.enqueue(callback);
    }

    public static void increaseVisitCount(Callback<ServiceResponse<Blog>> callback, long id) {

        ClientHandler clientHandler = new ClientHandler();
        BlogClient blogClient = clientHandler.getRetrofit().create(BlogClient.class);
        Call<ServiceResponse<Blog>> responseCall = blogClient.increaseVisitCount(id);
        responseCall.enqueue(callback);
    }
}
