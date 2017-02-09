package com.sundxing.android.baseandroid.drawable;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sundxing.android.baseandroid.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sundxing on 17/1/18.
 */

public class VectorTestActivity extends AppCompatActivity {

   static Map<String, Integer> resMap = new HashMap<>();
    static {
        resMap.put("src_in", R.drawable.vector_hint_color);
        resMap.put("add", R.drawable.vector_hint_color_add);
        resMap.put("screen", R.drawable.vector_hint_color_screen);
        resMap.put("multiply", R.drawable.vector_hint_color_muti);
        resMap.put("src_over", R.drawable.vector_hint_color_over);
        resMap.put("src_atop", R.drawable.vector_hint_color_atop);
        resMap.put("原图", R.drawable.vector_hint);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Drawable drawableClickBlue = VectorDrawableCompat.create(getResources(), R.drawable.vector_hint_color, null);
        View textView = new ImageView(this);
        AppCompatImageView appCompatImageView = new AppCompatImageView(this);
        try {
            textView.setBackgroundDrawable(drawableClickBlue);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } catch (Exception e) {
            // 4.3 crash : android.content.res.Resources$NotFoundException
            // add 'vectorDrawables.useSupportLibrary = true' configures in build.gradle
            e.printStackTrace();
            try {
                textView.setBackgroundDrawable(drawableClickBlue);
            } catch (Exception e2) {
                e2.printStackTrace();
                appCompatImageView.setImageResource(R.drawable.ic_android_black_24dp);
            }
        }
        TextView tv = new TextView(this);
        tv.setText("Draw Left & Top & Bottom");
        // This drawable use new instance in different views.
        // tv.setCompoundDrawablesWithIntrinsicBounds(null, drawableClickBlue, null, null);
        tv.setCompoundDrawablesWithIntrinsicBounds(null,
                VectorDrawableCompat.create(getResources(), R.drawable.vector_hint_color, null), null,
                drawableClickBlue);
        tv.setClickable(true);

        TextView colorTv = new TextView(this);
        colorTv.setBackgroundColor(Color.parseColor("#7700cc00"));
        colorTv.setText("Tint Color : #7700cc00");
        colorTv.setPadding(20,20,20,20);

        ScrollView container = new ScrollView(this);
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        l.addView(colorTv);
        fill(l);
        l.addView(getLayoutInflater().inflate(R.layout.layout_png_tint, l, false));
        container.addView(l);
        setContentView(container);
    }

    private void fill(LinearLayout l) {
        for (Map.Entry<String, Integer> entry: resMap.entrySet()) {
            TextView tv = new TextView(this);
            tv.setText(entry.getKey());

            tv.setCompoundDrawablesWithIntrinsicBounds(VectorDrawableCompat.create(getResources(), entry.getValue(), null),
                    null, null, null);
            tv.setClickable(true);
            l.addView(tv);
        }

    }
}
