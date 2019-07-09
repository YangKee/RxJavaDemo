package example.yangyee.com.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * author: Yangxusong
 * created on: 2019/7/1 0001
 */
public class Example_1 extends Activity {
    public static String url = "http://m.maoyan.com/mmdb/comments/movie/1200486.json?_v_=yes&offset=0&startTime=2018-07-28%2022%3A25%3A03";
    private UserAdapter mUserAdapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recyclerView = new RecyclerView(this);
        setContentView(recyclerView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        recyclerView.setAdapter(mUserAdapter = new UserAdapter());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        loadData(url);
    }


    private void loadData(String url) {
        Observable.just(url)
                .observeOn(Schedulers.io())
                .map(new Function<String, Response>() {
                    @Override
                    public Response apply(String mS) throws Exception {
                        System.out.println(Thread.currentThread().getName() + "---" + mS);
                        Response response = null;

                        URL url = new URL(mS);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setReadTimeout(5000);
                        if (conn.getResponseCode() == 200) {

                            InputStream in = conn.getInputStream();
                            byte[] b = new byte[1024 * 512];
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            int len = 0;
                            while ((len = in.read(b)) > -1) {
                                baos.write(b, 0, len);
                            }
                            String msg = baos.toString();
                            response = new Gson().fromJson(msg, Response.class);
                        }
                        System.out.println(Thread.currentThread().getName() + "-response--" + response);
                        return response;
                    }
                })
                .flatMap(new Function<Response, ObservableSource<User>>() {
                    @Override
                    public ObservableSource<User> apply(Response mResponse) throws Exception {
                        return Observable.fromIterable(mResponse.getCmts());
                    }
                })
                .filter(new Predicate<User>() {
                    @Override
                    public boolean test(User mUser) throws Exception {
                        return mUser.gender == 2;
                    }
                })
                .toSortedList(new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        return o2.getUserLevel() - o1.getUserLevel();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(List<User> mUsers) {
                        System.out.println(Thread.currentThread().getName() + "---" + mUsers);
                        mUserAdapter.setNewData(mUsers);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }


    public static class Response {
        List<User> cmts;

        public List<User> getCmts() {
            return cmts;
        }

        public void setCmts(List<User> mCmts) {
            cmts = mCmts;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "cmts=" + cmts +
                    '}';
        }
    }

    private static class User {
        String nickName;
        String avatarurl;
        String startTime;
        int gender;
        int userLevel;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String mNickName) {
            nickName = mNickName;
        }

        public String getAvatarurl() {
            return avatarurl;
        }

        public void setAvatarurl(String mAvatarurl) {
            avatarurl = mAvatarurl;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int mGender) {
            gender = mGender;
        }

        public int getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(int mUserLevel) {
            userLevel = mUserLevel;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String mStartTime) {
            startTime = mStartTime;
        }

        @Override
        public String toString() {
            return "User{" +
                    "nickName='" + nickName + '\'' +
                    ", avatarurl='" + avatarurl + '\'' +
                    ", startTime='" + startTime + '\'' +
                    ", gender=" + gender +
                    ", userLevel=" + userLevel +
                    '}';
        }
    }


    private static class UserAdapter extends BaseQuickAdapter<User, BaseViewHolder> {

        public UserAdapter() {
            super(R.layout.item_user);
        }

        @Override
        protected void convert(BaseViewHolder helper, User item) {
            helper.setText(R.id.tv, item.getNickName());
            helper.setText(R.id.tv_lv, "level:" + item.getUserLevel());
            ImageView view = helper.getView(R.id.img);
            Glide.with(view.getContext()).load(item.avatarurl).into(view);
        }
    }
}
