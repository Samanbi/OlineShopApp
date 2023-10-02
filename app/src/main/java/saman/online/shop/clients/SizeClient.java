package saman.online.shop.clients;

import retrofit2.Call;
import retrofit2.http.GET;
import saman.online.shop.models.Size;
import saman.online.shop.models.base.ServiceResponse;

public interface SizeClient {
    @GET("size/")
    Call<ServiceResponse<Size>> getAll();
}
