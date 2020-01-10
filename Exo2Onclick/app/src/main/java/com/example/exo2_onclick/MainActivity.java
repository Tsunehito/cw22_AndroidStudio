package com.example.exo2_onclick;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.toast_btn, Toast.LENGTH_LONG).show();
            }
        });
    }

    protected void btnAction(View v){
        Toast.makeText(getApplicationContext(), R.string.toast_btn2, Toast.LENGTH_LONG).show();
    }
}
