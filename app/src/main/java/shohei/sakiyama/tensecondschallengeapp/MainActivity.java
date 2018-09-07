package shohei.sakiyama.tensecondschallengeapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    private Timer mTimer;
    private TextView mTimerText;
    private TextView mGuideText;
    private TextView mResultText;
    private double mTimerSec = 0.0;
    //private AdView mAdView;

    private Handler mHandler = new Handler();

    private Button mChallengeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // AdMobの初期化
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



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
                    //mTimerText.setText("計測中...");
                    mGuideText.setText("１０秒経ったと思ったらストップ！");
                    mTimer = new Timer();
                    mTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {


                            mTimerSec += 0.1;

                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    // 上限は３０秒に設定
                                    if (mTimerSec > 30.0) {
                                        mTimer.cancel();
                                        mTimer = null;
                                        mTimerText.setText(String.format("%.1f", mTimerSec));
                                        setRestart();
                                    }else if(mTimerSec < 0.8){
                                        // 0.8秒までは秒数を表示
                                        mTimerText.setText(String.format("%.1f", mTimerSec));
                                    }else{
                                        mTimerText.setText("計測中...");
                                    }

                                }
                            });
                        }
                    }, 100, 100);
                } else {
                    // ストップが押された時
                    mTimer.cancel();
                    mTimer = null;
                    mTimerText.setText(String.format("%.1f", mTimerSec));
                    setRestart();

                }
            }
        });


//        AdView adView = findViewById(R.id.adView);
//
//        adView.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                Log.d("Debug", "onAdLoaded()");
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                Log.d("Debug", "onAdFailedToLoad() errorCode=" + errorCode);
//            }
//
//            @Override
//            public void onAdOpened() {
//                Log.d("Debug", "onAdOpened()");
//            }
//
//            @Override
//            public void onAdLeftApplication() {
//                Log.d("Debug", "onAdLeftApplication()");
//            }
//
//            @Override
//            public void onAdClosed() {
//                Log.d("Debug", "onAdClosed()");
//            }
//        });


    }

    private void setRestart() {
        // 10秒ちょうどだったらおめでとう
        if (mTimerSec == 10.0) {
            mResultText.setText("すごい！");
        } else if (9.5 <= mTimerSec && mTimerSec <= 10.5 && mTimerSec!=10.0){
            mResultText.setText("おしい！");
        }else{
            mResultText.setText("ざんねん...");

        }
        mResultText.setVisibility(View.VISIBLE);

        mGuideText.setText("心の中で１０秒数えてね");
        mChallengeButton.setText("スタート！");
    }
}
