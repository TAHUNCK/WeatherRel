package cn.edu.gues.weatherrel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gues.weatherrel.db.DBManager;

public class MoreActivity extends AppCompatActivity implements View.OnClickListener{
    TextView bgTv,cacheTv,versionTv,shareTv;
    RadioGroup exbgRg;
    ImageView backIv;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        bgTv=findViewById(R.id.more_tv_exchangebg);
        cacheTv=findViewById(R.id.more_tv_cache);
        versionTv=findViewById(R.id.more_tv_version);
        shareTv=findViewById(R.id.more_tv_share);
        exbgRg=findViewById(R.id.more_rg);
        backIv=findViewById(R.id.more_iv_back);

        bgTv.setOnClickListener(this);
        cacheTv.setOnClickListener(this);
        shareTv.setOnClickListener(this);
        backIv.setOnClickListener(this);

        pref = getSharedPreferences("bg_pref", MODE_PRIVATE);

        String versionName=getVersionName();
        versionTv.setText("当前版本：    v"+versionName);

        //设置改变背景图片的单选按钮的监听
        setRgListener();

    }

    private void setRgListener() {
        exbgRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //目前的默认壁纸
                int bg=pref.getInt("bg",1);
                SharedPreferences.Editor editor=pref.edit();
                Intent intent=new Intent(MoreActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                switch (checkedId){
                    case R.id.more_rb_sky:
                        if(bg==1){
                            Toast.makeText(MoreActivity.this,"您已选择此背景！",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        editor.putInt("bg",1);
                        editor.commit();
                        break;
                    case R.id.more_rb_cblue:
                        if(bg==2){
                            Toast.makeText(MoreActivity.this,"您已选择此背景！",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        editor.putInt("bg",2);
                        editor.commit();
                        break;
                    case R.id.more_rb_yellow:
                        if(bg==3){
                            Toast.makeText(MoreActivity.this,"您已选择此背景！",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        editor.putInt("bg",3);
                        editor.commit();
                        break;
                    case R.id.more_rb_pink:
                        if(bg==4){
                            Toast.makeText(MoreActivity.this,"您已选择此背景！",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        editor.putInt("bg",4);
                        editor.commit();
                        break;
                    case R.id.more_rb_dblue:
                        if(bg==5){
                            Toast.makeText(MoreActivity.this,"您已选择此背景！",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        editor.putInt("bg",5);
                        editor.commit();
                        break;
                    case R.id.more_rb_cat:
                        if(bg==6){
                            Toast.makeText(MoreActivity.this,"您已选择此背景！",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        editor.putInt("bg",6);
                        editor.commit();
                        break;
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.more_iv_back:
                finish();
                break;
            case R.id.more_tv_cache:
                clearCache();
                break;
            case R.id.more_tv_share:
                shareSoftware("来GitHub下载源码并点亮Star，和作者完成py交易。\nhttps://github.com/TAHUNCK/WeatherRel");
                break;
            case R.id.more_tv_exchangebg:
                if(exbgRg.getVisibility()==View.VISIBLE){
                    exbgRg.setVisibility(View.GONE);
                }else{
                    exbgRg.setVisibility(View.VISIBLE);
                }
                break;
        }
    }


    private String getVersionName() {
        PackageManager packageManager = getPackageManager();
        String versionName=null;
        try{
            PackageInfo info=packageManager.getPackageInfo(getPackageName(),0);
            versionName=info.versionName;
        }catch (Exception e){
            e.printStackTrace();
        }
        return versionName;
    }

    private void shareSoftware(String s) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,s);
        startActivity(Intent.createChooser(intent,"天气预报"));
    }

    private void clearCache() {
        //清除缓存，即删除数据库中的数据
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("提示信息").setMessage("确定删除所有缓存吗?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBManager.deleteAllInfo();
                Toast.makeText(MoreActivity.this,"已清除全部缓存！",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MoreActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }).setNegativeButton("取消",null);
        builder.create().show();
    }
}
