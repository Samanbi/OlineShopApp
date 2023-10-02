package saman.online.shop.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import saman.online.shop.R;
import saman.online.shop.enums.ApiAddress;
import saman.online.shop.models.CardItem;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private List<CardItem> dataList;
    private Activity activity;

    public CardAdapter(List<CardItem> dataList, Activity activity) {
        this.dataList = dataList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.card_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardItem cardItem = dataList.get(position);

        TextView name = holder.name;
        TextView price = holder.price;
        TextView quantity = holder.quantity;
        ImageView image = holder.image;


        String productTitle = cardItem.getProduct().getTitle();
        if (cardItem.getSize() != null){
            productTitle += " - " + cardItem.getSize().getTitle();
        }
        if (cardItem.getColor() != null){
            productTitle += " (" + cardItem.getColor().getName() + ")";
        }
        name.setText(productTitle);
        Picasso.get().load(ApiAddress.getFileUrl(cardItem.getProduct().getImage()))
                .placeholder(R.drawable.loading)
                .error(R.drawable.brokenimage)
                .into(holder.image);

        price.setText(cardItem.getProduct().getPrice() + "$");
        quantity.setText(String.valueOf(cardItem.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView name, price, quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.product_image);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            quantity = itemView.findViewById(R.id.product_quantity);
        }
    }
}
