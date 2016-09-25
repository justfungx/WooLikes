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
 * Created by DUKE-KAO on 2016/9/25.
 */

public class BoardListAdapter extends BaseAdapter{
    private Context mContext;
    private LayoutInflater mInflater;
    private JSONArray mJsonArray;

    private static class  ViewHolder {
        public ImageView thumbImageView;
        public TextView titleTextView;
        public TextView descTextView;
    }
    public BoardListAdapter(Context context){
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mJsonArray = new JSONArray();
    }

    public  void updateData(JSONArray jsonArray){
        mJsonArray = jsonArray;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        return mJsonArray.length();
    }

    @Override
    public Object getItem(int position) {

        return mJsonArray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = new ViewHolder();

        // 取得目前這個Row的JSON資料
        JSONObject jsonObject = (JSONObject) getItem(position);

        Boolean hasThumb = false;
        if (jsonObject.has("img_list")) {
            JSONArray img_list = jsonObject.optJSONArray("img_list");
            if(img_list.length()!=0) {
                String imageURL = img_list.optString(0);
                // 使用 Picasso 來載入網路上的圖片
                // 圖片載入前先用placeholder顯示預設圖片
                Picasso.with(mContext).load(imageURL).placeholder(R.drawable.woolike300).into(holder.thumbImageView);
                hasThumb = true;
            }
        }
        if(!hasThumb){ // 沒有縮圖的話放 disp logo
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
