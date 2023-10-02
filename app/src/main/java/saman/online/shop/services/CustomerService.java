package saman.online.shop.services;

import retrofit2.Call;
import retrofit2.Callback;
import saman.online.shop.clients.CustomerClient;
import saman.online.shop.handlers.ClientHandler;
import saman.online.shop.models.Customer;
import saman.online.shop.models.base.ServiceResponse;

public class CustomerService {

    public static void updateInfo(Callback<ServiceResponse<Customer>> callback, Customer customer) {

        ClientHandler clientHandler = new ClientHandler();
        CustomerClient customerClient = clientHandler.getRetrofit().create(CustomerClient.class);
        Call<ServiceResponse<Customer>> responseCall = customerClient.updateInfo(customer);
        responseCall.enqueue(callback);
    }
}
