package saman.online.shop.fragments;

import android.app.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saman.online.shop.R;
import saman.online.shop.adapters.ProductAdapter;
import saman.online.shop.adapters.ProductCategoryAdapter;
import saman.online.shop.adapters.SliderAdapter;

import saman.online.shop.enums.ApiAddress;


import saman.online.shop.models.Product;
import saman.online.shop.models.ProductCategory;
import saman.online.shop.models.SliderItem;
import saman.online.shop.models.base.ServiceResponse;
import saman.online.shop.services.ProductCategoryService;
import saman.online.shop.services.ProductService;
import saman.online.shop.services.SliderService;

public class HomeFragment extends Fragment {

    private Activity activity;
    private RecyclerView mainRecyclerView;
    private RecyclerView newProductsRecyclerView;
    private RecyclerView popularProductsRecyclerView;
    private ImageSlider sliderView;

    public HomeFragment(Activity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fram_home, container, false);
        init(rootView);
        return rootView;
    }

    private void init(ViewGroup view) {
        bindViews(view);

        fillProductCategoryData();

        fillNewProductsData();

        fillPopularProductsData();

        fillSliderData();

    }

    private void fillSliderData() {
        final SliderAdapter sliderAdapter = new SliderAdapter(activity);

        SliderService.getAll(new Callback<ServiceResponse<SliderItem>>() {
            @Override
            public void onResponse(Call<ServiceResponse<SliderItem>> call, Response<ServiceResponse<SliderItem>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isHasError()) {
                    List<SliderItem> sliders = response.body().getDataList();
                    List<SlideModel> sliderModelList = new ArrayList<>();
                    sliders.forEach(x -> sliderModelList.add(new SlideModel(ApiAddress.getFileUrl(x.getImage()), x.getTitle(), ScaleTypes.FIT)));
                    //sliderAdapter.setItems(sliders);
                    sliderView.setImageList(sliderModelList);
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse<SliderItem>> call, Throwable t) {
                Toast.makeText(activity, "Error on getting Slider Data from service", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fillProductCategoryData() {
        ProductCategoryService.getAll(new Callback<ServiceResponse<ProductCategory>>() {
            @Override
            public void onResponse(Call<ServiceResponse<ProductCategory>> call, Response<ServiceResponse<ProductCategory>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isHasError()) {
                    List<ProductCategory> dataList = response.body().getDataList();
                    mainRecyclerView.setAdapter(new ProductCategoryAdapter(activity, dataList));
                    LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
                    mainRecyclerView.setLayoutManager(layoutManager);
                }

            }

            @Override
            public void onFailure(Call<ServiceResponse<ProductCategory>> call, Throwable t) {
                Toast.makeText(activity, "Error on getting product categories data from server", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fillNewProductsData() {
        ProductService.getNew(new Callback<ServiceResponse<Product>>() {
            @Override
            public void onResponse(Call<ServiceResponse<Product>> call, Response<ServiceResponse<Product>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isHasError()) {
                    List<Product> dataList = response.body().getDataList();
                    GridLayoutManager newProductsLayoutManager = new GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false);
                    newProductsRecyclerView.setAdapter(new ProductAdapter(activity, dataList));
                    newProductsRecyclerView.setLayoutManager(newProductsLayoutManager);
                }

            }

            @Override
            public void onFailure(Call<ServiceResponse<Product>> call, Throwable t) {
                Toast.makeText(activity, "Error on getting product categories data from server", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void fillPopularProductsData() {
        ProductService.getPopular(new Callback<ServiceResponse<Product>>() {
            @Override
            public void onResponse(Call<ServiceResponse<Product>> call, Response<ServiceResponse<Product>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isHasError()) {
                    List<Product> dataList = response.body().getDataList();
                    GridLayoutManager popularProductsLayoutManager = new GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false);
                    popularProductsRecyclerView.setAdapter(new ProductAdapter(activity, dataList));
                    popularProductsRecyclerView.setLayoutManager(popularProductsLayoutManager);
                }

            }

            @Override
            public void onFailure(Call<ServiceResponse<Product>> call, Throwable t) {
                Toast.makeText(activity, "Error on getting product categories data from server", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void bindViews(ViewGroup view) {
        mainRecyclerView = view.findViewById(R.id.main_recycler_view);
        newProductsRecyclerView = view.findViewById(R.id.new_products_recycler_view);
        popularProductsRecyclerView = view.findViewById(R.id.popular_products_recycler_view);
        sliderView = view.findViewById(R.id.imageSlider);
    }

}


