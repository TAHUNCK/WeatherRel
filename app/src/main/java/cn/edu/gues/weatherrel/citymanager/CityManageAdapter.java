package cn.edu.gues.weatherrel.citymanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import cn.edu.gues.weatherrel.R;
import cn.edu.gues.weatherrel.bean.WeatherBean;
import cn.edu.gues.weatherrel.db.DatabaseBean;

public class CityManageAdapter extends BaseAdapter {

    Context context;
    List<DatabaseBean> beanList;

    public CityManageAdapter(Context context, List<DatabaseBean> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    @Override
    public int getCount() {
        return beanList.size();
    }

    @Override
    public Object getItem(int position) {
        return beanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(null == convertView){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_city_manager,null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        DatabaseBean bean=beanList.get(position);
        holder.cityTv.setText(bean.getCity());
        WeatherBean weatherBean = new Gson().fromJson(bean.getContent(), WeatherBean.class);
        WeatherBean.ResultBean resultBean=weatherBean.getResult();
        holder.cityTv.setText(resultBean.getCity());
        holder.conTv.setText("天气："+resultBean.getWeather());
        holder.currentTempTv.setText(resultBean.getTemp()+"℃");
        holder.windTv.setText(resultBean.getWinddirect()+"："+resultBean.getWindpower());
        holder.tempTangeTv.setText(resultBean.getTemplow()+"℃~"+resultBean.getTemphigh()+"℃");
        return convertView;
    }

    class ViewHolder{
        TextView cityTv,conTv,currentTempTv,windTv,tempTangeTv;
        public ViewHolder(View itemView){
            cityTv=itemView.findViewById(R.id.item_city_tv_city);
            conTv=itemView.findViewById(R.id.item_city_tv_condition);
            currentTempTv=itemView.findViewById(R.id.item_city_tv_temp);
            windTv=itemView.findViewById(R.id.item_city_wind);
            tempTangeTv=itemView.findViewById(R.id.item_city_temprange);
        }
    }

}
