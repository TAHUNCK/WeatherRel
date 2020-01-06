package cn.edu.gues.weatherrel;

import androidx.fragment.app.Fragment;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class BaseFragment extends Fragment implements Callback.CommonCallback<String> {

    public void loadData(String path){
        RequestParams params=new RequestParams(path);
        params.addHeader("Authorization","APPCODE 26da263aadd74cdd854f5c8d617bff6a");
        x.http().get(params,this);

    }

    //获取数据成功时调用的接口
    @Override
    public void onSuccess(String result) {
        System.out.println("success");
    }

    //获取数据失败时调用的接口
    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        System.out.println("error");
    }

    //取消请求时调用的接口
    @Override
    public void onCancelled(CancelledException cex) {
        System.out.println("cancel");
    }

    //请求完成后，会回调的接口
    @Override
    public void onFinished() {
        System.out.println("finish");
    }
}
