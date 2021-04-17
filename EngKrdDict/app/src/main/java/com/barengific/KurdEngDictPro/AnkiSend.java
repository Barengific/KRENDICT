package com.barengific.KurdEngDictPro;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.text.Html;
import android.util.TypedValue;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
//import com.google.android.gms.ads.MobileAds;
//import com.google.android.gms.ads.initialization.InitializationStatus;
//import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;

public class AnkiSend extends AppCompatActivity {
    public static final String EXTRA_WORD = "com.barengific.KurdEngDict.EXTRA_WORD";
    public static final String EXTRA_MEANING = "com.barengific.KurdEngDict.EXTRA_MEANING";
    public static final String EXTRA_LANGUAGE = "com.barengific.KurdEngDict.EXTRA_LANGUAGE";

    private TextView ftxt;  ////////////////////////////////////////////////////
    private TextView btxt;
    private String meaning;
    public Button txtLarge;
    public Button txtSmall;
//    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anki_send);

//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
//        mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        ftxt = findViewById(R.id.ftxt);   //////////////////////////////////////////////
        btxt = findViewById(R.id.btxt);

        txtSmall = findViewById(R.id.txtSmall);
        txtLarge = findViewById(R.id.txtLarge);

        Intent intent = getIntent();
        meaning = intent.getStringExtra(EXTRA_MEANING);
        ftxt.setText(intent.getStringExtra(EXTRA_WORD)); ///////////////////////////////////////////////////
        btxt.setText(Html.fromHtml(meaning));

        txtLarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ftxt.setTextSize(TypedValue.COMPLEX_UNIT_PX,ftxt.getTextSize()+15);
                btxt.setTextSize(TypedValue.COMPLEX_UNIT_PX,btxt.getTextSize()+15);
            }
        });
        txtSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ftxt.setTextSize(TypedValue.COMPLEX_UNIT_PX,ftxt.getTextSize()-15);
                btxt.setTextSize(TypedValue.COMPLEX_UNIT_PX,btxt.getTextSize()-15);
            }
        });
    }
}
