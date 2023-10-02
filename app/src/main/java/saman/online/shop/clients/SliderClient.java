package saman.online.shop.clients;

import retrofit2.Call;
import retrofit2.http.GET;
import saman.online.shop.models.SliderItem;
import saman.online.shop.models.base.ServiceResponse;

public interface SliderClient {
    @GET("slider/")
    Call<ServiceResponse<SliderItem>> get();
}
