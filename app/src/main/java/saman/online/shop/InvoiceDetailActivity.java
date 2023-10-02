package saman.online.shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saman.online.shop.adapters.CardAdapter;
import saman.online.shop.handlers.CurrentUserHandler;
import saman.online.shop.models.CardItem;
import saman.online.shop.models.Invoice;
import saman.online.shop.models.base.ServiceResponse;
import saman.online.shop.services.InvoiceService;

public class InvoiceDetailActivity extends AppCompatActivity {

    private Invoice invoice;
    private ImageView image;
    private ProgressBar progressBar;
    private RecyclerView orderItemsView;
    private TextView status, number, invoiceDate, payedDate;
    private TextView totalCount, totalPrice;
    private long totalCountValue = 0;
    private long totalPriceValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_detail);
        init();
    }

    private void init() {
        bindViews();

        Invoice extraInvoice = (Invoice) getIntent().getExtras().get("invoice");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        orderItemsView.setLayoutManager(layoutManager);

        InvoiceService.get(new Callback<ServiceResponse<Invoice>>() {
            @Override
            public void onResponse(Call<ServiceResponse<Invoice>> call, Response<ServiceResponse<Invoice>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isHasError()) {
                    invoice = response.body().getDataList().get(0);
                    if (invoice.isPayed()) {
                        image.setImageDrawable(ContextCompat.getDrawable(InvoiceDetailActivity.this, R.drawable.ic_baseline_check_circle_grin_24));


                    } else {
                        image.setImageDrawable(ContextCompat.getDrawable(InvoiceDetailActivity.this, R.drawable.ic_baseline_remove_circle_red_24));

                    }
                    status.setText(invoice.isPayed() ? "Payed" : "Not Payed");
                    //status.setTextColor(getResources().getColor(invoice.isPayed() ? R.color.colorGreen : R.color.colorRed));
                    status.setTextColor(ContextCompat.getColor(InvoiceDetailActivity.this, invoice.isPayed() ? R.color.colorGreen : R.color.colorRed));
                    number.setText("No : " + String.valueOf(invoice.getId()));
                    invoiceDate.setText("Invoice Date : " + invoice.getInvoiceDateStr());
                    payedDate.setText("Payed Date : " + invoice.getPayedDateStr());


                    List<CardItem> dataList = new ArrayList<>();
                    if (invoice.getOrderItems() != null && invoice.getOrderItems().size() > 0) {
                        invoice.getOrderItems().forEach(x -> {
                            CardItem item = new CardItem();
                            item.setColor(x.getColor());
                            item.setSize(x.getSize());
                            item.setProduct(x.getProduct().convert());
                            item.setQuantity(x.getCount());
                            item.setId(x.getId());
                            dataList.add(item);
                            totalCountValue += item.getQuantity();
                            totalPriceValue += (item.getQuantity() * item.getProduct().getPrice());
                        });
                    }
                    orderItemsView.setAdapter(new CardAdapter(dataList,InvoiceDetailActivity.this));
                    progressBar.setVisibility(View.GONE);

                    totalPrice.setText(totalPriceValue + "$");
                    totalCount.setText(String.valueOf(totalCountValue));
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse<Invoice>> call, Throwable t) {
                Toast.makeText(InvoiceDetailActivity.this, "Error on getting data from server", Toast.LENGTH_SHORT).show();
            }
        }, extraInvoice.getId(), CurrentUserHandler.getCurrentUser().getToken());

    }

    private void bindViews() {
        image = findViewById(R.id.invoice_image);
        status = findViewById(R.id.invoice_status);
        number = findViewById(R.id.invoice_number);
        invoiceDate = findViewById(R.id.invoice_date);
        payedDate = findViewById(R.id.payed_date);
        progressBar = findViewById(R.id.progress_bar);
        orderItemsView = findViewById(R.id.item_recycler_view);
        totalCount = findViewById(R.id.invoice_total_count);
        totalPrice = findViewById(R.id.invoice_total_price);
    }
}