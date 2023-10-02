package saman.online.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saman.online.shop.enums.PaymentType;
import saman.online.shop.handlers.CardDBHandler;
import saman.online.shop.handlers.CurrentUserHandler;
import saman.online.shop.models.CardItem;
import saman.online.shop.models.OrderItem;
import saman.online.shop.models.Payment;
import saman.online.shop.models.PaymentUserVM;
import saman.online.shop.models.StartPaymentVM;
import saman.online.shop.models.User;
import saman.online.shop.models.base.ServiceResponse;
import saman.online.shop.services.PaymentService;

public class PaymentActivity extends AppCompatActivity {

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
    private Button btnPayment;
    private LinearLayout mainView;
    private ProgressBar progressBar;
    private List<CardItem> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        init();
    }

    private void init() {
        bindViews();
        fillDataList();
        fillUserData();

        btnPayment.setOnClickListener(view -> {
            if (!checkFields()) {
                Snackbar.make(mainView, "Please field all of the fields", BaseTransientBottomBar.LENGTH_LONG).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            btnPayment.setVisibility(View.GONE);
            PaymentUserVM userVM = new PaymentUserVM();
            userVM.setAddress(address.getEditText().getText().toString());
            userVM.setEmail(email.getEditText().getText().toString());
            userVM.setFirstName(firstName.getEditText().getText().toString());
            userVM.setLastName(lastName.getEditText().getText().toString());
            userVM.setPassword(password.getEditText().getText().toString());
            userVM.setMobile(mobile.getEditText().getText().toString());
            userVM.setPostalCode(postalCode.getEditText().getText().toString());
            userVM.setUsername(username.getEditText().getText().toString());
            userVM.setTel(tel.getEditText().getText().toString());
            if (CurrentUserHandler.IsLoggedIn()){
                userVM.setCustomerId(CurrentUserHandler.getCurrentUser().getCustomerId());
            }

            List<OrderItem> orderItemList = new ArrayList<>();

            dataList.forEach(y -> {
                OrderItem item = new OrderItem();
                if (y.getColor() != null) {
                    item.setColorId(y.getColor().getId());
                }
                if (y.getSize() != null) {
                    item.setSizeId(y.getSize().getId());
                }
                item.setProductId(y.getProduct().getId());
                item.setCount(y.getQuantity());
                orderItemList.add(item);
            });

            Payment payment = new Payment();
            payment.setCustomer(userVM);
            payment.setPaymentType(PaymentType.ZarinPal);
            payment.setOrderItems(orderItemList);

            PaymentService.addPayment(new Callback<ServiceResponse<StartPaymentVM>>() {
                @Override
                public void onResponse(Call<ServiceResponse<StartPaymentVM>> call, Response<ServiceResponse<StartPaymentVM>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (!response.body().isHasError()) {
                            StartPaymentVM data = response.body().getDataList().get(0);
                            if (data.getLocation() != null && !data.getLocation().equals("")) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.getLocation()));
                                startActivity(browserIntent);
                                CardDBHandler dbHandler = new CardDBHandler(PaymentActivity.this);
                                dbHandler.deleteAllBasket();
                                finish();
                            }
                       }else if (response.body().isHasError()){
                            Snackbar.make(mainView, response.body().getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                            return;
                        }
                    }
                }

                @Override
                public void onFailure(Call<ServiceResponse<StartPaymentVM>> call, Throwable t) {
                    Toast.makeText(PaymentActivity.this, "Error on payment method", Toast.LENGTH_SHORT).show();
                }
            }, payment);
        });
    }

    private void bindViews() {
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        tel = findViewById(R.id.tel);
        address = findViewById(R.id.address);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        postalCode = findViewById(R.id.postal);
        btnPayment = findViewById(R.id.btn_payment);
        progressBar = findViewById(R.id.progress_bar);
        mainView = findViewById(R.id.payment_detail_view);
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
        if (!CurrentUserHandler.IsLoggedIn() && password.getEditText().getText().toString().equals(""))
            return false;
        if (postalCode.getEditText().getText().toString().equals(""))
            return false;
        if (username.getEditText().getText().toString().equals(""))
            return false;
        if (tel.getEditText().getText().toString().equals(""))
            return false;
        return true;
    }

    private void fillDataList() {
        dataList = new ArrayList<>();
        CardDBHandler dbHandler = new CardDBHandler(this);
        dataList = dbHandler.getAllData();
    }

    private void fillUserData() {
        if (CurrentUserHandler.IsLoggedIn()) {
            User user = CurrentUserHandler.getCurrentUser();
            firstName.getEditText().setText(user.getFullName());
            lastName.getEditText().setText(user.getLastName());
            username.getEditText().setText(user.getFullName());
            email.getEditText().setText(user.getEmail());
            password.setVisibility(View.GONE);
            if (user.getCustomer() != null) {
                tel.getEditText().setText(user.getCustomer().getTel());
                address.getEditText().setText(user.getCustomer().getAddress());
                mobile.getEditText().setText(user.getCustomer().getMobile());
                postalCode.getEditText().setText(user.getCustomer().getPostalCode());
            }
        }
    }

}