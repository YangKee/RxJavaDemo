package example.yangyee.com.rxjava;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * author: Yangxusong
 * created on: 2019/7/1 0001
 */
public class RetrofitManager {


    private static IUserApi sIUserApi;

    public static final String API_HOST = "http://m.maoyan.com/";


    private static class SingleInstanceHolder {
        private static RetrofitManager instance = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return SingleInstanceHolder.instance;
    }

    private RetrofitManager() {
        init();
    }

    /**
     * 初始化网络通信服务
     */
    private void init() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addNetworkInterceptor(new StethoInterceptor())
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(8, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(API_HOST)
                .build();
        sIUserApi = retrofit.create(IUserApi.class);

    }

    public  IUserApi getIUserApi() {
        return sIUserApi;
    }


    public static interface IUserApi {
        @GET("mmdb/comments/movie/1200486.json/")
        Observable<Example_1.Response> getUser(@Query("_v_") String _v_, @Query("offset") int offset, @Query("startTime") String startTime);
    }

}
