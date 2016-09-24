package tw.org.iii.woolike;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by DUKE-KAO on 2016/9/24.
 */

public class TextActivity  extends AppCompatActivity{
    TextView urlTextViwe;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 設定這個頁面XML的layout名稱
        setContentView(R.layout.activity_text);

        //製作一個顯示回上一頁的按鈕
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //讀取Intent 內的資料、再轉成文章網址儲存成  url

        Bundle arges =this.getIntent().getExtras();
        String url = "http://disp.cc/b/" + arges.getString("bi") + "-"
                + arges.getString("ti");

        //讀取XML的TextView 設定文字 為url
        urlTextViwe = (TextView) findViewById(R.id.url_textview);
        urlTextViwe.setText(url);
    }
}
