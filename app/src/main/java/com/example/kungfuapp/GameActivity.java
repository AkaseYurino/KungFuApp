package com.example.kungfuapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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

    public static Intent newIntent(Context context, int gameLevel) {
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtra(KEY_GAME_LEVEL, gameLevel);
        return intent ;
    };

    private TextView resultTimeText;
    private ImageView shampooImageView;
    private View actionPunch;
    private View actionKick;
    private CountDownTimer timer;
    private FrameLayout oniTapBtn;
    private int count = 0;
    private int goalCount = 0;
    private int remainingCount;
    private int gameLevel;
    private AnimatorSet punchAnimatorSet;
    private AnimatorSet kickAnimatorSet;

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
        gameLevel = getIntent().getIntExtra(KEY_GAME_LEVEL, -1);

        //ゲームレベルに応じてカウントの値を変更する
        if(gameLevel == 1){
            goalCount = 10;
        }else if(gameLevel == 2){
            goalCount = 20;
        }else if(gameLevel == 3){
            goalCount = 30;
        }else if(gameLevel == 4){
            goalCount = 40;
        }else if(gameLevel == 5){
            goalCount = 50;
        }

        //レイアウトのIDと紐づける
        resultTimeText = findViewById(R.id.result_time_text);
        shampooImageView = findViewById(R.id.shampoo);
        actionPunch = findViewById(R.id.action_punch);
        actionKick = findViewById(R.id.action_kick);
        oniTapBtn = findViewById(R.id.tap_button);

        //アニメーションの設定
        punchActionSetting();
        kickActionSetting();

        //鬼タップボタンをタップしたときの処理
        oniTapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //countに1プラスする処理
                count++;
            //アニメーションスタート
                if (count % 2 == 0){
                    kickAnimatorSet.start();
                    shampooImageView.setImageResource(R.drawable.shampoo2);
                } else{
                    punchAnimatorSet.start();
                    shampooImageView.setImageResource(R.drawable.shampoo3);
                }
            }
        });
    }

    //アニメーション処理(パンチ)
    private void punchActionSetting(){
        ObjectAnimator moveRight = ObjectAnimator.ofFloat(actionPunch, "translationX", -1000f, 0f);
        moveRight.setDuration(300);
        ObjectAnimator moveUp = ObjectAnimator.ofFloat(actionPunch, "translationY", 1000f, 0f);
        moveUp.setDuration(300);
        punchAnimatorSet = new AnimatorSet();
        punchAnimatorSet.playTogether(moveRight, moveUp);
        punchAnimatorSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                actionPunch.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                actionPunch.setVisibility(View.GONE);
            }
        });
    }

    //アニメーション処理(キック)
    private void kickActionSetting(){
        ObjectAnimator moveLeft = ObjectAnimator.ofFloat(actionKick, "translationX", 1000f, 0f);
        moveLeft.setDuration(200);
        ObjectAnimator moveUp = ObjectAnimator.ofFloat(actionKick, "translationY", 1000f, 0f);
        moveUp.setDuration(200);
        kickAnimatorSet = new AnimatorSet();
        kickAnimatorSet.playTogether(moveLeft, moveUp);
        kickAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                actionKick.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                actionKick.setVisibility(View.GONE);
            }
        });

    }

    //タイマーの設定
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
        timer = new CountDownTimer(10000, 1000){

            // 残り時間を表示する
            @Override
            public void onTick(long millisUntilFinished) {
                long second = millisUntilFinished/1000;
                resultTimeText.setText(getString(R.string.result_time, (int)second));
            }

            // カウントダウン終了時の処理
            @Override
            public void onFinish() {
                // タップボタンを反応しないようにしたい
                // null＝反応しなくなる
                oniTapBtn.setOnClickListener(null);

                //残りカウント数を計算
                remainingCount = goalCount - count;

                //結果判定処理
                ResultStatus resultStatus;
                if (remainingCount <= 0) {
                    resultStatus = ResultStatus.CLEAR;
                } else if (remainingCount > 0) {
                   resultStatus = getResultStatus(gameLevel, remainingCount);
                }else{
                    resultStatus = ResultStatus.FAILED;
                }

                //画面遷移
                Intent intent = new Intent(GameActivity.this, ClearActivity.class);
                intent.putExtra("status", resultStatus.name());
                startActivity(intent);
            }
        };
    }

    private ResultStatus getResultStatus(int gameLevel, int remainCount) {
        if (gameLevel == 1){
            if(remainCount <=5){
                return ResultStatus.NORMAL;
            }else{
                return ResultStatus.FAILED;
            }
        }if (gameLevel == 2){
            if(remainCount <=10){
                return ResultStatus.NORMAL;
            }else{
                return ResultStatus.FAILED;
            }
        }if (gameLevel == 3){
            if(remainCount <=15){
                return ResultStatus.NORMAL;
            }else{
                return ResultStatus.FAILED;
            }
        }if (gameLevel == 4){
            if(remainCount <=20){
                return ResultStatus.NORMAL;
            }else{
                return ResultStatus.FAILED;
            }
        }if (gameLevel == 5){
            if(remainCount <=25){
                return ResultStatus.NORMAL;
            }else{
                return ResultStatus.FAILED;
            }
        }else {
            return ResultStatus.FAILED;
        }
    }
}