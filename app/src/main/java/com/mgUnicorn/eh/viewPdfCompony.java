package com.mgUnicorn.eh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URLEncoder;

public class viewPdfCompony extends AppCompatActivity {


    WebView pdfview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf_compony);
        pdfview=(WebView) findViewById(R.id.pdfView);

        pdfview.getSettings().setJavaScriptEnabled(true);

        String fileUrl= getIntent().getStringExtra("url");

        ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle("Url");
        pd.setMessage("Opening...!!");

        pdfview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pd.dismiss();
            }
        });

        String url="";
        try{

            url= URLEncoder.encode(fileUrl,"UTF-8");
            pdfview.loadUrl("http://docs.google.com/gview?embedded=true&url="+url);

        }catch (Exception e){

        }



    }
}