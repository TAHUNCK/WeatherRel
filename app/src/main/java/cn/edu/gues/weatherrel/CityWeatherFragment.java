package cn.edu.gues.weatherrel;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.List;

import cn.edu.gues.weatherrel.base.BaseFragment;
import cn.edu.gues.weatherrel.bean.WeatherBean;
import cn.edu.gues.weatherrel.db.DBManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class CityWeatherFragment extends BaseFragment implements View.OnClickListener{

    TextView tempTv,cityTv,conditionTv,windTv,tempRangeTv,dateTv,clothIndexTv,carIndexTv,coldIndexTv,sportIndexTv,raysIndexTv,airIndexTv;
    ImageView dayIv;
    LinearLayout futureLayout;
    String city;
    String url="https://jisutqybmf.market.alicloudapi.com/weather/query?city=";
    private List<WeatherBean.ResultBean.IndexBean> indexList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_city_weather, container, false);
        initView(view);
        //通过activity传值获取到当前fragment加载的是哪个地方的天气情况
        Bundle bundle=getArguments();
        city=bundle.getString("city");
        try {
            url=url+URLEncoder.encode(city,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //调用父类获取数据的方法
        loadData(url);
        return view;
    }

    @Override
    public void onSuccess(String result) {
        //解析并展示数据
        parseShowData(result);
        //更新数据
        int i= DBManager.updateInfoByCity(city,result);
        if(i<=0){
            //更新失败，增加记录
            DBManager.addCityInfo(city,result);
        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        //数据库中查找上一次信息显示在Fragment中
        String s=DBManager.queryInfoByCity(city);
        if(!TextUtils.isEmpty(s)){
            parseShowData(s);
        }

    }

    private void parseShowData(String result) {
        //使用Gson解析数据
        //String s=result;
        WeatherBean weatherBean=new Gson().fromJson(result,WeatherBean.class);
        WeatherBean.ResultBean resultBean=weatherBean.getResult();
        //获取指数信息集合列表
        indexList = resultBean.getIndex();
        //设置TextView,获取今日天气情况
        dateTv.setText(resultBean.getDate());
        cityTv.setText(resultBean.getCity());
        tempTv.setText(resultBean.getTemp()+"℃");
        windTv.setText(resultBean.getWinddirect()+"："+resultBean.getWindpower());
        tempRangeTv.setText(resultBean.getTemplow()+"℃~"+resultBean.getTemphigh()+"℃");
        conditionTv.setText(resultBean.getWeather());
        //设置显示天气的图片
        Picasso.with(getActivity()).load(getResource("x"+resultBean.getImg())).into(dayIv);
        //获取未来七天的天气情况
        List<WeatherBean.ResultBean.DailyBean> futureList = resultBean.getDaily();
        futureList.remove(0);
        for(int i=0;i<futureList.size();i++){
            View itemView=LayoutInflater.from(getActivity()).inflate(R.layout.item_main_center,null);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            futureLayout.addView(itemView);
            TextView idateTv=itemView.findViewById(R.id.item_center_tv_date);
            TextView iconTv=itemView.findViewById(R.id.item_center_tv_con);
            TextView itemprangeTv=itemView.findViewById(R.id.item_center_tv_temp);
            ImageView iIv=itemView.findViewById(R.id.item_center_iv);
            //获取对应位置的天气情况
            WeatherBean.ResultBean.DailyBean dailyBean=futureList.get(i);
            idateTv.setText(dailyBean.getDate());
            iconTv.setText(dailyBean.getDay().getWeather());
            itemprangeTv.setText(dailyBean.getNight().getTemplow()+"℃~"+dailyBean.getDay().getTemphigh()+"℃");
            Picasso.with(getActivity()).load(getResource("x"+resultBean.getDaily().get(i).getDay().getImg())).into(iIv);
        }
    }

    //通过反射获取图片id,传入的图片名无后缀名
    private int getResource(String imageName){
        Class mipmap=R.mipmap.class;
        try{
            Field field=mipmap.getDeclaredField(imageName);
            int resId=field.getInt(imageName);
            return resId;
        }catch (Exception e){
            return 0;
        }
    }

    private void initView(View view){
        tempTv=view.findViewById(R.id.frag_tv_currenttemp);
        cityTv=view.findViewById(R.id.frag_tv_city);
        conditionTv=view.findViewById(R.id.frag_tv_condition);
        windTv=view.findViewById(R.id.frag_tv_wind);
        tempRangeTv=view.findViewById(R.id.frag_tv_temprange);
        dateTv=view.findViewById(R.id.frag_tv_date);
        clothIndexTv=view.findViewById(R.id.frag_index_tv_dress);
        carIndexTv=view.findViewById(R.id.frag_index_tv_washcar);
        coldIndexTv=view.findViewById(R.id.frag_index_tv_cold);
        sportIndexTv=view.findViewById(R.id.frag_index_tv_sport);
        raysIndexTv=view.findViewById(R.id.frag_index_tv_rays);
        airIndexTv=view.findViewById(R.id.frag_index_tv_air);
        dayIv=view.findViewById(R.id.frag_iv_today);
        futureLayout=view.findViewById(R.id.frag_center_layout);
        //监听点击事件设置
        clothIndexTv.setOnClickListener(this);
        carIndexTv.setOnClickListener(this);
        coldIndexTv.setOnClickListener(this);
        sportIndexTv.setOnClickListener(this);
        raysIndexTv.setOnClickListener(this);
        airIndexTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        WeatherBean.ResultBean.IndexBean indexBean;
        String msg;
        switch (view.getId()){
            case R.id.frag_index_tv_dress:
                builder.setTitle("穿衣指数");
                indexBean=indexList.get(6);
                msg=indexBean.getIvalue()+"\n"+indexBean.getDetail();
                builder.setMessage(msg);
                builder.setPositiveButton("确定",null);
                break;
            case R.id.frag_index_tv_washcar:
                builder.setTitle("洗车指数");
                indexBean=indexList.get(4);
                msg=indexBean.getIvalue()+"\n"+indexBean.getDetail();
                builder.setMessage(msg);
                builder.setPositiveButton("确定",null);
                break;
            case R.id.frag_index_tv_cold:
                builder.setTitle("感冒指数");
                indexBean=indexList.get(3);
                msg=indexBean.getIvalue()+"\n"+indexBean.getDetail();
                builder.setMessage(msg);
                builder.setPositiveButton("确定",null);
                break;
            case R.id.frag_index_tv_sport:
                builder.setTitle("运动指数");
                indexBean=indexList.get(1);
                msg=indexBean.getIvalue()+"\n"+indexBean.getDetail();
                builder.setMessage(msg);
                builder.setPositiveButton("确定",null);
                break;
            case R.id.frag_index_tv_rays:
                builder.setTitle("紫外线指数");
                indexBean=indexList.get(2);
                msg=indexBean.getIvalue()+"\n"+indexBean.getDetail();
                builder.setMessage(msg);
                builder.setPositiveButton("确定",null);
                break;
            case R.id.frag_index_tv_air:
                builder.setTitle("空调指数");
                indexBean=indexList.get(0);
                msg=indexBean.getIvalue()+"\n"+indexBean.getDetail();
                builder.setMessage(msg);
                builder.setPositiveButton("确定",null);
        }
        builder.create().show();
    }

}
