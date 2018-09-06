package shohei.sakiyama.tensecondschallengeapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private AdView mAdView;

    private Handler mHandler = new Handler();

    private Button mChallengeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // AdMobの初期化
        MobileAds.initialize(this, "ca-app-pub-7517861216605994~5209715764");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-7517861216605994/8603548759");
        adView.setVisibility(View.VISIBLE);

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

//        mAdView.setAdListener(new AdListener(){
//            @Override
//            public void onAdLoaded() {
//                // Code to be executed when an ad finishes loading.
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                // Code to be executed when an ad request fails.
//            }
//
//            @Override
//            public void onAdOpened() {
//                // Code to be executed when an ad opens an overlay that
//                // covers the screen.
//            }
//
//            @Override
//            public void onAdLeftApplication() {
//                // Code to be executed when the user has left the app.
//            }
//
//            @Override
//            public void onAdClosed() {
//                // Code to be executed when when the user is about to return
//                // to the app after tapping on an ad.
//            }
//        });


    }
}
