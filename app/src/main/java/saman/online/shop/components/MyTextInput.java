package saman.online.shop.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputLayout;

import saman.online.shop.R;


public class MyTextInput extends LinearLayout {

    private TextInputLayout inputLayout;
    private Context context;
    private AttributeSet attrs;

    public MyTextInput(Context context, AttributeSet attrs) {
        super(context);
        this.context = context;
        this.attrs = attrs;
        init();
    }

    private void init() {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.my_text_input, this);
        bindViews();
        setAttributes();
    }

    private void bindViews() {
        inputLayout = findViewById(R.id.my_input_text_layout);
    }

    private void setAttributes() {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTextInput);

        String hint = typedArray.getString(R.styleable.MyTextInput_hint);
        if (hint != null && !hint.equals(""))
            inputLayout.setHint(hint);


        String text = typedArray.getString(R.styleable.MyTextInput_text);
        if (text != null && !text.equals(""))
            inputLayout.getEditText().setText(text);

        typedArray.recycle();
    }

}