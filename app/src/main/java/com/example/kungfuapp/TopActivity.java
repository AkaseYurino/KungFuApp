package com.example.kungfuapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class TopActivity extends AppCompatActivity {

    private FrameLayout startBtn;

    //ClearActivityから戻ってくる処理
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context,TopActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  setContentView(R.layout.activity_top);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        startBtn = findViewById(R.id.start_button);
        startBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //スタートボタンタップ処理
                //TODO：タップ数の生成

                //乱数生成
                Random random = new Random();

                //1～100の中のどれかを取得。NextInt(100)だと0～4になるのでプラス1する。
                int number = random.nextInt(5) + 1;

                /*レベル1：10タップ30秒
                  レベル2：20タップ30秒
                  レベル3：30タップ30秒
                  レベル4：40タップ30秒
                  レベル5：50タップ30秒
                 */

                int gameLevel = random.nextInt(5) + 1;
                Log.d("gameLevel", "gameLevel : " + gameLevel);

                //ゲーム画面に遷移
                Intent intent = GameActivity.newIntent(TopActivity.this, gameLevel);
                startActivity(intent);
            }
        });
    }
}