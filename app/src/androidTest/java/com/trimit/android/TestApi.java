package com.trimit.android;

import android.app.Instrumentation;
import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.trimit.android.model.EmailExistsResponce;
import com.trimit.android.net.JsonUtils;
import com.trimit.android.net.RetroUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by artem on 4/19/17.
 */
@RunWith(AndroidJUnit4.class)
public class TestApi {
    Instrumentation mInstrumentation;
    Context mContext;
    private CountingIdlingResource mIdle;
    private static final String TAG = "TestApi";
    @Before
    public void before(){
        mInstrumentation= InstrumentationRegistry.getInstrumentation();
        mContext=mInstrumentation.getTargetContext();
        mIdle = new CountingIdlingResource("api");
    }

    @Test
    public void testApiCheckEmail(){
        RetroUtils retroUtils =new RetroUtils(mContext);
        mIdle.increment();

        retroUtils.checkEmailObservable("murano500k@gmail.com").subscribe(new Consumer<EmailExistsResponce>() {
            @Override
            public void accept(@NonNull EmailExistsResponce emailExistsResponce) throws Exception {
                Log.d(TAG, "accept: "+emailExistsResponce);
                mIdle.decrement();
            }

        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                throwable.printStackTrace();
                mIdle.decrement();
            }
        });
        while (!mIdle.isIdleNow()) SystemClock.sleep(1000);
    }
    @Test
    public void testJsonUser(){
        JsonUtils jsonUtils=new JsonUtils();
        String user = jsonUtils.createUser("aaa", "aaa", "murano500k@gmail.com", "qwerty", "Male", "1990-11-11", "3");
        Log.d(TAG, "testJsonUser: "+user);
        RetroUtils retroUtils = new RetroUtils(mContext);
        mIdle.increment();
        retroUtils.signUpObservable(user).subscribe(o -> {
            Log.d(TAG, "responce: " + o.toString());
            mIdle.decrement();
        }, throwable -> {
            throwable.printStackTrace();
            mIdle.decrement();
            Assert.fail(throwable.getMessage());
        });
        while (!mIdle.isIdleNow()) SystemClock.sleep(1000);

    }

    @Test
    public void testForgotPwd(){
        RetroUtils retroUtils = new RetroUtils(mContext);
        mIdle.increment();
        retroUtils.forgotPasswordObservable("murano500k@gmail.com").subscribe(o -> {
            Log.d(TAG, "responce: " + o.toString());
            mIdle.decrement();
        }, throwable -> {
            throwable.printStackTrace();
            mIdle.decrement();
            Assert.fail(throwable.getMessage());
        });
        while (!mIdle.isIdleNow()) SystemClock.sleep(1000);

    }

    @Test
    public void testLogin(){
        RetroUtils retroUtils = new RetroUtils(mContext);
        mIdle.increment();
        retroUtils.loginObservable("murano500k@gmail.com", "qwerty").subscribe(o -> {
            Log.d(TAG, "responce: " + o.toString());
            mIdle.decrement();
        }, throwable -> {
            throwable.printStackTrace();
            mIdle.decrement();
            Assert.fail(throwable.getMessage());
        });
        while (!mIdle.isIdleNow()) SystemClock.sleep(1000);

    }
}
