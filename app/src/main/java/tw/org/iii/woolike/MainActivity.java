package tw.org.iii.woolike;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ProgressDialog mDialog;
    private ListView mListView;
    private MainAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView.setOnItemClickListener(this);

        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Loading Data..ing");

        mListView = (ListView)findViewById(R.id.main_listview);
        mAdapter = new MainAdapter(this);
        mListView.setAdapter(mAdapter);

        loadData();

    }

    private void loadData() {
        mDialog.show();
        String urlString = "http://disp.cc/api/hot_text.json";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(urlString, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                mDialog.dismiss();
                Toast.makeText(getApplicationContext(),
                        "Success!", Toast.LENGTH_LONG).show();
                if( response.has("err") && response.optInt("err")!=0 ){
                    Toast.makeText(getApplicationContext(),"Data error", Toast.LENGTH_LONG).show();
                }
                JSONArray list = response.optJSONArray("list");
                if(list==null){
                    Toast.makeText(getApplicationContext(),"Data error", Toast.LENGTH_LONG).show();
                }
                mAdapter.updataData(list);
                Log.d("Hot Text:", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject error) {
                mDialog.dismiss();
                Toast.makeText(getApplicationContext(),
                        "Error: " + statusCode + " " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
                // Log error message
                Log.e("Hot Text:", statusCode + " " + e.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
