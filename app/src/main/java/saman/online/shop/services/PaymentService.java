package saman.online.shop.services;

import retrofit2.Call;
import retrofit2.Callback;
import saman.online.shop.clients.PaymentClient;
import saman.online.shop.handlers.ClientHandler;
import saman.online.shop.models.Payment;
import saman.online.shop.models.StartPaymentVM;
import saman.online.shop.models.base.ServiceResponse;

public class PaymentService {

    public static void addPayment(Callback<ServiceResponse<StartPaymentVM>> callback, Payment data) {

        ClientHandler clientHandler = new ClientHandler();
        PaymentClient paymentClient = clientHandler.getRetrofit().create(PaymentClient.class);
        Call<ServiceResponse<StartPaymentVM>> responseCall = paymentClient.addPayment(data);
        responseCall.enqueue(callback);
    }
}
