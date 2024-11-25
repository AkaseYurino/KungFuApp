package com.example.kungfuapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameActivity extends AppCompatActivity {

    private static final String KEY_GAME_LEVEL = "key_game_level";

    private TextView resultTimeText;
    private ImageView shampooImageView;
    private CountDownTimer timer;
    private FrameLayout oniTapBtn;

    private int count = 0;

    public static Intent newIntent(Context context, int gameLevel) {
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtra(KEY_GAME_LEVEL, gameLevel);
        return intent ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.game), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //KEY_GAME_LEVELをキーにして値を受け取っている
        int gameLevel = getIntent().getIntExtra(KEY_GAME_LEVEL, -1);

        resultTimeText = findViewById(R.id.result_time_text);
        shampooImageView = findViewById(R.id.shampoo);
        oniTapBtn = findViewById(R.id.tap_button);

        //鬼タップボタンをタップしたときの処理
        oniTapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //countに1プラスする処理
                count++;

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //タイマー初期化処理
        initTimer();
        //タイマー開始処理
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //タイマーキャンセル
        timer.cancel();
        timer = null;
    }

    private void initTimer() {
        resultTimeText.setText(getString(R.string.result_time, 30));
        // タイマーの初期化
        timer = new CountDownTimer(30000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                // 残り時間を表示する
                long second = millisUntilFinished/1000;
                resultTimeText.setText(getString(R.string.result_time, (int)second));
            }

            @Override
            public void onFinish() {
                // カウントダウン終了時の処理
                // タップボタンを反応しないようにしたい
                oniTapBtn.setOnClickListener(null);
                // nullを押したら反応しなくなる
                // カウンターテキスト＝タップ数を取得し、結果画面に渡す＆結果画面に遷移する
                // 画面遷移＆値(変数名count)を渡してあげる
                Intent intent = ClearActivity
                        .newIntent(GameActivity.this, count);
                // startActivityはAppCompatActivityが持っているメソッド
                startActivity(intent);

            }
        };
    }

}