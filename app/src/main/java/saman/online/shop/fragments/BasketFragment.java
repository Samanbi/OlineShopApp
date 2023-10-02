package saman.online.shop.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import saman.online.shop.PaymentActivity;
import saman.online.shop.R;
import saman.online.shop.adapters.CardAdapter;
import saman.online.shop.handlers.CardDBHandler;
import saman.online.shop.models.CardItem;

public class BasketFragment extends Fragment {

    private Activity activity;
    private RecyclerView basketView;
    private TextView totalCount, totalPrice, ProductQuantity;
    private LinearLayout summeryView, emptyView;
    private Button btnPayment;
    private Button btnDeleteProductQuantity, btnAddProductQuantity;

    private List<CardItem> dataList;
    private long totalCountValue = 0;
    private long totalPriceValue = 0;

    public BasketFragment(Activity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fram_basket, container, false);
        init(rootView);
        return rootView;
    }

    private void init(ViewGroup rootView) {
        bindView(rootView);
        fillDataList();

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        basketView.setLayoutManager(layoutManager);
        totalPrice.setText(totalPriceValue + "$");
        totalCount.setText(String.valueOf(totalCountValue));
        if (totalCountValue == 0) {
            summeryView.setVisibility(View.INVISIBLE);
            btnPayment.setVisibility(View.INVISIBLE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
        btnPayment.setOnClickListener(view -> {
            startActivity(new Intent(activity, PaymentActivity.class));
        });

    }

    private void bindView(ViewGroup rootView) {
        basketView = rootView.findViewById(R.id.basket_recycler_view);
        totalCount = rootView.findViewById(R.id.basket_total_count);
        totalPrice = rootView.findViewById(R.id.basket_total_price);
        summeryView = rootView.findViewById(R.id.basket_summery_view);
        emptyView = rootView.findViewById(R.id.basket_empty_view);
        btnPayment = rootView.findViewById(R.id.btn_payment);
        btnDeleteProductQuantity = rootView.findViewById(R.id.delete_product_quantity);
        ProductQuantity = rootView.findViewById(R.id.product_quantity);
        btnAddProductQuantity = rootView.findViewById(R.id.add_product_quantity);
    }

    private void fillDataList() {
        dataList = new ArrayList<>();
        CardDBHandler dbHandler = new CardDBHandler(activity);
        dataList = dbHandler.getAllData();

        dataList.stream().forEach(x -> {
            totalCountValue += x.getQuantity();
            totalPriceValue += (x.getQuantity() * x.getProduct().getPrice());
        });
        basketView.setAdapter(new CardAdapter(dataList, activity));
    }

    @Override
    public void onResume() {
        super.onResume();
        fillDataList();
    }
}
