package tw.org.iii.woolike;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;

/**
 * Created by DUKE-KAO on 2016/9/24.
 */

public class MainAdapter extends BaseAdapter{
    Context mContext;
    LayoutInflater mInFlaster;
    JSONArray mJsonrray;

    private static class ViweHolder{
        //存入row id
        public ImageView thumbImageView;
        public TextView  titleTextView;
        public TextView  descTextView;

    }

    public  MainAdapter(Context context){
        mContext = context;
        mInFlaster = LayoutInflater.from(mContext);
        mJsonrray = new JSONArray();
    }
    // 輸入JSON資料
    public void updataData(JSONArray jsonArray){
        mJsonrray = jsonArray;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
