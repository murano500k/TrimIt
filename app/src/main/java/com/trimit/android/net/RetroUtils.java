package com.trimit.android.net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.trimit.android.model.AuthResponce;
import com.trimit.android.model.EmailExistsResponce;
import com.trimit.android.model.Responce;
import com.trimit.android.model.ResponceBarberType;
import com.trimit.android.model.barber.BarberResponce;
import com.trimit.android.model.createuser.ResponceCreateUser;
import com.trimit.android.model.getuser.ResponceGetUser;
import com.trimit.android.utils.PrefsUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by artem on 4/19/17.
 */

public class RetroUtils {
    private static final String TAG = "RetroUtils";
    public String mBaseUrl;

    public static final String PARAM_CLIENT_ID = "client_id";
    public static final String VALUE_CLIENT_ID = "AndroidFreelancerClient";

    public static final String PARAM_CLIENT_SECRET = "client_secret";
    public static final String VALUE_CLIENT_SECRET = "cGfa2k*GrOMpfkdonfes!jz87f";

    public static final String PARAM_GRANT_TYPE = "grant_type";
    public static final String VALUE_RESPONCE_TYPE = "client_credentials";
    private JsonUtils mJsonUtils;

    private final Api mApi;
    private final Gson mGson;
    Retrofit mRetrofit;
    private PrefsUtils mPrefsUtils;
    private AuthUtils mAuthUtils;

    public RetroUtils(Retrofit retrofit, Gson gson, PrefsUtils prefsUtils) {
        this.mBaseUrl=retrofit.baseUrl().toString();
        this.mRetrofit = retrofit;
        this.mPrefsUtils = prefsUtils;
        this.mGson=gson;
        mApi = mRetrofit.create(Api.class);
        mAuthUtils =new AuthUtils(mPrefsUtils);
        mJsonUtils =new JsonUtils(mGson);
    }
    public Observable<String> authObservable() {
        String requestUrl = mBaseUrl + "oauth/token";
        Observable<String> observableAuthResponce;
        if (mAuthUtils.hasValidToken()){
            Log.d(TAG, "token:"+mAuthUtils.getToken());
            observableAuthResponce = Observable.just(mAuthUtils.getToken());
        } else {
            observableAuthResponce = Rx2AndroidNetworking.post(requestUrl)
                    .addBodyParameter(PARAM_CLIENT_ID, VALUE_CLIENT_ID)
                    .addBodyParameter(PARAM_CLIENT_SECRET, VALUE_CLIENT_SECRET)
                    .addBodyParameter(PARAM_GRANT_TYPE, VALUE_RESPONCE_TYPE)
                    .build()
                    .getObjectObservable(AuthResponce.class)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .map(authResponce -> {
                        String token = authResponce.getAccessToken();
                        Log.d(TAG, "token:"+token);
                        mAuthUtils.updateToken(token);
                        return token;
                    });
        }
        return observableAuthResponce;
    }
    public Observable<EmailExistsResponce> checkEmailObservable(String email){
        return authObservable().flatMap(new Function<String, ObservableSource<EmailExistsResponce>>() {
            @Override
            public ObservableSource<EmailExistsResponce> apply(@NonNull String s) throws Exception {
                JsonObject jsonObject=new JsonObject();
                jsonObject.addProperty("email", email);
                Log.d(TAG, "jsonObject: "+jsonObject.toString());
                return mApi.checkEmailRx(s, jsonObject.toString());
            }
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<ResponceCreateUser> signUpObservable(String user){
        return authObservable().flatMap(new Function<String, Observable<ResponceCreateUser>>() {
            @Override
            public Observable<ResponceCreateUser> apply(@NonNull String token) throws Exception {
                Log.d(TAG, "user: "+user);
                return mApi.createUser(token, user);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<ResponceCreateUser> createUserFromPrefs(){
        return signUpObservable(getUserFromPrefs());
    }

    private String getUserFromPrefs() {
        String email = mPrefsUtils.getStringValue(PrefsUtils.PREFS_KEY_EMAIL);
        String firstName = mPrefsUtils.getStringValue(PrefsUtils.PREFS_KEY_FIRST_NAME);
        String lastName = mPrefsUtils.getStringValue(PrefsUtils.PREFS_KEY_LAST_NAME);
        String password = mPrefsUtils.getStringValue(PrefsUtils.PREFS_KEY_PASSWORD);
        String dob = mPrefsUtils.getStringValue(PrefsUtils.PREFS_KEY_BIRTHDAY);
        String gender = mPrefsUtils.getStringValue(PrefsUtils.PREFS_KEY_GENDER);
        String barber_type_id = mPrefsUtils.getStringValue(PrefsUtils.PREFS_KEY_BARBER_TYPE);
        return mJsonUtils.createUser(firstName,lastName, email,password,gender,dob,barber_type_id);
    }


    public Observable<Responce> forgotPasswordObservable(String email){
        return authObservable().flatMap(new Function<String, ObservableSource<Responce>>() {
            @Override
            public ObservableSource<Responce> apply(@NonNull String s) throws Exception {
                JsonObject jsonObject=new JsonObject();
                jsonObject.addProperty("email", email);
                Log.d(TAG, "jsonObject: "+jsonObject.toString());
                return mApi.forgotPassword(s, jsonObject.toString());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Responce> loginObservable(String email, String password){
        final String loginData = mJsonUtils.getLoginData(email,password);
        return authObservable().flatMap(new Function<String, ObservableSource<Responce>>() {
            @Override
            public ObservableSource<Responce> apply(@NonNull String s) throws Exception {
                Log.d(TAG, "loginData: "+loginData);
                return mApi.login(s, loginData);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ResponceGetUser> getUserObservable(){
        final String userId=mPrefsUtils.getStringValue(PrefsUtils.PREFS_KEY_USER_ID);
        if(userId==null){
            return Observable.error(new Throwable("No user id"));
        }
        return authObservable().flatMap(new Function<String, Observable<ResponceGetUser>>() {
            @Override
            public Observable<ResponceGetUser> apply(@NonNull String s) throws Exception {
                Log.d(TAG, "userId: "+userId);
                return mApi.getUser(userId, s);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ResponceBarberType> getBarberTypesObservable(){
                return authObservable().flatMap(new Function<String, Observable<ResponceBarberType>>() {
            @Override
            public Observable<ResponceBarberType> apply(@NonNull String s) throws Exception {
                return mApi.getBarberTypes(s);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BarberResponce> getBarbersObservable(){
        return authObservable().flatMap(new Function<String, Observable<BarberResponce>>() {
            @Override
            public Observable<BarberResponce> apply(@NonNull String s) throws Exception {
                return mApi.getBarbers(s);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BarberResponce> getBarberObservable(String barberId){
        return authObservable().flatMap(new Function<String, Observable<BarberResponce>>() {
            @Override
            public Observable<BarberResponce> apply(@NonNull String s) throws Exception {
                return mApi.getBarber(barberId,s);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BarberResponce> findBarbers(LatLng point){
        return authObservable().flatMap(new Function<String, Observable<BarberResponce>>() {
            @Override
            public Observable<BarberResponce> apply(@NonNull String s) throws Exception {
                return mApi.findBarbers(s, mJsonUtils.getLocationData(point));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
