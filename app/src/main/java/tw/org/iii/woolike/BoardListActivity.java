package tw.org.iii.woolike;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static android.R.attr.type;

/**
 * Created by DUKE-KAO on 2016/9/25.
 */

public class BoardListActivity extends AppCompatActivity implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
    private Button btnHotText;
    private Button btnBoardList;

    private ListView boardListView;
    private BoardListAdapter boardListAdapter;

    private ProgressDialog mDialog;
    private SwipeRefreshLayout mSwipeLayout;


    //上方切換按鈕 看板 & 文章
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //back to Hot
            case R.id.btn_HotText:
                Intent mainIntent = new Intent(this,MainActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainIntent);
                overridePendingTransition(0,0);
            case R.id.row_footer:
                Log.d("DK","footer click OK");
                return;
            case R.id.btnBoardList:
                return;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        //        //add listview footer
//        View footerView = getLayoutInflater().inflate(R.layout.row_footer,null);
//        mainListView.addFooterView(footerView);
//        footerView.setOnClickListener(this);

//        boardListView =  new ListView(this);
        setContentView(R.layout.activity_boardlist);

        boardListView =(ListView)findViewById(R.id.board_listview);
        boardListAdapter = new BoardListAdapter(this,getLayoutInflater());
        boardListView.setAdapter(boardListAdapter);
        boardListView.setOnItemClickListener(this);

//        ************* new add ***************
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("網路不穩讀取中..");
        mDialog.setCancelable(false);

        loadData();

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeColors(Color.BLUE);
//        *** *********   ******* ***** *******

        btnHotText = (Button)findViewById(R.id.btn_HotText);
        btnHotText.setOnClickListener(this);
        btnBoardList = (Button)findViewById(R.id.btnBoardList);
        btnBoardList.setOnClickListener(this);
        btnBoardList.setBackgroundColor(Color.LTGRAY);
        btnBoardList.setTextColor(Color.YELLOW);
    }

//   ***************   * new *    *************************************************************
    private void loadData(){
        String urlString = "http://disp.cc/api/get.php?act=bSearchList";
        mDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(urlString, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                mDialog.dismiss();
                mSwipeLayout.setRefreshing(false); //結束更新動畫
                Toast.makeText(getApplicationContext(),
                        "版面已刷新", Toast.LENGTH_LONG).show();
                //Log.d("Hot Text:", response.toString());

                //這行載入後 就出現閃退了
                boardListAdapter.updateData(response.optJSONArray("list"));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject error) {
                mDialog.dismiss();
                mSwipeLayout.setRefreshing(false); //結束更新動畫
                Toast.makeText(getApplicationContext(),
                        "Error: " + statusCode + " " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
                // Log error message
                //Log.e("Hot Text:", statusCode + " " + e.getMessage());
            }
        });
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("DK","click "+position);
        //從成員mainAdapter中用getItem取出第position項的資料，存成jsonObject
        JSONObject jsonObject = (JSONObject) boardListAdapter.getItem(position);
        //取出我們要的兩個資料 bi 和 ti
        String bi = jsonObject.optString("bi","");
        String ti = jsonObject.optString("type","");

        // 建立一個 Intent 用來傳遞資料到文章閱讀頁面 TextActivity
        Intent textIntent = new Intent(this, TextActivity.class);
        // 將要傳遞的資料放進 Intent
        textIntent.putExtra("bi", bi);
        textIntent.putExtra("type", type);

        // 使用準備好的 Intent 來開啟新的頁面
        startActivity(textIntent);
        //改變進入畫面與離開畫面
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }
//    ******************************************************************************************

}
