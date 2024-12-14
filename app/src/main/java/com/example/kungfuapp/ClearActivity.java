package com.example.kungfuapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ClearActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);

        Intent intent = getIntent();
        String statusString = intent.getStringExtra("status");

        if(statusString != null){
            ResultStatus status = ResultStatus.valueOf(statusString);
            Log.d("resultStatus", "status : " + status.name());

            String resultMsg;
            int resultImResId;

            switch (status) {
                case CLEAR:
                    resultMsg = "クリア！\nおめでとう★";
                    resultImResId = R.drawable.clear_clear2;
                    break;

                case NORMAL:
                    resultMsg = "あともう少し！\nもう1回！";
                    resultImResId = R.drawable.clear_clear1;
                    break;

                case FAILED:
                default:
                    resultMsg = "失敗。。。\n頑張ろう！";
                    resultImResId = R.drawable.clear_lose;
                    break;
            }
            //結果画面のTextViewを読み込む
            TextView resultTextView = findViewById(R.id.clear_text);
            resultTextView.setText(resultMsg);

            //結果画像のImageViewを読み込む
            ImageView resultImageView = findViewById(R.id.clear_image);
            resultImageView.setBackgroundResource(resultImResId);
        }

        findViewById(R.id.return_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = TopActivity.newIntent(ClearActivity.this);
                startActivity(intent);
            }
        });
    }
}