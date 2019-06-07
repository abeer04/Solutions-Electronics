package com.market.solutions_electronics;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class contactus extends AppCompatActivity implements View.OnClickListener {
    ImageView location;
    ImageView phone1;
    ImageView phone2;
    ImageView mail1;
    ImageView mail2;
    TextView address;
    TextView number1;
    TextView number2;
    TextView email1;
    TextView email2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);

        getSupportActionBar().setTitle("Contact Us");

        location=findViewById(R.id.contact_map);
        phone1=findViewById(R.id.call);
        phone2=findViewById(R.id.call_icon);
        mail1=findViewById(R.id.email_icon);
        mail2=findViewById(R.id.email2_icon);

        address=findViewById(R.id.contact_address);
        number1=findViewById(R.id.number);
        number2=findViewById(R.id.number2);
        email1=findViewById(R.id.email);
        email2=findViewById(R.id.email2);

        location.setOnClickListener(this);
        phone1.setOnClickListener(this);
        phone2.setOnClickListener(this);
        mail1.setOnClickListener(this);
        mail2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==location)
        {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q="+address.getText().toString());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);

        }
        else if(v==phone1)
        {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+number1.getText().toString()));
            startActivity(intent);
        }
        else if(v==phone2)
        {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+number2.getText().toString()));
            startActivity(intent);
        }
        else if(v==mail1)
        {
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{email1.getText().toString()});


            //need this to prompts email client only
            email.setType("message/rfc822");

            startActivity(Intent.createChooser(email, "Choose an Email client :"));

        }
        else if(v==mail2)
        {
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{email2.getText().toString()});

            //need this to prompts email client only
            email.setType("message/rfc822");

            startActivity(Intent.createChooser(email, "Choose an Email client :"));

        }
    }
}
