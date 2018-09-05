package shohei.sakiyama.tensecondschallengeapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Timer mTimer;
    TextView mTimerText;
    TextView mGuideText;
    TextView mResultText;
    double mTimerSec = 0.0;


    Handler mHandler = new Handler();

    Button mChallengeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimerText = (TextView) findViewById(R.id.timer);
        mResultText = (TextView) findViewById(R.id.result);
        mGuideText = (TextView) findViewById(R.id.guide_textView);
        mChallengeButton = (Button) findViewById(R.id.challenge_button);


        mResultText.setVisibility(View.INVISIBLE);

        mChallengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mTimer == null) {
                    // スタートが押された時
                    mTimerSec = 0.0;
                    mChallengeButton.setText("ストップ！");
                    mResultText.setVisibility(View.INVISIBLE);
                    mTimerText.setText("計測中...");
                    mGuideText.setText("１０秒経ったと思ったらストップ！");
                    mTimer = new Timer();
                    mTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            mTimerSec += 0.1;

                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                        }
                    }, 100, 100);
                }else{
                    // ストップが押された時
                    mTimer.cancel();
                    mTimer = null;
                    mTimerText.setText(String.format("%.1f",mTimerSec));


                    // 10秒ちょうどだったらおめでとう
                    if( String.format("%.1f",mTimerSec).equals("10.0")){
                        mResultText.setText("すごい！　おめでとう！");



                    }else{
                        mResultText.setText("ざんねん...");

                    }
                    mResultText.setVisibility(View.VISIBLE);

                    mGuideText.setText("心の中で１０秒数えてね");
                    mChallengeButton.setText("スタート！");

                }
            }
        });


    }
}
