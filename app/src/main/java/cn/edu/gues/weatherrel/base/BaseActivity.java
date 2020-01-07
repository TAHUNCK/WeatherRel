package cn.edu.gues.weatherrel.base;

import androidx.appcompat.app.AppCompatActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class BaseActivity extends AppCompatActivity implements Callback.CommonCallback<String> {

    public void loadData(String url){
        RequestParams params=new RequestParams(url);
        params.addHeader("Authorization","APPCODE 26da263aadd74cdd854f5c8d617bff6a");
        x.http().get(params,this);
    }

    @Override
    public void onSuccess(String result) {

    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {

    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }
}
