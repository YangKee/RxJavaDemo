package example.yangyee.com.rxjava;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * author: Yangxusong
 * created on: 2019/7/3 0003
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        System.setProperty("rx2.buffer-size", "200");
        Stetho.initializeWithDefaults(this);
    }
}
