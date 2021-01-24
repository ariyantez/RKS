package com.example.uasrks_ariyankt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Toko extends AppCompatActivity {
    protected Cursor cursor;
    Button gbr1, gbr2;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        gbr1 = (Button) findViewById(R.id.hoodie1);
        gbr2 = (Button) findViewById(R.id.hoodie2);

        gbr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(Toko.this, Login.class);
                startActivity(inte);
            }
        });
        gbr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent inte = new Intent(Toko.this, Toko.class);
                startActivity(inte);
            }
        });
    }
}
