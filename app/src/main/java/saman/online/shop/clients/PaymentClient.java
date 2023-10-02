package saman.online.shop.clients;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import saman.online.shop.models.Payment;
import saman.online.shop.models.StartPaymentVM;
import saman.online.shop.models.base.ServiceResponse;

public interface PaymentClient {
    @POST("payment/")
    Call<ServiceResponse<StartPaymentVM>> addPayment(
            @Body()Payment data
            );
}
