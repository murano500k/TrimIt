package com.trimit.android.net;

import android.content.Context;
import android.util.Log;

import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.trimit.android.model.AuthResponce;
import com.trimit.android.utils.AuthUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.trimit.android.net.NetworkUtils.ApiRequests.BASE_URL;
import static com.trimit.android.net.NetworkUtils.ApiRequests.PARAM_CLIENT_ID;
import static com.trimit.android.net.NetworkUtils.ApiRequests.PARAM_CLIENT_SECRET;
import static com.trimit.android.net.NetworkUtils.ApiRequests.PARAM_GRANT_TYPE;
import static com.trimit.android.net.NetworkUtils.ApiRequests.VALUE_CLIENT_ID;
import static com.trimit.android.net.NetworkUtils.ApiRequests.VALUE_CLIENT_SECRET;
import static com.trimit.android.net.NetworkUtils.ApiRequests.VALUE_RESPONCE_TYPE;

/**
 * Created by artem on 4/18/17.
 */

public class NetworkUtils {
    private static final String TAG = "NetworkUtils";
    private static final String PARAM_ACCESS_TOKEN = "token";
    private final Context mContext;

    public NetworkUtils(Context context) {
        mContext = context;
    }


    public class ApiRequests {

        public static final String BASE_URL = "https://api.trimitapp.co.uk/v1-dev";
        public static final String REQUEST_AUTH = "/oauth/token";
        public static final String REQUEST_USER = "/user";
        public static final String REQUEST_LOGIN = "/login";

        public static final String PARAM_CLIENT_ID = "client_id";
        public static final String VALUE_CLIENT_ID = "AndroidFreelancerClient";

        public static final String PARAM_CLIENT_SECRET = "client_secret";
        public static final String VALUE_CLIENT_SECRET = "cGfa2k*GrOMpfkdonfes!jz87f";

        public static final String PARAM_GRANT_TYPE = "grant_type";
        public static final String VALUE_RESPONCE_TYPE = "client_credentials";

        private static final String PARAM_ACCESS_TOKEN = "token";
    }

    public void signUp(){
        String requestUrl=BASE_URL+ApiRequests.REQUEST_USER;
        Observable<String> observableSignUp;
    }
    public void getUsers(){
        String requestUrl=BASE_URL+ApiRequests.REQUEST_USER;
        authObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<String, Observable<String>>() {
            @Override
            public Observable<String> apply(@NonNull String token) throws Exception {
                return  Rx2AndroidNetworking.get(requestUrl)
                        .addPathParameter(PARAM_ACCESS_TOKEN, token)
                        .build()
                        .getStringObservable();
            }
        })

                //.observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext: "+s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public Observable<String> authObservable() {
        String requestUrl = BASE_URL + ApiRequests.REQUEST_AUTH;
        Log.d(TAG, "auth: " + requestUrl);
        Observable<String> observableAuthResponce;
        if (AuthUtils.hasValidToken(mContext))
            observableAuthResponce = Observable.just(AuthUtils.getToken(mContext));
        else {
            observableAuthResponce = Rx2AndroidNetworking.post(requestUrl)
                    .addBodyParameter(PARAM_CLIENT_ID, VALUE_CLIENT_ID)
                    .addBodyParameter(PARAM_CLIENT_SECRET, VALUE_CLIENT_SECRET)
                    .addBodyParameter(PARAM_GRANT_TYPE, VALUE_RESPONCE_TYPE)
                    .build()
                    .getObjectObservable(AuthResponce.class)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .map(new Function<AuthResponce, String>() {
                        @Override
                        public String apply(@NonNull AuthResponce authResponce) throws Exception {
                            Log.d(TAG, "apply: "+authResponce);
                            String token = authResponce.getAccessToken();
                            AuthUtils.updateToken(mContext, token);
                            return token;
                        }
                    });
        }
        return observableAuthResponce;
                //.observeOn(AndroidSchedulers.mainThread());
                /*.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String token) throws Exception {
                Log.d(TAG, "accept: " + token);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Log.e(TAG, "accept: ", throwable );
            }
        });*/
    }

}
