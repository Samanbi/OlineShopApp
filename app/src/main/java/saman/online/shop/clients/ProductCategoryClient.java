package saman.online.shop.clients;

import retrofit2.Call;
import retrofit2.http.GET;
import saman.online.shop.models.ProductCategory;
import saman.online.shop.models.base.ServiceResponse;

public interface ProductCategoryClient {
    @GET("productCategory")
    Call<ServiceResponse<ProductCategory>> get();
}

