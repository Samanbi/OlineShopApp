package saman.online.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saman.online.shop.enums.ApiAddress;
import saman.online.shop.models.Blog;
import saman.online.shop.models.base.ServiceResponse;
import saman.online.shop.services.BlogService;


public class BlogDetailActivity extends AppCompatActivity {

    private Blog blog;
    private TextView title, subTitle, description, visit, date;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);
        init();
    }

    private void init() {
        bindViews();

        blog = (Blog) getIntent().getExtras().get("blog");
        title.setText(blog.getTitle());
        subTitle.setText(blog.getSubTitle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            description.setText(Html.fromHtml(blog.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            description.setText(Html.fromHtml(blog.getDescription()));
        }
        Picasso.get().load(ApiAddress.getFileUrl(blog.getImage()))
                .placeholder(R.drawable.loading)
                .error(R.drawable.brokenimage)
                .into(image);
        visit.setText(String.valueOf(blog.getVisitCount()));
        date.setText(blog.getPublishDate());

        BlogService.increaseVisitCount(new Callback<ServiceResponse<Blog>>() {
            @Override
            public void onResponse(Call<ServiceResponse<Blog>> call, Response<ServiceResponse<Blog>> response) {

            }

            @Override
            public void onFailure(Call<ServiceResponse<Blog>> call, Throwable t) {

            }
        },blog.getId());

    }

    private void bindViews() {
        title = findViewById(R.id.blog_title);
        subTitle = findViewById(R.id.blog_subtitle);
        image = findViewById(R.id.blog_image);
        description = findViewById(R.id.blog_description);
        visit = findViewById(R.id.blog_visit_count);
        date = findViewById(R.id.blog_publish_data);
    }
}