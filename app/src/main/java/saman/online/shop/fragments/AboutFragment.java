package saman.online.shop.fragments;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saman.online.shop.R;
import saman.online.shop.adapters.BlogAdapter;
import saman.online.shop.enums.ApiAddress;
import saman.online.shop.models.Blog;
import saman.online.shop.models.Content;
import saman.online.shop.models.Product;
import saman.online.shop.models.base.ServiceResponse;
import saman.online.shop.services.BlogService;
import saman.online.shop.services.ContentService;

public class AboutFragment extends Fragment {

    private Activity activity;
    private ProgressBar progressBar;
    private TextView aboutContent, versionName;
    private ImageView image;

    public AboutFragment(Activity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fram_about, container, false);
        init(rootView);
        return rootView;
    }

    private void init(ViewGroup rootView) {
        bindView(rootView);

        ContentService.getAll(new Callback<ServiceResponse<Content>>() {
            @Override
            public void onResponse(Call<ServiceResponse<Content>> call, Response<ServiceResponse<Content>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isHasError()) {
                    List<Content> contentList = response.body().getDataList();
                    Optional<Content> aboutOptional = contentList.stream().filter(x->x.getKey() != null&& x.getKey().toLowerCase().equals("about")).findFirst();
                    if (aboutOptional.isPresent()){
                        String aboutValue = aboutOptional.get().getValue();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            aboutContent.setText(Html.fromHtml(aboutValue, Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            aboutContent.setText(Html.fromHtml(aboutValue));
                        }
                    }
                }
                progressBar.setVisibility(View.GONE);
                versionName.setVisibility(View.VISIBLE);
                aboutContent.setVisibility(View.VISIBLE);
            }


            @Override
            public void onFailure(Call<ServiceResponse<Content>> call, Throwable t) {

            }
        });

        try {
            PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(),0);
            versionName.setText("Version: " + packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Picasso.get().load(ApiAddress.baseHttpDomain + "/images/about.jpg")
                .placeholder(R.drawable.loading)
                .error(R.drawable.brokenimage)
                .into(image);
    }

    private void bindView(ViewGroup rootView) {
        progressBar = rootView.findViewById(R.id.progress_bar);
        aboutContent = rootView.findViewById(R.id.about_content);
        versionName = rootView.findViewById(R.id.version_name);
        image = rootView.findViewById(R.id.about_image);
    }

}
