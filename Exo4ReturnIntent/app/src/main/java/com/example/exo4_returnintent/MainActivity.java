package com.example.exo4_returnintent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected static final int REQUEST_CODE_SECOND_ACTIVITY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SECOND_ACTIVITY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SECOND_ACTIVITY){
            if (resultCode == RESULT_OK) {
                String resultRequest = data.getStringExtra(SecondActivity.INPUT_SECOND);
                ((TextView) findViewById(R.id.textMain)).setText(resultRequest);
            } else {
                Toast.makeText(getApplicationContext(), android.R.string.cancel, Toast.LENGTH_LONG).show();
            }
        }
    }
}
