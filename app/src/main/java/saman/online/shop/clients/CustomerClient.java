package saman.online.shop.clients;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import saman.online.shop.models.Customer;
import saman.online.shop.models.base.ServiceResponse;

public interface CustomerClient {
    @PUT("customer/updateInfo")
    Call<ServiceResponse<Customer>> updateInfo(@Body() Customer data);
}
