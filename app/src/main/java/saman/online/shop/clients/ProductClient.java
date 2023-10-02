package saman.online.shop.clients;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import saman.online.shop.models.Product;
import saman.online.shop.models.base.ServiceResponse;

public interface ProductClient {
    @GET("product/newProducts")
    Call<ServiceResponse<Product>> getNew();

    @GET("product/popularProducts")
    Call<ServiceResponse<Product>> getPopular();

    @GET("product/getAll/{cid}")
    Call<ServiceResponse<Product>> getByCategory(
            @Path("cid") long cid,
            @Query("pageNumber") int pageNumber,
            @Query("pageSize") int pageSize);
}
