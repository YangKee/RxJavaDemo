package example.yangyee.com.rxjava;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * author: Yangxusong
 * created on: 2019/7/1 0001
 */
public class Example_3 {
    public static void main(String... arg) {
       test();
        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException mE) {
            mE.printStackTrace();
        }
    }


    private static void test() {
        Im.getInstance().setOnReceiveMessageListener(new OnReceiveMessageListener() {
            @Override
            public void receive(String msg) {
                System.out.println("interface-" + msg);
            }
        });
        Im.getInstance().setOnReceiveMessageObserver(new Consumer<String>() {
            @Override
            public void accept(String mS) throws Exception {
                System.out.println("Observer-" + mS);
            }
        });

        Observable.interval(500, TimeUnit.MILLISECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long mLong) {
                Im.getInstance().receiveMessage("收到消息啦"+mLong);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    public static class Im {
        private List<OnReceiveMessageListener> mOnReceiveMessageListeners = new ArrayList<>();
        private PublishSubject<String> mPublishSubject = PublishSubject.create();

        private static class ImHolder {
            public static Im instance = new Im();
        }

        public static Im getInstance() {
            return ImHolder.instance;
        }

        public void receiveMessage(String msg) {
            Iterator<OnReceiveMessageListener> iterator = mOnReceiveMessageListeners.iterator();
            while (iterator.hasNext()) {
                iterator.next().receive(msg);
            }

            mPublishSubject.onNext(msg);
        }

        public void setOnReceiveMessageListener(OnReceiveMessageListener mListener) {
            if (null == mListener || mOnReceiveMessageListeners.contains(mListener)) {
            } else {
                mOnReceiveMessageListeners.add(mListener);
            }
        }

        public void setOnReceiveMessageObserver(Consumer<String> mConsumer) {
            if (null != mConsumer) {
                mPublishSubject.subscribe(mConsumer);
            }
        }


    }


    public static interface OnReceiveMessageListener {
        void receive(String msg);
    }


}
