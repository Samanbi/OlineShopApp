package saman.online.shop.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saman.online.shop.R;
import saman.online.shop.adapters.InvoiceAdapter;
import saman.online.shop.handlers.CurrentUserHandler;
import saman.online.shop.models.Invoice;
import saman.online.shop.models.base.ServiceResponse;
import saman.online.shop.services.InvoiceService;

public class InvoicesFragment extends Fragment {

    private Activity activity;
    private RecyclerView mainRecyclerView;
    private List<Invoice> dataList;
    private ProgressBar progressBar;
    private NestedScrollView mainScrollView;
    private int pageNumber = 0, pageSize = 10;

    public InvoicesFragment(Activity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fram_invoices, container, false);
        init(rootView);
        return rootView;
    }

    private void init(ViewGroup rootView) {
        bindViews(rootView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        mainRecyclerView.setLayoutManager(layoutManager);

        dataList = new ArrayList<>();
        fillDataList();

        mainScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    progressBar.setVisibility(View.VISIBLE);
                    pageNumber++;
                    fillDataList();
                }
            }
        });
    }

    private void bindViews(ViewGroup rootView) {
        mainRecyclerView = rootView.findViewById(R.id.invoices_recycler_view);
        progressBar = rootView.findViewById(R.id.progress_bar);
        mainScrollView = rootView.findViewById(R.id.invoices_scrollview);
    }


    private void fillDataList() {
        InvoiceService.findByCustomerId(new Callback<ServiceResponse<Invoice>>() {
            @Override
            public void onResponse(Call<ServiceResponse<Invoice>> call, Response<ServiceResponse<Invoice>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isHasError()) {
                    List<Invoice> newDataList = response.body().getDataList();
                    dataList.addAll(newDataList);
                    mainRecyclerView.setAdapter(new InvoiceAdapter(activity, dataList));
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse<Invoice>> call, Throwable t) {
                Toast.makeText(activity, "Error on getting invoices data from server", Toast.LENGTH_LONG).show();

            }
        }, CurrentUserHandler.getCurrentUser().getCustomerId(), pageNumber, pageSize, CurrentUserHandler.getCurrentUser().getToken());
    }

}