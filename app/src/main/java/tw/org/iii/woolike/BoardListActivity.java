package tw.org.iii.woolike;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by DUKE-KAO on 2016/9/25.
 */

public class BoardListActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnHotText;
    private Button btnBoardList;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //back to Hot
            case R.id.btn_HotText:
                Intent mainIntent = new Intent(this,MainActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainIntent);
                overridePendingTransition(0,0);
                return;
            case R.id.btnBoardList:
                return;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_boardlist);

        btnHotText = (Button)findViewById(R.id.btn_HotText);
        btnHotText.setOnClickListener(this);
        btnBoardList = (Button)findViewById(R.id.btnBoardList);
        btnBoardList.setOnClickListener(this);
        btnBoardList.setBackgroundColor(Color.LTGRAY);
        btnBoardList.setTextColor(Color.YELLOW);
    }
}
