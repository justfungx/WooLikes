package tw.org.iii.woolike;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;

/**
 * Created by user on 2016/9/26.
 */
public class BoardTextActivity extends AppCompatActivity {

    WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 設定這個頁面XML的layout名稱
        setContentView(R.layout.activity_boardtext);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        *****  網址格式的正確性?*****
        Bundle args = this.getIntent().getExtras();
        String url = "http://disp.cc/m/" + args.getString("bi") + "-" + args.getString("ti");
        // 取得XML中的WebView
        mWebView = (WebView) findViewById(R.id.webview);

        // WebView的設定選項
        WebSettings webSettings = mWebView.getSettings();
        // Enable Javascript
        webSettings.setJavaScriptEnabled(true);
        // Enable LocalStorage
        webSettings.setDomStorageEnabled(true);
        //webSettings.setAllowUniversalAccessFromFileURLs(true);

        // 加這行以避免跳出APP用瀏覽器開啟
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());

        // 載入網址
        mWebView.loadUrl(url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ( item.getItemId()){
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_out_left,R.anim.slide_in_right);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
