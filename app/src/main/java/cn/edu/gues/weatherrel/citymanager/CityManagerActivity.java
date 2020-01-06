package cn.edu.gues.weatherrel.citymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gues.weatherrel.R;
import cn.edu.gues.weatherrel.db.DBManager;
import cn.edu.gues.weatherrel.db.DatabaseBean;

public class CityManagerActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView addIv,backIv,deleteIv;
    ListView cityLv;
    List<DatabaseBean> beanList;//作为列表的数据源
    CityManageAdapter cityManageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_manager);

        addIv=findViewById(R.id.city_iv_add);
        backIv=findViewById(R.id.city_iv_back);
        deleteIv=findViewById(R.id.city_iv_delete);
        cityLv=findViewById(R.id.city_lv);
        beanList=new ArrayList<>();
        //添加点击监听事件
        addIv.setOnClickListener(this);
        backIv.setOnClickListener(this);
        deleteIv.setOnClickListener(this);

        //设置适配器
        cityManageAdapter = new CityManageAdapter(this, beanList);
        cityLv.setAdapter(cityManageAdapter);
    }

    //获取数据库中真实数据源，添加到原有数据源中，提示适配器更新
    @Override
    protected void onResume() {
        super.onResume();
        List<DatabaseBean> list=DBManager.queryAlllInfo();
        beanList.clear();
        beanList.addAll(list);
        cityManageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.city_iv_add:
                int cityCount = DBManager.getCityCount();
                if(cityCount<5){
                    Intent intent=new Intent(this,SearchCityActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(this,"城市数量已达上限，请删除之后再添加",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.city_iv_back:
                finish();
                break;
            case R.id.city_iv_delete:
                Intent intent1 = new Intent(this, DeleteCityActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
