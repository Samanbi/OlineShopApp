package saman.online.shop.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saman.online.shop.R;
import saman.online.shop.adapters.ProductAdapter;
import saman.online.shop.adapters.ProductCategoryAdapter;

import saman.online.shop.models.Product;
import saman.online.shop.models.ProductCategory;
import saman.online.shop.models.base.ServiceResponse;
import saman.online.shop.services.ProductCategoryService;
import saman.online.shop.services.ProductService;

public class ProductFragment extends Fragment {

    private Activity activity;
    private RecyclerView categoryRecyclerView, filteredRecyclerView;
    private Chip newChip, cheapChip, popularChop, expensiveChip;
    private TextView filterText;


    public ProductFragment(Activity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fram_product, container, false);
        init(rootView);
        return rootView;
    }

    private void init(ViewGroup root) {
        bindViews(root);
        fillProductCategoryData();

        GridLayoutManager newProductLayoutManager = new GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false);
        filteredRecyclerView.setLayoutManager(newProductLayoutManager);
        fillNewProductData();

        newChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                 fillNewProductData();
                }
            }
        });


        popularChop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                 fillPopularProductData();
                }
            }
        });

        cheapChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){

                }
            }
        });

        expensiveChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){

                }
            }
        });

    }

    private void bindViews(ViewGroup root) {
        categoryRecyclerView = root.findViewById(R.id.category_list);
        filteredRecyclerView = root.findViewById(R.id.filtered_products_list);
        newChip = root.findViewById(R.id.new_chip);
        cheapChip = root.findViewById(R.id.cheap_chip);
        popularChop = root.findViewById(R.id.popular_chip);
        expensiveChip = root.findViewById(R.id.expansive_chip);
        filterText = root.findViewById(R.id.filtered_text);
    }

    private void fillProductCategoryData(){
        ProductCategoryService.getAll(new Callback<ServiceResponse<ProductCategory>>() {
            @Override
            public void onResponse(Call<ServiceResponse<ProductCategory>> call, Response<ServiceResponse<ProductCategory>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isHasError()) {
                    List<ProductCategory> dataList = response.body().getDataList();
                    categoryRecyclerView.setAdapter(new ProductCategoryAdapter(activity, dataList));
                    LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
                    categoryRecyclerView.setLayoutManager(layoutManager);
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse<ProductCategory>> call, Throwable t) {
                Toast.makeText(activity, "Error on getting Product Category Data from service", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fillNewProductData() {
        ProductService.getNew(new Callback<ServiceResponse<Product>>() {
            @Override
            public void onResponse(Call<ServiceResponse<Product>> call, Response<ServiceResponse<Product>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isHasError()) {
                    List<Product> dataList = response.body().getDataList();
                    filteredRecyclerView.setAdapter(new ProductAdapter(activity,dataList));
                    filterText.setText(getResources().getText(R.string.new_products));
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse<Product>> call, Throwable t) {
                Toast.makeText(activity, "Error on getting Product Category Data from service", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fillPopularProductData() {
        ProductService.getPopular(new Callback<ServiceResponse<Product>>() {
            @Override
            public void onResponse(Call<ServiceResponse<Product>> call, Response<ServiceResponse<Product>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isHasError()) {
                    List<Product> dataList = response.body().getDataList();
                    filteredRecyclerView.setAdapter(new ProductAdapter(activity,dataList));
                    filterText.setText(getResources().getText(R.string.popular_products));
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse<Product>> call, Throwable t) {
                Toast.makeText(activity, "Error on getting Product Category Data from service", Toast.LENGTH_LONG).show();
            }
        });
    }

}
