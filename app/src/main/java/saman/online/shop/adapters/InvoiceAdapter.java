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


import java.util.List;

import saman.online.shop.InvoiceDetailActivity;
import saman.online.shop.R;
import saman.online.shop.models.Invoice;


public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.ViewHolder> {

    private List<Invoice> dataList;
    private Activity activity;

    public InvoiceAdapter(Activity activity, List<Invoice> dataList) {
        this.dataList = dataList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.invoice_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Invoice invoice = dataList.get(position);

        holder.number.setText(String.valueOf(invoice.getId()));
        holder.invoiceDate.setText("Create : " + invoice.getInvoiceDateStr());
        holder.payedDate.setText("Payed : " + invoice.getPayedDateStr());
        if (invoice.isPayed()) {
            holder.image.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_baseline_check_circle_grin_24));
        } else {
            holder.image.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_baseline_remove_circle_red_24));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent invoiceDetailIntent = new Intent(activity, InvoiceDetailActivity.class);
                invoiceDetailIntent.putExtra("invoice", invoice);
                Pair[] pairs = new Pair[4];
                pairs[0] = new Pair(holder.number, "invoice_number");
                pairs[1] = new Pair(holder.image, "invoice_image");
                pairs[2] = new Pair(holder.invoiceDate, "invoice_date");
                pairs[3] = new Pair(holder.payedDate, "payed_date");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, pairs);
                activity.startActivity(invoiceDetailIntent, options.toBundle());
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView number;
        public TextView invoiceDate;
        public TextView payedDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.status_image);
            number = itemView.findViewById(R.id.invoice_number);
            invoiceDate = itemView.findViewById(R.id.invoice_date);
            payedDate = itemView.findViewById(R.id.payed_date);
        }
    }
}
