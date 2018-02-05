package com.example.kojimadaiki.mybrowserapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private WebView myWebView;
    private EditText urlText;
    private static final String INITIAL_WEBSITE = "http://dotinstall.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myWebView = (WebView) findViewById(R.id.myWebView);
        urlText = (EditText) findViewById(R.id.urlText);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view,String url){
                getSupportActionBar().setSubtitle(view.getTitle());
                urlText.setText(url);
            }
        });
        myWebView.loadUrl(INITIAL_WEBSITE);
    }

    public void showWebSite(View view){
        String url = urlText.getText().toString().trim();
        if(!Patterns.WEB_URL.matcher(url).matches()){
            urlText.setError("Invalid Url");
        }else {
            if(!url.startsWith("http://") && !url.startsWith("https://")){
                url = "http://" + url;
            }
            myWebView.loadUrl(url);
        }
    }

    public void clearUrl(View view){
        urlText.setText("");
    }

    @Override
    public void onBackPressed(){
        if(myWebView.canGoBack()){
            myWebView.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(myWebView != null){
            myWebView.stopLoading();
            myWebView.setWebViewClient(null);
            myWebView.destroy();
        }

        myWebView = null;

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem forwardItem = (MenuItem) menu.findItem(R.id.action_forward);
        MenuItem backItem = (MenuItem) menu.findItem(R.id.action_back);

        forwardItem.setEnabled(myWebView.canGoForward());
        backItem.setEnabled(myWebView.canGoBack());

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        //if(id == R.id.action_reload){
          //  return true;
        //}

        switch (id){
            case R.id.action_reload:
                myWebView.reload();
                return true;
            case R.id.action_forward:
                myWebView.goForward();
                return true;
            case R.id.action_back:
                myWebView.goBack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }



    }

}
