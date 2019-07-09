package example.yangyee.com.rxjava;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * author: Yangxusong
 * created on: 2019/7/1 0001
 */
public class Example_2 {
    public static void main(String... arg) {
        upload("localPath", "发一个简单的贴子");
        try {
            Thread.sleep(150 * 1000);
        } catch (InterruptedException mE) {
            mE.printStackTrace();
        }
    }

    private static void upload(String localPath, String content) {
        Observable.zip(checkContent(content), uploadImage(localPath), new BiFunction<String, String, String>() {
            @Override
            public String apply(String mS, String mS2) throws Exception {
                System.out.println("现在开始分布--" + Thread.currentThread().getName());
                Thread.sleep(2000);
                return "发布成功";
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String mS) {
                        System.out.println(mS + "--" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("-Throwable-" + e.getMessage() + "--" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private static Observable<String> uploadImage(String localPath) {
        return Observable.zip(Observable.just(localPath).observeOn(Schedulers.io()).map(new Function<String, byte[]>() {
            @Override
            public byte[] apply(String path) throws Exception {
                //裁剪压缩后返回byte
                System.out.println("裁剪压缩图片--" + Thread.currentThread().getName());
                Thread.sleep(3000);
                System.out.println("裁剪压缩图片成功--" + Thread.currentThread().getName());
                return new byte[0];
            }
        }), Observable.just("token").observeOn(Schedulers.io()).map(new Function<String, String>() {
            @Override
            public String apply(String mS) throws Exception {
                System.out.println("拿云端地址--" + Thread.currentThread().getName());
                Thread.sleep(1000);
                System.out.println("拿云端地址成功--" + Thread.currentThread().getName());
                return "http://www.baidu.com";
            }
        }), new BiFunction<byte[], String, String>() {
            @Override
            public String apply(byte[] mBytes, String mS) throws Exception {
                System.out.println("压缩成功，拿云端地址都成功，现在上传--" + Thread.currentThread().getName());
                Thread.sleep(3000);
                System.out.println("现在上传成功，返回云端地址--" + Thread.currentThread().getName());
                return mS;
            }
        });
    }


    /**
     * 检查文案
     *
     * @param content
     * @return
     */
    private static Observable<String> checkContent(String content) {
        return Observable.just(content).flatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(String mS) throws Exception {
                System.out.println("文案检查开始--" + Thread.currentThread().getName());
                Thread.sleep(1000);
                if (null == mS || mS.length() <= 0) {
                    System.out.println("文案检查失败--" + Thread.currentThread().getName());
                    throw new NullPointerException("content can not be null !");
                }
                System.out.println("文案检查成功--" + Thread.currentThread().getName());
                return Observable.just(mS);
            }
        });
    }


}
