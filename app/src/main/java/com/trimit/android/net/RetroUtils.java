package com.trimit.android.net;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.trimit.android.model.AuthResponce;
import com.trimit.android.model.EmailExistsResponce;
import com.trimit.android.model.Responce;
import com.trimit.android.model.ResponceUserCreate;
import com.trimit.android.utils.AuthUtils;
import com.trimit.android.utils.PrefsUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by artem on 4/19/17.
 */

public class RetroUtils {
    private final Api api;
    Retrofit retrofit;
    private static final String TAG = "RetroUtils";
    public static final String BASE_URL = "https://api.trimitapp.co.uk/v1-dev/";

    public static final String PARAM_CLIENT_ID = "client_id";
    public static final String VALUE_CLIENT_ID = "AndroidFreelancerClient";

    public static final String PARAM_CLIENT_SECRET = "client_secret";
    public static final String VALUE_CLIENT_SECRET = "cGfa2k*GrOMpfkdonfes!jz87f";

    public static final String PARAM_GRANT_TYPE = "grant_type";
    public static final String VALUE_RESPONCE_TYPE = "client_credentials";
    private Context mContext;
    private JsonUtils jsonUtils;
    public RetroUtils(Context context) {
        this.mContext = context;
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        RxJava2CallAdapterFactory rxJava2CallAdapterFactory=RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .build();
        api = retrofit.create(Api.class);
        jsonUtils=new JsonUtils();
    }
    public Observable<String> authObservable() {
        String requestUrl = BASE_URL + "oauth/token";
        Log.d(TAG, "auth: " + requestUrl);
        Observable<String> observableAuthResponce;
        if (AuthUtils.hasValidToken(mContext)){
            observableAuthResponce = Observable.just(AuthUtils.getToken(mContext));
            Log.d(TAG, "authObservable: has token");
        } else {
            Log.d(TAG, "authObservable: get token");
            observableAuthResponce = Rx2AndroidNetworking.post(requestUrl)
                    .addBodyParameter(PARAM_CLIENT_ID, VALUE_CLIENT_ID)
                    .addBodyParameter(PARAM_CLIENT_SECRET, VALUE_CLIENT_SECRET)
                    .addBodyParameter(PARAM_GRANT_TYPE, VALUE_RESPONCE_TYPE)
                    .build()
                    .getObjectObservable(AuthResponce.class)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .map(authResponce -> {
                        Log.d(TAG, "apply: "+authResponce);
                        String token = authResponce.getAccessToken();
                        AuthUtils.updateToken(mContext, token);
                        return token;
                    });
        }
        return observableAuthResponce;
    }
    public Observable<EmailExistsResponce> checkEmailObservable(String email){
        return authObservable().flatMap(new Function<String, ObservableSource<EmailExistsResponce>>() {
            @Override
            public ObservableSource<EmailExistsResponce> apply(@NonNull String s) throws Exception {
                Log.d(TAG, "token:"+s);
                JsonObject jsonObject=new JsonObject();
                jsonObject.addProperty("email", email);
                Log.d(TAG, "jsonObject: "+jsonObject.toString());
                return api.checkEmailRx(s, jsonObject.toString());
            }
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<ResponceUserCreate> signUpObservable(String user){
        return authObservable().flatMap(new Function<String, Observable<ResponceUserCreate>>() {
            @Override
            public Observable<ResponceUserCreate> apply(@NonNull String token) throws Exception {
                Log.d(TAG, "token: "+token);
                Log.d(TAG, "user: "+user);
                return api.createUser(token, user);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<ResponceUserCreate> createUserFromPrefs(){
        return signUpObservable(getUserFromPrefs());
    }

    private String getUserFromPrefs() {
        String email = PrefsUtils.getStringValue(mContext, PrefsUtils.PREFS_KEY_EMAIL);
        String firstName = PrefsUtils.getStringValue(mContext, PrefsUtils.PREFS_KEY_FIRST_NAME);
        String lastName = PrefsUtils.getStringValue(mContext, PrefsUtils.PREFS_KEY_LAST_NAME);
        String password = PrefsUtils.getStringValue(mContext, PrefsUtils.PREFS_KEY_PASSWORD);
        String dob = PrefsUtils.getStringValue(mContext, PrefsUtils.PREFS_KEY_BIRTHDAY);
        String gender = PrefsUtils.getStringValue(mContext, PrefsUtils.PREFS_KEY_GENDER);
        String barber_type_id = PrefsUtils.getStringValue(mContext, PrefsUtils.PREFS_KEY_BARBER_TYPE);
        return jsonUtils.createUser(firstName,lastName, email,password,gender,dob,barber_type_id);
    }


    public Observable<Responce> forgotPasswordObservable(String email){
        return authObservable().flatMap(new Function<String, ObservableSource<Responce>>() {
            @Override
            public ObservableSource<Responce> apply(@NonNull String s) throws Exception {
                Log.d(TAG, "token:"+s);
                JsonObject jsonObject=new JsonObject();
                jsonObject.addProperty("email", email);
                Log.d(TAG, "jsonObject: "+jsonObject.toString());
                return api.forgotPassword(s, jsonObject.toString());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Responce> loginObservable(String email, String password){
        final String loginData = jsonUtils.getLoginData(email,password);
        return authObservable().flatMap(new Function<String, ObservableSource<Responce>>() {
            @Override
            public ObservableSource<Responce> apply(@NonNull String s) throws Exception {
                Log.d(TAG, "token:"+s);
                Log.d(TAG, "loginData: "+loginData);
                return api.login(s, loginData);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
