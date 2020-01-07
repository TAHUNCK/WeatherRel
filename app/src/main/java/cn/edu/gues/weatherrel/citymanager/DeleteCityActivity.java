package cn.edu.gues.weatherrel.citymanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gues.weatherrel.R;
import cn.edu.gues.weatherrel.db.DBManager;

public class DeleteCityActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView errorIv,rightIv;
    ListView deleteLv;
    List<String> mDatas;//数据源
    List<String> deleteCity;//存储将删除的城市信息
    DeleteCityAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_city);

        errorIv=findViewById(R.id.delete_iv_error);
        rightIv=findViewById(R.id.delete_iv_right);
        deleteLv=findViewById(R.id.delete_lv);
        mDatas=DBManager.queryAllName();
        deleteCity=new ArrayList<>();
        errorIv.setOnClickListener(this);
        rightIv.setOnClickListener(this);

        adapter = new DeleteCityAdapter(this, mDatas, deleteCity);
        deleteLv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.delete_iv_error:
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("提示信息").setMessage("您确定要取消更改吗?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                builder.setNegativeButton("取消",null);
                builder.create().show();
                break;
            case R.id.delete_iv_right:
                for(int i=0;i<deleteCity.size();i++){
                    String city=deleteCity.get(i);
                    DBManager.deleteInfoCity(city);
                }
                finish();
                break;
        }
    }

}
