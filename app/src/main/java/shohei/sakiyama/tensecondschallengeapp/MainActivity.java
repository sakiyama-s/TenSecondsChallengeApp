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

        //MobileAds.initialize(this, getString(R.string.adAppId) );

        // AdMobの初期化
        //MobileAds.initialize(this, "ca-app-pub-7517861216605994~5209715764");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
//
//        AdView adView = new AdView(this);
//        adView.setAdSize(AdSize.BANNER);
//        adView.setAdUnitId(getString(R.string.adUnitId));


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
                                    // 上限は３０秒に設定
                                    if (mTimerSec > 30.0) {
                                        mTimer.cancel();
                                        mTimer = null;
                                        mTimerText.setText(String.format("%.1f", mTimerSec));
                                        setRestart();
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
        if (String.format("%.1f", mTimerSec).equals("10.0")) {
            mResultText.setText("すごい！　おめでとう！");
        } else {
            mResultText.setText("ざんねん...");

        }
        mResultText.setVisibility(View.VISIBLE);

        mGuideText.setText("心の中で１０秒数えてね");
        mChallengeButton.setText("スタート！");
    }
}
