package cn.edu.gues.weatherrel.base;

import android.app.Application;

import org.xutils.x;

import cn.edu.gues.weatherrel.db.DBManager;

public class UniteApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        DBManager.initDB(this);
    }
}
