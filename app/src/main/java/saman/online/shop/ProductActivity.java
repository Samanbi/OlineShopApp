package saman.online.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saman.online.shop.adapters.ProductAdapter;
import saman.online.shop.enums.ApiAddress;
import saman.online.shop.models.Product;
import saman.online.shop.models.ProductCategory;
import saman.online.shop.models.base.ServiceResponse;
import saman.online.shop.services.ProductService;

public class ProductActivity extends AppCompatActivity {

    private ProductCategory category;
    private TextView name;
    private ImageView image;
    private RecyclerView productsRecyclerView;
    private int pageNumber = 0, pageSize = 2;
    private NestedScrollView mainScrollView;
    private ProgressBar progressBar;
    private List dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        init();

    }

    private void init() {
        bindViews();

        category = (ProductCategory) getIntent().getExtras().get("category");
        name.setText(category.getTitle());
        Picasso.get().load(ApiAddress.getFileUrl(category.getImage()))
                .placeholder(R.drawable.loading)
                .error(R.drawable.brokenimage)
                .into(image);

        dataList = new ArrayList<>();
        GridLayoutManager productListLayout = new GridLayoutManager(ProductActivity.this, 2, RecyclerView.VERTICAL, false);
        productsRecyclerView.setLayoutManager(productListLayout);

        fillProductsDataList();

        mainScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                    progressBar.setVisibility(View.VISIBLE);
                    pageNumber++;
                    fillProductsDataList();
                }
            }
        });

    }

    private void fillProductsDataList() {
        ProductService.getByCategory(new Callback<ServiceResponse<Product>>() {
            @Override
            public void onResponse(Call<ServiceResponse<Product>> call, Response<ServiceResponse<Product>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isHasError()) {
                    List<Product> newDataList = response.body().getDataList();
                    dataList.addAll(newDataList);
                    productsRecyclerView.setAdapter(new ProductAdapter(ProductActivity.this, dataList));
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse<Product>> call, Throwable t) {
                Toast.makeText(ProductActivity.this, "Error on getting Product Category Data from service", Toast.LENGTH_LONG).show();
            }
        }, category.getId(), pageNumber, pageSize);
    }

    private void bindViews() {
        name = findViewById(R.id.category_name);
        image = findViewById(R.id.category_image);
        productsRecyclerView = findViewById(R.id.products_recycler);
        mainScrollView = findViewById(R.id.product_scrollview);
        progressBar = findViewById(R.id.progress_bar);
    }
}