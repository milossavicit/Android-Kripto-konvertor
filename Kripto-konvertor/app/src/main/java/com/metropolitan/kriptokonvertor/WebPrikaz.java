package com.metropolitan.kriptokonvertor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebPrikaz extends AppCompatActivity {

    public WebView mWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        //Uključuje back dugme
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_prikaz);
        mWebView = findViewById(R.id.webview1);
        mWebView.loadUrl("https://coins.live");

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient());

    }



}
