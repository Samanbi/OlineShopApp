package saman.online.shop.services;

import retrofit2.Call;
import retrofit2.Callback;
import saman.online.shop.clients.InvoiceClient;
import saman.online.shop.handlers.ClientHandler;
import saman.online.shop.models.Invoice;
import saman.online.shop.models.base.ServiceResponse;

public class InvoiceService {

    public static void findByCustomerId(Callback<ServiceResponse<Invoice>> callback, long customerId, int pageNumber, int pageSize, String token) {

        ClientHandler clientHandler = new ClientHandler();
        InvoiceClient invoiceClient = clientHandler.getRetrofit().create(InvoiceClient.class);
        if (!token.toLowerCase().startsWith("bearer")) {
            token = "Bearer " + token;
        }
        Call<ServiceResponse<Invoice>> responseCall = invoiceClient.findByCustomerId(token, customerId, pageNumber, pageSize);
        responseCall.enqueue(callback);
    }

    public static void get(Callback<ServiceResponse<Invoice>> callback, long id, String token) {

        ClientHandler clientHandler = new ClientHandler();
        InvoiceClient invoiceClient = clientHandler.getRetrofit().create(InvoiceClient.class);
        if (!token.toLowerCase().startsWith("bearer")) {
            token = "Bearer " + token;
        }
        Call<ServiceResponse<Invoice>> responseCall = invoiceClient.get(id, token);
        responseCall.enqueue(callback);
    }
}