package cn.edu.gues.weatherrel.citymanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.edu.gues.weatherrel.R;

public class DeleteCityAdapter  extends BaseAdapter {

    Context context;
    List<String> mDatas;
    List<String> deleteCity;

    public DeleteCityAdapter(Context context, List<String> mDatas, List<String> deleteCity) {
        this.context = context;
        this.mDatas = mDatas;
        this.deleteCity = deleteCity;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
       return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_deletecity,null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        final String city = mDatas.get(position);
        holder.tv.setText(city);
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatas.remove(city);
                deleteCity.add(city);
                notifyDataSetChanged();//删除了提示适配器更新
            }
        });
        return convertView;
    }

    class ViewHolder{
        TextView tv;
        ImageView iv;
        public ViewHolder(View view){
            tv=view.findViewById(R.id.item_delete_tv);
            iv=view.findViewById(R.id.item_delete_iv);
        }
    }

}
