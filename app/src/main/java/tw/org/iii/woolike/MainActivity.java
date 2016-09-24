package tw.org.iii.woolike;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
 private ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Loading Data..ing");
        loadData();

    }

    private void loadData() {
        mDialog.show();
        String urlString = "http://disp.cc/m/{bi}-{ti}";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(urlString, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                mDialog.dismiss();
                Toast.makeText(getApplicationContext(),
                        "Success!", Toast.LENGTH_LONG).show();
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
}
