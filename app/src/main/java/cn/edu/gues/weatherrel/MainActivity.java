package cn.edu.gues.weatherrel;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gues.weatherrel.citymanager.CityManagerActivity;
import cn.edu.gues.weatherrel.db.DBManager;

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
        cityList= DBManager.queryAllName();
        imgList=new ArrayList<>();

        if(cityList.size()==0){
            cityList.add("毕节");
        }
        //可能搜索界面到此界面会传值，此处获取
        Intent intent=getIntent();
        String city=intent.getStringExtra("city");
        if(!cityList.contains(city)&& !TextUtils.isEmpty(city)){
            cityList.add(city);
        }

        //初始化ViewPager页面的方法
        initPager();
        adapter = new CityFragmentPagerAdapter(getSupportFragmentManager(),0,fragmentList);
        mainVp.setAdapter(adapter);
        //创建小圆点指示器
        initPoint();
        //设置最后一个城市
        mainVp.setCurrentItem(fragmentList.size()-1);
        //设置ViewPager页面监听
        setPagerListener();

    }

    private void setPagerListener() {
        //设置监听事件
        mainVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0;i<imgList.size();i++){
                    imgList.get(i).setImageResource(R.mipmap.point_black);
                }
                imgList.get(position).setImageResource(R.mipmap.point_green);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initPoint() {
        //创建小圆点，ViewPager页面指示器的函数
        for(int i=0;i<fragmentList.size();i++){
            ImageView pIv=new ImageView(this);
            pIv.setImageResource(R.mipmap.point_black);
            pIv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) pIv.getLayoutParams();
            lp.setMargins(0,0,20,0);
            imgList.add(pIv);
            pointLayout.addView(pIv);
        }
        imgList.get(imgList.size()-1).setImageResource(R.mipmap.point_green);
    }

    private void initPager() {
        //创建Fragment对象，添加到ViewPager数据源中
        for(int i=0;i<cityList.size();i++){
            CityWeatherFragment cwFragment = new CityWeatherFragment();
            Bundle bundle = new Bundle();
            bundle.putString("city",cityList.get(i));
            cwFragment.setArguments(bundle);
            fragmentList.add(cwFragment);
            System.out.println(fragmentList.size());
        }
        //mainVp.setOffscreenPageLimit(fragmentList.size());
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()){
            case R.id.main_iv_add:
                intent.setClass(this, CityManagerActivity.class);
                break;
            case R.id.main_iv_more:

                break;
        }
        startActivity(intent);
    }

    //页面重新加载时调用的函数，此处完成ViewPager的更新
    @Override
    protected void onRestart() {
        super.onRestart();
        //获取数据库中还剩的城市列表
        List<String> list=DBManager.queryAllName();
        if(list.size()==0){
            list.add("毕节");
        }
        cityList.clear();
        cityList.addAll(list);
        //剩余城市也要创建对应的Fragment页面
        fragmentList.clear();
        initPager();
        //提示适配器更新
        adapter.notifyDataSetChanged();
        //页面数量改变，指示器也改变
        imgList.clear();
        pointLayout.removeAllViews();//移除所有元素小点点
        //创建并添加
        initPoint();
        mainVp.setCurrentItem(fragmentList.size()-1);
    }

}
