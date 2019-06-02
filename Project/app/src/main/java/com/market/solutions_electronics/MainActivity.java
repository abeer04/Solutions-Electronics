package com.market.solutions_electronics;

import android.content.Intent;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView product;
     TextView text1;

    FirebaseFirestore db;
    // SliderLayout sliderShow;
    private SliderLayout mDemoSlider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();
        product=findViewById(R.id.products);
        product.setOnClickListener(this);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPage);
        ImageAdapter adapterView = new ImageAdapter(this);
        mViewPager.setAdapter(adapterView);
        //inflateImageSlider();
/*        sliderShow = findViewById(R.id.slider);
        ArrayList<String> sliderImages = new ArrayList<>();
        sliderImages.add("https://images.pexels.com/photos/257360/pexels-photo-257360.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        sliderImages.add("https://images.pexels.com/photos/257360/pexels-photo-257360.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        sliderImages.add("https://images.pexels.com/photos/257360/pexels-photo-257360.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        sliderImages.add("https://images.pexels.com/photos/257360/pexels-photo-257360.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        for (String s : sliderImages) {
            DefaultSliderView sliderView = new DefaultSliderView(this);
            sliderView.image(s);
            sliderShow.addSlider(sliderView);
        }
        */


    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.products) {

            Intent intent = new Intent(MainActivity.this, products.class);
            startActivity(intent);
        }



    }
    private void inflateImageSlider() {

        // Using Image Slider -----------------------------------------------------------------------
        //sliderShow = findViewById(R.id.slider);

        //populating Image slider
        ArrayList<String> sliderImages = new ArrayList<>();
        sliderImages.add("https://images.pexels.com/photos/257360/pexels-photo-257360.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        sliderImages.add("https://images.pexels.com/photos/257360/pexels-photo-257360.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        sliderImages.add("https://images.pexels.com/photos/257360/pexels-photo-257360.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        sliderImages.add("https://images.pexels.com/photos/257360/pexels-photo-257360.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        //sliderImages.add("https://firebasestorage.googleapis.com/v0/b/solutions-electronics.appspot.com/o/image3.png?alt=media&token=78d17307-eee7-4f7b-bce9-b3d133dc3b8b");
        //sliderImages.add("https://firebasestorage.googleapis.com/v0/b/solutions-electronics.appspot.com/o/image4.png?alt=media&token=89d4605b-75b4-4d73-8bcd-8c94c14b270a");
        //sliderImages.add("https://firebasestorage.googleapis.com/v0/b/solutions-electronics.appspot.com/o/image5.png?alt=media&token=26fb43a9-1a0d-431a-81c1-9a9c8480e812");
       /*
        DefaultSliderView sliderView = new DefaultSliderView(this);
        sliderView.image("https://images.pexels.com/photos/257360/pexels-photo-257360.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        sliderShow.addSlider(sliderView);*/

        for (String s : sliderImages) {
            DefaultSliderView sliderView = new DefaultSliderView(this);
            sliderView.image(s);
           // sliderShow.addSlider(sliderView);
        }




        //sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);


    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.mainmenu, menu);


        return true;

    }

}


