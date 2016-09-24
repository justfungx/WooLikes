package tw.org.iii.woolike;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

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
       return  mJsonrray.length();
    }

    @Override
    public Object getItem(int position) {
        return mJsonrray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViweHolder holder;

        // test view has null ture or false
        if (convertView == null) {
            // Inflate the custom row layout from your XML.
            convertView = mInFlaster.inflate(R.layout.row_main, null);
            // create a new "Holder" with subviews
            holder = new ViweHolder();
            holder.thumbImageView = (ImageView) convertView.findViewById(R.id.img_thumb);
            holder.titleTextView = (TextView) convertView.findViewById(R.id.text_title);
            holder.descTextView = (TextView) convertView.findViewById(R.id.text_desc);
            // hang onto this holder for future recyclage
            convertView.setTag(holder);
        } else {
            // skip all the expensive inflation/findViewById
            // and just get the holder you already made
            holder = (ViweHolder) convertView.getTag();
        }

        // 取得目前這個Row的JSON資料
        JSONObject jsonObject = (JSONObject) getItem(position);

        String imageUrl = "";
        if (jsonObject.has("img_list")) {
            JSONArray img_list = jsonObject.optJSONArray("img_list");
            if(img_list.length()!=0) {
                imageUrl = img_list.optString(0);
            }
        }
        if (!imageUrl.equals("")) {
            // 使用 Picasso 來載入網路上的圖片
            // 圖片載入前先用placeholder顯示預設圖片
            Picasso.with(mContext).load(imageUrl).placeholder(R.drawable.woolike300).into(holder.thumbImageView);
        } else { // 沒有縮圖的話放 logo
            holder.thumbImageView.setImageResource(R.drawable.woolike300);
        }

        // 從JSON資料取得標題和摘要
        String title = "";
        String desc = "";
        if (jsonObject.has("title")) {
            title = jsonObject.optString("title");
        }
        if (jsonObject.has("desc")) {
            desc = jsonObject.optString("desc");
        }

        // 將標題和摘要顯示在TextView上
        holder.titleTextView.setText(title);
        holder.descTextView.setText(desc);

        return convertView;
    }
}
