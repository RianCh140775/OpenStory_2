package com.azhar.openstory.activities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.azhar.openstory.adapter.ItemGridAdapter;
import com.azhar.openstory.adapter.ProductAdapter;
import com.azhar.openstory.adapter.SliderImageAdapter;
import com.azhar.openstory.model.CategoryModel;
import com.azhar.openstory.model.ProductModel;
import com.azhar.openstory.util.Constant;
import com.project.openstory.R;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;



public class MainActivity extends AppCompatActivity {

//    Toolbar toolbar;
    SliderView sliderMyshop;
    TextView titleText;
    private LinearLayout llroot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sliderMyshop = findViewById(R.id.imageSlider);

//        toolbar.setTitle("");
//        setSupportActionBar(toolbar);
        llroot = findViewById(R.id.mainLinearLayout1);

        loadjson(llroot, "New Items", 0, 25);

            View card = getLayoutInflater().inflate(R.layout.item_card, null);
            RecyclerView rv = card.findViewById(R.id.cardListView1);
            rv.setNestedScrollingEnabled(false);
            TextView tv = card.findViewById(R.id.cardTextView1);
            List<CategoryModel> datacat = new ArrayList<CategoryModel>();
//            for (int ic : cats.keySet()) {
//                datacat.add(new CategoryModel(ic, ic, cats.get(ic), false));
//            }
            rv.addItemDecoration(new SimpleDividerItemDecoration(this));
            rv.setAdapter(new ItemGridAdapter(datacat));
            rv.setLayoutManager(new GridLayoutManager(this, 2));

            llroot.addView(card);


        final SliderImageAdapter sliderImageAdapter = new SliderImageAdapter(this);
        sliderImageAdapter.setCount(4);
        sliderMyshop.setSliderAdapter(sliderImageAdapter);
        sliderMyshop.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderMyshop.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderMyshop.setIndicatorSelectedColor(Color.WHITE);
        sliderMyshop.setIndicatorUnselectedColor(Color.GRAY);
        sliderMyshop.startAutoCycle();
        sliderMyshop.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderMyshop.setCurrentPagePosition(position);
            }
        });



    }

    private void loadjson(LinearLayout root, String title, int startpos, int endpos) {
        try {
            Resources res = getResources();
            InputStream in_s = res.openRawResource(R.raw.bldata);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            View v = getLayoutInflater().inflate(R.layout.product_horizontal, null);
            RecyclerView rv = v.findViewById(R.id.producthorizontalRecyclerView1);
            TextView tv = v.findViewById(R.id.producthorizontalTextView1);
            tv.setText(title);
            List<ProductModel> pdata = new ArrayList<ProductModel>();
            JSONArray jdata = new JSONObject(new String(b)).getJSONArray("products");
            for (int i = startpos; i < (endpos == 0 ? jdata.length() : endpos); i++) {
                JSONObject p = jdata.getJSONObject(i);
                String name = p.getString("name");
                long price = p.getLong("price");
//                long oprice = price - 1000;
                String store = p.getString("seller_name");
                String img = p.getJSONArray("images").getString(0);
//                float rating = Float.parseFloat(p.getJSONObject("rating").getString("average_rate") + "f");
                pdata.add(new ProductModel(name, store, price, img));
            }
            rv.setAdapter(new ProductAdapter(pdata, this));
            rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
            rv.setNestedScrollingEnabled(false);
            rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
            rv.scrollToPosition(rv.getAdapter().getItemCount() - 1);
            root.addView(v);
        } catch (Exception e) {
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        public SimpleDividerItemDecoration(Context context) {
            mDivider = context.getResources().getDrawable(R.drawable.line_divider);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    public static Drawable setTint(Drawable d, int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(d);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }
}
