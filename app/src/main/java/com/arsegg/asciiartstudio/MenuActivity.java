package com.arsegg.asciiartstudio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.buttonLocalImage) {
            Intent intent = new Intent(this, AsciiImageActivity.class);
            startActivity(intent);
        }
    }
}
