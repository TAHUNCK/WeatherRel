package cn.edu.gues.weatherrel;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView addCityIv;
    ImageView moreIv;
    LinearLayout pointLayout;
    ViewPager mainVp;
    //ViewPager的数据源
    List<Fragment> fragmentList;
    //表示需要显示的城市的集合
    List<String> cityList;
    //表示ViewPager的页数指示器集合
    List<ImageView> imgList;
    private CityFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addCityIv=findViewById(R.id.main_iv_add);
        moreIv=findViewById(R.id.main_iv_more);
        pointLayout=findViewById(R.id.main_layout_point);
        mainVp=findViewById(R.id.main_vp);
        //首页添加点击事件
        addCityIv.setOnClickListener(this);
        moreIv.setOnClickListener(this);

        fragmentList=new ArrayList<>();
        cityList=new ArrayList<>();
        imgList=new ArrayList<>();

        if(cityList.size()==0){
            cityList.add("毕节");
        }

        //初始化ViewPager页面的方法
        initPager();
        adapter = new CityFragmentPagerAdapter(getSupportFragmentManager(),0,fragmentList);
        mainVp.setAdapter(adapter);
        //创建小圆点指示器
        initPoint();

    }

    private void initPoint() {

    }

    private void initPager() {
        //创建Fragment对象，添加到ViewPager数据源中
        for(int i=0;i<cityList.size();i++){
            CityWeatherFragment cwFragment = new CityWeatherFragment();
            Bundle bundle = new Bundle();
            bundle.putString("city",cityList.get(i));
            cwFragment.setArguments(bundle);
            fragmentList.add(cwFragment);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_iv_add:

                break;
            case R.id.main_iv_more:

                break;
        }
    }
}
