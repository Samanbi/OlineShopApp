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
import saman.online.shop.ProductActivity;
import saman.online.shop.R;
import saman.online.shop.adapters.BlogAdapter;
import saman.online.shop.adapters.ProductAdapter;
import saman.online.shop.models.Blog;
import saman.online.shop.models.Product;
import saman.online.shop.models.base.ServiceResponse;
import saman.online.shop.services.BlogService;
import saman.online.shop.services.ProductService;

public class BlogFragment extends Fragment {

    private Activity activity;
    private RecyclerView blogView;
    private ProgressBar progressBar;
    private List<Blog> dataList;
    private NestedScrollView mainScrollView;
    private int pageNumber = 0, pageSize = 10;


    public BlogFragment(Activity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fram_blog, container, false);
        init(rootView);
        return rootView;
    }

    private void init(ViewGroup rootView) {
        bindView(rootView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        blogView.setLayoutManager(layoutManager);

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

    private void bindView(ViewGroup rootView) {
        blogView = rootView.findViewById(R.id.blog_recycler_view);
        progressBar = rootView.findViewById(R.id.blog_progressbar);
        mainScrollView = rootView.findViewById(R.id.blog_scrollview);
    }

    private void fillDataList() {
        BlogService.getAllData(new Callback<ServiceResponse<Blog>>() {
            @Override
            public void onResponse(Call<ServiceResponse<Blog>> call, Response<ServiceResponse<Blog>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isHasError()) {
                    List<Blog> newDataList = response.body().getDataList();
                    dataList.addAll(newDataList);
                    blogView.setAdapter(new BlogAdapter(activity, dataList));
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse<Blog>> call, Throwable t) {
                Toast.makeText(activity, "Error on getting blog data from server", Toast.LENGTH_LONG).show();

            }
        }, pageNumber, pageSize);
    }
}
