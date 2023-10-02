package saman.online.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;


import saman.online.shop.enums.ApiAddress;
import saman.online.shop.handlers.CardDBHandler;
import saman.online.shop.models.CardItem;
import saman.online.shop.models.Color;
import saman.online.shop.models.Product;
import saman.online.shop.models.Size;

public class ProductDetailActivity extends AppCompatActivity {

    private Product product;
    private TextView price, name, description;
    private Button btnAddToCard;
    private ImageView image;
    private ChipGroup sizeChips, colorChips;
    private LinearLayout mainView;

    private Size selectedSize = null;
    private Color selectedColor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        init();
    }


    private void init() {
        bindViews();

        product = (Product) getIntent().getExtras().get("product");
        price.setText(product.getPrice() + "$");
        name.setText(product.getTitle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            description.setText(Html.fromHtml(product.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            description.setText(Html.fromHtml(product.getDescription()));
        }
        Picasso.get().load(ApiAddress.getFileUrl(product.getImage()))
                .placeholder(R.drawable.loading)
                .error(R.drawable.brokenimage)
                .into(image);


        for (final Size size : product.getSizesList()) {
            Chip chip = new Chip(this);
            chip.setText(size.getTitle());
            chip.setCheckable(true);
            chip.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                if (isChecked) {
                    selectedSize = size;
                } else {
                    selectedSize = null;
                }
            });
            sizeChips.addView(chip);
        }

        for (final Color color : product.getColorsList()) {
            Chip chip = new Chip(this);
            chip.setText(color.getName());
            chip.setCheckable(true);
            chip.setTextColor(android.graphics.Color.parseColor(color.getValue()));
            chip.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                if (isChecked) {
                    selectedColor = color;
                } else {
                    selectedColor = null;
                }
            });
            colorChips.addView(chip);
        }

        btnAddToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.getSizesList().size() > 0 && selectedSize == null) {
                    Snackbar.make(mainView, "Please select a size", BaseTransientBottomBar.LENGTH_LONG).show();
                    return;
                }
                if (product.getColorsList().size() > 0 && selectedColor == null) {
                    Snackbar.make(mainView, "Please select a color", BaseTransientBottomBar.LENGTH_LONG).show();
                    return;
                }
                try {
                    CardDBHandler dbHandler = new CardDBHandler(ProductDetailActivity.this);
                    CardItem cardItem = new CardItem();
                    cardItem.setProduct(product);
                    cardItem.setSize(selectedSize);
                    cardItem.setColor(selectedColor);
                    cardItem.setQuantity(1);
                    dbHandler.addToBasket(cardItem);
                } catch (Exception e) {
                    Toast.makeText(ProductDetailActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                finish();
                ProductDetailActivity.this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

            }
        });
    }


    private void bindViews() {
        mainView = findViewById(R.id.product_detail_view);
        price = findViewById(R.id.product_price);
        name = findViewById(R.id.product_name);
        image = findViewById(R.id.product_image);
        description = findViewById(R.id.product_description);
        sizeChips = findViewById(R.id.chip_product_sizes);
        colorChips = findViewById(R.id.chip_product_colors);
        btnAddToCard = findViewById(R.id.btn_add_to_card);
    }

}
