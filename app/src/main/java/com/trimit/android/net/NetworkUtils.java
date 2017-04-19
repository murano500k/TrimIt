package com.trimit.android.net;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.common.Priority;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.trimit.android.model.Account;
import com.trimit.android.model.AuthResponce;
import com.trimit.android.model.UserCreate;
import com.trimit.android.utils.AuthUtils;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
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
    private static final String PARAM_ACCESS_TOKEN = "access_token";
    private static final String PARAM_EMAIL = "email";
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
                return  Rx2AndroidNetworking.post(requestUrl)
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

    public Account createAccount(String firstName, String lastName, String email, String password, String timestamp){
        Account account = new Account();
        account.setEmail(email);
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setTimestamp(timestamp);
        account.setPassword(password);
        return account;
    }

    public UserCreate createUser(Account account, String gender, String dob, int barberTypeId){
        UserCreate user = new UserCreate();
        user.setGender(gender);
        user.setDob(dob);
        user.setBarberTypeId(barberTypeId);
        user.setLoyaltyPoints(0);
        user.addAccount(account);
        return user;
    }

    public JSONObject parseUser(UserCreate user) throws JSONException {
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("gender", user.getGender());
        jsonUser.put("dob", user.getDob());
        jsonUser.put("barber_type_id", "3"/*user.getBarberTypeId()*/);
        jsonUser.put("loyalty_points", "0");
        JSONObject jsonAccount = new JSONObject();
        Account account = user.getAccounts().get(0);
        jsonAccount.put("email", account.getEmail());
        jsonAccount.put("first_name", account.getFirstName());
        jsonAccount.put("last_name", account.getLastName());
        jsonAccount.put("password", account.getPassword());
        jsonAccount.put("timestamp", account.getTimestamp());

        //JSONArray accountArray=new JSONArray();
        //accountArray.put(jsonAccount);
        jsonUser.put("account", jsonAccount);
        return jsonUser;
    }



    public void signUp(UserCreate user){
        String requestUrl = BASE_URL + ApiRequests.REQUEST_USER;

        try {
        final     JSONObject     jsonUser = parseUser(user);

        Log.d(TAG, "jsonUser: " + jsonUser);
        authObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<String, Observable<JSONObject>>() {
                    @Override
                    public Observable<JSONObject> apply(@NonNull String token) throws Exception {
                        Log.d(TAG, "token to create user: "+token);
                        return  Rx2AndroidNetworking.post(requestUrl)
                                .addPathParameter(PARAM_ACCESS_TOKEN, token)

                                .addApplicationJsonBody(jsonUser)
                                .setTag("test")
                                .setPriority(Priority.MEDIUM)

                                .build()
                                .getJSONObjectObservable();
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JSONObject>() {
                    @Override
                    public void accept(@NonNull JSONObject s) throws Exception {
                        Log.d(TAG, "accept: " + s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        Log.e(TAG, "accept: "+throwable.getMessage());
                    }
                });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    public void emailExists(String email) throws JSONException {
        String requestUrl = "https://api.trimitapp.co.uk/v1-dev/account/email_exists?access_token=f335dfacd294ce6b2d7749998b8d69ce53d5b0d6";//BASE_URL + "/account/email_exists";
        Log.d(TAG, "emailExists: url = "+requestUrl);

        Rx2AndroidNetworking.post(requestUrl)
                //.addPathParameter(PARAM_ACCESS_TOKEN, "f335dfacd294ce6b2d7749998b8d69ce53d5b0d6")
                .addHeaders("content-type", "application/x-www-form-urlencoded")
                .addBodyParameter("data","%7B%20%22email%22%20%3A%20%22user11111%40gmail.com%22%20%7D")
                //.addBodyParameter("email", email)
                .build()
                .getJSONObjectObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JSONObject s) {
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
    /*
    access_token:3533025da7840c9f5108d45ca84509a0600b2e91
gender:Male
dob:2003-08-03
barber_type_id:3
account.last_name:qwe
account.first_name:qweee
account.password:qwerty
account.email:adddddd@gmail.com
*/
    /*
    access_token:3533025da7840c9f5108d45ca84509a0600b2e91
gender:Male
dob:2003-08-03
barber_type_id:3
account.last_name:qwe
account.first_name:qweee
account.password:qwerty
account.email:adddddd@gmail.co,
    */
/*
"user":{
"gender":"Male",
"dob":"1990-05-04",
"barber_type_id":"3",
"loyalty_points":"0",
"account":{
	"email":"qwe1qqq@www.com",
	"first_name":"kllkj",
	"last_name":"egeg",
	"password":"qwerty",
	"timestamp":"2017-02-18 16:04"
	}
}
curl -i -X POST -H 'Content-Type: application/json' -d '{"email": "aa@ff.cc", "password": "qwerty"}' https://api.trimitapp.co.uk/v1-dev/login?access_token=c6bb3974c234a5309be86af75090338ad50abd63
curl -i -X POST -H 'Content-Type: application/json' -d '{"email": "aa@ff.cc"}' https://api.trimitapp.co.uk/v1-dev/login?access_token=3533025da7840c9f5108d45ca84509a0600b2e91
curl -i -X POST -H "Content-Type:application/json" https://api.trimitapp.co.uk/v1-dev/account/email_exists?access_token=3533025da7840c9f5108d45ca84509a0600b2e91 -d '{"email":"qwe1qqq@www.com"}'
*/


/*
curl -X POST \
  'https://api.trimitapp.co.uk/v1-dev/account/email_exists?access_token=f335dfacd294ce6b2d7749998b8d69ce53d5b0d6' \
  -H 'content-type: application/x-www-form-urlencoded' \
  -d data=%7B%20%22email%22%20%3A%20%22user11111%40gmail.com%22%20%7D

*/
}
