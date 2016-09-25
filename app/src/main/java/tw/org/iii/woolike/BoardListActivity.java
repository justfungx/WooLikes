package tw.org.iii.woolike;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by DUKE-KAO on 2016/9/25.
 */

public class BoardListActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnHotText;
    private Button btnBoardList;
    private ListView mainListView;

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

        setContentView(R.layout.activity_boardlist);

        btnHotText = (Button)findViewById(R.id.btn_HotText);
        btnHotText.setOnClickListener(this);
        btnBoardList = (Button)findViewById(R.id.btnBoardList);
        btnBoardList.setOnClickListener(this);
        btnBoardList.setBackgroundColor(Color.LTGRAY);
        btnBoardList.setTextColor(Color.YELLOW);
    }
}
