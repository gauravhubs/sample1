package com.example.gauravkumar.sample1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by gaurav.kumar on 22/08/16.
 */
public class IrctcWebview extends Activity{
    private WebView webView;
    private DBHelper mydb;
    int id_To_Update = 0;
    Button cancleB;
    TextView statusB;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.irctc_booking_webview);
        mydb = new DBHelper(this);

        statusB = (TextView) findViewById(R.id.webview_status);
        statusB.setText("IRCTC Opening");

        cancleB = (Button) findViewById(R.id.webview_cancle_button);
        cancleB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });





        final Bundle extras = getIntent().getExtras();

        if (extras == null) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "INTENT PASS NOT WORKD",
                    Toast.LENGTH_SHORT);

            toast.show();

        }


        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setSupportMultipleWindows(false);
//        webView.getSettings().setSupportZoom(true);
//        webView.setVerticalScrollBarEnabled(true);
//        webView.setHorizontalScrollBarEnabled(true);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setLoadWithOverviewMode(true);


        int id_to_run = extras.getInt("id");
        WebViewClientImpl webViewClient = new WebViewClientImpl(this);
        webViewClient.setProfileId(id_to_run);
        webView.setWebViewClient(webViewClient);
        try {

            webView.loadUrl("https://www.irctc.co.in/eticketing/loginHome.jsf");

        } catch (Exception e) {
            e.printStackTrace();
        }
        //webView.addJavascriptInterface(new AppJavaScriptProxy(this, webView), "androidAppProxy");

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }




}
