package example.yangyee.com.rxjava;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jakewharton.rxbinding2.view.RxView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {


    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.INTERNET};
        RxPermissions rxPermissions = new RxPermissions(this);
        Disposable subscribe = rxPermissions.request(permissions).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean mBoolean) throws Exception {
                System.out.println(mBoolean ? "获取权限成功" : "获取权限失败");
            }
        });
        RxView.clicks(findViewById(R.id.tv1))
                .throttleFirst(100, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object mO) throws Exception {
//                        Intent intent = new Intent(MainActivity.this, Example_1.class);
////                        startActivity(intent);
                        RxSchedulerHelper.runOnIOThread(mRunnable);
                        RxSchedulerHelper.runOnIOThreadDelay(mRunnable,2000);
                    }
                });
        RxView.clicks(findViewById(R.id.tv2))
                .throttleFirst(100, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object mO) throws Exception {
//                        Intent intent = new Intent(MainActivity.this, Example_4_Flowable.class);
//                        startActivity(intent);
                        RxSchedulerHelper.runOnComputationThread(mRunnable);
                        RxSchedulerHelper.runOnComputationThreadDelay(mRunnable, 1000);
                    }
                });

        RxView.clicks(findViewById(R.id.tv3))
                .throttleFirst(100, TimeUnit.MILLISECONDS)
//                .compose(this.<Object>bindToLifecycle())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object mO) throws Exception {
//                        Intent intent = new Intent(MainActivity.this, Example_4_Flowable.class);
//                        startActivity(intent);
                        RxSchedulerHelper.runOnIOThreadDelay(mRunnable1,1000);
                        RxSchedulerHelper.runOnComputationThreadDelay(mRunnable1,3000);
                    }
                });


    }


    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Log.e("yy", Thread.currentThread().getName());

        }
    };
    private Runnable mRunnable1 = new Runnable() {
        @Override
        public void run() {
            Log.e("yy", Thread.currentThread().getName());
            RxSchedulerHelper.runOnUIThreadDelay(mRunnable, 1000);
        }
    };
}
