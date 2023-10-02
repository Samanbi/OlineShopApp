package saman.online.shop.adapters;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import saman.online.shop.ProductActivity;
import saman.online.shop.ProductDetailActivity;
import saman.online.shop.R;
import saman.online.shop.enums.ApiAddress;
import saman.online.shop.models.ProductCategory;

public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.ViewHolder> {

    private List<ProductCategory> dataList;
    private Activity activity;

    public ProductCategoryAdapter(Activity activity, List<ProductCategory> dataList) {
        this.dataList = dataList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.product_category_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductCategory category = dataList.get(position);

        TextView title = holder.title;
        ImageView image = holder.image;

        title.setText(category.getTitle());
        Picasso.get().load(ApiAddress.getFileUrl(category.getImage()))
                .placeholder(R.drawable.loading)
                .error(R.drawable.brokenimage)
                .into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productIntent = new Intent(activity, ProductActivity.class);
                productIntent.putExtra("category", category);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair(title, "category_name");
                pairs[1] = new Pair(image, "category_image");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, pairs);
                activity.startActivity(productIntent, options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.product_category_image);
            title = itemView.findViewById(R.id.product_category_title);
        }
    }
}