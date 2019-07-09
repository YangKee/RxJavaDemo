package example.yangyee.com.rxjava;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jakewharton.rxbinding2.view.RxView;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.atomic.AtomicLong;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author: Yangxusong
 * created on: 2019/7/3 0003
 */
public class Example_4_Flowable extends AppCompatActivity {
    private int count = 0;
    private final int TOTAL = 10000;
    private SendAdapter mSendAdapter;
    private ReceiveAdapter mReceiveAdapter;

    private Subscription mSubscription;


    private TextView mSendTextView;
    private EditText mEditText;

    private AtomicLong mSendLong = new AtomicLong();
    private AtomicLong mReceiveLong = new AtomicLong();
    FlowableEmitter<Integer> mFlowableEmitter;


    LinearLayoutManager mLayoutManager1;
    LinearLayoutManager mLayoutManager2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getActionBar()) {
            getActionBar().hide();
        }

        setContentView(R.layout.activity_flowable);
        initView();

    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setAdapter(mSendAdapter = new SendAdapter());
        mLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager1);


        RecyclerView recyclerView1 = findViewById(R.id.rv1);
        recyclerView1.setAdapter(mReceiveAdapter = new ReceiveAdapter());
        mLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(mLayoutManager2);

        mSendAdapter.setUpFetchEnable(true);
        mReceiveAdapter.setUpFetchEnable(true);


        mSendTextView = findViewById(R.id.start);
        mEditText = findViewById(R.id.et);
        RxView.clicks(mSendTextView).observeOn(Schedulers.io()).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object mO) throws Exception {
                Flowable<Integer> flowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                        mFlowableEmitter = emitter;
                        int start = count * TOTAL;
                        int end = (count + 1) * TOTAL;
                        count++;
                        for (int i = start; i < end; i++) {
                            mSendLong.set(i);
                            DeBean deBean = new DeBean(mSendLong.get(), mReceiveLong.get(), mFlowableEmitter.requested());
                            emitter.onNext(i);
                            Thread.sleep(40);
                            Observable.just(deBean).observeOn(AndroidSchedulers.mainThread()).subscribe(mConsumer1);
                        }
                    }
                }, BackpressureStrategy.DROP);
                flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mSubscription = s;
                        mSubscription.request(Integer.parseInt(mEditText.getText().toString()));
                    }

                    @Override
                    public void onNext(Integer mInteger) {
                        mReceiveLong.set(mInteger);
                        DeBean deBean = new DeBean(mSendLong.get(), mReceiveLong.get(), mFlowableEmitter.requested());
                        Observable.just(deBean).observeOn(AndroidSchedulers.mainThread()).subscribe(mConsumer);
                    }

                    @Override
                    public void onError(final Throwable t) {
                        Log.e("yy", "e-" + t.getMessage());
                        Observable.just(t).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable mThrowable) throws Exception {
                                Toast.makeText(Example_4_Flowable.this, mThrowable.getMessage(), Toast.LENGTH_SHORT).show();
                                mSendTextView.setText(mSendTextView.getText() + t.getMessage());
                            }
                        });


                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        });

        RxView.clicks(findViewById(R.id.get)).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object mO) throws Exception {
                mSubscription.request(Integer.parseInt(mEditText.getText().toString()));
            }
        });

    }

    public void moveToPosition(LinearLayoutManager manager, int n) {
        manager.scrollToPositionWithOffset(n, 0);
        manager.setStackFromEnd(true);
    }

    private Consumer<DeBean> mConsumer = new Consumer<DeBean>() {
        @Override
        public void accept(DeBean mInteger) throws Exception {
            mReceiveAdapter.addData(mInteger);
            moveToPosition(mLayoutManager2, mReceiveAdapter.getData().size() - 1);
        }
    };
    private Consumer<DeBean> mConsumer1 = new Consumer<DeBean>() {
        @Override
        public void accept(DeBean mS) throws Exception {
            mSendTextView.setText(mS.toString());
            mSendAdapter.addData(mS);
//            MoveToPosition(mLayoutManager1,mReceiveAdapter.getData().size()-1);
        }
    };

    private class ReceiveAdapter extends BaseQuickAdapter<DeBean, BaseViewHolder> {

        public ReceiveAdapter() {
            super(R.layout.item_flowable);
        }

        @Override
        protected void convert(BaseViewHolder helper, DeBean item) {
            TextView textView = helper.getView(R.id.tv);

            if (item.send == TOTAL - 1) {
                textView.setTextColor(Color.RED);
            } else if (item.size == 0) {
                textView.setTextColor(Color.GREEN);
            } else {
                textView.setTextColor(Color.BLACK);
            }
            textView.setText(item.toString());
        }
    }

    private class SendAdapter extends BaseQuickAdapter<DeBean, BaseViewHolder> {
        public SendAdapter() {
            super(R.layout.item_flowable);
        }

        @Override
        protected void convert(BaseViewHolder helper, DeBean item) {
            TextView textView = helper.getView(R.id.tv);
            if (item.size == 0) {
                textView.setTextColor(Color.GREEN);
            } else {
                textView.setTextColor(Color.BLACK);
            }
            textView.setText(item.toString());
        }
    }

    private static class DeBean {
        long send;
        Long receive;
        long size;

        public DeBean(long mSend, Long mReceive, long mSize) {
            send = mSend;
            receive = mReceive;
            size = mSize;
        }

        @Override
        public String toString() {
            return
                    "send=" + send +
                            ", r=" + receive +
                            ", s=" + size;

        }
    }
}
