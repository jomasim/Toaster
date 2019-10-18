package com.devloop.toaster;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.devloop.toaster.Views.CustomView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Drawing extends AppCompatActivity {
    CustomView customView;
    @BindView(R.id.container)
    LinearLayout container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawing_layout);
        ButterKnife.bind(this);
        customView = new CustomView(this);
        container.addView(customView);

    }
}
