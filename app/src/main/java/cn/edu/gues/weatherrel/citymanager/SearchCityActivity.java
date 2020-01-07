package cn.edu.gues.weatherrel.citymanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cn.edu.gues.weatherrel.MainActivity;
import cn.edu.gues.weatherrel.R;
import cn.edu.gues.weatherrel.base.BaseActivity;
import cn.edu.gues.weatherrel.bean.WeatherBean;

public class SearchCityActivity extends BaseActivity implements View.OnClickListener{

    EditText searchEt;
    ImageView submitIv;
    GridView searchGv;

    String[] hotCitys={"北京","上海","广州","深圳","珠海","东莞","西安","南京","天津","苏州","厦门","长沙","成都","重庆"
            ,"杭州","武汉","太原","青岛","沈阳","南宁"};
    String url="https://jisutqybmf.market.alicloudapi.com/weather/query?city=";
    String city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        searchEt=findViewById(R.id.search_et);
        submitIv=findViewById(R.id.search_iv_submit);
        searchGv=findViewById(R.id.search_gv);
        submitIv.setOnClickListener(this);
        //设置适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_hotcity, hotCitys);
        searchGv.setAdapter(adapter);
        setListener();

    }

    //设置监听事件
    private void setListener() {
        searchGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                city=hotCitys[position];
                String url1="";
                try {
                    url1=url+ URLEncoder.encode(city,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                loadData(url1);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_iv_submit:
                city=searchEt.getText().toString();
                String url1="";
                if(!TextUtils.isEmpty(city)){
                    try {
                        url1=url+ URLEncoder.encode(city,"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    loadData(url1);
                }else{
                    Toast.makeText(this,"输入的内容不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onSuccess(String result) {
        WeatherBean weatherBean=new Gson().fromJson(result,WeatherBean.class);
        if(weatherBean.getStatus()==0){
            Intent intent = new Intent(this, MainActivity.class);
            //清空原来的Activity栈，开启新栈
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("city",city);
            startActivity(intent);
        }else if(weatherBean.getStatus()==201){
            Toast.makeText(this,"城市和城市ID和城市代号都为空",Toast.LENGTH_SHORT).show();
        }else if(weatherBean.getStatus()==202){
            Toast.makeText(this,"城市不存在",Toast.LENGTH_SHORT).show();
        }else if(weatherBean.getStatus()==203){
            Toast.makeText(this,"此城市没有天气信息",Toast.LENGTH_SHORT).show();
        }else if(weatherBean.getStatus()==210) {
            Toast.makeText(this, "没有信息", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        Toast.makeText(this,"请不要乱输入哦",Toast.LENGTH_SHORT).show();
    }
}
