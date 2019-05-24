package com.market.solutions_electronics;

import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button get;
     TextView text1;
     FirebaseDatabase fire;
     DatabaseReference data;
     SliderLayout sliderShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//       fire =FirebaseDatabase.getInstance();
        setContentView(R.layout.activity_main);
        //get=findViewById(R.id.get);
        //text1=findViewById(R.id.text1);
//        get.setOnClickListener(this);

       // data=fire.getReferenceFromUrl("https://solutions-electronics.firebaseio.com/");
        inflateImageSlider();

    }

    @Override
    public void onClick(View view) {
/*
        if(view.getId() == R.id.get){
            final DatabaseReference myRef = data.child("dd");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    if(value==null){
                        text1.setText("Error Not Found");
                    }
                    else{

                        text1.setText(value);
                    }


                    data.child("dd").setValue("abc");
                    //DatabaseReference myRef2 = data.child("abeer").push();
                   // myRef2.setValue("def","name");

                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
*/
    }
    private void inflateImageSlider() {

        // Using Image Slider -----------------------------------------------------------------------
        sliderShow = findViewById(R.id.slider);

        //populating Image slider
        ArrayList<String> sliderImages = new ArrayList<>();
        sliderImages.add("http://www.amityelectronics.in/images/banner4.jpg");
        sliderImages.add("https://cmassets.akamaized.net/global/upload/home/2-mc500p.png");
        sliderImages.add("https://gloimg.gbtcdn.com/soa/gb/pdm-product-pic/Electronic/2018/07/05/source-img/20180705190154_31078.jpg");
        sliderImages.add("https://cf2.s3.souqcdn.com/item/2016/10/31/11/78/34/36/item_XL_11783436_17185976.jpg");

        for (String s : sliderImages) {
            DefaultSliderView sliderView = new DefaultSliderView(this);
            sliderView.image(s);
            sliderShow.addSlider(sliderView);
        }

        sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.mainmenu, menu);


        return true;

    }

}


