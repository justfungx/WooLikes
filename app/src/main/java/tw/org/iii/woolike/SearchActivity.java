package tw.org.iii.woolike;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.TextureView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by user on 2016/9/26.
 */

public class SearchActivity  extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private ListView mListView;
    private ArrayList<String> mSearchList =  new ArrayList<>();
    private  ArrayAdapter mSearchAdapter;
    private boolean mIsSearch = false;
    private String query;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SearchView searchView = (SearchView) findViewById(R.id.searchview);
        searchView.setOnQueryTextListener(this);
        searchView.setIconifiedByDefault(false); //是否要點選搜尋圖示後再打開輸入框
        searchView.setSubmitButtonEnabled(false);//輸入框後是否要加上送出的按鈕
        searchView.setQueryHint("輸入看板名稱"); //輸入框沒有值時要顯示的提示文字


        View headerView = getLayoutInflater().inflate(R.layout.row_boardsearchheader, null);
        mListView.addHeaderView(headerView);

        mListView = (ListView) findViewById(R.id.listview);
        mSearchAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mSearchList);


        mListView.setOnItemClickListener((AdapterView.OnItemClickListener) this);

        loadData();
    }


    private void loadData(){
        String urlString = "http://disp.cc/api/get.php?act=bSearchList";

        AsyncHttpClient client  = new AsyncHttpClient();
        client.get(urlString, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray list =  response.optJSONArray("list");
                JSONObject board;
                for(int  i = 0 ; i < list.length(); i++){
                    board = list.optJSONObject(i);
                    mSearchList.add(board.optString("name")+ "" + board.optString("title"));

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable , JSONObject errorResponse) {
                Toast.makeText(getApplicationContext(),
                        "Error:" + statusCode + ""+ throwable.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        
        if(!mIsSearch && query.length()!=0) { //搜尋框有值時
            mListView.setAdapter(mSearchAdapter);
            mIsSearch = true;
        }else if(mIsSearch && query.length()==0){ //搜尋框是空的時
            mListView.setAdapter(null);
            mIsSearch = false;
        }
        if(mIsSearch) { //過濾Adapter的內容
            Filter filter = mSearchAdapter.getFilter();
            filter.filter(query);
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Toast.makeText(this, "輸入的是：" + query, Toast.LENGTH_SHORT).show();
        return true;
    }

}
