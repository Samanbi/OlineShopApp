package saman.online.shop.clients;

import retrofit2.Call;
import retrofit2.http.GET;
import saman.online.shop.models.Content;
import saman.online.shop.models.base.ServiceResponse;

public interface ContentClient {
    @GET("content/getAllData")
    Call<ServiceResponse<Content>> getAll();
}
