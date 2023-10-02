package saman.online.shop.services;

import retrofit2.Call;
import retrofit2.Callback;
import saman.online.shop.clients.UserClient;
import saman.online.shop.handlers.ClientHandler;
import saman.online.shop.models.User;
import saman.online.shop.models.base.ServiceResponse;

public class UserService {
    public static void login(Callback<ServiceResponse<User>> callback, String username, String password) {

        ClientHandler clientHandler = new ClientHandler();
        UserClient userClient = clientHandler.getRetrofit().create(UserClient.class);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        Call<ServiceResponse<User>> responseCall = userClient.login(user);
        responseCall.enqueue(callback);
    }

    public static void getUserInfo(Callback<ServiceResponse<User>> callback, String token) {

        ClientHandler clientHandler = new ClientHandler();
        UserClient userClient = clientHandler.getRetrofit().create(UserClient.class);
        if (!token.toLowerCase().startsWith("bearer")) {
            token = "Bearer " + token;
        }
        Call<ServiceResponse<User>> responseCall = userClient.getUserInfo(token);
        responseCall.enqueue(callback);
    }
}
