package saman.online.shop.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saman.online.shop.MainActivity;
import saman.online.shop.PaymentActivity;
import saman.online.shop.R;
import saman.online.shop.handlers.CurrentUserHandler;
import saman.online.shop.handlers.UserDBHandler;
import saman.online.shop.models.SliderItem;
import saman.online.shop.models.User;
import saman.online.shop.models.base.ServiceResponse;
import saman.online.shop.services.UserService;

public class LoginFragment extends Fragment {

    private Activity activity;
    private LinearLayout mainView;
    private TextInputLayout userName, password;
    private Button btnLogin;
    private String user, pass;
    private ProgressBar progressBar;


    public LoginFragment(Activity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fram_login, container, false);
        init(rootView);
        btnLogin.setOnClickListener(view -> {
            user = userName.getEditText().getText().toString();
            pass = password.getEditText().getText().toString();
            if (user.equals("")) {
                showError("please enter your username");
                return;
            }
            if (password.equals("")) {
                showError("please enter your password");
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.GONE);
            UserService.login(new Callback<ServiceResponse<User>>() {
                @Override
                public void onResponse(Call<ServiceResponse<User>> call, Response<ServiceResponse<User>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (!response.body().isHasError()) {
                            User user = response.body().getDataList().get(0);

                            UserDBHandler dbHandler = new UserDBHandler(activity);
                            dbHandler.deleteAllUsers();
                            dbHandler.addData(user.getContentValues());
                            CurrentUserHandler currentUserHandler = new CurrentUserHandler();
                            currentUserHandler.setCurrentUser(user);
                            MainActivity mainActivity = (MainActivity) getActivity();
                            mainActivity.syncMenu();

                            FragmentTransaction transaction = mainActivity.getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.main_frame, new HomeFragment(activity));
                            transaction.commit();

                            showError("Welcome back " + user.getFullName());
                            getUserInfo(user.getToken());

                        } else {
                            showError(response.body().getMessage());
                            progressBar.setVisibility(View.GONE);
                            btnLogin.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ServiceResponse<User>> call, Throwable t) {
                    showError("Error on login");
                    progressBar.setVisibility(View.GONE);
                    btnLogin.setVisibility(View.VISIBLE);
                }
            }, user, pass);
        });
        return rootView;
    }

    private void init(ViewGroup rootView) {
        bindViews(rootView);
    }

    private void bindViews(ViewGroup rootView) {
        mainView = rootView.findViewById(R.id.login_view);
        userName = rootView.findViewById(R.id.username);
        password = rootView.findViewById(R.id.password);
        btnLogin = rootView.findViewById(R.id.btn_login);
        progressBar = rootView.findViewById(R.id.progress_bar);
    }

    private void showError(String error) {
        Snackbar snackbar = Snackbar.make(mainView, error, BaseTransientBottomBar.LENGTH_LONG);
        snackbar.getView().setTranslationY(-130);
        snackbar.show();
    }

    private void getUserInfo(String token) {
        UserService.getUserInfo(new Callback<ServiceResponse<User>>() {
            @Override
            public void onResponse(Call<ServiceResponse<User>> call, Response<ServiceResponse<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (!response.body().isHasError()) {
                        User user = response.body().getDataList().get(0);
                        CurrentUserHandler currentUserHandler = new CurrentUserHandler();
                        User currentUser = CurrentUserHandler.getCurrentUser();
                        currentUser.setCustomerId(user.getCustomerId());
                        currentUserHandler.setCurrentUser(user);
                    }
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse<User>> call, Throwable t) {

            }
        }, token);
    }
}
