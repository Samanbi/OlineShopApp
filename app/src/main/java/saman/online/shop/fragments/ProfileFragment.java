package saman.online.shop.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saman.online.shop.MainActivity;
import saman.online.shop.R;
import saman.online.shop.handlers.CurrentUserHandler;
import saman.online.shop.models.Customer;
import saman.online.shop.models.User;
import saman.online.shop.models.base.ServiceResponse;
import saman.online.shop.services.CustomerService;
import saman.online.shop.services.UserService;

public class ProfileFragment extends Fragment {

    private Customer customer;
    private TextInputLayout
            firstName,
            lastName,
            username,
            password,
            tel,
            address,
            mobile,
            email,
            postalCode;
    private Button btnUpdate;
    private ProgressBar progressBar;
    private LinearLayout mainView;
    private Activity activity;

    public ProfileFragment(Activity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fram_profile, container, false);
        init(rootView);
        return rootView;
    }

    private void init(ViewGroup rootView) {
        bindViews(rootView);
        fillUserData();
        customer = new Customer();
        btnUpdate.setOnClickListener(x -> {
            if (!checkFields()) {
                showError("Please fill all of the fields");
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);

            customer.setAddress(address.getEditText().getText().toString());
            customer.setEmail(email.getEditText().getText().toString());
            customer.setFirstName(firstName.getEditText().getText().toString());
            customer.setLastName(lastName.getEditText().getText().toString());
            customer.setMobile(mobile.getEditText().getText().toString());
            customer.setPassword(password.getEditText().getText().toString());
            customer.setPostalCode(postalCode.getEditText().getText().toString());
            customer.setTel(tel.getEditText().getText().toString());
            customer.setUsername(username.getEditText().getText().toString());

            CustomerService.updateInfo(new Callback<ServiceResponse<Customer>>() {
                @Override
                public void onResponse(Call<ServiceResponse<Customer>> call, Response<ServiceResponse<Customer>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (!response.body().isHasError()) {
                            showError("Your profile data has been updated");

                            FragmentTransaction transaction = ((MainActivity) activity).getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.main_frame, new HomeFragment(activity));
                            transaction.commit();

                        } else if (response.body().isHasError()) {
                            showError(response.body().getMessage());
                        }
                    } else {
                        showError("Error on updating your information");
                    }
                }

                @Override
                public void onFailure(Call<ServiceResponse<Customer>> call, Throwable t) {

                }
            }, customer);

        });
    }

    private boolean checkFields() {

        if (address.getEditText().getText().toString().equals(""))
            return false;
        if (email.getEditText().getText().toString().equals(""))
            return false;
        if (firstName.getEditText().getText().toString().equals(""))
            return false;
        if (lastName.getEditText().getText().toString().equals(""))
            return false;
        if (mobile.getEditText().getText().toString().equals(""))
            return false;
        if (postalCode.getEditText().getText().toString().equals(""))
            return false;
        if (tel.getEditText().getText().toString().equals(""))
            return false;

        return true;
    }

    private void bindViews(ViewGroup rootView) {
        firstName = rootView.findViewById(R.id.first_name);
        lastName = rootView.findViewById(R.id.last_name);
        username = rootView.findViewById(R.id.username);
        password = rootView.findViewById(R.id.password);
        tel = rootView.findViewById(R.id.tel);
        address = rootView.findViewById(R.id.address);
        mobile = rootView.findViewById(R.id.mobile);
        email = rootView.findViewById(R.id.email);
        postalCode = rootView.findViewById(R.id.postal);
        btnUpdate = rootView.findViewById(R.id.btn_update);
        progressBar = rootView.findViewById(R.id.progress_bar);
        mainView = rootView.findViewById(R.id.main_view);
    }


    private void fillUserData() {
        if (CurrentUserHandler.IsLoggedIn()) {
            User user = CurrentUserHandler.getCurrentUser();

            UserService.getUserInfo(new Callback<ServiceResponse<User>>() {
                @Override
                public void onResponse(Call<ServiceResponse<User>> call, Response<ServiceResponse<User>> response) {
                    if (response.isSuccessful() && response.body() != null && !response.body().isHasError()) {
                        User userInfo = response.body().getDataList().get(0);
                        firstName.getEditText().setText(userInfo.getFirstName());
                        lastName.getEditText().setText(userInfo.getLastName());
                        username.getEditText().setText(userInfo.getUsername());
                        email.getEditText().setText(userInfo.getEmail());
                        if (userInfo.getCustomer() != null) {
                            tel.getEditText().setText(userInfo.getCustomer().getTel());
                            address.getEditText().setText(userInfo.getCustomer().getAddress());
                            mobile.getEditText().setText(userInfo.getCustomer().getMobile());
                            postalCode.getEditText().setText(userInfo.getCustomer().getPostalCode());
                            customer.setId(userInfo.getCustomer().getId());
                        }
                        customer.setUserId(userInfo.getId());
                    }
                }

                @Override
                public void onFailure(Call<ServiceResponse<User>> call, Throwable t) {

                }
            }, user.getToken());

        }
    }

    private void showError(String error) {
        Snackbar snackbar = Snackbar.make(mainView, error, BaseTransientBottomBar.LENGTH_LONG);
        snackbar.getView().setTranslationY(-130);
        snackbar.show();
    }

}
