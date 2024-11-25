package com.example.kungfuapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ClearActivity extends AppCompatActivity {

    private static final String KEY_TAP_COUNT = "key_tap_count";

    public static Intent newIntent(Context context, int count){
        Intent intent = new Intent(context, ClearActivity.class);
        intent.putExtra(KEY_TAP_COUNT, count);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);
    }
}