package com.ugo1995.SecretlyStoriesView;


import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.widget.Toast;


public class MainActivity extends AppCompatActivity {



    public WebView wv;
    public SwipeRefreshLayout swipe;


    @Override
    public void onBackPressed() {
        if(wv.canGoBack()) {
            wv.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipe = (SwipeRefreshLayout)  findViewById(R.id.swipel);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                wv.reload();
            }
        });
        wv = (WebView)findViewById(R.id.webv) ;

        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        wv.setWebChromeClient(new WebChromeClient());
        wv.setWebViewClient(new WebViewClient() {


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String host = Uri.parse(request.getUrl().toString()).getHost();
                if(host.equals("m.facebook.com") || host.equals("www.facebook.com")) {
                    //Toast.makeText(MainActivity.this, "Il login con Facebook non è al momento disponibile. Eseguire il login con le credenziali di Instagram.", Toast.LENGTH_LONG).show();
                    Toast.makeText(MainActivity.this, "Login with Facebook is not available at the moment. Log in with your Instagram credentials.", Toast.LENGTH_LONG).show();
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                swipe.setRefreshing(false);
                super.onPageFinished(view, url);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                //Log.d("RICHIESTA",request.getUrl().toString());
                if(request.getUrl().toString().equals("https://www.instagram.com/stories/reel/seen"))
                {
                    Log.d("RICHIESTA","CANCELLATA");
                    return new WebResourceResponse(null,null,null);
                }
                return super.shouldInterceptRequest(view, request);
            }
        });
        wv.loadUrl("https://www.instagram.com/");
        swipe.setRefreshing(true);

    }

}
