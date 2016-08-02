package com.max.tse.rx;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-25
 * Time: 下午4:57
 * To change this template use File | Settings | File Templates.
 */
public class RxHelloWorld {

    public static void testSimple() {
        //事件源
        Observable<String> myObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("Hello world!");
                        subscriber.onCompleted();
                    }
                }
        );
        //处理者
        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        };

        myObservable.subscribe(mySubscriber);//观察者订阅了事件源
    }

    public static void testSimple1() {
        Observable<String> myObservable = Observable.just("Hello world");

        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        };

        myObservable.subscribe(onNextAction);
    }

    public static void main(String[] args) {
        testSimple();
        testSimple1();

    }
}
